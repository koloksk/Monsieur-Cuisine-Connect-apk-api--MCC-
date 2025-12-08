package defpackage;

import android.widget.SeekBar;
import application.App;
import de.silpion.mc2.R;
import fragment.SettingsFragment;
import helper.SharedPreferencesHelper;
import sound.SoundLength;

/* loaded from: classes.dex */
public class ak implements SeekBar.OnSeekBarChangeListener {
    public final /* synthetic */ SettingsFragment a;

    public ak(SettingsFragment settingsFragment) {
        this.a = settingsFragment;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        SettingsFragment settingsFragment = this.a;
        if (i <= 0) {
            settingsFragment.y.setImageResource(R.drawable.asset_002_sound_disabled);
        } else {
            settingsFragment.y.setImageResource(R.drawable.asset_002_sound);
        }
        SharedPreferencesHelper.getInstance().saveSoundVolume(i);
        App.getInstance().setSoundVolume(i);
        App.getInstance().playSound(R.raw.finished, SoundLength.SMALL_THRESHOLD);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        App.getInstance().playSound(R.raw.finished, SoundLength.LONG);
    }
}
