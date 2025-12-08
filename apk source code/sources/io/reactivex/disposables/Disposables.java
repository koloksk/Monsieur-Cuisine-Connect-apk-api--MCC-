package io.reactivex.disposables;

import defpackage.tk;
import defpackage.uk;
import defpackage.wk;
import defpackage.xk;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Future;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class Disposables {
    public Disposables() {
        throw new IllegalStateException("No instances!");
    }

    @NonNull
    public static Disposable disposed() {
        return EmptyDisposable.INSTANCE;
    }

    @NonNull
    public static Disposable empty() {
        return fromRunnable(Functions.EMPTY_RUNNABLE);
    }

    @NonNull
    public static Disposable fromAction(@NonNull Action action) {
        ObjectHelper.requireNonNull(action, "run is null");
        return new tk(action);
    }

    @NonNull
    public static Disposable fromFuture(@NonNull Future<?> future) {
        ObjectHelper.requireNonNull(future, "future is null");
        return fromFuture(future, true);
    }

    @NonNull
    public static Disposable fromRunnable(@NonNull Runnable runnable) {
        ObjectHelper.requireNonNull(runnable, "run is null");
        return new wk(runnable);
    }

    @NonNull
    public static Disposable fromSubscription(@NonNull Subscription subscription) {
        ObjectHelper.requireNonNull(subscription, "subscription is null");
        return new xk(subscription);
    }

    @NonNull
    public static Disposable fromFuture(@NonNull Future<?> future, boolean z) {
        ObjectHelper.requireNonNull(future, "future is null");
        return new uk(future, z);
    }
}
