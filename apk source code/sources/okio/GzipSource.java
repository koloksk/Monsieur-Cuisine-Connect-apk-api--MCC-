package okio;

import defpackage.fo;
import defpackage.g9;
import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* loaded from: classes.dex */
public final class GzipSource implements Source {
    public final BufferedSource b;
    public final Inflater c;
    public final InflaterSource d;
    public int a = 0;
    public final CRC32 e = new CRC32();

    public GzipSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.c = new Inflater(true);
        BufferedSource bufferedSourceBuffer = Okio.buffer(source);
        this.b = bufferedSourceBuffer;
        this.d = new InflaterSource(bufferedSourceBuffer, this.c);
    }

    public final void a(Buffer buffer, long j, long j2) {
        fo foVar = buffer.a;
        while (true) {
            int i = foVar.c;
            int i2 = foVar.b;
            if (j < i - i2) {
                break;
            }
            j -= i - i2;
            foVar = foVar.f;
        }
        while (j2 > 0) {
            int iMin = (int) Math.min(foVar.c - r6, j2);
            this.e.update(foVar.a, (int) (foVar.b + j), iMin);
            j2 -= iMin;
            foVar = foVar.f;
            j = 0;
        }
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.d.close();
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) throws DataFormatException, IOException {
        long j2;
        if (j < 0) {
            throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
        }
        if (j == 0) {
            return 0L;
        }
        if (this.a == 0) {
            this.b.require(10L);
            byte b = this.b.buffer().getByte(3L);
            boolean z = ((b >> 1) & 1) == 1;
            if (z) {
                a(this.b.buffer(), 0L, 10L);
            }
            a("ID1ID2", 8075, this.b.readShort());
            this.b.skip(8L);
            if (((b >> 2) & 1) == 1) {
                this.b.require(2L);
                if (z) {
                    a(this.b.buffer(), 0L, 2L);
                }
                long shortLe = this.b.buffer().readShortLe();
                this.b.require(shortLe);
                if (z) {
                    j2 = shortLe;
                    a(this.b.buffer(), 0L, shortLe);
                } else {
                    j2 = shortLe;
                }
                this.b.skip(j2);
            }
            if (((b >> 3) & 1) == 1) {
                long jIndexOf = this.b.indexOf((byte) 0);
                if (jIndexOf == -1) {
                    throw new EOFException();
                }
                if (z) {
                    a(this.b.buffer(), 0L, jIndexOf + 1);
                }
                this.b.skip(jIndexOf + 1);
            }
            if (((b >> 4) & 1) == 1) {
                long jIndexOf2 = this.b.indexOf((byte) 0);
                if (jIndexOf2 == -1) {
                    throw new EOFException();
                }
                if (z) {
                    a(this.b.buffer(), 0L, jIndexOf2 + 1);
                }
                this.b.skip(jIndexOf2 + 1);
            }
            if (z) {
                a("FHCRC", this.b.readShortLe(), (short) this.e.getValue());
                this.e.reset();
            }
            this.a = 1;
        }
        if (this.a == 1) {
            long j3 = buffer.b;
            long j4 = this.d.read(buffer, j);
            if (j4 != -1) {
                a(buffer, j3, j4);
                return j4;
            }
            this.a = 2;
        }
        if (this.a == 2) {
            a("CRC", this.b.readIntLe(), (int) this.e.getValue());
            a("ISIZE", this.b.readIntLe(), (int) this.c.getBytesWritten());
            this.a = 3;
            if (!this.b.exhausted()) {
                throw new IOException("gzip finished without exhausting source");
            }
        }
        return -1L;
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.b.timeout();
    }

    public final void a(String str, int i, int i2) throws IOException {
        if (i2 != i) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", str, Integer.valueOf(i2), Integer.valueOf(i)));
        }
    }
}
