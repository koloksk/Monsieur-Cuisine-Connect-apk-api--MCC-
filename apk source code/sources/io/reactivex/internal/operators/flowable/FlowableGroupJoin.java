package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends zk<TLeft, R> {
    public final Publisher<? extends TRight> b;
    public final Function<? super TLeft, ? extends Publisher<TLeftEnd>> c;
    public final Function<? super TRight, ? extends Publisher<TRightEnd>> d;
    public final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> e;

    public interface b {
        void a(d dVar);

        void a(Throwable th);

        void a(boolean z, c cVar);

        void a(boolean z, Object obj);

        void b(Throwable th);
    }

    public static final class c extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        public static final long serialVersionUID = 1883890389173668373L;
        public final b a;
        public final boolean b;
        public final int c;

        public c(b bVar, boolean z, int i) {
            this.a = bVar;
            this.b = z;
            this.c = i;
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
            this.a.a(this.b, this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.b(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (SubscriptionHelper.cancel(this)) {
                this.a.a(this.b, this);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public static final class d extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        public static final long serialVersionUID = 1883890389173668373L;
        public final b a;
        public final boolean b;

        public d(b bVar, boolean z) {
            this.a = bVar;
            this.b = z;
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
            this.a.a(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.a.a(this.b, obj);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public FlowableGroupJoin(Flowable<TLeft> flowable, Publisher<? extends TRight> publisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> biFunction) {
        super(flowable);
        this.b = publisher;
        this.c = function;
        this.d = function2;
        this.e = biFunction;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        a aVar = new a(subscriber, this.c, this.d, this.e);
        subscriber.onSubscribe(aVar);
        d dVar = new d(aVar, true);
        aVar.d.add(dVar);
        d dVar2 = new d(aVar, false);
        aVar.d.add(dVar2);
        this.source.subscribe((FlowableSubscriber) dVar);
        this.b.subscribe(dVar2);
    }

    public static final class a<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Subscription, b {
        public static final Integer o = 1;
        public static final Integer p = 2;
        public static final Integer q = 3;
        public static final Integer r = 4;
        public static final long serialVersionUID = -6071216598687999801L;
        public final Subscriber<? super R> a;
        public final Function<? super TLeft, ? extends Publisher<TLeftEnd>> h;
        public final Function<? super TRight, ? extends Publisher<TRightEnd>> i;
        public final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> j;
        public int l;
        public int m;
        public volatile boolean n;
        public final AtomicLong b = new AtomicLong();
        public final CompositeDisposable d = new CompositeDisposable();
        public final SpscLinkedArrayQueue<Object> c = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        public final Map<Integer, UnicastProcessor<TRight>> e = new LinkedHashMap();
        public final Map<Integer, TRight> f = new LinkedHashMap();
        public final AtomicReference<Throwable> g = new AtomicReference<>();
        public final AtomicInteger k = new AtomicInteger(2);

        public a(Subscriber<? super R> subscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> biFunction) {
            this.a = subscriber;
            this.h = function;
            this.i = function2;
            this.j = biFunction;
        }

        public void a(Subscriber<?> subscriber) {
            Throwable thTerminate = ExceptionHelper.terminate(this.g);
            Iterator<UnicastProcessor<TRight>> it = this.e.values().iterator();
            while (it.hasNext()) {
                it.next().onError(thTerminate);
            }
            this.e.clear();
            this.f.clear();
            subscriber.onError(thTerminate);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void b(Throwable th) {
            if (ExceptionHelper.addThrowable(this.g, th)) {
                a();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.n) {
                return;
            }
            this.n = true;
            this.d.dispose();
            if (getAndIncrement() == 0) {
                this.c.clear();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.b, j);
            }
        }

        public void a(Throwable th, Subscriber<?> subscriber, SimpleQueue<?> simpleQueue) {
            Exceptions.throwIfFatal(th);
            ExceptionHelper.addThrowable(this.g, th);
            simpleQueue.clear();
            this.d.dispose();
            a(subscriber);
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.c;
            Subscriber<? super R> subscriber = this.a;
            int iAddAndGet = 1;
            while (!this.n) {
                if (this.g.get() != null) {
                    spscLinkedArrayQueue.clear();
                    this.d.dispose();
                    a(subscriber);
                    return;
                }
                boolean z = this.k.get() == 0;
                Integer num = (Integer) spscLinkedArrayQueue.poll();
                boolean z2 = num == null;
                if (z && z2) {
                    Iterator<UnicastProcessor<TRight>> it = this.e.values().iterator();
                    while (it.hasNext()) {
                        it.next().onComplete();
                    }
                    this.e.clear();
                    this.f.clear();
                    this.d.dispose();
                    subscriber.onComplete();
                    return;
                }
                if (z2) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    Object objPoll = spscLinkedArrayQueue.poll();
                    if (num == o) {
                        UnicastProcessor unicastProcessorCreate = UnicastProcessor.create();
                        int i = this.l;
                        this.l = i + 1;
                        this.e.put(Integer.valueOf(i), unicastProcessorCreate);
                        try {
                            Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.h.apply(objPoll), "The leftEnd returned a null Publisher");
                            c cVar = new c(this, true, i);
                            this.d.add(cVar);
                            publisher.subscribe(cVar);
                            if (this.g.get() != null) {
                                spscLinkedArrayQueue.clear();
                                this.d.dispose();
                                a(subscriber);
                                return;
                            }
                            try {
                                defpackage.a aVar = (Object) ObjectHelper.requireNonNull(this.j.apply(objPoll, unicastProcessorCreate), "The resultSelector returned a null value");
                                if (this.b.get() != 0) {
                                    subscriber.onNext(aVar);
                                    BackpressureHelper.produced(this.b, 1L);
                                    Iterator<TRight> it2 = this.f.values().iterator();
                                    while (it2.hasNext()) {
                                        unicastProcessorCreate.onNext(it2.next());
                                    }
                                } else {
                                    a(new MissingBackpressureException("Could not emit value due to lack of requests"), subscriber, spscLinkedArrayQueue);
                                    return;
                                }
                            } catch (Throwable th) {
                                a(th, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } catch (Throwable th2) {
                            a(th2, subscriber, spscLinkedArrayQueue);
                            return;
                        }
                    } else if (num == p) {
                        int i2 = this.m;
                        this.m = i2 + 1;
                        this.f.put(Integer.valueOf(i2), objPoll);
                        try {
                            Publisher publisher2 = (Publisher) ObjectHelper.requireNonNull(this.i.apply(objPoll), "The rightEnd returned a null Publisher");
                            c cVar2 = new c(this, false, i2);
                            this.d.add(cVar2);
                            publisher2.subscribe(cVar2);
                            if (this.g.get() != null) {
                                spscLinkedArrayQueue.clear();
                                this.d.dispose();
                                a(subscriber);
                                return;
                            } else {
                                Iterator<UnicastProcessor<TRight>> it3 = this.e.values().iterator();
                                while (it3.hasNext()) {
                                    it3.next().onNext(objPoll);
                                }
                            }
                        } catch (Throwable th3) {
                            a(th3, subscriber, spscLinkedArrayQueue);
                            return;
                        }
                    } else if (num == q) {
                        c cVar3 = (c) objPoll;
                        UnicastProcessor<TRight> unicastProcessorRemove = this.e.remove(Integer.valueOf(cVar3.c));
                        this.d.remove(cVar3);
                        if (unicastProcessorRemove != null) {
                            unicastProcessorRemove.onComplete();
                        }
                    } else if (num == r) {
                        c cVar4 = (c) objPoll;
                        this.f.remove(Integer.valueOf(cVar4.c));
                        this.d.remove(cVar4);
                    }
                }
            }
            spscLinkedArrayQueue.clear();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void a(Throwable th) {
            if (ExceptionHelper.addThrowable(this.g, th)) {
                this.k.decrementAndGet();
                a();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void a(d dVar) {
            this.d.delete(dVar);
            this.k.decrementAndGet();
            a();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void a(boolean z, Object obj) {
            synchronized (this) {
                this.c.offer(z ? o : p, obj);
            }
            a();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void a(boolean z, c cVar) {
            synchronized (this) {
                this.c.offer(z ? q : r, cVar);
            }
            a();
        }
    }
}
