package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableSequenceEqual<T> extends Flowable<Boolean> {
    public final Publisher<? extends T> b;
    public final Publisher<? extends T> c;
    public final BiPredicate<? super T, ? super T> d;
    public final int e;

    public static final class a<T> extends DeferredScalarSubscription<Boolean> implements b {
        public static final long serialVersionUID = -6178010334400373240L;
        public final BiPredicate<? super T, ? super T> a;
        public final c<T> b;
        public final c<T> c;
        public final AtomicThrowable d;
        public final AtomicInteger e;
        public T f;
        public T g;

        public a(Subscriber<? super Boolean> subscriber, int i, BiPredicate<? super T, ? super T> biPredicate) {
            super(subscriber);
            this.a = biPredicate;
            this.e = new AtomicInteger();
            this.b = new c<>(this, i);
            this.c = new c<>(this, i);
            this.d = new AtomicThrowable();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableSequenceEqual.b
        public void a(Throwable th) {
            if (this.d.addThrowable(th)) {
                drain();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            c<T> cVar = this.b;
            if (cVar == null) {
                throw null;
            }
            SubscriptionHelper.cancel(cVar);
            c<T> cVar2 = this.c;
            if (cVar2 == null) {
                throw null;
            }
            SubscriptionHelper.cancel(cVar2);
            if (this.e.getAndIncrement() == 0) {
                this.b.a();
                this.c.a();
            }
        }

        public void d() {
            c<T> cVar = this.b;
            if (cVar == null) {
                throw null;
            }
            SubscriptionHelper.cancel(cVar);
            this.b.a();
            c<T> cVar2 = this.c;
            if (cVar2 == null) {
                throw null;
            }
            SubscriptionHelper.cancel(cVar2);
            this.c.a();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableSequenceEqual.b
        public void drain() {
            if (this.e.getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            do {
                SimpleQueue<T> simpleQueue = this.b.e;
                SimpleQueue<T> simpleQueue2 = this.c.e;
                if (simpleQueue != null && simpleQueue2 != null) {
                    while (!isCancelled()) {
                        if (this.d.get() != null) {
                            d();
                            this.downstream.onError(this.d.terminate());
                            return;
                        }
                        boolean z = this.b.f;
                        T tPoll = this.f;
                        if (tPoll == null) {
                            try {
                                tPoll = simpleQueue.poll();
                                this.f = tPoll;
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                d();
                                this.d.addThrowable(th);
                                this.downstream.onError(this.d.terminate());
                                return;
                            }
                        }
                        boolean z2 = tPoll == null;
                        boolean z3 = this.c.f;
                        T tPoll2 = this.g;
                        if (tPoll2 == null) {
                            try {
                                tPoll2 = simpleQueue2.poll();
                                this.g = tPoll2;
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                d();
                                this.d.addThrowable(th2);
                                this.downstream.onError(this.d.terminate());
                                return;
                            }
                        }
                        boolean z4 = tPoll2 == null;
                        if (z && z3 && z2 && z4) {
                            complete(true);
                            return;
                        }
                        if (z && z3 && z2 != z4) {
                            d();
                            complete(false);
                            return;
                        }
                        if (!z2 && !z4) {
                            try {
                                if (!this.a.test(tPoll, tPoll2)) {
                                    d();
                                    complete(false);
                                    return;
                                } else {
                                    this.f = null;
                                    this.g = null;
                                    this.b.b();
                                    this.c.b();
                                }
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                d();
                                this.d.addThrowable(th3);
                                this.downstream.onError(this.d.terminate());
                                return;
                            }
                        }
                    }
                    this.b.a();
                    this.c.a();
                    return;
                }
                if (isCancelled()) {
                    this.b.a();
                    this.c.a();
                    return;
                } else if (this.d.get() != null) {
                    d();
                    this.downstream.onError(this.d.terminate());
                    return;
                }
                iAddAndGet = this.e.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }
    }

    public interface b {
        void a(Throwable th);

        void drain();
    }

    public static final class c<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        public static final long serialVersionUID = 4804128302091633067L;
        public final b a;
        public final int b;
        public final int c;
        public long d;
        public volatile SimpleQueue<T> e;
        public volatile boolean f;
        public int g;

        public c(b bVar, int i) {
            this.a = bVar;
            this.c = i - (i >> 2);
            this.b = i;
        }

        public void a() {
            SimpleQueue<T> simpleQueue = this.e;
            if (simpleQueue != null) {
                simpleQueue.clear();
            }
        }

        public void b() {
            if (this.g != 1) {
                long j = this.d + 1;
                if (j < this.c) {
                    this.d = j;
                } else {
                    this.d = 0L;
                    get().request(j);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.f = true;
            this.a.drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.g != 0 || this.e.offer(t)) {
                this.a.drain();
            } else {
                this.a.a(new MissingBackpressureException());
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.g = iRequestFusion;
                        this.e = queueSubscription;
                        this.f = true;
                        this.a.drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.g = iRequestFusion;
                        this.e = queueSubscription;
                        subscription.request(this.b);
                        return;
                    }
                }
                this.e = new SpscArrayQueue(this.b);
                subscription.request(this.b);
            }
        }
    }

    public FlowableSequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.b = publisher;
        this.c = publisher2;
        this.d = biPredicate;
        this.e = i;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Boolean> subscriber) {
        a aVar = new a(subscriber, this.e, this.d);
        subscriber.onSubscribe(aVar);
        Publisher<? extends T> publisher = this.b;
        Publisher<? extends T> publisher2 = this.c;
        publisher.subscribe(aVar.b);
        publisher2.subscribe(aVar.c);
    }
}
