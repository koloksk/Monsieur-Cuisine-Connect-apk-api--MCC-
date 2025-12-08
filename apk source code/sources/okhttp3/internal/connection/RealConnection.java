package okhttp3.internal.connection;

import defpackage.g9;
import defpackage.hn;
import io.reactivex.annotations.SchedulerSupport;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.ConnectionSpec;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http1.Http1ExchangeCodec;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2ExchangeCodec;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.http2.StreamResetException;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.OkHostnameVerifier;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/* loaded from: classes.dex */
public final class RealConnection extends Http2Connection.Listener implements Connection {
    public final Route a;
    public Socket b;
    public Socket c;
    public final RealConnectionPool connectionPool;
    public Handshake d;
    public Protocol e;
    public Http2Connection f;
    public BufferedSource g;
    public BufferedSink h;
    public boolean i;
    public int j;
    public int k;
    public int l;
    public int m = 1;
    public final List<Reference<Transmitter>> n = new ArrayList();
    public long o = Long.MAX_VALUE;

    public RealConnection(RealConnectionPool realConnectionPool, Route route) {
        this.connectionPool = realConnectionPool;
        this.a = route;
    }

    public final void a(int i, int i2, Call call, EventListener eventListener) throws IOException {
        Proxy proxy = this.a.proxy();
        this.b = (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.HTTP) ? this.a.address().socketFactory().createSocket() : new Socket(proxy);
        eventListener.connectStart(call, this.a.socketAddress(), proxy);
        this.b.setSoTimeout(i2);
        try {
            Platform.get().connectSocket(this.b, this.a.socketAddress(), i);
            try {
                this.g = Okio.buffer(Okio.source(this.b));
                this.h = Okio.buffer(Okio.sink(this.b));
            } catch (NullPointerException e) {
                if ("throw with null exception".equals(e.getMessage())) {
                    throw new IOException(e);
                }
            }
        } catch (ConnectException e2) {
            StringBuilder sbA = g9.a("Failed to connect to ");
            sbA.append(this.a.socketAddress());
            ConnectException connectException = new ConnectException(sbA.toString());
            connectException.initCause(e2);
            throw connectException;
        }
    }

    public void cancel() throws IOException {
        Util.closeQuietly(this.b);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e6 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x015c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void connect(int r17, int r18, int r19, int r20, boolean r21, okhttp3.Call r22, okhttp3.EventListener r23) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.RealConnection.connect(int, int, int, int, boolean, okhttp3.Call, okhttp3.EventListener):void");
    }

    @Override // okhttp3.Connection
    public Handshake handshake() {
        return this.d;
    }

    public boolean isHealthy(boolean z) throws SocketException {
        if (this.c.isClosed() || this.c.isInputShutdown() || this.c.isOutputShutdown()) {
            return false;
        }
        Http2Connection http2Connection = this.f;
        if (http2Connection != null) {
            return http2Connection.isHealthy(System.nanoTime());
        }
        if (z) {
            try {
                int soTimeout = this.c.getSoTimeout();
                try {
                    this.c.setSoTimeout(1);
                    return !this.g.exhausted();
                } finally {
                    this.c.setSoTimeout(soTimeout);
                }
            } catch (SocketTimeoutException unused) {
            } catch (IOException unused2) {
                return false;
            }
        }
        return true;
    }

    public boolean isMultiplexed() {
        return this.f != null;
    }

    public void noNewExchanges() {
        synchronized (this.connectionPool) {
            this.i = true;
        }
    }

    @Override // okhttp3.internal.http2.Http2Connection.Listener
    public void onSettings(Http2Connection http2Connection) {
        synchronized (this.connectionPool) {
            this.m = http2Connection.maxConcurrentStreams();
        }
    }

    @Override // okhttp3.internal.http2.Http2Connection.Listener
    public void onStream(Http2Stream http2Stream) throws IOException {
        http2Stream.close(ErrorCode.REFUSED_STREAM, null);
    }

    @Override // okhttp3.Connection
    public Protocol protocol() {
        return this.e;
    }

    @Override // okhttp3.Connection
    public Route route() {
        return this.a;
    }

    @Override // okhttp3.Connection
    public Socket socket() {
        return this.c;
    }

    public boolean supportsUrl(HttpUrl httpUrl) {
        if (httpUrl.port() != this.a.address().url().port()) {
            return false;
        }
        if (httpUrl.host().equals(this.a.address().url().host())) {
            return true;
        }
        return this.d != null && OkHostnameVerifier.INSTANCE.verify(httpUrl.host(), (X509Certificate) this.d.peerCertificates().get(0));
    }

    public String toString() {
        StringBuilder sbA = g9.a("Connection{");
        sbA.append(this.a.address().url().host());
        sbA.append(":");
        sbA.append(this.a.address().url().port());
        sbA.append(", proxy=");
        sbA.append(this.a.proxy());
        sbA.append(" hostAddress=");
        sbA.append(this.a.socketAddress());
        sbA.append(" cipherSuite=");
        Handshake handshake = this.d;
        sbA.append(handshake != null ? handshake.cipherSuite() : SchedulerSupport.NONE);
        sbA.append(" protocol=");
        sbA.append(this.e);
        sbA.append('}');
        return sbA.toString();
    }

    public final void a(hn hnVar, int i, Call call, EventListener eventListener) throws Throwable {
        SSLSocket sSLSocket;
        Protocol protocol;
        if (this.a.address().sslSocketFactory() == null) {
            if (this.a.address().protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
                this.c = this.b;
                this.e = Protocol.H2_PRIOR_KNOWLEDGE;
                a(i);
                return;
            } else {
                this.c = this.b;
                this.e = Protocol.HTTP_1_1;
                return;
            }
        }
        eventListener.secureConnectStart(call);
        Address address = this.a.address();
        try {
            try {
                sSLSocket = (SSLSocket) address.sslSocketFactory().createSocket(this.b, address.url().host(), address.url().port(), true);
                try {
                    ConnectionSpec connectionSpecA = hnVar.a(sSLSocket);
                    if (connectionSpecA.supportsTlsExtensions()) {
                        Platform.get().configureTlsExtensions(sSLSocket, address.url().host(), address.protocols());
                    }
                    sSLSocket.startHandshake();
                    SSLSession session = sSLSocket.getSession();
                    Handshake handshake = Handshake.get(session);
                    if (!address.hostnameVerifier().verify(address.url().host(), session)) {
                        List<Certificate> listPeerCertificates = handshake.peerCertificates();
                        if (!listPeerCertificates.isEmpty()) {
                            X509Certificate x509Certificate = (X509Certificate) listPeerCertificates.get(0);
                            throw new SSLPeerUnverifiedException("Hostname " + address.url().host() + " not verified:\n    certificate: " + CertificatePinner.pin(x509Certificate) + "\n    DN: " + x509Certificate.getSubjectDN().getName() + "\n    subjectAltNames: " + OkHostnameVerifier.allSubjectAltNames(x509Certificate));
                        }
                        throw new SSLPeerUnverifiedException("Hostname " + address.url().host() + " not verified (no certificates)");
                    }
                    address.certificatePinner().check(address.url().host(), handshake.peerCertificates());
                    String selectedProtocol = connectionSpecA.supportsTlsExtensions() ? Platform.get().getSelectedProtocol(sSLSocket) : null;
                    this.c = sSLSocket;
                    this.g = Okio.buffer(Okio.source(sSLSocket));
                    this.h = Okio.buffer(Okio.sink(this.c));
                    this.d = handshake;
                    if (selectedProtocol != null) {
                        protocol = Protocol.get(selectedProtocol);
                    } else {
                        protocol = Protocol.HTTP_1_1;
                    }
                    this.e = protocol;
                    Platform.get().afterHandshake(sSLSocket);
                    eventListener.secureConnectEnd(call, this.d);
                    if (this.e == Protocol.HTTP_2) {
                        a(i);
                    }
                } catch (AssertionError e) {
                    e = e;
                    if (!Util.isAndroidGetsocknameError(e)) {
                        throw e;
                    }
                    throw new IOException(e);
                } catch (Throwable th) {
                    th = th;
                    if (sSLSocket != null) {
                        Platform.get().afterHandshake(sSLSocket);
                    }
                    Util.closeQuietly((Socket) sSLSocket);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                sSLSocket = null;
            }
        } catch (AssertionError e2) {
            e = e2;
        }
    }

    public final void a(int i) throws IOException {
        this.c.setSoTimeout(0);
        Http2Connection http2ConnectionBuild = new Http2Connection.Builder(true).socket(this.c, this.a.address().url().host(), this.g, this.h).listener(this).pingIntervalMillis(i).build();
        this.f = http2ConnectionBuild;
        http2ConnectionBuild.start();
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x015b, code lost:
    
        if (r0 != null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x015e, code lost:
    
        okhttp3.internal.Util.closeQuietly(r10.b);
        r1 = false;
        r10.b = null;
        r10.h = null;
        r10.g = null;
        r15.connectEnd(r14, r10.a.socketAddress(), r10.a.proxy(), null);
        r4 = r4 + 1;
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:?, code lost:
    
        return;
     */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v5, types: [okhttp3.OkHttpClient, okhttp3.internal.connection.RealConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(int r11, int r12, int r13, okhttp3.Call r14, okhttp3.EventListener r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 391
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.RealConnection.a(int, int, int, okhttp3.Call, okhttp3.EventListener):void");
    }

    public ExchangeCodec a(OkHttpClient okHttpClient, Interceptor.Chain chain) throws SocketException {
        if (this.f != null) {
            return new Http2ExchangeCodec(okHttpClient, this, chain, this.f);
        }
        this.c.setSoTimeout(chain.readTimeoutMillis());
        this.g.timeout().timeout(chain.readTimeoutMillis(), TimeUnit.MILLISECONDS);
        this.h.timeout().timeout(chain.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
        return new Http1ExchangeCodec(okHttpClient, this, this.g, this.h);
    }

    public void a(@Nullable IOException iOException) {
        synchronized (this.connectionPool) {
            if (iOException instanceof StreamResetException) {
                ErrorCode errorCode = ((StreamResetException) iOException).errorCode;
                if (errorCode == ErrorCode.REFUSED_STREAM) {
                    int i = this.l + 1;
                    this.l = i;
                    if (i > 1) {
                        this.i = true;
                        this.j++;
                    }
                } else if (errorCode != ErrorCode.CANCEL) {
                    this.i = true;
                    this.j++;
                }
            } else if (!isMultiplexed() || (iOException instanceof ConnectionShutdownException)) {
                this.i = true;
                if (this.k == 0) {
                    if (iOException != null) {
                        this.connectionPool.connectFailed(this.a, iOException);
                    }
                    this.j++;
                }
            }
        }
    }
}
