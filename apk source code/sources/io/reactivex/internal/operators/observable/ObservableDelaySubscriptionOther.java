package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes.dex */
public final class ObservableDelaySubscriptionOther<T, U> extends Observable<T> {
    public final ObservableSource<? extends T> a;
    public final ObservableSource<U> b;

    public final class a implements Observer<U> {
        public final SequentialDisposable a;
        public final Observer<? super T> b;
        public boolean c;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDelaySubscriptionOther$a$a, reason: collision with other inner class name */
        public final class C0057a implements Observer<T> {
            public C0057a() {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                a.this.b.onComplete();
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                a.this.b.onError(th);
            }

            @Override // io.reactivex.Observer
            public void onNext(T t) {
                a.this.b.onNext(t);
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                a.this.a.update(disposable);
            }
        }

        public a(SequentialDisposable sequentialDisposable, Observer<? super T> observer) {
            this.a = sequentialDisposable;
            this.b = observer;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.c) {
                return;
            }
            this.c = true;
            ObservableDelaySubscriptionOther.this.a.subscribe(new C0057a());
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
            } else {
                this.c = true;
                this.b.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(U u) {
            onComplete();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            this.a.update(disposable);
        }
    }

    public ObservableDelaySubscriptionOther(ObservableSource<? extends T> observableSource, ObservableSource<U> observableSource2) {
        this.a = observableSource;
        this.b = observableSource2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        observer.onSubscribe(sequentialDisposable);
        this.b.subscribe(new a(sequentialDisposable, observer));
    }
}
