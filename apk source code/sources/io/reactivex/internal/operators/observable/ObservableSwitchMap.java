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
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableSwitchMap<T, R> extends bl<T, R> {
    public final Function<? super T, ? extends ObservableSource<? extends R>> a;
    public final int b;
    public final boolean c;

    public static final class a<T, R> extends AtomicReference<Disposable> implements Observer<R> {
        public static final long serialVersionUID = 3837284832786408377L;
        public final b<T, R> a;
        public final long b;
        public final int c;
        public volatile SimpleQueue<R> d;
        public volatile boolean e;

        public a(b<T, R> bVar, long j, int i) {
            this.a = bVar;
            this.b = j;
            this.c = i;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.b == this.a.j) {
                this.e = true;
                this.a.b();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            b<T, R> bVar = this.a;
            if (bVar == null) {
                throw null;
            }
            if (this.b != bVar.j || !bVar.e.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!bVar.d) {
                bVar.h.dispose();
                bVar.f = true;
            }
            this.e = true;
            bVar.b();
        }

        @Override // io.reactivex.Observer
        public void onNext(R r) {
            if (this.b == this.a.j) {
                if (r != null) {
                    this.d.offer(r);
                }
                this.a.b();
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int iRequestFusion = queueDisposable.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.d = queueDisposable;
                        this.e = true;
                        this.a.b();
                        return;
                    } else if (iRequestFusion == 2) {
                        this.d = queueDisposable;
                        return;
                    }
                }
                this.d = new SpscLinkedArrayQueue(this.c);
            }
        }
    }

    public static final class b<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        public static final a<Object, Object> k;
        public static final long serialVersionUID = -3491074160481096299L;
        public final Observer<? super R> a;
        public final Function<? super T, ? extends ObservableSource<? extends R>> b;
        public final int c;
        public final boolean d;
        public volatile boolean f;
        public volatile boolean g;
        public Disposable h;
        public volatile long j;
        public final AtomicReference<a<T, R>> i = new AtomicReference<>();
        public final AtomicThrowable e = new AtomicThrowable();

        static {
            a<Object, Object> aVar = new a<>(null, -1L, 1);
            k = aVar;
            DisposableHelper.dispose(aVar);
        }

        public b(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, boolean z) {
            this.a = observer;
            this.b = function;
            this.c = i;
            this.d = z;
        }

        public void a() {
            a<Object, Object> aVar;
            a<T, R> aVar2 = this.i.get();
            a<Object, Object> aVar3 = k;
            if (aVar2 == aVar3 || (aVar = (a) this.i.getAndSet(aVar3)) == k || aVar == null) {
                return;
            }
            DisposableHelper.dispose(aVar);
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x000f A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:95:0x00e9 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void b() {
            /*
                Method dump skipped, instructions count: 241
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableSwitchMap.b.b():void");
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.g) {
                return;
            }
            this.g = true;
            this.h.dispose();
            a();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.g;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.f) {
                return;
            }
            this.f = true;
            b();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.f || !this.e.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.d) {
                a();
            }
            this.f = true;
            b();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            a<T, R> aVar;
            long j = this.j + 1;
            this.j = j;
            a<T, R> aVar2 = this.i.get();
            if (aVar2 != null) {
                DisposableHelper.dispose(aVar2);
            }
            try {
                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The ObservableSource returned is null");
                a<T, R> aVar3 = new a<>(this, j, this.c);
                do {
                    aVar = this.i.get();
                    if (aVar == k) {
                        return;
                    }
                } while (!this.i.compareAndSet(aVar, aVar3));
                observableSource.subscribe(aVar3);
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
    }

    public ObservableSwitchMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = i;
        this.c = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        if (ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.a)) {
            return;
        }
        this.source.subscribe(new b(observer, this.a, this.b, this.c));
    }
}
