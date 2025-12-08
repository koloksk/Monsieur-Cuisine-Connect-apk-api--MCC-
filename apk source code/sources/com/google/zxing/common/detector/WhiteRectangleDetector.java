package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes.dex */
public final class WhiteRectangleDetector {
    public final BitMatrix a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    public final int f;
    public final int g;

    public WhiteRectangleDetector(BitMatrix bitMatrix) throws NotFoundException {
        this(bitMatrix, 10, bitMatrix.getWidth() / 2, bitMatrix.getHeight() / 2);
    }

    public final ResultPoint a(float f, float f2, float f3, float f4) {
        int iRound = MathUtils.round(MathUtils.distance(f, f2, f3, f4));
        float f5 = iRound;
        float f6 = (f3 - f) / f5;
        float f7 = (f4 - f2) / f5;
        for (int i = 0; i < iRound; i++) {
            float f8 = i;
            int iRound2 = MathUtils.round((f8 * f6) + f);
            int iRound3 = MathUtils.round((f8 * f7) + f2);
            if (this.a.get(iRound2, iRound3)) {
                return new ResultPoint(iRound2, iRound3);
            }
        }
        return null;
    }

    public ResultPoint[] detect() throws NotFoundException {
        boolean z;
        int i = this.d;
        int i2 = this.e;
        int i3 = this.g;
        int i4 = this.f;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = true;
        while (z7) {
            boolean z8 = false;
            boolean zA = true;
            while (true) {
                if ((!zA && z2) || i2 >= this.c) {
                    break;
                }
                zA = a(i3, i4, i2, false);
                if (zA) {
                    i2++;
                    z2 = true;
                    z8 = true;
                } else if (!z2) {
                    i2++;
                }
            }
            if (i2 < this.c) {
                boolean zA2 = true;
                while (true) {
                    if ((!zA2 && z3) || i4 >= this.b) {
                        break;
                    }
                    zA2 = a(i, i2, i4, true);
                    if (zA2) {
                        i4++;
                        z3 = true;
                        z8 = true;
                    } else if (!z3) {
                        i4++;
                    }
                }
                if (i4 < this.b) {
                    boolean zA3 = true;
                    while (true) {
                        if ((!zA3 && z4) || i < 0) {
                            break;
                        }
                        zA3 = a(i3, i4, i, false);
                        if (zA3) {
                            i--;
                            z4 = true;
                            z8 = true;
                        } else if (!z4) {
                            i--;
                        }
                    }
                    if (i >= 0) {
                        z7 = z8;
                        boolean zA4 = true;
                        while (true) {
                            if ((!zA4 && z6) || i3 < 0) {
                                break;
                            }
                            zA4 = a(i, i2, i3, true);
                            if (zA4) {
                                i3--;
                                z7 = true;
                                z6 = true;
                            } else if (!z6) {
                                i3--;
                            }
                        }
                        if (i3 >= 0) {
                            if (z7) {
                                z5 = true;
                            }
                        }
                    }
                }
            }
            z = true;
            break;
        }
        z = false;
        if (z || !z5) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i5 = i2 - i;
        ResultPoint resultPointA = null;
        ResultPoint resultPointA2 = null;
        for (int i6 = 1; resultPointA2 == null && i6 < i5; i6++) {
            resultPointA2 = a(i, i4 - i6, i + i6, i4);
        }
        if (resultPointA2 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint resultPointA3 = null;
        for (int i7 = 1; resultPointA3 == null && i7 < i5; i7++) {
            resultPointA3 = a(i, i3 + i7, i + i7, i3);
        }
        if (resultPointA3 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint resultPointA4 = null;
        for (int i8 = 1; resultPointA4 == null && i8 < i5; i8++) {
            resultPointA4 = a(i2, i3 + i8, i2 - i8, i3);
        }
        if (resultPointA4 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        for (int i9 = 1; resultPointA == null && i9 < i5; i9++) {
            resultPointA = a(i2, i4 - i9, i2 - i9, i4);
        }
        if (resultPointA == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        float x = resultPointA.getX();
        float y = resultPointA.getY();
        float x2 = resultPointA2.getX();
        float y2 = resultPointA2.getY();
        float x3 = resultPointA4.getX();
        float y3 = resultPointA4.getY();
        float x4 = resultPointA3.getX();
        float y4 = resultPointA3.getY();
        return x < ((float) this.c) / 2.0f ? new ResultPoint[]{new ResultPoint(x4 - 1.0f, y4 + 1.0f), new ResultPoint(x2 + 1.0f, y2 + 1.0f), new ResultPoint(x3 - 1.0f, y3 - 1.0f), new ResultPoint(x + 1.0f, y - 1.0f)} : new ResultPoint[]{new ResultPoint(x4 + 1.0f, y4 + 1.0f), new ResultPoint(x2 + 1.0f, y2 - 1.0f), new ResultPoint(x3 - 1.0f, y3 + 1.0f), new ResultPoint(x - 1.0f, y - 1.0f)};
    }

    public WhiteRectangleDetector(BitMatrix bitMatrix, int i, int i2, int i3) throws NotFoundException {
        this.a = bitMatrix;
        this.b = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        this.c = width;
        int i4 = i / 2;
        int i5 = i2 - i4;
        this.d = i5;
        int i6 = i2 + i4;
        this.e = i6;
        int i7 = i3 - i4;
        this.g = i7;
        int i8 = i3 + i4;
        this.f = i8;
        if (i7 < 0 || i5 < 0 || i8 >= this.b || i6 >= width) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    public final boolean a(int i, int i2, int i3, boolean z) {
        if (z) {
            while (i <= i2) {
                if (this.a.get(i, i3)) {
                    return true;
                }
                i++;
            }
            return false;
        }
        while (i <= i2) {
            if (this.a.get(i3, i)) {
                return true;
            }
            i++;
        }
        return false;
    }
}
