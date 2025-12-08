package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableSkipUntil<T, U> extends zk<T, T> {
    public final Publisher<U> b;

    public static final class a<T> extends AtomicInteger implements ConditionalSubscriber<T>, Subscription {
        public static final long serialVersionUID = -6270983465606289181L;
        public final Subscriber<? super T> a;
        public final AtomicReference<Subscription> b = new AtomicReference<>();
        public final AtomicLong c = new AtomicLong();
        public final a<T>.C0027a d = new C0027a();
        public final AtomicThrowable e = new AtomicThrowable();
        public volatile boolean f;

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableSkipUntil$a$a, reason: collision with other inner class name */
        public final class C0027a extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            public static final long serialVersionUID = -5592042965931999169L;

            public C0027a() {
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                a.this.f = true;
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                SubscriptionHelper.cancel(a.this.b);
                a aVar = a.this;
                HalfSerializer.onError(aVar.a, th, aVar, aVar.e);
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Object obj) {
                a.this.f = true;
                get().cancel();
            }

            @Override // io.reactivex.FlowableSubscriber
            public void onSubscribe(Subscription subscription) {
                SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
            }
        }

        public a(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.b);
            SubscriptionHelper.cancel(this.d);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SubscriptionHelper.cancel(this.d);
            HalfSerializer.onComplete(this.a, this, this.e);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.d);
            HalfSerializer.onError(this.a, th, this, this.e);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.b.get().request(1L);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.b, this.c, subscription);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.b, this.c, j);
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (!this.f) {
                return false;
            }
            HalfSerializer.onNext(this.a, t, this, this.e);
            return true;
        }
    }

    public FlowableSkipUntil(Flowable<T> flowable, Publisher<U> publisher) {
        super(flowable);
        this.b = publisher;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber);
        subscriber.onSubscribe(aVar);
        this.b.subscribe(aVar.d);
        this.source.subscribe((FlowableSubscriber) aVar);
    }
}
