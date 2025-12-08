package android.support.v4.util;

import defpackage.z5;

/* loaded from: classes.dex */
public class LongSparseArray<E> implements Cloneable {
    public static final Object e = new Object();
    public boolean a;
    public long[] b;
    public Object[] c;
    public int d;

    public LongSparseArray() {
        this(10);
    }

    public final void a() {
        int i = this.d;
        long[] jArr = this.b;
        Object[] objArr = this.c;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != e) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.a = false;
        this.d = i2;
    }

    public void append(long j, E e2) {
        int i = this.d;
        if (i != 0 && j <= this.b[i - 1]) {
            put(j, e2);
            return;
        }
        if (this.a && this.d >= this.b.length) {
            a();
        }
        int i2 = this.d;
        if (i2 >= this.b.length) {
            int iC = z5.c(i2 + 1);
            long[] jArr = new long[iC];
            Object[] objArr = new Object[iC];
            long[] jArr2 = this.b;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr2 = this.c;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.b = jArr;
            this.c = objArr;
        }
        this.b[i2] = j;
        this.c[i2] = e2;
        this.d = i2 + 1;
    }

    public void clear() {
        int i = this.d;
        Object[] objArr = this.c;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.d = 0;
        this.a = false;
    }

    public void delete(long j) {
        int iA = z5.a(this.b, this.d, j);
        if (iA >= 0) {
            Object[] objArr = this.c;
            Object obj = objArr[iA];
            Object obj2 = e;
            if (obj != obj2) {
                objArr[iA] = obj2;
                this.a = true;
            }
        }
    }

    public E get(long j) {
        return get(j, null);
    }

    public int indexOfKey(long j) {
        if (this.a) {
            a();
        }
        return z5.a(this.b, this.d, j);
    }

    public int indexOfValue(E e2) {
        if (this.a) {
            a();
        }
        for (int i = 0; i < this.d; i++) {
            if (this.c[i] == e2) {
                return i;
            }
        }
        return -1;
    }

    public long keyAt(int i) {
        if (this.a) {
            a();
        }
        return this.b[i];
    }

    public void put(long j, E e2) {
        int iA = z5.a(this.b, this.d, j);
        if (iA >= 0) {
            this.c[iA] = e2;
            return;
        }
        int i = ~iA;
        if (i < this.d) {
            Object[] objArr = this.c;
            if (objArr[i] == e) {
                this.b[i] = j;
                objArr[i] = e2;
                return;
            }
        }
        if (this.a && this.d >= this.b.length) {
            a();
            i = ~z5.a(this.b, this.d, j);
        }
        int i2 = this.d;
        if (i2 >= this.b.length) {
            int iC = z5.c(i2 + 1);
            long[] jArr = new long[iC];
            Object[] objArr2 = new Object[iC];
            long[] jArr2 = this.b;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr3 = this.c;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.b = jArr;
            this.c = objArr2;
        }
        int i3 = this.d;
        if (i3 - i != 0) {
            long[] jArr3 = this.b;
            int i4 = i + 1;
            System.arraycopy(jArr3, i, jArr3, i4, i3 - i);
            Object[] objArr4 = this.c;
            System.arraycopy(objArr4, i, objArr4, i4, this.d - i);
        }
        this.b[i] = j;
        this.c[i] = e2;
        this.d++;
    }

    public void remove(long j) {
        delete(j);
    }

    public void removeAt(int i) {
        Object[] objArr = this.c;
        Object obj = objArr[i];
        Object obj2 = e;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.a = true;
        }
    }

    public void setValueAt(int i, E e2) {
        if (this.a) {
            a();
        }
        this.c[i] = e2;
    }

    public int size() {
        if (this.a) {
            a();
        }
        return this.d;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.d * 28);
        sb.append('{');
        for (int i = 0; i < this.d; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            E eValueAt = valueAt(i);
            if (eValueAt != this) {
                sb.append(eValueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public E valueAt(int i) {
        if (this.a) {
            a();
        }
        return (E) this.c[i];
    }

    public LongSparseArray(int i) {
        this.a = false;
        if (i == 0) {
            this.b = z5.b;
            this.c = z5.c;
        } else {
            int iC = z5.c(i);
            this.b = new long[iC];
            this.c = new Object[iC];
        }
        this.d = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public LongSparseArray<E> m5clone() {
        LongSparseArray<E> longSparseArray;
        LongSparseArray<E> longSparseArray2 = null;
        try {
            longSparseArray = (LongSparseArray) super.clone();
        } catch (CloneNotSupportedException unused) {
        }
        try {
            longSparseArray.b = (long[]) this.b.clone();
            longSparseArray.c = (Object[]) this.c.clone();
            return longSparseArray;
        } catch (CloneNotSupportedException unused2) {
            longSparseArray2 = longSparseArray;
            return longSparseArray2;
        }
    }

    public E get(long j, E e2) {
        int iA = z5.a(this.b, this.d, j);
        if (iA >= 0) {
            Object[] objArr = this.c;
            if (objArr[iA] != e) {
                return (E) objArr[iA];
            }
        }
        return e2;
    }
}
