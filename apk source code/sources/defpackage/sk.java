package defpackage;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class sk extends Scheduler {
    public final Handler b;
    public final boolean c;

    public static final class a extends Scheduler.Worker {
        public final Handler a;
        public final boolean b;
        public volatile boolean c;

        public a(Handler handler, boolean z) {
            this.a = handler;
            this.b = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c = true;
            this.a.removeCallbacksAndMessages(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }

        @Override // io.reactivex.Scheduler.Worker
        @SuppressLint({"NewApi"})
        public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            if (runnable == null) {
                throw new NullPointerException("run == null");
            }
            if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            }
            if (this.c) {
                return Disposables.disposed();
            }
            b bVar = new b(this.a, RxJavaPlugins.onSchedule(runnable));
            Message messageObtain = Message.obtain(this.a, bVar);
            messageObtain.obj = this;
            if (this.b) {
                messageObtain.setAsynchronous(true);
            }
            this.a.sendMessageDelayed(messageObtain, timeUnit.toMillis(j));
            if (!this.c) {
                return bVar;
            }
            this.a.removeCallbacks(bVar);
            return Disposables.disposed();
        }
    }

    public static final class b implements Runnable, Disposable {
        public final Handler a;
        public final Runnable b;
        public volatile boolean c;

        public b(Handler handler, Runnable runnable) {
            this.a = handler;
            this.b = runnable;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.a.removeCallbacks(this);
            this.c = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.b.run();
            } catch (Throwable th) {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public sk(Handler handler, boolean z) {
        this.b = handler;
        this.c = z;
    }

    @Override // io.reactivex.Scheduler
    public Scheduler.Worker createWorker() {
        return new a(this.b, this.c);
    }

    @Override // io.reactivex.Scheduler
    @SuppressLint({"NewApi"})
    public Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("run == null");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit == null");
        }
        b bVar = new b(this.b, RxJavaPlugins.onSchedule(runnable));
        Message messageObtain = Message.obtain(this.b, bVar);
        if (this.c) {
            messageObtain.setAsynchronous(true);
        }
        this.b.sendMessageDelayed(messageObtain, timeUnit.toMillis(j));
        return bVar;
    }
}
