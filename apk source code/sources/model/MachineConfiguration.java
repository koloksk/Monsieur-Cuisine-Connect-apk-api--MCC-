package model;

import defpackage.g9;
import java.util.Objects;

/* loaded from: classes.dex */
public class MachineConfiguration {
    public int a;
    public int b;
    public int c;
    public int d;

    public MachineConfiguration(int i, int i2, int i3, int i4) {
        this.a = 0;
        this.b = 0;
        this.c = 1;
        this.d = 0;
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MachineConfiguration.class != obj.getClass()) {
            return false;
        }
        MachineConfiguration machineConfiguration = (MachineConfiguration) obj;
        return this.a == machineConfiguration.a && this.b == machineConfiguration.b && this.c == machineConfiguration.c && this.d == machineConfiguration.d;
    }

    public int getDirection() {
        return this.c;
    }

    public int getSpeedLevel() {
        return this.b;
    }

    public int getTemperatureLevel() {
        return this.d;
    }

    public int getWorkMode() {
        return this.a;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.c), Integer.valueOf(this.d));
    }

    public void setDirection(int i) {
        this.c = i;
    }

    public void setSpeedLevel(int i) {
        this.b = i;
    }

    public void setTemperatureLevel(int i) {
        this.d = i;
    }

    public void setWorkMode(int i) {
        this.a = i;
    }

    public String toString() {
        StringBuilder sbA = g9.a("MachineConfiguration{, workMode=");
        sbA.append(this.a);
        sbA.append(", speedLevel=");
        sbA.append(this.b);
        sbA.append(", direction=");
        sbA.append(this.c);
        sbA.append(", temperatureLevel=");
        sbA.append(this.d);
        sbA.append('}');
        return sbA.toString();
    }

    public MachineConfiguration(MachineConfiguration machineConfiguration) {
        this.a = 0;
        this.b = 0;
        this.c = 1;
        this.d = 0;
        if (machineConfiguration != null) {
            this.a = machineConfiguration.a;
            this.b = machineConfiguration.b;
            this.c = machineConfiguration.c;
            this.d = machineConfiguration.d;
        }
    }
}
