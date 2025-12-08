package com.google.zxing.pdf417.detector;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.List;

/* loaded from: classes.dex */
public final class PDF417DetectorResult {
    public final BitMatrix a;
    public final List<ResultPoint[]> b;

    public PDF417DetectorResult(BitMatrix bitMatrix, List<ResultPoint[]> list) {
        this.a = bitMatrix;
        this.b = list;
    }

    public BitMatrix getBits() {
        return this.a;
    }

    public List<ResultPoint[]> getPoints() {
        return this.b;
    }
}
