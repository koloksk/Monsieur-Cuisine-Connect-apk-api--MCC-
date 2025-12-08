package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class BlockingFlowableIterable<T> implements Iterable<T> {
    public final Flowable<T> a;
    public final int b;

    public static final class a<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Iterator<T>, Runnable, Disposable {
        public static final long serialVersionUID = 6695226475494099826L;
        public final SpscArrayQueue<T> a;
        public final long b;
        public final long c;
        public final Lock d;
        public final Condition e;
        public long f;
        public volatile boolean g;
        public volatile Throwable h;

        public a(int i) {
            this.a = new SpscArrayQueue<>(i);
            this.b = i;
            this.c = i - (i >> 2);
            ReentrantLock reentrantLock = new ReentrantLock();
            this.d = reentrantLock;
            this.e = reentrantLock.newCondition();
        }

        public void a() {
            this.d.lock();
            try {
                this.e.signalAll();
            } finally {
                this.d.unlock();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
            a();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (!isDisposed()) {
                boolean z = this.g;
                boolean zIsEmpty = this.a.isEmpty();
                if (z) {
                    Throwable th = this.h;
                    if (th != null) {
                        throw ExceptionHelper.wrapOrThrow(th);
                    }
                    if (zIsEmpty) {
                        return false;
                    }
                }
                if (!zIsEmpty) {
                    return true;
                }
                BlockingHelper.verifyNonBlocking();
                this.d.lock();
                while (!this.g && this.a.isEmpty() && !isDisposed()) {
                    try {
                        try {
                            this.e.await();
                        } catch (InterruptedException e) {
                            SubscriptionHelper.cancel(this);
                            a();
                            throw ExceptionHelper.wrapOrThrow(e);
                        }
                    } finally {
                        this.d.unlock();
                    }
                }
            }
            Throwable th2 = this.h;
            if (th2 == null) {
                return false;
            }
            throw ExceptionHelper.wrapOrThrow(th2);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T tPoll = this.a.poll();
            long j = this.f + 1;
            if (j == this.c) {
                this.f = 0L;
                get().request(j);
            } else {
                this.f = j;
            }
            return tPoll;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.g = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.h = th;
            this.g = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.a.offer(t)) {
                a();
                return;
            }
            SubscriptionHelper.cancel(this);
            this.h = new MissingBackpressureException("Queue full?!");
            this.g = true;
            a();
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, this.b);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        @Override // java.lang.Runnable
        public void run() {
            SubscriptionHelper.cancel(this);
            a();
        }
    }

    public BlockingFlowableIterable(Flowable<T> flowable, int i) {
        this.a = flowable;
        this.b = i;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        a aVar = new a(this.b);
        this.a.subscribe((FlowableSubscriber) aVar);
        return aVar;
    }
}
