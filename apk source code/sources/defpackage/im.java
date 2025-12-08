package defpackage;

/* loaded from: classes.dex */
public class im implements Cloneable {
    public long b;
    public long e;
    public int g;
    public long h;
    public int i;
    public int a = 0;
    public int c = 1;
    public int d = 0;
    public int f = 0;
    public int j = 0;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        StringBuilder sbA = g9.a("SensorValueHolder{motorState=");
        sbA.append(this.f);
        sbA.append(", motorDirection=");
        sbA.append(this.d);
        sbA.append(", motorSpeedLevel=");
        sbA.append(this.e);
        sbA.append(", scaleState=");
        sbA.append(this.i);
        sbA.append(", scaleCalibration=");
        sbA.append(this.g);
        sbA.append(", scaleMeasureValue=");
        sbA.append(this.h);
        sbA.append(", heatingElementState=");
        sbA.append(this.a);
        sbA.append(", heatingElementTemperature=");
        sbA.append(this.b);
        sbA.append(", lidState=");
        sbA.append(this.c);
        sbA.append(", errorState=");
        sbA.append(this.j);
        sbA.append('}');
        return sbA.toString();
    }
}
