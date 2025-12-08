package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import com.journeyapps.barcodescanner.Size;
import java.util.List;

/* loaded from: classes.dex */
public class DisplayConfiguration {
    public Size a;
    public int b;
    public PreviewScalingStrategy c = new FitCenterStrategy();

    public DisplayConfiguration(int i) {
        this.b = i;
    }

    public Size getBestPreviewSize(List<Size> list, boolean z) {
        return this.c.getBestPreviewSize(list, getDesiredPreviewSize(z));
    }

    public Size getDesiredPreviewSize(boolean z) {
        Size size = this.a;
        if (size == null) {
            return null;
        }
        return z ? size.rotate() : size;
    }

    public PreviewScalingStrategy getPreviewScalingStrategy() {
        return this.c;
    }

    public int getRotation() {
        return this.b;
    }

    public Size getViewfinderSize() {
        return this.a;
    }

    public Rect scalePreview(Size size) {
        return this.c.scalePreview(size, this.a);
    }

    public void setPreviewScalingStrategy(PreviewScalingStrategy previewScalingStrategy) {
        this.c = previewScalingStrategy;
    }

    public DisplayConfiguration(int i, Size size) {
        this.b = i;
        this.a = size;
    }
}
