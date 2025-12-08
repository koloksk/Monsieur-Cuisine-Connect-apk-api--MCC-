package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes.dex */
public final class SpscLinkedArrayQueue<T> implements SimplePlainQueue<T> {
    public static final int i = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
    public static final Object j = new Object();
    public int b;
    public long c;
    public final int d;
    public AtomicReferenceArray<Object> e;
    public final int f;
    public AtomicReferenceArray<Object> g;
    public final AtomicLong a = new AtomicLong();
    public final AtomicLong h = new AtomicLong();

    public SpscLinkedArrayQueue(int i2) {
        int iRoundToPowerOfTwo = Pow2.roundToPowerOfTwo(Math.max(8, i2));
        int i3 = iRoundToPowerOfTwo - 1;
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(iRoundToPowerOfTwo + 1);
        this.e = atomicReferenceArray;
        this.d = i3;
        this.b = Math.min(iRoundToPowerOfTwo / 4, i);
        this.g = atomicReferenceArray;
        this.f = i3;
        this.c = i3 - 1;
        this.a.lazySet(0L);
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    public final long d() {
        return this.h.get();
    }

    public final long e() {
        return this.a.get();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return e() == d();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t) {
        if (t == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        AtomicReferenceArray<Object> atomicReferenceArray = this.e;
        long j2 = this.a.get();
        int i2 = this.d;
        int i3 = ((int) j2) & i2;
        if (j2 < this.c) {
            atomicReferenceArray.lazySet(i3, t);
            this.a.lazySet(j2 + 1);
            return true;
        }
        long j3 = this.b + j2;
        if (atomicReferenceArray.get(((int) j3) & i2) == null) {
            this.c = j3 - 1;
            atomicReferenceArray.lazySet(i3, t);
            this.a.lazySet(j2 + 1);
            return true;
        }
        long j4 = j2 + 1;
        if (atomicReferenceArray.get(((int) j4) & i2) == null) {
            atomicReferenceArray.lazySet(i3, t);
            this.a.lazySet(j4);
            return true;
        }
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.e = atomicReferenceArray2;
        this.c = (i2 + j2) - 1;
        atomicReferenceArray2.lazySet(i3, t);
        atomicReferenceArray.lazySet(atomicReferenceArray.length() - 1, atomicReferenceArray2);
        atomicReferenceArray.lazySet(i3, j);
        this.a.lazySet(j4);
        return true;
    }

    public T peek() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.g;
        long j2 = this.h.get();
        int i2 = this.f;
        int i3 = ((int) j2) & i2;
        T t = (T) atomicReferenceArray.get(i3);
        if (t != j) {
            return t;
        }
        int i4 = i2 + 1;
        AtomicReferenceArray<Object> atomicReferenceArray2 = (AtomicReferenceArray) atomicReferenceArray.get(i4);
        atomicReferenceArray.lazySet(i4, null);
        this.g = atomicReferenceArray2;
        return (T) atomicReferenceArray2.get(i3);
    }

    @Override // io.reactivex.internal.fuseable.SimplePlainQueue, io.reactivex.internal.fuseable.SimpleQueue
    @Nullable
    public T poll() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.g;
        long j2 = this.h.get();
        int i2 = this.f;
        int i3 = ((int) j2) & i2;
        T t = (T) atomicReferenceArray.get(i3);
        boolean z = t == j;
        if (t != null && !z) {
            atomicReferenceArray.lazySet(i3, null);
            this.h.lazySet(j2 + 1);
            return t;
        }
        if (!z) {
            return null;
        }
        int i4 = i2 + 1;
        AtomicReferenceArray<Object> atomicReferenceArray2 = (AtomicReferenceArray) atomicReferenceArray.get(i4);
        atomicReferenceArray.lazySet(i4, null);
        this.g = atomicReferenceArray2;
        T t2 = (T) atomicReferenceArray2.get(i3);
        if (t2 != null) {
            atomicReferenceArray2.lazySet(i3, null);
            this.h.lazySet(j2 + 1);
        }
        return t2;
    }

    public int size() {
        long jD = d();
        while (true) {
            long jE = e();
            long jD2 = d();
            if (jD == jD2) {
                return (int) (jE - jD2);
            }
            jD = jD2;
        }
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t, T t2) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.e;
        long jE = e();
        int i2 = this.d;
        long j2 = 2 + jE;
        if (atomicReferenceArray.get(((int) j2) & i2) == null) {
            int i3 = ((int) jE) & i2;
            atomicReferenceArray.lazySet(i3 + 1, t2);
            atomicReferenceArray.lazySet(i3, t);
            this.a.lazySet(j2);
            return true;
        }
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.e = atomicReferenceArray2;
        int i4 = ((int) jE) & i2;
        atomicReferenceArray2.lazySet(i4 + 1, t2);
        atomicReferenceArray2.lazySet(i4, t);
        atomicReferenceArray.lazySet(atomicReferenceArray.length() - 1, atomicReferenceArray2);
        atomicReferenceArray.lazySet(i4, j);
        this.a.lazySet(j2);
        return true;
    }
}
