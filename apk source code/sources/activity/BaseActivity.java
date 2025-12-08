package activity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import application.App;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import machineAdapter.adapter.MachineCallbackAdapter;

/* loaded from: classes.dex */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String g = BaseActivity.class.getSimpleName();
    public ConnectivityManager d;
    public long e;
    public final MachineCallbackAdapter f = new a();

    public class a extends MachineCallbackAdapter {
        public a() {
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialPagingRequested(int i) {
            BaseActivity.this.a();
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialPushed(int i) {
            Log.d(BaseActivity.g, String.format("*** Jog dial pushed %s.", Integer.valueOf(i)));
            BaseActivity.this.a();
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialTurned(int i, long j) {
            Log.d(BaseActivity.g, String.format(Locale.ENGLISH, "*** Jog dial turned %s, speed: %d.", Integer.valueOf(i), Long.valueOf(j)));
            BaseActivity.this.a();
        }
    }

    public class b extends TimerTask {
        public b() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (App.getInstance().isMachineSleeping()) {
                Log.i(BaseActivity.g, "user interaction timer while sleeping: ignore & reset countdown.");
                BaseActivity.this.a();
            } else {
                int iRound = (int) Math.round((System.currentTimeMillis() - BaseActivity.this.e) / 1000.0d);
                if (iRound >= 60) {
                    BaseActivity.this.onUserInteractionTimeout(iRound);
                }
            }
        }
    }

    public void a() {
        this.e = System.currentTimeMillis();
    }

    public boolean isNetworkConnected() {
        if (this.d == null) {
            this.d = (ConnectivityManager) getSystemService("connectivity");
        }
        NetworkInfo activeNetworkInfo = this.d.getActiveNetworkInfo();
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
        if (!z) {
            Log.w(g, "internet is not available");
        }
        return z;
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Log.i(g, "onCreate");
        super.onCreate(bundle);
        App.getInstance().getMachineAdapter().registerMachineCallback(this.f);
        a();
        new Timer().scheduleAtFixedRate(new b(), 60000L, 60000L);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.f);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return App.getInstance().getMachineAdapter().onKeyDown(i, keyEvent) || super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return App.getInstance().getMachineAdapter().onKeyLongPress(i, keyEvent) || super.onKeyLongPress(i, keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 135 && App.getInstance().isMachineSleeping()) {
            Log.w(g, "received jogdial up, but still sleeping - wake up!!");
            App.getInstance().wakeUpMachine();
        }
        return App.getInstance().getMachineAdapter().onKeyUp(i, keyEvent) || super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Activity
    public void onUserInteraction() {
        Log.v(g, "onUserInteraction");
        a();
        super.onUserInteraction();
    }

    public abstract void onUserInteractionTimeout(int i);
}
