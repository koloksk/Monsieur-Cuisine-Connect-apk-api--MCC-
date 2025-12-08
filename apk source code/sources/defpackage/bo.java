package defpackage;

import java.io.IOException;
import okio.Buffer;
import okio.BufferedSource;
import okio.Source;
import okio.Timeout;

/* loaded from: classes.dex */
public final class bo implements Source {
    public final BufferedSource a;
    public final Buffer b;
    public fo c;
    public int d;
    public boolean e;
    public long f;

    public bo(BufferedSource bufferedSource) {
        this.a = bufferedSource;
        Buffer buffer = bufferedSource.buffer();
        this.b = buffer;
        fo foVar = buffer.a;
        this.c = foVar;
        this.d = foVar != null ? foVar.b : -1;
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.e = true;
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) throws IOException {
        fo foVar;
        fo foVar2;
        if (this.e) {
            throw new IllegalStateException("closed");
        }
        fo foVar3 = this.c;
        if (foVar3 != null && (foVar3 != (foVar2 = this.b.a) || this.d != foVar2.b)) {
            throw new IllegalStateException("Peek source is invalid because upstream source was used");
        }
        this.a.request(this.f + j);
        if (this.c == null && (foVar = this.b.a) != null) {
            this.c = foVar;
            this.d = foVar.b;
        }
        long jMin = Math.min(j, this.b.b - this.f);
        if (jMin <= 0) {
            return -1L;
        }
        this.b.copyTo(buffer, this.f, jMin);
        this.f += jMin;
        return jMin;
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.a.timeout();
    }
}
