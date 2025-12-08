package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableMergeWithSingle<T> extends zk<T, T> {
    public final SingleSource<? extends T> b;

    public static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = -4592979584110982903L;
        public final Subscriber<? super T> a;
        public final AtomicReference<Subscription> b = new AtomicReference<>();
        public final C0026a<T> c = new C0026a<>(this);
        public final AtomicThrowable d = new AtomicThrowable();
        public final AtomicLong e = new AtomicLong();
        public final int f;
        public final int g;
        public volatile SimplePlainQueue<T> h;
        public T i;
        public volatile boolean j;
        public volatile boolean k;
        public volatile int l;
        public long m;
        public int n;

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableMergeWithSingle$a$a, reason: collision with other inner class name */
        public static final class C0026a<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            public static final long serialVersionUID = -2935427570954647017L;
            public final a<T> a;

            public C0026a(a<T> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a<T> aVar = this.a;
                if (!aVar.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                } else {
                    SubscriptionHelper.cancel(aVar.b);
                    aVar.a();
                }
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(T t) {
                a<T> aVar = this.a;
                if (aVar.compareAndSet(0, 1)) {
                    long j = aVar.m;
                    if (aVar.e.get() != j) {
                        aVar.m = j + 1;
                        aVar.a.onNext(t);
                        aVar.l = 2;
                    } else {
                        aVar.i = t;
                        aVar.l = 1;
                        if (aVar.decrementAndGet() == 0) {
                            return;
                        }
                    }
                } else {
                    aVar.i = t;
                    aVar.l = 1;
                    if (aVar.getAndIncrement() != 0) {
                        return;
                    }
                }
                aVar.b();
            }
        }

        public a(Subscriber<? super T> subscriber) {
            this.a = subscriber;
            int iBufferSize = Flowable.bufferSize();
            this.f = iBufferSize;
            this.g = iBufferSize - (iBufferSize >> 2);
        }

        public void a() {
            if (getAndIncrement() == 0) {
                b();
            }
        }

        public void b() {
            Subscriber<? super T> subscriber = this.a;
            long j = this.m;
            int i = this.n;
            int i2 = this.g;
            int i3 = 1;
            int iAddAndGet = 1;
            while (true) {
                long j2 = this.e.get();
                while (j != j2) {
                    if (this.j) {
                        this.i = null;
                        this.h = null;
                        return;
                    }
                    if (this.d.get() != null) {
                        this.i = null;
                        this.h = null;
                        subscriber.onError(this.d.terminate());
                        return;
                    }
                    int i4 = this.l;
                    if (i4 == i3) {
                        T t = this.i;
                        this.i = null;
                        this.l = 2;
                        subscriber.onNext(t);
                        j++;
                    } else {
                        boolean z = this.k;
                        SimplePlainQueue<T> simplePlainQueue = this.h;
                        defpackage.a aVarPoll = simplePlainQueue != null ? simplePlainQueue.poll() : null;
                        boolean z2 = aVarPoll == null;
                        if (z && z2 && i4 == 2) {
                            this.h = null;
                            subscriber.onComplete();
                            return;
                        } else {
                            if (z2) {
                                break;
                            }
                            subscriber.onNext(aVarPoll);
                            j++;
                            i++;
                            if (i == i2) {
                                this.b.get().request(i2);
                                i = 0;
                            }
                            i3 = 1;
                        }
                    }
                }
                if (j == j2) {
                    if (this.j) {
                        this.i = null;
                        this.h = null;
                        return;
                    }
                    if (this.d.get() != null) {
                        this.i = null;
                        this.h = null;
                        subscriber.onError(this.d.terminate());
                        return;
                    }
                    boolean z3 = this.k;
                    SimplePlainQueue<T> simplePlainQueue2 = this.h;
                    boolean z4 = simplePlainQueue2 == null || simplePlainQueue2.isEmpty();
                    if (z3 && z4 && this.l == 2) {
                        this.h = null;
                        subscriber.onComplete();
                        return;
                    }
                }
                this.m = j;
                this.n = i;
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                } else {
                    i3 = 1;
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.j = true;
            SubscriptionHelper.cancel(this.b);
            DisposableHelper.dispose(this.c);
            if (getAndIncrement() == 0) {
                this.h = null;
                this.i = null;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.k = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                DisposableHelper.dispose(this.c);
                a();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (compareAndSet(0, 1)) {
                long j = this.m;
                if (this.e.get() != j) {
                    SimplePlainQueue<T> simplePlainQueue = this.h;
                    if (simplePlainQueue == null || simplePlainQueue.isEmpty()) {
                        this.m = j + 1;
                        this.a.onNext(t);
                        int i = this.n + 1;
                        if (i == this.g) {
                            this.n = 0;
                            this.b.get().request(i);
                        } else {
                            this.n = i;
                        }
                    } else {
                        simplePlainQueue.offer(t);
                    }
                } else {
                    SpscArrayQueue spscArrayQueue = this.h;
                    if (spscArrayQueue == null) {
                        spscArrayQueue = new SpscArrayQueue(Flowable.bufferSize());
                        this.h = spscArrayQueue;
                    }
                    spscArrayQueue.offer(t);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SpscArrayQueue spscArrayQueue2 = this.h;
                if (spscArrayQueue2 == null) {
                    spscArrayQueue2 = new SpscArrayQueue(Flowable.bufferSize());
                    this.h = spscArrayQueue2;
                }
                spscArrayQueue2.offer(t);
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            b();
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this.b, subscription, this.f);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.e, j);
            a();
        }
    }

    public FlowableMergeWithSingle(Flowable<T> flowable, SingleSource<? extends T> singleSource) {
        super(flowable);
        this.b = singleSource;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber);
        subscriber.onSubscribe(aVar);
        this.source.subscribe((FlowableSubscriber) aVar);
        this.b.subscribe(aVar.c);
    }
}
