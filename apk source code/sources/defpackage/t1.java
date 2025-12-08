package defpackage;

import android.animation.ValueAnimator;
import android.support.design.widget.BaseTransientBottomBar;

/* loaded from: classes.dex */
public class t1 implements ValueAnimator.AnimatorUpdateListener {
    public int a = 0;
    public final /* synthetic */ BaseTransientBottomBar b;

    public t1(BaseTransientBottomBar baseTransientBottomBar) {
        this.b = baseTransientBottomBar;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        BaseTransientBottomBar.d();
        this.b.c.setTranslationY(iIntValue);
        this.a = iIntValue;
    }
}
