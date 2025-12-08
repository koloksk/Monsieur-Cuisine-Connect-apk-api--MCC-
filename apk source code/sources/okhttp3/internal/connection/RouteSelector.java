package okhttp3.internal.connection;

import defpackage.kn;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Route;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
public final class RouteSelector {
    public final Address a;
    public final kn b;
    public final Call c;
    public final EventListener d;
    public List<Proxy> e;
    public int f;
    public List<InetSocketAddress> g = Collections.emptyList();
    public final List<Route> h = new ArrayList();

    public static final class Selection {
        public final List<Route> a;
        public int b = 0;

        public Selection(List<Route> list) {
            this.a = list;
        }

        public List<Route> getAll() {
            return new ArrayList(this.a);
        }

        public boolean hasNext() {
            return this.b < this.a.size();
        }

        public Route next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            List<Route> list = this.a;
            int i = this.b;
            this.b = i + 1;
            return list.get(i);
        }
    }

    public RouteSelector(Address address, kn knVar, Call call, EventListener eventListener) {
        this.e = Collections.emptyList();
        this.a = address;
        this.b = knVar;
        this.c = call;
        this.d = eventListener;
        HttpUrl httpUrlUrl = address.url();
        Proxy proxy = address.proxy();
        if (proxy != null) {
            this.e = Collections.singletonList(proxy);
        } else {
            List<Proxy> listSelect = this.a.proxySelector().select(httpUrlUrl.uri());
            this.e = (listSelect == null || listSelect.isEmpty()) ? Util.immutableList(Proxy.NO_PROXY) : Util.immutableList(listSelect);
        }
        this.f = 0;
    }

    public boolean a() {
        return b() || !this.h.isEmpty();
    }

    public final boolean b() {
        return this.f < this.e.size();
    }
}
