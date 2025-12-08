package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableThrottleLatest<T> extends bl<T, T> {
    public final long a;
    public final TimeUnit b;
    public final Scheduler c;
    public final boolean d;

    public static final class a<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        public static final long serialVersionUID = -8296689127439125014L;
        public final Observer<? super T> a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler.Worker d;
        public final boolean e;
        public final AtomicReference<T> f = new AtomicReference<>();
        public Disposable g;
        public volatile boolean h;
        public Throwable i;
        public volatile boolean j;
        public volatile boolean k;
        public boolean l;

        public a(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler.Worker worker, boolean z) {
            this.a = observer;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
            this.e = z;
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            AtomicReference<T> atomicReference = this.f;
            Observer<? super T> observer = this.a;
            int iAddAndGet = 1;
            while (!this.j) {
                boolean z = this.h;
                if (z && this.i != null) {
                    atomicReference.lazySet(null);
                    observer.onError(this.i);
                    this.d.dispose();
                    return;
                }
                boolean z2 = atomicReference.get() == null;
                if (z) {
                    T andSet = atomicReference.getAndSet(null);
                    if (!z2 && this.e) {
                        observer.onNext(andSet);
                    }
                    observer.onComplete();
                    this.d.dispose();
                    return;
                }
                if (z2) {
                    if (this.k) {
                        this.l = false;
                        this.k = false;
                    }
                } else if (!this.l || this.k) {
                    observer.onNext(atomicReference.getAndSet(null));
                    this.k = false;
                    this.l = true;
                    this.d.schedule(this, this.b, this.c);
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            atomicReference.lazySet(null);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.j = true;
            this.g.dispose();
            this.d.dispose();
            if (getAndIncrement() == 0) {
                this.f.lazySet(null);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.j;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.h = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.i = th;
            this.h = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.f.set(t);
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.g, disposable)) {
                this.g = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.k = true;
            a();
        }
    }

    public ObservableThrottleLatest(Observable<T> observable, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(observable);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a, this.b, this.c.createWorker(), this.d));
    }
}
