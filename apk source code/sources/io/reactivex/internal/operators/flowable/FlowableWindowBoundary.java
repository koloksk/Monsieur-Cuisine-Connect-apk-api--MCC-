package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableWindowBoundary<T, B> extends zk<T, Flowable<T>> {
    public final Publisher<B> b;
    public final int c;

    public static final class a<T, B> extends DisposableSubscriber<B> {
        public final b<T, B> b;
        public boolean c;

        public a(b<T, B> bVar) {
            this.b = bVar;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.c) {
                return;
            }
            this.c = true;
            b<T, B> bVar = this.b;
            SubscriptionHelper.cancel(bVar.d);
            bVar.j = true;
            bVar.a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            b<T, B> bVar = this.b;
            SubscriptionHelper.cancel(bVar.d);
            if (!bVar.g.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                bVar.j = true;
                bVar.a();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b) {
            if (this.c) {
                return;
            }
            b<T, B> bVar = this.b;
            bVar.f.offer(b.m);
            bVar.a();
        }
    }

    public static final class b<T, B> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        public static final Object m = new Object();
        public static final long serialVersionUID = 2233020065421370272L;
        public final Subscriber<? super Flowable<T>> a;
        public final int b;
        public final a<T, B> c = new a<>(this);
        public final AtomicReference<Subscription> d = new AtomicReference<>();
        public final AtomicInteger e = new AtomicInteger(1);
        public final MpscLinkedQueue<Object> f = new MpscLinkedQueue<>();
        public final AtomicThrowable g = new AtomicThrowable();
        public final AtomicBoolean h = new AtomicBoolean();
        public final AtomicLong i = new AtomicLong();
        public volatile boolean j;
        public UnicastProcessor<T> k;
        public long l;

        public b(Subscriber<? super Flowable<T>> subscriber, int i) {
            this.a = subscriber;
            this.b = i;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super Flowable<T>> subscriber = this.a;
            MpscLinkedQueue<Object> mpscLinkedQueue = this.f;
            AtomicThrowable atomicThrowable = this.g;
            long j = this.l;
            int iAddAndGet = 1;
            while (this.e.get() != 0) {
                UnicastProcessor<T> unicastProcessor = this.k;
                boolean z = this.j;
                if (z && atomicThrowable.get() != null) {
                    mpscLinkedQueue.clear();
                    Throwable thTerminate = atomicThrowable.terminate();
                    if (unicastProcessor != 0) {
                        this.k = null;
                        unicastProcessor.onError(thTerminate);
                    }
                    subscriber.onError(thTerminate);
                    return;
                }
                Object objPoll = mpscLinkedQueue.poll();
                boolean z2 = objPoll == null;
                if (z && z2) {
                    Throwable thTerminate2 = atomicThrowable.terminate();
                    if (thTerminate2 == null) {
                        if (unicastProcessor != 0) {
                            this.k = null;
                            unicastProcessor.onComplete();
                        }
                        subscriber.onComplete();
                        return;
                    }
                    if (unicastProcessor != 0) {
                        this.k = null;
                        unicastProcessor.onError(thTerminate2);
                    }
                    subscriber.onError(thTerminate2);
                    return;
                }
                if (z2) {
                    this.l = j;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else if (objPoll != m) {
                    unicastProcessor.onNext(objPoll);
                } else {
                    if (unicastProcessor != 0) {
                        this.k = null;
                        unicastProcessor.onComplete();
                    }
                    if (!this.h.get()) {
                        UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.b, this);
                        this.k = unicastProcessorCreate;
                        this.e.getAndIncrement();
                        if (j != this.i.get()) {
                            j++;
                            subscriber.onNext(unicastProcessorCreate);
                        } else {
                            SubscriptionHelper.cancel(this.d);
                            this.c.dispose();
                            atomicThrowable.addThrowable(new MissingBackpressureException("Could not deliver a window due to lack of requests"));
                            this.j = true;
                        }
                    }
                }
            }
            mpscLinkedQueue.clear();
            this.k = null;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.h.compareAndSet(false, true)) {
                this.c.dispose();
                if (this.e.decrementAndGet() == 0) {
                    SubscriptionHelper.cancel(this.d);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.c.dispose();
            this.j = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.c.dispose();
            if (!this.g.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.j = true;
                a();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.f.offer(t);
            a();
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this.d, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.i, j);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.e.decrementAndGet() == 0) {
                SubscriptionHelper.cancel(this.d);
            }
        }
    }

    public FlowableWindowBoundary(Flowable<T> flowable, Publisher<B> publisher, int i) {
        super(flowable);
        this.b = publisher;
        this.c = i;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        b bVar = new b(subscriber, this.c);
        subscriber.onSubscribe(bVar);
        bVar.f.offer(b.m);
        bVar.a();
        this.b.subscribe(bVar.c);
        this.source.subscribe((FlowableSubscriber) bVar);
    }
}
