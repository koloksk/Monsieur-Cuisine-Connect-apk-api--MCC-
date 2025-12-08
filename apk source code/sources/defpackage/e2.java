package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import defpackage.h2;

/* loaded from: classes.dex */
public class e2 extends AnimatorListenerAdapter {
    public boolean a;
    public final /* synthetic */ boolean b;
    public final /* synthetic */ h2.c c;
    public final /* synthetic */ h2 d;

    public e2(h2 h2Var, boolean z, h2.c cVar) {
        this.d = h2Var;
        this.b = z;
        this.c = cVar;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        this.a = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        h2 h2Var = this.d;
        h2Var.a = 0;
        if (this.a) {
            return;
        }
        h2Var.k.a(this.b ? 8 : 4, this.b);
        h2.c cVar = this.c;
        if (cVar != null) {
            d2 d2Var = (d2) cVar;
            d2Var.a.onHidden(d2Var.b);
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.d.k.a(0, this.b);
        this.a = false;
    }
}
