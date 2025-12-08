package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableWindow<T> extends zk<T, Flowable<T>> {
    public final long b;
    public final long c;
    public final int d;

    public static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        public static final long serialVersionUID = -2365647875069161133L;
        public final Subscriber<? super Flowable<T>> a;
        public final long b;
        public final AtomicBoolean c;
        public final int d;
        public long e;
        public Subscription f;
        public UnicastProcessor<T> g;

        public a(Subscriber<? super Flowable<T>> subscriber, long j, int i) {
            super(1);
            this.a = subscriber;
            this.b = j;
            this.c = new AtomicBoolean();
            this.d = i;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.c.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.g;
            if (unicastProcessor != null) {
                this.g = null;
                unicastProcessor.onComplete();
            }
            this.a.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            UnicastProcessor<T> unicastProcessor = this.g;
            if (unicastProcessor != null) {
                this.g = null;
                unicastProcessor.onError(th);
            }
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.e;
            UnicastProcessor<T> unicastProcessorCreate = this.g;
            if (j == 0) {
                getAndIncrement();
                unicastProcessorCreate = UnicastProcessor.create(this.d, this);
                this.g = unicastProcessorCreate;
                this.a.onNext(unicastProcessorCreate);
            }
            long j2 = j + 1;
            unicastProcessorCreate.onNext(t);
            if (j2 != this.b) {
                this.e = j2;
                return;
            }
            this.e = 0L;
            this.g = null;
            unicastProcessorCreate.onComplete();
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                this.f.request(BackpressureHelper.multiplyCap(this.b, j));
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.f.cancel();
            }
        }
    }

    public static final class c<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        public static final long serialVersionUID = -8792836352386833856L;
        public final Subscriber<? super Flowable<T>> a;
        public final long b;
        public final long c;
        public final AtomicBoolean d;
        public final AtomicBoolean e;
        public final int f;
        public long g;
        public Subscription h;
        public UnicastProcessor<T> i;

        public c(Subscriber<? super Flowable<T>> subscriber, long j, long j2, int i) {
            super(1);
            this.a = subscriber;
            this.b = j;
            this.c = j2;
            this.d = new AtomicBoolean();
            this.e = new AtomicBoolean();
            this.f = i;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.d.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.i;
            if (unicastProcessor != null) {
                this.i = null;
                unicastProcessor.onComplete();
            }
            this.a.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            UnicastProcessor<T> unicastProcessor = this.i;
            if (unicastProcessor != null) {
                this.i = null;
                unicastProcessor.onError(th);
            }
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.g;
            UnicastProcessor<T> unicastProcessorCreate = this.i;
            if (j == 0) {
                getAndIncrement();
                unicastProcessorCreate = UnicastProcessor.create(this.f, this);
                this.i = unicastProcessorCreate;
                this.a.onNext(unicastProcessorCreate);
            }
            long j2 = j + 1;
            if (unicastProcessorCreate != null) {
                unicastProcessorCreate.onNext(t);
            }
            if (j2 == this.b) {
                this.i = null;
                unicastProcessorCreate.onComplete();
            }
            if (j2 == this.c) {
                this.g = 0L;
            } else {
                this.g = j2;
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.h, subscription)) {
                this.h = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                if (this.e.get() || !this.e.compareAndSet(false, true)) {
                    this.h.request(BackpressureHelper.multiplyCap(this.c, j));
                } else {
                    this.h.request(BackpressureHelper.addCap(BackpressureHelper.multiplyCap(this.b, j), BackpressureHelper.multiplyCap(this.c - this.b, j - 1)));
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.h.cancel();
            }
        }
    }

    public FlowableWindow(Flowable<T> flowable, long j, long j2, int i) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = i;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        long j = this.c;
        long j2 = this.b;
        if (j == j2) {
            this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.d));
        } else if (j > j2) {
            this.source.subscribe((FlowableSubscriber) new c(subscriber, this.b, this.c, this.d));
        } else {
            this.source.subscribe((FlowableSubscriber) new b(subscriber, this.b, this.c, this.d));
        }
    }

    public static final class b<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        public static final long serialVersionUID = 2428527070996323976L;
        public final Subscriber<? super Flowable<T>> a;
        public final SpscLinkedArrayQueue<UnicastProcessor<T>> b;
        public final long c;
        public final long d;
        public final ArrayDeque<UnicastProcessor<T>> e;
        public final AtomicBoolean f;
        public final AtomicBoolean g;
        public final AtomicLong h;
        public final AtomicInteger i;
        public final int j;
        public long k;
        public long l;
        public Subscription m;
        public volatile boolean n;
        public Throwable o;
        public volatile boolean p;

        public b(Subscriber<? super Flowable<T>> subscriber, long j, long j2, int i) {
            super(1);
            this.a = subscriber;
            this.c = j;
            this.d = j2;
            this.b = new SpscLinkedArrayQueue<>(i);
            this.e = new ArrayDeque<>();
            this.f = new AtomicBoolean();
            this.g = new AtomicBoolean();
            this.h = new AtomicLong();
            this.i = new AtomicInteger();
            this.j = i;
        }

        public void a() {
            if (this.i.getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super Flowable<T>> subscriber = this.a;
            SpscLinkedArrayQueue<UnicastProcessor<T>> spscLinkedArrayQueue = this.b;
            int iAddAndGet = 1;
            do {
                long j = this.h.get();
                long j2 = 0;
                while (j2 != j) {
                    boolean z = this.n;
                    UnicastProcessor<T> unicastProcessorPoll = spscLinkedArrayQueue.poll();
                    boolean z2 = unicastProcessorPoll == null;
                    if (a(z, z2, subscriber, spscLinkedArrayQueue)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(unicastProcessorPoll);
                    j2++;
                }
                if (j2 == j && a(this.n, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                    return;
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    this.h.addAndGet(-j2);
                }
                iAddAndGet = this.i.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.p = true;
            if (this.f.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.n) {
                return;
            }
            Iterator<UnicastProcessor<T>> it = this.e.iterator();
            while (it.hasNext()) {
                it.next().onComplete();
            }
            this.e.clear();
            this.n = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.n) {
                RxJavaPlugins.onError(th);
                return;
            }
            Iterator<UnicastProcessor<T>> it = this.e.iterator();
            while (it.hasNext()) {
                it.next().onError(th);
            }
            this.e.clear();
            this.o = th;
            this.n = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.n) {
                return;
            }
            long j = this.k;
            if (j == 0 && !this.p) {
                getAndIncrement();
                UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.j, this);
                this.e.offer(unicastProcessorCreate);
                this.b.offer(unicastProcessorCreate);
                a();
            }
            long j2 = j + 1;
            Iterator<UnicastProcessor<T>> it = this.e.iterator();
            while (it.hasNext()) {
                it.next().onNext(t);
            }
            long j3 = this.l + 1;
            if (j3 == this.c) {
                this.l = j3 - this.d;
                UnicastProcessor<T> unicastProcessorPoll = this.e.poll();
                if (unicastProcessorPoll != null) {
                    unicastProcessorPoll.onComplete();
                }
            } else {
                this.l = j3;
            }
            if (j2 == this.d) {
                this.k = 0L;
            } else {
                this.k = j2;
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.m, subscription)) {
                this.m = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.h, j);
                if (this.g.get() || !this.g.compareAndSet(false, true)) {
                    this.m.request(BackpressureHelper.multiplyCap(this.d, j));
                } else {
                    this.m.request(BackpressureHelper.addCap(this.c, BackpressureHelper.multiplyCap(this.d, j - 1)));
                }
                a();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.m.cancel();
            }
        }

        public boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.p) {
                spscLinkedArrayQueue.clear();
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable th = this.o;
            if (th != null) {
                spscLinkedArrayQueue.clear();
                subscriber.onError(th);
                return true;
            }
            if (!z2) {
                return false;
            }
            subscriber.onComplete();
            return true;
        }
    }
}
