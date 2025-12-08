package defpackage;

import com.google.zxing.aztec.encoder.HighLevelEncoder;

/* loaded from: classes.dex */
public final class fa {
    public static final fa e = new fa(ga.b, 0, 0, 0);
    public final int a;
    public final ga b;
    public final int c;
    public final int d;

    public fa(ga gaVar, int i, int i2, int i3) {
        this.b = gaVar;
        this.a = i;
        this.c = i2;
        this.d = i3;
    }

    public fa a(int i, int i2) {
        int i3 = this.d;
        ga eaVar = this.b;
        int i4 = this.a;
        if (i != i4) {
            int i5 = HighLevelEncoder.c[i4][i];
            int i6 = 65535 & i5;
            int i7 = i5 >> 16;
            if (eaVar == null) {
                throw null;
            }
            i3 += i7;
            eaVar = new ea(eaVar, i6, i7);
        }
        int i8 = i == 2 ? 4 : 5;
        if (eaVar != null) {
            return new fa(new ea(eaVar, i2, i8), i, 0, i3 + i8);
        }
        throw null;
    }

    public fa b(int i, int i2) {
        ga gaVar = this.b;
        int i3 = this.a == 2 ? 4 : 5;
        int i4 = HighLevelEncoder.e[this.a][i];
        if (gaVar != null) {
            return new fa(new ea(new ea(gaVar, i4, i3), i2, 5), this.a, 0, this.d + i3 + 5);
        }
        throw null;
    }

    public String toString() {
        return String.format("%s bits=%d bytes=%d", HighLevelEncoder.b[this.a], Integer.valueOf(this.d), Integer.valueOf(this.c));
    }

    public fa b(int i) {
        int i2 = this.c;
        if (i2 == 0) {
            return this;
        }
        ga gaVar = this.b;
        int i3 = i - i2;
        if (gaVar != null) {
            return new fa(new da(gaVar, i3, i2), this.a, 0, this.d);
        }
        throw null;
    }

    public fa a(int i) {
        ga gaVar = this.b;
        int i2 = this.a;
        int i3 = this.d;
        if (i2 == 4 || i2 == 2) {
            int i4 = HighLevelEncoder.c[i2][0];
            int i5 = 65535 & i4;
            int i6 = i4 >> 16;
            if (gaVar != null) {
                ea eaVar = new ea(gaVar, i5, i6);
                i3 += i6;
                i2 = 0;
                gaVar = eaVar;
            } else {
                throw null;
            }
        }
        int i7 = this.c;
        fa faVar = new fa(gaVar, i2, this.c + 1, i3 + ((i7 == 0 || i7 == 31) ? 18 : i7 == 62 ? 9 : 8));
        return faVar.c == 2078 ? faVar.b(i + 1) : faVar;
    }

    public boolean a(fa faVar) {
        int i;
        int i2 = this.d + (HighLevelEncoder.c[this.a][faVar.a] >> 16);
        int i3 = faVar.c;
        if (i3 > 0 && ((i = this.c) == 0 || i > i3)) {
            i2 += 10;
        }
        return i2 <= faVar.d;
    }
}
