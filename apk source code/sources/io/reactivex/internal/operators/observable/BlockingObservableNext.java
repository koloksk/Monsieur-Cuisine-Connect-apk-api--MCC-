package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class BlockingObservableNext<T> implements Iterable<T> {
    public final ObservableSource<T> a;

    public static final class a<T> implements Iterator<T> {
        public final b<T> a;
        public final ObservableSource<T> b;
        public T c;
        public boolean d = true;
        public boolean e = true;
        public Throwable f;
        public boolean g;

        public a(ObservableSource<T> observableSource, b<T> bVar) {
            this.b = observableSource;
            this.a = bVar;
        }

        @Override // java.util.Iterator
        public boolean hasNext() throws InterruptedException {
            boolean z;
            Throwable th = this.f;
            if (th != null) {
                throw ExceptionHelper.wrapOrThrow(th);
            }
            if (!this.d) {
                return false;
            }
            if (this.e) {
                if (!this.g) {
                    this.g = true;
                    this.a.c.set(1);
                    new ObservableMaterialize(this.b).subscribe(this.a);
                }
                try {
                    b<T> bVar = this.a;
                    bVar.c.set(1);
                    BlockingHelper.verifyNonBlocking();
                    Notification<T> notificationTake = bVar.b.take();
                    if (notificationTake.isOnNext()) {
                        this.e = false;
                        this.c = notificationTake.getValue();
                        z = true;
                    } else {
                        this.d = false;
                        if (!notificationTake.isOnComplete()) {
                            Throwable error = notificationTake.getError();
                            this.f = error;
                            throw ExceptionHelper.wrapOrThrow(error);
                        }
                        z = false;
                    }
                    if (!z) {
                        return false;
                    }
                } catch (InterruptedException e) {
                    this.a.dispose();
                    this.f = e;
                    throw ExceptionHelper.wrapOrThrow(e);
                }
            }
            return true;
        }

        @Override // java.util.Iterator
        public T next() {
            Throwable th = this.f;
            if (th != null) {
                throw ExceptionHelper.wrapOrThrow(th);
            }
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            this.e = true;
            return this.c;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Read only iterator");
        }
    }

    public static final class b<T> extends DisposableObserver<Notification<T>> {
        public final BlockingQueue<Notification<T>> b = new ArrayBlockingQueue(1);
        public final AtomicInteger c = new AtomicInteger();

        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(Object obj) {
            Notification<T> notification = (Notification) obj;
            if (this.c.getAndSet(0) == 1 || !notification.isOnNext()) {
                while (!this.b.offer(notification)) {
                    Notification<T> notificationPoll = this.b.poll();
                    if (notificationPoll != null && !notificationPoll.isOnNext()) {
                        notification = notificationPoll;
                    }
                }
            }
        }
    }

    public BlockingObservableNext(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new a(this.a, new b());
    }
}
