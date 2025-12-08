package okhttp3.internal.connection;

import defpackage.g9;
import defpackage.kn;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.Proxy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.Address;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Transmitter;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.OkHostnameVerifier;

/* loaded from: classes.dex */
public final class RealConnectionPool {
    public static final Executor g = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
    public final int a;
    public final long b;
    public final Runnable c = new Runnable() { // from class: gn
        @Override // java.lang.Runnable
        public final void run() throws IOException {
            this.a.a();
        }
    };
    public final Deque<RealConnection> d = new ArrayDeque();
    public final kn e = new kn();
    public boolean f;

    public RealConnectionPool(int i, long j, TimeUnit timeUnit) {
        this.a = i;
        this.b = timeUnit.toNanos(j);
        if (j <= 0) {
            throw new IllegalArgumentException(g9.a("keepAliveDuration <= 0: ", j));
        }
    }

    public /* synthetic */ void a() throws IOException {
        while (true) {
            long jA = a(System.nanoTime());
            if (jA == -1) {
                return;
            }
            if (jA > 0) {
                long j = jA / 1000000;
                long j2 = jA - (1000000 * j);
                synchronized (this) {
                    try {
                        wait(j, (int) j2);
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
    }

    public void connectFailed(Route route, IOException iOException) {
        if (route.proxy().type() != Proxy.Type.DIRECT) {
            Address address = route.address();
            address.proxySelector().connectFailed(address.url().uri(), route.proxy().address(), iOException);
        }
        this.e.b(route);
    }

    public synchronized int connectionCount() {
        return this.d.size();
    }

    public void evictAll() throws IOException {
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            Iterator<RealConnection> it = this.d.iterator();
            while (it.hasNext()) {
                RealConnection next = it.next();
                if (next.n.isEmpty()) {
                    next.i = true;
                    arrayList.add(next);
                    it.remove();
                }
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Util.closeQuietly(((RealConnection) it2.next()).socket());
        }
    }

    public synchronized int idleConnectionCount() {
        int i;
        i = 0;
        Iterator<RealConnection> it = this.d.iterator();
        while (it.hasNext()) {
            if (it.next().n.isEmpty()) {
                i++;
            }
        }
        return i;
    }

    public boolean a(Address address, Transmitter transmitter, @Nullable List<Route> list, boolean z) {
        boolean z2;
        Iterator<RealConnection> it = this.d.iterator();
        while (true) {
            boolean z3 = false;
            if (!it.hasNext()) {
                return false;
            }
            RealConnection next = it.next();
            if (!z || next.isMultiplexed()) {
                if (next.n.size() < next.m && !next.i) {
                    Internal internal = Internal.instance;
                    Address address2 = next.a.address();
                    if (((OkHttpClient.a) internal) != null) {
                        if (address2.a(address)) {
                            if (address.url().host().equals(next.route().address().url().host())) {
                                z3 = true;
                            } else if (next.f != null && list != null) {
                                int size = list.size();
                                int i = 0;
                                while (true) {
                                    if (i >= size) {
                                        z2 = false;
                                        break;
                                    }
                                    Route route = list.get(i);
                                    if (route.proxy().type() == Proxy.Type.DIRECT && next.a.proxy().type() == Proxy.Type.DIRECT && next.a.socketAddress().equals(route.socketAddress())) {
                                        z2 = true;
                                        break;
                                    }
                                    i++;
                                }
                                if (z2 && address.hostnameVerifier() == OkHostnameVerifier.INSTANCE && next.supportsUrl(address.url())) {
                                    try {
                                        address.certificatePinner().check(address.url().host(), next.handshake().peerCertificates());
                                        z3 = true;
                                    } catch (SSLPeerUnverifiedException unused) {
                                    }
                                }
                            }
                        }
                    } else {
                        throw null;
                    }
                }
                if (z3) {
                    transmitter.a(next);
                    return true;
                }
            }
        }
    }

    public long a(long j) throws IOException {
        synchronized (this) {
            RealConnection realConnection = null;
            long j2 = Long.MIN_VALUE;
            int i = 0;
            int i2 = 0;
            for (RealConnection realConnection2 : this.d) {
                if (a(realConnection2, j) > 0) {
                    i2++;
                } else {
                    i++;
                    long j3 = j - realConnection2.o;
                    if (j3 > j2) {
                        realConnection = realConnection2;
                        j2 = j3;
                    }
                }
            }
            if (j2 < this.b && i <= this.a) {
                if (i > 0) {
                    return this.b - j2;
                }
                if (i2 > 0) {
                    return this.b;
                }
                this.f = false;
                return -1L;
            }
            this.d.remove(realConnection);
            Util.closeQuietly(realConnection.socket());
            return 0L;
        }
    }

    public final int a(RealConnection realConnection, long j) {
        List<Reference<Transmitter>> list = realConnection.n;
        int i = 0;
        while (i < list.size()) {
            Reference<Transmitter> reference = list.get(i);
            if (reference.get() != null) {
                i++;
            } else {
                StringBuilder sbA = g9.a("A connection to ");
                sbA.append(realConnection.route().address().url());
                sbA.append(" was leaked. Did you forget to close a response body?");
                Platform.get().logCloseableLeak(sbA.toString(), ((Transmitter.b) reference).a);
                list.remove(i);
                realConnection.i = true;
                if (list.isEmpty()) {
                    realConnection.o = j - this.b;
                    return 0;
                }
            }
        }
        return list.size();
    }
}
