package android.support.design.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import defpackage.q2;

/* loaded from: classes.dex */
public class ViewOffsetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public q2 a;
    public int b;
    public int c;

    public ViewOffsetBehavior() {
        this.b = 0;
        this.c = 0;
    }

    public int getLeftAndRightOffset() {
        q2 q2Var = this.a;
        if (q2Var != null) {
            return q2Var.e;
        }
        return 0;
    }

    public int getTopAndBottomOffset() {
        q2 q2Var = this.a;
        if (q2Var != null) {
            return q2Var.d;
        }
        return 0;
    }

    public void layoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        coordinatorLayout.onLayoutChild(v, i);
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        layoutChild(coordinatorLayout, v, i);
        if (this.a == null) {
            this.a = new q2(v);
        }
        q2 q2Var = this.a;
        q2Var.b = q2Var.a.getTop();
        q2Var.c = q2Var.a.getLeft();
        q2Var.a();
        int i2 = this.b;
        if (i2 != 0) {
            this.a.a(i2);
            this.b = 0;
        }
        int i3 = this.c;
        if (i3 == 0) {
            return true;
        }
        q2 q2Var2 = this.a;
        if (q2Var2.e != i3) {
            q2Var2.e = i3;
            q2Var2.a();
        }
        this.c = 0;
        return true;
    }

    public boolean setLeftAndRightOffset(int i) {
        q2 q2Var = this.a;
        if (q2Var == null) {
            this.c = i;
            return false;
        }
        if (q2Var.e == i) {
            return false;
        }
        q2Var.e = i;
        q2Var.a();
        return true;
    }

    public boolean setTopAndBottomOffset(int i) {
        q2 q2Var = this.a;
        if (q2Var != null) {
            return q2Var.a(i);
        }
        this.b = i;
        return false;
    }

    public ViewOffsetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = 0;
        this.c = 0;
    }
}
