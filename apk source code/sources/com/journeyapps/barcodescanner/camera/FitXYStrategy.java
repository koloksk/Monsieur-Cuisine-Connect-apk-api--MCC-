package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import com.journeyapps.barcodescanner.Size;

/* loaded from: classes.dex */
public class FitXYStrategy extends PreviewScalingStrategy {
    public static float a(float f) {
        return f < 1.0f ? 1.0f / f : f;
    }

    @Override // com.journeyapps.barcodescanner.camera.PreviewScalingStrategy
    public float getScore(Size size, Size size2) {
        int i = size.width;
        if (i <= 0 || size.height <= 0) {
            return 0.0f;
        }
        float fA = (1.0f / a((i * 1.0f) / size2.width)) / a((size.height * 1.0f) / size2.height);
        float fA2 = a(((size.width * 1.0f) / size.height) / ((size2.width * 1.0f) / size2.height));
        return (((1.0f / fA2) / fA2) / fA2) * fA;
    }

    @Override // com.journeyapps.barcodescanner.camera.PreviewScalingStrategy
    public Rect scalePreview(Size size, Size size2) {
        return new Rect(0, 0, size2.width, size2.height);
    }
}
