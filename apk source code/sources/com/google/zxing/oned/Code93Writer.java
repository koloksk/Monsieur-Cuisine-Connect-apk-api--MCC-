package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import defpackage.g9;
import java.util.Map;

/* loaded from: classes.dex */
public class Code93Writer extends OneDimensionalCodeWriter {
    public static void a(int i, int[] iArr) {
        for (int i2 = 0; i2 < 9; i2++) {
            int i3 = 1;
            if (((1 << (8 - i2)) & i) == 0) {
                i3 = 0;
            }
            iArr[i2] = i3;
        }
    }

    public static int appendPattern(boolean[] zArr, int i, int[] iArr, boolean z) {
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i + 1;
            zArr[i] = iArr[i2] != 0;
            i2++;
            i = i3;
        }
        return 9;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_93) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_93, but got " + barcodeFormat);
    }

    public static int a(String str, int i) {
        int iIndexOf = 0;
        int i2 = 1;
        for (int length = str.length() - 1; length >= 0; length--) {
            iIndexOf += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(length)) * i2;
            i2++;
            if (i2 > i) {
                i2 = 1;
            }
        }
        return iIndexOf % 47;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        if (length <= 80) {
            int[] iArr = new int[9];
            boolean[] zArr = new boolean[((str.length() + 2 + 2) * 9) + 1];
            a(Code93Reader.d[47], iArr);
            int iAppendPattern = appendPattern(zArr, 0, iArr, true);
            for (int i = 0; i < length; i++) {
                a(Code93Reader.d["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(i))], iArr);
                iAppendPattern += appendPattern(zArr, iAppendPattern, iArr, true);
            }
            int iA = a(str, 20);
            a(Code93Reader.d[iA], iArr);
            int iAppendPattern2 = appendPattern(zArr, iAppendPattern, iArr, true) + iAppendPattern;
            StringBuilder sbA = g9.a(str);
            sbA.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".charAt(iA));
            a(Code93Reader.d[a(sbA.toString(), 15)], iArr);
            int iAppendPattern3 = appendPattern(zArr, iAppendPattern2, iArr, true) + iAppendPattern2;
            a(Code93Reader.d[47], iArr);
            zArr[appendPattern(zArr, iAppendPattern3, iArr, true) + iAppendPattern3] = true;
            return zArr;
        }
        throw new IllegalArgumentException(g9.a("Requested contents should be less than 80 digits long, but got ", length));
    }
}
