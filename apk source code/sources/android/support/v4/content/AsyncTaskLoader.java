package android.support.v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ModernAsyncTask;
import android.support.v4.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public abstract class AsyncTaskLoader<D> extends Loader<D> {
    public final Executor j;
    public volatile AsyncTaskLoader<D>.a k;
    public volatile AsyncTaskLoader<D>.a l;
    public long m;
    public long n;
    public Handler o;

    public final class a extends ModernAsyncTask<Void, Void, D> implements Runnable {
        public final CountDownLatch j = new CountDownLatch(1);
        public boolean k;

        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            this.k = false;
            AsyncTaskLoader.this.a();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsyncTaskLoader(@NonNull Context context) {
        super(context);
        Executor executor = ModernAsyncTask.h;
        this.n = -10000L;
        this.j = executor;
    }

    public void a() {
        if (this.l != null || this.k == null) {
            return;
        }
        if (this.k.k) {
            this.k.k = false;
            this.o.removeCallbacks(this.k);
        }
        if (this.m > 0 && SystemClock.uptimeMillis() < this.n + this.m) {
            this.k.k = true;
            this.o.postAtTime(this.k, this.n + this.m);
            return;
        }
        AsyncTaskLoader<D>.a aVar = this.k;
        Executor executor = this.j;
        if (aVar.c == ModernAsyncTask.Status.PENDING) {
            aVar.c = ModernAsyncTask.Status.RUNNING;
            aVar.a.a = null;
            executor.execute(aVar.b);
        } else {
            int iOrdinal = aVar.c.ordinal();
            if (iOrdinal == 1) {
                throw new IllegalStateException("Cannot execute task: the task is already running.");
            }
            if (iOrdinal == 2) {
                throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
            throw new IllegalStateException("We should never reach this state");
        }
    }

    public void cancelLoadInBackground() {
    }

    @Override // android.support.v4.content.Loader
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        if (this.k != null) {
            printWriter.print(str);
            printWriter.print("mTask=");
            printWriter.print(this.k);
            printWriter.print(" waiting=");
            printWriter.println(this.k.k);
        }
        if (this.l != null) {
            printWriter.print(str);
            printWriter.print("mCancellingTask=");
            printWriter.print(this.l);
            printWriter.print(" waiting=");
            printWriter.println(this.l.k);
        }
        if (this.m != 0) {
            printWriter.print(str);
            printWriter.print("mUpdateThrottle=");
            TimeUtils.formatDuration(this.m, printWriter);
            printWriter.print(" mLastLoadCompleteTime=");
            TimeUtils.formatDuration(this.n, SystemClock.uptimeMillis(), printWriter);
            printWriter.println();
        }
    }

    public boolean isLoadInBackgroundCanceled() {
        return this.l != null;
    }

    @Nullable
    public abstract D loadInBackground();

    @Override // android.support.v4.content.Loader
    public boolean onCancelLoad() {
        if (this.k == null) {
            return false;
        }
        if (!this.e) {
            this.h = true;
        }
        if (this.l != null) {
            if (this.k.k) {
                this.k.k = false;
                this.o.removeCallbacks(this.k);
            }
            this.k = null;
            return false;
        }
        if (this.k.k) {
            this.k.k = false;
            this.o.removeCallbacks(this.k);
            this.k = null;
            return false;
        }
        AsyncTaskLoader<D>.a aVar = this.k;
        aVar.d.set(true);
        boolean zCancel = aVar.b.cancel(false);
        if (zCancel) {
            this.l = this.k;
            cancelLoadInBackground();
        }
        this.k = null;
        return zCancel;
    }

    public void onCanceled(@Nullable D d) {
    }

    @Override // android.support.v4.content.Loader
    public void onForceLoad() {
        super.onForceLoad();
        cancelLoad();
        this.k = new a();
        a();
    }

    @Nullable
    public D onLoadInBackground() {
        return loadInBackground();
    }

    public void setUpdateThrottle(long j) {
        this.m = j;
        if (j != 0) {
            this.o = new Handler();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void waitForLoader() throws InterruptedException {
        AsyncTaskLoader<D>.a aVar = this.k;
        if (aVar != null) {
            try {
                aVar.j.await();
            } catch (InterruptedException unused) {
            }
        }
    }

    public void a(AsyncTaskLoader<D>.a aVar, D d) {
        onCanceled(d);
        if (this.l == aVar) {
            rollbackContentChanged();
            this.n = SystemClock.uptimeMillis();
            this.l = null;
            deliverCancellation();
            a();
        }
    }
}
