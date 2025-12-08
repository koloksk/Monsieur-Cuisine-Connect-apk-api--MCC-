package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableSkipLastTimed<T> extends zk<T, T> {
    public final long b;
    public final TimeUnit c;
    public final Scheduler d;
    public final int e;
    public final boolean f;

    public static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = -5677354903406201275L;
        public final Subscriber<? super T> a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;
        public final SpscLinkedArrayQueue<Object> e;
        public final boolean f;
        public Subscription g;
        public final AtomicLong h = new AtomicLong();
        public volatile boolean i;
        public volatile boolean j;
        public Throwable k;

        public a(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
            this.a = subscriber;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = new SpscLinkedArrayQueue<>(i);
            this.f = z;
        }

        /* JADX WARN: Removed duplicated region for block: B:36:0x0079  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0078 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a() {
            /*
                r24 = this;
                r0 = r24
                int r1 = r24.getAndIncrement()
                if (r1 == 0) goto L9
                return
            L9:
                org.reactivestreams.Subscriber<? super T> r1 = r0.a
                io.reactivex.internal.queue.SpscLinkedArrayQueue<java.lang.Object> r2 = r0.e
                boolean r3 = r0.f
                java.util.concurrent.TimeUnit r4 = r0.c
                io.reactivex.Scheduler r5 = r0.d
                long r6 = r0.b
                r9 = 1
            L16:
                java.util.concurrent.atomic.AtomicLong r10 = r0.h
                long r10 = r10.get()
                r14 = 0
            L1e:
                int r16 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
                if (r16 == 0) goto L8a
                boolean r8 = r0.j
                java.lang.Object r17 = r2.peek()
                java.lang.Long r17 = (java.lang.Long) r17
                r18 = 0
                if (r17 != 0) goto L31
                r19 = 1
                goto L33
            L31:
                r19 = r18
            L33:
                long r20 = r5.now(r4)
                if (r19 != 0) goto L45
                long r22 = r17.longValue()
                long r20 = r20 - r6
                int r17 = (r22 > r20 ? 1 : (r22 == r20 ? 0 : -1))
                if (r17 <= 0) goto L45
                r19 = 1
            L45:
                boolean r12 = r0.i
                if (r12 == 0) goto L51
                io.reactivex.internal.queue.SpscLinkedArrayQueue<java.lang.Object> r8 = r0.e
                r8.clear()
            L4e:
                r18 = 1
                goto L76
            L51:
                if (r8 == 0) goto L76
                if (r3 == 0) goto L63
                if (r19 == 0) goto L76
                java.lang.Throwable r8 = r0.k
                if (r8 == 0) goto L5f
                r1.onError(r8)
                goto L4e
            L5f:
                r1.onComplete()
                goto L4e
            L63:
                java.lang.Throwable r8 = r0.k
                if (r8 == 0) goto L70
                io.reactivex.internal.queue.SpscLinkedArrayQueue<java.lang.Object> r12 = r0.e
                r12.clear()
                r1.onError(r8)
                goto L4e
            L70:
                if (r19 == 0) goto L76
                r1.onComplete()
                goto L4e
            L76:
                if (r18 == 0) goto L79
                return
            L79:
                if (r19 == 0) goto L7c
                goto L8a
            L7c:
                r2.poll()
                java.lang.Object r8 = r2.poll()
                r1.onNext(r8)
                r12 = 1
                long r14 = r14 + r12
                goto L1e
            L8a:
                r10 = 0
                int r8 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
                if (r8 == 0) goto L95
                java.util.concurrent.atomic.AtomicLong r8 = r0.h
                io.reactivex.internal.util.BackpressureHelper.produced(r8, r14)
            L95:
                int r8 = -r9
                int r9 = r0.addAndGet(r8)
                if (r9 != 0) goto L16
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableSkipLastTimed.a.a():void");
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.i) {
                return;
            }
            this.i = true;
            this.g.cancel();
            if (getAndIncrement() == 0) {
                this.e.clear();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.j = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.k = th;
            this.j = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.e.offer(Long.valueOf(this.d.now(this.c)), t);
            a();
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.h, j);
                a();
            }
        }
    }

    public FlowableSkipLastTimed(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
        super(flowable);
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = i;
        this.f = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d, this.e, this.f));
    }
}
