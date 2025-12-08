package okhttp3.internal.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.connection.Transmitter;

/* loaded from: classes.dex */
public final class RetryAndFollowUpInterceptor implements Interceptor {
    public final OkHttpClient a;

    public RetryAndFollowUpInterceptor(OkHttpClient okHttpClient) {
        this.a = okHttpClient;
    }

    public final boolean a(IOException iOException, Transmitter transmitter, boolean z, Request request) {
        if (!this.a.retryOnConnectionFailure()) {
            return false;
        }
        if (z) {
            RequestBody requestBodyBody = request.body();
            if ((requestBodyBody != null && requestBodyBody.isOneShot()) || (iOException instanceof FileNotFoundException)) {
                return false;
            }
        }
        return (!(iOException instanceof ProtocolException) && (!(iOException instanceof InterruptedIOException) ? (!(iOException instanceof SSLHandshakeException) || !(iOException.getCause() instanceof CertificateException)) && !(iOException instanceof SSLPeerUnverifiedException) : (iOException instanceof SocketTimeoutException) && !z)) && transmitter.canRetry();
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01af A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0154 A[FALL_THROUGH] */
    @Override // okhttp3.Interceptor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public okhttp3.Response intercept(okhttp3.Interceptor.Chain r13) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 558
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
    }

    public final int a(Response response, int i) {
        String strHeader = response.header("Retry-After");
        if (strHeader == null) {
            return i;
        }
        if (strHeader.matches("\\d+")) {
            return Integer.valueOf(strHeader).intValue();
        }
        return Integer.MAX_VALUE;
    }
}
