package cooking;

import android.util.Log;
import application.App;
import defpackage.g9;
import helper.UsageLogger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import machineAdapter.ICommandInterface;
import machineAdapter.impl.service.HardwareLEDService;
import machineAdapter.impl.service.LEDColor;
import mcapi.McUsageApi;
import model.MachineConfiguration;
import model.Presets;
import timer.CookingCountDownTimer;

/* loaded from: classes.dex */
public final class CookingManager {
    public static final String D = "CookingManager";
    public static CookingManager E = new CookingManager();
    public long B;
    public long C;
    public int b;
    public int c;
    public long d;
    public volatile CookingCountDownTimer g;
    public volatile CookingCountDownTimer h;
    public long n;
    public int p;
    public long q;
    public int r;
    public int s;
    public int t;
    public long u;
    public long v;
    public int w;
    public int x;
    public int y;
    public int a = 1;
    public int e = Presets.defaultCookingStep().getMachineConfiguration().getWorkMode();
    public ICommandInterface f = App.getInstance().getMachineAdapter().getCommandInterface();
    public CookingListener i = null;
    public int j = -1;
    public int k = -1;
    public int l = 0;
    public SingleCookingStep m = Presets.defaultCookingStep();
    public CookingStep o = Presets.defaultCookingStep();
    public int z = 0;
    public int A = Limits.TEMPERATURE_LEVEL_MAX;

    public interface CookingListener {
        void onFinishCooking();

        void onNextSingleCookingStep(SingleCookingStep singleCookingStep);

        void onPauseCooking();

        void onResumeCooking();

        void onStartCooking(int i);

        void onStopCooking();

        void onTickCooking(long j);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        public static final int FINISHED = 3;
        public static final int PAUSE = 2;
        public static final int RUN = 1;
        public static final int STOP = 0;
    }

    public class a extends CookingCountDownTimer {
        public a(long j) {
            super(j);
        }

        @Override // timer.CookingCountDownTimer, timer.BaseCountDownTimer
        public void onFinish() {
            CookingManager.this.stopCooking();
        }
    }

    public class b extends CookingCountDownTimer {
        public b(long j) {
            super(j);
        }

        @Override // timer.CookingCountDownTimer, timer.BaseCountDownTimer
        public void onFinish() {
            CookingManager.a(CookingManager.this);
        }

        @Override // timer.CookingCountDownTimer, timer.BaseCountDownTimer
        public void onTick(long j) {
            boolean z;
            SingleCookingStep singleCookingStep;
            CookingManager cookingManager = CookingManager.this;
            cookingManager.n = cookingManager.C - j;
            cookingManager.q = j;
            StringBuilder sbA = g9.a("onTickTimer .. to run ");
            sbA.append(cookingManager.C);
            sbA.append(" .. remaining ");
            sbA.append(cookingManager.q);
            sbA.append("  .. elapsed ");
            sbA.append(cookingManager.n);
            sbA.append(" .. Index ");
            sbA.append(cookingManager.l);
            Log.i("CookingManager", sbA.toString());
            CookingStep cookingStep = cookingManager.o;
            if (cookingStep instanceof CookingUnit) {
                CookingUnit cookingUnit = (CookingUnit) cookingStep;
                if (cookingUnit.getSingleCookingSteps() != null && cookingUnit.getSingleCookingSteps().size() > 0) {
                    long sumOfCookingStepDurationInMillis = cookingManager.n % cookingUnit.getSumOfCookingStepDurationInMillis();
                    long cookingDurationInMillis = 0;
                    int i = 0;
                    while (true) {
                        if (i >= cookingUnit.getSingleCookingSteps().size()) {
                            z = false;
                            break;
                        }
                        SingleCookingStep singleCookingStep2 = cookingUnit.getSingleCookingSteps().get(i);
                        if (sumOfCookingStepDurationInMillis <= singleCookingStep2.getCookingDurationInMillis() + cookingDurationInMillis) {
                            z = true;
                            break;
                        } else {
                            cookingDurationInMillis += singleCookingStep2.getCookingDurationInMillis();
                            i++;
                        }
                    }
                    int size = i % cookingUnit.getSingleCookingSteps().size();
                    if (z && (singleCookingStep = cookingUnit.getSingleCookingSteps().get(size)) != null && !cookingManager.m.equals(singleCookingStep)) {
                        cookingManager.l = size;
                        StringBuilder sbA2 = g9.a("onTickTimer NEXT .. elapsed ");
                        sbA2.append(cookingManager.n);
                        sbA2.append(" ..  rest of unit ");
                        sbA2.append(sumOfCookingStepDurationInMillis);
                        sbA2.append(" .. step time ");
                        sbA2.append(singleCookingStep.getCookingDurationInMillis());
                        sbA2.append(" .. Index ");
                        sbA2.append(size);
                        Log.i("CookingManager", sbA2.toString());
                        cookingManager.m = singleCookingStep;
                        singleCookingStep.setMachineConfiguration(new MachineConfiguration(singleCookingStep.getMachineConfiguration() != null ? cookingManager.m.getMachineConfiguration() : Presets.defaultMachineConfiguration()));
                        cookingManager.a = cookingManager.m.getMachineConfiguration().getDirection();
                        int temperatureLevel = cookingManager.k;
                        if (temperatureLevel == -1) {
                            temperatureLevel = cookingManager.m.getMachineConfiguration().getTemperatureLevel();
                        }
                        cookingManager.c = temperatureLevel;
                        int speedLevel = cookingManager.j;
                        if (speedLevel == -1) {
                            speedLevel = cookingManager.m.getMachineConfiguration().getSpeedLevel();
                        }
                        cookingManager.b = speedLevel;
                        cookingManager.e = cookingManager.m.getMachineConfiguration().getWorkMode();
                        if (cookingManager.i != null && cookingManager.n > cookingUnit.getSingleCookingSteps().get(0).getCookingDurationInMillis()) {
                            cookingManager.i.onNextSingleCookingStep(new SingleCookingStep(cookingManager.m));
                        }
                        cookingManager.i();
                        cookingManager.b();
                    }
                }
            }
            CookingListener cookingListener = CookingManager.this.i;
            if (cookingListener != null) {
                cookingListener.onTickCooking(j);
            }
        }
    }

    public CookingManager() {
        f();
    }

    public static CookingManager getInstance() {
        return E;
    }

    public final void a() {
        int i;
        if (this.b != 0 || (i = this.e) == 3 || i == 4) {
            return;
        }
        if (i == 5 && hasCustomInfo()) {
            return;
        }
        this.b = 1;
    }

    public final void b() {
        int i = this.z;
        if (i != 1) {
            if (i != 2) {
                return;
            }
            if (this.d == this.u && this.B == this.v) {
                return;
            }
            StringBuilder sbA = g9.a("applyValues State.Pause adjustedTime ");
            sbA.append(this.d);
            Log.d("CookingManager", sbA.toString());
            if (this.w == 1 && this.d == 0) {
                stopCooking();
                return;
            } else {
                c();
                d();
                return;
            }
        }
        if ((this.a != this.r && this.e != 5) || this.b != this.s || this.c != this.t || this.e != this.x) {
            g();
        }
        if (this.d == this.u && this.B == this.v) {
            return;
        }
        if (this.w == 1 && this.d == 0) {
            stopCooking();
            return;
        }
        c();
        d();
        this.g.start();
    }

    public final void c() {
        Log.i("CookingManager", "cancelCookingCountdown");
        if (this.g != null) {
            this.g.cancel();
            this.g = null;
        }
    }

    public void clearAutoStopTimer() {
        if (this.h != null) {
            this.h.cancel();
            this.h = null;
        }
    }

    public final void d() {
        long j = this.n;
        int i = this.z;
        if (i == 0 || i == 3 || this.w == 1) {
            this.n = 0L;
        }
        long j2 = this.d;
        this.u = j2;
        long j3 = this.B;
        this.v = j3;
        if (j2 == 0) {
            j2 = j3;
        }
        this.C = j2;
        long j4 = this.d;
        if (j4 == 0) {
            j4 = this.B - this.n;
        }
        this.q = j4;
        Log.i("CookingManager", "createCookingCountdown >> elapsed " + j + " -> " + this.n + " >> adjusted " + this.d + " >> limit " + this.B + " >> total run " + this.C + " >> remaining " + this.q);
        this.g = new b(this.q);
    }

    public final SingleCookingStep e() {
        CookingStep cookingStep = this.o;
        if (!(cookingStep instanceof CookingSteps)) {
            return null;
        }
        try {
            return ((CookingSteps) cookingStep).getSingleCookingSteps().get(this.l + 1);
        } catch (ArrayIndexOutOfBoundsException unused) {
            return null;
        }
    }

    public final void f() {
        this.d = 0L;
        this.e = 0;
        this.a = 0;
        this.c = 0;
        this.b = 0;
        this.n = 0L;
        CookingStep cookingStep = this.o;
        if (cookingStep instanceof CookingSteps) {
            CookingSteps cookingSteps = (CookingSteps) cookingStep;
            if (cookingSteps.getSingleCookingSteps().size() > 0) {
                SingleCookingStep singleCookingStep = cookingSteps.getSingleCookingSteps().get(0);
                SingleCookingStep singleCookingStep2 = singleCookingStep != null ? new SingleCookingStep(singleCookingStep) : new SingleCookingStep(Presets.defaultCookingStep());
                this.m = singleCookingStep2;
                singleCookingStep2.setMachineConfiguration(new MachineConfiguration(singleCookingStep2.getMachineConfiguration() != null ? this.m.getMachineConfiguration() : Presets.defaultMachineConfiguration()));
                this.l = 0;
                this.d = this.m.getCookingDurationInMillis();
                this.e = this.m.getMachineConfiguration().getWorkMode();
            }
        } else if (cookingStep instanceof CookingUnit) {
            CookingUnit cookingUnit = (CookingUnit) cookingStep;
            if (cookingUnit.getSingleCookingSteps() != null && cookingUnit.getSingleCookingSteps().size() > 0) {
                SingleCookingStep singleCookingStep3 = cookingUnit.getSingleCookingSteps().get(0);
                SingleCookingStep singleCookingStep4 = singleCookingStep3 != null ? new SingleCookingStep(singleCookingStep3) : new SingleCookingStep(Presets.defaultCookingStep());
                this.m = singleCookingStep4;
                singleCookingStep4.setMachineConfiguration(new MachineConfiguration(singleCookingStep4.getMachineConfiguration() != null ? this.m.getMachineConfiguration() : Presets.defaultMachineConfiguration()));
                this.m.setCookingDurationInMillis(cookingUnit.getCookingUnitDurationInMillis());
                this.l = 0;
                this.j = -1;
                this.k = -1;
                this.d = cookingUnit.getCookingUnitDurationInMillis();
                this.e = this.m.getMachineConfiguration().getWorkMode();
            }
        } else {
            SingleCookingStep singleCookingStep5 = (cookingStep == null || !(cookingStep instanceof SingleCookingStep)) ? new SingleCookingStep(Presets.defaultCookingStep()) : new SingleCookingStep((SingleCookingStep) this.o);
            this.m = singleCookingStep5;
            singleCookingStep5.setMachineConfiguration(new MachineConfiguration(singleCookingStep5.getMachineConfiguration() != null ? this.m.getMachineConfiguration() : Presets.defaultMachineConfiguration()));
            this.l = 0;
            this.d = this.m.getCookingDurationInMillis();
            this.e = this.m.getMachineConfiguration().getWorkMode();
        }
        this.a = this.m.getMachineConfiguration().getDirection();
        this.c = this.m.getMachineConfiguration().getTemperatureLevel();
        this.b = this.m.getMachineConfiguration().getSpeedLevel();
        i();
    }

    public final void g() {
        this.x = this.e;
        this.s = this.b;
        this.r = this.a;
        this.t = this.c;
        StringBuilder sbA = g9.a("startBusCommand: workMode >> ");
        sbA.append(this.x);
        sbA.append(", speedLevel >> ");
        sbA.append(this.s);
        sbA.append(", direction >> ");
        sbA.append(this.r);
        sbA.append(", temperatureLevel >> ");
        sbA.append(this.t);
        Log.w("CookingManager", sbA.toString());
        this.f.start(this.x, this.s, this.r, this.t);
        UsageLogger.getInstance().logStart(this.x, this.s, this.t);
    }

    public int getCurrentCookingStepIndex() {
        return this.l;
    }

    public String getCustomInfo() {
        return this.m.getCustomInfo();
    }

    public String getDescription() {
        return this.m.getDescription();
    }

    public int getDirection() {
        return this.a;
    }

    public long getDisplaySetTime() {
        CookingStep cookingStep = this.o;
        if (cookingStep instanceof RampSteps) {
            return ((CookingSteps) cookingStep).getSumOfCookingStepDurationInMillis();
        }
        int i = this.z;
        return (i == 0 || i == 3) ? getTime() : getTimeToRun();
    }

    public long getDisplayTime() {
        StringBuilder sbA = g9.a("getDisplayTime, STATE = ");
        sbA.append(this.z);
        Log.d("CookingManager", sbA.toString());
        CookingStep cookingStep = this.o;
        if (cookingStep instanceof RampSteps) {
            List<SingleCookingStep> singleCookingSteps = ((CookingSteps) cookingStep).getSingleCookingSteps();
            long cookingDurationInMillis = 0;
            if (singleCookingSteps.size() > 0) {
                for (int i = this.l; i < singleCookingSteps.size(); i++) {
                    cookingDurationInMillis += singleCookingSteps.get(i).getCookingDurationInMillis();
                }
            }
            return this.z != 0 ? cookingDurationInMillis - this.n : cookingDurationInMillis;
        }
        if (this.z == 0) {
            long time = getTime();
            Log.d("CookingManager", "getDisplayTime State.STOP " + time);
            return time;
        }
        long remainingTime = this.w == 1 ? getRemainingTime() : getElapsedTime();
        Log.d("CookingManager", "getDisplayTime else " + remainingTime);
        return remainingTime;
    }

    public long getElapsedTime() {
        return this.n;
    }

    public CookingStep getInitCookingStep() {
        return this.o;
    }

    public int getMeasuredTemperature() {
        return this.p;
    }

    public long getRemainingTime() {
        return this.q;
    }

    public int getRunningTimeMode() {
        return this.w;
    }

    public String getSpeedDescription() {
        return this.m.getSpeedDescription();
    }

    public int getSpeedLevel() {
        return this.b;
    }

    public int getSpeedLevelLimit() {
        return this.y;
    }

    public int getState() {
        return this.z;
    }

    public String getTemperatureDescription() {
        return this.m.getTemperatureDescription();
    }

    public int getTemperatureLevel() {
        return this.c;
    }

    public int getTemperatureLevelLimit() {
        return this.A;
    }

    public long getTime() {
        SingleCookingStep singleCookingStepE;
        return (!isPreheatStep() || (singleCookingStepE = e()) == null) ? this.d : singleCookingStepE.getCookingDurationInMillis();
    }

    public String getTimeDescription() {
        return this.m.getTimeDescription();
    }

    public long getTimeLimit() {
        SingleCookingStep singleCookingStepE;
        CookingStep cookingStep = this.o;
        if (cookingStep instanceof CookingUnit) {
            long cookingUnitDurationLimitInMillis = ((CookingUnit) cookingStep).getCookingUnitDurationLimitInMillis();
            if (cookingUnitDurationLimitInMillis > 0) {
                return Math.min(this.B, cookingUnitDurationLimitInMillis);
            }
        }
        return (!isPreheatStep() || (singleCookingStepE = e()) == null) ? this.o instanceof CookingSteps ? this.m.getTimeLimitInMillis() : this.B : singleCookingStepE.getTimeLimitInMillis();
    }

    public long getTimeToRun() {
        return this.C;
    }

    public final void h() {
        this.f.stop();
        UsageLogger.getInstance().logStop();
    }

    public boolean hasCustomInfo() {
        return this.m.hasCustomInfo();
    }

    public final synchronized void i() {
        Log.i(D, "validate");
        if (this.c > 0) {
            Log.i(D, "validate setMaxSpeed");
            this.y = 3;
        } else if (this.a != 0) {
            this.y = 10;
        } else if (this.e == 5) {
            this.y = 4;
        } else {
            this.y = 3;
        }
        this.b = Math.min(this.b, this.y);
        if (this.m.getSpeedLimit() >= 0 && this.m.getSpeedLimit() < this.y) {
            this.y = this.m.getSpeedLimit();
        }
        this.B = Limits.getMaxTimeForSpeedLevel(this.b);
        CookingUnit cookingUnit = this.o instanceof CookingUnit ? (CookingUnit) this.o : null;
        if (cookingUnit != null && cookingUnit.getCookingUnitDurationLimitInMillis() > 0) {
            this.B = Math.min(this.B, cookingUnit.getCookingUnitDurationLimitInMillis());
            if (this.e == 5) {
                Log.i(Presets.Tags.KNEADING, "setting up time...");
                List<SingleCookingStep> singleCookingSteps = cookingUnit.getSingleCookingSteps();
                if (singleCookingSteps != null) {
                    if (this.d > 85000) {
                        singleCookingSteps.get(1).setCookingDurationInMillis(this.d - 85000);
                        Log.w(D, "set Ruhephase " + (this.d - 85000));
                    } else {
                        singleCookingSteps.get(1).setCookingDurationInMillis(1000L);
                    }
                }
            }
        } else if (this.m != null && this.m.getTimeLimitInMillis() > 0 && (this.o instanceof CookingSteps)) {
            this.B = Math.min(this.B, this.m.getTimeLimitInMillis());
        }
        this.d = Math.min(this.d, this.B);
        this.n = Math.min(this.n, this.B);
        this.q = Math.min(this.q, this.B);
    }

    public void illustrateStopCooking() {
        this.z = 0;
    }

    public boolean isDirectionModifiable() {
        return this.m.isDirectionModifiable();
    }

    public boolean isPreheatStep() {
        return this.m.isPreheatStep();
    }

    public boolean isScaleEnabled() {
        return this.m.isScaleEnabled();
    }

    public boolean isSpeedKnobModifiable() {
        return this.m.isSpeedKnobModifiable();
    }

    public boolean isTemperatureKnobModifiable() {
        return this.m.isTemperatureKnobModifiable();
    }

    public boolean isTimeKnobModifiable() {
        return this.m.isTimeKnobModifiable();
    }

    public boolean isTurboEnabled() {
        return this.m.isTurboEnabled();
    }

    public void pauseCooking() {
        Log.v("CookingManager", "pauseCooking");
        if (this.g != null) {
            StringBuilder sbA = g9.a("pauseCooking pauseTimer ");
            sbA.append(this.g);
            Log.v("CookingManager", sbA.toString());
            this.g.pause();
        }
        h();
        HardwareLEDService.getInstance().switchLEDOn(LEDColor.WHITE);
        if (this.z != 2) {
            this.z = 2;
            CookingListener cookingListener = this.i;
            if (cookingListener != null) {
                cookingListener.onPauseCooking();
                this.h = new a(30000L);
                this.h.start();
            }
        }
    }

    public void resumeCooking() {
        Log.d("CookingManager", "resumeCooking");
        i();
        a();
        g();
        HardwareLEDService.getInstance().pulseLED(LEDColor.WHITE);
        if (this.g != null) {
            this.g.startOrResume();
            Log.v("CookingManager", "resumeCooking resumeTimer " + this.g);
        }
        clearAutoStopTimer();
        if (this.z != 1) {
            this.z = 1;
            CookingListener cookingListener = this.i;
            if (cookingListener != null) {
                cookingListener.onResumeCooking();
            }
        }
    }

    public void setCookingListener(CookingListener cookingListener) {
        this.i = cookingListener;
    }

    public void setInitCookingStep(CookingStep cookingStep) {
        stopCooking();
        this.o = cookingStep;
        f();
    }

    public synchronized void setMeasuredTemperature(int i) {
        Log.i("CookingManager", "setMeasuredTemperature >> " + i + "°C");
        this.p = i;
        i();
    }

    public void setSpeedLevel(int i) {
        this.b = i;
        i();
        if (this.o instanceof CookingUnit) {
            this.j = this.b;
        }
        b();
    }

    public void setTemperature(int i) {
        this.c = CookingUtils.getTemperatureLevel(i);
        i();
        if (this.o instanceof CookingUnit) {
            this.k = this.c;
        }
        b();
    }

    public void setTemperatureLevel(int i) {
        this.c = Math.min(this.A, i);
        i();
        b();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setTime(long r4) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "setTime "
            r0.append(r1)
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "CookingManager"
            android.util.Log.i(r1, r0)
            int r0 = r3.z
            if (r0 == 0) goto L36
            r1 = 1
            if (r0 == r1) goto L24
            r2 = 2
            if (r0 == r2) goto L24
            r1 = 3
            if (r0 == r1) goto L36
            goto L39
        L24:
            int r0 = r3.w
            if (r0 != r1) goto L39
            r0 = 0
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 > 0) goto L32
            r3.stopCooking()
            goto L39
        L32:
            r3.a(r4)
            goto L39
        L36:
            r3.a(r4)
        L39:
            r3.i()
            r3.b()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cooking.CookingManager.setTime(long):void");
    }

    public void skipCookingStep() {
        this.g.skip();
    }

    public void startCooking() {
        StringBuilder sbA = g9.a("startCooking for ");
        sbA.append(this.d);
        sbA.append("ms, limit ");
        sbA.append(this.B);
        sbA.append("ms.");
        Log.i("CookingManager", sbA.toString());
        i();
        c();
        a();
        this.w = this.d == 0 ? 10 : 1;
        HardwareLEDService.getInstance().pulseLED(LEDColor.WHITE);
        g();
        d();
        this.g.start();
        if (this.z != 1) {
            this.z = 1;
            CookingListener cookingListener = this.i;
            if (cookingListener != null) {
                cookingListener.onStartCooking(this.w);
            }
        }
        McUsageApi.getInstance().postMachineEvent(this.e, true);
    }

    public void stopCooking() {
        StringBuilder sbA = g9.a("stopCooking >> state ");
        sbA.append(this.z);
        Log.d("CookingManager", sbA.toString());
        c();
        HardwareLEDService.getInstance().switchLEDOn(LEDColor.WHITE);
        h();
        if (this.z != 0) {
            McUsageApi.getInstance().postMachineEvent(this.e, false);
        }
        f();
        clearAutoStopTimer();
        if (this.z != 0) {
            Log.v("CookingManager", "stopCooking (1) set state to STOP");
            this.z = 0;
            if (this.i != null) {
                Log.v("CookingManager", "stopCooking (2) notify");
                this.i.onStopCooking();
            }
        }
    }

    public void toggleDirection() {
        this.a = this.a == 0 ? 1 : 0;
        i();
        b();
    }

    public static /* synthetic */ void a(CookingManager cookingManager) {
        if (cookingManager != null) {
            Log.d("CookingManager", "onFinishTimer");
            cookingManager.h();
            CookingStep cookingStep = cookingManager.o;
            if (!(cookingStep instanceof SingleCookingStep) && !(cookingStep instanceof CookingUnit)) {
                if (cookingStep instanceof CookingSteps) {
                    CookingSteps cookingSteps = (CookingSteps) cookingStep;
                    if (cookingManager.l < cookingSteps.getSingleCookingSteps().size() - 1) {
                        cookingManager.l++;
                        SingleCookingStep singleCookingStep = new SingleCookingStep(cookingSteps.getSingleCookingSteps().get(cookingManager.l));
                        cookingManager.m = singleCookingStep;
                        singleCookingStep.setMachineConfiguration(new MachineConfiguration(singleCookingStep.getMachineConfiguration() != null ? cookingManager.m.getMachineConfiguration() : Presets.defaultMachineConfiguration()));
                        cookingManager.e = cookingManager.m.getMachineConfiguration().getWorkMode();
                        cookingManager.b = cookingManager.m.getMachineConfiguration().getSpeedLevel();
                        cookingManager.c = cookingManager.m.getMachineConfiguration().getTemperatureLevel();
                        cookingManager.a = cookingManager.m.getMachineConfiguration().getDirection();
                        cookingManager.d = cookingManager.m.getCookingDurationInMillis();
                        cookingManager.startCooking();
                        CookingListener cookingListener = cookingManager.i;
                        if (cookingListener != null) {
                            cookingListener.onNextSingleCookingStep(new SingleCookingStep(cookingManager.m));
                            return;
                        }
                        return;
                    }
                    cookingManager.z = 3;
                    cookingManager.l = 0;
                    SingleCookingStep singleCookingStep2 = new SingleCookingStep(cookingSteps.getSingleCookingSteps().get(cookingManager.l));
                    cookingManager.m = singleCookingStep2;
                    singleCookingStep2.setMachineConfiguration(new MachineConfiguration(singleCookingStep2.getMachineConfiguration() != null ? cookingManager.m.getMachineConfiguration() : Presets.defaultMachineConfiguration()));
                    cookingManager.e = cookingManager.m.getMachineConfiguration().getWorkMode();
                    CookingListener cookingListener2 = cookingManager.i;
                    if (cookingListener2 != null) {
                        cookingListener2.onFinishCooking();
                    }
                }
            } else {
                cookingManager.z = 3;
                cookingManager.l = 0;
                CookingListener cookingListener3 = cookingManager.i;
                if (cookingListener3 != null) {
                    cookingListener3.onFinishCooking();
                }
            }
            HardwareLEDService.getInstance().blinkLED(LEDColor.GREEN, 3000);
            return;
        }
        throw null;
    }

    public final void a(long j) {
        Log.i("CookingManager", "setTimeForStep " + j);
        if (isPreheatStep()) {
            SingleCookingStep singleCookingStepE = e();
            if (singleCookingStepE != null) {
                singleCookingStepE.setCookingDurationInMillis(j);
                return;
            }
            return;
        }
        this.d = j;
    }
}
