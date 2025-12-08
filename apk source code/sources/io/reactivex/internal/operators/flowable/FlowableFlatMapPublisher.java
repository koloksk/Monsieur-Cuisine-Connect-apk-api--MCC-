package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes.dex */
public final class FlowableFlatMapPublisher<T, U> extends Flowable<U> {
    public final Publisher<T> b;
    public final Function<? super T, ? extends Publisher<? extends U>> c;
    public final boolean d;
    public final int e;
    public final int f;

    public FlowableFlatMapPublisher(Publisher<T> publisher, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        this.b = publisher;
        this.c = function;
        this.d = z;
        this.e = i;
        this.f = i2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super U> subscriber) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.b, subscriber, this.c)) {
            return;
        }
        this.b.subscribe(FlowableFlatMap.subscribe(subscriber, this.c, this.d, this.e, this.f));
    }
}
