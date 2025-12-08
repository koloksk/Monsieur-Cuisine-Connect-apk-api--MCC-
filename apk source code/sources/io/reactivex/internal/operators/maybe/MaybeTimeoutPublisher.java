package io.reactivex.internal.operators.maybe;

import defpackage.al;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class MaybeTimeoutPublisher<T, U> extends al<T, T> {
    public final Publisher<U> a;
    public final MaybeSource<? extends T> b;

    public static final class a<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        public static final long serialVersionUID = 8663801314800248617L;
        public final MaybeObserver<? super T> a;

        public a(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            this.a.onSuccess(t);
        }
    }

    public static final class b<T, U> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        public static final long serialVersionUID = -5955289211445418871L;
        public final MaybeObserver<? super T> a;
        public final c<T, U> b = new c<>(this);
        public final MaybeSource<? extends T> c;
        public final a<T> d;

        public b(MaybeObserver<? super T> maybeObserver, MaybeSource<? extends T> maybeSource) {
            this.a = maybeObserver;
            this.c = maybeSource;
            this.d = maybeSource != null ? new a<>(maybeObserver) : null;
        }

        public void a() {
            if (DisposableHelper.dispose(this)) {
                MaybeSource<? extends T> maybeSource = this.c;
                if (maybeSource == null) {
                    this.a.onError(new TimeoutException());
                } else {
                    maybeSource.subscribe(this.d);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            SubscriptionHelper.cancel(this.b);
            a<T> aVar = this.d;
            if (aVar != null) {
                DisposableHelper.dispose(aVar);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            SubscriptionHelper.cancel(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            SubscriptionHelper.cancel(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onSuccess(t);
            }
        }
    }

    public static final class c<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        public static final long serialVersionUID = 8663801314800248617L;
        public final b<T, U> a;

        public c(b<T, U> bVar) {
            this.a = bVar;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            b<T, U> bVar = this.a;
            if (bVar == null) {
                throw null;
            }
            if (DisposableHelper.dispose(bVar)) {
                bVar.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            get().cancel();
            this.a.a();
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public MaybeTimeoutPublisher(MaybeSource<T> maybeSource, Publisher<U> publisher, MaybeSource<? extends T> maybeSource2) {
        super(maybeSource);
        this.a = publisher;
        this.b = maybeSource2;
    }

    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        b bVar = new b(maybeObserver, this.b);
        maybeObserver.onSubscribe(bVar);
        this.a.subscribe(bVar.b);
        this.source.subscribe(bVar);
    }
}
