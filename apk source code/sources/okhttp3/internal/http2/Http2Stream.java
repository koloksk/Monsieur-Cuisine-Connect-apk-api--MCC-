package okhttp3.internal.http2;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* loaded from: classes.dex */
public final class Http2Stream {
    public long b;
    public final int c;
    public final Http2Connection d;
    public boolean f;
    public final b g;
    public final a h;

    @Nullable
    public ErrorCode k;

    @Nullable
    public IOException l;
    public long a = 0;
    public final Deque<Headers> e = new ArrayDeque();
    public final c i = new c();
    public final c j = new c();

    public final class a implements Sink {
        public final Buffer a = new Buffer();
        public Headers b;
        public boolean c;
        public boolean d;

        public a() {
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x0061  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void a(boolean r12) throws java.io.IOException {
            /*
                r11 = this;
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                monitor-enter(r0)
                okhttp3.internal.http2.Http2Stream r1 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L89
                okhttp3.internal.http2.Http2Stream$c r1 = r1.j     // Catch: java.lang.Throwable -> L89
                r1.enter()     // Catch: java.lang.Throwable -> L89
            La:
                okhttp3.internal.http2.Http2Stream r1 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L80
                long r1 = r1.b     // Catch: java.lang.Throwable -> L80
                r3 = 0
                int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r1 > 0) goto L28
                boolean r1 = r11.d     // Catch: java.lang.Throwable -> L80
                if (r1 != 0) goto L28
                boolean r1 = r11.c     // Catch: java.lang.Throwable -> L80
                if (r1 != 0) goto L28
                okhttp3.internal.http2.Http2Stream r1 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L80
                okhttp3.internal.http2.ErrorCode r1 = r1.k     // Catch: java.lang.Throwable -> L80
                if (r1 != 0) goto L28
                okhttp3.internal.http2.Http2Stream r1 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L80
                r1.c()     // Catch: java.lang.Throwable -> L80
                goto La
            L28:
                okhttp3.internal.http2.Http2Stream r1 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L89
                okhttp3.internal.http2.Http2Stream$c r1 = r1.j     // Catch: java.lang.Throwable -> L89
                r1.b()     // Catch: java.lang.Throwable -> L89
                okhttp3.internal.http2.Http2Stream r1 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L89
                r1.b()     // Catch: java.lang.Throwable -> L89
                okhttp3.internal.http2.Http2Stream r1 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L89
                long r1 = r1.b     // Catch: java.lang.Throwable -> L89
                okio.Buffer r3 = r11.a     // Catch: java.lang.Throwable -> L89
                long r3 = r3.size()     // Catch: java.lang.Throwable -> L89
                long r9 = java.lang.Math.min(r1, r3)     // Catch: java.lang.Throwable -> L89
                okhttp3.internal.http2.Http2Stream r1 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L89
                long r2 = r1.b     // Catch: java.lang.Throwable -> L89
                long r2 = r2 - r9
                r1.b = r2     // Catch: java.lang.Throwable -> L89
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L89
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Stream$c r0 = r0.j
                r0.enter()
                if (r12 == 0) goto L61
                okio.Buffer r12 = r11.a     // Catch: java.lang.Throwable -> L5f
                long r0 = r12.size()     // Catch: java.lang.Throwable -> L5f
                int r12 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
                if (r12 != 0) goto L61
                r12 = 1
                goto L62
            L5f:
                r12 = move-exception
                goto L78
            L61:
                r12 = 0
            L62:
                r7 = r12
                okhttp3.internal.http2.Http2Stream r12 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L5f
                okhttp3.internal.http2.Http2Connection r5 = r12.d     // Catch: java.lang.Throwable -> L5f
                okhttp3.internal.http2.Http2Stream r12 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L5f
                int r6 = r12.c     // Catch: java.lang.Throwable -> L5f
                okio.Buffer r8 = r11.a     // Catch: java.lang.Throwable -> L5f
                r5.writeData(r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L5f
                okhttp3.internal.http2.Http2Stream r12 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Stream$c r12 = r12.j
                r12.b()
                return
            L78:
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Stream$c r0 = r0.j
                r0.b()
                throw r12
            L80:
                r12 = move-exception
                okhttp3.internal.http2.Http2Stream r1 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> L89
                okhttp3.internal.http2.Http2Stream$c r1 = r1.j     // Catch: java.lang.Throwable -> L89
                r1.b()     // Catch: java.lang.Throwable -> L89
                throw r12     // Catch: java.lang.Throwable -> L89
            L89:
                r12 = move-exception
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L89
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.a.a(boolean):void");
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
        public void close() throws IOException {
            synchronized (Http2Stream.this) {
                if (this.c) {
                    return;
                }
                if (!Http2Stream.this.h.d) {
                    boolean z = this.a.size() > 0;
                    if (this.b != null) {
                        while (this.a.size() > 0) {
                            a(false);
                        }
                        Http2Stream http2Stream = Http2Stream.this;
                        http2Stream.d.v.a(true, http2Stream.c, Util.toHeaderBlock(this.b));
                    } else if (z) {
                        while (this.a.size() > 0) {
                            a(true);
                        }
                    } else {
                        Http2Stream http2Stream2 = Http2Stream.this;
                        http2Stream2.d.writeData(http2Stream2.c, true, null, 0L);
                    }
                }
                synchronized (Http2Stream.this) {
                    this.c = true;
                }
                Http2Stream.this.d.flush();
                Http2Stream.this.a();
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            synchronized (Http2Stream.this) {
                Http2Stream.this.b();
            }
            while (this.a.size() > 0) {
                a(false);
                Http2Stream.this.d.flush();
            }
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return Http2Stream.this.j;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            this.a.write(buffer, j);
            while (this.a.size() >= PlaybackStateCompat.ACTION_PREPARE) {
                a(false);
            }
        }
    }

    public final class b implements Source {
        public final Buffer a = new Buffer();
        public final Buffer b = new Buffer();
        public final long c;
        public Headers d;
        public boolean e;
        public boolean f;

        public b(long j) {
            this.c = j;
        }

        public final void a(long j) {
            Http2Stream.this.d.a(j);
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            long size;
            synchronized (Http2Stream.this) {
                this.e = true;
                size = this.b.size();
                this.b.clear();
                Http2Stream.this.notifyAll();
            }
            if (size > 0) {
                a(size);
            }
            Http2Stream.this.a();
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x009f  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00a3  */
        @Override // okio.Source
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public long read(okio.Buffer r12, long r13) throws java.lang.Throwable {
            /*
                r11 = this;
                r0 = 0
                int r2 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
                if (r2 < 0) goto Lbb
            L6:
                r2 = 0
                okhttp3.internal.http2.Http2Stream r3 = okhttp3.internal.http2.Http2Stream.this
                monitor-enter(r3)
                okhttp3.internal.http2.Http2Stream r4 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Lb8
                okhttp3.internal.http2.Http2Stream$c r4 = r4.i     // Catch: java.lang.Throwable -> Lb8
                r4.enter()     // Catch: java.lang.Throwable -> Lb8
                okhttp3.internal.http2.Http2Stream r4 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.ErrorCode r4 = r4.k     // Catch: java.lang.Throwable -> Laf
                if (r4 == 0) goto L2b
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                java.io.IOException r2 = r2.l     // Catch: java.lang.Throwable -> Laf
                if (r2 == 0) goto L22
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                java.io.IOException r2 = r2.l     // Catch: java.lang.Throwable -> Laf
                goto L2b
            L22:
                okhttp3.internal.http2.StreamResetException r2 = new okhttp3.internal.http2.StreamResetException     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Http2Stream r4 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.ErrorCode r4 = r4.k     // Catch: java.lang.Throwable -> Laf
                r2.<init>(r4)     // Catch: java.lang.Throwable -> Laf
            L2b:
                boolean r4 = r11.e     // Catch: java.lang.Throwable -> Laf
                if (r4 != 0) goto La7
                okio.Buffer r4 = r11.b     // Catch: java.lang.Throwable -> Laf
                long r4 = r4.size()     // Catch: java.lang.Throwable -> Laf
                int r4 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                r5 = -1
                if (r4 <= 0) goto L7d
                okio.Buffer r4 = r11.b     // Catch: java.lang.Throwable -> Laf
                okio.Buffer r7 = r11.b     // Catch: java.lang.Throwable -> Laf
                long r7 = r7.size()     // Catch: java.lang.Throwable -> Laf
                long r13 = java.lang.Math.min(r13, r7)     // Catch: java.lang.Throwable -> Laf
                long r12 = r4.read(r12, r13)     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Http2Stream r14 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                long r7 = r14.a     // Catch: java.lang.Throwable -> Laf
                long r7 = r7 + r12
                r14.a = r7     // Catch: java.lang.Throwable -> Laf
                if (r2 != 0) goto L93
                okhttp3.internal.http2.Http2Stream r14 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                long r7 = r14.a     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Http2Stream r14 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Http2Connection r14 = r14.d     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Settings r14 = r14.s     // Catch: java.lang.Throwable -> Laf
                int r14 = r14.a()     // Catch: java.lang.Throwable -> Laf
                int r14 = r14 / 2
                long r9 = (long) r14     // Catch: java.lang.Throwable -> Laf
                int r14 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r14 < 0) goto L93
                okhttp3.internal.http2.Http2Stream r14 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Http2Connection r14 = r14.d     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Http2Stream r4 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                int r4 = r4.c     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Http2Stream r7 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                long r7 = r7.a     // Catch: java.lang.Throwable -> Laf
                r14.a(r4, r7)     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Http2Stream r14 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                r14.a = r0     // Catch: java.lang.Throwable -> Laf
                goto L93
            L7d:
                boolean r4 = r11.f     // Catch: java.lang.Throwable -> Laf
                if (r4 != 0) goto L92
                if (r2 != 0) goto L92
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Laf
                r2.c()     // Catch: java.lang.Throwable -> Laf
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Lb8
                okhttp3.internal.http2.Http2Stream$c r2 = r2.i     // Catch: java.lang.Throwable -> Lb8
                r2.b()     // Catch: java.lang.Throwable -> Lb8
                monitor-exit(r3)     // Catch: java.lang.Throwable -> Lb8
                goto L6
            L92:
                r12 = r5
            L93:
                okhttp3.internal.http2.Http2Stream r14 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Lb8
                okhttp3.internal.http2.Http2Stream$c r14 = r14.i     // Catch: java.lang.Throwable -> Lb8
                r14.b()     // Catch: java.lang.Throwable -> Lb8
                monitor-exit(r3)     // Catch: java.lang.Throwable -> Lb8
                int r14 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
                if (r14 == 0) goto La3
                r11.a(r12)
                return r12
            La3:
                if (r2 != 0) goto La6
                return r5
            La6:
                throw r2
            La7:
                java.io.IOException r12 = new java.io.IOException     // Catch: java.lang.Throwable -> Laf
                java.lang.String r13 = "stream closed"
                r12.<init>(r13)     // Catch: java.lang.Throwable -> Laf
                throw r12     // Catch: java.lang.Throwable -> Laf
            Laf:
                r12 = move-exception
                okhttp3.internal.http2.Http2Stream r13 = okhttp3.internal.http2.Http2Stream.this     // Catch: java.lang.Throwable -> Lb8
                okhttp3.internal.http2.Http2Stream$c r13 = r13.i     // Catch: java.lang.Throwable -> Lb8
                r13.b()     // Catch: java.lang.Throwable -> Lb8
                throw r12     // Catch: java.lang.Throwable -> Lb8
            Lb8:
                r12 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> Lb8
                throw r12
            Lbb:
                java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
                java.lang.String r0 = "byteCount < 0: "
                java.lang.String r13 = defpackage.g9.a(r0, r13)
                r12.<init>(r13)
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.b.read(okio.Buffer, long):long");
        }

        @Override // okio.Source
        public Timeout timeout() {
            return Http2Stream.this.i;
        }

        public void a(BufferedSource bufferedSource, long j) throws IOException {
            boolean z;
            boolean z2;
            boolean z3;
            long size;
            while (j > 0) {
                synchronized (Http2Stream.this) {
                    z = this.f;
                    z2 = true;
                    z3 = this.b.size() + j > this.c;
                }
                if (z3) {
                    bufferedSource.skip(j);
                    Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                }
                if (z) {
                    bufferedSource.skip(j);
                    return;
                }
                long j2 = bufferedSource.read(this.a, j);
                if (j2 == -1) {
                    throw new EOFException();
                }
                j -= j2;
                synchronized (Http2Stream.this) {
                    if (this.e) {
                        size = this.a.size();
                        this.a.clear();
                    } else {
                        if (this.b.size() != 0) {
                            z2 = false;
                        }
                        this.b.writeAll(this.a);
                        if (z2) {
                            Http2Stream.this.notifyAll();
                        }
                        size = 0;
                    }
                }
                if (size > 0) {
                    a(size);
                }
            }
        }
    }

    public class c extends AsyncTimeout {
        public c() {
        }

        public void b() throws IOException {
            if (exit()) {
                throw newTimeoutException(null);
            }
        }

        @Override // okio.AsyncTimeout
        public IOException newTimeoutException(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        @Override // okio.AsyncTimeout
        public void timedOut() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
            Http2Stream.this.d.a();
        }
    }

    public Http2Stream(int i, Http2Connection http2Connection, boolean z, boolean z2, @Nullable Headers headers) {
        if (http2Connection == null) {
            throw new NullPointerException("connection == null");
        }
        this.c = i;
        this.d = http2Connection;
        this.b = http2Connection.t.a();
        this.g = new b(http2Connection.s.a());
        a aVar = new a();
        this.h = aVar;
        this.g.f = z2;
        aVar.d = z;
        if (headers != null) {
            this.e.add(headers);
        }
        if (isLocallyInitiated() && headers != null) {
            throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
        }
        if (!isLocallyInitiated() && headers == null) {
            throw new IllegalStateException("remotely-initiated streams should have headers");
        }
    }

    public final boolean a(ErrorCode errorCode, @Nullable IOException iOException) {
        synchronized (this) {
            if (this.k != null) {
                return false;
            }
            if (this.g.f && this.h.d) {
                return false;
            }
            this.k = errorCode;
            this.l = iOException;
            notifyAll();
            this.d.c(this.c);
            return true;
        }
    }

    public void b() throws IOException {
        a aVar = this.h;
        if (aVar.c) {
            throw new IOException("stream closed");
        }
        if (aVar.d) {
            throw new IOException("stream finished");
        }
        if (this.k != null) {
            IOException iOException = this.l;
            if (iOException == null) {
                throw new StreamResetException(this.k);
            }
        }
    }

    public void c() throws InterruptedException, InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    public void close(ErrorCode errorCode, @Nullable IOException iOException) throws IOException {
        if (a(errorCode, iOException)) {
            Http2Connection http2Connection = this.d;
            http2Connection.v.a(this.c, errorCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (a(errorCode, (IOException) null)) {
            this.d.a(this.c, errorCode);
        }
    }

    public void enqueueTrailers(Headers headers) {
        synchronized (this) {
            if (this.h.d) {
                throw new IllegalStateException("already finished");
            }
            if (headers.size() == 0) {
                throw new IllegalArgumentException("trailers.size() == 0");
            }
            this.h.b = headers;
        }
    }

    public Http2Connection getConnection() {
        return this.d;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.k;
    }

    public int getId() {
        return this.c;
    }

    public Sink getSink() {
        synchronized (this) {
            if (!this.f && !isLocallyInitiated()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.h;
    }

    public Source getSource() {
        return this.g;
    }

    public boolean isLocallyInitiated() {
        return this.d.a == ((this.c & 1) == 1);
    }

    public synchronized boolean isOpen() {
        if (this.k != null) {
            return false;
        }
        if ((this.g.f || this.g.e) && (this.h.d || this.h.c)) {
            if (this.f) {
                return false;
            }
        }
        return true;
    }

    public Timeout readTimeout() {
        return this.i;
    }

    public synchronized Headers takeHeaders() throws IOException {
        this.i.enter();
        while (this.e.isEmpty() && this.k == null) {
            try {
                c();
            } catch (Throwable th) {
                this.i.b();
                throw th;
            }
        }
        this.i.b();
        if (this.e.isEmpty()) {
            if (this.l != null) {
                throw this.l;
            }
            throw new StreamResetException(this.k);
        }
        return this.e.removeFirst();
    }

    public synchronized Headers trailers() throws IOException {
        if (this.k != null) {
            if (this.l != null) {
                throw this.l;
            }
            throw new StreamResetException(this.k);
        }
        if (!this.g.f || !this.g.a.exhausted() || !this.g.b.exhausted()) {
            throw new IllegalStateException("too early; can't read the trailers yet");
        }
        return this.g.d != null ? this.g.d : Util.EMPTY_HEADERS;
    }

    public void writeHeaders(List<Header> list, boolean z, boolean z2) throws IOException {
        if (list == null) {
            throw new NullPointerException("headers == null");
        }
        synchronized (this) {
            this.f = true;
            if (z) {
                this.h.d = true;
            }
        }
        if (!z2) {
            synchronized (this.d) {
                z2 = this.d.r == 0;
            }
        }
        this.d.v.a(z, this.c, list);
        if (z2) {
            this.d.flush();
        }
    }

    public Timeout writeTimeout() {
        return this.j;
    }

    public void a(Headers headers, boolean z) {
        boolean zIsOpen;
        synchronized (this) {
            if (this.f && z) {
                this.g.d = headers;
            } else {
                this.f = true;
                this.e.add(headers);
            }
            if (z) {
                this.g.f = true;
            }
            zIsOpen = isOpen();
            notifyAll();
        }
        if (zIsOpen) {
            return;
        }
        this.d.c(this.c);
    }

    public synchronized void a(ErrorCode errorCode) {
        if (this.k == null) {
            this.k = errorCode;
            notifyAll();
        }
    }

    public void a() throws IOException {
        boolean z;
        boolean zIsOpen;
        synchronized (this) {
            z = !this.g.f && this.g.e && (this.h.d || this.h.c);
            zIsOpen = isOpen();
        }
        if (z) {
            close(ErrorCode.CANCEL, null);
        } else {
            if (zIsOpen) {
                return;
            }
            this.d.c(this.c);
        }
    }
}
