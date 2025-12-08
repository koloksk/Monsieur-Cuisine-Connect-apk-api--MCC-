package defpackage;

import java.util.AbstractList;
import java.util.RandomAccess;

/* loaded from: classes.dex */
public final class f9<T> extends AbstractList<T> implements RandomAccess {
    public final T[] a;

    public f9(T[] tArr) {
        this.a = tArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        for (T t : this.a) {
            if (t == obj) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    public T get(int i) {
        return this.a[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.a.length;
    }
}
