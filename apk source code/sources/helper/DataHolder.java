package helper;

import android.util.Log;
import cooking.CookingStep;
import cooking.Limits;
import defpackage.g9;
import model.MachineValues;

/* loaded from: classes.dex */
public class DataHolder {
    public static DataHolder e;
    public MachineValues a = new MachineValues();
    public MachineValues b = new MachineValues();
    public MachineValues c = new MachineValues();
    public CookingStep d = null;

    public static DataHolder getInstance() {
        if (e == null) {
            e = new DataHolder();
        }
        return e;
    }

    public MachineValues getMachineValues() {
        if (this.a == null) {
            this.a = new MachineValues();
        }
        return this.a;
    }

    public MachineValues getPresetMachineValues() {
        return this.c;
    }

    public CookingStep getSavedCookingStep() {
        return this.d;
    }

    public MachineValues getSavedMachineValues() {
        if (this.b == null) {
            this.b = new MachineValues();
        }
        return this.b;
    }

    public void initDefaultMachineValues() {
        Log.d("DataHolder", "initDefaultMachineValues: ");
        getMachineValues().setTimeInMillis(Limits.TIME_MAX_MILLIS);
        getMachineValues().setSpeed(1);
        getMachineValues().setTemperature(0);
    }

    public void resetMachineValues() {
        getMachineValues().resetValues();
    }

    public void saveCookingStep(CookingStep cookingStep) {
        this.d = cookingStep;
    }

    public void saveMachineValues() {
        StringBuilder sbA = g9.a("saveMachineValues: ");
        sbA.append(this.a);
        Log.i("DataHolder", sbA.toString());
        MachineValues machineValues = new MachineValues();
        this.b = machineValues;
        machineValues.setTimeInMillis(this.a.getTimeInMillis());
        this.b.setSpeed(this.a.getSpeed());
        this.b.setTemperature(this.a.getTemperature());
    }

    public void setMachineValuesFromSavedValues() {
        StringBuilder sbA = g9.a("saved book mark: ");
        sbA.append(this.b);
        Log.v("DataHolder", sbA.toString());
        MachineValues machineValues = new MachineValues();
        this.a = machineValues;
        machineValues.setTimeInMillis(this.b.getTimeInMillis());
        this.a.setSpeed(this.b.getSpeed());
        this.a.setTemperature(this.b.getTemperature());
    }

    public void setPresetMachineValues(MachineValues machineValues) {
        this.c.setTag(machineValues.getTag());
        this.c.setDirection(machineValues.getDirection());
        this.c.setSpeed(machineValues.getSpeed());
        this.c.setTemperature(machineValues.getTemperature());
        this.c.setTimeInMillis(machineValues.getTimeInMillis());
    }

    public void setSavedMachineValues(MachineValues machineValues) {
        this.b = machineValues;
    }
}
