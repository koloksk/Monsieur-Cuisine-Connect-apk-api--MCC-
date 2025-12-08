package io.reactivex.internal.operators.maybe;

import defpackage.al;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class MaybeSubscribeOn<T> extends al<T, T> {
    public final Scheduler a;

    public static final class a<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        public static final long serialVersionUID = 8571289934935992137L;
        public final SequentialDisposable a = new SequentialDisposable();
        public final MaybeObserver<? super T> b;

        public a(MaybeObserver<? super T> maybeObserver) {
            this.b = maybeObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            this.a.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.b.onComplete();
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.b.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            this.b.onSuccess(t);
        }
    }

    public static final class b<T> implements Runnable {
        public final MaybeObserver<? super T> a;
        public final MaybeSource<T> b;

        public b(MaybeObserver<? super T> maybeObserver, MaybeSource<T> maybeSource) {
            this.a = maybeObserver;
            this.b = maybeSource;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.b.subscribe(this.a);
        }
    }

    public MaybeSubscribeOn(MaybeSource<T> maybeSource, Scheduler scheduler) {
        super(maybeSource);
        this.a = scheduler;
    }

    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        a aVar = new a(maybeObserver);
        maybeObserver.onSubscribe(aVar);
        aVar.a.replace(this.a.scheduleDirect(new b(aVar, this.source)));
    }
}
