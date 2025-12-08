package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class FlowableReplay<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T>, ResettableConnectable {
    public static final Callable f = new c();
    public final Flowable<T> b;
    public final AtomicReference<j<T>> c;
    public final Callable<? extends g<T>> d;
    public final Publisher<T> e;

    public static final class b<T> extends ConnectableFlowable<T> {
        public final ConnectableFlowable<T> b;
        public final Flowable<T> c;

        public b(ConnectableFlowable<T> connectableFlowable, Flowable<T> flowable) {
            this.b = connectableFlowable;
            this.c = flowable;
        }

        @Override // io.reactivex.flowables.ConnectableFlowable
        public void connect(Consumer<? super Disposable> consumer) {
            this.b.connect(consumer);
        }

        @Override // io.reactivex.Flowable
        public void subscribeActual(Subscriber<? super T> subscriber) {
            this.c.subscribe(subscriber);
        }
    }

    public static final class c implements Callable<Object> {
        @Override // java.util.concurrent.Callable
        public Object call() {
            return new n(16);
        }
    }

    public static final class d<T> extends AtomicLong implements Subscription, Disposable {
        public static final long serialVersionUID = -4453897557930727610L;
        public final j<T> a;
        public final Subscriber<? super T> b;
        public Object c;
        public final AtomicLong d = new AtomicLong();
        public boolean e;
        public boolean f;

        public d(j<T> jVar, Subscriber<? super T> subscriber) {
            this.a = jVar;
            this.b = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.a.a(this);
                this.a.a();
                this.c = null;
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == Long.MIN_VALUE;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (!SubscriptionHelper.validate(j) || BackpressureHelper.addCancel(this, j) == Long.MIN_VALUE) {
                return;
            }
            BackpressureHelper.add(this.d, j);
            this.a.a();
            this.a.a.a((d) this);
        }
    }

    public static final class e<R, U> extends Flowable<R> {
        public final Callable<? extends ConnectableFlowable<U>> b;
        public final Function<? super Flowable<U>, ? extends Publisher<R>> c;

        public final class a implements Consumer<Disposable> {
            public final SubscriberResourceWrapper<R> a;

            public a(e eVar, SubscriberResourceWrapper<R> subscriberResourceWrapper) {
                this.a = subscriberResourceWrapper;
            }

            @Override // io.reactivex.functions.Consumer
            public void accept(Disposable disposable) throws Exception {
                this.a.setResource(disposable);
            }
        }

        public e(Callable<? extends ConnectableFlowable<U>> callable, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
            this.b = callable;
            this.c = function;
        }

        @Override // io.reactivex.Flowable
        public void subscribeActual(Subscriber<? super R> subscriber) {
            try {
                ConnectableFlowable connectableFlowable = (ConnectableFlowable) ObjectHelper.requireNonNull(this.b.call(), "The connectableFactory returned null");
                try {
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.c.apply(connectableFlowable), "The selector returned a null Publisher");
                    SubscriberResourceWrapper subscriberResourceWrapper = new SubscriberResourceWrapper(subscriber);
                    publisher.subscribe(subscriberResourceWrapper);
                    connectableFlowable.connect(new a(this, subscriberResourceWrapper));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
            }
        }
    }

    public static final class f extends AtomicReference<f> {
        public static final long serialVersionUID = 245354315435971818L;
        public final Object a;
        public final long b;

        public f(Object obj, long j) {
            this.a = obj;
            this.b = j;
        }
    }

    public interface g<T> {
        void a(d<T> dVar);

        void a(T t);

        void a(Throwable th);

        void complete();
    }

    public static final class h<T> implements Callable<g<T>> {
        public final int a;

        public h(int i) {
            this.a = i;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return new m(this.a);
        }
    }

    public static final class i<T> implements Publisher<T> {
        public final AtomicReference<j<T>> a;
        public final Callable<? extends g<T>> b;

        public i(AtomicReference<j<T>> atomicReference, Callable<? extends g<T>> callable) {
            this.a = atomicReference;
            this.b = callable;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            j<T> jVar;
            d<T>[] dVarArr;
            d<T>[] dVarArr2;
            while (true) {
                jVar = this.a.get();
                if (jVar != null) {
                    break;
                }
                try {
                    j<T> jVar2 = new j<>(this.b.call());
                    if (this.a.compareAndSet(null, jVar2)) {
                        jVar = jVar2;
                        break;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                    return;
                }
            }
            d<T> dVar = new d<>(jVar, subscriber);
            subscriber.onSubscribe(dVar);
            do {
                dVarArr = jVar.c.get();
                if (dVarArr == j.i) {
                    break;
                }
                int length = dVarArr.length;
                dVarArr2 = new d[length + 1];
                System.arraycopy(dVarArr, 0, dVarArr2, 0, length);
                dVarArr2[length] = dVar;
            } while (!jVar.c.compareAndSet(dVarArr, dVarArr2));
            if (dVar.isDisposed()) {
                jVar.a(dVar);
            } else {
                jVar.a();
                jVar.a.a((d) dVar);
            }
        }
    }

    public static final class k<T> implements Callable<g<T>> {
        public final int a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;

        public k(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = i;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return new l(this.a, this.b, this.c, this.d);
        }
    }

    public static final class l<T> extends a<T> {
        public static final long serialVersionUID = 3457957419649567404L;
        public final Scheduler d;
        public final long e;
        public final TimeUnit f;
        public final int g;

        public l(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.d = scheduler;
            this.g = i;
            this.e = j;
            this.f = timeUnit;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        public f a() {
            f fVar;
            long jNow = this.d.now(this.f) - this.e;
            f fVar2 = get();
            f fVar3 = fVar2.get();
            while (true) {
                f fVar4 = fVar3;
                fVar = fVar2;
                fVar2 = fVar4;
                if (fVar2 == null) {
                    break;
                }
                Timed timed = (Timed) fVar2.a;
                if (NotificationLite.isComplete(timed.value()) || NotificationLite.isError(timed.value()) || timed.time() > jNow) {
                    break;
                }
                fVar3 = fVar2.get();
            }
            return fVar;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        public Object b(Object obj) {
            return new Timed(obj, this.d.now(this.f), this.f);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        public Object c(Object obj) {
            return ((Timed) obj).value();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        public void b() {
            f fVar;
            long jNow = this.d.now(this.f) - this.e;
            f fVar2 = get();
            f fVar3 = fVar2.get();
            int i = 0;
            while (true) {
                f fVar4 = fVar3;
                fVar = fVar2;
                fVar2 = fVar4;
                if (fVar2 == null) {
                    break;
                }
                int i2 = this.b;
                if (i2 > this.g && i2 > 1) {
                    i++;
                    this.b = i2 - 1;
                    fVar3 = fVar2.get();
                } else {
                    if (((Timed) fVar2.a).time() > jNow) {
                        break;
                    }
                    i++;
                    this.b--;
                    fVar3 = fVar2.get();
                }
            }
            if (i != 0) {
                set(fVar);
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        public void c() {
            f fVar;
            long jNow = this.d.now(this.f) - this.e;
            f fVar2 = get();
            f fVar3 = fVar2.get();
            int i = 0;
            while (true) {
                f fVar4 = fVar3;
                fVar = fVar2;
                fVar2 = fVar4;
                if (fVar2 == null || this.b <= 1 || ((Timed) fVar2.a).time() > jNow) {
                    break;
                }
                i++;
                this.b--;
                fVar3 = fVar2.get();
            }
            if (i != 0) {
                set(fVar);
            }
        }
    }

    public static final class m<T> extends a<T> {
        public static final long serialVersionUID = -5898283885385201806L;
        public final int d;

        public m(int i) {
            this.d = i;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.a
        public void b() {
            if (this.b > this.d) {
                f fVar = get().get();
                if (fVar == null) {
                    throw new IllegalStateException("Empty list!");
                }
                this.b--;
                set(fVar);
            }
        }
    }

    public FlowableReplay(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<j<T>> atomicReference, Callable<? extends g<T>> callable) {
        this.e = publisher;
        this.b = flowable;
        this.c = atomicReference;
        this.d = callable;
    }

    public static <T> ConnectableFlowable<T> a(Flowable<T> flowable, Callable<? extends g<T>> callable) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new FlowableReplay(new i(atomicReference, callable), flowable, atomicReference, callable));
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i2) {
        return i2 == Integer.MAX_VALUE ? createFrom(flowable) : a(flowable, new h(i2));
    }

    public static <T> ConnectableFlowable<T> createFrom(Flowable<? extends T> flowable) {
        return a(flowable, f);
    }

    public static <U, R> Flowable<R> multicastSelector(Callable<? extends ConnectableFlowable<U>> callable, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
        return new e(callable, function);
    }

    public static <T> ConnectableFlowable<T> observeOn(ConnectableFlowable<T> connectableFlowable, Scheduler scheduler) {
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new b(connectableFlowable, connectableFlowable.observeOn(scheduler)));
    }

    @Override // io.reactivex.flowables.ConnectableFlowable
    public void connect(Consumer<? super Disposable> consumer) {
        j<T> jVar;
        while (true) {
            jVar = this.c.get();
            if (jVar != null && !jVar.isDisposed()) {
                break;
            }
            try {
                j<T> jVar2 = new j<>(this.d.call());
                if (this.c.compareAndSet(jVar, jVar2)) {
                    jVar = jVar2;
                    break;
                }
            } finally {
                Exceptions.throwIfFatal(th);
                RuntimeException runtimeExceptionWrapOrThrow = ExceptionHelper.wrapOrThrow(th);
            }
        }
        boolean z = !jVar.d.get() && jVar.d.compareAndSet(false, true);
        try {
            consumer.accept(jVar);
            if (z) {
                this.b.subscribe((FlowableSubscriber) jVar);
            }
        } catch (Throwable th) {
            if (z) {
                jVar.d.compareAndSet(true, false);
            }
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.internal.disposables.ResettableConnectable
    public void resetIf(Disposable disposable) {
        this.c.compareAndSet((j) disposable, null);
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamPublisher
    public Publisher<T> source() {
        return this.b;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.e.subscribe(subscriber);
    }

    public static final class n<T> extends ArrayList<Object> implements g<T> {
        public static final long serialVersionUID = 7063189396499112664L;
        public volatile int a;

        public n(int i) {
            super(i);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public void a(T t) {
            add(NotificationLite.next(t));
            this.a++;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public void complete() {
            add(NotificationLite.complete());
            this.a++;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public void a(Throwable th) {
            add(NotificationLite.error(th));
            this.a++;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public void a(d<T> dVar) {
            synchronized (dVar) {
                if (dVar.e) {
                    dVar.f = true;
                    return;
                }
                dVar.e = true;
                Subscriber<? super T> subscriber = dVar.b;
                while (!dVar.isDisposed()) {
                    int i = this.a;
                    Integer num = (Integer) dVar.c;
                    int iIntValue = num != null ? num.intValue() : 0;
                    long j = dVar.get();
                    long j2 = j;
                    long j3 = 0;
                    while (j2 != 0 && iIntValue < i) {
                        Object obj = get(iIntValue);
                        try {
                            if (NotificationLite.accept(obj, subscriber) || dVar.isDisposed()) {
                                return;
                            }
                            iIntValue++;
                            j2--;
                            j3++;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            dVar.dispose();
                            if (NotificationLite.isError(obj) || NotificationLite.isComplete(obj)) {
                                return;
                            }
                            subscriber.onError(th);
                            return;
                        }
                    }
                    if (j3 != 0) {
                        dVar.c = Integer.valueOf(iIntValue);
                        if (j != Long.MAX_VALUE) {
                            BackpressureHelper.producedCancel(dVar, j3);
                        }
                    }
                    synchronized (dVar) {
                        if (!dVar.f) {
                            dVar.e = false;
                            return;
                        }
                        dVar.f = false;
                    }
                }
            }
        }
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return create(flowable, j2, timeUnit, scheduler, Integer.MAX_VALUE);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2) {
        return a(flowable, new k(i2, j2, timeUnit, scheduler));
    }

    public static class a<T> extends AtomicReference<f> implements g<T> {
        public static final long serialVersionUID = 2346567790059478686L;
        public f a;
        public int b;
        public long c;

        public a() {
            f fVar = new f(null, 0L);
            this.a = fVar;
            set(fVar);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public final void a(T t) {
            Object objB = b(NotificationLite.next(t));
            long j = this.c + 1;
            this.c = j;
            f fVar = new f(objB, j);
            this.a.set(fVar);
            this.a = fVar;
            this.b++;
            b();
        }

        public Object b(Object obj) {
            return obj;
        }

        public void b() {
            throw null;
        }

        public Object c(Object obj) {
            return obj;
        }

        public void c() {
            f fVar = get();
            if (fVar.a != null) {
                f fVar2 = new f(null, 0L);
                fVar2.lazySet(fVar.get());
                set(fVar2);
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public final void complete() {
            Object objB = b(NotificationLite.complete());
            long j = this.c + 1;
            this.c = j;
            f fVar = new f(objB, j);
            this.a.set(fVar);
            this.a = fVar;
            this.b++;
            c();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public final void a(Throwable th) {
            Object objB = b(NotificationLite.error(th));
            long j = this.c + 1;
            this.c = j;
            f fVar = new f(objB, j);
            this.a.set(fVar);
            this.a = fVar;
            this.b++;
            c();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableReplay.g
        public final void a(d<T> dVar) {
            f fVar;
            synchronized (dVar) {
                if (dVar.e) {
                    dVar.f = true;
                    return;
                }
                dVar.e = true;
                while (!dVar.isDisposed()) {
                    long j = dVar.get();
                    boolean z = j == Long.MAX_VALUE;
                    f fVarA = (f) dVar.c;
                    if (fVarA == null) {
                        fVarA = a();
                        dVar.c = fVarA;
                        BackpressureHelper.add(dVar.d, fVarA.b);
                    }
                    long j2 = 0;
                    while (j != 0 && (fVar = fVarA.get()) != null) {
                        Object objC = c(fVar.a);
                        try {
                            if (NotificationLite.accept(objC, dVar.b)) {
                                dVar.c = null;
                                return;
                            }
                            j2++;
                            j--;
                            if (dVar.isDisposed()) {
                                dVar.c = null;
                                return;
                            }
                            fVarA = fVar;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            dVar.c = null;
                            dVar.dispose();
                            if (NotificationLite.isError(objC) || NotificationLite.isComplete(objC)) {
                                return;
                            }
                            dVar.b.onError(th);
                            return;
                        }
                    }
                    if (j2 != 0) {
                        dVar.c = fVarA;
                        if (!z) {
                            BackpressureHelper.producedCancel(dVar, j2);
                        }
                    }
                    synchronized (dVar) {
                        if (!dVar.f) {
                            dVar.e = false;
                            return;
                        }
                        dVar.f = false;
                    }
                }
                dVar.c = null;
            }
        }

        public f a() {
            return get();
        }
    }

    public static final class j<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable {
        public static final d[] h = new d[0];
        public static final d[] i = new d[0];
        public static final long serialVersionUID = 7224554242710036740L;
        public final g<T> a;
        public boolean b;
        public long f;
        public long g;
        public final AtomicInteger e = new AtomicInteger();
        public final AtomicReference<d<T>[]> c = new AtomicReference<>(h);
        public final AtomicBoolean d = new AtomicBoolean();

        public j(g<T> gVar) {
            this.a = gVar;
        }

        public void a(d<T> dVar) {
            d<T>[] dVarArr;
            d<T>[] dVarArr2;
            do {
                dVarArr = this.c.get();
                int length = dVarArr.length;
                if (length == 0) {
                    return;
                }
                int i2 = -1;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    }
                    if (dVarArr[i3].equals(dVar)) {
                        i2 = i3;
                        break;
                    }
                    i3++;
                }
                if (i2 < 0) {
                    return;
                }
                if (length == 1) {
                    dVarArr2 = h;
                } else {
                    d<T>[] dVarArr3 = new d[length - 1];
                    System.arraycopy(dVarArr, 0, dVarArr3, 0, i2);
                    System.arraycopy(dVarArr, i2 + 1, dVarArr3, i2, (length - i2) - 1);
                    dVarArr2 = dVarArr3;
                }
            } while (!this.c.compareAndSet(dVarArr, dVarArr2));
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.set(i);
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.get() == i;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.b) {
                return;
            }
            this.b = true;
            this.a.complete();
            for (d<T> dVar : this.c.getAndSet(i)) {
                this.a.a((d) dVar);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.a(th);
            for (d<T> dVar : this.c.getAndSet(i)) {
                this.a.a((d) dVar);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.b) {
                return;
            }
            this.a.a((g<T>) t);
            for (d<T> dVar : this.c.get()) {
                this.a.a((d) dVar);
            }
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                a();
                for (d<T> dVar : this.c.get()) {
                    this.a.a((d) dVar);
                }
            }
        }

        public void a() {
            if (this.e.getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            while (!isDisposed()) {
                d<T>[] dVarArr = this.c.get();
                long j = this.f;
                long jMax = j;
                for (d<T> dVar : dVarArr) {
                    jMax = Math.max(jMax, dVar.d.get());
                }
                long j2 = this.g;
                Subscription subscription = get();
                long j3 = jMax - j;
                if (j3 != 0) {
                    this.f = jMax;
                    if (subscription == null) {
                        long j4 = j2 + j3;
                        if (j4 < 0) {
                            j4 = Long.MAX_VALUE;
                        }
                        this.g = j4;
                    } else if (j2 != 0) {
                        this.g = 0L;
                        subscription.request(j2 + j3);
                    } else {
                        subscription.request(j3);
                    }
                } else if (j2 != 0 && subscription != null) {
                    this.g = 0L;
                    subscription.request(j2);
                }
                iAddAndGet = this.e.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }
    }
}
