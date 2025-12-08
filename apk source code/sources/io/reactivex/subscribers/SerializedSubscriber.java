package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class SerializedSubscriber<T> implements FlowableSubscriber<T>, Subscription {
    public final Subscriber<? super T> a;
    public final boolean b;
    public Subscription c;
    public boolean d;
    public AppendOnlyLinkedArrayList<Object> e;
    public volatile boolean f;

    public SerializedSubscriber(Subscriber<? super T> subscriber) {
        this(subscriber, false);
    }

    public void a() {
        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
        do {
            synchronized (this) {
                appendOnlyLinkedArrayList = this.e;
                if (appendOnlyLinkedArrayList == null) {
                    this.d = false;
                    return;
                }
                this.e = null;
            }
        } while (!appendOnlyLinkedArrayList.accept(this.a));
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        this.c.cancel();
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.f) {
            return;
        }
        synchronized (this) {
            if (this.f) {
                return;
            }
            if (!this.d) {
                this.f = true;
                this.d = true;
                this.a.onComplete();
            } else {
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.e;
                if (appendOnlyLinkedArrayList == null) {
                    appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                    this.e = appendOnlyLinkedArrayList;
                }
                appendOnlyLinkedArrayList.add(NotificationLite.complete());
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.f) {
            RxJavaPlugins.onError(th);
            return;
        }
        synchronized (this) {
            boolean z = true;
            if (!this.f) {
                if (this.d) {
                    this.f = true;
                    AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.e;
                    if (appendOnlyLinkedArrayList == null) {
                        appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                        this.e = appendOnlyLinkedArrayList;
                    }
                    Object objError = NotificationLite.error(th);
                    if (this.b) {
                        appendOnlyLinkedArrayList.add(objError);
                    } else {
                        appendOnlyLinkedArrayList.setFirst(objError);
                    }
                    return;
                }
                this.f = true;
                this.d = true;
                z = false;
            }
            if (z) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.f) {
            return;
        }
        if (t == null) {
            this.c.cancel();
            onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            return;
        }
        synchronized (this) {
            if (this.f) {
                return;
            }
            if (!this.d) {
                this.d = true;
                this.a.onNext(t);
                a();
            } else {
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.e;
                if (appendOnlyLinkedArrayList == null) {
                    appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                    this.e = appendOnlyLinkedArrayList;
                }
                appendOnlyLinkedArrayList.add(NotificationLite.next(t));
            }
        }
    }

    @Override // io.reactivex.FlowableSubscriber
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.c, subscription)) {
            this.c = subscription;
            this.a.onSubscribe(this);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        this.c.request(j);
    }

    public SerializedSubscriber(Subscriber<? super T> subscriber, boolean z) {
        this.a = subscriber;
        this.b = z;
    }
}
