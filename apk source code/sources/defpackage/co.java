package defpackage;

import java.util.concurrent.TimeUnit;
import okio.Timeout;

/* loaded from: classes.dex */
public final class co extends Timeout {
    public Timeout d;
    public boolean e;
    public long f;
    public long g;

    public void a(Timeout timeout) {
        this.d = timeout;
        boolean zHasDeadline = timeout.hasDeadline();
        this.e = zHasDeadline;
        this.f = zHasDeadline ? timeout.deadlineNanoTime() : -1L;
        long jTimeoutNanos = timeout.timeoutNanos();
        this.g = jTimeoutNanos;
        long jTimeoutNanos2 = timeoutNanos();
        if (jTimeoutNanos == 0 || (jTimeoutNanos2 != 0 && jTimeoutNanos >= jTimeoutNanos2)) {
            jTimeoutNanos = jTimeoutNanos2;
        }
        timeout.timeout(jTimeoutNanos, TimeUnit.NANOSECONDS);
        if (this.e && hasDeadline()) {
            timeout.deadlineNanoTime(Math.min(deadlineNanoTime(), this.f));
        } else if (hasDeadline()) {
            timeout.deadlineNanoTime(deadlineNanoTime());
        }
    }

    public void a() {
        this.d.timeout(this.g, TimeUnit.NANOSECONDS);
        if (this.e) {
            this.d.deadlineNanoTime(this.f);
        } else {
            this.d.clearDeadline();
        }
    }
}
