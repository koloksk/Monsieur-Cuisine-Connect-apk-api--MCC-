package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableScanSeed<T, R> extends zk<T, R> {
    public final BiFunction<R, ? super T, R> b;
    public final Callable<R> c;

    public static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = -1776795561228106469L;
        public final Subscriber<? super R> a;
        public final BiFunction<R, ? super T, R> b;
        public final SimplePlainQueue<R> c;
        public final AtomicLong d;
        public final int e;
        public final int f;
        public volatile boolean g;
        public volatile boolean h;
        public Throwable i;
        public Subscription j;
        public R k;
        public int l;

        public a(Subscriber<? super R> subscriber, BiFunction<R, ? super T, R> biFunction, R r, int i) {
            this.a = subscriber;
            this.b = biFunction;
            this.k = r;
            this.e = i;
            this.f = i - (i >> 2);
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(i);
            this.c = spscArrayQueue;
            spscArrayQueue.offer(r);
            this.d = new AtomicLong();
        }

        public void a() {
            Throwable th;
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super R> subscriber = this.a;
            SimplePlainQueue<R> simplePlainQueue = this.c;
            int i = this.f;
            int i2 = this.l;
            int iAddAndGet = 1;
            do {
                long j = this.d.get();
                long j2 = 0;
                while (j2 != j) {
                    if (this.g) {
                        simplePlainQueue.clear();
                        return;
                    }
                    boolean z = this.h;
                    if (z && (th = this.i) != null) {
                        simplePlainQueue.clear();
                        subscriber.onError(th);
                        return;
                    }
                    R rPoll = simplePlainQueue.poll();
                    boolean z2 = rPoll == null;
                    if (z && z2) {
                        subscriber.onComplete();
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(rPoll);
                    j2++;
                    i2++;
                    if (i2 == i) {
                        this.j.request(i);
                        i2 = 0;
                    }
                }
                if (j2 == j && this.h) {
                    Throwable th2 = this.i;
                    if (th2 != null) {
                        simplePlainQueue.clear();
                        subscriber.onError(th2);
                        return;
                    } else if (simplePlainQueue.isEmpty()) {
                        subscriber.onComplete();
                        return;
                    }
                }
                if (j2 != 0) {
                    BackpressureHelper.produced(this.d, j2);
                }
                this.l = i2;
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.g = true;
            this.j.cancel();
            if (getAndIncrement() == 0) {
                this.c.clear();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.h) {
                return;
            }
            this.h = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.i = th;
            this.h = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.h) {
                return;
            }
            try {
                R r = (R) ObjectHelper.requireNonNull(this.b.apply(this.k, t), "The accumulator returned a null value");
                this.k = r;
                this.c.offer(r);
                a();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.j.cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.j, subscription)) {
                this.j = subscription;
                this.a.onSubscribe(this);
                subscription.request(this.e - 1);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.d, j);
                a();
            }
        }
    }

    public FlowableScanSeed(Flowable<T> flowable, Callable<R> callable, BiFunction<R, ? super T, R> biFunction) {
        super(flowable);
        this.b = biFunction;
        this.c = callable;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, ObjectHelper.requireNonNull(this.c.call(), "The seed supplied is null"), Flowable.bufferSize()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
