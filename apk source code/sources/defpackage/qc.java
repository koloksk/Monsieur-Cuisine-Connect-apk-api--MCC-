package defpackage;

import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.AlignmentPattern;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class qc {
    public final BitMatrix a;
    public final int c;
    public final int d;
    public final int e;
    public final int f;
    public final float g;
    public final ResultPointCallback i;
    public final List<AlignmentPattern> b = new ArrayList(5);
    public final int[] h = new int[3];

    public qc(BitMatrix bitMatrix, int i, int i2, int i3, int i4, float f, ResultPointCallback resultPointCallback) {
        this.a = bitMatrix;
        this.c = i;
        this.d = i2;
        this.e = i3;
        this.f = i4;
        this.g = f;
        this.i = resultPointCallback;
    }

    public static float a(int[] iArr, int i) {
        return (i - iArr[2]) - (iArr[1] / 2.0f);
    }

    public final boolean a(int[] iArr) {
        float f = this.g;
        float f2 = f / 2.0f;
        for (int i = 0; i < 3; i++) {
            if (Math.abs(f - iArr[i]) >= f2) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x0101  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.qrcode.detector.AlignmentPattern a(int[] r13, int r14, int r15) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.qc.a(int[], int, int):com.google.zxing.qrcode.detector.AlignmentPattern");
    }
}
