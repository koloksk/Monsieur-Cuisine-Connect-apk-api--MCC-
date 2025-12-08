package fragment;

import activity.MainActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import controller.CookingControls;
import cooking.CookingManager;
import cooking.CookingStep;
import cooking.CookingSteps;
import cooking.CookingUnit;
import cooking.Limits;
import cooking.SingleCookingStep;
import de.silpion.mc2.R;
import defpackage.g9;
import helper.ActionListener;
import helper.DialogHelper;
import helper.KnobUtils;
import helper.LayoutHelper;
import helper.SharedPreferencesHelper;
import helper.UsageLogger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import machineAdapter.ICommandInterface;
import machineAdapter.IMachineCallback;
import machineAdapter.adapter.MachineCallbackAdapter;
import model.Presets;
import org.apache.commons.lang3.ArrayUtils;
import sound.SoundLength;
import view.QuestionDialogView;
import view.QuicksandTextView;
import view.knob.Knob;
import view.knob.KnobListener;
import view.knob.KnobScale;
import view.knob.KnobSpeed;
import view.knob.KnobTemperature;
import view.knob.KnobTime;

/* loaded from: classes.dex */
public class MainFragment extends BaseFragment implements CookingManager.CookingListener, KnobListener {
    public static final String D = MainFragment.class.getSimpleName();
    public static boolean E = false;
    public ImageView A;
    public long B;
    public QuestionDialogView c;

    @BindView(R.id.cooking_controls_container)
    @Nullable
    public RelativeLayout controlRelativeLayout;
    public CookingControls e;
    public TextView f;
    public KnobScale g;
    public KnobSpeed h;
    public KnobTemperature i;
    public KnobTime j;
    public KnobSpeed k;

    @BindView(R.id.knob_left)
    public RelativeLayout knobLeftContainer;

    @BindView(R.id.knob_left_turbo)
    public RelativeLayout knobLeftTurboContainer;

    @BindView(R.id.knob_middle)
    public RelativeLayout knobMiddleContainer;

    @BindView(R.id.knob_middle_turbo)
    public RelativeLayout knobMiddleTurboContainer;

    @BindView(R.id.knob_right)
    public RelativeLayout knobRightContainer;
    public KnobTime l;
    public String m;
    public View n;
    public ImageView o;
    public QuicksandTextView p;
    public ImageView q;
    public QuicksandTextView r;

    @BindView(R.id.scale_container)
    public RelativeLayout scaleContainer;

    @Nullable
    public Handler t;
    public ImageView u;
    public int v;
    public QuicksandTextView x;
    public CookingStep z;
    public ICommandInterface d = App.getInstance().getMachineAdapter().getCommandInterface();
    public volatile boolean s = false;

    @NonNull
    public final Runnable w = new Runnable() { // from class: qj
        @Override // java.lang.Runnable
        public final void run() {
            this.a.n();
        }
    };
    public final ActionListener<Integer> y = new ActionListener() { // from class: kf
        @Override // helper.ActionListener
        public final void onAction(Object obj) {
            this.a.a((Integer) obj);
        }
    };
    public final IMachineCallback C = new a();

    @Retention(RetentionPolicy.SOURCE)
    public @interface KNOB {
        public static final String LEFT = "knobTime";
        public static final String LEFT_TURBO = "knobTurboTime";
        public static final String MIDDLE = "knobSpeed";
        public static final String MIDDLE_TURBO = "knobTurboSpeed";
        public static final String RIGHT = "knobTemperature";
        public static final String SCALE = "knobScale";
    }

    public final void c() {
        CookingManager cookingManager = CookingManager.getInstance();
        this.j.setEnabled(cookingManager.isTimeKnobModifiable());
        this.h.setEnabled(cookingManager.isSpeedKnobModifiable());
        this.i.setEnabled(cookingManager.isTemperatureKnobModifiable());
        this.j.setModeImageToSelected();
        this.j.valueSetVisibility(true);
        if (TextUtils.isEmpty(cookingManager.getTimeDescription())) {
            this.j.valueSetText(cookingManager.getTime());
        }
        this.h.setModeImageToSelected();
        this.h.valueSetVisibility(true);
        if (TextUtils.isEmpty(cookingManager.getSpeedDescription())) {
            this.h.setText(String.valueOf(cookingManager.getSpeedLevel()));
            KnobSpeed knobSpeed = this.h;
            knobSpeed.setProgress(knobSpeed.getProgressFromValue(cookingManager.getSpeedLevel()));
        }
        p();
        o();
        this.i.setTemperatureLevel(cookingManager.getTemperatureLevel());
        this.i.setMeasuredTemperature(cookingManager.getMeasuredTemperature());
        if (!TextUtils.isEmpty(cookingManager.getTemperatureDescription())) {
            this.i.setTextForMeasuredTemperature(cookingManager.getTemperatureDescription());
        }
        if (cookingManager.isPreheatStep()) {
            this.h.setPreheatMode(this);
            this.j.setPreheatMode();
            return;
        }
        this.h.unsetPreheatMode();
        this.j.unsetPreheatMode();
        if (cookingManager.hasCustomInfo()) {
            this.h.showCustomInfo(cookingManager.getCustomInfo());
        } else {
            this.h.hideCustomInfo();
        }
    }

    public void cancelLastKnobSelection() {
        String str = D;
        StringBuilder sbA = g9.a("cancelLastKnobSelection >> lastSelectedKnobTag ");
        sbA.append(this.m);
        Log.i(str, sbA.toString());
        this.j.deselect();
        this.h.deselect();
        this.i.deselect();
        if (this.o.isSelected()) {
            d(false);
        }
        this.m = "";
    }

    public final void d() {
        int i = SharedPreferencesHelper.getInstance().getShowLabels() ? 0 : 4;
        this.x.setVisibility(i);
        this.r.setVisibility(i);
        this.p.setVisibility(i);
    }

    public /* synthetic */ void e(QuestionDialogView questionDialogView) {
        this.c.setVisibility(8);
        a();
    }

    public /* synthetic */ void f(QuestionDialogView questionDialogView) {
        this.c.setVisibility(8);
        CookingManager.getInstance().setSpeedLevel(Limits.WARNING_SPEED_LEVEL_AT_HIGH_TEMPERATURE);
        a();
        this.s = true;
    }

    public /* synthetic */ void g() {
        this.h.setModeImageToSelected();
        this.A.setVisibility(0);
    }

    public final void h() {
        Log.v(D, "resetCooking: ");
        this.e.setCurrentState(1);
        CookingManager.getInstance().stopCooking();
        a(false, true);
        l();
        a(CookingManager.getInstance().getDirection() != 0 ? 0 : 1, CookingManager.getInstance().isDirectionModifiable());
        a(CookingManager.getInstance().isScaleEnabled());
        k();
        i();
        j();
        if (TextUtils.isEmpty(CookingManager.getInstance().getDescription())) {
            this.f.setText("");
            this.f.setVisibility(8);
        } else {
            this.f.setText(CookingManager.getInstance().getDescription());
            this.f.setVisibility(0);
        }
    }

    public final void i() {
        KnobSpeed knobSpeed = this.h;
        if (knobSpeed == null) {
            return;
        }
        knobSpeed.setText(String.valueOf(CookingManager.getInstance().getSpeedLevel()));
        KnobSpeed knobSpeed2 = this.h;
        knobSpeed2.setProgress(knobSpeed2.getProgressFromValue(CookingManager.getInstance().getSpeedLevel()));
        this.h.setEnabled(CookingManager.getInstance().isSpeedKnobModifiable());
        this.h.setActive(false);
        this.h.setDrawActiveIndicator(CookingManager.getInstance().isSpeedKnobModifiable());
        this.h.setMaxValueLimit(CookingManager.getInstance().getSpeedLevelLimit());
        this.h.unsetPreheatMode();
        this.h.hideCustomInfo();
        this.h.setToIdleState();
        if (TextUtils.isEmpty(CookingManager.getInstance().getSpeedDescription())) {
            this.h.setValueSetTextSizeNormal();
            this.h.valueSetText("");
            this.h.valueSetVisibility(false);
        } else {
            this.h.setValueSetTextSizeSmall();
            this.h.valueSetText(CookingManager.getInstance().getSpeedDescription());
            this.h.setModeImageToSelected();
            this.h.valueSetVisibility(true);
        }
        p();
        o();
    }

    public void initCooking(CookingStep cookingStep) {
        this.z = cookingStep;
        CookingManager.getInstance().setCookingListener(this);
        CookingManager.getInstance().setInitCookingStep(cookingStep.mo10clone());
        h();
        a();
        d();
    }

    public void initViews() {
        this.c = (QuestionDialogView) getActivity().findViewById(R.id.speed_warning_question);
        initCooking(Presets.defaultCookingStep());
    }

    public boolean isRunning() {
        return this.e.isCooking();
    }

    public final void j() {
        KnobTemperature knobTemperature = this.i;
        if (knobTemperature == null) {
            return;
        }
        knobTemperature.setTemperatureLevel(CookingManager.getInstance().getTemperatureLevel());
        this.i.setEnabled(CookingManager.getInstance().isTemperatureKnobModifiable());
        this.i.setActive(false);
        this.i.setDrawActiveIndicator(CookingManager.getInstance().isTemperatureKnobModifiable());
        this.i.setMeasuredTemperature(CookingManager.getInstance().getMeasuredTemperature());
        if (TextUtils.isEmpty(CookingManager.getInstance().getTemperatureDescription())) {
            return;
        }
        this.i.setTextForMeasuredTemperature(CookingManager.getInstance().getTemperatureDescription());
    }

    public final void k() {
        CookingManager cookingManager = CookingManager.getInstance();
        CookingStep initCookingStep = cookingManager.getInitCookingStep();
        this.j.setDisplayProgress(true);
        KnobTime knobTime = this.j;
        if (knobTime == null) {
            return;
        }
        if (1 == knobTime.getCurrentMode()) {
            this.j.initTimeKnob();
        }
        if (initCookingStep instanceof CookingUnit) {
            CookingUnit cookingUnit = (CookingUnit) initCookingStep;
            this.j.setTime(cookingUnit.getCookingUnitDurationInMillis());
            this.j.updateProgressTime(cookingUnit.getCookingUnitDurationInMillis());
        } else if ((initCookingStep instanceof CookingSteps) && cookingManager.isPreheatStep()) {
            SingleCookingStep singleCookingStep = ((CookingSteps) initCookingStep).getSingleCookingSteps().get(cookingManager.getCurrentCookingStepIndex() + 1);
            this.j.setTime(singleCookingStep.getCookingDurationInMillis());
            this.j.updateProgressTime(singleCookingStep.getCookingDurationInMillis());
        } else {
            this.j.setTime(cookingManager.getTime());
            this.j.updateProgressTime(cookingManager.getTime());
        }
        if (cookingManager.getTimeLimit() != -1) {
            this.j.setMaxTimeInMillis((int) cookingManager.getTimeLimit());
        }
        this.j.setEnabled(cookingManager.isTimeKnobModifiable());
        this.j.setActive(false);
        this.j.setDrawActiveIndicator(cookingManager.isTimeKnobModifiable());
        this.j.setToIdleState();
        String timeDescription = cookingManager.getTimeDescription();
        if (TextUtils.isEmpty(timeDescription)) {
            this.j.setValueSetTextSizeNormal();
            this.j.valueSetVisibility(false);
        } else {
            this.j.setValueSetTextSizeSmall();
            this.j.valueSetText(timeDescription);
            this.j.setModeImageToSelected();
            this.j.valueSetVisibility(true);
        }
    }

    @Override // view.knob.Knob.StateListener
    public void knobSelected(Knob knob, int i, String str) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        Log.d(D, "knobSelected >> state " + i + " >> tag " + str + " >> lastSelectedKnobTag " + this.m);
        if (2 != i || str.equals(this.m) || KNOB.SCALE.equals(str)) {
            return;
        }
        a(knob);
        cancelLastKnobSelection();
        this.m = str;
    }

    public final void l() {
        if (this.A == null || this.h == null) {
            return;
        }
        if (CookingManager.getInstance().getSpeedLevel() >= 5) {
            this.n.post(new Runnable() { // from class: qf
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.g();
                }
            });
            return;
        }
        CookingControls cookingControls = this.e;
        if (cookingControls != null && cookingControls.getCurrentState() == 1) {
            this.h.setModeImageToIdle();
        }
        this.A.setVisibility(8);
    }

    public final void m() {
        Handler handler = this.t;
        if (handler == null) {
            return;
        }
        handler.removeCallbacks(this.w);
        this.v = 0;
        this.t = null;
        KnobTime knobTime = this.l;
        if (knobTime != null) {
            KnobUtils.resetWarningProgressColor(knobTime);
            this.l.setProgress(0);
        }
        this.knobLeftTurboContainer.setVisibility(8);
        this.knobMiddleTurboContainer.setVisibility(8);
        this.knobLeftContainer.setVisibility(0);
        this.knobLeftContainer.setEnabled(true);
        this.knobMiddleContainer.setVisibility(0);
        this.knobMiddleContainer.setEnabled(true);
        this.knobRightContainer.setEnabled(true);
        this.j.setMoveEnabled(CookingManager.getInstance().isTimeKnobModifiable());
        this.j.setThumbEnabled(true);
        this.h.setMoveEnabled(CookingManager.getInstance().isSpeedKnobModifiable());
        this.h.setThumbEnabled(true);
        this.i.setMoveEnabled(CookingManager.getInstance().isTemperatureKnobModifiable());
        this.i.setThumbEnabled(true);
        this.e.setEnabled(true);
        a(CookingManager.getInstance().isScaleEnabled());
        e(false);
    }

    public final void n() {
        if (b(true)) {
            m();
            return;
        }
        if (this.v == 0) {
            cancelLastKnobSelection();
            this.knobLeftTurboContainer.setVisibility(0);
            this.knobMiddleTurboContainer.setVisibility(0);
            this.knobLeftContainer.setVisibility(8);
            this.knobLeftContainer.setEnabled(false);
            this.knobMiddleContainer.setVisibility(8);
            this.knobMiddleContainer.setEnabled(false);
            this.knobRightContainer.setEnabled(false);
            this.j.setMoveEnabled(false);
            this.j.setThumbEnabled(false);
            this.h.setMoveEnabled(false);
            this.h.setThumbEnabled(false);
            this.i.setMoveEnabled(false);
            this.i.setThumbEnabled(false);
            this.e.setEnabled(false);
            a(false);
            e(true);
        }
        if (this.v >= 8) {
            KnobUtils.setWarningProgressColor(this.l);
        }
        int i = this.v;
        if (i >= 600) {
            m();
            return;
        }
        int i2 = i + 1;
        this.v = i2;
        KnobUtils.updateTurbo(this.l, i2);
        Handler handler = this.t;
        if (handler != null) {
            handler.postDelayed(this.w, 1000L);
        }
    }

    public final void o() {
        this.h.setReverse(CookingManager.getInstance().getDirection() == 0);
    }

    @Override // android.support.v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(D, "onAttach");
        App.getInstance().getMachineAdapter().registerMachineCallback(this.C);
        LayoutHelper.getInstance().addViewSelectionListener(this.y);
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.i(D, "onCreateView");
        View viewInflate = layoutInflater.inflate(R.layout.fragment_main, viewGroup, false);
        this.n = viewInflate;
        ButterKnife.bind(this, viewInflate);
        this.f = (TextView) this.n.findViewById(R.id.cooking_label_tv);
        return this.n;
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        Log.i(D, "onDetach");
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.C);
        LayoutHelper.getInstance().removeViewSelectionListener(this.y);
    }

    @Override // cooking.CookingManager.CookingListener
    public void onFinishCooking() {
        Log.v(D, "onFinishCooking");
        if (this.g.getCurrentState() == 2) {
            this.g.scaleKnob();
        }
        this.j.setTime(0L);
        this.j.setProgress(0);
        this.j.setCurrentState(1);
        if (this.j.getEnabled()) {
            KnobUtils.setActiveIndicator(this.j);
        }
        this.h.setCurrentState(1);
        if (this.h.getEnabled()) {
            KnobUtils.setActiveIndicator(this.h);
        }
        this.i.setCurrentState(1);
        if (this.i.getEnabled()) {
            KnobUtils.setActiveIndicator(this.i);
        }
        int i = MainActivity.currentGlobalMode;
        if (10 == i || 5 == i) {
            MainActivity.currentGlobalMode = 5;
        } else {
            MainActivity.currentGlobalMode = 4;
        }
        performStopClick();
        if (Presets.kneadingCookingUnit().equals(CookingManager.getInstance().getInitCookingStep())) {
            DialogHelper.getInstance().showAfterKneadingWarningDialog();
        }
        if (CookingManager.getInstance().getInitCookingStep() instanceof CookingSteps) {
            h();
        }
        performStopClick();
        App.getInstance().playSound(R.raw.finished, SoundLength.LONG);
        a(true);
        CookingControls cookingControls = this.e;
        if (cookingControls != null) {
            cookingControls.setCurrentState(1);
        }
        E = false;
    }

    @Override // cooking.CookingManager.CookingListener
    public void onNextSingleCookingStep(SingleCookingStep singleCookingStep) {
        Log.v(D, "onNextSingleCookingStep " + singleCookingStep);
        if (App.getInstance().getString(R.string.steaming).equals(singleCookingStep.getDescription())) {
            App.getInstance().playSound(R.raw.bell, SoundLength.SHORT);
        }
        c();
        a();
        a(false, true);
        p();
        o();
    }

    @Override // cooking.CookingManager.CookingListener
    public void onPauseCooking() {
        Log.v(D, "onPauseCooking");
    }

    @Override // cooking.CookingManager.CookingListener
    public void onResumeCooking() {
        Log.v(D, "onResumeCooking");
        a();
    }

    @Override // view.knob.Knob.SkipPreheatStepListener
    public void onSkipPreheatStep() {
        if (1 == this.d.getCurrentLidState()) {
            CookingManager.getInstance().skipCookingStep();
        } else {
            App.getInstance().playSound(R.raw.info, SoundLength.LONG);
            DialogHelper.getInstance().showLidOpenDialog(false);
        }
    }

    @Override // cooking.CookingManager.CookingListener
    public void onStartCooking(int i) {
        Log.v(D, "onStartCooking");
        c();
        this.j.setCurrentMode(i);
        KnobUtils.setActiveIndicator(this.j);
        KnobUtils.setActiveIndicator(this.h);
        KnobUtils.setActiveIndicator(this.i);
    }

    @Override // cooking.CookingManager.CookingListener
    public void onStopCooking() {
        Log.v(D, "onStopCooking");
        E = false;
        a();
        this.e.performStopClick();
        a(false, true);
        p();
        o();
    }

    @Override // view.knob.KnobScale.KnobScaleTareListener
    public void onTareClick() {
        this.d.setScaleTare();
        this.d.setScaleTare();
        this.d.setScaleTare();
    }

    @Override // view.knob.KnobTemperature.KnobTemperatureListener
    public void onTemperatureValueSet(int i) {
        Log.v(D, "onTemperatureValueSet >> value " + i);
        CookingManager.getInstance().setTemperature(i);
        a();
        a(false, true);
    }

    @Override // cooking.CookingManager.CookingListener
    public void onTickCooking(long j) {
        Log.i(D, "onTickCooking millisUntilFinished = " + j);
        if (10 != this.j.getCurrentMode()) {
            Log.v(D, "onTickCooking MODE: TIMER_DOWN");
            this.j.setTime(j);
            this.j.updateProgressTime(j);
            return;
        }
        long elapsedTime = CookingManager.getInstance().getElapsedTime();
        Log.v(D, "onTickCooking MODE: TIMER_UP , elapsed: " + elapsedTime);
        this.j.setTime(elapsedTime);
        this.j.updateProgressTime(elapsedTime);
    }

    @Override // view.knob.KnobTime.KnobTimeListener
    public void onTimeValueSet(long j) {
        Log.i(D, "onTimeValueSet >> value " + j);
        CookingManager.getInstance().setTime(j);
        a();
        a(false, true);
    }

    @Override // view.knob.Knob.KnobValueListener
    public synchronized void onValueSet(int i) {
        Log.i(D, "onValueSet >> value " + i);
        if (i <= 0 && CookingManager.getInstance().getState() == 1) {
            this.h.cancelTouchEvent();
            this.m = "";
            performStopClick();
        } else if (this.s || i < Limits.WARNING_SPEED_LEVEL_AT_HIGH_TEMPERATURE || CookingManager.getInstance().getState() != 1 || CookingManager.getInstance().getMeasuredTemperature() < Limits.WARNING_TEMPERATURE_AT_HIGH_SPEED) {
            CookingManager.getInstance().setSpeedLevel(i);
            a();
        } else {
            this.c.setVisibility(0);
            this.c.setButtonOneClickListener(new ActionListener() { // from class: lf
                @Override // helper.ActionListener
                public final void onAction(Object obj) {
                    this.a.e((QuestionDialogView) obj);
                }
            });
            this.c.setButtonTwoClickListener(new ActionListener() { // from class: nf
                @Override // helper.ActionListener
                public final void onAction(Object obj) {
                    this.a.f((QuestionDialogView) obj);
                }
            });
        }
    }

    @Override // fragment.BaseFragment, android.support.v4.app.Fragment
    public void onViewCreated(@NonNull View view2, Bundle bundle) {
        super.onViewCreated(view2, bundle);
        this.x = (QuicksandTextView) this.n.findViewById(R.id.turbo_tv);
        ImageView imageView = (ImageView) this.n.findViewById(R.id.turbo_iv);
        this.u = imageView;
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: gf
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view3, MotionEvent motionEvent) {
                return this.a.a(view3, motionEvent);
            }
        });
        this.r = (QuicksandTextView) this.n.findViewById(R.id.reverse_tv);
        ImageView imageView2 = (ImageView) this.n.findViewById(R.id.reverse_iv);
        this.q = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: mf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.b(view3);
            }
        });
        this.p = (QuicksandTextView) this.n.findViewById(R.id.scale_tv);
        ImageView imageView3 = (ImageView) this.n.findViewById(R.id.scale_iv);
        this.o = imageView3;
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: jf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.c(view3);
            }
        });
        KnobTime knobTime = (KnobTime) this.knobLeftTurboContainer.findViewById(R.id.knob);
        this.l = knobTime;
        RelativeLayout relativeLayout = this.knobLeftTurboContainer;
        knobTime.setKnobTag(KNOB.LEFT_TURBO);
        knobTime.setParent(relativeLayout);
        knobTime.initViews();
        knobTime.setStateListener(null);
        this.l.initTurbo();
        KnobSpeed knobSpeed = (KnobSpeed) this.knobMiddleTurboContainer.findViewById(R.id.knob);
        this.k = knobSpeed;
        RelativeLayout relativeLayout2 = this.knobMiddleTurboContainer;
        knobSpeed.setKnobTag(KNOB.MIDDLE_TURBO);
        knobSpeed.setParent(relativeLayout2);
        knobSpeed.initViews();
        knobSpeed.setStateListener(null);
        this.k.initTurbo();
        KnobTime knobTime2 = (KnobTime) this.knobLeftContainer.findViewById(R.id.knob);
        this.j = knobTime2;
        RelativeLayout relativeLayout3 = this.knobLeftContainer;
        knobTime2.setKnobTag(KNOB.LEFT);
        knobTime2.setParent(relativeLayout3);
        knobTime2.initViews();
        knobTime2.setStateListener(this);
        this.j.init();
        this.j.initTimeKnob();
        this.j.setDrawActiveIndicator(true);
        this.j.setKnobTimeListener(this);
        KnobSpeed knobSpeed2 = (KnobSpeed) this.knobMiddleContainer.findViewById(R.id.knob);
        this.h = knobSpeed2;
        RelativeLayout relativeLayout4 = this.knobMiddleContainer;
        knobSpeed2.setKnobTag(KNOB.MIDDLE);
        knobSpeed2.setParent(relativeLayout4);
        knobSpeed2.initViews();
        knobSpeed2.setStateListener(this);
        this.h.init();
        this.h.setDrawActiveIndicator(true);
        this.h.setKnobValueListener(this);
        this.A = (ImageView) this.knobMiddleContainer.findViewById(R.id.warning_no_stirrer_iv);
        l();
        KnobTemperature knobTemperature = (KnobTemperature) this.knobRightContainer.findViewById(R.id.knob);
        this.i = knobTemperature;
        RelativeLayout relativeLayout5 = this.knobRightContainer;
        knobTemperature.setKnobTag(KNOB.RIGHT);
        knobTemperature.setParent(relativeLayout5);
        knobTemperature.initViews();
        knobTemperature.setStateListener(this);
        this.i.init();
        this.i.setDrawActiveIndicator(true);
        this.i.setKnobTemperatureListener(this);
        KnobScale knobScale = (KnobScale) this.scaleContainer.findViewById(R.id.knob);
        this.g = knobScale;
        RelativeLayout relativeLayout6 = this.scaleContainer;
        knobScale.setKnobTag(KNOB.SCALE);
        knobScale.setParent(relativeLayout6);
        knobScale.initViews();
        knobScale.setStateListener(this);
        this.g.init();
        this.g.setKnobScaleTareListener(this);
        ((ImageView) this.scaleContainer.findViewById(R.id.scale_confirm_iv)).setOnClickListener(new View.OnClickListener() { // from class: ff
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.a(view3);
            }
        });
        RelativeLayout relativeLayout7 = this.controlRelativeLayout;
        if (relativeLayout7 != null) {
            this.e = new CookingControls(relativeLayout7, new CookingControls.StateChangeListener() { // from class: vj
                @Override // controller.CookingControls.StateChangeListener
                public final void onStateChanged(int i) {
                    this.a.a(i);
                }
            });
        }
        ICommandInterface commandInterface = App.getInstance().getMachineAdapter().getCommandInterface();
        this.d = commandInterface;
        commandInterface.setScaleCalibration(2);
        initViews();
    }

    public final void p() {
        a(CookingManager.getInstance().getDirection() == 0 ? 1 : 0, CookingManager.getInstance().isDirectionModifiable());
    }

    public void performStopClick() {
        this.e.performStopClick();
    }

    public void processStop() {
        CookingManager.getInstance().stopCooking();
        this.e.setCurrentState(5);
        h();
    }

    public final boolean b(boolean z) {
        a(false, true);
        if (CookingManager.getInstance().getMeasuredTemperature() < 60) {
            return false;
        }
        if (z) {
            DialogHelper.getInstance().showTurboDisabledDialog((RelativeLayout) getActivity().findViewById(R.id.dialog_turbo_disabled));
            cancelLastKnobSelection();
        }
        return true;
    }

    public final boolean e() {
        if (this.s || CookingManager.getInstance().getMeasuredTemperature() < Limits.WARNING_TEMPERATURE_AT_HIGH_SPEED || CookingManager.getInstance().getSpeedLevel() < Limits.WARNING_SPEED_LEVEL_AT_HIGH_TEMPERATURE) {
            return true;
        }
        this.e.performPlayClick();
        this.c.setVisibility(0);
        this.c.setButtonOneClickListener(new ActionListener() { // from class: hf
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((QuestionDialogView) obj);
            }
        });
        this.c.setButtonTwoClickListener(new ActionListener() { // from class: if
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.b((QuestionDialogView) obj);
            }
        });
        return false;
    }

    public /* synthetic */ void a(Integer num) {
        if (num.intValue() == 0 || num.intValue() == 11) {
            d();
        }
    }

    public /* synthetic */ void d(QuestionDialogView questionDialogView) {
        this.c.setVisibility(8);
        int speedLevel = CookingManager.getInstance().getSpeedLevel();
        int temperatureLevel = CookingManager.getInstance().getTemperatureLevel();
        long time = CookingManager.getInstance().getTime();
        performStopClick();
        CookingManager.getInstance().setTime(time);
        CookingManager.getInstance().setTemperatureLevel(temperatureLevel);
        CookingManager.getInstance().setSpeedLevel(speedLevel);
        k();
        i();
        j();
    }

    public final boolean f() {
        if (this.s || CookingManager.getInstance().getMeasuredTemperature() < Limits.WARNING_TEMPERATURE_AT_HIGH_SPEED || CookingManager.getInstance().getSpeedLevel() < Limits.WARNING_SPEED_LEVEL_AT_HIGH_TEMPERATURE) {
            return true;
        }
        this.e.performPlayClick();
        this.c.setVisibility(0);
        this.c.setButtonOneClickListener(new ActionListener() { // from class: of
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.d((QuestionDialogView) obj);
            }
        });
        this.c.setButtonTwoClickListener(new ActionListener() { // from class: pf
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.c((QuestionDialogView) obj);
            }
        });
        return false;
    }

    public final void a(Knob knob) {
        KnobTime knobTime = this.j;
        knobTime.setActive(knob == knobTime);
        KnobSpeed knobSpeed = this.h;
        knobSpeed.setActive(knob == knobSpeed);
        KnobTemperature knobTemperature = this.i;
        knobTemperature.setActive(knob == knobTemperature);
    }

    public final Knob b() {
        if (this.j.isActive()) {
            return this.j;
        }
        if (this.h.isActive()) {
            return this.h;
        }
        if (this.i.isActive()) {
            return this.i;
        }
        return null;
    }

    public final void a(boolean z) {
        if (z) {
            this.o.setImageResource(R.drawable.selector_scale);
        } else {
            this.o.setImageResource(R.drawable.scale_deactivated);
        }
    }

    public class a extends MachineCallbackAdapter {
        public a() {
        }

        public /* synthetic */ void a(long j) {
            int i = (int) j;
            CookingManager.getInstance().setMeasuredTemperature(i);
            MainFragment.this.a();
            MainFragment.this.i.setMeasuredTemperature(i);
            if (MainFragment.this.b(false)) {
                MainFragment mainFragment = MainFragment.this;
                if (mainFragment.t != null) {
                    mainFragment.m();
                }
            }
        }

        public /* synthetic */ void b() {
            CookingManager.getInstance().pauseCooking();
            MainFragment.this.e.setCurrentState(3);
        }

        public /* synthetic */ void c() {
            MainFragment.this.a(false, false);
            App.getInstance().playSound(R.raw.lid_closed, SoundLength.MIDDLE);
        }

        public /* synthetic */ void d() {
            MainFragment.this.m();
            UsageLogger.getInstance().logSealOpened();
            MainFragment.this.a(true, false);
            App.getInstance().playSound(R.raw.lid_opened, SoundLength.MIDDLE);
            if (CookingManager.getInstance().getState() == 1) {
                if (CookingManager.getInstance().getRunningTimeMode() == 10 && CookingManager.getInstance().getElapsedTime() == 0) {
                    CookingManager.getInstance().stopCooking();
                } else {
                    CookingManager.getInstance().pauseCooking();
                }
                MainFragment.this.a(true);
                MainFragment.this.cancelLastKnobSelection();
                MainFragment.this.e.setCurrentState(3);
                DialogHelper.getInstance().showLidOpenDialog(true);
                UsageLogger.getInstance().logMotorBraked();
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onBadState(final int[] iArr) {
            String str = MainFragment.D;
            StringBuilder sbA = g9.a("onBadState >> view ");
            sbA.append(LayoutHelper.getInstance().getSelectedView());
            sbA.append(" >> warnings ");
            sbA.append(Arrays.toString(iArr));
            sbA.append(" >> active view ");
            sbA.append(LayoutHelper.getInstance().getSelectedView());
            Log.w(str, sbA.toString());
            if (LayoutHelper.getInstance().isViewSelected(0, 11)) {
                boolean zContains = ArrayUtils.contains(iArr, 7);
                int[] iArrRemoveElement = ArrayUtils.removeElement(iArr, 7);
                if (zContains && !MainFragment.this.o.isSelected()) {
                    Log.w(MainFragment.D, "onBadState - ignore scale overload");
                    iArr = iArrRemoveElement;
                }
                if (iArr.length == 0) {
                    return;
                }
                App.getInstance().playSound(R.raw.error, SoundLength.MIDDLE);
                if (ArrayUtils.contains(iArr, 13)) {
                    MainFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: xe
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.a.a();
                        }
                    });
                }
                if (ArrayUtils.contains(iArr, 5)) {
                    MainFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: bf
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.a.b();
                        }
                    });
                }
                final RelativeLayout relativeLayout = (RelativeLayout) MainFragment.this.getActivity().findViewById(R.id.dialog_warning);
                MainFragment.this.n.post(new Runnable() { // from class: ye
                    @Override // java.lang.Runnable
                    public final void run() {
                        DialogHelper.getInstance().showWarningDialog(relativeLayout, iArr);
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialPushed(int i) {
            if (LayoutHelper.getInstance().isViewSelected(0, 11)) {
                Log.i(MainFragment.D, "onJogDialPushed >> pushstate " + i);
                if (1 == i) {
                    MainFragment mainFragment = MainFragment.this;
                    if (mainFragment == null) {
                        throw null;
                    }
                    ArrayList arrayList = new ArrayList();
                    if (mainFragment.j.getEnabled()) {
                        arrayList.add(mainFragment.j);
                    }
                    if (mainFragment.h.getEnabled()) {
                        arrayList.add(mainFragment.h);
                    }
                    if (mainFragment.i.getEnabled()) {
                        arrayList.add(mainFragment.i);
                    }
                    if (arrayList.size() <= 1) {
                        if (arrayList.size() > 0) {
                            mainFragment.a((Knob) arrayList.get(0));
                        }
                    } else {
                        int iIndexOf = arrayList.indexOf(mainFragment.b());
                        if (iIndexOf == -1) {
                            mainFragment.a((Knob) arrayList.get(0));
                        } else {
                            int i2 = iIndexOf + 1;
                            mainFragment.a((Knob) arrayList.get(i2 < arrayList.size() ? i2 : 0));
                        }
                    }
                }
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialTurned(int i, long j) {
            if (LayoutHelper.getInstance().isViewSelected(0, 11)) {
                Log.i(MainFragment.D, "onJogDialTurned >> direction " + i + " >> speed " + j);
                final Knob knobB = MainFragment.this.b();
                if (knobB != null) {
                    final int iAddTemperatureSteps = 0;
                    int progress = knobB.getProgress();
                    MainFragment mainFragment = MainFragment.this;
                    if (mainFragment.j == knobB) {
                        if (CookingManager.getInstance().getRunningTimeMode() == 10 && (CookingManager.getInstance().getState() == 1 || CookingManager.getInstance().getState() == 2)) {
                            return;
                        } else {
                            iAddTemperatureSteps = i == 0 ? progress - ((int) j) : progress + ((int) j);
                        }
                    } else if (mainFragment.h == knobB) {
                        iAddTemperatureSteps = i == 0 ? (int) (progress - (j * 100)) : (int) ((j * 100) + progress);
                    } else {
                        KnobTemperature knobTemperature = mainFragment.i;
                        if (knobTemperature == knobB) {
                            iAddTemperatureSteps = i == 0 ? knobTemperature.addTemperatureSteps(((int) j) * (-1)) : knobTemperature.addTemperatureSteps((int) j);
                        }
                    }
                    MainFragment.this.n.post(new Runnable() { // from class: cf
                        @Override // java.lang.Runnable
                        public final void run() {
                            knobB.updateProgress(iAddTemperatureSteps, true);
                        }
                    });
                    MainFragment.E = true;
                }
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onLidClosed() {
            if (LayoutHelper.getInstance().isViewSelected(0, 11)) {
                Log.v(MainFragment.D, "onLidClosed");
                MainFragment.this.n.post(new Runnable() { // from class: df
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.c();
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onLidOpened() {
            if (LayoutHelper.getInstance().isViewSelected(0, 11)) {
                Log.v(MainFragment.D, "onLidOpened");
                MainFragment.this.n.post(new Runnable() { // from class: af
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.d();
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onMachineInterfaceConnected() {
            Log.v(MainFragment.D, "onMachineInterfaceConnected: ");
            int i = (int) MainFragment.this.d.getCurrentHeatingElementState().temperature;
            CookingManager.getInstance().setMeasuredTemperature(i);
            MainFragment.this.i.setMeasuredTemperature(i);
            if (MainFragment.this.b(false)) {
                MainFragment mainFragment = MainFragment.this;
                if (mainFragment.t != null) {
                    mainFragment.m();
                }
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onMotorSpeedChanged(long j, int i) {
            Log.i(MainFragment.D, "Motor >> speed " + j + " >> direction " + i);
            super.onMotorSpeedChanged(j, i);
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onMotorStateChanged(int i) {
            Log.i(MainFragment.D, "Motor >> state " + i);
            super.onMotorStateChanged(i);
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onScaleStateChanged(final int i, final long j, int i2) {
            MainFragment.this.B = j;
            if (LayoutHelper.getInstance().isViewSelected(0, 11)) {
                Log.v(MainFragment.D, "onScaleStateChanged >> scaleState " + i + " >> scaleMeasureValue " + j);
                MainFragment.this.n.post(new Runnable() { // from class: ef
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.a(i, j);
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public synchronized void onTemperatureChanged(final long j) {
            if (LayoutHelper.getInstance().isViewSelected(0, 11)) {
                Log.v(MainFragment.D, "onTemperatureChanged >> temperature " + j);
                MainFragment.this.n.post(new Runnable() { // from class: ze
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.a(j);
                    }
                });
            }
        }

        public /* synthetic */ void a(int i, long j) {
            MainFragment.this.a(i, j);
        }

        public /* synthetic */ void a() {
            MainFragment.this.performStopClick();
        }
    }

    public final void e(boolean z) {
        ICommandInterface commandInterface = App.getInstance().getMachineAdapter().getCommandInterface();
        if (z) {
            commandInterface.start(2, 10, 1, 0);
            UsageLogger.getInstance().logStart(2, 10, 0);
        } else {
            commandInterface.stop();
            UsageLogger.getInstance().logStop();
        }
    }

    public final void a(int i, long j) {
        if (this.g == null || this.scaleContainer.getVisibility() != 0) {
            return;
        }
        if (1 == i) {
            this.g.setScaleOverloadText();
        } else {
            this.g.setScaleFromBus(j);
        }
    }

    public /* synthetic */ void b(View view2) {
        if (this.t != null) {
            return;
        }
        cancelLastKnobSelection();
        Log.i(D, "speedDirectionImageView: onClick");
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        CookingManager.getInstance().toggleDirection();
        a();
        a(false, true);
        p();
        o();
    }

    public final void a() {
        CookingManager cookingManager = CookingManager.getInstance();
        String str = D;
        StringBuilder sbA = g9.a("apply values >> elapsed ");
        sbA.append(cookingManager.getElapsedTime());
        sbA.append(" >> remaining ");
        sbA.append(cookingManager.getRemainingTime());
        sbA.append(" >> set ");
        sbA.append(cookingManager.getTimeToRun());
        sbA.append(" >> display ");
        sbA.append(cookingManager.getDisplayTime());
        sbA.append(" / ");
        sbA.append(cookingManager.getDisplaySetTime());
        Log.i(str, sbA.toString());
        if (!cookingManager.isPreheatStep()) {
            this.j.setTime(cookingManager.getDisplayTime());
        } else {
            this.j.setCurrentAngleUsingMillis(CookingManager.getInstance().getDisplayTime());
        }
        KnobTime knobTime = this.j;
        boolean z = true;
        if (!cookingManager.isTimeKnobModifiable() || (cookingManager.getRunningTimeMode() == 10 && (cookingManager.getState() == 1 || cookingManager.getState() == 2))) {
            z = false;
        }
        knobTime.setMoveEnabled(z);
        this.j.valueSetText(cookingManager.getDisplaySetTime());
        this.j.setMaxTimeInMillis(cookingManager.getTimeLimit());
        int speedLevel = cookingManager.getSpeedLevel();
        this.h.setText(String.valueOf(speedLevel));
        this.h.setMaxValueLimit(cookingManager.getSpeedLevelLimit());
        KnobSpeed knobSpeed = this.h;
        knobSpeed.updateProgress(knobSpeed.getProgressFromValue(speedLevel), false);
        l();
        this.i.setTemperatureLevelLimit(cookingManager.getTemperatureLevelLimit());
        this.i.setTemperatureLevel(cookingManager.getTemperatureLevel());
    }

    public final void d(boolean z) {
        this.g.setScaled();
        if (z) {
            if (this.e.isCooking()) {
                this.e.performPlayClick();
            }
            this.scaleContainer.setVisibility(0);
            this.knobRightContainer.setVisibility(8);
            this.o.setSelected(true);
            return;
        }
        this.scaleContainer.setVisibility(8);
        this.knobRightContainer.setVisibility(0);
        this.o.setSelected(false);
    }

    public /* synthetic */ void b(QuestionDialogView questionDialogView) {
        this.c.setVisibility(8);
        this.s = true;
        this.e.performPlayClick();
    }

    public /* synthetic */ void c(View view2) {
        if (this.t != null) {
            return;
        }
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        int currentState = this.e.getCurrentState();
        if (1 == currentState || 3 == currentState || 5 == currentState) {
            if (this.o.isSelected()) {
                d(false);
                return;
            }
            this.d.setScaleTare();
            this.d.setScaleTare();
            this.d.setScaleTare();
            cancelLastKnobSelection();
            d(true);
            ICommandInterface.ScaleState currentScaleState = this.d.getCurrentScaleState();
            if (currentScaleState != null) {
                a(currentScaleState.scaleState, currentScaleState.scaleMeasureValue);
            }
        }
    }

    public final synchronized void a(int i) {
        if (LayoutHelper.getInstance().isViewSelected(0, 11)) {
            Log.i(D, "handleControlStateChanged >> state " + i);
            if (i == 1) {
                Log.v(D, "handleControlStateChanged: CookingControls.State.IDLE");
                a(true);
                a(false, true);
                this.s = false;
            } else if (i == 2) {
                Log.v(D, "handleControlStateChanged: CookingControls.State.PLAY");
                CookingManager.getInstance().startCooking();
                if (c(false) && f()) {
                    cancelLastKnobSelection();
                    a(false);
                    if (!E) {
                        k();
                        i();
                        j();
                    }
                    c();
                    a();
                    a(false, true);
                }
            } else if (i == 3) {
                Log.v(D, "handleControlStateChanged: CookingControls.State.PAUSE");
                a(true);
                a(false, true);
                cancelLastKnobSelection();
                CookingManager.getInstance().pauseCooking();
            } else if (i == 4) {
                Log.v(D, "handleControlStateChanged: CookingControls.State.RESUME");
                CookingManager.getInstance().resumeCooking();
                if (c(true) && e()) {
                    a(false);
                    a(false, true);
                    cancelLastKnobSelection();
                }
            } else if (i == 5) {
                this.s = false;
                Log.v(D, "handleControlStateChanged: CookingControls.State.STOP");
                a(true);
                cancelLastKnobSelection();
                if (this.z != null) {
                    CookingManager.getInstance().setInitCookingStep(this.z.mo10clone());
                }
                h();
                a();
                a(false, true);
            }
        }
    }

    public final boolean c(boolean z) {
        if (this.d.getErrorState() != 0) {
            if (this.e.isCooking()) {
                this.e.performPlayClick();
            }
            DialogHelper.getInstance().showWarningDialog(this.d.getErrorState());
            return false;
        }
        if (this.d.getCurrentLidState() == 1) {
            return true;
        }
        String str = D;
        StringBuilder sbA = g9.a("isReadyForCooking ");
        sbA.append(this.e.isCooking());
        Log.w(str, sbA.toString());
        if (this.e.isCooking()) {
            if (!z && CookingManager.getInstance().getRunningTimeMode() == 10) {
                performStopClick();
            } else {
                this.e.performPlayClick();
            }
        }
        DialogHelper.getInstance().showLidOpenDialog(false);
        return false;
    }

    public /* synthetic */ void c(QuestionDialogView questionDialogView) {
        this.c.setVisibility(8);
        this.s = true;
        this.e.performPlayClick();
    }

    public /* synthetic */ void a(View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        d(false);
    }

    public /* synthetic */ boolean a(View view2, MotionEvent motionEvent) {
        if (this.u.isSelected() && motionEvent.getAction() == 0 && LayoutHelper.getInstance().isViewSelected(0, 11)) {
            App.getInstance().playSound(R.raw.click, SoundLength.LONG);
            Handler handler = new Handler();
            this.t = handler;
            this.v = 0;
            handler.postDelayed(this.w, 100L);
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 4) {
            m();
        }
        return true;
    }

    public /* synthetic */ void a(QuestionDialogView questionDialogView) {
        this.c.setVisibility(8);
    }

    public final void a(int i, boolean z) {
        ImageView imageView = this.q;
        if (imageView == null || this.r == null) {
            return;
        }
        if (1 == i) {
            imageView.setImageResource(z ? R.drawable.button_speed_direction_right_enabled : R.drawable.button_speed_direction_right_disabled);
            this.r.setText(getString(R.string.right_direction));
        } else {
            imageView.setImageResource(z ? R.drawable.button_speed_direction_left_enabled : R.drawable.button_speed_direction_left_disabled);
            this.r.setText(getString(R.string.left_direction));
        }
        this.q.setClickable(z);
    }

    public final void a(boolean z, boolean z2) {
        boolean z3 = false;
        boolean z4 = !z && (CookingManager.getInstance().getState() == 0 || CookingManager.getInstance().getState() == 3) && CookingManager.getInstance().getMeasuredTemperature() < 60 && CookingManager.getInstance().getTemperatureLevel() < 5 && CookingManager.getInstance().getDirection() != 0 && CookingManager.getInstance().isTurboEnabled();
        if (z2) {
            int currentLidState = this.d.getCurrentLidState();
            if (z4 && currentLidState == 1) {
                z3 = true;
            }
            z4 = z3;
        }
        this.u.setSelected(z4);
        if (z4) {
            ImageView imageView = this.u;
            if (imageView != null) {
                imageView.setImageDrawable(ContextCompat.getDrawable(App.getInstance(), R.drawable.button_turbo_enabled));
                this.u.invalidate();
                return;
            }
            return;
        }
        ImageView imageView2 = this.u;
        if (imageView2 != null) {
            imageView2.setImageDrawable(ContextCompat.getDrawable(App.getInstance(), R.drawable.button_turbo_disabled));
            this.u.invalidate();
        }
    }
}
