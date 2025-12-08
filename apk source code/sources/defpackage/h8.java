package defpackage;

import android.support.v4.os.TraceCompat;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class h8 implements Runnable {
    public static final ThreadLocal<h8> e = new ThreadLocal<>();
    public static Comparator<c> f = new a();
    public long b;
    public long c;
    public ArrayList<RecyclerView> a = new ArrayList<>();
    public ArrayList<c> d = new ArrayList<>();

    public static class a implements Comparator<c> {
        /* JADX WARN: Code restructure failed: missing block: B:13:0x0019, code lost:
        
            if (r6.d == null) goto L14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0025, code lost:
        
            if (r0 != false) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:?, code lost:
        
            return 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:?, code lost:
        
            return -1;
         */
        @Override // java.util.Comparator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int compare(h8.c r6, h8.c r7) {
            /*
                r5 = this;
                h8$c r6 = (h8.c) r6
                h8$c r7 = (h8.c) r7
                android.support.v7.widget.RecyclerView r0 = r6.d
                r1 = 0
                r2 = 1
                if (r0 != 0) goto Lc
                r0 = r2
                goto Ld
            Lc:
                r0 = r1
            Ld:
                android.support.v7.widget.RecyclerView r3 = r7.d
                if (r3 != 0) goto L13
                r3 = r2
                goto L14
            L13:
                r3 = r1
            L14:
                r4 = -1
                if (r0 == r3) goto L1f
                android.support.v7.widget.RecyclerView r6 = r6.d
                if (r6 != 0) goto L1d
            L1b:
                r1 = r2
                goto L39
            L1d:
                r1 = r4
                goto L39
            L1f:
                boolean r0 = r6.a
                boolean r3 = r7.a
                if (r0 == r3) goto L28
                if (r0 == 0) goto L1b
                goto L1d
            L28:
                int r0 = r7.b
                int r2 = r6.b
                int r0 = r0 - r2
                if (r0 == 0) goto L31
                r1 = r0
                goto L39
            L31:
                int r6 = r6.c
                int r7 = r7.c
                int r6 = r6 - r7
                if (r6 == 0) goto L39
                r1 = r6
            L39:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: h8.a.compare(java.lang.Object, java.lang.Object):int");
        }
    }

    public static class c {
        public boolean a;
        public int b;
        public int c;
        public RecyclerView d;
        public int e;
    }

    public void a(RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.isAttachedToWindow() && this.b == 0) {
            this.b = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        b bVar = recyclerView.g0;
        bVar.a = i;
        bVar.b = i2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            TraceCompat.beginSection("RV Prefetch");
            if (!this.a.isEmpty()) {
                int size = this.a.size();
                long jMax = 0;
                for (int i = 0; i < size; i++) {
                    RecyclerView recyclerView = this.a.get(i);
                    if (recyclerView.getWindowVisibility() == 0) {
                        jMax = Math.max(recyclerView.getDrawingTime(), jMax);
                    }
                }
                if (jMax != 0) {
                    a(TimeUnit.MILLISECONDS.toNanos(jMax) + this.c);
                }
            }
        } finally {
            this.b = 0L;
            TraceCompat.endSection();
        }
    }

    public void a(long j) {
        RecyclerView recyclerView;
        c cVar;
        int size = this.a.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            RecyclerView recyclerView2 = this.a.get(i2);
            if (recyclerView2.getWindowVisibility() == 0) {
                recyclerView2.g0.a(recyclerView2, false);
                i += recyclerView2.g0.d;
            }
        }
        this.d.ensureCapacity(i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            RecyclerView recyclerView3 = this.a.get(i4);
            if (recyclerView3.getWindowVisibility() == 0) {
                b bVar = recyclerView3.g0;
                int iAbs = Math.abs(bVar.b) + Math.abs(bVar.a);
                for (int i5 = 0; i5 < bVar.d * 2; i5 += 2) {
                    if (i3 >= this.d.size()) {
                        cVar = new c();
                        this.d.add(cVar);
                    } else {
                        cVar = this.d.get(i3);
                    }
                    int i6 = bVar.c[i5 + 1];
                    cVar.a = i6 <= iAbs;
                    cVar.b = iAbs;
                    cVar.c = i6;
                    cVar.d = recyclerView3;
                    cVar.e = bVar.c[i5];
                    i3++;
                }
            }
        }
        Collections.sort(this.d, f);
        for (int i7 = 0; i7 < this.d.size(); i7++) {
            c cVar2 = this.d.get(i7);
            if (cVar2.d == null) {
                return;
            }
            RecyclerView.ViewHolder viewHolderA = a(cVar2.d, cVar2.e, cVar2.a ? Long.MAX_VALUE : j);
            if (viewHolderA != null && viewHolderA.a != null && viewHolderA.d() && !viewHolderA.e() && (recyclerView = viewHolderA.a.get()) != null) {
                if (recyclerView.D && recyclerView.e.b() != 0) {
                    recyclerView.q();
                }
                b bVar2 = recyclerView.g0;
                bVar2.a(recyclerView, true);
                if (bVar2.d != 0) {
                    try {
                        TraceCompat.beginSection("RV Nested Prefetch");
                        RecyclerView.State state = recyclerView.h0;
                        RecyclerView.Adapter adapter2 = recyclerView.l;
                        state.e = 1;
                        state.f = adapter2.getItemCount();
                        state.h = false;
                        state.i = false;
                        state.j = false;
                        for (int i8 = 0; i8 < bVar2.d * 2; i8 += 2) {
                            a(recyclerView, bVar2.c[i8], j);
                        }
                    } finally {
                        TraceCompat.endSection();
                    }
                } else {
                    continue;
                }
            }
            cVar2.a = false;
            cVar2.b = 0;
            cVar2.c = 0;
            cVar2.d = null;
            cVar2.e = 0;
        }
    }

    public static class b implements RecyclerView.LayoutManager.LayoutPrefetchRegistry {
        public int a;
        public int b;
        public int[] c;
        public int d;

        public void a(RecyclerView recyclerView, boolean z) {
            this.d = 0;
            int[] iArr = this.c;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            RecyclerView.LayoutManager layoutManager = recyclerView.m;
            if (recyclerView.l == null || layoutManager == null || !layoutManager.isItemPrefetchEnabled()) {
                return;
            }
            if (z) {
                if (!recyclerView.d.c()) {
                    layoutManager.collectInitialPrefetchPositions(recyclerView.l.getItemCount(), this);
                }
            } else if (!recyclerView.hasPendingAdapterUpdates()) {
                layoutManager.collectAdjacentPrefetchPositions(this.a, this.b, recyclerView.h0, this);
            }
            int i = this.d;
            if (i > layoutManager.m) {
                layoutManager.m = i;
                layoutManager.n = z;
                recyclerView.b.c();
            }
        }

        @Override // android.support.v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry
        public void addPosition(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            }
            if (i2 < 0) {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            }
            int i3 = this.d * 2;
            int[] iArr = this.c;
            if (iArr == null) {
                int[] iArr2 = new int[4];
                this.c = iArr2;
                Arrays.fill(iArr2, -1);
            } else if (i3 >= iArr.length) {
                int[] iArr3 = new int[i3 * 2];
                this.c = iArr3;
                System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            }
            int[] iArr4 = this.c;
            iArr4[i3] = i;
            iArr4[i3 + 1] = i2;
            this.d++;
        }

        public boolean a(int i) {
            if (this.c != null) {
                int i2 = this.d * 2;
                for (int i3 = 0; i3 < i2; i3 += 2) {
                    if (this.c[i3] == i) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public final RecyclerView.ViewHolder a(RecyclerView recyclerView, int i, long j) {
        boolean z;
        int iB = recyclerView.e.b();
        int i2 = 0;
        while (true) {
            if (i2 >= iB) {
                z = false;
                break;
            }
            RecyclerView.ViewHolder viewHolderD = RecyclerView.d(recyclerView.e.d(i2));
            if (viewHolderD.b == i && !viewHolderD.e()) {
                z = true;
                break;
            }
            i2++;
        }
        if (z) {
            return null;
        }
        RecyclerView.Recycler recycler = recyclerView.b;
        try {
            recyclerView.n();
            RecyclerView.ViewHolder viewHolderA = recycler.a(i, false, j);
            if (viewHolderA != null) {
                if (viewHolderA.d() && !viewHolderA.e()) {
                    recycler.recycleView(viewHolderA.itemView);
                } else {
                    recycler.a(viewHolderA, false);
                }
            }
            return viewHolderA;
        } finally {
            recyclerView.a(false);
        }
    }
}
