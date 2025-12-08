package android.support.v4.util;

/* loaded from: classes.dex */
public final class CircularIntArray {
    public int[] a;
    public int b;
    public int c;
    public int d;

    public CircularIntArray() {
        this(8);
    }

    public final void a() {
        int[] iArr = this.a;
        int length = iArr.length;
        int i = this.b;
        int i2 = length - i;
        int i3 = length << 1;
        if (i3 < 0) {
            throw new RuntimeException("Max array capacity exceeded");
        }
        int[] iArr2 = new int[i3];
        System.arraycopy(iArr, i, iArr2, 0, i2);
        System.arraycopy(this.a, 0, iArr2, i2, this.b);
        this.a = iArr2;
        this.b = 0;
        this.c = length;
        this.d = i3 - 1;
    }

    public void addFirst(int i) {
        int i2 = (this.b - 1) & this.d;
        this.b = i2;
        this.a[i2] = i;
        if (i2 == this.c) {
            a();
        }
    }

    public void addLast(int i) {
        int[] iArr = this.a;
        int i2 = this.c;
        iArr[i2] = i;
        int i3 = this.d & (i2 + 1);
        this.c = i3;
        if (i3 == this.b) {
            a();
        }
    }

    public void clear() {
        this.c = this.b;
    }

    public int get(int i) {
        if (i < 0 || i >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.a[this.d & (this.b + i)];
    }

    public int getFirst() {
        int i = this.b;
        if (i != this.c) {
            return this.a[i];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getLast() {
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

    public int popFirst() {
        int i = this.b;
        if (i == this.c) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i2 = this.a[i];
        this.b = (i + 1) & this.d;
        return i2;
    }

    public int popLast() {
        int i = this.b;
        int i2 = this.c;
        if (i == i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = this.d & (i2 - 1);
        int i4 = this.a[i3];
        this.c = i3;
        return i4;
    }

    public void removeFromEnd(int i) {
        if (i <= 0) {
            return;
        }
        if (i > size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.c = this.d & (this.c - i);
    }

    public void removeFromStart(int i) {
        if (i <= 0) {
            return;
        }
        if (i > size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.b = this.d & (this.b + i);
    }

    public int size() {
        return (this.c - this.b) & this.d;
    }

    public CircularIntArray(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }
        if (i > 1073741824) {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
        i = Integer.bitCount(i) != 1 ? Integer.highestOneBit(i - 1) << 1 : i;
        this.d = i - 1;
        this.a = new int[i];
    }
}
