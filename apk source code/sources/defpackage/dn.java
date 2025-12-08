package defpackage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.cache.CacheRequest;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Source;
import okio.Timeout;

/* loaded from: classes.dex */
public class dn implements Source {
    public boolean a;
    public final /* synthetic */ BufferedSource b;
    public final /* synthetic */ CacheRequest c;
    public final /* synthetic */ BufferedSink d;

    public dn(CacheInterceptor cacheInterceptor, BufferedSource bufferedSource, CacheRequest cacheRequest, BufferedSink bufferedSink) {
        this.b = bufferedSource;
        this.c = cacheRequest;
        this.d = bufferedSink;
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.a && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
            this.a = true;
            this.c.abort();
        }
        this.b.close();
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) throws IOException {
        try {
            long j2 = this.b.read(buffer, j);
            if (j2 != -1) {
                buffer.copyTo(this.d.buffer(), buffer.size() - j2, j2);
                this.d.emitCompleteSegments();
                return j2;
            }
            if (!this.a) {
                this.a = true;
                this.d.close();
            }
            return -1L;
        } catch (IOException e) {
            if (!this.a) {
                this.a = true;
                this.c.abort();
            }
            throw e;
        }
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.b.timeout();
    }
}
