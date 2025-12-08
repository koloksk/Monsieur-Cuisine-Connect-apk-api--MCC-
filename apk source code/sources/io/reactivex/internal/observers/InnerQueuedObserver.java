package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class InnerQueuedObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
    public static final long serialVersionUID = -5417183359794346637L;
    public final InnerQueuedObserverSupport<T> a;
    public final int b;
    public SimpleQueue<T> c;
    public volatile boolean d;
    public int e;

    public InnerQueuedObserver(InnerQueuedObserverSupport<T> innerQueuedObserverSupport, int i) {
        this.a = innerQueuedObserverSupport;
        this.b = i;
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        DisposableHelper.dispose(this);
    }

    public int fusionMode() {
        return this.e;
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return DisposableHelper.isDisposed(get());
    }

    public boolean isDone() {
        return this.d;
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        this.a.innerComplete(this);
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        this.a.innerError(this, th);
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        if (this.e == 0) {
            this.a.innerNext(this, t);
        } else {
            this.a.drain();
        }
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.setOnce(this, disposable)) {
            if (disposable instanceof QueueDisposable) {
                QueueDisposable queueDisposable = (QueueDisposable) disposable;
                int iRequestFusion = queueDisposable.requestFusion(3);
                if (iRequestFusion == 1) {
                    this.e = iRequestFusion;
                    this.c = queueDisposable;
                    this.d = true;
                    this.a.innerComplete(this);
                    return;
                }
                if (iRequestFusion == 2) {
                    this.e = iRequestFusion;
                    this.c = queueDisposable;
                    return;
                }
            }
            this.c = QueueDrainHelper.createQueue(-this.b);
        }
    }

    public SimpleQueue<T> queue() {
        return this.c;
    }

    public void setDone() {
        this.d = true;
    }
}
