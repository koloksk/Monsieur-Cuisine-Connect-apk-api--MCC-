package com.google.zxing.qrcode.detector;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import defpackage.qc;

/* loaded from: classes.dex */
public class Detector {
    public final BitMatrix a;
    public ResultPointCallback b;

    public Detector(BitMatrix bitMatrix) {
        this.a = bitMatrix;
    }

    public final float a(ResultPoint resultPoint, ResultPoint resultPoint2) {
        float fB = b((int) resultPoint.getX(), (int) resultPoint.getY(), (int) resultPoint2.getX(), (int) resultPoint2.getY());
        float fB2 = b((int) resultPoint2.getX(), (int) resultPoint2.getY(), (int) resultPoint.getX(), (int) resultPoint.getY());
        return Float.isNaN(fB) ? fB2 / 7.0f : Float.isNaN(fB2) ? fB / 7.0f : (fB + fB2) / 14.0f;
    }

    public final float b(int i, int i2, int i3, int i4) {
        float width;
        float height;
        float fA = a(i, i2, i3, i4);
        int width2 = i - (i3 - i);
        int height2 = 0;
        if (width2 < 0) {
            width = i / (i - width2);
            width2 = 0;
        } else if (width2 >= this.a.getWidth()) {
            width = ((this.a.getWidth() - 1) - i) / (width2 - i);
            width2 = this.a.getWidth() - 1;
        } else {
            width = 1.0f;
        }
        float f = i2;
        int i5 = (int) (f - ((i4 - i2) * width));
        if (i5 < 0) {
            height = f / (i2 - i5);
        } else if (i5 >= this.a.getHeight()) {
            height = ((this.a.getHeight() - 1) - i2) / (i5 - i2);
            height2 = this.a.getHeight() - 1;
        } else {
            height2 = i5;
            height = 1.0f;
        }
        return (a(i, i2, (int) (((width2 - i) * height) + i), height2) + fA) - 1.0f;
    }

    public final float calculateModuleSize(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        return (a(resultPoint, resultPoint3) + a(resultPoint, resultPoint2)) / 2.0f;
    }

    public DetectorResult detect() throws NotFoundException, FormatException {
        return detect(null);
    }

    public final AlignmentPattern findAlignmentInRegion(float f, int i, int i2, float f2) throws NotFoundException {
        AlignmentPattern alignmentPatternA;
        AlignmentPattern alignmentPatternA2;
        int i3 = (int) (f2 * f);
        int iMax = Math.max(0, i - i3);
        int iMin = Math.min(this.a.getWidth() - 1, i + i3) - iMax;
        float f3 = 3.0f * f;
        if (iMin < f3) {
            throw NotFoundException.getNotFoundInstance();
        }
        int iMax2 = Math.max(0, i2 - i3);
        int iMin2 = Math.min(this.a.getHeight() - 1, i2 + i3) - iMax2;
        if (iMin2 < f3) {
            throw NotFoundException.getNotFoundInstance();
        }
        qc qcVar = new qc(this.a, iMax, iMax2, iMin, iMin2, f, this.b);
        int i4 = qcVar.c;
        int i5 = qcVar.f;
        int i6 = qcVar.e + i4;
        int i7 = (i5 / 2) + qcVar.d;
        int[] iArr = new int[3];
        for (int i8 = 0; i8 < i5; i8++) {
            int i9 = ((i8 & 1) == 0 ? (i8 + 1) / 2 : -((i8 + 1) / 2)) + i7;
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            int i10 = i4;
            while (i10 < i6 && !qcVar.a.get(i10, i9)) {
                i10++;
            }
            int i11 = 0;
            while (i10 < i6) {
                if (!qcVar.a.get(i10, i9)) {
                    if (i11 == 1) {
                        i11++;
                    }
                    iArr[i11] = iArr[i11] + 1;
                } else if (i11 == 1) {
                    iArr[1] = iArr[1] + 1;
                } else if (i11 != 2) {
                    i11++;
                    iArr[i11] = iArr[i11] + 1;
                } else {
                    if (qcVar.a(iArr) && (alignmentPatternA2 = qcVar.a(iArr, i9, i10)) != null) {
                        return alignmentPatternA2;
                    }
                    iArr[0] = iArr[2];
                    iArr[1] = 1;
                    iArr[2] = 0;
                    i11 = 1;
                }
                i10++;
            }
            if (qcVar.a(iArr) && (alignmentPatternA = qcVar.a(iArr, i9, i6)) != null) {
                return alignmentPatternA;
            }
        }
        if (qcVar.b.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return qcVar.b.get(0);
    }

    public final BitMatrix getImage() {
        return this.a;
    }

    public final ResultPointCallback getResultPointCallback() {
        return this.b;
    }

    public final DetectorResult processFinderPatternInfo(FinderPatternInfo finderPatternInfo) throws NotFoundException, FormatException {
        float x;
        float y;
        float f;
        FinderPattern topLeft = finderPatternInfo.getTopLeft();
        FinderPattern topRight = finderPatternInfo.getTopRight();
        FinderPattern bottomLeft = finderPatternInfo.getBottomLeft();
        float fCalculateModuleSize = calculateModuleSize(topLeft, topRight, bottomLeft);
        if (fCalculateModuleSize < 1.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int iRound = ((MathUtils.round(ResultPoint.distance(topLeft, bottomLeft) / fCalculateModuleSize) + MathUtils.round(ResultPoint.distance(topLeft, topRight) / fCalculateModuleSize)) / 2) + 7;
        int i = iRound & 3;
        if (i == 0) {
            iRound++;
        } else if (i == 2) {
            iRound--;
        } else if (i == 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        Version provisionalVersionForDimension = Version.getProvisionalVersionForDimension(iRound);
        int dimensionForVersion = provisionalVersionForDimension.getDimensionForVersion() - 7;
        AlignmentPattern alignmentPatternFindAlignmentInRegion = null;
        if (provisionalVersionForDimension.getAlignmentPatternCenters().length > 0) {
            float x2 = bottomLeft.getX() + (topRight.getX() - topLeft.getX());
            float y2 = bottomLeft.getY() + (topRight.getY() - topLeft.getY());
            float f2 = 1.0f - (3.0f / dimensionForVersion);
            int x3 = (int) (((x2 - topLeft.getX()) * f2) + topLeft.getX());
            int y3 = (int) (((y2 - topLeft.getY()) * f2) + topLeft.getY());
            for (int i2 = 4; i2 <= 16; i2 <<= 1) {
                try {
                    alignmentPatternFindAlignmentInRegion = findAlignmentInRegion(fCalculateModuleSize, x3, y3, i2);
                    break;
                } catch (NotFoundException unused) {
                }
            }
        }
        float f3 = iRound - 3.5f;
        if (alignmentPatternFindAlignmentInRegion != null) {
            x = alignmentPatternFindAlignmentInRegion.getX();
            y = alignmentPatternFindAlignmentInRegion.getY();
            f = f3 - 3.0f;
        } else {
            x = bottomLeft.getX() + (topRight.getX() - topLeft.getX());
            y = bottomLeft.getY() + (topRight.getY() - topLeft.getY());
            f = f3;
        }
        return new DetectorResult(GridSampler.getInstance().sampleGrid(this.a, iRound, iRound, PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f3, 3.5f, f, f, 3.5f, f3, topLeft.getX(), topLeft.getY(), topRight.getX(), topRight.getY(), x, y, bottomLeft.getX(), bottomLeft.getY())), alignmentPatternFindAlignmentInRegion == null ? new ResultPoint[]{bottomLeft, topLeft, topRight} : new ResultPoint[]{bottomLeft, topLeft, topRight, alignmentPatternFindAlignmentInRegion});
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.common.DetectorResult detect(java.util.Map<com.google.zxing.DecodeHintType, ?> r17) throws com.google.zxing.NotFoundException, com.google.zxing.FormatException {
        /*
            Method dump skipped, instructions count: 572
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.detector.Detector.detect(java.util.Map):com.google.zxing.common.DetectorResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0087, code lost:
    
        if (r15 != r0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x008f, code lost:
    
        return com.google.zxing.common.detector.MathUtils.distance(r19, r6, r1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0090, code lost:
    
        return Float.NaN;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float a(int r18, int r19, int r20, int r21) {
        /*
            r17 = this;
            int r0 = r21 - r19
            int r0 = java.lang.Math.abs(r0)
            int r1 = r20 - r18
            int r1 = java.lang.Math.abs(r1)
            r3 = 1
            if (r0 <= r1) goto L11
            r0 = r3
            goto L12
        L11:
            r0 = 0
        L12:
            if (r0 == 0) goto L1d
            r4 = r18
            r1 = r19
            r6 = r20
            r5 = r21
            goto L25
        L1d:
            r1 = r18
            r4 = r19
            r5 = r20
            r6 = r21
        L25:
            int r7 = r5 - r1
            int r7 = java.lang.Math.abs(r7)
            int r8 = r6 - r4
            int r8 = java.lang.Math.abs(r8)
            int r9 = -r7
            r10 = 2
            int r9 = r9 / r10
            r11 = -1
            if (r1 >= r5) goto L39
            r12 = r3
            goto L3a
        L39:
            r12 = r11
        L3a:
            if (r4 >= r6) goto L3d
            r11 = r3
        L3d:
            int r5 = r5 + r12
            r13 = r1
            r14 = r4
            r15 = 0
        L41:
            if (r13 == r5) goto L82
            if (r0 == 0) goto L47
            r2 = r14
            goto L48
        L47:
            r2 = r13
        L48:
            if (r0 == 0) goto L4c
            r10 = r13
            goto L4d
        L4c:
            r10 = r14
        L4d:
            if (r15 != r3) goto L57
            r16 = r0
            r0 = r3
            r19 = r5
            r3 = r17
            goto L5e
        L57:
            r3 = r17
            r16 = r0
            r19 = r5
            r0 = 0
        L5e:
            com.google.zxing.common.BitMatrix r5 = r3.a
            boolean r2 = r5.get(r2, r10)
            if (r0 != r2) goto L70
            r0 = 2
            if (r15 != r0) goto L6e
            float r0 = com.google.zxing.common.detector.MathUtils.distance(r13, r14, r1, r4)
            return r0
        L6e:
            int r15 = r15 + 1
        L70:
            int r9 = r9 + r8
            if (r9 <= 0) goto L7a
            if (r14 == r6) goto L78
            int r14 = r14 + r11
            int r9 = r9 - r7
            goto L7a
        L78:
            r0 = 2
            goto L87
        L7a:
            int r13 = r13 + r12
            r5 = r19
            r0 = r16
            r3 = 1
            r10 = 2
            goto L41
        L82:
            r3 = r17
            r19 = r5
            r0 = r10
        L87:
            if (r15 != r0) goto L90
            r5 = r19
            float r0 = com.google.zxing.common.detector.MathUtils.distance(r5, r6, r1, r4)
            return r0
        L90:
            r0 = 2143289344(0x7fc00000, float:NaN)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.detector.Detector.a(int, int, int, int):float");
    }
}
