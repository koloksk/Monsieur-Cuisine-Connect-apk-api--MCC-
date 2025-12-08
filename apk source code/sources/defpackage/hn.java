package defpackage;

import java.io.IOException;
import java.net.UnknownServiceException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
public final class hn {
    public final List<ConnectionSpec> a;
    public int b = 0;
    public boolean c;
    public boolean d;

    public hn(List<ConnectionSpec> list) {
        this.a = list;
    }

    public ConnectionSpec a(SSLSocket sSLSocket) throws IOException {
        boolean z;
        ConnectionSpec connectionSpec;
        int i = this.b;
        int size = this.a.size();
        while (true) {
            z = true;
            if (i >= size) {
                connectionSpec = null;
                break;
            }
            connectionSpec = this.a.get(i);
            if (connectionSpec.isCompatible(sSLSocket)) {
                this.b = i + 1;
                break;
            }
            i++;
        }
        if (connectionSpec == null) {
            StringBuilder sbA = g9.a("Unable to find acceptable protocols. isFallback=");
            sbA.append(this.d);
            sbA.append(", modes=");
            sbA.append(this.a);
            sbA.append(", supported protocols=");
            sbA.append(Arrays.toString(sSLSocket.getEnabledProtocols()));
            throw new UnknownServiceException(sbA.toString());
        }
        int i2 = this.b;
        while (true) {
            if (i2 >= this.a.size()) {
                z = false;
                break;
            }
            if (this.a.get(i2).isCompatible(sSLSocket)) {
                break;
            }
            i2++;
        }
        this.c = z;
        Internal internal = Internal.instance;
        boolean z2 = this.d;
        if (((OkHttpClient.a) internal) == null) {
            throw null;
        }
        String[] strArrIntersect = connectionSpec.c != null ? Util.intersect(CipherSuite.b, sSLSocket.getEnabledCipherSuites(), connectionSpec.c) : sSLSocket.getEnabledCipherSuites();
        String[] strArrIntersect2 = connectionSpec.d != null ? Util.intersect(Util.NATURAL_ORDER, sSLSocket.getEnabledProtocols(), connectionSpec.d) : sSLSocket.getEnabledProtocols();
        String[] supportedCipherSuites = sSLSocket.getSupportedCipherSuites();
        int iIndexOf = Util.indexOf(CipherSuite.b, supportedCipherSuites, "TLS_FALLBACK_SCSV");
        if (z2 && iIndexOf != -1) {
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
        return connectionSpec;
    }
}
