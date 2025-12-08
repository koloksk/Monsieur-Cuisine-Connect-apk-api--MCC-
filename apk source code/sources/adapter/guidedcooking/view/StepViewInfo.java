package adapter.guidedcooking.view;

import db.model.MachineValues;

/* loaded from: classes.dex */
public interface StepViewInfo {
    MachineValues getMachineValues();

    String getMode();

    int getStepIndex();

    String getText();
}
