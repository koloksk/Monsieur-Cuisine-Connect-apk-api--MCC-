package defpackage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RealConnectionPool;
import okhttp3.internal.connection.RouteSelector;
import okhttp3.internal.connection.Transmitter;

/* loaded from: classes.dex */
public final class in {
    public final Transmitter a;
    public final Address b;
    public final RealConnectionPool c;
    public final Call d;
    public final EventListener e;
    public RouteSelector.Selection f;
    public final RouteSelector g;
    public RealConnection h;
    public boolean i;
    public Route j;

    public in(Transmitter transmitter, RealConnectionPool realConnectionPool, Address address, Call call, EventListener eventListener) {
        this.a = transmitter;
        this.c = realConnectionPool;
        this.b = address;
        this.d = call;
        this.e = eventListener;
        this.g = new RouteSelector(address, realConnectionPool.e, call, eventListener);
    }

    public final RealConnection a(int i, int i2, int i3, int i4, boolean z, boolean z2) throws Throwable {
        while (true) {
            RealConnection realConnectionA = a(i, i2, i3, i4, z);
            synchronized (this.c) {
                if (realConnectionA.k == 0 && !realConnectionA.isMultiplexed()) {
                    return realConnectionA;
                }
                if (realConnectionA.isHealthy(z2)) {
                    return realConnectionA;
                }
                realConnectionA.noNewExchanges();
            }
        }
    }

    public boolean b() {
        boolean z;
        synchronized (this.c) {
            z = this.i;
        }
        return z;
    }

    public final boolean c() {
        RealConnection realConnection = this.a.connection;
        return realConnection != null && realConnection.j == 0 && Util.sameConnection(realConnection.route().address().url(), this.b.url());
    }

    public void d() {
        synchronized (this.c) {
            this.i = true;
        }
    }

    public final RealConnection a(int i, int i2, int i3, int i4, boolean z) throws Throwable {
        Socket socket;
        Socket socketA;
        RealConnection realConnection;
        RealConnection realConnection2;
        int i5;
        boolean z2;
        Route next;
        boolean z3;
        List<Route> all;
        RouteSelector.Selection selection;
        String strHost;
        int iPort;
        synchronized (this.c) {
            if (!this.a.isCanceled()) {
                this.i = false;
                RealConnection realConnection3 = this.a.connection;
                socket = null;
                socketA = (this.a.connection == null || !this.a.connection.i) ? null : this.a.a();
                if (this.a.connection != null) {
                    realConnection2 = this.a.connection;
                    realConnection = null;
                } else {
                    realConnection = realConnection3;
                    realConnection2 = null;
                }
                i5 = 1;
                if (realConnection2 != null) {
                    z2 = false;
                    next = null;
                } else if (this.c.a(this.b, this.a, null, false)) {
                    realConnection2 = this.a.connection;
                    next = null;
                    z2 = true;
                } else {
                    if (this.j != null) {
                        next = this.j;
                        this.j = null;
                    } else {
                        if (c()) {
                            next = this.a.connection.route();
                        }
                        z2 = false;
                        next = null;
                    }
                    z2 = false;
                }
            } else {
                throw new IOException("Canceled");
            }
        }
        Util.closeQuietly(socketA);
        if (realConnection != null) {
            this.e.connectionReleased(this.d, realConnection);
        }
        if (z2) {
            this.e.connectionAcquired(this.d, realConnection2);
        }
        if (realConnection2 != null) {
            return realConnection2;
        }
        if (next != null || ((selection = this.f) != null && selection.hasNext())) {
            z3 = false;
        } else {
            RouteSelector routeSelector = this.g;
            if (routeSelector.a()) {
                ArrayList arrayList = new ArrayList();
                while (routeSelector.b()) {
                    if (routeSelector.b()) {
                        List<Proxy> list = routeSelector.e;
                        int i6 = routeSelector.f;
                        routeSelector.f = i6 + 1;
                        Proxy proxy = list.get(i6);
                        routeSelector.g = new ArrayList();
                        if (proxy.type() != Proxy.Type.DIRECT && proxy.type() != Proxy.Type.SOCKS) {
                            SocketAddress socketAddressAddress = proxy.address();
                            if (socketAddressAddress instanceof InetSocketAddress) {
                                InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddressAddress;
                                InetAddress address = inetSocketAddress.getAddress();
                                if (address == null) {
                                    strHost = inetSocketAddress.getHostName();
                                } else {
                                    strHost = address.getHostAddress();
                                }
                                iPort = inetSocketAddress.getPort();
                            } else {
                                StringBuilder sbA = g9.a("Proxy.address() is not an InetSocketAddress: ");
                                sbA.append(socketAddressAddress.getClass());
                                throw new IllegalArgumentException(sbA.toString());
                            }
                        } else {
                            strHost = routeSelector.a.url().host();
                            iPort = routeSelector.a.url().port();
                        }
                        if (iPort >= i5 && iPort <= 65535) {
                            if (proxy.type() == Proxy.Type.SOCKS) {
                                routeSelector.g.add(InetSocketAddress.createUnresolved(strHost, iPort));
                            } else {
                                routeSelector.d.dnsStart(routeSelector.c, strHost);
                                List<InetAddress> listLookup = routeSelector.a.dns().lookup(strHost);
                                if (!listLookup.isEmpty()) {
                                    routeSelector.d.dnsEnd(routeSelector.c, strHost, listLookup);
                                    int size = listLookup.size();
                                    for (int i7 = 0; i7 < size; i7++) {
                                        routeSelector.g.add(new InetSocketAddress(listLookup.get(i7), iPort));
                                    }
                                } else {
                                    throw new UnknownHostException(routeSelector.a.dns() + " returned no addresses for " + strHost);
                                }
                            }
                            int size2 = routeSelector.g.size();
                            for (int i8 = 0; i8 < size2; i8++) {
                                Route route = new Route(routeSelector.a, proxy, routeSelector.g.get(i8));
                                if (routeSelector.b.c(route)) {
                                    routeSelector.h.add(route);
                                } else {
                                    arrayList.add(route);
                                }
                            }
                            if (!arrayList.isEmpty()) {
                                break;
                            }
                            i5 = 1;
                        } else {
                            throw new SocketException("No route to " + strHost + ":" + iPort + "; port is out of range");
                        }
                    } else {
                        StringBuilder sbA2 = g9.a("No route to ");
                        sbA2.append(routeSelector.a.url().host());
                        sbA2.append("; exhausted proxy configurations: ");
                        sbA2.append(routeSelector.e);
                        throw new SocketException(sbA2.toString());
                    }
                }
                if (arrayList.isEmpty()) {
                    arrayList.addAll(routeSelector.h);
                    routeSelector.h.clear();
                }
                this.f = new RouteSelector.Selection(arrayList);
                z3 = true;
            } else {
                throw new NoSuchElementException();
            }
        }
        synchronized (this.c) {
            if (this.a.isCanceled()) {
                throw new IOException("Canceled");
            }
            if (z3) {
                all = this.f.getAll();
                if (this.c.a(this.b, this.a, all, false)) {
                    realConnection2 = this.a.connection;
                    z2 = true;
                }
            } else {
                all = null;
            }
            if (!z2) {
                if (next == null) {
                    next = this.f.next();
                }
                realConnection2 = new RealConnection(this.c, next);
                this.h = realConnection2;
            }
        }
        if (z2) {
            this.e.connectionAcquired(this.d, realConnection2);
            return realConnection2;
        }
        realConnection2.connect(i, i2, i3, i4, z, this.d, this.e);
        this.c.e.a(realConnection2.route());
        synchronized (this.c) {
            this.h = null;
            if (this.c.a(this.b, this.a, all, true)) {
                realConnection2.i = true;
                socket = realConnection2.socket();
                realConnection2 = this.a.connection;
                this.j = next;
            } else {
                RealConnectionPool realConnectionPool = this.c;
                if (!realConnectionPool.f) {
                    realConnectionPool.f = true;
                    RealConnectionPool.g.execute(realConnectionPool.c);
                }
                realConnectionPool.d.add(realConnection2);
                this.a.a(realConnection2);
            }
        }
        Util.closeQuietly(socket);
        this.e.connectionAcquired(this.d, realConnection2);
        return realConnection2;
    }

    public boolean a() {
        synchronized (this.c) {
            boolean z = true;
            if (this.j != null) {
                return true;
            }
            if (c()) {
                this.j = this.a.connection.route();
                return true;
            }
            if ((this.f == null || !this.f.hasNext()) && !this.g.a()) {
                z = false;
            }
            return z;
        }
    }
}
