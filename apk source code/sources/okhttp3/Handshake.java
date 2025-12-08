package okhttp3;

import defpackage.g9;
import java.io.IOException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
public final class Handshake {
    public final TlsVersion a;
    public final CipherSuite b;
    public final List<Certificate> c;
    public final List<Certificate> d;

    public Handshake(TlsVersion tlsVersion, CipherSuite cipherSuite, List<Certificate> list, List<Certificate> list2) {
        this.a = tlsVersion;
        this.b = cipherSuite;
        this.c = list;
        this.d = list2;
    }

    public static Handshake get(SSLSession sSLSession) throws IOException {
        Certificate[] peerCertificates;
        String cipherSuite = sSLSession.getCipherSuite();
        if (cipherSuite == null) {
            throw new IllegalStateException("cipherSuite == null");
        }
        if ("SSL_NULL_WITH_NULL_NULL".equals(cipherSuite)) {
            throw new IOException("cipherSuite == SSL_NULL_WITH_NULL_NULL");
        }
        CipherSuite cipherSuiteForJavaName = CipherSuite.forJavaName(cipherSuite);
        String protocol = sSLSession.getProtocol();
        if (protocol == null) {
            throw new IllegalStateException("tlsVersion == null");
        }
        if ("NONE".equals(protocol)) {
            throw new IOException("tlsVersion == NONE");
        }
        TlsVersion tlsVersionForJavaName = TlsVersion.forJavaName(protocol);
        try {
            peerCertificates = sSLSession.getPeerCertificates();
        } catch (SSLPeerUnverifiedException unused) {
            peerCertificates = null;
        }
        List listImmutableList = peerCertificates != null ? Util.immutableList(peerCertificates) : Collections.emptyList();
        Certificate[] localCertificates = sSLSession.getLocalCertificates();
        return new Handshake(tlsVersionForJavaName, cipherSuiteForJavaName, listImmutableList, localCertificates != null ? Util.immutableList(localCertificates) : Collections.emptyList());
    }

    public final List<String> a(List<Certificate> list) {
        ArrayList arrayList = new ArrayList();
        for (Certificate certificate : list) {
            if (certificate instanceof X509Certificate) {
                arrayList.add(String.valueOf(((X509Certificate) certificate).getSubjectDN()));
            } else {
                arrayList.add(certificate.getType());
            }
        }
        return arrayList;
    }

    public CipherSuite cipherSuite() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Handshake)) {
            return false;
        }
        Handshake handshake = (Handshake) obj;
        return this.a.equals(handshake.a) && this.b.equals(handshake.b) && this.c.equals(handshake.c) && this.d.equals(handshake.d);
    }

    public int hashCode() {
        return this.d.hashCode() + ((this.c.hashCode() + ((this.b.hashCode() + ((this.a.hashCode() + 527) * 31)) * 31)) * 31);
    }

    public List<Certificate> localCertificates() {
        return this.d;
    }

    @Nullable
    public Principal localPrincipal() {
        if (this.d.isEmpty()) {
            return null;
        }
        return ((X509Certificate) this.d.get(0)).getSubjectX500Principal();
    }

    public List<Certificate> peerCertificates() {
        return this.c;
    }

    @Nullable
    public Principal peerPrincipal() {
        if (this.c.isEmpty()) {
            return null;
        }
        return ((X509Certificate) this.c.get(0)).getSubjectX500Principal();
    }

    public TlsVersion tlsVersion() {
        return this.a;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Handshake{tlsVersion=");
        sbA.append(this.a);
        sbA.append(" cipherSuite=");
        sbA.append(this.b);
        sbA.append(" peerCertificates=");
        sbA.append(a(this.c));
        sbA.append(" localCertificates=");
        sbA.append(a(this.d));
        sbA.append('}');
        return sbA.toString();
    }

    public static Handshake get(TlsVersion tlsVersion, CipherSuite cipherSuite, List<Certificate> list, List<Certificate> list2) {
        if (tlsVersion == null) {
            throw new NullPointerException("tlsVersion == null");
        }
        if (cipherSuite != null) {
            return new Handshake(tlsVersion, cipherSuite, Util.immutableList(list), Util.immutableList(list2));
        }
        throw new NullPointerException("cipherSuite == null");
    }
}
