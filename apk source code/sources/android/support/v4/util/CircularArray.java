package android.support.v4.util;

/* loaded from: classes.dex */
public final class CircularArray<E> {
    public E[] a;
    public int b;
    public int c;
    public int d;

    public CircularArray() {
        this(8);
    }

    public final void a() {
        E[] eArr = this.a;
        int length = eArr.length;
        int i = this.b;
        int i2 = length - i;
        int i3 = length << 1;
        if (i3 < 0) {
            throw new RuntimeException("Max array capacity exceeded");
        }
        E[] eArr2 = (E[]) new Object[i3];
        System.arraycopy(eArr, i, eArr2, 0, i2);
        System.arraycopy(this.a, 0, eArr2, i2, this.b);
        this.a = eArr2;
        this.b = 0;
        this.c = length;
        this.d = i3 - 1;
    }

    public void addFirst(E e) {
        int i = (this.b - 1) & this.d;
        this.b = i;
        this.a[i] = e;
        if (i == this.c) {
            a();
        }
    }

    public void addLast(E e) {
        E[] eArr = this.a;
        int i = this.c;
        eArr[i] = e;
        int i2 = this.d & (i + 1);
        this.c = i2;
        if (i2 == this.b) {
            a();
        }
    }

    public void clear() {
        removeFromStart(size());
    }

    public E get(int i) {
        if (i < 0 || i >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.a[this.d & (this.b + i)];
    }

    public E getFirst() {
        int i = this.b;
        if (i != this.c) {
            return this.a[i];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E getLast() {
        int i = this.b;
        int i2 = this.c;
        if (i != i2) {
            return this.a[(i2 - 1) & this.d];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public boolean isEmpty() {
        return this.b == this.c;
    }

    public E popFirst() {
        int i = this.b;
        if (i == this.c) {
            throw new ArrayIndexOutOfBoundsException();
        }
        E[] eArr = this.a;
        E e = eArr[i];
        eArr[i] = null;
        this.b = (i + 1) & this.d;
        return e;
    }

    public E popLast() {
        int i = this.b;
        int i2 = this.c;
        if (i == i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = this.d & (i2 - 1);
        E[] eArr = this.a;
        E e = eArr[i3];
        eArr[i3] = null;
        this.c = i3;
        return e;
    }

    public void removeFromEnd(int i) {
        int i2;
        if (i <= 0) {
            return;
        }
        if (i > size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = this.c;
        int i4 = i < i3 ? i3 - i : 0;
        int i5 = i4;
        while (true) {
            i2 = this.c;
            if (i5 >= i2) {
                break;
            }
            this.a[i5] = null;
            i5++;
        }
        int i6 = i2 - i4;
        int i7 = i - i6;
        this.c = i2 - i6;
        if (i7 > 0) {
            int length = this.a.length;
            this.c = length;
            int i8 = length - i7;
            for (int i9 = i8; i9 < this.c; i9++) {
                this.a[i9] = null;
            }
            this.c = i8;
        }
    }

    public void removeFromStart(int i) {
        if (i <= 0) {
            return;
        }
        if (i > size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int length = this.a.length;
        int i2 = this.b;
        if (i < length - i2) {
            length = i2 + i;
        }
        for (int i3 = this.b; i3 < length; i3++) {
            this.a[i3] = null;
        }
        int i4 = this.b;
        int i5 = length - i4;
        int i6 = i - i5;
        this.b = this.d & (i4 + i5);
        if (i6 > 0) {
            for (int i7 = 0; i7 < i6; i7++) {
                this.a[i7] = null;
            }
            this.b = i6;
        }
    }

    public int size() {
        return (this.c - this.b) & this.d;
    }

    public CircularArray(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }
        if (i > 1073741824) {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
        i = Integer.bitCount(i) != 1 ? Integer.highestOneBit(i - 1) << 1 : i;
        this.d = i - 1;
        this.a = (E[]) new Object[i];
    }
}
