package defpackage;

import android.support.v4.view.ViewCompat;
import android.view.View;

/* loaded from: classes.dex */
public class q2 {
    public final View a;
    public int b;
    public int c;
    public int d;
    public int e;

    public q2(View view2) {
        this.a = view2;
    }

    public final void a() {
        View view2 = this.a;
        ViewCompat.offsetTopAndBottom(view2, this.d - (view2.getTop() - this.b));
        View view3 = this.a;
        ViewCompat.offsetLeftAndRight(view3, this.e - (view3.getLeft() - this.c));
    }

    public boolean a(int i) {
        if (this.d == i) {
            return false;
        }
        this.d = i;
        a();
        return true;
    }
}
