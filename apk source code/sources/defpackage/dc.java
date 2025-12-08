package defpackage;

import com.google.zxing.ResultPoint;
import java.util.Formatter;

/* loaded from: classes.dex */
public final class dc {
    public final yb a;
    public final ec[] b;
    public ac c;
    public final int d;

    public dc(yb ybVar, ac acVar) {
        this.a = ybVar;
        int i = ybVar.a;
        this.d = i;
        this.c = acVar;
        this.b = new ec[i + 2];
    }

    public final void a(ec ecVar) {
        int i;
        if (ecVar != null) {
            fc fcVar = (fc) ecVar;
            yb ybVar = this.a;
            bc[] bcVarArr = fcVar.b;
            for (bc bcVar : bcVarArr) {
                if (bcVar != null) {
                    bcVar.b();
                }
            }
            fcVar.a(bcVarArr, ybVar);
            ac acVar = fcVar.a;
            ResultPoint resultPoint = fcVar.c ? acVar.b : acVar.d;
            ResultPoint resultPoint2 = fcVar.c ? acVar.c : acVar.e;
            int iB = fcVar.b((int) resultPoint.getY());
            int iB2 = fcVar.b((int) resultPoint2.getY());
            int i2 = -1;
            int i3 = 0;
            int i4 = 1;
            while (iB < iB2) {
                if (bcVarArr[iB] != null) {
                    bc bcVar2 = bcVarArr[iB];
                    int i5 = bcVar2.e;
                    int i6 = i5 - i2;
                    if (i6 == 0) {
                        i3++;
                    } else {
                        if (i6 == 1) {
                            int iMax = Math.max(i4, i3);
                            i = bcVar2.e;
                            i4 = iMax;
                        } else if (i6 < 0 || i5 >= ybVar.e || i6 > iB) {
                            bcVarArr[iB] = null;
                        } else {
                            if (i4 > 2) {
                                i6 *= i4 - 2;
                            }
                            boolean z = i6 >= iB;
                            for (int i7 = 1; i7 <= i6 && !z; i7++) {
                                z = bcVarArr[iB - i7] != null;
                            }
                            if (z) {
                                bcVarArr[iB] = null;
                            } else {
                                i = bcVar2.e;
                            }
                        }
                        i2 = i;
                        i3 = 1;
                    }
                }
                iB++;
            }
        }
    }

    public String toString() {
        ec[] ecVarArr = this.b;
        ec ecVar = ecVarArr[0];
        if (ecVar == null) {
            ecVar = ecVarArr[this.d + 1];
        }
        Formatter formatter = new Formatter();
        for (int i = 0; i < ecVar.b.length; i++) {
            formatter.format("CW %3d:", Integer.valueOf(i));
            for (int i2 = 0; i2 < this.d + 2; i2++) {
                ec[] ecVarArr2 = this.b;
                if (ecVarArr2[i2] == null) {
                    formatter.format("    |   ", new Object[0]);
                } else {
                    bc bcVar = ecVarArr2[i2].b[i];
                    if (bcVar == null) {
                        formatter.format("    |   ", new Object[0]);
                    } else {
                        formatter.format(" %3d|%3d", Integer.valueOf(bcVar.e), Integer.valueOf(bcVar.d));
                    }
                }
            }
            formatter.format("%n", new Object[0]);
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    public static int a(int i, int i2, bc bcVar) {
        if (bcVar == null || bcVar.a()) {
            return i2;
        }
        if (!(i != -1 && bcVar.c == (i % 3) * 3)) {
            return i2 + 1;
        }
        bcVar.e = i;
        return 0;
    }
}
