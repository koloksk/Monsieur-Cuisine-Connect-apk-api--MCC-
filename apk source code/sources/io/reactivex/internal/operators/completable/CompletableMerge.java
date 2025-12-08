package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class CompletableMerge extends Completable {
    public final Publisher<? extends CompletableSource> a;
    public final int b;
    public final boolean c;

    public static final class a extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
        public static final long serialVersionUID = -2108443387387077490L;
        public final CompletableObserver a;
        public final int b;
        public final boolean c;
        public Subscription f;
        public final CompositeDisposable e = new CompositeDisposable();
        public final AtomicThrowable d = new AtomicThrowable();

        /* renamed from: io.reactivex.internal.operators.completable.CompletableMerge$a$a, reason: collision with other inner class name */
        public final class C0012a extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
            public static final long serialVersionUID = 251330541679988317L;

            public C0012a() {
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                a aVar = a.this;
                aVar.e.delete(this);
                if (aVar.decrementAndGet() != 0) {
                    if (aVar.b != Integer.MAX_VALUE) {
                        aVar.f.request(1L);
                    }
                } else {
                    Throwable th = aVar.d.get();
                    if (th != null) {
                        aVar.a.onError(th);
                    } else {
                        aVar.a.onComplete();
                    }
                }
            }

            @Override // io.reactivex.CompletableObserver
            public void onError(Throwable th) {
                a aVar = a.this;
                aVar.e.delete(this);
                if (!aVar.c) {
                    aVar.f.cancel();
                    aVar.e.dispose();
                    if (!aVar.d.addThrowable(th)) {
                        RxJavaPlugins.onError(th);
                        return;
                    } else {
                        if (aVar.getAndSet(0) > 0) {
                            aVar.a.onError(aVar.d.terminate());
                            return;
                        }
                        return;
                    }
                }
                if (!aVar.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                } else if (aVar.decrementAndGet() == 0) {
                    aVar.a.onError(aVar.d.terminate());
                } else if (aVar.b != Integer.MAX_VALUE) {
                    aVar.f.request(1L);
                }
            }

            @Override // io.reactivex.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }
        }

        public a(CompletableObserver completableObserver, int i, boolean z) {
            this.a = completableObserver;
            this.b = i;
            this.c = z;
            lazySet(1);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f.cancel();
            this.e.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e.isDisposed();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (decrementAndGet() == 0) {
                if (this.d.get() != null) {
                    this.a.onError(this.d.terminate());
                } else {
                    this.a.onComplete();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.c) {
                if (!this.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                } else {
                    if (decrementAndGet() == 0) {
                        this.a.onError(this.d.terminate());
                        return;
                    }
                    return;
                }
            }
            this.e.dispose();
            if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (getAndSet(0) > 0) {
                this.a.onError(this.d.terminate());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            getAndIncrement();
            C0012a c0012a = new C0012a();
            this.e.add(c0012a);
            ((CompletableSource) obj).subscribe(c0012a);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                this.a.onSubscribe(this);
                int i = this.b;
                if (i == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request(i);
                }
            }
        }
    }

    public CompletableMerge(Publisher<? extends CompletableSource> publisher, int i, boolean z) {
        this.a = publisher;
        this.b = i;
        this.c = z;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new a(completableObserver, this.b, this.c));
    }
}
