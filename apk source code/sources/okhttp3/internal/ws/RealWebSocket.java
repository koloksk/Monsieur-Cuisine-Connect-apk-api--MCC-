package okhttp3.internal.ws;

import defpackage.an;
import defpackage.g9;
import defpackage.yn;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.ws.WebSocketReader;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class RealWebSocket implements WebSocket, WebSocketReader.FrameCallback {
    public static final List<Protocol> x = Collections.singletonList(Protocol.HTTP_1_1);
    public final Request a;
    public final WebSocketListener b;
    public final Random c;
    public final long d;
    public final String e;
    public Call f;
    public final Runnable g;
    public WebSocketReader h;
    public yn i;
    public ScheduledExecutorService j;
    public Streams k;
    public long n;
    public boolean o;
    public ScheduledFuture<?> p;
    public String r;
    public boolean s;
    public int t;
    public int u;
    public int v;
    public boolean w;
    public final ArrayDeque<ByteString> l = new ArrayDeque<>();
    public final ArrayDeque<Object> m = new ArrayDeque<>();
    public int q = -1;

    public static abstract class Streams implements Closeable {
        public final boolean client;
        public final BufferedSink sink;
        public final BufferedSource source;

        public Streams(boolean z, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.client = z;
            this.source = bufferedSource;
            this.sink = bufferedSink;
        }
    }

    public class a implements Callback {
        public final /* synthetic */ Request a;

        public a(Request request) {
            this.a = request;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            RealWebSocket.this.failWebSocket(iOException, null);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) {
            if (((OkHttpClient.a) Internal.instance) == null) {
                throw null;
            }
            Exchange exchange = response.m;
            try {
                RealWebSocket.this.a(response, exchange);
                try {
                    RealWebSocket.this.initReaderAndWriter("OkHttp WebSocket " + this.a.url().redact(), exchange.newWebSocketStreams());
                    RealWebSocket.this.b.onOpen(RealWebSocket.this, response);
                    RealWebSocket.this.loopReader();
                } catch (Exception e) {
                    RealWebSocket.this.failWebSocket(e, null);
                }
            } catch (IOException e2) {
                if (exchange != null) {
                    exchange.webSocketUpgradeFailed();
                }
                RealWebSocket.this.failWebSocket(e2, response);
                Util.closeQuietly(response);
            }
        }
    }

    public final class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            RealWebSocket.this.cancel();
        }
    }

    public static final class c {
        public final int a;
        public final ByteString b;
        public final long c;

        public c(int i, ByteString byteString, long j) {
            this.a = i;
            this.b = byteString;
            this.c = j;
        }
    }

    public static final class d {
        public final int a;
        public final ByteString b;

        public d(int i, ByteString byteString) {
            this.a = i;
            this.b = byteString;
        }
    }

    public final class e implements Runnable {
        public e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            RealWebSocket.this.d();
        }
    }

    public RealWebSocket(Request request, WebSocketListener webSocketListener, Random random, long j) {
        if (!"GET".equals(request.method())) {
            StringBuilder sbA = g9.a("Request must be GET: ");
            sbA.append(request.method());
            throw new IllegalArgumentException(sbA.toString());
        }
        this.a = request;
        this.b = webSocketListener;
        this.c = random;
        this.d = j;
        byte[] bArr = new byte[16];
        random.nextBytes(bArr);
        this.e = ByteString.of(bArr).base64();
        this.g = new Runnable() { // from class: xn
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a();
            }
        };
    }

    public synchronized boolean a(int i, String str, long j) {
        String strA = WebSocketProtocol.a(i);
        if (strA != null) {
            throw new IllegalArgumentException(strA);
        }
        ByteString byteStringEncodeUtf8 = null;
        if (str != null) {
            byteStringEncodeUtf8 = ByteString.encodeUtf8(str);
            if (byteStringEncodeUtf8.size() > 123) {
                throw new IllegalArgumentException("reason.size() > 123: " + str);
            }
        }
        if (!this.s && !this.o) {
            this.o = true;
            this.m.add(new c(i, byteStringEncodeUtf8, j));
            b();
            return true;
        }
        return false;
    }

    public final void b() {
        ScheduledExecutorService scheduledExecutorService = this.j;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.execute(this.g);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v9 */
    public boolean c() throws IOException {
        Streams streams;
        String str;
        synchronized (this) {
            if (this.s) {
                return false;
            }
            yn ynVar = this.i;
            ByteString byteStringPoll = this.l.poll();
            int i = -1;
            d dVar = 0;
            if (byteStringPoll == null) {
                Object objPoll = this.m.poll();
                if (objPoll instanceof c) {
                    int i2 = this.q;
                    str = this.r;
                    if (i2 != -1) {
                        Streams streams2 = this.k;
                        this.k = null;
                        this.j.shutdown();
                        dVar = objPoll;
                        streams = streams2;
                        i = i2;
                    } else {
                        this.p = this.j.schedule(new b(), ((c) objPoll).c, TimeUnit.MILLISECONDS);
                        i = i2;
                        streams = null;
                        dVar = objPoll;
                    }
                } else {
                    if (objPoll == null) {
                        return false;
                    }
                    str = null;
                    dVar = objPoll;
                    streams = null;
                }
            } else {
                streams = null;
                str = null;
            }
            try {
                if (byteStringPoll != null) {
                    ynVar.b(10, byteStringPoll);
                } else if (dVar instanceof d) {
                    ByteString byteString = dVar.b;
                    int i3 = dVar.a;
                    long size = byteString.size();
                    if (ynVar.h) {
                        throw new IllegalStateException("Another message writer is active. Did you call close()?");
                    }
                    ynVar.h = true;
                    yn.a aVar = ynVar.g;
                    aVar.a = i3;
                    aVar.b = size;
                    aVar.c = true;
                    aVar.d = false;
                    BufferedSink bufferedSinkBuffer = Okio.buffer(aVar);
                    bufferedSinkBuffer.write(byteString);
                    bufferedSinkBuffer.close();
                    synchronized (this) {
                        this.n -= byteString.size();
                    }
                } else {
                    if (!(dVar instanceof c)) {
                        throw new AssertionError();
                    }
                    c cVar = (c) dVar;
                    ynVar.a(cVar.a, cVar.b);
                    if (streams != null) {
                        this.b.onClosed(this, i, str);
                    }
                }
                return true;
            } finally {
                Util.closeQuietly(streams);
            }
        }
    }

    @Override // okhttp3.WebSocket
    public void cancel() {
        this.f.cancel();
    }

    @Override // okhttp3.WebSocket
    public boolean close(int i, String str) {
        return a(i, str, 60000L);
    }

    public void connect(OkHttpClient okHttpClient) {
        OkHttpClient okHttpClientBuild = okHttpClient.newBuilder().eventListener(EventListener.NONE).protocols(x).build();
        Request requestBuild = this.a.newBuilder().header("Upgrade", "websocket").header("Connection", "Upgrade").header("Sec-WebSocket-Key", this.e).header("Sec-WebSocket-Version", "13").build();
        if (((OkHttpClient.a) Internal.instance) == null) {
            throw null;
        }
        an anVarA = an.a(okHttpClientBuild, requestBuild, true);
        this.f = anVarA;
        anVarA.enqueue(new a(requestBuild));
    }

    public void d() {
        synchronized (this) {
            if (this.s) {
                return;
            }
            yn ynVar = this.i;
            int i = this.w ? this.t : -1;
            this.t++;
            this.w = true;
            if (i == -1) {
                try {
                    ynVar.b(9, ByteString.EMPTY);
                    return;
                } catch (IOException e2) {
                    failWebSocket(e2, null);
                    return;
                }
            }
            StringBuilder sbA = g9.a("sent ping but didn't receive pong within ");
            sbA.append(this.d);
            sbA.append("ms (after ");
            sbA.append(i - 1);
            sbA.append(" successful ping/pongs)");
            failWebSocket(new SocketTimeoutException(sbA.toString()), null);
        }
    }

    public void failWebSocket(Exception exc, @Nullable Response response) {
        synchronized (this) {
            if (this.s) {
                return;
            }
            this.s = true;
            Streams streams = this.k;
            this.k = null;
            if (this.p != null) {
                this.p.cancel(false);
            }
            if (this.j != null) {
                this.j.shutdown();
            }
            try {
                this.b.onFailure(this, exc, response);
            } finally {
                Util.closeQuietly(streams);
            }
        }
    }

    public void initReaderAndWriter(String str, Streams streams) throws IOException {
        synchronized (this) {
            this.k = streams;
            this.i = new yn(streams.client, streams.sink, this.c);
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, Util.threadFactory(str, false));
            this.j = scheduledThreadPoolExecutor;
            if (this.d != 0) {
                scheduledThreadPoolExecutor.scheduleAtFixedRate(new e(), this.d, this.d, TimeUnit.MILLISECONDS);
            }
            if (!this.m.isEmpty()) {
                b();
            }
        }
        this.h = new WebSocketReader(streams.client, streams.source, this);
    }

    public void loopReader() throws IOException {
        while (this.q == -1) {
            WebSocketReader webSocketReader = this.h;
            webSocketReader.b();
            if (!webSocketReader.h) {
                int i = webSocketReader.e;
                if (i != 1 && i != 2) {
                    StringBuilder sbA = g9.a("Unknown opcode: ");
                    sbA.append(Integer.toHexString(i));
                    throw new ProtocolException(sbA.toString());
                }
                while (!webSocketReader.d) {
                    long j = webSocketReader.f;
                    if (j > 0) {
                        webSocketReader.b.readFully(webSocketReader.j, j);
                        if (!webSocketReader.a) {
                            webSocketReader.j.readAndWriteUnsafe(webSocketReader.l);
                            webSocketReader.l.seek(webSocketReader.j.size() - webSocketReader.f);
                            WebSocketProtocol.a(webSocketReader.l, webSocketReader.k);
                            webSocketReader.l.close();
                        }
                    }
                    if (!webSocketReader.g) {
                        while (!webSocketReader.d) {
                            webSocketReader.b();
                            if (!webSocketReader.h) {
                                break;
                            } else {
                                webSocketReader.a();
                            }
                        }
                        if (webSocketReader.e != 0) {
                            StringBuilder sbA2 = g9.a("Expected continuation opcode. Got: ");
                            sbA2.append(Integer.toHexString(webSocketReader.e));
                            throw new ProtocolException(sbA2.toString());
                        }
                    } else if (i == 1) {
                        webSocketReader.c.onReadMessage(webSocketReader.j.readUtf8());
                    } else {
                        webSocketReader.c.onReadMessage(webSocketReader.j.readByteString());
                    }
                }
                throw new IOException("closed");
            }
            webSocketReader.a();
        }
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadClose(int i, String str) {
        Streams streams;
        if (i == -1) {
            throw new IllegalArgumentException();
        }
        synchronized (this) {
            if (this.q != -1) {
                throw new IllegalStateException("already closed");
            }
            this.q = i;
            this.r = str;
            streams = null;
            if (this.o && this.m.isEmpty()) {
                Streams streams2 = this.k;
                this.k = null;
                if (this.p != null) {
                    this.p.cancel(false);
                }
                this.j.shutdown();
                streams = streams2;
            }
        }
        try {
            this.b.onClosing(this, i, str);
            if (streams != null) {
                this.b.onClosed(this, i, str);
            }
        } finally {
            Util.closeQuietly(streams);
        }
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(String str) throws IOException {
        this.b.onMessage(this, str);
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPing(ByteString byteString) {
        if (!this.s && (!this.o || !this.m.isEmpty())) {
            this.l.add(byteString);
            b();
            this.u++;
        }
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPong(ByteString byteString) {
        this.v++;
        this.w = false;
    }

    @Override // okhttp3.WebSocket
    public synchronized long queueSize() {
        return this.n;
    }

    @Override // okhttp3.WebSocket
    public Request request() {
        return this.a;
    }

    @Override // okhttp3.WebSocket
    public boolean send(String str) {
        if (str != null) {
            return a(ByteString.encodeUtf8(str), 1);
        }
        throw new NullPointerException("text == null");
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(ByteString byteString) throws IOException {
        this.b.onMessage(this, byteString);
    }

    @Override // okhttp3.WebSocket
    public boolean send(ByteString byteString) {
        if (byteString != null) {
            return a(byteString, 2);
        }
        throw new NullPointerException("bytes == null");
    }

    public /* synthetic */ void a() {
        do {
            try {
            } catch (IOException e2) {
                failWebSocket(e2, null);
                return;
            }
        } while (c());
    }

    public void a(Response response, @Nullable Exchange exchange) throws IOException {
        if (response.code() == 101) {
            String strHeader = response.header("Connection");
            if ("Upgrade".equalsIgnoreCase(strHeader)) {
                String strHeader2 = response.header("Upgrade");
                if ("websocket".equalsIgnoreCase(strHeader2)) {
                    String strHeader3 = response.header("Sec-WebSocket-Accept");
                    String strBase64 = ByteString.encodeUtf8(this.e + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").sha1().base64();
                    if (strBase64.equals(strHeader3)) {
                        if (exchange == null) {
                            throw new ProtocolException("Web Socket exchange missing: bad interceptor?");
                        }
                        return;
                    }
                    throw new ProtocolException("Expected 'Sec-WebSocket-Accept' header value '" + strBase64 + "' but was '" + strHeader3 + "'");
                }
                throw new ProtocolException(g9.a("Expected 'Upgrade' header value 'websocket' but was '", strHeader2, "'"));
            }
            throw new ProtocolException(g9.a("Expected 'Connection' header value 'Upgrade' but was '", strHeader, "'"));
        }
        StringBuilder sbA = g9.a("Expected HTTP 101 response but was '");
        sbA.append(response.code());
        sbA.append(StringUtils.SPACE);
        sbA.append(response.message());
        sbA.append("'");
        throw new ProtocolException(sbA.toString());
    }

    public final synchronized boolean a(ByteString byteString, int i) {
        if (!this.s && !this.o) {
            if (this.n + byteString.size() > 16777216) {
                close(1001, null);
                return false;
            }
            this.n += byteString.size();
            this.m.add(new d(i, byteString));
            b();
            return true;
        }
        return false;
    }
}
