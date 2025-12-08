package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservablePublishAlt<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T>, ResettableConnectable {
    public final ObservableSource<T> a;
    public final AtomicReference<b<T>> b = new AtomicReference<>();

    public static final class a<T> extends AtomicReference<b<T>> implements Disposable {
        public static final long serialVersionUID = 7463222674719692880L;
        public final Observer<? super T> a;

        public a(Observer<? super T> observer, b<T> bVar) {
            this.a = observer;
            lazySet(bVar);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            b<T> andSet = getAndSet(null);
            if (andSet != null) {
                andSet.a(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }
    }

    public static final class b<T> extends AtomicReference<a<T>[]> implements Observer<T>, Disposable {
        public static final a[] e = new a[0];
        public static final a[] f = new a[0];
        public static final long serialVersionUID = -3251430252873581268L;
        public final AtomicReference<b<T>> b;
        public Throwable d;
        public final AtomicBoolean a = new AtomicBoolean();
        public final AtomicReference<Disposable> c = new AtomicReference<>();

        public b(AtomicReference<b<T>> atomicReference) {
            this.b = atomicReference;
            lazySet(e);
        }

        public void a(a<T> aVar) {
            a<T>[] aVarArr;
            a[] aVarArr2;
            do {
                aVarArr = get();
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
                    if (aVarArr[i2] == aVar) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
                if (i < 0) {
                    return;
                }
                aVarArr2 = e;
                if (length != 1) {
                    aVarArr2 = new a[length - 1];
                    System.arraycopy(aVarArr, 0, aVarArr2, 0, i);
                    System.arraycopy(aVarArr, i + 1, aVarArr2, i, (length - i) - 1);
                }
            } while (!compareAndSet(aVarArr, aVarArr2));
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            getAndSet(f);
            this.b.compareAndSet(this, null);
            DisposableHelper.dispose(this.c);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == f;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.c.lazySet(DisposableHelper.DISPOSED);
            for (a<T> aVar : getAndSet(f)) {
                aVar.a.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.d = th;
            this.c.lazySet(DisposableHelper.DISPOSED);
            for (a<T> aVar : getAndSet(f)) {
                aVar.a.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            for (a<T> aVar : get()) {
                aVar.a.onNext(t);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.c, disposable);
        }
    }

    public ObservablePublishAlt(ObservableSource<T> observableSource) {
        this.a = observableSource;
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
        boolean z = !bVar.a.get() && bVar.a.compareAndSet(false, true);
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

    @Override // io.reactivex.internal.disposables.ResettableConnectable
    public void resetIf(Disposable disposable) {
        this.b.compareAndSet((b) disposable, null);
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamObservableSource
    public ObservableSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        b<T> bVar;
        boolean z;
        while (true) {
            bVar = this.b.get();
            if (bVar != null) {
                break;
            }
            b<T> bVar2 = new b<>(this.b);
            if (this.b.compareAndSet(bVar, bVar2)) {
                bVar = bVar2;
                break;
            }
        }
        a<T> aVar = new a<>(observer, bVar);
        observer.onSubscribe(aVar);
        while (true) {
            a<T>[] aVarArr = bVar.get();
            z = false;
            if (aVarArr == b.f) {
                break;
            }
            int length = aVarArr.length;
            a[] aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
            if (bVar.compareAndSet(aVarArr, aVarArr2)) {
                z = true;
                break;
            }
        }
        if (z) {
            if (aVar.isDisposed()) {
                bVar.a(aVar);
            }
        } else {
            Throwable th = bVar.d;
            if (th != null) {
                observer.onError(th);
            } else {
                observer.onComplete();
            }
        }
    }
}
