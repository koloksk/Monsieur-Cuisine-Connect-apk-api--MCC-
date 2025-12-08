package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import defpackage.g9;

/* loaded from: classes.dex */
public final class Encoder {
    public static final int DEFAULT_AZTEC_LAYERS = 0;
    public static final int DEFAULT_EC_PERCENT = 33;
    public static final int[] a = {4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    public static void a(BitMatrix bitMatrix, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3 += 2) {
            int i4 = i - i3;
            int i5 = i4;
            while (true) {
                int i6 = i + i3;
                if (i5 <= i6) {
                    bitMatrix.set(i5, i4);
                    bitMatrix.set(i5, i6);
                    bitMatrix.set(i4, i5);
                    bitMatrix.set(i6, i5);
                    i5++;
                }
            }
        }
        int i7 = i - i2;
        bitMatrix.set(i7, i7);
        int i8 = i7 + 1;
        bitMatrix.set(i8, i7);
        bitMatrix.set(i7, i8);
        int i9 = i + i2;
        bitMatrix.set(i9, i7);
        bitMatrix.set(i9, i8);
        bitMatrix.set(i9, i9 - 1);
    }

    public static AztecCode encode(byte[] bArr) {
        return encode(bArr, 33, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static AztecCode encode(byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        BitArray bitArrayA;
        boolean z;
        int iAbs;
        int i5;
        BitArray bitArrayA2;
        int i6;
        BitArray bitArrayEncode = new HighLevelEncoder(bArr).encode();
        int size = ((bitArrayEncode.getSize() * i) / 100) + 11;
        int size2 = bitArrayEncode.getSize() + size;
        int i7 = 0;
        int i8 = 1;
        if (i2 == 0) {
            BitArray bitArrayA3 = null;
            int i9 = 0;
            int i10 = 0;
            while (i9 <= i) {
                int i11 = i9 <= 3 ? i8 : i7;
                int i12 = i11 != 0 ? i9 + 1 : i9;
                int i13 = ((i11 != 0 ? 88 : 112) + (i12 << 4)) * i12;
                if (size2 <= i13) {
                    int[] iArr = a;
                    if (i10 != iArr[i12]) {
                        i3 = iArr[i12];
                        bitArrayA3 = a(bitArrayEncode, i3);
                    } else {
                        i3 = i10;
                    }
                    int i14 = i13 - (i13 % i3);
                    if ((i11 == 0 || bitArrayA3.getSize() <= (i3 << 6)) && bitArrayA3.getSize() + size <= i14) {
                        i4 = i3;
                        bitArrayA = bitArrayA3;
                        z = i11;
                        iAbs = i12;
                        i5 = i13;
                    } else {
                        i10 = i3;
                    }
                }
                i9++;
                i8 = i8;
                i = 32;
                i7 = 0;
            }
            throw new IllegalArgumentException("Data too large for an Aztec code");
        }
        boolean z2 = i2 < 0;
        iAbs = Math.abs(i2);
        if (iAbs > (z2 ? 4 : 32)) {
            throw new IllegalArgumentException(String.format("Illegal value %s for layers", Integer.valueOf(i2)));
        }
        i5 = ((z2 ? 88 : 112) + (iAbs << 4)) * iAbs;
        i4 = a[iAbs];
        int i15 = i5 - (i5 % i4);
        bitArrayA = a(bitArrayEncode, i4);
        z = z2;
        if (bitArrayA.getSize() + size > i15) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        if (z2) {
            z = z2;
            if (bitArrayA.getSize() > (i4 << 6)) {
                throw new IllegalArgumentException("Data to large for user specified layer");
            }
        }
        BitArray bitArrayA4 = a(bitArrayA, i5, i4);
        int size3 = bitArrayA.getSize() / i4;
        BitArray bitArray = new BitArray();
        int i16 = 2;
        if (z != 0) {
            bitArray.appendBits(iAbs - 1, 2);
            bitArray.appendBits(size3 - 1, 6);
            bitArrayA2 = a(bitArray, 28, 4);
        } else {
            bitArray.appendBits(iAbs - 1, 5);
            bitArray.appendBits(size3 - 1, 11);
            bitArrayA2 = a(bitArray, 40, 4);
        }
        int i17 = (z == 0 ? 14 : 11) + (iAbs << 2);
        int[] iArr2 = new int[i17];
        if (z != 0) {
            for (int i18 = i7; i18 < i17; i18++) {
                iArr2[i18] = i18;
            }
            i6 = i17;
        } else {
            int i19 = i17 / 2;
            i6 = (((i19 - 1) / 15) * 2) + i17 + 1;
            int i20 = i6 / 2;
            for (int i21 = i7; i21 < i19; i21++) {
                iArr2[(i19 - i21) - 1] = (i20 - r15) - 1;
                iArr2[i19 + i21] = (i21 / 15) + i21 + i20 + i8;
            }
        }
        BitMatrix bitMatrix = new BitMatrix(i6);
        int i22 = i7;
        int i23 = i22;
        while (i22 < iAbs) {
            int i24 = ((iAbs - i22) << i16) + (z != 0 ? 9 : 12);
            while (i7 < i24) {
                int i25 = i7 << 1;
                int i26 = 0;
                while (i26 < i16) {
                    if (bitArrayA4.get(i23 + i25 + i26)) {
                        int i27 = i22 << 1;
                        bitMatrix.set(iArr2[i27 + i26], iArr2[i27 + i7]);
                    }
                    if (bitArrayA4.get((i24 << 1) + i23 + i25 + i26)) {
                        int i28 = i22 << 1;
                        bitMatrix.set(iArr2[i28 + i7], iArr2[((i17 - 1) - i28) - i26]);
                    }
                    if (bitArrayA4.get((i24 << 2) + i23 + i25 + i26)) {
                        int i29 = (i17 - 1) - (i22 << 1);
                        bitMatrix.set(iArr2[i29 - i26], iArr2[i29 - i7]);
                    }
                    if (bitArrayA4.get((i24 * 6) + i23 + i25 + i26)) {
                        int i30 = i22 << 1;
                        bitMatrix.set(iArr2[((i17 - 1) - i30) - i7], iArr2[i30 + i26]);
                    }
                    i26++;
                    i16 = 2;
                }
                i7++;
                i16 = 2;
            }
            i23 += i24 << 3;
            i22++;
            i16 = 2;
            i7 = 0;
        }
        int i31 = i6 / 2;
        int i32 = 0;
        if (z != 0) {
            while (i32 < 7) {
                int i33 = (i31 - 3) + i32;
                if (bitArrayA2.get(i32)) {
                    bitMatrix.set(i33, i31 - 5);
                }
                if (bitArrayA2.get(i32 + 7)) {
                    bitMatrix.set(i31 + 5, i33);
                }
                if (bitArrayA2.get(20 - i32)) {
                    bitMatrix.set(i33, i31 + 5);
                }
                if (bitArrayA2.get(27 - i32)) {
                    bitMatrix.set(i31 - 5, i33);
                }
                i32++;
            }
        } else {
            while (i32 < 10) {
                int i34 = (i32 / 5) + (i31 - 5) + i32;
                if (bitArrayA2.get(i32)) {
                    bitMatrix.set(i34, i31 - 7);
                }
                if (bitArrayA2.get(i32 + 10)) {
                    bitMatrix.set(i31 + 7, i34);
                }
                if (bitArrayA2.get(29 - i32)) {
                    bitMatrix.set(i34, i31 + 7);
                }
                if (bitArrayA2.get(39 - i32)) {
                    bitMatrix.set(i31 - 7, i34);
                }
                i32++;
            }
        }
        if (z != 0) {
            a(bitMatrix, i31, 5);
        } else {
            a(bitMatrix, i31, 7);
            int i35 = 0;
            int i36 = 0;
            while (i35 < (i17 / 2) - 1) {
                for (int i37 = i31 & 1; i37 < i6; i37 += 2) {
                    int i38 = i31 - i36;
                    bitMatrix.set(i38, i37);
                    int i39 = i31 + i36;
                    bitMatrix.set(i39, i37);
                    bitMatrix.set(i37, i38);
                    bitMatrix.set(i37, i39);
                }
                i35 += 15;
                i36 += 16;
            }
        }
        AztecCode aztecCode = new AztecCode();
        aztecCode.setCompact(z);
        aztecCode.setSize(i6);
        aztecCode.setLayers(iAbs);
        aztecCode.setCodeWords(size3);
        aztecCode.setMatrix(bitMatrix);
        return aztecCode;
    }

    public static BitArray a(BitArray bitArray, int i, int i2) {
        GenericGF genericGF;
        int size = bitArray.getSize() / i2;
        if (i2 == 4) {
            genericGF = GenericGF.AZTEC_PARAM;
        } else if (i2 == 6) {
            genericGF = GenericGF.AZTEC_DATA_6;
        } else if (i2 == 8) {
            genericGF = GenericGF.AZTEC_DATA_8;
        } else if (i2 == 10) {
            genericGF = GenericGF.AZTEC_DATA_10;
        } else if (i2 == 12) {
            genericGF = GenericGF.AZTEC_DATA_12;
        } else {
            throw new IllegalArgumentException(g9.a("Unsupported word size ", i2));
        }
        ReedSolomonEncoder reedSolomonEncoder = new ReedSolomonEncoder(genericGF);
        int i3 = i / i2;
        int[] iArr = new int[i3];
        int size2 = bitArray.getSize() / i2;
        for (int i4 = 0; i4 < size2; i4++) {
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                i5 |= bitArray.get((i4 * i2) + i6) ? 1 << ((i2 - i6) - 1) : 0;
            }
            iArr[i4] = i5;
        }
        reedSolomonEncoder.encode(iArr, i3 - size);
        BitArray bitArray2 = new BitArray();
        bitArray2.appendBits(0, i % i2);
        for (int i7 = 0; i7 < i3; i7++) {
            bitArray2.appendBits(iArr[i7], i2);
        }
        return bitArray2;
    }

    public static BitArray a(BitArray bitArray, int i) {
        BitArray bitArray2 = new BitArray();
        int size = bitArray.getSize();
        int i2 = (1 << i) - 2;
        int i3 = 0;
        while (i3 < size) {
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int i6 = i3 + i5;
                if (i6 >= size || bitArray.get(i6)) {
                    i4 |= 1 << ((i - 1) - i5);
                }
            }
            int i7 = i4 & i2;
            if (i7 == i2) {
                bitArray2.appendBits(i7, i);
            } else if (i7 == 0) {
                bitArray2.appendBits(i4 | 1, i);
            } else {
                bitArray2.appendBits(i4, i);
                i3 += i;
            }
            i3--;
            i3 += i;
        }
        return bitArray2;
    }
}
