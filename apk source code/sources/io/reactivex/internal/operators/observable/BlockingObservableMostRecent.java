package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DefaultObserver;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public final class BlockingObservableMostRecent<T> implements Iterable<T> {
    public final ObservableSource<T> a;
    public final T b;

    public static final class a<T> extends DefaultObserver<T> {
        public volatile Object b;

        /* renamed from: io.reactivex.internal.operators.observable.BlockingObservableMostRecent$a$a, reason: collision with other inner class name */
        public final class C0052a implements Iterator<T> {
            public Object a;

            public C0052a() {
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                this.a = a.this.b;
                return !NotificationLite.isComplete(r0);
            }

            @Override // java.util.Iterator
            public T next() {
                try {
                    if (this.a == null) {
                        this.a = a.this.b;
                    }
                    if (NotificationLite.isComplete(this.a)) {
                        throw new NoSuchElementException();
                    }
                    if (NotificationLite.isError(this.a)) {
                        throw ExceptionHelper.wrapOrThrow(NotificationLite.getError(this.a));
                    }
                    return (T) NotificationLite.getValue(this.a);
                } finally {
                    this.a = null;
                }
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Read only iterator");
            }
        }

        public a(T t) {
            this.b = NotificationLite.next(t);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.b = NotificationLite.complete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.b = NotificationLite.error(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.b = NotificationLite.next(t);
        }
    }

    public BlockingObservableMostRecent(ObservableSource<T> observableSource, T t) {
        this.a = observableSource;
        this.b = t;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        a aVar = new a(this.b);
        this.a.subscribe(aVar);
        return new a.C0052a();
    }
}
