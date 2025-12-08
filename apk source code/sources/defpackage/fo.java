package defpackage;

import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class fo {
    public final byte[] a;
    public int b;
    public int c;
    public boolean d;
    public boolean e;
    public fo f;
    public fo g;

    public fo() {
        this.a = new byte[8192];
        this.e = true;
        this.d = false;
    }

    @Nullable
    public final fo a() {
        fo foVar = this.f;
        if (foVar == this) {
            foVar = null;
        }
        fo foVar2 = this.g;
        foVar2.f = this.f;
        this.f.g = foVar2;
        this.f = null;
        this.g = null;
        return foVar;
    }

    public final fo b() {
        this.d = true;
        return new fo(this.a, this.b, this.c, true, false);
    }

    public fo(byte[] bArr, int i, int i2, boolean z, boolean z2) {
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.d = z;
        this.e = z2;
    }

    public final fo a(fo foVar) {
        foVar.g = this;
        foVar.f = this.f;
        this.f.g = foVar;
        this.f = foVar;
        return foVar;
    }

    public final void a(fo foVar, int i) {
        if (foVar.e) {
            int i2 = foVar.c;
            if (i2 + i > 8192) {
                if (!foVar.d) {
                    int i3 = foVar.b;
                    if ((i2 + i) - i3 <= 8192) {
                        byte[] bArr = foVar.a;
                        System.arraycopy(bArr, i3, bArr, 0, i2 - i3);
                        foVar.c -= foVar.b;
                        foVar.b = 0;
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }
            System.arraycopy(this.a, this.b, foVar.a, foVar.c, i);
            foVar.c += i;
            this.b += i;
            return;
        }
        throw new IllegalArgumentException();
    }
}
