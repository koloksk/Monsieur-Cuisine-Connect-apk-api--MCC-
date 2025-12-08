package io.reactivex.internal.schedulers;

import defpackage.cl;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes.dex */
public final class ScheduledDirectPeriodicTask extends cl implements Runnable {
    public static final long serialVersionUID = 1811839108042568751L;

    public ScheduledDirectPeriodicTask(Runnable runnable) {
        super(runnable);
    }

    @Override // defpackage.cl, io.reactivex.schedulers.SchedulerRunnableIntrospection
    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    @Override // java.lang.Runnable
    public void run() {
        this.runner = Thread.currentThread();
        try {
            this.runnable.run();
            this.runner = null;
        } catch (Throwable th) {
            this.runner = null;
            lazySet(cl.FINISHED);
            RxJavaPlugins.onError(th);
        }
    }
}
