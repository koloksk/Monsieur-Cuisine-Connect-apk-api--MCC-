package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableReplay<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T>, ResettableConnectable {
    public static final b e = new o();
    public final ObservableSource<T> a;
    public final AtomicReference<j<T>> b;
    public final b<T> c;
    public final ObservableSource<T> d;

    public interface b<T> {
        h<T> call();
    }

    public static final class c<R> implements Consumer<Disposable> {
        public final ObserverResourceWrapper<R> a;

        public c(ObserverResourceWrapper<R> observerResourceWrapper) {
            this.a = observerResourceWrapper;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Disposable disposable) throws Exception {
            this.a.setResource(disposable);
        }
    }

    public static final class d<T> extends AtomicInteger implements Disposable {
        public static final long serialVersionUID = 2728361546769921047L;
        public final j<T> a;
        public final Observer<? super T> b;
        public Object c;
        public volatile boolean d;

        public d(j<T> jVar, Observer<? super T> observer) {
            this.a = jVar;
            this.b = observer;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.d) {
                return;
            }
            this.d = true;
            this.a.a(this);
            this.c = null;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d;
        }
    }

    public static final class e<R, U> extends Observable<R> {
        public final Callable<? extends ConnectableObservable<U>> a;
        public final Function<? super Observable<U>, ? extends ObservableSource<R>> b;

        public e(Callable<? extends ConnectableObservable<U>> callable, Function<? super Observable<U>, ? extends ObservableSource<R>> function) {
            this.a = callable;
            this.b = function;
        }

        @Override // io.reactivex.Observable
        public void subscribeActual(Observer<? super R> observer) {
            try {
                ConnectableObservable connectableObservable = (ConnectableObservable) ObjectHelper.requireNonNull(this.a.call(), "The connectableFactory returned a null ConnectableObservable");
                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(connectableObservable), "The selector returned a null ObservableSource");
                ObserverResourceWrapper observerResourceWrapper = new ObserverResourceWrapper(observer);
                observableSource.subscribe(observerResourceWrapper);
                connectableObservable.connect(new c(observerResourceWrapper));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        }
    }

    public static final class f extends AtomicReference<f> {
        public static final long serialVersionUID = 245354315435971818L;
        public final Object a;

        public f(Object obj) {
            this.a = obj;
        }
    }

    public static final class g<T> extends ConnectableObservable<T> {
        public final ConnectableObservable<T> a;
        public final Observable<T> b;

        public g(ConnectableObservable<T> connectableObservable, Observable<T> observable) {
            this.a = connectableObservable;
            this.b = observable;
        }

        @Override // io.reactivex.observables.ConnectableObservable
        public void connect(Consumer<? super Disposable> consumer) {
            this.a.connect(consumer);
        }

        @Override // io.reactivex.Observable
        public void subscribeActual(Observer<? super T> observer) {
            this.b.subscribe(observer);
        }
    }

    public interface h<T> {
        void a(d<T> dVar);

        void a(T t);

        void a(Throwable th);

        void complete();
    }

    public static final class i<T> implements b<T> {
        public final int a;

        public i(int i) {
            this.a = i;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.b
        public h<T> call() {
            return new n(this.a);
        }
    }

    public static final class k<T> implements ObservableSource<T> {
        public final AtomicReference<j<T>> a;
        public final b<T> b;

        public k(AtomicReference<j<T>> atomicReference, b<T> bVar) {
            this.a = atomicReference;
            this.b = bVar;
        }

        @Override // io.reactivex.ObservableSource
        public void subscribe(Observer<? super T> observer) {
            j<T> jVar;
            d[] dVarArr;
            d[] dVarArr2;
            while (true) {
                jVar = this.a.get();
                if (jVar != null) {
                    break;
                }
                j<T> jVar2 = new j<>(this.b.call());
                if (this.a.compareAndSet(null, jVar2)) {
                    jVar = jVar2;
                    break;
                }
            }
            d<T> dVar = new d<>(jVar, observer);
            observer.onSubscribe(dVar);
            do {
                dVarArr = jVar.c.get();
                if (dVarArr == j.f) {
                    break;
                }
                int length = dVarArr.length;
                dVarArr2 = new d[length + 1];
                System.arraycopy(dVarArr, 0, dVarArr2, 0, length);
                dVarArr2[length] = dVar;
            } while (!jVar.c.compareAndSet(dVarArr, dVarArr2));
            if (dVar.d) {
                jVar.a(dVar);
            } else {
                jVar.a.a((d) dVar);
            }
        }
    }

    public static final class l<T> implements b<T> {
        public final int a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;

        public l(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = i;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.b
        public h<T> call() {
            return new m(this.a, this.b, this.c, this.d);
        }
    }

    public static final class m<T> extends a<T> {
        public static final long serialVersionUID = 3457957419649567404L;
        public final Scheduler c;
        public final long d;
        public final TimeUnit e;
        public final int f;

        public m(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.c = scheduler;
            this.f = i;
            this.d = j;
            this.e = timeUnit;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
        public f a() {
            f fVar;
            long jNow = this.c.now(this.e) - this.d;
            f fVar2 = get();
            f fVar3 = fVar2.get();
            while (true) {
                f fVar4 = fVar3;
                fVar = fVar2;
                fVar2 = fVar4;
                if (fVar2 == null) {
                    break;
                }
                Timed timed = (Timed) fVar2.a;
                if (NotificationLite.isComplete(timed.value()) || NotificationLite.isError(timed.value()) || timed.time() > jNow) {
                    break;
                }
                fVar3 = fVar2.get();
            }
            return fVar;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
        public Object b(Object obj) {
            return new Timed(obj, this.c.now(this.e), this.e);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
        public Object c(Object obj) {
            return ((Timed) obj).value();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
        public void b() {
            f fVar;
            long jNow = this.c.now(this.e) - this.d;
            f fVar2 = get();
            f fVar3 = fVar2.get();
            int i = 0;
            while (true) {
                f fVar4 = fVar3;
                fVar = fVar2;
                fVar2 = fVar4;
                if (fVar2 == null) {
                    break;
                }
                int i2 = this.b;
                if (i2 > this.f && i2 > 1) {
                    i++;
                    this.b = i2 - 1;
                    fVar3 = fVar2.get();
                } else {
                    if (((Timed) fVar2.a).time() > jNow) {
                        break;
                    }
                    i++;
                    this.b--;
                    fVar3 = fVar2.get();
                }
            }
            if (i != 0) {
                set(fVar);
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
        public void c() {
            f fVar;
            long jNow = this.c.now(this.e) - this.d;
            f fVar2 = get();
            f fVar3 = fVar2.get();
            int i = 0;
            while (true) {
                f fVar4 = fVar3;
                fVar = fVar2;
                fVar2 = fVar4;
                if (fVar2 == null || this.b <= 1 || ((Timed) fVar2.a).time() > jNow) {
                    break;
                }
                i++;
                this.b--;
                fVar3 = fVar2.get();
            }
            if (i != 0) {
                set(fVar);
            }
        }
    }

    public static final class n<T> extends a<T> {
        public static final long serialVersionUID = -5898283885385201806L;
        public final int c;

        public n(int i) {
            this.c = i;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.a
        public void b() {
            if (this.b > this.c) {
                this.b--;
                set(get().get());
            }
        }
    }

    public static final class o implements b<Object> {
        @Override // io.reactivex.internal.operators.observable.ObservableReplay.b
        public h<Object> call() {
            return new p(16);
        }
    }

    public ObservableReplay(ObservableSource<T> observableSource, ObservableSource<T> observableSource2, AtomicReference<j<T>> atomicReference, b<T> bVar) {
        this.d = observableSource;
        this.a = observableSource2;
        this.b = atomicReference;
        this.c = bVar;
    }

    public static <T> ConnectableObservable<T> a(ObservableSource<T> observableSource, b<T> bVar) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableObservable) new ObservableReplay(new k(atomicReference, bVar), observableSource, atomicReference, bVar));
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, int i2) {
        return i2 == Integer.MAX_VALUE ? createFrom(observableSource) : a(observableSource, new i(i2));
    }

    public static <T> ConnectableObservable<T> createFrom(ObservableSource<? extends T> observableSource) {
        return a(observableSource, e);
    }

    public static <U, R> Observable<R> multicastSelector(Callable<? extends ConnectableObservable<U>> callable, Function<? super Observable<U>, ? extends ObservableSource<R>> function) {
        return RxJavaPlugins.onAssembly(new e(callable, function));
    }

    public static <T> ConnectableObservable<T> observeOn(ConnectableObservable<T> connectableObservable, Scheduler scheduler) {
        return RxJavaPlugins.onAssembly((ConnectableObservable) new g(connectableObservable, connectableObservable.observeOn(scheduler)));
    }

    @Override // io.reactivex.observables.ConnectableObservable
    public void connect(Consumer<? super Disposable> consumer) {
        j<T> jVar;
        while (true) {
            jVar = this.b.get();
            if (jVar != null && !jVar.isDisposed()) {
                break;
            }
            j<T> jVar2 = new j<>(this.c.call());
            if (this.b.compareAndSet(jVar, jVar2)) {
                jVar = jVar2;
                break;
            }
        }
        boolean z = !jVar.d.get() && jVar.d.compareAndSet(false, true);
        try {
            consumer.accept(jVar);
            if (z) {
                this.a.subscribe(jVar);
            }
        } catch (Throwable th) {
            if (z) {
                jVar.d.compareAndSet(true, false);
            }
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.internal.disposables.ResettableConnectable
    public void resetIf(Disposable disposable) {
        this.b.compareAndSet((j) disposable, null);
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamObservableSource
    public ObservableSource<T> source() {
        return this.a;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.d.subscribe(observer);
    }

    public static final class p<T> extends ArrayList<Object> implements h<T> {
        public static final long serialVersionUID = 7063189396499112664L;
        public volatile int a;

        public p(int i) {
            super(i);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public void a(T t) {
            add(NotificationLite.next(t));
            this.a++;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public void complete() {
            add(NotificationLite.complete());
            this.a++;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public void a(Throwable th) {
            add(NotificationLite.error(th));
            this.a++;
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public void a(d<T> dVar) {
            if (dVar.getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = dVar.b;
            int iAddAndGet = 1;
            while (!dVar.d) {
                int i = this.a;
                Integer num = (Integer) dVar.c;
                int iIntValue = num != null ? num.intValue() : 0;
                while (iIntValue < i) {
                    if (NotificationLite.accept(get(iIntValue), observer) || dVar.d) {
                        return;
                    } else {
                        iIntValue++;
                    }
                }
                dVar.c = Integer.valueOf(iIntValue);
                iAddAndGet = dVar.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return create(observableSource, j2, timeUnit, scheduler, Integer.MAX_VALUE);
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2) {
        return a(observableSource, new l(i2, j2, timeUnit, scheduler));
    }

    public static abstract class a<T> extends AtomicReference<f> implements h<T> {
        public static final long serialVersionUID = 2346567790059478686L;
        public f a;
        public int b;

        public a() {
            f fVar = new f(null);
            this.a = fVar;
            set(fVar);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public final void a(T t) {
            f fVar = new f(b(NotificationLite.next(t)));
            this.a.set(fVar);
            this.a = fVar;
            this.b++;
            b();
        }

        public Object b(Object obj) {
            return obj;
        }

        public abstract void b();

        public Object c(Object obj) {
            return obj;
        }

        public void c() {
            f fVar = get();
            if (fVar.a != null) {
                f fVar2 = new f(null);
                fVar2.lazySet(fVar.get());
                set(fVar2);
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public final void complete() {
            f fVar = new f(b(NotificationLite.complete()));
            this.a.set(fVar);
            this.a = fVar;
            this.b++;
            c();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public final void a(Throwable th) {
            f fVar = new f(b(NotificationLite.error(th)));
            this.a.set(fVar);
            this.a = fVar;
            this.b++;
            c();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableReplay.h
        public final void a(d<T> dVar) {
            if (dVar.getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            do {
                f fVarA = (f) dVar.c;
                if (fVarA == null) {
                    fVarA = a();
                    dVar.c = fVarA;
                }
                while (!dVar.d) {
                    f fVar = fVarA.get();
                    if (fVar != null) {
                        if (NotificationLite.accept(c(fVar.a), dVar.b)) {
                            dVar.c = null;
                            return;
                        }
                        fVarA = fVar;
                    } else {
                        dVar.c = fVarA;
                        iAddAndGet = dVar.addAndGet(-iAddAndGet);
                    }
                }
                dVar.c = null;
                return;
            } while (iAddAndGet != 0);
        }

        public f a() {
            return get();
        }
    }

    public static final class j<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        public static final d[] e = new d[0];
        public static final d[] f = new d[0];
        public static final long serialVersionUID = -533785617179540163L;
        public final h<T> a;
        public boolean b;
        public final AtomicReference<d[]> c = new AtomicReference<>(e);
        public final AtomicBoolean d = new AtomicBoolean();

        public j(h<T> hVar) {
            this.a = hVar;
        }

        public void a(d<T> dVar) {
            d[] dVarArr;
            d[] dVarArr2;
            do {
                dVarArr = this.c.get();
                int length = dVarArr.length;
                if (length == 0) {
                    return;
                }
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (dVarArr[i2].equals(dVar)) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    dVarArr2 = e;
                } else {
                    d[] dVarArr3 = new d[length - 1];
                    System.arraycopy(dVarArr, 0, dVarArr3, 0, i);
                    System.arraycopy(dVarArr, i + 1, dVarArr3, i, (length - i) - 1);
                    dVarArr2 = dVarArr3;
                }
            } while (!this.c.compareAndSet(dVarArr, dVarArr2));
        }

        public void b() {
            for (d<T> dVar : this.c.getAndSet(f)) {
                this.a.a((d) dVar);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.set(f);
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.get() == f;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.b) {
                return;
            }
            this.b = true;
            this.a.complete();
            b();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.a(th);
            b();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.b) {
                return;
            }
            this.a.a((h<T>) t);
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                a();
            }
        }

        public void a() {
            for (d<T> dVar : this.c.get()) {
                this.a.a((d) dVar);
            }
        }
    }
}
