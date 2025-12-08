package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscribers.InnerQueuedSubscriber;
import io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableConcatMapEager<T, R> extends zk<T, R> {
    public final Function<? super T, ? extends Publisher<? extends R>> b;
    public final int c;
    public final int d;
    public final ErrorMode e;

    public static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, InnerQueuedSubscriberSupport<R> {
        public static final long serialVersionUID = -4255299542215038287L;
        public final Subscriber<? super R> a;
        public final Function<? super T, ? extends Publisher<? extends R>> b;
        public final int c;
        public final int d;
        public final ErrorMode e;
        public final AtomicThrowable f = new AtomicThrowable();
        public final AtomicLong g = new AtomicLong();
        public final SpscLinkedArrayQueue<InnerQueuedSubscriber<R>> h;
        public Subscription i;
        public volatile boolean j;
        public volatile boolean k;
        public volatile InnerQueuedSubscriber<R> l;

        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, ErrorMode errorMode) {
            this.a = subscriber;
            this.b = function;
            this.c = i;
            this.d = i2;
            this.e = errorMode;
            this.h = new SpscLinkedArrayQueue<>(Math.min(i2, i));
        }

        public void a() {
            InnerQueuedSubscriber<R> innerQueuedSubscriber = this.l;
            this.l = null;
            if (innerQueuedSubscriber != null) {
                innerQueuedSubscriber.cancel();
            }
            while (true) {
                InnerQueuedSubscriber<R> innerQueuedSubscriberPoll = this.h.poll();
                if (innerQueuedSubscriberPoll == null) {
                    return;
                } else {
                    innerQueuedSubscriberPoll.cancel();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.j) {
                return;
            }
            this.j = true;
            this.i.cancel();
            if (getAndIncrement() == 0) {
                do {
                    a();
                } while (decrementAndGet() != 0);
            }
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void drain() {
            InnerQueuedSubscriber<R> innerQueuedSubscriberPoll;
            int i;
            boolean z;
            long j;
            long j2;
            SimpleQueue<R> simpleQueueQueue;
            if (getAndIncrement() != 0) {
                return;
            }
            InnerQueuedSubscriber<R> innerQueuedSubscriber = this.l;
            Subscriber<? super R> subscriber = this.a;
            ErrorMode errorMode = this.e;
            int iAddAndGet = 1;
            while (true) {
                long j3 = this.g.get();
                if (innerQueuedSubscriber != null) {
                    innerQueuedSubscriberPoll = innerQueuedSubscriber;
                } else {
                    if (errorMode != ErrorMode.END && this.f.get() != null) {
                        a();
                        subscriber.onError(this.f.terminate());
                        return;
                    }
                    boolean z2 = this.k;
                    innerQueuedSubscriberPoll = this.h.poll();
                    if (z2 && innerQueuedSubscriberPoll == null) {
                        Throwable thTerminate = this.f.terminate();
                        if (thTerminate != null) {
                            subscriber.onError(thTerminate);
                            return;
                        } else {
                            subscriber.onComplete();
                            return;
                        }
                    }
                    if (innerQueuedSubscriberPoll != null) {
                        this.l = innerQueuedSubscriberPoll;
                    }
                }
                if (innerQueuedSubscriberPoll == null || (simpleQueueQueue = innerQueuedSubscriberPoll.queue()) == null) {
                    i = iAddAndGet;
                    z = false;
                    j = 0;
                    j2 = 0;
                } else {
                    j2 = 0;
                    while (true) {
                        i = iAddAndGet;
                        if (j2 == j3) {
                            break;
                        }
                        if (this.j) {
                            a();
                            return;
                        }
                        if (errorMode == ErrorMode.IMMEDIATE && this.f.get() != null) {
                            this.l = null;
                            innerQueuedSubscriberPoll.cancel();
                            a();
                            subscriber.onError(this.f.terminate());
                            return;
                        }
                        boolean zIsDone = innerQueuedSubscriberPoll.isDone();
                        try {
                            R rPoll = simpleQueueQueue.poll();
                            boolean z3 = rPoll == null;
                            if (zIsDone && z3) {
                                this.l = null;
                                this.i.request(1L);
                                innerQueuedSubscriberPoll = null;
                                z = true;
                                break;
                            }
                            if (z3) {
                                break;
                            }
                            subscriber.onNext(rPoll);
                            j2++;
                            innerQueuedSubscriberPoll.requestOne();
                            iAddAndGet = i;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.l = null;
                            innerQueuedSubscriberPoll.cancel();
                            a();
                            subscriber.onError(th);
                            return;
                        }
                    }
                    z = false;
                    if (j2 == j3) {
                        if (this.j) {
                            a();
                            return;
                        }
                        if (errorMode == ErrorMode.IMMEDIATE && this.f.get() != null) {
                            this.l = null;
                            innerQueuedSubscriberPoll.cancel();
                            a();
                            subscriber.onError(this.f.terminate());
                            return;
                        }
                        boolean zIsDone2 = innerQueuedSubscriberPoll.isDone();
                        boolean zIsEmpty = simpleQueueQueue.isEmpty();
                        if (zIsDone2 && zIsEmpty) {
                            this.l = null;
                            this.i.request(1L);
                            innerQueuedSubscriberPoll = null;
                            z = true;
                        }
                    }
                    j = 0;
                }
                if (j2 != j && j3 != Long.MAX_VALUE) {
                    this.g.addAndGet(-j2);
                }
                if (z) {
                    innerQueuedSubscriber = innerQueuedSubscriberPoll;
                    iAddAndGet = i;
                } else {
                    iAddAndGet = addAndGet(-i);
                    if (iAddAndGet == 0) {
                        return;
                    } else {
                        innerQueuedSubscriber = innerQueuedSubscriberPoll;
                    }
                }
            }
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerComplete(InnerQueuedSubscriber<R> innerQueuedSubscriber) {
            innerQueuedSubscriber.setDone();
            drain();
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerError(InnerQueuedSubscriber<R> innerQueuedSubscriber, Throwable th) {
            if (!this.f.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            innerQueuedSubscriber.setDone();
            if (this.e != ErrorMode.END) {
                this.i.cancel();
            }
            drain();
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerNext(InnerQueuedSubscriber<R> innerQueuedSubscriber, R r) {
            if (innerQueuedSubscriber.queue().offer(r)) {
                drain();
            } else {
                innerQueuedSubscriber.cancel();
                innerError(innerQueuedSubscriber, new MissingBackpressureException());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.k = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.f.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.k = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null Publisher");
                InnerQueuedSubscriber<R> innerQueuedSubscriber = new InnerQueuedSubscriber<>(this, this.d);
                if (this.j) {
                    return;
                }
                this.h.offer(innerQueuedSubscriber);
                publisher.subscribe(innerQueuedSubscriber);
                if (this.j) {
                    innerQueuedSubscriber.cancel();
                    if (getAndIncrement() == 0) {
                        do {
                            a();
                        } while (decrementAndGet() != 0);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.i.cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.i, subscription)) {
                this.i = subscription;
                this.a.onSubscribe(this);
                int i = this.c;
                subscription.request(i == Integer.MAX_VALUE ? Long.MAX_VALUE : i);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.g, j);
                drain();
            }
        }
    }

    public FlowableConcatMapEager(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, ErrorMode errorMode) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = i2;
        this.e = errorMode;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d, this.e));
    }
}
