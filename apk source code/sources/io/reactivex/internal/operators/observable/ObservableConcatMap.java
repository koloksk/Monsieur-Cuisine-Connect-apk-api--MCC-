package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableConcatMap<T, U> extends bl<T, U> {
    public final Function<? super T, ? extends ObservableSource<? extends U>> a;
    public final int b;
    public final ErrorMode c;

    public static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = -6951100001833242599L;
        public final Observer<? super R> a;
        public final Function<? super T, ? extends ObservableSource<? extends R>> b;
        public final int c;
        public final AtomicThrowable d = new AtomicThrowable();
        public final C0054a<R> e;
        public final boolean f;
        public SimpleQueue<T> g;
        public Disposable h;
        public volatile boolean i;
        public volatile boolean j;
        public volatile boolean k;
        public int l;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableConcatMap$a$a, reason: collision with other inner class name */
        public static final class C0054a<R> extends AtomicReference<Disposable> implements Observer<R> {
            public static final long serialVersionUID = 2620149119579502636L;
            public final Observer<? super R> a;
            public final a<?, R> b;

            public C0054a(Observer<? super R> observer, a<?, R> aVar) {
                this.a = observer;
                this.b = aVar;
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                a<?, R> aVar = this.b;
                aVar.i = false;
                aVar.a();
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                a<?, R> aVar = this.b;
                if (!aVar.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (!aVar.f) {
                    aVar.h.dispose();
                }
                aVar.i = false;
                aVar.a();
            }

            @Override // io.reactivex.Observer
            public void onNext(R r) {
                this.a.onNext(r);
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }
        }

        public a(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, boolean z) {
            this.a = observer;
            this.b = function;
            this.c = i;
            this.f = z;
            this.e = new C0054a<>(observer, this);
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            Observer<? super R> observer = this.a;
            SimpleQueue<T> simpleQueue = this.g;
            AtomicThrowable atomicThrowable = this.d;
            while (true) {
                if (!this.i) {
                    if (this.k) {
                        simpleQueue.clear();
                        return;
                    }
                    if (!this.f && atomicThrowable.get() != null) {
                        simpleQueue.clear();
                        this.k = true;
                        observer.onError(atomicThrowable.terminate());
                        return;
                    }
                    boolean z = this.j;
                    try {
                        T tPoll = simpleQueue.poll();
                        boolean z2 = tPoll == null;
                        if (z && z2) {
                            this.k = true;
                            Throwable thTerminate = atomicThrowable.terminate();
                            if (thTerminate != null) {
                                observer.onError(thTerminate);
                                return;
                            } else {
                                observer.onComplete();
                                return;
                            }
                        }
                        if (!z2) {
                            try {
                                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(tPoll), "The mapper returned a null ObservableSource");
                                if (observableSource instanceof Callable) {
                                    try {
                                        defpackage.a aVar = (Object) ((Callable) observableSource).call();
                                        if (aVar != null && !this.k) {
                                            observer.onNext(aVar);
                                        }
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        atomicThrowable.addThrowable(th);
                                    }
                                } else {
                                    this.i = true;
                                    observableSource.subscribe(this.e);
                                }
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                this.k = true;
                                this.h.dispose();
                                simpleQueue.clear();
                                atomicThrowable.addThrowable(th2);
                                observer.onError(atomicThrowable.terminate());
                                return;
                            }
                        }
                    } catch (Throwable th3) {
                        Exceptions.throwIfFatal(th3);
                        this.k = true;
                        this.h.dispose();
                        atomicThrowable.addThrowable(th3);
                        observer.onError(atomicThrowable.terminate());
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.k = true;
            this.h.dispose();
            C0054a<R> c0054a = this.e;
            if (c0054a == null) {
                throw null;
            }
            DisposableHelper.dispose(c0054a);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.k;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.j = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.j = true;
                a();
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.l == 0) {
                this.g.offer(t);
            }
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int iRequestFusion = queueDisposable.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.l = iRequestFusion;
                        this.g = queueDisposable;
                        this.j = true;
                        this.a.onSubscribe(this);
                        a();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.l = iRequestFusion;
                        this.g = queueDisposable;
                        this.a.onSubscribe(this);
                        return;
                    }
                }
                this.g = new SpscLinkedArrayQueue(this.c);
                this.a.onSubscribe(this);
            }
        }
    }

    public static final class b<T, U> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = 8828587559905699186L;
        public final Observer<? super U> a;
        public final Function<? super T, ? extends ObservableSource<? extends U>> b;
        public final a<U> c;
        public final int d;
        public SimpleQueue<T> e;
        public Disposable f;
        public volatile boolean g;
        public volatile boolean h;
        public volatile boolean i;
        public int j;

        public static final class a<U> extends AtomicReference<Disposable> implements Observer<U> {
            public static final long serialVersionUID = -7449079488798789337L;
            public final Observer<? super U> a;
            public final b<?, ?> b;

            public a(Observer<? super U> observer, b<?, ?> bVar) {
                this.a = observer;
                this.b = bVar;
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                b<?, ?> bVar = this.b;
                bVar.g = false;
                bVar.a();
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                this.b.dispose();
                this.a.onError(th);
            }

            @Override // io.reactivex.Observer
            public void onNext(U u) {
                this.a.onNext(u);
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }
        }

        public b(Observer<? super U> observer, Function<? super T, ? extends ObservableSource<? extends U>> function, int i) {
            this.a = observer;
            this.b = function;
            this.d = i;
            this.c = new a<>(observer, this);
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            while (!this.h) {
                if (!this.g) {
                    boolean z = this.i;
                    try {
                        T tPoll = this.e.poll();
                        boolean z2 = tPoll == null;
                        if (z && z2) {
                            this.h = true;
                            this.a.onComplete();
                            return;
                        } else if (!z2) {
                            try {
                                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(tPoll), "The mapper returned a null ObservableSource");
                                this.g = true;
                                observableSource.subscribe(this.c);
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                dispose();
                                this.e.clear();
                                this.a.onError(th);
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        dispose();
                        this.e.clear();
                        this.a.onError(th2);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            this.e.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.h = true;
            a<U> aVar = this.c;
            if (aVar == null) {
                throw null;
            }
            DisposableHelper.dispose(aVar);
            this.f.dispose();
            if (getAndIncrement() == 0) {
                this.e.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.h;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.i) {
                return;
            }
            this.i = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.i) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.i = true;
            dispose();
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.i) {
                return;
            }
            if (this.j == 0) {
                this.e.offer(t);
            }
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int iRequestFusion = queueDisposable.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.j = iRequestFusion;
                        this.e = queueDisposable;
                        this.i = true;
                        this.a.onSubscribe(this);
                        a();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.j = iRequestFusion;
                        this.e = queueDisposable;
                        this.a.onSubscribe(this);
                        return;
                    }
                }
                this.e = new SpscLinkedArrayQueue(this.d);
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableConcatMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends U>> function, int i, ErrorMode errorMode) {
        super(observableSource);
        this.a = function;
        this.c = errorMode;
        this.b = Math.max(8, i);
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super U> observer) {
        if (ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.a)) {
            return;
        }
        if (this.c == ErrorMode.IMMEDIATE) {
            this.source.subscribe(new b(new SerializedObserver(observer), this.a, this.b));
        } else {
            this.source.subscribe(new a(observer, this.a, this.b, this.c == ErrorMode.END));
        }
    }
}
