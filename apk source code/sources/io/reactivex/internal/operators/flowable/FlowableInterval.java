package io.reactivex.internal.operators.flowable;

import defpackage.g9;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableInterval extends Flowable<Long> {
    public final Scheduler b;
    public final long c;
    public final long d;
    public final TimeUnit e;

    public static final class a extends AtomicLong implements Subscription, Runnable {
        public static final long serialVersionUID = -2809475196591179431L;
        public final Subscriber<? super Long> a;
        public long b;
        public final AtomicReference<Disposable> c = new AtomicReference<>();

        public a(Subscriber<? super Long> subscriber) {
            this.a = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            DisposableHelper.dispose(this.c);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.c.get() != DisposableHelper.DISPOSED) {
                if (get() != 0) {
                    Subscriber<? super Long> subscriber = this.a;
                    long j = this.b;
                    this.b = j + 1;
                    subscriber.onNext(Long.valueOf(j));
                    BackpressureHelper.produced(this, 1L);
                    return;
                }
                Subscriber<? super Long> subscriber2 = this.a;
                StringBuilder sbA = g9.a("Can't deliver value ");
                sbA.append(this.b);
                sbA.append(" due to lack of requests");
                subscriber2.onError(new MissingBackpressureException(sbA.toString()));
                DisposableHelper.dispose(this.c);
            }
        }
    }

    public FlowableInterval(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        this.c = j;
        this.d = j2;
        this.e = timeUnit;
        this.b = scheduler;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Long> subscriber) {
        a aVar = new a(subscriber);
        subscriber.onSubscribe(aVar);
        Scheduler scheduler = this.b;
        if (!(scheduler instanceof TrampolineScheduler)) {
            DisposableHelper.setOnce(aVar.c, scheduler.schedulePeriodicallyDirect(aVar, this.c, this.d, this.e));
        } else {
            Scheduler.Worker workerCreateWorker = scheduler.createWorker();
            DisposableHelper.setOnce(aVar.c, workerCreateWorker);
            workerCreateWorker.schedulePeriodically(aVar, this.c, this.d, this.e);
        }
    }
}
