package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* loaded from: classes.dex */
public class c8 extends AnimatorListenerAdapter {
    public final /* synthetic */ RecyclerView.ViewHolder a;
    public final /* synthetic */ int b;
    public final /* synthetic */ View c;
    public final /* synthetic */ int d;
    public final /* synthetic */ ViewPropertyAnimator e;
    public final /* synthetic */ DefaultItemAnimator f;

    public c8(DefaultItemAnimator defaultItemAnimator, RecyclerView.ViewHolder viewHolder, int i, View view2, int i2, ViewPropertyAnimator viewPropertyAnimator) {
        this.f = defaultItemAnimator;
        this.a = viewHolder;
        this.b = i;
        this.c = view2;
        this.d = i2;
        this.e = viewPropertyAnimator;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        if (this.b != 0) {
            this.c.setTranslationX(0.0f);
        }
        if (this.d != 0) {
            this.c.setTranslationY(0.0f);
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.e.setListener(null);
        this.f.dispatchMoveFinished(this.a);
        this.f.p.remove(this.a);
        this.f.a();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.f.dispatchMoveStarting(this.a);
    }
}
