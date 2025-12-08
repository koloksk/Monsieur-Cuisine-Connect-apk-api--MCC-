package activity;

import activity.MainActivity;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cooking.CookingManager;
import cooking.CookingStep;
import db.DbHelper;
import db.ImportRecipesAndImagesTask;
import de.silpion.mc2.BuildConfig;
import de.silpion.mc2.R;
import defpackage.g9;
import defpackage.n0;
import defpackage.o0;
import defpackage.p0;
import defpackage.q0;
import fragment.GuidedCookingFragment;
import fragment.MainFragment;
import fragment.RecipeOverviewFragment;
import helper.ActionListener;
import helper.CommonUtils;
import helper.DataHolder;
import helper.DialogHelper;
import helper.LayoutHelper;
import helper.NetworkStateReceiver;
import helper.ResourceHelper;
import helper.SharedPreferencesHelper;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.BufferedReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import machineAdapter.ICommandInterface;
import machineAdapter.impl.service.HardwareLEDService;
import machineAdapter.impl.service.LEDColor;
import mcapi.HttpResponseListener;
import mcapi.McApi;
import mcapi.McUsageApi;
import mcapi.NewMcApi;
import mcapi.ResponseListener;
import mcapi.json.UserDataResponse;
import mcapi.json.message.CampaignMessage;
import model.Presets;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONException;
import services.RecipeImportService;
import sound.SoundLength;
import view.DataPrivacyAcknowledgeLayout;
import view.PresetLayout;
import view.QuestionDialogView;
import view.SurveyDialogView;

/* loaded from: classes.dex */
public class MainActivity extends BaseActivity implements PresetLayout.PresetPlayListener {

    @BindView(R.id.bookmark_question_dialog)
    public QuestionDialogView bookmarkDialog;

    @BindView(R.id.bookmark_iv)
    public ImageView bookmarkImageView;

    @BindView(R.id.container_guided_cooking)
    public FrameLayout containerGuidedCooking;

    @BindView(R.id.container_main)
    public FrameLayout containerMain;

    @BindView(R.id.container_recipes)
    public FrameLayout containerRecipes;

    @BindView(R.id.main_activity_content)
    public ViewGroup content;

    @BindView(R.id.dialog_recipes_received)
    public QuestionDialogView dialogRecipesReceived;

    @BindView(R.id.dialog_user_timeout)
    public RelativeLayout dialogUserTimeout;
    public FrameLayout.LayoutParams n;
    public ViewTreeObserver o;
    public volatile boolean p;

    @BindView(R.id.popup_acknowledge_data_privacy)
    public DataPrivacyAcknowledgeLayout privacyAcknowledge;
    public int q;

    @BindView(R.id.main_activity_root)
    public FrameLayout root;
    public Timer s;

    @BindView(R.id.popup_survey)
    public SurveyDialogView surveyDialog;

    @BindView(R.id.popup_acknowledge_terms_of_use)
    public QuestionDialogView termsOfUseAcknowledgeDialog;

    @BindView(R.id.top_bar_left_button)
    public ImageButton topLeftButton;

    @BindView(R.id.top_bar_left_label)
    public TextView topLeftLabel;

    @BindView(R.id.top_bar_middle_button)
    public ImageButton topMiddleButton;

    @BindView(R.id.top_bar_middle_label)
    public TextView topMiddleLabel;

    @BindView(R.id.top_bar_right_button)
    public ImageButton topRightButton;

    @BindView(R.id.top_bar_right_label)
    public TextView topRightLabel;
    public NetworkStateReceiver v;
    public static final String w = MainActivity.class.getSimpleName();
    public static int currentGlobalMode = 1;
    public final Handler h = new Handler();
    public final boolean[] i = {false};
    public final SparseArray<c> j = new SparseArray<>();
    public final int[] k = {0};
    public final Handler l = new a();
    public final ActionListener<Integer> m = new ActionListener() { // from class: c0
        @Override // helper.ActionListener
        public final void onAction(Object obj) {
            this.a.a((Integer) obj);
        }
    };
    public final ViewTreeObserver.OnGlobalLayoutListener r = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: s
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            this.a.k();
        }
    };
    public volatile boolean t = true;
    public volatile long u = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GlobalMode {
        public static final int DEFAULT = 2;
        public static final int FINISHED = 4;
        public static final int GUIDED_COOKING = 11;
        public static final int IDLE = 1;
        public static final int MANUAL = 3;
        public static final int PRESET = 10;
        public static final int PRESET_FINISHED = 5;
    }

    public class a extends Handler {
        public a() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            LayoutHelper.applyFullscreen(MainActivity.this.root);
            LayoutHelper.keepScreenOn(MainActivity.this);
        }
    }

    public class b implements HttpResponseListener {
        public b(MainActivity mainActivity) {
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
        }
    }

    public class c {
        public final boolean a;
        public final boolean b;

        @StringRes
        public final int c;
        public final boolean d;

        @StringRes
        public final int e;
        public final boolean f;

        @StringRes
        public final int g;

        public /* synthetic */ c(MainActivity mainActivity, boolean z, boolean z2, boolean z3, boolean z4, int i, int i2, int i3, a aVar) {
            this.b = z;
            this.a = z2;
            this.d = z3;
            this.f = z4;
            this.c = i;
            this.e = i2;
            this.g = i3;
        }
    }

    public MainActivity() {
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        this.j.put(0, new c(this, z, z2, z3, false, R.string.auto, R.string.recipes, R.string.settings, null));
        SparseArray<c> sparseArray = this.j;
        int i = R.string.auto;
        int i2 = R.string.recipes;
        int i3 = R.string.settings;
        a aVar = null;
        sparseArray.put(11, new c(this, false, z, z2, z3, i, i2, i3, aVar));
        SparseArray<c> sparseArray2 = this.j;
        int i4 = R.string.manual;
        sparseArray2.put(1, new c(this, true, z, z2, z3, i4, i2, i3, aVar));
        SparseArray<c> sparseArray3 = this.j;
        boolean z4 = false;
        boolean z5 = true;
        int i5 = R.string.auto;
        int i6 = R.string.settings;
        a aVar2 = null;
        sparseArray3.put(2, new c(this, z4, false, z5, z2, i5, i4, i6, aVar2));
        SparseArray<c> sparseArray4 = this.j;
        boolean z6 = true;
        int i7 = R.string.recipes;
        sparseArray4.put(5, new c(this, z4, z6, z5, z2, i7, i4, i6, aVar2));
        this.j.put(3, new c(this, z4, z6, z5, z2, i7, i4, i6, aVar2));
        this.j.put(4, new c(this, z4, z6, z5, z2, i7, i4, i6, aVar2));
        this.j.put(7, new c(this, z4, false, false, true, R.string.auto, R.string.recipes, R.string.manual, aVar2));
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
    }

    public static /* synthetic */ void b(Throwable th) throws Exception {
    }

    public static /* synthetic */ void x() {
        HardwareLEDService.getInstance().switchLEDOn(LEDColor.WHITE);
        App.getInstance().startSoundManager();
    }

    public static /* synthetic */ void y() {
        RecipeOverviewFragment recipeOverviewFragment = (RecipeOverviewFragment) LayoutHelper.getInstance().findFragment(2);
        if (recipeOverviewFragment != null) {
            recipeOverviewFragment.refreshData();
        }
    }

    public /* synthetic */ void a(Integer num) {
        if (num.intValue() == 4) {
            this.containerRecipes.setVisibility(8);
            this.containerMain.setVisibility(8);
            LayoutHelper.getInstance().setPresetLayoutVisibility(this.root, false);
        }
        c cVar = this.j.get(num.intValue());
        if (cVar != null) {
            ImageButton imageButton = this.topLeftButton;
            ImageButton imageButton2 = this.topMiddleButton;
            ImageButton imageButton3 = this.topRightButton;
            TextView textView = this.topLeftLabel;
            TextView textView2 = this.topMiddleLabel;
            TextView textView3 = this.topRightLabel;
            imageButton.setSelected(cVar.b);
            imageButton.setActivated(cVar.a);
            textView.setText(cVar.c);
            imageButton2.setSelected(cVar.d);
            textView2.setText(cVar.e);
            imageButton3.setSelected(cVar.f);
            textView3.setText(cVar.g);
        }
        i();
    }

    @OnClick({R.id.bookmark_iv})
    public void bookmarkClick() {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        ImageView imageView = this.bookmarkImageView;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        if (g().isRunning() || CookingManager.getInstance().getState() == 1) {
            CookingManager.getInstance().pauseCooking();
            a(new DialogHelper.SelectionListener() { // from class: q
                @Override // helper.DialogHelper.SelectionListener
                public final void onSelect(int i) {
                    this.a.a(i);
                }
            });
        } else {
            LayoutHelper.getInstance().openGuidedCooking(DbHelper.getInstance().getRecipeById(SharedPreferencesHelper.getInstance().getRecipeBookmark().getRecipeId()));
            ((GuidedCookingFragment) LayoutHelper.getInstance().findFragment(4)).checkAndScrollToBookmark();
        }
    }

    public /* synthetic */ void c(QuestionDialogView questionDialogView) {
        c();
    }

    @SuppressLint({"CheckResult"})
    public void checkAndShowDataPrivacyDialog() {
        final int acceptedPrivacyPolicyVersion = SharedPreferencesHelper.getInstance().getAcceptedPrivacyPolicyVersion();
        final int localPrivacyTermVersion = ResourceHelper.getLocalPrivacyTermVersion();
        NewMcApi.getInstance().getDataPrivacyHTML().subscribe(new Consumer() { // from class: u
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.a.a(localPrivacyTermVersion, acceptedPrivacyPolicyVersion, (String) obj);
            }
        }, new Consumer() { // from class: x
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.a.a(localPrivacyTermVersion, acceptedPrivacyPolicyVersion, (Throwable) obj);
            }
        });
    }

    @SuppressLint({"CheckResult"})
    public void checkAndShowTermsOfUseDialog() {
        final int acceptedTermsOfUseVersion = SharedPreferencesHelper.getInstance().getAcceptedTermsOfUseVersion();
        final int localTermsOfUseVersion = ResourceHelper.getLocalTermsOfUseVersion();
        McApi.getInstance().fetchUserData(new ResponseListener() { // from class: d
            @Override // mcapi.ResponseListener
            public final void receivedResponse(int i, Object obj, Exception exc) {
                this.a.a(localTermsOfUseVersion, acceptedTermsOfUseVersion, i, (UserDataResponse) obj, exc);
            }
        });
    }

    public /* synthetic */ void d(QuestionDialogView questionDialogView) {
        d();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && ((motionEvent.getAction() == 1 || motionEvent.getAction() == 2) && (currentFocus instanceof EditText) && !LayoutHelper.getInstance().isViewSelected(5) && !currentFocus.getClass().getName().startsWith("android.webkit."))) {
            currentFocus.getLocationOnScreen(new int[2]);
            float rawX = (motionEvent.getRawX() + currentFocus.getLeft()) - r1[0];
            float rawY = (motionEvent.getRawY() + currentFocus.getTop()) - r1[1];
            String str = w;
            StringBuilder sbA = g9.a("RecipeSearch ");
            sbA.append(String.valueOf(LayoutHelper.getInstance().getSelectedView()));
            Log.d(str, sbA.toString());
            if (rawX < currentFocus.getLeft() || rawX > currentFocus.getRight() || rawY < currentFocus.getTop() || rawY > currentFocus.getBottom()) {
                hideSoftKeyboard();
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public /* synthetic */ void e(QuestionDialogView questionDialogView) {
        e();
    }

    public final void f() {
        this.l.removeMessages(0);
        this.l.sendEmptyMessageDelayed(0, 600L);
    }

    @NonNull
    public final MainFragment g() {
        return (MainFragment) LayoutHelper.getInstance().findFragment(0);
    }

    public final void h() {
        Log.d(w, "hideRecipeOverviewFragment");
        g().initViews();
        RecipeOverviewFragment recipeOverviewFragment = (RecipeOverviewFragment) LayoutHelper.getInstance().findFragment(2);
        if (recipeOverviewFragment != null) {
            recipeOverviewFragment.hideRecipeDetail();
        }
        this.containerGuidedCooking.setVisibility(8);
        this.containerRecipes.setVisibility(8);
        this.containerMain.setVisibility(0);
        LayoutHelper.getInstance().setSelectedView(0);
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        if (inputMethodManager == null || getCurrentFocus() == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public final void i() {
        int i = SharedPreferencesHelper.getInstance().getShowLabels() ? 0 : 4;
        this.topLeftLabel.setVisibility(i);
        this.topMiddleLabel.setVisibility(i);
        this.topRightLabel.setVisibility(i);
    }

    public final boolean j() {
        return SharedPreferencesHelper.getInstance().getImportedRecipesVersion() >= 179 && SharedPreferencesHelper.getInstance().getLanguage().equals(SharedPreferencesHelper.getInstance().getImportedRecipesLanguage());
    }

    public /* synthetic */ void k() {
        Rect rect = new Rect();
        this.root.getWindowVisibleDisplayFrame(rect);
        int iHeight = rect.height();
        if (iHeight != this.q) {
            Log.i(w, "onGlobalLayout height change .. " + rect + " >> prev height " + this.q);
            if (iHeight > this.q || LayoutHelper.getInstance().isViewSelected(5)) {
                this.n.height = iHeight;
                this.content.layout(rect.left, rect.top, rect.right, rect.bottom);
                this.content.requestLayout();
            }
            LayoutHelper.applyFullscreen(this.root);
            this.q = iHeight;
        }
    }

    public /* synthetic */ void l() {
        this.t = false;
    }

    public /* synthetic */ void m() {
        a();
        Timer timer2 = this.s;
        if (timer2 != null) {
            timer2.cancel();
            this.s.purge();
        }
        this.i[0] = false;
        this.k[0] = 30;
        this.p = true;
        hideSoftKeyboard();
        TextView textViewShowUserTimeoutDialog = DialogHelper.getInstance().showUserTimeoutDialog(this.dialogUserTimeout, new DialogHelper.SelectionListener() { // from class: i
            @Override // helper.DialogHelper.SelectionListener
            public final void onSelect(int i) {
                this.a.b(i);
            }
        });
        textViewShowUserTimeoutDialog.setText(getString(R.string.dialog_user_timeout_seconds, new Object[]{String.valueOf(30)}));
        Timer timer3 = new Timer();
        this.s = timer3;
        timer3.scheduleAtFixedRate(new n0(this, textViewShowUserTimeoutDialog), 1000L, 1000L);
    }

    public final void n() {
        String localPrivacyTermHTML = ResourceHelper.getLocalPrivacyTermHTML();
        final int documentVersionFromHTML = ResourceHelper.getDocumentVersionFromHTML(localPrivacyTermHTML);
        this.privacyAcknowledge.setAcknowledgeListener(new ActionListener() { // from class: g
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a(documentVersionFromHTML, (DataPrivacyAcknowledgeLayout) obj);
            }
        });
        this.privacyAcknowledge.setHTMLContent(localPrivacyTermHTML);
        this.privacyAcknowledge.markPolicyTermAsChanged(true);
        this.privacyAcknowledge.setVisibility(0);
    }

    public final void o() {
        LayoutHelper.getInstance().setPresetLayoutVisibility(this.root, false);
        this.containerMain.setVisibility(0);
        LayoutHelper.getInstance().setSelectedView(0);
        LayoutHelper.getInstance().setSelectedView(0);
        if (!g().isRunning()) {
            DataHolder.getInstance().resetMachineValues();
            currentGlobalMode = 2;
            g().performStopClick();
        }
        g().initViews();
        this.containerRecipes.setVisibility(8);
    }

    @OnClick({R.id.top_bar_left_button})
    public void onClickLeftTopBarIcon() {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        if (a(new ActionListener() { // from class: b0
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.c((QuestionDialogView) obj);
            }
        })) {
            return;
        }
        c();
    }

    @OnClick({R.id.top_bar_middle_button})
    public void onClickMiddleTopBarIcon() {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        if (a(new ActionListener() { // from class: e0
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.d((QuestionDialogView) obj);
            }
        })) {
            return;
        }
        d();
    }

    @OnClick({R.id.top_bar_right_button})
    public void onClickRightTopBarIcon() {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        if (a(new ActionListener() { // from class: z
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.e((QuestionDialogView) obj);
            }
        })) {
            return;
        }
        e();
    }

    @Override // activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() { // from class: g0
            @Override // java.lang.Runnable
            public final void run() {
                this.a.l();
            }
        }, 60000L);
        this.o = this.content.getViewTreeObserver();
        this.n = (FrameLayout.LayoutParams) this.content.getLayoutParams();
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance();
        sharedPreferencesHelper.setJogDialPushedAtBooting(false);
        App.getInstance().getMachineAdapter().registerMachineCallback(new o0(this));
        Log.i(w, "Registered Jog Dial Callbacks");
        App.getInstance().setSoundVolume(sharedPreferencesHelper.getSoundVolume());
        if (!j()) {
            new ImportRecipesAndImagesTask(this, BuildConfig.VERSION_CODE, new ActionListener() { // from class: n
                @Override // helper.ActionListener
                public final void onAction(Object obj) {
                    this.a.a((Boolean) obj);
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
        ((PresetLayout) findViewById(R.id.preset_layout)).setPresetPlayListener(this);
        LayoutHelper.getInstance().setMainActivityView(this.root);
        LayoutHelper.getInstance().setFragmentManager(getSupportFragmentManager());
        LayoutHelper.getInstance().inflateRecipesFragment();
        LayoutHelper.getInstance().inflateGuidedCookingFragment();
        LayoutHelper.getInstance().inflateMainFragment();
        LayoutHelper.getInstance().setSelectedView(0);
        LayoutHelper.getInstance().addViewSelectionListener(this.m);
        LayoutHelper.applyFullscreen(this.root);
        LayoutHelper.keepScreenOn(this);
        App.getInstance().wakeUpMachine();
        DataHolder.getInstance().initDefaultMachineValues();
        new Thread(new Runnable() { // from class: k0
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.x();
            }
        }).start();
        if (!sharedPreferencesHelper.hasUserAcknowledgedDataPrivacyTerms() || sharedPreferencesHelper.shouldShowFirstLaunch()) {
            LayoutHelper.getInstance().openFirstLaunchFragment();
        }
        checkAndShowTermsOfUseDialog();
        checkAndShowDataPrivacyDialog();
        a(0L);
        Log.i(w, "scheduleImportService");
        triggerImportService();
        Intent intent = new Intent(getApplicationContext(), (Class<?>) RecipeImportService.class);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(NotificationCompat.CATEGORY_ALARM);
        PendingIntent service = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
        int iFloor = (int) Math.floor(Math.random() * 12.0d);
        int iFloor2 = (int) Math.floor(Math.random() * 60.0d);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(10, iFloor);
        calendar.add(12, iFloor2);
        alarmManager.setRepeating(1, calendar.getTimeInMillis(), 43200000L, service);
        resetBookmarkImage();
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        this.v = networkStateReceiver;
        networkStateReceiver.addListener(new q0(this));
        registerReceiver(this.v, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        i();
    }

    @Override // activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        CookingManager.getInstance().stopCooking();
        App.getInstance().getMachineAdapter().shutDown();
        super.onDestroy();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onPause() throws JSONException {
        String str = w;
        StringBuilder sbA = g9.a("onPause [");
        sbA.append(App.getInstance().isMachineSleeping());
        sbA.append("]");
        Log.i(str, sbA.toString());
        McUsageApi.getInstance().postStandbyEvent(App.getInstance().isMachineSleeping(), new b(this));
        super.onPause();
        ViewTreeObserver viewTreeObserver = this.o;
        if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
            return;
        }
        this.o.removeOnGlobalLayoutListener(this.r);
    }

    @Override // view.PresetLayout.PresetPlayListener
    public void onPresetSelected(CookingStep cookingStep) {
        LayoutHelper.getInstance().setSelectedView(11);
        resetBookmarkImage();
        this.containerGuidedCooking.setVisibility(8);
        this.containerRecipes.setVisibility(8);
        MainFragment mainFragmentG = g();
        if (cookingStep != null) {
            mainFragmentG.initCooking(cookingStep);
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        Log.i(w, "onResume");
        super.onResume();
        a(SharedPreferencesHelper.getInstance().getLastSurveyPopupTimestamp());
        LayoutHelper.applyFullscreen(this.root);
        LayoutHelper.keepScreenOn(this);
        if (HardwareLEDService.getInstance().isOff()) {
            HardwareLEDService.getInstance().switchLEDOn(LEDColor.WHITE);
            ICommandInterface commandInterface = App.getInstance().getMachineAdapter().getCommandInterface();
            if (commandInterface != null) {
                commandInterface.start(0, 0, 0, 0);
            }
        }
        ViewTreeObserver viewTreeObserver = this.o;
        if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
            this.o = this.content.getViewTreeObserver();
        }
        this.o.addOnGlobalLayoutListener(this.r);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onStop() {
        try {
            unregisterReceiver(this.v);
        } catch (Exception e) {
            Log.e(w, e.getMessage());
        }
        super.onStop();
    }

    @Override // activity.BaseActivity
    public void onUserInteractionTimeout(int i) {
        Log.d(w, String.format(Locale.ENGLISH, "*** No user interaction for %d seconds", Integer.valueOf(i)));
        if (g().isRunning() || CookingManager.getInstance().getState() == 1 || CookingManager.getInstance().getState() == 2 || LayoutHelper.getInstance().isVideoPlayerFragmentPlaying()) {
            a();
        } else if (i >= 420) {
            if (LayoutHelper.getInstance().isViewSelected(7) && LayoutHelper.getInstance().getSettingsFragment() != null) {
                LayoutHelper.getInstance().getSettingsFragment().cancelAlert();
            }
            this.h.post(new Runnable() { // from class: p
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.m();
                }
            });
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            f();
        } else {
            this.l.removeMessages(0);
        }
    }

    public final void p() {
        LayoutHelper.getInstance().setPresetLayoutVisibility(this.root, true);
        this.containerMain.setVisibility(0);
        LayoutHelper.getInstance().setSelectedView(0);
        this.containerRecipes.setVisibility(8);
        LayoutHelper.getInstance().setSelectedView(1);
    }

    public final void q() {
        findViewById(R.id.top_bar_layout).setVisibility(0);
        Log.d(w, "showRecipeOverview");
        LayoutHelper.getInstance().openRecipeOverview();
        this.containerMain.setVisibility(8);
        this.containerGuidedCooking.setVisibility(8);
        LayoutHelper.getInstance().setPresetLayoutVisibility(this.root, false);
        LayoutHelper.getInstance().setSelectedView(2);
    }

    public final void r() {
        if (!g().isRunning() && CookingManager.getInstance().getState() != 1) {
            q();
        } else {
            CookingManager.getInstance().pauseCooking();
            a(new DialogHelper.SelectionListener() { // from class: y
                @Override // helper.DialogHelper.SelectionListener
                public final void onSelect(int i) {
                    this.a.f(i);
                }
            });
        }
    }

    public void refreshRecipesDisplay() {
        runOnUiThread(new Runnable() { // from class: h
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.y();
            }
        });
    }

    public void resetBookmarkImage() {
        boolean z = SharedPreferencesHelper.getInstance().getRecipeBookmark() != null;
        ImageView imageView = this.bookmarkImageView;
        if (imageView != null) {
            if (z) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    public final void s() {
        LayoutHelper.getInstance().setSettingsFragmentVisibility(true);
        this.containerGuidedCooking.setVisibility(8);
        this.containerRecipes.setVisibility(8);
    }

    public void showNewRecipesDialog(@StringRes final int i) {
        runOnUiThread(new Runnable() { // from class: o
            @Override // java.lang.Runnable
            public final void run() {
                this.a.d(i);
            }
        });
    }

    @SuppressLint({"CheckResult"})
    public void showSurveyDialog(String str, String str2, String str3) {
        Bitmap bitmapGenerateQrCodeBitmap = CommonUtils.generateQrCodeBitmap(str, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        this.surveyDialog.setTitle(str2);
        this.surveyDialog.setCampaignText(str3);
        this.surveyDialog.setImageBitmap(bitmapGenerateQrCodeBitmap);
        this.surveyDialog.setButtonOneClickListener(new ActionListener() { // from class: l
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((SurveyDialogView) obj);
            }
        });
        this.surveyDialog.setButtonTwoClickListener(new ActionListener() { // from class: t
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.b((SurveyDialogView) obj);
            }
        });
        this.surveyDialog.setVisibility(0);
    }

    public final void t() {
        if (!g().isRunning() && CookingManager.getInstance().getState() != 1) {
            s();
        } else {
            CookingManager.getInstance().pauseCooking();
            a(new DialogHelper.SelectionListener() { // from class: j
                @Override // helper.DialogHelper.SelectionListener
                public final void onSelect(int i) {
                    this.a.g(i);
                }
            });
        }
    }

    public void triggerImportService() {
        if (j() && isNetworkConnected()) {
            startService(new Intent(getApplicationContext(), (Class<?>) RecipeImportService.class));
        }
    }

    public final void u() {
        String localTermsOfUseHTML = ResourceHelper.getLocalTermsOfUseHTML();
        final int acceptedTermsOfUseVersion = SharedPreferencesHelper.getInstance().getAcceptedTermsOfUseVersion();
        this.termsOfUseAcknowledgeDialog.setVisibility(0);
        this.termsOfUseAcknowledgeDialog.setTitleText(getString(R.string.settings_terms_of_use));
        this.termsOfUseAcknowledgeDialog.setBodyText(localTermsOfUseHTML);
        this.termsOfUseAcknowledgeDialog.setBodyTextSize(11.0f);
        this.termsOfUseAcknowledgeDialog.setBodyGravity(3);
        this.termsOfUseAcknowledgeDialog.setButtonTwoClickListener(new ActionListener() { // from class: k
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a(acceptedTermsOfUseVersion, (QuestionDialogView) obj);
            }
        });
        this.termsOfUseAcknowledgeDialog.setButtonOneClickListener(new ActionListener() { // from class: h0
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.g((QuestionDialogView) obj);
            }
        });
    }

    public final void v() {
        App.getInstance().getMachineAdapter().getCommandInterface().stop();
    }

    public /* synthetic */ void b(QuestionDialogView questionDialogView) {
        this.dialogRecipesReceived.setVisibility(8);
        q();
        ((RecipeOverviewFragment) LayoutHelper.getInstance().findFragment(2)).showNewRecipes();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void c() {
        /*
            r3 = this;
            java.lang.String r0 = activity.MainActivity.w
            java.lang.String r1 = "clickLeftTopBarIcon >> selectedView "
            java.lang.StringBuilder r1 = defpackage.g9.a(r1)
            helper.LayoutHelper r2 = helper.LayoutHelper.getInstance()
            java.lang.String r2 = r2.getSelectedViewTag()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
            r3.v()
            r3.hideSoftKeyboard()
            r3.f()
            r3.resetBookmarkImage()
            helper.LayoutHelper r0 = helper.LayoutHelper.getInstance()
            r1 = 0
            r0.setSettingsFragmentVisibility(r1)
            helper.LayoutHelper r0 = helper.LayoutHelper.getInstance()
            int r0 = r0.getSelectedView()
            r1 = 1
            if (r0 == 0) goto L67
            if (r0 == r1) goto L63
            r2 = 2
            if (r0 == r2) goto L5f
            r2 = 3
            if (r0 == r2) goto L5b
            r2 = 4
            if (r0 == r2) goto L57
            r2 = 5
            if (r0 == r2) goto L53
            r2 = 7
            if (r0 == r2) goto L4f
            r2 = 11
            if (r0 == r2) goto L67
            goto L8f
        L4f:
            r3.p()
            goto L8f
        L53:
            r3.q()
            goto L8f
        L57:
            r3.r()
            goto L8f
        L5b:
            r3.q()
            goto L8f
        L5f:
            r3.p()
            goto L8f
        L63:
            r3.o()
            goto L8f
        L67:
            fragment.MainFragment r0 = r3.g()
            boolean r0 = r0.isRunning()
            if (r0 != 0) goto L80
            cooking.CookingManager r0 = cooking.CookingManager.getInstance()
            int r0 = r0.getState()
            if (r0 != r1) goto L7c
            goto L80
        L7c:
            r3.p()
            goto L8f
        L80:
            cooking.CookingManager r0 = cooking.CookingManager.getInstance()
            r0.pauseCooking()
            a0 r0 = new a0
            r0.<init>()
            r3.a(r0)
        L8f:
            r3.b()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: activity.MainActivity.c():void");
    }

    public /* synthetic */ void d(int i) {
        this.dialogRecipesReceived.setBodyText(getString(i));
        this.dialogRecipesReceived.setVisibility(0);
        this.dialogRecipesReceived.setButtonOneClickListener(new ActionListener() { // from class: b
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((QuestionDialogView) obj);
            }
        });
        this.dialogRecipesReceived.setButtonTwoClickListener(new ActionListener() { // from class: j0
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.b((QuestionDialogView) obj);
            }
        });
    }

    public final void e() {
        Log.i(w, "clickRightTopBarIcon");
        v();
        hideSoftKeyboard();
        f();
        resetBookmarkImage();
        int selectedView = LayoutHelper.getInstance().getSelectedView();
        if (selectedView == 0 || selectedView == 4) {
            t();
        } else if (selectedView == 7) {
            LayoutHelper.getInstance().setSettingsFragmentVisibility(false);
            this.containerMain.setVisibility(0);
            o();
        } else if (selectedView != 11) {
            s();
        } else {
            t();
        }
        b();
    }

    public /* synthetic */ void g(int i) {
        if (3 != i) {
            CookingManager.getInstance().resumeCooking();
        } else {
            g().processStop();
            s();
        }
    }

    public /* synthetic */ void f(QuestionDialogView questionDialogView) {
        this.bookmarkDialog.setVisibility(8);
    }

    public /* synthetic */ void f(int i) {
        if (3 == i) {
            g().processStop();
            q();
        } else {
            CookingManager.getInstance().resumeCooking();
        }
    }

    public /* synthetic */ void b(SurveyDialogView surveyDialogView) {
        this.surveyDialog.setVisibility(4);
    }

    public /* synthetic */ void g(QuestionDialogView questionDialogView) {
        this.termsOfUseAcknowledgeDialog.setVisibility(8);
    }

    public /* synthetic */ void b(int i) {
        a(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void d() {
        /*
            r3 = this;
            java.lang.String r0 = activity.MainActivity.w
            java.lang.String r1 = "clickMiddleTopBarIcon"
            android.util.Log.i(r0, r1)
            r3.v()
            r3.hideSoftKeyboard()
            r3.f()
            android.widget.FrameLayout r0 = r3.root
            helper.LayoutHelper.applyFullscreen(r0)
            helper.LayoutHelper.keepScreenOn(r3)
            r3.resetBookmarkImage()
            helper.LayoutHelper r0 = helper.LayoutHelper.getInstance()
            r1 = 0
            r0.setSettingsFragmentVisibility(r1)
            helper.LayoutHelper r0 = helper.LayoutHelper.getInstance()
            int r0 = r0.getSelectedView()
            if (r0 == 0) goto L79
            r1 = 1
            if (r0 == r1) goto L79
            r2 = 2
            if (r0 == r2) goto L75
            r2 = 3
            if (r0 == r2) goto L71
            r2 = 4
            if (r0 == r2) goto L48
            r1 = 5
            if (r0 == r1) goto L44
            r1 = 7
            if (r0 == r1) goto L79
            r1 = 11
            if (r0 == r1) goto L79
            goto L7c
        L44:
            r3.h()
            goto L7c
        L48:
            fragment.MainFragment r0 = r3.g()
            boolean r0 = r0.isRunning()
            if (r0 != 0) goto L61
            cooking.CookingManager r0 = cooking.CookingManager.getInstance()
            int r0 = r0.getState()
            if (r0 != r1) goto L5d
            goto L61
        L5d:
            r3.h()
            goto L7c
        L61:
            cooking.CookingManager r0 = cooking.CookingManager.getInstance()
            r0.pauseCooking()
            d0 r0 = new d0
            r0.<init>()
            r3.a(r0)
            goto L7c
        L71:
            r3.h()
            goto L7c
        L75:
            r3.h()
            goto L7c
        L79:
            r3.r()
        L7c:
            r3.b()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: activity.MainActivity.d():void");
    }

    public final void b() {
        g().cancelLastKnobSelection();
    }

    public /* synthetic */ void b(int i, int i2, String str) throws Exception {
        int documentVersionFromHTML = ResourceHelper.getDocumentVersionFromHTML(str);
        if (documentVersionFromHTML > i) {
            ResourceHelper.storeLocalTermsOfUseHTML(str);
        }
        if (Math.max(documentVersionFromHTML, i) > i2) {
            u();
        }
    }

    public /* synthetic */ void b(int i, int i2, Throwable th) throws Exception {
        if (i > i2) {
            u();
        }
    }

    public /* synthetic */ void a(int i) {
        if (3 == i) {
            g().processStop();
            LayoutHelper.getInstance().openGuidedCooking(DbHelper.getInstance().getRecipeById(SharedPreferencesHelper.getInstance().getRecipeBookmark().getRecipeId()));
            ((GuidedCookingFragment) LayoutHelper.getInstance().findFragment(4)).checkAndScrollToBookmark();
            return;
        }
        CookingManager.getInstance().resumeCooking();
    }

    public /* synthetic */ void e(int i) {
        if (3 == i) {
            g().processStop();
            p();
        } else {
            CookingManager.getInstance().resumeCooking();
        }
    }

    public /* synthetic */ void a(QuestionDialogView questionDialogView) {
        this.dialogRecipesReceived.setVisibility(8);
    }

    public /* synthetic */ void c(int i) {
        if (3 == i) {
            g().processStop();
            h();
        } else {
            CookingManager.getInstance().resumeCooking();
        }
    }

    public /* synthetic */ void a(SurveyDialogView surveyDialogView) {
        this.surveyDialog.setVisibility(4);
        NewMcApi.getInstance().deleteCampaignMessage().subscribe(new Action() { // from class: f0
            @Override // io.reactivex.functions.Action
            public final void run() {
                Log.d(MainActivity.w, "Deleted campaign message successfully");
            }
        }, new Consumer() { // from class: e
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                MainActivity.b((Throwable) obj);
            }
        });
    }

    public /* synthetic */ void a(Boolean bool) {
        refreshRecipesDisplay();
        triggerImportService();
    }

    public static /* synthetic */ void a(MainActivity mainActivity, boolean z) {
        if (!mainActivity.g().isRunning() && CookingManager.getInstance().getState() != 1 && CookingManager.getInstance().getState() != 2 && !LayoutHelper.getInstance().isVideoPlayerFragmentPlaying()) {
            HardwareLEDService.getInstance().turnOff();
            App.getInstance().sleep(z);
        } else {
            mainActivity.a();
        }
    }

    public final void a(boolean z) {
        this.i[0] = true;
        Timer timer2 = this.s;
        if (timer2 != null) {
            timer2.cancel();
            this.s.purge();
            this.s = null;
        }
        a();
        if (z) {
            this.dialogUserTimeout.setVisibility(8);
            this.p = false;
        } else {
            this.p = false;
        }
        String str = w;
        StringBuilder sbA = g9.a("timeout dialog cancelled ");
        sbA.append(this.k[0]);
        Log.v(str, sbA.toString());
    }

    public static /* synthetic */ void a(MainActivity mainActivity) throws JSONException {
        if (mainActivity != null) {
            if (System.currentTimeMillis() < SharedPreferencesHelper.getInstance().getLastInternetCheckTimestamp() + 300000) {
                return;
            }
            SharedPreferencesHelper.getInstance().setLastInternetCheckTimestamp(System.currentTimeMillis());
            McUsageApi.getInstance().postConnectionCheckEvent(new p0(mainActivity));
            return;
        }
        throw null;
    }

    @SuppressLint({"CheckResult"})
    public final void a(long j) {
        if (SharedPreferencesHelper.getInstance().shouldShowSurveyDialogs()) {
            final long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - j < DateUtils.MILLIS_PER_DAY) {
                return;
            }
            NewMcApi.getInstance().getMessageFromServer().subscribe(new Consumer() { // from class: f
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.a.a(jCurrentTimeMillis, (CampaignMessage) obj);
                }
            }, new Consumer() { // from class: m
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    MainActivity.a((Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void a(long j, CampaignMessage campaignMessage) throws Exception {
        if (campaignMessage == null) {
            Log.d(w, "No campaign message. Skip the survey dialog");
            return;
        }
        Log.d(w, "Received a campaign message. Showing the survey dialog now");
        String url = campaignMessage.getURL();
        String str = campaignMessage.display_text;
        String str2 = campaignMessage.display_title;
        SharedPreferencesHelper.getInstance().setLastSurveyPopupTimestamp(j);
        showSurveyDialog(url, str2, str);
    }

    public final boolean a(final ActionListener<QuestionDialogView> actionListener) {
        final GuidedCookingFragment guidedCookingFragment = (GuidedCookingFragment) LayoutHelper.getInstance().findFragment(4);
        if (LayoutHelper.getInstance().getSelectedView() != 4) {
            return false;
        }
        if (guidedCookingFragment.isFirstOrLastStepPosition()) {
            actionListener.onAction(this.bookmarkDialog);
            return true;
        }
        String currentRecipeName = guidedCookingFragment.getCurrentRecipeName();
        int currentPosition = guidedCookingFragment.getCurrentPosition() + 1;
        this.bookmarkDialog.setButtonOneClickListener(new ActionListener() { // from class: l0
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.f((QuestionDialogView) obj);
            }
        });
        this.bookmarkDialog.setButtonTwoClickListener(new ActionListener() { // from class: w
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                MainActivity.a(guidedCookingFragment, actionListener, (QuestionDialogView) obj);
            }
        });
        this.bookmarkDialog.setButtonThreeClickListener(new ActionListener() { // from class: i0
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                MainActivity.a(actionListener, (QuestionDialogView) obj);
            }
        });
        this.bookmarkDialog.setVisibility(0);
        this.bookmarkDialog.setBodyText(String.format(getString(R.string.recipe_bookmark_question_dialog), currentRecipeName, Integer.valueOf(currentPosition)));
        return true;
    }

    public static /* synthetic */ void a(GuidedCookingFragment guidedCookingFragment, ActionListener actionListener, QuestionDialogView questionDialogView) {
        guidedCookingFragment.saveBookmark();
        actionListener.onAction(questionDialogView);
        questionDialogView.setVisibility(8);
    }

    public static /* synthetic */ void a(ActionListener actionListener, QuestionDialogView questionDialogView) {
        actionListener.onAction(questionDialogView);
        questionDialogView.setVisibility(8);
    }

    public final void a(@NonNull DialogHelper.SelectionListener selectionListener) {
        int i;
        CookingStep initCookingStep = CookingManager.getInstance().getInitCookingStep();
        String str = null;
        if (Presets.defaultCookingStep().equals(initCookingStep)) {
            i = 3;
        } else {
            if (Presets.kneadingCookingUnit().equals(initCookingStep)) {
                str = Presets.Tags.KNEADING;
            } else if (Presets.roastingCookingUnit().equals(initCookingStep)) {
                str = Presets.Tags.ROASTING;
            } else {
                if (Presets.steamingCookingSteps().equals(initCookingStep)) {
                    str = Presets.Tags.STEAMING;
                }
                i = 3;
            }
            i = 10;
        }
        String str2 = str;
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.dialog_stop);
        if (relativeLayout != null) {
            DialogHelper.getInstance().showStopDialog(this, i, str2, relativeLayout, selectionListener);
        }
    }

    public /* synthetic */ void a(int i, int i2, String str) throws Exception {
        int documentVersionFromHTML = ResourceHelper.getDocumentVersionFromHTML(str);
        Log.i(w, "Found data privacy version in HTML. online-version=" + documentVersionFromHTML + ", local-version=" + i + ", ack-version=" + i2);
        if (documentVersionFromHTML > i) {
            ResourceHelper.storeLocalPrivacyTermHTML(str);
        }
        if (Math.max(i, documentVersionFromHTML) > i2) {
            n();
        }
    }

    public /* synthetic */ void a(int i, int i2, Throwable th) throws Exception {
        if (i > i2) {
            n();
        }
    }

    public /* synthetic */ void a(int i, DataPrivacyAcknowledgeLayout dataPrivacyAcknowledgeLayout) {
        SharedPreferencesHelper.getInstance().userAcknowledgedDataPrivacyTerms();
        SharedPreferencesHelper.getInstance().acceptDataPrivacyPolicy(true);
        SharedPreferencesHelper.getInstance().setAcceptedPrivacyPolicyVersion(i);
        this.privacyAcknowledge.setVisibility(8);
        McApi.getInstance().updateDataPrivacyOptions();
        this.privacyAcknowledge.markPolicyTermAsChanged(false);
    }

    public /* synthetic */ void a(final int i, final int i2, int i3, UserDataResponse userDataResponse, Exception exc) {
        if (exc == null && i3 == 200 && userDataResponse != null) {
            NewMcApi.getInstance().getTermsOfUseHTML().subscribe(new Consumer() { // from class: r
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.a.b(i, i2, (String) obj);
                }
            }, new Consumer() { // from class: v
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.a.b(i, i2, (Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void a(int i, QuestionDialogView questionDialogView) {
        this.termsOfUseAcknowledgeDialog.setVisibility(8);
        SharedPreferencesHelper.getInstance().setAcceptedTermsOfUseVersion(i);
        McApi.getInstance().updateDataPrivacyOptions();
    }
}
