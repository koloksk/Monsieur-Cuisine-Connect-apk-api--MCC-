package android.arch.core.internal;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import defpackage.g9;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SafeIterableMap<K, V> implements Iterable<Map.Entry<K, V>> {
    public d<K, V> a;
    public d<K, V> b;
    public WeakHashMap<g<K, V>, Boolean> c = new WeakHashMap<>();
    public int d = 0;

    public static class b<K, V> extends f<K, V> {
        public b(d<K, V> dVar, d<K, V> dVar2) {
            super(dVar, dVar2);
        }

        @Override // android.arch.core.internal.SafeIterableMap.f
        public d<K, V> b(d<K, V> dVar) {
            return dVar.d;
        }

        @Override // android.arch.core.internal.SafeIterableMap.f
        public d<K, V> c(d<K, V> dVar) {
            return dVar.c;
        }
    }

    public static class c<K, V> extends f<K, V> {
        public c(d<K, V> dVar, d<K, V> dVar2) {
            super(dVar, dVar2);
        }

        @Override // android.arch.core.internal.SafeIterableMap.f
        public d<K, V> b(d<K, V> dVar) {
            return dVar.c;
        }

        @Override // android.arch.core.internal.SafeIterableMap.f
        public d<K, V> c(d<K, V> dVar) {
            return dVar.d;
        }
    }

    public static class d<K, V> implements Map.Entry<K, V> {

        @NonNull
        public final K a;

        @NonNull
        public final V b;
        public d<K, V> c;
        public d<K, V> d;

        public d(@NonNull K k, @NonNull V v) {
            this.a = k;
            this.b = v;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            return this.a.equals(dVar.a) && this.b.equals(dVar.b);
        }

        @Override // java.util.Map.Entry
        @NonNull
        public K getKey() {
            return this.a;
        }

        @Override // java.util.Map.Entry
        @NonNull
        public V getValue() {
            return this.b;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public String toString() {
            return this.a + "=" + this.b;
        }
    }

    public class e implements Iterator<Map.Entry<K, V>>, g<K, V> {
        public d<K, V> a;
        public boolean b = true;

        public /* synthetic */ e(a aVar) {
        }

        @Override // android.arch.core.internal.SafeIterableMap.g
        public void a(@NonNull d<K, V> dVar) {
            d<K, V> dVar2 = this.a;
            if (dVar == dVar2) {
                d<K, V> dVar3 = dVar2.d;
                this.a = dVar3;
                this.b = dVar3 == null;
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.b) {
                return SafeIterableMap.this.a != null;
            }
            d<K, V> dVar = this.a;
            return (dVar == null || dVar.c == null) ? false : true;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (this.b) {
                this.b = false;
                this.a = SafeIterableMap.this.a;
            } else {
                d<K, V> dVar = this.a;
                this.a = dVar != null ? dVar.c : null;
            }
            return this.a;
        }
    }

    public static abstract class f<K, V> implements Iterator<Map.Entry<K, V>>, g<K, V> {
        public d<K, V> a;
        public d<K, V> b;

        public f(d<K, V> dVar, d<K, V> dVar2) {
            this.a = dVar2;
            this.b = dVar;
        }

        @Override // android.arch.core.internal.SafeIterableMap.g
        public void a(@NonNull d<K, V> dVar) {
            d<K, V> dVarC = null;
            if (this.a == dVar && dVar == this.b) {
                this.b = null;
                this.a = null;
            }
            d<K, V> dVar2 = this.a;
            if (dVar2 == dVar) {
                this.a = b(dVar2);
            }
            d<K, V> dVar3 = this.b;
            if (dVar3 == dVar) {
                d<K, V> dVar4 = this.a;
                if (dVar3 != dVar4 && dVar4 != null) {
                    dVarC = c(dVar3);
                }
                this.b = dVarC;
            }
        }

        public abstract d<K, V> b(d<K, V> dVar);

        public abstract d<K, V> c(d<K, V> dVar);

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b != null;
        }

        @Override // java.util.Iterator
        public Object next() {
            d<K, V> dVar = this.b;
            d<K, V> dVar2 = this.a;
            this.b = (dVar == dVar2 || dVar2 == null) ? null : c(dVar);
            return dVar;
        }
    }

    public interface g<K, V> {
        void a(@NonNull d<K, V> dVar);
    }

    public Iterator<Map.Entry<K, V>> descendingIterator() {
        c cVar = new c(this.b, this.a);
        this.c.put(cVar, false);
        return cVar;
    }

    public Map.Entry<K, V> eldest() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SafeIterableMap)) {
            return false;
        }
        SafeIterableMap safeIterableMap = (SafeIterableMap) obj;
        if (size() != safeIterableMap.size()) {
            return false;
        }
        Iterator<Map.Entry<K, V>> it = iterator();
        Iterator<Map.Entry<K, V>> it2 = safeIterableMap.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry<K, V> next = it.next();
            Map.Entry<K, V> next2 = it2.next();
            if ((next == null && next2 != null) || (next != null && !next.equals(next2))) {
                return false;
            }
        }
        return (it.hasNext() || it2.hasNext()) ? false : true;
    }

    public d<K, V> get(K k) {
        d<K, V> dVar = this.a;
        while (dVar != null && !dVar.a.equals(k)) {
            dVar = dVar.c;
        }
        return dVar;
    }

    @Override // java.lang.Iterable
    @NonNull
    public Iterator<Map.Entry<K, V>> iterator() {
        b bVar = new b(this.a, this.b);
        this.c.put(bVar, false);
        return bVar;
    }

    public SafeIterableMap<K, V>.e iteratorWithAdditions() {
        SafeIterableMap<K, V>.e eVar = new e(null);
        this.c.put(eVar, false);
        return eVar;
    }

    public Map.Entry<K, V> newest() {
        return this.b;
    }

    public d<K, V> put(@NonNull K k, @NonNull V v) {
        d<K, V> dVar = new d<>(k, v);
        this.d++;
        d<K, V> dVar2 = this.b;
        if (dVar2 == null) {
            this.a = dVar;
            this.b = dVar;
            return dVar;
        }
        dVar2.c = dVar;
        dVar.d = dVar2;
        this.b = dVar;
        return dVar;
    }

    public V putIfAbsent(@NonNull K k, @NonNull V v) {
        d<K, V> dVar = get(k);
        if (dVar != null) {
            return dVar.b;
        }
        put(k, v);
        return null;
    }

    public V remove(@NonNull K k) {
        d<K, V> dVar = get(k);
        if (dVar == null) {
            return null;
        }
        this.d--;
        if (!this.c.isEmpty()) {
            Iterator<g<K, V>> it = this.c.keySet().iterator();
            while (it.hasNext()) {
                it.next().a(dVar);
            }
        }
        d<K, V> dVar2 = dVar.d;
        if (dVar2 != null) {
            dVar2.c = dVar.c;
        } else {
            this.a = dVar.c;
        }
        d<K, V> dVar3 = dVar.c;
        if (dVar3 != null) {
            dVar3.d = dVar.d;
        } else {
            this.b = dVar.d;
        }
        dVar.c = null;
        dVar.d = null;
        return dVar.b;
    }

    public int size() {
        return this.d;
    }

    public String toString() {
        StringBuilder sbA = g9.a("[");
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
            sbA.append(it.next().toString());
            if (it.hasNext()) {
                sbA.append(", ");
            }
        }
        sbA.append("]");
        return sbA.toString();
    }
}
