package com.google.zxing.common;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class BitMatrix implements Cloneable {
    public final int a;
    public final int b;
    public final int c;
    public final int[] d;

    public BitMatrix(int i) {
        this(i, i);
    }

    public static BitMatrix parse(String str, String str2, String str3) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        boolean[] zArr = new boolean[str.length()];
        int i = -1;
        int length = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (length < str.length()) {
            if (str.charAt(length) == '\n' || str.charAt(length) == '\r') {
                if (i2 > i3) {
                    if (i == -1) {
                        i = i2 - i3;
                    } else if (i2 - i3 != i) {
                        throw new IllegalArgumentException("row lengths do not match");
                    }
                    i4++;
                    i3 = i2;
                }
                length++;
            } else {
                if (str.substring(length, str2.length() + length).equals(str2)) {
                    length += str2.length();
                    zArr[i2] = true;
                } else {
                    if (!str.substring(length, str3.length() + length).equals(str3)) {
                        throw new IllegalArgumentException("illegal character encountered: " + str.substring(length));
                    }
                    length += str3.length();
                    zArr[i2] = false;
                }
                i2++;
            }
        }
        if (i2 > i3) {
            if (i == -1) {
                i = i2 - i3;
            } else if (i2 - i3 != i) {
                throw new IllegalArgumentException("row lengths do not match");
            }
            i4++;
        }
        BitMatrix bitMatrix = new BitMatrix(i, i4);
        for (int i5 = 0; i5 < i2; i5++) {
            if (zArr[i5]) {
                bitMatrix.set(i5 % i, i5 / i);
            }
        }
        return bitMatrix;
    }

    public final String a(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder((this.a + 1) * this.b);
        for (int i = 0; i < this.b; i++) {
            for (int i2 = 0; i2 < this.a; i2++) {
                sb.append(get(i2, i) ? str : str2);
            }
            sb.append(str3);
        }
        return sb.toString();
    }

    public void clear() {
        int length = this.d.length;
        for (int i = 0; i < length; i++) {
            this.d[i] = 0;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BitMatrix)) {
            return false;
        }
        BitMatrix bitMatrix = (BitMatrix) obj;
        return this.a == bitMatrix.a && this.b == bitMatrix.b && this.c == bitMatrix.c && Arrays.equals(this.d, bitMatrix.d);
    }

    public void flip(int i, int i2) {
        int i3 = (i / 32) + (i2 * this.c);
        int[] iArr = this.d;
        iArr[i3] = (1 << (i & 31)) ^ iArr[i3];
    }

    public boolean get(int i, int i2) {
        return ((this.d[(i / 32) + (i2 * this.c)] >>> (i & 31)) & 1) != 0;
    }

    public int[] getBottomRightOnBit() {
        int length = this.d.length - 1;
        while (length >= 0 && this.d[length] == 0) {
            length--;
        }
        if (length < 0) {
            return null;
        }
        int i = this.c;
        int i2 = length / i;
        int i3 = (length % i) << 5;
        int i4 = 31;
        while ((this.d[length] >>> i4) == 0) {
            i4--;
        }
        return new int[]{i3 + i4, i2};
    }

    public int[] getEnclosingRectangle() {
        int i = this.a;
        int i2 = this.b;
        int i3 = -1;
        int i4 = -1;
        for (int i5 = 0; i5 < this.b; i5++) {
            int i6 = 0;
            while (true) {
                int i7 = this.c;
                if (i6 < i7) {
                    int i8 = this.d[(i7 * i5) + i6];
                    if (i8 != 0) {
                        if (i5 < i2) {
                            i2 = i5;
                        }
                        if (i5 > i4) {
                            i4 = i5;
                        }
                        int i9 = i6 << 5;
                        int i10 = 31;
                        if (i9 < i) {
                            int i11 = 0;
                            while ((i8 << (31 - i11)) == 0) {
                                i11++;
                            }
                            int i12 = i11 + i9;
                            if (i12 < i) {
                                i = i12;
                            }
                        }
                        if (i9 + 31 > i3) {
                            while ((i8 >>> i10) == 0) {
                                i10--;
                            }
                            int i13 = i9 + i10;
                            if (i13 > i3) {
                                i3 = i13;
                            }
                        }
                    }
                    i6++;
                }
            }
        }
        if (i3 < i || i4 < i2) {
            return null;
        }
        return new int[]{i, i2, (i3 - i) + 1, (i4 - i2) + 1};
    }

    public int getHeight() {
        return this.b;
    }

    public BitArray getRow(int i, BitArray bitArray) {
        if (bitArray == null || bitArray.getSize() < this.a) {
            bitArray = new BitArray(this.a);
        } else {
            bitArray.clear();
        }
        int i2 = i * this.c;
        for (int i3 = 0; i3 < this.c; i3++) {
            bitArray.setBulk(i3 << 5, this.d[i2 + i3]);
        }
        return bitArray;
    }

    public int getRowSize() {
        return this.c;
    }

    public int[] getTopLeftOnBit() {
        int i = 0;
        while (true) {
            int[] iArr = this.d;
            if (i >= iArr.length || iArr[i] != 0) {
                break;
            }
            i++;
        }
        int[] iArr2 = this.d;
        if (i == iArr2.length) {
            return null;
        }
        int i2 = this.c;
        int i3 = i / i2;
        int i4 = (i % i2) << 5;
        int i5 = iArr2[i];
        int i6 = 0;
        while ((i5 << (31 - i6)) == 0) {
            i6++;
        }
        return new int[]{i4 + i6, i3};
    }

    public int getWidth() {
        return this.a;
    }

    public int hashCode() {
        int i = this.a;
        return Arrays.hashCode(this.d) + (((((((i * 31) + i) * 31) + this.b) * 31) + this.c) * 31);
    }

    public void rotate180() {
        int width = getWidth();
        int height = getHeight();
        BitArray bitArray = new BitArray(width);
        BitArray bitArray2 = new BitArray(width);
        for (int i = 0; i < (height + 1) / 2; i++) {
            bitArray = getRow(i, bitArray);
            int i2 = (height - 1) - i;
            bitArray2 = getRow(i2, bitArray2);
            bitArray.reverse();
            bitArray2.reverse();
            setRow(i, bitArray2);
            setRow(i2, bitArray);
        }
    }

    public void set(int i, int i2) {
        int i3 = (i / 32) + (i2 * this.c);
        int[] iArr = this.d;
        iArr[i3] = (1 << (i & 31)) | iArr[i3];
    }

    public void setRegion(int i, int i2, int i3, int i4) {
        if (i2 < 0 || i < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        }
        if (i4 <= 0 || i3 <= 0) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        }
        int i5 = i3 + i;
        int i6 = i4 + i2;
        if (i6 > this.b || i5 > this.a) {
            throw new IllegalArgumentException("The region must fit inside the matrix");
        }
        while (i2 < i6) {
            int i7 = this.c * i2;
            for (int i8 = i; i8 < i5; i8++) {
                int[] iArr = this.d;
                int i9 = (i8 / 32) + i7;
                iArr[i9] = iArr[i9] | (1 << (i8 & 31));
            }
            i2++;
        }
    }

    public void setRow(int i, BitArray bitArray) {
        int[] bitArray2 = bitArray.getBitArray();
        int[] iArr = this.d;
        int i2 = this.c;
        System.arraycopy(bitArray2, 0, iArr, i * i2, i2);
    }

    public String toString() {
        return toString("X ", "  ");
    }

    public void unset(int i, int i2) {
        int i3 = (i / 32) + (i2 * this.c);
        int[] iArr = this.d;
        iArr[i3] = (~(1 << (i & 31))) & iArr[i3];
    }

    public void xor(BitMatrix bitMatrix) {
        if (this.a != bitMatrix.getWidth() || this.b != bitMatrix.getHeight() || this.c != bitMatrix.getRowSize()) {
            throw new IllegalArgumentException("input matrix dimensions do not match");
        }
        BitArray bitArray = new BitArray((this.a / 32) + 1);
        for (int i = 0; i < this.b; i++) {
            int i2 = this.c * i;
            int[] bitArray2 = bitMatrix.getRow(i, bitArray).getBitArray();
            for (int i3 = 0; i3 < this.c; i3++) {
                int[] iArr = this.d;
                int i4 = i2 + i3;
                iArr[i4] = iArr[i4] ^ bitArray2[i3];
            }
        }
    }

    public BitMatrix(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.a = i;
        this.b = i2;
        int i3 = (i + 31) / 32;
        this.c = i3;
        this.d = new int[i3 * i2];
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BitMatrix m9clone() {
        return new BitMatrix(this.a, this.b, this.c, (int[]) this.d.clone());
    }

    public String toString(String str, String str2) {
        return a(str, str2, org.apache.commons.lang3.StringUtils.LF);
    }

    @Deprecated
    public String toString(String str, String str2, String str3) {
        return a(str, str2, str3);
    }

    public BitMatrix(int i, int i2, int i3, int[] iArr) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = iArr;
    }
}
