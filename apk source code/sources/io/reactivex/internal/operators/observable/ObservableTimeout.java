package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableTimeoutTimed;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableTimeout<T, U, V> extends bl<T, T> {
    public final ObservableSource<U> a;
    public final Function<? super T, ? extends ObservableSource<V>> b;
    public final ObservableSource<? extends T> c;

    public static final class a extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
        public static final long serialVersionUID = 8708641127342403073L;
        public final d a;
        public final long b;

        public a(long j, d dVar) {
            this.b = j;
            this.a = dVar;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            Object obj = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (obj != disposableHelper) {
                lazySet(disposableHelper);
                this.a.a(this.b);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            Object obj = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (obj == disposableHelper) {
                RxJavaPlugins.onError(th);
            } else {
                lazySet(disposableHelper);
                this.a.a(this.b, th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(Object obj) {
            Disposable disposable = (Disposable) get();
            if (disposable != DisposableHelper.DISPOSED) {
                disposable.dispose();
                lazySet(DisposableHelper.DISPOSED);
                this.a.a(this.b);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public interface d extends ObservableTimeoutTimed.d {
        void a(long j, Throwable th);
    }

    public ObservableTimeout(Observable<T> observable, ObservableSource<U> observableSource, Function<? super T, ? extends ObservableSource<V>> function, ObservableSource<? extends T> observableSource2) {
        super(observable);
        this.a = observableSource;
        this.b = function;
        this.c = observableSource2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        if (this.c == null) {
            c cVar = new c(observer, this.b);
            observer.onSubscribe(cVar);
            ObservableSource<U> observableSource = this.a;
            if (observableSource != null) {
                a aVar = new a(0L, cVar);
                if (cVar.c.replace(aVar)) {
                    observableSource.subscribe(aVar);
                }
            }
            this.source.subscribe(cVar);
            return;
        }
        b bVar = new b(observer, this.b, this.c);
        observer.onSubscribe(bVar);
        ObservableSource<U> observableSource2 = this.a;
        if (observableSource2 != null) {
            a aVar2 = new a(0L, bVar);
            if (bVar.c.replace(aVar2)) {
                observableSource2.subscribe(aVar2);
            }
        }
        this.source.subscribe(bVar);
    }

    public static final class c<T> extends AtomicLong implements Observer<T>, Disposable, d {
        public static final long serialVersionUID = 3764492702657003550L;
        public final Observer<? super T> a;
        public final Function<? super T, ? extends ObservableSource<?>> b;
        public final SequentialDisposable c = new SequentialDisposable();
        public final AtomicReference<Disposable> d = new AtomicReference<>();

        public c(Observer<? super T> observer, Function<? super T, ? extends ObservableSource<?>> function) {
            this.a = observer;
            this.b = function;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeoutTimed.d
        public void a(long j) {
            if (compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this.d);
                this.a.onError(new TimeoutException());
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.d);
            this.c.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.d.get());
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.c.dispose();
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (getAndSet(Long.MAX_VALUE) == Long.MAX_VALUE) {
                RxJavaPlugins.onError(th);
            } else {
                this.c.dispose();
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            long j = get();
            if (j != Long.MAX_VALUE) {
                long j2 = 1 + j;
                if (compareAndSet(j, j2)) {
                    Disposable disposable = this.c.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.a.onNext(t);
                    try {
                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The itemTimeoutIndicator returned a null ObservableSource.");
                        a aVar = new a(j2, this);
                        if (this.c.replace(aVar)) {
                            observableSource.subscribe(aVar);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.d.get().dispose();
                        getAndSet(Long.MAX_VALUE);
                        this.a.onError(th);
                    }
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.d, disposable);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeout.d
        public void a(long j, Throwable th) {
            if (compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this.d);
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public static final class b<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable, d {
        public static final long serialVersionUID = -7508389464265974549L;
        public final Observer<? super T> a;
        public final Function<? super T, ? extends ObservableSource<?>> b;
        public final SequentialDisposable c = new SequentialDisposable();
        public final AtomicLong d = new AtomicLong();
        public final AtomicReference<Disposable> e = new AtomicReference<>();
        public ObservableSource<? extends T> f;

        public b(Observer<? super T> observer, Function<? super T, ? extends ObservableSource<?>> function, ObservableSource<? extends T> observableSource) {
            this.a = observer;
            this.b = function;
            this.f = observableSource;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeoutTimed.d
        public void a(long j) {
            if (this.d.compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this.e);
                ObservableSource<? extends T> observableSource = this.f;
                this.f = null;
                observableSource.subscribe(new ObservableTimeoutTimed.a(this.a, this));
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.e);
            DisposableHelper.dispose(this);
            this.c.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.d.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.c.dispose();
                this.a.onComplete();
                this.c.dispose();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.d.getAndSet(Long.MAX_VALUE) == Long.MAX_VALUE) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c.dispose();
            this.a.onError(th);
            this.c.dispose();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            long j = this.d.get();
            if (j != Long.MAX_VALUE) {
                long j2 = 1 + j;
                if (this.d.compareAndSet(j, j2)) {
                    Disposable disposable = this.c.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.a.onNext(t);
                    try {
                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The itemTimeoutIndicator returned a null ObservableSource.");
                        a aVar = new a(j2, this);
                        if (this.c.replace(aVar)) {
                            observableSource.subscribe(aVar);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.e.get().dispose();
                        this.d.getAndSet(Long.MAX_VALUE);
                        this.a.onError(th);
                    }
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.e, disposable);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableTimeout.d
        public void a(long j, Throwable th) {
            if (this.d.compareAndSet(j, Long.MAX_VALUE)) {
                DisposableHelper.dispose(this);
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }
}
