package com.google.zxing;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes.dex */
public abstract class Binarizer {
    public final LuminanceSource a;

    public Binarizer(LuminanceSource luminanceSource) {
        this.a = luminanceSource;
    }

    public abstract Binarizer createBinarizer(LuminanceSource luminanceSource);

    public abstract BitMatrix getBlackMatrix() throws NotFoundException;

    public abstract BitArray getBlackRow(int i, BitArray bitArray) throws NotFoundException;

    public final int getHeight() {
        return this.a.getHeight();
    }

    public final LuminanceSource getLuminanceSource() {
        return this.a;
    }

    public final int getWidth() {
        return this.a.getWidth();
    }
}
