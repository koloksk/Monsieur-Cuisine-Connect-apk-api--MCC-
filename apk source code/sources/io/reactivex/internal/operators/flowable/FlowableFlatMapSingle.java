package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
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
public final class FlowableFlatMapSingle<T, R> extends zk<T, R> {
    public final Function<? super T, ? extends SingleSource<? extends R>> b;
    public final boolean c;
    public final int d;

    public FlowableFlatMapSingle(Flowable<T> flowable, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z, int i) {
        super(flowable);
        this.b = function;
        this.c = z;
        this.d = i;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d));
    }

    public static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = 8600231336733376951L;
        public final Subscriber<? super R> a;
        public final boolean b;
        public final int c;
        public final Function<? super T, ? extends SingleSource<? extends R>> h;
        public Subscription j;
        public volatile boolean k;
        public final AtomicLong d = new AtomicLong();
        public final CompositeDisposable e = new CompositeDisposable();
        public final AtomicThrowable g = new AtomicThrowable();
        public final AtomicInteger f = new AtomicInteger(1);
        public final AtomicReference<SpscLinkedArrayQueue<R>> i = new AtomicReference<>();

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableFlatMapSingle$a$a, reason: collision with other inner class name */
        public final class C0023a extends AtomicReference<Disposable> implements SingleObserver<R>, Disposable {
            public static final long serialVersionUID = -502562646270949838L;

            public C0023a() {
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a aVar = a.this;
                aVar.e.delete(this);
                if (!aVar.g.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (!aVar.b) {
                    aVar.j.cancel();
                    aVar.e.dispose();
                } else if (aVar.c != Integer.MAX_VALUE) {
                    aVar.j.request(1L);
                }
                aVar.f.decrementAndGet();
                aVar.b();
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(R r) {
                a.this.a(this, r);
            }
        }

        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z, int i) {
            this.a = subscriber;
            this.h = function;
            this.b = z;
            this.c = i;
        }

        /* JADX WARN: Removed duplicated region for block: B:35:0x007b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a(io.reactivex.internal.operators.flowable.FlowableFlatMapSingle.a<T, R>.C0023a r5, R r6) {
            /*
                r4 = this;
                io.reactivex.disposables.CompositeDisposable r0 = r4.e
                r0.delete(r5)
                int r5 = r4.get()
                if (r5 != 0) goto L7b
                r5 = 1
                r0 = 0
                boolean r1 = r4.compareAndSet(r0, r5)
                if (r1 == 0) goto L7b
                java.util.concurrent.atomic.AtomicInteger r1 = r4.f
                int r1 = r1.decrementAndGet()
                if (r1 != 0) goto L1c
                goto L1d
            L1c:
                r5 = r0
            L1d:
                java.util.concurrent.atomic.AtomicLong r0 = r4.d
                long r0 = r0.get()
                r2 = 0
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 == 0) goto L68
                org.reactivestreams.Subscriber<? super R> r0 = r4.a
                r0.onNext(r6)
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.queue.SpscLinkedArrayQueue<R>> r6 = r4.i
                java.lang.Object r6 = r6.get()
                io.reactivex.internal.queue.SpscLinkedArrayQueue r6 = (io.reactivex.internal.queue.SpscLinkedArrayQueue) r6
                if (r5 == 0) goto L54
                if (r6 == 0) goto L40
                boolean r5 = r6.isEmpty()
                if (r5 == 0) goto L54
            L40:
                io.reactivex.internal.util.AtomicThrowable r5 = r4.g
                java.lang.Throwable r5 = r5.terminate()
                if (r5 == 0) goto L4e
                org.reactivestreams.Subscriber<? super R> r6 = r4.a
                r6.onError(r5)
                goto L53
            L4e:
                org.reactivestreams.Subscriber<? super R> r5 = r4.a
                r5.onComplete()
            L53:
                return
            L54:
                java.util.concurrent.atomic.AtomicLong r5 = r4.d
                r0 = 1
                io.reactivex.internal.util.BackpressureHelper.produced(r5, r0)
                int r5 = r4.c
                r6 = 2147483647(0x7fffffff, float:NaN)
                if (r5 == r6) goto L71
                org.reactivestreams.Subscription r5 = r4.j
                r5.request(r0)
                goto L71
            L68:
                io.reactivex.internal.queue.SpscLinkedArrayQueue r5 = r4.d()
                monitor-enter(r5)
                r5.offer(r6)     // Catch: java.lang.Throwable -> L78
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L78
            L71:
                int r5 = r4.decrementAndGet()
                if (r5 != 0) goto L90
                return
            L78:
                r6 = move-exception
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L78
                throw r6
            L7b:
                io.reactivex.internal.queue.SpscLinkedArrayQueue r5 = r4.d()
                monitor-enter(r5)
                r5.offer(r6)     // Catch: java.lang.Throwable -> L94
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L94
                java.util.concurrent.atomic.AtomicInteger r5 = r4.f
                r5.decrementAndGet()
                int r5 = r4.getAndIncrement()
                if (r5 == 0) goto L90
                return
            L90:
                r4.c()
                return
            L94:
                r6 = move-exception
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L94
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableFlatMapSingle.a.a(io.reactivex.internal.operators.flowable.FlowableFlatMapSingle$a$a, java.lang.Object):void");
        }

        public void b() {
            if (getAndIncrement() == 0) {
                c();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x0077, code lost:
        
            if (r10 != r6) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x007b, code lost:
        
            if (r17.k == false) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x007d, code lost:
        
            a();
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0080, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x0083, code lost:
        
            if (r17.b != false) goto L48;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x008d, code lost:
        
            if (r17.g.get() == null) goto L48;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x008f, code lost:
        
            r2 = r17.g.terminate();
            a();
            r1.onError(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x009b, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00a0, code lost:
        
            if (r2.get() != 0) goto L51;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00a2, code lost:
        
            r6 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x00a4, code lost:
        
            r6 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00a5, code lost:
        
            r7 = r3.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x00ab, code lost:
        
            if (r7 == null) goto L56;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00b1, code lost:
        
            if (r7.isEmpty() == false) goto L57;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00b3, code lost:
        
            r13 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00b4, code lost:
        
            if (r6 == false) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00b6, code lost:
        
            if (r13 == false) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00b8, code lost:
        
            r2 = r17.g.terminate();
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x00be, code lost:
        
            if (r2 == null) goto L62;
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00c0, code lost:
        
            r1.onError(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00c4, code lost:
        
            r1.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x00c7, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00ca, code lost:
        
            if (r10 == 0) goto L69;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x00cc, code lost:
        
            io.reactivex.internal.util.BackpressureHelper.produced(r17.d, r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x00d6, code lost:
        
            if (r17.c == Integer.MAX_VALUE) goto L69;
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x00d8, code lost:
        
            r17.j.request(r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x00dd, code lost:
        
            r5 = addAndGet(-r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:83:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void c() {
            /*
                Method dump skipped, instructions count: 229
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableFlatMapSingle.a.c():void");
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.k = true;
            this.j.cancel();
            this.e.dispose();
        }

        public SpscLinkedArrayQueue<R> d() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue;
            do {
                SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = this.i.get();
                if (spscLinkedArrayQueue2 != null) {
                    return spscLinkedArrayQueue2;
                }
                spscLinkedArrayQueue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
            } while (!this.i.compareAndSet(null, spscLinkedArrayQueue));
            return spscLinkedArrayQueue;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.f.decrementAndGet();
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.f.decrementAndGet();
            if (!this.g.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.b) {
                this.e.dispose();
            }
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            try {
                SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.h.apply(t), "The mapper returned a null SingleSource");
                this.f.getAndIncrement();
                C0023a c0023a = new C0023a();
                if (this.k || !this.e.add(c0023a)) {
                    return;
                }
                singleSource.subscribe(c0023a);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.j.cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.j, subscription)) {
                this.j = subscription;
                this.a.onSubscribe(this);
                int i = this.c;
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
                BackpressureHelper.add(this.d, j);
                b();
            }
        }

        public void a() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.i.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }
    }
}
