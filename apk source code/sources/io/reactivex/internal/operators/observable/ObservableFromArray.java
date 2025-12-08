package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

/* loaded from: classes.dex */
public final class ObservableFromArray<T> extends Observable<T> {
    public final T[] a;

    public static final class a<T> extends BasicQueueDisposable<T> {
        public final Observer<? super T> a;
        public final T[] b;
        public int c;
        public boolean d;
        public volatile boolean e;

        public a(Observer<? super T> observer, T[] tArr) {
            this.a = observer;
            this.b = tArr;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.c = this.b.length;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.e = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.c == this.b.length;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            int i = this.c;
            T[] tArr = this.b;
            if (i == tArr.length) {
                return null;
            }
            this.c = i + 1;
            return (T) ObjectHelper.requireNonNull(tArr[i], "The array element is null");
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 1) == 0) {
                return 0;
            }
            this.d = true;
            return 1;
        }
    }

    public ObservableFromArray(T[] tArr) {
        this.a = tArr;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar = new a(observer, this.a);
        observer.onSubscribe(aVar);
        if (aVar.d) {
            return;
        }
        T[] tArr = aVar.b;
        int length = tArr.length;
        for (int i = 0; i < length && !aVar.e; i++) {
            T t = tArr[i];
            if (t == null) {
                aVar.a.onError(new NullPointerException("The element at index " + i + " is null"));
                return;
            }
            aVar.a.onNext(t);
        }
        if (aVar.e) {
            return;
        }
        aVar.a.onComplete();
    }
}
