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
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableFlatMap<T, U> extends bl<T, U> {
    public final Function<? super T, ? extends ObservableSource<? extends U>> a;
    public final boolean b;
    public final int c;
    public final int d;

    public static final class a<T, U> extends AtomicReference<Disposable> implements Observer<U> {
        public static final long serialVersionUID = -4606175640614850599L;
        public final long a;
        public final b<T, U> b;
        public volatile boolean c;
        public volatile SimpleQueue<U> d;
        public int e;

        public a(b<T, U> bVar, long j) {
            this.a = j;
            this.b = bVar;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.c = true;
            this.b.c();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.b.h.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            b<T, U> bVar = this.b;
            if (!bVar.c) {
                bVar.b();
            }
            this.c = true;
            this.b.c();
        }

        @Override // io.reactivex.Observer
        public void onNext(U u) {
            if (this.e != 0) {
                this.b.c();
                return;
            }
            b<T, U> bVar = this.b;
            if (bVar.get() == 0 && bVar.compareAndSet(0, 1)) {
                bVar.a.onNext(u);
                if (bVar.decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue spscLinkedArrayQueue = this.d;
                if (spscLinkedArrayQueue == null) {
                    spscLinkedArrayQueue = new SpscLinkedArrayQueue(bVar.e);
                    this.d = spscLinkedArrayQueue;
                }
                spscLinkedArrayQueue.offer(u);
                if (bVar.getAndIncrement() != 0) {
                    return;
                }
            }
            bVar.d();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable) && (disposable instanceof QueueDisposable)) {
                QueueDisposable queueDisposable = (QueueDisposable) disposable;
                int iRequestFusion = queueDisposable.requestFusion(7);
                if (iRequestFusion == 1) {
                    this.e = iRequestFusion;
                    this.d = queueDisposable;
                    this.c = true;
                    this.b.c();
                    return;
                }
                if (iRequestFusion == 2) {
                    this.e = iRequestFusion;
                    this.d = queueDisposable;
                }
            }
        }
    }

    public ObservableFlatMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean z, int i, int i2) {
        super(observableSource);
        this.a = function;
        this.b = z;
        this.c = i;
        this.d = i2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super U> observer) {
        if (ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.a)) {
            return;
        }
        this.source.subscribe(new b(observer, this.a, this.b, this.c, this.d));
    }

    public static final class b<T, U> extends AtomicInteger implements Disposable, Observer<T> {
        public static final a<?, ?>[] q = new a[0];
        public static final a<?, ?>[] r = new a[0];
        public static final long serialVersionUID = -2117620485640801370L;
        public final Observer<? super U> a;
        public final Function<? super T, ? extends ObservableSource<? extends U>> b;
        public final boolean c;
        public final int d;
        public final int e;
        public volatile SimplePlainQueue<U> f;
        public volatile boolean g;
        public final AtomicThrowable h = new AtomicThrowable();
        public volatile boolean i;
        public final AtomicReference<a<?, ?>[]> j;
        public Disposable k;
        public long l;
        public long m;
        public int n;
        public Queue<ObservableSource<? extends U>> o;
        public int p;

        public b(Observer<? super U> observer, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean z, int i, int i2) {
            this.a = observer;
            this.b = function;
            this.c = z;
            this.d = i;
            this.e = i2;
            if (i != Integer.MAX_VALUE) {
                this.o = new ArrayDeque(i);
            }
            this.j = new AtomicReference<>(q);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v22 */
        /* JADX WARN: Type inference failed for: r3v23 */
        /* JADX WARN: Type inference failed for: r3v8, types: [io.reactivex.internal.fuseable.SimpleQueue] */
        public void a(ObservableSource<? extends U> observableSource) {
            boolean z;
            boolean z2;
            Object objCall;
            do {
                z = false;
                if (!(observableSource instanceof Callable)) {
                    long j = this.l;
                    this.l = 1 + j;
                    a<?, ?> aVar = new a<>(this, j);
                    while (true) {
                        a<?, ?>[] aVarArr = this.j.get();
                        if (aVarArr == r) {
                            DisposableHelper.dispose(aVar);
                            break;
                        }
                        int length = aVarArr.length;
                        a<?, ?>[] aVarArr2 = new a[length + 1];
                        System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
                        aVarArr2[length] = aVar;
                        if (this.j.compareAndSet(aVarArr, aVarArr2)) {
                            z = true;
                            break;
                        }
                    }
                    if (z) {
                        observableSource.subscribe(aVar);
                        return;
                    }
                    return;
                }
                try {
                    objCall = ((Callable) observableSource).call();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.h.addThrowable(th);
                    c();
                }
                if (objCall == null) {
                    z2 = true;
                } else if (get() == 0 && compareAndSet(0, 1)) {
                    this.a.onNext(objCall);
                    if (decrementAndGet() != 0) {
                        d();
                    }
                    z2 = true;
                } else {
                    SimplePlainQueue<U> simplePlainQueue = this.f;
                    ?? r3 = simplePlainQueue;
                    if (simplePlainQueue == false) {
                        SimplePlainQueue<U> spscLinkedArrayQueue = this.d == Integer.MAX_VALUE ? new SpscLinkedArrayQueue(this.e) : new SpscArrayQueue(this.d);
                        this.f = spscLinkedArrayQueue;
                        r3 = spscLinkedArrayQueue;
                    }
                    if (r3.offer(objCall)) {
                        if (getAndIncrement() != 0) {
                            z2 = false;
                        }
                        d();
                        z2 = true;
                    } else {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        z2 = true;
                    }
                }
                if (!z2 || this.d == Integer.MAX_VALUE) {
                    return;
                }
                synchronized (this) {
                    observableSource = this.o.poll();
                    if (observableSource == null) {
                        this.p--;
                        z = true;
                    }
                }
            } while (!z);
            c();
        }

        public boolean b() {
            a<?, ?>[] andSet;
            this.k.dispose();
            a<?, ?>[] aVarArr = this.j.get();
            a<?, ?>[] aVarArr2 = r;
            if (aVarArr == aVarArr2 || (andSet = this.j.getAndSet(aVarArr2)) == r) {
                return false;
            }
            for (a<?, ?> aVar : andSet) {
                if (aVar == null) {
                    throw null;
                }
                DisposableHelper.dispose(aVar);
            }
            return true;
        }

        public void c() {
            if (getAndIncrement() == 0) {
                d();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:128:0x0004, code lost:
        
            continue;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:120:0x00ea A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:133:0x00f2 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:82:0x00eb  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x00f1 A[PHI: r4
  0x00f1: PHI (r4v10 int) = (r4v8 int), (r4v11 int) binds: [B:72:0x00d0, B:84:0x00ef] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void d() {
            /*
                Method dump skipped, instructions count: 299
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableFlatMap.b.d():void");
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            Throwable thTerminate;
            if (this.i) {
                return;
            }
            this.i = true;
            if (!b() || (thTerminate = this.h.terminate()) == null || thTerminate == ExceptionHelper.TERMINATED) {
                return;
            }
            RxJavaPlugins.onError(thTerminate);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.i;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.g) {
                return;
            }
            this.g = true;
            c();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
            } else if (!this.h.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.g = true;
                c();
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.g) {
                return;
            }
            try {
                ObservableSource<? extends U> observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null ObservableSource");
                if (this.d != Integer.MAX_VALUE) {
                    synchronized (this) {
                        if (this.p == this.d) {
                            this.o.offer(observableSource);
                            return;
                        }
                        this.p++;
                    }
                }
                a(observableSource);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.k.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.k, disposable)) {
                this.k = disposable;
                this.a.onSubscribe(this);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a(a<T, U> aVar) {
            a<?, ?>[] aVarArr;
            a<?, ?>[] aVarArr2;
            do {
                aVarArr = this.j.get();
                int length = aVarArr.length;
                if (length == 0) {
                    return;
                }
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (aVarArr[i2] == aVar) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    aVarArr2 = q;
                } else {
                    a<?, ?>[] aVarArr3 = new a[length - 1];
                    System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                    System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                    aVarArr2 = aVarArr3;
                }
            } while (!this.j.compareAndSet(aVarArr, aVarArr2));
        }

        public boolean a() {
            if (this.i) {
                return true;
            }
            Throwable th = this.h.get();
            if (this.c || th == null) {
                return false;
            }
            b();
            Throwable thTerminate = this.h.terminate();
            if (thTerminate != ExceptionHelper.TERMINATED) {
                this.a.onError(thTerminate);
            }
            return true;
        }
    }
}
