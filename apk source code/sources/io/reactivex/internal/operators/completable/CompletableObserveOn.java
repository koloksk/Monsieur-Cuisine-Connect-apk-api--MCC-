package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class CompletableObserveOn extends Completable {
    public final CompletableSource a;
    public final Scheduler b;

    public static final class a extends AtomicReference<Disposable> implements CompletableObserver, Disposable, Runnable {
        public static final long serialVersionUID = 8571289934935992137L;
        public final CompletableObserver a;
        public final Scheduler b;
        public Throwable c;

        public a(CompletableObserver completableObserver, Scheduler scheduler) {
            this.a = completableObserver;
            this.b = scheduler;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            DisposableHelper.replace(this, this.b.scheduleDirect(this));
        }

        @Override // io.reactivex.CompletableObserver
        public void onError(Throwable th) {
            this.c = th;
            DisposableHelper.replace(this, this.b.scheduleDirect(this));
        }

        @Override // io.reactivex.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.a.onSubscribe(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Throwable th = this.c;
            if (th == null) {
                this.a.onComplete();
            } else {
                this.c = null;
                this.a.onError(th);
            }
        }
    }

    public CompletableObserveOn(CompletableSource completableSource, Scheduler scheduler) {
        this.a = completableSource;
        this.b = scheduler;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new a(completableObserver, this.b));
    }
}
