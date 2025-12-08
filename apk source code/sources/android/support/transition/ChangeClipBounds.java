package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import defpackage.a4;
import defpackage.l3;

/* loaded from: classes.dex */
public class ChangeClipBounds extends Transition {
    public static final String[] I = {"android:clipBounds:clip"};

    public class a extends AnimatorListenerAdapter {
        public final /* synthetic */ View a;

        public a(ChangeClipBounds changeClipBounds, View view2) {
            this.a = view2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            ViewCompat.setClipBounds(this.a, null);
        }
    }

    public ChangeClipBounds() {
    }

    public final void b(TransitionValues transitionValues) {
        View view2 = transitionValues.f0view;
        if (view2.getVisibility() == 8) {
            return;
        }
        Rect clipBounds = ViewCompat.getClipBounds(view2);
        transitionValues.values.put("android:clipBounds:clip", clipBounds);
        if (clipBounds == null) {
            transitionValues.values.put("android:clipBounds:bounds", new Rect(0, 0, view2.getWidth(), view2.getHeight()));
        }
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
    public Animator createAnimator(@NonNull ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        ObjectAnimator objectAnimatorOfObject = null;
        if (transitionValues != null && transitionValues2 != null && transitionValues.values.containsKey("android:clipBounds:clip") && transitionValues2.values.containsKey("android:clipBounds:clip")) {
            Rect rect = (Rect) transitionValues.values.get("android:clipBounds:clip");
            Rect rect2 = (Rect) transitionValues2.values.get("android:clipBounds:clip");
            boolean z = rect2 == null;
            if (rect == null && rect2 == null) {
                return null;
            }
            if (rect == null) {
                rect = (Rect) transitionValues.values.get("android:clipBounds:bounds");
            } else if (rect2 == null) {
                rect2 = (Rect) transitionValues2.values.get("android:clipBounds:bounds");
            }
            if (rect.equals(rect2)) {
                return null;
            }
            ViewCompat.setClipBounds(transitionValues2.f0view, rect);
            objectAnimatorOfObject = ObjectAnimator.ofObject(transitionValues2.f0view, (Property<View, V>) a4.e, (TypeEvaluator) new l3(new Rect()), (Object[]) new Rect[]{rect, rect2});
            if (z) {
                objectAnimatorOfObject.addListener(new a(this, transitionValues2.f0view));
            }
        }
        return objectAnimatorOfObject;
    }

    @Override // android.support.transition.Transition
    public String[] getTransitionProperties() {
        return I;
    }

    public ChangeClipBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
