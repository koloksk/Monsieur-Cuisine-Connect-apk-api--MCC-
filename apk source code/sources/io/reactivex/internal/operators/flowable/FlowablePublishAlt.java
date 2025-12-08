package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowablePublishAlt<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T>, ResettableConnectable {
    public final Publisher<T> b;
    public final int c;
    public final AtomicReference<b<T>> d = new AtomicReference<>();

    public static final class a<T> extends AtomicLong implements Subscription {
        public static final long serialVersionUID = 2845000326761540265L;
        public final Subscriber<? super T> a;
        public final b<T> b;
        public long c;

        public a(Subscriber<? super T> subscriber, b<T> bVar) {
            this.a = subscriber;
            this.b = bVar;
        }

        public boolean a() {
            return get() == Long.MIN_VALUE;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.b.a(this);
                this.b.a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.addCancel(this, j);
            this.b.a();
        }
    }

    public FlowablePublishAlt(Publisher<T> publisher, int i) {
        this.b = publisher;
        this.c = i;
    }

    @Override // io.reactivex.flowables.ConnectableFlowable
    public void connect(Consumer<? super Disposable> consumer) {
        b<T> bVar;
        while (true) {
            bVar = this.d.get();
            if (bVar != null && !bVar.isDisposed()) {
                break;
            }
            b<T> bVar2 = new b<>(this.d, this.c);
            if (this.d.compareAndSet(bVar, bVar2)) {
                bVar = bVar2;
                break;
            }
        }
        boolean z = !bVar.c.get() && bVar.c.compareAndSet(false, true);
        try {
            consumer.accept(bVar);
            if (z) {
                this.b.subscribe(bVar);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public int publishBufferSize() {
        return this.c;
    }

    @Override // io.reactivex.internal.disposables.ResettableConnectable
    public void resetIf(Disposable disposable) {
        this.d.compareAndSet((b) disposable, null);
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamPublisher
    public Publisher<T> source() {
        return this.b;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        b<T> bVar;
        boolean z;
        while (true) {
            bVar = this.d.get();
            if (bVar != null) {
                break;
            }
            b<T> bVar2 = new b<>(this.d, this.c);
            if (this.d.compareAndSet(bVar, bVar2)) {
                bVar = bVar2;
                break;
            }
        }
        a<T> aVar = new a<>(subscriber, bVar);
        subscriber.onSubscribe(aVar);
        while (true) {
            a<T>[] aVarArr = bVar.d.get();
            z = false;
            if (aVarArr == b.l) {
                break;
            }
            int length = aVarArr.length;
            a<T>[] aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
            if (bVar.d.compareAndSet(aVarArr, aVarArr2)) {
                z = true;
                break;
            }
        }
        if (z) {
            if (aVar.a()) {
                bVar.a(aVar);
                return;
            } else {
                bVar.a();
                return;
            }
        }
        Throwable th = bVar.i;
        if (th != null) {
            subscriber.onError(th);
        } else {
            subscriber.onComplete();
        }
    }

    public static final class b<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
        public static final a[] k = new a[0];
        public static final a[] l = new a[0];
        public static final long serialVersionUID = -1672047311619175801L;
        public final AtomicReference<b<T>> a;
        public final AtomicReference<Subscription> b = new AtomicReference<>();
        public final AtomicBoolean c = new AtomicBoolean();
        public final AtomicReference<a<T>[]> d = new AtomicReference<>(k);
        public final int e;
        public volatile SimpleQueue<T> f;
        public int g;
        public volatile boolean h;
        public Throwable i;
        public int j;

        public b(AtomicReference<b<T>> atomicReference, int i) {
            this.a = atomicReference;
            this.e = i;
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimpleQueue<T> simpleQueue = this.f;
            int i = this.j;
            int i2 = this.e;
            int i3 = i2 - (i2 >> 2);
            boolean z = this.g != 1;
            int iAddAndGet = 1;
            SimpleQueue<T> simpleQueue2 = simpleQueue;
            int i4 = i;
            while (true) {
                if (simpleQueue2 != null) {
                    long jMin = Long.MAX_VALUE;
                    a<T>[] aVarArr = this.d.get();
                    boolean z2 = false;
                    for (a<T> aVar : aVarArr) {
                        long j = aVar.get();
                        if (j != Long.MIN_VALUE) {
                            jMin = Math.min(j - aVar.c, jMin);
                            z2 = true;
                        }
                    }
                    if (!z2) {
                        jMin = 0;
                    }
                    for (long j2 = 0; jMin != j2; j2 = 0) {
                        boolean z3 = this.h;
                        try {
                            T tPoll = simpleQueue2.poll();
                            boolean z4 = tPoll == null;
                            if (a(z3, z4)) {
                                return;
                            }
                            if (z4) {
                                break;
                            }
                            for (a<T> aVar2 : aVarArr) {
                                if (!aVar2.a()) {
                                    aVar2.a.onNext(tPoll);
                                    aVar2.c++;
                                }
                            }
                            if (z && (i4 = i4 + 1) == i3) {
                                this.b.get().request(i3);
                                i4 = 0;
                            }
                            jMin--;
                            if (aVarArr != this.d.get()) {
                                break;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.b.get().cancel();
                            simpleQueue2.clear();
                            this.h = true;
                            a(th);
                            return;
                        }
                    }
                    if (a(this.h, simpleQueue2.isEmpty())) {
                        return;
                    }
                }
                this.j = i4;
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
                if (simpleQueue2 == null) {
                    simpleQueue2 = this.f;
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.d.getAndSet(l);
            this.a.compareAndSet(this, null);
            SubscriptionHelper.cancel(this.b);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.get() == l;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.h = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.i = th;
            this.h = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.g != 0 || this.f.offer(t)) {
                a();
            } else {
                onError(new MissingBackpressureException("Prefetch queue is full?!"));
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.b, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.g = iRequestFusion;
                        this.f = queueSubscription;
                        this.h = true;
                        a();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.g = iRequestFusion;
                        this.f = queueSubscription;
                        subscription.request(this.e);
                        return;
                    }
                }
                this.f = new SpscArrayQueue(this.e);
                subscription.request(this.e);
            }
        }

        public boolean a(boolean z, boolean z2) {
            if (!z || !z2) {
                return false;
            }
            Throwable th = this.i;
            if (th != null) {
                a(th);
                return true;
            }
            for (a<T> aVar : this.d.getAndSet(l)) {
                if (!aVar.a()) {
                    aVar.a.onComplete();
                }
            }
            return true;
        }

        public void a(Throwable th) {
            for (a<T> aVar : this.d.getAndSet(l)) {
                if (!aVar.a()) {
                    aVar.a.onError(th);
                }
            }
        }

        public void a(a<T> aVar) {
            a<T>[] aVarArr;
            a<T>[] aVarArr2;
            do {
                aVarArr = this.d.get();
                int length = aVarArr.length;
                if (length == 0) {
                    return;
                }
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (aVarArr[i2] == aVar) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    aVarArr2 = k;
                } else {
                    a<T>[] aVarArr3 = new a[length - 1];
                    System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                    System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                    aVarArr2 = aVarArr3;
                }
            } while (!this.d.compareAndSet(aVarArr, aVarArr2));
        }
    }
}
