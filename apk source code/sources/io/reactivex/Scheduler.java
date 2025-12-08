package io.reactivex;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.internal.schedulers.SchedulerWhen;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.SchedulerRunnableIntrospection;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public abstract class Scheduler {
    public static final long a = TimeUnit.MINUTES.toNanos(Long.getLong("rx2.scheduler.drift-tolerance", 15).longValue());

    public static abstract class Worker implements Disposable {

        public final class a implements Runnable, SchedulerRunnableIntrospection {

            @NonNull
            public final Runnable a;

            @NonNull
            public final SequentialDisposable b;
            public final long c;
            public long d;
            public long e;
            public long f;

            public a(long j, @NonNull Runnable runnable, long j2, @NonNull SequentialDisposable sequentialDisposable, long j3) {
                this.a = runnable;
                this.b = sequentialDisposable;
                this.c = j3;
                this.e = j2;
                this.f = j;
            }

            @Override // io.reactivex.schedulers.SchedulerRunnableIntrospection
            public Runnable getWrappedRunnable() {
                return this.a;
            }

            /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r10 = this;
                    java.lang.Runnable r0 = r10.a
                    r0.run()
                    io.reactivex.internal.disposables.SequentialDisposable r0 = r10.b
                    boolean r0 = r0.isDisposed()
                    if (r0 != 0) goto L52
                    io.reactivex.Scheduler$Worker r0 = io.reactivex.Scheduler.Worker.this
                    java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
                    long r0 = r0.now(r1)
                    long r2 = io.reactivex.Scheduler.a
                    long r4 = r0 + r2
                    long r6 = r10.e
                    int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                    r8 = 1
                    if (r4 < 0) goto L34
                    long r4 = r10.c
                    long r6 = r6 + r4
                    long r6 = r6 + r2
                    int r2 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                    if (r2 < 0) goto L2a
                    goto L34
                L2a:
                    long r2 = r10.f
                    long r6 = r10.d
                    long r6 = r6 + r8
                    r10.d = r6
                    long r6 = r6 * r4
                    long r6 = r6 + r2
                    goto L42
                L34:
                    long r2 = r10.c
                    long r6 = r0 + r2
                    long r4 = r10.d
                    long r4 = r4 + r8
                    r10.d = r4
                    long r2 = r2 * r4
                    long r2 = r6 - r2
                    r10.f = r2
                L42:
                    r10.e = r0
                    long r6 = r6 - r0
                    io.reactivex.internal.disposables.SequentialDisposable r0 = r10.b
                    io.reactivex.Scheduler$Worker r1 = io.reactivex.Scheduler.Worker.this
                    java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.NANOSECONDS
                    io.reactivex.disposables.Disposable r1 = r1.schedule(r10, r6, r2)
                    r0.replace(r1)
                L52:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: io.reactivex.Scheduler.Worker.a.run():void");
            }
        }

        public long now(@NonNull TimeUnit timeUnit) {
            return timeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            return schedule(runnable, 0L, TimeUnit.NANOSECONDS);
        }

        @NonNull
        public abstract Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit);

        @NonNull
        public Disposable schedulePeriodically(@NonNull Runnable runnable, long j, long j2, @NonNull TimeUnit timeUnit) {
            SequentialDisposable sequentialDisposable = new SequentialDisposable();
            SequentialDisposable sequentialDisposable2 = new SequentialDisposable(sequentialDisposable);
            Runnable runnableOnSchedule = RxJavaPlugins.onSchedule(runnable);
            long nanos = timeUnit.toNanos(j2);
            long jNow = now(TimeUnit.NANOSECONDS);
            Disposable disposableSchedule = schedule(new a(timeUnit.toNanos(j) + jNow, runnableOnSchedule, jNow, sequentialDisposable2, nanos), j, timeUnit);
            if (disposableSchedule == EmptyDisposable.INSTANCE) {
                return disposableSchedule;
            }
            sequentialDisposable.replace(disposableSchedule);
            return sequentialDisposable2;
        }
    }

    public static final class a implements Disposable, Runnable, SchedulerRunnableIntrospection {

        @NonNull
        public final Runnable a;

        @NonNull
        public final Worker b;

        @Nullable
        public Thread c;

        public a(@NonNull Runnable runnable, @NonNull Worker worker) {
            this.a = runnable;
            this.b = worker;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.c == Thread.currentThread()) {
                Worker worker = this.b;
                if (worker instanceof NewThreadWorker) {
                    ((NewThreadWorker) worker).shutdown();
                    return;
                }
            }
            this.b.dispose();
        }

        @Override // io.reactivex.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            return this.a;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // java.lang.Runnable
        public void run() {
            this.c = Thread.currentThread();
            try {
                this.a.run();
            } finally {
                dispose();
                this.c = null;
            }
        }
    }

    public static final class b implements Disposable, Runnable, SchedulerRunnableIntrospection {

        @NonNull
        public final Runnable a;

        @NonNull
        public final Worker b;
        public volatile boolean c;

        public b(@NonNull Runnable runnable, @NonNull Worker worker) {
            this.a = runnable;
            this.b = worker;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c = true;
            this.b.dispose();
        }

        @Override // io.reactivex.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            return this.a;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.c) {
                return;
            }
            try {
                this.a.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.dispose();
                throw ExceptionHelper.wrapOrThrow(th);
            }
        }
    }

    public static long clockDriftTolerance() {
        return a;
    }

    @NonNull
    public abstract Worker createWorker();

    public long now(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable) {
        return scheduleDirect(runnable, 0L, TimeUnit.NANOSECONDS);
    }

    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, @NonNull TimeUnit timeUnit) {
        Worker workerCreateWorker = createWorker();
        b bVar = new b(RxJavaPlugins.onSchedule(runnable), workerCreateWorker);
        Disposable disposableSchedulePeriodically = workerCreateWorker.schedulePeriodically(bVar, j, j2, timeUnit);
        return disposableSchedulePeriodically == EmptyDisposable.INSTANCE ? disposableSchedulePeriodically : bVar;
    }

    public void shutdown() {
    }

    public void start() {
    }

    @NonNull
    public <S extends Scheduler & Disposable> S when(@NonNull Function<Flowable<Flowable<Completable>>, Completable> function) {
        return new SchedulerWhen(function, this);
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
        Worker workerCreateWorker = createWorker();
        a aVar = new a(RxJavaPlugins.onSchedule(runnable), workerCreateWorker);
        workerCreateWorker.schedule(aVar, j, timeUnit);
        return aVar;
    }
}
