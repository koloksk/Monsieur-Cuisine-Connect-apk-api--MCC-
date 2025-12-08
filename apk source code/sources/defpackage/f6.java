package defpackage;

import android.animation.Animator;
import android.support.v4.widget.CircularProgressDrawable;

/* loaded from: classes.dex */
public class f6 implements Animator.AnimatorListener {
    public final /* synthetic */ CircularProgressDrawable.a a;
    public final /* synthetic */ CircularProgressDrawable b;

    public f6(CircularProgressDrawable circularProgressDrawable, CircularProgressDrawable.a aVar) {
        this.b = circularProgressDrawable;
        this.a = aVar;
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
        CircularProgressDrawable.a(this.b, 1.0f, this.a, true);
        CircularProgressDrawable.a aVar = this.a;
        aVar.k = aVar.e;
        aVar.l = aVar.f;
        aVar.m = aVar.g;
        aVar.a((aVar.j + 1) % aVar.i.length);
        CircularProgressDrawable circularProgressDrawable = this.b;
        if (!circularProgressDrawable.f) {
            circularProgressDrawable.e += 1.0f;
            return;
        }
        circularProgressDrawable.f = false;
        animator.cancel();
        animator.setDuration(1332L);
        animator.start();
        this.a.a(false);
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.b.e = 0.0f;
    }
}
