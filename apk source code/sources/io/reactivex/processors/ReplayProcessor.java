package io.reactivex.processors;

import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class ReplayProcessor<T> extends FlowableProcessor<T> {
    public static final Object[] e = new Object[0];
    public static final c[] f = new c[0];
    public static final c[] g = new c[0];
    public final b<T> b;
    public boolean c;
    public final AtomicReference<c<T>[]> d = new AtomicReference<>(f);

    public static final class a<T> extends AtomicReference<a<T>> {
        public static final long serialVersionUID = 6404226426336033100L;
        public final T a;

        public a(T t) {
            this.a = t;
        }
    }

    public interface b<T> {
        void a();

        void a(c<T> cVar);

        void a(T t);

        void a(Throwable th);

        T[] a(T[] tArr);

        Throwable b();

        void complete();

        @Nullable
        T getValue();

        boolean isDone();

        int size();
    }

    public static final class c<T> extends AtomicInteger implements Subscription {
        public static final long serialVersionUID = 466549804534799122L;
        public final Subscriber<? super T> a;
        public final ReplayProcessor<T> b;
        public Object c;
        public final AtomicLong d = new AtomicLong();
        public volatile boolean e;
        public long f;

        public c(Subscriber<? super T> subscriber, ReplayProcessor<T> replayProcessor) {
            this.a = subscriber;
            this.b = replayProcessor;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.e) {
                return;
            }
            this.e = true;
            this.b.a(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.d, j);
                this.b.b.a((c) this);
            }
        }
    }

    public static final class f<T> extends AtomicReference<f<T>> {
        public static final long serialVersionUID = 6404226426336033100L;
        public final T a;
        public final long b;

        public f(T t, long j) {
            this.a = t;
            this.b = j;
        }
    }

    public ReplayProcessor(b<T> bVar) {
        this.b = bVar;
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> create() {
        return new ReplayProcessor<>(new g(16));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> createWithSize(int i) {
        return new ReplayProcessor<>(new e(i));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return new ReplayProcessor<>(new d(Integer.MAX_VALUE, j, timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> createWithTimeAndSize(long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        return new ReplayProcessor<>(new d(i, j, timeUnit, scheduler));
    }

    public void a(c<T> cVar) {
        c<T>[] cVarArr;
        c<T>[] cVarArr2;
        do {
            cVarArr = this.d.get();
            if (cVarArr == g || cVarArr == f) {
                return;
            }
            int length = cVarArr.length;
            int i = -1;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (cVarArr[i2] == cVar) {
                    i = i2;
                    break;
                }
                i2++;
            }
            if (i < 0) {
                return;
            }
            if (length == 1) {
                cVarArr2 = f;
            } else {
                c<T>[] cVarArr3 = new c[length - 1];
                System.arraycopy(cVarArr, 0, cVarArr3, 0, i);
                System.arraycopy(cVarArr, i + 1, cVarArr3, i, (length - i) - 1);
                cVarArr2 = cVarArr3;
            }
        } while (!this.d.compareAndSet(cVarArr, cVarArr2));
    }

    public void cleanupBuffer() {
        this.b.a();
    }

    @Override // io.reactivex.processors.FlowableProcessor
    @Nullable
    public Throwable getThrowable() {
        b<T> bVar = this.b;
        if (bVar.isDone()) {
            return bVar.b();
        }
        return null;
    }

    public T getValue() {
        return this.b.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object[] getValues() {
        Object[] values = getValues(e);
        return values == e ? new Object[0] : values;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasComplete() {
        b<T> bVar = this.b;
        return bVar.isDone() && bVar.b() == null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.d.get().length != 0;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasThrowable() {
        b<T> bVar = this.b;
        return bVar.isDone() && bVar.b() != null;
    }

    public boolean hasValue() {
        return this.b.size() != 0;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.c) {
            return;
        }
        this.c = true;
        b<T> bVar = this.b;
        bVar.complete();
        for (c<T> cVar : this.d.getAndSet(g)) {
            bVar.a((c) cVar);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.c) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.c = true;
        b<T> bVar = this.b;
        bVar.a(th);
        for (c<T> cVar : this.d.getAndSet(g)) {
            bVar.a((c) cVar);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.c) {
            return;
        }
        b<T> bVar = this.b;
        bVar.a((b<T>) t);
        for (c<T> cVar : this.d.get()) {
            bVar.a((c) cVar);
        }
    }

    @Override // org.reactivestreams.Subscriber, io.reactivex.FlowableSubscriber
    public void onSubscribe(Subscription subscription) {
        if (this.c) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        boolean z;
        c<T> cVar = new c<>(subscriber, this);
        subscriber.onSubscribe(cVar);
        while (true) {
            c<T>[] cVarArr = this.d.get();
            z = false;
            if (cVarArr == g) {
                break;
            }
            int length = cVarArr.length;
            c<T>[] cVarArr2 = new c[length + 1];
            System.arraycopy(cVarArr, 0, cVarArr2, 0, length);
            cVarArr2[length] = cVar;
            if (this.d.compareAndSet(cVarArr, cVarArr2)) {
                z = true;
                break;
            }
        }
        if (z && cVar.e) {
            a(cVar);
        } else {
            this.b.a((c) cVar);
        }
    }

    public static final class g<T> implements b<T> {
        public final List<T> a;
        public Throwable b;
        public volatile boolean c;
        public volatile int d;

        public g(int i) {
            this.a = new ArrayList(ObjectHelper.verifyPositive(i, "capacityHint"));
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a() {
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(T t) {
            this.a.add(t);
            this.d++;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public Throwable b() {
            return this.b;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void complete() {
            this.c = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        @Nullable
        public T getValue() {
            int i = this.d;
            if (i == 0) {
                return null;
            }
            return this.a.get(i - 1);
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public boolean isDone() {
            return this.c;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public int size() {
            return this.d;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(Throwable th) {
            this.b = th;
            this.c = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public T[] a(T[] tArr) {
            int i = this.d;
            if (i == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            List<T> list = this.a;
            if (tArr.length < i) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i));
            }
            for (int i2 = 0; i2 < i; i2++) {
                tArr[i2] = list.get(i2);
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(c<T> cVar) {
            if (cVar.getAndIncrement() != 0) {
                return;
            }
            List<T> list = this.a;
            Subscriber<? super T> subscriber = cVar.a;
            Integer num = (Integer) cVar.c;
            int iIntValue = 0;
            if (num != null) {
                iIntValue = num.intValue();
            } else {
                cVar.c = 0;
            }
            long j = cVar.f;
            int iAddAndGet = 1;
            do {
                long j2 = cVar.d.get();
                while (j != j2) {
                    if (cVar.e) {
                        cVar.c = null;
                        return;
                    }
                    boolean z = this.c;
                    int i = this.d;
                    if (z && iIntValue == i) {
                        cVar.c = null;
                        cVar.e = true;
                        Throwable th = this.b;
                        if (th == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th);
                            return;
                        }
                    }
                    if (iIntValue == i) {
                        break;
                    }
                    subscriber.onNext(list.get(iIntValue));
                    iIntValue++;
                    j++;
                }
                if (j == j2) {
                    if (cVar.e) {
                        cVar.c = null;
                        return;
                    }
                    boolean z2 = this.c;
                    int i2 = this.d;
                    if (z2 && iIntValue == i2) {
                        cVar.c = null;
                        cVar.e = true;
                        Throwable th2 = this.b;
                        if (th2 == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th2);
                            return;
                        }
                    }
                }
                cVar.c = Integer.valueOf(iIntValue);
                cVar.f = j;
                iAddAndGet = cVar.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplayProcessor<T> create(int i) {
        return new ReplayProcessor<>(new g(i));
    }

    public static final class d<T> implements b<T> {
        public final int a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;
        public int e;
        public volatile f<T> f;
        public f<T> g;
        public Throwable h;
        public volatile boolean i;

        public d(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = ObjectHelper.verifyPositive(i, "maxSize");
            this.b = ObjectHelper.verifyPositive(j, "maxAge");
            this.c = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
            this.d = (Scheduler) ObjectHelper.requireNonNull(scheduler, "scheduler is null");
            f<T> fVar = new f<>(null, 0L);
            this.g = fVar;
            this.f = fVar;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a() {
            if (this.f.a != null) {
                f<T> fVar = new f<>(null, 0L);
                fVar.lazySet(this.f.get());
                this.f = fVar;
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public Throwable b() {
            return this.h;
        }

        public f<T> c() {
            f<T> fVar;
            f<T> fVar2 = this.f;
            long jNow = this.d.now(this.c) - this.b;
            f<T> fVar3 = fVar2.get();
            while (true) {
                f<T> fVar4 = fVar3;
                fVar = fVar2;
                fVar2 = fVar4;
                if (fVar2 == null || fVar2.b > jNow) {
                    break;
                }
                fVar3 = fVar2.get();
            }
            return fVar;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void complete() {
            d();
            this.i = true;
        }

        public void d() {
            long jNow = this.d.now(this.c) - this.b;
            f<T> fVar = this.f;
            while (true) {
                f<T> fVar2 = fVar.get();
                if (fVar2 == null) {
                    if (fVar.a != null) {
                        this.f = new f<>(null, 0L);
                        return;
                    } else {
                        this.f = fVar;
                        return;
                    }
                }
                if (fVar2.b > jNow) {
                    if (fVar.a == null) {
                        this.f = fVar;
                        return;
                    }
                    f<T> fVar3 = new f<>(null, 0L);
                    fVar3.lazySet(fVar.get());
                    this.f = fVar3;
                    return;
                }
                fVar = fVar2;
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        @Nullable
        public T getValue() {
            f<T> fVar = this.f;
            while (true) {
                f<T> fVar2 = fVar.get();
                if (fVar2 == null) {
                    break;
                }
                fVar = fVar2;
            }
            if (fVar.b < this.d.now(this.c) - this.b) {
                return null;
            }
            return fVar.a;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public boolean isDone() {
            return this.i;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public int size() {
            return a((f) c());
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(T t) {
            f<T> fVar = new f<>(t, this.d.now(this.c));
            f<T> fVar2 = this.g;
            this.g = fVar;
            this.e++;
            fVar2.set(fVar);
            int i = this.e;
            if (i > this.a) {
                this.e = i - 1;
                this.f = this.f.get();
            }
            long jNow = this.d.now(this.c) - this.b;
            f<T> fVar3 = this.f;
            while (this.e > 1) {
                f<T> fVar4 = fVar3.get();
                if (fVar4 == null) {
                    this.f = fVar3;
                    return;
                } else if (fVar4.b > jNow) {
                    this.f = fVar3;
                    return;
                } else {
                    this.e--;
                    fVar3 = fVar4;
                }
            }
            this.f = fVar3;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(Throwable th) {
            d();
            this.h = th;
            this.i = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public T[] a(T[] tArr) {
            f<T> fVarC = c();
            int iA = a((f) fVarC);
            if (iA == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
            } else {
                if (tArr.length < iA) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), iA));
                }
                for (int i = 0; i != iA; i++) {
                    fVarC = fVarC.get();
                    tArr[i] = fVarC.a;
                }
                if (tArr.length > iA) {
                    tArr[iA] = null;
                }
            }
            return tArr;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(c<T> cVar) {
            if (cVar.getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = cVar.a;
            f<T> fVarC = (f) cVar.c;
            if (fVarC == null) {
                fVarC = c();
            }
            long j = cVar.f;
            int iAddAndGet = 1;
            do {
                long j2 = cVar.d.get();
                while (j != j2) {
                    if (cVar.e) {
                        cVar.c = null;
                        return;
                    }
                    boolean z = this.i;
                    f<T> fVar = fVarC.get();
                    boolean z2 = fVar == null;
                    if (z && z2) {
                        cVar.c = null;
                        cVar.e = true;
                        Throwable th = this.h;
                        if (th == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th);
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(fVar.a);
                    j++;
                    fVarC = fVar;
                }
                if (j == j2) {
                    if (cVar.e) {
                        cVar.c = null;
                        return;
                    }
                    if (this.i && fVarC.get() == null) {
                        cVar.c = null;
                        cVar.e = true;
                        Throwable th2 = this.h;
                        if (th2 == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th2);
                            return;
                        }
                    }
                }
                cVar.c = fVarC;
                cVar.f = j;
                iAddAndGet = cVar.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        public int a(f<T> fVar) {
            int i = 0;
            while (i != Integer.MAX_VALUE && (fVar = fVar.get()) != null) {
                i++;
            }
            return i;
        }
    }

    public T[] getValues(T[] tArr) {
        return this.b.a((Object[]) tArr);
    }

    public static final class e<T> implements b<T> {
        public final int a;
        public int b;
        public volatile a<T> c;
        public a<T> d;
        public Throwable e;
        public volatile boolean f;

        public e(int i) {
            this.a = ObjectHelper.verifyPositive(i, "maxSize");
            a<T> aVar = new a<>(null);
            this.d = aVar;
            this.c = aVar;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(T t) {
            a<T> aVar = new a<>(t);
            a<T> aVar2 = this.d;
            this.d = aVar;
            this.b++;
            aVar2.set(aVar);
            int i = this.b;
            if (i > this.a) {
                this.b = i - 1;
                this.c = this.c.get();
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public Throwable b() {
            return this.e;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void complete() {
            a();
            this.f = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public T getValue() {
            a<T> aVar = this.c;
            while (true) {
                a<T> aVar2 = aVar.get();
                if (aVar2 == null) {
                    return aVar.a;
                }
                aVar = aVar2;
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public boolean isDone() {
            return this.f;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public int size() {
            a<T> aVar = this.c;
            int i = 0;
            while (i != Integer.MAX_VALUE && (aVar = aVar.get()) != null) {
                i++;
            }
            return i;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(Throwable th) {
            this.e = th;
            a();
            this.f = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a() {
            if (this.c.a != null) {
                a<T> aVar = new a<>(null);
                aVar.lazySet(this.c.get());
                this.c = aVar;
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public T[] a(T[] tArr) {
            a<T> aVar = this.c;
            a<T> aVar2 = aVar;
            int i = 0;
            while (true) {
                aVar2 = aVar2.get();
                if (aVar2 == null) {
                    break;
                }
                i++;
            }
            if (tArr.length < i) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i));
            }
            for (int i2 = 0; i2 < i; i2++) {
                aVar = aVar.get();
                tArr[i2] = aVar.a;
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.processors.ReplayProcessor.b
        public void a(c<T> cVar) {
            if (cVar.getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = cVar.a;
            a<T> aVar = (a) cVar.c;
            if (aVar == null) {
                aVar = this.c;
            }
            long j = cVar.f;
            int iAddAndGet = 1;
            do {
                long j2 = cVar.d.get();
                while (j != j2) {
                    if (cVar.e) {
                        cVar.c = null;
                        return;
                    }
                    boolean z = this.f;
                    a<T> aVar2 = aVar.get();
                    boolean z2 = aVar2 == null;
                    if (z && z2) {
                        cVar.c = null;
                        cVar.e = true;
                        Throwable th = this.e;
                        if (th == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th);
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(aVar2.a);
                    j++;
                    aVar = aVar2;
                }
                if (j == j2) {
                    if (cVar.e) {
                        cVar.c = null;
                        return;
                    }
                    if (this.f && aVar.get() == null) {
                        cVar.c = null;
                        cVar.e = true;
                        Throwable th2 = this.e;
                        if (th2 == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(th2);
                            return;
                        }
                    }
                }
                cVar.c = aVar;
                cVar.f = j;
                iAddAndGet = cVar.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }
    }
}
