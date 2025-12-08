package android.arch.core.executor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import java.util.concurrent.Executor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class ArchTaskExecutor extends TaskExecutor {
    public static volatile ArchTaskExecutor c;

    @NonNull
    public static final Executor d = new a();

    @NonNull
    public static final Executor e = new b();

    @NonNull
    public TaskExecutor a;

    @NonNull
    public TaskExecutor b;

    public static class a implements Executor {
        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            ArchTaskExecutor.getInstance().postToMainThread(runnable);
        }
    }

    public static class b implements Executor {
        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            ArchTaskExecutor.getInstance().executeOnDiskIO(runnable);
        }
    }

    public ArchTaskExecutor() {
        DefaultTaskExecutor defaultTaskExecutor = new DefaultTaskExecutor();
        this.b = defaultTaskExecutor;
        this.a = defaultTaskExecutor;
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return e;
    }

    @NonNull
    public static ArchTaskExecutor getInstance() {
        if (c != null) {
            return c;
        }
        synchronized (ArchTaskExecutor.class) {
            if (c == null) {
                c = new ArchTaskExecutor();
            }
        }
        return c;
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return d;
    }

    @Override // android.arch.core.executor.TaskExecutor
    public void executeOnDiskIO(Runnable runnable) {
        this.a.executeOnDiskIO(runnable);
    }

    @Override // android.arch.core.executor.TaskExecutor
    public boolean isMainThread() {
        return this.a.isMainThread();
    }

    @Override // android.arch.core.executor.TaskExecutor
    public void postToMainThread(Runnable runnable) {
        this.a.postToMainThread(runnable);
    }

    public void setDelegate(@Nullable TaskExecutor taskExecutor) {
        if (taskExecutor == null) {
            taskExecutor = this.b;
        }
        this.a = taskExecutor;
    }
}
