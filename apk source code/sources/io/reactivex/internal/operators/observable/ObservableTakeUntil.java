package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableTakeUntil<T, U> extends bl<T, T> {
    public final ObservableSource<? extends U> a;

    public static final class a<T, U> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = 1418547743690811973L;
        public final Observer<? super T> a;
        public final AtomicReference<Disposable> b = new AtomicReference<>();
        public final a<T, U>.C0067a c = new C0067a();
        public final AtomicThrowable d = new AtomicThrowable();

        /* renamed from: io.reactivex.internal.operators.observable.ObservableTakeUntil$a$a, reason: collision with other inner class name */
        public final class C0067a extends AtomicReference<Disposable> implements Observer<U> {
            public static final long serialVersionUID = -8693423678067375039L;

            public C0067a() {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                a aVar = a.this;
                DisposableHelper.dispose(aVar.b);
                HalfSerializer.onComplete(aVar.a, aVar, aVar.d);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                a aVar = a.this;
                DisposableHelper.dispose(aVar.b);
                HalfSerializer.onError(aVar.a, th, aVar, aVar.d);
            }

            @Override // io.reactivex.Observer
            public void onNext(U u) {
                DisposableHelper.dispose(this);
                a aVar = a.this;
                DisposableHelper.dispose(aVar.b);
                HalfSerializer.onComplete(aVar.a, aVar, aVar.d);
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }
        }

        public a(Observer<? super T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.b);
            DisposableHelper.dispose(this.c);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.b.get());
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            DisposableHelper.dispose(this.c);
            HalfSerializer.onComplete(this.a, this, this.d);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.c);
            HalfSerializer.onError(this.a, th, this, this.d);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            HalfSerializer.onNext(this.a, t, this, this.d);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.b, disposable);
        }
    }

    public ObservableTakeUntil(ObservableSource<T> observableSource, ObservableSource<? extends U> observableSource2) {
        super(observableSource);
        this.a = observableSource2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar = new a(observer);
        observer.onSubscribe(aVar);
        this.a.subscribe(aVar.c);
        this.source.subscribe(aVar);
    }
}
