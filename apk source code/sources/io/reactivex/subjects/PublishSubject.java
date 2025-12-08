package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class PublishSubject<T> extends Subject<T> {
    public static final a[] c = new a[0];
    public static final a[] d = new a[0];
    public final AtomicReference<a<T>[]> a = new AtomicReference<>(d);
    public Throwable b;

    public static final class a<T> extends AtomicBoolean implements Disposable {
        public static final long serialVersionUID = 3562861878281475070L;
        public final Observer<? super T> a;
        public final PublishSubject<T> b;

        public a(Observer<? super T> observer, PublishSubject<T> publishSubject) {
            this.a = observer;
            this.b = publishSubject;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (compareAndSet(false, true)) {
                this.b.a(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get();
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> PublishSubject<T> create() {
        return new PublishSubject<>();
    }

    public void a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.a.get();
            if (aVarArr == c || aVarArr == d) {
                return;
            }
            int length = aVarArr.length;
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
                aVarArr2 = d;
            } else {
                a<T>[] aVarArr3 = new a[length - 1];
                System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                aVarArr2 = aVarArr3;
            }
        } while (!this.a.compareAndSet(aVarArr, aVarArr2));
    }

    @Override // io.reactivex.subjects.Subject
    @Nullable
    public Throwable getThrowable() {
        if (this.a.get() == c) {
            return this.b;
        }
        return null;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasComplete() {
        return this.a.get() == c && this.b == null;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasObservers() {
        return this.a.get().length != 0;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasThrowable() {
        return this.a.get() == c && this.b != null;
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        a<T>[] aVarArr = this.a.get();
        a<T>[] aVarArr2 = c;
        if (aVarArr == aVarArr2) {
            return;
        }
        for (a<T> aVar : this.a.getAndSet(aVarArr2)) {
            if (!aVar.get()) {
                aVar.a.onComplete();
            }
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        a<T>[] aVarArr = this.a.get();
        a<T>[] aVarArr2 = c;
        if (aVarArr == aVarArr2) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.b = th;
        for (a<T> aVar : this.a.getAndSet(aVarArr2)) {
            if (aVar.get()) {
                RxJavaPlugins.onError(th);
            } else {
                aVar.a.onError(th);
            }
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        for (a<T> aVar : this.a.get()) {
            if (!aVar.get()) {
                aVar.a.onNext(t);
            }
        }
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.a.get() == c) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        boolean z;
        a<T> aVar = new a<>(observer, this);
        observer.onSubscribe(aVar);
        while (true) {
            a<T>[] aVarArr = this.a.get();
            z = false;
            if (aVarArr == c) {
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
            if (aVar.get()) {
                a(aVar);
            }
        } else {
            Throwable th = this.b;
            if (th != null) {
                observer.onError(th);
            } else {
                observer.onComplete();
            }
        }
    }
}
