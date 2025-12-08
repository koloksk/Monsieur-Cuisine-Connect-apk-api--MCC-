package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableWindowBoundarySelector<T, B, V> extends bl<T, Observable<T>> {
    public final ObservableSource<B> a;
    public final Function<? super B, ? extends ObservableSource<V>> b;
    public final int c;

    public static final class a<T, V> extends DisposableObserver<V> {
        public final c<T, ?, V> b;
        public final UnicastSubject<T> c;
        public boolean d;

        public a(c<T, ?, V> cVar, UnicastSubject<T> unicastSubject) {
            this.b = cVar;
            this.c = unicastSubject;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.d) {
                return;
            }
            this.d = true;
            c<T, ?, V> cVar = this.b;
            cVar.e.delete(this);
            cVar.queue.offer(new d(this.c, null));
            if (cVar.enter()) {
                cVar.a();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            c<T, ?, V> cVar = this.b;
            cVar.f.dispose();
            cVar.e.dispose();
            cVar.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(V v) {
            dispose();
            onComplete();
        }
    }

    public static final class b<T, B> extends DisposableObserver<B> {
        public final c<T, B, ?> b;

        public b(c<T, B, ?> cVar) {
            this.b = cVar;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.b.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            c<T, B, ?> cVar = this.b;
            cVar.f.dispose();
            cVar.e.dispose();
            cVar.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(B b) {
            c<T, B, ?> cVar = this.b;
            cVar.queue.offer(new d(null, b));
            if (cVar.enter()) {
                cVar.a();
            }
        }
    }

    public static final class c<T, B, V> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        public final ObservableSource<B> b;
        public final Function<? super B, ? extends ObservableSource<V>> c;
        public final int d;
        public final CompositeDisposable e;
        public Disposable f;
        public final AtomicReference<Disposable> g;
        public final List<UnicastSubject<T>> h;
        public final AtomicLong i;
        public final AtomicBoolean j;

        public c(Observer<? super Observable<T>> observer, ObservableSource<B> observableSource, Function<? super B, ? extends ObservableSource<V>> function, int i) {
            super(observer, new MpscLinkedQueue());
            this.g = new AtomicReference<>();
            this.i = new AtomicLong();
            this.j = new AtomicBoolean();
            this.b = observableSource;
            this.c = function;
            this.d = i;
            this.e = new CompositeDisposable();
            this.h = new ArrayList();
            this.i.lazySet(1L);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer<? super V> observer = this.downstream;
            List<UnicastSubject<T>> list = this.h;
            int iLeave = 1;
            while (true) {
                boolean z = this.done;
                Object objPoll = mpscLinkedQueue.poll();
                boolean z2 = objPoll == null;
                if (z && z2) {
                    this.e.dispose();
                    DisposableHelper.dispose(this.g);
                    Throwable th = this.error;
                    if (th != null) {
                        Iterator<UnicastSubject<T>> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().onError(th);
                        }
                    } else {
                        Iterator<UnicastSubject<T>> it2 = list.iterator();
                        while (it2.hasNext()) {
                            it2.next().onComplete();
                        }
                    }
                    list.clear();
                    return;
                }
                if (z2) {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                } else if (objPoll instanceof d) {
                    d dVar = (d) objPoll;
                    UnicastSubject<T> unicastSubject = dVar.a;
                    if (unicastSubject != null) {
                        if (list.remove(unicastSubject)) {
                            dVar.a.onComplete();
                            if (this.i.decrementAndGet() == 0) {
                                this.e.dispose();
                                DisposableHelper.dispose(this.g);
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.j.get()) {
                        UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.d);
                        list.add(unicastSubjectCreate);
                        observer.onNext(unicastSubjectCreate);
                        try {
                            ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.c.apply(dVar.b), "The ObservableSource supplied is null");
                            a aVar = new a(this, unicastSubjectCreate);
                            if (this.e.add(aVar)) {
                                this.i.getAndIncrement();
                                observableSource.subscribe(aVar);
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.j.set(true);
                            observer.onError(th2);
                        }
                    }
                } else {
                    Iterator<UnicastSubject<T>> it3 = list.iterator();
                    while (it3.hasNext()) {
                        it3.next().onNext(NotificationLite.getValue(objPoll));
                    }
                }
            }
        }

        @Override // io.reactivex.internal.observers.QueueDrainObserver, io.reactivex.internal.util.ObservableQueueDrain
        public void accept(Observer<? super Observable<T>> observer, Object obj) {
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.j.compareAndSet(false, true)) {
                DisposableHelper.dispose(this.g);
                if (this.i.decrementAndGet() == 0) {
                    this.f.dispose();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.j.get();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (enter()) {
                a();
            }
            if (this.i.decrementAndGet() == 0) {
                this.e.dispose();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            if (enter()) {
                a();
            }
            if (this.i.decrementAndGet() == 0) {
                this.e.dispose();
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (fastEnter()) {
                Iterator<UnicastSubject<T>> it = this.h.iterator();
                while (it.hasNext()) {
                    it.next().onNext(t);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
            }
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.downstream.onSubscribe(this);
                if (this.j.get()) {
                    return;
                }
                b bVar = new b(this);
                if (this.g.compareAndSet(null, bVar)) {
                    this.b.subscribe(bVar);
                }
            }
        }
    }

    public static final class d<T, B> {
        public final UnicastSubject<T> a;
        public final B b;

        public d(UnicastSubject<T> unicastSubject, B b) {
            this.a = unicastSubject;
            this.b = b;
        }
    }

    public ObservableWindowBoundarySelector(ObservableSource<T> observableSource, ObservableSource<B> observableSource2, Function<? super B, ? extends ObservableSource<V>> function, int i) {
        super(observableSource);
        this.a = observableSource2;
        this.b = function;
        this.c = i;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Observable<T>> observer) {
        this.source.subscribe(new c(new SerializedObserver(observer), this.a, this.b, this.c));
    }
}
