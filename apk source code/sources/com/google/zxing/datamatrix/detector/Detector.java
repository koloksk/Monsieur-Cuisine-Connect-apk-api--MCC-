package com.google.zxing.datamatrix.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/* loaded from: classes.dex */
public final class Detector {
    public final BitMatrix a;
    public final WhiteRectangleDetector b;

    public static final class b {
        public final ResultPoint a;
        public final ResultPoint b;
        public final int c;

        public /* synthetic */ b(ResultPoint resultPoint, ResultPoint resultPoint2, int i, a aVar) {
            this.a = resultPoint;
            this.b = resultPoint2;
            this.c = i;
        }

        public String toString() {
            return this.a + "/" + this.b + '/' + this.c;
        }
    }

    public static final class c implements Serializable, Comparator<b> {
        public /* synthetic */ c(a aVar) {
        }

        @Override // java.util.Comparator
        public int compare(b bVar, b bVar2) {
            return bVar.c - bVar2.c;
        }
    }

    public Detector(BitMatrix bitMatrix) throws NotFoundException {
        this.a = bitMatrix;
        this.b = new WhiteRectangleDetector(bitMatrix);
    }

    public static int b(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2));
    }

    public final boolean a(ResultPoint resultPoint) {
        return resultPoint.getX() >= 0.0f && resultPoint.getX() < ((float) this.a.getWidth()) && resultPoint.getY() > 0.0f && resultPoint.getY() < ((float) this.a.getHeight());
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0297  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.common.DetectorResult detect() throws com.google.zxing.NotFoundException {
        /*
            Method dump skipped, instructions count: 704
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.detector.Detector.detect():com.google.zxing.common.DetectorResult");
    }

    public static void a(Map<ResultPoint, Integer> map, ResultPoint resultPoint) {
        Integer num = map.get(resultPoint);
        map.put(resultPoint, Integer.valueOf(num != null ? 1 + num.intValue() : 1));
    }

    public static BitMatrix a(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) throws NotFoundException {
        float f = i - 0.5f;
        float f2 = i2 - 0.5f;
        return GridSampler.getInstance().sampleGrid(bitMatrix, i, i2, 0.5f, 0.5f, f, 0.5f, f, f2, 0.5f, f2, resultPoint.getX(), resultPoint.getY(), resultPoint4.getX(), resultPoint4.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    public final b a(ResultPoint resultPoint, ResultPoint resultPoint2) {
        int x = (int) resultPoint.getX();
        int y = (int) resultPoint.getY();
        int x2 = (int) resultPoint2.getX();
        int y2 = (int) resultPoint2.getY();
        int i = 0;
        boolean z = Math.abs(y2 - y) > Math.abs(x2 - x);
        if (z) {
            y = x;
            x = y;
            y2 = x2;
            x2 = y2;
        }
        int iAbs = Math.abs(x2 - x);
        int iAbs2 = Math.abs(y2 - y);
        int i2 = (-iAbs) / 2;
        int i3 = y < y2 ? 1 : -1;
        int i4 = x >= x2 ? -1 : 1;
        boolean z2 = this.a.get(z ? y : x, z ? x : y);
        while (x != x2) {
            boolean z3 = this.a.get(z ? y : x, z ? x : y);
            if (z3 != z2) {
                i++;
                z2 = z3;
            }
            i2 += iAbs2;
            if (i2 > 0) {
                if (y == y2) {
                    break;
                }
                y += i3;
                i2 -= iAbs;
            }
            x += i4;
        }
        return new b(resultPoint, resultPoint2, i, null);
    }
}
