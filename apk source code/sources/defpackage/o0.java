package defpackage;

import activity.MainActivity;
import android.content.Intent;
import android.util.Log;
import helper.SharedPreferencesHelper;
import machineAdapter.adapter.MachineCallbackAdapter;

/* loaded from: classes.dex */
public class o0 extends MachineCallbackAdapter {
    public final /* synthetic */ MainActivity a;

    public o0(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public /* synthetic */ void a() {
        this.a.a(true);
    }

    @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
    public void onJogDialPushed(int i) {
        Log.i(MainActivity.w, "onJogDialPushed >> state " + i);
        if (this.a.u < 0 && this.a.t && i == 0) {
            this.a.u = System.currentTimeMillis();
        } else if (this.a.t) {
            if (i == 1) {
                this.a.t = false;
            }
            boolean z = System.currentTimeMillis() - this.a.u > 5000;
            SharedPreferencesHelper.getInstance().setJogDialPushedAtBooting(z);
            Log.i(MainActivity.w, "Jog Dial pushed during boot " + z);
            if (z) {
                MainActivity mainActivity = this.a;
                if (mainActivity == null) {
                    throw null;
                }
                Log.i(MainActivity.w, "Trigger FOTA Update");
                Intent intent = new Intent();
                intent.setAction("action:fota.msg.install_other_image");
                intent.addFlags(32);
                mainActivity.getApplicationContext().sendBroadcast(intent, "permission.fota_update");
            }
        }
        if (this.a.p && i == 0) {
            this.a.runOnUiThread(new Runnable() { // from class: c
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.a();
                }
            });
        }
    }
}
