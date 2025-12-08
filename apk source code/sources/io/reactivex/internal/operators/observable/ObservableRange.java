package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.observers.BasicIntQueueDisposable;

/* loaded from: classes.dex */
public final class ObservableRange extends Observable<Integer> {
    public final int a;
    public final long b;

    public static final class a extends BasicIntQueueDisposable<Integer> {
        public static final long serialVersionUID = 396518478098735504L;
        public final Observer<? super Integer> a;
        public final long b;
        public long c;
        public boolean d;

        public a(Observer<? super Integer> observer, long j, long j2) {
            this.a = observer;
            this.c = j;
            this.b = j2;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.c = this.b;
            lazySet(1);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            set(1);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() != 0;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.c == this.b;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public Object poll() throws Exception {
            long j = this.c;
            if (j != this.b) {
                this.c = 1 + j;
                return Integer.valueOf((int) j);
            }
            lazySet(1);
            return null;
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

    public ObservableRange(int i, int i2) {
        this.a = i;
        this.b = i + i2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Integer> observer) {
        a aVar = new a(observer, this.a, this.b);
        observer.onSubscribe(aVar);
        if (aVar.d) {
            return;
        }
        Observer<? super Integer> observer2 = aVar.a;
        long j = aVar.b;
        for (long j2 = aVar.c; j2 != j && aVar.get() == 0; j2++) {
            observer2.onNext(Integer.valueOf((int) j2));
        }
        if (aVar.get() == 0) {
            aVar.lazySet(1);
            observer2.onComplete();
        }
    }
}
