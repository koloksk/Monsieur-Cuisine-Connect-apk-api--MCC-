package defpackage;

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/* loaded from: classes.dex */
public class q1 {
    public static final Interpolator a = new LinearInterpolator();
    public static final Interpolator b = new FastOutSlowInInterpolator();
    public static final Interpolator c = new FastOutLinearInInterpolator();
    public static final Interpolator d = new LinearOutSlowInInterpolator();
    public static final Interpolator e = new DecelerateInterpolator();

    public static float a(float f, float f2, float f3) {
        return g9.a(f2, f, f3, f);
    }

    public static int a(int i, int i2, float f) {
        return Math.round(f * (i2 - i)) + i;
    }
}
