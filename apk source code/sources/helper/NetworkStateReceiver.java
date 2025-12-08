package helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public class NetworkStateReceiver extends BroadcastReceiver {
    public Set<NetworkStateReceiverListener> listeners = new HashSet();
    public Boolean connected = null;

    public interface NetworkStateReceiverListener {
        void networkAvailable();

        void networkUnavailable();
    }

    public final void a(NetworkStateReceiverListener networkStateReceiverListener) {
        Boolean bool = this.connected;
        if (bool == null || networkStateReceiverListener == null) {
            return;
        }
        if (bool.booleanValue()) {
            networkStateReceiverListener.networkAvailable();
        } else {
            networkStateReceiverListener.networkUnavailable();
        }
    }

    public void addListener(NetworkStateReceiverListener networkStateReceiverListener) {
        this.listeners.add(networkStateReceiverListener);
        a(networkStateReceiverListener);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
            this.connected = true;
        } else if (intent.getBooleanExtra("noConnectivity", Boolean.FALSE.booleanValue())) {
            this.connected = false;
        }
        Iterator<NetworkStateReceiverListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            a(it.next());
        }
    }

    public void removeListener(NetworkStateReceiverListener networkStateReceiverListener) {
        this.listeners.remove(networkStateReceiverListener);
    }
}
