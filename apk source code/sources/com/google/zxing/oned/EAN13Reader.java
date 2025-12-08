package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes.dex */
public final class EAN13Reader extends UPCEANReader {
    public static final int[] j = {0, 11, 13, 14, 19, 25, 28, 21, 22, 26};
    public final int[] i = new int[4];

    @Override // com.google.zxing.oned.UPCEANReader
    public BarcodeFormat a() {
        return BarcodeFormat.EAN_13;
    }

    @Override // com.google.zxing.oned.UPCEANReader
    public int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder sb) throws NotFoundException {
        int[] iArr2 = this.i;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int size = bitArray.getSize();
        int i = iArr[1];
        int i2 = 0;
        for (int i3 = 0; i3 < 6 && i < size; i3++) {
            int iA = UPCEANReader.a(bitArray, iArr2, i, UPCEANReader.h);
            sb.append((char) ((iA % 10) + 48));
            for (int i4 : iArr2) {
                i += i4;
            }
            if (iA >= 10) {
                i2 |= 1 << (5 - i3);
            }
        }
        for (int i5 = 0; i5 < 10; i5++) {
            if (i2 == j[i5]) {
                sb.insert(0, (char) (i5 + 48));
                int i6 = UPCEANReader.a(bitArray, i, true, UPCEANReader.e)[1];
                for (int i7 = 0; i7 < 6 && i6 < size; i7++) {
                    sb.append((char) (UPCEANReader.a(bitArray, iArr2, i6, UPCEANReader.g) + 48));
                    for (int i8 : iArr2) {
                        i6 += i8;
                    }
                }
                return i6;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
