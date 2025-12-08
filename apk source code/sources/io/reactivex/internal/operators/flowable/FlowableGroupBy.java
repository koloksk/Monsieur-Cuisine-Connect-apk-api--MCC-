package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.EmptyComponent;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableGroupBy<T, K, V> extends zk<T, GroupedFlowable<K, V>> {
    public final Function<? super T, ? extends K> b;
    public final Function<? super T, ? extends V> c;
    public final int d;
    public final boolean e;
    public final Function<? super Consumer<Object>, ? extends Map<K, Object>> f;

    public static final class GroupBySubscriber<T, K, V> extends BasicIntQueueSubscription<GroupedFlowable<K, V>> implements FlowableSubscriber<T> {
        public static final Object q = new Object();
        public static final long serialVersionUID = -3688291656102519502L;
        public final Subscriber<? super GroupedFlowable<K, V>> a;
        public final Function<? super T, ? extends K> b;
        public final Function<? super T, ? extends V> c;
        public final int d;
        public final boolean e;
        public final Map<Object, b<K, V>> f;
        public final SpscLinkedArrayQueue<GroupedFlowable<K, V>> g;
        public final Queue<b<K, V>> h;
        public Subscription i;
        public final AtomicBoolean j = new AtomicBoolean();
        public final AtomicLong k = new AtomicLong();
        public final AtomicInteger l = new AtomicInteger(1);
        public Throwable m;
        public volatile boolean n;
        public boolean o;
        public boolean p;

        public GroupBySubscriber(Subscriber<? super GroupedFlowable<K, V>> subscriber, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z, Map<Object, b<K, V>> map, Queue<b<K, V>> queue) {
            this.a = subscriber;
            this.b = function;
            this.c = function2;
            this.d = i;
            this.e = z;
            this.f = map;
            this.h = queue;
            this.g = new SpscLinkedArrayQueue<>(i);
        }

        public boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.j.get()) {
                spscLinkedArrayQueue.clear();
                return true;
            }
            if (this.e) {
                if (!z || !z2) {
                    return false;
                }
                Throwable th = this.m;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable th2 = this.m;
            if (th2 != null) {
                spscLinkedArrayQueue.clear();
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
            if (this.j.compareAndSet(false, true)) {
                d();
                if (this.l.decrementAndGet() == 0) {
                    this.i.cancel();
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.g.clear();
        }

        public final void d() {
            if (this.h != null) {
                int i = 0;
                while (true) {
                    b<K, V> bVarPoll = this.h.poll();
                    if (bVarPoll == null) {
                        break;
                    }
                    c<V, K> cVar = bVarPoll.c;
                    cVar.f = true;
                    cVar.drain();
                    i++;
                }
                if (i != 0) {
                    this.l.addAndGet(-i);
                }
            }
        }

        public void drain() {
            Throwable th;
            if (getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            if (this.p) {
                SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.g;
                Subscriber<? super GroupedFlowable<K, V>> subscriber = this.a;
                while (!this.j.get()) {
                    boolean z = this.n;
                    if (z && !this.e && (th = this.m) != null) {
                        spscLinkedArrayQueue.clear();
                        subscriber.onError(th);
                        return;
                    }
                    subscriber.onNext(null);
                    if (z) {
                        Throwable th2 = this.m;
                        if (th2 != null) {
                            subscriber.onError(th2);
                            return;
                        } else {
                            subscriber.onComplete();
                            return;
                        }
                    }
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
                return;
            }
            SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue2 = this.g;
            Subscriber<? super GroupedFlowable<K, V>> subscriber2 = this.a;
            int iAddAndGet2 = 1;
            do {
                long j = this.k.get();
                long j2 = 0;
                while (j2 != j) {
                    boolean z2 = this.n;
                    GroupedFlowable<K, V> groupedFlowablePoll = spscLinkedArrayQueue2.poll();
                    boolean z3 = groupedFlowablePoll == null;
                    if (a(z2, z3, subscriber2, spscLinkedArrayQueue2)) {
                        return;
                    }
                    if (z3) {
                        break;
                    }
                    subscriber2.onNext(groupedFlowablePoll);
                    j2++;
                }
                if (j2 == j && a(this.n, spscLinkedArrayQueue2.isEmpty(), subscriber2, spscLinkedArrayQueue2)) {
                    return;
                }
                if (j2 != 0) {
                    if (j != Long.MAX_VALUE) {
                        this.k.addAndGet(-j2);
                    }
                    this.i.request(j2);
                }
                iAddAndGet2 = addAndGet(-iAddAndGet2);
            } while (iAddAndGet2 != 0);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.g.isEmpty();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.o) {
                return;
            }
            Iterator<b<K, V>> it = this.f.values().iterator();
            while (it.hasNext()) {
                c<V, K> cVar = it.next().c;
                cVar.f = true;
                cVar.drain();
            }
            this.f.clear();
            Queue<b<K, V>> queue = this.h;
            if (queue != null) {
                queue.clear();
            }
            this.o = true;
            this.n = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.o) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.o = true;
            Iterator<b<K, V>> it = this.f.values().iterator();
            while (it.hasNext()) {
                c<V, K> cVar = it.next().c;
                cVar.g = th;
                cVar.f = true;
                cVar.drain();
            }
            this.f.clear();
            Queue<b<K, V>> queue = this.h;
            if (queue != null) {
                queue.clear();
            }
            this.m = th;
            this.n = true;
            drain();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.o) {
                return;
            }
            SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.g;
            try {
                K kApply = this.b.apply(t);
                boolean z = false;
                Object obj = kApply != null ? kApply : q;
                b<K, V> bVarA = this.f.get(obj);
                if (bVarA == null) {
                    if (this.j.get()) {
                        return;
                    }
                    bVarA = b.a(kApply, this.d, this, this.e);
                    this.f.put(obj, bVarA);
                    this.l.getAndIncrement();
                    z = true;
                }
                try {
                    Object objRequireNonNull = ObjectHelper.requireNonNull(this.c.apply(t), "The valueSelector returned null");
                    c<V, K> cVar = bVarA.c;
                    cVar.b.offer(objRequireNonNull);
                    cVar.drain();
                    d();
                    if (z) {
                        spscLinkedArrayQueue.offer(bVarA);
                        drain();
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.i.cancel();
                    onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.i.cancel();
                onError(th2);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.i, subscription)) {
                this.i = subscription;
                this.a.onSubscribe(this);
                subscription.request(this.d);
            }
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
            if ((i & 2) == 0) {
                return 0;
            }
            this.p = true;
            return 2;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public GroupedFlowable<K, V> poll() {
            return this.g.poll();
        }

        public void cancel(K k) {
            if (k == null) {
                k = (K) q;
            }
            this.f.remove(k);
            if (this.l.decrementAndGet() == 0) {
                this.i.cancel();
                if (this.p || getAndIncrement() != 0) {
                    return;
                }
                this.g.clear();
            }
        }
    }

    public static final class a<K, V> implements Consumer<b<K, V>> {
        public final Queue<b<K, V>> a;

        public a(Queue<b<K, V>> queue) {
            this.a = queue;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Object obj) throws Exception {
            this.a.offer((b) obj);
        }
    }

    public static final class b<K, T> extends GroupedFlowable<K, T> {
        public final c<T, K> c;

        public b(K k, c<T, K> cVar) {
            super(k);
            this.c = cVar;
        }

        public static <T, K> b<K, T> a(K k, int i, GroupBySubscriber<?, K, T> groupBySubscriber, boolean z) {
            return new b<>(k, new c(i, groupBySubscriber, k, z));
        }

        @Override // io.reactivex.Flowable
        public void subscribeActual(Subscriber<? super T> subscriber) {
            this.c.subscribe(subscriber);
        }
    }

    public static final class c<T, K> extends BasicIntQueueSubscription<T> implements Publisher<T> {
        public static final long serialVersionUID = -3852313036005250360L;
        public final K a;
        public final SpscLinkedArrayQueue<T> b;
        public final GroupBySubscriber<?, K, T> c;
        public final boolean d;
        public volatile boolean f;
        public Throwable g;
        public boolean k;
        public int l;
        public final AtomicLong e = new AtomicLong();
        public final AtomicBoolean h = new AtomicBoolean();
        public final AtomicReference<Subscriber<? super T>> i = new AtomicReference<>();
        public final AtomicBoolean j = new AtomicBoolean();

        public c(int i, GroupBySubscriber<?, K, T> groupBySubscriber, K k, boolean z) {
            this.b = new SpscLinkedArrayQueue<>(i);
            this.c = groupBySubscriber;
            this.a = k;
            this.d = z;
        }

        public boolean a(boolean z, boolean z2, Subscriber<? super T> subscriber, boolean z3, long j) {
            if (this.h.get()) {
                while (this.b.poll() != null) {
                    j++;
                }
                if (j != 0) {
                    this.c.i.request(j);
                }
                return true;
            }
            if (!z) {
                return false;
            }
            if (z3) {
                if (!z2) {
                    return false;
                }
                Throwable th = this.g;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
            Throwable th2 = this.g;
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
            if (this.h.compareAndSet(false, true)) {
                this.c.cancel(this.a);
                drain();
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
            while (spscLinkedArrayQueue.poll() != null) {
                this.l++;
            }
            d();
        }

        public void d() {
            int i = this.l;
            if (i != 0) {
                this.l = 0;
                this.c.i.request(i);
            }
        }

        public void drain() {
            Throwable th;
            if (getAndIncrement() != 0) {
                return;
            }
            if (this.k) {
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
                Subscriber<? super T> subscriber = this.i.get();
                int iAddAndGet = 1;
                while (true) {
                    if (subscriber != null) {
                        if (this.h.get()) {
                            return;
                        }
                        boolean z = this.f;
                        if (z && !this.d && (th = this.g) != null) {
                            spscLinkedArrayQueue.clear();
                            subscriber.onError(th);
                            return;
                        }
                        subscriber.onNext(null);
                        if (z) {
                            Throwable th2 = this.g;
                            if (th2 != null) {
                                subscriber.onError(th2);
                                return;
                            } else {
                                subscriber.onComplete();
                                return;
                            }
                        }
                    }
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                    if (subscriber == null) {
                        subscriber = this.i.get();
                    }
                }
            } else {
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue2 = this.b;
                boolean z2 = this.d;
                Subscriber<? super T> subscriber2 = this.i.get();
                int iAddAndGet2 = 1;
                while (true) {
                    if (subscriber2 != null) {
                        long j = this.e.get();
                        long j2 = 0;
                        while (true) {
                            if (j2 == j) {
                                break;
                            }
                            boolean z3 = this.f;
                            T tPoll = spscLinkedArrayQueue2.poll();
                            boolean z4 = tPoll == null;
                            long j3 = j2;
                            if (a(z3, z4, subscriber2, z2, j2)) {
                                return;
                            }
                            if (z4) {
                                j2 = j3;
                                break;
                            } else {
                                subscriber2.onNext(tPoll);
                                j2 = j3 + 1;
                            }
                        }
                        if (j2 == j) {
                            long j4 = j2;
                            if (a(this.f, spscLinkedArrayQueue2.isEmpty(), subscriber2, z2, j2)) {
                                return;
                            } else {
                                j2 = j4;
                            }
                        }
                        if (j2 != 0) {
                            if (j != Long.MAX_VALUE) {
                                this.e.addAndGet(-j2);
                            }
                            this.c.i.request(j2);
                        }
                    }
                    iAddAndGet2 = addAndGet(-iAddAndGet2);
                    if (iAddAndGet2 == 0) {
                        return;
                    }
                    if (subscriber2 == null) {
                        subscriber2 = this.i.get();
                    }
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            if (!this.b.isEmpty()) {
                return false;
            }
            d();
            return true;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            T tPoll = this.b.poll();
            if (tPoll != null) {
                this.l++;
                return tPoll;
            }
            d();
            return null;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.e, j);
                drain();
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.k = true;
            return 2;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            if (!this.j.compareAndSet(false, true)) {
                EmptySubscription.error(new IllegalStateException("Only one Subscriber allowed!"), subscriber);
                return;
            }
            subscriber.onSubscribe(this);
            this.i.lazySet(subscriber);
            drain();
        }
    }

    public FlowableGroupBy(Flowable<T> flowable, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z, Function<? super Consumer<Object>, ? extends Map<K, Object>> function3) {
        super(flowable);
        this.b = function;
        this.c = function2;
        this.d = i;
        this.e = z;
        this.f = function3;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super GroupedFlowable<K, V>> subscriber) {
        ConcurrentLinkedQueue concurrentLinkedQueue;
        Map<K, Object> mapApply;
        try {
            if (this.f == null) {
                concurrentLinkedQueue = null;
                mapApply = new ConcurrentHashMap<>();
            } else {
                concurrentLinkedQueue = new ConcurrentLinkedQueue();
                mapApply = this.f.apply(new a(concurrentLinkedQueue));
            }
            this.source.subscribe((FlowableSubscriber) new GroupBySubscriber(subscriber, this.b, this.c, this.d, this.e, mapApply, concurrentLinkedQueue));
        } catch (Exception e) {
            Exceptions.throwIfFatal(e);
            subscriber.onSubscribe(EmptyComponent.INSTANCE);
            subscriber.onError(e);
        }
    }
}
