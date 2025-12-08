package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableWithLatestFromMany<T, R> extends zk<T, R> {

    @Nullable
    public final Publisher<?>[] b;

    @Nullable
    public final Iterable<? extends Publisher<?>> c;
    public final Function<? super Object[], R> d;

    public final class a implements Function<T, R> {
        public a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.functions.Function
        public R apply(T t) throws Exception {
            return (R) ObjectHelper.requireNonNull(FlowableWithLatestFromMany.this.d.apply(new Object[]{t}), "The combiner returned a null value");
        }
    }

    public static final class b<T, R> extends AtomicInteger implements ConditionalSubscriber<T>, Subscription {
        public static final long serialVersionUID = 1577321883966341961L;
        public final Subscriber<? super R> a;
        public final Function<? super Object[], R> b;
        public final c[] c;
        public final AtomicReferenceArray<Object> d;
        public final AtomicReference<Subscription> e;
        public final AtomicLong f;
        public final AtomicThrowable g;
        public volatile boolean h;

        public b(Subscriber<? super R> subscriber, Function<? super Object[], R> function, int i) {
            this.a = subscriber;
            this.b = function;
            c[] cVarArr = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                cVarArr[i2] = new c(this, i2);
            }
            this.c = cVarArr;
            this.d = new AtomicReferenceArray<>(i);
            this.e = new AtomicReference<>();
            this.f = new AtomicLong();
            this.g = new AtomicThrowable();
        }

        public void a(int i) {
            c[] cVarArr = this.c;
            for (int i2 = 0; i2 < cVarArr.length; i2++) {
                if (i2 != i) {
                    c cVar = cVarArr[i2];
                    if (cVar == null) {
                        throw null;
                    }
                    SubscriptionHelper.cancel(cVar);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.e);
            for (c cVar : this.c) {
                if (cVar == null) {
                    throw null;
                }
                SubscriptionHelper.cancel(cVar);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.h) {
                return;
            }
            this.h = true;
            a(-1);
            HalfSerializer.onComplete(this.a, this, this.g);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.h = true;
            a(-1);
            HalfSerializer.onError(this.a, th, this, this.g);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t) || this.h) {
                return;
            }
            this.e.get().request(1L);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.e, this.f, subscription);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.e, this.f, j);
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.h) {
                return false;
            }
            AtomicReferenceArray<Object> atomicReferenceArray = this.d;
            int length = atomicReferenceArray.length();
            Object[] objArr = new Object[length + 1];
            objArr[0] = t;
            int i = 0;
            while (i < length) {
                Object obj = atomicReferenceArray.get(i);
                if (obj == null) {
                    return false;
                }
                i++;
                objArr[i] = obj;
            }
            try {
                HalfSerializer.onNext(this.a, ObjectHelper.requireNonNull(this.b.apply(objArr), "The combiner returned a null value"), this, this.g);
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
                return false;
            }
        }
    }

    public static final class c extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        public static final long serialVersionUID = 3256684027868224024L;
        public final b<?, ?> a;
        public final int b;
        public boolean c;

        public c(b<?, ?> bVar, int i) {
            this.a = bVar;
            this.b = i;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            b<?, ?> bVar = this.a;
            int i = this.b;
            boolean z = this.c;
            if (bVar == null) {
                throw null;
            }
            if (z) {
                return;
            }
            bVar.h = true;
            SubscriptionHelper.cancel(bVar.e);
            bVar.a(i);
            HalfSerializer.onComplete(bVar.a, bVar, bVar.g);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            b<?, ?> bVar = this.a;
            int i = this.b;
            bVar.h = true;
            SubscriptionHelper.cancel(bVar.e);
            bVar.a(i);
            HalfSerializer.onError(bVar.a, th, bVar, bVar.g);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (!this.c) {
                this.c = true;
            }
            b<?, ?> bVar = this.a;
            bVar.d.set(this.b, obj);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public FlowableWithLatestFromMany(@NonNull Flowable<T> flowable, @NonNull Publisher<?>[] publisherArr, Function<? super Object[], R> function) {
        super(flowable);
        this.b = publisherArr;
        this.c = null;
        this.d = function;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        int length;
        Publisher<?>[] publisherArr = this.b;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                length = 0;
                for (Publisher<?> publisher : this.c) {
                    if (length == publisherArr.length) {
                        publisherArr = (Publisher[]) Arrays.copyOf(publisherArr, (length >> 1) + length);
                    }
                    int i = length + 1;
                    publisherArr[length] = publisher;
                    length = i;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(th, subscriber);
                return;
            }
        } else {
            length = publisherArr.length;
        }
        if (length == 0) {
            new FlowableMap(this.source, new a()).subscribeActual(subscriber);
            return;
        }
        b bVar = new b(subscriber, this.d, length);
        subscriber.onSubscribe(bVar);
        c[] cVarArr = bVar.c;
        AtomicReference<Subscription> atomicReference = bVar.e;
        for (int i2 = 0; i2 < length && atomicReference.get() != SubscriptionHelper.CANCELLED; i2++) {
            publisherArr[i2].subscribe(cVarArr[i2]);
        }
        this.source.subscribe((FlowableSubscriber) bVar);
    }

    public FlowableWithLatestFromMany(@NonNull Flowable<T> flowable, @NonNull Iterable<? extends Publisher<?>> iterable, @NonNull Function<? super Object[], R> function) {
        super(flowable);
        this.b = null;
        this.c = iterable;
        this.d = function;
    }
}
