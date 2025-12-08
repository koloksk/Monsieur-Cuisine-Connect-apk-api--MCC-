package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableBufferBoundary<T, U extends Collection<? super T>, Open, Close> extends zk<T, U> {
    public final Callable<U> b;
    public final Publisher<? extends Open> c;
    public final Function<? super Open, ? extends Publisher<? extends Close>> d;

    public static final class b<T, C extends Collection<? super T>> extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        public static final long serialVersionUID = -8498650778633225126L;
        public final a<T, C, ?, ?> a;
        public final long b;

        public b(a<T, C, ?, ?> aVar, long j) {
            this.a = aVar;
            this.b = j;
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
            Subscription subscription = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription != subscriptionHelper) {
                lazySet(subscriptionHelper);
                this.a.a(this, this.b);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Subscription subscription = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription == subscriptionHelper) {
                RxJavaPlugins.onError(th);
                return;
            }
            lazySet(subscriptionHelper);
            a<T, C, ?, ?> aVar = this.a;
            SubscriptionHelper.cancel(aVar.g);
            aVar.e.delete(this);
            aVar.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Subscription subscription = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription != subscriptionHelper) {
                lazySet(subscriptionHelper);
                subscription.cancel();
                this.a.a(this, this.b);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public FlowableBufferBoundary(Flowable<T> flowable, Publisher<? extends Open> publisher, Function<? super Open, ? extends Publisher<? extends Close>> function, Callable<U> callable) {
        super(flowable);
        this.c = publisher;
        this.d = function;
        this.b = callable;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super U> subscriber) {
        a aVar = new a(subscriber, this.c, this.d, this.b);
        subscriber.onSubscribe(aVar);
        this.source.subscribe((FlowableSubscriber) aVar);
    }

    public static final class a<T, C extends Collection<? super T>, Open, Close> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = -8466418554264089604L;
        public final Subscriber<? super C> a;
        public final Callable<C> b;
        public final Publisher<? extends Open> c;
        public final Function<? super Open, ? extends Publisher<? extends Close>> d;
        public volatile boolean i;
        public volatile boolean k;
        public long l;
        public long n;
        public final SpscLinkedArrayQueue<C> j = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        public final CompositeDisposable e = new CompositeDisposable();
        public final AtomicLong f = new AtomicLong();
        public final AtomicReference<Subscription> g = new AtomicReference<>();
        public Map<Long, C> m = new LinkedHashMap();
        public final AtomicThrowable h = new AtomicThrowable();

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferBoundary$a$a, reason: collision with other inner class name */
        public static final class C0016a<Open> extends AtomicReference<Subscription> implements FlowableSubscriber<Open>, Disposable {
            public static final long serialVersionUID = -8498650778633225126L;
            public final a<?, ?, Open, ?> a;

            public C0016a(a<?, ?, Open, ?> aVar) {
                this.a = aVar;
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
                lazySet(SubscriptionHelper.CANCELLED);
                a<?, ?, Open, ?> aVar = this.a;
                aVar.e.delete(this);
                if (aVar.e.size() == 0) {
                    SubscriptionHelper.cancel(aVar.g);
                    aVar.i = true;
                    aVar.a();
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                lazySet(SubscriptionHelper.CANCELLED);
                a<?, ?, Open, ?> aVar = this.a;
                SubscriptionHelper.cancel(aVar.g);
                aVar.e.delete(this);
                aVar.onError(th);
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Open open) {
                this.a.a(open);
            }

            @Override // io.reactivex.FlowableSubscriber
            public void onSubscribe(Subscription subscription) {
                SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
            }
        }

        public a(Subscriber<? super C> subscriber, Publisher<? extends Open> publisher, Function<? super Open, ? extends Publisher<? extends Close>> function, Callable<C> callable) {
            this.a = subscriber;
            this.b = callable;
            this.c = publisher;
            this.d = function;
        }

        public void a(Open open) {
            try {
                Collection collection = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null Collection");
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.d.apply(open), "The bufferClose returned a null Publisher");
                long j = this.l;
                this.l = 1 + j;
                synchronized (this) {
                    Map<Long, C> map = this.m;
                    if (map == null) {
                        return;
                    }
                    map.put(Long.valueOf(j), collection);
                    b bVar = new b(this, j);
                    this.e.add(bVar);
                    publisher.subscribe(bVar);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                SubscriptionHelper.cancel(this.g);
                onError(th);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (SubscriptionHelper.cancel(this.g)) {
                this.k = true;
                this.e.dispose();
                synchronized (this) {
                    this.m = null;
                }
                if (getAndIncrement() != 0) {
                    this.j.clear();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.e.dispose();
            synchronized (this) {
                Map<Long, C> map = this.m;
                if (map == null) {
                    return;
                }
                Iterator<C> it = map.values().iterator();
                while (it.hasNext()) {
                    this.j.offer(it.next());
                }
                this.m = null;
                this.i = true;
                a();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.h.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e.dispose();
            synchronized (this) {
                this.m = null;
            }
            this.i = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            synchronized (this) {
                Map<Long, C> map = this.m;
                if (map == null) {
                    return;
                }
                Iterator<C> it = map.values().iterator();
                while (it.hasNext()) {
                    it.next().add(t);
                }
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.g, subscription)) {
                C0016a c0016a = new C0016a(this);
                this.e.add(c0016a);
                this.c.subscribe(c0016a);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.f, j);
            a();
        }

        public void a(b<T, C> bVar, long j) {
            boolean z;
            this.e.delete(bVar);
            if (this.e.size() == 0) {
                SubscriptionHelper.cancel(this.g);
                z = true;
            } else {
                z = false;
            }
            synchronized (this) {
                if (this.m == null) {
                    return;
                }
                this.j.offer(this.m.remove(Long.valueOf(j)));
                if (z) {
                    this.i = true;
                }
                a();
            }
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            long j = this.n;
            Subscriber<? super C> subscriber = this.a;
            SpscLinkedArrayQueue<C> spscLinkedArrayQueue = this.j;
            int iAddAndGet = 1;
            do {
                long j2 = this.f.get();
                while (j != j2) {
                    if (this.k) {
                        spscLinkedArrayQueue.clear();
                        return;
                    }
                    boolean z = this.i;
                    if (z && this.h.get() != null) {
                        spscLinkedArrayQueue.clear();
                        subscriber.onError(this.h.terminate());
                        return;
                    }
                    C cPoll = spscLinkedArrayQueue.poll();
                    boolean z2 = cPoll == null;
                    if (z && z2) {
                        subscriber.onComplete();
                        return;
                    } else {
                        if (z2) {
                            break;
                        }
                        subscriber.onNext(cPoll);
                        j++;
                    }
                }
                if (j == j2) {
                    if (this.k) {
                        spscLinkedArrayQueue.clear();
                        return;
                    }
                    if (this.i) {
                        if (this.h.get() != null) {
                            spscLinkedArrayQueue.clear();
                            subscriber.onError(this.h.terminate());
                            return;
                        } else if (spscLinkedArrayQueue.isEmpty()) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                }
                this.n = j;
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }
    }
}
