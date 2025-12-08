package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.single.SingleMap;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class SingleZipArray<T, R> extends Single<R> {
    public final SingleSource<? extends T>[] a;
    public final Function<? super Object[], ? extends R> b;

    public final class a implements Function<T, R> {
        public a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.functions.Function
        public R apply(T t) throws Exception {
            return (R) ObjectHelper.requireNonNull(SingleZipArray.this.b.apply(new Object[]{t}), "The zipper returned a null value");
        }
    }

    public static final class b<T, R> extends AtomicInteger implements Disposable {
        public static final long serialVersionUID = -5556924161382950569L;
        public final SingleObserver<? super R> a;
        public final Function<? super Object[], ? extends R> b;
        public final c<T>[] c;
        public final Object[] d;

        public b(SingleObserver<? super R> singleObserver, int i, Function<? super Object[], ? extends R> function) {
            super(i);
            this.a = singleObserver;
            this.b = function;
            c<T>[] cVarArr = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                cVarArr[i2] = new c<>(this, i2);
            }
            this.c = cVarArr;
            this.d = new Object[i];
        }

        public void a(Throwable th, int i) {
            if (getAndSet(0) <= 0) {
                RxJavaPlugins.onError(th);
                return;
            }
            c<T>[] cVarArr = this.c;
            int length = cVarArr.length;
            for (int i2 = 0; i2 < i; i2++) {
                c<T> cVar = cVarArr[i2];
                if (cVar == null) {
                    throw null;
                }
                DisposableHelper.dispose(cVar);
            }
            while (true) {
                i++;
                if (i >= length) {
                    this.a.onError(th);
                    return;
                }
                c<T> cVar2 = cVarArr[i];
                if (cVar2 == null) {
                    throw null;
                }
                DisposableHelper.dispose(cVar2);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (getAndSet(0) > 0) {
                for (c<T> cVar : this.c) {
                    if (cVar == null) {
                        throw null;
                    }
                    DisposableHelper.dispose(cVar);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() <= 0;
        }
    }

    public static final class c<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
        public static final long serialVersionUID = 3323743579927613702L;
        public final b<T, ?> a;
        public final int b;

        public c(b<T, ?> bVar, int i) {
            this.a = bVar;
            this.b = i;
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.a.a(th, this.b);
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            b<T, ?> bVar = this.a;
            bVar.d[this.b] = t;
            if (bVar.decrementAndGet() == 0) {
                try {
                    bVar.a.onSuccess(ObjectHelper.requireNonNull(bVar.b.apply(bVar.d), "The zipper returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    bVar.a.onError(th);
                }
            }
        }
    }

    public SingleZipArray(SingleSource<? extends T>[] singleSourceArr, Function<? super Object[], ? extends R> function) {
        this.a = singleSourceArr;
        this.b = function;
    }

    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        SingleSource<? extends T>[] singleSourceArr = this.a;
        int length = singleSourceArr.length;
        if (length == 1) {
            singleSourceArr[0].subscribe(new SingleMap.a(singleObserver, new a()));
            return;
        }
        b bVar = new b(singleObserver, length, this.b);
        singleObserver.onSubscribe(bVar);
        for (int i = 0; i < length && !bVar.isDisposed(); i++) {
            SingleSource<? extends T> singleSource = singleSourceArr[i];
            if (singleSource == null) {
                bVar.a(new NullPointerException("One of the sources is null"), i);
                return;
            }
            singleSource.subscribe(bVar.c[i]);
        }
    }
}
