package io.reactivex.internal.operators.flowable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
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
public final class FlowableCreate<T> extends Flowable<T> {
    public final FlowableOnSubscribe<T> b;
    public final BackpressureStrategy c;

    public static final class b<T> extends a<T> {
        public static final long serialVersionUID = 2427151001689639875L;
        public final SpscLinkedArrayQueue<T> c;
        public Throwable d;
        public volatile boolean e;
        public final AtomicInteger f;

        public b(Subscriber<? super T> subscriber, int i) {
            super(subscriber);
            this.c = new SpscLinkedArrayQueue<>(i);
            this.f = new AtomicInteger();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.a
        public void b() {
            d();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.a
        public void c() {
            if (this.f.getAndIncrement() == 0) {
                this.c.clear();
            }
        }

        public void d() {
            if (this.f.getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = this.a;
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.c;
            int iAddAndGet = 1;
            do {
                long j = get();
                long j2 = 0;
                while (j2 != j) {
                    if (isCancelled()) {
                        spscLinkedArrayQueue.clear();
                        return;
                    }
                    boolean z = this.e;
                    T tPoll = spscLinkedArrayQueue.poll();
                    boolean z2 = tPoll == null;
                    if (z && z2) {
                        Throwable th = this.d;
                        if (th != null) {
                            a(th);
                            return;
                        } else {
                            a();
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(tPoll);
                    j2++;
                }
                if (j2 == j) {
                    if (isCancelled()) {
                        spscLinkedArrayQueue.clear();
                        return;
                    }
                    boolean z3 = this.e;
                    boolean zIsEmpty = spscLinkedArrayQueue.isEmpty();
                    if (z3 && zIsEmpty) {
                        Throwable th2 = this.d;
                        if (th2 != null) {
                            a(th2);
                            return;
                        } else {
                            a();
                            return;
                        }
                    }
                }
                if (j2 != 0) {
                    BackpressureHelper.produced(this, j2);
                }
                iAddAndGet = this.f.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.a, io.reactivex.Emitter
        public void onComplete() {
            this.e = true;
            d();
        }

        @Override // io.reactivex.Emitter
        public void onNext(T t) {
            if (this.e || isCancelled()) {
                return;
            }
            if (t != null) {
                this.c.offer(t);
                d();
            } else {
                NullPointerException nullPointerException = new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
                if (tryOnError(nullPointerException)) {
                    return;
                }
                RxJavaPlugins.onError(nullPointerException);
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.a, io.reactivex.FlowableEmitter
        public boolean tryOnError(Throwable th) {
            if (this.e || isCancelled()) {
                return false;
            }
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            this.d = th;
            this.e = true;
            d();
            return true;
        }
    }

    public static final class c<T> extends g<T> {
        public static final long serialVersionUID = 8360058422307496563L;

        public c(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.g
        public void d() {
        }
    }

    public static final class d<T> extends g<T> {
        public static final long serialVersionUID = 338953216916120960L;

        public d(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.g
        public void d() {
            MissingBackpressureException missingBackpressureException = new MissingBackpressureException("create: could not emit value due to lack of requests");
            if (tryOnError(missingBackpressureException)) {
                return;
            }
            RxJavaPlugins.onError(missingBackpressureException);
        }
    }

    public static final class e<T> extends a<T> {
        public static final long serialVersionUID = 4023437720691792495L;
        public final AtomicReference<T> c;
        public Throwable d;
        public volatile boolean e;
        public final AtomicInteger f;

        public e(Subscriber<? super T> subscriber) {
            super(subscriber);
            this.c = new AtomicReference<>();
            this.f = new AtomicInteger();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.a
        public void b() {
            d();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.a
        public void c() {
            if (this.f.getAndIncrement() == 0) {
                this.c.lazySet(null);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x004f, code lost:
        
            if (r9 != r5) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0055, code lost:
        
            if (isCancelled() == false) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0057, code lost:
        
            r2.lazySet(null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x005a, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x005b, code lost:
        
            r5 = r17.e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0061, code lost:
        
            if (r2.get() != null) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0063, code lost:
        
            r12 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0064, code lost:
        
            if (r5 == false) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0066, code lost:
        
            if (r12 == false) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0068, code lost:
        
            r1 = r17.d;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x006a, code lost:
        
            if (r1 == null) goto L40;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x006c, code lost:
        
            a(r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0070, code lost:
        
            a();
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0073, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x0076, code lost:
        
            if (r9 == 0) goto L45;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0078, code lost:
        
            io.reactivex.internal.util.BackpressureHelper.produced(r17, r9);
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x007b, code lost:
        
            r4 = r17.f.addAndGet(-r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void d() {
            /*
                r17 = this;
                r0 = r17
                java.util.concurrent.atomic.AtomicInteger r1 = r0.f
                int r1 = r1.getAndIncrement()
                if (r1 == 0) goto Lb
                return
            Lb:
                org.reactivestreams.Subscriber<? super T> r1 = r0.a
                java.util.concurrent.atomic.AtomicReference<T> r2 = r0.c
                r3 = 1
                r4 = r3
            L11:
                long r5 = r17.get()
                r7 = 0
                r9 = r7
            L18:
                int r11 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
                r12 = 0
                r13 = 0
                if (r11 == 0) goto L4f
                boolean r14 = r17.isCancelled()
                if (r14 == 0) goto L28
                r2.lazySet(r13)
                return
            L28:
                boolean r14 = r0.e
                java.lang.Object r15 = r2.getAndSet(r13)
                if (r15 != 0) goto L33
                r16 = r3
                goto L35
            L33:
                r16 = r12
            L35:
                if (r14 == 0) goto L45
                if (r16 == 0) goto L45
                java.lang.Throwable r1 = r0.d
                if (r1 == 0) goto L41
                r0.a(r1)
                goto L44
            L41:
                r17.a()
            L44:
                return
            L45:
                if (r16 == 0) goto L48
                goto L4f
            L48:
                r1.onNext(r15)
                r11 = 1
                long r9 = r9 + r11
                goto L18
            L4f:
                if (r11 != 0) goto L74
                boolean r5 = r17.isCancelled()
                if (r5 == 0) goto L5b
                r2.lazySet(r13)
                return
            L5b:
                boolean r5 = r0.e
                java.lang.Object r6 = r2.get()
                if (r6 != 0) goto L64
                r12 = r3
            L64:
                if (r5 == 0) goto L74
                if (r12 == 0) goto L74
                java.lang.Throwable r1 = r0.d
                if (r1 == 0) goto L70
                r0.a(r1)
                goto L73
            L70:
                r17.a()
            L73:
                return
            L74:
                int r5 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                if (r5 == 0) goto L7b
                io.reactivex.internal.util.BackpressureHelper.produced(r0, r9)
            L7b:
                java.util.concurrent.atomic.AtomicInteger r5 = r0.f
                int r4 = -r4
                int r4 = r5.addAndGet(r4)
                if (r4 != 0) goto L11
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableCreate.e.d():void");
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.a, io.reactivex.Emitter
        public void onComplete() {
            this.e = true;
            d();
        }

        @Override // io.reactivex.Emitter
        public void onNext(T t) {
            if (this.e || isCancelled()) {
                return;
            }
            if (t != null) {
                this.c.set(t);
                d();
            } else {
                NullPointerException nullPointerException = new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
                if (tryOnError(nullPointerException)) {
                    return;
                }
                RxJavaPlugins.onError(nullPointerException);
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableCreate.a, io.reactivex.FlowableEmitter
        public boolean tryOnError(Throwable th) {
            if (this.e || isCancelled()) {
                return false;
            }
            if (th == null) {
                onError(new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources."));
            }
            this.d = th;
            this.e = true;
            d();
            return true;
        }
    }

    public static final class f<T> extends a<T> {
        public static final long serialVersionUID = 3776720187248809713L;

        public f(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.Emitter
        public void onNext(T t) {
            long j;
            if (isCancelled()) {
                return;
            }
            if (t == null) {
                NullPointerException nullPointerException = new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
                if (tryOnError(nullPointerException)) {
                    return;
                }
                RxJavaPlugins.onError(nullPointerException);
                return;
            }
            this.a.onNext(t);
            do {
                j = get();
                if (j == 0) {
                    return;
                }
            } while (!compareAndSet(j, j - 1));
        }
    }

    public static abstract class g<T> extends a<T> {
        public static final long serialVersionUID = 4127754106204442833L;

        public g(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        public abstract void d();

        @Override // io.reactivex.Emitter
        public final void onNext(T t) {
            if (isCancelled()) {
                return;
            }
            if (t == null) {
                NullPointerException nullPointerException = new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
                if (tryOnError(nullPointerException)) {
                    return;
                }
                RxJavaPlugins.onError(nullPointerException);
                return;
            }
            if (get() == 0) {
                d();
            } else {
                this.a.onNext(t);
                BackpressureHelper.produced(this, 1L);
            }
        }
    }

    public static final class h<T> extends AtomicInteger implements FlowableEmitter<T> {
        public static final long serialVersionUID = 4883307006032401862L;
        public final a<T> a;
        public final AtomicThrowable b = new AtomicThrowable();
        public final SimplePlainQueue<T> c = new SpscLinkedArrayQueue(16);
        public volatile boolean d;

        public h(a<T> aVar) {
            this.a = aVar;
        }

        public void a() {
            if (getAndIncrement() == 0) {
                b();
            }
        }

        public void b() {
            a<T> aVar = this.a;
            SimplePlainQueue<T> simplePlainQueue = this.c;
            AtomicThrowable atomicThrowable = this.b;
            int iAddAndGet = 1;
            while (!aVar.isCancelled()) {
                if (atomicThrowable.get() != null) {
                    simplePlainQueue.clear();
                    aVar.onError(atomicThrowable.terminate());
                    return;
                }
                boolean z = this.d;
                T tPoll = simplePlainQueue.poll();
                boolean z2 = tPoll == null;
                if (z && z2) {
                    aVar.onComplete();
                    return;
                } else if (z2) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    aVar.onNext(tPoll);
                }
            }
            simplePlainQueue.clear();
        }

        @Override // io.reactivex.FlowableEmitter
        public boolean isCancelled() {
            return this.a.isCancelled();
        }

        @Override // io.reactivex.Emitter
        public void onComplete() {
            if (this.a.isCancelled() || this.d) {
                return;
            }
            this.d = true;
            a();
        }

        @Override // io.reactivex.Emitter
        public void onError(Throwable th) {
            boolean z = false;
            if (!this.a.isCancelled() && !this.d) {
                if (this.b.addThrowable(th == null ? new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.") : th)) {
                    z = true;
                    this.d = true;
                    a();
                }
            }
            if (z) {
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Emitter
        public void onNext(T t) {
            if (this.a.isCancelled() || this.d) {
                return;
            }
            if (t == null) {
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            }
            if (get() == 0 && compareAndSet(0, 1)) {
                this.a.onNext(t);
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimplePlainQueue<T> simplePlainQueue = this.c;
                synchronized (simplePlainQueue) {
                    simplePlainQueue.offer(t);
                }
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            b();
        }

        @Override // io.reactivex.FlowableEmitter
        public long requested() {
            return this.a.get();
        }

        @Override // io.reactivex.FlowableEmitter
        public FlowableEmitter<T> serialize() {
            return this;
        }

        @Override // io.reactivex.FlowableEmitter
        public void setCancellable(Cancellable cancellable) {
            a<T> aVar = this.a;
            if (aVar == null) {
                throw null;
            }
            aVar.b.update(new CancellableDisposable(cancellable));
        }

        @Override // io.reactivex.FlowableEmitter
        public void setDisposable(Disposable disposable) {
            this.a.b.update(disposable);
        }

        @Override // java.util.concurrent.atomic.AtomicInteger
        public String toString() {
            return this.a.toString();
        }

        @Override // io.reactivex.FlowableEmitter
        public boolean tryOnError(Throwable th) {
            if (!this.a.isCancelled() && !this.d) {
                if (th == null) {
                    th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
                }
                if (this.b.addThrowable(th)) {
                    this.d = true;
                    a();
                    return true;
                }
            }
            return false;
        }
    }

    public FlowableCreate(FlowableOnSubscribe<T> flowableOnSubscribe, BackpressureStrategy backpressureStrategy) {
        this.b = flowableOnSubscribe;
        this.c = backpressureStrategy;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        int iOrdinal = this.c.ordinal();
        a bVar = iOrdinal != 0 ? iOrdinal != 1 ? iOrdinal != 3 ? iOrdinal != 4 ? new b(subscriber, Flowable.bufferSize()) : new e(subscriber) : new c(subscriber) : new d(subscriber) : new f(subscriber);
        subscriber.onSubscribe(bVar);
        try {
            this.b.subscribe(bVar);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (bVar.tryOnError(th)) {
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }

    public static abstract class a<T> extends AtomicLong implements FlowableEmitter<T>, Subscription {
        public static final long serialVersionUID = 7326289992464377023L;
        public final Subscriber<? super T> a;
        public final SequentialDisposable b = new SequentialDisposable();

        public a(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        public void a() {
            if (isCancelled()) {
                return;
            }
            try {
                this.a.onComplete();
            } finally {
                this.b.dispose();
            }
        }

        public void b() {
        }

        public void c() {
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.b.dispose();
            c();
        }

        @Override // io.reactivex.FlowableEmitter
        public final boolean isCancelled() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.Emitter
        public void onComplete() {
            a();
        }

        @Override // io.reactivex.Emitter
        public final void onError(Throwable th) {
            if (tryOnError(th)) {
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
                b();
            }
        }

        @Override // io.reactivex.FlowableEmitter
        public final long requested() {
            return get();
        }

        @Override // io.reactivex.FlowableEmitter
        public final FlowableEmitter<T> serialize() {
            return new h(this);
        }

        @Override // io.reactivex.FlowableEmitter
        public final void setCancellable(Cancellable cancellable) {
            this.b.update(new CancellableDisposable(cancellable));
        }

        @Override // io.reactivex.FlowableEmitter
        public final void setDisposable(Disposable disposable) {
            this.b.update(disposable);
        }

        @Override // java.util.concurrent.atomic.AtomicLong
        public String toString() {
            return String.format("%s{%s}", getClass().getSimpleName(), super.toString());
        }

        @Override // io.reactivex.FlowableEmitter
        public boolean tryOnError(Throwable th) {
            return a(th);
        }

        public boolean a(Throwable th) {
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (isCancelled()) {
                return false;
            }
            try {
                this.a.onError(th);
                this.b.dispose();
                return true;
            } catch (Throwable th2) {
                this.b.dispose();
                throw th2;
            }
        }
    }
}
