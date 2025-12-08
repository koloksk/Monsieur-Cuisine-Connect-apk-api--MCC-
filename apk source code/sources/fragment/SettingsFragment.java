package fragment;

import adapter.FaqAdapter;
import adapter.HelpContentItemAdapter;
import adapter.LanguageAdapter;
import adapter.ScanResultAdapter;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import application.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import db.DbHelper;
import db.RecipeType;
import de.silpion.mc2.R;
import de.silpion.mcupdater.IUpdateService;
import de.silpion.mcupdater.UpdateServiceAdapter;
import defpackage.ak;
import defpackage.bk;
import defpackage.ck;
import defpackage.g9;
import defpackage.xh;
import defpackage.yj;
import defpackage.zj;
import fragment.SettingsFragment;
import helper.ActionListener;
import helper.CommonUtils;
import helper.DialogHelper;
import helper.EncryptionChipServiceAdapter;
import helper.LayoutHelper;
import helper.RecipeAssetsHelper;
import helper.ResourceHelper;
import helper.SharedPreferencesHelper;
import helper.SystemProperties;
import helper.WifiHelper;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import machineAdapter.ICommandInterface;
import machineAdapter.adapter.MachineCallbackAdapter;
import mcapi.McApi;
import mcapi.McUsageApi;
import model.HelpContentVideo;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import sound.SoundLength;
import view.ProfileLayout;
import view.QuestionDialogView;
import view.QuicksandTextView;

/* loaded from: classes.dex */
public class SettingsFragment extends BaseFragment {
    public static final String Q = SettingsFragment.class.getSimpleName();
    public UpdateServiceAdapter.StateListener A;
    public ImageButton C;
    public RelativeLayout D;
    public NestedScrollView E;
    public Switch G;
    public Switch H;
    public Switch I;
    public View J;
    public WifiHelper L;
    public Switch M;
    public TextView N;
    public ListView O;
    public ProgressBar P;

    @BindView(R.id.about_root_container)
    public View aboutRowView;

    @BindView(R.id.sub_settings_bg)
    public ImageView bgImageView;

    @BindView(R.id.fragment_settings_bottom_container_ll)
    public View bottomBtnContainer;
    public AlertDialog d;
    public View.OnClickListener e;
    public View.OnClickListener f;

    @BindView(R.id.faq_root_container)
    public View faqRowView;
    public View.OnClickListener g;
    public QuestionDialogView h;
    public QuestionDialogView i;
    public View j;
    public ImageButton k;

    @BindView(R.id.language_root_container)
    public View languageRowView;
    public RelativeLayout m;
    public WifiHelper.WifiInfoView o;
    public ImageButton q;
    public ProfileLayout r;

    @BindView(R.id.rows_container)
    public ViewGroup rowsContainer;
    public RelativeLayout s;

    @BindView(R.id.show_labels_root_container)
    public View showLabelsRowView;
    public ScanResultAdapter u;
    public SharedPreferencesHelper w;

    @BindView(R.id.wifi_root_container)
    public View wifiRowView;
    public RelativeLayout x;
    public ImageView y;
    public int c = 0;
    public boolean l = false;
    public boolean n = false;
    public int p = 0;
    public int t = 0;
    public boolean v = false;
    public int z = 1;
    public Toast B = null;
    public final MachineCallbackAdapter F = new a();
    public int K = 0;

    public class a extends MachineCallbackAdapter {
        public a() {
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialTurned(int i, long j) {
            int i2;
            SettingsFragment settingsFragment = SettingsFragment.this;
            if (settingsFragment.z != 4 || settingsFragment.E == null) {
                return;
            }
            if (i == 0) {
                i2 = (int) (0 - ((50 * j) * j));
            } else if (1 == i) {
                i2 = (int) ((50 * j * j) + 0);
            } else {
                i2 = 0;
            }
            SettingsFragment.this.E.scrollBy(0, i2);
        }
    }

    public class b extends QuestionDialogView.LinkHandler {
        public b() {
        }

        @Override // view.QuestionDialogView.LinkHandler
        public void onLinkClicked(String str) {
            if (str.equals("POPUP")) {
                SettingsFragment.this.j();
            }
        }
    }

    public class c {
        public long a;
        public Date b;
        public String c;

        public /* synthetic */ c(SettingsFragment settingsFragment, a aVar) {
        }
    }

    public static /* synthetic */ void h(QuestionDialogView questionDialogView) {
        if (questionDialogView.isButtonOneVisible()) {
            questionDialogView.setButtonTwoEnabled(questionDialogView.isCheckboxChecked());
        }
    }

    public void activate() {
        Log.i(Q, "activate");
        if (this.E != null) {
            QuicksandTextView quicksandTextView = (QuicksandTextView) this.s.findViewById(R.id.wifi_row_back);
            this.M.setVisibility(8);
            this.O.setVisibility(8);
            quicksandTextView.setText(getString(R.string.change));
            this.e.onClick(this.aboutRowView);
            this.f.onClick(this.faqRowView);
            this.g.onClick(this.languageRowView);
            k();
            this.E.fullScroll(33);
        }
        new Thread(new Runnable() { // from class: sj
            @Override // java.lang.Runnable
            public final void run() {
                this.a.i();
            }
        }).start();
    }

    public /* synthetic */ void b(QuestionDialogView questionDialogView) {
        SharedPreferencesHelper.getInstance().acceptDataPrivacyPolicy(questionDialogView.isCheckboxChecked());
        if (!questionDialogView.isCheckboxChecked()) {
            this.M.setChecked(false);
            this.G.setChecked(false);
            this.H.setChecked(false);
        }
        questionDialogView.setButtonOneVisibility(0);
        questionDialogView.setButtonTwoText(getString(R.string.next));
        questionDialogView.setButtonTwoEnabled(questionDialogView.isCheckboxChecked());
        questionDialogView.setVisibility(8);
    }

    public /* synthetic */ void c(QuestionDialogView questionDialogView) {
        j();
    }

    public void cancelAlert() {
        AlertDialog alertDialog = this.d;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.d = null;
        }
    }

    public /* synthetic */ void d() {
        RecipeAssetsHelper.clearRecipeAssets(getContext());
        DbHelper.getInstance().clearDatabase(getContext());
        System.exit(0);
    }

    public void deactivate() {
        Log.i(Q, "deactivate");
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.F);
    }

    public /* synthetic */ void e() throws PackageManager.NameNotFoundException {
        String str;
        final String str2;
        final String str3;
        String strValueOf;
        String str4 = "";
        try {
            PackageInfo packageInfo = App.getInstance().getPackageManager().getPackageInfo(App.getInstance().getPackageName(), 0);
            if (packageInfo != null) {
                str = packageInfo.versionName;
                try {
                    strValueOf = String.valueOf(packageInfo.versionCode);
                    str4 = str;
                } catch (PackageManager.NameNotFoundException e) {
                    e = e;
                    e.printStackTrace();
                    str2 = "";
                    str3 = str;
                    final String str5 = SystemProperties.get("ro.custom.build.version", "v1.0_");
                    final String sESerial = EncryptionChipServiceAdapter.getInstance().getSESerial(" - ");
                    final Bitmap bitmapGenerateQrCodeBitmap = CommonUtils.generateQrCodeBitmap(Build.SERIAL, 256);
                    b();
                    this.s.post(new Runnable() { // from class: ui
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.a.a(str3, str2, str5, bitmapGenerateQrCodeBitmap, sESerial);
                        }
                    });
                }
            } else {
                strValueOf = "";
            }
            str3 = str4;
            str2 = strValueOf;
        } catch (PackageManager.NameNotFoundException e2) {
            e = e2;
            str = "";
        }
        final String str52 = SystemProperties.get("ro.custom.build.version", "v1.0_");
        final String sESerial2 = EncryptionChipServiceAdapter.getInstance().getSESerial(" - ");
        final Bitmap bitmapGenerateQrCodeBitmap2 = CommonUtils.generateQrCodeBitmap(Build.SERIAL, 256);
        b();
        this.s.post(new Runnable() { // from class: ui
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(str3, str2, str52, bitmapGenerateQrCodeBitmap2, sESerial2);
            }
        });
    }

    public /* synthetic */ void i(View view2) throws SecurityException {
        a(this.q);
    }

    public /* synthetic */ void j(View view2) throws SecurityException {
        a(this.k);
    }

    public /* synthetic */ void k(View view2) throws SecurityException {
        a(this.C);
    }

    public /* synthetic */ void l(View view2) {
        this.E.scrollTo(0, view2.getTop());
    }

    public final void m() {
        if (this.L.startScan(getContext(), new WifiHelper.ScanResultsListener() { // from class: fh
            @Override // helper.WifiHelper.ScanResultsListener
            public final void scanResultsAvailable() {
                this.a.g();
            }
        })) {
            this.P.setVisibility(0);
        }
    }

    public final void n() {
        this.G.setChecked(this.w.shouldSendTrackingData());
        this.H.setChecked(this.w.isNewsletterSubscribed());
        this.I.setChecked(this.w.shouldShowSurveyDialogs());
        ((QuestionDialogView) this.s.findViewById(R.id.data_privacy_question_dialog)).setVisibility(8);
    }

    public final void o() {
        if (this.N.isSelected()) {
            String connectedSSID = this.L.getConnectedSSID();
            if (TextUtils.isEmpty(connectedSSID)) {
                this.N.setText(getString(R.string.choose_network));
                return;
            } else {
                this.N.setText(connectedSSID);
                return;
            }
        }
        String connectedSSID2 = this.L.getConnectedSSID();
        if (TextUtils.isEmpty(connectedSSID2)) {
            this.N.setText(getString(R.string.choose_network_click));
        } else {
            this.N.setText(connectedSSID2);
        }
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) throws SecurityException {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.fragment_settings, viewGroup, false);
        this.s = relativeLayout;
        this.r = (ProfileLayout) relativeLayout.findViewById(R.id.settings_profile);
        this.x = (RelativeLayout) this.s.findViewById(R.id.shopping_container_rl);
        this.m = (RelativeLayout) this.s.findViewById(R.id.help_container_rl);
        this.D = (RelativeLayout) this.s.findViewById(R.id.sub_settings_container_rl);
        this.E = (NestedScrollView) this.s.findViewById(R.id.sub_settings_sv);
        this.O = (ListView) this.s.findViewById(R.id.wifi_scan_results);
        this.q = (ImageButton) this.s.findViewById(R.id.fragment_settings_profile_ib);
        this.k = (ImageButton) this.s.findViewById(R.id.fragment_settings_help_ib);
        this.C = (ImageButton) this.s.findViewById(R.id.fragment_settings_sub_ib);
        this.G = (Switch) this.s.findViewById(R.id.switch_send_tracking_data_to_server);
        this.H = (Switch) this.s.findViewById(R.id.switch_subscribe_newsletter);
        this.I = (Switch) this.s.findViewById(R.id.switch_show_survey_dialog);
        this.C.setSelected(true);
        this.q.setOnClickListener(new View.OnClickListener() { // from class: ai
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws SecurityException {
                this.a.i(view2);
            }
        });
        this.k.setOnClickListener(new View.OnClickListener() { // from class: li
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws SecurityException {
                this.a.j(view2);
            }
        });
        this.C.setOnClickListener(new View.OnClickListener() { // from class: fj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws SecurityException {
                this.a.k(view2);
            }
        });
        this.w = SharedPreferencesHelper.getInstance();
        this.i = (QuestionDialogView) this.s.findViewById(R.id.factory_reset_question);
        QuestionDialogView questionDialogView = (QuestionDialogView) this.s.findViewById(R.id.data_privacy_policy_question);
        this.h = questionDialogView;
        questionDialogView.setCheckboxCheckedListener(new ActionListener() { // from class: ji
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                SettingsFragment.h((QuestionDialogView) obj);
            }
        });
        this.h.setButtonTwoClickListener(new ActionListener() { // from class: kh
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.b((QuestionDialogView) obj);
            }
        });
        this.h.setButtonOneClickListener(new ActionListener() { // from class: wh
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                SettingsFragment.i((QuestionDialogView) obj);
            }
        });
        this.h.setCheckboxLinkClickListener(new ActionListener() { // from class: bi
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.c((QuestionDialogView) obj);
            }
        });
        this.h.setVisibility(8);
        b(this.C);
        View viewFindViewById = this.s.findViewById(R.id.setting_subscribe_newsletter_container);
        if ("de".equals(SharedPreferencesHelper.getInstance().getLanguage())) {
            viewFindViewById.setVisibility(0);
        } else {
            viewFindViewById.setVisibility(8);
        }
        viewFindViewById.setVisibility(8);
        this.r.setOnTermDialogRequestedCallback(new ActionListener() { // from class: oh
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((ProfileLayout) obj);
            }
        });
        ButterKnife.bind(this, this.s);
        return this.s;
    }

    @Override // android.support.v4.app.Fragment
    public void onStart() throws Resources.NotFoundException {
        super.onStart();
        if (this.n) {
            return;
        }
        this.n = true;
        Log.i(Q, ">> initWifi");
        this.M = (Switch) this.s.findViewById(R.id.wifi_on_sw);
        QuicksandTextView quicksandTextView = (QuicksandTextView) this.s.findViewById(R.id.wifi_wrong_password_error);
        this.u = new ScanResultAdapter(getContext(), R.layout.item_scan_result, new ArrayList());
        WifiHelper wifiHelper = WifiHelper.getInstance(new WifiHelper.NetworkStateListener() { // from class: wi
            @Override // helper.WifiHelper.NetworkStateListener
            public final void networkStateChanged(String str, String str2) {
                this.a.a(str, str2);
            }
        }, new bk(this, quicksandTextView));
        this.L = wifiHelper;
        this.M.setChecked(wifiHelper.isWifiEnabled() && this.w.isDataPrivacyPolicyAccepted());
        if (!this.w.isDataPrivacyPolicyAccepted()) {
            this.L.setWifiOn(false);
            this.M.setChecked(false);
        }
        this.u.setWifiHelper(this.L);
        this.O.setAdapter((ListAdapter) this.u);
        ProgressBar progressBar = (ProgressBar) this.s.findViewById(R.id.wifi_scanning_progress);
        this.P = progressBar;
        progressBar.setVisibility(8);
        this.M.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: yh
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.a.d(compoundButton, z);
            }
        });
        this.O.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: si
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view2, int i, long j) {
                this.a.a(adapterView, view2, i, j);
            }
        });
        this.O.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: sh
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public final boolean onItemLongClick(AdapterView adapterView, View view2, int i, long j) {
                return this.a.b(adapterView, view2, i, j);
            }
        });
        this.N = (TextView) this.s.findViewById(R.id.wifi_row_what_tv);
        o();
        final QuicksandTextView quicksandTextView2 = (QuicksandTextView) this.s.findViewById(R.id.wifi_row_back);
        this.N.setOnClickListener(new View.OnClickListener() { // from class: ki
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(quicksandTextView2, view2);
            }
        });
        quicksandTextView2.setOnClickListener(new View.OnClickListener() { // from class: yi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.b(quicksandTextView2, view2);
            }
        });
        new Thread(new Runnable() { // from class: nh
            @Override // java.lang.Runnable
            public final void run() {
                this.a.f();
            }
        }).start();
        Log.i(Q, ">> initSound");
        SeekBar seekBar = (SeekBar) this.s.findViewById(R.id.seek_bar);
        int soundVolume = SharedPreferencesHelper.getInstance().getSoundVolume();
        Log.i(Q, "soundVolume from SharedPreferences: " + soundVolume);
        seekBar.setProgress(soundVolume);
        ImageView imageView = (ImageView) this.s.findViewById(R.id.sound_iv);
        this.y = imageView;
        if (soundVolume <= 0) {
            imageView.setImageResource(R.drawable.asset_002_sound_disabled);
        } else {
            imageView.setImageResource(R.drawable.asset_002_sound);
        }
        seekBar.setOnSeekBarChangeListener(new ak(this));
        Log.i(Q, ">> initAbout");
        a();
        Log.i(Q, ">> initTerms");
        final RelativeLayout relativeLayout = (RelativeLayout) this.s.findViewById(R.id.terms_root_container);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.terms_row_what_tv);
        TextView textView2 = (TextView) relativeLayout.findViewById(R.id.terms_row_back);
        new Thread(new Runnable() { // from class: rh
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(relativeLayout);
            }
        }).start();
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: zg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.g(view2);
            }
        };
        textView.setOnClickListener(onClickListener);
        textView2.setOnClickListener(onClickListener);
        RelativeLayout relativeLayout2 = (RelativeLayout) this.s.findViewById(R.id.terms_of_use_root_container);
        TextView textView3 = (TextView) relativeLayout2.findViewById(R.id.terms_of_use_what_tv);
        TextView textView4 = (TextView) relativeLayout2.findViewById(R.id.terms_of_use_back);
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: gj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.h(view2);
            }
        };
        textView3.setOnClickListener(onClickListener2);
        textView4.setOnClickListener(onClickListener2);
        Log.i(Q, ">> initFaq");
        final RecyclerView recyclerView = (RecyclerView) this.s.findViewById(R.id.faq_row_rv);
        final FaqAdapter faqAdapter = new FaqAdapter(getContext(), (List) new Gson().fromJson(RecipeAssetsHelper.readTextFile(getContext(), R.raw.faq_v002), new ck(this).getType()));
        recyclerView.setAdapter(faqAdapter);
        faqAdapter.setFaqItemExpandListener(new FaqAdapter.FaqItemExpandListener() { // from class: di
            @Override // adapter.FaqAdapter.FaqItemExpandListener
            public final void onFaqItemExpanded(View view2) {
                this.a.a(recyclerView, view2);
            }
        });
        RelativeLayout relativeLayout3 = (RelativeLayout) this.s.findViewById(R.id.faq_root_container);
        final TextView textView5 = (TextView) relativeLayout3.findViewById(R.id.faq_row_what_tv);
        final TextView textView6 = (TextView) relativeLayout3.findViewById(R.id.faq_row_back);
        TextView textView7 = (TextView) relativeLayout3.findViewById(R.id.faq_row_open);
        final RelativeLayout relativeLayout4 = (RelativeLayout) relativeLayout3.findViewById(R.id.faq_container);
        final RelativeLayout relativeLayout5 = (RelativeLayout) relativeLayout3.findViewById(R.id.left_row);
        final RelativeLayout relativeLayout6 = (RelativeLayout) relativeLayout3.findViewById(R.id.middle_row);
        final ViewGroup viewGroup = (ViewGroup) relativeLayout3.findViewById(R.id.right_row);
        View.OnClickListener onClickListener3 = new View.OnClickListener() { // from class: xi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(textView5, relativeLayout6, relativeLayout4, relativeLayout5, viewGroup, textView6, view2);
            }
        };
        textView5.setOnClickListener(onClickListener3);
        this.f = new View.OnClickListener() { // from class: dh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(faqAdapter, relativeLayout6, textView5, relativeLayout4, relativeLayout5, viewGroup, textView6, view2);
            }
        };
        textView7.setOnClickListener(onClickListener3);
        textView6.setOnClickListener(this.f);
        Log.i(Q, ">> initFactoryReset");
        RelativeLayout relativeLayout7 = (RelativeLayout) this.s.findViewById(R.id.factory_reset_root_container);
        ((TextView) relativeLayout7.findViewById(R.id.factory_reset_row_what_tv)).setOnClickListener(new View.OnClickListener() { // from class: fi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.d(view2);
            }
        });
        ((TextView) relativeLayout7.findViewById(R.id.factory_reset_row_back)).setOnClickListener(new View.OnClickListener() { // from class: gh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.e(view2);
            }
        });
        this.i.setButtonOneClickListener(new ActionListener() { // from class: hi
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                ((QuestionDialogView) obj).setVisibility(8);
            }
        });
        this.i.setButtonTwoClickListener(new ActionListener() { // from class: pi
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((QuestionDialogView) obj);
            }
        });
        Log.i(Q, ">> initDataPrivacySettings");
        n();
        McApi.getInstance().updateDataPrivacyOptions();
        this.G.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: cj
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.a.a(compoundButton, z);
            }
        });
        this.H.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: ph
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.a.b(compoundButton, z);
            }
        });
        this.I.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: th
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SharedPreferencesHelper.getInstance().setShowSurveyDialogs(z);
            }
        });
        Log.i(Q, ">> initCountrySelector");
        final ArrayList arrayList = new ArrayList();
        for (String str : getResources().getStringArray(R.array.supported_languages)) {
            arrayList.add(new Locale(str));
        }
        RelativeLayout relativeLayout8 = (RelativeLayout) this.s.findViewById(R.id.language_root_container);
        QuicksandTextView quicksandTextView3 = (QuicksandTextView) relativeLayout8.findViewById(R.id.language_row_what_tv);
        String displayLanguage = new Locale(SharedPreferencesHelper.getInstance().getLanguage()).getDisplayLanguage();
        quicksandTextView3.setText(String.format("%s%s", displayLanguage.substring(0, 1).toUpperCase(), displayLanguage.substring(1)));
        final LinearLayout linearLayout = (LinearLayout) relativeLayout8.findViewById(R.id.language_container);
        final QuicksandTextView quicksandTextView4 = (QuicksandTextView) relativeLayout8.findViewById(R.id.language_row_back);
        this.g = new View.OnClickListener() { // from class: eh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(linearLayout, quicksandTextView4, view2);
            }
        };
        quicksandTextView4.setOnClickListener(new View.OnClickListener() { // from class: gi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.b(linearLayout, quicksandTextView4, view2);
            }
        });
        ListView listView = (ListView) relativeLayout8.findViewById(R.id.language_row_lv);
        listView.setAdapter((ListAdapter) new LanguageAdapter(getContext(), R.layout.item_language, arrayList));
        final QuestionDialogView questionDialogView = (QuestionDialogView) this.s.findViewById(R.id.confirm_change_locale);
        questionDialogView.setButtonOneClickListener(new ActionListener() { // from class: mi
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                ((QuestionDialogView) obj).setVisibility(8);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: mh
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view2, int i, long j) {
                SettingsFragment.a(questionDialogView, arrayList, adapterView, view2, i, j);
            }
        });
        Log.i(Q, ">> initShowLabels");
        ToggleButton toggleButton = (ToggleButton) this.showLabelsRowView.findViewById(R.id.show_labels_toggle);
        toggleButton.setChecked(this.w.getShowLabels());
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: hh
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.a.c(compoundButton, z);
            }
        });
        Log.i(Q, ">> initScaleCalibration");
        View viewFindViewById = this.faqRowView.findViewById(R.id.middle_row);
        this.j = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: lh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.f(view2);
            }
        });
    }

    @Override // fragment.BaseFragment, android.support.v4.app.Fragment
    public void onViewCreated(@NonNull View view2, Bundle bundle) {
        Log.i(Q, "onViewCreated");
        super.onViewCreated(view2, bundle);
    }

    public void updateMachineConfigView() {
        a();
    }

    public static /* synthetic */ void i(QuestionDialogView questionDialogView) {
        questionDialogView.setCheckboxChecked(SharedPreferencesHelper.getInstance().isDataPrivacyPolicyAccepted());
        questionDialogView.setVisibility(8);
    }

    public /* synthetic */ void a(ProfileLayout profileLayout) {
        j();
    }

    public /* synthetic */ void c(View view2) {
        this.v = !this.v;
        this.s.findViewById(R.id.qr_image_view).setVisibility(this.v ? 0 : 8);
    }

    public /* synthetic */ void f(View view2) {
        int i = this.t + 1;
        this.t = i;
        if (i % 5 == 0) {
            LayoutHelper.getInstance().openScaleCalibrationFragment();
        }
    }

    public /* synthetic */ void g(View view2) {
        j();
    }

    public final void j() {
        l();
        QuicksandTextView quicksandTextView = (QuicksandTextView) this.J.findViewById(R.id.terms_row_content_tv);
        quicksandTextView.setText(Html.fromHtml(ResourceHelper.getLocalPrivacyTermHTML()));
        quicksandTextView.setMovementMethod(new b());
    }

    public final void k() {
        Log.d(Q, "setRowsVisibility: isVis=true");
        a(true, (View) null);
    }

    public final void l() {
        this.J = getActivity().getLayoutInflater().inflate(R.layout.terms_row_firstlaunch, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(this.J);
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() { // from class: ej
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.a.a(dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(alertDialogCreate.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -1;
        this.d = alertDialogCreate;
        alertDialogCreate.show();
        alertDialogCreate.getWindow().setAttributes(layoutParams);
    }

    public final void a(ImageButton imageButton) throws SecurityException {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        b(imageButton);
    }

    public /* synthetic */ void g() {
        this.u.clear();
        this.u.notifyDataSetChanged();
        this.u.addAll(this.L.getScanResults());
        this.P.setVisibility(8);
    }

    public /* synthetic */ void h(View view2) {
        l();
        ((QuicksandTextView) this.J.findViewById(R.id.terms_row_content_tv)).setText(Html.fromHtml(ResourceHelper.getLocalTermsOfUseHTML()));
    }

    public /* synthetic */ void c(String str) {
        if (str.equals("POPUP")) {
            j();
        }
    }

    public final void i() {
        App.getInstance().getMachineAdapter().registerMachineCallback(this.F);
    }

    public /* synthetic */ void a(QuestionDialogView questionDialogView) {
        this.H.setChecked(false);
        this.G.setChecked(false);
        this.h.setCheckboxChecked(false);
        this.r.logout();
        this.r.switchToStart();
        this.w.doFactoryReset();
        this.L.doFactoryReset(new Runnable() { // from class: ni
            @Override // java.lang.Runnable
            public final void run() {
                this.a.d();
            }
        });
        questionDialogView.setVisibility(8);
    }

    public /* synthetic */ void d(View view2) {
        this.i.setVisibility(0);
    }

    public /* synthetic */ void f() {
        final List<WifiHelper.WifiInfoView> scanResults = this.L.getScanResults();
        this.N.post(new Runnable() { // from class: jh
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(scanResults);
            }
        });
    }

    public /* synthetic */ void c(CompoundButton compoundButton, boolean z) {
        this.w.setShowLabels(z);
        LayoutHelper.getInstance().invalidateSelectedView();
    }

    public /* synthetic */ void d(CompoundButton compoundButton, final boolean z) {
        boolean wifiEnabled;
        Log.i(Q, "switch >> ischecked " + z + " >> wifi " + this.L.isWifiEnabled());
        if (this.L.getConnectedSSID() == null || TextUtils.isEmpty(this.L.getConnectedSSID()) || !this.w.isDataPrivacyPolicyAccepted() || !z) {
            boolean z2 = false;
            this.M.setEnabled(false);
            this.O.setEnabled(false);
            if (z && !this.L.isWifiEnabled()) {
                if (this.w.isDataPrivacyPolicyAccepted()) {
                    wifiEnabled = this.L.setWifiEnabled(getContext(), z && this.w.isDataPrivacyPolicyAccepted(), new WifiHelper.WifiStateListener() { // from class: zi
                        @Override // helper.WifiHelper.WifiStateListener
                        public final void stateReached(int i) {
                            this.a.a(z, i);
                        }
                    });
                } else {
                    wifiEnabled = false;
                }
            } else {
                boolean wifiEnabled2 = this.L.setWifiEnabled(getContext(), false, null);
                if (wifiEnabled2) {
                    this.M.setEnabled(true);
                    this.O.setEnabled(z);
                    this.u.clear();
                    this.u.notifyDataSetChanged();
                    this.u.addAll(this.L.getScanResults());
                    this.P.setVisibility(8);
                } else if (!this.w.isDataPrivacyPolicyAccepted()) {
                    this.h.setVisibility(0);
                }
                wifiEnabled = wifiEnabled2;
            }
            if (!wifiEnabled) {
                if (!this.w.isDataPrivacyPolicyAccepted()) {
                    this.h.setVisibility(0);
                }
                Switch r1 = this.M;
                if (this.L.isWifiEnabled() && this.L.hasEnabledConfiguration() && this.w.isDataPrivacyPolicyAccepted()) {
                    z2 = true;
                }
                r1.setChecked(z2);
                this.M.setEnabled(true);
                this.O.setEnabled(true);
            }
            String str = Q;
            StringBuilder sbA = g9.a("initWifi >> policy accepted ");
            sbA.append(this.w.isDataPrivacyPolicyAccepted());
            sbA.append(" >> wifiOnOffSwitch ");
            sbA.append(this.M.isChecked());
            sbA.append(" >> succeeded ");
            sbA.append(wifiEnabled);
            Log.w(str, sbA.toString());
        }
    }

    public /* synthetic */ void h() {
        try {
            IUpdateService updateService = UpdateServiceAdapter.getInstance().getUpdateService(getContext().getApplicationContext());
            if (updateService != null) {
                Log.i(Q, "calling startCheck().");
                updateService.startCheck();
            } else {
                Log.w(Q, "update service not connected.");
                this.s.post(new xh(this, "update service not connected."));
            }
        } catch (RemoteException | NullPointerException e) {
            Log.e(Q, " !! failed to check for update.", e);
            this.s.post(new xh(this, "!! failed to check for update."));
        }
    }

    public final boolean c() {
        if (this.L.getConnectedSSID() != null) {
            return true;
        }
        DialogHelper.getInstance().showWarningDialog(99);
        return false;
    }

    public /* synthetic */ void b(View view2) {
        int i = this.p + 1;
        this.p = i;
        if (i % 5 == 0) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(new ComponentName("com.adups.tgifota", "com.adups.tgifota.ui.view.activity.MainActivity"));
            intent.setFlags(67108864);
            getContext().startActivity(intent);
        }
    }

    public /* synthetic */ void e(View view2) {
        this.i.setVisibility(0);
    }

    @SuppressLint({"HardwareIds"})
    public final void a() {
        Log.i(Q, "initAbout");
        final RelativeLayout relativeLayout = (RelativeLayout) this.s.findViewById(R.id.about_root_container);
        final TextView textView = (TextView) this.s.findViewById(R.id.about_row_what_tv);
        final TextView textView2 = (TextView) this.s.findViewById(R.id.about_row_back);
        QuicksandTextView quicksandTextView = (QuicksandTextView) this.s.findViewById(R.id.about_row_mac_addr);
        final QuicksandTextView quicksandTextView2 = (QuicksandTextView) this.s.findViewById(R.id.about_row_connected_to_server);
        final QuicksandTextView quicksandTextView3 = (QuicksandTextView) this.s.findViewById(R.id.about_row_ip_addr);
        final LinearLayout linearLayout = (LinearLayout) this.s.findViewById(R.id.about_container);
        final Button button = (Button) this.s.findViewById(R.id.test_updater_bt);
        button.setOnClickListener(new View.OnClickListener() { // from class: bh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(button, view2);
            }
        });
        quicksandTextView.setText("MAC: " + this.L.getMACAddress());
        new Thread(new Runnable() { // from class: ah
            @Override // java.lang.Runnable
            public final void run() throws PackageManager.NameNotFoundException {
                this.a.e();
            }
        }).start();
        this.s.findViewById(R.id.about_row_android_version_tv).setOnClickListener(new View.OnClickListener() { // from class: ch
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(view2);
            }
        });
        this.s.findViewById(R.id.about_row_app_version_tv).setOnClickListener(new View.OnClickListener() { // from class: ih
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.b(view2);
            }
        });
        this.s.findViewById(R.id.about_row_serial_number_tv).setOnClickListener(new View.OnClickListener() { // from class: ti
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.c(view2);
            }
        });
        final View.OnClickListener onClickListener = new View.OnClickListener() { // from class: oi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws JSONException {
                this.a.a(textView2, linearLayout, textView, quicksandTextView3, quicksandTextView2, button, relativeLayout, view2);
            }
        };
        textView.setOnClickListener(onClickListener);
        this.e = new View.OnClickListener() { // from class: ei
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(textView, textView2, linearLayout, button, view2);
            }
        };
        textView2.setOnClickListener(new View.OnClickListener() { // from class: yg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(textView, onClickListener, view2);
            }
        });
    }

    public /* synthetic */ void b(LinearLayout linearLayout, QuicksandTextView quicksandTextView, View view2) {
        if (linearLayout.getVisibility() == 0) {
            this.g.onClick(view2);
            return;
        }
        linearLayout.setVisibility(0);
        quicksandTextView.setText(getString(R.string.close));
        a(false, this.languageRowView);
    }

    public /* synthetic */ void b(String str) {
        if (str.equals("POPUP")) {
            j();
        }
    }

    public /* synthetic */ void b(final CompoundButton compoundButton, boolean z) {
        if (c()) {
            if (!this.w.hasUserToken()) {
                DialogHelper.getInstance().showWarningDialog(98);
            }
            if (this.w.hasUserToken()) {
                if (z && !this.w.isDataPrivacyPolicyAccepted()) {
                    this.h.setVisibility(0);
                    compoundButton.setChecked(false);
                    return;
                }
                if (z) {
                    QuestionDialogView questionDialogView = (QuestionDialogView) this.s.findViewById(R.id.data_privacy_question_dialog);
                    questionDialogView.setOnBodyURLClick(new ActionListener() { // from class: ri
                        @Override // helper.ActionListener
                        public final void onAction(Object obj) {
                            this.a.c((String) obj);
                        }
                    });
                    questionDialogView.setButtonOneClickListener(new ActionListener() { // from class: aj
                        @Override // helper.ActionListener
                        public final void onAction(Object obj) {
                            SettingsFragment.b(compoundButton, (QuestionDialogView) obj);
                        }
                    });
                    questionDialogView.setBodyText(getString(R.string.question_mc_newsletter_subscription));
                    questionDialogView.setButtonTwoClickListener(new ActionListener() { // from class: uh
                        @Override // helper.ActionListener
                        public final void onAction(Object obj) {
                            ((QuestionDialogView) obj).setVisibility(8);
                        }
                    });
                    questionDialogView.setVisibility(0);
                    questionDialogView.setButtonOneText(getString(R.string.no_subscribe));
                    questionDialogView.setButtonTwoText(getString(R.string.subscribe));
                    questionDialogView.setBodyTextSize(14.0f);
                    questionDialogView.setTitleText("");
                }
                this.w.setNewsletterSubscribed(z);
                McApi.getInstance().updateDataPrivacyOptions();
                return;
            }
        }
        compoundButton.setChecked(false);
    }

    public /* synthetic */ void d(String str) {
        Toast toast = this.B;
        if (toast == null) {
            this.B = Toast.makeText(getContext(), str, 0);
        } else {
            toast.setText(str);
            this.B.setDuration(0);
        }
        this.B.show();
    }

    public /* synthetic */ void a(Button button, View view2) {
        if (c()) {
            button.setEnabled(false);
            new Thread(new Runnable() { // from class: ii
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.h();
                }
            }).start();
        }
    }

    public /* synthetic */ void a(String str, String str2, String str3, Bitmap bitmap, String str4) {
        TextView textView = (TextView) this.s.findViewById(R.id.about_row_android_version_tv);
        ((TextView) this.s.findViewById(R.id.about_row_app_version_tv)).setText(getString(R.string.app_version, g9.a(str, ", Build: ", str2)));
        textView.setText(String.format("%s, %s", getString(R.string.android_version, str3), getString(R.string.android_securitypatchlevel, Build.VERSION.SECURITY_PATCH)));
        ImageView imageView = (ImageView) this.s.findViewById(R.id.qr_image_view);
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(8);
        TextView textView2 = (TextView) this.s.findViewById(R.id.about_row_serial_number_tv);
        TextView textView3 = (TextView) this.s.findViewById(R.id.about_row_seserial_number_tv);
        textView2.setText(getString(R.string.serial_number, Build.SERIAL));
        textView3.setText(getString(R.string.seserial_number, str4));
    }

    public static /* synthetic */ void b(CompoundButton compoundButton, QuestionDialogView questionDialogView) {
        questionDialogView.setVisibility(8);
        compoundButton.setChecked(false);
    }

    public final void b() {
        String string = getString(R.string.recipes);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        String language = SharedPreferencesHelper.getInstance().getLanguage();
        for (String str : Arrays.asList("", RecipeType.DEFAULT, RecipeType.LIVE, RecipeType.BETA)) {
            long jCountRecipesByType = DbHelper.getInstance().countRecipesByType(str, language, true);
            Date maxRecipesUpdate = DbHelper.getInstance().getMaxRecipesUpdate(str, language);
            Log.d(Q, "countRecipesByTypes " + str + StringUtils.SPACE + jCountRecipesByType);
            if (jCountRecipesByType > 0) {
                c cVar = new c(this, null);
                cVar.c = str;
                cVar.a = jCountRecipesByType;
                cVar.b = maxRecipesUpdate;
                arrayList.add(cVar);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            c cVar2 = (c) it.next();
            sb.append(cVar2.a);
            sb.append(' ');
            if (!cVar2.c.equals(RecipeType.DEFAULT)) {
                sb.append(cVar2.c.substring(0, 1).toUpperCase() + cVar2.c.substring(1));
                sb.append('-');
            }
            sb.append(string);
            if (cVar2.b != null) {
                sb.append(StringUtils.SPACE);
                sb.append(simpleDateFormat.format(cVar2.b));
            }
            sb.append(StringUtils.LF);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(StringUtils.LF));
        }
        final String string2 = sb.toString();
        this.s.post(new Runnable() { // from class: vh
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(string2);
            }
        });
    }

    public /* synthetic */ void a(View view2) {
        int i = this.c + 1;
        this.c = i;
        if (i % 5 == 0) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(new ComponentName("com.discovery.factorymode", "com.discovery.factorymode.HomeActivity"));
            intent.setFlags(67108864);
            getContext().startActivity(intent);
        }
    }

    public /* synthetic */ void a(TextView textView, LinearLayout linearLayout, TextView textView2, QuicksandTextView quicksandTextView, QuicksandTextView quicksandTextView2, Button button, final RelativeLayout relativeLayout, View view2) throws JSONException {
        textView.setVisibility(0);
        textView.setText(getString(R.string.close));
        linearLayout.setVisibility(0);
        if (!textView2.isSelected()) {
            StringBuilder sbA = g9.a("IP: ");
            sbA.append(this.L.getIPAddress());
            quicksandTextView.setText(sbA.toString());
            quicksandTextView2.setText(getString(R.string.connected_to_server, getString(R.string.checking_connection)));
            McUsageApi.getInstance().postConnectionCheckEvent(new zj(this, quicksandTextView2));
            b();
            ICommandInterface commandInterface = App.getInstance().getMachineAdapter().getCommandInterface();
            if (commandInterface != null) {
                textView2.setText(getString(R.string.firmware_version, String.valueOf(commandInterface.getFirmwareVersion())));
            }
            UpdateServiceAdapter.getInstance().getUpdateService(getContext().getApplicationContext());
            Log.i(Q, "addUpdateListener");
            if (this.A != null) {
                UpdateServiceAdapter.getInstance().removeStateListener(this.A);
                this.A = null;
            }
            this.A = new yj(this);
            UpdateServiceAdapter.getInstance().addStateListener(this.A);
            textView2.setSelected(true);
            Log.i(Q, "aboutTextView.isSelected(): ");
            button.setVisibility(0);
            this.E.post(new Runnable() { // from class: dj
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.l(relativeLayout);
                }
            });
            k();
            a(false, this.aboutRowView);
            return;
        }
        int i = this.K % 5;
    }

    public /* synthetic */ boolean b(AdapterView adapterView, View view2, int i, long j) {
        final WifiHelper.WifiInfoView item = this.u.getItem(i);
        if (item == null) {
            return false;
        }
        String str = Q;
        StringBuilder sbA = g9.a("Wi-Fi: long clicked scan result @", i, StringUtils.SPACE);
        sbA.append(item.SSID);
        Log.v(str, sbA.toString());
        if (!item.isConfigured) {
            return false;
        }
        PopupMenu popupMenu = new PopupMenu(getContext(), view2);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: tg
            @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return this.a.a(item, menuItem);
            }
        });
        popupMenu.getMenuInflater().inflate(R.menu.configured_wifi_menu, popupMenu.getMenu());
        popupMenu.show();
        return true;
    }

    public /* synthetic */ void a(TextView textView, TextView textView2, LinearLayout linearLayout, Button button, View view2) {
        textView.setSelected(false);
        textView.setText(getString(R.string.your_monsieur_cuisine));
        textView2.setText(getString(R.string.open));
        linearLayout.setVisibility(8);
        button.setVisibility(8);
        if (this.A != null) {
            Log.i(Q, "removeUpdateListener");
            UpdateServiceAdapter.getInstance().removeStateListener(this.A);
            this.A = null;
        }
        k();
    }

    public /* synthetic */ void b(QuicksandTextView quicksandTextView, View view2) {
        if (!this.N.isSelected()) {
            m();
        }
        this.N.setSelected(!r5.isSelected());
        o();
        quicksandTextView.setText(this.N.isSelected() ? getString(R.string.close) : getString(R.string.change));
        if (this.N.isSelected()) {
            a(false, this.wifiRowView);
            this.s.findViewById(R.id.wifi_row_back).setVisibility(0);
            this.M.setVisibility(0);
            this.O.setVisibility(0);
            quicksandTextView.setText(getString(R.string.close));
            return;
        }
        k();
        this.M.setVisibility(8);
        this.O.setVisibility(8);
    }

    public /* synthetic */ void a(TextView textView, View.OnClickListener onClickListener, View view2) {
        if (textView.isSelected()) {
            this.e.onClick(view2);
        } else {
            onClickListener.onClick(view2);
        }
    }

    public /* synthetic */ void a(LinearLayout linearLayout, QuicksandTextView quicksandTextView, View view2) {
        linearLayout.setVisibility(8);
        k();
        quicksandTextView.setText(getString(R.string.change));
    }

    public final void b(ImageButton imageButton) throws SecurityException {
        ImageButton imageButton2;
        this.r.setVisibility(8);
        this.x.setVisibility(8);
        this.m.setVisibility(8);
        this.D.setVisibility(8);
        this.q.setSelected(false);
        this.k.setSelected(false);
        this.C.setSelected(false);
        if (imageButton.equals(this.q)) {
            imageButton2 = this.q;
            this.r.setVisibility(0);
            this.r.startSettingsMode();
            this.z = 1;
        } else if (imageButton.equals(this.k)) {
            imageButton2 = this.k;
            this.m.setVisibility(0);
            this.z = 3;
            if (!this.l) {
                RecyclerView recyclerView = (RecyclerView) this.m.findViewById(R.id.help_listview_videos);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(1);
                recyclerView.setLayoutManager(linearLayoutManager);
                HelpContentItemAdapter helpContentItemAdapter = new HelpContentItemAdapter(getActivity());
                recyclerView.setAdapter(helpContentItemAdapter);
                ArrayList arrayList = new ArrayList();
                Map<String, Integer> filesMapByRegex = ResourceHelper.getInstance().getFilesMapByRegex("(.)*_video");
                for (String str : filesMapByRegex.keySet()) {
                    arrayList.add(new HelpContentVideo(ResourceHelper.getInstance().getStringByName(str, getContext()), getContext().getResources().getIdentifier(g9.b(str, "_bg"), "raw", getContext().getPackageName()), filesMapByRegex.get(str).intValue()));
                    Log.d(Q, "Found video: " + str);
                }
                helpContentItemAdapter.setItems(arrayList);
                RecyclerView recyclerView2 = (RecyclerView) this.m.findViewById(R.id.help_listview_tutorials);
                recyclerView2.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
                linearLayoutManager2.setOrientation(1);
                recyclerView2.setLayoutManager(linearLayoutManager2);
                HelpContentItemAdapter helpContentItemAdapter2 = new HelpContentItemAdapter(getActivity());
                recyclerView2.setAdapter(helpContentItemAdapter2);
                helpContentItemAdapter2.setItems(ResourceHelper.getInstance().getTutorialList(getContext()));
                this.l = true;
            }
        } else if (imageButton.equals(this.C)) {
            imageButton2 = this.C;
            this.D.setVisibility(0);
            this.z = 4;
            if (this.H != null) {
                n();
            }
        } else {
            imageButton2 = null;
        }
        if (imageButton2 == null || imageButton2.isSelected()) {
            return;
        }
        imageButton2.setSelected(true);
    }

    public static /* synthetic */ void a(QuestionDialogView questionDialogView, final List list, AdapterView adapterView, View view2, final int i, long j) {
        questionDialogView.setVisibility(0);
        questionDialogView.setButtonTwoClickListener(new ActionListener() { // from class: zh
            @Override // helper.ActionListener
            public final void onAction(Object obj) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
                SettingsFragment.a(list, i, (QuestionDialogView) obj);
            }
        });
    }

    public static /* synthetic */ void a(List list, int i, QuestionDialogView questionDialogView) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        App.getInstance().changeLocale((Locale) list.get(i));
        questionDialogView.setVisibility(8);
    }

    public /* synthetic */ void a(final CompoundButton compoundButton, boolean z) {
        if (c()) {
            if (z && !this.w.isDataPrivacyPolicyAccepted()) {
                this.h.setVisibility(0);
                compoundButton.setChecked(false);
                return;
            }
            if (z) {
                QuestionDialogView questionDialogView = (QuestionDialogView) this.s.findViewById(R.id.data_privacy_question_dialog);
                questionDialogView.setOnBodyURLClick(new ActionListener() { // from class: qh
                    @Override // helper.ActionListener
                    public final void onAction(Object obj) {
                        this.a.b((String) obj);
                    }
                });
                questionDialogView.setButtonOneClickListener(new ActionListener() { // from class: vi
                    @Override // helper.ActionListener
                    public final void onAction(Object obj) {
                        SettingsFragment.a(compoundButton, (QuestionDialogView) obj);
                    }
                });
                questionDialogView.setBodyText(getString(R.string.question_hoyer_transfer));
                questionDialogView.setButtonTwoClickListener(new ActionListener() { // from class: bj
                    @Override // helper.ActionListener
                    public final void onAction(Object obj) {
                        ((QuestionDialogView) obj).setVisibility(8);
                    }
                });
                questionDialogView.setButtonOneText(getString(R.string.decline));
                questionDialogView.setButtonTwoText(getString(R.string.accept));
                questionDialogView.setVisibility(0);
                questionDialogView.setBodyTextSize(14.0f);
                questionDialogView.setTitleText("");
            }
            this.w.setSendTrackingData(z);
            McApi.getInstance().updateDataPrivacyOptions();
            return;
        }
        compoundButton.setChecked(false);
    }

    public static /* synthetic */ void a(CompoundButton compoundButton, QuestionDialogView questionDialogView) {
        questionDialogView.setVisibility(8);
        compoundButton.setChecked(false);
    }

    public /* synthetic */ void a(final RecyclerView recyclerView, final View view2) {
        this.E.post(new Runnable() { // from class: xg
            @Override // java.lang.Runnable
            public final void run() {
                SettingsFragment.a(view2, recyclerView);
            }
        });
    }

    public static /* synthetic */ void a(View view2, RecyclerView recyclerView) {
        int[] iArr = new int[2];
        view2.getLocationOnScreen(iArr);
        int i = iArr[1];
        if (i > 350) {
            recyclerView.smoothScrollBy(0, i - 270);
        }
    }

    public /* synthetic */ void a(TextView textView, RelativeLayout relativeLayout, RelativeLayout relativeLayout2, RelativeLayout relativeLayout3, ViewGroup viewGroup, TextView textView2, View view2) {
        if (textView.isSelected()) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.width = 700;
        relativeLayout.setLayoutParams(layoutParams);
        textView.setSelected(true);
        relativeLayout2.setVisibility(0);
        relativeLayout3.setBackgroundResource(R.drawable.shape_settings_rectangle_rounded_left_top_open_right);
        relativeLayout.setBackgroundResource(R.drawable.shape_settings_rectangle_not_rounded_left_right_open);
        viewGroup.setVisibility(4);
        textView2.setVisibility(0);
        a(false, this.faqRowView);
    }

    public /* synthetic */ void a(FaqAdapter faqAdapter, RelativeLayout relativeLayout, TextView textView, RelativeLayout relativeLayout2, RelativeLayout relativeLayout3, ViewGroup viewGroup, TextView textView2, View view2) {
        faqAdapter.collapseFaq();
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.width = 170;
        relativeLayout.setLayoutParams(layoutParams);
        textView.setSelected(false);
        relativeLayout2.setVisibility(8);
        relativeLayout3.setBackgroundResource(0);
        relativeLayout.setBackgroundResource(0);
        viewGroup.setVisibility(0);
        textView2.setVisibility(8);
        k();
    }

    public /* synthetic */ void a(String str) {
        ((TextView) this.s.findViewById(R.id.about_row_recipes_tv)).setText(str);
    }

    public /* synthetic */ void a(final RelativeLayout relativeLayout) {
        final String localPrivacyTermHTML = ResourceHelper.getLocalPrivacyTermHTML();
        this.s.post(new Runnable() { // from class: qi
            @Override // java.lang.Runnable
            public final void run() {
                ((TextView) relativeLayout.findViewById(R.id.terms_row_content_tv)).setText(Html.fromHtml(localPrivacyTermHTML));
            }
        });
    }

    public /* synthetic */ void a(String str, String str2) {
        Log.d(Q, "WifiListener connection changed");
        this.u.connectionChanged(str);
        if (this.L.isWifiEnabled() && !this.M.isChecked() && this.w.isDataPrivacyPolicyAccepted()) {
            this.M.setChecked(true);
        }
        o();
    }

    public /* synthetic */ void a(boolean z, int i) {
        this.M.setEnabled(true);
        this.O.setEnabled(true);
        this.u.clear();
        this.u.notifyDataSetChanged();
        this.u.addAll(this.L.getScanResults());
        m();
        this.P.setVisibility(8);
        if (this.L.setWifiOn(z && this.w.isDataPrivacyPolicyAccepted())) {
            return;
        }
        Log.i(Q, "switch >> setWifiOn " + z + " >> wifi " + this.L.isWifiEnabled());
        this.M.setChecked(this.L.isWifiEnabled() && this.L.hasEnabledConfiguration() && this.w.isDataPrivacyPolicyAccepted());
    }

    public /* synthetic */ void a(AdapterView adapterView, View view2, int i, long j) {
        final WifiHelper.WifiInfoView item = this.u.getItem(i);
        if (item == null) {
            return;
        }
        String str = Q;
        StringBuilder sbA = g9.a("Wi-Fi: clicked scan result @", i, StringUtils.SPACE);
        sbA.append(item.SSID);
        Log.v(str, sbA.toString());
        if (!this.w.isDataPrivacyPolicyAccepted()) {
            Log.v(Q, " >> privacy policy not accepted, yet.");
            this.h.setVisibility(0);
            return;
        }
        if (this.L.isWifiConnected(item.SSID)) {
            this.P.setVisibility(0);
            this.o = null;
            this.L.reconnect();
            return;
        }
        if (item.isConfigured) {
            this.P.setVisibility(0);
            this.L.connectConfiguredWifi(item.SSID);
            this.o = null;
            return;
        }
        this.P.setVisibility(0);
        if (!item.isSecured) {
            this.L.configureWifi(item, "abc");
            this.L.connectConfiguredWifi(item.SSID);
            item.isConfigured = true;
            this.o = item;
            return;
        }
        final TextInputEditText textInputEditText = new TextInputEditText(getContext());
        textInputEditText.setInputType(129);
        TextInputLayout textInputLayout = new TextInputLayout(getContext());
        textInputLayout.setPasswordVisibilityToggleEnabled(true);
        textInputLayout.addView(textInputEditText);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(textInputLayout);
        builder.setTitle(getString(R.string.enter_password_for, item.SSID));
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.connect), new DialogInterface.OnClickListener() { // from class: ci
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.a.a(item, textInputEditText, dialogInterface, i2);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        this.d = alertDialogCreate;
        alertDialogCreate.show();
    }

    public /* synthetic */ boolean a(WifiHelper.WifiInfoView wifiInfoView, MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.delete_wifi_configuration) {
            return true;
        }
        StringBuilder sbA = g9.a("DeleteConfiguration ");
        sbA.append(Thread.currentThread().getName());
        sbA.append(StringUtils.SPACE);
        sbA.append(Thread.currentThread().getId());
        Log.i("WifiHelper", sbA.toString());
        if (!this.L.deleteWifiConfiguration(wifiInfoView)) {
            return true;
        }
        wifiInfoView.isConfigured = false;
        this.u.notifyDataSetChanged();
        return true;
    }

    public /* synthetic */ void a(QuicksandTextView quicksandTextView, View view2) {
        a(false, this.wifiRowView);
        this.s.findViewById(R.id.wifi_row_back).setVisibility(0);
        this.M.setVisibility(0);
        this.O.setVisibility(0);
        if (!view2.isSelected()) {
            view2.setSelected(true);
            o();
            m();
        }
        quicksandTextView.setText(getString(R.string.close));
    }

    public /* synthetic */ void a(List list) {
        this.u.addAll(list);
        this.u.notifyDataSetChanged();
    }

    public final void a(boolean z, View view2) {
        Log.d(Q, "setRowsVisibilityExcept: isVis=" + z);
        if (z && view2 == null) {
            this.bgImageView.setImageResource(R.drawable.recipe_bg);
            this.bgImageView.getDrawable().clearColorFilter();
            getActivity().findViewById(R.id.top_bar_layout).setVisibility(0);
            this.bottomBtnContainer.setVisibility(0);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.bgImageView.getLayoutParams();
            layoutParams.setMargins(0, 98, 0, 0);
            this.bgImageView.setLayoutParams(layoutParams);
        } else if (!z && view2 != null) {
            this.bgImageView.setImageDrawable(getContext().getDrawable(R.drawable.info_bg));
            this.bgImageView.setColorFilter(-2105377, PorterDuff.Mode.MULTIPLY);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.bgImageView.getLayoutParams();
            layoutParams2.setMargins(0, 0, 0, 0);
            this.bgImageView.setLayoutParams(layoutParams2);
            this.bottomBtnContainer.setVisibility(4);
            getActivity().findViewById(R.id.top_bar_layout).setVisibility(8);
        }
        for (int i = 0; i < this.rowsContainer.getChildCount(); i++) {
            View childAt = this.rowsContainer.getChildAt(i);
            if (childAt != null) {
                childAt.setVisibility((childAt != view2) == z ? 0 : 8);
            }
        }
    }

    public /* synthetic */ void a(WifiHelper.WifiInfoView wifiInfoView, TextInputEditText textInputEditText, DialogInterface dialogInterface, int i) {
        if (this.L.configureWifi(wifiInfoView, textInputEditText.getText().toString())) {
            this.L.connectConfiguredWifi(wifiInfoView.SSID);
            wifiInfoView.isConfigured = true;
            this.o = wifiInfoView;
        }
        this.d = null;
        dialogInterface.dismiss();
    }

    public /* synthetic */ void a(DialogInterface dialogInterface, int i) {
        this.d = null;
        dialogInterface.dismiss();
    }
}
