package org.apache.commons.lang3.concurrent;

/* loaded from: classes.dex */
public abstract class LazyInitializer<T> implements ConcurrentInitializer<T> {
    public static final Object b = new Object();
    public volatile T a = (T) b;

    @Override // org.apache.commons.lang3.concurrent.ConcurrentInitializer
    public T get() throws ConcurrentException {
        T tInitialize = this.a;
        if (tInitialize == b) {
            synchronized (this) {
                tInitialize = this.a;
                if (tInitialize == b) {
                    tInitialize = initialize();
                    this.a = tInitialize;
                }
            }
        }
        return tInitialize;
    }

    public abstract T initialize() throws ConcurrentException;
}
