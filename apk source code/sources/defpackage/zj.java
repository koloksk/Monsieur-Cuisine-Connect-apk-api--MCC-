package defpackage;

import android.support.v4.app.FragmentActivity;
import de.silpion.mc2.R;
import fragment.SettingsFragment;
import helper.SharedPreferencesHelper;
import java.io.BufferedReader;
import mcapi.HttpResponseListener;
import view.QuicksandTextView;

/* loaded from: classes.dex */
public class zj implements HttpResponseListener {
    public final /* synthetic */ QuicksandTextView a;
    public final /* synthetic */ SettingsFragment b;

    public zj(SettingsFragment settingsFragment, QuicksandTextView quicksandTextView) {
        this.b = settingsFragment;
        this.a = quicksandTextView;
    }

    public /* synthetic */ void a(QuicksandTextView quicksandTextView) {
        SettingsFragment settingsFragment = this.b;
        quicksandTextView.setText(settingsFragment.getString(R.string.connected_to_server, settingsFragment.getString(R.string.not_connected)));
    }

    public /* synthetic */ void b(QuicksandTextView quicksandTextView) {
        String machineType = SharedPreferencesHelper.getInstance().getMachineType();
        String strB = machineType.length() > 0 ? g9.b(" - ", machineType) : "";
        StringBuilder sb = new StringBuilder();
        SettingsFragment settingsFragment = this.b;
        sb.append(settingsFragment.getString(R.string.connected_to_server, settingsFragment.getString(R.string.connected)));
        sb.append(strB);
        quicksandTextView.setText(sb.toString());
    }

    @Override // mcapi.HttpResponseListener
    public void failure(Exception exc) {
        FragmentActivity activity2 = this.b.getActivity();
        final QuicksandTextView quicksandTextView = this.a;
        activity2.runOnUiThread(new Runnable() { // from class: vg
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(quicksandTextView);
            }
        });
    }

    @Override // mcapi.HttpResponseListener
    public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
        FragmentActivity activity2 = this.b.getActivity();
        final QuicksandTextView quicksandTextView = this.a;
        activity2.runOnUiThread(new Runnable() { // from class: wg
            @Override // java.lang.Runnable
            public final void run() {
                this.a.b(quicksandTextView);
            }
        });
    }
}
