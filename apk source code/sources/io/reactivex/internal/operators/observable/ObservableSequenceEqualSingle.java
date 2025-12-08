package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class ObservableSequenceEqualSingle<T> extends Single<Boolean> implements FuseToObservable<Boolean> {
    public final ObservableSource<? extends T> a;
    public final ObservableSource<? extends T> b;
    public final BiPredicate<? super T, ? super T> c;
    public final int d;

    public static final class b<T> implements Observer<T> {
        public final a<T> a;
        public final SpscLinkedArrayQueue<T> b;
        public final int c;
        public volatile boolean d;
        public Throwable e;

        public b(a<T> aVar, int i, int i2) {
            this.a = aVar;
            this.c = i;
            this.b = new SpscLinkedArrayQueue<>(i2);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.d = true;
            this.a.a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.e = th;
            this.d = true;
            this.a.a();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.b.offer(t);
            this.a.a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            a<T> aVar = this.a;
            aVar.c.setResource(this.c, disposable);
        }
    }

    public ObservableSequenceEqualSingle(ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.a = observableSource;
        this.b = observableSource2;
        this.c = biPredicate;
        this.d = i;
    }

    @Override // io.reactivex.internal.fuseable.FuseToObservable
    public Observable<Boolean> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableSequenceEqual(this.a, this.b, this.c, this.d));
    }

    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        a aVar = new a(singleObserver, this.d, this.a, this.b, this.c);
        singleObserver.onSubscribe(aVar);
        b<T>[] bVarArr = aVar.f;
        aVar.d.subscribe(bVarArr[0]);
        aVar.e.subscribe(bVarArr[1]);
    }

    public static final class a<T> extends AtomicInteger implements Disposable {
        public static final long serialVersionUID = -6178010334400373240L;
        public final SingleObserver<? super Boolean> a;
        public final BiPredicate<? super T, ? super T> b;
        public final ArrayCompositeDisposable c = new ArrayCompositeDisposable(2);
        public final ObservableSource<? extends T> d;
        public final ObservableSource<? extends T> e;
        public final b<T>[] f;
        public volatile boolean g;
        public T h;
        public T i;

        public a(SingleObserver<? super Boolean> singleObserver, int i, ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate) {
            this.a = singleObserver;
            this.d = observableSource;
            this.e = observableSource2;
            this.b = biPredicate;
            this.f = new b[]{new b<>(this, 0, i), new b<>(this, 1, i)};
        }

        public void a(SpscLinkedArrayQueue<T> spscLinkedArrayQueue, SpscLinkedArrayQueue<T> spscLinkedArrayQueue2) {
            this.g = true;
            spscLinkedArrayQueue.clear();
            spscLinkedArrayQueue2.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.g) {
                return;
            }
            this.g = true;
            this.c.dispose();
            if (getAndIncrement() == 0) {
                b<T>[] bVarArr = this.f;
                bVarArr[0].b.clear();
                bVarArr[1].b.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.g;
        }

        public void a() {
            Throwable th;
            Throwable th2;
            if (getAndIncrement() != 0) {
                return;
            }
            b<T>[] bVarArr = this.f;
            b<T> bVar = bVarArr[0];
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = bVar.b;
            b<T> bVar2 = bVarArr[1];
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue2 = bVar2.b;
            int iAddAndGet = 1;
            while (!this.g) {
                boolean z = bVar.d;
                if (z && (th2 = bVar.e) != null) {
                    a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                    this.a.onError(th2);
                    return;
                }
                boolean z2 = bVar2.d;
                if (z2 && (th = bVar2.e) != null) {
                    a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                    this.a.onError(th);
                    return;
                }
                if (this.h == null) {
                    this.h = spscLinkedArrayQueue.poll();
                }
                boolean z3 = this.h == null;
                if (this.i == null) {
                    this.i = spscLinkedArrayQueue2.poll();
                }
                boolean z4 = this.i == null;
                if (z && z2 && z3 && z4) {
                    this.a.onSuccess(true);
                    return;
                }
                if (z && z2 && z3 != z4) {
                    a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                    this.a.onSuccess(false);
                    return;
                }
                if (!z3 && !z4) {
                    try {
                        if (!this.b.test(this.h, this.i)) {
                            a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                            this.a.onSuccess(false);
                            return;
                        } else {
                            this.h = null;
                            this.i = null;
                        }
                    } catch (Throwable th3) {
                        Exceptions.throwIfFatal(th3);
                        a(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                        this.a.onError(th3);
                        return;
                    }
                }
                if (z3 || z4) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            spscLinkedArrayQueue.clear();
            spscLinkedArrayQueue2.clear();
        }
    }
}
