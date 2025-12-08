package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class ParallelReduceFull<T> extends Flowable<T> {
    public final ParallelFlowable<? extends T> b;
    public final BiFunction<T, T, T> c;

    public static final class a<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        public static final long serialVersionUID = -7954444275102466525L;
        public final b<T> a;
        public final BiFunction<T, T, T> b;
        public T c;
        public boolean d;

        public a(b<T> bVar, BiFunction<T, T, T> biFunction) {
            this.a = bVar;
            this.b = biFunction;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            int i;
            if (this.d) {
                return;
            }
            this.d = true;
            b<T> bVar = this.a;
            T t = this.c;
            if (t != null) {
                while (true) {
                    c<T> cVar = bVar.c.get();
                    if (cVar == null) {
                        cVar = new c<>();
                        if (!bVar.c.compareAndSet(null, cVar)) {
                            continue;
                        }
                    }
                    while (true) {
                        i = cVar.get();
                        if (i >= 2) {
                            i = -1;
                            break;
                        } else if (cVar.compareAndSet(i, i + 1)) {
                            break;
                        }
                    }
                    if (i >= 0) {
                        if (i == 0) {
                            cVar.a = t;
                        } else {
                            cVar.b = t;
                        }
                        if (cVar.c.incrementAndGet() == 2) {
                            bVar.c.compareAndSet(cVar, null);
                        } else {
                            cVar = null;
                        }
                        if (cVar == null) {
                            break;
                        }
                        try {
                            t = (T) ObjectHelper.requireNonNull(bVar.b.apply(cVar.a, cVar.b), "The reducer returned a null value");
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            bVar.a(th);
                            return;
                        }
                    } else {
                        bVar.c.compareAndSet(cVar, null);
                    }
                }
            }
            if (bVar.d.decrementAndGet() == 0) {
                c<T> cVar2 = bVar.c.get();
                bVar.c.lazySet(null);
                if (cVar2 != null) {
                    bVar.complete(cVar2.a);
                } else {
                    bVar.downstream.onComplete();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
            } else {
                this.d = true;
                this.a.a(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.d) {
                return;
            }
            T t2 = this.c;
            if (t2 == null) {
                this.c = t;
                return;
            }
            try {
                this.c = (T) ObjectHelper.requireNonNull(this.b.apply(t2, t), "The reducer returned a null value");
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                get().cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public static final class b<T> extends DeferredScalarSubscription<T> {
        public static final long serialVersionUID = -5370107872170712765L;
        public final a<T>[] a;
        public final BiFunction<T, T, T> b;
        public final AtomicReference<c<T>> c;
        public final AtomicInteger d;
        public final AtomicReference<Throwable> e;

        public b(Subscriber<? super T> subscriber, int i, BiFunction<T, T, T> biFunction) {
            super(subscriber);
            this.c = new AtomicReference<>();
            this.d = new AtomicInteger();
            this.e = new AtomicReference<>();
            a<T>[] aVarArr = new a[i];
            for (int i2 = 0; i2 < i; i2++) {
                aVarArr[i2] = new a<>(this, biFunction);
            }
            this.a = aVarArr;
            this.b = biFunction;
            this.d.lazySet(i);
        }

        public void a(Throwable th) {
            if (this.e.compareAndSet(null, th)) {
                cancel();
                this.downstream.onError(th);
            } else if (th != this.e.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            for (a<T> aVar : this.a) {
                if (aVar == null) {
                    throw null;
                }
                SubscriptionHelper.cancel(aVar);
            }
        }
    }

    public static final class c<T> extends AtomicInteger {
        public static final long serialVersionUID = 473971317683868662L;
        public T a;
        public T b;
        public final AtomicInteger c = new AtomicInteger();
    }

    public ParallelReduceFull(ParallelFlowable<? extends T> parallelFlowable, BiFunction<T, T, T> biFunction) {
        this.b = parallelFlowable;
        this.c = biFunction;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        b bVar = new b(subscriber, this.b.parallelism(), this.c);
        subscriber.onSubscribe(bVar);
        this.b.subscribe(bVar.a);
    }
}
