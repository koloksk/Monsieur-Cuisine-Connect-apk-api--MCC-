package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableBufferBoundary<T, U extends Collection<? super T>, Open, Close> extends bl<T, U> {
    public final Callable<U> a;
    public final ObservableSource<? extends Open> b;
    public final Function<? super Open, ? extends ObservableSource<? extends Close>> c;

    public static final class b<T, C extends Collection<? super T>> extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
        public static final long serialVersionUID = -8498650778633225126L;
        public final a<T, C, ?, ?> a;
        public final long b;

        public b(a<T, C, ?, ?> aVar, long j) {
            this.a = aVar;
            this.b = j;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable != disposableHelper) {
                lazySet(disposableHelper);
                this.a.a(this, this.b);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper) {
                RxJavaPlugins.onError(th);
                return;
            }
            lazySet(disposableHelper);
            a<T, C, ?, ?> aVar = this.a;
            DisposableHelper.dispose(aVar.f);
            aVar.e.delete(this);
            aVar.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(Object obj) {
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable != disposableHelper) {
                lazySet(disposableHelper);
                disposable.dispose();
                this.a.a(this, this.b);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public ObservableBufferBoundary(ObservableSource<T> observableSource, ObservableSource<? extends Open> observableSource2, Function<? super Open, ? extends ObservableSource<? extends Close>> function, Callable<U> callable) {
        super(observableSource);
        this.b = observableSource2;
        this.c = function;
        this.a = callable;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super U> observer) {
        a aVar = new a(observer, this.b, this.c, this.a);
        observer.onSubscribe(aVar);
        this.source.subscribe(aVar);
    }

    public static final class a<T, C extends Collection<? super T>, Open, Close> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = -8466418554264089604L;
        public final Observer<? super C> a;
        public final Callable<C> b;
        public final ObservableSource<? extends Open> c;
        public final Function<? super Open, ? extends ObservableSource<? extends Close>> d;
        public volatile boolean h;
        public volatile boolean j;
        public long k;
        public final SpscLinkedArrayQueue<C> i = new SpscLinkedArrayQueue<>(Observable.bufferSize());
        public final CompositeDisposable e = new CompositeDisposable();
        public final AtomicReference<Disposable> f = new AtomicReference<>();
        public Map<Long, C> l = new LinkedHashMap();
        public final AtomicThrowable g = new AtomicThrowable();

        /* renamed from: io.reactivex.internal.operators.observable.ObservableBufferBoundary$a$a, reason: collision with other inner class name */
        public static final class C0053a<Open> extends AtomicReference<Disposable> implements Observer<Open>, Disposable {
            public static final long serialVersionUID = -8498650778633225126L;
            public final a<?, ?, Open, ?> a;

            public C0053a(a<?, ?, Open, ?> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return get() == DisposableHelper.DISPOSED;
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                lazySet(DisposableHelper.DISPOSED);
                a<?, ?, Open, ?> aVar = this.a;
                aVar.e.delete(this);
                if (aVar.e.size() == 0) {
                    DisposableHelper.dispose(aVar.f);
                    aVar.h = true;
                    aVar.a();
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                lazySet(DisposableHelper.DISPOSED);
                a<?, ?, Open, ?> aVar = this.a;
                DisposableHelper.dispose(aVar.f);
                aVar.e.delete(this);
                aVar.onError(th);
            }

            @Override // io.reactivex.Observer
            public void onNext(Open open) {
                this.a.a(open);
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }
        }

        public a(Observer<? super C> observer, ObservableSource<? extends Open> observableSource, Function<? super Open, ? extends ObservableSource<? extends Close>> function, Callable<C> callable) {
            this.a = observer;
            this.b = callable;
            this.c = observableSource;
            this.d = function;
        }

        public void a(Open open) {
            try {
                Collection collection = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null Collection");
                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.d.apply(open), "The bufferClose returned a null ObservableSource");
                long j = this.k;
                this.k = 1 + j;
                synchronized (this) {
                    Map<Long, C> map = this.l;
                    if (map == null) {
                        return;
                    }
                    map.put(Long.valueOf(j), collection);
                    b bVar = new b(this, j);
                    this.e.add(bVar);
                    observableSource.subscribe(bVar);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                DisposableHelper.dispose(this.f);
                onError(th);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (DisposableHelper.dispose(this.f)) {
                this.j = true;
                this.e.dispose();
                synchronized (this) {
                    this.l = null;
                }
                if (getAndIncrement() != 0) {
                    this.i.clear();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.f.get());
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.e.dispose();
            synchronized (this) {
                Map<Long, C> map = this.l;
                if (map == null) {
                    return;
                }
                Iterator<C> it = map.values().iterator();
                while (it.hasNext()) {
                    this.i.offer(it.next());
                }
                this.l = null;
                this.h = true;
                a();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.g.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e.dispose();
            synchronized (this) {
                this.l = null;
            }
            this.h = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            synchronized (this) {
                Map<Long, C> map = this.l;
                if (map == null) {
                    return;
                }
                Iterator<C> it = map.values().iterator();
                while (it.hasNext()) {
                    it.next().add(t);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this.f, disposable)) {
                C0053a c0053a = new C0053a(this);
                this.e.add(c0053a);
                this.c.subscribe(c0053a);
            }
        }

        public void a(b<T, C> bVar, long j) {
            boolean z;
            this.e.delete(bVar);
            if (this.e.size() == 0) {
                DisposableHelper.dispose(this.f);
                z = true;
            } else {
                z = false;
            }
            synchronized (this) {
                if (this.l == null) {
                    return;
                }
                this.i.offer(this.l.remove(Long.valueOf(j)));
                if (z) {
                    this.h = true;
                }
                a();
            }
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            Observer<? super C> observer = this.a;
            SpscLinkedArrayQueue<C> spscLinkedArrayQueue = this.i;
            int iAddAndGet = 1;
            while (!this.j) {
                boolean z = this.h;
                if (z && this.g.get() != null) {
                    spscLinkedArrayQueue.clear();
                    observer.onError(this.g.terminate());
                    return;
                }
                C cPoll = spscLinkedArrayQueue.poll();
                boolean z2 = cPoll == null;
                if (z && z2) {
                    observer.onComplete();
                    return;
                } else if (z2) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    observer.onNext(cPoll);
                }
            }
            spscLinkedArrayQueue.clear();
        }
    }
}
