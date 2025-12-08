package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class InnerQueuedSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
    public static final long serialVersionUID = 22876611072430776L;
    public final InnerQueuedSubscriberSupport<T> a;
    public final int b;
    public final int c;
    public volatile SimpleQueue<T> d;
    public volatile boolean e;
    public long f;
    public int g;

    public InnerQueuedSubscriber(InnerQueuedSubscriberSupport<T> innerQueuedSubscriberSupport, int i) {
        this.a = innerQueuedSubscriberSupport;
        this.b = i;
        this.c = i - (i >> 2);
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        SubscriptionHelper.cancel(this);
    }

    public boolean isDone() {
        return this.e;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        this.a.innerComplete(this);
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.a.innerError(this, th);
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.g == 0) {
            this.a.innerNext(this, t);
        } else {
            this.a.drain();
        }
    }

    @Override // io.reactivex.FlowableSubscriber
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this, subscription)) {
            if (subscription instanceof QueueSubscription) {
                QueueSubscription queueSubscription = (QueueSubscription) subscription;
                int iRequestFusion = queueSubscription.requestFusion(3);
                if (iRequestFusion == 1) {
                    this.g = iRequestFusion;
                    this.d = queueSubscription;
                    this.e = true;
                    this.a.innerComplete(this);
                    return;
                }
                if (iRequestFusion == 2) {
                    this.g = iRequestFusion;
                    this.d = queueSubscription;
                    QueueDrainHelper.request(subscription, this.b);
                    return;
                }
            }
            this.d = QueueDrainHelper.createQueue(this.b);
            QueueDrainHelper.request(subscription, this.b);
        }
    }

    public SimpleQueue<T> queue() {
        return this.d;
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        if (this.g != 1) {
            long j2 = this.f + j;
            if (j2 < this.c) {
                this.f = j2;
            } else {
                this.f = 0L;
                get().request(j2);
            }
        }
    }

    public void requestOne() {
        if (this.g != 1) {
            long j = this.f + 1;
            if (j != this.c) {
                this.f = j;
            } else {
                this.f = 0L;
                get().request(j);
            }
        }
    }

    public void setDone() {
        this.e = true;
    }
}
