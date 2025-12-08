package fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.silpion.mc2.R;
import fragment.FirstLaunchFragment;
import helper.ActionListener;
import helper.LayoutHelper;
import helper.ResourceHelper;
import helper.SharedPreferencesHelper;
import view.DataPrivacyAcknowledgeLayout;
import view.LanguageSelectLayout;
import view.ProfileLayout;
import view.QuestionDialogView;
import view.QuicksandTextView;
import view.WifiSetupLayout;

/* loaded from: classes.dex */
public class FirstLaunchFragment extends BaseFragment {
    public static final String c = FirstLaunchFragment.class.getSimpleName();

    @BindView(R.id.firstlaunch_language_select)
    public LanguageSelectLayout languageSelect;

    @BindView(R.id.firstlaunch_acknowledge_data_privacy)
    public DataPrivacyAcknowledgeLayout privacyAcknowledge;

    @BindView(R.id.firstlaunch_profile)
    public ProfileLayout profileLayout;

    @BindView(R.id.firstlaunch_transfer_hoyer_question)
    public QuestionDialogView transferHoyerQuestion;

    @BindView(R.id.firstlaunch_wifi_setup)
    public WifiSetupLayout wifiSetup;

    @BindView(R.id.firstlaunch_wifi_setup_question)
    public QuestionDialogView wifiSetupQuestion;

    public static /* synthetic */ void d(QuestionDialogView questionDialogView) {
        SharedPreferencesHelper.getInstance().countLaunch();
        LayoutHelper.getInstance().closeFirstLaunchFragment();
    }

    public /* synthetic */ void a(String str) {
        if (str.equals("POPUP")) {
            String localPrivacyTermHTML = ResourceHelper.getLocalPrivacyTermHTML();
            View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.terms_row_firstlaunch, (ViewGroup) null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(viewInflate);
            builder.setCancelable(true);
            builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() { // from class: fe
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            ((QuicksandTextView) viewInflate.findViewById(R.id.terms_row_content_tv)).setText(Html.fromHtml(localPrivacyTermHTML));
            AlertDialog alertDialogCreate = builder.create();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(alertDialogCreate.getWindow().getAttributes());
            layoutParams.width = -1;
            layoutParams.height = -1;
            alertDialogCreate.show();
            alertDialogCreate.getWindow().setAttributes(layoutParams);
        }
    }

    public /* synthetic */ void b(WifiSetupLayout wifiSetupLayout) {
        this.transferHoyerQuestion.setVisibility(0);
        this.wifiSetup.setVisibility(8);
    }

    public /* synthetic */ void c(QuestionDialogView questionDialogView) {
        Log.i(c, "User allows usage data transfer to HOYER.");
        SharedPreferencesHelper.getInstance().setSendTrackingData(true);
        a();
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_firstlaunch, viewGroup, false);
        this.a = viewInflate;
        ButterKnife.bind(this, viewInflate);
        this.transferHoyerQuestion.setOnBodyURLClick(new ActionListener() { // from class: de
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((String) obj);
            }
        });
        this.transferHoyerQuestion.setBodyTextSize(16.0f);
        this.wifiSetupQuestion.setBodyTextSize(24.0f);
        if (!SharedPreferencesHelper.getInstance().isLocaleChosen()) {
            this.languageSelect.setVisibility(0);
            return this.a;
        }
        this.languageSelect.setVisibility(8);
        if (SharedPreferencesHelper.getInstance().hasUserAcknowledgedDataPrivacyTerms()) {
            this.wifiSetupQuestion.setVisibility(0);
            this.privacyAcknowledge.setVisibility(8);
        } else {
            this.privacyAcknowledge.setVisibility(0);
            this.privacyAcknowledge.setAcknowledgeListener(new ActionListener() { // from class: he
                @Override // helper.ActionListener
                public final void onAction(Object obj) {
                    this.a.a((DataPrivacyAcknowledgeLayout) obj);
                }
            });
            this.wifiSetupQuestion.setVisibility(8);
        }
        this.wifiSetupQuestion.setButtonOneClickListener(new ActionListener() { // from class: zd
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                FirstLaunchFragment.d((QuestionDialogView) obj);
            }
        });
        this.wifiSetupQuestion.setButtonTwoClickListener(new ActionListener() { // from class: be
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((QuestionDialogView) obj);
            }
        });
        this.wifiSetup.setVisibility(8);
        this.wifiSetup.setCancelListener(new ActionListener() { // from class: ge
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((WifiSetupLayout) obj);
            }
        });
        this.wifiSetup.setNextListener(new ActionListener() { // from class: xd
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.b((WifiSetupLayout) obj);
            }
        });
        this.transferHoyerQuestion.setVisibility(8);
        this.transferHoyerQuestion.setButtonOneClickListener(new ActionListener() { // from class: ce
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.b((QuestionDialogView) obj);
            }
        });
        this.transferHoyerQuestion.setButtonTwoClickListener(new ActionListener() { // from class: yd
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.c((QuestionDialogView) obj);
            }
        });
        this.profileLayout.setVisibility(8);
        this.profileLayout.setOnLoggedInCallback(new ActionListener() { // from class: ee
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((ProfileLayout) obj);
            }
        });
        this.profileLayout.setOnSkipBtnClickedCallback(new ActionListener() { // from class: ae
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                FirstLaunchFragment.b((ProfileLayout) obj);
            }
        });
        return this.a;
    }

    public /* synthetic */ void b(QuestionDialogView questionDialogView) {
        Log.i(c, "User forbids  usage data transfer to HOYER.");
        SharedPreferencesHelper.getInstance().setSendTrackingData(false);
        a();
    }

    public static /* synthetic */ void b(ProfileLayout profileLayout) {
        SharedPreferencesHelper.getInstance().countLaunch();
        LayoutHelper.getInstance().closeFirstLaunchFragment();
    }

    public /* synthetic */ void a(DataPrivacyAcknowledgeLayout dataPrivacyAcknowledgeLayout) {
        SharedPreferencesHelper.getInstance().userAcknowledgedDataPrivacyTerms();
        SharedPreferencesHelper.getInstance().setAcceptedPrivacyPolicyVersion(ResourceHelper.getLocalPrivacyTermVersion());
        SharedPreferencesHelper.getInstance().acceptDataPrivacyPolicy(true);
        this.privacyAcknowledge.setVisibility(8);
        if (SharedPreferencesHelper.getInstance().shouldShowFirstLaunch()) {
            this.wifiSetupQuestion.setVisibility(0);
        } else {
            this.transferHoyerQuestion.setVisibility(0);
        }
    }

    public /* synthetic */ void a(QuestionDialogView questionDialogView) {
        this.wifiSetup.setVisibility(0);
        this.wifiSetupQuestion.setVisibility(8);
        this.wifiSetup.start();
    }

    public /* synthetic */ void a(WifiSetupLayout wifiSetupLayout) {
        this.wifiSetup.stop();
        this.wifiSetup.setVisibility(8);
        this.wifiSetupQuestion.setVisibility(0);
    }

    public /* synthetic */ void a(ProfileLayout profileLayout) {
        this.profileLayout.setVisibility(8);
        SharedPreferencesHelper.getInstance().setFirstLaunchDone();
        LayoutHelper.getInstance().closeFirstLaunchFragment();
    }

    public final void a() {
        this.transferHoyerQuestion.setVisibility(8);
        if (!SharedPreferencesHelper.getInstance().shouldShowFirstLaunch() && SharedPreferencesHelper.getInstance().getUserToken() != null) {
            SharedPreferencesHelper.getInstance().setFirstLaunchDone();
            LayoutHelper.getInstance().closeFirstLaunchFragment();
        } else {
            this.profileLayout.setVisibility(0);
            this.profileLayout.startWizardMode();
        }
    }
}
