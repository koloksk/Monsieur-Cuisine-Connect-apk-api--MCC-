package defpackage;

import android.animation.ValueAnimator;
import android.support.v4.widget.CircularProgressDrawable;

/* loaded from: classes.dex */
public class e6 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ CircularProgressDrawable.a a;
    public final /* synthetic */ CircularProgressDrawable b;

    public e6(CircularProgressDrawable circularProgressDrawable, CircularProgressDrawable.a aVar) {
        this.b = circularProgressDrawable;
        this.a = aVar;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.b.a(fFloatValue, this.a);
        CircularProgressDrawable.a(this.b, fFloatValue, this.a, false);
        this.b.invalidateSelf();
    }
}
