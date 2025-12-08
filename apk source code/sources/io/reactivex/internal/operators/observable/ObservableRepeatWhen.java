package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ObservableRepeatWhen<T> extends bl<T, T> {
    public final Function<? super Observable<Object>, ? extends ObservableSource<?>> a;

    public static final class a<T> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = 802743776666017014L;
        public final Observer<? super T> a;
        public final Subject<Object> d;
        public final ObservableSource<T> g;
        public volatile boolean h;
        public final AtomicInteger b = new AtomicInteger();
        public final AtomicThrowable c = new AtomicThrowable();
        public final a<T>.C0065a e = new C0065a();
        public final AtomicReference<Disposable> f = new AtomicReference<>();

        /* renamed from: io.reactivex.internal.operators.observable.ObservableRepeatWhen$a$a, reason: collision with other inner class name */
        public final class C0065a extends AtomicReference<Disposable> implements Observer<Object> {
            public static final long serialVersionUID = 3254781284376480842L;

            public C0065a() {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                a aVar = a.this;
                DisposableHelper.dispose(aVar.f);
                HalfSerializer.onComplete(aVar.a, aVar, aVar.c);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                a aVar = a.this;
                DisposableHelper.dispose(aVar.f);
                HalfSerializer.onError(aVar.a, th, aVar, aVar.c);
            }

            @Override // io.reactivex.Observer
            public void onNext(Object obj) {
                a.this.a();
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }
        }

        public a(Observer<? super T> observer, Subject<Object> subject, ObservableSource<T> observableSource) {
            this.a = observer;
            this.d = subject;
            this.g = observableSource;
        }

        public void a() {
            if (this.b.getAndIncrement() == 0) {
                while (!isDisposed()) {
                    if (!this.h) {
                        this.h = true;
                        this.g.subscribe(this);
                    }
                    if (this.b.decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.f);
            DisposableHelper.dispose(this.e);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.f.get());
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            DisposableHelper.replace(this.f, null);
            this.h = false;
            this.d.onNext(0);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.e);
            HalfSerializer.onError(this.a, th, this, this.c);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            HalfSerializer.onNext(this.a, t, this, this.c);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.f, disposable);
        }
    }

    public ObservableRepeatWhen(ObservableSource<T> observableSource, Function<? super Observable<Object>, ? extends ObservableSource<?>> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        Subject<T> serialized = PublishSubject.create().toSerialized();
        try {
            ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.a.apply(serialized), "The handler returned a null ObservableSource");
            a aVar = new a(observer, serialized, this.source);
            observer.onSubscribe(aVar);
            observableSource.subscribe(aVar.e);
            aVar.a();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
