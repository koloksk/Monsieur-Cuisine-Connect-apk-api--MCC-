package defpackage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;
import java.util.EnumMap;

/* loaded from: classes.dex */
public final class cb {
    public static final int[] c = {1, 1, 2};
    public final ab a = new ab();
    public final bb b = new bb();

    public Result a(int i, BitArray bitArray, int i2) throws NotFoundException {
        EnumMap enumMap;
        int[] iArrA = UPCEANReader.a(bitArray, i2, false, c);
        try {
            return this.b.a(i, bitArray, iArrA);
        } catch (ReaderException unused) {
            ab abVar = this.a;
            StringBuilder sb = abVar.b;
            sb.setLength(0);
            int[] iArr = abVar.a;
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            iArr[3] = 0;
            int size = bitArray.getSize();
            int nextUnset = iArrA[1];
            int i3 = 0;
            for (int i4 = 0; i4 < 2 && nextUnset < size; i4++) {
                int iA = UPCEANReader.a(bitArray, iArr, nextUnset, UPCEANReader.h);
                sb.append((char) ((iA % 10) + 48));
                for (int i5 : iArr) {
                    nextUnset += i5;
                }
                if (iA >= 10) {
                    i3 |= 1 << (1 - i4);
                }
                if (i4 != 1) {
                    nextUnset = bitArray.getNextUnset(bitArray.getNextSet(nextUnset));
                }
            }
            if (sb.length() != 2) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (Integer.parseInt(sb.toString()) % 4 != i3) {
                throw NotFoundException.getNotFoundInstance();
            }
            String string = sb.toString();
            if (string.length() != 2) {
                enumMap = null;
            } else {
                enumMap = new EnumMap(ResultMetadataType.class);
                enumMap.put((EnumMap) ResultMetadataType.ISSUE_NUMBER, (ResultMetadataType) Integer.valueOf(string));
            }
            float f = i;
            Result result = new Result(string, null, new ResultPoint[]{new ResultPoint((iArrA[0] + iArrA[1]) / 2.0f, f), new ResultPoint(nextUnset, f)}, BarcodeFormat.UPC_EAN_EXTENSION);
            if (enumMap != null) {
                result.putAllMetadata(enumMap);
            }
            return result;
        }
    }
}
