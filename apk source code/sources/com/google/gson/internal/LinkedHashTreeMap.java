package com.google.gson.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes.dex */
public final class LinkedHashTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    public static final Comparator<Comparable> i = new a();
    public Comparator<? super K> a;
    public f<K, V>[] b;
    public final f<K, V> c;
    public int d;
    public int e;
    public int f;
    public LinkedHashTreeMap<K, V>.c g;
    public LinkedHashTreeMap<K, V>.d h;

    public static class a implements Comparator<Comparable> {
        @Override // java.util.Comparator
        public int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    }

    public final class c extends AbstractSet<Map.Entry<K, V>> {

        public class a extends LinkedHashTreeMap<K, V>.e<Map.Entry<K, V>> {
            public a(c cVar) {
                super();
            }

            @Override // java.util.Iterator
            public Object next() {
                return a();
            }
        }

        public c() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            LinkedHashTreeMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return (obj instanceof Map.Entry) && LinkedHashTreeMap.this.a((Map.Entry<?, ?>) obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new a(this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            f<K, V> fVarA;
            if (!(obj instanceof Map.Entry) || (fVarA = LinkedHashTreeMap.this.a((Map.Entry<?, ?>) obj)) == null) {
                return false;
            }
            LinkedHashTreeMap.this.b(fVarA, true);
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return LinkedHashTreeMap.this.d;
        }
    }

    public final class d extends AbstractSet<K> {

        public class a extends LinkedHashTreeMap<K, V>.e<K> {
            public a(d dVar) {
                super();
            }

            @Override // java.util.Iterator
            public K next() {
                return a().f;
            }
        }

        public d() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            LinkedHashTreeMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return LinkedHashTreeMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new a(this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            LinkedHashTreeMap linkedHashTreeMap = LinkedHashTreeMap.this;
            f<K, V> fVarA = linkedHashTreeMap.a(obj);
            if (fVarA != null) {
                linkedHashTreeMap.b(fVarA, true);
            }
            return fVarA != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return LinkedHashTreeMap.this.d;
        }
    }

    public abstract class e<T> implements Iterator<T> {
        public f<K, V> a;
        public f<K, V> b;
        public int c;

        public e() {
            LinkedHashTreeMap linkedHashTreeMap = LinkedHashTreeMap.this;
            this.a = linkedHashTreeMap.c.d;
            this.b = null;
            this.c = linkedHashTreeMap.e;
        }

        public final f<K, V> a() {
            f<K, V> fVar = this.a;
            LinkedHashTreeMap linkedHashTreeMap = LinkedHashTreeMap.this;
            if (fVar == linkedHashTreeMap.c) {
                throw new NoSuchElementException();
            }
            if (linkedHashTreeMap.e != this.c) {
                throw new ConcurrentModificationException();
            }
            this.a = fVar.d;
            this.b = fVar;
            return fVar;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.a != LinkedHashTreeMap.this.c;
        }

        @Override // java.util.Iterator
        public final void remove() {
            f<K, V> fVar = this.b;
            if (fVar == null) {
                throw new IllegalStateException();
            }
            LinkedHashTreeMap.this.b(fVar, true);
            this.b = null;
            this.c = LinkedHashTreeMap.this.e;
        }
    }

    public LinkedHashTreeMap() {
        this(i);
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }

    public f<K, V> a(K k, boolean z) {
        f<K, V> fVar;
        int i2;
        f<K, V> fVar2;
        f<K, V> fVar3;
        f<K, V> fVar4;
        f<K, V> fVar5;
        f<K, V> fVar6;
        Comparator<? super K> comparator = this.a;
        f<K, V>[] fVarArr = this.b;
        int iHashCode = k.hashCode();
        int i3 = iHashCode ^ ((iHashCode >>> 20) ^ (iHashCode >>> 12));
        int i4 = ((i3 >>> 7) ^ i3) ^ (i3 >>> 4);
        int length = i4 & (fVarArr.length - 1);
        f<K, V> fVar7 = fVarArr[length];
        if (fVar7 != null) {
            Comparable comparable = comparator == i ? (Comparable) k : null;
            while (true) {
                int iCompareTo = comparable != null ? comparable.compareTo(fVar7.f) : comparator.compare(k, fVar7.f);
                if (iCompareTo == 0) {
                    return fVar7;
                }
                f<K, V> fVar8 = iCompareTo < 0 ? fVar7.b : fVar7.c;
                if (fVar8 == null) {
                    fVar = fVar7;
                    i2 = iCompareTo;
                    break;
                }
                fVar7 = fVar8;
            }
        } else {
            fVar = fVar7;
            i2 = 0;
        }
        if (!z) {
            return null;
        }
        f<K, V> fVar9 = this.c;
        if (fVar != null) {
            f<K, V> fVar10 = new f<>(fVar, k, i4, fVar9, fVar9.e);
            if (i2 < 0) {
                fVar.b = fVar10;
            } else {
                fVar.c = fVar10;
            }
            a((f) fVar, true);
            fVar2 = fVar10;
        } else {
            if (comparator == i && !(k instanceof Comparable)) {
                throw new ClassCastException(k.getClass().getName() + " is not Comparable");
            }
            fVar2 = new f<>(fVar, k, i4, fVar9, fVar9.e);
            fVarArr[length] = fVar2;
        }
        int i5 = this.d;
        this.d = i5 + 1;
        if (i5 > this.f) {
            f<K, V>[] fVarArr2 = this.b;
            int length2 = fVarArr2.length;
            int i6 = length2 * 2;
            f<K, V>[] fVarArr3 = new f[i6];
            b bVar = new b();
            b bVar2 = new b();
            for (int i7 = 0; i7 < length2; i7++) {
                f<K, V> fVar11 = fVarArr2[i7];
                if (fVar11 != null) {
                    f<K, V> fVar12 = null;
                    for (f<K, V> fVar13 = fVar11; fVar13 != null; fVar13 = fVar13.b) {
                        fVar13.a = fVar12;
                        fVar12 = fVar13;
                    }
                    int i8 = 0;
                    int i9 = 0;
                    while (true) {
                        if (fVar12 == null) {
                            fVar3 = fVar12;
                            fVar12 = null;
                        } else {
                            fVar3 = fVar12.a;
                            fVar12.a = null;
                            for (f<K, V> fVar14 = fVar12.c; fVar14 != null; fVar14 = fVar14.b) {
                                fVar14.a = fVar3;
                                fVar3 = fVar14;
                            }
                        }
                        if (fVar12 == null) {
                            break;
                        }
                        if ((fVar12.g & length2) == 0) {
                            i8++;
                        } else {
                            i9++;
                        }
                        fVar12 = fVar3;
                    }
                    bVar.a(i8);
                    bVar2.a(i9);
                    f<K, V> fVar15 = null;
                    while (fVar11 != null) {
                        fVar11.a = fVar15;
                        f<K, V> fVar16 = fVar11;
                        fVar11 = fVar11.b;
                        fVar15 = fVar16;
                    }
                    while (true) {
                        if (fVar15 != null) {
                            f<K, V> fVar17 = fVar15.a;
                            fVar15.a = null;
                            f<K, V> fVar18 = fVar15.c;
                            while (true) {
                                f<K, V> fVar19 = fVar18;
                                fVar4 = fVar17;
                                fVar17 = fVar19;
                                if (fVar17 == null) {
                                    break;
                                }
                                fVar17.a = fVar4;
                                fVar18 = fVar17.b;
                            }
                        } else {
                            fVar4 = fVar15;
                            fVar15 = null;
                        }
                        if (fVar15 == null) {
                            break;
                        }
                        if ((fVar15.g & length2) == 0) {
                            bVar.a(fVar15);
                        } else {
                            bVar2.a(fVar15);
                        }
                        fVar15 = fVar4;
                    }
                    if (i8 > 0) {
                        fVar5 = bVar.a;
                        if (fVar5.a != null) {
                            throw new IllegalStateException();
                        }
                    } else {
                        fVar5 = null;
                    }
                    fVarArr3[i7] = fVar5;
                    int i10 = i7 + length2;
                    if (i9 > 0) {
                        fVar6 = bVar2.a;
                        if (fVar6.a != null) {
                            throw new IllegalStateException();
                        }
                    } else {
                        fVar6 = null;
                    }
                    fVarArr3[i10] = fVar6;
                }
            }
            this.b = fVarArr3;
            this.f = (i6 / 4) + (i6 / 2);
        }
        this.e++;
        return fVar2;
    }

    public void b(f<K, V> fVar, boolean z) {
        f<K, V> fVar2;
        f<K, V> fVar3;
        int i2;
        if (z) {
            f<K, V> fVar4 = fVar.e;
            fVar4.d = fVar.d;
            fVar.d.e = fVar4;
            fVar.e = null;
            fVar.d = null;
        }
        f<K, V> fVar5 = fVar.b;
        f<K, V> fVar6 = fVar.c;
        f<K, V> fVar7 = fVar.a;
        int i3 = 0;
        if (fVar5 == null || fVar6 == null) {
            if (fVar5 != null) {
                a(fVar, fVar5);
                fVar.b = null;
            } else if (fVar6 != null) {
                a(fVar, fVar6);
                fVar.c = null;
            } else {
                a(fVar, (f) null);
            }
            a((f) fVar7, false);
            this.d--;
            this.e++;
            return;
        }
        if (fVar5.i > fVar6.i) {
            f<K, V> fVar8 = fVar5.c;
            while (true) {
                f<K, V> fVar9 = fVar8;
                fVar3 = fVar5;
                fVar5 = fVar9;
                if (fVar5 == null) {
                    break;
                } else {
                    fVar8 = fVar5.c;
                }
            }
        } else {
            f<K, V> fVar10 = fVar6.b;
            while (true) {
                fVar2 = fVar6;
                fVar6 = fVar10;
                if (fVar6 == null) {
                    break;
                } else {
                    fVar10 = fVar6.b;
                }
            }
            fVar3 = fVar2;
        }
        b(fVar3, false);
        f<K, V> fVar11 = fVar.b;
        if (fVar11 != null) {
            i2 = fVar11.i;
            fVar3.b = fVar11;
            fVar11.a = fVar3;
            fVar.b = null;
        } else {
            i2 = 0;
        }
        f<K, V> fVar12 = fVar.c;
        if (fVar12 != null) {
            i3 = fVar12.i;
            fVar3.c = fVar12;
            fVar12.a = fVar3;
            fVar.c = null;
        }
        fVar3.i = Math.max(i2, i3) + 1;
        a(fVar, fVar3);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Arrays.fill(this.b, (Object) null);
        this.d = 0;
        this.e++;
        f<K, V> fVar = this.c;
        f<K, V> fVar2 = fVar.d;
        while (fVar2 != fVar) {
            f<K, V> fVar3 = fVar2.d;
            fVar2.e = null;
            fVar2.d = null;
            fVar2 = fVar3;
        }
        fVar.e = fVar;
        fVar.d = fVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return a(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        LinkedHashTreeMap<K, V>.c cVar = this.g;
        if (cVar != null) {
            return cVar;
        }
        LinkedHashTreeMap<K, V>.c cVar2 = new c();
        this.g = cVar2;
        return cVar2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        f<K, V> fVarA = a(obj);
        if (fVarA != null) {
            return fVarA.h;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        LinkedHashTreeMap<K, V>.d dVar = this.h;
        if (dVar != null) {
            return dVar;
        }
        LinkedHashTreeMap<K, V>.d dVar2 = new d();
        this.h = dVar2;
        return dVar2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        f<K, V> fVarA = a((LinkedHashTreeMap<K, V>) k, true);
        V v2 = fVarA.h;
        fVarA.h = v;
        return v2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        f<K, V> fVarA = a(obj);
        if (fVarA != null) {
            b(fVarA, true);
        }
        if (fVarA != null) {
            return fVarA.h;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.d;
    }

    public LinkedHashTreeMap(Comparator<? super K> comparator) {
        this.d = 0;
        this.e = 0;
        this.a = comparator == null ? i : comparator;
        this.c = new f<>();
        f<K, V>[] fVarArr = new f[16];
        this.b = fVarArr;
        this.f = (fVarArr.length / 4) + (fVarArr.length / 2);
    }

    public static final class f<K, V> implements Map.Entry<K, V> {
        public f<K, V> a;
        public f<K, V> b;
        public f<K, V> c;
        public f<K, V> d;
        public f<K, V> e;
        public final K f;
        public final int g;
        public V h;
        public int i;

        public f() {
            this.f = null;
            this.g = -1;
            this.e = this;
            this.d = this;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            K k = this.f;
            if (k == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            } else if (!k.equals(entry.getKey())) {
                return false;
            }
            V v = this.h;
            if (v == null) {
                if (entry.getValue() != null) {
                    return false;
                }
            } else if (!v.equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.f;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.h;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            K k = this.f;
            int iHashCode = k == null ? 0 : k.hashCode();
            V v = this.h;
            return iHashCode ^ (v != null ? v.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = this.h;
            this.h = v;
            return v2;
        }

        public String toString() {
            return this.f + "=" + this.h;
        }

        public f(f<K, V> fVar, K k, int i, f<K, V> fVar2, f<K, V> fVar3) {
            this.a = fVar;
            this.f = k;
            this.g = i;
            this.i = 1;
            this.d = fVar2;
            this.e = fVar3;
            fVar3.d = this;
            fVar2.e = this;
        }
    }

    public static final class b<K, V> {
        public f<K, V> a;
        public int b;
        public int c;
        public int d;

        public void a(int i) {
            this.b = ((Integer.highestOneBit(i) * 2) - 1) - i;
            this.d = 0;
            this.c = 0;
            this.a = null;
        }

        public void a(f<K, V> fVar) {
            fVar.c = null;
            fVar.a = null;
            fVar.b = null;
            fVar.i = 1;
            int i = this.b;
            if (i > 0) {
                int i2 = this.d;
                if ((i2 & 1) == 0) {
                    this.d = i2 + 1;
                    this.b = i - 1;
                    this.c++;
                }
            }
            fVar.a = this.a;
            this.a = fVar;
            int i3 = this.d + 1;
            this.d = i3;
            int i4 = this.b;
            if (i4 > 0 && (i3 & 1) == 0) {
                this.d = i3 + 1;
                this.b = i4 - 1;
                this.c++;
            }
            int i5 = 4;
            while (true) {
                int i6 = i5 - 1;
                if ((this.d & i6) != i6) {
                    return;
                }
                int i7 = this.c;
                if (i7 == 0) {
                    f<K, V> fVar2 = this.a;
                    f<K, V> fVar3 = fVar2.a;
                    f<K, V> fVar4 = fVar3.a;
                    fVar3.a = fVar4.a;
                    this.a = fVar3;
                    fVar3.b = fVar4;
                    fVar3.c = fVar2;
                    fVar3.i = fVar2.i + 1;
                    fVar4.a = fVar3;
                    fVar2.a = fVar3;
                } else if (i7 == 1) {
                    f<K, V> fVar5 = this.a;
                    f<K, V> fVar6 = fVar5.a;
                    this.a = fVar6;
                    fVar6.c = fVar5;
                    fVar6.i = fVar5.i + 1;
                    fVar5.a = fVar6;
                    this.c = 0;
                } else if (i7 == 2) {
                    this.c = 0;
                }
                i5 *= 2;
            }
        }
    }

    public final void b(f<K, V> fVar) {
        f<K, V> fVar2 = fVar.b;
        f<K, V> fVar3 = fVar.c;
        f<K, V> fVar4 = fVar2.b;
        f<K, V> fVar5 = fVar2.c;
        fVar.b = fVar5;
        if (fVar5 != null) {
            fVar5.a = fVar;
        }
        a(fVar, fVar2);
        fVar2.c = fVar;
        fVar.a = fVar2;
        int iMax = Math.max(fVar3 != null ? fVar3.i : 0, fVar5 != null ? fVar5.i : 0) + 1;
        fVar.i = iMax;
        fVar2.i = Math.max(iMax, fVar4 != null ? fVar4.i : 0) + 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public f<K, V> a(Object obj) {
        if (obj == 0) {
            return null;
        }
        try {
            return a((LinkedHashTreeMap<K, V>) obj, false);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.gson.internal.LinkedHashTreeMap.f<K, V> a(java.util.Map.Entry<?, ?> r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r5.getKey()
            com.google.gson.internal.LinkedHashTreeMap$f r0 = r4.a(r0)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L23
            V r3 = r0.h
            java.lang.Object r5 = r5.getValue()
            if (r3 == r5) goto L1f
            if (r3 == 0) goto L1d
            boolean r5 = r3.equals(r5)
            if (r5 == 0) goto L1d
            goto L1f
        L1d:
            r5 = r2
            goto L20
        L1f:
            r5 = r1
        L20:
            if (r5 == 0) goto L23
            goto L24
        L23:
            r1 = r2
        L24:
            if (r1 == 0) goto L27
            goto L28
        L27:
            r0 = 0
        L28:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.LinkedHashTreeMap.a(java.util.Map$Entry):com.google.gson.internal.LinkedHashTreeMap$f");
    }

    public final void a(f<K, V> fVar, f<K, V> fVar2) {
        f<K, V> fVar3 = fVar.a;
        fVar.a = null;
        if (fVar2 != null) {
            fVar2.a = fVar3;
        }
        if (fVar3 != null) {
            if (fVar3.b == fVar) {
                fVar3.b = fVar2;
                return;
            } else {
                fVar3.c = fVar2;
                return;
            }
        }
        int i2 = fVar.g;
        this.b[i2 & (r0.length - 1)] = fVar2;
    }

    public final void a(f<K, V> fVar, boolean z) {
        while (fVar != null) {
            f<K, V> fVar2 = fVar.b;
            f<K, V> fVar3 = fVar.c;
            int i2 = fVar2 != null ? fVar2.i : 0;
            int i3 = fVar3 != null ? fVar3.i : 0;
            int i4 = i2 - i3;
            if (i4 == -2) {
                f<K, V> fVar4 = fVar3.b;
                f<K, V> fVar5 = fVar3.c;
                int i5 = (fVar4 != null ? fVar4.i : 0) - (fVar5 != null ? fVar5.i : 0);
                if (i5 != -1 && (i5 != 0 || z)) {
                    b(fVar3);
                    a((f) fVar);
                } else {
                    a((f) fVar);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 2) {
                f<K, V> fVar6 = fVar2.b;
                f<K, V> fVar7 = fVar2.c;
                int i6 = (fVar6 != null ? fVar6.i : 0) - (fVar7 != null ? fVar7.i : 0);
                if (i6 != 1 && (i6 != 0 || z)) {
                    a((f) fVar2);
                    b(fVar);
                } else {
                    b(fVar);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 0) {
                fVar.i = i2 + 1;
                if (z) {
                    return;
                }
            } else {
                fVar.i = Math.max(i2, i3) + 1;
                if (!z) {
                    return;
                }
            }
            fVar = fVar.a;
        }
    }

    public final void a(f<K, V> fVar) {
        f<K, V> fVar2 = fVar.b;
        f<K, V> fVar3 = fVar.c;
        f<K, V> fVar4 = fVar3.b;
        f<K, V> fVar5 = fVar3.c;
        fVar.c = fVar4;
        if (fVar4 != null) {
            fVar4.a = fVar;
        }
        a(fVar, fVar3);
        fVar3.b = fVar;
        fVar.a = fVar3;
        int iMax = Math.max(fVar2 != null ? fVar2.i : 0, fVar4 != null ? fVar4.i : 0) + 1;
        fVar.i = iMax;
        fVar3.i = Math.max(iMax, fVar5 != null ? fVar5.i : 0) + 1;
    }
}
