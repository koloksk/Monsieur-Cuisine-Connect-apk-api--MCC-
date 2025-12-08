package android.support.v7.util;

import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.widget.RecyclerView;
import defpackage.g9;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class DiffUtil {
    public static final Comparator<d> a = new a();

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        @Nullable
        public Object getChangePayload(int i, int i2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    public static class DiffResult {
        public final List<d> a;
        public final int[] b;
        public final int[] c;
        public final Callback d;
        public final int e;
        public final int f;
        public final boolean g;

        public DiffResult(Callback callback, List<d> list, int[] iArr, int[] iArr2, boolean z) {
            this.a = list;
            this.b = iArr;
            this.c = iArr2;
            Arrays.fill(iArr, 0);
            Arrays.fill(this.c, 0);
            this.d = callback;
            AsyncListDiffer.a.C0006a c0006a = (AsyncListDiffer.a.C0006a) callback;
            this.e = AsyncListDiffer.a.this.a.size();
            this.f = AsyncListDiffer.a.this.b.size();
            this.g = z;
            d dVar = this.a.isEmpty() ? null : this.a.get(0);
            if (dVar == null || dVar.a != 0 || dVar.b != 0) {
                d dVar2 = new d();
                dVar2.a = 0;
                dVar2.b = 0;
                dVar2.d = false;
                dVar2.c = 0;
                dVar2.e = false;
                this.a.add(0, dVar2);
            }
            int i = this.e;
            int i2 = this.f;
            for (int size = this.a.size() - 1; size >= 0; size--) {
                d dVar3 = this.a.get(size);
                int i3 = dVar3.a;
                int i4 = dVar3.c;
                int i5 = i3 + i4;
                int i6 = dVar3.b + i4;
                if (this.g) {
                    while (i > i5) {
                        int i7 = i - 1;
                        if (this.b[i7] == 0) {
                            a(i, i2, size, false);
                        }
                        i = i7;
                    }
                    while (i2 > i6) {
                        int i8 = i2 - 1;
                        if (this.c[i8] == 0) {
                            a(i, i2, size, true);
                        }
                        i2 = i8;
                    }
                }
                for (int i9 = 0; i9 < dVar3.c; i9++) {
                    int i10 = dVar3.a + i9;
                    int i11 = dVar3.b + i9;
                    int i12 = this.d.areContentsTheSame(i10, i11) ? 1 : 2;
                    this.b[i10] = (i11 << 5) | i12;
                    this.c[i11] = (i10 << 5) | i12;
                }
                i = dVar3.a;
                i2 = dVar3.b;
            }
        }

        public final boolean a(int i, int i2, int i3, boolean z) {
            int i4;
            int i5;
            int i6;
            if (z) {
                i2--;
                i5 = i;
                i4 = i2;
            } else {
                i4 = i - 1;
                i5 = i4;
            }
            while (i3 >= 0) {
                d dVar = this.a.get(i3);
                int i7 = dVar.a;
                int i8 = dVar.c;
                int i9 = i7 + i8;
                int i10 = dVar.b + i8;
                if (z) {
                    for (int i11 = i5 - 1; i11 >= i9; i11--) {
                        if (this.d.areItemsTheSame(i11, i4)) {
                            i6 = this.d.areContentsTheSame(i11, i4) ? 8 : 4;
                            this.c[i4] = (i11 << 5) | 16;
                            this.b[i11] = (i4 << 5) | i6;
                            return true;
                        }
                    }
                } else {
                    for (int i12 = i2 - 1; i12 >= i10; i12--) {
                        if (this.d.areItemsTheSame(i4, i12)) {
                            i6 = this.d.areContentsTheSame(i4, i12) ? 8 : 4;
                            int i13 = i - 1;
                            this.b[i13] = (i12 << 5) | 16;
                            this.c[i12] = (i13 << 5) | i6;
                            return true;
                        }
                    }
                }
                i5 = dVar.a;
                i2 = dVar.b;
                i3--;
            }
            return false;
        }

        public void dispatchUpdatesTo(RecyclerView.Adapter adapter2) {
            dispatchUpdatesTo(new AdapterListUpdateCallback(adapter2));
        }

        public void dispatchUpdatesTo(ListUpdateCallback listUpdateCallback) {
            int i;
            d dVar;
            BatchingListUpdateCallback batchingListUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback) listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            ArrayList arrayList = new ArrayList();
            int i2 = this.e;
            int i3 = this.f;
            int size = this.a.size() - 1;
            while (size >= 0) {
                d dVar2 = this.a.get(size);
                int i4 = dVar2.c;
                int i5 = dVar2.a + i4;
                int i6 = dVar2.b + i4;
                int i7 = 8;
                int i8 = 4;
                if (i5 < i2) {
                    int i9 = i2 - i5;
                    if (this.g) {
                        int i10 = i9 - 1;
                        while (i10 >= 0) {
                            int i11 = i5 + i10;
                            int i12 = this.b[i11] & 31;
                            if (i12 == 0) {
                                i = size;
                                dVar = dVar2;
                                int i13 = 1;
                                batchingListUpdateCallback.onRemoved(i11, 1);
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    ((b) it.next()).b -= i13;
                                    i13 = 1;
                                }
                            } else if (i12 == i8 || i12 == i7) {
                                int i14 = this.b[i11] >> 5;
                                b bVarA = a(arrayList, i14, false);
                                i = size;
                                dVar = dVar2;
                                batchingListUpdateCallback.onMoved(i11, bVarA.b - 1);
                                if (i12 == 4) {
                                    batchingListUpdateCallback.onChanged(bVarA.b - 1, 1, this.d.getChangePayload(i11, i14));
                                }
                            } else {
                                if (i12 != 16) {
                                    StringBuilder sbA = g9.a("unknown flag for pos ", i11, StringUtils.SPACE);
                                    sbA.append(Long.toBinaryString(i12));
                                    throw new IllegalStateException(sbA.toString());
                                }
                                arrayList.add(new b(i11, i11, true));
                                i = size;
                                dVar = dVar2;
                            }
                            i10--;
                            i8 = 4;
                            i7 = 8;
                            size = i;
                            dVar2 = dVar;
                        }
                    } else {
                        batchingListUpdateCallback.onRemoved(i5, i9);
                    }
                }
                int i15 = size;
                d dVar3 = dVar2;
                if (i6 < i3) {
                    int i16 = i3 - i6;
                    if (this.g) {
                        while (true) {
                            i16--;
                            if (i16 < 0) {
                                break;
                            }
                            int i17 = i6 + i16;
                            int i18 = this.c[i17] & 31;
                            if (i18 == 0) {
                                int i19 = 1;
                                batchingListUpdateCallback.onInserted(i5, 1);
                                Iterator it2 = arrayList.iterator();
                                while (it2.hasNext()) {
                                    ((b) it2.next()).b += i19;
                                    i19 = 1;
                                }
                            } else if (i18 == 4 || i18 == 8) {
                                int i20 = this.c[i17] >> 5;
                                batchingListUpdateCallback.onMoved(a(arrayList, i20, true).b, i5);
                                if (i18 == 4) {
                                    batchingListUpdateCallback.onChanged(i5, 1, this.d.getChangePayload(i20, i17));
                                }
                            } else {
                                if (i18 != 16) {
                                    StringBuilder sbA2 = g9.a("unknown flag for pos ", i17, StringUtils.SPACE);
                                    sbA2.append(Long.toBinaryString(i18));
                                    throw new IllegalStateException(sbA2.toString());
                                }
                                arrayList.add(new b(i17, i5, false));
                            }
                        }
                    } else {
                        batchingListUpdateCallback.onInserted(i5, i16);
                    }
                }
                int i21 = i4 - 1;
                while (i21 >= 0) {
                    int[] iArr = this.b;
                    d dVar4 = dVar3;
                    int i22 = dVar4.a + i21;
                    if ((iArr[i22] & 31) == 2) {
                        batchingListUpdateCallback.onChanged(i22, 1, this.d.getChangePayload(i22, dVar4.b + i21));
                    }
                    i21--;
                    dVar3 = dVar4;
                }
                d dVar5 = dVar3;
                i2 = dVar5.a;
                i3 = dVar5.b;
                size = i15 - 1;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }

        public static b a(List<b> list, int i, boolean z) {
            int size = list.size() - 1;
            while (size >= 0) {
                b bVar = list.get(size);
                if (bVar.a == i && bVar.c == z) {
                    list.remove(size);
                    while (size < list.size()) {
                        list.get(size).b += z ? 1 : -1;
                        size++;
                    }
                    return bVar;
                }
                size--;
            }
            return null;
        }
    }

    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(T t, T t2);

        public abstract boolean areItemsTheSame(T t, T t2);

        public Object getChangePayload(T t, T t2) {
            return null;
        }
    }

    public static class a implements Comparator<d> {
        @Override // java.util.Comparator
        public int compare(d dVar, d dVar2) {
            d dVar3 = dVar;
            d dVar4 = dVar2;
            int i = dVar3.a - dVar4.a;
            return i == 0 ? dVar3.b - dVar4.b : i;
        }
    }

    public static class b {
        public int a;
        public int b;
        public boolean c;

        public b(int i, int i2, boolean z) {
            this.a = i;
            this.b = i2;
            this.c = z;
        }
    }

    public static class c {
        public int a;
        public int b;
        public int c;
        public int d;

        public c() {
        }

        public c(int i, int i2, int i3, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
        }
    }

    public static class d {
        public int a;
        public int b;
        public int c;
        public boolean d;
        public boolean e;
    }

    public static DiffResult calculateDiff(Callback callback) {
        return calculateDiff(callback, true);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:27:0x00c4
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:195)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:62)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:124)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:124)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public static android.support.v7.util.DiffUtil.DiffResult calculateDiff(android.support.v7.util.DiffUtil.Callback r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 658
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.util.DiffUtil.calculateDiff(android.support.v7.util.DiffUtil$Callback, boolean):android.support.v7.util.DiffUtil$DiffResult");
    }
}
