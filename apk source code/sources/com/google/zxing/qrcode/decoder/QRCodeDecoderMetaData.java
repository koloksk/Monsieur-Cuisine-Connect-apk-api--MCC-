package com.google.zxing.qrcode.decoder;

import com.google.zxing.ResultPoint;

/* loaded from: classes.dex */
public final class QRCodeDecoderMetaData {
    public final boolean a;

    public QRCodeDecoderMetaData(boolean z) {
        this.a = z;
    }

    public void applyMirroredCorrection(ResultPoint[] resultPointArr) {
        if (!this.a || resultPointArr == null || resultPointArr.length < 3) {
            return;
        }
        ResultPoint resultPoint = resultPointArr[0];
        resultPointArr[0] = resultPointArr[2];
        resultPointArr[2] = resultPoint;
    }

    public boolean isMirrored() {
        return this.a;
    }
}
