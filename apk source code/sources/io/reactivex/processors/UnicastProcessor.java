package io.reactivex.processors;

import io.reactivex.Flowable;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class UnicastProcessor<T> extends FlowableProcessor<T> {
    public final SpscLinkedArrayQueue<T> b;
    public final AtomicReference<Runnable> c;
    public final boolean d;
    public volatile boolean e;
    public Throwable f;
    public volatile boolean h;
    public boolean l;
    public final AtomicReference<Subscriber<? super T>> g = new AtomicReference<>();
    public final AtomicBoolean i = new AtomicBoolean();
    public final BasicIntQueueSubscription<T> j = new a();
    public final AtomicLong k = new AtomicLong();

    public final class a extends BasicIntQueueSubscription<T> {
        public static final long serialVersionUID = -4896760517184205454L;

        public a() {
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (UnicastProcessor.this.h) {
                return;
            }
            UnicastProcessor.this.h = true;
            UnicastProcessor.this.a();
            UnicastProcessor.this.g.lazySet(null);
            if (UnicastProcessor.this.j.getAndIncrement() == 0) {
                UnicastProcessor.this.g.lazySet(null);
                UnicastProcessor unicastProcessor = UnicastProcessor.this;
                if (unicastProcessor.l) {
                    return;
                }
                unicastProcessor.b.clear();
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            UnicastProcessor.this.b.clear();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return UnicastProcessor.this.b.isEmpty();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            return UnicastProcessor.this.b.poll();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(UnicastProcessor.this.k, j);
                UnicastProcessor.this.b();
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            UnicastProcessor.this.l = true;
            return 2;
        }
    }

    public UnicastProcessor(int i, Runnable runnable, boolean z) {
        this.b = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(i, "capacityHint"));
        this.c = new AtomicReference<>(runnable);
        this.d = z;
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create() {
        return new UnicastProcessor<>(Flowable.bufferSize(), null, true);
    }

    public void a() {
        Runnable andSet = this.c.getAndSet(null);
        if (andSet != null) {
            andSet.run();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void b() {
        long j;
        if (this.j.getAndIncrement() != 0) {
            return;
        }
        int iAddAndGet = 1;
        Subscriber<? super T> subscriber = this.g.get();
        int iAddAndGet2 = 1;
        while (subscriber == null) {
            iAddAndGet2 = this.j.addAndGet(-iAddAndGet2);
            if (iAddAndGet2 == 0) {
                return;
            }
            subscriber = this.g.get();
            iAddAndGet = 1;
        }
        if (this.l) {
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
            int i = (this.d ? 1 : 0) ^ iAddAndGet;
            while (!this.h) {
                boolean z = this.e;
                if (i != 0 && z && this.f != null) {
                    spscLinkedArrayQueue.clear();
                    this.g.lazySet(null);
                    subscriber.onError(this.f);
                    return;
                }
                subscriber.onNext(null);
                if (z) {
                    this.g.lazySet(null);
                    Throwable th = this.f;
                    if (th != null) {
                        subscriber.onError(th);
                        return;
                    } else {
                        subscriber.onComplete();
                        return;
                    }
                }
                iAddAndGet = this.j.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            this.g.lazySet(null);
            return;
        }
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue2 = this.b;
        boolean z2 = !this.d;
        int iAddAndGet3 = iAddAndGet;
        while (true) {
            long j2 = this.k.get();
            long j3 = 0;
            while (true) {
                if (j2 == j3) {
                    j = j3;
                    break;
                }
                boolean z3 = this.e;
                T tPoll = spscLinkedArrayQueue2.poll();
                int i2 = tPoll == null ? iAddAndGet : 0;
                j = j3;
                if (a(z2, z3, i2, subscriber, spscLinkedArrayQueue2)) {
                    return;
                }
                if (i2 != 0) {
                    break;
                }
                subscriber.onNext(tPoll);
                j3 = j + 1;
                iAddAndGet = 1;
            }
            if (j2 == j3 && a(z2, this.e, spscLinkedArrayQueue2.isEmpty(), subscriber, spscLinkedArrayQueue2)) {
                return;
            }
            if (j != 0 && j2 != Long.MAX_VALUE) {
                this.k.addAndGet(-j);
            }
            iAddAndGet3 = this.j.addAndGet(-iAddAndGet3);
            if (iAddAndGet3 == 0) {
                return;
            } else {
                iAddAndGet = 1;
            }
        }
    }

    @Override // io.reactivex.processors.FlowableProcessor
    @Nullable
    public Throwable getThrowable() {
        if (this.e) {
            return this.f;
        }
        return null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasComplete() {
        return this.e && this.f == null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.g.get() != null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasThrowable() {
        return this.e && this.f != null;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.e || this.h) {
            return;
        }
        this.e = true;
        a();
        b();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.e || this.h) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.f = th;
        this.e = true;
        a();
        b();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.e || this.h) {
            return;
        }
        this.b.offer(t);
        b();
    }

    @Override // org.reactivestreams.Subscriber, io.reactivex.FlowableSubscriber
    public void onSubscribe(Subscription subscription) {
        if (this.e || this.h) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.i.get() || !this.i.compareAndSet(false, true)) {
            EmptySubscription.error(new IllegalStateException("This processor allows only a single Subscriber"), subscriber);
            return;
        }
        subscriber.onSubscribe(this.j);
        this.g.set(subscriber);
        if (this.h) {
            this.g.lazySet(null);
        } else {
            b();
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int i) {
        return new UnicastProcessor<>(i, null, true);
    }

    public boolean a(boolean z, boolean z2, boolean z3, Subscriber<? super T> subscriber, SpscLinkedArrayQueue<T> spscLinkedArrayQueue) {
        if (this.h) {
            spscLinkedArrayQueue.clear();
            this.g.lazySet(null);
            return true;
        }
        if (!z2) {
            return false;
        }
        if (z && this.f != null) {
            spscLinkedArrayQueue.clear();
            this.g.lazySet(null);
            subscriber.onError(this.f);
            return true;
        }
        if (!z3) {
            return false;
        }
        Throwable th = this.f;
        this.g.lazySet(null);
        if (th != null) {
            subscriber.onError(th);
        } else {
            subscriber.onComplete();
        }
        return true;
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(boolean z) {
        return new UnicastProcessor<>(Flowable.bufferSize(), null, z);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int i, Runnable runnable) {
        ObjectHelper.requireNonNull(runnable, "onTerminate");
        return new UnicastProcessor<>(i, runnable, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int i, Runnable runnable, boolean z) {
        ObjectHelper.requireNonNull(runnable, "onTerminate");
        return new UnicastProcessor<>(i, runnable, z);
    }
}
