package adapter.guidedcooking.view;

import android.support.annotation.NonNull;
import cooking.CookingUtils;
import cooking.RampSteps;
import cooking.SingleCookingStep;
import db.model.MachineValues;
import db.model.Step;
import db.model.StepMode;
import defpackage.g9;
import helper.Logger;
import java.util.ArrayList;
import java.util.List;
import model.MachineConfiguration;

/* loaded from: classes.dex */
public class RampStepViewInfo implements StepViewInfo {
    public final List<Step> a;

    public class a extends ArrayList<Step> {
        public final /* synthetic */ Step a;

        public a(RampStepViewInfo rampStepViewInfo, Step step) {
            this.a = step;
            add(this.a);
        }
    }

    public RampStepViewInfo(@NonNull Step step) {
        this.a = new a(this, step);
    }

    public void addStep(Step step) {
        this.a.add(step);
    }

    @Override // adapter.guidedcooking.view.StepViewInfo
    public MachineValues getMachineValues() {
        return this.a.get(0).getMachineValues();
    }

    @Override // adapter.guidedcooking.view.StepViewInfo
    public String getMode() {
        return StepMode.RAMP;
    }

    @Override // adapter.guidedcooking.view.StepViewInfo
    public int getStepIndex() {
        return this.a.get(0).getStep();
    }

    @Override // adapter.guidedcooking.view.StepViewInfo
    public String getText() {
        StringBuilder sbA = g9.a("RAMP ");
        sbA.append(getStepIndex());
        return sbA.toString();
    }

    public RampSteps toCookingSteps() {
        Logger.i("RampStepViewInfo", "toCookingSteps");
        RampSteps rampSteps = new RampSteps();
        for (Step step : this.a) {
            MachineValues machineValues = step.getMachineValues();
            rampSteps.addSingleCookingStep(new SingleCookingStep.Builder().cookingDurationInMillis(machineValues.getTime() * 1000).machineConfiguration(new MachineConfiguration(1, machineValues.getSpeed(), !machineValues.getReverse() ? 1 : 0, CookingUtils.getTemperatureLevel(machineValues.getTemp()))).description(step.getText()).turboEnabled(true).directionModifiable(false).scaleEnabled(false).timeKnobModifiable(false).timeDescription("").timeLimitInMillis(-1L).speedKnobModifiable(false).speedDescription("").temperatureKnobModifiable(false).temperatureDescription("").build());
        }
        return rampSteps;
    }
}
