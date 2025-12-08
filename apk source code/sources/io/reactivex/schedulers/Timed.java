package io.reactivex.schedulers;

import defpackage.g9;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class Timed<T> {
    public final T a;
    public final long b;
    public final TimeUnit c;

    public Timed(@NonNull T t, long j, @NonNull TimeUnit timeUnit) {
        this.a = t;
        this.b = j;
        this.c = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Timed)) {
            return false;
        }
        Timed timed = (Timed) obj;
        return ObjectHelper.equals(this.a, timed.a) && this.b == timed.b && ObjectHelper.equals(this.c, timed.c);
    }

    public int hashCode() {
        T t = this.a;
        int iHashCode = t != null ? t.hashCode() : 0;
        long j = this.b;
        return this.c.hashCode() + (((iHashCode * 31) + ((int) (j ^ (j >>> 31)))) * 31);
    }

    public long time() {
        return this.b;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Timed[time=");
        sbA.append(this.b);
        sbA.append(", unit=");
        sbA.append(this.c);
        sbA.append(", value=");
        sbA.append(this.a);
        sbA.append("]");
        return sbA.toString();
    }

    @NonNull
    public TimeUnit unit() {
        return this.c;
    }

    @NonNull
    public T value() {
        return this.a;
    }

    public long time(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.b, this.c);
    }
}
