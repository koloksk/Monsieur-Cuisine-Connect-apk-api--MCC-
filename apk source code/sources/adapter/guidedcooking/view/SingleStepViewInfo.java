package adapter.guidedcooking.view;

import android.support.annotation.NonNull;
import db.model.MachineValues;
import db.model.Step;

/* loaded from: classes.dex */
public class SingleStepViewInfo implements StepViewInfo {

    @NonNull
    public final Step a;

    public SingleStepViewInfo(@NonNull Step step) {
        this.a = step;
    }

    @Override // adapter.guidedcooking.view.StepViewInfo
    public MachineValues getMachineValues() {
        return this.a.getMachineValues();
    }

    @Override // adapter.guidedcooking.view.StepViewInfo
    public String getMode() {
        return this.a.getMode();
    }

    @Override // adapter.guidedcooking.view.StepViewInfo
    public int getStepIndex() {
        return this.a.getStep();
    }

    @Override // adapter.guidedcooking.view.StepViewInfo
    public String getText() {
        return this.a.getText();
    }
}
