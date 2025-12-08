package io.reactivex.internal.subscriptions;

import defpackage.g9;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class BooleanSubscription extends AtomicBoolean implements Subscription {
    public static final long serialVersionUID = -8127758972444290902L;

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        lazySet(true);
    }

    public boolean isCancelled() {
        return get();
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        SubscriptionHelper.validate(j);
    }

    @Override // java.util.concurrent.atomic.AtomicBoolean
    public String toString() {
        StringBuilder sbA = g9.a("BooleanSubscription(cancelled=");
        sbA.append(get());
        sbA.append(")");
        return sbA.toString();
    }
}
