package defpackage;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class el implements Callable<Void>, Disposable {
    public static final FutureTask<Void> f = new FutureTask<>(Functions.EMPTY_RUNNABLE, null);
    public final Runnable a;
    public final ExecutorService d;
    public Thread e;
    public final AtomicReference<Future<?>> c = new AtomicReference<>();
    public final AtomicReference<Future<?>> b = new AtomicReference<>();

    public el(Runnable runnable, ExecutorService executorService) {
        this.a = runnable;
        this.d = executorService;
    }

    public void a(Future<?> future) {
        Future<?> future2;
        do {
            future2 = this.c.get();
            if (future2 == f) {
                future.cancel(this.e != Thread.currentThread());
                return;
            }
        } while (!this.c.compareAndSet(future2, future));
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        this.e = Thread.currentThread();
        try {
            this.a.run();
            Future<?> futureSubmit = this.d.submit(this);
            while (true) {
                Future<?> future = this.b.get();
                if (future == f) {
                    futureSubmit.cancel(this.e != Thread.currentThread());
                } else if (this.b.compareAndSet(future, futureSubmit)) {
                    break;
                }
            }
            this.e = null;
        } catch (Throwable th) {
            this.e = null;
            RxJavaPlugins.onError(th);
        }
        return null;
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        Future<?> andSet = this.c.getAndSet(f);
        if (andSet != null && andSet != f) {
            andSet.cancel(this.e != Thread.currentThread());
        }
        Future<?> andSet2 = this.b.getAndSet(f);
        if (andSet2 == null || andSet2 == f) {
            return;
        }
        andSet2.cancel(this.e != Thread.currentThread());
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return this.c.get() == f;
    }
}
