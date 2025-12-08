package okhttp3.logging;

import defpackage.g9;
import java.io.EOFException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public final class HttpLoggingInterceptor implements Interceptor {
    public static final Charset c = Charset.forName(CharEncoding.UTF_8);
    public final Logger a;
    public volatile Level b;

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public interface Logger {
        public static final Logger DEFAULT = new a();

        public static class a implements Logger {
            @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
            public void log(String str) {
                Platform.get().log(4, str, null);
            }
        }

        void log(String str);
    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public static boolean a(Buffer buffer) {
        try {
            Buffer buffer2 = new Buffer();
            buffer.copyTo(buffer2, 0L, buffer.size() < 64 ? buffer.size() : 64L);
            for (int i = 0; i < 16; i++) {
                if (buffer2.exhausted()) {
                    return true;
                }
                int utf8CodePoint = buffer2.readUtf8CodePoint();
                if (Character.isISOControl(utf8CodePoint) && !Character.isWhitespace(utf8CodePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException unused) {
            return false;
        }
    }

    public Level getLevel() {
        return this.b;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws Exception {
        String str;
        String str2;
        Level level = this.b;
        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        boolean z = level == Level.BODY;
        boolean z2 = z || level == Level.HEADERS;
        RequestBody requestBodyBody = request.body();
        boolean z3 = requestBodyBody != null;
        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        StringBuilder sbA = g9.a("--> ");
        sbA.append(request.method());
        sbA.append(' ');
        sbA.append(request.url());
        sbA.append(' ');
        sbA.append(protocol);
        String string = sbA.toString();
        if (!z2 && z3) {
            StringBuilder sbA2 = g9.a(string, " (");
            sbA2.append(requestBodyBody.contentLength());
            sbA2.append("-byte body)");
            string = sbA2.toString();
        }
        this.a.log(string);
        String str3 = ": ";
        if (z2) {
            if (z3) {
                if (requestBodyBody.contentType() != null) {
                    Logger logger = this.a;
                    StringBuilder sbA3 = g9.a("Content-Type: ");
                    sbA3.append(requestBodyBody.contentType());
                    logger.log(sbA3.toString());
                }
                if (requestBodyBody.contentLength() != -1) {
                    Logger logger2 = this.a;
                    StringBuilder sbA4 = g9.a("Content-Length: ");
                    sbA4.append(requestBodyBody.contentLength());
                    logger2.log(sbA4.toString());
                }
            }
            Headers headers = request.headers();
            int size = headers.size();
            int i = 0;
            while (i < size) {
                String strName = headers.name(i);
                int i2 = size;
                if ("Content-Type".equalsIgnoreCase(strName) || "Content-Length".equalsIgnoreCase(strName)) {
                    str2 = str3;
                } else {
                    Logger logger3 = this.a;
                    StringBuilder sbA5 = g9.a(strName, str3);
                    str2 = str3;
                    sbA5.append(headers.value(i));
                    logger3.log(sbA5.toString());
                }
                i++;
                size = i2;
                str3 = str2;
            }
            str = str3;
            if (!z || !z3) {
                Logger logger4 = this.a;
                StringBuilder sbA6 = g9.a("--> END ");
                sbA6.append(request.method());
                logger4.log(sbA6.toString());
            } else if (a(request.headers())) {
                Logger logger5 = this.a;
                StringBuilder sbA7 = g9.a("--> END ");
                sbA7.append(request.method());
                sbA7.append(" (encoded body omitted)");
                logger5.log(sbA7.toString());
            } else {
                Buffer buffer = new Buffer();
                requestBodyBody.writeTo(buffer);
                Charset charset = c;
                MediaType mediaTypeContentType = requestBodyBody.contentType();
                if (mediaTypeContentType != null) {
                    charset = mediaTypeContentType.charset(c);
                }
                this.a.log("");
                if (a(buffer)) {
                    this.a.log(buffer.readString(charset));
                    Logger logger6 = this.a;
                    StringBuilder sbA8 = g9.a("--> END ");
                    sbA8.append(request.method());
                    sbA8.append(" (");
                    sbA8.append(requestBodyBody.contentLength());
                    sbA8.append("-byte body)");
                    logger6.log(sbA8.toString());
                } else {
                    Logger logger7 = this.a;
                    StringBuilder sbA9 = g9.a("--> END ");
                    sbA9.append(request.method());
                    sbA9.append(" (binary ");
                    sbA9.append(requestBodyBody.contentLength());
                    sbA9.append("-byte body omitted)");
                    logger7.log(sbA9.toString());
                }
            }
        } else {
            str = ": ";
        }
        long jNanoTime = System.nanoTime();
        try {
            Response responseProceed = chain.proceed(request);
            long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime);
            ResponseBody responseBodyBody = responseProceed.body();
            long jContentLength = responseBodyBody.contentLength();
            String str4 = jContentLength != -1 ? jContentLength + "-byte" : "unknown-length";
            Logger logger8 = this.a;
            StringBuilder sbA10 = g9.a("<-- ");
            sbA10.append(responseProceed.code());
            sbA10.append(' ');
            sbA10.append(responseProceed.message());
            sbA10.append(' ');
            sbA10.append(responseProceed.request().url());
            sbA10.append(" (");
            sbA10.append(millis);
            sbA10.append("ms");
            sbA10.append(!z2 ? g9.a(", ", str4, " body") : "");
            sbA10.append(')');
            logger8.log(sbA10.toString());
            if (z2) {
                Headers headers2 = responseProceed.headers();
                int size2 = headers2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    this.a.log(headers2.name(i3) + str + headers2.value(i3));
                }
                if (!z || !HttpHeaders.hasBody(responseProceed)) {
                    this.a.log("<-- END HTTP");
                } else if (a(responseProceed.headers())) {
                    this.a.log("<-- END HTTP (encoded body omitted)");
                } else {
                    BufferedSource bufferedSourceSource = responseBodyBody.source();
                    bufferedSourceSource.request(Long.MAX_VALUE);
                    Buffer buffer2 = bufferedSourceSource.buffer();
                    Charset charset2 = c;
                    MediaType mediaTypeContentType2 = responseBodyBody.contentType();
                    if (mediaTypeContentType2 != null) {
                        try {
                            charset2 = mediaTypeContentType2.charset(c);
                        } catch (UnsupportedCharsetException unused) {
                            this.a.log("");
                            this.a.log("Couldn't decode the response body; charset is likely malformed.");
                            this.a.log("<-- END HTTP");
                            return responseProceed;
                        }
                    }
                    if (!a(buffer2)) {
                        this.a.log("");
                        Logger logger9 = this.a;
                        StringBuilder sbA11 = g9.a("<-- END HTTP (binary ");
                        sbA11.append(buffer2.size());
                        sbA11.append("-byte body omitted)");
                        logger9.log(sbA11.toString());
                        return responseProceed;
                    }
                    if (jContentLength != 0) {
                        this.a.log("");
                        this.a.log(buffer2.clone().readString(charset2));
                    }
                    Logger logger10 = this.a;
                    StringBuilder sbA12 = g9.a("<-- END HTTP (");
                    sbA12.append(buffer2.size());
                    sbA12.append("-byte body)");
                    logger10.log(sbA12.toString());
                }
            }
            return responseProceed;
        } catch (Exception e) {
            this.a.log("<-- HTTP FAILED: " + e);
            throw e;
        }
    }

    public HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.b = level;
        return this;
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.b = Level.NONE;
        this.a = logger;
    }

    public final boolean a(Headers headers) {
        String str = headers.get("Content-Encoding");
        return (str == null || str.equalsIgnoreCase("identity")) ? false : true;
    }
}
