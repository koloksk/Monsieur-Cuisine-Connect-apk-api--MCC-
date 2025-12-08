package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableZip<T, R> extends Flowable<R> {
    public final Publisher<? extends T>[] b;
    public final Iterable<? extends Publisher<? extends T>> c;
    public final Function<? super Object[], ? extends R> d;
    public final int e;
    public final boolean f;

    public static final class a<T, R> extends AtomicInteger implements Subscription {
        public static final long serialVersionUID = -2434867452883857743L;
        public final Subscriber<? super R> a;
        public final b<T, R>[] b;
        public final Function<? super Object[], ? extends R> c;
        public final AtomicLong d;
        public final AtomicThrowable e;
        public final boolean f;
        public volatile boolean g;
        public final Object[] h;

        public a(Subscriber<? super R> subscriber, Function<? super Object[], ? extends R> function, int i, int i2, boolean z) {
            this.a = subscriber;
            this.c = function;
            this.f = z;
            b<T, R>[] bVarArr = new b[i];
            for (int i3 = 0; i3 < i; i3++) {
                bVarArr[i3] = new b<>(this, i2);
            }
            this.h = new Object[i];
            this.b = bVarArr;
            this.d = new AtomicLong();
            this.e = new AtomicThrowable();
        }

        public void a() {
            for (b<T, R> bVar : this.b) {
                if (bVar == null) {
                    throw null;
                }
                SubscriptionHelper.cancel(bVar);
            }
        }

        public void b() {
            boolean z;
            T tPoll;
            boolean z2;
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super R> subscriber = this.a;
            b<T, R>[] bVarArr = this.b;
            int length = bVarArr.length;
            Object[] objArr = this.h;
            int iAddAndGet = 1;
            do {
                long j = this.d.get();
                long j2 = 0;
                while (j != j2) {
                    if (this.g) {
                        return;
                    }
                    if (!this.f && this.e.get() != null) {
                        a();
                        subscriber.onError(this.e.terminate());
                        return;
                    }
                    boolean z3 = false;
                    for (int i = 0; i < length; i++) {
                        b<T, R> bVar = bVarArr[i];
                        if (objArr[i] == null) {
                            try {
                                z = bVar.f;
                                SimpleQueue<T> simpleQueue = bVar.d;
                                tPoll = simpleQueue != null ? simpleQueue.poll() : null;
                                z2 = tPoll == null;
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.e.addThrowable(th);
                                if (!this.f) {
                                    a();
                                    subscriber.onError(this.e.terminate());
                                    return;
                                }
                            }
                            if (z && z2) {
                                a();
                                if (this.e.get() != null) {
                                    subscriber.onError(this.e.terminate());
                                    return;
                                } else {
                                    subscriber.onComplete();
                                    return;
                                }
                            }
                            if (z2) {
                                z3 = true;
                            } else {
                                objArr[i] = tPoll;
                            }
                        }
                    }
                    if (z3) {
                        break;
                    }
                    try {
                        subscriber.onNext((Object) ObjectHelper.requireNonNull(this.c.apply(objArr.clone()), "The zipper returned a null value"));
                        j2++;
                        Arrays.fill(objArr, (Object) null);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        a();
                        this.e.addThrowable(th2);
                        subscriber.onError(this.e.terminate());
                        return;
                    }
                }
                if (j == j2) {
                    if (this.g) {
                        return;
                    }
                    if (!this.f && this.e.get() != null) {
                        a();
                        subscriber.onError(this.e.terminate());
                        return;
                    }
                    for (int i2 = 0; i2 < length; i2++) {
                        b<T, R> bVar2 = bVarArr[i2];
                        if (objArr[i2] == null) {
                            try {
                                boolean z4 = bVar2.f;
                                SimpleQueue<T> simpleQueue2 = bVar2.d;
                                T tPoll2 = simpleQueue2 != null ? simpleQueue2.poll() : null;
                                boolean z5 = tPoll2 == null;
                                if (z4 && z5) {
                                    a();
                                    if (this.e.get() != null) {
                                        subscriber.onError(this.e.terminate());
                                        return;
                                    } else {
                                        subscriber.onComplete();
                                        return;
                                    }
                                }
                                if (!z5) {
                                    objArr[i2] = tPoll2;
                                }
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                this.e.addThrowable(th3);
                                if (!this.f) {
                                    a();
                                    subscriber.onError(this.e.terminate());
                                    return;
                                }
                            }
                        }
                    }
                }
                if (j2 != 0) {
                    for (b<T, R> bVar3 : bVarArr) {
                        bVar3.request(j2);
                    }
                    if (j != Long.MAX_VALUE) {
                        this.d.addAndGet(-j2);
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.g) {
                return;
            }
            this.g = true;
            a();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.d, j);
                b();
            }
        }
    }

    public static final class b<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = -4627193790118206028L;
        public final a<T, R> a;
        public final int b;
        public final int c;
        public SimpleQueue<T> d;
        public long e;
        public volatile boolean f;
        public int g;

        public b(a<T, R> aVar, int i) {
            this.a = aVar;
            this.b = i;
            this.c = i - (i >> 2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.f = true;
            this.a.b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            a<T, R> aVar = this.a;
            if (!aVar.e.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.f = true;
                aVar.b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.g != 2) {
                this.d.offer(t);
            }
            this.a.b();
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.g = iRequestFusion;
                        this.d = queueSubscription;
                        this.f = true;
                        this.a.b();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.g = iRequestFusion;
                        this.d = queueSubscription;
                        subscription.request(this.b);
                        return;
                    }
                }
                this.d = new SpscArrayQueue(this.b);
                subscription.request(this.b);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.g != 1) {
                long j2 = this.e + j;
                if (j2 < this.c) {
                    this.e = j2;
                } else {
                    this.e = 0L;
                    get().request(j2);
                }
            }
        }
    }

    public FlowableZip(Publisher<? extends T>[] publisherArr, Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.b = publisherArr;
        this.c = iterable;
        this.d = function;
        this.e = i;
        this.f = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        int length;
        Publisher<? extends T>[] publisherArr = this.b;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            length = 0;
            for (Publisher<? extends T> publisher : this.c) {
                if (length == publisherArr.length) {
                    Publisher<? extends T>[] publisherArr2 = new Publisher[(length >> 2) + length];
                    System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                    publisherArr = publisherArr2;
                }
                publisherArr[length] = publisher;
                length++;
            }
        } else {
            length = publisherArr.length;
        }
        int i = length;
        if (i == 0) {
            EmptySubscription.complete(subscriber);
            return;
        }
        a aVar = new a(subscriber, this.d, i, this.e, this.f);
        subscriber.onSubscribe(aVar);
        b<T, R>[] bVarArr = aVar.b;
        for (int i2 = 0; i2 < i && !aVar.g; i2++) {
            if (!aVar.f && aVar.e.get() != null) {
                return;
            }
            publisherArr[i2].subscribe(bVarArr[i2]);
        }
    }
}
