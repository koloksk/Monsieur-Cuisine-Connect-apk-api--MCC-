package com.google.zxing.common.reedsolomon;

import defpackage.ja;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ReedSolomonEncoder {
    public final GenericGF a;
    public final List<ja> b;

    public ReedSolomonEncoder(GenericGF genericGF) {
        this.a = genericGF;
        ArrayList arrayList = new ArrayList();
        this.b = arrayList;
        arrayList.add(new ja(genericGF, new int[]{1}));
    }

    public void encode(int[] iArr, int i) {
        if (i == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int length = iArr.length - i;
        if (length <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        if (i >= this.b.size()) {
            List<ja> list = this.b;
            ja jaVarB = list.get(list.size() - 1);
            for (int size = this.b.size(); size <= i; size++) {
                GenericGF genericGF = this.a;
                jaVarB = jaVarB.b(new ja(genericGF, new int[]{1, genericGF.a[genericGF.getGeneratorBase() + (size - 1)]}));
                this.b.add(jaVarB);
            }
        }
        ja jaVar = this.b.get(i);
        int[] iArr2 = new int[length];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        GenericGF genericGF2 = this.a;
        if (iArr2.length == 0) {
            throw new IllegalArgumentException();
        }
        int length2 = iArr2.length;
        if (length2 > 1 && iArr2[0] == 0) {
            int i2 = 1;
            while (i2 < length2 && iArr2[i2] == 0) {
                i2++;
            }
            if (i2 == length2) {
                iArr2 = new int[]{0};
            } else {
                int[] iArr3 = new int[length2 - i2];
                System.arraycopy(iArr2, i2, iArr3, 0, iArr3.length);
                iArr2 = iArr3;
            }
        }
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        int length3 = iArr2.length;
        int[] iArr4 = new int[i + length3];
        for (int i3 = 0; i3 < length3; i3++) {
            iArr4[i3] = genericGF2.b(iArr2[i3], 1);
        }
        ja jaVar2 = new ja(genericGF2, iArr4);
        if (!jaVar2.a.equals(jaVar.a)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (jaVar.b()) {
            throw new IllegalArgumentException("Divide by 0");
        }
        ja jaVarA = jaVar2.a.c;
        int iA = jaVar2.a.a(jaVar.b(jaVar.a()));
        ja jaVarA2 = jaVar2;
        while (jaVarA2.a() >= jaVar.a() && !jaVarA2.b()) {
            int iA2 = jaVarA2.a() - jaVar.a();
            int iB = jaVar2.a.b(jaVarA2.b(jaVarA2.a()), iA);
            ja jaVarA3 = jaVar.a(iA2, iB);
            jaVarA = jaVarA.a(jaVar2.a.a(iA2, iB));
            jaVarA2 = jaVarA2.a(jaVarA3);
        }
        int[] iArr5 = new ja[]{jaVarA, jaVarA2}[1].b;
        int length4 = i - iArr5.length;
        for (int i4 = 0; i4 < length4; i4++) {
            iArr[length + i4] = 0;
        }
        System.arraycopy(iArr5, 0, iArr, length + length4, iArr5.length);
    }
}
