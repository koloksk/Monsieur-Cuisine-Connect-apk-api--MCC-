package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes.dex */
public final class ObservableWithLatestFromMany<T, R> extends bl<T, R> {

    @Nullable
    public final ObservableSource<?>[] a;

    @Nullable
    public final Iterable<? extends ObservableSource<?>> b;

    @NonNull
    public final Function<? super Object[], R> c;

    public final class a implements Function<T, R> {
        public a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.functions.Function
        public R apply(T t) throws Exception {
            return (R) ObjectHelper.requireNonNull(ObservableWithLatestFromMany.this.c.apply(new Object[]{t}), "The combiner returned a null value");
        }
    }

    public static final class b<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = 1577321883966341961L;
        public final Observer<? super R> a;
        public final Function<? super Object[], R> b;
        public final c[] c;
        public final AtomicReferenceArray<Object> d;
        public final AtomicReference<Disposable> e;
        public final AtomicThrowable f;
        public volatile boolean g;

        public b(Observer<? super R> observer, Function<? super Object[], R> function, int i) {
            this.a = observer;
            this.b = function;
            c[] cVarArr = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                cVarArr[i2] = new c(this, i2);
            }
            this.c = cVarArr;
            this.d = new AtomicReferenceArray<>(i);
            this.e = new AtomicReference<>();
            this.f = new AtomicThrowable();
        }

        public void a(int i) {
            c[] cVarArr = this.c;
            for (int i2 = 0; i2 < cVarArr.length; i2++) {
                if (i2 != i) {
                    c cVar = cVarArr[i2];
                    if (cVar == null) {
                        throw null;
                    }
                    DisposableHelper.dispose(cVar);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.e);
            for (c cVar : this.c) {
                if (cVar == null) {
                    throw null;
                }
                DisposableHelper.dispose(cVar);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.e.get());
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.g) {
                return;
            }
            this.g = true;
            a(-1);
            HalfSerializer.onComplete(this.a, this, this.f);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = true;
            a(-1);
            HalfSerializer.onError(this.a, th, this, this.f);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.g) {
                return;
            }
            AtomicReferenceArray<Object> atomicReferenceArray = this.d;
            int length = atomicReferenceArray.length();
            Object[] objArr = new Object[length + 1];
            int i = 0;
            objArr[0] = t;
            while (i < length) {
                Object obj = atomicReferenceArray.get(i);
                if (obj == null) {
                    return;
                }
                i++;
                objArr[i] = obj;
            }
            try {
                HalfSerializer.onNext(this.a, ObjectHelper.requireNonNull(this.b.apply(objArr), "combiner returned a null value"), this, this.f);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.e, disposable);
        }
    }

    public static final class c extends AtomicReference<Disposable> implements Observer<Object> {
        public static final long serialVersionUID = 3256684027868224024L;
        public final b<?, ?> a;
        public final int b;
        public boolean c;

        public c(b<?, ?> bVar, int i) {
            this.a = bVar;
            this.b = i;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            b<?, ?> bVar = this.a;
            int i = this.b;
            boolean z = this.c;
            if (bVar == null) {
                throw null;
            }
            if (z) {
                return;
            }
            bVar.g = true;
            bVar.a(i);
            HalfSerializer.onComplete(bVar.a, bVar, bVar.f);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            b<?, ?> bVar = this.a;
            int i = this.b;
            bVar.g = true;
            DisposableHelper.dispose(bVar.e);
            bVar.a(i);
            HalfSerializer.onError(bVar.a, th, bVar, bVar.f);
        }

        @Override // io.reactivex.Observer
        public void onNext(Object obj) {
            if (!this.c) {
                this.c = true;
            }
            b<?, ?> bVar = this.a;
            bVar.d.set(this.b, obj);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public ObservableWithLatestFromMany(@NonNull ObservableSource<T> observableSource, @NonNull ObservableSource<?>[] observableSourceArr, @NonNull Function<? super Object[], R> function) {
        super(observableSource);
        this.a = observableSourceArr;
        this.b = null;
        this.c = function;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        int length;
        ObservableSource<?>[] observableSourceArr = this.a;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            try {
                length = 0;
                for (ObservableSource<?> observableSource : this.b) {
                    if (length == observableSourceArr.length) {
                        observableSourceArr = (ObservableSource[]) Arrays.copyOf(observableSourceArr, (length >> 1) + length);
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
            new ObservableMap(this.source, new a()).subscribeActual(observer);
            return;
        }
        b bVar = new b(observer, this.c, length);
        observer.onSubscribe(bVar);
        c[] cVarArr = bVar.c;
        AtomicReference<Disposable> atomicReference = bVar.e;
        for (int i2 = 0; i2 < length && !DisposableHelper.isDisposed(atomicReference.get()) && !bVar.g; i2++) {
            observableSourceArr[i2].subscribe(cVarArr[i2]);
        }
        this.source.subscribe(bVar);
    }

    public ObservableWithLatestFromMany(@NonNull ObservableSource<T> observableSource, @NonNull Iterable<? extends ObservableSource<?>> iterable, @NonNull Function<? super Object[], R> function) {
        super(observableSource);
        this.a = null;
        this.b = iterable;
        this.c = function;
    }
}
