package okhttp3;

import defpackage.g9;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
public final class Address {
    public final HttpUrl a;
    public final Dns b;
    public final SocketFactory c;
    public final Authenticator d;
    public final List<Protocol> e;
    public final List<ConnectionSpec> f;
    public final ProxySelector g;

    @Nullable
    public final Proxy h;

    @Nullable
    public final SSLSocketFactory i;

    @Nullable
    public final HostnameVerifier j;

    @Nullable
    public final CertificatePinner k;

    public Address(String str, int i, Dns dns, SocketFactory socketFactory, @Nullable SSLSocketFactory sSLSocketFactory, @Nullable HostnameVerifier hostnameVerifier, @Nullable CertificatePinner certificatePinner, Authenticator authenticator, @Nullable Proxy proxy, List<Protocol> list, List<ConnectionSpec> list2, ProxySelector proxySelector) {
        this.a = new HttpUrl.Builder().scheme(sSLSocketFactory != null ? "https" : "http").host(str).port(i).build();
        if (dns == null) {
            throw new NullPointerException("dns == null");
        }
        this.b = dns;
        if (socketFactory == null) {
            throw new NullPointerException("socketFactory == null");
        }
        this.c = socketFactory;
        if (authenticator == null) {
            throw new NullPointerException("proxyAuthenticator == null");
        }
        this.d = authenticator;
        if (list == null) {
            throw new NullPointerException("protocols == null");
        }
        this.e = Util.immutableList(list);
        if (list2 == null) {
            throw new NullPointerException("connectionSpecs == null");
        }
        this.f = Util.immutableList(list2);
        if (proxySelector == null) {
            throw new NullPointerException("proxySelector == null");
        }
        this.g = proxySelector;
        this.h = proxy;
        this.i = sSLSocketFactory;
        this.j = hostnameVerifier;
        this.k = certificatePinner;
    }

    public boolean a(Address address) {
        return this.b.equals(address.b) && this.d.equals(address.d) && this.e.equals(address.e) && this.f.equals(address.f) && this.g.equals(address.g) && Objects.equals(this.h, address.h) && Objects.equals(this.i, address.i) && Objects.equals(this.j, address.j) && Objects.equals(this.k, address.k) && url().port() == address.url().port();
    }

    @Nullable
    public CertificatePinner certificatePinner() {
        return this.k;
    }

    public List<ConnectionSpec> connectionSpecs() {
        return this.f;
    }

    public Dns dns() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Address) {
            Address address = (Address) obj;
            if (this.a.equals(address.a) && a(address)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.k) + ((Objects.hashCode(this.j) + ((Objects.hashCode(this.i) + ((Objects.hashCode(this.h) + ((this.g.hashCode() + ((this.f.hashCode() + ((this.e.hashCode() + ((this.d.hashCode() + ((this.b.hashCode() + ((this.a.hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31);
    }

    @Nullable
    public HostnameVerifier hostnameVerifier() {
        return this.j;
    }

    public List<Protocol> protocols() {
        return this.e;
    }

    @Nullable
    public Proxy proxy() {
        return this.h;
    }

    public Authenticator proxyAuthenticator() {
        return this.d;
    }

    public ProxySelector proxySelector() {
        return this.g;
    }

    public SocketFactory socketFactory() {
        return this.c;
    }

    @Nullable
    public SSLSocketFactory sslSocketFactory() {
        return this.i;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Address{");
        sbA.append(this.a.host());
        sbA.append(":");
        sbA.append(this.a.port());
        if (this.h != null) {
            sbA.append(", proxy=");
            sbA.append(this.h);
        } else {
            sbA.append(", proxySelector=");
            sbA.append(this.g);
        }
        sbA.append("}");
        return sbA.toString();
    }

    public HttpUrl url() {
        return this.a;
    }
}
