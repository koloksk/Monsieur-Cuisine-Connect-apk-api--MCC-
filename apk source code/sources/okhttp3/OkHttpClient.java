package okhttp3;

import defpackage.an;
import defpackage.g9;
import defpackage.xm;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.ConnectionSpec;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealConnectionPool;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.proxy.NullProxySelector;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* loaded from: classes.dex */
public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {
    public static final List<Protocol> C = Util.immutableList(Protocol.HTTP_2, Protocol.HTTP_1_1);
    public static final List<ConnectionSpec> D = Util.immutableList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT);
    public final int A;
    public final int B;
    public final Dispatcher a;

    @Nullable
    public final Proxy b;
    public final List<Protocol> c;
    public final List<ConnectionSpec> d;
    public final List<Interceptor> e;
    public final List<Interceptor> f;
    public final EventListener.Factory g;
    public final ProxySelector h;
    public final CookieJar i;

    @Nullable
    public final Cache j;

    @Nullable
    public final InternalCache k;
    public final SocketFactory l;
    public final SSLSocketFactory m;
    public final CertificateChainCleaner n;
    public final HostnameVerifier o;
    public final CertificatePinner p;
    public final Authenticator q;
    public final Authenticator r;
    public final ConnectionPool s;
    public final Dns t;
    public final boolean u;
    public final boolean v;
    public final boolean w;
    public final int x;
    public final int y;
    public final int z;

    public static final class Builder {
        public int A;
        public int B;
        public Dispatcher a;

        @Nullable
        public Proxy b;
        public List<Protocol> c;
        public List<ConnectionSpec> d;
        public final List<Interceptor> e;
        public final List<Interceptor> f;
        public EventListener.Factory g;
        public ProxySelector h;
        public CookieJar i;

        @Nullable
        public Cache j;

        @Nullable
        public InternalCache k;
        public SocketFactory l;

        @Nullable
        public SSLSocketFactory m;

        @Nullable
        public CertificateChainCleaner n;
        public HostnameVerifier o;
        public CertificatePinner p;
        public Authenticator q;
        public Authenticator r;
        public ConnectionPool s;
        public Dns t;
        public boolean u;
        public boolean v;
        public boolean w;
        public int x;
        public int y;
        public int z;

        public Builder() {
            this.e = new ArrayList();
            this.f = new ArrayList();
            this.a = new Dispatcher();
            this.c = OkHttpClient.C;
            this.d = OkHttpClient.D;
            this.g = new xm(EventListener.NONE);
            ProxySelector proxySelector = ProxySelector.getDefault();
            this.h = proxySelector;
            if (proxySelector == null) {
                this.h = new NullProxySelector();
            }
            this.i = CookieJar.NO_COOKIES;
            this.l = SocketFactory.getDefault();
            this.o = OkHostnameVerifier.INSTANCE;
            this.p = CertificatePinner.DEFAULT;
            Authenticator authenticator = Authenticator.NONE;
            this.q = authenticator;
            this.r = authenticator;
            this.s = new ConnectionPool();
            this.t = Dns.SYSTEM;
            this.u = true;
            this.v = true;
            this.w = true;
            this.x = 0;
            this.y = 10000;
            this.z = 10000;
            this.A = 10000;
            this.B = 0;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptor == null) {
                throw new IllegalArgumentException("interceptor == null");
            }
            this.e.add(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            if (interceptor == null) {
                throw new IllegalArgumentException("interceptor == null");
            }
            this.f.add(interceptor);
            return this;
        }

        public Builder authenticator(Authenticator authenticator) {
            if (authenticator == null) {
                throw new NullPointerException("authenticator == null");
            }
            this.r = authenticator;
            return this;
        }

        public OkHttpClient build() {
            return new OkHttpClient(this);
        }

        public Builder cache(@Nullable Cache cache) {
            this.j = cache;
            this.k = null;
            return this;
        }

        public Builder callTimeout(long j, TimeUnit timeUnit) {
            this.x = Util.checkDuration("timeout", j, timeUnit);
            return this;
        }

        public Builder certificatePinner(CertificatePinner certificatePinner) {
            if (certificatePinner == null) {
                throw new NullPointerException("certificatePinner == null");
            }
            this.p = certificatePinner;
            return this;
        }

        public Builder connectTimeout(long j, TimeUnit timeUnit) {
            this.y = Util.checkDuration("timeout", j, timeUnit);
            return this;
        }

        public Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) {
                throw new NullPointerException("connectionPool == null");
            }
            this.s = connectionPool;
            return this;
        }

        public Builder connectionSpecs(List<ConnectionSpec> list) {
            this.d = Util.immutableList(list);
            return this;
        }

        public Builder cookieJar(CookieJar cookieJar) {
            if (cookieJar == null) {
                throw new NullPointerException("cookieJar == null");
            }
            this.i = cookieJar;
            return this;
        }

        public Builder dispatcher(Dispatcher dispatcher) {
            if (dispatcher == null) {
                throw new IllegalArgumentException("dispatcher == null");
            }
            this.a = dispatcher;
            return this;
        }

        public Builder dns(Dns dns) {
            if (dns == null) {
                throw new NullPointerException("dns == null");
            }
            this.t = dns;
            return this;
        }

        public Builder eventListener(EventListener eventListener) {
            if (eventListener == null) {
                throw new NullPointerException("eventListener == null");
            }
            this.g = EventListener.a(eventListener);
            return this;
        }

        public Builder eventListenerFactory(EventListener.Factory factory) {
            if (factory == null) {
                throw new NullPointerException("eventListenerFactory == null");
            }
            this.g = factory;
            return this;
        }

        public Builder followRedirects(boolean z) {
            this.v = z;
            return this;
        }

        public Builder followSslRedirects(boolean z) {
            this.u = z;
            return this;
        }

        public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            if (hostnameVerifier == null) {
                throw new NullPointerException("hostnameVerifier == null");
            }
            this.o = hostnameVerifier;
            return this;
        }

        public List<Interceptor> interceptors() {
            return this.e;
        }

        public List<Interceptor> networkInterceptors() {
            return this.f;
        }

        public Builder pingInterval(long j, TimeUnit timeUnit) {
            this.B = Util.checkDuration("interval", j, timeUnit);
            return this;
        }

        public Builder protocols(List<Protocol> list) {
            ArrayList arrayList = new ArrayList(list);
            if (!arrayList.contains(Protocol.H2_PRIOR_KNOWLEDGE) && !arrayList.contains(Protocol.HTTP_1_1)) {
                throw new IllegalArgumentException("protocols must contain h2_prior_knowledge or http/1.1: " + arrayList);
            }
            if (arrayList.contains(Protocol.H2_PRIOR_KNOWLEDGE) && arrayList.size() > 1) {
                throw new IllegalArgumentException("protocols containing h2_prior_knowledge cannot use other protocols: " + arrayList);
            }
            if (arrayList.contains(Protocol.HTTP_1_0)) {
                throw new IllegalArgumentException("protocols must not contain http/1.0: " + arrayList);
            }
            if (arrayList.contains(null)) {
                throw new IllegalArgumentException("protocols must not contain null");
            }
            arrayList.remove(Protocol.SPDY_3);
            this.c = Collections.unmodifiableList(arrayList);
            return this;
        }

        public Builder proxy(@Nullable Proxy proxy) {
            this.b = proxy;
            return this;
        }

        public Builder proxyAuthenticator(Authenticator authenticator) {
            if (authenticator == null) {
                throw new NullPointerException("proxyAuthenticator == null");
            }
            this.q = authenticator;
            return this;
        }

        public Builder proxySelector(ProxySelector proxySelector) {
            if (proxySelector == null) {
                throw new NullPointerException("proxySelector == null");
            }
            this.h = proxySelector;
            return this;
        }

        public Builder readTimeout(long j, TimeUnit timeUnit) {
            this.z = Util.checkDuration("timeout", j, timeUnit);
            return this;
        }

        public Builder retryOnConnectionFailure(boolean z) {
            this.w = z;
            return this;
        }

        public Builder socketFactory(SocketFactory socketFactory) {
            if (socketFactory == null) {
                throw new NullPointerException("socketFactory == null");
            }
            if (socketFactory instanceof SSLSocketFactory) {
                throw new IllegalArgumentException("socketFactory instanceof SSLSocketFactory");
            }
            this.l = socketFactory;
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            if (sSLSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null");
            }
            this.m = sSLSocketFactory;
            this.n = Platform.get().buildCertificateChainCleaner(sSLSocketFactory);
            return this;
        }

        public Builder writeTimeout(long j, TimeUnit timeUnit) {
            this.A = Util.checkDuration("timeout", j, timeUnit);
            return this;
        }

        @IgnoreJRERequirement
        public Builder callTimeout(Duration duration) {
            this.x = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        @IgnoreJRERequirement
        public Builder connectTimeout(Duration duration) {
            this.y = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        @IgnoreJRERequirement
        public Builder pingInterval(Duration duration) {
            this.B = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        @IgnoreJRERequirement
        public Builder readTimeout(Duration duration) {
            this.z = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        @IgnoreJRERequirement
        public Builder writeTimeout(Duration duration) {
            this.A = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
            if (sSLSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null");
            }
            if (x509TrustManager != null) {
                this.m = sSLSocketFactory;
                this.n = CertificateChainCleaner.get(x509TrustManager);
                return this;
            }
            throw new NullPointerException("trustManager == null");
        }

        public Builder(OkHttpClient okHttpClient) {
            this.e = new ArrayList();
            this.f = new ArrayList();
            this.a = okHttpClient.a;
            this.b = okHttpClient.b;
            this.c = okHttpClient.c;
            this.d = okHttpClient.d;
            this.e.addAll(okHttpClient.e);
            this.f.addAll(okHttpClient.f);
            this.g = okHttpClient.g;
            this.h = okHttpClient.h;
            this.i = okHttpClient.i;
            this.k = okHttpClient.k;
            this.j = okHttpClient.j;
            this.l = okHttpClient.l;
            this.m = okHttpClient.m;
            this.n = okHttpClient.n;
            this.o = okHttpClient.o;
            this.p = okHttpClient.p;
            this.q = okHttpClient.q;
            this.r = okHttpClient.r;
            this.s = okHttpClient.s;
            this.t = okHttpClient.t;
            this.u = okHttpClient.u;
            this.v = okHttpClient.v;
            this.w = okHttpClient.w;
            this.x = okHttpClient.x;
            this.y = okHttpClient.y;
            this.z = okHttpClient.z;
            this.A = okHttpClient.A;
            this.B = okHttpClient.B;
        }
    }

    public class a extends Internal {
        @Override // okhttp3.internal.Internal
        public void addLenient(Headers.Builder builder, String str) {
            builder.a(str);
        }

        @Override // okhttp3.internal.Internal
        public void apply(ConnectionSpec connectionSpec, SSLSocket sSLSocket, boolean z) {
            String[] strArrIntersect = connectionSpec.c != null ? Util.intersect(CipherSuite.b, sSLSocket.getEnabledCipherSuites(), connectionSpec.c) : sSLSocket.getEnabledCipherSuites();
            String[] strArrIntersect2 = connectionSpec.d != null ? Util.intersect(Util.NATURAL_ORDER, sSLSocket.getEnabledProtocols(), connectionSpec.d) : sSLSocket.getEnabledProtocols();
            String[] supportedCipherSuites = sSLSocket.getSupportedCipherSuites();
            int iIndexOf = Util.indexOf(CipherSuite.b, supportedCipherSuites, "TLS_FALLBACK_SCSV");
            if (z && iIndexOf != -1) {
                strArrIntersect = Util.concat(strArrIntersect, supportedCipherSuites[iIndexOf]);
            }
            ConnectionSpec connectionSpecBuild = new ConnectionSpec.Builder(connectionSpec).cipherSuites(strArrIntersect).tlsVersions(strArrIntersect2).build();
            String[] strArr = connectionSpecBuild.d;
            if (strArr != null) {
                sSLSocket.setEnabledProtocols(strArr);
            }
            String[] strArr2 = connectionSpecBuild.c;
            if (strArr2 != null) {
                sSLSocket.setEnabledCipherSuites(strArr2);
            }
        }

        @Override // okhttp3.internal.Internal
        public int code(Response.Builder builder) {
            return builder.c;
        }

        @Override // okhttp3.internal.Internal
        public boolean equalsNonHost(Address address, Address address2) {
            return address.a(address2);
        }

        @Override // okhttp3.internal.Internal
        @Nullable
        public Exchange exchange(Response response) {
            return response.m;
        }

        @Override // okhttp3.internal.Internal
        public void initExchange(Response.Builder builder, Exchange exchange) {
            builder.m = exchange;
        }

        @Override // okhttp3.internal.Internal
        public Call newWebSocketCall(OkHttpClient okHttpClient, Request request) {
            return an.a(okHttpClient, request, true);
        }

        @Override // okhttp3.internal.Internal
        public RealConnectionPool realConnectionPool(ConnectionPool connectionPool) {
            return connectionPool.a;
        }

        @Override // okhttp3.internal.Internal
        public void addLenient(Headers.Builder builder, String str, String str2) {
            builder.a.add(str);
            builder.a.add(str2.trim());
        }
    }

    static {
        Internal.instance = new a();
    }

    public OkHttpClient() {
        this(new Builder());
    }

    public Authenticator authenticator() {
        return this.r;
    }

    @Nullable
    public Cache cache() {
        return this.j;
    }

    public int callTimeoutMillis() {
        return this.x;
    }

    public CertificatePinner certificatePinner() {
        return this.p;
    }

    public int connectTimeoutMillis() {
        return this.y;
    }

    public ConnectionPool connectionPool() {
        return this.s;
    }

    public List<ConnectionSpec> connectionSpecs() {
        return this.d;
    }

    public CookieJar cookieJar() {
        return this.i;
    }

    public Dispatcher dispatcher() {
        return this.a;
    }

    public Dns dns() {
        return this.t;
    }

    public EventListener.Factory eventListenerFactory() {
        return this.g;
    }

    public boolean followRedirects() {
        return this.v;
    }

    public boolean followSslRedirects() {
        return this.u;
    }

    public HostnameVerifier hostnameVerifier() {
        return this.o;
    }

    public List<Interceptor> interceptors() {
        return this.e;
    }

    public List<Interceptor> networkInterceptors() {
        return this.f;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override // okhttp3.Call.Factory
    public Call newCall(Request request) {
        return an.a(this, request, false);
    }

    @Override // okhttp3.WebSocket.Factory
    public WebSocket newWebSocket(Request request, WebSocketListener webSocketListener) {
        RealWebSocket realWebSocket = new RealWebSocket(request, webSocketListener, new Random(), this.B);
        realWebSocket.connect(this);
        return realWebSocket;
    }

    public int pingIntervalMillis() {
        return this.B;
    }

    public List<Protocol> protocols() {
        return this.c;
    }

    @Nullable
    public Proxy proxy() {
        return this.b;
    }

    public Authenticator proxyAuthenticator() {
        return this.q;
    }

    public ProxySelector proxySelector() {
        return this.h;
    }

    public int readTimeoutMillis() {
        return this.z;
    }

    public boolean retryOnConnectionFailure() {
        return this.w;
    }

    public SocketFactory socketFactory() {
        return this.l;
    }

    public SSLSocketFactory sslSocketFactory() {
        return this.m;
    }

    public int writeTimeoutMillis() {
        return this.A;
    }

    public OkHttpClient(Builder builder) throws KeyManagementException {
        boolean z;
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = Util.immutableList(builder.e);
        this.f = Util.immutableList(builder.f);
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
        this.k = builder.k;
        this.l = builder.l;
        Iterator<ConnectionSpec> it = this.d.iterator();
        loop0: while (true) {
            z = false;
            while (it.hasNext()) {
                z = (z || it.next().isTls()) ? true : z;
            }
        }
        if (builder.m == null && z) {
            X509TrustManager x509TrustManagerPlatformTrustManager = Util.platformTrustManager();
            try {
                SSLContext sSLContext = Platform.get().getSSLContext();
                sSLContext.init(null, new TrustManager[]{x509TrustManagerPlatformTrustManager}, null);
                this.m = sSLContext.getSocketFactory();
                this.n = CertificateChainCleaner.get(x509TrustManagerPlatformTrustManager);
            } catch (GeneralSecurityException e) {
                throw new AssertionError("No System TLS", e);
            }
        } else {
            this.m = builder.m;
            this.n = builder.n;
        }
        if (this.m != null) {
            Platform.get().configureSslSocketFactory(this.m);
        }
        this.o = builder.o;
        CertificatePinner certificatePinner = builder.p;
        CertificateChainCleaner certificateChainCleaner = this.n;
        this.p = Objects.equals(certificatePinner.b, certificateChainCleaner) ? certificatePinner : new CertificatePinner(certificatePinner.a, certificateChainCleaner);
        this.q = builder.q;
        this.r = builder.r;
        this.s = builder.s;
        this.t = builder.t;
        this.u = builder.u;
        this.v = builder.v;
        this.w = builder.w;
        this.x = builder.x;
        this.y = builder.y;
        this.z = builder.z;
        this.A = builder.A;
        this.B = builder.B;
        if (this.e.contains(null)) {
            StringBuilder sbA = g9.a("Null interceptor: ");
            sbA.append(this.e);
            throw new IllegalStateException(sbA.toString());
        }
        if (this.f.contains(null)) {
            StringBuilder sbA2 = g9.a("Null network interceptor: ");
            sbA2.append(this.f);
            throw new IllegalStateException(sbA2.toString());
        }
    }
}
