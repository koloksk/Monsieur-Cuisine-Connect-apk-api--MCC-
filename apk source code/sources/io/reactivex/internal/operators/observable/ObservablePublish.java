package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservablePublish<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T>, ObservablePublishClassic<T> {
    public final ObservableSource<T> a;
    public final AtomicReference<b<T>> b;
    public final ObservableSource<T> c;

    public static final class a<T> extends AtomicReference<Object> implements Disposable {
        public static final long serialVersionUID = -1100270633763673112L;
        public final Observer<? super T> a;

        public a(Observer<? super T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            Object andSet = getAndSet(this);
            if (andSet == null || andSet == this) {
                return;
            }
            ((b) andSet).a(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == this;
        }
    }

    public static final class b<T> implements Observer<T>, Disposable {
        public static final a[] e = new a[0];
        public static final a[] f = new a[0];
        public final AtomicReference<b<T>> a;
        public final AtomicReference<Disposable> d = new AtomicReference<>();
        public final AtomicReference<a<T>[]> b = new AtomicReference<>(e);
        public final AtomicBoolean c = new AtomicBoolean();

        public b(AtomicReference<b<T>> atomicReference) {
            this.a = atomicReference;
        }

        public void a(a<T> aVar) {
            a<T>[] aVarArr;
            a<T>[] aVarArr2;
            do {
                aVarArr = this.b.get();
                int length = aVarArr.length;
                if (length == 0) {
                    return;
                }
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (aVarArr[i2].equals(aVar)) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    aVarArr2 = e;
                } else {
                    a<T>[] aVarArr3 = new a[length - 1];
                    System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                    System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                    aVarArr2 = aVarArr3;
                }
            } while (!this.b.compareAndSet(aVarArr, aVarArr2));
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.b.getAndSet(f) != f) {
                this.a.compareAndSet(this, null);
                DisposableHelper.dispose(this.d);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b.get() == f;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.compareAndSet(this, null);
            for (a<T> aVar : this.b.getAndSet(f)) {
                aVar.a.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.compareAndSet(this, null);
            a<T>[] andSet = this.b.getAndSet(f);
            if (andSet.length == 0) {
                RxJavaPlugins.onError(th);
                return;
            }
            for (a<T> aVar : andSet) {
                aVar.a.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            for (a<T> aVar : this.b.get()) {
                aVar.a.onNext(t);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.d, disposable);
        }
    }

    public static final class c<T> implements ObservableSource<T> {
        public final AtomicReference<b<T>> a;

        public c(AtomicReference<b<T>> atomicReference) {
            this.a = atomicReference;
        }

        @Override // io.reactivex.ObservableSource
        public void subscribe(Observer<? super T> observer) {
            b<T> bVar;
            boolean z;
            a<T> aVar = new a<>(observer);
            observer.onSubscribe(aVar);
            while (true) {
                bVar = this.a.get();
                if (bVar == null || bVar.isDisposed()) {
                    b<T> bVar2 = new b<>(this.a);
                    if (this.a.compareAndSet(bVar, bVar2)) {
                        bVar = bVar2;
                    } else {
                        continue;
                    }
                }
                while (true) {
                    a<T>[] aVarArr = bVar.b.get();
                    z = false;
                    if (aVarArr == b.f) {
                        break;
                    }
                    int length = aVarArr.length;
                    a<T>[] aVarArr2 = new a[length + 1];
                    System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
                    aVarArr2[length] = aVar;
                    if (bVar.b.compareAndSet(aVarArr, aVarArr2)) {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    break;
                }
            }
            if (aVar.compareAndSet(null, bVar)) {
                return;
            }
            bVar.a(aVar);
        }
    }

    public ObservablePublish(ObservableSource<T> observableSource, ObservableSource<T> observableSource2, AtomicReference<b<T>> atomicReference) {
        this.c = observableSource;
        this.a = observableSource2;
        this.b = atomicReference;
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableObservable) new ObservablePublish(new c(atomicReference), observableSource, atomicReference));
    }

    @Override // io.reactivex.observables.ConnectableObservable
    public void connect(Consumer<? super Disposable> consumer) {
        b<T> bVar;
        while (true) {
            bVar = this.b.get();
            if (bVar != null && !bVar.isDisposed()) {
                break;
            }
            b<T> bVar2 = new b<>(this.b);
            if (this.b.compareAndSet(bVar, bVar2)) {
                bVar = bVar2;
                break;
            }
        }
        boolean z = !bVar.c.get() && bVar.c.compareAndSet(false, true);
        try {
            consumer.accept(bVar);
            if (z) {
                this.a.subscribe(bVar);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.internal.operators.observable.ObservablePublishClassic
    public ObservableSource<T> publishSource() {
        return this.a;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamObservableSource
    public ObservableSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.c.subscribe(observer);
    }
}
