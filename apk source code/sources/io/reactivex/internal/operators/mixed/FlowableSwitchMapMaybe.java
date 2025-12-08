package io.reactivex.internal.operators.mixed;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
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
public final class FlowableSwitchMapMaybe<T, R> extends Flowable<R> {
    public final Flowable<T> b;
    public final Function<? super T, ? extends MaybeSource<? extends R>> c;
    public final boolean d;

    public static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public static final C0044a<Object> k = new C0044a<>(null);
        public static final long serialVersionUID = -5402190102429853762L;
        public final Subscriber<? super R> a;
        public final Function<? super T, ? extends MaybeSource<? extends R>> b;
        public final boolean c;
        public final AtomicThrowable d = new AtomicThrowable();
        public final AtomicLong e = new AtomicLong();
        public final AtomicReference<C0044a<R>> f = new AtomicReference<>();
        public Subscription g;
        public volatile boolean h;
        public volatile boolean i;
        public long j;

        /* renamed from: io.reactivex.internal.operators.mixed.FlowableSwitchMapMaybe$a$a, reason: collision with other inner class name */
        public static final class C0044a<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            public static final long serialVersionUID = 8042919737683345351L;
            public final a<?, R> a;
            public volatile R b;

            public C0044a(a<?, R> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                a<?, R> aVar = this.a;
                if (aVar.f.compareAndSet(this, null)) {
                    aVar.b();
                }
            }

            @Override // io.reactivex.MaybeObserver
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

            @Override // io.reactivex.MaybeObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSuccess(R r) {
                this.b = r;
                this.a.b();
            }
        }

        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z) {
            this.a = subscriber;
            this.b = function;
            this.c = z;
        }

        public void a() {
            C0044a<Object> c0044a = (C0044a) this.f.getAndSet(k);
            if (c0044a == null || c0044a == k) {
                return;
            }
            DisposableHelper.dispose(c0044a);
        }

        public void b() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super R> subscriber = this.a;
            AtomicThrowable atomicThrowable = this.d;
            AtomicReference<C0044a<R>> atomicReference = this.f;
            AtomicLong atomicLong = this.e;
            long j = this.j;
            int iAddAndGet = 1;
            while (!this.i) {
                if (atomicThrowable.get() != null && !this.c) {
                    subscriber.onError(atomicThrowable.terminate());
                    return;
                }
                boolean z = this.h;
                C0044a<R> c0044a = atomicReference.get();
                boolean z2 = c0044a == null;
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
                if (z2 || c0044a.b == null || j == atomicLong.get()) {
                    this.j = j;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    atomicReference.compareAndSet(c0044a, null);
                    subscriber.onNext(c0044a.b);
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
            C0044a<R> c0044a;
            C0044a<R> c0044a2 = this.f.get();
            if (c0044a2 != null) {
                DisposableHelper.dispose(c0044a2);
            }
            try {
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null MaybeSource");
                C0044a<R> c0044a3 = new C0044a<>(this);
                do {
                    c0044a = this.f.get();
                    if (c0044a == k) {
                        return;
                    }
                } while (!this.f.compareAndSet(c0044a, c0044a3));
                maybeSource.subscribe(c0044a3);
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

    public FlowableSwitchMapMaybe(Flowable<T> flowable, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z) {
        this.b = flowable;
        this.c = function;
        this.d = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe((FlowableSubscriber) new a(subscriber, this.c, this.d));
    }
}
