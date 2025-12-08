package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.ChecksumException;
import defpackage.hc;

/* loaded from: classes.dex */
public final class ErrorCorrection {
    public final ModulusGF a = ModulusGF.PDF417_GF;

    public int decode(int[] iArr, int i, int[] iArr2) throws ChecksumException {
        hc hcVar;
        hc hcVar2 = new hc(this.a, iArr);
        int[] iArr3 = new int[i];
        boolean z = false;
        for (int i2 = i; i2 > 0; i2--) {
            int iA = hcVar2.a(this.a.a[i2]);
            iArr3[i - i2] = iA;
            if (iA != 0) {
                z = true;
            }
        }
        if (!z) {
            return 0;
        }
        hc hcVarB = this.a.d;
        if (iArr2 != null) {
            for (int i3 : iArr2) {
                ModulusGF modulusGF = this.a;
                hcVarB = hcVarB.b(new hc(modulusGF, new int[]{modulusGF.d(0, modulusGF.a[(iArr.length - 1) - i3]), 1}));
            }
        }
        hc hcVar3 = new hc(this.a, iArr3);
        hc hcVarB2 = this.a.b(i, 1);
        if (hcVarB2.a() >= hcVar3.a()) {
            hcVarB2 = hcVar3;
            hcVar3 = hcVarB2;
        }
        ModulusGF modulusGF2 = this.a;
        hc hcVar4 = modulusGF2.c;
        hc hcVarC = modulusGF2.d;
        while (true) {
            hc hcVar5 = hcVarB2;
            hcVarB2 = hcVar3;
            hcVar3 = hcVar5;
            hc hcVar6 = hcVar4;
            hcVar4 = hcVarC;
            if (hcVar3.a() < i / 2) {
                int iB = hcVar4.b(0);
                if (iB == 0) {
                    throw ChecksumException.getChecksumInstance();
                }
                int iA2 = this.a.a(iB);
                hc[] hcVarArr = {hcVar4.c(iA2), hcVar3.c(iA2)};
                hc hcVar7 = hcVarArr[0];
                hc hcVar8 = hcVarArr[1];
                int iA3 = hcVar7.a();
                int[] iArr4 = new int[iA3];
                int i4 = 0;
                for (int i5 = 1; i5 < this.a.e && i4 < iA3; i5++) {
                    if (hcVar7.a(i5) == 0) {
                        iArr4[i4] = this.a.a(i5);
                        i4++;
                    }
                }
                if (i4 != iA3) {
                    throw ChecksumException.getChecksumInstance();
                }
                int iA4 = hcVar7.a();
                int[] iArr5 = new int[iA4];
                for (int i6 = 1; i6 <= iA4; i6++) {
                    iArr5[iA4 - i6] = this.a.c(i6, hcVar7.b(i6));
                }
                hc hcVar9 = new hc(this.a, iArr5);
                int[] iArr6 = new int[iA3];
                for (int i7 = 0; i7 < iA3; i7++) {
                    int iA5 = this.a.a(iArr4[i7]);
                    iArr6[i7] = this.a.c(this.a.d(0, hcVar8.a(iA5)), this.a.a(hcVar9.a(iA5)));
                }
                for (int i8 = 0; i8 < iA3; i8++) {
                    int length = iArr.length - 1;
                    ModulusGF modulusGF3 = this.a;
                    int i9 = iArr4[i8];
                    if (modulusGF3 == null) {
                        throw null;
                    }
                    if (i9 == 0) {
                        throw new IllegalArgumentException();
                    }
                    int i10 = length - modulusGF3.b[i9];
                    if (i10 < 0) {
                        throw ChecksumException.getChecksumInstance();
                    }
                    iArr[i10] = modulusGF3.d(iArr[i10], iArr6[i8]);
                }
                return iA3;
            }
            if (hcVar3.b()) {
                throw ChecksumException.getChecksumInstance();
            }
            hc hcVarA = this.a.c;
            int iA6 = this.a.a(hcVar3.b(hcVar3.a()));
            while (hcVarB2.a() >= hcVar3.a() && !hcVarB2.b()) {
                int iA7 = hcVarB2.a() - hcVar3.a();
                int iC = this.a.c(hcVarB2.b(hcVarB2.a()), iA6);
                hcVarA = hcVarA.a(this.a.b(iA7, iC));
                if (iA7 < 0) {
                    throw new IllegalArgumentException();
                }
                if (iC == 0) {
                    hcVar = hcVar3.a.c;
                } else {
                    int length2 = hcVar3.b.length;
                    int[] iArr7 = new int[iA7 + length2];
                    for (int i11 = 0; i11 < length2; i11++) {
                        iArr7[i11] = hcVar3.a.c(hcVar3.b[i11], iC);
                    }
                    hcVar = new hc(hcVar3.a, iArr7);
                }
                hcVarB2 = hcVarB2.c(hcVar);
            }
            hcVarC = hcVarA.b(hcVar4).c(hcVar6).c();
        }
    }
}
