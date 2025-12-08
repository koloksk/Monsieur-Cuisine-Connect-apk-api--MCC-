package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

/* loaded from: classes.dex */
public final class FinderPattern extends ResultPoint {
    public final float c;
    public final int d;

    public FinderPattern(float f, float f2, float f3) {
        super(f, f2);
        this.c = f3;
        this.d = 1;
    }

    public float getEstimatedModuleSize() {
        return this.c;
    }

    public FinderPattern(float f, float f2, float f3, int i) {
        super(f, f2);
        this.c = f3;
        this.d = i;
    }
}
