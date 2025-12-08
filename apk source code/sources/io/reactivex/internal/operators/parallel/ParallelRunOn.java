package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class ParallelRunOn<T> extends ParallelFlowable<T> {
    public final ParallelFlowable<? extends T> a;
    public final Scheduler b;
    public final int c;

    public static abstract class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        public static final long serialVersionUID = 9222303586456402150L;
        public final int a;
        public final int b;
        public final SpscArrayQueue<T> c;
        public final Scheduler.Worker d;
        public Subscription e;
        public volatile boolean f;
        public Throwable g;
        public final AtomicLong h = new AtomicLong();
        public volatile boolean i;
        public int j;

        public a(int i, SpscArrayQueue<T> spscArrayQueue, Scheduler.Worker worker) {
            this.a = i;
            this.c = spscArrayQueue;
            this.b = i - (i >> 2);
            this.d = worker;
        }

        public final void a() {
            if (getAndIncrement() == 0) {
                this.d.schedule(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            if (this.i) {
                return;
            }
            this.i = true;
            this.e.cancel();
            this.d.dispose();
            if (getAndIncrement() == 0) {
                this.c.clear();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            if (this.f) {
                return;
            }
            this.f = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = th;
            this.f = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (this.f) {
                return;
            }
            if (this.c.offer(t)) {
                a();
            } else {
                this.e.cancel();
                onError(new MissingBackpressureException("Queue is full?!"));
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.h, j);
                a();
            }
        }
    }

    public final class b implements SchedulerMultiWorkerSupport.WorkerCallback {
        public final Subscriber<? super T>[] a;
        public final Subscriber<T>[] b;

        public b(Subscriber<? super T>[] subscriberArr, Subscriber<T>[] subscriberArr2) {
            this.a = subscriberArr;
            this.b = subscriberArr2;
        }

        @Override // io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport.WorkerCallback
        public void onWorker(int i, Scheduler.Worker worker) {
            ParallelRunOn.this.a(i, this.a, this.b, worker);
        }
    }

    public static final class c<T> extends a<T> {
        public static final long serialVersionUID = 1075119423897941642L;
        public final ConditionalSubscriber<? super T> k;

        public c(ConditionalSubscriber<? super T> conditionalSubscriber, int i, SpscArrayQueue<T> spscArrayQueue, Scheduler.Worker worker) {
            super(i, spscArrayQueue, worker);
            this.k = conditionalSubscriber;
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.k.onSubscribe(this);
                subscription.request(this.a);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Throwable th;
            int i = this.j;
            SpscArrayQueue<T> spscArrayQueue = this.c;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.k;
            int i2 = this.b;
            int iAddAndGet = 1;
            while (true) {
                long j = this.h.get();
                long j2 = 0;
                while (j2 != j) {
                    if (this.i) {
                        spscArrayQueue.clear();
                        return;
                    }
                    boolean z = this.f;
                    if (z && (th = this.g) != null) {
                        spscArrayQueue.clear();
                        conditionalSubscriber.onError(th);
                        this.d.dispose();
                        return;
                    }
                    T tPoll = spscArrayQueue.poll();
                    boolean z2 = tPoll == null;
                    if (z && z2) {
                        conditionalSubscriber.onComplete();
                        this.d.dispose();
                        return;
                    } else {
                        if (z2) {
                            break;
                        }
                        if (conditionalSubscriber.tryOnNext(tPoll)) {
                            j2++;
                        }
                        i++;
                        if (i == i2) {
                            this.e.request(i);
                            i = 0;
                        }
                    }
                }
                if (j2 == j) {
                    if (this.i) {
                        spscArrayQueue.clear();
                        return;
                    }
                    if (this.f) {
                        Throwable th2 = this.g;
                        if (th2 != null) {
                            spscArrayQueue.clear();
                            conditionalSubscriber.onError(th2);
                            this.d.dispose();
                            return;
                        } else if (spscArrayQueue.isEmpty()) {
                            conditionalSubscriber.onComplete();
                            this.d.dispose();
                            return;
                        }
                    }
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    this.h.addAndGet(-j2);
                }
                int i3 = get();
                if (i3 == iAddAndGet) {
                    this.j = i;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i3;
                }
            }
        }
    }

    public static final class d<T> extends a<T> {
        public static final long serialVersionUID = 1075119423897941642L;
        public final Subscriber<? super T> k;

        public d(Subscriber<? super T> subscriber, int i, SpscArrayQueue<T> spscArrayQueue, Scheduler.Worker worker) {
            super(i, spscArrayQueue, worker);
            this.k = subscriber;
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.k.onSubscribe(this);
                subscription.request(this.a);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Throwable th;
            int i = this.j;
            SpscArrayQueue<T> spscArrayQueue = this.c;
            Subscriber<? super T> subscriber = this.k;
            int i2 = this.b;
            int iAddAndGet = 1;
            while (true) {
                long j = this.h.get();
                long j2 = 0;
                while (j2 != j) {
                    if (this.i) {
                        spscArrayQueue.clear();
                        return;
                    }
                    boolean z = this.f;
                    if (z && (th = this.g) != null) {
                        spscArrayQueue.clear();
                        subscriber.onError(th);
                        this.d.dispose();
                        return;
                    }
                    T tPoll = spscArrayQueue.poll();
                    boolean z2 = tPoll == null;
                    if (z && z2) {
                        subscriber.onComplete();
                        this.d.dispose();
                        return;
                    } else {
                        if (z2) {
                            break;
                        }
                        subscriber.onNext(tPoll);
                        j2++;
                        i++;
                        if (i == i2) {
                            this.e.request(i);
                            i = 0;
                        }
                    }
                }
                if (j2 == j) {
                    if (this.i) {
                        spscArrayQueue.clear();
                        return;
                    }
                    if (this.f) {
                        Throwable th2 = this.g;
                        if (th2 != null) {
                            spscArrayQueue.clear();
                            subscriber.onError(th2);
                            this.d.dispose();
                            return;
                        } else if (spscArrayQueue.isEmpty()) {
                            subscriber.onComplete();
                            this.d.dispose();
                            return;
                        }
                    }
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    this.h.addAndGet(-j2);
                }
                int i3 = get();
                if (i3 == iAddAndGet) {
                    this.j = i;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i3;
                }
            }
        }
    }

    public ParallelRunOn(ParallelFlowable<? extends T> parallelFlowable, Scheduler scheduler, int i) {
        this.a = parallelFlowable;
        this.b = scheduler;
        this.c = i;
    }

    public void a(int i, Subscriber<? super T>[] subscriberArr, Subscriber<T>[] subscriberArr2, Scheduler.Worker worker) {
        Subscriber<? super T> subscriber = subscriberArr[i];
        SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.c);
        if (subscriber instanceof ConditionalSubscriber) {
            subscriberArr2[i] = new c((ConditionalSubscriber) subscriber, this.c, spscArrayQueue, worker);
        } else {
            subscriberArr2[i] = new d(subscriber, this.c, spscArrayQueue, worker);
        }
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<T>[] subscriberArr2 = new Subscriber[length];
            Object obj = this.b;
            if (obj instanceof SchedulerMultiWorkerSupport) {
                ((SchedulerMultiWorkerSupport) obj).createWorkers(length, new b(subscriberArr, subscriberArr2));
            } else {
                for (int i = 0; i < length; i++) {
                    a(i, subscriberArr, subscriberArr2, this.b.createWorker());
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }
}
