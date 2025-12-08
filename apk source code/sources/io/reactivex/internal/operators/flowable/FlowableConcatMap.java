package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableConcatMap<T, R> extends zk<T, R> {
    public final Function<? super T, ? extends Publisher<? extends R>> b;
    public final int c;
    public final ErrorMode d;

    public static abstract class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, e<R>, Subscription {
        public static final long serialVersionUID = -3511336836796789179L;
        public final Function<? super T, ? extends Publisher<? extends R>> b;
        public final int c;
        public final int d;
        public Subscription e;
        public int f;
        public SimpleQueue<T> g;
        public volatile boolean h;
        public volatile boolean i;
        public volatile boolean k;
        public int l;
        public final d<R> a = new d<>(this);
        public final AtomicThrowable j = new AtomicThrowable();

        public a(Function<? super T, ? extends Publisher<? extends R>> function, int i) {
            this.b = function;
            this.c = i;
            this.d = i - (i >> 2);
        }

        public abstract void a();

        public abstract void b();

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            this.h = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (this.l == 2 || this.g.offer(t)) {
                a();
            } else {
                this.e.cancel();
                onError(new IllegalStateException("Queue full?!"));
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public final void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.l = iRequestFusion;
                        this.g = queueSubscription;
                        this.h = true;
                        b();
                        a();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.l = iRequestFusion;
                        this.g = queueSubscription;
                        b();
                        subscription.request(this.c);
                        return;
                    }
                }
                this.g = new SpscArrayQueue(this.c);
                b();
                subscription.request(this.c);
            }
        }
    }

    public static final class b<T, R> extends a<T, R> {
        public static final long serialVersionUID = -2945777694260521066L;
        public final Subscriber<? super R> m;
        public final boolean n;

        public b(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
            super(function, i);
            this.m = subscriber;
            this.n = z;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableConcatMap.e
        public void a(R r) {
            this.m.onNext(r);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableConcatMap.a
        public void b() {
            this.m.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.i) {
                return;
            }
            this.i = true;
            this.a.cancel();
            this.e.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.j.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.h = true;
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.a.request(j);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableConcatMap.e
        public void a(Throwable th) {
            if (!this.j.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.n) {
                this.e.cancel();
                this.h = true;
            }
            this.k = false;
            a();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableConcatMap.a
        public void a() {
            Object objCall;
            if (getAndIncrement() == 0) {
                while (!this.i) {
                    if (!this.k) {
                        boolean z = this.h;
                        if (z && !this.n && this.j.get() != null) {
                            this.m.onError(this.j.terminate());
                            return;
                        }
                        try {
                            T tPoll = this.g.poll();
                            boolean z2 = tPoll == null;
                            if (z && z2) {
                                Throwable thTerminate = this.j.terminate();
                                if (thTerminate != null) {
                                    this.m.onError(thTerminate);
                                    return;
                                } else {
                                    this.m.onComplete();
                                    return;
                                }
                            }
                            if (!z2) {
                                try {
                                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(tPoll), "The mapper returned a null Publisher");
                                    if (this.l != 1) {
                                        int i = this.f + 1;
                                        if (i == this.d) {
                                            this.f = 0;
                                            this.e.request(i);
                                        } else {
                                            this.f = i;
                                        }
                                    }
                                    if (publisher instanceof Callable) {
                                        try {
                                            objCall = ((Callable) publisher).call();
                                        } catch (Throwable th) {
                                            Exceptions.throwIfFatal(th);
                                            this.j.addThrowable(th);
                                            if (!this.n) {
                                                this.e.cancel();
                                                this.m.onError(this.j.terminate());
                                                return;
                                            }
                                            objCall = null;
                                        }
                                        if (objCall == null) {
                                            continue;
                                        } else if (this.a.isUnbounded()) {
                                            this.m.onNext(objCall);
                                        } else {
                                            this.k = true;
                                            d<R> dVar = this.a;
                                            dVar.setSubscription(new f(objCall, dVar));
                                        }
                                    } else {
                                        this.k = true;
                                        publisher.subscribe(this.a);
                                    }
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    this.e.cancel();
                                    this.j.addThrowable(th2);
                                    this.m.onError(this.j.terminate());
                                    return;
                                }
                            }
                        } catch (Throwable th3) {
                            Exceptions.throwIfFatal(th3);
                            this.e.cancel();
                            this.j.addThrowable(th3);
                            this.m.onError(this.j.terminate());
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }
    }

    public static final class d<R> extends SubscriptionArbiter implements FlowableSubscriber<R> {
        public static final long serialVersionUID = 897683679971470653L;
        public final e<R> h;
        public long i;

        public d(e<R> eVar) {
            super(false);
            this.h = eVar;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            long j = this.i;
            if (j != 0) {
                this.i = 0L;
                produced(j);
            }
            a aVar = (a) this.h;
            aVar.k = false;
            aVar.a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            long j = this.i;
            if (j != 0) {
                this.i = 0L;
                produced(j);
            }
            this.h.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.i++;
            this.h.a((e<R>) r);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }
    }

    public interface e<T> {
        void a(T t);

        void a(Throwable th);
    }

    public static final class f<T> implements Subscription {
        public final Subscriber<? super T> a;
        public final T b;
        public boolean c;

        public f(T t, Subscriber<? super T> subscriber) {
            this.b = t;
            this.a = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (j <= 0 || this.c) {
                return;
            }
            this.c = true;
            Subscriber<? super T> subscriber = this.a;
            subscriber.onNext(this.b);
            subscriber.onComplete();
        }
    }

    public FlowableConcatMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, ErrorMode errorMode) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = errorMode;
    }

    public static <T, R> Subscriber<T> subscribe(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, ErrorMode errorMode) {
        int iOrdinal = errorMode.ordinal();
        return iOrdinal != 1 ? iOrdinal != 2 ? new c(subscriber, function, i) : new b(subscriber, function, i, true) : new b(subscriber, function, i, false);
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.b)) {
            return;
        }
        this.source.subscribe(subscribe(subscriber, this.b, this.c, this.d));
    }

    public static final class c<T, R> extends a<T, R> {
        public static final long serialVersionUID = 7898995095634264146L;
        public final Subscriber<? super R> m;
        public final AtomicInteger n;

        public c(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i) {
            super(function, i);
            this.m = subscriber;
            this.n = new AtomicInteger();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableConcatMap.e
        public void a(R r) {
            if (get() == 0 && compareAndSet(0, 1)) {
                this.m.onNext(r);
                if (compareAndSet(1, 0)) {
                    return;
                }
                this.m.onError(this.j.terminate());
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableConcatMap.a
        public void b() {
            this.m.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.i) {
                return;
            }
            this.i = true;
            this.a.cancel();
            this.e.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.j.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.a.cancel();
            if (getAndIncrement() == 0) {
                this.m.onError(this.j.terminate());
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.a.request(j);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableConcatMap.e
        public void a(Throwable th) {
            if (this.j.addThrowable(th)) {
                this.e.cancel();
                if (getAndIncrement() == 0) {
                    this.m.onError(this.j.terminate());
                    return;
                }
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableConcatMap.a
        public void a() {
            if (this.n.getAndIncrement() == 0) {
                while (!this.i) {
                    if (!this.k) {
                        boolean z = this.h;
                        try {
                            T tPoll = this.g.poll();
                            boolean z2 = tPoll == null;
                            if (z && z2) {
                                this.m.onComplete();
                                return;
                            }
                            if (!z2) {
                                try {
                                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(tPoll), "The mapper returned a null Publisher");
                                    if (this.l != 1) {
                                        int i = this.f + 1;
                                        if (i == this.d) {
                                            this.f = 0;
                                            this.e.request(i);
                                        } else {
                                            this.f = i;
                                        }
                                    }
                                    if (publisher instanceof Callable) {
                                        try {
                                            Object objCall = ((Callable) publisher).call();
                                            if (objCall == null) {
                                                continue;
                                            } else if (this.a.isUnbounded()) {
                                                if (get() == 0 && compareAndSet(0, 1)) {
                                                    this.m.onNext(objCall);
                                                    if (!compareAndSet(1, 0)) {
                                                        this.m.onError(this.j.terminate());
                                                        return;
                                                    }
                                                }
                                            } else {
                                                this.k = true;
                                                d<R> dVar = this.a;
                                                dVar.setSubscription(new f(objCall, dVar));
                                            }
                                        } catch (Throwable th) {
                                            Exceptions.throwIfFatal(th);
                                            this.e.cancel();
                                            this.j.addThrowable(th);
                                            this.m.onError(this.j.terminate());
                                            return;
                                        }
                                    } else {
                                        this.k = true;
                                        publisher.subscribe(this.a);
                                    }
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    this.e.cancel();
                                    this.j.addThrowable(th2);
                                    this.m.onError(this.j.terminate());
                                    return;
                                }
                            }
                        } catch (Throwable th3) {
                            Exceptions.throwIfFatal(th3);
                            this.e.cancel();
                            this.j.addThrowable(th3);
                            this.m.onError(this.j.terminate());
                            return;
                        }
                    }
                    if (this.n.decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }
    }
}
