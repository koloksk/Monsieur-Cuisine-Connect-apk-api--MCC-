package db.model;

import defpackage.g9;

/* loaded from: classes.dex */
public class Measurement {
    public Long a;
    public boolean b;
    public int c;
    public int d;
    public int e;

    public Measurement(Long l, boolean z, int i, int i2, int i3) {
        this.a = l;
        this.b = z;
        this.c = i;
        this.d = i2;
        this.e = i3;
    }

    public Long getId() {
        return this.a;
    }

    public boolean getLid() {
        return this.b;
    }

    public int getSpeed() {
        return this.c;
    }

    public int getTemp() {
        return this.d;
    }

    public int getWeight() {
        return this.e;
    }

    public void setId(Long l) {
        this.a = l;
    }

    public void setLid(boolean z) {
        this.b = z;
    }

    public void setSpeed(int i) {
        this.c = i;
    }

    public void setTemp(int i) {
        this.d = i;
    }

    public void setWeight(int i) {
        this.e = i;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Measurement{id=");
        sbA.append(this.a);
        sbA.append(", speed=");
        sbA.append(this.c);
        sbA.append(", temp=");
        sbA.append(this.d);
        sbA.append(", weight=");
        sbA.append(this.e);
        sbA.append(", lid=");
        sbA.append(this.b);
        sbA.append('}');
        return sbA.toString();
    }

    public Measurement() {
    }
}
