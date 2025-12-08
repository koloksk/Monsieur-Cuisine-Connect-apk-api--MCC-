package io.reactivex.internal.operators.mixed;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class CompletableAndThenObservable<R> extends Observable<R> {
    public final CompletableSource a;
    public final ObservableSource<? extends R> b;

    public static final class a<R> extends AtomicReference<Disposable> implements Observer<R>, CompletableObserver, Disposable {
        public static final long serialVersionUID = -8948264376121066672L;
        public final Observer<? super R> a;
        public ObservableSource<? extends R> b;

        public a(Observer<? super R> observer, ObservableSource<? extends R> observableSource) {
            this.b = observableSource;
            this.a = observer;
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
            ObservableSource<? extends R> observableSource = this.b;
            if (observableSource == null) {
                this.a.onComplete();
            } else {
                this.b = null;
                observableSource.subscribe(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(R r) {
            this.a.onNext(r);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
        }
    }

    public CompletableAndThenObservable(CompletableSource completableSource, ObservableSource<? extends R> observableSource) {
        this.a = completableSource;
        this.b = observableSource;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        a aVar = new a(observer, this.b);
        observer.onSubscribe(aVar);
        this.a.subscribe(aVar);
    }
}
