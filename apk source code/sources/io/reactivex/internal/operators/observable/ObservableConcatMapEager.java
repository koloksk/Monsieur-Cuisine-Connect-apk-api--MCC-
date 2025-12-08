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
import io.reactivex.internal.observers.InnerQueuedObserver;
import io.reactivex.internal.observers.InnerQueuedObserverSupport;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class ObservableConcatMapEager<T, R> extends bl<T, R> {
    public final Function<? super T, ? extends ObservableSource<? extends R>> a;
    public final ErrorMode b;
    public final int c;
    public final int d;

    public static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable, InnerQueuedObserverSupport<R> {
        public static final long serialVersionUID = 8080567949447303262L;
        public final Observer<? super R> a;
        public final Function<? super T, ? extends ObservableSource<? extends R>> b;
        public final int c;
        public final int d;
        public final ErrorMode e;
        public final AtomicThrowable f = new AtomicThrowable();
        public final ArrayDeque<InnerQueuedObserver<R>> g = new ArrayDeque<>();
        public SimpleQueue<T> h;
        public Disposable i;
        public volatile boolean j;
        public int k;
        public volatile boolean l;
        public InnerQueuedObserver<R> m;
        public int n;

        public a(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, int i2, ErrorMode errorMode) {
            this.a = observer;
            this.b = function;
            this.c = i;
            this.d = i2;
            this.e = errorMode;
        }

        public void a() {
            InnerQueuedObserver<R> innerQueuedObserver = this.m;
            if (innerQueuedObserver != null) {
                innerQueuedObserver.dispose();
            }
            while (true) {
                InnerQueuedObserver<R> innerQueuedObserverPoll = this.g.poll();
                if (innerQueuedObserverPoll == null) {
                    return;
                } else {
                    innerQueuedObserverPoll.dispose();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.l) {
                return;
            }
            this.l = true;
            this.i.dispose();
            if (getAndIncrement() == 0) {
                do {
                    this.h.clear();
                    a();
                } while (decrementAndGet() != 0);
            }
        }

        @Override // io.reactivex.internal.observers.InnerQueuedObserverSupport
        public void drain() {
            R rPoll;
            boolean z;
            if (getAndIncrement() != 0) {
                return;
            }
            SimpleQueue<T> simpleQueue = this.h;
            ArrayDeque<InnerQueuedObserver<R>> arrayDeque = this.g;
            Observer<? super R> observer = this.a;
            ErrorMode errorMode = this.e;
            int iAddAndGet = 1;
            while (true) {
                int i = this.n;
                while (i != this.c) {
                    if (this.l) {
                        simpleQueue.clear();
                        a();
                        return;
                    }
                    if (errorMode == ErrorMode.IMMEDIATE && this.f.get() != null) {
                        simpleQueue.clear();
                        a();
                        observer.onError(this.f.terminate());
                        return;
                    }
                    try {
                        T tPoll = simpleQueue.poll();
                        if (tPoll == null) {
                            break;
                        }
                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(tPoll), "The mapper returned a null ObservableSource");
                        InnerQueuedObserver<R> innerQueuedObserver = new InnerQueuedObserver<>(this, this.d);
                        arrayDeque.offer(innerQueuedObserver);
                        observableSource.subscribe(innerQueuedObserver);
                        i++;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.i.dispose();
                        simpleQueue.clear();
                        a();
                        this.f.addThrowable(th);
                        observer.onError(this.f.terminate());
                        return;
                    }
                }
                this.n = i;
                if (this.l) {
                    simpleQueue.clear();
                    a();
                    return;
                }
                if (errorMode == ErrorMode.IMMEDIATE && this.f.get() != null) {
                    simpleQueue.clear();
                    a();
                    observer.onError(this.f.terminate());
                    return;
                }
                InnerQueuedObserver<R> innerQueuedObserver2 = this.m;
                if (innerQueuedObserver2 == null) {
                    if (errorMode == ErrorMode.BOUNDARY && this.f.get() != null) {
                        simpleQueue.clear();
                        a();
                        observer.onError(this.f.terminate());
                        return;
                    }
                    boolean z2 = this.j;
                    InnerQueuedObserver<R> innerQueuedObserverPoll = arrayDeque.poll();
                    boolean z3 = innerQueuedObserverPoll == null;
                    if (z2 && z3) {
                        if (this.f.get() == null) {
                            observer.onComplete();
                            return;
                        }
                        simpleQueue.clear();
                        a();
                        observer.onError(this.f.terminate());
                        return;
                    }
                    if (!z3) {
                        this.m = innerQueuedObserverPoll;
                    }
                    innerQueuedObserver2 = innerQueuedObserverPoll;
                }
                if (innerQueuedObserver2 != null) {
                    SimpleQueue<R> simpleQueueQueue = innerQueuedObserver2.queue();
                    while (!this.l) {
                        boolean zIsDone = innerQueuedObserver2.isDone();
                        if (errorMode == ErrorMode.IMMEDIATE && this.f.get() != null) {
                            simpleQueue.clear();
                            a();
                            observer.onError(this.f.terminate());
                            return;
                        }
                        try {
                            rPoll = simpleQueueQueue.poll();
                            z = rPoll == null;
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.f.addThrowable(th2);
                            this.m = null;
                            this.n--;
                        }
                        if (zIsDone && z) {
                            this.m = null;
                            this.n--;
                        } else if (!z) {
                            observer.onNext(rPoll);
                        }
                    }
                    simpleQueue.clear();
                    a();
                    return;
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.internal.observers.InnerQueuedObserverSupport
        public void innerComplete(InnerQueuedObserver<R> innerQueuedObserver) {
            innerQueuedObserver.setDone();
            drain();
        }

        @Override // io.reactivex.internal.observers.InnerQueuedObserverSupport
        public void innerError(InnerQueuedObserver<R> innerQueuedObserver, Throwable th) {
            if (!this.f.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.e == ErrorMode.IMMEDIATE) {
                this.i.dispose();
            }
            innerQueuedObserver.setDone();
            drain();
        }

        @Override // io.reactivex.internal.observers.InnerQueuedObserverSupport
        public void innerNext(InnerQueuedObserver<R> innerQueuedObserver, R r) {
            innerQueuedObserver.queue().offer(r);
            drain();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.l;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.j = true;
            drain();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.f.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.j = true;
                drain();
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.k == 0) {
                this.h.offer(t);
            }
            drain();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.i, disposable)) {
                this.i = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int iRequestFusion = queueDisposable.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.k = iRequestFusion;
                        this.h = queueDisposable;
                        this.j = true;
                        this.a.onSubscribe(this);
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.k = iRequestFusion;
                        this.h = queueDisposable;
                        this.a.onSubscribe(this);
                        return;
                    }
                }
                this.h = new SpscLinkedArrayQueue(this.d);
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableConcatMapEager(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends R>> function, ErrorMode errorMode, int i, int i2) {
        super(observableSource);
        this.a = function;
        this.b = errorMode;
        this.c = i;
        this.d = i2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new a(observer, this.a, this.c, this.d, this.b));
    }
}
