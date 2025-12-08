package defpackage;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.view.View;

/* loaded from: classes.dex */
public class v6 implements Runnable {
    public final /* synthetic */ AppCompatDelegateImplV9 a;

    public class a extends ViewPropertyAnimatorListenerAdapter {
        public a() {
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view2) {
            v6.this.a.w.setAlpha(1.0f);
            v6.this.a.z.setListener(null);
            v6.this.a.z = null;
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
        public void onAnimationStart(View view2) {
            v6.this.a.w.setVisibility(0);
        }
    }

    public v6(AppCompatDelegateImplV9 appCompatDelegateImplV9) {
        this.a = appCompatDelegateImplV9;
    }

    @Override // java.lang.Runnable
    public void run() {
        AppCompatDelegateImplV9 appCompatDelegateImplV9 = this.a;
        appCompatDelegateImplV9.x.showAtLocation(appCompatDelegateImplV9.w, 55, 0, 0);
        this.a.d();
        if (!this.a.f()) {
            this.a.w.setAlpha(1.0f);
            this.a.w.setVisibility(0);
        } else {
            this.a.w.setAlpha(0.0f);
            AppCompatDelegateImplV9 appCompatDelegateImplV92 = this.a;
            appCompatDelegateImplV92.z = ViewCompat.animate(appCompatDelegateImplV92.w).alpha(1.0f);
            this.a.z.setListener(new a());
        }
    }
}
