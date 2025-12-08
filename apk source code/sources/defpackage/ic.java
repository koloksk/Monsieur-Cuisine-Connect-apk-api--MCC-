package defpackage;

/* loaded from: classes.dex */
public final class ic {
    public final byte[] a;
    public int b = 0;

    public ic(int i) {
        this.a = new byte[i];
    }

    public void a(boolean z, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = this.b;
            this.b = i3 + 1;
            this.a[i3] = z ? (byte) 1 : (byte) 0;
        }
    }
}
