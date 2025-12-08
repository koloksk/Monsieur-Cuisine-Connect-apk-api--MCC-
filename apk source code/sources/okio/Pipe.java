package okio;

import defpackage.co;
import defpackage.g9;
import java.io.IOException;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class Pipe {
    public final long a;
    public boolean c;
    public boolean d;

    @Nullable
    public Sink g;
    public final Buffer b = new Buffer();
    public final Sink e = new a();
    public final Source f = new b();

    public final class a implements Sink {
        public final co a = new co();

        public a() {
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
        public void close() throws IOException {
            Sink sink;
            synchronized (Pipe.this.b) {
                if (Pipe.this.c) {
                    return;
                }
                if (Pipe.this.g != null) {
                    sink = Pipe.this.g;
                } else {
                    if (Pipe.this.d && Pipe.this.b.size() > 0) {
                        throw new IOException("source is closed");
                    }
                    Pipe.this.c = true;
                    Pipe.this.b.notifyAll();
                    sink = null;
                }
                if (sink != null) {
                    this.a.a(sink.timeout());
                    try {
                        sink.close();
                    } finally {
                        this.a.a();
                    }
                }
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            Sink sink;
            synchronized (Pipe.this.b) {
                if (Pipe.this.c) {
                    throw new IllegalStateException("closed");
                }
                if (Pipe.this.g != null) {
                    sink = Pipe.this.g;
                } else {
                    if (Pipe.this.d && Pipe.this.b.size() > 0) {
                        throw new IOException("source is closed");
                    }
                    sink = null;
                }
            }
            if (sink != null) {
                this.a.a(sink.timeout());
                try {
                    sink.flush();
                } finally {
                    this.a.a();
                }
            }
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.a;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            Sink sink;
            synchronized (Pipe.this.b) {
                if (!Pipe.this.c) {
                    while (true) {
                        if (j <= 0) {
                            sink = null;
                            break;
                        }
                        if (Pipe.this.g != null) {
                            sink = Pipe.this.g;
                            break;
                        }
                        if (Pipe.this.d) {
                            throw new IOException("source is closed");
                        }
                        long size = Pipe.this.a - Pipe.this.b.size();
                        if (size == 0) {
                            this.a.waitUntilNotified(Pipe.this.b);
                        } else {
                            long jMin = Math.min(size, j);
                            Pipe.this.b.write(buffer, jMin);
                            j -= jMin;
                            Pipe.this.b.notifyAll();
                        }
                    }
                } else {
                    throw new IllegalStateException("closed");
                }
            }
            if (sink != null) {
                this.a.a(sink.timeout());
                try {
                    sink.write(buffer, j);
                } finally {
                    this.a.a();
                }
            }
        }
    }

    public final class b implements Source {
        public final Timeout a = new Timeout();

        public b() {
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            synchronized (Pipe.this.b) {
                Pipe.this.d = true;
                Pipe.this.b.notifyAll();
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            synchronized (Pipe.this.b) {
                if (Pipe.this.d) {
                    throw new IllegalStateException("closed");
                }
                while (Pipe.this.b.size() == 0) {
                    if (Pipe.this.c) {
                        return -1L;
                    }
                    this.a.waitUntilNotified(Pipe.this.b);
                }
                long j2 = Pipe.this.b.read(buffer, j);
                Pipe.this.b.notifyAll();
                return j2;
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.a;
        }
    }

    public Pipe(long j) {
        if (j < 1) {
            throw new IllegalArgumentException(g9.a("maxBufferSize < 1: ", j));
        }
        this.a = j;
    }

    public void fold(Sink sink) throws IOException {
        Buffer buffer;
        while (true) {
            synchronized (this.b) {
                if (this.g != null) {
                    throw new IllegalStateException("sink already folded");
                }
                if (this.b.exhausted()) {
                    this.d = true;
                    this.g = sink;
                    return;
                } else {
                    buffer = new Buffer();
                    buffer.write(this.b, this.b.b);
                    this.b.notifyAll();
                }
            }
            try {
                sink.write(buffer, buffer.b);
                sink.flush();
            } catch (Throwable th) {
                synchronized (this.b) {
                    this.d = true;
                    this.b.notifyAll();
                    throw th;
                }
            }
        }
    }

    public final Sink sink() {
        return this.e;
    }

    public final Source source() {
        return this.f;
    }
}
