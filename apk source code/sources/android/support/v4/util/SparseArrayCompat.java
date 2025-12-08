package android.support.v4.util;

import defpackage.z5;

/* loaded from: classes.dex */
public class SparseArrayCompat<E> implements Cloneable {
    public static final Object e = new Object();
    public boolean a;
    public int[] b;
    public Object[] c;
    public int d;

    public SparseArrayCompat() {
        this(10);
    }

    public final void a() {
        int i = this.d;
        int[] iArr = this.b;
        Object[] objArr = this.c;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != e) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.a = false;
        this.d = i2;
    }

    public void append(int i, E e2) {
        int i2 = this.d;
        if (i2 != 0 && i <= this.b[i2 - 1]) {
            put(i, e2);
            return;
        }
        if (this.a && this.d >= this.b.length) {
            a();
        }
        int i3 = this.d;
        if (i3 >= this.b.length) {
            int iB = z5.b(i3 + 1);
            int[] iArr = new int[iB];
            Object[] objArr = new Object[iB];
            int[] iArr2 = this.b;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr2 = this.c;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.b = iArr;
            this.c = objArr;
        }
        this.b[i3] = i;
        this.c[i3] = e2;
        this.d = i3 + 1;
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

    public void delete(int i) {
        int iA = z5.a(this.b, this.d, i);
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

    public E get(int i) {
        return get(i, null);
    }

    public int indexOfKey(int i) {
        if (this.a) {
            a();
        }
        return z5.a(this.b, this.d, i);
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

    public int keyAt(int i) {
        if (this.a) {
            a();
        }
        return this.b[i];
    }

    public void put(int i, E e2) {
        int iA = z5.a(this.b, this.d, i);
        if (iA >= 0) {
            this.c[iA] = e2;
            return;
        }
        int i2 = ~iA;
        if (i2 < this.d) {
            Object[] objArr = this.c;
            if (objArr[i2] == e) {
                this.b[i2] = i;
                objArr[i2] = e2;
                return;
            }
        }
        if (this.a && this.d >= this.b.length) {
            a();
            i2 = ~z5.a(this.b, this.d, i);
        }
        int i3 = this.d;
        if (i3 >= this.b.length) {
            int iB = z5.b(i3 + 1);
            int[] iArr = new int[iB];
            Object[] objArr2 = new Object[iB];
            int[] iArr2 = this.b;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.c;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.b = iArr;
            this.c = objArr2;
        }
        int i4 = this.d;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.b;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            Object[] objArr4 = this.c;
            System.arraycopy(objArr4, i2, objArr4, i5, this.d - i2);
        }
        this.b[i2] = i;
        this.c[i2] = e2;
        this.d++;
    }

    public void remove(int i) {
        delete(i);
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

    public void removeAtRange(int i, int i2) {
        int iMin = Math.min(this.d, i2 + i);
        while (i < iMin) {
            removeAt(i);
            i++;
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

    public SparseArrayCompat(int i) {
        this.a = false;
        if (i == 0) {
            this.b = z5.a;
            this.c = z5.c;
        } else {
            int iB = z5.b(i);
            this.b = new int[iB];
            this.c = new Object[iB];
        }
        this.d = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SparseArrayCompat<E> m6clone() {
        SparseArrayCompat<E> sparseArrayCompat;
        SparseArrayCompat<E> sparseArrayCompat2 = null;
        try {
            sparseArrayCompat = (SparseArrayCompat) super.clone();
        } catch (CloneNotSupportedException unused) {
        }
        try {
            sparseArrayCompat.b = (int[]) this.b.clone();
            sparseArrayCompat.c = (Object[]) this.c.clone();
            return sparseArrayCompat;
        } catch (CloneNotSupportedException unused2) {
            sparseArrayCompat2 = sparseArrayCompat;
            return sparseArrayCompat2;
        }
    }

    public E get(int i, E e2) {
        int iA = z5.a(this.b, this.d, i);
        if (iA >= 0) {
            Object[] objArr = this.c;
            if (objArr[iA] != e) {
                return (E) objArr[iA];
            }
        }
        return e2;
    }
}
