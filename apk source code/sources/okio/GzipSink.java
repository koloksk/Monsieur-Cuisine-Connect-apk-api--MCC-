package okio;

import defpackage.fo;
import defpackage.g9;
import defpackage.io;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

/* loaded from: classes.dex */
public final class GzipSink implements Sink {
    public final BufferedSink a;
    public final Deflater b;
    public final DeflaterSink c;
    public boolean d;
    public final CRC32 e = new CRC32();

    public GzipSink(Sink sink) {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        this.b = new Deflater(-1, true);
        BufferedSink bufferedSinkBuffer = Okio.buffer(sink);
        this.a = bufferedSinkBuffer;
        this.c = new DeflaterSink(bufferedSinkBuffer, this.b);
        Buffer buffer = this.a.buffer();
        buffer.writeShort(8075);
        buffer.writeByte(8);
        buffer.writeByte(0);
        buffer.writeInt(0);
        buffer.writeByte(0);
        buffer.writeByte(0);
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public void close() throws Throwable {
        if (this.d) {
            return;
        }
        try {
            DeflaterSink deflaterSink = this.c;
            deflaterSink.b.finish();
            deflaterSink.a(false);
            this.a.writeIntLe((int) this.e.getValue());
            this.a.writeIntLe((int) this.b.getBytesRead());
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.b.end();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        try {
            this.a.close();
        } catch (Throwable th3) {
            if (th == null) {
                th = th3;
            }
        }
        this.d = true;
        if (th == null) {
            return;
        }
        io.a(th);
        throw null;
    }

    public final Deflater deflater() {
        return this.b;
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        this.c.flush();
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.a.timeout();
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
        }
        if (j == 0) {
            return;
        }
        fo foVar = buffer.a;
        long j2 = j;
        while (j2 > 0) {
            int iMin = (int) Math.min(j2, foVar.c - foVar.b);
            this.e.update(foVar.a, foVar.b, iMin);
            j2 -= iMin;
            foVar = foVar.f;
        }
        this.c.write(buffer, j);
    }
}
