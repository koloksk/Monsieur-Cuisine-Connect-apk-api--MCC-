package defpackage;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.view.View;

/* loaded from: classes.dex */
public class w6 extends ViewPropertyAnimatorListenerAdapter {
    public final /* synthetic */ AppCompatDelegateImplV9 a;

    public w6(AppCompatDelegateImplV9 appCompatDelegateImplV9) {
        this.a = appCompatDelegateImplV9;
    }

    @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
    public void onAnimationEnd(View view2) {
        this.a.w.setAlpha(1.0f);
        this.a.z.setListener(null);
        this.a.z = null;
    }

    @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
    public void onAnimationStart(View view2) {
        this.a.w.setVisibility(0);
        this.a.w.sendAccessibilityEvent(32);
        if (this.a.w.getParent() instanceof View) {
            ViewCompat.requestApplyInsets((View) this.a.w.getParent());
        }
    }
}
