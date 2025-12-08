package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class ParallelFilterTry<T> extends ParallelFlowable<T> {
    public final ParallelFlowable<T> a;
    public final Predicate<? super T> b;
    public final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;

    public static abstract class a<T> implements ConditionalSubscriber<T>, Subscription {
        public final Predicate<? super T> a;
        public final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> b;
        public Subscription c;
        public boolean d;

        public a(Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.a = predicate;
            this.b = biFunction;
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.c.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (tryOnNext(t) || this.d) {
                return;
            }
            this.c.request(1L);
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            this.c.request(j);
        }
    }

    public static final class b<T> extends a<T> {
        public final ConditionalSubscriber<? super T> e;

        public b(ConditionalSubscriber<? super T> conditionalSubscriber, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            super(predicate, biFunction);
            this.e = conditionalSubscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.d) {
                return;
            }
            this.d = true;
            this.e.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
            } else {
                this.d = true;
                this.e.onError(th);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.e.onSubscribe(this);
            }
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (!this.d) {
                long j = 0;
                while (true) {
                    try {
                        return this.a.test(t) && this.e.tryOnNext(t);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        try {
                            j++;
                            int iOrdinal = ((ParallelFailureHandling) ObjectHelper.requireNonNull(this.b.apply(Long.valueOf(j), th), "The errorHandler returned a null item")).ordinal();
                            if (iOrdinal == 0) {
                                this.c.cancel();
                                onComplete();
                                return false;
                            }
                            if (iOrdinal == 2) {
                                break;
                            }
                            if (iOrdinal != 3) {
                                this.c.cancel();
                                onError(th);
                                break;
                            }
                            return false;
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.c.cancel();
                            onError(new CompositeException(th, th2));
                        }
                    }
                }
                return false;
            }
            return false;
        }
    }

    public static final class c<T> extends a<T> {
        public final Subscriber<? super T> e;

        public c(Subscriber<? super T> subscriber, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            super(predicate, biFunction);
            this.e = subscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.d) {
                return;
            }
            this.d = true;
            this.e.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
            } else {
                this.d = true;
                this.e.onError(th);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.e.onSubscribe(this);
            }
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (!this.d) {
                long j = 0;
                while (true) {
                    try {
                        if (!this.a.test(t)) {
                            return false;
                        }
                        this.e.onNext(t);
                        return true;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        try {
                            j++;
                            int iOrdinal = ((ParallelFailureHandling) ObjectHelper.requireNonNull(this.b.apply(Long.valueOf(j), th), "The errorHandler returned a null item")).ordinal();
                            if (iOrdinal == 0) {
                                this.c.cancel();
                                onComplete();
                                return false;
                            }
                            if (iOrdinal == 2) {
                                break;
                            }
                            if (iOrdinal != 3) {
                                this.c.cancel();
                                onError(th);
                                break;
                            }
                            return false;
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.c.cancel();
                            onError(new CompositeException(th, th2));
                        }
                    }
                }
                return false;
            }
            return false;
        }
    }

    public ParallelFilterTry(ParallelFlowable<T> parallelFlowable, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
        this.a = parallelFlowable;
        this.b = predicate;
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
                    subscriberArr2[i] = new b((ConditionalSubscriber) subscriber, this.b, this.c);
                } else {
                    subscriberArr2[i] = new c(subscriber, this.b, this.c);
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }
}
