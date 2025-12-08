package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableMergeWithSingle<T> extends bl<T, T> {
    public final SingleSource<? extends T> a;

    public static final class a<T> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = -4592979584110982903L;
        public final Observer<? super T> a;
        public final AtomicReference<Disposable> b = new AtomicReference<>();
        public final C0064a<T> c = new C0064a<>(this);
        public final AtomicThrowable d = new AtomicThrowable();
        public volatile SimplePlainQueue<T> e;
        public T f;
        public volatile boolean g;
        public volatile boolean h;
        public volatile int i;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableMergeWithSingle$a$a, reason: collision with other inner class name */
        public static final class C0064a<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            public static final long serialVersionUID = -2935427570954647017L;
            public final a<T> a;

            public C0064a(a<T> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a<T> aVar = this.a;
                if (!aVar.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                } else {
                    DisposableHelper.dispose(aVar.b);
                    aVar.a();
                }
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(T t) {
                a<T> aVar = this.a;
                if (aVar.compareAndSet(0, 1)) {
                    aVar.a.onNext(t);
                    aVar.i = 2;
                } else {
                    aVar.f = t;
                    aVar.i = 1;
                    if (aVar.getAndIncrement() != 0) {
                        return;
                    }
                }
                aVar.b();
            }
        }

        public a(Observer<? super T> observer) {
            this.a = observer;
        }

        public void a() {
            if (getAndIncrement() == 0) {
                b();
            }
        }

        public void b() {
            Observer<? super T> observer = this.a;
            int iAddAndGet = 1;
            while (!this.g) {
                if (this.d.get() != null) {
                    this.f = null;
                    this.e = null;
                    observer.onError(this.d.terminate());
                    return;
                }
                int i = this.i;
                if (i == 1) {
                    T t = this.f;
                    this.f = null;
                    this.i = 2;
                    observer.onNext(t);
                    i = 2;
                }
                boolean z = this.h;
                SimplePlainQueue<T> simplePlainQueue = this.e;
                defpackage.a aVarPoll = simplePlainQueue != null ? simplePlainQueue.poll() : null;
                boolean z2 = aVarPoll == null;
                if (z && z2 && i == 2) {
                    this.e = null;
                    observer.onComplete();
                    return;
                } else if (z2) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    observer.onNext(aVarPoll);
                }
            }
            this.f = null;
            this.e = null;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.g = true;
            DisposableHelper.dispose(this.b);
            DisposableHelper.dispose(this.c);
            if (getAndIncrement() == 0) {
                this.e = null;
                this.f = null;
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.b.get());
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.h = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                DisposableHelper.dispose(this.c);
                a();
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (compareAndSet(0, 1)) {
                this.a.onNext(t);
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SpscLinkedArrayQueue spscLinkedArrayQueue = this.e;
                if (spscLinkedArrayQueue == null) {
                    spscLinkedArrayQueue = new SpscLinkedArrayQueue(Observable.bufferSize());
                    this.e = spscLinkedArrayQueue;
                }
                spscLinkedArrayQueue.offer(t);
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            b();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.b, disposable);
        }
    }

    public ObservableMergeWithSingle(Observable<T> observable, SingleSource<? extends T> singleSource) {
        super(observable);
        this.a = singleSource;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar = new a(observer);
        observer.onSubscribe(aVar);
        this.source.subscribe(aVar);
        this.a.subscribe(aVar.c);
    }
}
