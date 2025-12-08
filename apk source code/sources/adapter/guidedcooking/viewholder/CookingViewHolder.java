package adapter.guidedcooking.viewholder;

import activity.MainActivity;
import adapter.guidedcooking.view.StepViewInfo;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import db.model.Recipe;
import de.silpion.mc2.R;
import defpackage.g9;
import fragment.MainFragment;
import helper.ActionListener;
import helper.DialogHelper;
import helper.KnobUtils;
import helper.LayoutHelper;
import helper.UsageLogger;
import java.util.ArrayList;
import machineAdapter.ICommandInterface;
import machineAdapter.IMachineCallback;
import machineAdapter.adapter.MachineCallbackAdapter;
import mcapi.McUsageApi;
import org.json.JSONException;
import sound.SoundLength;
import view.QuestionDialogView;
import view.knob.Knob;
import view.knob.KnobListener;
import view.knob.KnobSpeed;
import view.knob.KnobTemperature;
import view.knob.KnobTime;

/* loaded from: classes.dex */
public class CookingViewHolder extends StepViewHolder implements CookingManager.CookingListener, KnobListener {
    public boolean A;
    public KnobSpeed B;
    public KnobTemperature C;
    public KnobTime D;
    public String E;
    public long F;
    public volatile boolean G;
    public long H;
    public ImageView I;
    public final IMachineCallback J;

    @BindView(R.id.cooking_controls_container)
    @Nullable
    public RelativeLayout controlRelativeLayout;

    @BindView(R.id.cooking_label_tv)
    public TextView cookingLabelTextView;

    @BindView(R.id.knob_left)
    public RelativeLayout knobLeftContainer;

    @BindView(R.id.knob_middle)
    public RelativeLayout knobMiddleContainer;

    @BindView(R.id.knob_right)
    public RelativeLayout knobRightContainer;

    @BindView(R.id.step_number_rl)
    public RelativeLayout numberContainerRelativeLayout;

    @BindView(R.id.speed_warning_question)
    public QuestionDialogView speedWarningQuestion;
    public TextView t;
    public TextView u;
    public TextView v;
    public Long w;
    public StepViewInfo x;
    public ICommandInterface y;
    public CookingControls z;

    public CookingViewHolder(View view2) {
        super(view2);
        this.y = App.getInstance().getMachineAdapter().getCommandInterface();
        this.A = false;
        this.F = 0L;
        this.G = false;
        this.J = new a();
        ButterKnife.bind(this, view2);
        this.v = (TextView) view2.findViewById(R.id.item_step_number_tv);
        this.t = (TextView) view2.findViewById(R.id.recipe_name_horizontal);
        this.u = (TextView) view2.findViewById(R.id.recipe_name_vertical);
        r();
        RelativeLayout relativeLayout = this.controlRelativeLayout;
        if (relativeLayout != null) {
            this.z = new CookingControls(relativeLayout, new CookingControls.StateChangeListener() { // from class: w0
                @Override // controller.CookingControls.StateChangeListener
                public final void onStateChanged(int i) {
                    this.a.d(i);
                }
            });
        }
    }

    public static /* synthetic */ String D() {
        return "CookingViewHolder";
    }

    public final void A() {
        Log.v("CookingViewHolder", "resetTimeKnob");
        if (this.D == null) {
            return;
        }
        CookingManager cookingManager = CookingManager.getInstance();
        CookingStep initCookingStep = cookingManager.getInitCookingStep();
        this.D.setDisplayProgress(true);
        if (this.D.getCurrentMode() == 1) {
            this.D.initTimeKnob();
        }
        if (initCookingStep instanceof CookingUnit) {
            CookingUnit cookingUnit = (CookingUnit) initCookingStep;
            this.D.setTime(cookingUnit.getCookingUnitDurationInMillis());
            this.D.updateProgressTime(cookingUnit.getCookingUnitDurationInMillis());
        } else if (!(initCookingStep instanceof CookingSteps)) {
            this.D.setTime(cookingManager.getTime());
            this.D.updateProgressTime(cookingManager.getTime());
        } else if (cookingManager.isPreheatStep()) {
            SingleCookingStep singleCookingStep = ((CookingSteps) initCookingStep).getSingleCookingSteps().get(cookingManager.getCurrentCookingStepIndex() + 1);
            this.D.setTime(singleCookingStep.getCookingDurationInMillis());
            this.D.updateProgressTime(singleCookingStep.getCookingDurationInMillis());
        } else {
            this.D.setTime(cookingManager.getDisplayTime());
            this.D.updateProgressTime(cookingManager.getDisplayTime());
        }
        if (cookingManager.getTimeLimit() != -1) {
            this.D.setMaxTimeInMillis((int) cookingManager.getTimeLimit());
        } else {
            this.D.setMaxTimeInMillis(Limits.TIME_MAX_MILLIS);
        }
        this.D.setEnabled(cookingManager.isTimeKnobModifiable());
        this.D.setActive(false);
        this.D.setDrawActiveIndicator(cookingManager.isTimeKnobModifiable());
        this.D.setToIdleState();
        String timeDescription = cookingManager.getTimeDescription();
        if (TextUtils.isEmpty(timeDescription)) {
            this.D.setValueSetTextSizeNormal();
            if (this.A) {
                this.D.setModeImageToSelected();
            }
            this.D.valueSetVisibility(this.A);
            return;
        }
        this.D.setValueSetTextSizeSmall();
        this.D.valueSetText(timeDescription);
        this.D.setModeImageToSelected();
        this.D.valueSetVisibility(true);
    }

    public final void B() {
        if (this.I == null || this.B == null) {
            return;
        }
        if (CookingManager.getInstance().getSpeedLevel() >= 5) {
            this.itemView.post(new Runnable() { // from class: b1
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.w();
                }
            });
            return;
        }
        CookingControls cookingControls = this.z;
        if (cookingControls != null && cookingControls.getCurrentState() == 1) {
            this.B.setModeImageToIdle();
        }
        this.I.setVisibility(8);
    }

    public final void C() {
        this.B.setReverse(CookingManager.getInstance().getDirection() == 0);
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void activate() {
        Log.i("CookingViewHolder", "activate");
        this.J.onMachineInterfaceConnected();
        r();
        CookingManager.getInstance().setCookingListener(this);
        App.getInstance().getMachineAdapter().registerMachineCallback(this.J);
        refreshData();
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void bindStep(StepViewInfo stepViewInfo, Recipe recipe, int i, boolean z) {
        this.x = stepViewInfo;
        this.w = recipe == null ? null : recipe.getId();
        if (z) {
            this.numberContainerRelativeLayout.setVisibility(0);
            this.v.setText(c(i));
            this.u.setVisibility(0);
            this.t.setVisibility(8);
            this.u.setText(recipe != null ? recipe.getName() : "");
        } else {
            this.numberContainerRelativeLayout.setVisibility(8);
            this.u.setVisibility(8);
            this.t.setVisibility(0);
            this.t.setText(recipe != null ? recipe.getName() : "");
        }
        this.A = false;
        refreshData();
    }

    public /* synthetic */ void c(QuestionDialogView questionDialogView) {
        this.speedWarningQuestion.setVisibility(8);
        int speedLevel = CookingManager.getInstance().getSpeedLevel();
        int temperatureLevel = CookingManager.getInstance().getTemperatureLevel();
        long time = CookingManager.getInstance().getTime();
        this.z.performStopClick();
        CookingManager.getInstance().setTime(time);
        CookingManager.getInstance().setTemperatureLevel(temperatureLevel);
        CookingManager.getInstance().setSpeedLevel(speedLevel);
        A();
        y();
        z();
    }

    public final synchronized void d(int i) {
        if (LayoutHelper.getInstance().isViewSelected(4)) {
            Log.i("CookingViewHolder", "handleControlStateChanged >> state " + i);
            if (i == 1) {
                Log.v("CookingViewHolder", "handleControlStateChanged: CookingControls.State.IDLE");
                this.G = false;
            } else if (i == 2) {
                Log.v("CookingViewHolder", "handleControlStateChanged: CookingControls.State.PLAY");
                CookingManager.getInstance().startCooking();
                if (t() && v()) {
                    p();
                    Log.d("CookingViewHolder", "handlePlay");
                    s();
                    o();
                }
            } else if (i == 3) {
                Log.v("CookingViewHolder", "handleControlStateChanged: CookingControls.State.PAUSE");
                p();
                CookingManager.getInstance().pauseCooking();
            } else if (i == 4) {
                Log.v("CookingViewHolder", "handleControlStateChanged: CookingControls.State.RESUME");
                CookingManager.getInstance().resumeCooking();
                if (t() && u()) {
                    p();
                }
            } else if (i == 5) {
                Log.v("CookingViewHolder", "handleControlStateChanged: CookingControls.State.STOP");
                p();
                this.G = false;
                x();
                CookingManager.getInstance().stopCooking();
                o();
            }
        }
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void deactivate() {
        Log.i("CookingViewHolder", "deactivate");
        if (this.z.isCooking()) {
            this.z.performPlayClick();
        }
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.J);
    }

    public /* synthetic */ void e(QuestionDialogView questionDialogView) {
        this.speedWarningQuestion.setVisibility(8);
        o();
    }

    public /* synthetic */ void f(QuestionDialogView questionDialogView) {
        this.speedWarningQuestion.setVisibility(8);
        CookingManager.getInstance().setSpeedLevel(Limits.WARNING_SPEED_LEVEL_AT_HIGH_TEMPERATURE);
        o();
        this.G = true;
    }

    public boolean hasActiveKnob() {
        return q() != null;
    }

    @Override // view.knob.Knob.StateListener
    public void knobSelected(Knob knob, int i, String str) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        Log.d("CookingViewHolder", "handleKnobState: state >> " + i + ", tag >> " + str + "handleKnobState: lastSelectedKnobTag >> " + this.E);
        if (2 != i || str.equals(this.E)) {
            return;
        }
        a(knob);
        p();
        this.E = str;
    }

    public final void o() {
        CookingManager cookingManager = CookingManager.getInstance();
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
        Log.i("CookingViewHolder", sbA.toString());
        if (cookingManager.isPreheatStep() || this.A) {
            this.D.setCurrentAngleUsingMillis(cookingManager.getDisplayTime());
        } else {
            this.D.setTime(cookingManager.getDisplayTime());
        }
        KnobTime knobTime = this.D;
        boolean z = true;
        if (!cookingManager.isTimeKnobModifiable() || (cookingManager.getRunningTimeMode() == 10 && (cookingManager.getState() == 1 || cookingManager.getState() == 2))) {
            z = false;
        }
        knobTime.setMoveEnabled(z);
        this.D.valueSetText(this.F);
        this.D.setMaxTimeInMillis(cookingManager.getTimeLimit());
        int speedLevel = cookingManager.getSpeedLevel();
        this.B.setText(String.valueOf(speedLevel));
        this.B.setMaxValueLimit(cookingManager.getSpeedLevelLimit());
        KnobSpeed knobSpeed = this.B;
        knobSpeed.updateProgress(knobSpeed.getProgressFromValue(speedLevel), false);
        B();
        this.C.setTemperatureLevelLimit(cookingManager.getTemperatureLevelLimit());
        this.C.setTemperatureLevel(cookingManager.getTemperatureLevel());
    }

    @Override // cooking.CookingManager.CookingListener
    public void onFinishCooking() {
        Log.v("CookingViewHolder", "onFinishCooking");
        this.A = true;
        this.D.setCurrentState(1);
        if (this.D.getEnabled()) {
            KnobUtils.setActiveIndicator(this.D);
        }
        this.B.setCurrentState(1);
        if (this.B.getEnabled()) {
            KnobUtils.setActiveIndicator(this.B);
        }
        this.C.setCurrentState(1);
        if (this.C.getEnabled()) {
            KnobUtils.setActiveIndicator(this.C);
        }
        int i = MainActivity.currentGlobalMode;
        if (10 == i || 5 == i) {
            MainActivity.currentGlobalMode = 5;
        } else {
            MainActivity.currentGlobalMode = 4;
        }
        this.D.setTime(0L);
        this.D.setProgress(0);
        CookingManager.getInstance().illustrateStopCooking();
        n();
        this.D.setModeImageToSelected();
        this.D.valueSetVisibility(true);
        App.getInstance().playSound(R.raw.finished, SoundLength.LONG);
        CookingControls cookingControls = this.z;
        if (cookingControls != null) {
            cookingControls.setCurrentState(1);
        }
    }

    @Override // cooking.CookingManager.CookingListener
    public void onNextSingleCookingStep(SingleCookingStep singleCookingStep) {
        Log.v("CookingViewHolder", "onNextSingleCookingStep " + singleCookingStep);
        if (App.getInstance().getString(R.string.steaming).equals(singleCookingStep.getDescription())) {
            App.getInstance().playSound(R.raw.bell, SoundLength.SHORT);
        }
        s();
        o();
    }

    @Override // cooking.CookingManager.CookingListener
    public void onPauseCooking() {
        Log.v("CookingViewHolder", "onPauseCooking");
    }

    @Override // cooking.CookingManager.CookingListener
    public void onResumeCooking() {
        Log.v("CookingViewHolder", "onResumeCooking");
        o();
    }

    @Override // view.knob.Knob.SkipPreheatStepListener
    public void onSkipPreheatStep() {
        CookingManager.getInstance().skipCookingStep();
    }

    @Override // cooking.CookingManager.CookingListener
    public void onStartCooking(int i) throws JSONException {
        Log.v("CookingViewHolder", "onStartCooking");
        s();
        this.D.setCurrentMode(i);
        KnobUtils.setActiveIndicator(this.D);
        KnobUtils.setActiveIndicator(this.B);
        KnobUtils.setActiveIndicator(this.C);
        if (this.x == null || this.w == null) {
            return;
        }
        McUsageApi.getInstance().postRecipeStepEvent(this.w.longValue(), this.x.getStepIndex(), true);
    }

    @Override // cooking.CookingManager.CookingListener
    public void onStopCooking() throws JSONException {
        Log.v("CookingViewHolder", "onStopCooking");
        A();
        o();
        this.z.performStopClick();
        C();
        if (this.x == null || this.w == null) {
            return;
        }
        McUsageApi.getInstance().postRecipeStepEvent(this.w.longValue(), this.x.getStepIndex(), false);
    }

    @Override // view.knob.KnobScale.KnobScaleTareListener
    public void onTareClick() {
    }

    @Override // view.knob.KnobTemperature.KnobTemperatureListener
    public void onTemperatureValueSet(int i) {
        Log.v("CookingViewHolder", "onTemperatureValueSet >> value " + i);
        CookingManager.getInstance().setTemperature(i);
        o();
    }

    @Override // cooking.CookingManager.CookingListener
    public void onTickCooking(long j) {
        Log.i("CookingViewHolder", "onTickCooking millisUntilFinished = " + j);
        long elapsedTime = CookingManager.getInstance().getElapsedTime();
        if (10 != this.D.getCurrentMode()) {
            Log.v("CookingViewHolder", "onTickCooking MODE: TIMER_DOWN");
            long displayTime = CookingManager.getInstance().getDisplayTime();
            this.D.setTime(displayTime);
            this.D.updateProgressTime(displayTime);
            return;
        }
        Log.v("CookingViewHolder", "onTickCooking MODE: TIMER_UP , elapsed: " + elapsedTime);
        this.D.setTime(elapsedTime);
        this.D.updateProgressTime(elapsedTime);
    }

    @Override // view.knob.KnobTime.KnobTimeListener
    public void onTimeValueSet(long j) {
        Log.i("CookingViewHolder", "onTimeValueSet >> value " + j);
        CookingManager.getInstance().setTime(j);
        o();
    }

    @Override // view.knob.Knob.KnobValueListener
    public synchronized void onValueSet(int i) {
        Log.i("CookingViewHolder", "onValueSet >> value " + i);
        if (i <= 0 && CookingManager.getInstance().getState() == 1) {
            this.B.cancelTouchEvent();
            this.E = "";
            this.z.performStopClick();
        } else if (this.G || i < Limits.WARNING_SPEED_LEVEL_AT_HIGH_TEMPERATURE || CookingManager.getInstance().getState() != 1 || CookingManager.getInstance().getMeasuredTemperature() < Limits.WARNING_TEMPERATURE_AT_HIGH_SPEED) {
            CookingManager.getInstance().setSpeedLevel(i);
            o();
        } else {
            this.speedWarningQuestion.setVisibility(0);
            this.speedWarningQuestion.setButtonOneClickListener(new ActionListener() { // from class: f1
                @Override // helper.ActionListener
                public final void onAction(Object obj) {
                    this.a.e((QuestionDialogView) obj);
                }
            });
            this.speedWarningQuestion.setButtonTwoClickListener(new ActionListener() { // from class: g1
                @Override // helper.ActionListener
                public final void onAction(Object obj) {
                    this.a.f((QuestionDialogView) obj);
                }
            });
        }
    }

    public final void p() {
        StringBuilder sbA = g9.a("cancelLastKnobSelection: lastSelectedKnobTag >> ");
        sbA.append(this.E);
        Log.i("CookingViewHolder", sbA.toString());
        this.D.deselect();
        this.B.deselect();
        this.C.deselect();
        this.E = "";
    }

    public final Knob q() {
        if (this.D.isActive()) {
            return this.D;
        }
        if (this.B.isActive()) {
            return this.B;
        }
        if (this.C.isActive()) {
            return this.C;
        }
        return null;
    }

    public final void r() {
        KnobTime knobTime = (KnobTime) this.knobLeftContainer.findViewById(R.id.knob);
        this.D = knobTime;
        RelativeLayout relativeLayout = this.knobLeftContainer;
        knobTime.setKnobTag(MainFragment.KNOB.LEFT);
        knobTime.setParent(relativeLayout);
        knobTime.initViews();
        knobTime.setStateListener(this);
        this.D.init();
        this.D.initTimeKnob();
        this.D.setDrawActiveIndicator(true);
        this.D.setKnobTimeListener(this);
        KnobSpeed knobSpeed = (KnobSpeed) this.knobMiddleContainer.findViewById(R.id.knob);
        this.B = knobSpeed;
        RelativeLayout relativeLayout2 = this.knobMiddleContainer;
        knobSpeed.setKnobTag(MainFragment.KNOB.MIDDLE);
        knobSpeed.setParent(relativeLayout2);
        knobSpeed.initViews();
        knobSpeed.setStateListener(this);
        this.B.init();
        this.B.setDrawActiveIndicator(true);
        this.B.setKnobValueListener(this);
        this.I = (ImageView) this.knobMiddleContainer.findViewById(R.id.warning_no_stirrer_iv);
        B();
        KnobTemperature knobTemperature = (KnobTemperature) this.knobRightContainer.findViewById(R.id.knob);
        this.C = knobTemperature;
        RelativeLayout relativeLayout3 = this.knobRightContainer;
        knobTemperature.setKnobTag(MainFragment.KNOB.RIGHT);
        knobTemperature.setParent(relativeLayout3);
        knobTemperature.initViews();
        knobTemperature.setStateListener(this);
        this.C.init();
        this.C.setDrawActiveIndicator(true);
        this.C.setKnobTemperatureListener(this);
        a((Knob) null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void refreshData() {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: adapter.guidedcooking.viewholder.CookingViewHolder.refreshData():void");
    }

    public final void s() {
        this.A = false;
        CookingManager cookingManager = CookingManager.getInstance();
        this.D.setEnabled(cookingManager.isTimeKnobModifiable());
        this.B.setEnabled(cookingManager.isSpeedKnobModifiable());
        this.C.setEnabled(cookingManager.isTemperatureKnobModifiable());
        this.D.setModeImageToSelected();
        this.D.valueSetVisibility(true);
        if (TextUtils.isEmpty(cookingManager.getTimeDescription())) {
            this.D.valueSetText(cookingManager.getDisplaySetTime());
        }
        this.B.setModeImageToSelected();
        this.B.valueSetVisibility(true);
        if (TextUtils.isEmpty(cookingManager.getSpeedDescription())) {
            this.B.setText(String.valueOf(cookingManager.getSpeedLevel()));
            KnobSpeed knobSpeed = this.B;
            knobSpeed.setProgress(knobSpeed.getProgressFromValue(cookingManager.getSpeedLevel()));
        }
        C();
        this.C.setTemperatureLevel(cookingManager.getTemperatureLevel());
        this.C.setMeasuredTemperature(cookingManager.getMeasuredTemperature());
        if (!TextUtils.isEmpty(cookingManager.getTemperatureDescription())) {
            this.C.setTextForMeasuredTemperature(cookingManager.getTemperatureDescription());
        }
        if (cookingManager.isPreheatStep()) {
            this.B.setPreheatMode(this);
            this.D.setPreheatMode();
            return;
        }
        this.B.unsetPreheatMode();
        this.D.unsetPreheatMode();
        if (cookingManager.hasCustomInfo()) {
            this.B.showCustomInfo(cookingManager.getCustomInfo());
        } else {
            this.B.hideCustomInfo();
        }
    }

    public final boolean t() {
        if (this.y.getErrorState() != 0) {
            if (this.z.isCooking()) {
                this.z.performPlayClick();
            }
            DialogHelper.getInstance().showWarningDialog(this.y.getErrorState());
            return false;
        }
        if (1 == this.y.getCurrentLidState()) {
            return true;
        }
        if (this.z.isCooking()) {
            this.z.performPlayClick();
        }
        DialogHelper.getInstance().showLidOpenDialog(false);
        return false;
    }

    public final boolean u() {
        if (this.G || CookingManager.getInstance().getMeasuredTemperature() < Limits.WARNING_TEMPERATURE_AT_HIGH_SPEED || CookingManager.getInstance().getSpeedLevel() < Limits.WARNING_SPEED_LEVEL_AT_HIGH_TEMPERATURE) {
            return true;
        }
        this.z.performPlayClick();
        this.speedWarningQuestion.setVisibility(0);
        this.speedWarningQuestion.setButtonOneClickListener(new ActionListener() { // from class: d1
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((QuestionDialogView) obj);
            }
        });
        this.speedWarningQuestion.setButtonTwoClickListener(new ActionListener() { // from class: h1
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.b((QuestionDialogView) obj);
            }
        });
        return false;
    }

    public final boolean v() {
        if (this.G || CookingManager.getInstance().getMeasuredTemperature() < Limits.WARNING_TEMPERATURE_AT_HIGH_SPEED || CookingManager.getInstance().getSpeedLevel() < Limits.WARNING_SPEED_LEVEL_AT_HIGH_TEMPERATURE) {
            return true;
        }
        this.z.performPlayClick();
        this.speedWarningQuestion.setVisibility(0);
        this.speedWarningQuestion.setButtonOneClickListener(new ActionListener() { // from class: c1
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.c((QuestionDialogView) obj);
            }
        });
        this.speedWarningQuestion.setButtonTwoClickListener(new ActionListener() { // from class: e1
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.d((QuestionDialogView) obj);
            }
        });
        return false;
    }

    public /* synthetic */ void w() {
        this.B.setModeImageToSelected();
        this.I.setVisibility(0);
    }

    public final void x() {
        Log.v("CookingViewHolder", "resetCooking");
        this.z.setCurrentState(1);
        CookingManager.getInstance().stopCooking();
        B();
        A();
        y();
        z();
        if (TextUtils.isEmpty(CookingManager.getInstance().getDescription())) {
            this.cookingLabelTextView.setText("");
            this.cookingLabelTextView.setVisibility(8);
        } else {
            this.cookingLabelTextView.setText(CookingManager.getInstance().getDescription());
            this.cookingLabelTextView.setVisibility(0);
        }
    }

    public final void y() {
        Log.v("CookingViewHolder", "resetSpeedKnob");
        KnobSpeed knobSpeed = this.B;
        if (knobSpeed == null) {
            return;
        }
        knobSpeed.setText(String.valueOf(CookingManager.getInstance().getSpeedLevel()));
        KnobSpeed knobSpeed2 = this.B;
        knobSpeed2.setProgress(knobSpeed2.getProgressFromValue(CookingManager.getInstance().getSpeedLevel()));
        this.B.setEnabled(CookingManager.getInstance().isSpeedKnobModifiable());
        this.B.setActive(false);
        this.B.setDrawActiveIndicator(CookingManager.getInstance().isSpeedKnobModifiable());
        this.B.setMaxValueLimit(CookingManager.getInstance().getSpeedLevelLimit());
        this.B.unsetPreheatMode();
        this.B.hideCustomInfo();
        this.B.setToIdleState();
        if (TextUtils.isEmpty(CookingManager.getInstance().getSpeedDescription())) {
            this.B.setValueSetTextSizeNormal();
            this.B.valueSetText("");
            this.B.valueSetVisibility(false);
        } else {
            this.B.setValueSetTextSizeSmall();
            this.B.valueSetText(CookingManager.getInstance().getSpeedDescription());
            this.B.setModeImageToSelected();
            this.B.valueSetVisibility(true);
        }
        C();
    }

    public final void z() {
        Log.v("CookingViewHolder", "resetTemperatureKnob");
        KnobTemperature knobTemperature = this.C;
        if (knobTemperature == null) {
            return;
        }
        knobTemperature.setTemperatureLevel(CookingManager.getInstance().getTemperatureLevel());
        this.C.setEnabled(CookingManager.getInstance().isTemperatureKnobModifiable());
        this.C.setActive(false);
        this.C.setDrawActiveIndicator(CookingManager.getInstance().isTemperatureKnobModifiable());
        this.C.setMeasuredTemperature(CookingManager.getInstance().getMeasuredTemperature());
        if (TextUtils.isEmpty(CookingManager.getInstance().getTemperatureDescription())) {
            return;
        }
        this.C.setTextForMeasuredTemperature(CookingManager.getInstance().getTemperatureDescription());
    }

    public final void a(Knob knob) {
        KnobTime knobTime = this.D;
        knobTime.setActive(knob == knobTime);
        KnobSpeed knobSpeed = this.B;
        knobSpeed.setActive(knob == knobSpeed);
        KnobTemperature knobTemperature = this.C;
        knobTemperature.setActive(knob == knobTemperature);
        if (knob != null) {
            this.E = knob.getKnobTag();
        }
    }

    public /* synthetic */ void b(QuestionDialogView questionDialogView) {
        this.speedWarningQuestion.setVisibility(8);
        this.G = true;
        this.z.performPlayClick();
    }

    public class a extends MachineCallbackAdapter {
        public a() {
        }

        public /* synthetic */ void a(long j) {
            int i = (int) j;
            CookingManager.getInstance().setMeasuredTemperature(i);
            CookingViewHolder.this.o();
            CookingViewHolder.this.C.setMeasuredTemperature(i);
        }

        public /* synthetic */ void b() {
            int i = (int) CookingViewHolder.this.y.getCurrentHeatingElementState().temperature;
            CookingManager.getInstance().setMeasuredTemperature(i);
            CookingViewHolder.this.o();
            CookingViewHolder.this.C.setMeasuredTemperature(i);
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialPushed(int i) {
            int i2 = 0;
            if (LayoutHelper.getInstance().isViewSelected(4)) {
                CookingViewHolder.D();
                Log.i("CookingViewHolder", "onJogDialPushed >> pushstate " + i);
                if (1 == i) {
                    CookingViewHolder cookingViewHolder = CookingViewHolder.this;
                    if (cookingViewHolder == null) {
                        throw null;
                    }
                    ArrayList arrayList = new ArrayList();
                    if (cookingViewHolder.D.getEnabled()) {
                        arrayList.add(cookingViewHolder.D);
                    }
                    if (cookingViewHolder.B.getEnabled()) {
                        arrayList.add(cookingViewHolder.B);
                    }
                    if (cookingViewHolder.C.getEnabled()) {
                        arrayList.add(cookingViewHolder.C);
                    }
                    if (arrayList.size() <= 1) {
                        if (arrayList.size() > 0) {
                            Knob knob = (Knob) arrayList.get(0);
                            if (LayoutHelper.getInstance().isViewSelected(4)) {
                                knob.setActive(true ^ knob.isActive());
                            } else {
                                knob.setActive(true);
                            }
                            if (knob.isActive()) {
                                cookingViewHolder.E = knob.getKnobTag();
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    int iIndexOf = arrayList.indexOf(cookingViewHolder.q());
                    if (iIndexOf == -1) {
                        cookingViewHolder.a((Knob) arrayList.get(0));
                        return;
                    }
                    int i3 = iIndexOf + 1;
                    if (i3 < arrayList.size()) {
                        i2 = i3;
                    } else if (LayoutHelper.getInstance().isViewSelected(4)) {
                        i2 = -1;
                    }
                    cookingViewHolder.a(i2 != -1 ? (Knob) arrayList.get(i2) : null);
                }
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialTurned(int i, long j) {
            final int iAddTemperatureSteps = 0;
            if (LayoutHelper.getInstance().isViewSelected(4)) {
                CookingViewHolder.D();
                Log.i("CookingViewHolder", "onJogDialTurned >> direction " + i + " >> speed " + j);
                final Knob knobQ = CookingViewHolder.this.q();
                if (knobQ == null || !knobQ.getKnobTag().equals(CookingViewHolder.this.E)) {
                    return;
                }
                int progress = knobQ.getProgress();
                CookingViewHolder cookingViewHolder = CookingViewHolder.this;
                if (cookingViewHolder.D == knobQ) {
                    if (CookingManager.getInstance().getRunningTimeMode() == 10 && (CookingManager.getInstance().getState() == 1 || CookingManager.getInstance().getState() == 2)) {
                        return;
                    } else {
                        iAddTemperatureSteps = i == 0 ? progress - ((int) j) : progress + ((int) j);
                    }
                } else if (cookingViewHolder.B == knobQ) {
                    iAddTemperatureSteps = i == 0 ? (int) (progress - (j * 100)) : (int) ((j * 100) + progress);
                } else {
                    KnobTemperature knobTemperature = cookingViewHolder.C;
                    if (knobTemperature == knobQ) {
                        iAddTemperatureSteps = i == 0 ? knobTemperature.addTemperatureSteps(((int) j) * (-1)) : knobTemperature.addTemperatureSteps((int) j);
                    }
                }
                CookingViewHolder.this.itemView.post(new Runnable() { // from class: z0
                    @Override // java.lang.Runnable
                    public final void run() {
                        knobQ.updateProgress(iAddTemperatureSteps, true);
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onLidClosed() {
            if (LayoutHelper.getInstance().isViewSelected(4)) {
                CookingViewHolder.D();
                Log.v("CookingViewHolder", "onLidClosed");
                App.getInstance().playSound(R.raw.lid_closed, SoundLength.MIDDLE);
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onLidOpened() {
            if (LayoutHelper.getInstance().isViewSelected(4)) {
                CookingViewHolder.D();
                Log.v("CookingViewHolder", "onLidOpened");
                CookingViewHolder.this.itemView.post(new Runnable() { // from class: x0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.a();
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onMachineInterfaceConnected() {
            CookingViewHolder.D();
            Log.v("CookingViewHolder", "onMachineInterfaceConnected: ");
            CookingViewHolder.this.itemView.post(new Runnable() { // from class: y0
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.b();
                }
            });
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onScaleStateChanged(int i, long j, int i2) {
            CookingViewHolder.this.H = j;
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public synchronized void onTemperatureChanged(final long j) {
            if (LayoutHelper.getInstance().isViewSelected(4)) {
                CookingViewHolder.D();
                Log.v("CookingViewHolder", "onTemperatureChanged >> temperature " + j);
                CookingViewHolder.this.itemView.post(new Runnable() { // from class: a1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.a(j);
                    }
                });
            }
        }

        public /* synthetic */ void a() {
            App.getInstance().playSound(R.raw.lid_opened, SoundLength.MIDDLE);
            UsageLogger.getInstance().logSealOpened();
            if (CookingManager.getInstance().getState() == 1) {
                CookingManager.getInstance().pauseCooking();
                CookingViewHolder.this.p();
                CookingViewHolder.this.z.setCurrentState(3);
                UsageLogger.getInstance().logMotorBraked();
                DialogHelper.getInstance().showLidOpenDialog(true);
            }
        }
    }

    public /* synthetic */ void a(QuestionDialogView questionDialogView) {
        this.speedWarningQuestion.setVisibility(8);
    }

    public /* synthetic */ void d(QuestionDialogView questionDialogView) {
        this.speedWarningQuestion.setVisibility(8);
        this.G = true;
        this.z.performPlayClick();
    }
}
