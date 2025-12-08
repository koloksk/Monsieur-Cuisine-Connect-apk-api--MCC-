package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableFlatMapSingle<T, R> extends bl<T, R> {
    public final Function<? super T, ? extends SingleSource<? extends R>> a;
    public final boolean b;

    public ObservableFlatMapSingle(ObservableSource<T> observableSource, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new a(observer, this.a, this.b));
    }

    public static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = 8600231336733376951L;
        public final Observer<? super R> a;
        public final boolean b;
        public final Function<? super T, ? extends SingleSource<? extends R>> f;
        public Disposable h;
        public volatile boolean i;
        public final CompositeDisposable c = new CompositeDisposable();
        public final AtomicThrowable e = new AtomicThrowable();
        public final AtomicInteger d = new AtomicInteger(1);
        public final AtomicReference<SpscLinkedArrayQueue<R>> g = new AtomicReference<>();

        /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapSingle$a$a, reason: collision with other inner class name */
        public final class C0061a extends AtomicReference<Disposable> implements SingleObserver<R>, Disposable {
            public static final long serialVersionUID = -502562646270949838L;

            public C0061a() {
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a aVar = a.this;
                aVar.c.delete(this);
                if (!aVar.e.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (!aVar.b) {
                    aVar.h.dispose();
                    aVar.c.dispose();
                }
                aVar.d.decrementAndGet();
                aVar.a();
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(R r) {
                a.this.a(this, r);
            }
        }

        public a(Observer<? super R> observer, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
            this.a = observer;
            this.f = function;
            this.b = z;
        }

        /* JADX WARN: Removed duplicated region for block: B:43:? A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a(io.reactivex.internal.operators.observable.ObservableFlatMapSingle.a<T, R>.C0061a r3, R r4) {
            /*
                r2 = this;
                io.reactivex.disposables.CompositeDisposable r0 = r2.c
                r0.delete(r3)
                int r3 = r2.get()
                if (r3 != 0) goto L4f
                r3 = 1
                r0 = 0
                boolean r1 = r2.compareAndSet(r0, r3)
                if (r1 == 0) goto L4f
                io.reactivex.Observer<? super R> r1 = r2.a
                r1.onNext(r4)
                java.util.concurrent.atomic.AtomicInteger r4 = r2.d
                int r4 = r4.decrementAndGet()
                if (r4 != 0) goto L21
                goto L22
            L21:
                r3 = r0
            L22:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.queue.SpscLinkedArrayQueue<R>> r4 = r2.g
                java.lang.Object r4 = r4.get()
                io.reactivex.internal.queue.SpscLinkedArrayQueue r4 = (io.reactivex.internal.queue.SpscLinkedArrayQueue) r4
                if (r3 == 0) goto L48
                if (r4 == 0) goto L34
                boolean r3 = r4.isEmpty()
                if (r3 == 0) goto L48
            L34:
                io.reactivex.internal.util.AtomicThrowable r3 = r2.e
                java.lang.Throwable r3 = r3.terminate()
                if (r3 == 0) goto L42
                io.reactivex.Observer<? super R> r4 = r2.a
                r4.onError(r3)
                goto L47
            L42:
                io.reactivex.Observer<? super R> r3 = r2.a
                r3.onComplete()
            L47:
                return
            L48:
                int r3 = r2.decrementAndGet()
                if (r3 != 0) goto L7d
                return
            L4f:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.queue.SpscLinkedArrayQueue<R>> r3 = r2.g
                java.lang.Object r3 = r3.get()
                io.reactivex.internal.queue.SpscLinkedArrayQueue r3 = (io.reactivex.internal.queue.SpscLinkedArrayQueue) r3
                if (r3 == 0) goto L5a
                goto L6c
            L5a:
                io.reactivex.internal.queue.SpscLinkedArrayQueue r3 = new io.reactivex.internal.queue.SpscLinkedArrayQueue
                int r0 = io.reactivex.Observable.bufferSize()
                r3.<init>(r0)
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.queue.SpscLinkedArrayQueue<R>> r0 = r2.g
                r1 = 0
                boolean r0 = r0.compareAndSet(r1, r3)
                if (r0 == 0) goto L4f
            L6c:
                monitor-enter(r3)
                r3.offer(r4)     // Catch: java.lang.Throwable -> L81
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L81
                java.util.concurrent.atomic.AtomicInteger r3 = r2.d
                r3.decrementAndGet()
                int r3 = r2.getAndIncrement()
                if (r3 == 0) goto L7d
                return
            L7d:
                r2.b()
                return
            L81:
                r4 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L81
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableFlatMapSingle.a.a(io.reactivex.internal.operators.observable.ObservableFlatMapSingle$a$a, java.lang.Object):void");
        }

        public void b() {
            Observer<? super R> observer = this.a;
            AtomicInteger atomicInteger = this.d;
            AtomicReference<SpscLinkedArrayQueue<R>> atomicReference = this.g;
            int iAddAndGet = 1;
            while (!this.i) {
                if (!this.b && this.e.get() != null) {
                    Throwable thTerminate = this.e.terminate();
                    SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.g.get();
                    if (spscLinkedArrayQueue != null) {
                        spscLinkedArrayQueue.clear();
                    }
                    observer.onError(thTerminate);
                    return;
                }
                boolean z = atomicInteger.get() == 0;
                SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = atomicReference.get();
                defpackage.a aVarPoll = spscLinkedArrayQueue2 != null ? spscLinkedArrayQueue2.poll() : null;
                boolean z2 = aVarPoll == null;
                if (z && z2) {
                    Throwable thTerminate2 = this.e.terminate();
                    if (thTerminate2 != null) {
                        observer.onError(thTerminate2);
                        return;
                    } else {
                        observer.onComplete();
                        return;
                    }
                }
                if (z2) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    observer.onNext(aVarPoll);
                }
            }
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue3 = this.g.get();
            if (spscLinkedArrayQueue3 != null) {
                spscLinkedArrayQueue3.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.i = true;
            this.h.dispose();
            this.c.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.i;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.d.decrementAndGet();
            a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.d.decrementAndGet();
            if (!this.e.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.b) {
                this.c.dispose();
            }
            a();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            try {
                SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.f.apply(t), "The mapper returned a null SingleSource");
                this.d.getAndIncrement();
                C0061a c0061a = new C0061a();
                if (this.i || !this.c.add(c0061a)) {
                    return;
                }
                singleSource.subscribe(c0061a);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.h.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void a() {
            if (getAndIncrement() == 0) {
                b();
            }
        }
    }
}
