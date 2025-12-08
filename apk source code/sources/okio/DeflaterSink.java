package okio;

import defpackage.fo;
import defpackage.g9;
import defpackage.go;
import defpackage.io;
import java.io.IOException;
import java.util.zip.Deflater;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* loaded from: classes.dex */
public final class DeflaterSink implements Sink {
    public final BufferedSink a;
    public final Deflater b;
    public boolean c;

    public DeflaterSink(Sink sink, Deflater deflater) {
        this(Okio.buffer(sink), deflater);
    }

    @IgnoreJRERequirement
    public final void a(boolean z) throws IOException {
        fo foVarA;
        int iDeflate;
        Buffer buffer = this.a.buffer();
        while (true) {
            foVarA = buffer.a(1);
            if (z) {
                Deflater deflater = this.b;
                byte[] bArr = foVarA.a;
                int i = foVarA.c;
                iDeflate = deflater.deflate(bArr, i, 8192 - i, 2);
            } else {
                Deflater deflater2 = this.b;
                byte[] bArr2 = foVarA.a;
                int i2 = foVarA.c;
                iDeflate = deflater2.deflate(bArr2, i2, 8192 - i2);
            }
            if (iDeflate > 0) {
                foVarA.c += iDeflate;
                buffer.b += iDeflate;
                this.a.emitCompleteSegments();
            } else if (this.b.needsInput()) {
                break;
            }
        }
        if (foVarA.b == foVarA.c) {
            buffer.a = foVarA.a();
            go.a(foVarA);
        }
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public void close() throws Throwable {
        if (this.c) {
            return;
        }
        try {
            this.b.finish();
            a(false);
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
        this.c = true;
        if (th == null) {
            return;
        }
        io.a(th);
        throw null;
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        a(true);
        this.a.flush();
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.a.timeout();
    }

    public String toString() {
        StringBuilder sbA = g9.a("DeflaterSink(");
        sbA.append(this.a);
        sbA.append(")");
        return sbA.toString();
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        io.a(buffer.b, 0L, j);
        while (j > 0) {
            fo foVar = buffer.a;
            int iMin = (int) Math.min(j, foVar.c - foVar.b);
            this.b.setInput(foVar.a, foVar.b, iMin);
            a(false);
            long j2 = iMin;
            buffer.b -= j2;
            int i = foVar.b + iMin;
            foVar.b = i;
            if (i == foVar.c) {
                buffer.a = foVar.a();
                go.a(foVar);
            }
            j -= j2;
        }
    }

    public DeflaterSink(BufferedSink bufferedSink, Deflater deflater) {
        if (bufferedSink == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (deflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.a = bufferedSink;
        this.b = deflater;
    }
}
