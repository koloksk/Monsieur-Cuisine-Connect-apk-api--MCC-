package db.model;

import defpackage.g9;

/* loaded from: classes.dex */
public class MachineValues {
    public Long a;
    public boolean b;
    public int c;
    public int d;
    public long e;
    public int f;

    public MachineValues(Long l, boolean z, int i, int i2, long j, int i3) {
        this.a = l;
        this.b = z;
        this.c = i;
        this.d = i2;
        this.e = j;
        this.f = i3;
    }

    public Long getId() {
        return this.a;
    }

    public boolean getReverse() {
        return this.b;
    }

    public int getSpeed() {
        return this.c;
    }

    public int getTemp() {
        return this.d;
    }

    public long getTime() {
        return this.e;
    }

    public int getWeight() {
        return this.f;
    }

    public void setId(Long l) {
        this.a = l;
    }

    public void setReverse(boolean z) {
        this.b = z;
    }

    public void setSpeed(int i) {
        this.c = i;
    }

    public void setTemp(int i) {
        this.d = i;
    }

    public void setTime(long j) {
        this.e = j;
    }

    public void setWeight(int i) {
        this.f = i;
    }

    public String toString() {
        StringBuilder sbA = g9.a("MachineValues{id='");
        sbA.append(this.a);
        sbA.append('\'');
        sbA.append(", time=");
        sbA.append(this.e);
        sbA.append(", speed=");
        sbA.append(this.c);
        sbA.append(", temp=");
        sbA.append(this.d);
        sbA.append(", weight=");
        sbA.append(this.f);
        sbA.append(", reverse=");
        sbA.append(this.b);
        sbA.append('}');
        return sbA.toString();
    }

    public MachineValues() {
    }
}
