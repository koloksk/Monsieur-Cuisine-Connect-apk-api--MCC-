package com.google.zxing.common;

/* loaded from: classes.dex */
public final class BitSource {
    public final byte[] a;
    public int b;
    public int c;

    public BitSource(byte[] bArr) {
        this.a = bArr;
    }

    public int available() {
        return ((this.a.length - this.b) * 8) - this.c;
    }

    public int getBitOffset() {
        return this.c;
    }

    public int getByteOffset() {
        return this.b;
    }

    public int readBits(int i) {
        if (i <= 0 || i > 32 || i > available()) {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        int i2 = this.c;
        int i3 = 0;
        if (i2 > 0) {
            int i4 = 8 - i2;
            int i5 = i < i4 ? i : i4;
            int i6 = i4 - i5;
            byte[] bArr = this.a;
            int i7 = this.b;
            int i8 = (((255 >> (8 - i5)) << i6) & bArr[i7]) >> i6;
            i -= i5;
            int i9 = this.c + i5;
            this.c = i9;
            if (i9 == 8) {
                this.c = 0;
                this.b = i7 + 1;
            }
            i3 = i8;
        }
        if (i <= 0) {
            return i3;
        }
        while (i >= 8) {
            int i10 = i3 << 8;
            byte[] bArr2 = this.a;
            int i11 = this.b;
            i3 = (bArr2[i11] & 255) | i10;
            this.b = i11 + 1;
            i -= 8;
        }
        if (i <= 0) {
            return i3;
        }
        int i12 = 8 - i;
        int i13 = (i3 << i) | ((((255 >> i12) << i12) & this.a[this.b]) >> i12);
        this.c += i;
        return i13;
    }
}
