package defpackage;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.SwipeDismissBehavior;
import android.view.View;

/* loaded from: classes.dex */
public class u1 implements SwipeDismissBehavior.OnDismissListener {
    public final /* synthetic */ BaseTransientBottomBar a;

    public u1(BaseTransientBottomBar baseTransientBottomBar) {
        this.a = baseTransientBottomBar;
    }

    @Override // android.support.design.widget.SwipeDismissBehavior.OnDismissListener
    public void onDismiss(View view2) {
        view2.setVisibility(8);
        this.a.a(0);
    }

    @Override // android.support.design.widget.SwipeDismissBehavior.OnDismissListener
    public void onDragStateChanged(int i) {
        if (i == 0) {
            l2.b().h(this.a.h);
        } else if (i == 1 || i == 2) {
            l2.b().g(this.a.h);
        }
    }
}
