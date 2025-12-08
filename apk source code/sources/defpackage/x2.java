package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Matrix;
import android.support.transition.ChangeTransform;
import android.support.transition.R;
import android.view.View;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class x2 extends AnimatorListenerAdapter {
    public boolean a;
    public Matrix b = new Matrix();
    public final /* synthetic */ boolean c;
    public final /* synthetic */ Matrix d;
    public final /* synthetic */ View e;
    public final /* synthetic */ ChangeTransform.e f;
    public final /* synthetic */ ChangeTransform.d g;
    public final /* synthetic */ ChangeTransform h;

    public x2(ChangeTransform changeTransform, boolean z, Matrix matrix, View view2, ChangeTransform.e eVar, ChangeTransform.d dVar) {
        this.h = changeTransform;
        this.c = z;
        this.d = matrix;
        this.e = view2;
        this.f = eVar;
        this.g = dVar;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        this.a = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (!this.a) {
            if (this.c && this.h.I) {
                this.b.set(this.d);
                this.e.setTag(R.id.transition_transform, this.b);
                this.f.a(this.e);
            } else {
                this.e.setTag(R.id.transition_transform, null);
                this.e.setTag(R.id.parent_matrix, null);
            }
        }
        a4.a(this.e, (Matrix) null);
        this.f.a(this.e);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public void onAnimationPause(Animator animator) {
        this.b.set(this.g.a);
        this.e.setTag(R.id.transition_transform, this.b);
        this.f.a(this.e);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public void onAnimationResume(Animator animator) {
        ChangeTransform.b(this.e);
    }
}
