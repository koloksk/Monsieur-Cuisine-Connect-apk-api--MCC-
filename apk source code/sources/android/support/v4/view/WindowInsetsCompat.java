package android.support.v4.view;

import android.graphics.Rect;
import android.view.WindowInsets;

/* loaded from: classes.dex */
public class WindowInsetsCompat {
    public final Object a;

    public WindowInsetsCompat(Object obj) {
        this.a = obj;
    }

    public static WindowInsetsCompat a(Object obj) {
        if (obj == null) {
            return null;
        }
        return new WindowInsetsCompat(obj);
    }

    public WindowInsetsCompat consumeStableInsets() {
        return new WindowInsetsCompat(((WindowInsets) this.a).consumeStableInsets());
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        return new WindowInsetsCompat(((WindowInsets) this.a).consumeSystemWindowInsets());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || WindowInsetsCompat.class != obj.getClass()) {
            return false;
        }
        Object obj2 = this.a;
        Object obj3 = ((WindowInsetsCompat) obj).a;
        return obj2 == null ? obj3 == null : obj2.equals(obj3);
    }

    public int getStableInsetBottom() {
        return ((WindowInsets) this.a).getStableInsetBottom();
    }

    public int getStableInsetLeft() {
        return ((WindowInsets) this.a).getStableInsetLeft();
    }

    public int getStableInsetRight() {
        return ((WindowInsets) this.a).getStableInsetRight();
    }

    public int getStableInsetTop() {
        return ((WindowInsets) this.a).getStableInsetTop();
    }

    public int getSystemWindowInsetBottom() {
        return ((WindowInsets) this.a).getSystemWindowInsetBottom();
    }

    public int getSystemWindowInsetLeft() {
        return ((WindowInsets) this.a).getSystemWindowInsetLeft();
    }

    public int getSystemWindowInsetRight() {
        return ((WindowInsets) this.a).getSystemWindowInsetRight();
    }

    public int getSystemWindowInsetTop() {
        return ((WindowInsets) this.a).getSystemWindowInsetTop();
    }

    public boolean hasInsets() {
        return ((WindowInsets) this.a).hasInsets();
    }

    public boolean hasStableInsets() {
        return ((WindowInsets) this.a).hasStableInsets();
    }

    public boolean hasSystemWindowInsets() {
        return ((WindowInsets) this.a).hasSystemWindowInsets();
    }

    public int hashCode() {
        Object obj = this.a;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public boolean isConsumed() {
        return ((WindowInsets) this.a).isConsumed();
    }

    public boolean isRound() {
        return ((WindowInsets) this.a).isRound();
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        return new WindowInsetsCompat(((WindowInsets) this.a).replaceSystemWindowInsets(i, i2, i3, i4));
    }

    public static Object a(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat == null) {
            return null;
        }
        return windowInsetsCompat.a;
    }

    public WindowInsetsCompat(WindowInsetsCompat windowInsetsCompat) {
        this.a = windowInsetsCompat == null ? null : new WindowInsets((WindowInsets) windowInsetsCompat.a);
    }

    public WindowInsetsCompat replaceSystemWindowInsets(Rect rect) {
        return new WindowInsetsCompat(((WindowInsets) this.a).replaceSystemWindowInsets(rect));
    }
}
