package okhttp3;

import defpackage.g9;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class Route {
    public final Address a;
    public final Proxy b;
    public final InetSocketAddress c;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress) {
        if (address == null) {
            throw new NullPointerException("address == null");
        }
        if (proxy == null) {
            throw new NullPointerException("proxy == null");
        }
        if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        }
        this.a = address;
        this.b = proxy;
        this.c = inetSocketAddress;
    }

    public Address address() {
        return this.a;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Route) {
            Route route = (Route) obj;
            if (route.a.equals(this.a) && route.b.equals(this.b) && route.c.equals(this.c)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.c.hashCode() + ((this.b.hashCode() + ((this.a.hashCode() + 527) * 31)) * 31);
    }

    public Proxy proxy() {
        return this.b;
    }

    public boolean requiresTunnel() {
        return this.a.i != null && this.b.type() == Proxy.Type.HTTP;
    }

    public InetSocketAddress socketAddress() {
        return this.c;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Route{");
        sbA.append(this.c);
        sbA.append("}");
        return sbA.toString();
    }
}
