package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.util.Pools;
import android.support.v4.util.SimpleArrayMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class DirectedAcyclicGraph<T> {
    public final Pools.Pool<ArrayList<T>> a = new Pools.SimplePool(10);
    public final SimpleArrayMap<T, ArrayList<T>> b = new SimpleArrayMap<>();
    public final ArrayList<T> c = new ArrayList<>();
    public final HashSet<T> d = new HashSet<>();

    public final void a(T t, ArrayList<T> arrayList, HashSet<T> hashSet) {
        if (arrayList.contains(t)) {
            return;
        }
        if (hashSet.contains(t)) {
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
        hashSet.add(t);
        ArrayList<T> arrayList2 = this.b.get(t);
        if (arrayList2 != null) {
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                a(arrayList2.get(i), arrayList, hashSet);
            }
        }
        hashSet.remove(t);
        arrayList.add(t);
    }

    public void addEdge(@NonNull T t, @NonNull T t2) {
        if (!this.b.containsKey(t) || !this.b.containsKey(t2)) {
            throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
        }
        ArrayList<T> arrayListAcquire = this.b.get(t);
        if (arrayListAcquire == null) {
            arrayListAcquire = this.a.acquire();
            if (arrayListAcquire == null) {
                arrayListAcquire = new ArrayList<>();
            }
            this.b.put(t, arrayListAcquire);
        }
        arrayListAcquire.add(t2);
    }

    public void addNode(@NonNull T t) {
        if (this.b.containsKey(t)) {
            return;
        }
        this.b.put(t, null);
    }

    public void clear() {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            ArrayList<T> arrayListValueAt = this.b.valueAt(i);
            if (arrayListValueAt != null) {
                arrayListValueAt.clear();
                this.a.release(arrayListValueAt);
            }
        }
        this.b.clear();
    }

    public boolean contains(@NonNull T t) {
        return this.b.containsKey(t);
    }

    @Nullable
    public List getIncomingEdges(@NonNull T t) {
        return this.b.get(t);
    }

    @Nullable
    public List<T> getOutgoingEdges(@NonNull T t) {
        int size = this.b.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            ArrayList<T> arrayListValueAt = this.b.valueAt(i);
            if (arrayListValueAt != null && arrayListValueAt.contains(t)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(this.b.keyAt(i));
            }
        }
        return arrayList;
    }

    @NonNull
    public ArrayList<T> getSortedList() {
        this.c.clear();
        this.d.clear();
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            a(this.b.keyAt(i), this.c, this.d);
        }
        return this.c;
    }

    public boolean hasOutgoingEdges(@NonNull T t) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            ArrayList<T> arrayListValueAt = this.b.valueAt(i);
            if (arrayListValueAt != null && arrayListValueAt.contains(t)) {
                return true;
            }
        }
        return false;
    }
}
