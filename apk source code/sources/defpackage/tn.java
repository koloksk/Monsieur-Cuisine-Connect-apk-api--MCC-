package defpackage;

import android.annotation.SuppressLint;
import android.net.ssl.SSLSockets;
import android.os.Build;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;
import okhttp3.internal.platform.Platform;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

@SuppressLint({"NewApi"})
/* loaded from: classes.dex */
public class tn extends un {
    public tn(Class<?> cls) {
        super(cls, null, null, null, null, null);
    }

    @Nullable
    public static Platform buildIfSupported() {
        int i;
        if (!Platform.isAndroid()) {
            return null;
        }
        try {
            try {
                i = Build.VERSION.SDK_INT;
            } catch (NoClassDefFoundError unused) {
                i = 0;
            }
            if (i >= 29) {
                return new tn(Class.forName("com.android.org.conscrypt.SSLParametersImpl"));
            }
        } catch (ReflectiveOperationException unused2) {
        }
        return null;
    }

    public final void a(SSLSocket sSLSocket) {
        if (SSLSockets.isSupportedSocket(sSLSocket)) {
            SSLSockets.setUseSessionTickets(sSLSocket, true);
        }
    }

    @Override // defpackage.un, okhttp3.internal.platform.Platform
    @SuppressLint({"NewApi"})
    @IgnoreJRERequirement
    public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) throws IOException {
        try {
            a(sSLSocket);
            SSLParameters sSLParameters = sSLSocket.getSSLParameters();
            sSLParameters.setApplicationProtocols((String[]) Platform.alpnProtocolNames(list).toArray(new String[0]));
            sSLSocket.setSSLParameters(sSLParameters);
        } catch (IllegalArgumentException e) {
            throw new IOException("Android internal error", e);
        }
    }

    @Override // defpackage.un, okhttp3.internal.platform.Platform
    @Nullable
    @IgnoreJRERequirement
    public String getSelectedProtocol(SSLSocket sSLSocket) {
        String applicationProtocol = sSLSocket.getApplicationProtocol();
        if (applicationProtocol == null || applicationProtocol.isEmpty()) {
            return null;
        }
        return applicationProtocol;
    }
}
