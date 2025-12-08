package defpackage;

import com.google.zxing.common.reedsolomon.GenericGF;

/* loaded from: classes.dex */
public final class ja {
    public final GenericGF a;
    public final int[] b;

    public ja(GenericGF genericGF, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.a = genericGF;
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

    public ja c(int i) {
        if (i == 0) {
            return this.a.c;
        }
        if (i == 1) {
            return this;
        }
        int length = this.b.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = this.a.b(this.b[i2], i);
        }
        return new ja(this.a, iArr);
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
                    GenericGF genericGF = this.a;
                    if (genericGF == null) {
                        throw null;
                    }
                    if (iB == 0) {
                        throw new IllegalArgumentException();
                    }
                    int i = genericGF.b[iB];
                    if (i == 0) {
                        sb.append('1');
                    } else if (i == 1) {
                        sb.append('a');
                    } else {
                        sb.append("a^");
                        sb.append(i);
                    }
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
            int iC = 0;
            for (int i2 : this.b) {
                iC = GenericGF.c(iC, i2);
            }
            return iC;
        }
        int[] iArr = this.b;
        int iB = iArr[0];
        int length = iArr.length;
        for (int i3 = 1; i3 < length; i3++) {
            iB = this.a.b(i, iB) ^ this.b[i3];
        }
        return iB;
    }

    public int b(int i) {
        return this.b[(r0.length - 1) - i];
    }

    public ja b(ja jaVar) {
        if (this.a.equals(jaVar.a)) {
            if (!b() && !jaVar.b()) {
                int[] iArr = this.b;
                int length = iArr.length;
                int[] iArr2 = jaVar.b;
                int length2 = iArr2.length;
                int[] iArr3 = new int[(length + length2) - 1];
                for (int i = 0; i < length; i++) {
                    int i2 = iArr[i];
                    for (int i3 = 0; i3 < length2; i3++) {
                        int i4 = i + i3;
                        iArr3[i4] = iArr3[i4] ^ this.a.b(i2, iArr2[i3]);
                    }
                }
                return new ja(this.a, iArr3);
            }
            return this.a.c;
        }
        throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
    }

    public ja a(ja jaVar) {
        if (this.a.equals(jaVar.a)) {
            if (b()) {
                return jaVar;
            }
            if (jaVar.b()) {
                return this;
            }
            int[] iArr = this.b;
            int[] iArr2 = jaVar.b;
            if (iArr.length <= iArr2.length) {
                iArr = iArr2;
                iArr2 = iArr;
            }
            int[] iArr3 = new int[iArr.length];
            int length = iArr.length - iArr2.length;
            System.arraycopy(iArr, 0, iArr3, 0, length);
            for (int i = length; i < iArr.length; i++) {
                iArr3[i] = GenericGF.c(iArr2[i - length], iArr[i]);
            }
            return new ja(this.a, iArr3);
        }
        throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
    }

    public ja a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i2 == 0) {
            return this.a.c;
        }
        int length = this.b.length;
        int[] iArr = new int[i + length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.a.b(this.b[i3], i2);
        }
        return new ja(this.a, iArr);
    }
}
