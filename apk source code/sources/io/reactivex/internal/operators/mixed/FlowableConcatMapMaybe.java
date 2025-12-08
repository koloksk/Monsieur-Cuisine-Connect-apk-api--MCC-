package io.reactivex.internal.operators.mixed;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableConcatMapMaybe<T, R> extends Flowable<R> {
    public final Flowable<T> b;
    public final Function<? super T, ? extends MaybeSource<? extends R>> c;
    public final ErrorMode d;
    public final int e;

    public static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = -9140123220065488293L;
        public final Subscriber<? super R> a;
        public final Function<? super T, ? extends MaybeSource<? extends R>> b;
        public final int c;
        public final AtomicLong d = new AtomicLong();
        public final AtomicThrowable e = new AtomicThrowable();
        public final C0041a<R> f = new C0041a<>(this);
        public final SimplePlainQueue<T> g;
        public final ErrorMode h;
        public Subscription i;
        public volatile boolean j;
        public volatile boolean k;
        public long l;
        public int m;
        public R n;
        public volatile int o;

        /* renamed from: io.reactivex.internal.operators.mixed.FlowableConcatMapMaybe$a$a, reason: collision with other inner class name */
        public static final class C0041a<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            public static final long serialVersionUID = -3051469169682093892L;
            public final a<?, R> a;

            public C0041a(a<?, R> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                a<?, R> aVar = this.a;
                aVar.o = 0;
                aVar.a();
            }

            @Override // io.reactivex.MaybeObserver
            public void onError(Throwable th) {
                a<?, R> aVar = this.a;
                if (!aVar.e.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (aVar.h != ErrorMode.END) {
                    aVar.i.cancel();
                }
                aVar.o = 0;
                aVar.a();
            }

            @Override // io.reactivex.MaybeObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSuccess(R r) {
                a<?, R> aVar = this.a;
                aVar.n = r;
                aVar.o = 2;
                aVar.a();
            }
        }

        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends MaybeSource<? extends R>> function, int i, ErrorMode errorMode) {
            this.a = subscriber;
            this.b = function;
            this.c = i;
            this.h = errorMode;
            this.g = new SpscArrayQueue(i);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0036, code lost:
        
            r2.clear();
            r15.n = null;
            r0.onError(r3.terminate());
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0042, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a() {
            /*
                r15 = this;
                int r0 = r15.getAndIncrement()
                if (r0 == 0) goto L7
                return
            L7:
                org.reactivestreams.Subscriber<? super R> r0 = r15.a
                io.reactivex.internal.util.ErrorMode r1 = r15.h
                io.reactivex.internal.fuseable.SimplePlainQueue<T> r2 = r15.g
                io.reactivex.internal.util.AtomicThrowable r3 = r15.e
                java.util.concurrent.atomic.AtomicLong r4 = r15.d
                int r5 = r15.c
                int r6 = r5 >> 1
                int r5 = r5 - r6
                r6 = 1
                r7 = r6
            L18:
                boolean r8 = r15.k
                r9 = 0
                if (r8 == 0) goto L24
                r2.clear()
                r15.n = r9
                goto Lc0
            L24:
                int r8 = r15.o
                java.lang.Object r10 = r3.get()
                if (r10 == 0) goto L43
                io.reactivex.internal.util.ErrorMode r10 = io.reactivex.internal.util.ErrorMode.IMMEDIATE
                if (r1 == r10) goto L36
                io.reactivex.internal.util.ErrorMode r10 = io.reactivex.internal.util.ErrorMode.BOUNDARY
                if (r1 != r10) goto L43
                if (r8 != 0) goto L43
            L36:
                r2.clear()
                r15.n = r9
                java.lang.Throwable r1 = r3.terminate()
                r0.onError(r1)
                return
            L43:
                r10 = 0
                if (r8 != 0) goto La3
                boolean r8 = r15.j
                java.lang.Object r9 = r2.poll()
                if (r9 != 0) goto L50
                r11 = r6
                goto L51
            L50:
                r11 = r10
            L51:
                if (r8 == 0) goto L63
                if (r11 == 0) goto L63
                java.lang.Throwable r1 = r3.terminate()
                if (r1 != 0) goto L5f
                r0.onComplete()
                goto L62
            L5f:
                r0.onError(r1)
            L62:
                return
            L63:
                if (r11 == 0) goto L66
                goto Lc0
            L66:
                int r8 = r15.m
                int r8 = r8 + r6
                if (r8 != r5) goto L74
                r15.m = r10
                org.reactivestreams.Subscription r8 = r15.i
                long r10 = (long) r5
                r8.request(r10)
                goto L76
            L74:
                r15.m = r8
            L76:
                io.reactivex.functions.Function<? super T, ? extends io.reactivex.MaybeSource<? extends R>> r8 = r15.b     // Catch: java.lang.Throwable -> L8c
                java.lang.Object r8 = r8.apply(r9)     // Catch: java.lang.Throwable -> L8c
                java.lang.String r9 = "The mapper returned a null MaybeSource"
                java.lang.Object r8 = io.reactivex.internal.functions.ObjectHelper.requireNonNull(r8, r9)     // Catch: java.lang.Throwable -> L8c
                io.reactivex.MaybeSource r8 = (io.reactivex.MaybeSource) r8     // Catch: java.lang.Throwable -> L8c
                r15.o = r6
                io.reactivex.internal.operators.mixed.FlowableConcatMapMaybe$a$a<R> r9 = r15.f
                r8.subscribe(r9)
                goto Lc0
            L8c:
                r1 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r1)
                org.reactivestreams.Subscription r4 = r15.i
                r4.cancel()
                r2.clear()
                r3.addThrowable(r1)
                java.lang.Throwable r1 = r3.terminate()
                r0.onError(r1)
                return
            La3:
                r11 = 2
                if (r8 != r11) goto Lc0
                long r11 = r15.l
                long r13 = r4.get()
                int r8 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
                if (r8 == 0) goto Lc0
                R r8 = r15.n
                r15.n = r9
                r0.onNext(r8)
                r8 = 1
                long r11 = r11 + r8
                r15.l = r11
                r15.o = r10
                goto L18
            Lc0:
                int r7 = -r7
                int r7 = r15.addAndGet(r7)
                if (r7 != 0) goto L18
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.mixed.FlowableConcatMapMaybe.a.a():void");
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.k = true;
            this.i.cancel();
            C0041a<R> c0041a = this.f;
            if (c0041a == null) {
                throw null;
            }
            DisposableHelper.dispose(c0041a);
            if (getAndIncrement() == 0) {
                this.g.clear();
                this.n = null;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.j = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.e.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.h == ErrorMode.IMMEDIATE) {
                C0041a<R> c0041a = this.f;
                if (c0041a == null) {
                    throw null;
                }
                DisposableHelper.dispose(c0041a);
            }
            this.j = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.g.offer(t)) {
                a();
            } else {
                this.i.cancel();
                onError(new MissingBackpressureException("queue full?!"));
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.i, subscription)) {
                this.i = subscription;
                this.a.onSubscribe(this);
                subscription.request(this.c);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.d, j);
            a();
        }
    }

    public FlowableConcatMapMaybe(Flowable<T> flowable, Function<? super T, ? extends MaybeSource<? extends R>> function, ErrorMode errorMode, int i) {
        this.b = flowable;
        this.c = function;
        this.d = errorMode;
        this.e = i;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe((FlowableSubscriber) new a(subscriber, this.c, this.e, this.d));
    }
}
