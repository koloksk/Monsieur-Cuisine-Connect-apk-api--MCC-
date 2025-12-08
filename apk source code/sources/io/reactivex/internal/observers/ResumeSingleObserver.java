package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ResumeSingleObserver<T> implements SingleObserver<T> {
    public final AtomicReference<Disposable> a;
    public final SingleObserver<? super T> b;

    public ResumeSingleObserver(AtomicReference<Disposable> atomicReference, SingleObserver<? super T> singleObserver) {
        this.a = atomicReference;
        this.b = singleObserver;
    }

    @Override // io.reactivex.SingleObserver
    public void onError(Throwable th) {
        this.b.onError(th);
    }

    @Override // io.reactivex.SingleObserver
    public void onSubscribe(Disposable disposable) {
        DisposableHelper.replace(this.a, disposable);
    }

    @Override // io.reactivex.SingleObserver
    public void onSuccess(T t) {
        this.b.onSuccess(t);
    }
}
