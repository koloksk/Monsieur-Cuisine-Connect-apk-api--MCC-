package io.reactivex.internal.operators.maybe;

import defpackage.al;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class MaybeDelayOtherPublisher<T, U> extends al<T, T> {
    public final Publisher<U> a;

    public static final class a<T, U> implements MaybeObserver<T>, Disposable {
        public final b<T> a;
        public final Publisher<U> b;
        public Disposable c;

        public a(MaybeObserver<? super T> maybeObserver, Publisher<U> publisher) {
            this.a = new b<>(maybeObserver);
            this.b = publisher;
        }

        public void a() {
            this.b.subscribe(this.a);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
            SubscriptionHelper.cancel(this.a);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.a.get() == SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.c = DisposableHelper.DISPOSED;
            a();
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.c = DisposableHelper.DISPOSED;
            this.a.c = th;
            a();
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            this.c = DisposableHelper.DISPOSED;
            this.a.b = t;
            a();
        }
    }

    public static final class b<T> extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        public static final long serialVersionUID = -1215060610805418006L;
        public final MaybeObserver<? super T> a;
        public T b;
        public Throwable c;

        public b(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Throwable th = this.c;
            if (th != null) {
                this.a.onError(th);
                return;
            }
            T t = this.b;
            if (t != null) {
                this.a.onSuccess(t);
            } else {
                this.a.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Throwable th2 = this.c;
            if (th2 == null) {
                this.a.onError(th);
            } else {
                this.a.onError(new CompositeException(th2, th));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Subscription subscription = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription != subscriptionHelper) {
                lazySet(subscriptionHelper);
                subscription.cancel();
                onComplete();
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public MaybeDelayOtherPublisher(MaybeSource<T> maybeSource, Publisher<U> publisher) {
        super(maybeSource);
        this.a = publisher;
    }

    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a));
    }
}
