package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableSampleTimed<T> extends bl<T, T> {
    public final long a;
    public final TimeUnit b;
    public final Scheduler c;
    public final boolean d;

    public static final class a<T> extends c<T> {
        public static final long serialVersionUID = -7139995637533111443L;
        public final AtomicInteger g;

        public a(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(observer, j, timeUnit, scheduler);
            this.g = new AtomicInteger(1);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableSampleTimed.c
        public void a() {
            b();
            if (this.g.decrementAndGet() == 0) {
                this.a.onComplete();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.g.incrementAndGet() == 2) {
                b();
                if (this.g.decrementAndGet() == 0) {
                    this.a.onComplete();
                }
            }
        }
    }

    public static final class b<T> extends c<T> {
        public static final long serialVersionUID = -7139995637533111443L;

        public b(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(observer, j, timeUnit, scheduler);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableSampleTimed.c
        public void a() {
            this.a.onComplete();
        }

        @Override // java.lang.Runnable
        public void run() {
            b();
        }
    }

    public static abstract class c<T> extends AtomicReference<T> implements Observer<T>, Disposable, Runnable {
        public static final long serialVersionUID = -3517602651313910099L;
        public final Observer<? super T> a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;
        public final AtomicReference<Disposable> e = new AtomicReference<>();
        public Disposable f;

        public c(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = observer;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        public abstract void a();

        public void b() {
            T andSet = getAndSet(null);
            if (andSet != null) {
                this.a.onNext(andSet);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.e);
            this.f.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            DisposableHelper.dispose(this.e);
            a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.e);
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            lazySet(t);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.a.onSubscribe(this);
                Scheduler scheduler = this.d;
                long j = this.b;
                DisposableHelper.replace(this.e, scheduler.schedulePeriodicallyDirect(this, j, j, this.c));
            }
        }
    }

    public ObservableSampleTimed(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        if (this.d) {
            this.source.subscribe(new a(serializedObserver, this.a, this.b, this.c));
        } else {
            this.source.subscribe(new b(serializedObserver, this.a, this.b, this.c));
        }
    }
}
