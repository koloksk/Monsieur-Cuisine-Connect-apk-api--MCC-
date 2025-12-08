package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class CompletableCache extends Completable implements CompletableObserver {
    public static final a[] e = new a[0];
    public static final a[] f = new a[0];
    public final CompletableSource a;
    public final AtomicReference<a[]> b = new AtomicReference<>(e);
    public final AtomicBoolean c = new AtomicBoolean();
    public Throwable d;

    public final class a extends AtomicBoolean implements Disposable {
        public static final long serialVersionUID = 8943152917179642732L;
        public final CompletableObserver a;

        public a(CompletableObserver completableObserver) {
            this.a = completableObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (compareAndSet(false, true)) {
                CompletableCache.this.a(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get();
        }
    }

    public CompletableCache(CompletableSource completableSource) {
        this.a = completableSource;
    }

    public void a(a aVar) {
        a[] aVarArr;
        a[] aVarArr2;
        do {
            aVarArr = this.b.get();
            int length = aVarArr.length;
            if (length == 0) {
                return;
            }
            int i = -1;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (aVarArr[i2] == aVar) {
                    i = i2;
                    break;
                }
                i2++;
            }
            if (i < 0) {
                return;
            }
            if (length == 1) {
                aVarArr2 = e;
            } else {
                a[] aVarArr3 = new a[length - 1];
                System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                aVarArr2 = aVarArr3;
            }
        } while (!this.b.compareAndSet(aVarArr, aVarArr2));
    }

    @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
    public void onComplete() {
        for (a aVar : this.b.getAndSet(f)) {
            if (!aVar.get()) {
                aVar.a.onComplete();
            }
        }
    }

    @Override // io.reactivex.CompletableObserver
    public void onError(Throwable th) {
        this.d = th;
        for (a aVar : this.b.getAndSet(f)) {
            if (!aVar.get()) {
                aVar.a.onError(th);
            }
        }
    }

    @Override // io.reactivex.CompletableObserver
    public void onSubscribe(Disposable disposable) {
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        boolean z;
        a aVar = new a(completableObserver);
        completableObserver.onSubscribe(aVar);
        while (true) {
            a[] aVarArr = this.b.get();
            if (aVarArr == f) {
                z = false;
                break;
            }
            int length = aVarArr.length;
            a[] aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
            if (this.b.compareAndSet(aVarArr, aVarArr2)) {
                z = true;
                break;
            }
        }
        if (z) {
            if (aVar.get()) {
                a(aVar);
            }
            if (this.c.compareAndSet(false, true)) {
                this.a.subscribe(this);
                return;
            }
            return;
        }
        Throwable th = this.d;
        if (th != null) {
            completableObserver.onError(th);
        } else {
            completableObserver.onComplete();
        }
    }
}
