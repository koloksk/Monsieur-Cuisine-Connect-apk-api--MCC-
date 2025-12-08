package defpackage;

import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/* loaded from: classes.dex */
public class jr implements Interpolator {
    public Interpolator a = new DecelerateInterpolator();

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return 1.0f - this.a.getInterpolation(f);
    }
}
