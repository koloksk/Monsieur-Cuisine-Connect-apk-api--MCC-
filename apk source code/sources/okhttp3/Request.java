package okhttp3;

import defpackage.g9;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;

/* loaded from: classes.dex */
public final class Request {
    public final HttpUrl a;
    public final String b;
    public final Headers c;

    @Nullable
    public final RequestBody d;
    public final Map<Class<?>, Object> e;

    @Nullable
    public volatile CacheControl f;

    public static class Builder {

        @Nullable
        public HttpUrl a;
        public String b;
        public Headers.Builder c;

        @Nullable
        public RequestBody d;
        public Map<Class<?>, Object> e;

        public Builder() {
            this.e = Collections.emptyMap();
            this.b = "GET";
            this.c = new Headers.Builder();
        }

        public Builder addHeader(String str, String str2) {
            this.c.add(str, str2);
            return this;
        }

        public Request build() {
            if (this.a != null) {
                return new Request(this);
            }
            throw new IllegalStateException("url == null");
        }

        public Builder cacheControl(CacheControl cacheControl) {
            String string = cacheControl.toString();
            return string.isEmpty() ? removeHeader("Cache-Control") : header("Cache-Control", string);
        }

        public Builder delete(@Nullable RequestBody requestBody) {
            return method("DELETE", requestBody);
        }

        public Builder get() {
            return method("GET", null);
        }

        public Builder head() {
            return method("HEAD", null);
        }

        public Builder header(String str, String str2) {
            this.c.set(str, str2);
            return this;
        }

        public Builder headers(Headers headers) {
            this.c = headers.newBuilder();
            return this;
        }

        public Builder method(String str, @Nullable RequestBody requestBody) {
            if (str == null) {
                throw new NullPointerException("method == null");
            }
            if (str.length() == 0) {
                throw new IllegalArgumentException("method.length() == 0");
            }
            if (requestBody != null && !HttpMethod.permitsRequestBody(str)) {
                throw new IllegalArgumentException(g9.a("method ", str, " must not have a request body."));
            }
            if (requestBody == null && HttpMethod.requiresRequestBody(str)) {
                throw new IllegalArgumentException(g9.a("method ", str, " must have a request body."));
            }
            this.b = str;
            this.d = requestBody;
            return this;
        }

        public Builder patch(RequestBody requestBody) {
            return method("PATCH", requestBody);
        }

        public Builder post(RequestBody requestBody) {
            return method("POST", requestBody);
        }

        public Builder put(RequestBody requestBody) {
            return method("PUT", requestBody);
        }

        public Builder removeHeader(String str) {
            this.c.removeAll(str);
            return this;
        }

        public Builder tag(@Nullable Object obj) {
            return tag(Object.class, obj);
        }

        public Builder url(HttpUrl httpUrl) {
            if (httpUrl == null) {
                throw new NullPointerException("url == null");
            }
            this.a = httpUrl;
            return this;
        }

        public Builder delete() {
            return delete(Util.EMPTY_REQUEST);
        }

        public <T> Builder tag(Class<? super T> cls, @Nullable T t) {
            if (cls == null) {
                throw new NullPointerException("type == null");
            }
            if (t == null) {
                this.e.remove(cls);
            } else {
                if (this.e.isEmpty()) {
                    this.e = new LinkedHashMap();
                }
                this.e.put(cls, cls.cast(t));
            }
            return this;
        }

        public Builder url(String str) {
            if (str != null) {
                if (str.regionMatches(true, 0, "ws:", 0, 3)) {
                    StringBuilder sbA = g9.a("http:");
                    sbA.append(str.substring(3));
                    str = sbA.toString();
                } else if (str.regionMatches(true, 0, "wss:", 0, 4)) {
                    StringBuilder sbA2 = g9.a("https:");
                    sbA2.append(str.substring(4));
                    str = sbA2.toString();
                }
                return url(HttpUrl.get(str));
            }
            throw new NullPointerException("url == null");
        }

        public Builder(Request request) {
            Map<Class<?>, Object> linkedHashMap;
            this.e = Collections.emptyMap();
            this.a = request.a;
            this.b = request.b;
            this.d = request.d;
            if (request.e.isEmpty()) {
                linkedHashMap = Collections.emptyMap();
            } else {
                linkedHashMap = new LinkedHashMap<>(request.e);
            }
            this.e = linkedHashMap;
            this.c = request.c.newBuilder();
        }

        public Builder url(URL url) {
            if (url != null) {
                return url(HttpUrl.get(url.toString()));
            }
            throw new NullPointerException("url == null");
        }
    }

    public Request(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c.build();
        this.d = builder.d;
        this.e = Util.immutableMap(builder.e);
    }

    @Nullable
    public RequestBody body() {
        return this.d;
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.f;
        if (cacheControl != null) {
            return cacheControl;
        }
        CacheControl cacheControl2 = CacheControl.parse(this.c);
        this.f = cacheControl2;
        return cacheControl2;
    }

    @Nullable
    public String header(String str) {
        return this.c.get(str);
    }

    public Headers headers() {
        return this.c;
    }

    public boolean isHttps() {
        return this.a.isHttps();
    }

    public String method() {
        return this.b;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Nullable
    public Object tag() {
        return tag(Object.class);
    }

    public String toString() {
        StringBuilder sbA = g9.a("Request{method=");
        sbA.append(this.b);
        sbA.append(", url=");
        sbA.append(this.a);
        sbA.append(", tags=");
        sbA.append(this.e);
        sbA.append('}');
        return sbA.toString();
    }

    public HttpUrl url() {
        return this.a;
    }

    public List<String> headers(String str) {
        return this.c.values(str);
    }

    @Nullable
    public <T> T tag(Class<? extends T> cls) {
        return cls.cast(this.e.get(cls));
    }
}
