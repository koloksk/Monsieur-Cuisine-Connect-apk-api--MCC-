package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableConcatWithSingle<T> extends bl<T, T> {
    public final SingleSource<? extends T> a;

    public static final class a<T> extends AtomicReference<Disposable> implements Observer<T>, SingleObserver<T>, Disposable {
        public static final long serialVersionUID = -1953724749712440952L;
        public final Observer<? super T> a;
        public SingleSource<? extends T> b;
        public boolean c;

        public a(Observer<? super T> observer, SingleSource<? extends T> singleSource) {
            this.a = observer;
            this.b = singleSource;
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
            this.c = true;
            DisposableHelper.replace(this, null);
            SingleSource<? extends T> singleSource = this.b;
            this.b = null;
            singleSource.subscribe(this);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (!DisposableHelper.setOnce(this, disposable) || this.c) {
                return;
            }
            this.a.onSubscribe(this);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.a.onNext(t);
            this.a.onComplete();
        }
    }

    public ObservableConcatWithSingle(Observable<T> observable, SingleSource<? extends T> singleSource) {
        super(observable);
        this.a = singleSource;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a));
    }
}
