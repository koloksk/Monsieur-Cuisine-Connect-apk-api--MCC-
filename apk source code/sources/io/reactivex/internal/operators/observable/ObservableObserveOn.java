package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes.dex */
public final class ObservableObserveOn<T> extends bl<T, T> {
    public final Scheduler a;
    public final boolean b;
    public final int c;

    public static final class a<T> extends BasicIntQueueDisposable<T> implements Observer<T>, Runnable {
        public static final long serialVersionUID = 6576896619930983584L;
        public final Observer<? super T> a;
        public final Scheduler.Worker b;
        public final boolean c;
        public final int d;
        public SimpleQueue<T> e;
        public Disposable f;
        public Throwable g;
        public volatile boolean h;
        public volatile boolean i;
        public int j;
        public boolean k;

        public a(Observer<? super T> observer, Scheduler.Worker worker, boolean z, int i) {
            this.a = observer;
            this.b = worker;
            this.c = z;
            this.d = i;
        }

        public boolean a(boolean z, boolean z2, Observer<? super T> observer) {
            if (this.i) {
                this.e.clear();
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable th = this.g;
            if (this.c) {
                if (!z2) {
                    return false;
                }
                this.i = true;
                if (th != null) {
                    observer.onError(th);
                } else {
                    observer.onComplete();
                }
                this.b.dispose();
                return true;
            }
            if (th != null) {
                this.i = true;
                this.e.clear();
                observer.onError(th);
                this.b.dispose();
                return true;
            }
            if (!z2) {
                return false;
            }
            this.i = true;
            observer.onComplete();
            this.b.dispose();
            return true;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.e.clear();
        }

        public void d() {
            if (getAndIncrement() == 0) {
                this.b.schedule(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.i) {
                return;
            }
            this.i = true;
            this.f.dispose();
            this.b.dispose();
            if (this.k || getAndIncrement() != 0) {
                return;
            }
            this.e.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.i;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.e.isEmpty();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.h) {
                return;
            }
            this.h = true;
            d();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = th;
            this.h = true;
            d();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.h) {
                return;
            }
            if (this.j != 2) {
                this.e.offer(t);
            }
            d();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int iRequestFusion = queueDisposable.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.j = iRequestFusion;
                        this.e = queueDisposable;
                        this.h = true;
                        this.a.onSubscribe(this);
                        d();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.j = iRequestFusion;
                        this.e = queueDisposable;
                        this.a.onSubscribe(this);
                        return;
                    }
                }
                this.e = new SpscLinkedArrayQueue(this.d);
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            return this.e.poll();
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.k = true;
            return 2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:36:0x0075, code lost:
        
            r3 = addAndGet(-r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x007a, code lost:
        
            if (r3 != 0) goto L53;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:?, code lost:
        
            return;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r7 = this;
                boolean r0 = r7.k
                r1 = 1
                if (r0 == 0) goto L4f
                r0 = r1
            L6:
                boolean r2 = r7.i
                if (r2 == 0) goto Lc
                goto L97
            Lc:
                boolean r2 = r7.h
                java.lang.Throwable r3 = r7.g
                boolean r4 = r7.c
                if (r4 != 0) goto L28
                if (r2 == 0) goto L28
                if (r3 == 0) goto L28
                r7.i = r1
                io.reactivex.Observer<? super T> r0 = r7.a
                java.lang.Throwable r1 = r7.g
                r0.onError(r1)
                io.reactivex.Scheduler$Worker r0 = r7.b
                r0.dispose()
                goto L97
            L28:
                io.reactivex.Observer<? super T> r3 = r7.a
                r4 = 0
                r3.onNext(r4)
                if (r2 == 0) goto L47
                r7.i = r1
                java.lang.Throwable r0 = r7.g
                if (r0 == 0) goto L3c
                io.reactivex.Observer<? super T> r1 = r7.a
                r1.onError(r0)
                goto L41
            L3c:
                io.reactivex.Observer<? super T> r0 = r7.a
                r0.onComplete()
            L41:
                io.reactivex.Scheduler$Worker r0 = r7.b
                r0.dispose()
                goto L97
            L47:
                int r0 = -r0
                int r0 = r7.addAndGet(r0)
                if (r0 != 0) goto L6
                goto L97
            L4f:
                io.reactivex.internal.fuseable.SimpleQueue<T> r0 = r7.e
                io.reactivex.Observer<? super T> r2 = r7.a
                r3 = r1
            L54:
                boolean r4 = r7.h
                boolean r5 = r0.isEmpty()
                boolean r4 = r7.a(r4, r5, r2)
                if (r4 == 0) goto L61
                goto L97
            L61:
                boolean r4 = r7.h
                java.lang.Object r5 = r0.poll()     // Catch: java.lang.Throwable -> L81
                if (r5 != 0) goto L6b
                r6 = r1
                goto L6c
            L6b:
                r6 = 0
            L6c:
                boolean r4 = r7.a(r4, r6, r2)
                if (r4 == 0) goto L73
                goto L97
            L73:
                if (r6 == 0) goto L7d
                int r3 = -r3
                int r3 = r7.addAndGet(r3)
                if (r3 != 0) goto L54
                goto L97
            L7d:
                r2.onNext(r5)
                goto L61
            L81:
                r3 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r3)
                r7.i = r1
                io.reactivex.disposables.Disposable r1 = r7.f
                r1.dispose()
                r0.clear()
                r2.onError(r3)
                io.reactivex.Scheduler$Worker r0 = r7.b
                r0.dispose()
            L97:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableObserveOn.a.run():void");
        }
    }

    public ObservableObserveOn(ObservableSource<T> observableSource, Scheduler scheduler, boolean z, int i) {
        super(observableSource);
        this.a = scheduler;
        this.b = z;
        this.c = i;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        Scheduler scheduler = this.a;
        if (scheduler instanceof TrampolineScheduler) {
            this.source.subscribe(observer);
        } else {
            this.source.subscribe(new a(observer, scheduler.createWorker(), this.b, this.c));
        }
    }
}
