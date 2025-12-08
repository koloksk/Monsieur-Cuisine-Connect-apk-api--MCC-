package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class CompletableTimeout extends Completable {
    public final CompletableSource a;
    public final long b;
    public final TimeUnit c;
    public final Scheduler d;
    public final CompletableSource e;

    public final class a implements Runnable {
        public final AtomicBoolean a;
        public final CompositeDisposable b;
        public final CompletableObserver c;

        /* renamed from: io.reactivex.internal.operators.completable.CompletableTimeout$a$a, reason: collision with other inner class name */
        public final class C0014a implements CompletableObserver {
            public C0014a() {
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                a.this.b.dispose();
                a.this.c.onComplete();
            }

            @Override // io.reactivex.CompletableObserver
            public void onError(Throwable th) {
                a.this.b.dispose();
                a.this.c.onError(th);
            }

            @Override // io.reactivex.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                a.this.b.add(disposable);
            }
        }

        public a(AtomicBoolean atomicBoolean, CompositeDisposable compositeDisposable, CompletableObserver completableObserver) {
            this.a = atomicBoolean;
            this.b = compositeDisposable;
            this.c = completableObserver;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.a.compareAndSet(false, true)) {
                this.b.clear();
                CompletableSource completableSource = CompletableTimeout.this.e;
                if (completableSource != null) {
                    completableSource.subscribe(new C0014a());
                    return;
                }
                CompletableObserver completableObserver = this.c;
                CompletableTimeout completableTimeout = CompletableTimeout.this;
                completableObserver.onError(new TimeoutException(ExceptionHelper.timeoutMessage(completableTimeout.b, completableTimeout.c)));
            }
        }
    }

    public static final class b implements CompletableObserver {
        public final CompositeDisposable a;
        public final AtomicBoolean b;
        public final CompletableObserver c;

        public b(CompositeDisposable compositeDisposable, AtomicBoolean atomicBoolean, CompletableObserver completableObserver) {
            this.a = compositeDisposable;
            this.b = atomicBoolean;
            this.c = completableObserver;
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            if (this.b.compareAndSet(false, true)) {
                this.a.dispose();
                this.c.onComplete();
            }
        }

        @Override // io.reactivex.CompletableObserver
        public void onError(Throwable th) {
            if (!this.b.compareAndSet(false, true)) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.dispose();
                this.c.onError(th);
            }
        }

        @Override // io.reactivex.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.a.add(disposable);
        }
    }

    public CompletableTimeout(CompletableSource completableSource, long j, TimeUnit timeUnit, Scheduler scheduler, CompletableSource completableSource2) {
        this.a = completableSource;
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = completableSource2;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        completableObserver.onSubscribe(compositeDisposable);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        compositeDisposable.add(this.d.scheduleDirect(new a(atomicBoolean, compositeDisposable, completableObserver), this.b, this.c));
        this.a.subscribe(new b(compositeDisposable, atomicBoolean, completableObserver));
    }
}
