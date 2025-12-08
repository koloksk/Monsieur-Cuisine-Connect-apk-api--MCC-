package io.reactivex.internal.operators.mixed;

import defpackage.q5;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableSwitchMapSingle<T, R> extends Observable<R> {
    public final Observable<T> a;
    public final Function<? super T, ? extends SingleSource<? extends R>> b;
    public final boolean c;

    public static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        public static final C0051a<Object> i = new C0051a<>(null);
        public static final long serialVersionUID = -5402190102429853762L;
        public final Observer<? super R> a;
        public final Function<? super T, ? extends SingleSource<? extends R>> b;
        public final boolean c;
        public final AtomicThrowable d = new AtomicThrowable();
        public final AtomicReference<C0051a<R>> e = new AtomicReference<>();
        public Disposable f;
        public volatile boolean g;
        public volatile boolean h;

        /* renamed from: io.reactivex.internal.operators.mixed.ObservableSwitchMapSingle$a$a, reason: collision with other inner class name */
        public static final class C0051a<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
            public static final long serialVersionUID = 8042919737683345351L;
            public final a<?, R> a;
            public volatile R b;

            public C0051a(a<?, R> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a<?, R> aVar = this.a;
                if (!aVar.e.compareAndSet(this, null) || !aVar.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (!aVar.c) {
                    aVar.f.dispose();
                    aVar.a();
                }
                aVar.b();
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(R r) {
                this.b = r;
                this.a.b();
            }
        }

        public a(Observer<? super R> observer, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
            this.a = observer;
            this.b = function;
            this.c = z;
        }

        public void a() {
            C0051a<Object> c0051a = (C0051a) this.e.getAndSet(i);
            if (c0051a == null || c0051a == i) {
                return;
            }
            DisposableHelper.dispose(c0051a);
        }

        public void b() {
            if (getAndIncrement() != 0) {
                return;
            }
            Observer<? super R> observer = this.a;
            AtomicThrowable atomicThrowable = this.d;
            AtomicReference<C0051a<R>> atomicReference = this.e;
            int iAddAndGet = 1;
            while (!this.h) {
                if (atomicThrowable.get() != null && !this.c) {
                    observer.onError(atomicThrowable.terminate());
                    return;
                }
                boolean z = this.g;
                C0051a<R> c0051a = atomicReference.get();
                boolean z2 = c0051a == null;
                if (z && z2) {
                    Throwable thTerminate = atomicThrowable.terminate();
                    if (thTerminate != null) {
                        observer.onError(thTerminate);
                        return;
                    } else {
                        observer.onComplete();
                        return;
                    }
                }
                if (z2 || c0051a.b == null) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    atomicReference.compareAndSet(c0051a, null);
                    observer.onNext(c0051a.b);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.h = true;
            this.f.dispose();
            a();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.h;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.g = true;
            b();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.c) {
                a();
            }
            this.g = true;
            b();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            C0051a<R> c0051a;
            C0051a<R> c0051a2 = this.e.get();
            if (c0051a2 != null) {
                DisposableHelper.dispose(c0051a2);
            }
            try {
                SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null SingleSource");
                C0051a<R> c0051a3 = new C0051a<>(this);
                do {
                    c0051a = this.e.get();
                    if (c0051a == i) {
                        return;
                    }
                } while (!this.e.compareAndSet(c0051a, c0051a3));
                singleSource.subscribe(c0051a3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.f.dispose();
                this.e.getAndSet(i);
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableSwitchMapSingle(Observable<T> observable, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
        this.a = observable;
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        if (q5.b(this.a, this.b, observer)) {
            return;
        }
        this.a.subscribe(new a(observer, this.b, this.c));
    }
}
