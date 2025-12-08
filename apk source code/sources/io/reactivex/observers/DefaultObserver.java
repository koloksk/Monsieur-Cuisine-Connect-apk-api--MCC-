package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

/* loaded from: classes.dex */
public abstract class DefaultObserver<T> implements Observer<T> {
    public Disposable a;

    public final void cancel() {
        Disposable disposable = this.a;
        this.a = DisposableHelper.DISPOSED;
        disposable.dispose();
    }

    public void onStart() {
    }

    @Override // io.reactivex.Observer
    public final void onSubscribe(@NonNull Disposable disposable) {
        if (EndConsumerHelper.validate(this.a, disposable, getClass())) {
            this.a = disposable;
            onStart();
        }
    }
}
