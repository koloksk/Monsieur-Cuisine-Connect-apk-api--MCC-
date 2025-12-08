package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.transition.Transition;
import android.support.v4.util.ArrayMap;

/* loaded from: classes.dex */
public class n3 extends AnimatorListenerAdapter {
    public final /* synthetic */ ArrayMap a;
    public final /* synthetic */ Transition b;

    public n3(Transition transition, ArrayMap arrayMap) {
        this.b = transition;
        this.a = arrayMap;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.a.remove(animator);
        this.b.w.remove(animator);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.b.w.add(animator);
    }
}
