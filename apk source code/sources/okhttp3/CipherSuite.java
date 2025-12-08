package okhttp3;

import defpackage.g9;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import okhttp3.CipherSuite;

/* loaded from: classes.dex */
public final class CipherSuite {
    public final String a;
    public static final Comparator<String> b = new Comparator() { // from class: vm
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return CipherSuite.a((String) obj, (String) obj2);
        }
    };
    public static final Map<String, CipherSuite> c = new LinkedHashMap();
    public static final CipherSuite TLS_RSA_WITH_NULL_MD5 = a("SSL_RSA_WITH_NULL_MD5");
    public static final CipherSuite TLS_RSA_WITH_NULL_SHA = a("SSL_RSA_WITH_NULL_SHA");
    public static final CipherSuite TLS_RSA_EXPORT_WITH_RC4_40_MD5 = a("SSL_RSA_EXPORT_WITH_RC4_40_MD5");
    public static final CipherSuite TLS_RSA_WITH_RC4_128_MD5 = a("SSL_RSA_WITH_RC4_128_MD5");
    public static final CipherSuite TLS_RSA_WITH_RC4_128_SHA = a("SSL_RSA_WITH_RC4_128_SHA");
    public static final CipherSuite TLS_RSA_EXPORT_WITH_DES40_CBC_SHA = a("SSL_RSA_EXPORT_WITH_DES40_CBC_SHA");
    public static final CipherSuite TLS_RSA_WITH_DES_CBC_SHA = a("SSL_RSA_WITH_DES_CBC_SHA");
    public static final CipherSuite TLS_RSA_WITH_3DES_EDE_CBC_SHA = a("SSL_RSA_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA = a("SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");
    public static final CipherSuite TLS_DHE_DSS_WITH_DES_CBC_SHA = a("SSL_DHE_DSS_WITH_DES_CBC_SHA");
    public static final CipherSuite TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA = a("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA = a("SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA");
    public static final CipherSuite TLS_DHE_RSA_WITH_DES_CBC_SHA = a("SSL_DHE_RSA_WITH_DES_CBC_SHA");
    public static final CipherSuite TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA = a("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_DH_anon_EXPORT_WITH_RC4_40_MD5 = a("SSL_DH_anon_EXPORT_WITH_RC4_40_MD5");
    public static final CipherSuite TLS_DH_anon_WITH_RC4_128_MD5 = a("SSL_DH_anon_WITH_RC4_128_MD5");
    public static final CipherSuite TLS_DH_anon_EXPORT_WITH_DES40_CBC_SHA = a("SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA");
    public static final CipherSuite TLS_DH_anon_WITH_DES_CBC_SHA = a("SSL_DH_anon_WITH_DES_CBC_SHA");
    public static final CipherSuite TLS_DH_anon_WITH_3DES_EDE_CBC_SHA = a("SSL_DH_anon_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_KRB5_WITH_DES_CBC_SHA = a("TLS_KRB5_WITH_DES_CBC_SHA");
    public static final CipherSuite TLS_KRB5_WITH_3DES_EDE_CBC_SHA = a("TLS_KRB5_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_KRB5_WITH_RC4_128_SHA = a("TLS_KRB5_WITH_RC4_128_SHA");
    public static final CipherSuite TLS_KRB5_WITH_DES_CBC_MD5 = a("TLS_KRB5_WITH_DES_CBC_MD5");
    public static final CipherSuite TLS_KRB5_WITH_3DES_EDE_CBC_MD5 = a("TLS_KRB5_WITH_3DES_EDE_CBC_MD5");
    public static final CipherSuite TLS_KRB5_WITH_RC4_128_MD5 = a("TLS_KRB5_WITH_RC4_128_MD5");
    public static final CipherSuite TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA = a("TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA");
    public static final CipherSuite TLS_KRB5_EXPORT_WITH_RC4_40_SHA = a("TLS_KRB5_EXPORT_WITH_RC4_40_SHA");
    public static final CipherSuite TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5 = a("TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5");
    public static final CipherSuite TLS_KRB5_EXPORT_WITH_RC4_40_MD5 = a("TLS_KRB5_EXPORT_WITH_RC4_40_MD5");
    public static final CipherSuite TLS_RSA_WITH_AES_128_CBC_SHA = a("TLS_RSA_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_128_CBC_SHA = a("TLS_DHE_DSS_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_128_CBC_SHA = a("TLS_DHE_RSA_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_DH_anon_WITH_AES_128_CBC_SHA = a("TLS_DH_anon_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_RSA_WITH_AES_256_CBC_SHA = a("TLS_RSA_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_256_CBC_SHA = a("TLS_DHE_DSS_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_256_CBC_SHA = a("TLS_DHE_RSA_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_DH_anon_WITH_AES_256_CBC_SHA = a("TLS_DH_anon_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_RSA_WITH_NULL_SHA256 = a("TLS_RSA_WITH_NULL_SHA256");
    public static final CipherSuite TLS_RSA_WITH_AES_128_CBC_SHA256 = a("TLS_RSA_WITH_AES_128_CBC_SHA256");
    public static final CipherSuite TLS_RSA_WITH_AES_256_CBC_SHA256 = a("TLS_RSA_WITH_AES_256_CBC_SHA256");
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_128_CBC_SHA256 = a("TLS_DHE_DSS_WITH_AES_128_CBC_SHA256");
    public static final CipherSuite TLS_RSA_WITH_CAMELLIA_128_CBC_SHA = a("TLS_RSA_WITH_CAMELLIA_128_CBC_SHA");
    public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA = a("TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA");
    public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA = a("TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA");
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_128_CBC_SHA256 = a("TLS_DHE_RSA_WITH_AES_128_CBC_SHA256");
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_256_CBC_SHA256 = a("TLS_DHE_DSS_WITH_AES_256_CBC_SHA256");
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_256_CBC_SHA256 = a("TLS_DHE_RSA_WITH_AES_256_CBC_SHA256");
    public static final CipherSuite TLS_DH_anon_WITH_AES_128_CBC_SHA256 = a("TLS_DH_anon_WITH_AES_128_CBC_SHA256");
    public static final CipherSuite TLS_DH_anon_WITH_AES_256_CBC_SHA256 = a("TLS_DH_anon_WITH_AES_256_CBC_SHA256");
    public static final CipherSuite TLS_RSA_WITH_CAMELLIA_256_CBC_SHA = a("TLS_RSA_WITH_CAMELLIA_256_CBC_SHA");
    public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA = a("TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA");
    public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA = a("TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA");
    public static final CipherSuite TLS_PSK_WITH_RC4_128_SHA = a("TLS_PSK_WITH_RC4_128_SHA");
    public static final CipherSuite TLS_PSK_WITH_3DES_EDE_CBC_SHA = a("TLS_PSK_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_PSK_WITH_AES_128_CBC_SHA = a("TLS_PSK_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_PSK_WITH_AES_256_CBC_SHA = a("TLS_PSK_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_RSA_WITH_SEED_CBC_SHA = a("TLS_RSA_WITH_SEED_CBC_SHA");
    public static final CipherSuite TLS_RSA_WITH_AES_128_GCM_SHA256 = a("TLS_RSA_WITH_AES_128_GCM_SHA256");
    public static final CipherSuite TLS_RSA_WITH_AES_256_GCM_SHA384 = a("TLS_RSA_WITH_AES_256_GCM_SHA384");
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_128_GCM_SHA256 = a("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256");
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_256_GCM_SHA384 = a("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384");
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_128_GCM_SHA256 = a("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256");
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_256_GCM_SHA384 = a("TLS_DHE_DSS_WITH_AES_256_GCM_SHA384");
    public static final CipherSuite TLS_DH_anon_WITH_AES_128_GCM_SHA256 = a("TLS_DH_anon_WITH_AES_128_GCM_SHA256");
    public static final CipherSuite TLS_DH_anon_WITH_AES_256_GCM_SHA384 = a("TLS_DH_anon_WITH_AES_256_GCM_SHA384");
    public static final CipherSuite TLS_EMPTY_RENEGOTIATION_INFO_SCSV = a("TLS_EMPTY_RENEGOTIATION_INFO_SCSV");
    public static final CipherSuite TLS_FALLBACK_SCSV = a("TLS_FALLBACK_SCSV");
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_NULL_SHA = a("TLS_ECDH_ECDSA_WITH_NULL_SHA");
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_RC4_128_SHA = a("TLS_ECDH_ECDSA_WITH_RC4_128_SHA");
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA = a("TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA = a("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA = a("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_NULL_SHA = a("TLS_ECDHE_ECDSA_WITH_NULL_SHA");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_RC4_128_SHA = a("TLS_ECDHE_ECDSA_WITH_RC4_128_SHA");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA = a("TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA = a("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA = a("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_ECDH_RSA_WITH_NULL_SHA = a("TLS_ECDH_RSA_WITH_NULL_SHA");
    public static final CipherSuite TLS_ECDH_RSA_WITH_RC4_128_SHA = a("TLS_ECDH_RSA_WITH_RC4_128_SHA");
    public static final CipherSuite TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA = a("TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_128_CBC_SHA = a("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_256_CBC_SHA = a("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_NULL_SHA = a("TLS_ECDHE_RSA_WITH_NULL_SHA");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_RC4_128_SHA = a("TLS_ECDHE_RSA_WITH_RC4_128_SHA");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA = a("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA = a("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA = a("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_ECDH_anon_WITH_NULL_SHA = a("TLS_ECDH_anon_WITH_NULL_SHA");
    public static final CipherSuite TLS_ECDH_anon_WITH_RC4_128_SHA = a("TLS_ECDH_anon_WITH_RC4_128_SHA");
    public static final CipherSuite TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA = a("TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA");
    public static final CipherSuite TLS_ECDH_anon_WITH_AES_128_CBC_SHA = a("TLS_ECDH_anon_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_ECDH_anon_WITH_AES_256_CBC_SHA = a("TLS_ECDH_anon_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 = a("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384 = a("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384");
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256 = a("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256");
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384 = a("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256 = a("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384 = a("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384");
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256 = a("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256");
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384 = a("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 = a("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 = a("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384");
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256 = a("TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256");
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384 = a("TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 = a("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 = a("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384");
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256 = a("TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256");
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384 = a("TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384");
    public static final CipherSuite TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA = a("TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA");
    public static final CipherSuite TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA = a("TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA");
    public static final CipherSuite TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 = a("TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256");
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 = a("TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256");
    public static final CipherSuite TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256 = a("TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256");
    public static final CipherSuite TLS_ECDHE_PSK_WITH_CHACHA20_POLY1305_SHA256 = a("TLS_ECDHE_PSK_WITH_CHACHA20_POLY1305_SHA256");
    public static final CipherSuite TLS_AES_128_GCM_SHA256 = a("TLS_AES_128_GCM_SHA256");
    public static final CipherSuite TLS_AES_256_GCM_SHA384 = a("TLS_AES_256_GCM_SHA384");
    public static final CipherSuite TLS_CHACHA20_POLY1305_SHA256 = a("TLS_CHACHA20_POLY1305_SHA256");
    public static final CipherSuite TLS_AES_128_CCM_SHA256 = a("TLS_AES_128_CCM_SHA256");
    public static final CipherSuite TLS_AES_128_CCM_8_SHA256 = a("TLS_AES_128_CCM_8_SHA256");

    public CipherSuite(String str) {
        if (str == null) {
            throw null;
        }
        this.a = str;
    }

    public static /* synthetic */ int a(String str, String str2) {
        int iMin = Math.min(str.length(), str2.length());
        for (int i = 4; i < iMin; i++) {
            char cCharAt = str.charAt(i);
            char cCharAt2 = str2.charAt(i);
            if (cCharAt != cCharAt2) {
                return cCharAt < cCharAt2 ? -1 : 1;
            }
        }
        int length = str.length();
        int length2 = str2.length();
        if (length != length2) {
            return length < length2 ? -1 : 1;
        }
        return 0;
    }

    public static String b(String str) {
        if (str.startsWith("TLS_")) {
            StringBuilder sbA = g9.a("SSL_");
            sbA.append(str.substring(4));
            return sbA.toString();
        }
        if (!str.startsWith("SSL_")) {
            return str;
        }
        StringBuilder sbA2 = g9.a("TLS_");
        sbA2.append(str.substring(4));
        return sbA2.toString();
    }

    public static synchronized CipherSuite forJavaName(String str) {
        CipherSuite cipherSuite;
        cipherSuite = c.get(str);
        if (cipherSuite == null) {
            cipherSuite = c.get(b(str));
            if (cipherSuite == null) {
                cipherSuite = new CipherSuite(str);
            }
            c.put(str, cipherSuite);
        }
        return cipherSuite;
    }

    public String javaName() {
        return this.a;
    }

    public String toString() {
        return this.a;
    }

    public static CipherSuite a(String str) {
        CipherSuite cipherSuite = new CipherSuite(str);
        c.put(str, cipherSuite);
        return cipherSuite;
    }
}
