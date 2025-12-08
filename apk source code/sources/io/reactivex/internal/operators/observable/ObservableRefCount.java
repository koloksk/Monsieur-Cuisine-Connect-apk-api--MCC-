package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableRefCount<T> extends Observable<T> {
    public final ConnectableObservable<T> a;
    public final int b;
    public final long c;
    public final TimeUnit d;
    public final Scheduler e;
    public a f;

    public static final class a extends AtomicReference<Disposable> implements Runnable, Consumer<Disposable> {
        public static final long serialVersionUID = -4552101107598366241L;
        public final ObservableRefCount<?> a;
        public Disposable b;
        public long c;
        public boolean d;
        public boolean e;

        public a(ObservableRefCount<?> observableRefCount) {
            this.a = observableRefCount;
        }

        @Override // io.reactivex.functions.Consumer
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(Disposable disposable) throws Exception {
            DisposableHelper.replace(this, disposable);
            synchronized (this.a) {
                if (this.e) {
                    ((ResettableConnectable) this.a.a).resetIf(disposable);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.d(this);
        }
    }

    public static final class b<T> extends AtomicBoolean implements Observer<T>, Disposable {
        public static final long serialVersionUID = -7419642935409022375L;
        public final Observer<? super T> a;
        public final ObservableRefCount<T> b;
        public final a c;
        public Disposable d;

        public b(Observer<? super T> observer, ObservableRefCount<T> observableRefCount, a aVar) {
            this.a = observer;
            this.b = observableRefCount;
            this.c = aVar;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.d.dispose();
            if (compareAndSet(false, true)) {
                this.b.a(this.c);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (compareAndSet(false, true)) {
                this.b.c(this.c);
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!compareAndSet(false, true)) {
                RxJavaPlugins.onError(th);
            } else {
                this.b.c(this.c);
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableRefCount(ConnectableObservable<T> connectableObservable) {
        this(connectableObservable, 1, 0L, TimeUnit.NANOSECONDS, null);
    }

    public void a(a aVar) {
        synchronized (this) {
            if (this.f != null && this.f == aVar) {
                long j = aVar.c - 1;
                aVar.c = j;
                if (j == 0 && aVar.d) {
                    if (this.c == 0) {
                        d(aVar);
                        return;
                    }
                    SequentialDisposable sequentialDisposable = new SequentialDisposable();
                    aVar.b = sequentialDisposable;
                    sequentialDisposable.replace(this.e.scheduleDirect(aVar, this.c, this.d));
                }
            }
        }
    }

    public void b(a aVar) {
        ConnectableObservable<T> connectableObservable = this.a;
        if (connectableObservable instanceof Disposable) {
            ((Disposable) connectableObservable).dispose();
        } else if (connectableObservable instanceof ResettableConnectable) {
            ((ResettableConnectable) connectableObservable).resetIf(aVar.get());
        }
    }

    public void c(a aVar) {
        synchronized (this) {
            if (this.a instanceof ObservablePublishClassic) {
                if (this.f != null && this.f == aVar) {
                    this.f = null;
                    Disposable disposable = aVar.b;
                    if (disposable != null) {
                        disposable.dispose();
                        aVar.b = null;
                    }
                }
                long j = aVar.c - 1;
                aVar.c = j;
                if (j == 0) {
                    b(aVar);
                }
            } else if (this.f != null && this.f == aVar) {
                Disposable disposable2 = aVar.b;
                if (disposable2 != null) {
                    disposable2.dispose();
                    aVar.b = null;
                }
                long j2 = aVar.c - 1;
                aVar.c = j2;
                if (j2 == 0) {
                    this.f = null;
                    b(aVar);
                }
            }
        }
    }

    public void d(a aVar) {
        synchronized (this) {
            if (aVar.c == 0 && aVar == this.f) {
                this.f = null;
                Disposable disposable = aVar.get();
                DisposableHelper.dispose(aVar);
                if (this.a instanceof Disposable) {
                    ((Disposable) this.a).dispose();
                } else if (this.a instanceof ResettableConnectable) {
                    if (disposable == null) {
                        aVar.e = true;
                    } else {
                        ((ResettableConnectable) this.a).resetIf(disposable);
                    }
                }
            }
        }
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar;
        boolean z;
        synchronized (this) {
            aVar = this.f;
            if (aVar == null) {
                aVar = new a(this);
                this.f = aVar;
            }
            long j = aVar.c;
            if (j == 0 && aVar.b != null) {
                aVar.b.dispose();
            }
            long j2 = j + 1;
            aVar.c = j2;
            z = true;
            if (aVar.d || j2 != this.b) {
                z = false;
            } else {
                aVar.d = true;
            }
        }
        this.a.subscribe(new b(observer, this, aVar));
        if (z) {
            this.a.connect(aVar);
        }
    }

    public ObservableRefCount(ConnectableObservable<T> connectableObservable, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.a = connectableObservable;
        this.b = i;
        this.c = j;
        this.d = timeUnit;
        this.e = scheduler;
    }
}
