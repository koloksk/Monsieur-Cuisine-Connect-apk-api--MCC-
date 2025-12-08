package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableCache<T> extends zk<T, T> implements FlowableSubscriber<T> {
    public static final a[] k = new a[0];
    public static final a[] l = new a[0];
    public final AtomicBoolean b;
    public final int c;
    public final AtomicReference<a<T>[]> d;
    public volatile long e;
    public final b<T> f;
    public b<T> g;
    public int h;
    public Throwable i;
    public volatile boolean j;

    public static final class a<T> extends AtomicInteger implements Subscription {
        public static final long serialVersionUID = 6770240836423125754L;
        public final Subscriber<? super T> a;
        public final FlowableCache<T> b;
        public final AtomicLong c = new AtomicLong();
        public b<T> d;
        public int e;
        public long f;

        public a(Subscriber<? super T> subscriber, FlowableCache<T> flowableCache) {
            this.a = subscriber;
            this.b = flowableCache;
            this.d = flowableCache.f;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            a<T>[] aVarArr;
            a<T>[] aVarArr2;
            if (this.c.getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                FlowableCache<T> flowableCache = this.b;
                do {
                    aVarArr = flowableCache.d.get();
                    int length = aVarArr.length;
                    if (length == 0) {
                        return;
                    }
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            i = -1;
                            break;
                        } else if (aVarArr[i] == this) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (i < 0) {
                        return;
                    }
                    if (length == 1) {
                        aVarArr2 = FlowableCache.k;
                    } else {
                        a<T>[] aVarArr3 = new a[length - 1];
                        System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                        System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                        aVarArr2 = aVarArr3;
                    }
                } while (!flowableCache.d.compareAndSet(aVarArr, aVarArr2));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this.c, j);
                this.b.a(this);
            }
        }
    }

    public static final class b<T> {
        public final T[] a;
        public volatile b<T> b;

        public b(int i) {
            this.a = (T[]) new Object[i];
        }
    }

    public FlowableCache(Flowable<T> flowable, int i) {
        super(flowable);
        this.c = i;
        this.b = new AtomicBoolean();
        b<T> bVar = new b<>(i);
        this.f = bVar;
        this.g = bVar;
        this.d = new AtomicReference<>(k);
    }

    public void a(a<T> aVar) {
        if (aVar.getAndIncrement() != 0) {
            return;
        }
        long j = aVar.f;
        int i = aVar.e;
        b<T> bVar = aVar.d;
        AtomicLong atomicLong = aVar.c;
        Subscriber<? super T> subscriber = aVar.a;
        int i2 = this.c;
        int iAddAndGet = 1;
        while (true) {
            boolean z = this.j;
            boolean z2 = this.e == j;
            if (z && z2) {
                aVar.d = null;
                Throwable th = this.i;
                if (th != null) {
                    subscriber.onError(th);
                    return;
                } else {
                    subscriber.onComplete();
                    return;
                }
            }
            if (!z2) {
                long j2 = atomicLong.get();
                if (j2 == Long.MIN_VALUE) {
                    aVar.d = null;
                    return;
                } else if (j2 != j) {
                    if (i == i2) {
                        bVar = bVar.b;
                        i = 0;
                    }
                    subscriber.onNext(bVar.a[i]);
                    i++;
                    j++;
                }
            }
            aVar.f = j;
            aVar.e = i;
            aVar.d = bVar;
            iAddAndGet = aVar.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        this.j = true;
        for (a<T> aVar : this.d.getAndSet(l)) {
            a(aVar);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.j) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.i = th;
        this.j = true;
        for (a<T> aVar : this.d.getAndSet(l)) {
            a(aVar);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        int i = this.h;
        if (i == this.c) {
            b<T> bVar = new b<>(i);
            bVar.a[0] = t;
            this.h = 1;
            this.g.b = bVar;
            this.g = bVar;
        } else {
            this.g.a[i] = t;
            this.h = i + 1;
        }
        this.e++;
        for (a<T> aVar : this.d.get()) {
            a(aVar);
        }
    }

    @Override // io.reactivex.FlowableSubscriber
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        a<T> aVar = new a<>(subscriber, this);
        subscriber.onSubscribe(aVar);
        do {
            aVarArr = this.d.get();
            if (aVarArr == l) {
                break;
            }
            int length = aVarArr.length;
            aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
        } while (!this.d.compareAndSet(aVarArr, aVarArr2));
        if (this.b.get() || !this.b.compareAndSet(false, true)) {
            a(aVar);
        } else {
            this.source.subscribe((FlowableSubscriber) this);
        }
    }
}
