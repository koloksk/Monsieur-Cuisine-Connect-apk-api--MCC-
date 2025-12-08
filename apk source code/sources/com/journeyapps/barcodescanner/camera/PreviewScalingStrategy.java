package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import android.util.Log;
import com.journeyapps.barcodescanner.Size;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class PreviewScalingStrategy {

    public class a implements Comparator<Size> {
        public final /* synthetic */ Size a;

        public a(Size size) {
            this.a = size;
        }

        @Override // java.util.Comparator
        public int compare(Size size, Size size2) {
            return Float.compare(PreviewScalingStrategy.this.getScore(size2, this.a), PreviewScalingStrategy.this.getScore(size, this.a));
        }
    }

    public List<Size> getBestPreviewOrder(List<Size> list, Size size) {
        if (size == null) {
            return list;
        }
        Collections.sort(list, new a(size));
        return list;
    }

    public Size getBestPreviewSize(List<Size> list, Size size) {
        List<Size> bestPreviewOrder = getBestPreviewOrder(list, size);
        Log.i("PreviewScalingStrategy", "Viewfinder size: " + size);
        Log.i("PreviewScalingStrategy", "Preview in order of preference: " + bestPreviewOrder);
        return bestPreviewOrder.get(0);
    }

    public float getScore(Size size, Size size2) {
        return 0.5f;
    }

    public abstract Rect scalePreview(Size size, Size size2);
}
