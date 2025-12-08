package okhttp3.internal.connection;

import defpackage.g9;
import defpackage.in;
import defpackage.jn;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketException;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.ws.RealWebSocket;
import okio.Buffer;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/* loaded from: classes.dex */
public final class Exchange {
    public final Transmitter a;
    public final Call b;
    public final EventListener c;
    public final in d;
    public final ExchangeCodec e;
    public boolean f;

    public final class a extends ForwardingSink {
        public boolean b;
        public long c;
        public long d;
        public boolean e;

        public a(Sink sink, long j) {
            super(sink);
            this.c = j;
        }

        @Nullable
        public final IOException a(@Nullable IOException iOException) {
            if (this.b) {
                return iOException;
            }
            this.b = true;
            return Exchange.this.a(this.d, false, true, iOException);
        }

        @Override // okio.ForwardingSink, okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
        public void close() throws IOException {
            if (this.e) {
                return;
            }
            this.e = true;
            long j = this.c;
            if (j != -1 && this.d != j) {
                throw new ProtocolException("unexpected end of stream");
            }
            try {
                super.close();
                a(null);
            } catch (IOException e) {
                throw a(e);
            }
        }

        @Override // okio.ForwardingSink, okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            try {
                super.flush();
            } catch (IOException e) {
                throw a(e);
            }
        }

        @Override // okio.ForwardingSink, okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            if (this.e) {
                throw new IllegalStateException("closed");
            }
            long j2 = this.c;
            if (j2 == -1 || this.d + j <= j2) {
                try {
                    super.write(buffer, j);
                    this.d += j;
                    return;
                } catch (IOException e) {
                    throw a(e);
                }
            }
            StringBuilder sbA = g9.a("expected ");
            sbA.append(this.c);
            sbA.append(" bytes but received ");
            sbA.append(this.d + j);
            throw new ProtocolException(sbA.toString());
        }
    }

    public final class b extends ForwardingSource {
        public final long a;
        public long b;
        public boolean c;
        public boolean d;

        public b(Source source, long j) {
            super(source);
            this.a = j;
            if (j == 0) {
                a(null);
            }
        }

        @Nullable
        public IOException a(@Nullable IOException iOException) {
            if (this.c) {
                return iOException;
            }
            this.c = true;
            return Exchange.this.a(this.b, true, false, iOException);
        }

        @Override // okio.ForwardingSource, okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.d) {
                return;
            }
            this.d = true;
            try {
                super.close();
                a(null);
            } catch (IOException e) {
                throw a(e);
            }
        }

        @Override // okio.ForwardingSource, okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            if (this.d) {
                throw new IllegalStateException("closed");
            }
            try {
                long j2 = delegate().read(buffer, j);
                if (j2 == -1) {
                    a(null);
                    return -1L;
                }
                long j3 = this.b + j2;
                if (this.a != -1 && j3 > this.a) {
                    throw new ProtocolException("expected " + this.a + " bytes but received " + j3);
                }
                this.b = j3;
                if (j3 == this.a) {
                    a(null);
                }
                return j2;
            } catch (IOException e) {
                throw a(e);
            }
        }
    }

    public Exchange(Transmitter transmitter, Call call, EventListener eventListener, in inVar, ExchangeCodec exchangeCodec) {
        this.a = transmitter;
        this.b = call;
        this.c = eventListener;
        this.d = inVar;
        this.e = exchangeCodec;
    }

    @Nullable
    public IOException a(long j, boolean z, boolean z2, @Nullable IOException iOException) {
        if (iOException != null) {
            this.d.d();
            this.e.connection().a(iOException);
        }
        if (z2) {
            if (iOException != null) {
                this.c.requestFailed(this.b, iOException);
            } else {
                this.c.requestBodyEnd(this.b, j);
            }
        }
        if (z) {
            if (iOException != null) {
                this.c.responseFailed(this.b, iOException);
            } else {
                this.c.responseBodyEnd(this.b, j);
            }
        }
        return this.a.a(this, z2, z, iOException);
    }

    public void cancel() {
        this.e.cancel();
    }

    public RealConnection connection() {
        return this.e.connection();
    }

    public Sink createRequestBody(Request request, boolean z) throws IOException {
        this.f = z;
        long jContentLength = request.body().contentLength();
        this.c.requestBodyStart(this.b);
        return new a(this.e.createRequestBody(request, jContentLength), jContentLength);
    }

    public void detachWithViolence() {
        this.e.cancel();
        this.a.a(this, true, true, null);
    }

    public void finishRequest() throws IOException {
        try {
            this.e.finishRequest();
        } catch (IOException e) {
            this.c.requestFailed(this.b, e);
            this.d.d();
            this.e.connection().a(e);
            throw e;
        }
    }

    public void flushRequest() throws IOException {
        try {
            this.e.flushRequest();
        } catch (IOException e) {
            this.c.requestFailed(this.b, e);
            this.d.d();
            this.e.connection().a(e);
            throw e;
        }
    }

    public boolean isDuplex() {
        return this.f;
    }

    public RealWebSocket.Streams newWebSocketStreams() throws SocketException {
        this.a.timeoutEarlyExit();
        RealConnection realConnectionConnection = this.e.connection();
        realConnectionConnection.c.setSoTimeout(0);
        realConnectionConnection.noNewExchanges();
        return new jn(realConnectionConnection, true, realConnectionConnection.g, realConnectionConnection.h, this);
    }

    public void noNewExchangesOnConnection() {
        this.e.connection().noNewExchanges();
    }

    public void noRequestBody() {
        this.a.a(this, true, false, null);
    }

    public ResponseBody openResponseBody(Response response) throws IOException {
        try {
            this.c.responseBodyStart(this.b);
            String strHeader = response.header("Content-Type");
            long jReportedContentLength = this.e.reportedContentLength(response);
            return new RealResponseBody(strHeader, jReportedContentLength, Okio.buffer(new b(this.e.openResponseBodySource(response), jReportedContentLength)));
        } catch (IOException e) {
            this.c.responseFailed(this.b, e);
            this.d.d();
            this.e.connection().a(e);
            throw e;
        }
    }

    @Nullable
    public Response.Builder readResponseHeaders(boolean z) throws IOException {
        try {
            Response.Builder responseHeaders = this.e.readResponseHeaders(z);
            if (responseHeaders != null) {
                if (((OkHttpClient.a) Internal.instance) == null) {
                    throw null;
                }
                responseHeaders.m = this;
            }
            return responseHeaders;
        } catch (IOException e) {
            this.c.responseFailed(this.b, e);
            this.d.d();
            this.e.connection().a(e);
            throw e;
        }
    }

    public void responseHeadersEnd(Response response) {
        this.c.responseHeadersEnd(this.b, response);
    }

    public void responseHeadersStart() {
        this.c.responseHeadersStart(this.b);
    }

    public void timeoutEarlyExit() {
        this.a.timeoutEarlyExit();
    }

    public Headers trailers() throws IOException {
        return this.e.trailers();
    }

    public void webSocketUpgradeFailed() {
        a(-1L, true, true, null);
    }

    public void writeRequestHeaders(Request request) throws IOException {
        try {
            this.c.requestHeadersStart(this.b);
            this.e.writeRequestHeaders(request);
            this.c.requestHeadersEnd(this.b, request);
        } catch (IOException e) {
            this.c.requestFailed(this.b, e);
            this.d.d();
            this.e.connection().a(e);
            throw e;
        }
    }
}
