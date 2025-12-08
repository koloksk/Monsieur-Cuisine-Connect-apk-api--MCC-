package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import android.util.Log;
import com.journeyapps.barcodescanner.Size;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
public class LegacyPreviewScalingStrategy extends PreviewScalingStrategy {

    public class a implements Comparator<Size> {
        public final /* synthetic */ Size a;

        public a(LegacyPreviewScalingStrategy legacyPreviewScalingStrategy, Size size) {
            this.a = size;
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0040, code lost:
        
            if (r0 < 0) goto L8;
         */
        @Override // java.util.Comparator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int compare(com.journeyapps.barcodescanner.Size r5, com.journeyapps.barcodescanner.Size r6) {
            /*
                r4 = this;
                com.journeyapps.barcodescanner.Size r5 = (com.journeyapps.barcodescanner.Size) r5
                com.journeyapps.barcodescanner.Size r6 = (com.journeyapps.barcodescanner.Size) r6
                com.journeyapps.barcodescanner.Size r0 = r4.a
                com.journeyapps.barcodescanner.Size r0 = com.journeyapps.barcodescanner.camera.LegacyPreviewScalingStrategy.scale(r5, r0)
                int r0 = r0.width
                int r1 = r5.width
                int r0 = r0 - r1
                com.journeyapps.barcodescanner.Size r1 = r4.a
                com.journeyapps.barcodescanner.Size r1 = com.journeyapps.barcodescanner.camera.LegacyPreviewScalingStrategy.scale(r6, r1)
                int r1 = r1.width
                int r2 = r6.width
                int r1 = r1 - r2
                if (r0 != 0) goto L23
                if (r1 != 0) goto L23
                int r5 = r5.compareTo(r6)
                goto L43
            L23:
                r2 = -1
                if (r0 != 0) goto L28
            L26:
                r5 = r2
                goto L43
            L28:
                r3 = 1
                if (r1 != 0) goto L2d
            L2b:
                r5 = r3
                goto L43
            L2d:
                if (r0 >= 0) goto L36
                if (r1 >= 0) goto L36
                int r5 = r5.compareTo(r6)
                goto L43
            L36:
                if (r0 <= 0) goto L40
                if (r1 <= 0) goto L40
                int r5 = r5.compareTo(r6)
                int r5 = -r5
                goto L43
            L40:
                if (r0 >= 0) goto L2b
                goto L26
            L43:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.journeyapps.barcodescanner.camera.LegacyPreviewScalingStrategy.a.compare(java.lang.Object, java.lang.Object):int");
        }
    }

    public static Size scale(Size size, Size size2) {
        Size sizeScale;
        if (size2.fitsIn(size)) {
            while (true) {
                sizeScale = size.scale(2, 3);
                Size sizeScale2 = size.scale(1, 2);
                if (!size2.fitsIn(sizeScale2)) {
                    break;
                }
                size = sizeScale2;
            }
            return size2.fitsIn(sizeScale) ? sizeScale : size;
        }
        do {
            Size sizeScale3 = size.scale(3, 2);
            size = size.scale(2, 1);
            if (size2.fitsIn(sizeScale3)) {
                return sizeScale3;
            }
        } while (!size2.fitsIn(size));
        return size;
    }

    @Override // com.journeyapps.barcodescanner.camera.PreviewScalingStrategy
    public Size getBestPreviewSize(List<Size> list, Size size) {
        if (size == null) {
            return list.get(0);
        }
        Collections.sort(list, new a(this, size));
        Log.i("LegacyPreviewScalingStrategy", "Viewfinder size: " + size);
        Log.i("LegacyPreviewScalingStrategy", "Preview in order of preference: " + list);
        return list.get(0);
    }

    @Override // com.journeyapps.barcodescanner.camera.PreviewScalingStrategy
    public Rect scalePreview(Size size, Size size2) {
        Size sizeScale = scale(size, size2);
        Log.i("LegacyPreviewScalingStrategy", "Preview: " + size + "; Scaled: " + sizeScale + "; Want: " + size2);
        int i = (sizeScale.width - size2.width) / 2;
        int i2 = (sizeScale.height - size2.height) / 2;
        return new Rect(-i, -i2, sizeScale.width - i, sizeScale.height - i2);
    }
}
