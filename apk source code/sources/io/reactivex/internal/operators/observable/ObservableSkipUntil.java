package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;

/* loaded from: classes.dex */
public final class ObservableSkipUntil<T, U> extends bl<T, T> {
    public final ObservableSource<U> a;

    public final class a implements Observer<U> {
        public final ArrayCompositeDisposable a;
        public final b<T> b;
        public final SerializedObserver<T> c;
        public Disposable d;

        public a(ObservableSkipUntil observableSkipUntil, ArrayCompositeDisposable arrayCompositeDisposable, b<T> bVar, SerializedObserver<T> serializedObserver) {
            this.a = arrayCompositeDisposable;
            this.b = bVar;
            this.c = serializedObserver;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.b.d = true;
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.dispose();
            this.c.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(U u) {
            this.d.dispose();
            this.b.d = true;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.setResource(1, disposable);
            }
        }
    }

    public static final class b<T> implements Observer<T> {
        public final Observer<? super T> a;
        public final ArrayCompositeDisposable b;
        public Disposable c;
        public volatile boolean d;
        public boolean e;

        public b(Observer<? super T> observer, ArrayCompositeDisposable arrayCompositeDisposable) {
            this.a = observer;
            this.b = arrayCompositeDisposable;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.b.dispose();
            this.a.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.b.dispose();
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.e) {
                this.a.onNext(t);
            } else if (this.d) {
                this.e = true;
                this.a.onNext(t);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.b.setResource(0, disposable);
            }
        }
    }

    public ObservableSkipUntil(ObservableSource<T> observableSource, ObservableSource<U> observableSource2) {
        super(observableSource);
        this.a = observableSource2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        ArrayCompositeDisposable arrayCompositeDisposable = new ArrayCompositeDisposable(2);
        serializedObserver.onSubscribe(arrayCompositeDisposable);
        b bVar = new b(serializedObserver, arrayCompositeDisposable);
        this.a.subscribe(new a(this, arrayCompositeDisposable, bVar, serializedObserver));
        this.source.subscribe(bVar);
    }
}
