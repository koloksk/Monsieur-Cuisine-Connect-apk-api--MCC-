package defpackage;

import android.view.View;
import de.silpion.mc2.R;
import de.silpion.mcupdater.UpdateServiceAdapter;
import fragment.SettingsFragment;

/* loaded from: classes.dex */
public class yj implements UpdateServiceAdapter.StateListener {
    public final /* synthetic */ SettingsFragment a;

    public yj(SettingsFragment settingsFragment) {
        this.a = settingsFragment;
    }

    public /* synthetic */ void a() {
        View viewFindViewById = this.a.s.findViewById(R.id.test_updater_bt);
        if (viewFindViewById != null) {
            viewFindViewById.setEnabled(true);
        }
    }

    @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
    public void checking() {
        SettingsFragment settingsFragment = this.a;
        settingsFragment.s.post(new xh(settingsFragment, settingsFragment.getString(R.string.updater_checking)));
    }

    @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
    public void downloadProgress(int i) {
        SettingsFragment settingsFragment = this.a;
        settingsFragment.s.post(new xh(settingsFragment, settingsFragment.getString(R.string.updater_download_progress, Integer.valueOf(i))));
    }

    @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
    public void downloading() {
        SettingsFragment settingsFragment = this.a;
        settingsFragment.s.post(new xh(settingsFragment, settingsFragment.getString(R.string.updater_downloading)));
    }

    @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
    public void idle() {
        SettingsFragment settingsFragment = this.a;
        settingsFragment.s.post(new xh(settingsFragment, settingsFragment.getString(R.string.updater_idle)));
        this.a.s.post(new Runnable() { // from class: ug
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a();
            }
        });
    }

    @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
    public void installing() {
        SettingsFragment settingsFragment = this.a;
        settingsFragment.s.post(new xh(settingsFragment, settingsFragment.getString(R.string.updater_installing)));
    }

    @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
    public void unknown() {
    }

    @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
    public void updateAvailable() {
        SettingsFragment settingsFragment = this.a;
        settingsFragment.s.post(new xh(settingsFragment, settingsFragment.getString(R.string.updater_available)));
    }

    @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
    public void updateReady() {
        SettingsFragment settingsFragment = this.a;
        settingsFragment.s.post(new xh(settingsFragment, settingsFragment.getString(R.string.updater_ready)));
    }
}
