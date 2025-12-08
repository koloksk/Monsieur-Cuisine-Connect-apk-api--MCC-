package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.TextInputLayout;

/* loaded from: classes.dex */
public class o2 extends AnimatorListenerAdapter {
    public final /* synthetic */ CharSequence a;
    public final /* synthetic */ TextInputLayout b;

    public o2(TextInputLayout textInputLayout, CharSequence charSequence) {
        this.b = textInputLayout;
        this.a = charSequence;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.b.l.setText(this.a);
        this.b.l.setVisibility(4);
    }
}
