package io.reactivex.internal.operators.flowable;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableInternalHelper {

    public enum RequestMax implements Consumer<Subscription> {
        INSTANCE;

        @Override // io.reactivex.functions.Consumer
        public void accept(Subscription subscription) throws Exception {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public static final class a<T> implements Callable<ConnectableFlowable<T>> {
        public final Flowable<T> a;
        public final int b;

        public a(Flowable<T> flowable, int i) {
            this.a = flowable;
            this.b = i;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return this.a.replay(this.b);
        }
    }

    public static final class b<T> implements Callable<ConnectableFlowable<T>> {
        public final Flowable<T> a;
        public final int b;
        public final long c;
        public final TimeUnit d;
        public final Scheduler e;

        public b(Flowable<T> flowable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = flowable;
            this.b = i;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return this.a.replay(this.b, this.c, this.d, this.e);
        }
    }

    public static final class c<T, U> implements Function<T, Publisher<U>> {
        public final Function<? super T, ? extends Iterable<? extends U>> a;

        public c(Function<? super T, ? extends Iterable<? extends U>> function) {
            this.a = function;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return new FlowableFromIterable((Iterable) ObjectHelper.requireNonNull(this.a.apply(obj), "The mapper returned a null Iterable"));
        }
    }

    public static final class d<U, R, T> implements Function<U, R> {
        public final BiFunction<? super T, ? super U, ? extends R> a;
        public final T b;

        public d(BiFunction<? super T, ? super U, ? extends R> biFunction, T t) {
            this.a = biFunction;
            this.b = t;
        }

        @Override // io.reactivex.functions.Function
        public R apply(U u) throws Exception {
            return this.a.apply(this.b, u);
        }
    }

    public static final class e<T, R, U> implements Function<T, Publisher<R>> {
        public final BiFunction<? super T, ? super U, ? extends R> a;
        public final Function<? super T, ? extends Publisher<? extends U>> b;

        public e(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super T, ? extends Publisher<? extends U>> function) {
            this.a = biFunction;
            this.b = function;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return new FlowableMapPublisher((Publisher) ObjectHelper.requireNonNull(this.b.apply(obj), "The mapper returned a null Publisher"), new d(this.a, obj));
        }
    }

    public static final class f<T, U> implements Function<T, Publisher<T>> {
        public final Function<? super T, ? extends Publisher<U>> a;

        public f(Function<? super T, ? extends Publisher<U>> function) {
            this.a = function;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return new FlowableTakePublisher((Publisher) ObjectHelper.requireNonNull(this.a.apply(obj), "The itemDelay returned a null Publisher"), 1L).map(Functions.justFunction(obj)).defaultIfEmpty(obj);
        }
    }

    public static final class g<T> implements Callable<ConnectableFlowable<T>> {
        public final Flowable<T> a;

        public g(Flowable<T> flowable) {
            this.a = flowable;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return this.a.replay();
        }
    }

    public static final class h<T, R> implements Function<Flowable<T>, Publisher<R>> {
        public final Function<? super Flowable<T>, ? extends Publisher<R>> a;
        public final Scheduler b;

        public h(Function<? super Flowable<T>, ? extends Publisher<R>> function, Scheduler scheduler) {
            this.a = function;
            this.b = scheduler;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return Flowable.fromPublisher((Publisher) ObjectHelper.requireNonNull(this.a.apply((Flowable) obj), "The selector returned a null Publisher")).observeOn(this.b);
        }
    }

    public static final class i<T, S> implements BiFunction<S, Emitter<T>, S> {
        public final BiConsumer<S, Emitter<T>> a;

        public i(BiConsumer<S, Emitter<T>> biConsumer) {
            this.a = biConsumer;
        }

        @Override // io.reactivex.functions.BiFunction
        public Object apply(Object obj, Object obj2) throws Exception {
            this.a.accept(obj, (Emitter) obj2);
            return obj;
        }
    }

    public static final class j<T, S> implements BiFunction<S, Emitter<T>, S> {
        public final Consumer<Emitter<T>> a;

        public j(Consumer<Emitter<T>> consumer) {
            this.a = consumer;
        }

        @Override // io.reactivex.functions.BiFunction
        public Object apply(Object obj, Object obj2) throws Exception {
            this.a.accept((Emitter) obj2);
            return obj;
        }
    }

    public static final class k<T> implements Action {
        public final Subscriber<T> a;

        public k(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.functions.Action
        public void run() throws Exception {
            this.a.onComplete();
        }
    }

    public static final class l<T> implements Consumer<Throwable> {
        public final Subscriber<T> a;

        public l(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Throwable th) throws Exception {
            this.a.onError(th);
        }
    }

    public static final class m<T> implements Consumer<T> {
        public final Subscriber<T> a;

        public m(Subscriber<T> subscriber) {
            this.a = subscriber;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(T t) throws Exception {
            this.a.onNext(t);
        }
    }

    public static final class n<T> implements Callable<ConnectableFlowable<T>> {
        public final Flowable<T> a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;

        public n(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = flowable;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return this.a.replay(this.b, this.c, this.d);
        }
    }

    public static final class o<T, R> implements Function<List<Publisher<? extends T>>, Publisher<? extends R>> {
        public final Function<? super Object[], ? extends R> a;

        public o(Function<? super Object[], ? extends R> function) {
            this.a = function;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return Flowable.zipIterable((List) obj, this.a, false, Flowable.bufferSize());
        }
    }

    public FlowableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, U> Function<T, Publisher<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return new c(function);
    }

    public static <T, U, R> Function<T, Publisher<R>> flatMapWithCombiner(Function<? super T, ? extends Publisher<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return new e(biFunction, function);
    }

    public static <T, U> Function<T, Publisher<T>> itemDelay(Function<? super T, ? extends Publisher<U>> function) {
        return new f(function);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable) {
        return new g(flowable);
    }

    public static <T, R> Function<Flowable<T>, Publisher<R>> replayFunction(Function<? super Flowable<T>, ? extends Publisher<R>> function, Scheduler scheduler) {
        return new h(function, scheduler);
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> biConsumer) {
        return new i(biConsumer);
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new j(consumer);
    }

    public static <T> Action subscriberOnComplete(Subscriber<T> subscriber) {
        return new k(subscriber);
    }

    public static <T> Consumer<Throwable> subscriberOnError(Subscriber<T> subscriber) {
        return new l(subscriber);
    }

    public static <T> Consumer<T> subscriberOnNext(Subscriber<T> subscriber) {
        return new m(subscriber);
    }

    public static <T, R> Function<List<Publisher<? extends T>>, Publisher<? extends R>> zipIterable(Function<? super Object[], ? extends R> function) {
        return new o(function);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable, int i2) {
        return new a(flowable, i2);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable, int i2, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new b(flowable, i2, j2, timeUnit, scheduler);
    }

    public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new n(flowable, j2, timeUnit, scheduler);
    }
}
