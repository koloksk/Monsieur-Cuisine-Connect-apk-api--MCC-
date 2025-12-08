package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableLimit<T> extends zk<T, T> {
    public final long b;

    public static final class a<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = 2288246011222124525L;
        public final Subscriber<? super T> a;
        public long b;
        public Subscription c;

        public a(Subscriber<? super T> subscriber, long j) {
            this.a = subscriber;
            this.b = j;
            lazySet(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.c.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.b > 0) {
                this.b = 0L;
                this.a.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.b <= 0) {
                RxJavaPlugins.onError(th);
            } else {
                this.b = 0L;
                this.a.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.b;
            if (j > 0) {
                long j2 = j - 1;
                this.b = j2;
                this.a.onNext(t);
                if (j2 == 0) {
                    this.c.cancel();
                    this.a.onComplete();
                }
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                if (this.b == 0) {
                    subscription.cancel();
                    EmptySubscription.complete(this.a);
                } else {
                    this.c = subscription;
                    this.a.onSubscribe(this);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            long j2;
            long j3;
            if (SubscriptionHelper.validate(j)) {
                do {
                    j2 = get();
                    if (j2 == 0) {
                        return;
                    } else {
                        j3 = j2 <= j ? j2 : j;
                    }
                } while (!compareAndSet(j2, j2 - j3));
                this.c.request(j3);
            }
        }
    }

    public FlowableLimit(Flowable<T> flowable, long j) {
        super(flowable);
        this.b = j;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b));
    }
}
