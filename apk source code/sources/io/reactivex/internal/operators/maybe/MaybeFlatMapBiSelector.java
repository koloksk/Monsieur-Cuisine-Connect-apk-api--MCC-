package io.reactivex.internal.operators.maybe;

import defpackage.al;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class MaybeFlatMapBiSelector<T, U, R> extends al<T, R> {
    public final Function<? super T, ? extends MaybeSource<? extends U>> a;
    public final BiFunction<? super T, ? super U, ? extends R> b;

    public static final class a<T, U, R> implements MaybeObserver<T>, Disposable {
        public final Function<? super T, ? extends MaybeSource<? extends U>> a;
        public final C0032a<T, U, R> b;

        /* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapBiSelector$a$a, reason: collision with other inner class name */
        public static final class C0032a<T, U, R> extends AtomicReference<Disposable> implements MaybeObserver<U> {
            public static final long serialVersionUID = -2897979525538174559L;
            public final MaybeObserver<? super R> a;
            public final BiFunction<? super T, ? super U, ? extends R> b;
            public T c;

            public C0032a(MaybeObserver<? super R> maybeObserver, BiFunction<? super T, ? super U, ? extends R> biFunction) {
                this.a = maybeObserver;
                this.b = biFunction;
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                this.a.onComplete();
            }

            @Override // io.reactivex.MaybeObserver
            public void onError(Throwable th) {
                this.a.onError(th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSuccess(U u) {
                T t = this.c;
                this.c = null;
                try {
                    this.a.onSuccess(ObjectHelper.requireNonNull(this.b.apply(t, u), "The resultSelector returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.a.onError(th);
                }
            }
        }

        public a(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.b = new C0032a<>(maybeObserver, biFunction);
            this.a = function;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.b);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.b.get());
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.b.a.onComplete();
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.b.a.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this.b, disposable)) {
                this.b.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            try {
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.a.apply(t), "The mapper returned a null MaybeSource");
                if (DisposableHelper.replace(this.b, null)) {
                    C0032a<T, U, R> c0032a = this.b;
                    c0032a.c = t;
                    maybeSource.subscribe(c0032a);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.a.onError(th);
            }
        }
    }

    public MaybeFlatMapBiSelector(MaybeSource<T> maybeSource, Function<? super T, ? extends MaybeSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        super(maybeSource);
        this.a = function;
        this.b = biFunction;
    }

    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a, this.b));
    }
}
