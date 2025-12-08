package io.reactivex.internal.operators.observable;

import defpackage.bl;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class ObservableSkipLastTimed<T> extends bl<T, T> {
    public final long a;
    public final TimeUnit b;
    public final Scheduler c;
    public final int d;
    public final boolean e;

    public static final class a<T> extends AtomicInteger implements Observer<T>, Disposable {
        public static final long serialVersionUID = -5677354903406201275L;
        public final Observer<? super T> a;
        public final long b;
        public final TimeUnit c;
        public final Scheduler d;
        public final SpscLinkedArrayQueue<Object> e;
        public final boolean f;
        public Disposable g;
        public volatile boolean h;
        public volatile boolean i;
        public Throwable j;

        public a(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
            this.a = observer;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = new SpscLinkedArrayQueue<>(i);
            this.f = z;
        }

        public void a() {
            if (getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = this.a;
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.e;
            boolean z = this.f;
            TimeUnit timeUnit = this.c;
            Scheduler scheduler = this.d;
            long j = this.b;
            int iAddAndGet = 1;
            while (!this.h) {
                boolean z2 = this.i;
                Long l = (Long) spscLinkedArrayQueue.peek();
                boolean z3 = l == null;
                long jNow = scheduler.now(timeUnit);
                if (!z3 && l.longValue() > jNow - j) {
                    z3 = true;
                }
                if (z2) {
                    if (!z) {
                        Throwable th = this.j;
                        if (th != null) {
                            this.e.clear();
                            observer.onError(th);
                            return;
                        } else if (z3) {
                            observer.onComplete();
                            return;
                        }
                    } else if (z3) {
                        Throwable th2 = this.j;
                        if (th2 != null) {
                            observer.onError(th2);
                            return;
                        } else {
                            observer.onComplete();
                            return;
                        }
                    }
                }
                if (z3) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    spscLinkedArrayQueue.poll();
                    observer.onNext(spscLinkedArrayQueue.poll());
                }
            }
            this.e.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.h) {
                return;
            }
            this.h = true;
            this.g.dispose();
            if (getAndIncrement() == 0) {
                this.e.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.h;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.i = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.j = th;
            this.i = true;
            a();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.e.offer(Long.valueOf(this.d.now(this.c)), t);
            a();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.g, disposable)) {
                this.g = disposable;
                this.a.onSubscribe(this);
            }
        }
    }

    public ObservableSkipLastTimed(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = i;
        this.e = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a, this.b, this.c, this.d, this.e));
    }
}
