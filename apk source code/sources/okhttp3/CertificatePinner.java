package okhttp3;

import defpackage.g9;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.internal.tls.CertificateChainCleaner;
import okio.ByteString;

/* loaded from: classes.dex */
public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    public final Set<a> a;

    @Nullable
    public final CertificateChainCleaner b;

    public static final class Builder {
        public final List<a> a = new ArrayList();

        public Builder add(String str, String... strArr) {
            if (str == null) {
                throw new NullPointerException("pattern == null");
            }
            for (String str2 : strArr) {
                this.a.add(new a(str, str2));
            }
            return this;
        }

        public CertificatePinner build() {
            return new CertificatePinner(new LinkedHashSet(this.a), null);
        }
    }

    public static final class a {
        public final String a;
        public final String b;
        public final String c;
        public final ByteString d;

        public a(String str, String str2) {
            String strHost;
            this.a = str;
            if (str.startsWith("*.")) {
                StringBuilder sbA = g9.a("http://");
                sbA.append(str.substring(2));
                strHost = HttpUrl.get(sbA.toString()).host();
            } else {
                strHost = HttpUrl.get("http://" + str).host();
            }
            this.b = strHost;
            if (str2.startsWith("sha1/")) {
                this.c = "sha1/";
                this.d = ByteString.decodeBase64(str2.substring(5));
            } else {
                if (!str2.startsWith("sha256/")) {
                    throw new IllegalArgumentException(g9.b("pins must start with 'sha256/' or 'sha1/': ", str2));
                }
                this.c = "sha256/";
                this.d = ByteString.decodeBase64(str2.substring(7));
            }
            if (this.d == null) {
                throw new IllegalArgumentException(g9.b("pins must be base64: ", str2));
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof a) {
                a aVar = (a) obj;
                if (this.a.equals(aVar.a) && this.c.equals(aVar.c) && this.d.equals(aVar.d)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return this.d.hashCode() + ((this.c.hashCode() + ((this.a.hashCode() + 527) * 31)) * 31);
        }

        public String toString() {
            return this.c + this.d.base64();
        }
    }

    public CertificatePinner(Set<a> set, @Nullable CertificateChainCleaner certificateChainCleaner) {
        this.a = set;
        this.b = certificateChainCleaner;
    }

    public static String pin(Certificate certificate) {
        if (!(certificate instanceof X509Certificate)) {
            throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
        }
        StringBuilder sbA = g9.a("sha256/");
        sbA.append(ByteString.of(((X509Certificate) certificate).getPublicKey().getEncoded()).sha256().base64());
        return sbA.toString();
    }

    public void check(String str, Certificate... certificateArr) throws SSLPeerUnverifiedException {
        check(str, Arrays.asList(certificateArr));
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CertificatePinner) {
            CertificatePinner certificatePinner = (CertificatePinner) obj;
            if (Objects.equals(this.b, certificatePinner.b) && this.a.equals(certificatePinner.a)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() + (Objects.hashCode(this.b) * 31);
    }

    public void check(String str, List<Certificate> list) throws SSLPeerUnverifiedException {
        int i;
        List listEmptyList = Collections.emptyList();
        Iterator<a> it = this.a.iterator();
        List arrayList = listEmptyList;
        while (true) {
            zEquals = false;
            boolean zEquals = false;
            if (!it.hasNext()) {
                break;
            }
            a next = it.next();
            if (next.a.startsWith("*.")) {
                int iIndexOf = str.indexOf(46);
                if ((str.length() - iIndexOf) - 1 == next.b.length()) {
                    String str2 = next.b;
                    if (str.regionMatches(false, iIndexOf + 1, str2, 0, str2.length())) {
                        zEquals = true;
                    }
                }
            } else {
                zEquals = str.equals(next.b);
            }
            if (zEquals) {
                if (arrayList.isEmpty()) {
                    arrayList = new ArrayList();
                }
                arrayList.add(next);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        CertificateChainCleaner certificateChainCleaner = this.b;
        List<Certificate> listClean = certificateChainCleaner != null ? certificateChainCleaner.clean(list, str) : list;
        int size = listClean.size();
        for (int i2 = 0; i2 < size; i2++) {
            X509Certificate x509Certificate = (X509Certificate) listClean.get(i2);
            int size2 = arrayList.size();
            ByteString byteStringSha256 = null;
            ByteString byteStringSha1 = null;
            for (int i3 = 0; i3 < size2; i3++) {
                a aVar = (a) arrayList.get(i3);
                if (aVar.c.equals("sha256/")) {
                    if (byteStringSha256 == null) {
                        byteStringSha256 = ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha256();
                    }
                    if (aVar.d.equals(byteStringSha256)) {
                        return;
                    }
                } else {
                    if (!aVar.c.equals("sha1/")) {
                        StringBuilder sbA = g9.a("unsupported hashAlgorithm: ");
                        sbA.append(aVar.c);
                        throw new AssertionError(sbA.toString());
                    }
                    if (byteStringSha1 == null) {
                        byteStringSha1 = ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha1();
                    }
                    if (aVar.d.equals(byteStringSha1)) {
                        return;
                    }
                }
            }
        }
        StringBuilder sbA2 = g9.a("Certificate pinning failure!", "\n  Peer certificate chain:");
        int size3 = listClean.size();
        for (int i4 = 0; i4 < size3; i4++) {
            X509Certificate x509Certificate2 = (X509Certificate) listClean.get(i4);
            sbA2.append("\n    ");
            sbA2.append(pin(x509Certificate2));
            sbA2.append(": ");
            sbA2.append(x509Certificate2.getSubjectDN().getName());
        }
        sbA2.append("\n  Pinned certificates for ");
        sbA2.append(str);
        sbA2.append(":");
        int size4 = arrayList.size();
        for (i = 0; i < size4; i++) {
            a aVar2 = (a) arrayList.get(i);
            sbA2.append("\n    ");
            sbA2.append(aVar2);
        }
        throw new SSLPeerUnverifiedException(sbA2.toString());
    }
}
