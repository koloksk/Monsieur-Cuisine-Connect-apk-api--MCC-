package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class CompletableConcatIterable extends Completable {
    public final Iterable<? extends CompletableSource> a;

    public static final class a extends AtomicInteger implements CompletableObserver {
        public static final long serialVersionUID = -7965400327305809232L;
        public final CompletableObserver a;
        public final Iterator<? extends CompletableSource> b;
        public final SequentialDisposable c = new SequentialDisposable();

        public a(CompletableObserver completableObserver, Iterator<? extends CompletableSource> it) {
            this.a = completableObserver;
            this.b = it;
        }

        public void a() {
            if (!this.c.isDisposed() && getAndIncrement() == 0) {
                Iterator<? extends CompletableSource> it = this.b;
                while (!this.c.isDisposed()) {
                    try {
                        if (!it.hasNext()) {
                            this.a.onComplete();
                            return;
                        }
                        try {
                            ((CompletableSource) ObjectHelper.requireNonNull(it.next(), "The CompletableSource returned is null")).subscribe(this);
                            if (decrementAndGet() == 0) {
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.a.onError(th);
                            return;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        this.a.onError(th2);
                        return;
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
            this.c.replace(disposable);
        }
    }

    public CompletableConcatIterable(Iterable<? extends CompletableSource> iterable) {
        this.a = iterable;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        try {
            a aVar = new a(completableObserver, (Iterator) ObjectHelper.requireNonNull(this.a.iterator(), "The iterator returned is null"));
            completableObserver.onSubscribe(aVar.c);
            aVar.a();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, completableObserver);
        }
    }
}
