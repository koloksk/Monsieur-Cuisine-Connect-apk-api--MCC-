package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import defpackage.cb;
import defpackage.za;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class UPCEANReader extends OneDReader {
    public static final int[] d = {1, 1, 1};
    public static final int[] e = {1, 1, 1, 1, 1};
    public static final int[] f = {1, 1, 1, 1, 1, 1};
    public static final int[][] g;
    public static final int[][] h;
    public final StringBuilder a = new StringBuilder(20);
    public final cb b = new cb();
    public final za c = new za();

    static {
        int[][] iArr = {new int[]{3, 2, 1, 1}, new int[]{2, 2, 2, 1}, new int[]{2, 1, 2, 2}, new int[]{1, 4, 1, 1}, new int[]{1, 1, 3, 2}, new int[]{1, 2, 3, 1}, new int[]{1, 1, 1, 4}, new int[]{1, 3, 1, 2}, new int[]{1, 2, 1, 3}, new int[]{3, 1, 1, 2}};
        g = iArr;
        int[][] iArr2 = new int[20][];
        h = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, 10);
        for (int i = 10; i < 20; i++) {
            int[] iArr3 = g[i - 10];
            int[] iArr4 = new int[iArr3.length];
            for (int i2 = 0; i2 < iArr3.length; i2++) {
                iArr4[i2] = iArr3[(iArr3.length - i2) - 1];
            }
            h[i] = iArr4;
        }
    }

    public static int[] a(BitArray bitArray) throws NotFoundException {
        int[] iArr = new int[d.length];
        int[] iArrA = null;
        boolean zIsRange = false;
        int i = 0;
        while (!zIsRange) {
            Arrays.fill(iArr, 0, d.length, 0);
            iArrA = a(bitArray, i, false, d, iArr);
            int i2 = iArrA[0];
            int i3 = iArrA[1];
            int i4 = i2 - (i3 - i2);
            if (i4 >= 0) {
                zIsRange = bitArray.isRange(i4, i2, false);
            }
            i = i3;
        }
        return iArrA;
    }

    public abstract BarcodeFormat a();

    public boolean checkChecksum(String str) throws FormatException {
        return a(str);
    }

    public int[] decodeEnd(BitArray bitArray, int i) throws NotFoundException {
        return a(bitArray, i, false, d);
    }

    public abstract int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder sb) throws NotFoundException;

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        return decodeRow(i, bitArray, a(bitArray), map);
    }

    public Result decodeRow(int i, BitArray bitArray, int[] iArr, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, NumberFormatException, FormatException {
        int length;
        boolean z;
        String str = null;
        ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        if (resultPointCallback != null) {
            resultPointCallback.foundPossibleResultPoint(new ResultPoint((iArr[0] + iArr[1]) / 2.0f, i));
        }
        StringBuilder sb = this.a;
        sb.setLength(0);
        int iDecodeMiddle = decodeMiddle(bitArray, iArr, sb);
        if (resultPointCallback != null) {
            resultPointCallback.foundPossibleResultPoint(new ResultPoint(iDecodeMiddle, i));
        }
        int[] iArrDecodeEnd = decodeEnd(bitArray, iDecodeMiddle);
        if (resultPointCallback != null) {
            resultPointCallback.foundPossibleResultPoint(new ResultPoint((iArrDecodeEnd[0] + iArrDecodeEnd[1]) / 2.0f, i));
        }
        int i2 = iArrDecodeEnd[1];
        int i3 = (i2 - iArrDecodeEnd[0]) + i2;
        if (i3 >= bitArray.getSize() || !bitArray.isRange(i2, i3, false)) {
            throw NotFoundException.getNotFoundInstance();
        }
        String string = sb.toString();
        if (string.length() < 8) {
            throw FormatException.getFormatInstance();
        }
        if (!checkChecksum(string)) {
            throw ChecksumException.getChecksumInstance();
        }
        BarcodeFormat barcodeFormatA = a();
        float f2 = i;
        Result result = new Result(string, null, new ResultPoint[]{new ResultPoint((iArr[1] + iArr[0]) / 2.0f, f2), new ResultPoint((iArrDecodeEnd[1] + iArrDecodeEnd[0]) / 2.0f, f2)}, barcodeFormatA);
        try {
            Result resultA = this.b.a(i, bitArray, iArrDecodeEnd[1]);
            result.putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, resultA.getText());
            result.putAllMetadata(resultA.getResultMetadata());
            result.addResultPoints(resultA.getResultPoints());
            length = resultA.getText().length();
        } catch (ReaderException unused) {
            length = 0;
        }
        int[] iArr2 = map == null ? null : (int[]) map.get(DecodeHintType.ALLOWED_EAN_EXTENSIONS);
        if (iArr2 != null) {
            int length2 = iArr2.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length2) {
                    z = false;
                    break;
                }
                if (length == iArr2[i4]) {
                    z = true;
                    break;
                }
                i4++;
            }
            if (!z) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (barcodeFormatA == BarcodeFormat.EAN_13 || barcodeFormatA == BarcodeFormat.UPC_A) {
            za zaVar = this.c;
            zaVar.a();
            int i5 = Integer.parseInt(string.substring(0, 3));
            int size = zaVar.a.size();
            int i6 = 0;
            while (true) {
                if (i6 < size) {
                    int[] iArr3 = zaVar.a.get(i6);
                    int i7 = iArr3[0];
                    if (i5 < i7) {
                        break;
                    }
                    if (iArr3.length != 1) {
                        i7 = iArr3[1];
                    }
                    if (i5 <= i7) {
                        str = zaVar.b.get(i6);
                        break;
                    }
                    i6++;
                } else {
                    break;
                }
            }
            if (str != null) {
                result.putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, str);
            }
        }
        return result;
    }

    public static boolean a(CharSequence charSequence) throws FormatException {
        int length = charSequence.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        for (int i2 = length - 2; i2 >= 0; i2 -= 2) {
            int iCharAt = charSequence.charAt(i2) - '0';
            if (iCharAt < 0 || iCharAt > 9) {
                throw FormatException.getFormatInstance();
            }
            i += iCharAt;
        }
        int i3 = i * 3;
        for (int i4 = length - 1; i4 >= 0; i4 -= 2) {
            int iCharAt2 = charSequence.charAt(i4) - '0';
            if (iCharAt2 < 0 || iCharAt2 > 9) {
                throw FormatException.getFormatInstance();
            }
            i3 += iCharAt2;
        }
        return i3 % 10 == 0;
    }

    public static int[] a(BitArray bitArray, int i, boolean z, int[] iArr) throws NotFoundException {
        return a(bitArray, i, z, iArr, new int[iArr.length]);
    }

    public static int[] a(BitArray bitArray, int i, boolean z, int[] iArr, int[] iArr2) throws NotFoundException {
        int size = bitArray.getSize();
        int nextUnset = z ? bitArray.getNextUnset(i) : bitArray.getNextSet(i);
        int length = iArr.length;
        boolean z2 = z;
        int i2 = 0;
        int i3 = nextUnset;
        while (nextUnset < size) {
            if (bitArray.get(nextUnset) ^ z2) {
                iArr2[i2] = iArr2[i2] + 1;
            } else {
                int i4 = length - 1;
                if (i2 != i4) {
                    i2++;
                } else {
                    if (OneDReader.patternMatchVariance(iArr2, iArr, 0.7f) < 0.48f) {
                        return new int[]{i3, nextUnset};
                    }
                    i3 += iArr2[0] + iArr2[1];
                    int i5 = length - 2;
                    System.arraycopy(iArr2, 2, iArr2, 0, i5);
                    iArr2[i5] = 0;
                    iArr2[i4] = 0;
                    i2--;
                }
                iArr2[i2] = 1;
                z2 = !z2;
            }
            nextUnset++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public static int a(BitArray bitArray, int[] iArr, int i, int[][] iArr2) throws NotFoundException {
        OneDReader.recordPattern(bitArray, i, iArr);
        int length = iArr2.length;
        float f2 = 0.48f;
        int i2 = -1;
        for (int i3 = 0; i3 < length; i3++) {
            float fPatternMatchVariance = OneDReader.patternMatchVariance(iArr, iArr2[i3], 0.7f);
            if (fPatternMatchVariance < f2) {
                i2 = i3;
                f2 = fPatternMatchVariance;
            }
        }
        if (i2 >= 0) {
            return i2;
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
