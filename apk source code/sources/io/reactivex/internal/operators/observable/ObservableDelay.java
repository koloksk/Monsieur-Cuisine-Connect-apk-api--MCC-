package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class ObservableDelay<T> extends bl<T, T> {
    public final long a;
    public final TimeUnit b;
    public final Scheduler c;
    public final boolean d;

    public static final class a<T> implements Observer<T>, Disposable {
        public final Observer<? super T> a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler.Worker d;
        public final boolean e;
        public Disposable f;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$a$a, reason: collision with other inner class name */
        public final class RunnableC0056a implements Runnable {
            public RunnableC0056a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    a.this.a.onComplete();
                } finally {
                    a.this.d.dispose();
                }
            }
        }

        public final class b implements Runnable {
            public final Throwable a;

            public b(Throwable th) {
                this.a = th;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    a.this.a.onError(this.a);
                } finally {
                    a.this.d.dispose();
                }
            }
        }

        public final class c implements Runnable {
            public final T a;

            public c(T t) {
                this.a = t;
            }

            @Override // java.lang.Runnable
            public void run() {
                a.this.a.onNext(this.a);
            }
        }

        public a(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler.Worker worker, boolean z) {
            this.a = observer;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
            this.e = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f.dispose();
            this.d.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.d.schedule(new RunnableC0056a(), this.b, this.c);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.d.schedule(new b(th), this.e ? this.b : 0L, this.c);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.d.schedule(new c(t), this.b, this.c);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableDelay(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(this.d ? observer : new SerializedObserver(observer), this.a, this.b, this.c.createWorker(), this.d));
    }
}
