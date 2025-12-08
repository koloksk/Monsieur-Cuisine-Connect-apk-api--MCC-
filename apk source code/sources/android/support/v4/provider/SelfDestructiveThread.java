package android.support.v4.provider;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.GuardedBy;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SelfDestructiveThread {

    @GuardedBy("mLock")
    public HandlerThread b;

    @GuardedBy("mLock")
    public Handler c;
    public final int f;
    public final int g;
    public final String h;
    public final Object a = new Object();
    public Handler.Callback e = new a();

    @GuardedBy("mLock")
    public int d = 0;

    public interface ReplyCallback<T> {
        void onReply(T t);
    }

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                SelfDestructiveThread.this.a();
                return true;
            }
            if (i != 1) {
                return true;
            }
            SelfDestructiveThread.this.a((Runnable) message.obj);
            return true;
        }
    }

    public class b implements Runnable {
        public final /* synthetic */ Callable a;
        public final /* synthetic */ Handler b;
        public final /* synthetic */ ReplyCallback c;

        public class a implements Runnable {
            public final /* synthetic */ Object a;

            public a(Object obj) {
                this.a = obj;
            }

            @Override // java.lang.Runnable
            public void run() {
                b.this.c.onReply(this.a);
            }
        }

        public b(SelfDestructiveThread selfDestructiveThread, Callable callable, Handler handler, ReplyCallback replyCallback) {
            this.a = callable;
            this.b = handler;
            this.c = replyCallback;
        }

        @Override // java.lang.Runnable
        public void run() throws Exception {
            Object objCall;
            try {
                objCall = this.a.call();
            } catch (Exception unused) {
                objCall = null;
            }
            this.b.post(new a(objCall));
        }
    }

    public class c implements Runnable {
        public final /* synthetic */ AtomicReference a;
        public final /* synthetic */ Callable b;
        public final /* synthetic */ ReentrantLock c;
        public final /* synthetic */ AtomicBoolean d;
        public final /* synthetic */ Condition e;

        public c(SelfDestructiveThread selfDestructiveThread, AtomicReference atomicReference, Callable callable, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
            this.a = atomicReference;
            this.b = callable;
            this.c = reentrantLock;
            this.d = atomicBoolean;
            this.e = condition;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.a.set(this.b.call());
            } catch (Exception unused) {
            }
            this.c.lock();
            try {
                this.d.set(false);
                this.e.signal();
            } finally {
                this.c.unlock();
            }
        }
    }

    public SelfDestructiveThread(String str, int i, int i2) {
        this.h = str;
        this.g = i;
        this.f = i2;
    }

    public final void a(Runnable runnable) {
        runnable.run();
        synchronized (this.a) {
            this.c.removeMessages(0);
            this.c.sendMessageDelayed(this.c.obtainMessage(0), this.f);
        }
    }

    public final void b(Runnable runnable) {
        synchronized (this.a) {
            if (this.b == null) {
                HandlerThread handlerThread = new HandlerThread(this.h, this.g);
                this.b = handlerThread;
                handlerThread.start();
                this.c = new Handler(this.b.getLooper(), this.e);
                this.d++;
            }
            this.c.removeMessages(0);
            this.c.sendMessage(this.c.obtainMessage(1, runnable));
        }
    }

    @VisibleForTesting
    public int getGeneration() {
        int i;
        synchronized (this.a) {
            i = this.d;
        }
        return i;
    }

    @VisibleForTesting
    public boolean isRunning() {
        boolean z;
        synchronized (this.a) {
            z = this.b != null;
        }
        return z;
    }

    public <T> void postAndReply(Callable<T> callable, ReplyCallback<T> replyCallback) {
        b(new b(this, callable, new Handler(), replyCallback));
    }

    public <T> T postAndWait(Callable<T> callable, int i) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition conditionNewCondition = reentrantLock.newCondition();
        AtomicReference atomicReference = new AtomicReference();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        b(new c(this, atomicReference, callable, reentrantLock, atomicBoolean, conditionNewCondition));
        reentrantLock.lock();
        try {
            if (!atomicBoolean.get()) {
                return (T) atomicReference.get();
            }
            long nanos = TimeUnit.MILLISECONDS.toNanos(i);
            do {
                try {
                    nanos = conditionNewCondition.awaitNanos(nanos);
                } catch (InterruptedException unused) {
                }
                if (!atomicBoolean.get()) {
                    return (T) atomicReference.get();
                }
            } while (nanos > 0);
            throw new InterruptedException("timeout");
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void a() {
        synchronized (this.a) {
            if (this.c.hasMessages(1)) {
                return;
            }
            this.b.quit();
            this.b = null;
            this.c = null;
        }
    }
}
