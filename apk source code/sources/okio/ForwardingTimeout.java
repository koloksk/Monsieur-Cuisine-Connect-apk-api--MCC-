package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ForwardingTimeout extends Timeout {
    public Timeout d;

    public ForwardingTimeout(Timeout timeout) {
        if (timeout == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.d = timeout;
    }

    @Override // okio.Timeout
    public Timeout clearDeadline() {
        return this.d.clearDeadline();
    }

    @Override // okio.Timeout
    public Timeout clearTimeout() {
        return this.d.clearTimeout();
    }

    @Override // okio.Timeout
    public long deadlineNanoTime() {
        return this.d.deadlineNanoTime();
    }

    public final Timeout delegate() {
        return this.d;
    }

    @Override // okio.Timeout
    public boolean hasDeadline() {
        return this.d.hasDeadline();
    }

    public final ForwardingTimeout setDelegate(Timeout timeout) {
        if (timeout == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.d = timeout;
        return this;
    }

    @Override // okio.Timeout
    public void throwIfReached() throws IOException {
        this.d.throwIfReached();
    }

    @Override // okio.Timeout
    public Timeout timeout(long j, TimeUnit timeUnit) {
        return this.d.timeout(j, timeUnit);
    }

    @Override // okio.Timeout
    public long timeoutNanos() {
        return this.d.timeoutNanos();
    }

    @Override // okio.Timeout
    public Timeout deadlineNanoTime(long j) {
        return this.d.deadlineNanoTime(j);
    }
}
