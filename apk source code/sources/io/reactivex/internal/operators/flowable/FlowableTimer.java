package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableTimer extends Flowable<Long> {
    public final Scheduler b;
    public final long c;
    public final TimeUnit d;

    public static final class a extends AtomicReference<Disposable> implements Subscription, Runnable {
        public static final long serialVersionUID = -2809475196591179431L;
        public final Subscriber<? super Long> a;
        public volatile boolean b;

        public a(Subscriber<? super Long> subscriber) {
            this.a = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            DisposableHelper.dispose(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                this.b = true;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (get() != DisposableHelper.DISPOSED) {
                if (!this.b) {
                    lazySet(EmptyDisposable.INSTANCE);
                    this.a.onError(new MissingBackpressureException("Can't deliver value due to lack of requests"));
                } else {
                    this.a.onNext(0L);
                    lazySet(EmptyDisposable.INSTANCE);
                    this.a.onComplete();
                }
            }
        }
    }

    public FlowableTimer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.c = j;
        this.d = timeUnit;
        this.b = scheduler;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Long> subscriber) {
        a aVar = new a(subscriber);
        subscriber.onSubscribe(aVar);
        DisposableHelper.trySet(aVar, this.b.scheduleDirect(aVar, this.c, this.d));
    }
}
