package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableConcatArray<T> extends Flowable<T> {
    public final Publisher<? extends T>[] b;
    public final boolean c;

    public static final class a<T> extends SubscriptionArbiter implements FlowableSubscriber<T> {
        public static final long serialVersionUID = -8158322871608889516L;
        public final Subscriber<? super T> h;
        public final Publisher<? extends T>[] i;
        public final boolean j;
        public final AtomicInteger k;
        public int l;
        public List<Throwable> m;
        public long n;

        public a(Publisher<? extends T>[] publisherArr, boolean z, Subscriber<? super T> subscriber) {
            super(false);
            this.h = subscriber;
            this.i = publisherArr;
            this.j = z;
            this.k = new AtomicInteger();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.k.getAndIncrement() == 0) {
                Publisher<? extends T>[] publisherArr = this.i;
                int length = publisherArr.length;
                int i = this.l;
                while (i != length) {
                    Publisher<? extends T> publisher = publisherArr[i];
                    if (publisher == null) {
                        NullPointerException nullPointerException = new NullPointerException("A Publisher entry is null");
                        if (!this.j) {
                            this.h.onError(nullPointerException);
                            return;
                        }
                        List arrayList = this.m;
                        if (arrayList == null) {
                            arrayList = new ArrayList((length - i) + 1);
                            this.m = arrayList;
                        }
                        arrayList.add(nullPointerException);
                        i++;
                    } else {
                        long j = this.n;
                        if (j != 0) {
                            this.n = 0L;
                            produced(j);
                        }
                        publisher.subscribe(this);
                        i++;
                        this.l = i;
                        if (this.k.decrementAndGet() == 0) {
                            return;
                        }
                    }
                }
                List<Throwable> list = this.m;
                if (list == null) {
                    this.h.onComplete();
                } else if (list.size() == 1) {
                    this.h.onError(list.get(0));
                } else {
                    this.h.onError(new CompositeException(list));
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.j) {
                this.h.onError(th);
                return;
            }
            List arrayList = this.m;
            if (arrayList == null) {
                arrayList = new ArrayList((this.i.length - this.l) + 1);
                this.m = arrayList;
            }
            arrayList.add(th);
            onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.n++;
            this.h.onNext(t);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }
    }

    public FlowableConcatArray(Publisher<? extends T>[] publisherArr, boolean z) {
        this.b = publisherArr;
        this.c = z;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(this.b, this.c, subscriber);
        subscriber.onSubscribe(aVar);
        aVar.onComplete();
    }
}
