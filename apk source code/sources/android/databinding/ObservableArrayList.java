package android.databinding;

import android.databinding.ObservableList;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes.dex */
public class ObservableArrayList<T> extends ArrayList<T> implements ObservableList<T> {
    public transient ListChangeRegistry a = new ListChangeRegistry();

    public final void a(int i, int i2) {
        ListChangeRegistry listChangeRegistry = this.a;
        if (listChangeRegistry != null) {
            listChangeRegistry.notifyInserted(this, i, i2);
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(T t) {
        super.add(t);
        a(size() - 1, 1);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends T> collection) {
        int size = size();
        boolean zAddAll = super.addAll(collection);
        if (zAddAll) {
            a(size, size() - size);
        }
        return zAddAll;
    }

    @Override // android.databinding.ObservableList
    public void addOnListChangedCallback(ObservableList.OnListChangedCallback onListChangedCallback) {
        if (this.a == null) {
            this.a = new ListChangeRegistry();
        }
        this.a.add(onListChangedCallback);
    }

    public final void b(int i, int i2) {
        ListChangeRegistry listChangeRegistry = this.a;
        if (listChangeRegistry != null) {
            listChangeRegistry.notifyRemoved(this, i, i2);
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        int size = size();
        super.clear();
        if (size != 0) {
            b(0, size);
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public T remove(int i) {
        T t = (T) super.remove(i);
        ListChangeRegistry listChangeRegistry = this.a;
        if (listChangeRegistry != null) {
            listChangeRegistry.notifyRemoved(this, i, 1);
        }
        return t;
    }

    @Override // android.databinding.ObservableList
    public void removeOnListChangedCallback(ObservableList.OnListChangedCallback onListChangedCallback) {
        ListChangeRegistry listChangeRegistry = this.a;
        if (listChangeRegistry != null) {
            listChangeRegistry.remove(onListChangedCallback);
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList
    public void removeRange(int i, int i2) {
        super.removeRange(i, i2);
        int i3 = i2 - i;
        ListChangeRegistry listChangeRegistry = this.a;
        if (listChangeRegistry != null) {
            listChangeRegistry.notifyRemoved(this, i, i3);
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public T set(int i, T t) {
        T t2 = (T) super.set(i, t);
        ListChangeRegistry listChangeRegistry = this.a;
        if (listChangeRegistry != null) {
            listChangeRegistry.notifyChanged(this, i, 1);
        }
        return t2;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public void add(int i, T t) {
        super.add(i, t);
        ListChangeRegistry listChangeRegistry = this.a;
        if (listChangeRegistry != null) {
            listChangeRegistry.notifyInserted(this, i, 1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends T> collection) {
        boolean zAddAll = super.addAll(i, collection);
        if (zAddAll) {
            int size = collection.size();
            ListChangeRegistry listChangeRegistry = this.a;
            if (listChangeRegistry != null) {
                listChangeRegistry.notifyInserted(this, i, size);
            }
        }
        return zAddAll;
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return false;
        }
        remove(iIndexOf);
        return true;
    }
}
