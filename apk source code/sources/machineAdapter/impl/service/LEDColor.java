package machineAdapter.impl.service;

/* loaded from: classes.dex */
public class LEDColor {
    public int a;
    public int b;
    public int c;
    public static final LEDColor WHITE = new LEDColor(0, 0, 50);
    public static final LEDColor GREEN = new LEDColor(0, 60, 0);
    public static final LEDColor RED = new LEDColor(75, 0, 0);
    public static final LEDColor OFF = new LEDColor(0, 0, 0);

    public LEDColor(int i, int i2, int i3) {
        this.a = i;
        this.c = i3;
        this.b = i2;
    }

    public int getBlueValue() {
        return this.c;
    }

    public int getGreenValue() {
        return this.b;
    }

    public int getRedValue() {
        return this.a;
    }
}
