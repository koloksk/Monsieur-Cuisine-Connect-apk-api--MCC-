package android.arch.core.internal;

import android.arch.core.internal.SafeIterableMap;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class FastSafeIterableMap<K, V> extends SafeIterableMap<K, V> {
    public HashMap<K, SafeIterableMap.d<K, V>> e = new HashMap<>();

    public Map.Entry<K, V> ceil(K k) {
        if (contains(k)) {
            return this.e.get(k).d;
        }
        return null;
    }

    public boolean contains(K k) {
        return this.e.containsKey(k);
    }

    @Override // android.arch.core.internal.SafeIterableMap
    public SafeIterableMap.d<K, V> get(K k) {
        return this.e.get(k);
    }

    @Override // android.arch.core.internal.SafeIterableMap
    public V putIfAbsent(@NonNull K k, @NonNull V v) {
        SafeIterableMap.d<K, V> dVar = get(k);
        if (dVar != null) {
            return dVar.b;
        }
        this.e.put(k, put(k, v));
        return null;
    }

    @Override // android.arch.core.internal.SafeIterableMap
    public V remove(@NonNull K k) {
        V v = (V) super.remove(k);
        this.e.remove(k);
        return v;
    }
}
