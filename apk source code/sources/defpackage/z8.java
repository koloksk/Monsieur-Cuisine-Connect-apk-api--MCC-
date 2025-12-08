package defpackage;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.Pools;
import android.support.v7.widget.RecyclerView;

/* loaded from: classes.dex */
public class z8 {

    @VisibleForTesting
    public final ArrayMap<RecyclerView.ViewHolder, a> a = new ArrayMap<>();

    @VisibleForTesting
    public final LongSparseArray<RecyclerView.ViewHolder> b = new LongSparseArray<>();

    public interface b {
    }

    public final RecyclerView.ItemAnimator.ItemHolderInfo a(RecyclerView.ViewHolder viewHolder, int i) {
        a aVarValueAt;
        RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo;
        int iIndexOfKey = this.a.indexOfKey(viewHolder);
        if (iIndexOfKey >= 0 && (aVarValueAt = this.a.valueAt(iIndexOfKey)) != null) {
            int i2 = aVarValueAt.a;
            if ((i2 & i) != 0) {
                aVarValueAt.a = (~i) & i2;
                if (i == 4) {
                    itemHolderInfo = aVarValueAt.b;
                } else {
                    if (i != 8) {
                        throw new IllegalArgumentException("Must provide flag PRE or POST");
                    }
                    itemHolderInfo = aVarValueAt.c;
                }
                if ((aVarValueAt.a & 12) == 0) {
                    this.a.removeAt(iIndexOfKey);
                    a.a(aVarValueAt);
                }
                return itemHolderInfo;
            }
        }
        return null;
    }

    public void b(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        a aVarA = this.a.get(viewHolder);
        if (aVarA == null) {
            aVarA = a.a();
            this.a.put(viewHolder, aVarA);
        }
        aVarA.b = itemHolderInfo;
        aVarA.a |= 4;
    }

    public void c(RecyclerView.ViewHolder viewHolder) {
        a aVar = this.a.get(viewHolder);
        if (aVar == null) {
            return;
        }
        aVar.a &= -2;
    }

    public void d(RecyclerView.ViewHolder viewHolder) {
        int size = this.b.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (viewHolder == this.b.valueAt(size)) {
                this.b.removeAt(size);
                break;
            }
            size--;
        }
        a aVarRemove = this.a.remove(viewHolder);
        if (aVarRemove != null) {
            a.a(aVarRemove);
        }
    }

    public static class a {
        public static Pools.Pool<a> d = new Pools.SimplePool(20);
        public int a;

        @Nullable
        public RecyclerView.ItemAnimator.ItemHolderInfo b;

        @Nullable
        public RecyclerView.ItemAnimator.ItemHolderInfo c;

        public static a a() {
            a aVarAcquire = d.acquire();
            return aVarAcquire == null ? new a() : aVarAcquire;
        }

        public static void a(a aVar) {
            aVar.a = 0;
            aVar.b = null;
            aVar.c = null;
            d.release(aVar);
        }
    }

    public boolean b(RecyclerView.ViewHolder viewHolder) {
        a aVar = this.a.get(viewHolder);
        return (aVar == null || (aVar.a & 1) == 0) ? false : true;
    }

    public void a(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        a aVarA = this.a.get(viewHolder);
        if (aVarA == null) {
            aVarA = a.a();
            this.a.put(viewHolder, aVarA);
        }
        aVarA.c = itemHolderInfo;
        aVarA.a |= 8;
    }

    public void a(RecyclerView.ViewHolder viewHolder) {
        a aVarA = this.a.get(viewHolder);
        if (aVarA == null) {
            aVarA = a.a();
            this.a.put(viewHolder, aVarA);
        }
        aVarA.a |= 1;
    }
}
