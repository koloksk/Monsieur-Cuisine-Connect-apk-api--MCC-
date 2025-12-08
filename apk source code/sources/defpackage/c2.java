package defpackage;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.graphics.PorterDuff;
import android.support.transition.R;
import android.support.transition.TransitionValues;
import android.util.Property;
import android.view.View;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class c2 {
    public static Method a;

    public static PorterDuff.Mode a(int i, PorterDuff.Mode mode) {
        return i != 3 ? i != 5 ? i != 9 ? i != 14 ? i != 15 ? mode : PorterDuff.Mode.SCREEN : PorterDuff.Mode.MULTIPLY : PorterDuff.Mode.SRC_ATOP : PorterDuff.Mode.SRC_IN : PorterDuff.Mode.SRC_OVER;
    }

    public static <T> ArrayList<T> b(ArrayList<T> arrayList, T t) {
        if (arrayList == null) {
            return arrayList;
        }
        arrayList.remove(t);
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    public static Animator a(View view2, TransitionValues transitionValues, int i, int i2, float f, float f2, float f3, float f4, TimeInterpolator timeInterpolator) {
        float f5;
        float f6;
        float translationX = view2.getTranslationX();
        float translationY = view2.getTranslationY();
        if (((int[]) transitionValues.f0view.getTag(R.id.transition_position)) != null) {
            f5 = (r2[0] - i) + translationX;
            f6 = (r2[1] - i2) + translationY;
        } else {
            f5 = f;
            f6 = f2;
        }
        int iRound = Math.round(f5 - translationX) + i;
        int iRound2 = Math.round(f6 - translationY) + i2;
        view2.setTranslationX(f5);
        view2.setTranslationY(f6);
        if (f5 == f3 && f6 == f4) {
            return null;
        }
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view2, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_X, f5, f3), PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, f6, f4));
        r3 r3Var = new r3(view2, transitionValues.f0view, iRound, iRound2, translationX, translationY, null);
        objectAnimatorOfPropertyValuesHolder.addListener(r3Var);
        u2.a(objectAnimatorOfPropertyValuesHolder, r3Var);
        objectAnimatorOfPropertyValuesHolder.setInterpolator(timeInterpolator);
        return objectAnimatorOfPropertyValuesHolder;
    }

    public static Animator a(Animator animator, Animator animator2) {
        if (animator == null) {
            return animator2;
        }
        if (animator2 == null) {
            return animator;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, animator2);
        return animatorSet;
    }

    public static <T> ArrayList<T> a(ArrayList<T> arrayList, T t) {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        if (!arrayList.contains(t)) {
            arrayList.add(t);
        }
        return arrayList;
    }
}
