package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.TextInputLayout;

/* loaded from: classes.dex */
public class n2 extends AnimatorListenerAdapter {
    public final /* synthetic */ TextInputLayout a;

    public n2(TextInputLayout textInputLayout) {
        this.a = textInputLayout;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.a.l.setVisibility(0);
    }
}
