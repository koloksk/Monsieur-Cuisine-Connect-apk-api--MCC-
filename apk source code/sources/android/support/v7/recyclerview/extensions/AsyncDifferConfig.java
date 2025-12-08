package android.support.v7.recyclerview.extensions;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.util.DiffUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public final class AsyncDifferConfig<T> {

    @NonNull
    public final Executor a;

    @NonNull
    public final Executor b;

    @NonNull
    public final DiffUtil.ItemCallback<T> c;

    public static final class Builder<T> {
        public static Executor e;
        public Executor a;
        public Executor b;
        public final DiffUtil.ItemCallback<T> c;
        public static final Object d = new Object();
        public static final Executor f = new a(null);

        public static class a implements Executor {
            public final Handler a = new Handler(Looper.getMainLooper());

            public /* synthetic */ a(a aVar) {
            }

            @Override // java.util.concurrent.Executor
            public void execute(@NonNull Runnable runnable) {
                this.a.post(runnable);
            }
        }

        public Builder(@NonNull DiffUtil.ItemCallback<T> itemCallback) {
            this.c = itemCallback;
        }

        @NonNull
        public AsyncDifferConfig<T> build() {
            if (this.a == null) {
                this.a = f;
            }
            if (this.b == null) {
                synchronized (d) {
                    if (e == null) {
                        e = Executors.newFixedThreadPool(2);
                    }
                }
                this.b = e;
            }
            return new AsyncDifferConfig<>(this.a, this.b, this.c, null);
        }

        @NonNull
        public Builder<T> setBackgroundThreadExecutor(Executor executor) {
            this.b = executor;
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder<T> setMainThreadExecutor(Executor executor) {
            this.a = executor;
            return this;
        }
    }

    public /* synthetic */ AsyncDifferConfig(Executor executor, Executor executor2, DiffUtil.ItemCallback itemCallback, a aVar) {
        this.a = executor;
        this.b = executor2;
        this.c = itemCallback;
    }

    @NonNull
    public Executor getBackgroundThreadExecutor() {
        return this.b;
    }

    @NonNull
    public DiffUtil.ItemCallback<T> getDiffCallback() {
        return this.c;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Executor getMainThreadExecutor() {
        return this.a;
    }
}
