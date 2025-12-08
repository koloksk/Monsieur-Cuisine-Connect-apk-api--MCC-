package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Action;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableOnBackpressureBuffer<T> extends zk<T, T> {
    public final int b;
    public final boolean c;
    public final boolean d;
    public final Action e;

    public static final class a<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
        public static final long serialVersionUID = -2514538129242366402L;
        public final Subscriber<? super T> a;
        public final SimplePlainQueue<T> b;
        public final boolean c;
        public final Action d;
        public Subscription e;
        public volatile boolean f;
        public volatile boolean g;
        public Throwable h;
        public final AtomicLong i = new AtomicLong();
        public boolean j;

        public a(Subscriber<? super T> subscriber, int i, boolean z, boolean z2, Action action) {
            this.a = subscriber;
            this.d = action;
            this.c = z2;
            this.b = z ? new SpscLinkedArrayQueue<>(i) : new SpscArrayQueue<>(i);
        }

        public boolean a(boolean z, boolean z2, Subscriber<? super T> subscriber) {
            if (this.f) {
                this.b.clear();
                return true;
            }
            if (!z) {
                return false;
            }
            if (this.c) {
                if (!z2) {
                    return false;
                }
                Throwable th = this.h;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
            Throwable th2 = this.h;
            if (th2 != null) {
                this.b.clear();
                subscriber.onError(th2);
                return true;
            }
            if (!z2) {
                return false;
            }
            subscriber.onComplete();
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.f) {
                return;
            }
            this.f = true;
            this.e.cancel();
            if (this.j || getAndIncrement() != 0) {
                return;
            }
            this.b.clear();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.b.clear();
        }

        public void drain() {
            if (getAndIncrement() == 0) {
                SimplePlainQueue<T> simplePlainQueue = this.b;
                Subscriber<? super T> subscriber = this.a;
                int iAddAndGet = 1;
                while (!a(this.g, simplePlainQueue.isEmpty(), subscriber)) {
                    long j = this.i.get();
                    long j2 = 0;
                    while (j2 != j) {
                        boolean z = this.g;
                        T tPoll = simplePlainQueue.poll();
                        boolean z2 = tPoll == null;
                        if (a(z, z2, subscriber)) {
                            return;
                        }
                        if (z2) {
                            break;
                        }
                        subscriber.onNext(tPoll);
                        j2++;
                    }
                    if (j2 == j && a(this.g, simplePlainQueue.isEmpty(), subscriber)) {
                        return;
                    }
                    if (j2 != 0 && j != Long.MAX_VALUE) {
                        this.i.addAndGet(-j2);
                    }
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.b.isEmpty();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.g = true;
            if (this.j) {
                this.a.onComplete();
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.h = th;
            this.g = true;
            if (this.j) {
                this.a.onError(th);
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.b.offer(t)) {
                if (this.j) {
                    this.a.onNext(null);
                    return;
                } else {
                    drain();
                    return;
                }
            }
            this.e.cancel();
            MissingBackpressureException missingBackpressureException = new MissingBackpressureException("Buffer is full");
            try {
                this.d.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                missingBackpressureException.initCause(th);
            }
            onError(missingBackpressureException);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            return this.b.poll();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.j || !SubscriptionHelper.validate(j)) {
                return;
            }
            BackpressureHelper.add(this.i, j);
            drain();
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.j = true;
            return 2;
        }
    }

    public FlowableOnBackpressureBuffer(Flowable<T> flowable, int i, boolean z, boolean z2, Action action) {
        super(flowable);
        this.b = i;
        this.c = z;
        this.d = z2;
        this.e = action;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d, this.e));
    }
}
