package io.reactivex.internal.schedulers;

import defpackage.cl;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class ScheduledDirectTask extends cl implements Callable<Void> {
    public static final long serialVersionUID = 1811839108042568751L;

    public ScheduledDirectTask(Runnable runnable) {
        super(runnable);
    }

    @Override // defpackage.cl, io.reactivex.schedulers.SchedulerRunnableIntrospection
    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        this.runner = Thread.currentThread();
        try {
            this.runnable.run();
            return null;
        } finally {
            lazySet(cl.FINISHED);
            this.runner = null;
        }
    }
}
