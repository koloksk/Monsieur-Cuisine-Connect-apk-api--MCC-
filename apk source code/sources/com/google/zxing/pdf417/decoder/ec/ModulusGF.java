package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.pdf417.PDF417Common;
import defpackage.hc;

/* loaded from: classes.dex */
public final class ModulusGF {
    public static final ModulusGF PDF417_GF = new ModulusGF(PDF417Common.NUMBER_OF_CODEWORDS, 3);
    public final int[] a;
    public final int[] b;
    public final hc c;
    public final hc d;
    public final int e;

    public ModulusGF(int i, int i2) {
        this.e = i;
        this.a = new int[i];
        this.b = new int[i];
        int i3 = 1;
        for (int i4 = 0; i4 < i; i4++) {
            this.a[i4] = i3;
            i3 = (i3 * i2) % i;
        }
        for (int i5 = 0; i5 < i - 1; i5++) {
            this.b[this.a[i5]] = i5;
        }
        this.c = new hc(this, new int[]{0});
        this.d = new hc(this, new int[]{1});
    }

    public int a(int i, int i2) {
        return (i + i2) % this.e;
    }

    public hc b(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i2 == 0) {
            return this.c;
        }
        int[] iArr = new int[i + 1];
        iArr[0] = i2;
        return new hc(this, iArr);
    }

    public int c(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return 0;
        }
        int[] iArr = this.a;
        int[] iArr2 = this.b;
        return iArr[(iArr2[i] + iArr2[i2]) % (this.e - 1)];
    }

    public int d(int i, int i2) {
        int i3 = this.e;
        return ((i + i3) - i2) % i3;
    }

    public int a(int i) {
        if (i != 0) {
            return this.a[(this.e - this.b[i]) - 1];
        }
        throw new ArithmeticException();
    }
}
