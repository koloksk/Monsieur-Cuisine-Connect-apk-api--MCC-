package defpackage;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* renamed from: do, reason: invalid class name */
/* loaded from: classes.dex */
public final class Cdo implements BufferedSink {
    public final Buffer a = new Buffer();
    public final Sink b;
    public boolean c;

    public Cdo(Sink sink) {
        if (sink == null) {
            throw new NullPointerException("sink == null");
        }
        this.b = sink;
    }

    @Override // okio.BufferedSink
    public Buffer buffer() {
        return this.a;
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public void close() throws Throwable {
        if (this.c) {
            return;
        }
        try {
            if (this.a.b > 0) {
                this.b.write(this.a, this.a.b);
            }
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.b.close();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        this.c = true;
        if (th == null) {
            return;
        }
        io.a(th);
        throw null;
    }

    @Override // okio.BufferedSink
    public BufferedSink emit() throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long size = this.a.size();
        if (size > 0) {
            this.b.write(this.a, size);
        }
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink emitCompleteSegments() throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long jCompleteSegmentByteCount = this.a.completeSegmentByteCount();
        if (jCompleteSegmentByteCount > 0) {
            this.b.write(this.a, jCompleteSegmentByteCount);
        }
        return this;
    }

    @Override // okio.BufferedSink, okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        Buffer buffer = this.a;
        long j = buffer.b;
        if (j > 0) {
            this.b.write(buffer, j);
        }
        this.b.flush();
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.c;
    }

    @Override // okio.BufferedSink
    public OutputStream outputStream() {
        return new a();
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.b.timeout();
    }

    public String toString() {
        StringBuilder sbA = g9.a("buffer(");
        sbA.append(this.b);
        sbA.append(")");
        return sbA.toString();
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.write(buffer, j);
        emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long j2 = source.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
            if (j2 == -1) {
                return j;
            }
            j += j2;
            emitCompleteSegments();
        }
    }

    @Override // okio.BufferedSink
    public BufferedSink writeByte(int i) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeByte(i);
        emitCompleteSegments();
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink writeDecimalLong(long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeDecimalLong(j);
        emitCompleteSegments();
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink writeHexadecimalUnsignedLong(long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeHexadecimalUnsignedLong(j);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeInt(int i) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeInt(i);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeIntLe(int i) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeIntLe(i);
        emitCompleteSegments();
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink writeLong(long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeLong(j);
        emitCompleteSegments();
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink writeLongLe(long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeLongLe(j);
        emitCompleteSegments();
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink writeShort(int i) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeShort(i);
        emitCompleteSegments();
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink writeShortLe(int i) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeShortLe(i);
        emitCompleteSegments();
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink writeString(String str, Charset charset) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeString(str, charset);
        emitCompleteSegments();
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink writeUtf8(String str) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeUtf8(str);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeUtf8CodePoint(int i) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeUtf8CodePoint(i);
        emitCompleteSegments();
        return this;
    }

    /* renamed from: do$a */
    public class a extends OutputStream {
        public a() {
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws Throwable {
            Cdo.this.close();
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            Cdo cdo = Cdo.this;
            if (cdo.c) {
                return;
            }
            cdo.flush();
        }

        public String toString() {
            return Cdo.this + ".outputStream()";
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            Cdo cdo = Cdo.this;
            if (cdo.c) {
                throw new IOException("closed");
            }
            cdo.a.writeByte((int) ((byte) i));
            Cdo.this.emitCompleteSegments();
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            Cdo cdo = Cdo.this;
            if (!cdo.c) {
                cdo.a.write(bArr, i, i2);
                Cdo.this.emitCompleteSegments();
                return;
            }
            throw new IOException("closed");
        }
    }

    @Override // okio.BufferedSink
    public BufferedSink write(ByteString byteString) throws IOException {
        if (!this.c) {
            this.a.write(byteString);
            emitCompleteSegments();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink writeString(String str, int i, int i2, Charset charset) throws IOException {
        if (!this.c) {
            this.a.writeString(str, i, i2, charset);
            emitCompleteSegments();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink writeUtf8(String str, int i, int i2) throws IOException {
        if (!this.c) {
            this.a.writeUtf8(str, i, i2);
            emitCompleteSegments();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink write(byte[] bArr) throws IOException {
        if (!this.c) {
            this.a.write(bArr);
            emitCompleteSegments();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink write(byte[] bArr, int i, int i2) throws IOException {
        if (!this.c) {
            this.a.write(bArr, i, i2);
            emitCompleteSegments();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) throws IOException {
        if (!this.c) {
            int iWrite = this.a.write(byteBuffer);
            emitCompleteSegments();
            return iWrite;
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink write(Source source, long j) throws IOException {
        while (j > 0) {
            long j2 = source.read(this.a, j);
            if (j2 != -1) {
                j -= j2;
                emitCompleteSegments();
            } else {
                throw new EOFException();
            }
        }
        return this;
    }
}
