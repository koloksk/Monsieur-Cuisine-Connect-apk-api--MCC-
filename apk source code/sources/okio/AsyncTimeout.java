package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import defpackage.fo;
import defpackage.g9;
import defpackage.io;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class AsyncTimeout extends Timeout {
    public static final long g = TimeUnit.SECONDS.toMillis(60);
    public static final long h = TimeUnit.MILLISECONDS.toNanos(g);

    @Nullable
    public static AsyncTimeout i;
    public boolean d;

    @Nullable
    public AsyncTimeout e;
    public long f;

    public class a implements Sink {
        public final /* synthetic */ Sink a;

        public a(Sink sink) {
            this.a = sink;
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
        public void close() throws IOException {
            AsyncTimeout.this.enter();
            try {
                try {
                    this.a.close();
                    AsyncTimeout.this.a(true);
                } catch (IOException e) {
                    AsyncTimeout asyncTimeout = AsyncTimeout.this;
                    if (!asyncTimeout.exit()) {
                        throw e;
                    }
                    throw asyncTimeout.newTimeoutException(e);
                }
            } catch (Throwable th) {
                AsyncTimeout.this.a(false);
                throw th;
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            AsyncTimeout.this.enter();
            try {
                try {
                    this.a.flush();
                    AsyncTimeout.this.a(true);
                } catch (IOException e) {
                    AsyncTimeout asyncTimeout = AsyncTimeout.this;
                    if (!asyncTimeout.exit()) {
                        throw e;
                    }
                    throw asyncTimeout.newTimeoutException(e);
                }
            } catch (Throwable th) {
                AsyncTimeout.this.a(false);
                throw th;
            }
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return AsyncTimeout.this;
        }

        public String toString() {
            StringBuilder sbA = g9.a("AsyncTimeout.sink(");
            sbA.append(this.a);
            sbA.append(")");
            return sbA.toString();
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            io.a(buffer.b, 0L, j);
            while (true) {
                long j2 = 0;
                if (j <= 0) {
                    return;
                }
                fo foVar = buffer.a;
                while (true) {
                    if (j2 >= PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                        break;
                    }
                    j2 += foVar.c - foVar.b;
                    if (j2 >= j) {
                        j2 = j;
                        break;
                    }
                    foVar = foVar.f;
                }
                AsyncTimeout.this.enter();
                try {
                    try {
                        this.a.write(buffer, j2);
                        j -= j2;
                        AsyncTimeout.this.a(true);
                    } catch (IOException e) {
                        AsyncTimeout asyncTimeout = AsyncTimeout.this;
                        if (!asyncTimeout.exit()) {
                            throw e;
                        }
                        throw asyncTimeout.newTimeoutException(e);
                    }
                } catch (Throwable th) {
                    AsyncTimeout.this.a(false);
                    throw th;
                }
            }
        }
    }

    public class b implements Source {
        public final /* synthetic */ Source a;

        public b(Source source) {
            this.a = source;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            AsyncTimeout.this.enter();
            try {
                try {
                    this.a.close();
                    AsyncTimeout.this.a(true);
                } catch (IOException e) {
                    AsyncTimeout asyncTimeout = AsyncTimeout.this;
                    if (!asyncTimeout.exit()) {
                        throw e;
                    }
                    throw asyncTimeout.newTimeoutException(e);
                }
            } catch (Throwable th) {
                AsyncTimeout.this.a(false);
                throw th;
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            AsyncTimeout.this.enter();
            try {
                try {
                    long j2 = this.a.read(buffer, j);
                    AsyncTimeout.this.a(true);
                    return j2;
                } catch (IOException e) {
                    AsyncTimeout asyncTimeout = AsyncTimeout.this;
                    if (asyncTimeout.exit()) {
                        throw asyncTimeout.newTimeoutException(e);
                    }
                    throw e;
                }
            } catch (Throwable th) {
                AsyncTimeout.this.a(false);
                throw th;
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return AsyncTimeout.this;
        }

        public String toString() {
            StringBuilder sbA = g9.a("AsyncTimeout.source(");
            sbA.append(this.a);
            sbA.append(")");
            return sbA.toString();
        }
    }

    public static final class c extends Thread {
        public c() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0015, code lost:
        
            r1.timedOut();
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r3 = this;
            L0:
                java.lang.Class<okio.AsyncTimeout> r0 = okio.AsyncTimeout.class
                monitor-enter(r0)     // Catch: java.lang.InterruptedException -> L0
                okio.AsyncTimeout r1 = okio.AsyncTimeout.a()     // Catch: java.lang.Throwable -> L19
                if (r1 != 0) goto Lb
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L19
                goto L0
            Lb:
                okio.AsyncTimeout r2 = okio.AsyncTimeout.i     // Catch: java.lang.Throwable -> L19
                if (r1 != r2) goto L14
                r1 = 0
                okio.AsyncTimeout.i = r1     // Catch: java.lang.Throwable -> L19
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L19
                return
            L14:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L19
                r1.timedOut()     // Catch: java.lang.InterruptedException -> L0
                goto L0
            L19:
                r1 = move-exception
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L19
                throw r1     // Catch: java.lang.InterruptedException -> L0
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.AsyncTimeout.c.run():void");
        }
    }

    public static synchronized void a(AsyncTimeout asyncTimeout, long j, boolean z) {
        if (i == null) {
            i = new AsyncTimeout();
            new c().start();
        }
        long jNanoTime = System.nanoTime();
        if (j != 0 && z) {
            asyncTimeout.f = Math.min(j, asyncTimeout.deadlineNanoTime() - jNanoTime) + jNanoTime;
        } else if (j != 0) {
            asyncTimeout.f = j + jNanoTime;
        } else {
            if (!z) {
                throw new AssertionError();
            }
            asyncTimeout.f = asyncTimeout.deadlineNanoTime();
        }
        long j2 = asyncTimeout.f - jNanoTime;
        AsyncTimeout asyncTimeout2 = i;
        while (asyncTimeout2.e != null && j2 >= asyncTimeout2.e.f - jNanoTime) {
            asyncTimeout2 = asyncTimeout2.e;
        }
        asyncTimeout.e = asyncTimeout2.e;
        asyncTimeout2.e = asyncTimeout;
        if (asyncTimeout2 == i) {
            AsyncTimeout.class.notify();
        }
    }

    public final void enter() {
        if (this.d) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long jTimeoutNanos = timeoutNanos();
        boolean zHasDeadline = hasDeadline();
        if (jTimeoutNanos != 0 || zHasDeadline) {
            this.d = true;
            a(this, jTimeoutNanos, zHasDeadline);
        }
    }

    public final boolean exit() {
        if (!this.d) {
            return false;
        }
        this.d = false;
        return a(this);
    }

    public IOException newTimeoutException(@Nullable IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    public final Sink sink(Sink sink) {
        return new a(sink);
    }

    public final Source source(Source source) {
        return new b(source);
    }

    public void timedOut() {
    }

    public static synchronized boolean a(AsyncTimeout asyncTimeout) {
        for (AsyncTimeout asyncTimeout2 = i; asyncTimeout2 != null; asyncTimeout2 = asyncTimeout2.e) {
            if (asyncTimeout2.e == asyncTimeout) {
                asyncTimeout2.e = asyncTimeout.e;
                asyncTimeout.e = null;
                return false;
            }
        }
        return true;
    }

    public final void a(boolean z) throws IOException {
        if (exit() && z) {
            throw newTimeoutException(null);
        }
    }

    @Nullable
    public static AsyncTimeout a() throws InterruptedException {
        AsyncTimeout asyncTimeout = i.e;
        if (asyncTimeout == null) {
            long jNanoTime = System.nanoTime();
            AsyncTimeout.class.wait(g);
            if (i.e != null || System.nanoTime() - jNanoTime < h) {
                return null;
            }
            return i;
        }
        long jNanoTime2 = asyncTimeout.f - System.nanoTime();
        if (jNanoTime2 > 0) {
            long j = jNanoTime2 / 1000000;
            AsyncTimeout.class.wait(j, (int) (jNanoTime2 - (1000000 * j)));
            return null;
        }
        i.e = asyncTimeout.e;
        asyncTimeout.e = null;
        return asyncTimeout;
    }
}
