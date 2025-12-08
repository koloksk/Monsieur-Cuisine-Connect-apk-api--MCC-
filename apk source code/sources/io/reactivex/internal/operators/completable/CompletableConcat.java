package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class CompletableConcat extends Completable {
    public final Publisher<? extends CompletableSource> a;
    public final int b;

    public CompletableConcat(Publisher<? extends CompletableSource> publisher, int i) {
        this.a = publisher;
        this.b = i;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new a(completableObserver, this.b));
    }

    public static final class a extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
        public static final long serialVersionUID = 9032184911934499404L;
        public final CompletableObserver a;
        public final int b;
        public final int c;
        public final C0011a d = new C0011a(this);
        public final AtomicBoolean e = new AtomicBoolean();
        public int f;
        public int g;
        public SimpleQueue<CompletableSource> h;
        public Subscription i;
        public volatile boolean j;
        public volatile boolean k;

        /* renamed from: io.reactivex.internal.operators.completable.CompletableConcat$a$a, reason: collision with other inner class name */
        public static final class C0011a extends AtomicReference<Disposable> implements CompletableObserver {
            public static final long serialVersionUID = -5454794857847146511L;
            public final a a;

            public C0011a(a aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                a aVar = this.a;
                aVar.k = false;
                aVar.a();
            }

            @Override // io.reactivex.CompletableObserver
            public void onError(Throwable th) {
                this.a.a(th);
            }

            @Override // io.reactivex.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }
        }

        public a(CompletableObserver completableObserver, int i) {
            this.a = completableObserver;
            this.b = i;
            this.c = i - (i >> 2);
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            while (!isDisposed()) {
                if (!this.k) {
                    boolean z = this.j;
                    try {
                        CompletableSource completableSourcePoll = this.h.poll();
                        boolean z2 = completableSourcePoll == null;
                        if (z && z2) {
                            if (this.e.compareAndSet(false, true)) {
                                this.a.onComplete();
                                return;
                            }
                            return;
                        } else if (!z2) {
                            this.k = true;
                            completableSourcePoll.subscribe(this.d);
                            if (this.f != 1) {
                                int i = this.g + 1;
                                if (i == this.c) {
                                    this.g = 0;
                                    this.i.request(i);
                                } else {
                                    this.g = i;
                                }
                            }
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        a(th);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.i.cancel();
            DisposableHelper.dispose(this.d);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.d.get());
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.j = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.e.compareAndSet(false, true)) {
                RxJavaPlugins.onError(th);
            } else {
                DisposableHelper.dispose(this.d);
                this.a.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            CompletableSource completableSource = (CompletableSource) obj;
            if (this.f != 0 || this.h.offer(completableSource)) {
                a();
            } else {
                onError(new MissingBackpressureException());
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.i, subscription)) {
                this.i = subscription;
                int i = this.b;
                long j = i == Integer.MAX_VALUE ? Long.MAX_VALUE : i;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.f = iRequestFusion;
                        this.h = queueSubscription;
                        this.j = true;
                        this.a.onSubscribe(this);
                        a();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.f = iRequestFusion;
                        this.h = queueSubscription;
                        this.a.onSubscribe(this);
                        subscription.request(j);
                        return;
                    }
                }
                if (this.b == Integer.MAX_VALUE) {
                    this.h = new SpscLinkedArrayQueue(Flowable.bufferSize());
                } else {
                    this.h = new SpscArrayQueue(this.b);
                }
                this.a.onSubscribe(this);
                subscription.request(j);
            }
        }

        public void a(Throwable th) {
            if (this.e.compareAndSet(false, true)) {
                this.i.cancel();
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }
}
