package io.reactivex.internal.operators.mixed;

import defpackage.q5;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableSwitchMapCompletable<T> extends Completable {
    public final Observable<T> a;
    public final Function<? super T, ? extends CompletableSource> b;
    public final boolean c;

    public static final class a<T> implements Observer<T>, Disposable {
        public static final C0049a h = new C0049a(null);
        public final CompletableObserver a;
        public final Function<? super T, ? extends CompletableSource> b;
        public final boolean c;
        public final AtomicThrowable d = new AtomicThrowable();
        public final AtomicReference<C0049a> e = new AtomicReference<>();
        public volatile boolean f;
        public Disposable g;

        /* renamed from: io.reactivex.internal.operators.mixed.ObservableSwitchMapCompletable$a$a, reason: collision with other inner class name */
        public static final class C0049a extends AtomicReference<Disposable> implements CompletableObserver {
            public static final long serialVersionUID = -8003404460084760287L;
            public final a<?> a;

            public C0049a(a<?> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                a<?> aVar = this.a;
                if (aVar.e.compareAndSet(this, null) && aVar.f) {
                    Throwable thTerminate = aVar.d.terminate();
                    if (thTerminate == null) {
                        aVar.a.onComplete();
                    } else {
                        aVar.a.onError(thTerminate);
                    }
                }
            }

            @Override // io.reactivex.CompletableObserver
            public void onError(Throwable th) {
                a<?> aVar = this.a;
                if (!aVar.e.compareAndSet(this, null) || !aVar.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (aVar.c) {
                    if (aVar.f) {
                        aVar.a.onError(aVar.d.terminate());
                        return;
                    }
                    return;
                }
                aVar.dispose();
                Throwable thTerminate = aVar.d.terminate();
                if (thTerminate != ExceptionHelper.TERMINATED) {
                    aVar.a.onError(thTerminate);
                }
            }

            @Override // io.reactivex.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }
        }

        public a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, boolean z) {
            this.a = completableObserver;
            this.b = function;
            this.c = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.g.dispose();
            C0049a andSet = this.e.getAndSet(h);
            if (andSet == null || andSet == h) {
                return;
            }
            DisposableHelper.dispose(andSet);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e.get() == h;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.f = true;
            if (this.e.get() == null) {
                Throwable thTerminate = this.d.terminate();
                if (thTerminate == null) {
                    this.a.onComplete();
                } else {
                    this.a.onError(thTerminate);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.c) {
                onComplete();
                return;
            }
            C0049a andSet = this.e.getAndSet(h);
            if (andSet != null && andSet != h) {
                DisposableHelper.dispose(andSet);
            }
            Throwable thTerminate = this.d.terminate();
            if (thTerminate != ExceptionHelper.TERMINATED) {
                this.a.onError(thTerminate);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            C0049a c0049a;
            try {
                CompletableSource completableSource = (CompletableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null CompletableSource");
                C0049a c0049a2 = new C0049a(this);
                do {
                    c0049a = this.e.get();
                    if (c0049a == h) {
                        return;
                    }
                } while (!this.e.compareAndSet(c0049a, c0049a2));
                if (c0049a != null) {
                    DisposableHelper.dispose(c0049a);
                }
                completableSource.subscribe(c0049a2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.g.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.g, disposable)) {
                this.g = disposable;
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableSwitchMapCompletable(Observable<T> observable, Function<? super T, ? extends CompletableSource> function, boolean z) {
        this.a = observable;
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        if (q5.a(this.a, this.b, completableObserver)) {
            return;
        }
        this.a.subscribe(new a(completableObserver, this.b, this.c));
    }
}
