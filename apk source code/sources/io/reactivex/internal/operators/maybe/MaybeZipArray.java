package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.maybe.MaybeMap;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class MaybeZipArray<T, R> extends Maybe<R> {
    public final MaybeSource<? extends T>[] a;
    public final Function<? super Object[], ? extends R> b;

    public final class a implements Function<T, R> {
        public a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.functions.Function
        public R apply(T t) throws Exception {
            return (R) ObjectHelper.requireNonNull(MaybeZipArray.this.b.apply(new Object[]{t}), "The zipper returned a null value");
        }
    }

    public static final class b<T, R> extends AtomicInteger implements Disposable {
        public static final long serialVersionUID = -5556924161382950569L;
        public final MaybeObserver<? super R> a;
        public final Function<? super Object[], ? extends R> b;
        public final c<T>[] c;
        public final Object[] d;

        public b(MaybeObserver<? super R> maybeObserver, int i, Function<? super Object[], ? extends R> function) {
            super(i);
            this.a = maybeObserver;
            this.b = function;
            c<T>[] cVarArr = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                cVarArr[i2] = new c<>(this, i2);
            }
            this.c = cVarArr;
            this.d = new Object[i];
        }

        public void a(int i) {
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

    public static final class c<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        public static final long serialVersionUID = 3323743579927613702L;
        public final b<T, ?> a;
        public final int b;

        public c(b<T, ?> bVar, int i) {
            this.a = bVar;
            this.b = i;
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            b<T, ?> bVar = this.a;
            int i = this.b;
            if (bVar.getAndSet(0) > 0) {
                bVar.a(i);
                bVar.a.onComplete();
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            b<T, ?> bVar = this.a;
            int i = this.b;
            if (bVar.getAndSet(0) <= 0) {
                RxJavaPlugins.onError(th);
            } else {
                bVar.a(i);
                bVar.a.onError(th);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.MaybeObserver
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

    public MaybeZipArray(MaybeSource<? extends T>[] maybeSourceArr, Function<? super Object[], ? extends R> function) {
        this.a = maybeSourceArr;
        this.b = function;
    }

    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        MaybeSource<? extends T>[] maybeSourceArr = this.a;
        int length = maybeSourceArr.length;
        if (length == 1) {
            maybeSourceArr[0].subscribe(new MaybeMap.a(maybeObserver, new a()));
            return;
        }
        b bVar = new b(maybeObserver, length, this.b);
        maybeObserver.onSubscribe(bVar);
        for (int i = 0; i < length && !bVar.isDisposed(); i++) {
            MaybeSource<? extends T> maybeSource = maybeSourceArr[i];
            if (maybeSource == null) {
                NullPointerException nullPointerException = new NullPointerException("One of the sources is null");
                if (bVar.getAndSet(0) <= 0) {
                    RxJavaPlugins.onError(nullPointerException);
                    return;
                } else {
                    bVar.a(i);
                    bVar.a.onError(nullPointerException);
                    return;
                }
            }
            maybeSource.subscribe(bVar.c[i]);
        }
    }
}
