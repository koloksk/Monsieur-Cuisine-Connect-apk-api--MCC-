package defpackage;

import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.DecorContentParent;

/* loaded from: classes.dex */
public class u6 implements ContentFrameLayout.OnAttachListener {
    public final /* synthetic */ AppCompatDelegateImplV9 a;

    public u6(AppCompatDelegateImplV9 appCompatDelegateImplV9) {
        this.a = appCompatDelegateImplV9;
    }

    @Override // android.support.v7.widget.ContentFrameLayout.OnAttachListener
    public void onAttachedFromWindow() {
    }

    @Override // android.support.v7.widget.ContentFrameLayout.OnAttachListener
    public void onDetachedFromWindow() {
        MenuBuilder menuBuilder;
        AppCompatDelegateImplV9 appCompatDelegateImplV9 = this.a;
        DecorContentParent decorContentParent = appCompatDelegateImplV9.s;
        if (decorContentParent != null) {
            decorContentParent.dismissPopups();
        }
        if (appCompatDelegateImplV9.x != null) {
            appCompatDelegateImplV9.d.getDecorView().removeCallbacks(appCompatDelegateImplV9.y);
            if (appCompatDelegateImplV9.x.isShowing()) {
                try {
                    appCompatDelegateImplV9.x.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            appCompatDelegateImplV9.x = null;
        }
        appCompatDelegateImplV9.d();
        AppCompatDelegateImplV9.PanelFeatureState panelFeatureStateB = appCompatDelegateImplV9.b(0);
        if (panelFeatureStateB == null || (menuBuilder = panelFeatureStateB.j) == null) {
            return;
        }
        menuBuilder.close();
    }
}
