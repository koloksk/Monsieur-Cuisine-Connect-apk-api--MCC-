package io.reactivex.internal.subscriptions;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class AsyncSubscription extends AtomicLong implements Subscription, Disposable {
    public static final long serialVersionUID = 7028635084060361255L;
    public final AtomicReference<Subscription> a;
    public final AtomicReference<Disposable> b;

    public AsyncSubscription() {
        this.b = new AtomicReference<>();
        this.a = new AtomicReference<>();
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        dispose();
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        SubscriptionHelper.cancel(this.a);
        DisposableHelper.dispose(this.b);
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return this.a.get() == SubscriptionHelper.CANCELLED;
    }

    public boolean replaceResource(Disposable disposable) {
        return DisposableHelper.replace(this.b, disposable);
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        SubscriptionHelper.deferredRequest(this.a, this, j);
    }

    public boolean setResource(Disposable disposable) {
        return DisposableHelper.set(this.b, disposable);
    }

    public void setSubscription(Subscription subscription) {
        SubscriptionHelper.deferredSetOnce(this.a, this, subscription);
    }

    public AsyncSubscription(Disposable disposable) {
        this();
        this.b.lazySet(disposable);
    }
}
