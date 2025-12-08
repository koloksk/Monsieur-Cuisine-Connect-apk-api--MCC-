package io.reactivex.internal.operators.maybe;

import defpackage.al;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class MaybeFlatMapNotification<T, R> extends al<T, R> {
    public final Function<? super T, ? extends MaybeSource<? extends R>> a;
    public final Function<? super Throwable, ? extends MaybeSource<? extends R>> b;
    public final Callable<? extends MaybeSource<? extends R>> c;

    public static final class a<T, R> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        public static final long serialVersionUID = 4375739915521278546L;
        public final MaybeObserver<? super R> a;
        public final Function<? super T, ? extends MaybeSource<? extends R>> b;
        public final Function<? super Throwable, ? extends MaybeSource<? extends R>> c;
        public final Callable<? extends MaybeSource<? extends R>> d;
        public Disposable e;

        /* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapNotification$a$a, reason: collision with other inner class name */
        public final class C0033a implements MaybeObserver<R> {
            public C0033a() {
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                a.this.a.onComplete();
            }

            @Override // io.reactivex.MaybeObserver
            public void onError(Throwable th) {
                a.this.a.onError(th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(a.this, disposable);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSuccess(R r) {
                a.this.a.onSuccess(r);
            }
        }

        public a(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends R>> function, Function<? super Throwable, ? extends MaybeSource<? extends R>> function2, Callable<? extends MaybeSource<? extends R>> callable) {
            this.a = maybeObserver;
            this.b = function;
            this.c = function2;
            this.d = callable;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            this.e.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            try {
                ((MaybeSource) ObjectHelper.requireNonNull(this.d.call(), "The onCompleteSupplier returned a null MaybeSource")).subscribe(new C0033a());
            } catch (Exception e) {
                Exceptions.throwIfFatal(e);
                this.a.onError(e);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            try {
                ((MaybeSource) ObjectHelper.requireNonNull(this.c.apply(th), "The onErrorMapper returned a null MaybeSource")).subscribe(new C0033a());
            } catch (Exception e) {
                Exceptions.throwIfFatal(e);
                this.a.onError(new CompositeException(th, e));
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            try {
                ((MaybeSource) ObjectHelper.requireNonNull(this.b.apply(t), "The onSuccessMapper returned a null MaybeSource")).subscribe(new C0033a());
            } catch (Exception e) {
                Exceptions.throwIfFatal(e);
                this.a.onError(e);
            }
        }
    }

    public MaybeFlatMapNotification(MaybeSource<T> maybeSource, Function<? super T, ? extends MaybeSource<? extends R>> function, Function<? super Throwable, ? extends MaybeSource<? extends R>> function2, Callable<? extends MaybeSource<? extends R>> callable) {
        super(maybeSource);
        this.a = function;
        this.b = function2;
        this.c = callable;
    }

    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a, this.b, this.c));
    }
}
