package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.operators.flowable.FlowableGroupJoin;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
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
public final class FlowableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends zk<TLeft, R> {
    public final Publisher<? extends TRight> b;
    public final Function<? super TLeft, ? extends Publisher<TLeftEnd>> c;
    public final Function<? super TRight, ? extends Publisher<TRightEnd>> d;
    public final BiFunction<? super TLeft, ? super TRight, ? extends R> e;

    public FlowableJoin(Flowable<TLeft> flowable, Publisher<? extends TRight> publisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
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
        FlowableGroupJoin.d dVar = new FlowableGroupJoin.d(aVar, true);
        aVar.d.add(dVar);
        FlowableGroupJoin.d dVar2 = new FlowableGroupJoin.d(aVar, false);
        aVar.d.add(dVar2);
        this.source.subscribe((FlowableSubscriber) dVar);
        this.b.subscribe(dVar2);
    }

    public static final class a<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Subscription, FlowableGroupJoin.b {
        public static final Integer o = 1;
        public static final Integer p = 2;
        public static final Integer q = 3;
        public static final Integer r = 4;
        public static final long serialVersionUID = -6071216598687999801L;
        public final Subscriber<? super R> a;
        public final Function<? super TLeft, ? extends Publisher<TLeftEnd>> h;
        public final Function<? super TRight, ? extends Publisher<TRightEnd>> i;
        public final BiFunction<? super TLeft, ? super TRight, ? extends R> j;
        public int l;
        public int m;
        public volatile boolean n;
        public final AtomicLong b = new AtomicLong();
        public final CompositeDisposable d = new CompositeDisposable();
        public final SpscLinkedArrayQueue<Object> c = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        public final Map<Integer, TLeft> e = new LinkedHashMap();
        public final Map<Integer, TRight> f = new LinkedHashMap();
        public final AtomicReference<Throwable> g = new AtomicReference<>();
        public final AtomicInteger k = new AtomicInteger(2);

        public a(Subscriber<? super R> subscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
            this.a = subscriber;
            this.h = function;
            this.i = function2;
            this.j = biFunction;
        }

        public void a(Subscriber<?> subscriber) {
            Throwable thTerminate = ExceptionHelper.terminate(this.g);
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
            boolean z = true;
            int iAddAndGet = 1;
            while (!this.n) {
                if (this.g.get() != null) {
                    spscLinkedArrayQueue.clear();
                    this.d.dispose();
                    a(subscriber);
                    return;
                }
                boolean z2 = this.k.get() == 0 ? z : false;
                Integer num = (Integer) spscLinkedArrayQueue.poll();
                boolean z3 = num == null ? z : false;
                if (z2 && z3) {
                    this.e.clear();
                    this.f.clear();
                    this.d.dispose();
                    subscriber.onComplete();
                    return;
                }
                if (z3) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    Object objPoll = spscLinkedArrayQueue.poll();
                    if (num == o) {
                        int i = this.l;
                        this.l = i + 1;
                        this.e.put(Integer.valueOf(i), objPoll);
                        try {
                            Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.h.apply(objPoll), "The leftEnd returned a null Publisher");
                            FlowableGroupJoin.c cVar = new FlowableGroupJoin.c(this, z, i);
                            this.d.add(cVar);
                            publisher.subscribe(cVar);
                            if (this.g.get() != null) {
                                spscLinkedArrayQueue.clear();
                                this.d.dispose();
                                a(subscriber);
                                return;
                            }
                            long j = this.b.get();
                            Iterator<TRight> it = this.f.values().iterator();
                            long j2 = 0;
                            while (it.hasNext()) {
                                try {
                                    defpackage.a aVar = (Object) ObjectHelper.requireNonNull(this.j.apply(objPoll, it.next()), "The resultSelector returned a null value");
                                    if (j2 != j) {
                                        subscriber.onNext(aVar);
                                        j2++;
                                    } else {
                                        ExceptionHelper.addThrowable(this.g, new MissingBackpressureException("Could not emit value due to lack of requests"));
                                        spscLinkedArrayQueue.clear();
                                        this.d.dispose();
                                        a(subscriber);
                                        return;
                                    }
                                } catch (Throwable th) {
                                    a(th, subscriber, spscLinkedArrayQueue);
                                    return;
                                }
                            }
                            if (j2 != 0) {
                                BackpressureHelper.produced(this.b, j2);
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
                            FlowableGroupJoin.c cVar2 = new FlowableGroupJoin.c(this, false, i2);
                            this.d.add(cVar2);
                            publisher2.subscribe(cVar2);
                            if (this.g.get() != null) {
                                spscLinkedArrayQueue.clear();
                                this.d.dispose();
                                a(subscriber);
                                return;
                            }
                            long j3 = this.b.get();
                            Iterator<TLeft> it2 = this.e.values().iterator();
                            long j4 = 0;
                            while (it2.hasNext()) {
                                try {
                                    defpackage.a aVar2 = (Object) ObjectHelper.requireNonNull(this.j.apply(it2.next(), objPoll), "The resultSelector returned a null value");
                                    if (j4 != j3) {
                                        subscriber.onNext(aVar2);
                                        j4++;
                                    } else {
                                        ExceptionHelper.addThrowable(this.g, new MissingBackpressureException("Could not emit value due to lack of requests"));
                                        spscLinkedArrayQueue.clear();
                                        this.d.dispose();
                                        a(subscriber);
                                        return;
                                    }
                                } catch (Throwable th3) {
                                    a(th3, subscriber, spscLinkedArrayQueue);
                                    return;
                                }
                            }
                            if (j4 != 0) {
                                BackpressureHelper.produced(this.b, j4);
                            }
                        } catch (Throwable th4) {
                            a(th4, subscriber, spscLinkedArrayQueue);
                            return;
                        }
                    } else if (num == q) {
                        FlowableGroupJoin.c cVar3 = (FlowableGroupJoin.c) objPoll;
                        this.e.remove(Integer.valueOf(cVar3.c));
                        this.d.remove(cVar3);
                    } else if (num == r) {
                        FlowableGroupJoin.c cVar4 = (FlowableGroupJoin.c) objPoll;
                        this.f.remove(Integer.valueOf(cVar4.c));
                        this.d.remove(cVar4);
                    }
                    z = true;
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
        public void a(FlowableGroupJoin.d dVar) {
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
        public void a(boolean z, FlowableGroupJoin.c cVar) {
            synchronized (this) {
                this.c.offer(z ? q : r, cVar);
            }
            a();
        }
    }
}
