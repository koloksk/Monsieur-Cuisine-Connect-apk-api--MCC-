package defpackage;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/* loaded from: classes.dex */
public class j6 extends Animation {
    public final /* synthetic */ SwipeRefreshLayout a;

    public j6(SwipeRefreshLayout swipeRefreshLayout) {
        this.a = swipeRefreshLayout;
    }

    @Override // android.view.animation.Animation
    public void applyTransformation(float f, Transformation transformation) {
        SwipeRefreshLayout swipeRefreshLayout = this.a;
        float f2 = swipeRefreshLayout.w;
        swipeRefreshLayout.setAnimationProgress(((-f2) * f) + f2);
        this.a.c(f);
    }
}
