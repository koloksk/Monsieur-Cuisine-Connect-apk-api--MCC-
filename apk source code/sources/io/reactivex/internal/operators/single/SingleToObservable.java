package io.reactivex.internal.operators.single;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;

/* loaded from: classes.dex */
public final class SingleToObservable<T> extends Observable<T> {
    public final SingleSource<? extends T> a;

    public static final class a<T> extends DeferredScalarDisposable<T> implements SingleObserver<T> {
        public static final long serialVersionUID = 3786543492451018833L;
        public Disposable a;

        public a(Observer<? super T> observer) {
            super(observer);
        }

        @Override // io.reactivex.internal.observers.DeferredScalarDisposable, io.reactivex.disposables.Disposable
        public void dispose() {
            super.dispose();
            this.a.dispose();
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            error(th);
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.a, disposable)) {
                this.a = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            complete(t);
        }
    }

    public SingleToObservable(SingleSource<? extends T> singleSource) {
        this.a = singleSource;
    }

    public static <T> SingleObserver<T> create(Observer<? super T> observer) {
        return new a(observer);
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(create(observer));
    }
}
