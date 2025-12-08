package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.subjects.UnicastSubject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class ObservableWindowTimed<T> extends bl<T, Observable<T>> {
    public final long a;
    public final long b;
    public final TimeUnit c;
    public final Scheduler d;
    public final long e;
    public final int f;
    public final boolean g;

    public static final class a<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;
        public final int e;
        public final boolean f;
        public final long g;
        public final Scheduler.Worker h;
        public long i;
        public long j;
        public Disposable k;
        public UnicastSubject<T> l;
        public volatile boolean m;
        public final SequentialDisposable n;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableWindowTimed$a$a, reason: collision with other inner class name */
        public static final class RunnableC0069a implements Runnable {
            public final long a;
            public final a<?> b;

            public RunnableC0069a(long j, a<?> aVar) {
                this.a = j;
                this.b = aVar;
            }

            @Override // java.lang.Runnable
            public void run() {
                a<?> aVar = this.b;
                if (aVar.cancelled) {
                    aVar.m = true;
                } else {
                    aVar.queue.offer(this);
                }
                if (aVar.enter()) {
                    aVar.a();
                }
            }
        }

        public a(Observer<? super Observable<T>> observer, long j, TimeUnit timeUnit, Scheduler scheduler, int i, long j2, boolean z) {
            super(observer, new MpscLinkedQueue());
            this.n = new SequentialDisposable();
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = i;
            this.g = j2;
            this.f = z;
            if (z) {
                this.h = scheduler.createWorker();
            } else {
                this.h = null;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0, types: [io.reactivex.subjects.UnicastSubject<T>] */
        public void a() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer<? super V> observer = this.downstream;
            UnicastSubject<T> unicastSubject = this.l;
            int iLeave = 1;
            while (!this.m) {
                boolean z = this.done;
                Object objPoll = mpscLinkedQueue.poll();
                boolean z2 = objPoll == null;
                boolean z3 = objPoll instanceof RunnableC0069a;
                if (z && (z2 || z3)) {
                    this.l = null;
                    mpscLinkedQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        unicastSubject.onError(th);
                    } else {
                        unicastSubject.onComplete();
                    }
                    DisposableHelper.dispose(this.n);
                    Scheduler.Worker worker = this.h;
                    if (worker != null) {
                        worker.dispose();
                        return;
                    }
                    return;
                }
                if (z2) {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                } else if (z3) {
                    RunnableC0069a runnableC0069a = (RunnableC0069a) objPoll;
                    if (!this.f || this.j == runnableC0069a.a) {
                        unicastSubject.onComplete();
                        this.i = 0L;
                        unicastSubject = (UnicastSubject<T>) UnicastSubject.create(this.e);
                        this.l = unicastSubject;
                        observer.onNext(unicastSubject);
                    }
                } else {
                    unicastSubject.onNext(NotificationLite.getValue(objPoll));
                    long j = this.i + 1;
                    if (j >= this.g) {
                        this.j++;
                        this.i = 0L;
                        unicastSubject.onComplete();
                        unicastSubject = (UnicastSubject<T>) UnicastSubject.create(this.e);
                        this.l = unicastSubject;
                        this.downstream.onNext(unicastSubject);
                        if (this.f) {
                            Disposable disposable = this.n.get();
                            disposable.dispose();
                            Scheduler.Worker worker2 = this.h;
                            RunnableC0069a runnableC0069a2 = new RunnableC0069a(this.j, this);
                            long j2 = this.b;
                            Disposable disposableSchedulePeriodically = worker2.schedulePeriodically(runnableC0069a2, j2, j2, this.c);
                            if (!this.n.compareAndSet(disposable, disposableSchedulePeriodically)) {
                                disposableSchedulePeriodically.dispose();
                            }
                        }
                    } else {
                        this.i = j;
                    }
                }
            }
            this.k.dispose();
            mpscLinkedQueue.clear();
            DisposableHelper.dispose(this.n);
            Scheduler.Worker worker3 = this.h;
            if (worker3 != null) {
                worker3.dispose();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.m) {
                return;
            }
            if (fastEnter()) {
                UnicastSubject<T> unicastSubject = this.l;
                unicastSubject.onNext(t);
                long j = this.i + 1;
                if (j >= this.g) {
                    this.j++;
                    this.i = 0L;
                    unicastSubject.onComplete();
                    UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.e);
                    this.l = unicastSubjectCreate;
                    this.downstream.onNext(unicastSubjectCreate);
                    if (this.f) {
                        this.n.get().dispose();
                        Scheduler.Worker worker = this.h;
                        RunnableC0069a runnableC0069a = new RunnableC0069a(this.j, this);
                        long j2 = this.b;
                        DisposableHelper.replace(this.n, worker.schedulePeriodically(runnableC0069a, j2, j2, this.c));
                    }
                } else {
                    this.i = j;
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
            }
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            Disposable disposableSchedulePeriodicallyDirect;
            if (DisposableHelper.validate(this.k, disposable)) {
                this.k = disposable;
                Observer<? super V> observer = this.downstream;
                observer.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.e);
                this.l = unicastSubjectCreate;
                observer.onNext(unicastSubjectCreate);
                RunnableC0069a runnableC0069a = new RunnableC0069a(this.j, this);
                if (this.f) {
                    Scheduler.Worker worker = this.h;
                    long j = this.b;
                    disposableSchedulePeriodicallyDirect = worker.schedulePeriodically(runnableC0069a, j, j, this.c);
                } else {
                    Scheduler scheduler = this.d;
                    long j2 = this.b;
                    disposableSchedulePeriodicallyDirect = scheduler.schedulePeriodicallyDirect(runnableC0069a, j2, j2, this.c);
                }
                this.n.replace(disposableSchedulePeriodicallyDirect);
            }
        }
    }

    public static final class b<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Observer<T>, Disposable, Runnable {
        public static final Object j = new Object();
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;
        public final int e;
        public Disposable f;
        public UnicastSubject<T> g;
        public final SequentialDisposable h;
        public volatile boolean i;

        public b(Observer<? super Observable<T>> observer, long j2, TimeUnit timeUnit, Scheduler scheduler, int i) {
            super(observer, new MpscLinkedQueue());
            this.h = new SequentialDisposable();
            this.b = j2;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0023, code lost:
        
            r2.onError(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
        
            r2.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x002a, code lost:
        
            r7.h.dispose();
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x002f, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x0019, code lost:
        
            r7.g = null;
            r0.clear();
            r0 = r7.error;
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0021, code lost:
        
            if (r0 == null) goto L11;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0, types: [io.reactivex.subjects.UnicastSubject<T>] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a() {
            /*
                r7 = this;
                io.reactivex.internal.fuseable.SimplePlainQueue<U> r0 = r7.queue
                io.reactivex.internal.queue.MpscLinkedQueue r0 = (io.reactivex.internal.queue.MpscLinkedQueue) r0
                io.reactivex.Observer<? super V> r1 = r7.downstream
                io.reactivex.subjects.UnicastSubject<T> r2 = r7.g
                r3 = 1
            L9:
                boolean r4 = r7.i
                boolean r5 = r7.done
                java.lang.Object r6 = r0.poll()
                if (r5 == 0) goto L30
                if (r6 == 0) goto L19
                java.lang.Object r5 = io.reactivex.internal.operators.observable.ObservableWindowTimed.b.j
                if (r6 != r5) goto L30
            L19:
                r1 = 0
                r7.g = r1
                r0.clear()
                java.lang.Throwable r0 = r7.error
                if (r0 == 0) goto L27
                r2.onError(r0)
                goto L2a
            L27:
                r2.onComplete()
            L2a:
                io.reactivex.internal.disposables.SequentialDisposable r0 = r7.h
                r0.dispose()
                return
            L30:
                if (r6 != 0) goto L3a
                int r3 = -r3
                int r3 = r7.leave(r3)
                if (r3 != 0) goto L9
                return
            L3a:
                java.lang.Object r5 = io.reactivex.internal.operators.observable.ObservableWindowTimed.b.j
                if (r6 != r5) goto L55
                r2.onComplete()
                if (r4 != 0) goto L4f
                int r2 = r7.e
                io.reactivex.subjects.UnicastSubject r2 = io.reactivex.subjects.UnicastSubject.create(r2)
                r7.g = r2
                r1.onNext(r2)
                goto L9
            L4f:
                io.reactivex.disposables.Disposable r4 = r7.f
                r4.dispose()
                goto L9
            L55:
                java.lang.Object r4 = io.reactivex.internal.util.NotificationLite.getValue(r6)
                r2.onNext(r4)
                goto L9
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableWindowTimed.b.a():void");
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.i) {
                return;
            }
            if (fastEnter()) {
                this.g.onNext(t);
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
            }
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.g = UnicastSubject.create(this.e);
                Observer<? super V> observer = this.downstream;
                observer.onSubscribe(this);
                observer.onNext(this.g);
                if (this.cancelled) {
                    return;
                }
                Scheduler scheduler = this.d;
                long j2 = this.b;
                this.h.replace(scheduler.schedulePeriodicallyDirect(this, j2, j2, this.c));
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                this.i = true;
            }
            this.queue.offer(j);
            if (enter()) {
                a();
            }
        }
    }

    public static final class c<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable, Runnable {
        public final long b;
        public final long c;
        public final TimeUnit d;
        public final Scheduler.Worker e;
        public final int f;
        public final List<UnicastSubject<T>> g;
        public Disposable h;
        public volatile boolean i;

        public final class a implements Runnable {
            public final UnicastSubject<T> a;

            public a(UnicastSubject<T> unicastSubject) {
                this.a = unicastSubject;
            }

            @Override // java.lang.Runnable
            public void run() {
                c cVar = c.this;
                cVar.queue.offer(new b(this.a, false));
                if (cVar.enter()) {
                    cVar.a();
                }
            }
        }

        public static final class b<T> {
            public final UnicastSubject<T> a;
            public final boolean b;

            public b(UnicastSubject<T> unicastSubject, boolean z) {
                this.a = unicastSubject;
                this.b = z;
            }
        }

        public c(Observer<? super Observable<T>> observer, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker, int i) {
            super(observer, new MpscLinkedQueue());
            this.b = j;
            this.c = j2;
            this.d = timeUnit;
            this.e = worker;
            this.f = i;
            this.g = new LinkedList();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer<? super V> observer = this.downstream;
            List<UnicastSubject<T>> list = this.g;
            int iLeave = 1;
            while (!this.i) {
                boolean z = this.done;
                Object objPoll = mpscLinkedQueue.poll();
                boolean z2 = objPoll == null;
                boolean z3 = objPoll instanceof b;
                if (z && (z2 || z3)) {
                    mpscLinkedQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        Iterator<UnicastSubject<T>> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().onError(th);
                        }
                    } else {
                        Iterator<UnicastSubject<T>> it2 = list.iterator();
                        while (it2.hasNext()) {
                            it2.next().onComplete();
                        }
                    }
                    list.clear();
                    this.e.dispose();
                    return;
                }
                if (z2) {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                } else if (z3) {
                    b bVar = (b) objPoll;
                    if (!bVar.b) {
                        list.remove(bVar.a);
                        bVar.a.onComplete();
                        if (list.isEmpty() && this.cancelled) {
                            this.i = true;
                        }
                    } else if (!this.cancelled) {
                        UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.f);
                        list.add(unicastSubjectCreate);
                        observer.onNext(unicastSubjectCreate);
                        this.e.schedule(new a(unicastSubjectCreate), this.b, this.d);
                    }
                } else {
                    Iterator<UnicastSubject<T>> it3 = list.iterator();
                    while (it3.hasNext()) {
                        it3.next().onNext(objPoll);
                    }
                }
            }
            this.h.dispose();
            mpscLinkedQueue.clear();
            list.clear();
            this.e.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (fastEnter()) {
                Iterator<UnicastSubject<T>> it = this.g.iterator();
                while (it.hasNext()) {
                    it.next().onNext(t);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(t);
                if (!enter()) {
                    return;
                }
            }
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.downstream.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.f);
                this.g.add(unicastSubjectCreate);
                this.downstream.onNext(unicastSubjectCreate);
                this.e.schedule(new a(unicastSubjectCreate), this.b, this.d);
                Scheduler.Worker worker = this.e;
                long j = this.c;
                worker.schedulePeriodically(this, j, j, this.d);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            b bVar = new b(UnicastSubject.create(this.f), true);
            if (!this.cancelled) {
                this.queue.offer(bVar);
            }
            if (enter()) {
                a();
            }
        }
    }

    public ObservableWindowTimed(ObservableSource<T> observableSource, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, long j3, int i, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = j2;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = j3;
        this.f = i;
        this.g = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Observable<T>> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        long j = this.a;
        long j2 = this.b;
        if (j != j2) {
            this.source.subscribe(new c(serializedObserver, j, j2, this.c, this.d.createWorker(), this.f));
            return;
        }
        long j3 = this.e;
        if (j3 == Long.MAX_VALUE) {
            this.source.subscribe(new b(serializedObserver, this.a, this.c, this.d, this.f));
        } else {
            this.source.subscribe(new a(serializedObserver, j, this.c, this.d, this.f, j3, this.g));
        }
    }
}
