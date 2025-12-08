package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class ParallelSortedJoin<T> extends Flowable<T> {
    public final ParallelFlowable<List<T>> b;
    public final Comparator<? super T> c;

    public static final class a<T> extends AtomicReference<Subscription> implements FlowableSubscriber<List<T>> {
        public static final long serialVersionUID = 6751017204873808094L;
        public final b<T> a;
        public final int b;

        public a(b<T> bVar, int i) {
            this.a = bVar;
            this.b = i;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            b<T> bVar = this.a;
            if (bVar.i.compareAndSet(null, th)) {
                bVar.b();
            } else if (th != bVar.i.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            b<T> bVar = this.a;
            int i = this.b;
            bVar.c[i] = (List) obj;
            if (bVar.h.decrementAndGet() == 0) {
                bVar.b();
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public static final class b<T> extends AtomicInteger implements Subscription {
        public static final long serialVersionUID = 3481980673745556697L;
        public final Subscriber<? super T> a;
        public final a<T>[] b;
        public final List<T>[] c;
        public final int[] d;
        public final Comparator<? super T> e;
        public volatile boolean g;
        public final AtomicLong f = new AtomicLong();
        public final AtomicInteger h = new AtomicInteger();
        public final AtomicReference<Throwable> i = new AtomicReference<>();

        public b(Subscriber<? super T> subscriber, int i, Comparator<? super T> comparator) {
            this.a = subscriber;
            this.e = comparator;
            a<T>[] aVarArr = new a[i];
            for (int i2 = 0; i2 < i; i2++) {
                aVarArr[i2] = new a<>(this, i2);
            }
            this.b = aVarArr;
            this.c = new List[i];
            this.d = new int[i];
            this.h.lazySet(i);
        }

        public void a() {
            for (a<T> aVar : this.b) {
                if (aVar == null) {
                    throw null;
                }
                SubscriptionHelper.cancel(aVar);
            }
        }

        public void b() {
            boolean z;
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = this.a;
            List<T>[] listArr = this.c;
            int[] iArr = this.d;
            int length = iArr.length;
            int i = 1;
            while (true) {
                long j = this.f.get();
                long j2 = 0;
                while (j2 != j) {
                    if (this.g) {
                        Arrays.fill(listArr, (Object) null);
                        return;
                    }
                    Throwable th = this.i.get();
                    if (th != null) {
                        a();
                        Arrays.fill(listArr, (Object) null);
                        subscriber.onError(th);
                        return;
                    }
                    int i2 = -1;
                    T t = null;
                    for (int i3 = 0; i3 < length; i3++) {
                        List<T> list = listArr[i3];
                        int i4 = iArr[i3];
                        if (list.size() != i4) {
                            if (t == null) {
                                t = list.get(i4);
                            } else {
                                T t2 = list.get(i4);
                                try {
                                    if (this.e.compare(t, t2) > 0) {
                                        t = t2;
                                    }
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    a();
                                    Arrays.fill(listArr, (Object) null);
                                    if (!this.i.compareAndSet(null, th2)) {
                                        RxJavaPlugins.onError(th2);
                                    }
                                    subscriber.onError(this.i.get());
                                    return;
                                }
                            }
                            i2 = i3;
                        }
                    }
                    if (t == null) {
                        Arrays.fill(listArr, (Object) null);
                        subscriber.onComplete();
                        return;
                    } else {
                        subscriber.onNext(t);
                        iArr[i2] = iArr[i2] + 1;
                        j2++;
                    }
                }
                if (j2 == j) {
                    if (this.g) {
                        Arrays.fill(listArr, (Object) null);
                        return;
                    }
                    Throwable th3 = this.i.get();
                    if (th3 != null) {
                        a();
                        Arrays.fill(listArr, (Object) null);
                        subscriber.onError(th3);
                        return;
                    }
                    int i5 = 0;
                    while (true) {
                        if (i5 >= length) {
                            z = true;
                            break;
                        } else {
                            if (iArr[i5] != listArr[i5].size()) {
                                z = false;
                                break;
                            }
                            i5++;
                        }
                    }
                    if (z) {
                        Arrays.fill(listArr, (Object) null);
                        subscriber.onComplete();
                        return;
                    }
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    this.f.addAndGet(-j2);
                }
                int iAddAndGet = get();
                if (iAddAndGet == i && (iAddAndGet = addAndGet(-i)) == 0) {
                    return;
                } else {
                    i = iAddAndGet;
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.g) {
                return;
            }
            this.g = true;
            a();
            if (getAndIncrement() == 0) {
                Arrays.fill(this.c, (Object) null);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.f, j);
                if (this.h.get() == 0) {
                    b();
                }
            }
        }
    }

    public ParallelSortedJoin(ParallelFlowable<List<T>> parallelFlowable, Comparator<? super T> comparator) {
        this.b = parallelFlowable;
        this.c = comparator;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        b bVar = new b(subscriber, this.b.parallelism(), this.c);
        subscriber.onSubscribe(bVar);
        this.b.subscribe(bVar.b);
    }
}
