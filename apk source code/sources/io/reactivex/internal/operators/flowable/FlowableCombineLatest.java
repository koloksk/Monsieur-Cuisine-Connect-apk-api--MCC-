package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableMap;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableCombineLatest<T, R> extends Flowable<R> {

    @Nullable
    public final Publisher<? extends T>[] b;

    @Nullable
    public final Iterable<? extends Publisher<? extends T>> c;
    public final Function<? super Object[], ? extends R> d;
    public final int e;
    public final boolean f;

    public static final class b<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        public static final long serialVersionUID = -8730235182291002949L;
        public final a<T, ?> a;
        public final int b;
        public final int c;
        public final int d;
        public int e;

        public b(a<T, ?> aVar, int i, int i2) {
            this.a = aVar;
            this.b = i;
            this.c = i2;
            this.d = i2 - (i2 >> 2);
        }

        public void a() {
            int i = this.e + 1;
            if (i != this.d) {
                this.e = i;
            } else {
                this.e = 0;
                get().request(i);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.a(this.b);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            a<T, ?> aVar = this.a;
            int i = this.b;
            if (!ExceptionHelper.addThrowable(aVar.m, th)) {
                RxJavaPlugins.onError(th);
            } else {
                if (aVar.f) {
                    aVar.a(i);
                    return;
                }
                aVar.d();
                aVar.l = true;
                aVar.drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.a.a(this.b, t);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, this.c);
        }
    }

    public final class c implements Function<T, R> {
        public c() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.functions.Function
        public R apply(T t) throws Exception {
            return FlowableCombineLatest.this.d.apply(new Object[]{t});
        }
    }

    public FlowableCombineLatest(@NonNull Publisher<? extends T>[] publisherArr, @NonNull Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.b = publisherArr;
        this.c = null;
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
            try {
                Iterator it = (Iterator) ObjectHelper.requireNonNull(this.c.iterator(), "The iterator returned is null");
                length = 0;
                while (it.hasNext()) {
                    try {
                        try {
                            Publisher<? extends T> publisher = (Publisher) ObjectHelper.requireNonNull(it.next(), "The publisher returned by the iterator is null");
                            if (length == publisherArr.length) {
                                Publisher<? extends T>[] publisherArr2 = new Publisher[(length >> 2) + length];
                                System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                                publisherArr = publisherArr2;
                            }
                            publisherArr[length] = publisher;
                            length++;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            EmptySubscription.error(th, subscriber);
                            return;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        EmptySubscription.error(th2, subscriber);
                        return;
                    }
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                EmptySubscription.error(th3, subscriber);
                return;
            }
        } else {
            length = publisherArr.length;
        }
        int i = length;
        if (i == 0) {
            EmptySubscription.complete(subscriber);
            return;
        }
        if (i == 1) {
            publisherArr[0].subscribe(new FlowableMap.b(subscriber, new c()));
            return;
        }
        a aVar = new a(subscriber, this.d, i, this.e, this.f);
        subscriber.onSubscribe(aVar);
        b<T>[] bVarArr = aVar.c;
        for (int i2 = 0; i2 < i && !aVar.l && !aVar.j; i2++) {
            publisherArr[i2].subscribe(bVarArr[i2]);
        }
    }

    public FlowableCombineLatest(@NonNull Iterable<? extends Publisher<? extends T>> iterable, @NonNull Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.b = null;
        this.c = iterable;
        this.d = function;
        this.e = i;
        this.f = z;
    }

    public static final class a<T, R> extends BasicIntQueueSubscription<R> {
        public static final long serialVersionUID = -5082275438355852221L;
        public final Subscriber<? super R> a;
        public final Function<? super Object[], ? extends R> b;
        public final b<T>[] c;
        public final SpscLinkedArrayQueue<Object> d;
        public final Object[] e;
        public final boolean f;
        public boolean g;
        public int h;
        public int i;
        public volatile boolean j;
        public final AtomicLong k;
        public volatile boolean l;
        public final AtomicReference<Throwable> m;

        public a(Subscriber<? super R> subscriber, Function<? super Object[], ? extends R> function, int i, int i2, boolean z) {
            this.a = subscriber;
            this.b = function;
            b<T>[] bVarArr = new b[i];
            for (int i3 = 0; i3 < i; i3++) {
                bVarArr[i3] = new b<>(this, i3, i2);
            }
            this.c = bVarArr;
            this.e = new Object[i];
            this.d = new SpscLinkedArrayQueue<>(i2);
            this.k = new AtomicLong();
            this.m = new AtomicReference<>();
            this.f = z;
        }

        public void a(int i, T t) {
            boolean z;
            synchronized (this) {
                Object[] objArr = this.e;
                int i2 = this.h;
                if (objArr[i] == null) {
                    i2++;
                    this.h = i2;
                }
                objArr[i] = t;
                if (objArr.length == i2) {
                    this.d.offer(this.c[i], objArr.clone());
                    z = false;
                } else {
                    z = true;
                }
            }
            if (z) {
                this.c[i].a();
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.j = true;
            d();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.d.clear();
        }

        public void d() {
            for (b<T> bVar : this.c) {
                if (bVar == null) {
                    throw null;
                }
                SubscriptionHelper.cancel(bVar);
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            if (this.g) {
                Subscriber<? super R> subscriber = this.a;
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.d;
                while (!this.j) {
                    Throwable th = this.m.get();
                    if (th != null) {
                        spscLinkedArrayQueue.clear();
                        subscriber.onError(th);
                        return;
                    }
                    boolean z = this.l;
                    boolean zIsEmpty = spscLinkedArrayQueue.isEmpty();
                    if (!zIsEmpty) {
                        subscriber.onNext(null);
                    }
                    if (z && zIsEmpty) {
                        subscriber.onComplete();
                        return;
                    } else {
                        iAddAndGet = addAndGet(-iAddAndGet);
                        if (iAddAndGet == 0) {
                            return;
                        }
                    }
                }
                spscLinkedArrayQueue.clear();
                return;
            }
            Subscriber<? super R> subscriber2 = this.a;
            SpscLinkedArrayQueue<?> spscLinkedArrayQueue2 = this.d;
            int iAddAndGet2 = 1;
            do {
                long j = this.k.get();
                long j2 = 0;
                while (j2 != j) {
                    boolean z2 = this.l;
                    Object objPoll = spscLinkedArrayQueue2.poll();
                    boolean z3 = objPoll == null;
                    if (a(z2, z3, subscriber2, spscLinkedArrayQueue2)) {
                        return;
                    }
                    if (z3) {
                        break;
                    }
                    try {
                        subscriber2.onNext((Object) ObjectHelper.requireNonNull(this.b.apply((Object[]) spscLinkedArrayQueue2.poll()), "The combiner returned a null value"));
                        ((b) objPoll).a();
                        j2++;
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        d();
                        ExceptionHelper.addThrowable(this.m, th2);
                        subscriber2.onError(ExceptionHelper.terminate(this.m));
                        return;
                    }
                }
                if (j2 == j && a(this.l, spscLinkedArrayQueue2.isEmpty(), subscriber2, spscLinkedArrayQueue2)) {
                    return;
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    this.k.addAndGet(-j2);
                }
                iAddAndGet2 = addAndGet(-iAddAndGet2);
            } while (iAddAndGet2 != 0);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.d.isEmpty();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public R poll() throws Exception {
            Object objPoll = this.d.poll();
            if (objPoll == null) {
                return null;
            }
            R r = (R) ObjectHelper.requireNonNull(this.b.apply((Object[]) this.d.poll()), "The combiner returned a null value");
            ((b) objPoll).a();
            return r;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.k, j);
                drain();
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 4) != 0) {
                return 0;
            }
            int i2 = i & 2;
            this.g = i2 != 0;
            return i2;
        }

        public void a(int i) {
            int i2;
            synchronized (this) {
                Object[] objArr = this.e;
                if (objArr[i] == null || (i2 = this.i + 1) == objArr.length) {
                    this.l = true;
                    drain();
                } else {
                    this.i = i2;
                }
            }
        }

        public boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.j) {
                d();
                spscLinkedArrayQueue.clear();
                return true;
            }
            if (!z) {
                return false;
            }
            if (this.f) {
                if (!z2) {
                    return false;
                }
                d();
                Throwable thTerminate = ExceptionHelper.terminate(this.m);
                if (thTerminate != null && thTerminate != ExceptionHelper.TERMINATED) {
                    subscriber.onError(thTerminate);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
            Throwable thTerminate2 = ExceptionHelper.terminate(this.m);
            if (thTerminate2 != null && thTerminate2 != ExceptionHelper.TERMINATED) {
                d();
                spscLinkedArrayQueue.clear();
                subscriber.onError(thTerminate2);
                return true;
            }
            if (!z2) {
                return false;
            }
            d();
            subscriber.onComplete();
            return true;
        }
    }
}
