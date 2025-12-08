package io.reactivex.internal.operators.mixed;

import defpackage.q5;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableConcatMapSingle<T, R> extends Observable<R> {
    public final Observable<T> a;
    public final Function<? super T, ? extends SingleSource<? extends R>> b;
    public final ErrorMode c;
    public final int d;

    public static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = -9140123220065488293L;
        public final Observer<? super R> a;
        public final Function<? super T, ? extends SingleSource<? extends R>> b;
        public final AtomicThrowable c = new AtomicThrowable();
        public final C0048a<R> d = new C0048a<>(this);
        public final SimplePlainQueue<T> e;
        public final ErrorMode f;
        public Disposable g;
        public volatile boolean h;
        public volatile boolean i;
        public R j;
        public volatile int k;

        /* renamed from: io.reactivex.internal.operators.mixed.ObservableConcatMapSingle$a$a, reason: collision with other inner class name */
        public static final class C0048a<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
            public static final long serialVersionUID = -3051469169682093892L;
            public final a<?, R> a;

            public C0048a(a<?, R> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a<?, R> aVar = this.a;
                if (!aVar.c.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (aVar.f != ErrorMode.END) {
                    aVar.g.dispose();
                }
                aVar.k = 0;
                aVar.a();
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(R r) {
                a<?, R> aVar = this.a;
                aVar.j = r;
                aVar.k = 2;
                aVar.a();
            }
        }

        public a(Observer<? super R> observer, Function<? super T, ? extends SingleSource<? extends R>> function, int i, ErrorMode errorMode) {
            this.a = observer;
            this.b = function;
            this.f = errorMode;
            this.e = new SpscLinkedArrayQueue(i);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
        
            r2.clear();
            r10.j = null;
            r0.onError(r3.terminate());
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x003b, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a() {
            /*
                r10 = this;
                int r0 = r10.getAndIncrement()
                if (r0 == 0) goto L7
                return
            L7:
                io.reactivex.Observer<? super R> r0 = r10.a
                io.reactivex.internal.util.ErrorMode r1 = r10.f
                io.reactivex.internal.fuseable.SimplePlainQueue<T> r2 = r10.e
                io.reactivex.internal.util.AtomicThrowable r3 = r10.c
                r4 = 1
                r5 = r4
            L11:
                boolean r6 = r10.i
                r7 = 0
                if (r6 == 0) goto L1d
                r2.clear()
                r10.j = r7
                goto L98
            L1d:
                int r6 = r10.k
                java.lang.Object r8 = r3.get()
                if (r8 == 0) goto L3c
                io.reactivex.internal.util.ErrorMode r8 = io.reactivex.internal.util.ErrorMode.IMMEDIATE
                if (r1 == r8) goto L2f
                io.reactivex.internal.util.ErrorMode r8 = io.reactivex.internal.util.ErrorMode.BOUNDARY
                if (r1 != r8) goto L3c
                if (r6 != 0) goto L3c
            L2f:
                r2.clear()
                r10.j = r7
                java.lang.Throwable r1 = r3.terminate()
                r0.onError(r1)
                return
            L3c:
                r8 = 0
                if (r6 != 0) goto L8a
                boolean r6 = r10.h
                java.lang.Object r7 = r2.poll()
                if (r7 != 0) goto L48
                r8 = r4
            L48:
                if (r6 == 0) goto L5a
                if (r8 == 0) goto L5a
                java.lang.Throwable r1 = r3.terminate()
                if (r1 != 0) goto L56
                r0.onComplete()
                goto L59
            L56:
                r0.onError(r1)
            L59:
                return
            L5a:
                if (r8 == 0) goto L5d
                goto L98
            L5d:
                io.reactivex.functions.Function<? super T, ? extends io.reactivex.SingleSource<? extends R>> r6 = r10.b     // Catch: java.lang.Throwable -> L73
                java.lang.Object r6 = r6.apply(r7)     // Catch: java.lang.Throwable -> L73
                java.lang.String r7 = "The mapper returned a null SingleSource"
                java.lang.Object r6 = io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r7)     // Catch: java.lang.Throwable -> L73
                io.reactivex.SingleSource r6 = (io.reactivex.SingleSource) r6     // Catch: java.lang.Throwable -> L73
                r10.k = r4
                io.reactivex.internal.operators.mixed.ObservableConcatMapSingle$a$a<R> r7 = r10.d
                r6.subscribe(r7)
                goto L98
            L73:
                r1 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r1)
                io.reactivex.disposables.Disposable r4 = r10.g
                r4.dispose()
                r2.clear()
                r3.addThrowable(r1)
                java.lang.Throwable r1 = r3.terminate()
                r0.onError(r1)
                return
            L8a:
                r9 = 2
                if (r6 != r9) goto L98
                R r6 = r10.j
                r10.j = r7
                r0.onNext(r6)
                r10.k = r8
                goto L11
            L98:
                int r5 = -r5
                int r5 = r10.addAndGet(r5)
                if (r5 != 0) goto L11
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.mixed.ObservableConcatMapSingle.a.a():void");
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.i = true;
            this.g.dispose();
            C0048a<R> c0048a = this.d;
            if (c0048a == null) {
                throw null;
            }
            DisposableHelper.dispose(c0048a);
            if (getAndIncrement() == 0) {
                this.e.clear();
                this.j = null;
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.i;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.h = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.c.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.f == ErrorMode.IMMEDIATE) {
                C0048a<R> c0048a = this.d;
                if (c0048a == null) {
                    throw null;
                }
                DisposableHelper.dispose(c0048a);
            }
            this.h = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.e.offer(t);
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.g, disposable)) {
                this.g = disposable;
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableConcatMapSingle(Observable<T> observable, Function<? super T, ? extends SingleSource<? extends R>> function, ErrorMode errorMode, int i) {
        this.a = observable;
        this.b = function;
        this.c = errorMode;
        this.d = i;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        if (q5.b(this.a, this.b, observer)) {
            return;
        }
        this.a.subscribe(new a(observer, this.b, this.d, this.c));
    }
}
