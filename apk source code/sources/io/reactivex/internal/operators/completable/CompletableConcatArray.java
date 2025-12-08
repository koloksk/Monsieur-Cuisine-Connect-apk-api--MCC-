package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class CompletableConcatArray extends Completable {
    public final CompletableSource[] a;

    public static final class a extends AtomicInteger implements CompletableObserver {
        public static final long serialVersionUID = -7965400327305809232L;
        public final CompletableObserver a;
        public final CompletableSource[] b;
        public int c;
        public final SequentialDisposable d = new SequentialDisposable();

        public a(CompletableObserver completableObserver, CompletableSource[] completableSourceArr) {
            this.a = completableObserver;
            this.b = completableSourceArr;
        }

        public void a() {
            if (!this.d.isDisposed() && getAndIncrement() == 0) {
                CompletableSource[] completableSourceArr = this.b;
                while (!this.d.isDisposed()) {
                    int i = this.c;
                    this.c = i + 1;
                    if (i == completableSourceArr.length) {
                        this.a.onComplete();
                        return;
                    } else {
                        completableSourceArr[i].subscribe(this);
                        if (decrementAndGet() == 0) {
                            return;
                        }
                    }
                }
            }
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            a();
        }

        @Override // io.reactivex.CompletableObserver
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.d.replace(disposable);
        }
    }

    public CompletableConcatArray(CompletableSource[] completableSourceArr) {
        this.a = completableSourceArr;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        a aVar = new a(completableObserver, this.a);
        completableObserver.onSubscribe(aVar.d);
        aVar.a();
    }
}
