package defpackage;

import android.support.v4.view.InputDeviceCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

/* loaded from: classes.dex */
public final class oa implements sa {
    @Override // defpackage.sa
    public void a(ta taVar) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) 0);
        while (true) {
            if (!taVar.d()) {
                break;
            }
            sb.append(taVar.b());
            int i = taVar.f + 1;
            taVar.f = i;
            int iA = HighLevelEncoder.a(taVar.a, i, 5);
            if (iA != 5) {
                taVar.g = iA;
                break;
            }
        }
        int length = sb.length() - 1;
        int iA2 = taVar.a() + length + 1;
        taVar.a(iA2);
        boolean z = taVar.h.getDataCapacity() - iA2 > 0;
        if (taVar.d() || z) {
            if (length <= 249) {
                sb.setCharAt(0, (char) length);
            } else {
                if (length > 1555) {
                    throw new IllegalStateException(g9.a("Message length not in valid ranges: ", length));
                }
                sb.setCharAt(0, (char) ((length / ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION) + 249));
                sb.insert(1, (char) (length % ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION));
            }
        }
        int length2 = sb.length();
        for (int i2 = 0; i2 < length2; i2++) {
            int iA3 = (((taVar.a() + 1) * 149) % 255) + 1 + sb.charAt(i2);
            if (iA3 > 255) {
                iA3 += InputDeviceCompat.SOURCE_ANY;
            }
            taVar.e.append((char) iA3);
        }
    }
}
