package io.reactivex.internal.operators.flowable;

import defpackage.zk;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

/* loaded from: classes.dex */
public final class FlowableDistinctUntilChanged<T, K> extends zk<T, T> {
    public final Function<? super T, K> b;
    public final BiPredicate<? super K, ? super K> c;

    public static final class a<T, K> extends BasicFuseableConditionalSubscriber<T, T> {
        public final Function<? super T, K> a;
        public final BiPredicate<? super K, ? super K> b;
        public K c;
        public boolean d;

        public a(ConditionalSubscriber<? super T> conditionalSubscriber, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            super(conditionalSubscriber);
            this.a = function;
            this.b = biPredicate;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            while (true) {
                T tPoll = this.qs.poll();
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
                if (this.sourceMode != 1) {
                    this.upstream.request(1L);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                return this.downstream.tryOnNext(t);
            }
            try {
                K kApply = this.a.apply(t);
                if (this.d) {
                    boolean zTest = this.b.test(this.c, kApply);
                    this.c = kApply;
                    if (zTest) {
                        return false;
                    }
                } else {
                    this.d = true;
                    this.c = kApply;
                }
                this.downstream.onNext(t);
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    public static final class b<T, K> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
        public final Function<? super T, K> a;
        public final BiPredicate<? super K, ? super K> b;
        public K c;
        public boolean d;

        public b(Subscriber<? super T> subscriber, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            super(subscriber);
            this.a = function;
            this.b = biPredicate;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            while (true) {
                T tPoll = this.qs.poll();
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
                if (this.sourceMode != 1) {
                    this.upstream.request(1L);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(t);
                return true;
            }
            try {
                K kApply = this.a.apply(t);
                if (this.d) {
                    boolean zTest = this.b.test(this.c, kApply);
                    this.c = kApply;
                    if (zTest) {
                        return false;
                    }
                } else {
                    this.d = true;
                    this.c = kApply;
                }
                this.downstream.onNext(t);
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    public FlowableDistinctUntilChanged(Flowable<T> flowable, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
        super(flowable);
        this.b = function;
        this.c = biPredicate;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new a((ConditionalSubscriber) subscriber, this.b, this.c));
        } else {
            this.source.subscribe((FlowableSubscriber) new b(subscriber, this.b, this.c));
        }
    }
}
