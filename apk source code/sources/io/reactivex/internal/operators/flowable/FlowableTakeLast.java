package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableTakeLast<T> extends zk<T, T> {
    public final int b;

    public static final class a<T> extends ArrayDeque<T> implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = 7240042530241604978L;
        public final Subscriber<? super T> a;
        public final int b;
        public Subscription c;
        public volatile boolean d;
        public volatile boolean e;
        public final AtomicLong f = new AtomicLong();
        public final AtomicInteger g = new AtomicInteger();

        public a(Subscriber<? super T> subscriber, int i) {
            this.a = subscriber;
            this.b = i;
        }

        public void a() {
            if (this.g.getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = this.a;
                long jAddAndGet = this.f.get();
                while (!this.e) {
                    if (this.d) {
                        long j = 0;
                        while (j != jAddAndGet) {
                            if (this.e) {
                                return;
                            }
                            T tPoll = poll();
                            if (tPoll == null) {
                                subscriber.onComplete();
                                return;
                            } else {
                                subscriber.onNext(tPoll);
                                j++;
                            }
                        }
                        if (j != 0 && jAddAndGet != Long.MAX_VALUE) {
                            jAddAndGet = this.f.addAndGet(-j);
                        }
                    }
                    if (this.g.decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.e = true;
            this.c.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.d = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.b == size()) {
                poll();
            }
            offer(t);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.f, j);
                a();
            }
        }
    }

    public FlowableTakeLast(Flowable<T> flowable, int i) {
        super(flowable);
        this.b = i;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b));
    }
}
