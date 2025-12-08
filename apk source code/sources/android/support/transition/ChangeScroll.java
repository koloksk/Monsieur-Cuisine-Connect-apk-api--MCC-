package android.support.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import defpackage.c2;

/* loaded from: classes.dex */
public class ChangeScroll extends Transition {
    public static final String[] I = {"android:changeScroll:x", "android:changeScroll:y"};

    public ChangeScroll() {
    }

    public final void b(TransitionValues transitionValues) {
        transitionValues.values.put("android:changeScroll:x", Integer.valueOf(transitionValues.f0view.getScrollX()));
        transitionValues.values.put("android:changeScroll:y", Integer.valueOf(transitionValues.f0view.getScrollY()));
    }

    @Override // android.support.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    @Override // android.support.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    @Override // android.support.transition.Transition
    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        ObjectAnimator objectAnimatorOfInt;
        ObjectAnimator objectAnimatorOfInt2 = null;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        View view2 = transitionValues2.f0view;
        int iIntValue = ((Integer) transitionValues.values.get("android:changeScroll:x")).intValue();
        int iIntValue2 = ((Integer) transitionValues2.values.get("android:changeScroll:x")).intValue();
        int iIntValue3 = ((Integer) transitionValues.values.get("android:changeScroll:y")).intValue();
        int iIntValue4 = ((Integer) transitionValues2.values.get("android:changeScroll:y")).intValue();
        if (iIntValue != iIntValue2) {
            view2.setScrollX(iIntValue);
            objectAnimatorOfInt = ObjectAnimator.ofInt(view2, "scrollX", iIntValue, iIntValue2);
        } else {
            objectAnimatorOfInt = null;
        }
        if (iIntValue3 != iIntValue4) {
            view2.setScrollY(iIntValue3);
            objectAnimatorOfInt2 = ObjectAnimator.ofInt(view2, "scrollY", iIntValue3, iIntValue4);
        }
        return c2.a(objectAnimatorOfInt, objectAnimatorOfInt2);
    }

    @Override // android.support.transition.Transition
    @Nullable
    public String[] getTransitionProperties() {
        return I;
    }

    public ChangeScroll(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
