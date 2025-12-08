package com.google.zxing.pdf417.encoder;

import defpackage.ic;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public final class BarcodeMatrix {
    public final ic[] a;
    public int b;
    public final int c;
    public final int d;

    public BarcodeMatrix(int i, int i2) {
        ic[] icVarArr = new ic[i];
        this.a = icVarArr;
        int length = icVarArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            this.a[i3] = new ic(((i2 + 4) * 17) + 1);
        }
        this.d = i2 * 17;
        this.c = i;
        this.b = -1;
    }

    public ic a() {
        return this.a[this.b];
    }

    public byte[][] getMatrix() {
        return getScaledMatrix(1, 1);
    }

    public byte[][] getScaledMatrix(int i, int i2) {
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) byte.class, this.c * i2, this.d * i);
        int i3 = this.c * i2;
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = (i3 - i4) - 1;
            ic icVar = this.a[i4 / i2];
            int length = icVar.a.length * i;
            byte[] bArr2 = new byte[length];
            for (int i6 = 0; i6 < length; i6++) {
                bArr2[i6] = icVar.a[i6 / i];
            }
            bArr[i5] = bArr2;
        }
        return bArr;
    }
}
