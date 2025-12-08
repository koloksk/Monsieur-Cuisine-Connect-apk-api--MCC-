package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableRetryBiPredicate<T> extends zk<T, T> {
    public final BiPredicate<? super Integer, ? super Throwable> b;

    public static final class a<T> extends AtomicInteger implements FlowableSubscriber<T> {
        public static final long serialVersionUID = -7098360935104053232L;
        public final Subscriber<? super T> a;
        public final SubscriptionArbiter b;
        public final Publisher<? extends T> c;
        public final BiPredicate<? super Integer, ? super Throwable> d;
        public int e;
        public long f;

        public a(Subscriber<? super T> subscriber, BiPredicate<? super Integer, ? super Throwable> biPredicate, SubscriptionArbiter subscriptionArbiter, Publisher<? extends T> publisher) {
            this.a = subscriber;
            this.b = subscriptionArbiter;
            this.c = publisher;
            this.d = biPredicate;
        }

        public void a() {
            if (getAndIncrement() == 0) {
                int iAddAndGet = 1;
                while (!this.b.isCancelled()) {
                    long j = this.f;
                    if (j != 0) {
                        this.f = 0L;
                        this.b.produced(j);
                    }
                    this.c.subscribe(this);
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            try {
                BiPredicate<? super Integer, ? super Throwable> biPredicate = this.d;
                int i = this.e + 1;
                this.e = i;
                if (biPredicate.test(Integer.valueOf(i), th)) {
                    a();
                } else {
                    this.a.onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.a.onError(new CompositeException(th, th2));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.f++;
            this.a.onNext(t);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            this.b.setSubscription(subscription);
        }
    }

    public FlowableRetryBiPredicate(Flowable<T> flowable, BiPredicate<? super Integer, ? super Throwable> biPredicate) {
        super(flowable);
        this.b = biPredicate;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SubscriptionArbiter subscriptionArbiter = new SubscriptionArbiter(false);
        subscriber.onSubscribe(subscriptionArbiter);
        new a(subscriber, this.b, subscriptionArbiter, this.source).a();
    }
}
