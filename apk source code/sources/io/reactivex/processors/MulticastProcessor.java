package io.reactivex.processors;

import io.reactivex.Flowable;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@SchedulerSupport(SchedulerSupport.NONE)
@BackpressureSupport(BackpressureKind.FULL)
/* loaded from: classes.dex */
public final class MulticastProcessor<T> extends FlowableProcessor<T> {
    public static final a[] n = new a[0];
    public static final a[] o = new a[0];
    public final AtomicInteger b;
    public final AtomicReference<Subscription> c;
    public final AtomicReference<a<T>[]> d;
    public final AtomicBoolean e;
    public final int f;
    public final int g;
    public final boolean h;
    public volatile SimpleQueue<T> i;
    public volatile boolean j;
    public volatile Throwable k;
    public int l;
    public int m;

    public static final class a<T> extends AtomicLong implements Subscription {
        public static final long serialVersionUID = -363282618957264509L;
        public final Subscriber<? super T> a;
        public final MulticastProcessor<T> b;
        public long c;

        public a(Subscriber<? super T> subscriber, MulticastProcessor<T> multicastProcessor) {
            this.a = subscriber;
            this.b = multicastProcessor;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.b.a(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            long j2;
            long j3;
            if (SubscriptionHelper.validate(j)) {
                do {
                    j2 = get();
                    if (j2 == Long.MIN_VALUE) {
                        return;
                    }
                    if (j2 == Long.MAX_VALUE) {
                        return;
                    } else {
                        j3 = j2 + j;
                    }
                } while (!compareAndSet(j2, j3 >= 0 ? j3 : Long.MAX_VALUE));
                this.b.a();
            }
        }
    }

    public MulticastProcessor(int i, boolean z) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        this.f = i;
        this.g = i - (i >> 2);
        this.b = new AtomicInteger();
        this.d = new AtomicReference<>(n);
        this.c = new AtomicReference<>();
        this.h = z;
        this.e = new AtomicBoolean();
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create() {
        return new MulticastProcessor<>(Flowable.bufferSize(), false);
    }

    public void a(a<T> aVar) {
        while (true) {
            a<T>[] aVarArr = this.d.get();
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
            if (length != 1) {
                a<T>[] aVarArr2 = new a[length - 1];
                System.arraycopy(aVarArr, 0, aVarArr2, 0, i);
                System.arraycopy(aVarArr, i + 1, aVarArr2, i, (length - i) - 1);
                if (this.d.compareAndSet(aVarArr, aVarArr2)) {
                    return;
                }
            } else if (this.h) {
                if (this.d.compareAndSet(aVarArr, o)) {
                    SubscriptionHelper.cancel(this.c);
                    this.e.set(true);
                    return;
                }
            } else if (this.d.compareAndSet(aVarArr, n)) {
                return;
            }
        }
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public Throwable getThrowable() {
        if (this.e.get()) {
            return this.k;
        }
        return null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasComplete() {
        return this.e.get() && this.k == null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.d.get().length != 0;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasThrowable() {
        return this.e.get() && this.k != null;
    }

    public boolean offer(T t) {
        if (this.e.get()) {
            return false;
        }
        ObjectHelper.requireNonNull(t, "offer called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.m != 0 || !this.i.offer(t)) {
            return false;
        }
        a();
        return true;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.e.compareAndSet(false, true)) {
            this.j = true;
            a();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.e.compareAndSet(false, true)) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.k = th;
        this.j = true;
        a();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.e.get()) {
            return;
        }
        if (this.m == 0) {
            ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
            if (!this.i.offer(t)) {
                SubscriptionHelper.cancel(this.c);
                onError(new MissingBackpressureException());
                return;
            }
        }
        a();
    }

    @Override // org.reactivestreams.Subscriber, io.reactivex.FlowableSubscriber
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this.c, subscription)) {
            if (subscription instanceof QueueSubscription) {
                QueueSubscription queueSubscription = (QueueSubscription) subscription;
                int iRequestFusion = queueSubscription.requestFusion(3);
                if (iRequestFusion == 1) {
                    this.m = iRequestFusion;
                    this.i = queueSubscription;
                    this.j = true;
                    a();
                    return;
                }
                if (iRequestFusion == 2) {
                    this.m = iRequestFusion;
                    this.i = queueSubscription;
                    subscription.request(this.f);
                    return;
                }
            }
            this.i = new SpscArrayQueue(this.f);
            subscription.request(this.f);
        }
    }

    public void start() {
        if (SubscriptionHelper.setOnce(this.c, EmptySubscription.INSTANCE)) {
            this.i = new SpscArrayQueue(this.f);
        }
    }

    public void startUnbounded() {
        if (SubscriptionHelper.setOnce(this.c, EmptySubscription.INSTANCE)) {
            this.i = new SpscLinkedArrayQueue(this.f);
        }
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        boolean z;
        Throwable th;
        a<T> aVar = new a<>(subscriber, this);
        subscriber.onSubscribe(aVar);
        while (true) {
            a<T>[] aVarArr = this.d.get();
            z = false;
            if (aVarArr == o) {
                break;
            }
            int length = aVarArr.length;
            a<T>[] aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
            if (this.d.compareAndSet(aVarArr, aVarArr2)) {
                z = true;
                break;
            }
        }
        if (z) {
            if (aVar.get() == Long.MIN_VALUE) {
                a(aVar);
                return;
            } else {
                a();
                return;
            }
        }
        if ((this.e.get() || !this.h) && (th = this.k) != null) {
            subscriber.onError(th);
        } else {
            subscriber.onComplete();
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(boolean z) {
        return new MulticastProcessor<>(Flowable.bufferSize(), z);
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(int i) {
        return new MulticastProcessor<>(i, false);
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(int i, boolean z) {
        return new MulticastProcessor<>(i, z);
    }

    public void a() {
        T tPoll;
        if (this.b.getAndIncrement() != 0) {
            return;
        }
        AtomicReference<a<T>[]> atomicReference = this.d;
        int i = this.l;
        int i2 = this.g;
        int i3 = this.m;
        boolean z = true;
        int iAddAndGet = 1;
        while (true) {
            SimpleQueue<T> simpleQueue = this.i;
            if (simpleQueue != null) {
                a<T>[] aVarArr = atomicReference.get();
                if (aVarArr.length != 0) {
                    int length = aVarArr.length;
                    long j = -1;
                    long jMin = -1;
                    int i4 = 0;
                    while (i4 < length) {
                        a<T> aVar = aVarArr[i4];
                        long j2 = aVar.get();
                        if (j2 >= 0) {
                            if (jMin == j) {
                                jMin = j2 - aVar.c;
                            } else {
                                jMin = Math.min(jMin, j2 - aVar.c);
                            }
                        }
                        i4++;
                        j = -1;
                    }
                    int i5 = i;
                    while (true) {
                        long j3 = Long.MIN_VALUE;
                        if (jMin <= 0) {
                            break;
                        }
                        a<T>[] aVarArr2 = atomicReference.get();
                        if (aVarArr2 == o) {
                            simpleQueue.clear();
                            return;
                        }
                        if (aVarArr != aVarArr2) {
                            break;
                        }
                        boolean z2 = this.j;
                        try {
                            tPoll = simpleQueue.poll();
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            SubscriptionHelper.cancel(this.c);
                            this.k = th;
                            this.j = z;
                            tPoll = null;
                            z2 = z;
                        }
                        boolean z3 = tPoll == null ? z : false;
                        if (z2 && z3) {
                            Throwable th2 = this.k;
                            if (th2 != null) {
                                for (a<T> aVar2 : atomicReference.getAndSet(o)) {
                                    if (aVar2.get() != Long.MIN_VALUE) {
                                        aVar2.a.onError(th2);
                                    }
                                }
                                return;
                            }
                            for (a<T> aVar3 : atomicReference.getAndSet(o)) {
                                if (aVar3.get() != Long.MIN_VALUE) {
                                    aVar3.a.onComplete();
                                }
                            }
                            return;
                        }
                        if (z3) {
                            break;
                        }
                        int length2 = aVarArr.length;
                        int i6 = 0;
                        while (i6 < length2) {
                            a<T> aVar4 = aVarArr[i6];
                            if (aVar4.get() != j3) {
                                aVar4.c++;
                                aVar4.a.onNext(tPoll);
                            }
                            i6++;
                            j3 = Long.MIN_VALUE;
                        }
                        jMin--;
                        z = true;
                        if (i3 != 1 && (i5 = i5 + 1) == i2) {
                            this.c.get().request(i2);
                            i5 = 0;
                        }
                    }
                    if (jMin == 0) {
                        a<T>[] aVarArr3 = atomicReference.get();
                        if (aVarArr3 == o) {
                            simpleQueue.clear();
                            return;
                        }
                        if (aVarArr != aVarArr3) {
                            i = i5;
                        } else if (this.j && simpleQueue.isEmpty()) {
                            Throwable th3 = this.k;
                            if (th3 != null) {
                                for (a<T> aVar5 : atomicReference.getAndSet(o)) {
                                    if (aVar5.get() != Long.MIN_VALUE) {
                                        aVar5.a.onError(th3);
                                    }
                                }
                                return;
                            }
                            for (a<T> aVar6 : atomicReference.getAndSet(o)) {
                                if (aVar6.get() != Long.MIN_VALUE) {
                                    aVar6.a.onComplete();
                                }
                            }
                            return;
                        }
                    }
                    i = i5;
                }
            }
            this.l = i;
            iAddAndGet = this.b.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            }
        }
    }
}
