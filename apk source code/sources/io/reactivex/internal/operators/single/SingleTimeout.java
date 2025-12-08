package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class SingleTimeout<T> extends Single<T> {
    public final SingleSource<T> a;
    public final long b;
    public final TimeUnit c;
    public final Scheduler d;
    public final SingleSource<? extends T> e;

    public static final class a<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Runnable, Disposable {
        public static final long serialVersionUID = 37497744973048446L;
        public final SingleObserver<? super T> a;
        public final AtomicReference<Disposable> b = new AtomicReference<>();
        public final C0073a<T> c;
        public SingleSource<? extends T> d;
        public final long e;
        public final TimeUnit f;

        /* renamed from: io.reactivex.internal.operators.single.SingleTimeout$a$a, reason: collision with other inner class name */
        public static final class C0073a<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            public static final long serialVersionUID = 2071387740092105509L;
            public final SingleObserver<? super T> a;

            public C0073a(SingleObserver<? super T> singleObserver) {
                this.a = singleObserver;
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.a.onError(th);
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(T t) {
                this.a.onSuccess(t);
            }
        }

        public a(SingleObserver<? super T> singleObserver, SingleSource<? extends T> singleSource, long j, TimeUnit timeUnit) {
            this.a = singleObserver;
            this.d = singleSource;
            this.e = j;
            this.f = timeUnit;
            if (singleSource != null) {
                this.c = new C0073a<>(singleObserver);
            } else {
                this.c = null;
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            DisposableHelper.dispose(this.b);
            C0073a<T> c0073a = this.c;
            if (c0073a != null) {
                DisposableHelper.dispose(c0073a);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper || !compareAndSet(disposable, disposableHelper)) {
                RxJavaPlugins.onError(th);
            } else {
                DisposableHelper.dispose(this.b);
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper || !compareAndSet(disposable, disposableHelper)) {
                return;
            }
            DisposableHelper.dispose(this.b);
            this.a.onSuccess(t);
        }

        @Override // java.lang.Runnable
        public void run() {
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper || !compareAndSet(disposable, disposableHelper)) {
                return;
            }
            if (disposable != null) {
                disposable.dispose();
            }
            SingleSource<? extends T> singleSource = this.d;
            if (singleSource == null) {
                this.a.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.e, this.f)));
            } else {
                this.d = null;
                singleSource.subscribe(this.c);
            }
        }
    }

    public SingleTimeout(SingleSource<T> singleSource, long j, TimeUnit timeUnit, Scheduler scheduler, SingleSource<? extends T> singleSource2) {
        this.a = singleSource;
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = singleSource2;
    }

    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        a aVar = new a(singleObserver, this.e, this.b, this.c);
        singleObserver.onSubscribe(aVar);
        DisposableHelper.replace(aVar.b, this.d.scheduleDirect(aVar, this.b, this.c));
        this.a.subscribe(aVar);
    }
}
