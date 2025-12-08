package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class ParallelDoOnNextTry<T> extends ParallelFlowable<T> {
    public final ParallelFlowable<T> a;
    public final Consumer<? super T> b;
    public final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;

    public static final class a<T> implements ConditionalSubscriber<T>, Subscription {
        public final ConditionalSubscriber<? super T> a;
        public final Consumer<? super T> b;
        public final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;
        public Subscription d;
        public boolean e;

        public a(ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super T> consumer, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.a = conditionalSubscriber;
            this.b = consumer;
            this.c = biFunction;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.d.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.e) {
                return;
            }
            this.e = true;
            this.a.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
            } else {
                this.e = true;
                this.a.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t) || this.e) {
                return;
            }
            this.d.request(1L);
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

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.e) {
                return false;
            }
            long j = 0;
            while (true) {
                try {
                    this.b.accept(t);
                    return this.a.tryOnNext(t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    try {
                        j++;
                        int iOrdinal = ((ParallelFailureHandling) ObjectHelper.requireNonNull(this.c.apply(Long.valueOf(j), th), "The errorHandler returned a null item")).ordinal();
                        if (iOrdinal == 0) {
                            this.d.cancel();
                            onComplete();
                            return false;
                        }
                        if (iOrdinal == 2) {
                            break;
                        }
                        if (iOrdinal != 3) {
                            this.d.cancel();
                            onError(th);
                            break;
                        }
                        return false;
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        this.d.cancel();
                        onError(new CompositeException(th, th2));
                        return false;
                    }
                }
            }
            return false;
        }
    }

    public static final class b<T> implements ConditionalSubscriber<T>, Subscription {
        public final Subscriber<? super T> a;
        public final Consumer<? super T> b;
        public final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;
        public Subscription d;
        public boolean e;

        public b(Subscriber<? super T> subscriber, Consumer<? super T> consumer, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.a = subscriber;
            this.b = consumer;
            this.c = biFunction;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.d.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.e) {
                return;
            }
            this.e = true;
            this.a.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
            } else {
                this.e = true;
                this.a.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.d.request(1L);
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

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.e) {
                return false;
            }
            long j = 0;
            while (true) {
                try {
                    this.b.accept(t);
                    this.a.onNext(t);
                    return true;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    try {
                        j++;
                        int iOrdinal = ((ParallelFailureHandling) ObjectHelper.requireNonNull(this.c.apply(Long.valueOf(j), th), "The errorHandler returned a null item")).ordinal();
                        if (iOrdinal == 0) {
                            this.d.cancel();
                            onComplete();
                            return false;
                        }
                        if (iOrdinal == 2) {
                            break;
                        }
                        if (iOrdinal != 3) {
                            this.d.cancel();
                            onError(th);
                            break;
                        }
                        return false;
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        this.d.cancel();
                        onError(new CompositeException(th, th2));
                        return false;
                    }
                }
            }
            return false;
        }
    }

    public ParallelDoOnNextTry(ParallelFlowable<T> parallelFlowable, Consumer<? super T> consumer, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
        this.a = parallelFlowable;
        this.b = consumer;
        this.c = biFunction;
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                Subscriber<? super T> subscriber = subscriberArr[i];
                if (subscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new a((ConditionalSubscriber) subscriber, this.b, this.c);
                } else {
                    subscriberArr2[i] = new b(subscriber, this.b, this.c);
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }
}
