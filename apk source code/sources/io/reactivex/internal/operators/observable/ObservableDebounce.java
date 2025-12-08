package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableDebounce<T, U> extends bl<T, T> {
    public final Function<? super T, ? extends ObservableSource<U>> a;

    public static final class a<T, U> implements Observer<T>, Disposable {
        public final Observer<? super T> a;
        public final Function<? super T, ? extends ObservableSource<U>> b;
        public Disposable c;
        public final AtomicReference<Disposable> d = new AtomicReference<>();
        public volatile long e;
        public boolean f;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDebounce$a$a, reason: collision with other inner class name */
        public static final class C0055a<T, U> extends DisposableObserver<U> {
            public final a<T, U> b;
            public final long c;
            public final T d;
            public boolean e;
            public final AtomicBoolean f = new AtomicBoolean();

            public C0055a(a<T, U> aVar, long j, T t) {
                this.b = aVar;
                this.c = j;
                this.d = t;
            }

            public void a() {
                if (this.f.compareAndSet(false, true)) {
                    a<T, U> aVar = this.b;
                    long j = this.c;
                    T t = this.d;
                    if (j == aVar.e) {
                        aVar.a.onNext(t);
                    }
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (this.e) {
                    return;
                }
                this.e = true;
                a();
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                if (this.e) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                this.e = true;
                a<T, U> aVar = this.b;
                DisposableHelper.dispose(aVar.d);
                aVar.a.onError(th);
            }

            @Override // io.reactivex.Observer
            public void onNext(U u) {
                if (this.e) {
                    return;
                }
                this.e = true;
                dispose();
                a();
            }
        }

        public a(Observer<? super T> observer, Function<? super T, ? extends ObservableSource<U>> function) {
            this.a = observer;
            this.b = function;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.dispose();
            DisposableHelper.dispose(this.d);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.f) {
                return;
            }
            this.f = true;
            Disposable disposable = this.d.get();
            if (disposable != DisposableHelper.DISPOSED) {
                C0055a c0055a = (C0055a) disposable;
                if (c0055a != null) {
                    c0055a.a();
                }
                DisposableHelper.dispose(this.d);
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.d);
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.f) {
                return;
            }
            long j = this.e + 1;
            this.e = j;
            Disposable disposable = this.d.get();
            if (disposable != null) {
                disposable.dispose();
            }
            try {
                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The ObservableSource supplied is null");
                C0055a c0055a = new C0055a(this, j, t);
                if (this.d.compareAndSet(disposable, c0055a)) {
                    observableSource.subscribe(c0055a);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableDebounce(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<U>> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(new SerializedObserver(observer), this.a));
    }
}
