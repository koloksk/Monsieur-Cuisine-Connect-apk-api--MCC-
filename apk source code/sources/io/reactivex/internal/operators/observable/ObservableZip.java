package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableZip<T, R> extends Observable<R> {
    public final ObservableSource<? extends T>[] a;
    public final Iterable<? extends ObservableSource<? extends T>> b;
    public final Function<? super Object[], ? extends R> c;
    public final int d;
    public final boolean e;

    public static final class a<T, R> extends AtomicInteger implements Disposable {
        public static final long serialVersionUID = 2983708048395377667L;
        public final Observer<? super R> a;
        public final Function<? super Object[], ? extends R> b;
        public final b<T, R>[] c;
        public final T[] d;
        public final boolean e;
        public volatile boolean f;

        public a(Observer<? super R> observer, Function<? super Object[], ? extends R> function, int i, boolean z) {
            this.a = observer;
            this.b = function;
            this.c = new b[i];
            this.d = (T[]) new Object[i];
            this.e = z;
        }

        public void a() {
            for (b<T, R> bVar : this.c) {
                bVar.b.clear();
            }
            for (b<T, R> bVar2 : this.c) {
                DisposableHelper.dispose(bVar2.e);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:33:0x0068  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0067 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void b() {
            /*
                r16 = this;
                r1 = r16
                int r0 = r16.getAndIncrement()
                if (r0 == 0) goto L9
                return
            L9:
                io.reactivex.internal.operators.observable.ObservableZip$b<T, R>[] r0 = r1.c
                io.reactivex.Observer<? super R> r2 = r1.a
                T[] r3 = r1.d
                boolean r4 = r1.e
                r5 = 1
                r6 = r5
            L13:
                int r7 = r0.length
                r9 = 0
                r10 = 0
                r11 = 0
            L17:
                if (r9 >= r7) goto L88
                r12 = r0[r9]
                r13 = r3[r11]
                if (r13 != 0) goto L70
                boolean r13 = r12.c
                io.reactivex.internal.queue.SpscLinkedArrayQueue<T> r14 = r12.b
                java.lang.Object r14 = r14.poll()
                if (r14 != 0) goto L2b
                r15 = r5
                goto L2c
            L2b:
                r15 = 0
            L2c:
                boolean r8 = r1.f
                if (r8 == 0) goto L35
                r16.a()
            L33:
                r8 = r5
                goto L65
            L35:
                if (r13 == 0) goto L64
                if (r4 == 0) goto L4c
                if (r15 == 0) goto L64
                java.lang.Throwable r8 = r12.d
                r1.f = r5
                r16.a()
                if (r8 == 0) goto L48
                r2.onError(r8)
                goto L33
            L48:
                r2.onComplete()
                goto L33
            L4c:
                java.lang.Throwable r8 = r12.d
                if (r8 == 0) goto L59
                r1.f = r5
                r16.a()
                r2.onError(r8)
                goto L33
            L59:
                if (r15 == 0) goto L64
                r1.f = r5
                r16.a()
                r2.onComplete()
                goto L33
            L64:
                r8 = 0
            L65:
                if (r8 == 0) goto L68
                return
            L68:
                if (r15 != 0) goto L6d
                r3[r11] = r14
                goto L83
            L6d:
                int r10 = r10 + 1
                goto L83
            L70:
                boolean r8 = r12.c
                if (r8 == 0) goto L83
                if (r4 != 0) goto L83
                java.lang.Throwable r8 = r12.d
                if (r8 == 0) goto L83
                r1.f = r5
                r16.a()
                r2.onError(r8)
                return
            L83:
                int r11 = r11 + 1
                int r9 = r9 + 1
                goto L17
            L88:
                if (r10 == 0) goto L92
                int r6 = -r6
                int r6 = r1.addAndGet(r6)
                if (r6 != 0) goto L13
                return
            L92:
                io.reactivex.functions.Function<? super java.lang.Object[], ? extends R> r7 = r1.b     // Catch: java.lang.Throwable -> Lab
                java.lang.Object r8 = r3.clone()     // Catch: java.lang.Throwable -> Lab
                java.lang.Object r7 = r7.apply(r8)     // Catch: java.lang.Throwable -> Lab
                java.lang.String r8 = "The zipper returned a null value"
                java.lang.Object r7 = io.reactivex.internal.functions.ObjectHelper.requireNonNull(r7, r8)     // Catch: java.lang.Throwable -> Lab
                r2.onNext(r7)
                r7 = 0
                java.util.Arrays.fill(r3, r7)
                goto L13
            Lab:
                r0 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                r16.a()
                r2.onError(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableZip.a.b():void");
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.f) {
                return;
            }
            this.f = true;
            for (b<T, R> bVar : this.c) {
                DisposableHelper.dispose(bVar.e);
            }
            if (getAndIncrement() == 0) {
                for (b<T, R> bVar2 : this.c) {
                    bVar2.b.clear();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f;
        }
    }

    public static final class b<T, R> implements Observer<T> {
        public final a<T, R> a;
        public final SpscLinkedArrayQueue<T> b;
        public volatile boolean c;
        public Throwable d;
        public final AtomicReference<Disposable> e = new AtomicReference<>();

        public b(a<T, R> aVar, int i) {
            this.a = aVar;
            this.b = new SpscLinkedArrayQueue<>(i);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.c = true;
            this.a.b();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.d = th;
            this.c = true;
            this.a.b();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.b.offer(t);
            this.a.b();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.e, disposable);
        }
    }

    public ObservableZip(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i, boolean z) {
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
        if (length == 0) {
            EmptyDisposable.complete(observer);
            return;
        }
        a aVar = new a(observer, this.c, length, this.e);
        int i = this.d;
        b<T, R>[] bVarArr = aVar.c;
        int length2 = bVarArr.length;
        for (int i2 = 0; i2 < length2; i2++) {
            bVarArr[i2] = new b<>(aVar, i);
        }
        aVar.lazySet(0);
        aVar.a.onSubscribe(aVar);
        for (int i3 = 0; i3 < length2 && !aVar.f; i3++) {
            observableSourceArr[i3].subscribe(bVarArr[i3]);
        }
    }
}
