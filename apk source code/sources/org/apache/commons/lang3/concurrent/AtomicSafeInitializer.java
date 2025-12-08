package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public abstract class AtomicSafeInitializer<T> implements ConcurrentInitializer<T> {
    public final AtomicReference<AtomicSafeInitializer<T>> a = new AtomicReference<>();
    public final AtomicReference<T> b = new AtomicReference<>();

    @Override // org.apache.commons.lang3.concurrent.ConcurrentInitializer
    public final T get() throws ConcurrentException {
        while (true) {
            T t = this.b.get();
            if (t != null) {
                return t;
            }
            if (this.a.compareAndSet(null, this)) {
                this.b.set(initialize());
            }
        }
    }

    public abstract T initialize() throws ConcurrentException;
}
