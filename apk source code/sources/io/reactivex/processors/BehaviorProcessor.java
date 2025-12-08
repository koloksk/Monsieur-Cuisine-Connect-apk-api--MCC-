package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class BehaviorProcessor<T> extends FlowableProcessor<T> {
    public static final Object[] i = new Object[0];
    public static final a[] j = new a[0];
    public static final a[] k = new a[0];
    public final AtomicReference<a<T>[]> b;
    public final ReadWriteLock c;
    public final Lock d;
    public final Lock e;
    public final AtomicReference<Object> f = new AtomicReference<>();
    public final AtomicReference<Throwable> g;
    public long h;

    public BehaviorProcessor() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.c = reentrantReadWriteLock;
        this.d = reentrantReadWriteLock.readLock();
        this.e = this.c.writeLock();
        this.b = new AtomicReference<>(j);
        this.g = new AtomicReference<>();
    }

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorProcessor<T> create() {
        return new BehaviorProcessor<>();
    }

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorProcessor<T> createDefault(T t) {
        ObjectHelper.requireNonNull(t, "defaultValue is null");
        BehaviorProcessor<T> behaviorProcessor = new BehaviorProcessor<>();
        behaviorProcessor.f.lazySet(ObjectHelper.requireNonNull(t, "defaultValue is null"));
        return behaviorProcessor;
    }

    public void a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.b.get();
            int length = aVarArr.length;
            if (length == 0) {
                return;
            }
            int i2 = -1;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                if (aVarArr[i3] == aVar) {
                    i2 = i3;
                    break;
                }
                i3++;
            }
            if (i2 < 0) {
                return;
            }
            if (length == 1) {
                aVarArr2 = j;
            } else {
                a<T>[] aVarArr3 = new a[length - 1];
                System.arraycopy(aVarArr, 0, aVarArr3, 0, i2);
                System.arraycopy(aVarArr, i2 + 1, aVarArr3, i2, (length - i2) - 1);
                aVarArr2 = aVarArr3;
            }
        } while (!this.b.compareAndSet(aVarArr, aVarArr2));
    }

    public a<T>[] b(Object obj) {
        a<T>[] andSet = this.b.get();
        a<T>[] aVarArr = k;
        if (andSet != aVarArr && (andSet = this.b.getAndSet(aVarArr)) != k) {
            a(obj);
        }
        return andSet;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    @Nullable
    public Throwable getThrowable() {
        Object obj = this.f.get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    @Nullable
    public T getValue() {
        Object obj = this.f.get();
        if (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) {
            return null;
        }
        return (T) NotificationLite.getValue(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Object[] getValues() {
        Object[] values = getValues(i);
        return values == i ? new Object[0] : values;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasComplete() {
        return NotificationLite.isComplete(this.f.get());
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.b.get().length != 0;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasThrowable() {
        return NotificationLite.isError(this.f.get());
    }

    public boolean hasValue() {
        Object obj = this.f.get();
        return (obj == null || NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? false : true;
    }

    public boolean offer(T t) {
        if (t == null) {
            onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            return true;
        }
        a<T>[] aVarArr = this.b.get();
        for (a<T> aVar : aVarArr) {
            if (aVar.get() == 0) {
                return false;
            }
        }
        Object next = NotificationLite.next(t);
        a(next);
        for (a<T> aVar2 : aVarArr) {
            aVar2.a(next, this.h);
        }
        return true;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.g.compareAndSet(null, ExceptionHelper.TERMINATED)) {
            Object objComplete = NotificationLite.complete();
            for (a<T> aVar : b(objComplete)) {
                aVar.a(objComplete, this.h);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.g.compareAndSet(null, th)) {
            RxJavaPlugins.onError(th);
            return;
        }
        Object objError = NotificationLite.error(th);
        for (a<T> aVar : b(objError)) {
            aVar.a(objError, this.h);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.g.get() != null) {
            return;
        }
        Object next = NotificationLite.next(t);
        a(next);
        for (a<T> aVar : this.b.get()) {
            aVar.a(next, this.h);
        }
    }

    @Override // org.reactivestreams.Subscriber, io.reactivex.FlowableSubscriber
    public void onSubscribe(Subscription subscription) {
        if (this.g.get() != null) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        boolean z;
        a<T> aVar = new a<>(subscriber, this);
        subscriber.onSubscribe(aVar);
        while (true) {
            a<T>[] aVarArr = this.b.get();
            z = false;
            if (aVarArr == k) {
                break;
            }
            int length = aVarArr.length;
            a<T>[] aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
            if (this.b.compareAndSet(aVarArr, aVarArr2)) {
                z = true;
                break;
            }
        }
        if (z) {
            if (aVar.g) {
                a((a) aVar);
                return;
            } else {
                aVar.a();
                return;
            }
        }
        Throwable th = this.g.get();
        if (th == ExceptionHelper.TERMINATED) {
            subscriber.onComplete();
        } else {
            subscriber.onError(th);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public T[] getValues(T[] tArr) {
        Object obj = this.f.get();
        if (obj != null && !NotificationLite.isComplete(obj) && !NotificationLite.isError(obj)) {
            Object value = NotificationLite.getValue(obj);
            if (tArr.length != 0) {
                tArr[0] = value;
                if (tArr.length == 1) {
                    return tArr;
                }
                tArr[1] = 0;
                return tArr;
            }
            T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), 1));
            tArr2[0] = value;
            return tArr2;
        }
        if (tArr.length != 0) {
            tArr[0] = 0;
        }
        return tArr;
    }

    public void a(Object obj) {
        Lock lock = this.e;
        lock.lock();
        this.h++;
        this.f.lazySet(obj);
        lock.unlock();
    }

    public static final class a<T> extends AtomicLong implements Subscription, AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
        public static final long serialVersionUID = 3293175281126227086L;
        public final Subscriber<? super T> a;
        public final BehaviorProcessor<T> b;
        public boolean c;
        public boolean d;
        public AppendOnlyLinkedArrayList<Object> e;
        public boolean f;
        public volatile boolean g;
        public long h;

        public a(Subscriber<? super T> subscriber, BehaviorProcessor<T> behaviorProcessor) {
            this.a = subscriber;
            this.b = behaviorProcessor;
        }

        public void a() {
            if (this.g) {
                return;
            }
            synchronized (this) {
                if (this.g) {
                    return;
                }
                if (this.c) {
                    return;
                }
                BehaviorProcessor<T> behaviorProcessor = this.b;
                Lock lock = behaviorProcessor.d;
                lock.lock();
                this.h = behaviorProcessor.h;
                Object obj = behaviorProcessor.f.get();
                lock.unlock();
                this.d = obj != null;
                this.c = true;
                if (obj == null || test(obj)) {
                    return;
                }
                b();
            }
        }

        public void b() {
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
            while (!this.g) {
                synchronized (this) {
                    appendOnlyLinkedArrayList = this.e;
                    if (appendOnlyLinkedArrayList == null) {
                        this.d = false;
                        return;
                    }
                    this.e = null;
                }
                appendOnlyLinkedArrayList.forEachWhile(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.g) {
                return;
            }
            this.g = true;
            this.b.a((a) this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        @Override // io.reactivex.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.functions.Predicate
        public boolean test(Object obj) {
            if (this.g) {
                return true;
            }
            if (NotificationLite.isComplete(obj)) {
                this.a.onComplete();
                return true;
            }
            if (NotificationLite.isError(obj)) {
                this.a.onError(NotificationLite.getError(obj));
                return true;
            }
            long j = get();
            if (j == 0) {
                cancel();
                this.a.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
                return true;
            }
            this.a.onNext((Object) NotificationLite.getValue(obj));
            if (j == Long.MAX_VALUE) {
                return false;
            }
            decrementAndGet();
            return false;
        }

        public void a(Object obj, long j) {
            if (this.g) {
                return;
            }
            if (!this.f) {
                synchronized (this) {
                    if (this.g) {
                        return;
                    }
                    if (this.h == j) {
                        return;
                    }
                    if (this.d) {
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.e;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                            this.e = appendOnlyLinkedArrayList;
                        }
                        appendOnlyLinkedArrayList.add(obj);
                        return;
                    }
                    this.c = true;
                    this.f = true;
                }
            }
            test(obj);
        }
    }
}
