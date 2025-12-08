package view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import db.DbHelper;
import de.silpion.mc2.R;
import defpackage.g9;
import defpackage.yp;
import helper.ActionListener;
import helper.DialogHelper;
import helper.Logger;
import helper.SharedPreferencesHelper;
import helper.WifiHelper;
import java.util.Arrays;
import java.util.regex.Pattern;
import mcapi.McApi;
import mcapi.ResponseListener;
import mcapi.UnauthorizedException;
import mcapi.json.AuthenticationResponse;
import mcapi.json.ConstraintResponse;
import mcapi.json.UserDataResponse;
import org.apache.commons.lang3.StringUtils;
import view.ProfileLayout;
import view.QuestionDialogView;

/* loaded from: classes.dex */
public class ProfileLayout extends RelativeLayout {
    public static final String h = ProfileLayout.class.getSimpleName();
    public static final Pattern i = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", 2);
    public ActionListener<ProfileLayout> a;
    public ActionListener<ProfileLayout> b;
    public ActionListener<ProfileLayout> c;
    public TextWatcher d;
    public TextWatcher e;
    public ActionListener<ProfileLayout> f;
    public Unbinder g;

    @BindView(R.id.profile_login_done_container)
    public View layoutLoginDone;

    @BindView(R.id.profile_login_container)
    public ViewGroup loginContainer;

    @BindView(R.id.profile_login_email)
    public EditText loginEmail;

    @BindView(R.id.profile_login_error_text)
    public QuicksandTextView loginErrorTextView;

    @BindView(R.id.profile_login_password)
    public EditText loginPassword;

    @BindView(R.id.profile_login_submit)
    public View loginSubmit;

    @BindString(R.string.profile_signup_privacy_policy_consent)
    public String privacyPolicyConsentText;

    @BindView(R.id.progress_cancel_btn)
    public View progressCancelBtn;

    @BindView(R.id.profile_progress_container)
    public ViewGroup progressContainer;

    @BindView(R.id.profile_signup_done)
    public View relativeLayoutSignUpDone;

    @BindView(R.id.profile_signup_step1)
    public View relativeLayoutSignUpStep1;

    @BindView(R.id.profile_signup_step2)
    public View relativeLayoutSignUpStep2;

    @BindView(R.id.profile_signup_register_error)
    public QuicksandTextView signUpErrorTextView;

    @BindView(R.id.profile_signup_step1_next_btn)
    public ImageView signUpNextStepBtn;

    @BindView(R.id.profile_signup_submit)
    public ImageView signUpSubmit;

    @BindView(R.id.profile_signup_container)
    public ViewGroup signupContainer;

    @BindView(R.id.profile_signup_email)
    public EditText signupEmail;

    @BindView(R.id.profile_signup_firstname)
    public EditText signupFirstName;

    @BindView(R.id.profile_signup_lastname)
    public EditText signupLastName;

    @BindView(R.id.profile_signup_nickname)
    public EditText signupNickName;

    @BindView(R.id.profile_signup_password)
    public EditText signupPassword;

    @BindView(R.id.profile_signup_privacy_policy_cb)
    public CheckBox signupPrivacyPolicyCheckbox;

    @BindView(R.id.profile_signup_privacy_policy_tv)
    public TextView signupPrivacyPolicyTv;

    @BindView(R.id.profile_start_container)
    public ViewGroup startContainer;

    @BindView(R.id.profile_start_skip_button)
    public ImageView startSkipButton;

    @BindView(R.id.profile_start_skip_label)
    public TextView startSkipLabel;

    @BindView(R.id.profile_userdetails_container)
    public ViewGroup userDetailsContainer;

    @BindView(R.id.profile_userdetails_email)
    public TextView userDetailsEmail;

    @BindView(R.id.profile_userdetails_favorites)
    public TextView userDetailsFavorites;

    @BindView(R.id.profile_userdetails_nickname)
    public TextView userDetailsNickname;

    public class a implements TextWatcher {
        public a() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if ((!ProfileLayout.this.signupFirstName.getText().toString().equals("")) && (!ProfileLayout.this.signupLastName.getText().toString().equals(""))) {
                ProfileLayout.this.signUpNextStepBtn.setEnabled(true);
                ProfileLayout.this.signUpNextStepBtn.clearColorFilter();
            } else {
                ProfileLayout.this.signUpNextStepBtn.setEnabled(false);
                ProfileLayout.this.signUpNextStepBtn.setColorFilter(Color.argb(100, 150, 150, 150), PorterDuff.Mode.MULTIPLY);
            }
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    public class b implements TextWatcher {
        public b() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            ProfileLayout profileLayout = ProfileLayout.this;
            String string = profileLayout.signupNickName.getText().toString();
            if (profileLayout == null) {
                throw null;
            }
            if (string.length() > 2 && string.length() < 13) {
                if (ProfileLayout.i.matcher(ProfileLayout.this.signupEmail.getText().toString()).find()) {
                    ProfileLayout profileLayout2 = ProfileLayout.this;
                    String string2 = profileLayout2.signupPassword.getText().toString();
                    if (profileLayout2 == null) {
                        throw null;
                    }
                    if (string2.matches("^(?=.{10,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$") && ProfileLayout.this.signupPrivacyPolicyCheckbox.isChecked()) {
                        ProfileLayout.this.signUpSubmit.setEnabled(true);
                        ProfileLayout.this.signUpSubmit.clearColorFilter();
                        return;
                    }
                }
            }
            ProfileLayout.this.signUpSubmit.setEnabled(false);
            ProfileLayout.this.signUpSubmit.setColorFilter(Color.argb(100, 150, 150, 150), PorterDuff.Mode.MULTIPLY);
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    public class c extends QuestionDialogView.LinkHandler {
        public final /* synthetic */ ProfileLayout a;

        public c(ProfileLayout profileLayout) {
            this.a = profileLayout;
        }

        @Override // view.QuestionDialogView.LinkHandler
        public void onLinkClicked(String str) {
            ActionListener<ProfileLayout> actionListener;
            if (!str.equals("POPUP") || (actionListener = ProfileLayout.this.c) == null) {
                return;
            }
            actionListener.onAction(this.a);
        }
    }

    public ProfileLayout(Context context) {
        this(context, null);
    }

    private void setWizardMode(boolean z) {
        this.startSkipButton.setVisibility(z ? 0 : 8);
        this.startSkipLabel.setVisibility(z ? 0 : 8);
    }

    public /* synthetic */ void a(CompoundButton compoundButton, boolean z) {
        this.e.afterTextChanged(null);
    }

    public final boolean b() {
        if (WifiHelper.getInstance().getConnectedSSID() != null) {
            return true;
        }
        DialogHelper.getInstance().showWarningDialog(99);
        return false;
    }

    public /* synthetic */ void c() {
        this.progressCancelBtn.setVisibility(0);
    }

    public final void d() {
        this.startContainer.setVisibility(8);
        this.progressContainer.setVisibility(8);
        this.signupContainer.setVisibility(8);
        this.loginContainer.setVisibility(8);
        this.userDetailsContainer.setVisibility(0);
        this.layoutLoginDone.setVisibility(8);
    }

    @OnClick({R.id.profile_userdetails_logout})
    public void logout() {
        McApi.getInstance().logout();
        SharedPreferencesHelper.getInstance().clearUserSettings();
        switchToStart();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.g = ButterKnife.bind(this, this);
        this.d = new a();
        this.e = new b();
        this.signupFirstName.addTextChangedListener(this.d);
        this.signupLastName.addTextChangedListener(this.d);
        this.signupNickName.addTextChangedListener(this.e);
        this.signupEmail.addTextChangedListener(this.e);
        this.signupPassword.addTextChangedListener(this.e);
        this.signUpNextStepBtn.setEnabled(false);
        this.signUpNextStepBtn.setColorFilter(Color.argb(100, 150, 150, 150), PorterDuff.Mode.MULTIPLY);
        this.signUpSubmit.setEnabled(false);
        this.signUpSubmit.setColorFilter(Color.argb(100, 150, 150, 150), PorterDuff.Mode.MULTIPLY);
        this.signupPrivacyPolicyTv.setText(Html.fromHtml(this.privacyPolicyConsentText));
        this.signupPrivacyPolicyTv.setMovementMethod(new c(this));
        this.signupPrivacyPolicyCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: zp
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.a.a(compoundButton, z);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Unbinder unbinder = this.g;
        if (unbinder != null) {
            unbinder.unbind();
            this.g = null;
        }
    }

    @OnClick({R.id.profile_signup_step1_next_btn})
    public void openSignUpStep2() {
        this.signUpErrorTextView.setVisibility(8);
        this.relativeLayoutSignUpStep1.setVisibility(8);
        this.relativeLayoutSignUpStep2.setVisibility(0);
        this.relativeLayoutSignUpDone.setVisibility(8);
    }

    public void setOnLoggedInCallback(ActionListener<ProfileLayout> actionListener) {
        this.a = actionListener;
    }

    public void setOnSkipBtnClickedCallback(ActionListener<ProfileLayout> actionListener) {
        this.b = actionListener;
    }

    public void setOnTermDialogRequestedCallback(ActionListener<ProfileLayout> actionListener) {
        this.c = actionListener;
    }

    public void startSettingsMode() {
        setWizardMode(false);
        a(new ActionListener() { // from class: er
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                ((ProfileLayout) obj).switchToStart();
            }
        });
        McApi.getInstance().fetchUserData(new ResponseListener() { // from class: up
            @Override // mcapi.ResponseListener
            public final void receivedResponse(int i2, Object obj, Exception exc) {
                this.a.a(i2, (UserDataResponse) obj, exc);
            }
        });
    }

    public void startWizardMode() {
        setWizardMode(true);
        switchToStart();
    }

    @OnClick({R.id.profile_start_login_label, R.id.profile_start_login_button, R.id.profile_signup_to_signin_btn})
    public void switchToLogin() {
        this.startContainer.setVisibility(8);
        this.progressContainer.setVisibility(8);
        this.signupContainer.setVisibility(8);
        this.loginContainer.setVisibility(0);
        this.userDetailsContainer.setVisibility(8);
        this.loginSubmit.setEnabled(true);
    }

    @OnClick({R.id.profile_start_register_label, R.id.profile_start_register_button})
    public void switchToSignup() {
        this.startContainer.setVisibility(8);
        this.progressContainer.setVisibility(8);
        this.signupContainer.setVisibility(0);
        this.loginContainer.setVisibility(8);
        this.userDetailsContainer.setVisibility(8);
        openSignUpStep2();
    }

    @OnClick({R.id.profile_login_back, R.id.profile_signup_step1_back_btn, R.id.profile_signup_step2_back_btn})
    public void switchToStart() {
        this.startContainer.setVisibility(0);
        this.progressContainer.setVisibility(8);
        this.signupContainer.setVisibility(8);
        this.loginContainer.setVisibility(8);
        this.userDetailsContainer.setVisibility(8);
    }

    public ProfileLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void a(int i2, AuthenticationResponse authenticationResponse, Exception exc) {
        Log.i(h, "AUTHENTICATE ### Response >> code " + i2 + " >> response " + authenticationResponse);
        if (i2 == 200 && authenticationResponse != null && exc == null) {
            McApi.getInstance().fetchUserData(new ResponseListener() { // from class: lp
                @Override // mcapi.ResponseListener
                public final void receivedResponse(int i3, Object obj, Exception exc2) {
                    this.a.b(i3, (UserDataResponse) obj, exc2);
                }
            });
        } else {
            b(true, i2 == 0 && !(exc instanceof UnauthorizedException));
        }
    }

    public ProfileLayout(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public ProfileLayout(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        RelativeLayout.inflate(context, R.layout.profile_layout, this);
    }

    public final void b(final boolean z, final boolean z2) {
        SharedPreferencesHelper.getInstance().clearUserSettings();
        this.loginContainer.post(new Runnable() { // from class: bq
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(z, z2);
            }
        });
    }

    public final void a(final int i2, final Long[] lArr, final Exception exc) {
        if (i2 == 200 && lArr != null && exc == null) {
            DbHelper.getInstance().replaceFavorites(lArr, new ActionListener() { // from class: vp
                @Override // helper.ActionListener
                public final void onAction(Object obj) {
                    ProfileLayout.a(lArr, (Boolean) obj);
                }
            });
        }
        this.userDetailsContainer.post(new Runnable() { // from class: aq
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(exc, i2, lArr);
            }
        });
    }

    public final void b(int i2, UserDataResponse userDataResponse, Exception exc) {
        Log.i(h, "USERDATA ### Response >> code " + i2 + " >> response " + userDataResponse);
        if (exc == null && i2 == 200 && userDataResponse != null) {
            McApi.getInstance().updateDataPrivacyOptions();
            a(userDataResponse, false);
            return;
        }
        if (exc != null) {
            Log.w(h, "userDataResponse >> exception " + exc);
        }
        b(true, i2 == 0);
    }

    public static /* synthetic */ void a(Long[] lArr, Boolean bool) {
        String str = h;
        StringBuilder sbA = g9.a("replaced favorites, got ");
        sbA.append(String.valueOf(lArr.length));
        sbA.append(" from API >> success ");
        sbA.append(bool);
        Log.i(str, sbA.toString());
        App.getInstance().getMainActivity().refreshRecipesDisplay();
    }

    public /* synthetic */ void a(Exception exc, int i2, Long[] lArr) {
        if (exc == null && i2 == 200 && lArr != null && lArr.length != 0) {
            this.userDetailsFavorites.setText(Arrays.toString(lArr));
        } else {
            this.userDetailsFavorites.setText("--- no favorites ---");
        }
    }

    public final void a() {
        switchToSignup();
        this.relativeLayoutSignUpStep1.setVisibility(8);
        this.relativeLayoutSignUpStep2.setVisibility(8);
        this.relativeLayoutSignUpDone.setVisibility(0);
    }

    public /* synthetic */ void a(boolean z, boolean z2) {
        this.loginErrorTextView.setVisibility(z ? 0 : 8);
        this.loginErrorTextView.setText(getContext().getString(z2 ? R.string.profile_error_connection_error : R.string.profile_error_input));
        switchToLogin();
        this.loginSubmit.setEnabled(true);
    }

    public final void a(final UserDataResponse userDataResponse, final boolean z) {
        Logger.w(h, "logged in successfully");
        App.getInstance().getMainActivity().checkAndShowTermsOfUseDialog();
        this.loginContainer.post(new Runnable() { // from class: wp
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(z, userDataResponse);
            }
        });
        McApi.getInstance().fetchFavorites(new ResponseListener() { // from class: kp
            @Override // mcapi.ResponseListener
            public final void receivedResponse(int i2, Object obj, Exception exc) {
                this.a.a(i2, (Long[]) obj, exc);
            }
        });
    }

    public final void a(String str, boolean z) {
        this.signupContainer.post(new yp(this, z, str));
    }

    public /* synthetic */ void a(boolean z, String str) {
        switchToSignup();
        openSignUpStep2();
        this.signUpErrorTextView.setVisibility(z ? 0 : 8);
        this.signUpErrorTextView.setText(str);
        this.signUpSubmit.setEnabled(true);
    }

    public final void a(int i2, ConstraintResponse constraintResponse, Exception exc) {
        String str;
        Log.i(h, "REGISTER ### Response >> code " + i2 + " >> response " + constraintResponse);
        if (i2 == 0) {
            a(App.getInstance().getBaseContext().getString(R.string.profile_error_connection_error), true);
            return;
        }
        if (i2 == 409 && constraintResponse != null && (str = constraintResponse.constraint) != null) {
            if (str.contains("idx_unique_displayname")) {
                a(App.getInstance().getBaseContext().getString(R.string.profile_error_invalid_nickname), true);
                return;
            } else {
                if (constraintResponse.constraint.contains("userdata_pkey")) {
                    a(App.getInstance().getBaseContext().getString(R.string.profile_error_invalid_email), true);
                    return;
                }
                String str2 = h;
                StringBuilder sbA = g9.a("unhandled constraint error: ");
                sbA.append(constraintResponse.constraint);
                Log.w(str2, sbA.toString());
            }
        }
        if (exc == null && i2 == 201) {
            this.signUpSubmit.setEnabled(true);
            this.signupContainer.post(new Runnable() { // from class: gr
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.a();
                }
            });
            return;
        }
        if (exc != null) {
            a(App.getInstance().getBaseContext().getString(R.string.registration_failed) + i2 + ", " + App.getInstance().getBaseContext().getString(R.string.error) + StringUtils.SPACE + exc.getMessage(), true);
            return;
        }
        a(App.getInstance().getBaseContext().getString(R.string.registration_failed) + i2, true);
    }

    public /* synthetic */ void a(boolean z, UserDataResponse userDataResponse) {
        if (!z) {
            this.startContainer.setVisibility(8);
            this.progressContainer.setVisibility(8);
            this.signupContainer.setVisibility(8);
            this.loginContainer.setVisibility(8);
            this.userDetailsContainer.setVisibility(8);
            this.layoutLoginDone.setVisibility(0);
            App.getInstance().getMainActivity().triggerImportService();
        } else {
            d();
        }
        this.loginSubmit.setEnabled(true);
        try {
            this.userDetailsEmail.setText(userDataResponse.uid);
            this.userDetailsNickname.setText(userDataResponse.displayname);
            this.userDetailsFavorites.setText("");
        } catch (Exception unused) {
            Log.e(h, "Error while setting user info");
        }
        if (!(this.startSkipButton.getVisibility() == 0)) {
            SharedPreferencesHelper.getInstance().setFirstLaunchDone();
        }
        App.getInstance().getMainActivity().checkAndShowDataPrivacyDialog();
    }

    public final void a(ActionListener<ProfileLayout> actionListener) {
        App.getInstance().getMainActivity().hideSoftKeyboard();
        this.f = actionListener;
        this.startContainer.setVisibility(8);
        this.progressContainer.setVisibility(0);
        this.signupContainer.setVisibility(8);
        this.loginContainer.setVisibility(8);
        this.userDetailsContainer.setVisibility(8);
        this.progressCancelBtn.setVisibility(8);
        this.progressContainer.postDelayed(new Runnable() { // from class: xp
            @Override // java.lang.Runnable
            public final void run() {
                this.a.c();
            }
        }, 2000L);
    }

    public /* synthetic */ void a(int i2, UserDataResponse userDataResponse, Exception exc) {
        if (exc == null && i2 == 200 && userDataResponse != null) {
            a(userDataResponse, true);
        } else {
            this.startContainer.post(new Runnable() { // from class: np
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.switchToStart();
                }
            });
        }
    }
}
