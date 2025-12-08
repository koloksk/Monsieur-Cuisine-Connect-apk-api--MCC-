package okhttp3.internal.connection;

import defpackage.in;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionPool;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.AsyncTimeout;
import okio.Timeout;

/* loaded from: classes.dex */
public final class Transmitter {
    public final OkHttpClient a;
    public final RealConnectionPool b;
    public final Call c;
    public RealConnection connection;
    public final EventListener d;
    public final AsyncTimeout e = new a();

    @Nullable
    public Object f;
    public Request g;
    public in h;

    @Nullable
    public Exchange i;
    public boolean j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;

    public class a extends AsyncTimeout {
        public a() {
        }

        @Override // okio.AsyncTimeout
        public void timedOut() throws IOException {
            Transmitter.this.cancel();
        }
    }

    public static final class b extends WeakReference<Transmitter> {
        public final Object a;

        public b(Transmitter transmitter, Object obj) {
            super(transmitter);
            this.a = obj;
        }
    }

    public Transmitter(OkHttpClient okHttpClient, Call call) {
        this.a = okHttpClient;
        Internal internal = Internal.instance;
        ConnectionPool connectionPool = okHttpClient.connectionPool();
        if (((OkHttpClient.a) internal) == null) {
            throw null;
        }
        this.b = connectionPool.a;
        this.c = call;
        this.d = okHttpClient.eventListenerFactory().create(call);
        this.e.timeout(okHttpClient.callTimeoutMillis(), TimeUnit.MILLISECONDS);
    }

    public Exchange a(Interceptor.Chain chain, boolean z) {
        synchronized (this.b) {
            if (this.n) {
                throw new IllegalStateException("released");
            }
            if (this.i != null) {
                throw new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()");
            }
        }
        in inVar = this.h;
        OkHttpClient okHttpClient = this.a;
        if (inVar == null) {
            throw null;
        }
        try {
            Exchange exchange = new Exchange(this, this.c, this.d, this.h, inVar.a(chain.connectTimeoutMillis(), chain.readTimeoutMillis(), chain.writeTimeoutMillis(), okHttpClient.pingIntervalMillis(), okHttpClient.retryOnConnectionFailure(), z).a(okHttpClient, chain));
            synchronized (this.b) {
                this.i = exchange;
                this.j = false;
                this.k = false;
            }
            return exchange;
        } catch (IOException e) {
            inVar.d();
            throw new RouteException(e);
        } catch (RouteException e2) {
            inVar.d();
            throw e2;
        }
    }

    public void callStart() {
        this.f = Platform.get().getStackTraceForCloseable("response.body().close()");
        this.d.callStart(this.c);
    }

    public boolean canRetry() {
        return this.h.b() && this.h.a();
    }

    public void cancel() throws IOException {
        Exchange exchange;
        RealConnection realConnection;
        synchronized (this.b) {
            this.l = true;
            exchange = this.i;
            realConnection = (this.h == null || this.h.h == null) ? this.connection : this.h.h;
        }
        if (exchange != null) {
            exchange.cancel();
        } else if (realConnection != null) {
            realConnection.cancel();
        }
    }

    public void exchangeDoneDueToException() {
        synchronized (this.b) {
            if (this.n) {
                throw new IllegalStateException();
            }
            this.i = null;
        }
    }

    public boolean hasExchange() {
        boolean z;
        synchronized (this.b) {
            z = this.i != null;
        }
        return z;
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.b) {
            z = this.l;
        }
        return z;
    }

    @Nullable
    public IOException noMoreExchanges(@Nullable IOException iOException) {
        synchronized (this.b) {
            this.n = true;
        }
        return a(iOException, false);
    }

    public void prepareToConnect(Request request) throws IOException {
        SSLSocketFactory sslSocketFactory;
        HostnameVerifier hostnameVerifier;
        CertificatePinner certificatePinner;
        Request request2 = this.g;
        if (request2 != null) {
            if (Util.sameConnection(request2.url(), request.url()) && this.h.a()) {
                return;
            }
            if (this.i != null) {
                throw new IllegalStateException();
            }
            if (this.h != null) {
                a((IOException) null, true);
                this.h = null;
            }
        }
        this.g = request;
        RealConnectionPool realConnectionPool = this.b;
        HttpUrl httpUrlUrl = request.url();
        if (httpUrlUrl.isHttps()) {
            sslSocketFactory = this.a.sslSocketFactory();
            hostnameVerifier = this.a.hostnameVerifier();
            certificatePinner = this.a.certificatePinner();
        } else {
            sslSocketFactory = null;
            hostnameVerifier = null;
            certificatePinner = null;
        }
        this.h = new in(this, realConnectionPool, new Address(httpUrlUrl.host(), httpUrlUrl.port(), this.a.dns(), this.a.socketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, this.a.proxyAuthenticator(), this.a.proxy(), this.a.protocols(), this.a.connectionSpecs(), this.a.proxySelector()), this.c, this.d);
    }

    public Timeout timeout() {
        return this.e;
    }

    public void timeoutEarlyExit() {
        if (this.m) {
            throw new IllegalStateException();
        }
        this.m = true;
        this.e.exit();
    }

    public void timeoutEnter() {
        this.e.enter();
    }

    public void a(RealConnection realConnection) {
        if (this.connection == null) {
            this.connection = realConnection;
            realConnection.n.add(new b(this, this.f));
            return;
        }
        throw new IllegalStateException();
    }

    @Nullable
    public Socket a() {
        int size = this.connection.n.size();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            }
            if (this.connection.n.get(i).get() == this) {
                break;
            }
            i++;
        }
        if (i != -1) {
            RealConnection realConnection = this.connection;
            realConnection.n.remove(i);
            this.connection = null;
            if (realConnection.n.isEmpty()) {
                realConnection.o = System.nanoTime();
                RealConnectionPool realConnectionPool = this.b;
                if (realConnectionPool != null) {
                    if (!realConnection.i && realConnectionPool.a != 0) {
                        realConnectionPool.notifyAll();
                    } else {
                        realConnectionPool.d.remove(realConnection);
                        z = true;
                    }
                    if (z) {
                        return realConnection.socket();
                    }
                } else {
                    throw null;
                }
            }
            return null;
        }
        throw new IllegalStateException();
    }

    @Nullable
    public IOException a(Exchange exchange, boolean z, boolean z2, @Nullable IOException iOException) {
        boolean z3;
        synchronized (this.b) {
            if (exchange != this.i) {
                return iOException;
            }
            boolean z4 = true;
            if (z) {
                z3 = !this.j;
                this.j = true;
            } else {
                z3 = false;
            }
            if (z2) {
                if (!this.k) {
                    z3 = true;
                }
                this.k = true;
            }
            if (this.j && this.k && z3) {
                this.i.connection().k++;
                this.i = null;
            } else {
                z4 = false;
            }
            return z4 ? a(iOException, false) : iOException;
        }
    }

    @Nullable
    public final IOException a(@Nullable IOException iOException, boolean z) throws IOException {
        RealConnection realConnection;
        Socket socketA;
        boolean z2;
        synchronized (this.b) {
            if (z) {
                if (this.i != null) {
                    throw new IllegalStateException("cannot release connection while it is in use");
                }
            }
            realConnection = this.connection;
            socketA = (this.connection != null && this.i == null && (z || this.n)) ? a() : null;
            if (this.connection != null) {
                realConnection = null;
            }
            z2 = this.n && this.i == null;
        }
        Util.closeQuietly(socketA);
        if (realConnection != null) {
            this.d.connectionReleased(this.c, realConnection);
        }
        if (z2) {
            boolean z3 = iOException != null;
            if (!this.m && this.e.exit()) {
                InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
                if (iOException != null) {
                    interruptedIOException.initCause(iOException);
                }
                iOException = interruptedIOException;
            }
            if (z3) {
                this.d.callFailed(this.c, iOException);
            } else {
                this.d.callEnd(this.c);
            }
        }
        return iOException;
    }
}
