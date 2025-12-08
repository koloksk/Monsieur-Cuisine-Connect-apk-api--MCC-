package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes.dex */
public final class SingleDoOnSubscribe<T> extends Single<T> {
    public final SingleSource<T> a;
    public final Consumer<? super Disposable> b;

    public static final class a<T> implements SingleObserver<T> {
        public final SingleObserver<? super T> a;
        public final Consumer<? super Disposable> b;
        public boolean c;

        public a(SingleObserver<? super T> singleObserver, Consumer<? super Disposable> consumer) {
            this.a = singleObserver;
            this.b = consumer;
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            try {
                this.b.accept(disposable);
                this.a.onSubscribe(disposable);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.c = true;
                disposable.dispose();
                EmptyDisposable.error(th, this.a);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            if (this.c) {
                return;
            }
            this.a.onSuccess(t);
        }
    }

    public SingleDoOnSubscribe(SingleSource<T> singleSource, Consumer<? super Disposable> consumer) {
        this.a = singleSource;
        this.b = consumer;
    }

    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b));
    }
}
