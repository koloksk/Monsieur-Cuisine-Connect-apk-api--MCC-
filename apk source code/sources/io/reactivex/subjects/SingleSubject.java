package io.reactivex.subjects;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class SingleSubject<T> extends Single<T> implements SingleObserver<T> {
    public static final a[] e = new a[0];
    public static final a[] f = new a[0];
    public T c;
    public Throwable d;
    public final AtomicBoolean b = new AtomicBoolean();
    public final AtomicReference<a<T>[]> a = new AtomicReference<>(e);

    public static final class a<T> extends AtomicReference<SingleSubject<T>> implements Disposable {
        public static final long serialVersionUID = -7650903191002190468L;
        public final SingleObserver<? super T> a;

        public a(SingleObserver<? super T> singleObserver, SingleSubject<T> singleSubject) {
            this.a = singleObserver;
            lazySet(singleSubject);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SingleSubject<T> andSet = getAndSet(null);
            if (andSet != null) {
                andSet.a(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> SingleSubject<T> create() {
        return new SingleSubject<>();
    }

    public void a(@NonNull a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.a.get();
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
            if (length == 1) {
                aVarArr2 = e;
            } else {
                a<T>[] aVarArr3 = new a[length - 1];
                System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                aVarArr2 = aVarArr3;
            }
        } while (!this.a.compareAndSet(aVarArr, aVarArr2));
    }

    @Nullable
    public Throwable getThrowable() {
        if (this.a.get() == f) {
            return this.d;
        }
        return null;
    }

    @Nullable
    public T getValue() {
        if (this.a.get() == f) {
            return this.c;
        }
        return null;
    }

    public boolean hasObservers() {
        return this.a.get().length != 0;
    }

    public boolean hasThrowable() {
        return this.a.get() == f && this.d != null;
    }

    public boolean hasValue() {
        return this.a.get() == f && this.c != null;
    }

    @Override // io.reactivex.SingleObserver
    public void onError(@NonNull Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.b.compareAndSet(false, true)) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.d = th;
        for (a<T> aVar : this.a.getAndSet(f)) {
            aVar.a.onError(th);
        }
    }

    @Override // io.reactivex.SingleObserver
    public void onSubscribe(@NonNull Disposable disposable) {
        if (this.a.get() == f) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.SingleObserver
    public void onSuccess(@NonNull T t) {
        ObjectHelper.requireNonNull(t, "onSuccess called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.b.compareAndSet(false, true)) {
            this.c = t;
            for (a<T> aVar : this.a.getAndSet(f)) {
                aVar.a.onSuccess(t);
            }
        }
    }

    @Override // io.reactivex.Single
    public void subscribeActual(@NonNull SingleObserver<? super T> singleObserver) {
        boolean z;
        a<T> aVar = new a<>(singleObserver, this);
        singleObserver.onSubscribe(aVar);
        while (true) {
            a<T>[] aVarArr = this.a.get();
            z = false;
            if (aVarArr == f) {
                break;
            }
            int length = aVarArr.length;
            a<T>[] aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
            if (this.a.compareAndSet(aVarArr, aVarArr2)) {
                z = true;
                break;
            }
        }
        if (z) {
            if (aVar.isDisposed()) {
                a(aVar);
            }
        } else {
            Throwable th = this.d;
            if (th != null) {
                singleObserver.onError(th);
            } else {
                singleObserver.onSuccess(this.c);
            }
        }
    }
}
