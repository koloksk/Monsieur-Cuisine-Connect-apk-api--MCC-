package defpackage;

import android.util.Log;
import fragment.SettingsFragment;
import helper.WifiHelper;
import view.QuicksandTextView;

/* loaded from: classes.dex */
public class bk implements WifiHelper.WifiConnectionStateListener {
    public final /* synthetic */ QuicksandTextView a;
    public final /* synthetic */ SettingsFragment b;

    public bk(SettingsFragment settingsFragment, QuicksandTextView quicksandTextView) {
        this.b = settingsFragment;
        this.a = quicksandTextView;
    }

    @Override // helper.WifiHelper.WifiConnectionStateListener
    public void connected() {
        this.b.P.setVisibility(8);
        this.a.setVisibility(8);
        Log.d(SettingsFragment.Q, "WifiListener connected");
    }

    @Override // helper.WifiHelper.WifiConnectionStateListener
    public void establishingConnection() {
        this.b.P.setVisibility(0);
        Log.d(SettingsFragment.Q, "WifiListener connecting");
        this.a.setVisibility(8);
    }

    @Override // helper.WifiHelper.WifiConnectionStateListener
    public void failed() {
        this.b.P.setVisibility(8);
        SettingsFragment settingsFragment = this.b;
        WifiHelper.WifiInfoView wifiInfoView = settingsFragment.o;
        if (wifiInfoView != null && !settingsFragment.L.isWifiConnected(wifiInfoView.SSID)) {
            this.a.setVisibility(0);
            SettingsFragment settingsFragment2 = this.b;
            WifiHelper.WifiInfoView wifiInfoView2 = settingsFragment2.o;
            wifiInfoView2.isConfigured = false;
            settingsFragment2.L.deleteWifiConfiguration(wifiInfoView2);
        }
        Log.d(SettingsFragment.Q, "WifiListener failed");
    }
}
