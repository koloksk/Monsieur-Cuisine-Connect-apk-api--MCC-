package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Map;

/* loaded from: classes.dex */
public final class ITFReader extends OneDReader {
    public static final int[] b = {6, 8, 10, 12, 14};
    public static final int[] c = {1, 1, 1, 1};
    public static final int[] d = {1, 1, 3};
    public static final int[][] e = {new int[]{1, 1, 3, 3, 1}, new int[]{3, 1, 1, 1, 3}, new int[]{1, 3, 1, 1, 3}, new int[]{3, 3, 1, 1, 1}, new int[]{1, 1, 3, 1, 3}, new int[]{3, 1, 3, 1, 1}, new int[]{1, 3, 3, 1, 1}, new int[]{1, 1, 1, 3, 3}, new int[]{3, 1, 1, 3, 1}, new int[]{1, 3, 1, 3, 1}};
    public int a = -1;

    public final void a(BitArray bitArray, int i) throws NotFoundException {
        int i2 = this.a * 10;
        if (i2 >= i) {
            i2 = i;
        }
        for (int i3 = i - 1; i2 > 0 && i3 >= 0 && !bitArray.get(i3); i3--) {
            i2--;
        }
        if (i2 != 0) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        boolean z;
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        if (nextSet == size) {
            throw NotFoundException.getNotFoundInstance();
        }
        int[] iArrA = a(bitArray, nextSet, c);
        this.a = (iArrA[1] - iArrA[0]) / 4;
        a(bitArray, iArrA[0]);
        bitArray.reverse();
        try {
            int size2 = bitArray.getSize();
            int nextSet2 = bitArray.getNextSet(0);
            if (nextSet2 == size2) {
                throw NotFoundException.getNotFoundInstance();
            }
            int[] iArrA2 = a(bitArray, nextSet2, d);
            a(bitArray, iArrA2[0]);
            int i2 = iArrA2[0];
            iArrA2[0] = bitArray.getSize() - iArrA2[1];
            iArrA2[1] = bitArray.getSize() - i2;
            bitArray.reverse();
            StringBuilder sb = new StringBuilder(20);
            int i3 = iArrA[1];
            int i4 = iArrA2[0];
            int[] iArr = new int[10];
            int[] iArr2 = new int[5];
            int[] iArr3 = new int[5];
            while (i3 < i4) {
                OneDReader.recordPattern(bitArray, i3, iArr);
                for (int i5 = 0; i5 < 5; i5++) {
                    int i6 = i5 * 2;
                    iArr2[i5] = iArr[i6];
                    iArr3[i5] = iArr[i6 + 1];
                }
                sb.append((char) (a(iArr2) + 48));
                sb.append((char) (a(iArr3) + 48));
                for (int i7 = 0; i7 < 10; i7++) {
                    i3 += iArr[i7];
                }
            }
            String string = sb.toString();
            int[] iArr4 = map != null ? (int[]) map.get(DecodeHintType.ALLOWED_LENGTHS) : null;
            if (iArr4 == null) {
                iArr4 = b;
            }
            int length = string.length();
            int length2 = iArr4.length;
            int i8 = 0;
            int i9 = 0;
            while (true) {
                if (i8 >= length2) {
                    z = false;
                    break;
                }
                int i10 = iArr4[i8];
                if (length == i10) {
                    z = true;
                    break;
                }
                if (i10 > i9) {
                    i9 = i10;
                }
                i8++;
            }
            if (!z && length > i9) {
                z = true;
            }
            if (!z) {
                throw FormatException.getFormatInstance();
            }
            float f = i;
            return new Result(string, null, new ResultPoint[]{new ResultPoint(iArrA[1], f), new ResultPoint(iArrA2[0], f)}, BarcodeFormat.ITF);
        } catch (Throwable th) {
            bitArray.reverse();
            throw th;
        }
    }

    public static int[] a(BitArray bitArray, int i, int[] iArr) throws NotFoundException {
        int length = iArr.length;
        int[] iArr2 = new int[length];
        int size = bitArray.getSize();
        int i2 = i;
        boolean z = false;
        int i3 = 0;
        while (i < size) {
            if (bitArray.get(i) ^ z) {
                iArr2[i3] = iArr2[i3] + 1;
            } else {
                int i4 = length - 1;
                if (i3 != i4) {
                    i3++;
                } else {
                    if (OneDReader.patternMatchVariance(iArr2, iArr, 0.78f) < 0.38f) {
                        return new int[]{i2, i};
                    }
                    i2 += iArr2[0] + iArr2[1];
                    int i5 = length - 2;
                    System.arraycopy(iArr2, 2, iArr2, 0, i5);
                    iArr2[i5] = 0;
                    iArr2[i4] = 0;
                    i3--;
                }
                iArr2[i3] = 1;
                z = !z;
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public static int a(int[] iArr) throws NotFoundException {
        int length = e.length;
        float f = 0.38f;
        int i = -1;
        for (int i2 = 0; i2 < length; i2++) {
            float fPatternMatchVariance = OneDReader.patternMatchVariance(iArr, e[i2], 0.78f);
            if (fPatternMatchVariance < f) {
                i = i2;
                f = fPatternMatchVariance;
            }
        }
        if (i >= 0) {
            return i;
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
