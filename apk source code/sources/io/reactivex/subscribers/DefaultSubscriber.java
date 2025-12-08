package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public abstract class DefaultSubscriber<T> implements FlowableSubscriber<T> {
    public Subscription a;

    public final void cancel() {
        Subscription subscription = this.a;
        this.a = SubscriptionHelper.CANCELLED;
        subscription.cancel();
    }

    public void onStart() {
        request(Long.MAX_VALUE);
    }

    @Override // io.reactivex.FlowableSubscriber
    public final void onSubscribe(Subscription subscription) {
        if (EndConsumerHelper.validate(this.a, subscription, getClass())) {
            this.a = subscription;
            onStart();
        }
    }

    public final void request(long j) {
        Subscription subscription = this.a;
        if (subscription != null) {
            subscription.request(j);
        }
    }
}
