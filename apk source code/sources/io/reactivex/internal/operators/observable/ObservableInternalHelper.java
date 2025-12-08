package io.reactivex.internal.operators.observable;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observables.ConnectableObservable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class ObservableInternalHelper {

    public static final class a<T> implements Callable<ConnectableObservable<T>> {
        public final Observable<T> a;
        public final int b;

        public a(Observable<T> observable, int i) {
            this.a = observable;
            this.b = i;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return this.a.replay(this.b);
        }
    }

    public static final class b<T> implements Callable<ConnectableObservable<T>> {
        public final Observable<T> a;
        public final int b;
        public final long c;
        public final TimeUnit d;
        public final Scheduler e;

        public b(Observable<T> observable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = observable;
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

    public static final class c<T, U> implements Function<T, ObservableSource<U>> {
        public final Function<? super T, ? extends Iterable<? extends U>> a;

        public c(Function<? super T, ? extends Iterable<? extends U>> function) {
            this.a = function;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return new ObservableFromIterable((Iterable) ObjectHelper.requireNonNull(this.a.apply(obj), "The mapper returned a null Iterable"));
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

    public static final class e<T, R, U> implements Function<T, ObservableSource<R>> {
        public final BiFunction<? super T, ? super U, ? extends R> a;
        public final Function<? super T, ? extends ObservableSource<? extends U>> b;

        public e(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super T, ? extends ObservableSource<? extends U>> function) {
            this.a = biFunction;
            this.b = function;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return new ObservableMap((ObservableSource) ObjectHelper.requireNonNull(this.b.apply(obj), "The mapper returned a null ObservableSource"), new d(this.a, obj));
        }
    }

    public static final class f<T, U> implements Function<T, ObservableSource<T>> {
        public final Function<? super T, ? extends ObservableSource<U>> a;

        public f(Function<? super T, ? extends ObservableSource<U>> function) {
            this.a = function;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return new ObservableTake((ObservableSource) ObjectHelper.requireNonNull(this.a.apply(obj), "The itemDelay returned a null ObservableSource"), 1L).map(Functions.justFunction(obj)).defaultIfEmpty(obj);
        }
    }

    public static final class g<T> implements Action {
        public final Observer<T> a;

        public g(Observer<T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.functions.Action
        public void run() throws Exception {
            this.a.onComplete();
        }
    }

    public static final class h<T> implements Consumer<Throwable> {
        public final Observer<T> a;

        public h(Observer<T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Throwable th) throws Exception {
            this.a.onError(th);
        }
    }

    public static final class i<T> implements Consumer<T> {
        public final Observer<T> a;

        public i(Observer<T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(T t) throws Exception {
            this.a.onNext(t);
        }
    }

    public static final class j<T> implements Callable<ConnectableObservable<T>> {
        public final Observable<T> a;

        public j(Observable<T> observable) {
            this.a = observable;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return this.a.replay();
        }
    }

    public static final class k<T, R> implements Function<Observable<T>, ObservableSource<R>> {
        public final Function<? super Observable<T>, ? extends ObservableSource<R>> a;
        public final Scheduler b;

        public k(Function<? super Observable<T>, ? extends ObservableSource<R>> function, Scheduler scheduler) {
            this.a = function;
            this.b = scheduler;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return Observable.wrap((ObservableSource) ObjectHelper.requireNonNull(this.a.apply((Observable) obj), "The selector returned a null ObservableSource")).observeOn(this.b);
        }
    }

    public static final class l<T, S> implements BiFunction<S, Emitter<T>, S> {
        public final BiConsumer<S, Emitter<T>> a;

        public l(BiConsumer<S, Emitter<T>> biConsumer) {
            this.a = biConsumer;
        }

        @Override // io.reactivex.functions.BiFunction
        public Object apply(Object obj, Object obj2) throws Exception {
            this.a.accept(obj, (Emitter) obj2);
            return obj;
        }
    }

    public static final class m<T, S> implements BiFunction<S, Emitter<T>, S> {
        public final Consumer<Emitter<T>> a;

        public m(Consumer<Emitter<T>> consumer) {
            this.a = consumer;
        }

        @Override // io.reactivex.functions.BiFunction
        public Object apply(Object obj, Object obj2) throws Exception {
            this.a.accept((Emitter) obj2);
            return obj;
        }
    }

    public static final class n<T> implements Callable<ConnectableObservable<T>> {
        public final Observable<T> a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;

        public n(Observable<T> observable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = observable;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return this.a.replay(this.b, this.c, this.d);
        }
    }

    public static final class o<T, R> implements Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> {
        public final Function<? super Object[], ? extends R> a;

        public o(Function<? super Object[], ? extends R> function) {
            this.a = function;
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) throws Exception {
            return Observable.zipIterable((List) obj, this.a, false, Observable.bufferSize());
        }
    }

    public ObservableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, U> Function<T, ObservableSource<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return new c(function);
    }

    public static <T, U, R> Function<T, ObservableSource<R>> flatMapWithCombiner(Function<? super T, ? extends ObservableSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return new e(biFunction, function);
    }

    public static <T, U> Function<T, ObservableSource<T>> itemDelay(Function<? super T, ? extends ObservableSource<U>> function) {
        return new f(function);
    }

    public static <T> Action observerOnComplete(Observer<T> observer) {
        return new g(observer);
    }

    public static <T> Consumer<Throwable> observerOnError(Observer<T> observer) {
        return new h(observer);
    }

    public static <T> Consumer<T> observerOnNext(Observer<T> observer) {
        return new i(observer);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable) {
        return new j(observable);
    }

    public static <T, R> Function<Observable<T>, ObservableSource<R>> replayFunction(Function<? super Observable<T>, ? extends ObservableSource<R>> function, Scheduler scheduler) {
        return new k(function, scheduler);
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> biConsumer) {
        return new l(biConsumer);
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new m(consumer);
    }

    public static <T, R> Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> zipIterable(Function<? super Object[], ? extends R> function) {
        return new o(function);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable, int i2) {
        return new a(observable, i2);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable, int i2, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new b(observable, i2, j2, timeUnit, scheduler);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> observable, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return new n(observable, j2, timeUnit, scheduler);
    }
}
