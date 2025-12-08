package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class ParallelFromPublisher<T> extends ParallelFlowable<T> {
    public final Publisher<? extends T> a;
    public final int b;
    public final int c;

    public static final class a<T> extends AtomicInteger implements FlowableSubscriber<T> {
        public static final long serialVersionUID = -4470634016609963609L;
        public final Subscriber<? super T>[] a;
        public final AtomicLongArray b;
        public final long[] c;
        public final int d;
        public final int e;
        public Subscription f;
        public SimpleQueue<T> g;
        public Throwable h;
        public volatile boolean i;
        public int j;
        public volatile boolean k;
        public final AtomicInteger l = new AtomicInteger();
        public int m;
        public int n;

        /* renamed from: io.reactivex.internal.operators.parallel.ParallelFromPublisher$a$a, reason: collision with other inner class name */
        public final class C0070a implements Subscription {
            public final int a;
            public final int b;

            public C0070a(int i, int i2) {
                this.a = i;
                this.b = i2;
            }

            @Override // org.reactivestreams.Subscription
            public void cancel() {
                if (a.this.b.compareAndSet(this.a + this.b, 0L, 1L)) {
                    a aVar = a.this;
                    int i = this.b;
                    if (aVar.b.decrementAndGet(i + i) == 0) {
                        aVar.k = true;
                        aVar.f.cancel();
                        if (aVar.getAndIncrement() == 0) {
                            aVar.g.clear();
                        }
                    }
                }
            }

            @Override // org.reactivestreams.Subscription
            public void request(long j) {
                long j2;
                if (SubscriptionHelper.validate(j)) {
                    AtomicLongArray atomicLongArray = a.this.b;
                    do {
                        j2 = atomicLongArray.get(this.a);
                        if (j2 == Long.MAX_VALUE) {
                            return;
                        }
                    } while (!atomicLongArray.compareAndSet(this.a, j2, BackpressureHelper.addCap(j2, j)));
                    if (a.this.l.get() == this.b) {
                        a.this.a();
                    }
                }
            }
        }

        public a(Subscriber<? super T>[] subscriberArr, int i) {
            this.a = subscriberArr;
            this.d = i;
            this.e = i - (i >> 2);
            int length = subscriberArr.length;
            int i2 = length + length;
            AtomicLongArray atomicLongArray = new AtomicLongArray(i2 + 1);
            this.b = atomicLongArray;
            atomicLongArray.lazySet(i2, length);
            this.c = new long[length];
        }

        /* JADX WARN: Removed duplicated region for block: B:84:0x013c  */
        /* JADX WARN: Removed duplicated region for block: B:88:0x014a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a() {
            /*
                Method dump skipped, instructions count: 341
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelFromPublisher.a.a():void");
        }

        public void b() {
            Subscriber<? super T>[] subscriberArr = this.a;
            int length = subscriberArr.length;
            int i = 0;
            while (i < length && !this.k) {
                int i2 = i + 1;
                this.l.lazySet(i2);
                subscriberArr[i].onSubscribe(new C0070a(i, length));
                i = i2;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.i = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.h = th;
            this.i = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.n != 0 || this.g.offer(t)) {
                a();
                return;
            }
            this.f.cancel();
            this.h = new MissingBackpressureException("Queue is full?");
            this.i = true;
            a();
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.n = iRequestFusion;
                        this.g = queueSubscription;
                        this.i = true;
                        b();
                        a();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.n = iRequestFusion;
                        this.g = queueSubscription;
                        b();
                        subscription.request(this.d);
                        return;
                    }
                }
                this.g = new SpscArrayQueue(this.d);
                b();
                subscription.request(this.d);
            }
        }
    }

    public ParallelFromPublisher(Publisher<? extends T> publisher, int i, int i2) {
        this.a = publisher;
        this.b = i;
        this.c = i2;
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public int parallelism() {
        return this.b;
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            this.a.subscribe(new a(subscriberArr, this.c));
        }
    }
}
