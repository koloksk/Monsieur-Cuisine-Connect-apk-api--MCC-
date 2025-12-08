package io.reactivex.internal.operators.observable;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class ObservableGenerate<T, S> extends Observable<T> {
    public final Callable<S> a;
    public final BiFunction<S, Emitter<T>, S> b;
    public final Consumer<? super S> c;

    public static final class a<T, S> implements Emitter<T>, Disposable {
        public final Observer<? super T> a;
        public final BiFunction<S, ? super Emitter<T>, S> b;
        public final Consumer<? super S> c;
        public S d;
        public volatile boolean e;
        public boolean f;
        public boolean g;

        public a(Observer<? super T> observer, BiFunction<S, ? super Emitter<T>, S> biFunction, Consumer<? super S> consumer, S s) {
            this.a = observer;
            this.b = biFunction;
            this.c = consumer;
            this.d = s;
        }

        public final void a(S s) {
            try {
                this.c.accept(s);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.e = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e;
        }

        @Override // io.reactivex.Emitter
        public void onComplete() {
            if (this.f) {
                return;
            }
            this.f = true;
            this.a.onComplete();
        }

        @Override // io.reactivex.Emitter
        public void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            this.f = true;
            this.a.onError(th);
        }

        @Override // io.reactivex.Emitter
        public void onNext(T t) {
            if (this.f) {
                return;
            }
            if (this.g) {
                onError(new IllegalStateException("onNext already called in this generate turn"));
            } else if (t == null) {
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            } else {
                this.g = true;
                this.a.onNext(t);
            }
        }
    }

    public ObservableGenerate(Callable<S> callable, BiFunction<S, Emitter<T>, S> biFunction, Consumer<? super S> consumer) {
        this.a = callable;
        this.b = biFunction;
        this.c = consumer;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        try {
            a aVar = new a(observer, this.b, this.c, this.a.call());
            observer.onSubscribe(aVar);
            S sApply = aVar.d;
            if (aVar.e) {
                aVar.d = null;
                aVar.a(sApply);
                return;
            }
            BiFunction<S, ? super Emitter<T>, S> biFunction = aVar.b;
            while (!aVar.e) {
                aVar.g = false;
                try {
                    sApply = biFunction.apply(sApply, aVar);
                    if (aVar.f) {
                        aVar.e = true;
                        aVar.d = null;
                        aVar.a(sApply);
                        return;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    aVar.d = null;
                    aVar.e = true;
                    aVar.onError(th);
                    aVar.a(sApply);
                    return;
                }
            }
            aVar.d = null;
            aVar.a(sApply);
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, observer);
        }
    }
}
