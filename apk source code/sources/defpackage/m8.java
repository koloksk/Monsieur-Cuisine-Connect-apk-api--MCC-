package defpackage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import defpackage.z7;

/* loaded from: classes.dex */
public class m8 implements z7.b {
    public final /* synthetic */ RecyclerView a;

    public m8(RecyclerView recyclerView) {
        this.a = recyclerView;
    }

    public int a() {
        return this.a.getChildCount();
    }

    public void b(int i) {
        View childAt = this.a.getChildAt(i);
        if (childAt != null) {
            this.a.a(childAt);
            childAt.clearAnimation();
        }
        this.a.removeViewAt(i);
    }

    public View a(int i) {
        return this.a.getChildAt(i);
    }
}
