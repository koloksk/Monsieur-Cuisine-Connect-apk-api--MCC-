package defpackage;

import android.support.v4.app.NotificationCompat;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.connection.Transmitter;
import okhttp3.internal.platform.Platform;
import okio.Timeout;

/* loaded from: classes.dex */
public final class an implements Call {
    public final OkHttpClient a;
    public Transmitter b;
    public final Request c;
    public final boolean d;
    public boolean e;

    public final class a extends NamedRunnable {
        public final Callback a;
        public volatile AtomicInteger b;

        public a(Callback callback) {
            super("OkHttp %s", an.this.c.url().redact());
            this.b = new AtomicInteger(0);
            this.a = callback;
        }

        public String a() {
            return an.this.c.url().host();
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() {
            an.this.b.timeoutEnter();
            boolean z = false;
            try {
                try {
                    try {
                        this.a.onResponse(an.this, an.this.a());
                    } catch (IOException e) {
                        e = e;
                        z = true;
                        if (z) {
                            Platform.get().log(4, "Callback failure for " + an.this.b(), e);
                        } else {
                            this.a.onFailure(an.this, e);
                        }
                        an.this.a.dispatcher().b(this);
                    } catch (Throwable th) {
                        th = th;
                        z = true;
                        an.this.b.cancel();
                        if (!z) {
                            IOException iOException = new IOException("canceled due to " + th);
                            iOException.addSuppressed(th);
                            this.a.onFailure(an.this, iOException);
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                } catch (Throwable th2) {
                    th = th2;
                }
                an.this.a.dispatcher().b(this);
            } catch (Throwable th3) {
                an.this.a.dispatcher().b(this);
                throw th3;
            }
        }
    }

    public an(OkHttpClient okHttpClient, Request request, boolean z) {
        this.a = okHttpClient;
        this.c = request;
        this.d = z;
    }

    public static an a(OkHttpClient okHttpClient, Request request, boolean z) {
        an anVar = new an(okHttpClient, request, z);
        anVar.b = new Transmitter(okHttpClient, anVar);
        return anVar;
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.isCanceled() ? "canceled " : "");
        sb.append(this.d ? "web socket" : NotificationCompat.CATEGORY_CALL);
        sb.append(" to ");
        sb.append(this.c.url().redact());
        return sb.toString();
    }

    @Override // okhttp3.Call
    public void cancel() throws IOException {
        this.b.cancel();
    }

    public Object clone() throws CloneNotSupportedException {
        return a(this.a, this.c, this.d);
    }

    @Override // okhttp3.Call
    public void enqueue(Callback callback) {
        synchronized (this) {
            if (this.e) {
                throw new IllegalStateException("Already Executed");
            }
            this.e = true;
        }
        this.b.callStart();
        this.a.dispatcher().a(new a(callback));
    }

    @Override // okhttp3.Call
    public Response execute() throws IOException {
        synchronized (this) {
            if (this.e) {
                throw new IllegalStateException("Already Executed");
            }
            this.e = true;
        }
        this.b.timeoutEnter();
        this.b.callStart();
        try {
            this.a.dispatcher().a(this);
            return a();
        } finally {
            Dispatcher dispatcher = this.a.dispatcher();
            dispatcher.a(dispatcher.g, this);
        }
    }

    @Override // okhttp3.Call
    public boolean isCanceled() {
        return this.b.isCanceled();
    }

    @Override // okhttp3.Call
    public synchronized boolean isExecuted() {
        return this.e;
    }

    @Override // okhttp3.Call
    public Request request() {
        return this.c;
    }

    @Override // okhttp3.Call
    public Timeout timeout() {
        return this.b.timeout();
    }

    @Override // okhttp3.Call
    /* renamed from: clone, reason: collision with other method in class */
    public Call mo0clone() {
        return a(this.a, this.c, this.d);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public okhttp3.Response a() throws java.lang.Throwable {
        /*
            r12 = this;
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            okhttp3.OkHttpClient r0 = r12.a
            java.util.List r0 = r0.interceptors()
            r1.addAll(r0)
            okhttp3.internal.http.RetryAndFollowUpInterceptor r0 = new okhttp3.internal.http.RetryAndFollowUpInterceptor
            okhttp3.OkHttpClient r2 = r12.a
            r0.<init>(r2)
            r1.add(r0)
            okhttp3.internal.http.BridgeInterceptor r0 = new okhttp3.internal.http.BridgeInterceptor
            okhttp3.OkHttpClient r2 = r12.a
            okhttp3.CookieJar r2 = r2.cookieJar()
            r0.<init>(r2)
            r1.add(r0)
            okhttp3.internal.cache.CacheInterceptor r0 = new okhttp3.internal.cache.CacheInterceptor
            okhttp3.OkHttpClient r2 = r12.a
            okhttp3.Cache r3 = r2.j
            if (r3 == 0) goto L31
            okhttp3.internal.cache.InternalCache r2 = r3.a
            goto L33
        L31:
            okhttp3.internal.cache.InternalCache r2 = r2.k
        L33:
            r0.<init>(r2)
            r1.add(r0)
            okhttp3.internal.connection.ConnectInterceptor r0 = new okhttp3.internal.connection.ConnectInterceptor
            okhttp3.OkHttpClient r2 = r12.a
            r0.<init>(r2)
            r1.add(r0)
            boolean r0 = r12.d
            if (r0 != 0) goto L50
            okhttp3.OkHttpClient r0 = r12.a
            java.util.List r0 = r0.networkInterceptors()
            r1.addAll(r0)
        L50:
            okhttp3.internal.http.CallServerInterceptor r0 = new okhttp3.internal.http.CallServerInterceptor
            boolean r2 = r12.d
            r0.<init>(r2)
            r1.add(r0)
            okhttp3.internal.http.RealInterceptorChain r10 = new okhttp3.internal.http.RealInterceptorChain
            okhttp3.internal.connection.Transmitter r2 = r12.b
            r3 = 0
            r4 = 0
            okhttp3.Request r5 = r12.c
            okhttp3.OkHttpClient r0 = r12.a
            int r7 = r0.connectTimeoutMillis()
            okhttp3.OkHttpClient r0 = r12.a
            int r8 = r0.readTimeoutMillis()
            okhttp3.OkHttpClient r0 = r12.a
            int r9 = r0.writeTimeoutMillis()
            r0 = r10
            r6 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r0 = 0
            r1 = 0
            okhttp3.Request r2 = r12.c     // Catch: java.lang.Throwable -> L9a java.io.IOException -> L9c
            okhttp3.Response r2 = r10.proceed(r2)     // Catch: java.lang.Throwable -> L9a java.io.IOException -> L9c
            okhttp3.internal.connection.Transmitter r3 = r12.b     // Catch: java.lang.Throwable -> L9a java.io.IOException -> L9c
            boolean r3 = r3.isCanceled()     // Catch: java.lang.Throwable -> L9a java.io.IOException -> L9c
            if (r3 != 0) goto L8f
            okhttp3.internal.connection.Transmitter r0 = r12.b
            r0.noMoreExchanges(r1)
            return r2
        L8f:
            okhttp3.internal.Util.closeQuietly(r2)     // Catch: java.lang.Throwable -> L9a java.io.IOException -> L9c
            java.io.IOException r2 = new java.io.IOException     // Catch: java.lang.Throwable -> L9a java.io.IOException -> L9c
            java.lang.String r3 = "Canceled"
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L9a java.io.IOException -> L9c
            throw r2     // Catch: java.lang.Throwable -> L9a java.io.IOException -> L9c
        L9a:
            r2 = move-exception
            goto La9
        L9c:
            r0 = move-exception
            r2 = 1
            okhttp3.internal.connection.Transmitter r3 = r12.b     // Catch: java.lang.Throwable -> La5
            java.io.IOException r0 = r3.noMoreExchanges(r0)     // Catch: java.lang.Throwable -> La5
            throw r0     // Catch: java.lang.Throwable -> La5
        La5:
            r0 = move-exception
            r11 = r2
            r2 = r0
            r0 = r11
        La9:
            if (r0 != 0) goto Lb0
            okhttp3.internal.connection.Transmitter r0 = r12.b
            r0.noMoreExchanges(r1)
        Lb0:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.an.a():okhttp3.Response");
    }
}
