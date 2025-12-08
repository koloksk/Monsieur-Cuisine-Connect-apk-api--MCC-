package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;

/* loaded from: classes.dex */
public final class ObservableSwitchIfEmpty<T> extends bl<T, T> {
    public final ObservableSource<? extends T> a;

    public static final class a<T> implements Observer<T> {
        public final Observer<? super T> a;
        public final ObservableSource<? extends T> b;
        public boolean d = true;
        public final SequentialDisposable c = new SequentialDisposable();

        public a(Observer<? super T> observer, ObservableSource<? extends T> observableSource) {
            this.a = observer;
            this.b = observableSource;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.d) {
                this.a.onComplete();
            } else {
                this.d = false;
                this.b.subscribe(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.d) {
                this.d = false;
            }
            this.a.onNext(t);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            this.c.update(disposable);
        }
    }

    public ObservableSwitchIfEmpty(ObservableSource<T> observableSource, ObservableSource<? extends T> observableSource2) {
        super(observableSource);
        this.a = observableSource2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar = new a(observer, this.a);
        observer.onSubscribe(aVar.c);
        this.source.subscribe(aVar);
    }
}
