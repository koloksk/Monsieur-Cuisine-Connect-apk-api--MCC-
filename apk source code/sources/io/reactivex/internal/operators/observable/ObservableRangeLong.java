package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.observers.BasicIntQueueDisposable;

/* loaded from: classes.dex */
public final class ObservableRangeLong extends Observable<Long> {
    public final long a;
    public final long b;

    public static final class a extends BasicIntQueueDisposable<Long> {
        public static final long serialVersionUID = 396518478098735504L;
        public final Observer<? super Long> a;
        public final long b;
        public long c;
        public boolean d;

        public a(Observer<? super Long> observer, long j, long j2) {
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
                return Long.valueOf(j);
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

    public ObservableRangeLong(long j, long j2) {
        this.a = j;
        this.b = j2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Long> observer) {
        long j = this.a;
        a aVar = new a(observer, j, j + this.b);
        observer.onSubscribe(aVar);
        if (aVar.d) {
            return;
        }
        Observer<? super Long> observer2 = aVar.a;
        long j2 = aVar.b;
        for (long j3 = aVar.c; j3 != j2 && aVar.get() == 0; j3++) {
            observer2.onNext(Long.valueOf(j3));
        }
        if (aVar.get() == 0) {
            aVar.lazySet(1);
            observer2.onComplete();
        }
    }
}
