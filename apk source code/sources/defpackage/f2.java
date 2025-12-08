package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import defpackage.h2;

/* loaded from: classes.dex */
public class f2 extends AnimatorListenerAdapter {
    public final /* synthetic */ boolean a;
    public final /* synthetic */ h2.c b;
    public final /* synthetic */ h2 c;

    public f2(h2 h2Var, boolean z, h2.c cVar) {
        this.c = h2Var;
        this.a = z;
        this.b = cVar;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.c.a = 0;
        h2.c cVar = this.b;
        if (cVar != null) {
            d2 d2Var = (d2) cVar;
            d2Var.a.onShown(d2Var.b);
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.c.k.a(0, this.a);
    }
}
