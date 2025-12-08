package com.google.zxing.aztec.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

/* loaded from: classes.dex */
public final class Detector {
    public static final int[] g = {3808, 476, 2107, 1799};
    public final BitMatrix a;
    public boolean b;
    public int c;
    public int d;
    public int e;
    public int f;

    public static final class a {
        public final int a;
        public final int b;

        public a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public String toString() {
            return "<" + this.a + ' ' + this.b + '>';
        }
    }

    public Detector(BitMatrix bitMatrix) {
        this.a = bitMatrix;
    }

    public static ResultPoint[] a(ResultPoint[] resultPointArr, float f, float f2) {
        float f3 = f2 / (f * 2.0f);
        float x = resultPointArr[0].getX() - resultPointArr[2].getX();
        float y = resultPointArr[0].getY() - resultPointArr[2].getY();
        float x2 = (resultPointArr[2].getX() + resultPointArr[0].getX()) / 2.0f;
        float y2 = (resultPointArr[2].getY() + resultPointArr[0].getY()) / 2.0f;
        float f4 = x * f3;
        float f5 = y * f3;
        ResultPoint resultPoint = new ResultPoint(x2 + f4, y2 + f5);
        ResultPoint resultPoint2 = new ResultPoint(x2 - f4, y2 - f5);
        float x3 = resultPointArr[1].getX() - resultPointArr[3].getX();
        float y3 = resultPointArr[1].getY() - resultPointArr[3].getY();
        float x4 = (resultPointArr[3].getX() + resultPointArr[1].getX()) / 2.0f;
        float y4 = (resultPointArr[3].getY() + resultPointArr[1].getY()) / 2.0f;
        float f6 = x3 * f3;
        float f7 = f3 * y3;
        return new ResultPoint[]{resultPoint, new ResultPoint(x4 + f6, y4 + f7), resultPoint2, new ResultPoint(x4 - f6, y4 - f7)};
    }

    public AztecDetectorResult detect() throws NotFoundException {
        return detect(false);
    }

    public AztecDetectorResult detect(boolean z) throws NotFoundException {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        ResultPoint resultPoint4;
        ResultPoint resultPoint5;
        ResultPoint resultPoint6;
        ResultPoint resultPoint7;
        ResultPoint resultPoint8;
        int i;
        int i2;
        long j;
        int i3;
        a aVar;
        int i4 = 2;
        int i5 = -1;
        int i6 = 1;
        try {
            ResultPoint[] resultPointArrDetect = new WhiteRectangleDetector(this.a).detect();
            resultPoint4 = resultPointArrDetect[0];
            resultPoint3 = resultPointArrDetect[1];
            resultPoint2 = resultPointArrDetect[2];
            resultPoint = resultPointArrDetect[3];
        } catch (NotFoundException unused) {
            int width = this.a.getWidth() / 2;
            int height = this.a.getHeight() / 2;
            int i7 = height - 7;
            int i8 = width + 7 + 1;
            int i9 = i8;
            int i10 = i7;
            while (true) {
                i10--;
                if (!a(i9, i10) || this.a.get(i9, i10)) {
                    break;
                }
                i9++;
            }
            int i11 = i9 - 1;
            int i12 = i10 + 1;
            while (a(i11, i12) && !this.a.get(i11, i12)) {
                i11++;
            }
            int i13 = i11 - 1;
            while (a(i13, i12) && !this.a.get(i13, i12)) {
                i12--;
            }
            ResultPoint resultPoint9 = new ResultPoint(i13, i12 + 1);
            int i14 = height + 7;
            int i15 = i14;
            while (true) {
                i15++;
                if (!a(i8, i15) || this.a.get(i8, i15)) {
                    break;
                }
                i8++;
            }
            int i16 = i8 - 1;
            int i17 = i15 - 1;
            while (a(i16, i17) && !this.a.get(i16, i17)) {
                i16++;
            }
            int i18 = i16 - 1;
            while (a(i18, i17) && !this.a.get(i18, i17)) {
                i17++;
            }
            ResultPoint resultPoint10 = new ResultPoint(i18, i17 - 1);
            int i19 = width - 7;
            int i20 = i19 - 1;
            while (true) {
                i14++;
                if (!a(i20, i14) || this.a.get(i20, i14)) {
                    break;
                }
                i20--;
            }
            int i21 = i20 + 1;
            int i22 = i14 - 1;
            while (a(i21, i22) && !this.a.get(i21, i22)) {
                i21--;
            }
            int i23 = i21 + 1;
            while (a(i23, i22) && !this.a.get(i23, i22)) {
                i22++;
            }
            ResultPoint resultPoint11 = new ResultPoint(i23, i22 - 1);
            do {
                i19--;
                i7--;
                if (!a(i19, i7)) {
                    break;
                }
            } while (!this.a.get(i19, i7));
            int i24 = i19 + 1;
            int i25 = i7 + 1;
            while (a(i24, i25) && !this.a.get(i24, i25)) {
                i24--;
            }
            int i26 = i24 + 1;
            while (a(i26, i25) && !this.a.get(i26, i25)) {
                i25--;
            }
            resultPoint = new ResultPoint(i26, i25 + 1);
            resultPoint2 = resultPoint11;
            resultPoint3 = resultPoint10;
            resultPoint4 = resultPoint9;
        }
        int iRound = MathUtils.round((resultPoint2.getX() + (resultPoint3.getX() + (resultPoint.getX() + resultPoint4.getX()))) / 4.0f);
        int iRound2 = MathUtils.round((resultPoint2.getY() + (resultPoint3.getY() + (resultPoint.getY() + resultPoint4.getY()))) / 4.0f);
        try {
            ResultPoint[] resultPointArrDetect2 = new WhiteRectangleDetector(this.a, 15, iRound, iRound2).detect();
            resultPoint6 = resultPointArrDetect2[0];
            resultPoint8 = resultPointArrDetect2[1];
            resultPoint7 = resultPointArrDetect2[2];
            resultPoint5 = resultPointArrDetect2[3];
        } catch (NotFoundException unused2) {
            int i27 = iRound2 - 7;
            int i28 = iRound + 7 + 1;
            int i29 = i28;
            int i30 = i27;
            while (true) {
                i30--;
                if (!a(i29, i30) || this.a.get(i29, i30)) {
                    break;
                }
                i29++;
            }
            int i31 = i29 - 1;
            int i32 = i30 + 1;
            while (a(i31, i32) && !this.a.get(i31, i32)) {
                i31++;
            }
            int i33 = i31 - 1;
            while (a(i33, i32) && !this.a.get(i33, i32)) {
                i32--;
            }
            ResultPoint resultPoint12 = new ResultPoint(i33, i32 + 1);
            int i34 = iRound2 + 7;
            int i35 = i34;
            while (true) {
                i35++;
                if (!a(i28, i35) || this.a.get(i28, i35)) {
                    break;
                }
                i28++;
            }
            int i36 = i28 - 1;
            int i37 = i35 - 1;
            while (a(i36, i37) && !this.a.get(i36, i37)) {
                i36++;
            }
            int i38 = i36 - 1;
            while (a(i38, i37) && !this.a.get(i38, i37)) {
                i37++;
            }
            ResultPoint resultPoint13 = new ResultPoint(i38, i37 - 1);
            int i39 = iRound - 7;
            int i40 = i39 - 1;
            while (true) {
                i34++;
                if (!a(i40, i34) || this.a.get(i40, i34)) {
                    break;
                }
                i40--;
            }
            int i41 = i40 + 1;
            int i42 = i34 - 1;
            while (a(i41, i42) && !this.a.get(i41, i42)) {
                i41--;
            }
            int i43 = i41 + 1;
            while (a(i43, i42) && !this.a.get(i43, i42)) {
                i42++;
            }
            ResultPoint resultPoint14 = new ResultPoint(i43, i42 - 1);
            do {
                i39--;
                i27--;
                if (!a(i39, i27)) {
                    break;
                }
            } while (!this.a.get(i39, i27));
            int i44 = i39 + 1;
            int i45 = i27 + 1;
            while (a(i44, i45) && !this.a.get(i44, i45)) {
                i44--;
            }
            int i46 = i44 + 1;
            while (a(i46, i45) && !this.a.get(i46, i45)) {
                i45--;
            }
            resultPoint5 = new ResultPoint(i46, i45 + 1);
            resultPoint6 = resultPoint12;
            resultPoint7 = resultPoint14;
            resultPoint8 = resultPoint13;
        }
        a aVar2 = new a(MathUtils.round((resultPoint7.getX() + (resultPoint8.getX() + (resultPoint5.getX() + resultPoint6.getX()))) / 4.0f), MathUtils.round((resultPoint7.getY() + (resultPoint8.getY() + (resultPoint5.getY() + resultPoint6.getY()))) / 4.0f));
        this.e = 1;
        boolean z2 = true;
        a aVar3 = aVar2;
        a aVar4 = aVar3;
        a aVar5 = aVar4;
        while (this.e < 9) {
            a aVarA = a(aVar2, z2, i6, i5);
            a aVarA2 = a(aVar3, z2, i6, i6);
            a aVarA3 = a(aVar4, z2, i5, i6);
            a aVarA4 = a(aVar5, z2, i5, i5);
            if (this.e > i4) {
                double dDistance = (MathUtils.distance(aVarA4.a, aVarA4.b, aVarA.a, aVarA.b) * this.e) / (MathUtils.distance(aVar5.a, aVar5.b, aVar2.a, aVar2.b) * (this.e + i4));
                if (dDistance < 0.75d || dDistance > 1.25d) {
                    break;
                }
                a aVar6 = new a(aVarA.a - 3, aVarA.b + 3);
                a aVar7 = new a(aVarA2.a - 3, aVarA2.b - 3);
                a aVar8 = new a(aVarA3.a + 3, aVarA3.b - 3);
                aVar = aVarA;
                a aVar9 = new a(aVarA4.a + 3, aVarA4.b + 3);
                int iA = a(aVar9, aVar6);
                if (!(iA != 0 && a(aVar6, aVar7) == iA && a(aVar7, aVar8) == iA && a(aVar8, aVar9) == iA)) {
                    break;
                }
            } else {
                aVar = aVarA;
            }
            z2 = !z2;
            this.e++;
            aVar5 = aVarA4;
            aVar3 = aVarA2;
            aVar4 = aVarA3;
            aVar2 = aVar;
            i4 = 2;
            i5 = -1;
            i6 = 1;
        }
        int i47 = this.e;
        if (i47 != 5 && i47 != 7) {
            throw NotFoundException.getNotFoundInstance();
        }
        this.b = this.e == 5;
        ResultPoint[] resultPointArrA = a(new ResultPoint[]{new ResultPoint(aVar2.a + 0.5f, aVar2.b - 0.5f), new ResultPoint(aVar3.a + 0.5f, aVar3.b + 0.5f), new ResultPoint(aVar4.a - 0.5f, aVar4.b + 0.5f), new ResultPoint(aVar5.a - 0.5f, aVar5.b - 0.5f)}, r2 - 3, this.e * 2);
        if (z) {
            ResultPoint resultPoint15 = resultPointArrA[0];
            resultPointArrA[0] = resultPointArrA[2];
            resultPointArrA[2] = resultPoint15;
        }
        if (!a(resultPointArrA[0]) || !a(resultPointArrA[1]) || !a(resultPointArrA[2]) || !a(resultPointArrA[3])) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i48 = this.e * 2;
        int[] iArr = {a(resultPointArrA[0], resultPointArrA[1], i48), a(resultPointArrA[1], resultPointArrA[2], i48), a(resultPointArrA[2], resultPointArrA[3], i48), a(resultPointArrA[3], resultPointArrA[0], i48)};
        int i49 = 0;
        for (int i50 = 0; i50 < 4; i50++) {
            int i51 = iArr[i50];
            i49 = (i49 << 3) + ((i51 >> (i48 - 2)) << 1) + (i51 & 1);
        }
        int i52 = ((i49 & 1) << 11) + (i49 >> 1);
        for (int i53 = 0; i53 < 4; i53++) {
            if (Integer.bitCount(g[i53] ^ i52) <= 2) {
                this.f = i53;
                long j2 = 0;
                for (int i54 = 0; i54 < 4; i54++) {
                    int i55 = iArr[(this.f + i54) % 4];
                    if (this.b) {
                        j = j2 << 7;
                        i3 = (i55 >> 1) & 127;
                    } else {
                        j = j2 << 10;
                        i3 = ((i55 >> 2) & 992) + ((i55 >> 1) & 31);
                    }
                    j2 = j + i3;
                }
                if (this.b) {
                    i = 7;
                    i2 = 2;
                } else {
                    i = 10;
                    i2 = 4;
                }
                int i56 = i - i2;
                int[] iArr2 = new int[i];
                while (true) {
                    i--;
                    if (i < 0) {
                        try {
                            break;
                        } catch (ReedSolomonException unused3) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                    }
                    iArr2[i] = ((int) j2) & 15;
                    j2 >>= 4;
                }
                new ReedSolomonDecoder(GenericGF.AZTEC_PARAM).decode(iArr2, i56);
                int i57 = 0;
                for (int i58 = 0; i58 < i2; i58++) {
                    i57 = (i57 << 4) + iArr2[i58];
                }
                if (this.b) {
                    this.c = (i57 >> 6) + 1;
                    this.d = (i57 & 63) + 1;
                } else {
                    this.c = (i57 >> 11) + 1;
                    this.d = (i57 & 2047) + 1;
                }
                BitMatrix bitMatrix = this.a;
                int i59 = this.f;
                ResultPoint resultPoint16 = resultPointArrA[i59 % 4];
                ResultPoint resultPoint17 = resultPointArrA[(i59 + 1) % 4];
                ResultPoint resultPoint18 = resultPointArrA[(i59 + 2) % 4];
                ResultPoint resultPoint19 = resultPointArrA[(i59 + 3) % 4];
                GridSampler gridSampler = GridSampler.getInstance();
                int iA2 = a();
                float f = iA2 / 2.0f;
                float f2 = this.e;
                float f3 = f - f2;
                float f4 = f + f2;
                return new AztecDetectorResult(gridSampler.sampleGrid(bitMatrix, iA2, iA2, f3, f3, f4, f3, f4, f4, f3, f4, resultPoint16.getX(), resultPoint16.getY(), resultPoint17.getX(), resultPoint17.getY(), resultPoint18.getX(), resultPoint18.getY(), resultPoint19.getX(), resultPoint19.getY()), a(resultPointArrA, this.e * 2, a()), this.b, this.d, this.c);
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final boolean a(int i, int i2) {
        return i >= 0 && i < this.a.getWidth() && i2 > 0 && i2 < this.a.getHeight();
    }

    public final boolean a(ResultPoint resultPoint) {
        return a(MathUtils.round(resultPoint.getX()), MathUtils.round(resultPoint.getY()));
    }

    public final int a(ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
        float fDistance = MathUtils.distance(resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY());
        float f = fDistance / i;
        float x = resultPoint.getX();
        float y = resultPoint.getY();
        float x2 = ((resultPoint2.getX() - resultPoint.getX()) * f) / fDistance;
        float y2 = ((resultPoint2.getY() - resultPoint.getY()) * f) / fDistance;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            float f2 = i3;
            if (this.a.get(MathUtils.round((f2 * x2) + x), MathUtils.round((f2 * y2) + y))) {
                i2 |= 1 << ((i - i3) - 1);
            }
        }
        return i2;
    }

    public final int a() {
        if (this.b) {
            return (this.c * 4) + 11;
        }
        int i = this.c;
        if (i <= 4) {
            return (i * 4) + 15;
        }
        return ((((i - 4) / 8) + 1) * 2) + (i * 4) + 15;
    }

    public final int a(a aVar, a aVar2) {
        float fDistance = MathUtils.distance(aVar.a, aVar.b, aVar2.a, aVar2.b);
        int i = aVar2.a;
        int i2 = aVar.a;
        float f = (i - i2) / fDistance;
        int i3 = aVar2.b;
        int i4 = aVar.b;
        float f2 = (i3 - i4) / fDistance;
        float f3 = i2;
        float f4 = i4;
        boolean z = this.a.get(i2, i4);
        int iCeil = (int) Math.ceil(fDistance);
        int i5 = 0;
        for (int i6 = 0; i6 < iCeil; i6++) {
            f3 += f;
            f4 += f2;
            if (this.a.get(MathUtils.round(f3), MathUtils.round(f4)) != z) {
                i5++;
            }
        }
        float f5 = i5 / fDistance;
        if (f5 <= 0.1f || f5 >= 0.9f) {
            return (f5 <= 0.1f) == z ? 1 : -1;
        }
        return 0;
    }

    public final a a(a aVar, boolean z, int i, int i2) {
        int i3 = aVar.a + i;
        int i4 = aVar.b;
        while (true) {
            i4 += i2;
            if (!a(i3, i4) || this.a.get(i3, i4) != z) {
                break;
            }
            i3 += i;
        }
        int i5 = i3 - i;
        int i6 = i4 - i2;
        while (a(i5, i6) && this.a.get(i5, i6) == z) {
            i5 += i;
        }
        int i7 = i5 - i;
        while (a(i7, i6) && this.a.get(i7, i6) == z) {
            i6 += i2;
        }
        return new a(i7, i6 - i2);
    }
}
