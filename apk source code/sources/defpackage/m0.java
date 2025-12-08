package defpackage;

import activity.MainActivity;
import android.util.Log;
import de.silpion.mcupdater.UpdateServiceAdapter;

/* loaded from: classes.dex */
public class m0 extends UpdateServiceAdapter.StateListenerAdapter {
    public final /* synthetic */ n0 a;

    public m0(n0 n0Var) {
        this.a = n0Var;
    }

    @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListenerAdapter, de.silpion.mcupdater.UpdateServiceAdapter.StateListener
    public void idle() {
        super.idle();
        UpdateServiceAdapter.getInstance().removeStateListener(this);
        Log.v(MainActivity.w, "timeout dialog >>>>> sleep (idle -- probably no update).");
        MainActivity.a(this.a.b, true);
    }
}
