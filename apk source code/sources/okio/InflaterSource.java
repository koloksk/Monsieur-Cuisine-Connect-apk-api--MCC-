package okio;

import defpackage.fo;
import defpackage.g9;
import defpackage.go;
import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* loaded from: classes.dex */
public final class InflaterSource implements Source {
    public final BufferedSource a;
    public final Inflater b;
    public int c;
    public boolean d;

    public InflaterSource(Source source, Inflater inflater) {
        this(Okio.buffer(source), inflater);
    }

    public final void a() throws IOException {
        int i = this.c;
        if (i == 0) {
            return;
        }
        int remaining = i - this.b.getRemaining();
        this.c -= remaining;
        this.a.skip(remaining);
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.d) {
            return;
        }
        this.b.end();
        this.d = true;
        this.a.close();
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) throws DataFormatException, IOException {
        boolean zRefill;
        if (j < 0) {
            throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
        }
        if (this.d) {
            throw new IllegalStateException("closed");
        }
        if (j == 0) {
            return 0L;
        }
        do {
            zRefill = refill();
            try {
                fo foVarA = buffer.a(1);
                int iInflate = this.b.inflate(foVarA.a, foVarA.c, (int) Math.min(j, 8192 - foVarA.c));
                if (iInflate > 0) {
                    foVarA.c += iInflate;
                    long j2 = iInflate;
                    buffer.b += j2;
                    return j2;
                }
                if (!this.b.finished() && !this.b.needsDictionary()) {
                }
                a();
                if (foVarA.b != foVarA.c) {
                    return -1L;
                }
                buffer.a = foVarA.a();
                go.a(foVarA);
                return -1L;
            } catch (DataFormatException e) {
                throw new IOException(e);
            }
        } while (!zRefill);
        throw new EOFException("source exhausted prematurely");
    }

    public final boolean refill() throws IOException {
        if (!this.b.needsInput()) {
            return false;
        }
        a();
        if (this.b.getRemaining() != 0) {
            throw new IllegalStateException("?");
        }
        if (this.a.exhausted()) {
            return true;
        }
        fo foVar = this.a.buffer().a;
        int i = foVar.c;
        int i2 = foVar.b;
        int i3 = i - i2;
        this.c = i3;
        this.b.setInput(foVar.a, i2, i3);
        return false;
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.a.timeout();
    }

    public InflaterSource(BufferedSource bufferedSource, Inflater inflater) {
        if (bufferedSource == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (inflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.a = bufferedSource;
        this.b = inflater;
    }
}
