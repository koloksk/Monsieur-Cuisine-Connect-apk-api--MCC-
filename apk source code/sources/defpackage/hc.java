package defpackage;

import com.google.zxing.pdf417.decoder.ec.ModulusGF;

/* loaded from: classes.dex */
public final class hc {
    public final ModulusGF a;
    public final int[] b;

    public hc(ModulusGF modulusGF, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.a = modulusGF;
        int length = iArr.length;
        if (length <= 1 || iArr[0] != 0) {
            this.b = iArr;
            return;
        }
        int i = 1;
        while (i < length && iArr[i] == 0) {
            i++;
        }
        if (i == length) {
            this.b = new int[]{0};
            return;
        }
        int[] iArr2 = new int[length - i];
        this.b = iArr2;
        System.arraycopy(iArr, i, iArr2, 0, iArr2.length);
    }

    public int a() {
        return this.b.length - 1;
    }

    public boolean b() {
        return this.b[0] == 0;
    }

    public hc c(hc hcVar) {
        if (this.a.equals(hcVar.a)) {
            return hcVar.b() ? this : a(hcVar.c());
        }
        throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(a() * 8);
        for (int iA = a(); iA >= 0; iA--) {
            int iB = b(iA);
            if (iB != 0) {
                if (iB < 0) {
                    sb.append(" - ");
                    iB = -iB;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (iA == 0 || iB != 1) {
                    sb.append(iB);
                }
                if (iA != 0) {
                    if (iA == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(iA);
                    }
                }
            }
        }
        return sb.toString();
    }

    public int a(int i) {
        if (i == 0) {
            return b(0);
        }
        if (i == 1) {
            int iA = 0;
            for (int i2 : this.b) {
                iA = this.a.a(iA, i2);
            }
            return iA;
        }
        int[] iArr = this.b;
        int iA2 = iArr[0];
        int length = iArr.length;
        for (int i3 = 1; i3 < length; i3++) {
            ModulusGF modulusGF = this.a;
            iA2 = modulusGF.a(modulusGF.c(i, iA2), this.b[i3]);
        }
        return iA2;
    }

    public int b(int i) {
        return this.b[(r0.length - 1) - i];
    }

    public hc b(hc hcVar) {
        if (this.a.equals(hcVar.a)) {
            if (!b() && !hcVar.b()) {
                int[] iArr = this.b;
                int length = iArr.length;
                int[] iArr2 = hcVar.b;
                int length2 = iArr2.length;
                int[] iArr3 = new int[(length + length2) - 1];
                for (int i = 0; i < length; i++) {
                    int i2 = iArr[i];
                    for (int i3 = 0; i3 < length2; i3++) {
                        int i4 = i + i3;
                        ModulusGF modulusGF = this.a;
                        iArr3[i4] = modulusGF.a(iArr3[i4], modulusGF.c(i2, iArr2[i3]));
                    }
                }
                return new hc(this.a, iArr3);
            }
            return this.a.c;
        }
        throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
    }

    public hc c() {
        int length = this.b.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = this.a.d(0, this.b[i]);
        }
        return new hc(this.a, iArr);
    }

    public hc a(hc hcVar) {
        if (this.a.equals(hcVar.a)) {
            if (b()) {
                return hcVar;
            }
            if (hcVar.b()) {
                return this;
            }
            int[] iArr = this.b;
            int[] iArr2 = hcVar.b;
            if (iArr.length <= iArr2.length) {
                iArr = iArr2;
                iArr2 = iArr;
            }
            int[] iArr3 = new int[iArr.length];
            int length = iArr.length - iArr2.length;
            System.arraycopy(iArr, 0, iArr3, 0, length);
            for (int i = length; i < iArr.length; i++) {
                iArr3[i] = this.a.a(iArr2[i - length], iArr[i]);
            }
            return new hc(this.a, iArr3);
        }
        throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
    }

    public hc c(int i) {
        if (i == 0) {
            return this.a.c;
        }
        if (i == 1) {
            return this;
        }
        int length = this.b.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = this.a.c(this.b[i2], i);
        }
        return new hc(this.a, iArr);
    }
}
