package okhttp3.internal.cache;

import defpackage.dn;
import java.io.IOException;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealResponseBody;
import okio.Okio;
import okio.Sink;

/* loaded from: classes.dex */
public final class CacheInterceptor implements Interceptor {

    @Nullable
    public final InternalCache a;

    public CacheInterceptor(@Nullable InternalCache internalCache) {
        this.a = internalCache;
    }

    public static Response a(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    public static boolean b(String str) {
        return ("Connection".equalsIgnoreCase(str) || "Keep-Alive".equalsIgnoreCase(str) || "Proxy-Authenticate".equalsIgnoreCase(str) || "Proxy-Authorization".equalsIgnoreCase(str) || "TE".equalsIgnoreCase(str) || "Trailers".equalsIgnoreCase(str) || "Transfer-Encoding".equalsIgnoreCase(str) || "Upgrade".equalsIgnoreCase(str)) ? false : true;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Sink sinkBody;
        InternalCache internalCache = this.a;
        Response response = internalCache != null ? internalCache.get(chain.request()) : null;
        CacheStrategy cacheStrategy = new CacheStrategy.Factory(System.currentTimeMillis(), chain.request(), response).get();
        Request request = cacheStrategy.networkRequest;
        Response response2 = cacheStrategy.cacheResponse;
        InternalCache internalCache2 = this.a;
        if (internalCache2 != null) {
            internalCache2.trackResponse(cacheStrategy);
        }
        if (response != null && response2 == null) {
            Util.closeQuietly(response.body());
        }
        if (request == null && response2 == null) {
            return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis()).build();
        }
        if (request == null) {
            return response2.newBuilder().cacheResponse(a(response2)).build();
        }
        try {
            Response responseProceed = chain.proceed(request);
            if (responseProceed == null && response != null) {
            }
            if (response2 != null) {
                if (responseProceed.code() == 304) {
                    Response.Builder builderNewBuilder = response2.newBuilder();
                    Headers headers = response2.headers();
                    Headers headers2 = responseProceed.headers();
                    Headers.Builder builder = new Headers.Builder();
                    int size = headers.size();
                    for (int i = 0; i < size; i++) {
                        String strName = headers.name(i);
                        String strValue = headers.value(i);
                        if ((!"Warning".equalsIgnoreCase(strName) || !strValue.startsWith("1")) && (a(strName) || !b(strName) || headers2.get(strName) == null)) {
                            Internal.instance.addLenient(builder, strName, strValue);
                        }
                    }
                    int size2 = headers2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        String strName2 = headers2.name(i2);
                        if (!a(strName2) && b(strName2)) {
                            Internal.instance.addLenient(builder, strName2, headers2.value(i2));
                        }
                    }
                    Response responseBuild = builderNewBuilder.headers(builder.build()).sentRequestAtMillis(responseProceed.sentRequestAtMillis()).receivedResponseAtMillis(responseProceed.receivedResponseAtMillis()).cacheResponse(a(response2)).networkResponse(a(responseProceed)).build();
                    responseProceed.body().close();
                    this.a.trackConditionalCacheHit();
                    this.a.update(response2, responseBuild);
                    return responseBuild;
                }
                Util.closeQuietly(response2.body());
            }
            Response responseBuild2 = responseProceed.newBuilder().cacheResponse(a(response2)).networkResponse(a(responseProceed)).build();
            if (this.a != null) {
                if (HttpHeaders.hasBody(responseBuild2) && CacheStrategy.isCacheable(responseBuild2, request)) {
                    CacheRequest cacheRequestPut = this.a.put(responseBuild2);
                    if (cacheRequestPut == null || (sinkBody = cacheRequestPut.body()) == null) {
                        return responseBuild2;
                    }
                    return responseBuild2.newBuilder().body(new RealResponseBody(responseBuild2.header("Content-Type"), responseBuild2.body().contentLength(), Okio.buffer(new dn(this, responseBuild2.body().source(), cacheRequestPut, Okio.buffer(sinkBody))))).build();
                }
                if (HttpMethod.invalidatesCache(request.method())) {
                    try {
                        this.a.remove(request);
                    } catch (IOException unused) {
                    }
                }
            }
            return responseBuild2;
        } finally {
            if (response != null) {
                Util.closeQuietly(response.body());
            }
        }
    }

    public static boolean a(String str) {
        return "Content-Length".equalsIgnoreCase(str) || "Content-Encoding".equalsIgnoreCase(str) || "Content-Type".equalsIgnoreCase(str);
    }
}
