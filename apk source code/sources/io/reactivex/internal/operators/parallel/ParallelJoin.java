package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class ParallelJoin<T> extends Flowable<T> {
    public final ParallelFlowable<? extends T> b;
    public final int c;
    public final boolean d;

    public static abstract class c<T> extends AtomicInteger implements Subscription {
        public static final long serialVersionUID = 3100232009247827843L;
        public final Subscriber<? super T> a;
        public final a<T>[] b;
        public volatile boolean e;
        public final AtomicThrowable c = new AtomicThrowable();
        public final AtomicLong d = new AtomicLong();
        public final AtomicInteger f = new AtomicInteger();

        public c(Subscriber<? super T> subscriber, int i, int i2) {
            this.a = subscriber;
            a<T>[] aVarArr = new a[i];
            for (int i3 = 0; i3 < i; i3++) {
                aVarArr[i3] = new a<>(this, i2);
            }
            this.b = aVarArr;
            this.f.lazySet(i);
        }

        public void a() {
            for (a<T> aVar : this.b) {
                if (aVar == null) {
                    throw null;
                }
                SubscriptionHelper.cancel(aVar);
            }
        }

        public abstract void a(a<T> aVar, T t);

        public abstract void a(Throwable th);

        public void b() {
            for (a<T> aVar : this.b) {
                aVar.e = null;
            }
        }

        public abstract void c();

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.e) {
                return;
            }
            this.e = true;
            a();
            if (getAndIncrement() == 0) {
                b();
            }
        }

        public abstract void d();

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.d, j);
                c();
            }
        }
    }

    public ParallelJoin(ParallelFlowable<? extends T> parallelFlowable, int i, boolean z) {
        this.b = parallelFlowable;
        this.c = i;
        this.d = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        c dVar = this.d ? new d(subscriber, this.b.parallelism(), this.c) : new b(subscriber, this.b.parallelism(), this.c);
        subscriber.onSubscribe(dVar);
        this.b.subscribe(dVar.b);
    }

    public static final class a<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        public static final long serialVersionUID = 8410034718427740355L;
        public final c<T> a;
        public final int b;
        public final int c;
        public long d;
        public volatile SimplePlainQueue<T> e;

        public a(c<T> cVar, int i) {
            this.a = cVar;
            this.b = i;
            this.c = i - (i >> 2);
        }

        public void a(long j) {
            long j2 = this.d + j;
            if (j2 < this.c) {
                this.d = j2;
            } else {
                this.d = 0L;
                get().request(j2);
            }
        }

        public void b() {
            long j = this.d + 1;
            if (j != this.c) {
                this.d = j;
            } else {
                this.d = 0L;
                get().request(j);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.d();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.a.a(this, t);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, this.b);
        }

        public SimplePlainQueue<T> a() {
            SimplePlainQueue<T> simplePlainQueue = this.e;
            if (simplePlainQueue != null) {
                return simplePlainQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.b);
            this.e = spscArrayQueue;
            return spscArrayQueue;
        }
    }

    public static final class b<T> extends c<T> {
        public static final long serialVersionUID = 6312374661811000451L;

        public b(Subscriber<? super T> subscriber, int i, int i2) {
            super(subscriber, i, i2);
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.c
        public void a(a<T> aVar, T t) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.d.get() != 0) {
                    this.a.onNext(t);
                    if (this.d.get() != Long.MAX_VALUE) {
                        this.d.decrementAndGet();
                    }
                    aVar.a(1L);
                } else if (!aVar.a().offer(t)) {
                    a();
                    MissingBackpressureException missingBackpressureException = new MissingBackpressureException("Queue full?!");
                    if (this.c.compareAndSet(null, missingBackpressureException)) {
                        this.a.onError(missingBackpressureException);
                        return;
                    } else {
                        RxJavaPlugins.onError(missingBackpressureException);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!aVar.a().offer(t)) {
                a();
                a(new MissingBackpressureException("Queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            e();
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.c
        public void c() {
            if (getAndIncrement() != 0) {
                return;
            }
            e();
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.c
        public void d() {
            this.f.decrementAndGet();
            c();
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x005d, code lost:
        
            if (r12 == false) goto L85;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x005f, code lost:
        
            if (r15 == false) goto L86;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0061, code lost:
        
            r3.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0064, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0065, code lost:
        
            if (r15 == false) goto L87;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void e() {
            /*
                Method dump skipped, instructions count: 208
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelJoin.b.e():void");
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.c
        public void a(Throwable th) {
            if (this.c.compareAndSet(null, th)) {
                a();
                c();
            } else if (th != this.c.get()) {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public static final class d<T> extends c<T> {
        public static final long serialVersionUID = -5737965195918321883L;

        public d(Subscriber<? super T> subscriber, int i, int i2) {
            super(subscriber, i, i2);
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.c
        public void a(a<T> aVar, T t) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.d.get() != 0) {
                    this.a.onNext(t);
                    if (this.d.get() != Long.MAX_VALUE) {
                        this.d.decrementAndGet();
                    }
                    aVar.a(1L);
                } else if (!aVar.a().offer(t)) {
                    SubscriptionHelper.cancel(aVar);
                    this.c.addThrowable(new MissingBackpressureException("Queue full?!"));
                    this.f.decrementAndGet();
                    e();
                    return;
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                if (!aVar.a().offer(t) && SubscriptionHelper.cancel(aVar)) {
                    this.c.addThrowable(new MissingBackpressureException("Queue full?!"));
                    this.f.decrementAndGet();
                }
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            e();
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.c
        public void c() {
            if (getAndIncrement() != 0) {
                return;
            }
            e();
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.c
        public void d() {
            this.f.decrementAndGet();
            c();
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x004b, code lost:
        
            if (r12 == false) goto L80;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x004d, code lost:
        
            if (r15 == false) goto L81;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:
        
            if (r18.c.get() == null) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0059, code lost:
        
            r3.onError(r18.c.terminate());
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0063, code lost:
        
            r3.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0067, code lost:
        
            if (r15 == false) goto L82;
         */
        /* JADX WARN: Code restructure failed: missing block: B:91:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void e() {
            /*
                Method dump skipped, instructions count: 213
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelJoin.d.e():void");
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.c
        public void a(Throwable th) {
            this.c.addThrowable(th);
            this.f.decrementAndGet();
            c();
        }
    }
}
