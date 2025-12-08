package io.reactivex.internal.operators.mixed;

import defpackage.q5;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
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
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableConcatMapCompletable<T> extends Completable {
    public final Observable<T> a;
    public final Function<? super T, ? extends CompletableSource> b;
    public final ErrorMode c;
    public final int d;

    public static final class a<T> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = 3610901111000061034L;
        public final CompletableObserver a;
        public final Function<? super T, ? extends CompletableSource> b;
        public final ErrorMode c;
        public final AtomicThrowable d = new AtomicThrowable();
        public final C0046a e = new C0046a(this);
        public final int f;
        public SimpleQueue<T> g;
        public Disposable h;
        public volatile boolean i;
        public volatile boolean j;
        public volatile boolean k;

        /* renamed from: io.reactivex.internal.operators.mixed.ObservableConcatMapCompletable$a$a, reason: collision with other inner class name */
        public static final class C0046a extends AtomicReference<Disposable> implements CompletableObserver {
            public static final long serialVersionUID = 5638352172918776687L;
            public final a<?> a;

            public C0046a(a<?> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                a<?> aVar = this.a;
                aVar.i = false;
                aVar.a();
            }

            @Override // io.reactivex.CompletableObserver
            public void onError(Throwable th) {
                a<?> aVar = this.a;
                if (!aVar.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (aVar.c != ErrorMode.IMMEDIATE) {
                    aVar.i = false;
                    aVar.a();
                    return;
                }
                aVar.k = true;
                aVar.h.dispose();
                Throwable thTerminate = aVar.d.terminate();
                if (thTerminate != ExceptionHelper.TERMINATED) {
                    aVar.a.onError(thTerminate);
                }
                if (aVar.getAndIncrement() == 0) {
                    aVar.g.clear();
                }
            }

            @Override // io.reactivex.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }
        }

        public a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, ErrorMode errorMode, int i) {
            this.a = completableObserver;
            this.b = function;
            this.c = errorMode;
            this.f = i;
        }

        public void a() {
            boolean z;
            if (getAndIncrement() != 0) {
                return;
            }
            AtomicThrowable atomicThrowable = this.d;
            ErrorMode errorMode = this.c;
            while (!this.k) {
                if (!this.i) {
                    if (errorMode == ErrorMode.BOUNDARY && atomicThrowable.get() != null) {
                        this.k = true;
                        this.g.clear();
                        this.a.onError(atomicThrowable.terminate());
                        return;
                    }
                    boolean z2 = this.j;
                    CompletableSource completableSource = null;
                    try {
                        T tPoll = this.g.poll();
                        if (tPoll != null) {
                            completableSource = (CompletableSource) ObjectHelper.requireNonNull(this.b.apply(tPoll), "The mapper returned a null CompletableSource");
                            z = false;
                        } else {
                            z = true;
                        }
                        if (z2 && z) {
                            this.k = true;
                            Throwable thTerminate = atomicThrowable.terminate();
                            if (thTerminate != null) {
                                this.a.onError(thTerminate);
                                return;
                            } else {
                                this.a.onComplete();
                                return;
                            }
                        }
                        if (!z) {
                            this.i = true;
                            completableSource.subscribe(this.e);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.k = true;
                        this.g.clear();
                        this.h.dispose();
                        atomicThrowable.addThrowable(th);
                        this.a.onError(atomicThrowable.terminate());
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            this.g.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.k = true;
            this.h.dispose();
            C0046a c0046a = this.e;
            if (c0046a == null) {
                throw null;
            }
            DisposableHelper.dispose(c0046a);
            if (getAndIncrement() == 0) {
                this.g.clear();
            }
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
                return;
            }
            if (this.c != ErrorMode.IMMEDIATE) {
                this.j = true;
                a();
                return;
            }
            this.k = true;
            C0046a c0046a = this.e;
            if (c0046a == null) {
                throw null;
            }
            DisposableHelper.dispose(c0046a);
            Throwable thTerminate = this.d.terminate();
            if (thTerminate != ExceptionHelper.TERMINATED) {
                this.a.onError(thTerminate);
            }
            if (getAndIncrement() == 0) {
                this.g.clear();
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (t != null) {
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
                        this.g = queueDisposable;
                        this.j = true;
                        this.a.onSubscribe(this);
                        a();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.g = queueDisposable;
                        this.a.onSubscribe(this);
                        return;
                    }
                }
                this.g = new SpscLinkedArrayQueue(this.f);
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableConcatMapCompletable(Observable<T> observable, Function<? super T, ? extends CompletableSource> function, ErrorMode errorMode, int i) {
        this.a = observable;
        this.b = function;
        this.c = errorMode;
        this.d = i;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        if (q5.a(this.a, this.b, completableObserver)) {
            return;
        }
        this.a.subscribe(new a(completableObserver, this.b, this.c, this.d));
    }
}
