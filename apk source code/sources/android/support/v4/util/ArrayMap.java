package android.support.v4.util;

import a6.b;
import a6.c;
import a6.e;
import defpackage.a6;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {
    public a6<K, V> h;

    public class a extends a6<K, V> {
        public a() {
        }

        @Override // defpackage.a6
        public Object a(int i, int i2) {
            return ArrayMap.this.b[(i << 1) + i2];
        }

        @Override // defpackage.a6
        public int b(Object obj) {
            return ArrayMap.this.a(obj);
        }

        @Override // defpackage.a6
        public int c() {
            return ArrayMap.this.c;
        }

        @Override // defpackage.a6
        public int a(Object obj) {
            return ArrayMap.this.indexOfKey(obj);
        }

        @Override // defpackage.a6
        public Map<K, V> b() {
            return ArrayMap.this;
        }

        @Override // defpackage.a6
        public void a(K k, V v) {
            ArrayMap.this.put(k, v);
        }

        @Override // defpackage.a6
        public V a(int i, V v) {
            return ArrayMap.this.setValueAt(i, v);
        }

        @Override // defpackage.a6
        public void a(int i) {
            ArrayMap.this.removeAt(i);
        }

        @Override // defpackage.a6
        public void a() {
            ArrayMap.this.clear();
        }
    }

    public ArrayMap() {
    }

    public final a6<K, V> b() {
        if (this.h == null) {
            this.h = new a();
        }
        return this.h;
    }

    public boolean containsAll(Collection<?> collection) {
        return a6.a((Map) this, collection);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        a6<K, V> a6VarB = b();
        if (a6VarB.a == null) {
            a6VarB.a = a6VarB.new b();
        }
        return a6VarB.a;
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        a6<K, V> a6VarB = b();
        if (a6VarB.b == null) {
            a6VarB.b = a6VarB.new c();
        }
        return a6VarB.b;
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(map.size() + this.c);
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(Collection<?> collection) {
        return a6.b(this, collection);
    }

    public boolean retainAll(Collection<?> collection) {
        return a6.c(this, collection);
    }

    @Override // java.util.Map
    public Collection<V> values() {
        a6<K, V> a6VarB = b();
        if (a6VarB.c == null) {
            a6VarB.c = a6VarB.new e();
        }
        return a6VarB.c;
    }

    public ArrayMap(int i) {
        super(i);
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap);
    }
}
