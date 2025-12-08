package io.reactivex.internal.operators.mixed;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Notification;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

@Experimental
/* loaded from: classes.dex */
public final class MaterializeSingleObserver<T> implements SingleObserver<T>, MaybeObserver<T>, CompletableObserver, Disposable {
    public final SingleObserver<? super Notification<T>> a;
    public Disposable b;

    public MaterializeSingleObserver(SingleObserver<? super Notification<T>> singleObserver) {
        this.a = singleObserver;
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        this.b.dispose();
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return this.b.isDisposed();
    }

    @Override // io.reactivex.MaybeObserver
    public void onComplete() {
        this.a.onSuccess(Notification.createOnComplete());
    }

    @Override // io.reactivex.SingleObserver
    public void onError(Throwable th) {
        this.a.onSuccess(Notification.createOnError(th));
    }

    @Override // io.reactivex.SingleObserver
    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.validate(this.b, disposable)) {
            this.b = disposable;
            this.a.onSubscribe(this);
        }
    }

    @Override // io.reactivex.SingleObserver
    public void onSuccess(T t) {
        this.a.onSuccess(Notification.createOnNext(t));
    }
}
