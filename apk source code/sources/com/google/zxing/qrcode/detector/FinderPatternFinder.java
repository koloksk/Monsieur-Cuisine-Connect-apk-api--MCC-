package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class FinderPatternFinder {
    public static final int MAX_MODULES = 57;
    public static final int MIN_SKIP = 3;
    public final BitMatrix a;
    public final List<FinderPattern> b;
    public boolean c;
    public final int[] d;
    public final ResultPointCallback e;

    public static final class b implements Serializable, Comparator<FinderPattern> {
        public final float a;

        @Override // java.util.Comparator
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            FinderPattern finderPattern3 = finderPattern;
            FinderPattern finderPattern4 = finderPattern2;
            int i = finderPattern4.d;
            int i2 = finderPattern3.d;
            if (i != i2) {
                return i - i2;
            }
            float fAbs = Math.abs(finderPattern4.getEstimatedModuleSize() - this.a);
            float fAbs2 = Math.abs(finderPattern3.getEstimatedModuleSize() - this.a);
            if (fAbs < fAbs2) {
                return 1;
            }
            return fAbs == fAbs2 ? 0 : -1;
        }
    }

    public static final class c implements Serializable, Comparator<FinderPattern> {
        public final float a;

        @Override // java.util.Comparator
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            float fAbs = Math.abs(finderPattern2.getEstimatedModuleSize() - this.a);
            float fAbs2 = Math.abs(finderPattern.getEstimatedModuleSize() - this.a);
            if (fAbs < fAbs2) {
                return -1;
            }
            return fAbs == fAbs2 ? 0 : 1;
        }
    }

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this(bitMatrix, null);
    }

    public static float a(int[] iArr, int i) {
        return ((i - iArr[4]) - iArr[3]) - (iArr[2] / 2.0f);
    }

    public static boolean foundPatternCross(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            int i3 = iArr[i2];
            if (i3 == 0) {
                return false;
            }
            i += i3;
        }
        if (i < 7) {
            return false;
        }
        float f = i / 7.0f;
        float f2 = f / 2.0f;
        return Math.abs(f - ((float) iArr[0])) < f2 && Math.abs(f - ((float) iArr[1])) < f2 && Math.abs((f * 3.0f) - ((float) iArr[2])) < 3.0f * f2 && Math.abs(f - ((float) iArr[3])) < f2 && Math.abs(f - ((float) iArr[4])) < f2;
    }

    public final boolean b() {
        int size = this.b.size();
        float fAbs = 0.0f;
        float estimatedModuleSize = 0.0f;
        int i = 0;
        for (FinderPattern finderPattern : this.b) {
            if (finderPattern.d >= 2) {
                i++;
                estimatedModuleSize += finderPattern.getEstimatedModuleSize();
            }
        }
        if (i < 3) {
            return false;
        }
        float f = estimatedModuleSize / size;
        Iterator<FinderPattern> it = this.b.iterator();
        while (it.hasNext()) {
            fAbs += Math.abs(it.next().getEstimatedModuleSize() - f);
        }
        return fAbs <= estimatedModuleSize * 0.05f;
    }

    public final BitMatrix getImage() {
        return this.a;
    }

    public final List<FinderPattern> getPossibleCenters() {
        return this.b;
    }

    /* JADX WARN: Code restructure failed: missing block: B:176:0x0276, code lost:
    
        r6 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x029f, code lost:
    
        r2 = 4;
     */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02ca A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0342 A[LOOP:8: B:206:0x02d2->B:220:0x0342, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0317 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handlePossibleCenter(int[] r18, int r19, int r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 860
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.detector.FinderPatternFinder.handlePossibleCenter(int[], int, int, boolean):boolean");
    }

    public FinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        this.a = bitMatrix;
        this.b = new ArrayList();
        this.d = new int[5];
        this.e = resultPointCallback;
    }

    public final int[] a() {
        int[] iArr = this.d;
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        iArr[4] = 0;
        return iArr;
    }
}
