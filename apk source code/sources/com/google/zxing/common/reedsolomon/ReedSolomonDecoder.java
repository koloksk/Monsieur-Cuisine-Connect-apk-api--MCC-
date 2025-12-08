package com.google.zxing.common.reedsolomon;

import defpackage.ja;

/* loaded from: classes.dex */
public final class ReedSolomonDecoder {
    public final GenericGF a;

    public ReedSolomonDecoder(GenericGF genericGF) {
        this.a = genericGF;
    }

    public void decode(int[] iArr, int i) throws ReedSolomonException {
        int[] iArr2;
        ja jaVar = new ja(this.a, iArr);
        int[] iArr3 = new int[i];
        boolean z = true;
        for (int i2 = 0; i2 < i; i2++) {
            GenericGF genericGF = this.a;
            int iA = jaVar.a(genericGF.a[genericGF.getGeneratorBase() + i2]);
            iArr3[(i - 1) - i2] = iA;
            if (iA != 0) {
                z = false;
            }
        }
        if (z) {
            return;
        }
        ja jaVar2 = new ja(this.a, iArr3);
        ja jaVarA = this.a.a(i, 1);
        if (jaVarA.a() < jaVar2.a()) {
            jaVarA = jaVar2;
            jaVar2 = jaVarA;
        }
        GenericGF genericGF2 = this.a;
        ja jaVar3 = genericGF2.c;
        ja jaVar4 = genericGF2.d;
        ja jaVar5 = jaVar3;
        while (jaVar2.a() >= i / 2) {
            if (jaVar2.b()) {
                throw new ReedSolomonException("r_{i-1} was zero");
            }
            ja jaVarA2 = this.a.c;
            int iA2 = this.a.a(jaVar2.b(jaVar2.a()));
            while (jaVarA.a() >= jaVar2.a() && !jaVarA.b()) {
                int iA3 = jaVarA.a() - jaVar2.a();
                int iB = this.a.b(jaVarA.b(jaVarA.a()), iA2);
                jaVarA2 = jaVarA2.a(this.a.a(iA3, iB));
                jaVarA = jaVarA.a(jaVar2.a(iA3, iB));
            }
            ja jaVarA3 = jaVarA2.b(jaVar4).a(jaVar5);
            if (jaVarA.a() >= jaVar2.a()) {
                throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
            }
            ja jaVar6 = jaVarA;
            jaVarA = jaVar2;
            jaVar2 = jaVar6;
            ja jaVar7 = jaVar4;
            jaVar4 = jaVarA3;
            jaVar5 = jaVar7;
        }
        int iB2 = jaVar4.b(0);
        if (iB2 == 0) {
            throw new ReedSolomonException("sigmaTilde(0) was zero");
        }
        int iA4 = this.a.a(iB2);
        ja[] jaVarArr = {jaVar4.c(iA4), jaVar2.c(iA4)};
        ja jaVar8 = jaVarArr[0];
        ja jaVar9 = jaVarArr[1];
        int iA5 = jaVar8.a();
        if (iA5 == 1) {
            iArr2 = new int[]{jaVar8.b(1)};
        } else {
            int[] iArr4 = new int[iA5];
            int i3 = 0;
            for (int i4 = 1; i4 < this.a.getSize() && i3 < iA5; i4++) {
                if (jaVar8.a(i4) == 0) {
                    iArr4[i3] = this.a.a(i4);
                    i3++;
                }
            }
            if (i3 != iA5) {
                throw new ReedSolomonException("Error locator degree does not match number of roots");
            }
            iArr2 = iArr4;
        }
        int length = iArr2.length;
        int[] iArr5 = new int[length];
        for (int i5 = 0; i5 < length; i5++) {
            int iA6 = this.a.a(iArr2[i5]);
            int iB3 = 1;
            for (int i6 = 0; i6 < length; i6++) {
                if (i5 != i6) {
                    int iB4 = this.a.b(iArr2[i6], iA6);
                    iB3 = this.a.b(iB3, (iB4 & 1) == 0 ? iB4 | 1 : iB4 & (-2));
                }
            }
            iArr5[i5] = this.a.b(jaVar9.a(iA6), this.a.a(iB3));
            if (this.a.getGeneratorBase() != 0) {
                iArr5[i5] = this.a.b(iArr5[i5], iA6);
            }
        }
        for (int i7 = 0; i7 < iArr2.length; i7++) {
            int length2 = iArr.length - 1;
            GenericGF genericGF3 = this.a;
            int i8 = iArr2[i7];
            if (genericGF3 == null) {
                throw null;
            }
            if (i8 == 0) {
                throw new IllegalArgumentException();
            }
            int i9 = length2 - genericGF3.b[i8];
            if (i9 < 0) {
                throw new ReedSolomonException("Bad error location");
            }
            iArr[i9] = iArr[i9] ^ iArr5[i7];
        }
    }
}
