package io.reactivex.internal.schedulers;

import defpackage.dl;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.SchedulerRunnableIntrospection;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ExecutorScheduler extends Scheduler {
    public static final Scheduler d = Schedulers.single();
    public final boolean b;

    @NonNull
    public final Executor c;

    public final class a implements Runnable {
        public final b a;

        public a(b bVar) {
            this.a = bVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            b bVar = this.a;
            bVar.b.replace(ExecutorScheduler.this.scheduleDirect(bVar));
        }
    }

    public static final class b extends AtomicReference<Runnable> implements Runnable, Disposable, SchedulerRunnableIntrospection {
        public static final long serialVersionUID = -4101336210206799084L;
        public final SequentialDisposable a;
        public final SequentialDisposable b;

        public b(Runnable runnable) {
            super(runnable);
            this.a = new SequentialDisposable();
            this.b = new SequentialDisposable();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (getAndSet(null) != null) {
                this.a.dispose();
                this.b.dispose();
            }
        }

        @Override // io.reactivex.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            Runnable runnable = get();
            return runnable != null ? runnable : Functions.EMPTY_RUNNABLE;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable = get();
            if (runnable != null) {
                try {
                    runnable.run();
                } finally {
                    lazySet(null);
                    this.a.lazySet(DisposableHelper.DISPOSED);
                    this.b.lazySet(DisposableHelper.DISPOSED);
                }
            }
        }
    }

    public ExecutorScheduler(@NonNull Executor executor, boolean z) {
        this.c = executor;
        this.b = z;
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new ExecutorWorker(this.c, this.b);
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable) {
        Runnable runnableOnSchedule = RxJavaPlugins.onSchedule(runnable);
        try {
            if (this.c instanceof ExecutorService) {
                ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(runnableOnSchedule);
                scheduledDirectTask.setFuture(((ExecutorService) this.c).submit(scheduledDirectTask));
                return scheduledDirectTask;
            }
            if (this.b) {
                ExecutorWorker.b bVar = new ExecutorWorker.b(runnableOnSchedule, null);
                this.c.execute(bVar);
                return bVar;
            }
            ExecutorWorker.a aVar = new ExecutorWorker.a(runnableOnSchedule);
            this.c.execute(aVar);
            return aVar;
        } catch (RejectedExecutionException e) {
            RxJavaPlugins.onError(e);
            return EmptyDisposable.INSTANCE;
        }
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        if (!(this.c instanceof ScheduledExecutorService)) {
            return super.schedulePeriodicallyDirect(runnable, j, j2, timeUnit);
        }
        try {
            ScheduledDirectPeriodicTask scheduledDirectPeriodicTask = new ScheduledDirectPeriodicTask(RxJavaPlugins.onSchedule(runnable));
            scheduledDirectPeriodicTask.setFuture(((ScheduledExecutorService) this.c).scheduleAtFixedRate(scheduledDirectPeriodicTask, j, j2, timeUnit));
            return scheduledDirectPeriodicTask;
        } catch (RejectedExecutionException e) {
            RxJavaPlugins.onError(e);
            return EmptyDisposable.INSTANCE;
        }
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        Runnable runnableOnSchedule = RxJavaPlugins.onSchedule(runnable);
        if (this.c instanceof ScheduledExecutorService) {
            try {
                ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(runnableOnSchedule);
                scheduledDirectTask.setFuture(((ScheduledExecutorService) this.c).schedule(scheduledDirectTask, j, timeUnit));
                return scheduledDirectTask;
            } catch (RejectedExecutionException e) {
                RxJavaPlugins.onError(e);
                return EmptyDisposable.INSTANCE;
            }
        }
        b bVar = new b(runnableOnSchedule);
        bVar.a.replace(d.scheduleDirect(new a(bVar), j, timeUnit));
        return bVar;
    }

    public static final class ExecutorWorker extends Scheduler.Worker implements Runnable {
        public final boolean a;
        public final Executor b;
        public volatile boolean d;
        public final AtomicInteger e = new AtomicInteger();
        public final CompositeDisposable f = new CompositeDisposable();
        public final MpscLinkedQueue<Runnable> c = new MpscLinkedQueue<>();

        public static final class a extends AtomicBoolean implements Runnable, Disposable {
            public static final long serialVersionUID = -2421395018820541164L;
            public final Runnable a;

            public a(Runnable runnable) {
                this.a = runnable;
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                lazySet(true);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return get();
            }

            @Override // java.lang.Runnable
            public void run() {
                if (get()) {
                    return;
                }
                try {
                    this.a.run();
                } finally {
                    lazySet(true);
                }
            }
        }

        public static final class b extends AtomicInteger implements Runnable, Disposable {
            public static final long serialVersionUID = -3603436687413320876L;
            public final Runnable a;
            public final DisposableContainer b;
            public volatile Thread c;

            public b(Runnable runnable, DisposableContainer disposableContainer) {
                this.a = runnable;
                this.b = disposableContainer;
            }

            public void a() {
                DisposableContainer disposableContainer = this.b;
                if (disposableContainer != null) {
                    disposableContainer.delete(this);
                }
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                while (true) {
                    int i = get();
                    if (i >= 2) {
                        return;
                    }
                    if (i == 0) {
                        if (compareAndSet(0, 4)) {
                            a();
                            return;
                        }
                    } else if (compareAndSet(1, 3)) {
                        Thread thread = this.c;
                        if (thread != null) {
                            thread.interrupt();
                            this.c = null;
                        }
                        set(4);
                        a();
                        return;
                    }
                }
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return get() >= 2;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (get() == 0) {
                    this.c = Thread.currentThread();
                    if (!compareAndSet(0, 1)) {
                        this.c = null;
                        return;
                    }
                    try {
                        this.a.run();
                        this.c = null;
                        if (compareAndSet(1, 2)) {
                            a();
                            return;
                        }
                        while (get() == 3) {
                            Thread.yield();
                        }
                        Thread.interrupted();
                    } catch (Throwable th) {
                        this.c = null;
                        if (compareAndSet(1, 2)) {
                            a();
                        } else {
                            while (get() == 3) {
                                Thread.yield();
                            }
                            Thread.interrupted();
                        }
                        throw th;
                    }
                }
            }
        }

        public final class c implements Runnable {
            public final SequentialDisposable a;
            public final Runnable b;

            public c(SequentialDisposable sequentialDisposable, Runnable runnable) {
                this.a = sequentialDisposable;
                this.b = runnable;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.replace(ExecutorWorker.this.schedule(this.b));
            }
        }

        public ExecutorWorker(Executor executor, boolean z) {
            this.b = executor;
            this.a = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.d) {
                return;
            }
            this.d = true;
            this.f.dispose();
            if (this.e.getAndIncrement() == 0) {
                this.c.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d;
        }

        @Override // java.lang.Runnable
        public void run() {
            MpscLinkedQueue<Runnable> mpscLinkedQueue = this.c;
            int iAddAndGet = 1;
            while (!this.d) {
                do {
                    Runnable runnablePoll = mpscLinkedQueue.poll();
                    if (runnablePoll != null) {
                        runnablePoll.run();
                    } else if (this.d) {
                        mpscLinkedQueue.clear();
                        return;
                    } else {
                        iAddAndGet = this.e.addAndGet(-iAddAndGet);
                        if (iAddAndGet == 0) {
                            return;
                        }
                    }
                } while (!this.d);
                mpscLinkedQueue.clear();
                return;
            }
            mpscLinkedQueue.clear();
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            Disposable aVar;
            if (this.d) {
                return EmptyDisposable.INSTANCE;
            }
            Runnable runnableOnSchedule = RxJavaPlugins.onSchedule(runnable);
            if (this.a) {
                aVar = new b(runnableOnSchedule, this.f);
                this.f.add(aVar);
            } else {
                aVar = new a(runnableOnSchedule);
            }
            this.c.offer(aVar);
            if (this.e.getAndIncrement() == 0) {
                try {
                    this.b.execute(this);
                } catch (RejectedExecutionException e) {
                    this.d = true;
                    this.c.clear();
                    RxJavaPlugins.onError(e);
                    return EmptyDisposable.INSTANCE;
                }
            }
            return aVar;
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (j <= 0) {
                return schedule(runnable);
            }
            if (this.d) {
                return EmptyDisposable.INSTANCE;
            }
            SequentialDisposable sequentialDisposable = new SequentialDisposable();
            SequentialDisposable sequentialDisposable2 = new SequentialDisposable(sequentialDisposable);
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(new c(sequentialDisposable2, RxJavaPlugins.onSchedule(runnable)), this.f);
            this.f.add(scheduledRunnable);
            Executor executor = this.b;
            if (executor instanceof ScheduledExecutorService) {
                try {
                    scheduledRunnable.setFuture(((ScheduledExecutorService) executor).schedule((Callable) scheduledRunnable, j, timeUnit));
                } catch (RejectedExecutionException e) {
                    this.d = true;
                    RxJavaPlugins.onError(e);
                    return EmptyDisposable.INSTANCE;
                }
            } else {
                scheduledRunnable.setFuture(new dl(ExecutorScheduler.d.scheduleDirect(scheduledRunnable, j, timeUnit)));
            }
            sequentialDisposable.replace(scheduledRunnable);
            return sequentialDisposable2;
        }
    }
}
