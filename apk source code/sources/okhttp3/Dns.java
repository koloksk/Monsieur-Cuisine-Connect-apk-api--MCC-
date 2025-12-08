package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/* loaded from: classes.dex */
public interface Dns {
    public static final Dns SYSTEM = new Dns() { // from class: wm
        @Override // okhttp3.Dns
        public final List lookup(String str) {
            return zm.a(str);
        }
    };

    List<InetAddress> lookup(String str) throws UnknownHostException;
}
