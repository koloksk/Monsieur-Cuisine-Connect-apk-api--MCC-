package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ComputationScheduler extends Scheduler implements SchedulerMultiWorkerSupport {
    public static final b d;
    public static final RxThreadFactory e;
    public static final int f;
    public static final c g;
    public final ThreadFactory b;
    public final AtomicReference<b> c;

    public static final class b implements SchedulerMultiWorkerSupport {
        public final int a;
        public final c[] b;
        public long c;

        public b(int i, ThreadFactory threadFactory) {
            this.a = i;
            this.b = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.b[i2] = new c(threadFactory);
            }
        }

        public c a() {
            int i = this.a;
            if (i == 0) {
                return ComputationScheduler.g;
            }
            c[] cVarArr = this.b;
            long j = this.c;
            this.c = 1 + j;
            return cVarArr[(int) (j % i)];
        }

        public void b() {
            for (c cVar : this.b) {
                cVar.dispose();
            }
        }

        @Override // io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport
        public void createWorkers(int i, SchedulerMultiWorkerSupport.WorkerCallback workerCallback) {
            int i2 = this.a;
            if (i2 == 0) {
                for (int i3 = 0; i3 < i; i3++) {
                    workerCallback.onWorker(i3, ComputationScheduler.g);
                }
                return;
            }
            int i4 = ((int) this.c) % i2;
            for (int i5 = 0; i5 < i; i5++) {
                workerCallback.onWorker(i5, new a(this.b[i4]));
                i4++;
                if (i4 == i2) {
                    i4 = 0;
                }
            }
            this.c = i4;
        }
    }

    public static final class c extends NewThreadWorker {
        public c(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        int iIntValue = Integer.getInteger("rx2.computation-threads", 0).intValue();
        if (iIntValue > 0 && iIntValue <= iAvailableProcessors) {
            iAvailableProcessors = iIntValue;
        }
        f = iAvailableProcessors;
        c cVar = new c(new RxThreadFactory("RxComputationShutdown"));
        g = cVar;
        cVar.dispose();
        RxThreadFactory rxThreadFactory = new RxThreadFactory("RxComputationThreadPool", Math.max(1, Math.min(10, Integer.getInteger("rx2.computation-priority", 5).intValue())), true);
        e = rxThreadFactory;
        b bVar = new b(0, rxThreadFactory);
        d = bVar;
        bVar.b();
    }

    public ComputationScheduler() {
        this(e);
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new a(this.c.get().a());
    }

    @Override // io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport
    public void createWorkers(int i, SchedulerMultiWorkerSupport.WorkerCallback workerCallback) {
        ObjectHelper.verifyPositive(i, "number > 0 required");
        this.c.get().createWorkers(i, workerCallback);
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        return this.c.get().a().scheduleDirect(runnable, j, timeUnit);
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return this.c.get().a().schedulePeriodicallyDirect(runnable, j, j2, timeUnit);
    }

    @Override // io.reactivex.Scheduler
    public void shutdown() {
        b bVar;
        b bVar2;
        do {
            bVar = this.c.get();
            bVar2 = d;
            if (bVar == bVar2) {
                return;
            }
        } while (!this.c.compareAndSet(bVar, bVar2));
        bVar.b();
    }

    @Override // io.reactivex.Scheduler
    public void start() {
        b bVar = new b(f, this.b);
        if (this.c.compareAndSet(d, bVar)) {
            return;
        }
        bVar.b();
    }

    public ComputationScheduler(ThreadFactory threadFactory) {
        this.b = threadFactory;
        this.c = new AtomicReference<>(d);
        start();
    }

    public static final class a extends Scheduler.Worker {
        public final ListCompositeDisposable a = new ListCompositeDisposable();
        public final CompositeDisposable b = new CompositeDisposable();
        public final ListCompositeDisposable c;
        public final c d;
        public volatile boolean e;

        public a(c cVar) {
            this.d = cVar;
            ListCompositeDisposable listCompositeDisposable = new ListCompositeDisposable();
            this.c = listCompositeDisposable;
            listCompositeDisposable.add(this.a);
            this.c.add(this.b);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.e) {
                return;
            }
            this.e = true;
            this.c.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e;
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            return this.e ? EmptyDisposable.INSTANCE : this.d.scheduleActual(runnable, 0L, TimeUnit.MILLISECONDS, this.a);
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.e) {
                return EmptyDisposable.INSTANCE;
            }
            return this.d.scheduleActual(runnable, j, timeUnit, this.b);
        }
    }
}
