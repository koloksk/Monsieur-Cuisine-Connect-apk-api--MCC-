package io.reactivex.android.schedulers;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import defpackage.sk;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class AndroidSchedulers {
    public static final Scheduler a = RxAndroidPlugins.initMainThreadScheduler(new a());

    public static class a implements Callable<Scheduler> {
        @Override // java.util.concurrent.Callable
        public Scheduler call() throws Exception {
            return b.a;
        }
    }

    public static final class b {
        public static final Scheduler a = new sk(new Handler(Looper.getMainLooper()), false);
    }

    public AndroidSchedulers() {
        throw new AssertionError("No instances.");
    }

    public static Scheduler from(Looper looper) {
        return from(looper, false);
    }

    public static Scheduler mainThread() {
        return RxAndroidPlugins.onMainThreadScheduler(a);
    }

    @SuppressLint({"NewApi"})
    public static Scheduler from(Looper looper, boolean z) {
        if (looper != null) {
            return new sk(new Handler(looper), z);
        }
        throw new NullPointerException("looper == null");
    }
}
