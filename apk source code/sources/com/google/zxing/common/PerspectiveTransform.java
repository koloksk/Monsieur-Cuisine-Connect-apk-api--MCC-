package com.google.zxing.common;

/* loaded from: classes.dex */
public final class PerspectiveTransform {
    public final float a;
    public final float b;
    public final float c;
    public final float d;
    public final float e;
    public final float f;
    public final float g;
    public final float h;
    public final float i;

    public PerspectiveTransform(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.a = f;
        this.b = f4;
        this.c = f7;
        this.d = f2;
        this.e = f5;
        this.f = f8;
        this.g = f3;
        this.h = f6;
        this.i = f9;
    }

    public static PerspectiveTransform quadrilateralToQuadrilateral(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) {
        PerspectiveTransform perspectiveTransformQuadrilateralToSquare = quadrilateralToSquare(f, f2, f3, f4, f5, f6, f7, f8);
        PerspectiveTransform perspectiveTransformSquareToQuadrilateral = squareToQuadrilateral(f9, f10, f11, f12, f13, f14, f15, f16);
        float f17 = perspectiveTransformSquareToQuadrilateral.a;
        float f18 = perspectiveTransformQuadrilateralToSquare.a;
        float f19 = perspectiveTransformSquareToQuadrilateral.d;
        float f20 = perspectiveTransformQuadrilateralToSquare.b;
        float f21 = perspectiveTransformSquareToQuadrilateral.g;
        float f22 = perspectiveTransformQuadrilateralToSquare.c;
        float f23 = (f19 * f20) + (f17 * f18) + (f21 * f22);
        float f24 = perspectiveTransformQuadrilateralToSquare.d;
        float f25 = perspectiveTransformQuadrilateralToSquare.e;
        float f26 = perspectiveTransformQuadrilateralToSquare.f;
        float f27 = (f19 * f25) + (f17 * f24) + (f21 * f26);
        float f28 = perspectiveTransformQuadrilateralToSquare.g;
        float f29 = perspectiveTransformQuadrilateralToSquare.h;
        float f30 = perspectiveTransformQuadrilateralToSquare.i;
        float f31 = f21 * f30;
        float f32 = f31 + (f19 * f29) + (f17 * f28);
        float f33 = perspectiveTransformSquareToQuadrilateral.b;
        float f34 = perspectiveTransformSquareToQuadrilateral.e;
        float f35 = perspectiveTransformSquareToQuadrilateral.h;
        float f36 = (f35 * f22) + (f34 * f20) + (f33 * f18);
        float f37 = (f35 * f26) + (f34 * f25) + (f33 * f24);
        float f38 = (f34 * f29) + (f33 * f28) + (f35 * f30);
        float f39 = perspectiveTransformSquareToQuadrilateral.c;
        float f40 = perspectiveTransformSquareToQuadrilateral.f;
        float f41 = f20 * f40;
        float f42 = perspectiveTransformSquareToQuadrilateral.i;
        return new PerspectiveTransform(f23, f27, f32, f36, f37, f38, (f22 * f42) + f41 + (f18 * f39), (f26 * f42) + (f25 * f40) + (f24 * f39), (f42 * f30) + (f40 * f29) + (f39 * f28));
    }

    public static PerspectiveTransform quadrilateralToSquare(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        PerspectiveTransform perspectiveTransformSquareToQuadrilateral = squareToQuadrilateral(f, f2, f3, f4, f5, f6, f7, f8);
        float f9 = perspectiveTransformSquareToQuadrilateral.e;
        float f10 = perspectiveTransformSquareToQuadrilateral.i;
        float f11 = perspectiveTransformSquareToQuadrilateral.f;
        float f12 = perspectiveTransformSquareToQuadrilateral.h;
        float f13 = (f9 * f10) - (f11 * f12);
        float f14 = perspectiveTransformSquareToQuadrilateral.g;
        float f15 = perspectiveTransformSquareToQuadrilateral.d;
        float f16 = (f11 * f14) - (f15 * f10);
        float f17 = (f15 * f12) - (f9 * f14);
        float f18 = perspectiveTransformSquareToQuadrilateral.c;
        float f19 = perspectiveTransformSquareToQuadrilateral.b;
        float f20 = perspectiveTransformSquareToQuadrilateral.a;
        return new PerspectiveTransform(f13, f16, f17, (f18 * f12) - (f19 * f10), (f10 * f20) - (f18 * f14), (f14 * f19) - (f12 * f20), (f19 * f11) - (f18 * f9), (f18 * f15) - (f11 * f20), (f20 * f9) - (f19 * f15));
    }

    public static PerspectiveTransform squareToQuadrilateral(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float f9 = ((f - f3) + f5) - f7;
        float f10 = ((f2 - f4) + f6) - f8;
        if (f9 == 0.0f && f10 == 0.0f) {
            return new PerspectiveTransform(f3 - f, f5 - f3, f, f4 - f2, f6 - f4, f2, 0.0f, 0.0f, 1.0f);
        }
        float f11 = f3 - f5;
        float f12 = f7 - f5;
        float f13 = f4 - f6;
        float f14 = f8 - f6;
        float f15 = (f11 * f14) - (f12 * f13);
        float f16 = ((f14 * f9) - (f12 * f10)) / f15;
        float f17 = ((f11 * f10) - (f9 * f13)) / f15;
        return new PerspectiveTransform((f16 * f3) + (f3 - f), (f17 * f7) + (f7 - f), f, (f16 * f4) + (f4 - f2), (f17 * f8) + (f8 - f2), f2, f16, f17, 1.0f);
    }

    public void transformPoints(float[] fArr) {
        int length = fArr.length;
        float f = this.a;
        float f2 = this.b;
        float f3 = this.c;
        float f4 = this.d;
        float f5 = this.e;
        float f6 = this.f;
        float f7 = this.g;
        float f8 = this.h;
        float f9 = this.i;
        for (int i = 0; i < length; i += 2) {
            float f10 = fArr[i];
            int i2 = i + 1;
            float f11 = fArr[i2];
            float f12 = (f6 * f11) + (f3 * f10) + f9;
            fArr[i] = (((f4 * f11) + (f * f10)) + f7) / f12;
            fArr[i2] = (((f11 * f5) + (f10 * f2)) + f8) / f12;
        }
    }

    public void transformPoints(float[] fArr, float[] fArr2) {
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            float f = fArr[i];
            float f2 = fArr2[i];
            float f3 = (this.f * f2) + (this.c * f) + this.i;
            fArr[i] = (((this.d * f2) + (this.a * f)) + this.g) / f3;
            fArr2[i] = (((this.e * f2) + (this.b * f)) + this.h) / f3;
        }
    }
}
