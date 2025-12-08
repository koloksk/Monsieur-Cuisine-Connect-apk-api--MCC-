package io.reactivex.internal.operators.mixed;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
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
public final class FlowableSwitchMapSingle<T, R> extends Flowable<R> {
    public final Flowable<T> b;
    public final Function<? super T, ? extends SingleSource<? extends R>> c;
    public final boolean d;

    public static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final C0045a<Object> k = new C0045a<>(null);
        public static final long serialVersionUID = -5402190102429853762L;
        public final Subscriber<? super R> a;
        public final Function<? super T, ? extends SingleSource<? extends R>> b;
        public final boolean c;
        public final AtomicThrowable d = new AtomicThrowable();
        public final AtomicLong e = new AtomicLong();
        public final AtomicReference<C0045a<R>> f = new AtomicReference<>();
        public Subscription g;
        public volatile boolean h;
        public volatile boolean i;
        public long j;

        /* renamed from: io.reactivex.internal.operators.mixed.FlowableSwitchMapSingle$a$a, reason: collision with other inner class name */
        public static final class C0045a<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
            public static final long serialVersionUID = 8042919737683345351L;
            public final a<?, R> a;
            public volatile R b;

            public C0045a(a<?, R> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a<?, R> aVar = this.a;
                if (!aVar.f.compareAndSet(this, null) || !aVar.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (!aVar.c) {
                    aVar.g.cancel();
                    aVar.a();
                }
                aVar.b();
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(R r) {
                this.b = r;
                this.a.b();
            }
        }

        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
            this.a = subscriber;
            this.b = function;
            this.c = z;
        }

        public void a() {
            C0045a<Object> c0045a = (C0045a) this.f.getAndSet(k);
            if (c0045a == null || c0045a == k) {
                return;
            }
            DisposableHelper.dispose(c0045a);
        }

        public void b() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super R> subscriber = this.a;
            AtomicThrowable atomicThrowable = this.d;
            AtomicReference<C0045a<R>> atomicReference = this.f;
            AtomicLong atomicLong = this.e;
            long j = this.j;
            int iAddAndGet = 1;
            while (!this.i) {
                if (atomicThrowable.get() != null && !this.c) {
                    subscriber.onError(atomicThrowable.terminate());
                    return;
                }
                boolean z = this.h;
                C0045a<R> c0045a = atomicReference.get();
                boolean z2 = c0045a == null;
                if (z && z2) {
                    Throwable thTerminate = atomicThrowable.terminate();
                    if (thTerminate != null) {
                        subscriber.onError(thTerminate);
                        return;
                    } else {
                        subscriber.onComplete();
                        return;
                    }
                }
                if (z2 || c0045a.b == null || j == atomicLong.get()) {
                    this.j = j;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    atomicReference.compareAndSet(c0045a, null);
                    subscriber.onNext(c0045a.b);
                    j++;
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.i = true;
            this.g.cancel();
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.h = true;
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.c) {
                a();
            }
            this.h = true;
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            C0045a<R> c0045a;
            C0045a<R> c0045a2 = this.f.get();
            if (c0045a2 != null) {
                DisposableHelper.dispose(c0045a2);
            }
            try {
                SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null SingleSource");
                C0045a<R> c0045a3 = new C0045a<>(this);
                do {
                    c0045a = this.f.get();
                    if (c0045a == k) {
                        return;
                    }
                } while (!this.f.compareAndSet(c0045a, c0045a3));
                singleSource.subscribe(c0045a3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.g.cancel();
                this.f.getAndSet(k);
                onError(th);
            }
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
            BackpressureHelper.add(this.e, j);
            b();
        }
    }

    public FlowableSwitchMapSingle(Flowable<T> flowable, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
        this.b = flowable;
        this.c = function;
        this.d = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe((FlowableSubscriber) new a(subscriber, this.c, this.d));
    }
}
