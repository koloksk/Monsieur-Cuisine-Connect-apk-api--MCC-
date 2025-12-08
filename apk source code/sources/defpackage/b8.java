package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* loaded from: classes.dex */
public class b8 extends AnimatorListenerAdapter {
    public final /* synthetic */ RecyclerView.ViewHolder a;
    public final /* synthetic */ View b;
    public final /* synthetic */ ViewPropertyAnimator c;
    public final /* synthetic */ DefaultItemAnimator d;

    public b8(DefaultItemAnimator defaultItemAnimator, RecyclerView.ViewHolder viewHolder, View view2, ViewPropertyAnimator viewPropertyAnimator) {
        this.d = defaultItemAnimator;
        this.a = viewHolder;
        this.b = view2;
        this.c = viewPropertyAnimator;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        this.b.setAlpha(1.0f);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.c.setListener(null);
        this.d.dispatchAddFinished(this.a);
        this.d.o.remove(this.a);
        this.d.a();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.d.dispatchAddStarting(this.a);
    }
}
