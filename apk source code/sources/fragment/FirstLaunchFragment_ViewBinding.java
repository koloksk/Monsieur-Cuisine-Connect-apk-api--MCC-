package fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import view.DataPrivacyAcknowledgeLayout;
import view.LanguageSelectLayout;
import view.ProfileLayout;
import view.QuestionDialogView;
import view.WifiSetupLayout;

/* loaded from: classes.dex */
public class FirstLaunchFragment_ViewBinding implements Unbinder {
    public FirstLaunchFragment a;

    @UiThread
    public FirstLaunchFragment_ViewBinding(FirstLaunchFragment firstLaunchFragment, View view2) {
        this.a = firstLaunchFragment;
        firstLaunchFragment.languageSelect = (LanguageSelectLayout) Utils.findRequiredViewAsType(view2, R.id.firstlaunch_language_select, "field 'languageSelect'", LanguageSelectLayout.class);
        firstLaunchFragment.privacyAcknowledge = (DataPrivacyAcknowledgeLayout) Utils.findRequiredViewAsType(view2, R.id.firstlaunch_acknowledge_data_privacy, "field 'privacyAcknowledge'", DataPrivacyAcknowledgeLayout.class);
        firstLaunchFragment.profileLayout = (ProfileLayout) Utils.findRequiredViewAsType(view2, R.id.firstlaunch_profile, "field 'profileLayout'", ProfileLayout.class);
        firstLaunchFragment.transferHoyerQuestion = (QuestionDialogView) Utils.findRequiredViewAsType(view2, R.id.firstlaunch_transfer_hoyer_question, "field 'transferHoyerQuestion'", QuestionDialogView.class);
        firstLaunchFragment.wifiSetup = (WifiSetupLayout) Utils.findRequiredViewAsType(view2, R.id.firstlaunch_wifi_setup, "field 'wifiSetup'", WifiSetupLayout.class);
        firstLaunchFragment.wifiSetupQuestion = (QuestionDialogView) Utils.findRequiredViewAsType(view2, R.id.firstlaunch_wifi_setup_question, "field 'wifiSetupQuestion'", QuestionDialogView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        FirstLaunchFragment firstLaunchFragment = this.a;
        if (firstLaunchFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        firstLaunchFragment.languageSelect = null;
        firstLaunchFragment.privacyAcknowledge = null;
        firstLaunchFragment.profileLayout = null;
        firstLaunchFragment.transferHoyerQuestion = null;
        firstLaunchFragment.wifiSetup = null;
        firstLaunchFragment.wifiSetupQuestion = null;
    }
}
