package okhttp3.internal.http1;

import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.os.EnvironmentCompat;
import defpackage.g9;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* loaded from: classes.dex */
public final class Http1ExchangeCodec implements ExchangeCodec {
    public final OkHttpClient a;
    public final RealConnection b;
    public final BufferedSource c;
    public final BufferedSink d;
    public int e = 0;
    public long f = PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
    public Headers g;

    public abstract class b implements Source {
        public final ForwardingTimeout a;
        public boolean b;

        public /* synthetic */ b(a aVar) {
            this.a = new ForwardingTimeout(Http1ExchangeCodec.this.c.timeout());
        }

        public final void a() {
            Http1ExchangeCodec http1ExchangeCodec = Http1ExchangeCodec.this;
            int i = http1ExchangeCodec.e;
            if (i == 6) {
                return;
            }
            if (i == 5) {
                Http1ExchangeCodec.a(http1ExchangeCodec, this.a);
                Http1ExchangeCodec.this.e = 6;
            } else {
                StringBuilder sbA = g9.a("state: ");
                sbA.append(Http1ExchangeCodec.this.e);
                throw new IllegalStateException(sbA.toString());
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            try {
                return Http1ExchangeCodec.this.c.read(buffer, j);
            } catch (IOException e) {
                Http1ExchangeCodec.this.b.noNewExchanges();
                a();
                throw e;
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.a;
        }
    }

    public final class c implements Sink {
        public final ForwardingTimeout a;
        public boolean b;

        public c() {
            this.a = new ForwardingTimeout(Http1ExchangeCodec.this.d.timeout());
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
        public synchronized void close() throws IOException {
            if (this.b) {
                return;
            }
            this.b = true;
            Http1ExchangeCodec.this.d.writeUtf8("0\r\n\r\n");
            Http1ExchangeCodec.a(Http1ExchangeCodec.this, this.a);
            Http1ExchangeCodec.this.e = 3;
        }

        @Override // okio.Sink, java.io.Flushable
        public synchronized void flush() throws IOException {
            if (this.b) {
                return;
            }
            Http1ExchangeCodec.this.d.flush();
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.a;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            if (this.b) {
                throw new IllegalStateException("closed");
            }
            if (j == 0) {
                return;
            }
            Http1ExchangeCodec.this.d.writeHexadecimalUnsignedLong(j);
            Http1ExchangeCodec.this.d.writeUtf8("\r\n");
            Http1ExchangeCodec.this.d.write(buffer, j);
            Http1ExchangeCodec.this.d.writeUtf8("\r\n");
        }
    }

    public class d extends b {
        public final HttpUrl d;
        public long e;
        public boolean f;

        public d(HttpUrl httpUrl) {
            super(null);
            this.e = -1L;
            this.f = true;
            this.d = httpUrl;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.b) {
                return;
            }
            if (this.f && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                Http1ExchangeCodec.this.b.noNewExchanges();
                a();
            }
            this.b = true;
        }

        @Override // okhttp3.internal.http1.Http1ExchangeCodec.b, okio.Source
        public long read(Buffer buffer, long j) throws IOException, NumberFormatException {
            if (j < 0) {
                throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
            }
            if (this.b) {
                throw new IllegalStateException("closed");
            }
            if (!this.f) {
                return -1L;
            }
            long j2 = this.e;
            if (j2 == 0 || j2 == -1) {
                if (this.e != -1) {
                    Http1ExchangeCodec.this.c.readUtf8LineStrict();
                }
                try {
                    this.e = Http1ExchangeCodec.this.c.readHexadecimalUnsignedLong();
                    String strTrim = Http1ExchangeCodec.this.c.readUtf8LineStrict().trim();
                    if (this.e < 0 || !(strTrim.isEmpty() || strTrim.startsWith(";"))) {
                        throw new ProtocolException("expected chunk size and optional extensions but was \"" + this.e + strTrim + "\"");
                    }
                    if (this.e == 0) {
                        this.f = false;
                        Http1ExchangeCodec http1ExchangeCodec = Http1ExchangeCodec.this;
                        http1ExchangeCodec.g = http1ExchangeCodec.b();
                        HttpHeaders.receiveHeaders(Http1ExchangeCodec.this.a.cookieJar(), this.d, Http1ExchangeCodec.this.g);
                        a();
                    }
                    if (!this.f) {
                        return -1L;
                    }
                } catch (NumberFormatException e) {
                    throw new ProtocolException(e.getMessage());
                }
            }
            long j3 = super.read(buffer, Math.min(j, this.e));
            if (j3 != -1) {
                this.e -= j3;
                return j3;
            }
            Http1ExchangeCodec.this.b.noNewExchanges();
            ProtocolException protocolException = new ProtocolException("unexpected end of stream");
            a();
            throw protocolException;
        }
    }

    public class e extends b {
        public long d;

        public e(long j) {
            super(null);
            this.d = j;
            if (j == 0) {
                a();
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.b) {
                return;
            }
            if (this.d != 0 && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                Http1ExchangeCodec.this.b.noNewExchanges();
                a();
            }
            this.b = true;
        }

        @Override // okhttp3.internal.http1.Http1ExchangeCodec.b, okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            if (j < 0) {
                throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
            }
            if (this.b) {
                throw new IllegalStateException("closed");
            }
            long j2 = this.d;
            if (j2 == 0) {
                return -1L;
            }
            long j3 = super.read(buffer, Math.min(j2, j));
            if (j3 == -1) {
                Http1ExchangeCodec.this.b.noNewExchanges();
                ProtocolException protocolException = new ProtocolException("unexpected end of stream");
                a();
                throw protocolException;
            }
            long j4 = this.d - j3;
            this.d = j4;
            if (j4 == 0) {
                a();
            }
            return j3;
        }
    }

    public final class f implements Sink {
        public final ForwardingTimeout a;
        public boolean b;

        public /* synthetic */ f(a aVar) {
            this.a = new ForwardingTimeout(Http1ExchangeCodec.this.d.timeout());
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
        public void close() throws IOException {
            if (this.b) {
                return;
            }
            this.b = true;
            Http1ExchangeCodec.a(Http1ExchangeCodec.this, this.a);
            Http1ExchangeCodec.this.e = 3;
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            if (this.b) {
                return;
            }
            Http1ExchangeCodec.this.d.flush();
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.a;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            if (this.b) {
                throw new IllegalStateException("closed");
            }
            Util.checkOffsetAndCount(buffer.size(), 0L, j);
            Http1ExchangeCodec.this.d.write(buffer, j);
        }
    }

    public class g extends b {
        public boolean d;

        public /* synthetic */ g(Http1ExchangeCodec http1ExchangeCodec, a aVar) {
            super(null);
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.b) {
                return;
            }
            if (!this.d) {
                a();
            }
            this.b = true;
        }

        @Override // okhttp3.internal.http1.Http1ExchangeCodec.b, okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            if (j < 0) {
                throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
            }
            if (this.b) {
                throw new IllegalStateException("closed");
            }
            if (this.d) {
                return -1L;
            }
            long j2 = super.read(buffer, j);
            if (j2 != -1) {
                return j2;
            }
            this.d = true;
            a();
            return -1L;
        }
    }

    public Http1ExchangeCodec(OkHttpClient okHttpClient, RealConnection realConnection, BufferedSource bufferedSource, BufferedSink bufferedSink) {
        this.a = okHttpClient;
        this.b = realConnection;
        this.c = bufferedSource;
        this.d = bufferedSink;
    }

    public final String a() throws IOException {
        String utf8LineStrict = this.c.readUtf8LineStrict(this.f);
        this.f -= utf8LineStrict.length();
        return utf8LineStrict;
    }

    public final Headers b() throws IOException {
        Headers.Builder builder = new Headers.Builder();
        while (true) {
            String strA = a();
            if (strA.length() == 0) {
                return builder.build();
            }
            if (((OkHttpClient.a) Internal.instance) == null) {
                throw null;
            }
            builder.a(strA);
        }
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public void cancel() throws IOException {
        RealConnection realConnection = this.b;
        if (realConnection != null) {
            realConnection.cancel();
        }
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public RealConnection connection() {
        return this.b;
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public Sink createRequestBody(Request request, long j) throws IOException {
        if (request.body() != null && request.body().isDuplex()) {
            throw new ProtocolException("Duplex connections are not supported for HTTP/1");
        }
        if ("chunked".equalsIgnoreCase(request.header("Transfer-Encoding"))) {
            if (this.e == 1) {
                this.e = 2;
                return new c();
            }
            StringBuilder sbA = g9.a("state: ");
            sbA.append(this.e);
            throw new IllegalStateException(sbA.toString());
        }
        if (j == -1) {
            throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
        }
        if (this.e == 1) {
            this.e = 2;
            return new f(null);
        }
        StringBuilder sbA2 = g9.a("state: ");
        sbA2.append(this.e);
        throw new IllegalStateException(sbA2.toString());
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public void finishRequest() throws IOException {
        this.d.flush();
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public void flushRequest() throws IOException {
        this.d.flush();
    }

    public boolean isClosed() {
        return this.e == 6;
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public Source openResponseBodySource(Response response) {
        if (!HttpHeaders.hasBody(response)) {
            return a(0L);
        }
        if ("chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            HttpUrl httpUrlUrl = response.request().url();
            if (this.e == 4) {
                this.e = 5;
                return new d(httpUrlUrl);
            }
            StringBuilder sbA = g9.a("state: ");
            sbA.append(this.e);
            throw new IllegalStateException(sbA.toString());
        }
        long jContentLength = HttpHeaders.contentLength(response);
        if (jContentLength != -1) {
            return a(jContentLength);
        }
        if (this.e == 4) {
            this.e = 5;
            this.b.noNewExchanges();
            return new g(this, null);
        }
        StringBuilder sbA2 = g9.a("state: ");
        sbA2.append(this.e);
        throw new IllegalStateException(sbA2.toString());
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public Response.Builder readResponseHeaders(boolean z) throws NumberFormatException, IOException {
        int i = this.e;
        if (i != 1 && i != 3) {
            StringBuilder sbA = g9.a("state: ");
            sbA.append(this.e);
            throw new IllegalStateException(sbA.toString());
        }
        try {
            StatusLine statusLine = StatusLine.parse(a());
            Response.Builder builderHeaders = new Response.Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message).headers(b());
            if (z && statusLine.code == 100) {
                return null;
            }
            if (statusLine.code == 100) {
                this.e = 3;
                return builderHeaders;
            }
            this.e = 4;
            return builderHeaders;
        } catch (EOFException e2) {
            RealConnection realConnection = this.b;
            throw new IOException(g9.b("unexpected end of stream on ", realConnection != null ? realConnection.route().address().url().redact() : EnvironmentCompat.MEDIA_UNKNOWN), e2);
        }
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public long reportedContentLength(Response response) {
        if (!HttpHeaders.hasBody(response)) {
            return 0L;
        }
        if ("chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return -1L;
        }
        return HttpHeaders.contentLength(response);
    }

    public void skipConnectBody(Response response) throws IOException {
        long jContentLength = HttpHeaders.contentLength(response);
        if (jContentLength == -1) {
            return;
        }
        Source sourceA = a(jContentLength);
        Util.skipAll(sourceA, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        sourceA.close();
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public Headers trailers() {
        if (this.e != 6) {
            throw new IllegalStateException("too early; can't read the trailers yet");
        }
        Headers headers = this.g;
        return headers != null ? headers : Util.EMPTY_HEADERS;
    }

    public void writeRequest(Headers headers, String str) throws IOException {
        if (this.e != 0) {
            StringBuilder sbA = g9.a("state: ");
            sbA.append(this.e);
            throw new IllegalStateException(sbA.toString());
        }
        this.d.writeUtf8(str).writeUtf8("\r\n");
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            this.d.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8("\r\n");
        }
        this.d.writeUtf8("\r\n");
        this.e = 1;
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public void writeRequestHeaders(Request request) throws IOException {
        writeRequest(request.headers(), RequestLine.get(request, this.b.route().proxy().type()));
    }

    public final Source a(long j) {
        if (this.e == 4) {
            this.e = 5;
            return new e(j);
        }
        StringBuilder sbA = g9.a("state: ");
        sbA.append(this.e);
        throw new IllegalStateException(sbA.toString());
    }

    public static /* synthetic */ void a(Http1ExchangeCodec http1ExchangeCodec, ForwardingTimeout forwardingTimeout) {
        if (http1ExchangeCodec != null) {
            Timeout timeoutDelegate = forwardingTimeout.delegate();
            forwardingTimeout.setDelegate(Timeout.NONE);
            timeoutDelegate.clearDeadline();
            timeoutDelegate.clearTimeout();
            return;
        }
        throw null;
    }
}
