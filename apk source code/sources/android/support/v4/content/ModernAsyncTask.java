package android.support.v4.content;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.os.OperationCanceledException;
import android.util.Log;
import defpackage.g9;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public abstract class ModernAsyncTask<Params, Progress, Result> {
    public static final ThreadFactory f = new a();
    public static final BlockingQueue<Runnable> g = new LinkedBlockingQueue(10);
    public static final Executor h = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, g, f);
    public static e i;
    public volatile Status c = Status.PENDING;
    public final AtomicBoolean d = new AtomicBoolean();
    public final AtomicBoolean e = new AtomicBoolean();
    public final f<Params, Result> a = new b();
    public final FutureTask<Result> b = new c(this.a);

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    public static class a implements ThreadFactory {
        public final AtomicInteger a = new AtomicInteger(1);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            StringBuilder sbA = g9.a("ModernAsyncTask #");
            sbA.append(this.a.getAndIncrement());
            return new Thread(runnable, sbA.toString());
        }
    }

    public class b extends f<Params, Result> {
        public b() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Result call() throws Exception {
            ModernAsyncTask.this.e.set(true);
            Result resultOnLoadInBackground = null;
            try {
                Process.setThreadPriority(10);
                ModernAsyncTask modernAsyncTask = ModernAsyncTask.this;
                Params[] paramsArr = this.a;
                AsyncTaskLoader.a aVar = (AsyncTaskLoader.a) modernAsyncTask;
                if (aVar == null) {
                    throw null;
                }
                try {
                    resultOnLoadInBackground = AsyncTaskLoader.this.onLoadInBackground();
                } catch (OperationCanceledException e) {
                    if (!aVar.d.get()) {
                        throw e;
                    }
                }
                Binder.flushPendingCommands();
                return resultOnLoadInBackground;
            } finally {
            }
        }
    }

    public class c extends FutureTask<Result> {
        public c(Callable callable) {
            super(callable);
        }

        @Override // java.util.concurrent.FutureTask
        public void done() {
            try {
                Result result = get();
                ModernAsyncTask modernAsyncTask = ModernAsyncTask.this;
                if (modernAsyncTask.e.get()) {
                    return;
                }
                modernAsyncTask.a(result);
            } catch (InterruptedException e) {
                Log.w("AsyncTask", e);
            } catch (CancellationException unused) {
                ModernAsyncTask modernAsyncTask2 = ModernAsyncTask.this;
                if (modernAsyncTask2.e.get()) {
                    return;
                }
                modernAsyncTask2.a(null);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occurred while executing doInBackground()", e2.getCause());
            } catch (Throwable th) {
                throw new RuntimeException("An error occurred while executing doInBackground()", th);
            }
        }
    }

    public static class d<Data> {
        public final ModernAsyncTask a;
        public final Data[] b;

        public d(ModernAsyncTask modernAsyncTask, Data... dataArr) {
            this.a = modernAsyncTask;
            this.b = dataArr;
        }
    }

    public static class e extends Handler {
        public e() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AsyncTaskLoader<D>.a aVar;
            d dVar = (d) message.obj;
            int i = message.what;
            if (i != 1) {
                if (i == 2 && dVar.a == null) {
                    throw null;
                }
                return;
            }
            ModernAsyncTask modernAsyncTask = dVar.a;
            Object obj = dVar.b[0];
            if (modernAsyncTask.d.get()) {
                aVar = (AsyncTaskLoader.a) modernAsyncTask;
                try {
                    AsyncTaskLoader.this.a(aVar, obj);
                    aVar.j.countDown();
                } finally {
                }
            } else {
                aVar = (AsyncTaskLoader.a) modernAsyncTask;
                try {
                    AsyncTaskLoader asyncTaskLoader = AsyncTaskLoader.this;
                    if (asyncTaskLoader.k != aVar) {
                        asyncTaskLoader.a(aVar, obj);
                    } else if (asyncTaskLoader.isAbandoned()) {
                        asyncTaskLoader.onCanceled(obj);
                    } else {
                        asyncTaskLoader.commitContentChanged();
                        asyncTaskLoader.n = SystemClock.uptimeMillis();
                        asyncTaskLoader.k = null;
                        asyncTaskLoader.deliverResult(obj);
                    }
                } finally {
                }
            }
            modernAsyncTask.c = Status.FINISHED;
        }
    }

    public static abstract class f<Params, Result> implements Callable<Result> {
        public Params[] a;
    }

    public static Handler a() {
        e eVar;
        synchronized (ModernAsyncTask.class) {
            if (i == null) {
                i = new e();
            }
            eVar = i;
        }
        return eVar;
    }

    public Result a(Result result) {
        a().obtainMessage(1, new d(this, result)).sendToTarget();
        return result;
    }
}
