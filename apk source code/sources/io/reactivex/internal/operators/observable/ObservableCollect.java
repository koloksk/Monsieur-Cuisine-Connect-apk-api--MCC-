package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class ObservableCollect<T, U> extends bl<T, U> {
    public final Callable<? extends U> a;
    public final BiConsumer<? super U, ? super T> b;

    public static final class a<T, U> implements Observer<T>, Disposable {
        public final Observer<? super U> a;
        public final BiConsumer<? super U, ? super T> b;
        public final U c;
        public Disposable d;
        public boolean e;

        public a(Observer<? super U> observer, U u, BiConsumer<? super U, ? super T> biConsumer) {
            this.a = observer;
            this.b = biConsumer;
            this.c = u;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.d.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.e) {
                return;
            }
            this.e = true;
            this.a.onNext(this.c);
            this.a.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
            } else {
                this.e = true;
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.e) {
                return;
            }
            try {
                this.b.accept(this.c, t);
            } catch (Throwable th) {
                this.d.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableCollect(ObservableSource<T> observableSource, Callable<? extends U> callable, BiConsumer<? super U, ? super T> biConsumer) {
        super(observableSource);
        this.a = callable;
        this.b = biConsumer;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super U> observer) {
        try {
            this.source.subscribe(new a(observer, ObjectHelper.requireNonNull(this.a.call(), "The initialSupplier returned a null value"), this.b));
        } catch (Throwable th) {
            EmptyDisposable.error(th, observer);
        }
    }
}
