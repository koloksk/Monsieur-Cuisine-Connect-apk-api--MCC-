package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableTimeoutTimed;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableTimeout<T, U, V> extends zk<T, T> {
    public final Publisher<U> b;
    public final Function<? super T, ? extends Publisher<V>> c;
    public final Publisher<? extends T> d;

    public static final class a extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        public static final long serialVersionUID = 8708641127342403073L;
        public final c a;
        public final long b;

        public a(long j, c cVar) {
            this.b = j;
            this.a = cVar;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Object obj = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (obj != subscriptionHelper) {
                lazySet(subscriptionHelper);
                this.a.a(this.b);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Object obj = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (obj == subscriptionHelper) {
                RxJavaPlugins.onError(th);
            } else {
                lazySet(subscriptionHelper);
                this.a.a(this.b, th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Subscription subscription = (Subscription) get();
            if (subscription != SubscriptionHelper.CANCELLED) {
                subscription.cancel();
                lazySet(SubscriptionHelper.CANCELLED);
                this.a.a(this.b);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public interface c extends FlowableTimeoutTimed.d {
        void a(long j, Throwable th);
    }

    public FlowableTimeout(Flowable<T> flowable, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
        super(flowable);
        this.b = publisher;
        this.c = function;
        this.d = publisher2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.d == null) {
            d dVar = new d(subscriber, this.c);
            subscriber.onSubscribe(dVar);
            Publisher<U> publisher = this.b;
            if (publisher != null) {
                a aVar = new a(0L, dVar);
                if (dVar.c.replace(aVar)) {
                    publisher.subscribe(aVar);
                }
            }
            this.source.subscribe((FlowableSubscriber) dVar);
            return;
        }
        b bVar = new b(subscriber, this.c, this.d);
        subscriber.onSubscribe(bVar);
        Publisher<U> publisher2 = this.b;
        if (publisher2 != null) {
            a aVar2 = new a(0L, bVar);
            if (bVar.j.replace(aVar2)) {
                publisher2.subscribe(aVar2);
            }
        }
        this.source.subscribe((FlowableSubscriber) bVar);
    }

    public static final class d<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription, c {
        public static final long serialVersionUID = 3764492702657003550L;
        public final Subscriber<? super T> a;
        public final Function<? super T, ? extends Publisher<?>> b;
        public final SequentialDisposable c = new SequentialDisposable();
        public final AtomicReference<Subscription> d = new AtomicReference<>();
        public final AtomicLong e = new AtomicLong();

        public d(Subscriber<? super T> subscriber, Function<? super T, ? extends Publisher<?>> function) {
            this.a = subscriber;
            this.b = function;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableTimeoutTimed.d
        public void a(long j) {
            if (compareAndSet(j, Long.MAX_VALUE)) {
                SubscriptionHelper.cancel(this.d);
                this.a.onError(new TimeoutException());
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.d);
            this.c.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.c.dispose();
                this.a.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (getAndSet(Long.MAX_VALUE) == Long.MAX_VALUE) {
                RxJavaPlugins.onError(th);
            } else {
                this.c.dispose();
                this.a.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = get();
            if (j != Long.MAX_VALUE) {
                long j2 = 1 + j;
                if (compareAndSet(j, j2)) {
                    Disposable disposable = this.c.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.a.onNext(t);
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(t), "The itemTimeoutIndicator returned a null Publisher.");
                        a aVar = new a(j2, this);
                        if (this.c.replace(aVar)) {
                            publisher.subscribe(aVar);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.d.get().cancel();
                        getAndSet(Long.MAX_VALUE);
                        this.a.onError(th);
                    }
                }
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.d, this.e, subscription);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.d, this.e, j);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableTimeout.c
        public void a(long j, Throwable th) {
            if (compareAndSet(j, Long.MAX_VALUE)) {
                SubscriptionHelper.cancel(this.d);
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public static final class b<T> extends SubscriptionArbiter implements FlowableSubscriber<T>, c {
        public static final long serialVersionUID = 3764492702657003550L;
        public final Subscriber<? super T> h;
        public final Function<? super T, ? extends Publisher<?>> i;
        public final SequentialDisposable j;
        public final AtomicReference<Subscription> k;
        public final AtomicLong l;
        public Publisher<? extends T> m;
        public long n;

        public b(Subscriber<? super T> subscriber, Function<? super T, ? extends Publisher<?>> function, Publisher<? extends T> publisher) {
            super(true);
            this.h = subscriber;
            this.i = function;
            this.j = new SequentialDisposable();
            this.k = new AtomicReference<>();
            this.m = publisher;
            this.l = new AtomicLong();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableTimeoutTimed.d
        public void a(long j) {
            if (this.l.compareAndSet(j, Long.MAX_VALUE)) {
                SubscriptionHelper.cancel(this.k);
                Publisher<? extends T> publisher = this.m;
                this.m = null;
                long j2 = this.n;
                if (j2 != 0) {
                    produced(j2);
                }
                publisher.subscribe(new FlowableTimeoutTimed.a(this.h, this));
            }
        }

        @Override // io.reactivex.internal.subscriptions.SubscriptionArbiter, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.j.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.l.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.j.dispose();
                this.h.onComplete();
                this.j.dispose();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.l.getAndSet(Long.MAX_VALUE) == Long.MAX_VALUE) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.j.dispose();
            this.h.onError(th);
            this.j.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.l.get();
            if (j != Long.MAX_VALUE) {
                long j2 = j + 1;
                if (this.l.compareAndSet(j, j2)) {
                    Disposable disposable = this.j.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.n++;
                    this.h.onNext(t);
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.i.apply(t), "The itemTimeoutIndicator returned a null Publisher.");
                        a aVar = new a(j2, this);
                        if (this.j.replace(aVar)) {
                            publisher.subscribe(aVar);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.k.get().cancel();
                        this.l.getAndSet(Long.MAX_VALUE);
                        this.h.onError(th);
                    }
                }
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.k, subscription)) {
                setSubscription(subscription);
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableTimeout.c
        public void a(long j, Throwable th) {
            if (this.l.compareAndSet(j, Long.MAX_VALUE)) {
                SubscriptionHelper.cancel(this.k);
                this.h.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }
}
