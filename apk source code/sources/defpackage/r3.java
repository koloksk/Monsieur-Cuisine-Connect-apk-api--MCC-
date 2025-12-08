package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.transition.R;
import android.view.View;

/* loaded from: classes.dex */
public class r3 extends AnimatorListenerAdapter {
    public final View a;
    public final View b;
    public final int c;
    public final int d;
    public int[] e;
    public float f;
    public float g;
    public final float h;
    public final float i;

    public /* synthetic */ r3(View view2, View view3, int i, int i2, float f, float f2, q3 q3Var) {
        this.b = view2;
        this.a = view3;
        this.c = i - Math.round(view2.getTranslationX());
        this.d = i2 - Math.round(this.b.getTranslationY());
        this.h = f;
        this.i = f2;
        int[] iArr = (int[]) this.a.getTag(R.id.transition_position);
        this.e = iArr;
        if (iArr != null) {
            this.a.setTag(R.id.transition_position, null);
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        if (this.e == null) {
            this.e = new int[2];
        }
        this.e[0] = Math.round(this.b.getTranslationX() + this.c);
        this.e[1] = Math.round(this.b.getTranslationY() + this.d);
        this.a.setTag(R.id.transition_position, this.e);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.b.setTranslationX(this.h);
        this.b.setTranslationY(this.i);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public void onAnimationPause(Animator animator) {
        this.f = this.b.getTranslationX();
        this.g = this.b.getTranslationY();
        this.b.setTranslationX(this.h);
        this.b.setTranslationY(this.i);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public void onAnimationResume(Animator animator) {
        this.b.setTranslationX(this.f);
        this.b.setTranslationY(this.g);
    }
}
