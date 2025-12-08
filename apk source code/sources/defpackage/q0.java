package defpackage;

import activity.MainActivity;
import helper.NetworkStateReceiver;
import org.json.JSONException;

/* loaded from: classes.dex */
public class q0 implements NetworkStateReceiver.NetworkStateReceiverListener {
    public final /* synthetic */ MainActivity a;

    public q0(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    @Override // helper.NetworkStateReceiver.NetworkStateReceiverListener
    public void networkAvailable() throws JSONException {
        MainActivity.a(this.a);
    }

    @Override // helper.NetworkStateReceiver.NetworkStateReceiverListener
    public void networkUnavailable() {
    }
}
