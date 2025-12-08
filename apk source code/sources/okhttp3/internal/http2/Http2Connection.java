package okhttp3.internal.http2;

import defpackage.g9;
import defpackage.mn;
import defpackage.qn;
import defpackage.rn;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

/* loaded from: classes.dex */
public final class Http2Connection implements Closeable {
    public static final ExecutorService y = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Http2Connection", true));
    public final boolean a;
    public final Listener b;
    public final String d;
    public int e;
    public int f;
    public boolean g;
    public final ScheduledExecutorService h;
    public final ExecutorService i;
    public final PushObserver j;
    public long r;
    public final Socket u;
    public final rn v;
    public final g w;
    public final Map<Integer, Http2Stream> c = new LinkedHashMap();
    public long k = 0;
    public long l = 0;
    public long m = 0;
    public long n = 0;
    public long o = 0;
    public long p = 0;
    public long q = 0;
    public Settings s = new Settings();
    public final Settings t = new Settings();
    public final Set<Integer> x = new LinkedHashSet();

    public static abstract class Listener {
        public static final Listener REFUSE_INCOMING_STREAMS = new a();

        public class a extends Listener {
            @Override // okhttp3.internal.http2.Http2Connection.Listener
            public void onStream(Http2Stream http2Stream) throws IOException {
                http2Stream.close(ErrorCode.REFUSED_STREAM, null);
            }
        }

        public void onSettings(Http2Connection http2Connection) {
        }

        public abstract void onStream(Http2Stream http2Stream) throws IOException;
    }

    public class a extends NamedRunnable {
        public final /* synthetic */ int a;
        public final /* synthetic */ ErrorCode b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(String str, Object[] objArr, int i, ErrorCode errorCode) {
            super(str, objArr);
            this.a = i;
            this.b = errorCode;
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() throws IOException {
            try {
                Http2Connection http2Connection = Http2Connection.this;
                http2Connection.v.a(this.a, this.b);
            } catch (IOException e) {
                Http2Connection.a(Http2Connection.this, e);
            }
        }
    }

    public class b extends NamedRunnable {
        public final /* synthetic */ int a;
        public final /* synthetic */ long b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(String str, Object[] objArr, int i, long j) {
            super(str, objArr);
            this.a = i;
            this.b = j;
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() throws IOException {
            try {
                Http2Connection.this.v.a(this.a, this.b);
            } catch (IOException e) {
                Http2Connection.a(Http2Connection.this, e);
            }
        }
    }

    public class c extends NamedRunnable {
        public c(String str, Object... objArr) {
            super(str, objArr);
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() throws IOException {
            Http2Connection.this.a(false, 2, 0);
        }
    }

    public class d extends NamedRunnable {
        public final /* synthetic */ int a;
        public final /* synthetic */ List b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d(String str, Object[] objArr, int i, List list) {
            super(str, objArr);
            this.a = i;
            this.b = list;
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() {
            if (Http2Connection.this.j.onRequest(this.a, this.b)) {
                try {
                    Http2Connection.this.v.a(this.a, ErrorCode.CANCEL);
                    synchronized (Http2Connection.this) {
                        Http2Connection.this.x.remove(Integer.valueOf(this.a));
                    }
                } catch (IOException unused) {
                }
            }
        }
    }

    public final class e extends NamedRunnable {
        public e() {
            super("OkHttp %s ping", Http2Connection.this.d);
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() throws IOException {
            boolean z;
            synchronized (Http2Connection.this) {
                if (Http2Connection.this.l < Http2Connection.this.k) {
                    z = true;
                } else {
                    Http2Connection.this.k++;
                    z = false;
                }
            }
            if (z) {
                Http2Connection.a(Http2Connection.this, (IOException) null);
            } else {
                Http2Connection.this.a(false, 1, 0);
            }
        }
    }

    public final class f extends NamedRunnable {
        public final boolean a;
        public final int b;
        public final int c;

        public f(boolean z, int i, int i2) {
            super("OkHttp %s ping %08x%08x", Http2Connection.this.d, Integer.valueOf(i), Integer.valueOf(i2));
            this.a = z;
            this.b = i;
            this.c = i2;
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() throws IOException {
            Http2Connection.this.a(this.a, this.b, this.c);
        }
    }

    public Http2Connection(Builder builder) {
        this.j = builder.f;
        boolean z = builder.g;
        this.a = z;
        this.b = builder.e;
        int i = z ? 1 : 2;
        this.f = i;
        if (builder.g) {
            this.f = i + 2;
        }
        if (builder.g) {
            this.s.a(7, 16777216);
        }
        this.d = builder.b;
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, Util.threadFactory(Util.format("OkHttp %s Writer", this.d), false));
        this.h = scheduledThreadPoolExecutor;
        if (builder.h != 0) {
            e eVar = new e();
            int i2 = builder.h;
            scheduledThreadPoolExecutor.scheduleAtFixedRate(eVar, i2, i2, TimeUnit.MILLISECONDS);
        }
        this.i = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(Util.format("OkHttp %s Push Observer", this.d), true));
        this.t.a(7, 65535);
        this.t.a(5, 16384);
        this.r = this.t.a();
        this.u = builder.a;
        this.v = new rn(builder.d, this.a);
        this.w = new g(new qn(builder.c, this.a));
    }

    public synchronized Http2Stream a(int i) {
        return this.c.get(Integer.valueOf(i));
    }

    public boolean b(int i) {
        return i != 0 && (i & 1) == 0;
    }

    public synchronized Http2Stream c(int i) {
        Http2Stream http2StreamRemove;
        http2StreamRemove = this.c.remove(Integer.valueOf(i));
        notifyAll();
        return http2StreamRemove;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        a(ErrorCode.NO_ERROR, ErrorCode.CANCEL, (IOException) null);
    }

    public void flush() throws IOException {
        this.v.flush();
    }

    public synchronized boolean isHealthy(long j) {
        if (this.g) {
            return false;
        }
        if (this.n < this.m) {
            if (j >= this.p) {
                return false;
            }
        }
        return true;
    }

    public synchronized int maxConcurrentStreams() {
        Settings settings;
        settings = this.t;
        return (settings.a & 16) != 0 ? settings.b[4] : Integer.MAX_VALUE;
    }

    public Http2Stream newStream(List<Header> list, boolean z) throws IOException {
        return a(0, list, z);
    }

    public synchronized int openStreamCount() {
        return this.c.size();
    }

    public Http2Stream pushStream(int i, List<Header> list, boolean z) throws IOException {
        if (this.a) {
            throw new IllegalStateException("Client cannot push requests.");
        }
        return a(i, list, z);
    }

    public void setSettings(Settings settings) throws IOException {
        synchronized (this.v) {
            synchronized (this) {
                if (this.g) {
                    throw new ConnectionShutdownException();
                }
                this.s.a(settings);
            }
            this.v.b(settings);
        }
    }

    public void shutdown(ErrorCode errorCode) throws IOException {
        synchronized (this.v) {
            synchronized (this) {
                if (this.g) {
                    return;
                }
                this.g = true;
                this.v.a(this.e, errorCode, Util.EMPTY_BYTE_ARRAY);
            }
        }
    }

    public void start() throws IOException {
        this.v.a();
        this.v.b(this.s);
        if (this.s.a() != 65535) {
            this.v.a(0, r0 - 65535);
        }
        new Thread(this.w).start();
    }

    public void writeData(int i, boolean z, Buffer buffer, long j) throws IOException {
        int iMin;
        long j2;
        if (j == 0) {
            this.v.a(z, i, buffer, 0);
            return;
        }
        while (j > 0) {
            synchronized (this) {
                while (this.r <= 0) {
                    try {
                        if (!this.c.containsKey(Integer.valueOf(i))) {
                            throw new IOException("stream closed");
                        }
                        wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                        throw new InterruptedIOException();
                    }
                }
                iMin = Math.min((int) Math.min(j, this.r), this.v.d);
                j2 = iMin;
                this.r -= j2;
            }
            j -= j2;
            this.v.a(z && j == 0, i, buffer, iMin);
        }
    }

    public synchronized void a(long j) {
        long j2 = this.q + j;
        this.q = j2;
        if (j2 >= this.s.a() / 2) {
            a(0, this.q);
            this.q = 0L;
        }
    }

    public static class Builder {
        public Socket a;
        public String b;
        public BufferedSource c;
        public BufferedSink d;
        public Listener e = Listener.REFUSE_INCOMING_STREAMS;
        public PushObserver f = PushObserver.CANCEL;
        public boolean g;
        public int h;

        public Builder(boolean z) {
            this.g = z;
        }

        public Http2Connection build() {
            return new Http2Connection(this);
        }

        public Builder listener(Listener listener) {
            this.e = listener;
            return this;
        }

        public Builder pingIntervalMillis(int i) {
            this.h = i;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.f = pushObserver;
            return this;
        }

        public Builder socket(Socket socket) throws IOException {
            SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
            return socket(socket, remoteSocketAddress instanceof InetSocketAddress ? ((InetSocketAddress) remoteSocketAddress).getHostName() : remoteSocketAddress.toString(), Okio.buffer(Okio.source(socket)), Okio.buffer(Okio.sink(socket)));
        }

        public Builder socket(Socket socket, String str, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.a = socket;
            this.b = str;
            this.c = bufferedSource;
            this.d = bufferedSink;
            return this;
        }
    }

    public final Http2Stream a(int i, List<Header> list, boolean z) throws IOException {
        int i2;
        Http2Stream http2Stream;
        boolean z2;
        boolean z3 = !z;
        synchronized (this.v) {
            synchronized (this) {
                if (this.f > 1073741823) {
                    shutdown(ErrorCode.REFUSED_STREAM);
                }
                if (!this.g) {
                    i2 = this.f;
                    this.f += 2;
                    http2Stream = new Http2Stream(i2, this, z3, false, null);
                    z2 = !z || this.r == 0 || http2Stream.b == 0;
                    if (http2Stream.isOpen()) {
                        this.c.put(Integer.valueOf(i2), http2Stream);
                    }
                } else {
                    throw new ConnectionShutdownException();
                }
            }
            if (i == 0) {
                this.v.a(z3, i2, list);
            } else if (!this.a) {
                this.v.a(i, i2, list);
            } else {
                throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
            }
        }
        if (z2) {
            this.v.flush();
        }
        return http2Stream;
    }

    public class g extends NamedRunnable implements qn.b {
        public final qn a;

        public class a extends NamedRunnable {
            public final /* synthetic */ Http2Stream a;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public a(String str, Object[] objArr, Http2Stream http2Stream) {
                super(str, objArr);
                this.a = http2Stream;
            }

            @Override // okhttp3.internal.NamedRunnable
            public void execute() {
                try {
                    Http2Connection.this.b.onStream(this.a);
                } catch (IOException e) {
                    Platform platform = Platform.get();
                    StringBuilder sbA = g9.a("Http2Connection.Listener failure for ");
                    sbA.append(Http2Connection.this.d);
                    platform.log(4, sbA.toString(), e);
                    try {
                        this.a.close(ErrorCode.PROTOCOL_ERROR, e);
                    } catch (IOException unused) {
                    }
                }
            }
        }

        public class b extends NamedRunnable {
            public b(String str, Object... objArr) {
                super(str, objArr);
            }

            @Override // okhttp3.internal.NamedRunnable
            public void execute() {
                Http2Connection http2Connection = Http2Connection.this;
                http2Connection.b.onSettings(http2Connection);
            }
        }

        public g(qn qnVar) {
            super("OkHttp %s", Http2Connection.this.d);
            this.a = qnVar;
        }

        public void a(boolean z, int i, int i2, List<Header> list) {
            if (Http2Connection.this.b(i)) {
                Http2Connection http2Connection = Http2Connection.this;
                if (http2Connection == null) {
                    throw null;
                }
                try {
                    http2Connection.a(new mn(http2Connection, "OkHttp %s Push Headers[%s]", new Object[]{http2Connection.d, Integer.valueOf(i)}, i, list, z));
                    return;
                } catch (RejectedExecutionException unused) {
                    return;
                }
            }
            synchronized (Http2Connection.this) {
                Http2Stream http2StreamA = Http2Connection.this.a(i);
                if (http2StreamA != null) {
                    http2StreamA.a(Util.toHeaders(list), z);
                    return;
                }
                if (Http2Connection.this.g) {
                    return;
                }
                if (i <= Http2Connection.this.e) {
                    return;
                }
                if (i % 2 == Http2Connection.this.f % 2) {
                    return;
                }
                Http2Stream http2Stream = new Http2Stream(i, Http2Connection.this, false, z, Util.toHeaders(list));
                Http2Connection.this.e = i;
                Http2Connection.this.c.put(Integer.valueOf(i), http2Stream);
                Http2Connection.y.execute(new a("OkHttp %s stream %d", new Object[]{Http2Connection.this.d, Integer.valueOf(i)}, http2Stream));
            }
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() throws Throwable {
            ErrorCode errorCode;
            ErrorCode errorCode2;
            ErrorCode errorCode3 = ErrorCode.INTERNAL_ERROR;
            IOException e = null;
            try {
                this.a.a(this);
                while (this.a.a(false, (qn.b) this)) {
                }
                errorCode = ErrorCode.NO_ERROR;
            } catch (IOException e2) {
                e = e2;
            } catch (Throwable th) {
                th = th;
                errorCode = errorCode3;
                Http2Connection.this.a(errorCode, errorCode3, e);
                Util.closeQuietly(this.a);
                throw th;
            }
            try {
                try {
                    errorCode2 = ErrorCode.CANCEL;
                } catch (Throwable th2) {
                    th = th2;
                    Http2Connection.this.a(errorCode, errorCode3, e);
                    Util.closeQuietly(this.a);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                errorCode = ErrorCode.PROTOCOL_ERROR;
                errorCode2 = ErrorCode.PROTOCOL_ERROR;
                Http2Connection.this.a(errorCode, errorCode2, e);
                Util.closeQuietly(this.a);
            }
            Http2Connection.this.a(errorCode, errorCode2, e);
            Util.closeQuietly(this.a);
        }

        public void a(boolean z, Settings settings) {
            Http2Stream[] http2StreamArr;
            long j;
            synchronized (Http2Connection.this.v) {
                synchronized (Http2Connection.this) {
                    int iA = Http2Connection.this.t.a();
                    if (z) {
                        Settings settings2 = Http2Connection.this.t;
                        settings2.a = 0;
                        Arrays.fill(settings2.b, 0);
                    }
                    Http2Connection.this.t.a(settings);
                    int iA2 = Http2Connection.this.t.a();
                    http2StreamArr = null;
                    if (iA2 == -1 || iA2 == iA) {
                        j = 0;
                    } else {
                        j = iA2 - iA;
                        if (!Http2Connection.this.c.isEmpty()) {
                            http2StreamArr = (Http2Stream[]) Http2Connection.this.c.values().toArray(new Http2Stream[Http2Connection.this.c.size()]);
                        }
                    }
                }
                try {
                    Http2Connection.this.v.a(Http2Connection.this.t);
                } catch (IOException e) {
                    Http2Connection.a(Http2Connection.this, e);
                }
            }
            if (http2StreamArr != null) {
                for (Http2Stream http2Stream : http2StreamArr) {
                    synchronized (http2Stream) {
                        http2Stream.b += j;
                        if (j > 0) {
                            http2Stream.notifyAll();
                        }
                    }
                }
            }
            Http2Connection.y.execute(new b("OkHttp %s settings", Http2Connection.this.d));
        }

        public void a(boolean z, int i, int i2) {
            if (z) {
                synchronized (Http2Connection.this) {
                    try {
                        if (i == 1) {
                            Http2Connection.this.l++;
                        } else if (i == 2) {
                            Http2Connection.this.n++;
                        } else if (i == 3) {
                            Http2Connection.this.o++;
                            Http2Connection.this.notifyAll();
                        }
                    } finally {
                    }
                }
                return;
            }
            try {
                Http2Connection.this.h.execute(Http2Connection.this.new f(true, i, i2));
            } catch (RejectedExecutionException unused) {
            }
        }

        public void a(int i, ErrorCode errorCode, ByteString byteString) {
            Http2Stream[] http2StreamArr;
            byteString.size();
            synchronized (Http2Connection.this) {
                http2StreamArr = (Http2Stream[]) Http2Connection.this.c.values().toArray(new Http2Stream[Http2Connection.this.c.size()]);
                Http2Connection.this.g = true;
            }
            for (Http2Stream http2Stream : http2StreamArr) {
                if (http2Stream.getId() > i && http2Stream.isLocallyInitiated()) {
                    http2Stream.a(ErrorCode.REFUSED_STREAM);
                    Http2Connection.this.c(http2Stream.getId());
                }
            }
        }

        public void a(int i, long j) {
            if (i == 0) {
                synchronized (Http2Connection.this) {
                    Http2Connection.this.r += j;
                    Http2Connection.this.notifyAll();
                }
                return;
            }
            Http2Stream http2StreamA = Http2Connection.this.a(i);
            if (http2StreamA != null) {
                synchronized (http2StreamA) {
                    http2StreamA.b += j;
                    if (j > 0) {
                        http2StreamA.notifyAll();
                    }
                }
            }
        }
    }

    public void a(int i, ErrorCode errorCode) {
        try {
            this.h.execute(new a("OkHttp %s stream %d", new Object[]{this.d, Integer.valueOf(i)}, i, errorCode));
        } catch (RejectedExecutionException unused) {
        }
    }

    public void a(int i, long j) {
        try {
            this.h.execute(new b("OkHttp Window Update %s stream %d", new Object[]{this.d, Integer.valueOf(i)}, i, j));
        } catch (RejectedExecutionException unused) {
        }
    }

    public void a(boolean z, int i, int i2) throws IOException {
        try {
            this.v.a(z, i, i2);
        } catch (IOException e2) {
            ErrorCode errorCode = ErrorCode.PROTOCOL_ERROR;
            a(errorCode, errorCode, e2);
        }
    }

    public void a(ErrorCode errorCode, ErrorCode errorCode2, @Nullable IOException iOException) throws IOException {
        try {
            shutdown(errorCode);
        } catch (IOException unused) {
        }
        Http2Stream[] http2StreamArr = null;
        synchronized (this) {
            if (!this.c.isEmpty()) {
                http2StreamArr = (Http2Stream[]) this.c.values().toArray(new Http2Stream[this.c.size()]);
                this.c.clear();
            }
        }
        if (http2StreamArr != null) {
            for (Http2Stream http2Stream : http2StreamArr) {
                try {
                    http2Stream.close(errorCode2, iOException);
                } catch (IOException unused2) {
                }
            }
        }
        try {
            this.v.close();
        } catch (IOException unused3) {
        }
        try {
            this.u.close();
        } catch (IOException unused4) {
        }
        this.h.shutdown();
        this.i.shutdown();
    }

    public static /* synthetic */ void a(Http2Connection http2Connection, IOException iOException) throws IOException {
        if (http2Connection == null) {
            throw null;
        }
        ErrorCode errorCode = ErrorCode.PROTOCOL_ERROR;
        http2Connection.a(errorCode, errorCode, iOException);
    }

    public void a() {
        synchronized (this) {
            if (this.n < this.m) {
                return;
            }
            this.m++;
            this.p = System.nanoTime() + 1000000000;
            try {
                this.h.execute(new c("OkHttp %s ping", this.d));
            } catch (RejectedExecutionException unused) {
            }
        }
    }

    public void a(int i, List<Header> list) {
        synchronized (this) {
            if (this.x.contains(Integer.valueOf(i))) {
                a(i, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.x.add(Integer.valueOf(i));
            try {
                a(new d("OkHttp %s Push Request[%s]", new Object[]{this.d, Integer.valueOf(i)}, i, list));
            } catch (RejectedExecutionException unused) {
            }
        }
    }

    public final synchronized void a(NamedRunnable namedRunnable) {
        if (!this.g) {
            this.i.execute(namedRunnable);
        }
    }
}
