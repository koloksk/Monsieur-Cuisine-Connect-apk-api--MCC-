package model;

import android.util.Log;
import cooking.Limits;
import defpackage.g9;

/* loaded from: classes.dex */
public class MachineValues {
    public int a = 1;
    public int b = 0;
    public String c = "";
    public int d = 0;
    public long e = 0;

    public static class Builder {
        public MachineValues a = new MachineValues();

        public MachineValues build() {
            MachineValues machineValues = this.a;
            this.a = null;
            return machineValues;
        }

        public Builder direction(int i) {
            this.a.a = i;
            return this;
        }

        public Builder speed(int i) {
            this.a.b = i;
            return this;
        }

        public Builder tag(String str) {
            this.a.c = str;
            return this;
        }

        public Builder temperature(int i) {
            this.a.d = i;
            return this;
        }

        public Builder timeInMillis(long j) {
            this.a.e = j;
            return this;
        }
    }

    public int getDirection() {
        return this.a;
    }

    public int getSpeed() {
        return this.b;
    }

    public String getTag() {
        return this.c;
    }

    public int getTemperature() {
        return this.d;
    }

    public long getTimeInMillis() {
        return this.e;
    }

    public boolean isDefault() {
        return ((this.e > Limits.TIME_MAX_MILLIS ? 1 : (this.e == Limits.TIME_MAX_MILLIS ? 0 : -1)) == 0 && this.b == 1 && this.d == 0) || noValuesSet();
    }

    public boolean noValuesSet() {
        return this.e == 0 && this.b == 0 && this.d == 0;
    }

    public void resetValues() {
        StringBuilder sbA = g9.a("resetValues: tag >> ");
        sbA.append(this.c);
        Log.i("MachineValues", sbA.toString());
        this.e = 0L;
        this.b = 0;
        this.d = 0;
        this.a = 1;
        this.c = "";
    }

    public void setDirection(int i) {
        this.a = i;
        Log.v("MachineValues", "MachineValues setDirection: " + i);
    }

    public void setSpeed(int i) {
        Log.v("MachineValues", "MachineValues setSpeed: " + i);
        this.b = i;
    }

    public void setTag(String str) {
        this.c = str;
    }

    public void setTemperature(int i) {
        Log.v("MachineValues", "MachineValues setTemperature: " + i);
        this.d = i;
    }

    public void setTimeInMillis(long j) {
        Log.v("MachineValues", "MachineValues setTimeInMillis: " + j);
        this.e = j;
    }

    public String toString() {
        StringBuilder sbA = g9.a("MachineValues{timeInMillis=");
        sbA.append(this.e);
        sbA.append(", speed=");
        sbA.append(this.b);
        sbA.append(", temperature=");
        sbA.append(this.d);
        sbA.append(", direction=");
        sbA.append(this.a);
        sbA.append(", tag='");
        sbA.append(this.c);
        sbA.append("'");
        sbA.append('}');
        return sbA.toString();
    }
}
