package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes.dex */
public final class NewThreadScheduler extends Scheduler {
    public static final RxThreadFactory c = new RxThreadFactory("RxNewThreadScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.newthread-priority", 5).intValue())));
    public final ThreadFactory b;

    public NewThreadScheduler() {
        this(c);
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new NewThreadWorker(this.b);
    }

    public NewThreadScheduler(ThreadFactory threadFactory) {
        this.b = threadFactory;
    }
}
