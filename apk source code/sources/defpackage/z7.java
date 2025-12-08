package defpackage;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class z7 {
    public final b a;
    public final a b = new a();
    public final List<View> c = new ArrayList();

    public interface b {
    }

    public z7(b bVar) {
        this.a = bVar;
    }

    public final void a(View view2) {
        this.c.add(view2);
        m8 m8Var = (m8) this.a;
        if (m8Var == null) {
            throw null;
        }
        RecyclerView.ViewHolder viewHolderD = RecyclerView.d(view2);
        if (viewHolderD != null) {
            RecyclerView recyclerView = m8Var.a;
            int i = viewHolderD.p;
            if (i != -1) {
                viewHolderD.o = i;
            } else {
                viewHolderD.o = ViewCompat.getImportantForAccessibility(viewHolderD.itemView);
            }
            recyclerView.a(viewHolderD, 4);
        }
    }

    public View b(int i) {
        return ((m8) this.a).a(c(i));
    }

    public final int c(int i) {
        if (i < 0) {
            return -1;
        }
        int iA = ((m8) this.a).a();
        int i2 = i;
        while (i2 < iA) {
            int iB = i - (i2 - this.b.b(i2));
            if (iB == 0) {
                while (this.b.c(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += iB;
        }
        return -1;
    }

    public final boolean d(View view2) {
        if (!this.c.remove(view2)) {
            return false;
        }
        m8 m8Var = (m8) this.a;
        if (m8Var == null) {
            throw null;
        }
        RecyclerView.ViewHolder viewHolderD = RecyclerView.d(view2);
        if (viewHolderD == null) {
            return true;
        }
        m8Var.a.a(viewHolderD, viewHolderD.o);
        viewHolderD.o = 0;
        return true;
    }

    public void e(int i) {
        int iC = c(i);
        View viewA = ((m8) this.a).a(iC);
        if (viewA == null) {
            return;
        }
        if (this.b.d(iC)) {
            d(viewA);
        }
        ((m8) this.a).b(iC);
    }

    public String toString() {
        return this.b.toString() + ", hidden list:" + this.c.size();
    }

    public static class a {
        public long a = 0;
        public a b;

        public final void a() {
            if (this.b == null) {
                this.b = new a();
            }
        }

        public void b() {
            this.a = 0L;
            a aVar = this.b;
            if (aVar != null) {
                aVar.b();
            }
        }

        public boolean c(int i) {
            if (i < 64) {
                return (this.a & (1 << i)) != 0;
            }
            a();
            return this.b.c(i - 64);
        }

        public boolean d(int i) {
            if (i >= 64) {
                a();
                return this.b.d(i - 64);
            }
            long j = 1 << i;
            boolean z = (this.a & j) != 0;
            long j2 = this.a & (~j);
            this.a = j2;
            long j3 = j - 1;
            this.a = (j2 & j3) | Long.rotateRight((~j3) & j2, 1);
            a aVar = this.b;
            if (aVar != null) {
                if (aVar.c(0)) {
                    e(63);
                }
                this.b.d(0);
            }
            return z;
        }

        public void e(int i) {
            if (i < 64) {
                this.a |= 1 << i;
            } else {
                a();
                this.b.e(i - 64);
            }
        }

        public String toString() {
            if (this.b == null) {
                return Long.toBinaryString(this.a);
            }
            return this.b.toString() + "xx" + Long.toBinaryString(this.a);
        }

        public void a(int i) {
            if (i >= 64) {
                a aVar = this.b;
                if (aVar != null) {
                    aVar.a(i - 64);
                    return;
                }
                return;
            }
            this.a &= ~(1 << i);
        }

        public int b(int i) {
            a aVar = this.b;
            if (aVar == null) {
                if (i >= 64) {
                    return Long.bitCount(this.a);
                }
                return Long.bitCount(this.a & ((1 << i) - 1));
            }
            if (i < 64) {
                return Long.bitCount(this.a & ((1 << i) - 1));
            }
            return Long.bitCount(this.a) + aVar.b(i - 64);
        }

        public void a(int i, boolean z) {
            if (i >= 64) {
                a();
                this.b.a(i - 64, z);
                return;
            }
            boolean z2 = (this.a & Long.MIN_VALUE) != 0;
            long j = (1 << i) - 1;
            long j2 = this.a;
            this.a = ((j2 & (~j)) << 1) | (j2 & j);
            if (z) {
                e(i);
            } else {
                a(i);
            }
            if (z2 || this.b != null) {
                a();
                this.b.a(0, z2);
            }
        }
    }

    public int b() {
        return ((m8) this.a).a();
    }

    public int b(View view2) {
        int iIndexOfChild = ((m8) this.a).a.indexOfChild(view2);
        if (iIndexOfChild == -1 || this.b.c(iIndexOfChild)) {
            return -1;
        }
        return iIndexOfChild - this.b.b(iIndexOfChild);
    }

    public boolean c(View view2) {
        return this.c.contains(view2);
    }

    public View d(int i) {
        return ((m8) this.a).a.getChildAt(i);
    }

    public void a(View view2, int i, boolean z) {
        int iC;
        if (i < 0) {
            iC = ((m8) this.a).a();
        } else {
            iC = c(i);
        }
        this.b.a(iC, z);
        if (z) {
            a(view2);
        }
        m8 m8Var = (m8) this.a;
        m8Var.a.addView(view2, iC);
        RecyclerView recyclerView = m8Var.a;
        if (recyclerView != null) {
            RecyclerView.ViewHolder viewHolderD = RecyclerView.d(view2);
            recyclerView.onChildAttachedToWindow(view2);
            RecyclerView.Adapter adapter2 = recyclerView.l;
            if (adapter2 != null && viewHolderD != null) {
                adapter2.onViewAttachedToWindow(viewHolderD);
            }
            List<RecyclerView.OnChildAttachStateChangeListener> list = recyclerView.C;
            if (list == null) {
                return;
            }
            int size = list.size();
            while (true) {
                size--;
                if (size < 0) {
                    return;
                } else {
                    recyclerView.C.get(size).onChildViewAttachedToWindow(view2);
                }
            }
        } else {
            throw null;
        }
    }

    public void a(View view2, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        int iC;
        if (i < 0) {
            iC = ((m8) this.a).a();
        } else {
            iC = c(i);
        }
        this.b.a(iC, z);
        if (z) {
            a(view2);
        }
        m8 m8Var = (m8) this.a;
        if (m8Var != null) {
            RecyclerView.ViewHolder viewHolderD = RecyclerView.d(view2);
            if (viewHolderD != null) {
                if (!viewHolderD.h() && !viewHolderD.l()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Called attach on a child which is not detached: ");
                    sb.append(viewHolderD);
                    throw new IllegalArgumentException(g9.a(m8Var.a, sb));
                }
                viewHolderD.i &= -257;
            }
            m8Var.a.attachViewToParent(view2, iC, layoutParams);
            return;
        }
        throw null;
    }

    public int a() {
        return ((m8) this.a).a() - this.c.size();
    }

    public void a(int i) {
        RecyclerView.ViewHolder viewHolderD;
        int iC = c(i);
        this.b.d(iC);
        m8 m8Var = (m8) this.a;
        View childAt = m8Var.a.getChildAt(iC);
        if (childAt != null && (viewHolderD = RecyclerView.d(childAt)) != null) {
            if (viewHolderD.h() && !viewHolderD.l()) {
                StringBuilder sb = new StringBuilder();
                sb.append("called detach on an already detached child ");
                sb.append(viewHolderD);
                throw new IllegalArgumentException(g9.a(m8Var.a, sb));
            }
            viewHolderD.a(256);
        }
        m8Var.a.detachViewFromParent(iC);
    }
}
