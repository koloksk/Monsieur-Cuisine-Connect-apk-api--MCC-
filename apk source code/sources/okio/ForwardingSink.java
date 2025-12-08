package okio;

import java.io.IOException;

/* loaded from: classes.dex */
public abstract class ForwardingSink implements Sink {
    public final Sink a;

    public ForwardingSink(Sink sink) {
        if (sink == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.a = sink;
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public void close() throws IOException {
        this.a.close();
    }

    public final Sink delegate() {
        return this.a;
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        this.a.flush();
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.a.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.a.toString() + ")";
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        this.a.write(buffer, j);
    }
}
