package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableAmb<T> extends Observable<T> {
    public final ObservableSource<? extends T>[] a;
    public final Iterable<? extends ObservableSource<? extends T>> b;

    public static final class a<T> implements Disposable {
        public final Observer<? super T> a;
        public final b<T>[] b;
        public final AtomicInteger c = new AtomicInteger();

        public a(Observer<? super T> observer, int i) {
            this.a = observer;
            this.b = new b[i];
        }

        public boolean a(int i) {
            int i2 = this.c.get();
            int i3 = 0;
            if (i2 != 0) {
                return i2 == i;
            }
            if (!this.c.compareAndSet(0, i)) {
                return false;
            }
            b<T>[] bVarArr = this.b;
            int length = bVarArr.length;
            while (i3 < length) {
                int i4 = i3 + 1;
                if (i4 != i) {
                    b<T> bVar = bVarArr[i3];
                    if (bVar == null) {
                        throw null;
                    }
                    DisposableHelper.dispose(bVar);
                }
                i3 = i4;
            }
            return true;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.c.get() != -1) {
                this.c.lazySet(-1);
                for (b<T> bVar : this.b) {
                    if (bVar == null) {
                        throw null;
                    }
                    DisposableHelper.dispose(bVar);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.get() == -1;
        }
    }

    public static final class b<T> extends AtomicReference<Disposable> implements Observer<T> {
        public static final long serialVersionUID = -1185974347409665484L;
        public final a<T> a;
        public final int b;
        public final Observer<? super T> c;
        public boolean d;

        public b(a<T> aVar, int i, Observer<? super T> observer) {
            this.a = aVar;
            this.b = i;
            this.c = observer;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.d) {
                this.c.onComplete();
            } else if (this.a.a(this.b)) {
                this.d = true;
                this.c.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.d) {
                this.c.onError(th);
            } else if (!this.a.a(this.b)) {
                RxJavaPlugins.onError(th);
            } else {
                this.d = true;
                this.c.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.d) {
                this.c.onNext(t);
            } else if (!this.a.a(this.b)) {
                get().dispose();
            } else {
                this.d = true;
                this.c.onNext(t);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public ObservableAmb(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable) {
        this.a = observableSourceArr;
        this.b = iterable;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        int length;
        ObservableSource<? extends T>[] observableSourceArr = this.a;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            try {
                length = 0;
                for (ObservableSource<? extends T> observableSource : this.b) {
                    if (observableSource == null) {
                        EmptyDisposable.error(new NullPointerException("One of the sources is null"), observer);
                        return;
                    }
                    if (length == observableSourceArr.length) {
                        ObservableSource<? extends T>[] observableSourceArr2 = new ObservableSource[(length >> 2) + length];
                        System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, length);
                        observableSourceArr = observableSourceArr2;
                    }
                    int i = length + 1;
                    observableSourceArr[length] = observableSource;
                    length = i;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
                return;
            }
        } else {
            length = observableSourceArr.length;
        }
        if (length == 0) {
            EmptyDisposable.complete(observer);
            return;
        }
        if (length == 1) {
            observableSourceArr[0].subscribe(observer);
            return;
        }
        a aVar = new a(observer, length);
        b<T>[] bVarArr = aVar.b;
        int length2 = bVarArr.length;
        int i2 = 0;
        while (i2 < length2) {
            int i3 = i2 + 1;
            bVarArr[i2] = new b<>(aVar, i3, aVar.a);
            i2 = i3;
        }
        aVar.c.lazySet(0);
        aVar.a.onSubscribe(aVar);
        for (int i4 = 0; i4 < length2 && aVar.c.get() == 0; i4++) {
            observableSourceArr[i4].subscribe(bVarArr[i4]);
        }
    }
}
