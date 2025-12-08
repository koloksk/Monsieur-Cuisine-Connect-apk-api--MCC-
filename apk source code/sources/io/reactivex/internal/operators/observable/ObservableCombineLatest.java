package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableCombineLatest<T, R> extends Observable<R> {
    public final ObservableSource<? extends T>[] a;
    public final Iterable<? extends ObservableSource<? extends T>> b;
    public final Function<? super Object[], ? extends R> c;
    public final int d;
    public final boolean e;

    public static final class a<T, R> extends AtomicReference<Disposable> implements Observer<T> {
        public static final long serialVersionUID = -4823716997131257941L;
        public final b<T, R> a;
        public final int b;

        public a(b<T, R> bVar, int i) {
            this.a = bVar;
            this.b = i;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.a(this.b);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.a(this.b, th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.a.a(this.b, (int) t);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public ObservableCombineLatest(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.a = observableSourceArr;
        this.b = iterable;
        this.c = function;
        this.d = i;
        this.e = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        int length;
        ObservableSource<? extends T>[] observableSourceArr = this.a;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            length = 0;
            for (ObservableSource<? extends T> observableSource : this.b) {
                if (length == observableSourceArr.length) {
                    ObservableSource<? extends T>[] observableSourceArr2 = new ObservableSource[(length >> 2) + length];
                    System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, length);
                    observableSourceArr = observableSourceArr2;
                }
                observableSourceArr[length] = observableSource;
                length++;
            }
        } else {
            length = observableSourceArr.length;
        }
        int i = length;
        if (i == 0) {
            EmptyDisposable.complete(observer);
            return;
        }
        b bVar = new b(observer, this.c, i, this.d, this.e);
        a<T, R>[] aVarArr = bVar.c;
        int length2 = aVarArr.length;
        bVar.a.onSubscribe(bVar);
        for (int i2 = 0; i2 < length2 && !bVar.h && !bVar.g; i2++) {
            observableSourceArr[i2].subscribe(aVarArr[i2]);
        }
    }

    public static final class b<T, R> extends AtomicInteger implements Disposable {
        public static final long serialVersionUID = 8567835998786448817L;
        public final Observer<? super R> a;
        public final Function<? super Object[], ? extends R> b;
        public final a<T, R>[] c;
        public Object[] d;
        public final SpscLinkedArrayQueue<Object[]> e;
        public final boolean f;
        public volatile boolean g;
        public volatile boolean h;
        public final AtomicThrowable i = new AtomicThrowable();
        public int j;
        public int k;

        public b(Observer<? super R> observer, Function<? super Object[], ? extends R> function, int i, int i2, boolean z) {
            this.a = observer;
            this.b = function;
            this.f = z;
            this.d = new Object[i];
            a<T, R>[] aVarArr = new a[i];
            for (int i3 = 0; i3 < i; i3++) {
                aVarArr[i3] = new a<>(this, i3);
            }
            this.c = aVarArr;
            this.e = new SpscLinkedArrayQueue<>(i2);
        }

        public void a() {
            for (a<T, R> aVar : this.c) {
                if (aVar == null) {
                    throw null;
                }
                DisposableHelper.dispose(aVar);
            }
        }

        public void b() {
            if (getAndIncrement() != 0) {
                return;
            }
            SpscLinkedArrayQueue<Object[]> spscLinkedArrayQueue = this.e;
            Observer<? super R> observer = this.a;
            boolean z = this.f;
            int iAddAndGet = 1;
            while (!this.g) {
                if (!z && this.i.get() != null) {
                    a();
                    a((SpscLinkedArrayQueue<?>) spscLinkedArrayQueue);
                    observer.onError(this.i.terminate());
                    return;
                }
                boolean z2 = this.h;
                Object[] objArrPoll = spscLinkedArrayQueue.poll();
                boolean z3 = objArrPoll == null;
                if (z2 && z3) {
                    a((SpscLinkedArrayQueue<?>) spscLinkedArrayQueue);
                    Throwable thTerminate = this.i.terminate();
                    if (thTerminate == null) {
                        observer.onComplete();
                        return;
                    } else {
                        observer.onError(thTerminate);
                        return;
                    }
                }
                if (z3) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    try {
                        observer.onNext((Object) ObjectHelper.requireNonNull(this.b.apply(objArrPoll), "The combiner returned a null value"));
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.i.addThrowable(th);
                        a();
                        a((SpscLinkedArrayQueue<?>) spscLinkedArrayQueue);
                        observer.onError(this.i.terminate());
                        return;
                    }
                }
            }
            a((SpscLinkedArrayQueue<?>) spscLinkedArrayQueue);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.g) {
                return;
            }
            this.g = true;
            a();
            if (getAndIncrement() == 0) {
                a((SpscLinkedArrayQueue<?>) this.e);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.g;
        }

        public void a(SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            synchronized (this) {
                this.d = null;
            }
            spscLinkedArrayQueue.clear();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a(int i, T t) {
            boolean z;
            synchronized (this) {
                Object[] objArr = this.d;
                if (objArr == null) {
                    return;
                }
                Object obj = objArr[i];
                int i2 = this.j;
                if (obj == null) {
                    i2++;
                    this.j = i2;
                }
                objArr[i] = t;
                if (i2 == objArr.length) {
                    this.e.offer(objArr.clone());
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    b();
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0025 A[Catch: all -> 0x002a, TryCatch #0 {, blocks: (B:7:0x000e, B:9:0x0012, B:11:0x0014, B:16:0x001d, B:19:0x0027, B:18:0x0025), top: B:29:0x000e }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a(int r3, java.lang.Throwable r4) {
            /*
                r2 = this;
                io.reactivex.internal.util.AtomicThrowable r0 = r2.i
                boolean r0 = r0.addThrowable(r4)
                if (r0 == 0) goto L36
                boolean r4 = r2.f
                r0 = 1
                if (r4 == 0) goto L2d
                monitor-enter(r2)
                java.lang.Object[] r4 = r2.d     // Catch: java.lang.Throwable -> L2a
                if (r4 != 0) goto L14
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L2a
                return
            L14:
                r3 = r4[r3]     // Catch: java.lang.Throwable -> L2a
                if (r3 != 0) goto L1a
                r3 = r0
                goto L1b
            L1a:
                r3 = 0
            L1b:
                if (r3 != 0) goto L25
                int r1 = r2.k     // Catch: java.lang.Throwable -> L2a
                int r1 = r1 + r0
                r2.k = r1     // Catch: java.lang.Throwable -> L2a
                int r4 = r4.length     // Catch: java.lang.Throwable -> L2a
                if (r1 != r4) goto L27
            L25:
                r2.h = r0     // Catch: java.lang.Throwable -> L2a
            L27:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L2a
                r0 = r3
                goto L2d
            L2a:
                r3 = move-exception
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L2a
                throw r3
            L2d:
                if (r0 == 0) goto L32
                r2.a()
            L32:
                r2.b()
                goto L39
            L36:
                io.reactivex.plugins.RxJavaPlugins.onError(r4)
            L39:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableCombineLatest.b.a(int, java.lang.Throwable):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0019 A[Catch: all -> 0x0025, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:7:0x0007, B:12:0x0011, B:15:0x001b, B:14:0x0019), top: B:23:0x0001 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a(int r4) {
            /*
                r3 = this;
                monitor-enter(r3)
                java.lang.Object[] r0 = r3.d     // Catch: java.lang.Throwable -> L25
                if (r0 != 0) goto L7
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L25
                return
            L7:
                r4 = r0[r4]     // Catch: java.lang.Throwable -> L25
                r1 = 1
                if (r4 != 0) goto Le
                r4 = r1
                goto Lf
            Le:
                r4 = 0
            Lf:
                if (r4 != 0) goto L19
                int r2 = r3.k     // Catch: java.lang.Throwable -> L25
                int r2 = r2 + r1
                r3.k = r2     // Catch: java.lang.Throwable -> L25
                int r0 = r0.length     // Catch: java.lang.Throwable -> L25
                if (r2 != r0) goto L1b
            L19:
                r3.h = r1     // Catch: java.lang.Throwable -> L25
            L1b:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L25
                if (r4 == 0) goto L21
                r3.a()
            L21:
                r3.b()
                return
            L25:
                r4 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L25
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableCombineLatest.b.a(int):void");
        }
    }
}
