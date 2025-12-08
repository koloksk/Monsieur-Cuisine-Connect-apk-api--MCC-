package com.journeyapps.barcodescanner;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/* loaded from: classes.dex */
public class DecoderResultPointCallback implements ResultPointCallback {
    public Decoder a;

    public DecoderResultPointCallback(Decoder decoder) {
        this.a = decoder;
    }

    @Override // com.google.zxing.ResultPointCallback
    public void foundPossibleResultPoint(ResultPoint resultPoint) {
        Decoder decoder = this.a;
        if (decoder != null) {
            decoder.foundPossibleResultPoint(resultPoint);
        }
    }

    public Decoder getDecoder() {
        return this.a;
    }

    public void setDecoder(Decoder decoder) {
        this.a = decoder;
    }

    public DecoderResultPointCallback() {
    }
}
