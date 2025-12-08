package io.reactivex.android.plugins;

import io.reactivex.Scheduler;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class RxAndroidPlugins {
    public static volatile Function<Callable<Scheduler>, Scheduler> a;
    public static volatile Function<Scheduler, Scheduler> b;

    public RxAndroidPlugins() {
        throw new AssertionError("No instances.");
    }

    public static Function<Callable<Scheduler>, Scheduler> getInitMainThreadSchedulerHandler() {
        return a;
    }

    public static Function<Scheduler, Scheduler> getOnMainThreadSchedulerHandler() {
        return b;
    }

    public static Scheduler initMainThreadScheduler(Callable<Scheduler> callable) throws Exception {
        RuntimeException runtimeExceptionPropagate;
        if (callable == null) {
            throw new NullPointerException("scheduler == null");
        }
        Function<Callable<Scheduler>, Scheduler> function = a;
        if (function == null) {
            try {
                Scheduler schedulerCall = callable.call();
                if (schedulerCall != null) {
                    return schedulerCall;
                }
                throw new NullPointerException("Scheduler Callable returned null");
            } finally {
            }
        }
        try {
            Scheduler schedulerApply = function.apply(callable);
            if (schedulerApply != null) {
                return schedulerApply;
            }
            throw new NullPointerException("Scheduler Callable returned null");
        } finally {
        }
    }

    public static Scheduler onMainThreadScheduler(Scheduler scheduler) {
        if (scheduler == null) {
            throw new NullPointerException("scheduler == null");
        }
        Function<Scheduler, Scheduler> function = b;
        if (function == null) {
            return scheduler;
        }
        try {
            return function.apply(scheduler);
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    public static void reset() {
        setInitMainThreadSchedulerHandler(null);
        setMainThreadSchedulerHandler(null);
    }

    public static void setInitMainThreadSchedulerHandler(Function<Callable<Scheduler>, Scheduler> function) {
        a = function;
    }

    public static void setMainThreadSchedulerHandler(Function<Scheduler, Scheduler> function) {
        b = function;
    }
}
