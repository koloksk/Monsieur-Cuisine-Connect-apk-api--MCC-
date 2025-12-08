package org.apache.commons.lang3.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class CallableBackgroundInitializer<T> extends BackgroundInitializer<T> {
    public final Callable<T> d;

    public CallableBackgroundInitializer(Callable<T> callable) {
        Validate.isTrue(callable != null, "Callable must not be null!", new Object[0]);
        this.d = callable;
    }

    @Override // org.apache.commons.lang3.concurrent.BackgroundInitializer
    public T initialize() throws Exception {
        return this.d.call();
    }

    public CallableBackgroundInitializer(Callable<T> callable, ExecutorService executorService) {
        super(executorService);
        Validate.isTrue(callable != null, "Callable must not be null!", new Object[0]);
        this.d = callable;
    }
}
