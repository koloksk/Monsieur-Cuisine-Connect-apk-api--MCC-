package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.BasicFuseableObserver;

/* loaded from: classes.dex */
public final class ObservableDistinctUntilChanged<T, K> extends bl<T, T> {
    public final Function<? super T, K> a;
    public final BiPredicate<? super K, ? super K> b;

    public static final class a<T, K> extends BasicFuseableObserver<T, T> {
        public final Function<? super T, K> a;
        public final BiPredicate<? super K, ? super K> b;
        public K c;
        public boolean d;

        public a(Observer<? super T> observer, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            super(observer);
            this.a = function;
            this.b = biPredicate;
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(t);
                return;
            }
            try {
                K kApply = this.a.apply(t);
                if (this.d) {
                    boolean zTest = this.b.test(this.c, kApply);
                    this.c = kApply;
                    if (zTest) {
                        return;
                    }
                } else {
                    this.d = true;
                    this.c = kApply;
                }
                this.downstream.onNext(t);
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            while (true) {
                T tPoll = this.qd.poll();
                if (tPoll == null) {
                    return null;
                }
                K kApply = this.a.apply(tPoll);
                if (!this.d) {
                    this.d = true;
                    this.c = kApply;
                    return tPoll;
                }
                if (!this.b.test(this.c, kApply)) {
                    this.c = kApply;
                    return tPoll;
                }
                this.c = kApply;
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    public ObservableDistinctUntilChanged(ObservableSource<T> observableSource, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
        super(observableSource);
        this.a = function;
        this.b = biPredicate;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a, this.b));
    }
}
