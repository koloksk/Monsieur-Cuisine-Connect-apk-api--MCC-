package fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class SettingsFragment_ViewBinding implements Unbinder {
    public SettingsFragment a;

    @UiThread
    public SettingsFragment_ViewBinding(SettingsFragment settingsFragment, View view2) {
        this.a = settingsFragment;
        settingsFragment.aboutRowView = Utils.findRequiredView(view2, R.id.about_root_container, "field 'aboutRowView'");
        settingsFragment.bgImageView = (ImageView) Utils.findRequiredViewAsType(view2, R.id.sub_settings_bg, "field 'bgImageView'", ImageView.class);
        settingsFragment.bottomBtnContainer = Utils.findRequiredView(view2, R.id.fragment_settings_bottom_container_ll, "field 'bottomBtnContainer'");
        Utils.findRequiredView(view2, R.id.data_privacy_root_container, "field 'dataPrivacyRowView'");
        Utils.findRequiredView(view2, R.id.factory_reset_root_container, "field 'factoryResetRowView'");
        settingsFragment.faqRowView = Utils.findRequiredView(view2, R.id.faq_root_container, "field 'faqRowView'");
        settingsFragment.languageRowView = Utils.findRequiredView(view2, R.id.language_root_container, "field 'languageRowView'");
        settingsFragment.rowsContainer = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.rows_container, "field 'rowsContainer'", ViewGroup.class);
        settingsFragment.showLabelsRowView = Utils.findRequiredView(view2, R.id.show_labels_root_container, "field 'showLabelsRowView'");
        Utils.findRequiredView(view2, R.id.sound_root_container, "field 'soundRowView'");
        Utils.findRequiredView(view2, R.id.terms_root_container, "field 'termsRowView'");
        settingsFragment.wifiRowView = Utils.findRequiredView(view2, R.id.wifi_root_container, "field 'wifiRowView'");
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        SettingsFragment settingsFragment = this.a;
        if (settingsFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        settingsFragment.aboutRowView = null;
        settingsFragment.bgImageView = null;
        settingsFragment.bottomBtnContainer = null;
        settingsFragment.faqRowView = null;
        settingsFragment.languageRowView = null;
        settingsFragment.rowsContainer = null;
        settingsFragment.showLabelsRowView = null;
        settingsFragment.wifiRowView = null;
    }
}
