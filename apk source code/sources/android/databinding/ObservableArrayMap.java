package android.databinding;

import android.databinding.ObservableMap;
import android.support.v4.util.ArrayMap;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ObservableArrayMap<K, V> extends ArrayMap<K, V> implements ObservableMap<K, V> {
    public transient MapChangeRegistry i;

    @Override // android.databinding.ObservableMap
    public void addOnMapChangedCallback(ObservableMap.OnMapChangedCallback<? extends ObservableMap<K, V>, K, V> onMapChangedCallback) {
        if (this.i == null) {
            this.i = new MapChangeRegistry();
        }
        this.i.add(onMapChangedCallback);
    }

    public final void b(Object obj) {
        MapChangeRegistry mapChangeRegistry = this.i;
        if (mapChangeRegistry != null) {
            mapChangeRegistry.notifyCallbacks(this, 0, obj);
        }
    }

    @Override // android.support.v4.util.SimpleArrayMap, java.util.Map
    public void clear() {
        if (isEmpty()) {
            return;
        }
        super.clear();
        b(null);
    }

    @Override // android.support.v4.util.SimpleArrayMap, java.util.Map
    public V put(K k, V v) {
        super.put(k, v);
        MapChangeRegistry mapChangeRegistry = this.i;
        if (mapChangeRegistry != null) {
            mapChangeRegistry.notifyCallbacks(this, 0, k);
        }
        return v;
    }

    @Override // android.support.v4.util.ArrayMap
    public boolean removeAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            int iIndexOfKey = indexOfKey(it.next());
            if (iIndexOfKey >= 0) {
                z = true;
                removeAt(iIndexOfKey);
            }
        }
        return z;
    }

    @Override // android.support.v4.util.SimpleArrayMap
    public V removeAt(int i) {
        K kKeyAt = keyAt(i);
        V v = (V) super.removeAt(i);
        if (v != null) {
            b(kKeyAt);
        }
        return v;
    }

    @Override // android.databinding.ObservableMap
    public void removeOnMapChangedCallback(ObservableMap.OnMapChangedCallback<? extends ObservableMap<K, V>, K, V> onMapChangedCallback) {
        MapChangeRegistry mapChangeRegistry = this.i;
        if (mapChangeRegistry != null) {
            mapChangeRegistry.remove(onMapChangedCallback);
        }
    }

    @Override // android.support.v4.util.ArrayMap
    public boolean retainAll(Collection<?> collection) {
        boolean z = false;
        for (int size = size() - 1; size >= 0; size--) {
            if (!collection.contains(keyAt(size))) {
                removeAt(size);
                z = true;
            }
        }
        return z;
    }

    @Override // android.support.v4.util.SimpleArrayMap
    public V setValueAt(int i, V v) {
        K kKeyAt = keyAt(i);
        V v2 = (V) super.setValueAt(i, v);
        b(kKeyAt);
        return v2;
    }
}
