package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public abstract class AtomicInitializer<T> implements ConcurrentInitializer<T> {
    public final AtomicReference<T> a = new AtomicReference<>();

    @Override // org.apache.commons.lang3.concurrent.ConcurrentInitializer
    public T get() throws ConcurrentException {
        T t = this.a.get();
        if (t != null) {
            return t;
        }
        T tInitialize = initialize();
        return !this.a.compareAndSet(null, tInitialize) ? this.a.get() : tInitialize;
    }

    public abstract T initialize() throws ConcurrentException;
}
