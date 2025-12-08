package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ReplaySubject<T> extends Subject<T> {
    public static final c[] d = new c[0];
    public static final c[] e = new c[0];
    public static final Object[] f = new Object[0];
    public final b<T> a;
    public final AtomicReference<c<T>[]> b = new AtomicReference<>(d);
    public boolean c;

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

        void a(Object obj);

        T[] a(T[] tArr);

        void add(T t);

        @Nullable
        T getValue();

        int size();
    }

    public static final class c<T> extends AtomicInteger implements Disposable {
        public static final long serialVersionUID = 466549804534799122L;
        public final Observer<? super T> a;
        public final ReplaySubject<T> b;
        public Object c;
        public volatile boolean d;

        public c(Observer<? super T> observer, ReplaySubject<T> replaySubject) {
            this.a = observer;
            this.b = replaySubject;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.d) {
                return;
            }
            this.d = true;
            this.b.a((c) this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d;
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

    public ReplaySubject(b<T> bVar) {
        this.a = bVar;
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> create() {
        return new ReplaySubject<>(new g(16));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> createWithSize(int i) {
        return new ReplaySubject<>(new e(i));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return new ReplaySubject<>(new d(Integer.MAX_VALUE, j, timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> createWithTimeAndSize(long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        return new ReplaySubject<>(new d(i, j, timeUnit, scheduler));
    }

    public void a(c<T> cVar) {
        c<T>[] cVarArr;
        c<T>[] cVarArr2;
        do {
            cVarArr = this.b.get();
            if (cVarArr == e || cVarArr == d) {
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
                cVarArr2 = d;
            } else {
                c<T>[] cVarArr3 = new c[length - 1];
                System.arraycopy(cVarArr, 0, cVarArr3, 0, i);
                System.arraycopy(cVarArr, i + 1, cVarArr3, i, (length - i) - 1);
                cVarArr2 = cVarArr3;
            }
        } while (!this.b.compareAndSet(cVarArr, cVarArr2));
    }

    public void cleanupBuffer() {
        this.a.a();
    }

    @Override // io.reactivex.subjects.Subject
    @Nullable
    public Throwable getThrowable() {
        Object obj = ((AtomicReference) this.a).get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    @Nullable
    public T getValue() {
        return this.a.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object[] getValues() {
        Object[] values = getValues(f);
        return values == f ? new Object[0] : values;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasComplete() {
        return NotificationLite.isComplete(((AtomicReference) this.a).get());
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasObservers() {
        return this.b.get().length != 0;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasThrowable() {
        return NotificationLite.isError(((AtomicReference) this.a).get());
    }

    public boolean hasValue() {
        return this.a.size() != 0;
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        if (this.c) {
            return;
        }
        this.c = true;
        Object objComplete = NotificationLite.complete();
        b<T> bVar = this.a;
        bVar.a(objComplete);
        for (c<T> cVar : a(objComplete)) {
            bVar.a((c) cVar);
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.c) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.c = true;
        Object objError = NotificationLite.error(th);
        b<T> bVar = this.a;
        bVar.a(objError);
        for (c<T> cVar : a(objError)) {
            bVar.a((c) cVar);
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.c) {
            return;
        }
        b<T> bVar = this.a;
        bVar.add(t);
        for (c<T> cVar : this.b.get()) {
            bVar.a((c) cVar);
        }
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.c) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        boolean z;
        c<T> cVar = new c<>(observer, this);
        observer.onSubscribe(cVar);
        if (cVar.d) {
            return;
        }
        while (true) {
            c<T>[] cVarArr = this.b.get();
            z = false;
            if (cVarArr == e) {
                break;
            }
            int length = cVarArr.length;
            c<T>[] cVarArr2 = new c[length + 1];
            System.arraycopy(cVarArr, 0, cVarArr2, 0, length);
            cVarArr2[length] = cVar;
            if (this.b.compareAndSet(cVarArr, cVarArr2)) {
                z = true;
                break;
            }
        }
        if (z && cVar.d) {
            a((c) cVar);
        } else {
            this.a.a((c) cVar);
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> create(int i) {
        return new ReplaySubject<>(new g(i));
    }

    public static final class g<T> extends AtomicReference<Object> implements b<T> {
        public static final long serialVersionUID = -733876083048047795L;
        public final List<Object> a;
        public volatile boolean b;
        public volatile int c;

        public g(int i) {
            this.a = new ArrayList(ObjectHelper.verifyPositive(i, "capacityHint"));
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a() {
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(Object obj) {
            this.a.add(obj);
            this.c++;
            this.b = true;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void add(T t) {
            this.a.add(t);
            this.c++;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        @Nullable
        public T getValue() {
            int i = this.c;
            if (i == 0) {
                return null;
            }
            List<Object> list = this.a;
            T t = (T) list.get(i - 1);
            if (!NotificationLite.isComplete(t) && !NotificationLite.isError(t)) {
                return t;
            }
            if (i == 1) {
                return null;
            }
            return (T) list.get(i - 2);
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public int size() {
            int i = this.c;
            if (i == 0) {
                return 0;
            }
            int i2 = i - 1;
            Object obj = this.a.get(i2);
            return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i2 : i;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public T[] a(T[] tArr) {
            int i = this.c;
            if (i == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            List<Object> list = this.a;
            Object obj = list.get(i - 1);
            if ((NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) && i - 1 == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
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

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(c<T> cVar) {
            int i;
            if (cVar.getAndIncrement() != 0) {
                return;
            }
            List<Object> list = this.a;
            Observer<? super T> observer = cVar.a;
            Integer num = (Integer) cVar.c;
            int iIntValue = 0;
            if (num != null) {
                iIntValue = num.intValue();
            } else {
                cVar.c = 0;
            }
            int iAddAndGet = 1;
            while (!cVar.d) {
                int i2 = this.c;
                while (i2 != iIntValue) {
                    if (cVar.d) {
                        cVar.c = null;
                        return;
                    }
                    Object obj = list.get(iIntValue);
                    if (this.b && (i = iIntValue + 1) == i2 && i == (i2 = this.c)) {
                        if (NotificationLite.isComplete(obj)) {
                            observer.onComplete();
                        } else {
                            observer.onError(NotificationLite.getError(obj));
                        }
                        cVar.c = null;
                        cVar.d = true;
                        return;
                    }
                    observer.onNext(obj);
                    iIntValue++;
                }
                if (iIntValue == this.c) {
                    cVar.c = Integer.valueOf(iIntValue);
                    iAddAndGet = cVar.addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            cVar.c = null;
        }
    }

    public T[] getValues(T[] tArr) {
        return this.a.a((Object[]) tArr);
    }

    public static final class e<T> extends AtomicReference<Object> implements b<T> {
        public static final long serialVersionUID = 1107649250281456395L;
        public final int a;
        public int b;
        public volatile a<Object> c;
        public a<Object> d;
        public volatile boolean e;

        public e(int i) {
            this.a = ObjectHelper.verifyPositive(i, "maxSize");
            a<Object> aVar = new a<>(null);
            this.d = aVar;
            this.c = aVar;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(Object obj) {
            a<Object> aVar = new a<>(obj);
            a<Object> aVar2 = this.d;
            this.d = aVar;
            this.b++;
            aVar2.lazySet(aVar);
            a();
            this.e = true;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void add(T t) {
            a<Object> aVar = new a<>(t);
            a<Object> aVar2 = this.d;
            this.d = aVar;
            this.b++;
            aVar2.set(aVar);
            int i = this.b;
            if (i > this.a) {
                this.b = i - 1;
                this.c = this.c.get();
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        @Nullable
        public T getValue() {
            a<Object> aVar = this.c;
            a<Object> aVar2 = null;
            while (true) {
                a<T> aVar3 = aVar.get();
                if (aVar3 == null) {
                    break;
                }
                aVar2 = aVar;
                aVar = aVar3;
            }
            T t = (T) aVar.a;
            if (t == null) {
                return null;
            }
            return (NotificationLite.isComplete(t) || NotificationLite.isError(t)) ? (T) aVar2.a : t;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public int size() {
            a<Object> aVar = this.c;
            int i = 0;
            while (i != Integer.MAX_VALUE) {
                a<T> aVar2 = aVar.get();
                if (aVar2 == null) {
                    Object obj = aVar.a;
                    return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i - 1 : i;
                }
                i++;
                aVar = aVar2;
            }
            return i;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a() {
            a<Object> aVar = this.c;
            if (aVar.a != null) {
                a<Object> aVar2 = new a<>(null);
                aVar2.lazySet(aVar.get());
                this.c = aVar2;
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public T[] a(T[] tArr) {
            a<T> aVar = this.c;
            int size = size();
            if (size == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
            } else {
                if (tArr.length < size) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
                }
                for (int i = 0; i != size; i++) {
                    aVar = aVar.get();
                    tArr[i] = aVar.a;
                }
                if (tArr.length > size) {
                    tArr[size] = null;
                }
            }
            return tArr;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(c<T> cVar) {
            if (cVar.getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = cVar.a;
            a<Object> aVar = (a) cVar.c;
            if (aVar == null) {
                aVar = this.c;
            }
            int iAddAndGet = 1;
            while (!cVar.d) {
                a<T> aVar2 = aVar.get();
                if (aVar2 == null) {
                    if (aVar.get() != null) {
                        continue;
                    } else {
                        cVar.c = aVar;
                        iAddAndGet = cVar.addAndGet(-iAddAndGet);
                        if (iAddAndGet == 0) {
                            return;
                        }
                    }
                } else {
                    T t = aVar2.a;
                    if (this.e && aVar2.get() == null) {
                        if (NotificationLite.isComplete(t)) {
                            observer.onComplete();
                        } else {
                            observer.onError(NotificationLite.getError(t));
                        }
                        cVar.c = null;
                        cVar.d = true;
                        return;
                    }
                    observer.onNext(t);
                    aVar = aVar2;
                }
            }
            cVar.c = null;
        }
    }

    public c<T>[] a(Object obj) {
        if (((AtomicReference) this.a).compareAndSet(null, obj)) {
            return this.b.getAndSet(e);
        }
        return e;
    }

    public static final class d<T> extends AtomicReference<Object> implements b<T> {
        public static final long serialVersionUID = -8056260896137901749L;
        public final int a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;
        public int e;
        public volatile f<Object> f;
        public f<Object> g;
        public volatile boolean h;

        public d(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = ObjectHelper.verifyPositive(i, "maxSize");
            this.b = ObjectHelper.verifyPositive(j, "maxAge");
            this.c = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
            this.d = (Scheduler) ObjectHelper.requireNonNull(scheduler, "scheduler is null");
            f<Object> fVar = new f<>(null, 0L);
            this.g = fVar;
            this.f = fVar;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(Object obj) {
            f<Object> fVar = new f<>(obj, Long.MAX_VALUE);
            f<Object> fVar2 = this.g;
            this.g = fVar;
            this.e++;
            fVar2.lazySet(fVar);
            long jNow = this.d.now(this.c) - this.b;
            f<Object> fVar3 = this.f;
            while (true) {
                f<T> fVar4 = fVar3.get();
                if (fVar4.get() == null) {
                    if (fVar3.a != null) {
                        f<Object> fVar5 = new f<>(null, 0L);
                        fVar5.lazySet(fVar3.get());
                        this.f = fVar5;
                    } else {
                        this.f = fVar3;
                    }
                } else if (fVar4.b <= jNow) {
                    fVar3 = fVar4;
                } else if (fVar3.a != null) {
                    f<Object> fVar6 = new f<>(null, 0L);
                    fVar6.lazySet(fVar3.get());
                    this.f = fVar6;
                } else {
                    this.f = fVar3;
                }
            }
            this.h = true;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void add(T t) {
            f<Object> fVar = new f<>(t, this.d.now(this.c));
            f<Object> fVar2 = this.g;
            this.g = fVar;
            this.e++;
            fVar2.set(fVar);
            int i = this.e;
            if (i > this.a) {
                this.e = i - 1;
                this.f = this.f.get();
            }
            long jNow = this.d.now(this.c) - this.b;
            f<Object> fVar3 = this.f;
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

        public f<Object> b() {
            f<Object> fVar;
            f<Object> fVar2 = this.f;
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

        @Override // io.reactivex.subjects.ReplaySubject.b
        @Nullable
        public T getValue() {
            T t;
            f<Object> fVar = this.f;
            f<Object> fVar2 = null;
            while (true) {
                f<T> fVar3 = fVar.get();
                if (fVar3 == null) {
                    break;
                }
                fVar2 = fVar;
                fVar = fVar3;
            }
            if (fVar.b >= this.d.now(this.c) - this.b && (t = (T) fVar.a) != null) {
                return (NotificationLite.isComplete(t) || NotificationLite.isError(t)) ? (T) fVar2.a : t;
            }
            return null;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public int size() {
            return a(b());
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a() {
            f<Object> fVar = this.f;
            if (fVar.a != null) {
                f<Object> fVar2 = new f<>(null, 0L);
                fVar2.lazySet(fVar.get());
                this.f = fVar2;
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public T[] a(T[] tArr) {
            f<T> fVarB = b();
            int iA = a(fVarB);
            if (iA == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
            } else {
                if (tArr.length < iA) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), iA));
                }
                for (int i = 0; i != iA; i++) {
                    fVarB = fVarB.get();
                    tArr[i] = fVarB.a;
                }
                if (tArr.length > iA) {
                    tArr[iA] = null;
                }
            }
            return tArr;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(c<T> cVar) {
            if (cVar.getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = cVar.a;
            f<Object> fVarB = (f) cVar.c;
            if (fVarB == null) {
                fVarB = b();
            }
            int iAddAndGet = 1;
            while (!cVar.d) {
                while (!cVar.d) {
                    f<T> fVar = fVarB.get();
                    if (fVar == null) {
                        if (fVarB.get() == null) {
                            cVar.c = fVarB;
                            iAddAndGet = cVar.addAndGet(-iAddAndGet);
                            if (iAddAndGet == 0) {
                                return;
                            }
                        }
                    } else {
                        T t = fVar.a;
                        if (this.h && fVar.get() == null) {
                            if (NotificationLite.isComplete(t)) {
                                observer.onComplete();
                            } else {
                                observer.onError(NotificationLite.getError(t));
                            }
                            cVar.c = null;
                            cVar.d = true;
                            return;
                        }
                        observer.onNext(t);
                        fVarB = fVar;
                    }
                }
                cVar.c = null;
                return;
            }
            cVar.c = null;
        }

        public int a(f<Object> fVar) {
            int i = 0;
            while (i != Integer.MAX_VALUE) {
                f<T> fVar2 = fVar.get();
                if (fVar2 == null) {
                    Object obj = fVar.a;
                    return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i - 1 : i;
                }
                i++;
                fVar = fVar2;
            }
            return i;
        }
    }
}
