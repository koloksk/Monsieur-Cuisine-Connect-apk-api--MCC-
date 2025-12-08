package io.reactivex;

import defpackage.g9;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;

/* loaded from: classes.dex */
public final class Notification<T> {
    public static final Notification<Object> b = new Notification<>(null);
    public final Object a;

    public Notification(Object obj) {
        this.a = obj;
    }

    @NonNull
    public static <T> Notification<T> createOnComplete() {
        return (Notification<T>) b;
    }

    @NonNull
    public static <T> Notification<T> createOnError(@NonNull Throwable th) {
        ObjectHelper.requireNonNull(th, "error is null");
        return new Notification<>(NotificationLite.error(th));
    }

    @NonNull
    public static <T> Notification<T> createOnNext(@NonNull T t) {
        ObjectHelper.requireNonNull(t, "value is null");
        return new Notification<>(t);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Notification) {
            return ObjectHelper.equals(this.a, ((Notification) obj).a);
        }
        return false;
    }

    @Nullable
    public Throwable getError() {
        Object obj = this.a;
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    @Nullable
    public T getValue() {
        Object obj = this.a;
        if (obj == null || NotificationLite.isError(obj)) {
            return null;
        }
        return (T) this.a;
    }

    public int hashCode() {
        Object obj = this.a;
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public boolean isOnComplete() {
        return this.a == null;
    }

    public boolean isOnError() {
        return NotificationLite.isError(this.a);
    }

    public boolean isOnNext() {
        Object obj = this.a;
        return (obj == null || NotificationLite.isError(obj)) ? false : true;
    }

    public String toString() {
        Object obj = this.a;
        if (obj == null) {
            return "OnCompleteNotification";
        }
        if (NotificationLite.isError(obj)) {
            StringBuilder sbA = g9.a("OnErrorNotification[");
            sbA.append(NotificationLite.getError(obj));
            sbA.append("]");
            return sbA.toString();
        }
        StringBuilder sbA2 = g9.a("OnNextNotification[");
        sbA2.append(this.a);
        sbA2.append("]");
        return sbA2.toString();
    }
}
