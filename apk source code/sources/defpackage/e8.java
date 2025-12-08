package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* loaded from: classes.dex */
public class e8 extends AnimatorListenerAdapter {
    public final /* synthetic */ DefaultItemAnimator.d a;
    public final /* synthetic */ ViewPropertyAnimator b;
    public final /* synthetic */ View c;
    public final /* synthetic */ DefaultItemAnimator d;

    public e8(DefaultItemAnimator defaultItemAnimator, DefaultItemAnimator.d dVar, ViewPropertyAnimator viewPropertyAnimator, View view2) {
        this.d = defaultItemAnimator;
        this.a = dVar;
        this.b = viewPropertyAnimator;
        this.c = view2;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.b.setListener(null);
        this.c.setAlpha(1.0f);
        this.c.setTranslationX(0.0f);
        this.c.setTranslationY(0.0f);
        this.d.dispatchChangeFinished(this.a.b, false);
        this.d.r.remove(this.a.b);
        this.d.a();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.d.dispatchChangeStarting(this.a.b, false);
    }
}
