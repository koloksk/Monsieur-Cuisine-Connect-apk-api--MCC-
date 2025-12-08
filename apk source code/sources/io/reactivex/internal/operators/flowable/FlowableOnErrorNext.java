package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableOnErrorNext<T> extends zk<T, T> {
    public final Function<? super Throwable, ? extends Publisher<? extends T>> b;
    public final boolean c;

    public static final class a<T> extends SubscriptionArbiter implements FlowableSubscriber<T> {
        public static final long serialVersionUID = 4063763155303814625L;
        public final Subscriber<? super T> h;
        public final Function<? super Throwable, ? extends Publisher<? extends T>> i;
        public final boolean j;
        public boolean k;
        public boolean l;
        public long m;

        public a(Subscriber<? super T> subscriber, Function<? super Throwable, ? extends Publisher<? extends T>> function, boolean z) {
            super(false);
            this.h = subscriber;
            this.i = function;
            this.j = z;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.l) {
                return;
            }
            this.l = true;
            this.k = true;
            this.h.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.k) {
                if (this.l) {
                    RxJavaPlugins.onError(th);
                    return;
                } else {
                    this.h.onError(th);
                    return;
                }
            }
            this.k = true;
            if (this.j && !(th instanceof Exception)) {
                this.h.onError(th);
                return;
            }
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.i.apply(th), "The nextSupplier returned a null Publisher");
                long j = this.m;
                if (j != 0) {
                    produced(j);
                }
                publisher.subscribe(this);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.h.onError(new CompositeException(th, th2));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.l) {
                return;
            }
            if (!this.k) {
                this.m++;
            }
            this.h.onNext(t);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }
    }

    public FlowableOnErrorNext(Flowable<T> flowable, Function<? super Throwable, ? extends Publisher<? extends T>> function, boolean z) {
        super(flowable);
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber, this.b, this.c);
        subscriber.onSubscribe(aVar);
        this.source.subscribe((FlowableSubscriber) aVar);
    }
}
