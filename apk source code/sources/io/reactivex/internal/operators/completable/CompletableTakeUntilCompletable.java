package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class CompletableTakeUntilCompletable extends Completable {
    public final Completable a;
    public final CompletableSource b;

    public static final class a extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
        public static final long serialVersionUID = 3533011714830024923L;
        public final CompletableObserver a;
        public final C0013a b = new C0013a(this);
        public final AtomicBoolean c = new AtomicBoolean();

        /* renamed from: io.reactivex.internal.operators.completable.CompletableTakeUntilCompletable$a$a, reason: collision with other inner class name */
        public static final class C0013a extends AtomicReference<Disposable> implements CompletableObserver {
            public static final long serialVersionUID = 5176264485428790318L;
            public final a a;

            public C0013a(a aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                a aVar = this.a;
                if (aVar.c.compareAndSet(false, true)) {
                    DisposableHelper.dispose(aVar);
                    aVar.a.onComplete();
                }
            }

            @Override // io.reactivex.CompletableObserver
            public void onError(Throwable th) {
                a aVar = this.a;
                if (!aVar.c.compareAndSet(false, true)) {
                    RxJavaPlugins.onError(th);
                } else {
                    DisposableHelper.dispose(aVar);
                    aVar.a.onError(th);
                }
            }

            @Override // io.reactivex.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }
        }

        public a(CompletableObserver completableObserver) {
            this.a = completableObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.c.compareAndSet(false, true)) {
                DisposableHelper.dispose(this);
                DisposableHelper.dispose(this.b);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.get();
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            if (this.c.compareAndSet(false, true)) {
                DisposableHelper.dispose(this.b);
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.CompletableObserver
        public void onError(Throwable th) {
            if (!this.c.compareAndSet(false, true)) {
                RxJavaPlugins.onError(th);
            } else {
                DisposableHelper.dispose(this.b);
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public CompletableTakeUntilCompletable(Completable completable, CompletableSource completableSource) {
        this.a = completable;
        this.b = completableSource;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        a aVar = new a(completableObserver);
        completableObserver.onSubscribe(aVar);
        this.b.subscribe(aVar.b);
        this.a.subscribe(aVar);
    }
}
