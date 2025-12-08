package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class MpscLinkedQueue<T> implements SimplePlainQueue<T> {
    public final AtomicReference<a<T>> a = new AtomicReference<>();
    public final AtomicReference<a<T>> b = new AtomicReference<>();

    public static final class a<E> extends AtomicReference<a<E>> {
        public static final long serialVersionUID = 2404266111789071508L;
        public E a;

        public a() {
        }

        public a(E e) {
            this.a = e;
        }
    }

    public MpscLinkedQueue() {
        a<T> aVar = new a<>();
        this.b.lazySet(aVar);
        this.a.getAndSet(aVar);
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return this.b.get() == this.a.get();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t) {
        if (t == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        a<T> aVar = new a<>(t);
        this.a.getAndSet(aVar).lazySet(aVar);
        return true;
    }

    @Override // io.reactivex.internal.fuseable.SimplePlainQueue, io.reactivex.internal.fuseable.SimpleQueue
    @Nullable
    public T poll() {
        a aVar;
        a<T> aVar2 = this.b.get();
        a aVar3 = aVar2.get();
        if (aVar3 != null) {
            T t = aVar3.a;
            aVar3.a = null;
            this.b.lazySet(aVar3);
            return t;
        }
        if (aVar2 == this.a.get()) {
            return null;
        }
        do {
            aVar = aVar2.get();
        } while (aVar == null);
        T t2 = aVar.a;
        aVar.a = null;
        this.b.lazySet(aVar);
        return t2;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t, T t2) {
        offer(t);
        offer(t2);
        return true;
    }
}
