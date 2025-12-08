package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowablePublishMulticast<T, R> extends zk<T, R> {
    public final Function<? super Flowable<T>, ? extends Publisher<? extends R>> b;
    public final int c;
    public final boolean d;

    public static final class b<T> extends AtomicLong implements Subscription {
        public static final long serialVersionUID = 8664815189257569791L;
        public final Subscriber<? super T> a;
        public final a<T> b;
        public long c;

        public b(Subscriber<? super T> subscriber, a<T> aVar) {
            this.a = subscriber;
            this.b = aVar;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.b.a(this);
                this.b.b();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
                this.b.b();
            }
        }
    }

    public static final class c<R> implements FlowableSubscriber<R>, Subscription {
        public final Subscriber<? super R> a;
        public final a<?> b;
        public Subscription c;

        public c(Subscriber<? super R> subscriber, a<?> aVar) {
            this.a = subscriber;
            this.b = aVar;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.c.cancel();
            this.b.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
            this.b.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.onError(th);
            this.b.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.a.onNext(r);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.c.request(j);
        }
    }

    public FlowablePublishMulticast(Flowable<T> flowable, Function<? super Flowable<T>, ? extends Publisher<? extends R>> function, int i, boolean z) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        a aVar = new a(this.c, this.d);
        try {
            ((Publisher) ObjectHelper.requireNonNull(this.b.apply(aVar), "selector returned a null Publisher")).subscribe(new c(subscriber, aVar));
            this.source.subscribe((FlowableSubscriber) aVar);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }

    public static final class a<T> extends Flowable<T> implements FlowableSubscriber<T>, Disposable {
        public static final b[] m = new b[0];
        public static final b[] n = new b[0];
        public final int d;
        public final int e;
        public final boolean f;
        public volatile SimpleQueue<T> h;
        public int i;
        public volatile boolean j;
        public Throwable k;
        public int l;
        public final AtomicInteger b = new AtomicInteger();
        public final AtomicReference<Subscription> g = new AtomicReference<>();
        public final AtomicReference<b<T>[]> c = new AtomicReference<>(m);

        public a(int i, boolean z) {
            this.d = i;
            this.e = i - (i >> 2);
            this.f = z;
        }

        public void a(b<T> bVar) {
            b<T>[] bVarArr;
            b<T>[] bVarArr2;
            do {
                bVarArr = this.c.get();
                int length = bVarArr.length;
                if (length == 0) {
                    return;
                }
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (bVarArr[i2] == bVar) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    bVarArr2 = m;
                } else {
                    b<T>[] bVarArr3 = new b[length - 1];
                    System.arraycopy(bVarArr, 0, bVarArr3, 0, i);
                    System.arraycopy(bVarArr, i + 1, bVarArr3, i, (length - i) - 1);
                    bVarArr2 = bVarArr3;
                }
            } while (!this.c.compareAndSet(bVarArr, bVarArr2));
        }

        public void b() {
            AtomicReference<b<T>[]> atomicReference;
            Throwable th;
            Throwable th2;
            if (this.b.getAndIncrement() != 0) {
                return;
            }
            SimpleQueue<T> simpleQueue = this.h;
            int i = this.l;
            int i2 = this.e;
            boolean z = this.i != 1;
            AtomicReference<b<T>[]> atomicReference2 = this.c;
            b<T>[] bVarArr = atomicReference2.get();
            int iAddAndGet = 1;
            while (true) {
                int length = bVarArr.length;
                if (simpleQueue == null || length == 0) {
                    atomicReference = atomicReference2;
                } else {
                    int length2 = bVarArr.length;
                    long j = Long.MAX_VALUE;
                    long j2 = Long.MAX_VALUE;
                    int i3 = 0;
                    while (i3 < length2) {
                        b<T> bVar = bVarArr[i3];
                        AtomicReference<b<T>[]> atomicReference3 = atomicReference2;
                        long j3 = bVar.get() - bVar.c;
                        if (j3 == Long.MIN_VALUE) {
                            length--;
                        } else if (j2 > j3) {
                            j2 = j3;
                        }
                        i3++;
                        atomicReference2 = atomicReference3;
                    }
                    atomicReference = atomicReference2;
                    long j4 = 0;
                    if (length == 0) {
                        j2 = 0;
                    }
                    while (j2 != j4) {
                        if (isDisposed()) {
                            simpleQueue.clear();
                            return;
                        }
                        boolean z2 = this.j;
                        if (z2 && !this.f && (th2 = this.k) != null) {
                            a(th2);
                            return;
                        }
                        try {
                            T tPoll = simpleQueue.poll();
                            boolean z3 = tPoll == null;
                            if (z2 && z3) {
                                Throwable th3 = this.k;
                                if (th3 != null) {
                                    a(th3);
                                    return;
                                } else {
                                    a();
                                    return;
                                }
                            }
                            if (z3) {
                                break;
                            }
                            int length3 = bVarArr.length;
                            int i4 = 0;
                            boolean z4 = false;
                            while (i4 < length3) {
                                b<T> bVar2 = bVarArr[i4];
                                long j5 = bVar2.get();
                                if (j5 != Long.MIN_VALUE) {
                                    if (j5 != j) {
                                        bVar2.c++;
                                    }
                                    bVar2.a.onNext(tPoll);
                                } else {
                                    z4 = true;
                                }
                                i4++;
                                j = Long.MAX_VALUE;
                            }
                            j2--;
                            if (z && (i = i + 1) == i2) {
                                this.g.get().request(i2);
                                i = 0;
                            }
                            b<T>[] bVarArr2 = atomicReference.get();
                            if (z4 || bVarArr2 != bVarArr) {
                                bVarArr = bVarArr2;
                                break;
                            } else {
                                j4 = 0;
                                j = Long.MAX_VALUE;
                            }
                        } catch (Throwable th4) {
                            Exceptions.throwIfFatal(th4);
                            SubscriptionHelper.cancel(this.g);
                            a(th4);
                            return;
                        }
                    }
                    if (j2 == j4) {
                        if (isDisposed()) {
                            simpleQueue.clear();
                            return;
                        }
                        boolean z5 = this.j;
                        if (z5 && !this.f && (th = this.k) != null) {
                            a(th);
                            return;
                        }
                        if (z5 && simpleQueue.isEmpty()) {
                            Throwable th5 = this.k;
                            if (th5 != null) {
                                a(th5);
                                return;
                            } else {
                                a();
                                return;
                            }
                        }
                    }
                }
                this.l = i;
                iAddAndGet = this.b.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
                if (simpleQueue == null) {
                    simpleQueue = this.h;
                }
                bVarArr = atomicReference.get();
                atomicReference2 = atomicReference;
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SimpleQueue<T> simpleQueue;
            SubscriptionHelper.cancel(this.g);
            if (this.b.getAndIncrement() != 0 || (simpleQueue = this.h) == null) {
                return;
            }
            simpleQueue.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.g.get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.j) {
                return;
            }
            this.j = true;
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.j) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.k = th;
            this.j = true;
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.j) {
                return;
            }
            if (this.i != 0 || this.h.offer(t)) {
                b();
            } else {
                this.g.get().cancel();
                onError(new MissingBackpressureException());
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.g, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.i = iRequestFusion;
                        this.h = queueSubscription;
                        this.j = true;
                        b();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.i = iRequestFusion;
                        this.h = queueSubscription;
                        QueueDrainHelper.request(subscription, this.d);
                        return;
                    }
                }
                this.h = QueueDrainHelper.createQueue(this.d);
                QueueDrainHelper.request(subscription, this.d);
            }
        }

        @Override // io.reactivex.Flowable
        public void subscribeActual(Subscriber<? super T> subscriber) {
            boolean z;
            b<T> bVar = new b<>(subscriber, this);
            subscriber.onSubscribe(bVar);
            while (true) {
                b<T>[] bVarArr = this.c.get();
                if (bVarArr == n) {
                    z = false;
                    break;
                }
                int length = bVarArr.length;
                b<T>[] bVarArr2 = new b[length + 1];
                System.arraycopy(bVarArr, 0, bVarArr2, 0, length);
                bVarArr2[length] = bVar;
                if (this.c.compareAndSet(bVarArr, bVarArr2)) {
                    z = true;
                    break;
                }
            }
            if (z) {
                if (bVar.get() == Long.MIN_VALUE) {
                    a(bVar);
                    return;
                } else {
                    b();
                    return;
                }
            }
            Throwable th = this.k;
            if (th != null) {
                subscriber.onError(th);
            } else {
                subscriber.onComplete();
            }
        }

        public void a(Throwable th) {
            for (b<T> bVar : this.c.getAndSet(n)) {
                if (bVar.get() != Long.MIN_VALUE) {
                    bVar.a.onError(th);
                }
            }
        }

        public void a() {
            for (b<T> bVar : this.c.getAndSet(n)) {
                if (bVar.get() != Long.MIN_VALUE) {
                    bVar.a.onComplete();
                }
            }
        }
    }
}
