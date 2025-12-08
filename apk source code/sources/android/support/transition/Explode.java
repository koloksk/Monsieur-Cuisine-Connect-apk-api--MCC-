package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import defpackage.c2;

/* loaded from: classes.dex */
public class Explode extends Visibility {
    public static final TimeInterpolator L = new DecelerateInterpolator();
    public static final TimeInterpolator M = new AccelerateInterpolator();
    public int[] K;

    public Explode() {
        this.K = new int[2];
        setPropagation(new CircularPropagation());
    }

    private void b(TransitionValues transitionValues) {
        View view2 = transitionValues.f0view;
        view2.getLocationOnScreen(this.K);
        int[] iArr = this.K;
        int i = iArr[0];
        int i2 = iArr[1];
        transitionValues.values.put("android:explode:screenBounds", new Rect(i, i2, view2.getWidth() + i, view2.getHeight() + i2));
    }

    public final void a(View view2, Rect rect, int[] iArr) {
        int iCenterX;
        int iCenterY;
        view2.getLocationOnScreen(this.K);
        int[] iArr2 = this.K;
        int i = iArr2[0];
        int i2 = iArr2[1];
        Rect epicenter = getEpicenter();
        if (epicenter == null) {
            iCenterX = Math.round(view2.getTranslationX()) + (view2.getWidth() / 2) + i;
            iCenterY = Math.round(view2.getTranslationY()) + (view2.getHeight() / 2) + i2;
        } else {
            iCenterX = epicenter.centerX();
            iCenterY = epicenter.centerY();
        }
        float fCenterX = rect.centerX() - iCenterX;
        float fCenterY = rect.centerY() - iCenterY;
        if (fCenterX == 0.0f && fCenterY == 0.0f) {
            fCenterX = ((float) (Math.random() * 2.0d)) - 1.0f;
            fCenterY = ((float) (Math.random() * 2.0d)) - 1.0f;
        }
        float fSqrt = (float) Math.sqrt((fCenterY * fCenterY) + (fCenterX * fCenterX));
        int i3 = iCenterX - i;
        int i4 = iCenterY - i2;
        float fMax = Math.max(i3, view2.getWidth() - i3);
        float fMax2 = Math.max(i4, view2.getHeight() - i4);
        float fSqrt2 = (float) Math.sqrt((fMax2 * fMax2) + (fMax * fMax));
        iArr[0] = Math.round((fCenterX / fSqrt) * fSqrt2);
        iArr[1] = Math.round(fSqrt2 * (fCenterY / fSqrt));
    }

    @Override // android.support.transition.Visibility, android.support.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        b(transitionValues);
    }

    @Override // android.support.transition.Visibility, android.support.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        b(transitionValues);
    }

    @Override // android.support.transition.Visibility
    public Animator onAppear(ViewGroup viewGroup, View view2, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        Rect rect = (Rect) transitionValues2.values.get("android:explode:screenBounds");
        float translationX = view2.getTranslationX();
        float translationY = view2.getTranslationY();
        a(viewGroup, rect, this.K);
        int[] iArr = this.K;
        return c2.a(view2, transitionValues2, rect.left, rect.top, translationX + iArr[0], translationY + iArr[1], translationX, translationY, L);
    }

    @Override // android.support.transition.Visibility
    public Animator onDisappear(ViewGroup viewGroup, View view2, TransitionValues transitionValues, TransitionValues transitionValues2) {
        float f;
        float f2;
        if (transitionValues == null) {
            return null;
        }
        Rect rect = (Rect) transitionValues.values.get("android:explode:screenBounds");
        int i = rect.left;
        int i2 = rect.top;
        float translationX = view2.getTranslationX();
        float translationY = view2.getTranslationY();
        int[] iArr = (int[]) transitionValues.f0view.getTag(R.id.transition_position);
        if (iArr != null) {
            f = (iArr[0] - rect.left) + translationX;
            f2 = (iArr[1] - rect.top) + translationY;
            rect.offsetTo(iArr[0], iArr[1]);
        } else {
            f = translationX;
            f2 = translationY;
        }
        a(viewGroup, rect, this.K);
        int[] iArr2 = this.K;
        return c2.a(view2, transitionValues, i, i2, translationX, translationY, f + iArr2[0], f2 + iArr2[1], M);
    }

    public Explode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.K = new int[2];
        setPropagation(new CircularPropagation());
    }
}
