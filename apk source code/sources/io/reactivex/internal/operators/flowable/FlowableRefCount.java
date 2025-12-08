package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableRefCount<T> extends Flowable<T> {
    public final ConnectableFlowable<T> b;
    public final int c;
    public final long d;
    public final TimeUnit e;
    public final Scheduler f;
    public a g;

    public static final class a extends AtomicReference<Disposable> implements Runnable, Consumer<Disposable> {
        public static final long serialVersionUID = -4552101107598366241L;
        public final FlowableRefCount<?> a;
        public Disposable b;
        public long c;
        public boolean d;
        public boolean e;

        public a(FlowableRefCount<?> flowableRefCount) {
            this.a = flowableRefCount;
        }

        @Override // io.reactivex.functions.Consumer
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(Disposable disposable) throws Exception {
            DisposableHelper.replace(this, disposable);
            synchronized (this.a) {
                if (this.e) {
                    ((ResettableConnectable) this.a.b).resetIf(disposable);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.d(this);
        }
    }

    public static final class b<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
        public static final long serialVersionUID = -7419642935409022375L;
        public final Subscriber<? super T> a;
        public final FlowableRefCount<T> b;
        public final a c;
        public Subscription d;

        public b(Subscriber<? super T> subscriber, FlowableRefCount<T> flowableRefCount, a aVar) {
            this.a = subscriber;
            this.b = flowableRefCount;
            this.c = aVar;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.d.cancel();
            if (compareAndSet(false, true)) {
                this.b.a(this.c);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (compareAndSet(false, true)) {
                this.b.c(this.c);
                this.a.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!compareAndSet(false, true)) {
                RxJavaPlugins.onError(th);
            } else {
                this.b.c(this.c);
                this.a.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.d.request(j);
        }
    }

    public FlowableRefCount(ConnectableFlowable<T> connectableFlowable) {
        this(connectableFlowable, 1, 0L, TimeUnit.NANOSECONDS, null);
    }

    public void a(a aVar) {
        synchronized (this) {
            if (this.g != null && this.g == aVar) {
                long j = aVar.c - 1;
                aVar.c = j;
                if (j == 0 && aVar.d) {
                    if (this.d == 0) {
                        d(aVar);
                        return;
                    }
                    SequentialDisposable sequentialDisposable = new SequentialDisposable();
                    aVar.b = sequentialDisposable;
                    sequentialDisposable.replace(this.f.scheduleDirect(aVar, this.d, this.e));
                }
            }
        }
    }

    public void b(a aVar) {
        ConnectableFlowable<T> connectableFlowable = this.b;
        if (connectableFlowable instanceof Disposable) {
            ((Disposable) connectableFlowable).dispose();
        } else if (connectableFlowable instanceof ResettableConnectable) {
            ((ResettableConnectable) connectableFlowable).resetIf(aVar.get());
        }
    }

    public void c(a aVar) {
        synchronized (this) {
            if (this.b instanceof FlowablePublishClassic) {
                if (this.g != null && this.g == aVar) {
                    this.g = null;
                    Disposable disposable = aVar.b;
                    if (disposable != null) {
                        disposable.dispose();
                        aVar.b = null;
                    }
                }
                long j = aVar.c - 1;
                aVar.c = j;
                if (j == 0) {
                    b(aVar);
                }
            } else if (this.g != null && this.g == aVar) {
                Disposable disposable2 = aVar.b;
                if (disposable2 != null) {
                    disposable2.dispose();
                    aVar.b = null;
                }
                long j2 = aVar.c - 1;
                aVar.c = j2;
                if (j2 == 0) {
                    this.g = null;
                    b(aVar);
                }
            }
        }
    }

    public void d(a aVar) {
        synchronized (this) {
            if (aVar.c == 0 && aVar == this.g) {
                this.g = null;
                Disposable disposable = aVar.get();
                DisposableHelper.dispose(aVar);
                if (this.b instanceof Disposable) {
                    ((Disposable) this.b).dispose();
                } else if (this.b instanceof ResettableConnectable) {
                    if (disposable == null) {
                        aVar.e = true;
                    } else {
                        ((ResettableConnectable) this.b).resetIf(disposable);
                    }
                }
            }
        }
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar;
        boolean z;
        synchronized (this) {
            aVar = this.g;
            if (aVar == null) {
                aVar = new a(this);
                this.g = aVar;
            }
            long j = aVar.c;
            if (j == 0 && aVar.b != null) {
                aVar.b.dispose();
            }
            long j2 = j + 1;
            aVar.c = j2;
            z = true;
            if (aVar.d || j2 != this.c) {
                z = false;
            } else {
                aVar.d = true;
            }
        }
        this.b.subscribe((FlowableSubscriber) new b(subscriber, this, aVar));
        if (z) {
            this.b.connect(aVar);
        }
    }

    public FlowableRefCount(ConnectableFlowable<T> connectableFlowable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.b = connectableFlowable;
        this.c = i;
        this.d = j;
        this.e = timeUnit;
        this.f = scheduler;
    }
}
