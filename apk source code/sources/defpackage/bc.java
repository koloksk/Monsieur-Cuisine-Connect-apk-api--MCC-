package defpackage;

/* loaded from: classes.dex */
public final class bc {
    public final int a;
    public final int b;
    public final int c;
    public final int d;
    public int e = -1;

    public bc(int i, int i2, int i3, int i4) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    public boolean a() {
        int i = this.e;
        return i != -1 && this.c == (i % 3) * 3;
    }

    public void b() {
        this.e = (this.c / 3) + ((this.d / 30) * 3);
    }

    public String toString() {
        return this.e + "|" + this.d;
    }
}
