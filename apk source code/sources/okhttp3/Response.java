package okhttp3;

import defpackage.g9;
import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import sound.SoundLength;

/* loaded from: classes.dex */
public final class Response implements Closeable {
    public final Request a;
    public final Protocol b;
    public final int c;
    public final String d;

    @Nullable
    public final Handshake e;
    public final Headers f;

    @Nullable
    public final ResponseBody g;

    @Nullable
    public final Response h;

    @Nullable
    public final Response i;

    @Nullable
    public final Response j;
    public final long k;
    public final long l;

    @Nullable
    public final Exchange m;

    @Nullable
    public volatile CacheControl n;

    public Response(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f.build();
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
        this.k = builder.k;
        this.l = builder.l;
        this.m = builder.m;
    }

    @Nullable
    public ResponseBody body() {
        return this.g;
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.n;
        if (cacheControl != null) {
            return cacheControl;
        }
        CacheControl cacheControl2 = CacheControl.parse(this.f);
        this.n = cacheControl2;
        return cacheControl2;
    }

    @Nullable
    public Response cacheResponse() {
        return this.i;
    }

    public List<Challenge> challenges() {
        String str;
        int i = this.c;
        if (i == 401) {
            str = "WWW-Authenticate";
        } else {
            if (i != 407) {
                return Collections.emptyList();
            }
            str = "Proxy-Authenticate";
        }
        return HttpHeaders.parseChallenges(headers(), str);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        ResponseBody responseBody = this.g;
        if (responseBody == null) {
            throw new IllegalStateException("response is not eligible for a body and must not be closed");
        }
        responseBody.close();
    }

    public int code() {
        return this.c;
    }

    @Nullable
    public Handshake handshake() {
        return this.e;
    }

    @Nullable
    public String header(String str) {
        return header(str, null);
    }

    public List<String> headers(String str) {
        return this.f.values(str);
    }

    public boolean isRedirect() {
        int i = this.c;
        if (i == 307 || i == 308) {
            return true;
        }
        switch (i) {
            case SoundLength.SMALL_THRESHOLD /* 300 */:
            case 301:
            case 302:
            case 303:
                return true;
            default:
                return false;
        }
    }

    public boolean isSuccessful() {
        int i = this.c;
        return i >= 200 && i < 300;
    }

    public String message() {
        return this.d;
    }

    @Nullable
    public Response networkResponse() {
        return this.h;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public ResponseBody peekBody(long j) throws IOException {
        BufferedSource bufferedSourcePeek = this.g.source().peek();
        Buffer buffer = new Buffer();
        bufferedSourcePeek.request(j);
        buffer.write(bufferedSourcePeek, Math.min(j, bufferedSourcePeek.getBuffer().size()));
        return ResponseBody.create(this.g.contentType(), buffer.size(), buffer);
    }

    @Nullable
    public Response priorResponse() {
        return this.j;
    }

    public Protocol protocol() {
        return this.b;
    }

    public long receivedResponseAtMillis() {
        return this.l;
    }

    public Request request() {
        return this.a;
    }

    public long sentRequestAtMillis() {
        return this.k;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Response{protocol=");
        sbA.append(this.b);
        sbA.append(", code=");
        sbA.append(this.c);
        sbA.append(", message=");
        sbA.append(this.d);
        sbA.append(", url=");
        sbA.append(this.a.url());
        sbA.append('}');
        return sbA.toString();
    }

    public Headers trailers() throws IOException {
        Exchange exchange = this.m;
        if (exchange != null) {
            return exchange.trailers();
        }
        throw new IllegalStateException("trailers not available");
    }

    @Nullable
    public String header(String str, @Nullable String str2) {
        String str3 = this.f.get(str);
        return str3 != null ? str3 : str2;
    }

    public Headers headers() {
        return this.f;
    }

    public static class Builder {

        @Nullable
        public Request a;

        @Nullable
        public Protocol b;
        public int c;
        public String d;

        @Nullable
        public Handshake e;
        public Headers.Builder f;

        @Nullable
        public ResponseBody g;

        @Nullable
        public Response h;

        @Nullable
        public Response i;

        @Nullable
        public Response j;
        public long k;
        public long l;

        @Nullable
        public Exchange m;

        public Builder() {
            this.c = -1;
            this.f = new Headers.Builder();
        }

        public final void a(String str, Response response) {
            if (response.g != null) {
                throw new IllegalArgumentException(g9.b(str, ".body != null"));
            }
            if (response.h != null) {
                throw new IllegalArgumentException(g9.b(str, ".networkResponse != null"));
            }
            if (response.i != null) {
                throw new IllegalArgumentException(g9.b(str, ".cacheResponse != null"));
            }
            if (response.j != null) {
                throw new IllegalArgumentException(g9.b(str, ".priorResponse != null"));
            }
        }

        public Builder addHeader(String str, String str2) {
            this.f.add(str, str2);
            return this;
        }

        public Builder body(@Nullable ResponseBody responseBody) {
            this.g = responseBody;
            return this;
        }

        public Response build() {
            if (this.a == null) {
                throw new IllegalStateException("request == null");
            }
            if (this.b == null) {
                throw new IllegalStateException("protocol == null");
            }
            if (this.c >= 0) {
                if (this.d != null) {
                    return new Response(this);
                }
                throw new IllegalStateException("message == null");
            }
            StringBuilder sbA = g9.a("code < 0: ");
            sbA.append(this.c);
            throw new IllegalStateException(sbA.toString());
        }

        public Builder cacheResponse(@Nullable Response response) {
            if (response != null) {
                a("cacheResponse", response);
            }
            this.i = response;
            return this;
        }

        public Builder code(int i) {
            this.c = i;
            return this;
        }

        public Builder handshake(@Nullable Handshake handshake) {
            this.e = handshake;
            return this;
        }

        public Builder header(String str, String str2) {
            this.f.set(str, str2);
            return this;
        }

        public Builder headers(Headers headers) {
            this.f = headers.newBuilder();
            return this;
        }

        public Builder message(String str) {
            this.d = str;
            return this;
        }

        public Builder networkResponse(@Nullable Response response) {
            if (response != null) {
                a("networkResponse", response);
            }
            this.h = response;
            return this;
        }

        public Builder priorResponse(@Nullable Response response) {
            if (response != null && response.g != null) {
                throw new IllegalArgumentException("priorResponse.body != null");
            }
            this.j = response;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.b = protocol;
            return this;
        }

        public Builder receivedResponseAtMillis(long j) {
            this.l = j;
            return this;
        }

        public Builder removeHeader(String str) {
            this.f.removeAll(str);
            return this;
        }

        public Builder request(Request request) {
            this.a = request;
            return this;
        }

        public Builder sentRequestAtMillis(long j) {
            this.k = j;
            return this;
        }

        public Builder(Response response) {
            this.c = -1;
            this.a = response.a;
            this.b = response.b;
            this.c = response.c;
            this.d = response.d;
            this.e = response.e;
            this.f = response.f.newBuilder();
            this.g = response.g;
            this.h = response.h;
            this.i = response.i;
            this.j = response.j;
            this.k = response.k;
            this.l = response.l;
            this.m = response.m;
        }
    }
}
