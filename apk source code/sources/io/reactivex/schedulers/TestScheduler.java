package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class TestScheduler extends Scheduler {
    public final Queue<b> b = new PriorityBlockingQueue(11);
    public long c;
    public volatile long d;

    public static final class b implements Comparable<b> {
        public final long a;
        public final Runnable b;
        public final a c;
        public final long d;

        public b(a aVar, long j, Runnable runnable, long j2) {
            this.a = j;
            this.b = runnable;
            this.c = aVar;
            this.d = j2;
        }

        @Override // java.lang.Comparable
        public int compareTo(b bVar) {
            b bVar2 = bVar;
            long j = this.a;
            long j2 = bVar2.a;
            return j == j2 ? ObjectHelper.compare(this.d, bVar2.d) : ObjectHelper.compare(j, j2);
        }

        public String toString() {
            return String.format("TimedRunnable(time = %d, run = %s)", Long.valueOf(this.a), this.b.toString());
        }
    }

    public TestScheduler() {
    }

    public final void a(long j) {
        while (true) {
            b bVarPeek = this.b.peek();
            if (bVarPeek == null) {
                break;
            }
            long j2 = bVarPeek.a;
            if (j2 > j) {
                break;
            }
            if (j2 == 0) {
                j2 = this.d;
            }
            this.d = j2;
            this.b.remove(bVarPeek);
            if (!bVarPeek.c.a) {
                bVarPeek.b.run();
            }
        }
        this.d = j;
    }

    public void advanceTimeBy(long j, TimeUnit timeUnit) {
        advanceTimeTo(timeUnit.toNanos(j) + this.d, TimeUnit.NANOSECONDS);
    }

    public void advanceTimeTo(long j, TimeUnit timeUnit) {
        a(timeUnit.toNanos(j));
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new a();
    }

    @Override // io.reactivex.Scheduler
    public long now(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.d, TimeUnit.NANOSECONDS);
    }

    public void triggerActions() {
        a(this.d);
    }

    public TestScheduler(long j, TimeUnit timeUnit) {
        this.d = timeUnit.toNanos(j);
    }

    public final class a extends Scheduler.Worker {
        public volatile boolean a;

        /* renamed from: io.reactivex.schedulers.TestScheduler$a$a, reason: collision with other inner class name */
        public final class RunnableC0075a implements Runnable {
            public final b a;

            public RunnableC0075a(b bVar) {
                this.a = bVar;
            }

            @Override // java.lang.Runnable
            public void run() {
                TestScheduler.this.b.remove(this.a);
            }
        }

        public a() {
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.a = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.a;
        }

        @Override // io.reactivex.Scheduler.Worker
        public long now(@NonNull TimeUnit timeUnit) {
            return TestScheduler.this.now(timeUnit);
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            long nanos = timeUnit.toNanos(j) + TestScheduler.this.d;
            TestScheduler testScheduler = TestScheduler.this;
            long j2 = testScheduler.c;
            testScheduler.c = 1 + j2;
            b bVar = new b(this, nanos, runnable, j2);
            TestScheduler.this.b.add(bVar);
            return Disposables.fromRunnable(new RunnableC0075a(bVar));
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            TestScheduler testScheduler = TestScheduler.this;
            long j = testScheduler.c;
            testScheduler.c = 1 + j;
            b bVar = new b(this, 0L, runnable, j);
            TestScheduler.this.b.add(bVar);
            return Disposables.fromRunnable(new RunnableC0075a(bVar));
        }
    }
}
