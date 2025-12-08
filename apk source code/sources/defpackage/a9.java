package defpackage;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/* loaded from: classes.dex */
public class a9 implements Runnable {
    public final /* synthetic */ ItemTouchHelper.e a;
    public final /* synthetic */ int b;
    public final /* synthetic */ ItemTouchHelper c;

    public a9(ItemTouchHelper itemTouchHelper, ItemTouchHelper.e eVar, int i) {
        this.c = itemTouchHelper;
        this.a = eVar;
        this.b = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        RecyclerView recyclerView = this.c.r;
        if (recyclerView == null || !recyclerView.isAttachedToWindow()) {
            return;
        }
        ItemTouchHelper.e eVar = this.a;
        if (eVar.l || eVar.e.getAdapterPosition() == -1) {
            return;
        }
        RecyclerView.ItemAnimator itemAnimator = this.c.r.getItemAnimator();
        if (itemAnimator == null || !itemAnimator.isRunning(null)) {
            ItemTouchHelper itemTouchHelper = this.c;
            int size = itemTouchHelper.p.size();
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (!itemTouchHelper.p.get(i).m) {
                    z = true;
                    break;
                }
                i++;
            }
            if (!z) {
                this.c.m.onSwiped(this.a.e, this.b);
                return;
            }
        }
        this.c.r.post(this);
    }
}
