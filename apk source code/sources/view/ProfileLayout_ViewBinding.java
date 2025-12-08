package view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import helper.ActionListener;
import helper.Logger;
import mcapi.McApi;
import mcapi.ResponseListener;
import mcapi.json.AuthenticationResponse;
import mcapi.json.ConstraintResponse;
import view.ProfileLayout;

/* loaded from: classes.dex */
public class ProfileLayout_ViewBinding implements Unbinder {
    public ProfileLayout a;
    public View b;
    public View c;
    public View d;
    public View e;
    public View f;
    public View g;
    public View h;
    public View i;
    public View j;
    public View k;
    public View l;
    public View m;
    public View n;
    public View o;
    public View p;

    public class a extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public a(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            ProfileLayout profileLayout = this.c;
            ActionListener<ProfileLayout> actionListener = profileLayout.b;
            if (actionListener != null) {
                actionListener.onAction(profileLayout);
            }
        }
    }

    public class b extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public b(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.logout();
        }
    }

    public class c extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public c(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.switchToStart();
        }
    }

    public class d extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public d(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.switchToStart();
        }
    }

    public class e extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public e(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.switchToStart();
        }
    }

    public class f extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public f(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            ProfileLayout profileLayout = this.c;
            if (!(profileLayout.startSkipButton.getVisibility() == 0)) {
                profileLayout.d();
                return;
            }
            Log.w(ProfileLayout.h, "after authenticate - wizard mode");
            if (profileLayout.a != null) {
                Log.w(ProfileLayout.h, " > invoking onLoggedInCallback");
                profileLayout.a.onAction(profileLayout);
            } else {
                Log.w(ProfileLayout.h, " ! no onLoggedInCallback");
                profileLayout.d();
            }
        }
    }

    public class g extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public g(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            final ProfileLayout profileLayout = this.c;
            if (profileLayout.b()) {
                Log.w(ProfileLayout.h, "loginSubmit");
                profileLayout.loginSubmit.setEnabled(false);
                profileLayout.a(new ActionListener() { // from class: fr
                    @Override // helper.ActionListener
                    public final void onAction(Object obj) {
                        ((ProfileLayout) obj).b(false, false);
                    }
                });
                McApi.getInstance().authenticate(new ResponseListener() { // from class: op
                    @Override // mcapi.ResponseListener
                    public final void receivedResponse(int i, Object obj, Exception exc) {
                        profileLayout.a(i, (AuthenticationResponse) obj, exc);
                    }
                }, profileLayout.loginEmail.getText().toString(), profileLayout.loginPassword.getText().toString());
            }
        }
    }

    public class h extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public h(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            ProfileLayout profileLayout = this.c;
            ActionListener<ProfileLayout> actionListener = profileLayout.f;
            if (actionListener != null) {
                actionListener.onAction(profileLayout);
            }
        }
    }

    public class i extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public i(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.openSignUpStep2();
        }
    }

    public class j extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public j(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            final ProfileLayout profileLayout = this.c;
            if (profileLayout.b()) {
                Logger.w(ProfileLayout.h, "signupSubmit");
                profileLayout.signUpSubmit.setEnabled(false);
                profileLayout.a(new ActionListener() { // from class: tp
                    @Override // helper.ActionListener
                    public final void onAction(Object obj) {
                        ProfileLayout profileLayout2 = (ProfileLayout) obj;
                        profileLayout2.signupContainer.post(new yp(profileLayout2, false, ""));
                    }
                });
                McApi.getInstance().register(new ResponseListener() { // from class: mp
                    @Override // mcapi.ResponseListener
                    public final void receivedResponse(int i, Object obj, Exception exc) {
                        profileLayout.a(i, (ConstraintResponse) obj, exc);
                    }
                }, profileLayout.signupEmail.getText().toString(), profileLayout.signupNickName.getText().toString(), profileLayout.signupPassword.getText().toString(), profileLayout.signupFirstName.getText().toString(), profileLayout.signupLastName.getText().toString());
            }
        }
    }

    public class k extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public k(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.switchToLogin();
        }
    }

    public class l extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public l(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.switchToLogin();
        }
    }

    public class m extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public m(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.switchToLogin();
        }
    }

    public class n extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public n(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.switchToSignup();
        }
    }

    public class o extends DebouncingOnClickListener {
        public final /* synthetic */ ProfileLayout c;

        public o(ProfileLayout_ViewBinding profileLayout_ViewBinding, ProfileLayout profileLayout) {
            this.c = profileLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.switchToSignup();
        }
    }

    @UiThread
    public ProfileLayout_ViewBinding(ProfileLayout profileLayout) {
        this(profileLayout, profileLayout);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ProfileLayout profileLayout = this.a;
        if (profileLayout == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        profileLayout.layoutLoginDone = null;
        profileLayout.loginContainer = null;
        profileLayout.loginEmail = null;
        profileLayout.loginErrorTextView = null;
        profileLayout.loginPassword = null;
        profileLayout.loginSubmit = null;
        profileLayout.progressCancelBtn = null;
        profileLayout.progressContainer = null;
        profileLayout.relativeLayoutSignUpDone = null;
        profileLayout.relativeLayoutSignUpStep1 = null;
        profileLayout.relativeLayoutSignUpStep2 = null;
        profileLayout.signUpErrorTextView = null;
        profileLayout.signUpNextStepBtn = null;
        profileLayout.signUpSubmit = null;
        profileLayout.signupContainer = null;
        profileLayout.signupEmail = null;
        profileLayout.signupFirstName = null;
        profileLayout.signupLastName = null;
        profileLayout.signupNickName = null;
        profileLayout.signupPassword = null;
        profileLayout.signupPrivacyPolicyTv = null;
        profileLayout.signupPrivacyPolicyCheckbox = null;
        profileLayout.startContainer = null;
        profileLayout.startSkipButton = null;
        profileLayout.startSkipLabel = null;
        profileLayout.userDetailsContainer = null;
        profileLayout.userDetailsEmail = null;
        profileLayout.userDetailsFavorites = null;
        profileLayout.userDetailsNickname = null;
        this.b.setOnClickListener(null);
        this.b = null;
        this.c.setOnClickListener(null);
        this.c = null;
        this.d.setOnClickListener(null);
        this.d = null;
        this.e.setOnClickListener(null);
        this.e = null;
        this.f.setOnClickListener(null);
        this.f = null;
        this.g.setOnClickListener(null);
        this.g = null;
        this.h.setOnClickListener(null);
        this.h = null;
        this.i.setOnClickListener(null);
        this.i = null;
        this.j.setOnClickListener(null);
        this.j = null;
        this.k.setOnClickListener(null);
        this.k = null;
        this.l.setOnClickListener(null);
        this.l = null;
        this.m.setOnClickListener(null);
        this.m = null;
        this.n.setOnClickListener(null);
        this.n = null;
        this.o.setOnClickListener(null);
        this.o = null;
        this.p.setOnClickListener(null);
        this.p = null;
    }

    @UiThread
    public ProfileLayout_ViewBinding(ProfileLayout profileLayout, View view2) {
        this.a = profileLayout;
        profileLayout.layoutLoginDone = Utils.findRequiredView(view2, R.id.profile_login_done_container, "field 'layoutLoginDone'");
        profileLayout.loginContainer = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.profile_login_container, "field 'loginContainer'", ViewGroup.class);
        profileLayout.loginEmail = (EditText) Utils.findRequiredViewAsType(view2, R.id.profile_login_email, "field 'loginEmail'", EditText.class);
        profileLayout.loginErrorTextView = (QuicksandTextView) Utils.findRequiredViewAsType(view2, R.id.profile_login_error_text, "field 'loginErrorTextView'", QuicksandTextView.class);
        profileLayout.loginPassword = (EditText) Utils.findRequiredViewAsType(view2, R.id.profile_login_password, "field 'loginPassword'", EditText.class);
        View viewFindRequiredView = Utils.findRequiredView(view2, R.id.profile_login_submit, "field 'loginSubmit' and method 'loginSubmit'");
        profileLayout.loginSubmit = viewFindRequiredView;
        this.b = viewFindRequiredView;
        viewFindRequiredView.setOnClickListener(new g(this, profileLayout));
        View viewFindRequiredView2 = Utils.findRequiredView(view2, R.id.progress_cancel_btn, "field 'progressCancelBtn' and method 'cancelProgress'");
        profileLayout.progressCancelBtn = viewFindRequiredView2;
        this.c = viewFindRequiredView2;
        viewFindRequiredView2.setOnClickListener(new h(this, profileLayout));
        profileLayout.progressContainer = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.profile_progress_container, "field 'progressContainer'", ViewGroup.class);
        profileLayout.relativeLayoutSignUpDone = Utils.findRequiredView(view2, R.id.profile_signup_done, "field 'relativeLayoutSignUpDone'");
        profileLayout.relativeLayoutSignUpStep1 = Utils.findRequiredView(view2, R.id.profile_signup_step1, "field 'relativeLayoutSignUpStep1'");
        profileLayout.relativeLayoutSignUpStep2 = Utils.findRequiredView(view2, R.id.profile_signup_step2, "field 'relativeLayoutSignUpStep2'");
        profileLayout.signUpErrorTextView = (QuicksandTextView) Utils.findRequiredViewAsType(view2, R.id.profile_signup_register_error, "field 'signUpErrorTextView'", QuicksandTextView.class);
        View viewFindRequiredView3 = Utils.findRequiredView(view2, R.id.profile_signup_step1_next_btn, "field 'signUpNextStepBtn' and method 'openSignUpStep2'");
        profileLayout.signUpNextStepBtn = (ImageView) Utils.castView(viewFindRequiredView3, R.id.profile_signup_step1_next_btn, "field 'signUpNextStepBtn'", ImageView.class);
        this.d = viewFindRequiredView3;
        viewFindRequiredView3.setOnClickListener(new i(this, profileLayout));
        View viewFindRequiredView4 = Utils.findRequiredView(view2, R.id.profile_signup_submit, "field 'signUpSubmit' and method 'signupSubmit'");
        profileLayout.signUpSubmit = (ImageView) Utils.castView(viewFindRequiredView4, R.id.profile_signup_submit, "field 'signUpSubmit'", ImageView.class);
        this.e = viewFindRequiredView4;
        viewFindRequiredView4.setOnClickListener(new j(this, profileLayout));
        View viewFindRequiredView5 = Utils.findRequiredView(view2, R.id.profile_signup_to_signin_btn, "field 'signUpToSignInBtn' and method 'switchToLogin'");
        this.f = viewFindRequiredView5;
        viewFindRequiredView5.setOnClickListener(new k(this, profileLayout));
        profileLayout.signupContainer = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.profile_signup_container, "field 'signupContainer'", ViewGroup.class);
        profileLayout.signupEmail = (EditText) Utils.findRequiredViewAsType(view2, R.id.profile_signup_email, "field 'signupEmail'", EditText.class);
        profileLayout.signupFirstName = (EditText) Utils.findRequiredViewAsType(view2, R.id.profile_signup_firstname, "field 'signupFirstName'", EditText.class);
        profileLayout.signupLastName = (EditText) Utils.findRequiredViewAsType(view2, R.id.profile_signup_lastname, "field 'signupLastName'", EditText.class);
        profileLayout.signupNickName = (EditText) Utils.findRequiredViewAsType(view2, R.id.profile_signup_nickname, "field 'signupNickName'", EditText.class);
        profileLayout.signupPassword = (EditText) Utils.findRequiredViewAsType(view2, R.id.profile_signup_password, "field 'signupPassword'", EditText.class);
        profileLayout.signupPrivacyPolicyTv = (TextView) Utils.findRequiredViewAsType(view2, R.id.profile_signup_privacy_policy_tv, "field 'signupPrivacyPolicyTv'", TextView.class);
        profileLayout.signupPrivacyPolicyCheckbox = (CheckBox) Utils.findRequiredViewAsType(view2, R.id.profile_signup_privacy_policy_cb, "field 'signupPrivacyPolicyCheckbox'", CheckBox.class);
        profileLayout.startContainer = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.profile_start_container, "field 'startContainer'", ViewGroup.class);
        View viewFindRequiredView6 = Utils.findRequiredView(view2, R.id.profile_start_login_button, "field 'startLoginButton' and method 'switchToLogin'");
        this.g = viewFindRequiredView6;
        viewFindRequiredView6.setOnClickListener(new l(this, profileLayout));
        View viewFindRequiredView7 = Utils.findRequiredView(view2, R.id.profile_start_login_label, "field 'startLoginLabel' and method 'switchToLogin'");
        this.h = viewFindRequiredView7;
        viewFindRequiredView7.setOnClickListener(new m(this, profileLayout));
        View viewFindRequiredView8 = Utils.findRequiredView(view2, R.id.profile_start_register_button, "field 'startRegisterButton' and method 'switchToSignup'");
        this.i = viewFindRequiredView8;
        viewFindRequiredView8.setOnClickListener(new n(this, profileLayout));
        View viewFindRequiredView9 = Utils.findRequiredView(view2, R.id.profile_start_register_label, "field 'startRegisterLabel' and method 'switchToSignup'");
        this.j = viewFindRequiredView9;
        viewFindRequiredView9.setOnClickListener(new o(this, profileLayout));
        View viewFindRequiredView10 = Utils.findRequiredView(view2, R.id.profile_start_skip_button, "field 'startSkipButton' and method 'onStartSkipButtonClick'");
        profileLayout.startSkipButton = (ImageView) Utils.castView(viewFindRequiredView10, R.id.profile_start_skip_button, "field 'startSkipButton'", ImageView.class);
        this.k = viewFindRequiredView10;
        viewFindRequiredView10.setOnClickListener(new a(this, profileLayout));
        profileLayout.startSkipLabel = (TextView) Utils.findRequiredViewAsType(view2, R.id.profile_start_skip_label, "field 'startSkipLabel'", TextView.class);
        profileLayout.userDetailsContainer = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.profile_userdetails_container, "field 'userDetailsContainer'", ViewGroup.class);
        profileLayout.userDetailsEmail = (TextView) Utils.findRequiredViewAsType(view2, R.id.profile_userdetails_email, "field 'userDetailsEmail'", TextView.class);
        profileLayout.userDetailsFavorites = (TextView) Utils.findRequiredViewAsType(view2, R.id.profile_userdetails_favorites, "field 'userDetailsFavorites'", TextView.class);
        profileLayout.userDetailsNickname = (TextView) Utils.findRequiredViewAsType(view2, R.id.profile_userdetails_nickname, "field 'userDetailsNickname'", TextView.class);
        View viewFindRequiredView11 = Utils.findRequiredView(view2, R.id.profile_userdetails_logout, "method 'logout'");
        this.l = viewFindRequiredView11;
        viewFindRequiredView11.setOnClickListener(new b(this, profileLayout));
        View viewFindRequiredView12 = Utils.findRequiredView(view2, R.id.profile_login_back, "method 'switchToStart'");
        this.m = viewFindRequiredView12;
        viewFindRequiredView12.setOnClickListener(new c(this, profileLayout));
        View viewFindRequiredView13 = Utils.findRequiredView(view2, R.id.profile_signup_step1_back_btn, "method 'switchToStart'");
        this.n = viewFindRequiredView13;
        viewFindRequiredView13.setOnClickListener(new d(this, profileLayout));
        View viewFindRequiredView14 = Utils.findRequiredView(view2, R.id.profile_signup_step2_back_btn, "method 'switchToStart'");
        this.o = viewFindRequiredView14;
        viewFindRequiredView14.setOnClickListener(new e(this, profileLayout));
        View viewFindRequiredView15 = Utils.findRequiredView(view2, R.id.profile_signin_to_info, "method 'doAfterLogin'");
        this.p = viewFindRequiredView15;
        viewFindRequiredView15.setOnClickListener(new f(this, profileLayout));
        profileLayout.privacyPolicyConsentText = view2.getContext().getResources().getString(R.string.profile_signup_privacy_policy_consent);
    }
}
