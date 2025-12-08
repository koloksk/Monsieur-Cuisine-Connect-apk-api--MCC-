package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableTakeLastTimed<T> extends zk<T, T> {
    public final long b;
    public final long c;
    public final TimeUnit d;
    public final Scheduler e;
    public final int f;
    public final boolean g;

    public FlowableTakeLastTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = timeUnit;
        this.e = scheduler;
        this.f = i;
        this.g = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d, this.e, this.f, this.g));
    }

    public static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = -5677354903406201275L;
        public final Subscriber<? super T> a;
        public final long b;
        public final long c;
        public final TimeUnit d;
        public final Scheduler e;
        public final SpscLinkedArrayQueue<Object> f;
        public final boolean g;
        public Subscription h;
        public final AtomicLong i = new AtomicLong();
        public volatile boolean j;
        public volatile boolean k;
        public Throwable l;

        public a(Subscriber<? super T> subscriber, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
            this.a = subscriber;
            this.b = j;
            this.c = j2;
            this.d = timeUnit;
            this.e = scheduler;
            this.f = new SpscLinkedArrayQueue<>(i);
            this.g = z;
        }

        public void a(long j, SpscLinkedArrayQueue<Object> spscLinkedArrayQueue) {
            long j2 = this.c;
            long j3 = this.b;
            boolean z = j3 == Long.MAX_VALUE;
            while (!spscLinkedArrayQueue.isEmpty()) {
                if (((Long) spscLinkedArrayQueue.peek()).longValue() >= j - j2 && (z || (spscLinkedArrayQueue.size() >> 1) <= j3)) {
                    return;
                }
                spscLinkedArrayQueue.poll();
                spscLinkedArrayQueue.poll();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.j) {
                return;
            }
            this.j = true;
            this.h.cancel();
            if (getAndIncrement() == 0) {
                this.f.clear();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            a(this.e.now(this.d), this.f);
            this.k = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.g) {
                a(this.e.now(this.d), this.f);
            }
            this.l = th;
            this.k = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.f;
            long jNow = this.e.now(this.d);
            spscLinkedArrayQueue.offer(Long.valueOf(jNow), t);
            a(jNow, spscLinkedArrayQueue);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.h, subscription)) {
                this.h = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.i, j);
                a();
            }
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = this.a;
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.f;
            boolean z = this.g;
            int iAddAndGet = 1;
            do {
                if (this.k) {
                    if (a(spscLinkedArrayQueue.isEmpty(), subscriber, z)) {
                        return;
                    }
                    long j = this.i.get();
                    long j2 = 0;
                    while (true) {
                        if (a(spscLinkedArrayQueue.peek() == null, subscriber, z)) {
                            return;
                        }
                        if (j != j2) {
                            spscLinkedArrayQueue.poll();
                            subscriber.onNext(spscLinkedArrayQueue.poll());
                            j2++;
                        } else if (j2 != 0) {
                            BackpressureHelper.produced(this.i, j2);
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        public boolean a(boolean z, Subscriber<? super T> subscriber, boolean z2) {
            if (this.j) {
                this.f.clear();
                return true;
            }
            if (z2) {
                if (!z) {
                    return false;
                }
                Throwable th = this.l;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
            Throwable th2 = this.l;
            if (th2 != null) {
                this.f.clear();
                subscriber.onError(th2);
                return true;
            }
            if (!z) {
                return false;
            }
            subscriber.onComplete();
            return true;
        }
    }
}
