package io.reactivex.internal.operators.maybe;

import defpackage.al;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class MaybeTakeUntilMaybe<T, U> extends al<T, T> {
    public final MaybeSource<U> a;

    public static final class a<T, U> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        public static final long serialVersionUID = -2187421758664251153L;
        public final MaybeObserver<? super T> a;
        public final C0038a<U> b = new C0038a<>(this);

        /* renamed from: io.reactivex.internal.operators.maybe.MaybeTakeUntilMaybe$a$a, reason: collision with other inner class name */
        public static final class C0038a<U> extends AtomicReference<Disposable> implements MaybeObserver<U> {
            public static final long serialVersionUID = -1266041316834525931L;
            public final a<?, U> a;

            public C0038a(a<?, U> aVar) {
                this.a = aVar;
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                a<?, U> aVar = this.a;
                if (aVar == null) {
                    throw null;
                }
                if (DisposableHelper.dispose(aVar)) {
                    aVar.a.onComplete();
                }
            }

            @Override // io.reactivex.MaybeObserver
            public void onError(Throwable th) {
                a<?, U> aVar = this.a;
                if (aVar == null) {
                    throw null;
                }
                if (DisposableHelper.dispose(aVar)) {
                    aVar.a.onError(th);
                } else {
                    RxJavaPlugins.onError(th);
                }
            }

            @Override // io.reactivex.MaybeObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSuccess(Object obj) {
                a<?, U> aVar = this.a;
                if (aVar == null) {
                    throw null;
                }
                if (DisposableHelper.dispose(aVar)) {
                    aVar.a.onComplete();
                }
            }
        }

        public a(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            DisposableHelper.dispose(this.b);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            DisposableHelper.dispose(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            DisposableHelper.dispose(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onSuccess(t);
            }
        }
    }

    public MaybeTakeUntilMaybe(MaybeSource<T> maybeSource, MaybeSource<U> maybeSource2) {
        super(maybeSource);
        this.a = maybeSource2;
    }

    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        a aVar = new a(maybeObserver);
        maybeObserver.onSubscribe(aVar);
        this.a.subscribe(aVar.b);
        this.source.subscribe(aVar);
    }
}
