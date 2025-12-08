package defpackage;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.util.Random;
import okhttp3.internal.ws.WebSocketProtocol;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Sink;
import okio.Timeout;

/* loaded from: classes.dex */
public final class yn {
    public final boolean a;
    public final Random b;
    public final BufferedSink c;
    public final Buffer d;
    public boolean e;
    public final Buffer f = new Buffer();
    public final a g = new a();
    public boolean h;
    public final byte[] i;
    public final Buffer.UnsafeCursor j;

    public final class a implements Sink {
        public int a;
        public long b;
        public boolean c;
        public boolean d;

        public a() {
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
        public void close() throws IOException {
            if (this.d) {
                throw new IOException("closed");
            }
            yn ynVar = yn.this;
            ynVar.a(this.a, ynVar.f.size(), this.c, true);
            this.d = true;
            yn.this.h = false;
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            if (this.d) {
                throw new IOException("closed");
            }
            yn ynVar = yn.this;
            ynVar.a(this.a, ynVar.f.size(), this.c, false);
            this.c = false;
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return yn.this.c.timeout();
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            if (this.d) {
                throw new IOException("closed");
            }
            yn.this.f.write(buffer, j);
            boolean z = this.c && this.b != -1 && yn.this.f.size() > this.b - PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            long jCompleteSegmentByteCount = yn.this.f.completeSegmentByteCount();
            if (jCompleteSegmentByteCount <= 0 || z) {
                return;
            }
            yn.this.a(this.a, jCompleteSegmentByteCount, this.c, false);
            this.c = false;
        }
    }

    public yn(boolean z, BufferedSink bufferedSink, Random random) {
        if (bufferedSink == null) {
            throw new NullPointerException("sink == null");
        }
        if (random == null) {
            throw new NullPointerException("random == null");
        }
        this.a = z;
        this.c = bufferedSink;
        this.d = bufferedSink.buffer();
        this.b = random;
        this.i = z ? new byte[4] : null;
        this.j = z ? new Buffer.UnsafeCursor() : null;
    }

    public void a(int i, ByteString byteString) throws IOException {
        String strA;
        ByteString byteString2 = ByteString.EMPTY;
        if (i != 0 || byteString != null) {
            if (i != 0 && (strA = WebSocketProtocol.a(i)) != null) {
                throw new IllegalArgumentException(strA);
            }
            Buffer buffer = new Buffer();
            buffer.writeShort(i);
            if (byteString != null) {
                buffer.write(byteString);
            }
            byteString2 = buffer.readByteString();
        }
        try {
            b(8, byteString2);
        } finally {
            this.e = true;
        }
    }

    public final void b(int i, ByteString byteString) throws IOException {
        if (this.e) {
            throw new IOException("closed");
        }
        int size = byteString.size();
        if (size > 125) {
            throw new IllegalArgumentException("Payload size must be less than or equal to 125");
        }
        this.d.writeByte(i | 128);
        if (this.a) {
            this.d.writeByte(size | 128);
            this.b.nextBytes(this.i);
            this.d.write(this.i);
            if (size > 0) {
                long size2 = this.d.size();
                this.d.write(byteString);
                this.d.readAndWriteUnsafe(this.j);
                this.j.seek(size2);
                WebSocketProtocol.a(this.j, this.i);
                this.j.close();
            }
        } else {
            this.d.writeByte(size);
            this.d.write(byteString);
        }
        this.c.flush();
    }

    public void a(int i, long j, boolean z, boolean z2) throws IOException {
        if (!this.e) {
            if (!z) {
                i = 0;
            }
            if (z2) {
                i |= 128;
            }
            this.d.writeByte(i);
            int i2 = this.a ? 128 : 0;
            if (j <= 125) {
                this.d.writeByte(((int) j) | i2);
            } else if (j <= 65535) {
                this.d.writeByte(i2 | 126);
                this.d.writeShort((int) j);
            } else {
                this.d.writeByte(i2 | 127);
                this.d.writeLong(j);
            }
            if (this.a) {
                this.b.nextBytes(this.i);
                this.d.write(this.i);
                if (j > 0) {
                    long size = this.d.size();
                    this.d.write(this.f, j);
                    this.d.readAndWriteUnsafe(this.j);
                    this.j.seek(size);
                    WebSocketProtocol.a(this.j, this.i);
                    this.j.close();
                }
            } else {
                this.d.write(this.f, j);
            }
            this.c.emit();
            return;
        }
        throw new IOException("closed");
    }
}
