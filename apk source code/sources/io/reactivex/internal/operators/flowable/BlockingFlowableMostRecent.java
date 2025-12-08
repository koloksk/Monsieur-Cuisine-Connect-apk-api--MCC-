package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.subscribers.DefaultSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public final class BlockingFlowableMostRecent<T> implements Iterable<T> {
    public final Flowable<T> a;
    public final T b;

    public static final class a<T> extends DefaultSubscriber<T> {
        public volatile Object b;

        /* renamed from: io.reactivex.internal.operators.flowable.BlockingFlowableMostRecent$a$a, reason: collision with other inner class name */
        public final class C0015a implements Iterator<T> {
            public Object a;

            public C0015a() {
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

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.b = NotificationLite.complete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.b = NotificationLite.error(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.b = NotificationLite.next(t);
        }
    }

    public BlockingFlowableMostRecent(Flowable<T> flowable, T t) {
        this.a = flowable;
        this.b = t;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        a aVar = new a(this.b);
        this.a.subscribe((FlowableSubscriber) aVar);
        return new a.C0015a();
    }
}
