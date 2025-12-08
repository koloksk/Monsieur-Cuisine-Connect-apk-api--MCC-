package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class IoScheduler extends Scheduler {
    public static final long KEEP_ALIVE_TIME_DEFAULT = 60;
    public static final RxThreadFactory d;
    public static final RxThreadFactory e;
    public static final c h;
    public static final a i;
    public final ThreadFactory b;
    public final AtomicReference<a> c;
    public static final TimeUnit g = TimeUnit.SECONDS;
    public static final long f = Long.getLong("rx2.io-keep-alive-time", 60).longValue();

    public static final class a implements Runnable {
        public final long a;
        public final ConcurrentLinkedQueue<c> b;
        public final CompositeDisposable c;
        public final ScheduledExecutorService d;
        public final Future<?> e;
        public final ThreadFactory f;

        public a(long j, TimeUnit timeUnit, ThreadFactory threadFactory) {
            ScheduledFuture<?> scheduledFutureScheduleWithFixedDelay;
            this.a = timeUnit != null ? timeUnit.toNanos(j) : 0L;
            this.b = new ConcurrentLinkedQueue<>();
            this.c = new CompositeDisposable();
            this.f = threadFactory;
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = null;
            if (timeUnit != null) {
                scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, IoScheduler.e);
                long j2 = this.a;
                scheduledFutureScheduleWithFixedDelay = scheduledExecutorServiceNewScheduledThreadPool.scheduleWithFixedDelay(this, j2, j2, TimeUnit.NANOSECONDS);
            } else {
                scheduledFutureScheduleWithFixedDelay = null;
            }
            this.d = scheduledExecutorServiceNewScheduledThreadPool;
            this.e = scheduledFutureScheduleWithFixedDelay;
        }

        public void a() {
            this.c.dispose();
            Future<?> future = this.e;
            if (future != null) {
                future.cancel(true);
            }
            ScheduledExecutorService scheduledExecutorService = this.d;
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdownNow();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.b.isEmpty()) {
                return;
            }
            long jNanoTime = System.nanoTime();
            Iterator<c> it = this.b.iterator();
            while (it.hasNext()) {
                c next = it.next();
                if (next.c > jNanoTime) {
                    return;
                }
                if (this.b.remove(next)) {
                    this.c.remove(next);
                }
            }
        }
    }

    public static final class b extends Scheduler.Worker {
        public final a b;
        public final c c;
        public final AtomicBoolean d = new AtomicBoolean();
        public final CompositeDisposable a = new CompositeDisposable();

        public b(a aVar) {
            c cVar;
            c cVar2;
            this.b = aVar;
            if (aVar.c.isDisposed()) {
                cVar2 = IoScheduler.h;
            } else {
                while (true) {
                    if (aVar.b.isEmpty()) {
                        cVar = new c(aVar.f);
                        aVar.c.add(cVar);
                        break;
                    } else {
                        cVar = aVar.b.poll();
                        if (cVar != null) {
                            break;
                        }
                    }
                }
                cVar2 = cVar;
            }
            this.c = cVar2;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.d.compareAndSet(false, true)) {
                this.a.dispose();
                a aVar = this.b;
                c cVar = this.c;
                if (aVar == null) {
                    throw null;
                }
                cVar.c = System.nanoTime() + aVar.a;
                aVar.b.offer(cVar);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.get();
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            return this.a.isDisposed() ? EmptyDisposable.INSTANCE : this.c.scheduleActual(runnable, j, timeUnit, this.a);
        }
    }

    public static final class c extends NewThreadWorker {
        public long c;

        public c(ThreadFactory threadFactory) {
            super(threadFactory);
            this.c = 0L;
        }
    }

    static {
        c cVar = new c(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));
        h = cVar;
        cVar.dispose();
        int iMax = Math.max(1, Math.min(10, Integer.getInteger("rx2.io-priority", 5).intValue()));
        d = new RxThreadFactory("RxCachedThreadScheduler", iMax);
        e = new RxThreadFactory("RxCachedWorkerPoolEvictor", iMax);
        a aVar = new a(0L, null, d);
        i = aVar;
        aVar.a();
    }

    public IoScheduler() {
        this(d);
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new b(this.c.get());
    }

    @Override // io.reactivex.Scheduler
    public void shutdown() {
        a aVar;
        a aVar2;
        do {
            aVar = this.c.get();
            aVar2 = i;
            if (aVar == aVar2) {
                return;
            }
        } while (!this.c.compareAndSet(aVar, aVar2));
        aVar.a();
    }

    public int size() {
        return this.c.get().c.size();
    }

    @Override // io.reactivex.Scheduler
    public void start() {
        a aVar = new a(f, g, this.b);
        if (this.c.compareAndSet(i, aVar)) {
            return;
        }
        aVar.a();
    }

    public IoScheduler(ThreadFactory threadFactory) {
        this.b = threadFactory;
        this.c = new AtomicReference<>(i);
        start();
    }
}
