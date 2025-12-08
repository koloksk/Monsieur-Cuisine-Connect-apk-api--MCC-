package defpackage;

import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.pdf417.PDF417Common;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public final class gc {
    public static final float[][] a = (float[][]) Array.newInstance((Class<?>) float.class, PDF417Common.SYMBOL_TABLE.length, 8);

    static {
        int i;
        int i2 = 0;
        while (true) {
            int[] iArr = PDF417Common.SYMBOL_TABLE;
            if (i2 >= iArr.length) {
                return;
            }
            int i3 = iArr[i2];
            int i4 = i3 & 1;
            int i5 = 0;
            while (i5 < 8) {
                float f = 0.0f;
                while (true) {
                    i = i3 & 1;
                    if (i == i4) {
                        f += 1.0f;
                        i3 >>= 1;
                    }
                }
                a[i2][(8 - i5) - 1] = f / 17.0f;
                i5++;
                i4 = i;
            }
            i2++;
        }
    }

    public static int a(int[] iArr) {
        float fSum = MathUtils.sum(iArr);
        int[] iArr2 = new int[8];
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 17; i3++) {
            if (iArr[i] + i2 <= ((i3 * fSum) / 17.0f) + (fSum / 34.0f)) {
                i2 += iArr[i];
                i++;
            }
            iArr2[i] = iArr2[i] + 1;
        }
        long j = 0;
        for (int i4 = 0; i4 < 8; i4++) {
            for (int i5 = 0; i5 < iArr2[i4]; i5++) {
                j = (j << 1) | (i4 % 2 == 0 ? 1 : 0);
            }
        }
        int i6 = (int) j;
        int i7 = -1;
        if (PDF417Common.getCodeword(i6) == -1) {
            i6 = -1;
        }
        if (i6 != -1) {
            return i6;
        }
        int iSum = MathUtils.sum(iArr);
        float[] fArr = new float[8];
        for (int i8 = 0; i8 < 8; i8++) {
            fArr[i8] = iArr[i8] / iSum;
        }
        float f = Float.MAX_VALUE;
        int i9 = 0;
        while (true) {
            float[][] fArr2 = a;
            if (i9 >= fArr2.length) {
                return i7;
            }
            float f2 = 0.0f;
            float[] fArr3 = fArr2[i9];
            for (int i10 = 0; i10 < 8; i10++) {
                float f3 = fArr3[i10] - fArr[i10];
                f2 += f3 * f3;
                if (f2 >= f) {
                    break;
                }
            }
            if (f2 < f) {
                i7 = PDF417Common.SYMBOL_TABLE[i9];
                f = f2;
            }
            i9++;
        }
    }
}
