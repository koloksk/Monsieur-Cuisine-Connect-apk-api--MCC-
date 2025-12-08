package okhttp3.internal.http2;

/* loaded from: classes.dex */
public final class Settings {
    public int a;
    public final int[] b = new int[10];

    public Settings a(int i, int i2) {
        if (i >= 0) {
            int[] iArr = this.b;
            if (i < iArr.length) {
                this.a = (1 << i) | this.a;
                iArr[i] = i2;
            }
        }
        return this;
    }

    public void a(Settings settings) {
        for (int i = 0; i < 10; i++) {
            if (((1 << i) & settings.a) != 0) {
                a(i, settings.b[i]);
            }
        }
    }

    public int a() {
        if ((this.a & 128) != 0) {
            return this.b[7];
        }
        return 65535;
    }
}
