package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableFlatMap<T, U> extends zk<T, U> {
    public final Function<? super T, ? extends Publisher<? extends U>> b;
    public final boolean c;
    public final int d;
    public final int e;

    public static final class a<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<U>, Disposable {
        public static final long serialVersionUID = -4606175640614850599L;
        public final long a;
        public final b<T, U> b;
        public final int c;
        public final int d;
        public volatile boolean e;
        public volatile SimpleQueue<U> f;
        public long g;
        public int h;

        public a(b<T, U> bVar, long j) {
            this.a = j;
            this.b = bVar;
            int i = bVar.e;
            this.d = i;
            this.c = i >> 2;
        }

        public void a(long j) {
            if (this.h != 1) {
                long j2 = this.g + j;
                if (j2 < this.c) {
                    this.g = j2;
                } else {
                    this.g = 0L;
                    get().request(j2);
                }
            }
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
            this.e = true;
            this.b.b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            lazySet(SubscriptionHelper.CANCELLED);
            b<T, U> bVar = this.b;
            if (!bVar.h.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            if (!bVar.c) {
                bVar.l.cancel();
                for (a<?, ?> aVar : bVar.j.getAndSet(b.s)) {
                    if (aVar == null) {
                        throw null;
                    }
                    SubscriptionHelper.cancel(aVar);
                }
            }
            bVar.b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u) {
            if (this.h == 2) {
                this.b.b();
                return;
            }
            b<T, U> bVar = this.b;
            if (bVar.get() == 0 && bVar.compareAndSet(0, 1)) {
                long j = bVar.k.get();
                SimpleQueue spscArrayQueue = this.f;
                if (j == 0 || !(spscArrayQueue == null || spscArrayQueue.isEmpty())) {
                    if (spscArrayQueue == null && (spscArrayQueue = this.f) == null) {
                        spscArrayQueue = new SpscArrayQueue(bVar.e);
                        this.f = spscArrayQueue;
                    }
                    if (!spscArrayQueue.offer(u)) {
                        bVar.onError(new MissingBackpressureException("Inner queue full?!"));
                        return;
                    }
                } else {
                    bVar.a.onNext(u);
                    if (j != Long.MAX_VALUE) {
                        bVar.k.decrementAndGet();
                    }
                    a(1L);
                }
                if (bVar.decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue spscArrayQueue2 = this.f;
                if (spscArrayQueue2 == null) {
                    spscArrayQueue2 = new SpscArrayQueue(bVar.e);
                    this.f = spscArrayQueue2;
                }
                if (!spscArrayQueue2.offer(u)) {
                    bVar.onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                } else if (bVar.getAndIncrement() != 0) {
                    return;
                }
            }
            bVar.c();
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.h = iRequestFusion;
                        this.f = queueSubscription;
                        this.e = true;
                        this.b.b();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.h = iRequestFusion;
                        this.f = queueSubscription;
                    }
                }
                subscription.request(this.d);
            }
        }
    }

    public FlowableFlatMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        super(flowable);
        this.b = function;
        this.c = z;
        this.d = i;
        this.e = i2;
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        return new b(subscriber, function, z, i, i2);
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super U> subscriber) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.b)) {
            return;
        }
        this.source.subscribe((FlowableSubscriber) subscribe(subscriber, this.b, this.c, this.d, this.e));
    }

    public static final class b<T, U> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final a<?, ?>[] r = new a[0];
        public static final a<?, ?>[] s = new a[0];
        public static final long serialVersionUID = -2117620485640801370L;
        public final Subscriber<? super U> a;
        public final Function<? super T, ? extends Publisher<? extends U>> b;
        public final boolean c;
        public final int d;
        public final int e;
        public volatile SimplePlainQueue<U> f;
        public volatile boolean g;
        public volatile boolean i;
        public Subscription l;
        public long m;
        public long n;
        public int o;
        public int p;
        public final int q;
        public final AtomicThrowable h = new AtomicThrowable();
        public final AtomicReference<a<?, ?>[]> j = new AtomicReference<>();
        public final AtomicLong k = new AtomicLong();

        public b(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
            this.a = subscriber;
            this.b = function;
            this.c = z;
            this.d = i;
            this.e = i2;
            this.q = Math.max(1, i >> 1);
            this.j.lazySet(r);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a(a<T, U> aVar) {
            a<?, ?>[] aVarArr;
            a<?, ?>[] aVarArr2;
            do {
                aVarArr = this.j.get();
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
                    aVarArr2 = r;
                } else {
                    a<?, ?>[] aVarArr3 = new a[length - 1];
                    System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                    System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                    aVarArr2 = aVarArr3;
                }
            } while (!this.j.compareAndSet(aVarArr, aVarArr2));
        }

        public void b() {
            if (getAndIncrement() == 0) {
                c();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void c() {
            long j;
            long j2;
            boolean z;
            int i;
            int i2;
            long j3;
            Object obj;
            Subscriber<? super U> subscriber = this.a;
            int iAddAndGet = 1;
            while (!a()) {
                SimplePlainQueue<U> simplePlainQueue = this.f;
                long jAddAndGet = this.k.get();
                boolean z2 = jAddAndGet == Long.MAX_VALUE;
                long j4 = 0;
                long j5 = 0;
                if (simplePlainQueue != null) {
                    do {
                        long j6 = 0;
                        obj = null;
                        while (true) {
                            if (jAddAndGet == 0) {
                                break;
                            }
                            U uPoll = simplePlainQueue.poll();
                            if (a()) {
                                return;
                            }
                            if (uPoll == null) {
                                obj = uPoll;
                                break;
                            }
                            subscriber.onNext(uPoll);
                            j5++;
                            j6++;
                            jAddAndGet--;
                            obj = uPoll;
                        }
                        if (j6 != 0) {
                            jAddAndGet = z2 ? Long.MAX_VALUE : this.k.addAndGet(-j6);
                        }
                        if (jAddAndGet == 0) {
                            break;
                        }
                    } while (obj != null);
                }
                boolean z3 = this.g;
                SimplePlainQueue<U> simplePlainQueue2 = this.f;
                a<?, ?>[] aVarArr = this.j.get();
                int length = aVarArr.length;
                if (z3 && ((simplePlainQueue2 == null || simplePlainQueue2.isEmpty()) && length == 0)) {
                    Throwable thTerminate = this.h.terminate();
                    if (thTerminate != ExceptionHelper.TERMINATED) {
                        if (thTerminate == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(thTerminate);
                            return;
                        }
                    }
                    return;
                }
                int i3 = iAddAndGet;
                if (length != 0) {
                    long j7 = this.n;
                    int i4 = this.o;
                    if (length <= i4 || aVarArr[i4].a != j7) {
                        if (length <= i4) {
                            i4 = 0;
                        }
                        for (int i5 = 0; i5 < length && aVarArr[i4].a != j7; i5++) {
                            i4++;
                            if (i4 == length) {
                                i4 = 0;
                            }
                        }
                        this.o = i4;
                        this.n = aVarArr[i4].a;
                    }
                    int i6 = i4;
                    boolean z4 = false;
                    int i7 = 0;
                    while (true) {
                        if (i7 >= length) {
                            z = z4;
                            break;
                        }
                        if (a()) {
                            return;
                        }
                        a<T, U> aVar = aVarArr[i6];
                        Object obj2 = null;
                        while (!a()) {
                            SimpleQueue<U> simpleQueue = aVar.f;
                            if (simpleQueue == null) {
                                i = length;
                            } else {
                                i = length;
                                Object obj3 = obj2;
                                long j8 = j4;
                                while (true) {
                                    if (jAddAndGet == j4) {
                                        break;
                                    }
                                    try {
                                        U uPoll2 = simpleQueue.poll();
                                        if (uPoll2 == null) {
                                            obj3 = uPoll2;
                                            j4 = 0;
                                            break;
                                        }
                                        subscriber.onNext(uPoll2);
                                        if (a()) {
                                            return;
                                        }
                                        jAddAndGet--;
                                        j8++;
                                        obj3 = uPoll2;
                                        j4 = 0;
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        SubscriptionHelper.cancel(aVar);
                                        this.h.addThrowable(th);
                                        if (!this.c) {
                                            this.l.cancel();
                                        }
                                        if (a()) {
                                            return;
                                        }
                                        a(aVar);
                                        i7++;
                                        z4 = true;
                                        i2 = 1;
                                    }
                                }
                                if (j8 != j4) {
                                    jAddAndGet = !z2 ? this.k.addAndGet(-j8) : Long.MAX_VALUE;
                                    aVar.a(j8);
                                    j3 = 0;
                                } else {
                                    j3 = j4;
                                }
                                if (jAddAndGet != j3 && obj3 != null) {
                                    length = i;
                                    obj2 = obj3;
                                    j4 = 0;
                                }
                            }
                            boolean z5 = aVar.e;
                            SimpleQueue<U> simpleQueue2 = aVar.f;
                            if (z5 && (simpleQueue2 == null || simpleQueue2.isEmpty())) {
                                a(aVar);
                                if (a()) {
                                    return;
                                }
                                j5++;
                                z4 = true;
                            }
                            if (jAddAndGet == 0) {
                                z = z4;
                                break;
                            }
                            i6++;
                            if (i6 == i) {
                                i6 = 0;
                            }
                            i2 = 1;
                            i7 += i2;
                            length = i;
                            j4 = 0;
                        }
                        return;
                    }
                    this.o = i6;
                    this.n = aVarArr[i6].a;
                    j2 = j5;
                    j = 0;
                } else {
                    j = 0;
                    j2 = j5;
                    z = false;
                }
                if (j2 != j && !this.i) {
                    this.l.request(j2);
                }
                if (z) {
                    iAddAndGet = i3;
                } else {
                    iAddAndGet = addAndGet(-i3);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SimplePlainQueue<U> simplePlainQueue;
            a<?, ?>[] andSet;
            if (this.i) {
                return;
            }
            this.i = true;
            this.l.cancel();
            a<?, ?>[] aVarArr = this.j.get();
            a<?, ?>[] aVarArr2 = s;
            if (aVarArr != aVarArr2 && (andSet = this.j.getAndSet(aVarArr2)) != s) {
                for (a<?, ?> aVar : andSet) {
                    if (aVar == null) {
                        throw null;
                    }
                    SubscriptionHelper.cancel(aVar);
                }
                Throwable thTerminate = this.h.terminate();
                if (thTerminate != null && thTerminate != ExceptionHelper.TERMINATED) {
                    RxJavaPlugins.onError(thTerminate);
                }
            }
            if (getAndIncrement() != 0 || (simplePlainQueue = this.f) == null) {
                return;
            }
            simplePlainQueue.clear();
        }

        public SimpleQueue<U> d() {
            SimplePlainQueue<U> spscLinkedArrayQueue = this.f;
            if (spscLinkedArrayQueue == null) {
                spscLinkedArrayQueue = this.d == Integer.MAX_VALUE ? new SpscLinkedArrayQueue<>(this.e) : new SpscArrayQueue<>(this.d);
                this.f = spscLinkedArrayQueue;
            }
            return spscLinkedArrayQueue;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.g) {
                return;
            }
            this.g = true;
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.h.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = true;
            if (!this.c) {
                for (a<?, ?> aVar : this.j.getAndSet(s)) {
                    if (aVar == null) {
                        throw null;
                    }
                    SubscriptionHelper.cancel(aVar);
                }
            }
            b();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.g) {
                return;
            }
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null Publisher");
                boolean z = false;
                if (!(publisher instanceof Callable)) {
                    long j = this.m;
                    this.m = 1 + j;
                    a<?, ?> aVar = new a<>(this, j);
                    while (true) {
                        a<?, ?>[] aVarArr = this.j.get();
                        if (aVarArr == s) {
                            SubscriptionHelper.cancel(aVar);
                            break;
                        }
                        int length = aVarArr.length;
                        a<?, ?>[] aVarArr2 = new a[length + 1];
                        System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
                        aVarArr2[length] = aVar;
                        if (this.j.compareAndSet(aVarArr, aVarArr2)) {
                            z = true;
                            break;
                        }
                    }
                    if (z) {
                        publisher.subscribe(aVar);
                        return;
                    }
                    return;
                }
                try {
                    Object objCall = ((Callable) publisher).call();
                    if (objCall == null) {
                        if (this.d == Integer.MAX_VALUE || this.i) {
                            return;
                        }
                        int i = this.p + 1;
                        this.p = i;
                        int i2 = this.q;
                        if (i == i2) {
                            this.p = 0;
                            this.l.request(i2);
                            return;
                        }
                        return;
                    }
                    if (get() == 0 && compareAndSet(0, 1)) {
                        long j2 = this.k.get();
                        SimpleQueue<U> simpleQueueD = this.f;
                        if (j2 == 0 || !(simpleQueueD == 0 || simpleQueueD.isEmpty())) {
                            if (simpleQueueD == 0) {
                                simpleQueueD = d();
                            }
                            if (!simpleQueueD.offer(objCall)) {
                                onError(new IllegalStateException("Scalar queue full?!"));
                                return;
                            }
                        } else {
                            this.a.onNext(objCall);
                            if (j2 != Long.MAX_VALUE) {
                                this.k.decrementAndGet();
                            }
                            if (this.d != Integer.MAX_VALUE && !this.i) {
                                int i3 = this.p + 1;
                                this.p = i3;
                                int i4 = this.q;
                                if (i3 == i4) {
                                    this.p = 0;
                                    this.l.request(i4);
                                }
                            }
                        }
                        if (decrementAndGet() == 0) {
                            return;
                        }
                    } else if (!d().offer(objCall)) {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return;
                    } else if (getAndIncrement() != 0) {
                        return;
                    }
                    c();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.h.addThrowable(th);
                    b();
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.l.cancel();
                onError(th2);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.l, subscription)) {
                this.l = subscription;
                this.a.onSubscribe(this);
                if (this.i) {
                    return;
                }
                int i = this.d;
                if (i == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request(i);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.k, j);
                b();
            }
        }

        public boolean a() {
            if (this.i) {
                SimplePlainQueue<U> simplePlainQueue = this.f;
                if (simplePlainQueue != null) {
                    simplePlainQueue.clear();
                }
                return true;
            }
            if (this.c || this.h.get() == null) {
                return false;
            }
            SimplePlainQueue<U> simplePlainQueue2 = this.f;
            if (simplePlainQueue2 != null) {
                simplePlainQueue2.clear();
            }
            Throwable thTerminate = this.h.terminate();
            if (thTerminate != ExceptionHelper.TERMINATED) {
                this.a.onError(thTerminate);
            }
            return true;
        }
    }
}
