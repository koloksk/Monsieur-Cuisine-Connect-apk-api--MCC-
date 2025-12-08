package defpackage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import defpackage.o7;

/* loaded from: classes.dex */
public class n8 implements o7.a {
    public final /* synthetic */ RecyclerView a;

    public n8(RecyclerView recyclerView) {
        this.a = recyclerView;
    }

    public RecyclerView.ViewHolder a(int i) {
        RecyclerView.ViewHolder viewHolderA = this.a.a(i, true);
        if (viewHolderA == null || this.a.e.c(viewHolderA.itemView)) {
            return null;
        }
        return viewHolderA;
    }

    public void b(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        RecyclerView recyclerView = this.a;
        int iB = recyclerView.e.b();
        int i10 = -1;
        if (i < i2) {
            i4 = i;
            i3 = i2;
            i5 = -1;
        } else {
            i3 = i;
            i4 = i2;
            i5 = 1;
        }
        for (int i11 = 0; i11 < iB; i11++) {
            RecyclerView.ViewHolder viewHolderD = RecyclerView.d(recyclerView.e.d(i11));
            if (viewHolderD != null && (i9 = viewHolderD.b) >= i4 && i9 <= i3) {
                if (i9 == i) {
                    viewHolderD.a(i2 - i, false);
                } else {
                    viewHolderD.a(i5, false);
                }
                recyclerView.h0.g = true;
            }
        }
        RecyclerView.Recycler recycler = recyclerView.b;
        if (i < i2) {
            i7 = i;
            i6 = i2;
        } else {
            i6 = i;
            i7 = i2;
            i10 = 1;
        }
        int size = recycler.c.size();
        for (int i12 = 0; i12 < size; i12++) {
            RecyclerView.ViewHolder viewHolder = recycler.c.get(i12);
            if (viewHolder != null && (i8 = viewHolder.b) >= i7 && i8 <= i6) {
                if (i8 == i) {
                    viewHolder.a(i2 - i, false);
                } else {
                    viewHolder.a(i10, false);
                }
            }
        }
        recyclerView.requestLayout();
        this.a.k0 = true;
    }

    public void a(int i, int i2, Object obj) {
        int i3;
        int i4;
        RecyclerView recyclerView = this.a;
        int iB = recyclerView.e.b();
        int i5 = i2 + i;
        for (int i6 = 0; i6 < iB; i6++) {
            View viewD = recyclerView.e.d(i6);
            RecyclerView.ViewHolder viewHolderD = RecyclerView.d(viewD);
            if (viewHolderD != null && !viewHolderD.l() && (i4 = viewHolderD.b) >= i && i4 < i5) {
                viewHolderD.a(2);
                viewHolderD.a(obj);
                ((RecyclerView.LayoutParams) viewD.getLayoutParams()).c = true;
            }
        }
        RecyclerView.Recycler recycler = recyclerView.b;
        int size = recycler.c.size();
        while (true) {
            size--;
            if (size >= 0) {
                RecyclerView.ViewHolder viewHolder = recycler.c.get(size);
                if (viewHolder != null && (i3 = viewHolder.b) >= i && i3 < i5) {
                    viewHolder.a(2);
                    recycler.a(size);
                }
            } else {
                this.a.l0 = true;
                return;
            }
        }
    }

    public void a(o7.b bVar) {
        int i = bVar.a;
        if (i == 1) {
            RecyclerView recyclerView = this.a;
            recyclerView.m.onItemsAdded(recyclerView, bVar.b, bVar.d);
            return;
        }
        if (i == 2) {
            RecyclerView recyclerView2 = this.a;
            recyclerView2.m.onItemsRemoved(recyclerView2, bVar.b, bVar.d);
        } else if (i == 4) {
            RecyclerView recyclerView3 = this.a;
            recyclerView3.m.onItemsUpdated(recyclerView3, bVar.b, bVar.d, bVar.c);
        } else {
            if (i != 8) {
                return;
            }
            RecyclerView recyclerView4 = this.a;
            recyclerView4.m.onItemsMoved(recyclerView4, bVar.b, bVar.d, 1);
        }
    }

    public void a(int i, int i2) {
        RecyclerView recyclerView = this.a;
        int iB = recyclerView.e.b();
        for (int i3 = 0; i3 < iB; i3++) {
            RecyclerView.ViewHolder viewHolderD = RecyclerView.d(recyclerView.e.d(i3));
            if (viewHolderD != null && !viewHolderD.l() && viewHolderD.b >= i) {
                viewHolderD.a(i2, false);
                recyclerView.h0.g = true;
            }
        }
        RecyclerView.Recycler recycler = recyclerView.b;
        int size = recycler.c.size();
        for (int i4 = 0; i4 < size; i4++) {
            RecyclerView.ViewHolder viewHolder = recycler.c.get(i4);
            if (viewHolder != null && viewHolder.b >= i) {
                viewHolder.a(i2, true);
            }
        }
        recyclerView.requestLayout();
        this.a.k0 = true;
    }
}
