package defpackage;

import android.graphics.Rect;
import android.support.v4.widget.FocusStrategy$BoundsAdapter;
import java.util.Comparator;

/* loaded from: classes.dex */
public class h6<T> implements Comparator<T> {
    public final Rect a = new Rect();
    public final Rect b = new Rect();
    public final boolean c;
    public final FocusStrategy$BoundsAdapter<T> d;

    public h6(boolean z, FocusStrategy$BoundsAdapter<T> focusStrategy$BoundsAdapter) {
        this.c = z;
        this.d = focusStrategy$BoundsAdapter;
    }

    @Override // java.util.Comparator
    public int compare(T t, T t2) {
        Rect rect = this.a;
        Rect rect2 = this.b;
        this.d.obtainBounds(t, rect);
        this.d.obtainBounds(t2, rect2);
        int i = rect.top;
        int i2 = rect2.top;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        int i3 = rect.left;
        int i4 = rect2.left;
        if (i3 < i4) {
            return this.c ? 1 : -1;
        }
        if (i3 > i4) {
            return this.c ? -1 : 1;
        }
        int i5 = rect.bottom;
        int i6 = rect2.bottom;
        if (i5 < i6) {
            return -1;
        }
        if (i5 > i6) {
            return 1;
        }
        int i7 = rect.right;
        int i8 = rect2.right;
        if (i7 < i8) {
            return this.c ? 1 : -1;
        }
        if (i7 > i8) {
            return this.c ? -1 : 1;
        }
        return 0;
    }
}
