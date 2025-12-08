package defpackage;

import activity.MainActivity;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import cooking.CookingManager;
import de.silpion.mc2.R;
import de.silpion.mcupdater.IUpdateService;
import de.silpion.mcupdater.UpdateServiceAdapter;
import helper.LayoutHelper;
import java.util.Timer;
import java.util.TimerTask;
import machineAdapter.impl.service.HardwareLEDService;

/* loaded from: classes.dex */
public class n0 extends TimerTask {
    public final /* synthetic */ TextView a;
    public final /* synthetic */ MainActivity b;

    public n0(MainActivity mainActivity, TextView textView) {
        this.b = mainActivity;
        this.a = textView;
    }

    public /* synthetic */ void a(TextView textView) {
        MainActivity mainActivity = this.b;
        int[] iArr = mainActivity.k;
        if (iArr[0] > 0) {
            String string = mainActivity.getString(R.string.dialog_user_timeout_seconds, new Object[]{String.valueOf(iArr[0])});
            int[] iArr2 = this.b.k;
            iArr2[0] = iArr2[0] - 1;
            textView.setText(string);
            return;
        }
        if (CookingManager.getInstance().getState() == 1) {
            CookingManager.getInstance().pauseCooking();
        }
        Timer timer2 = this.b.s;
        if (timer2 != null) {
            timer2.cancel();
            this.b.s.purge();
            this.b.s = null;
        }
        MainActivity mainActivity2 = this.b;
        mainActivity2.dialogUserTimeout.setVisibility(8);
        mainActivity2.p = false;
        HardwareLEDService.getInstance().turnOff();
        IUpdateService updateService = UpdateServiceAdapter.getInstance().getUpdateService(this.b.getApplicationContext());
        if (updateService == null || LayoutHelper.getInstance().isRecipeOpened()) {
            Log.v(MainActivity.w, "timeout dialog >>>>> sleep (recipe opened OR no update service).");
            MainActivity.a(this.b, true);
            return;
        }
        try {
            UpdateServiceAdapter.getInstance().addStateListener(new m0(this));
            Log.v(MainActivity.w, "timeout dialog >>>>> nap.");
            MainActivity.a(this.b, false);
            updateService.startCheck();
        } catch (RemoteException e) {
            String str = MainActivity.w;
            StringBuilder sbA = g9.a("failed to trigger updater.\n");
            sbA.append(e.getMessage());
            Log.w(str, sbA.toString());
            e.printStackTrace();
            Log.v(MainActivity.w, "timeout dialog >>>>> sleep (check failed).");
            MainActivity.a(this.b, true);
        }
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        String str = MainActivity.w;
        StringBuilder sbA = g9.a("timeout dialog tick ");
        sbA.append(this.b.k[0]);
        Log.v(str, sbA.toString());
        MainActivity mainActivity = this.b;
        if (!mainActivity.i[0]) {
            mainActivity.a();
            Handler handler = this.b.h;
            final TextView textView = this.a;
            handler.post(new Runnable() { // from class: a
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.a(textView);
                }
            });
            return;
        }
        Timer timer2 = mainActivity.s;
        if (timer2 != null) {
            timer2.cancel();
            this.b.s.purge();
        }
    }
}
