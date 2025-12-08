package com.google.zxing.maxicode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import defpackage.wa;
import defpackage.xa;
import java.text.DecimalFormat;
import java.util.Map;

/* loaded from: classes.dex */
public final class Decoder {
    public final ReedSolomonDecoder a = new ReedSolomonDecoder(GenericGF.MAXICODE_FIELD_64);

    public final void a(byte[] bArr, int i, int i2, int i3, int i4) throws ChecksumException {
        int i5 = i2 + i3;
        int i6 = i4 == 0 ? 1 : 2;
        int[] iArr = new int[i5 / i6];
        for (int i7 = 0; i7 < i5; i7++) {
            if (i4 == 0 || i7 % 2 == i4 - 1) {
                iArr[i7 / i6] = bArr[i7 + i] & 255;
            }
        }
        try {
            this.a.decode(iArr, i3 / i6);
            for (int i8 = 0; i8 < i2; i8++) {
                if (i4 == 0 || i8 % 2 == i4 - 1) {
                    bArr[i8 + i] = (byte) iArr[i8 / i6];
                }
            }
        } catch (ReedSolomonException unused) {
            throw ChecksumException.getChecksumInstance();
        }
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return decode(bitMatrix, null);
    }

    public DecoderResult decode(BitMatrix bitMatrix, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        byte[] bArr;
        wa waVar = new wa(bitMatrix);
        byte[] bArr2 = new byte[144];
        int height = waVar.a.getHeight();
        int width = waVar.a.getWidth();
        for (int i = 0; i < height; i++) {
            int[] iArr = wa.b[i];
            for (int i2 = 0; i2 < width; i2++) {
                int i3 = iArr[i2];
                if (i3 >= 0 && waVar.a.get(i2, i)) {
                    int i4 = i3 / 6;
                    bArr2[i4] = (byte) (((byte) (1 << (5 - (i3 % 6)))) | bArr2[i4]);
                }
            }
        }
        a(bArr2, 0, 10, 10, 0);
        int i5 = bArr2[0] & 15;
        if (i5 == 2 || i5 == 3 || i5 == 4) {
            a(bArr2, 20, 84, 40, 1);
            a(bArr2, 20, 84, 40, 2);
            bArr = new byte[94];
        } else {
            if (i5 != 5) {
                throw FormatException.getFormatInstance();
            }
            a(bArr2, 20, 68, 56, 1);
            a(bArr2, 20, 68, 56, 2);
            bArr = new byte[78];
        }
        System.arraycopy(bArr2, 0, bArr, 0, 10);
        System.arraycopy(bArr2, 20, bArr, 10, bArr.length - 10);
        StringBuilder sb = new StringBuilder(144);
        if (i5 == 2 || i5 == 3) {
            String strValueOf = i5 == 2 ? new DecimalFormat("0000000000".substring(0, xa.a(bArr, new byte[]{39, 40, 41, 42, 31, 32}))).format(xa.a(bArr, new byte[]{33, 34, 35, 36, 25, 26, 27, 28, 29, 30, 19, 20, 21, 22, 23, 24, 13, 14, 15, 16, 17, 18, 7, 8, 9, 10, 11, 12, 1, 2})) : String.valueOf(new char[]{xa.a[0].charAt(xa.a(bArr, new byte[]{39, 40, 41, 42, 31, 32})), xa.a[0].charAt(xa.a(bArr, new byte[]{33, 34, 35, 36, 25, 26})), xa.a[0].charAt(xa.a(bArr, new byte[]{27, 28, 29, 30, 19, 20})), xa.a[0].charAt(xa.a(bArr, new byte[]{21, 22, 23, 24, 13, 14})), xa.a[0].charAt(xa.a(bArr, new byte[]{15, 16, 17, 18, 7, 8})), xa.a[0].charAt(xa.a(bArr, new byte[]{9, 10, 11, 12, 1, 2}))});
            DecimalFormat decimalFormat = new DecimalFormat("000");
            String str = decimalFormat.format(xa.a(bArr, new byte[]{53, 54, 43, 44, 45, 46, 47, 48, 37, 38}));
            String str2 = decimalFormat.format(xa.a(bArr, new byte[]{55, 56, 57, 58, 59, 60, 49, 50, 51, 52}));
            sb.append(xa.a(bArr, 10, 84));
            if (sb.toString().startsWith("[)>\u001e01\u001d")) {
                sb.insert(9, strValueOf + (char) 29 + str + (char) 29 + str2 + (char) 29);
            } else {
                sb.insert(0, strValueOf + (char) 29 + str + (char) 29 + str2 + (char) 29);
            }
        } else if (i5 == 4) {
            sb.append(xa.a(bArr, 1, 93));
        } else if (i5 == 5) {
            sb.append(xa.a(bArr, 1, 77));
        }
        return new DecoderResult(bArr, sb.toString(), null, String.valueOf(i5));
    }
}
