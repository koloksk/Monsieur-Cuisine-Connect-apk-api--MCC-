package defpackage;

import defpackage.ln;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Http2;
import okhttp3.internal.http2.Settings;
import okio.Buffer;
import okio.BufferedSink;

/* loaded from: classes.dex */
public final class rn implements Closeable {
    public static final Logger g = Logger.getLogger(Http2.class.getName());
    public final BufferedSink a;
    public final boolean b;
    public final Buffer c;
    public int d;
    public boolean e;
    public final ln.b f;

    public rn(BufferedSink bufferedSink, boolean z) {
        this.a = bufferedSink;
        this.b = z;
        Buffer buffer = new Buffer();
        this.c = buffer;
        this.f = new ln.b(buffer);
        this.d = 16384;
    }

    public synchronized void a() throws IOException {
        if (this.e) {
            throw new IOException("closed");
        }
        if (this.b) {
            if (g.isLoggable(Level.FINE)) {
                g.fine(Util.format(">> CONNECTION %s", Http2.a.hex()));
            }
            this.a.write(Http2.a.toByteArray());
            this.a.flush();
        }
    }

    public synchronized void b(Settings settings) throws IOException {
        if (this.e) {
            throw new IOException("closed");
        }
        a(0, Integer.bitCount(settings.a) * 6, (byte) 4, (byte) 0);
        int i = 0;
        while (i < 10) {
            boolean z = true;
            if (((1 << i) & settings.a) == 0) {
                z = false;
            }
            if (z) {
                this.a.writeShort(i == 4 ? 3 : i == 7 ? 4 : i);
                this.a.writeInt(settings.b[i]);
            }
            i++;
        }
        this.a.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.e = true;
        this.a.close();
    }

    public synchronized void flush() throws IOException {
        if (this.e) {
            throw new IOException("closed");
        }
        this.a.flush();
    }

    public synchronized void a(Settings settings) throws IOException {
        if (!this.e) {
            int i = this.d;
            if ((settings.a & 32) != 0) {
                i = settings.b[5];
            }
            this.d = i;
            if (((settings.a & 2) != 0 ? settings.b[1] : -1) != -1) {
                ln.b bVar = this.f;
                int i2 = (settings.a & 2) != 0 ? settings.b[1] : -1;
                if (bVar != null) {
                    int iMin = Math.min(i2, 16384);
                    int i3 = bVar.e;
                    if (i3 != iMin) {
                        if (iMin < i3) {
                            bVar.c = Math.min(bVar.c, iMin);
                        }
                        bVar.d = true;
                        bVar.e = iMin;
                        int i4 = bVar.i;
                        if (iMin < i4) {
                            if (iMin == 0) {
                                bVar.a();
                            } else {
                                bVar.a(i4 - iMin);
                            }
                        }
                    }
                } else {
                    throw null;
                }
            }
            a(0, 0, (byte) 4, (byte) 1);
            this.a.flush();
        } else {
            throw new IOException("closed");
        }
    }

    public final void b(int i, long j) throws IOException {
        while (j > 0) {
            int iMin = (int) Math.min(this.d, j);
            long j2 = iMin;
            j -= j2;
            a(i, iMin, (byte) 9, j == 0 ? (byte) 4 : (byte) 0);
            this.a.write(this.c, j2);
        }
    }

    public synchronized void a(int i, int i2, List<Header> list) throws IOException {
        if (!this.e) {
            this.f.a(list);
            long size = this.c.size();
            int iMin = (int) Math.min(this.d - 4, size);
            long j = iMin;
            a(i, iMin + 4, (byte) 5, size == j ? (byte) 4 : (byte) 0);
            this.a.writeInt(i2 & Integer.MAX_VALUE);
            this.a.write(this.c, j);
            if (size > j) {
                b(i, size - j);
            }
        } else {
            throw new IOException("closed");
        }
    }

    public synchronized void a(int i, ErrorCode errorCode) throws IOException {
        if (!this.e) {
            if (errorCode.httpCode != -1) {
                a(i, 4, (byte) 3, (byte) 0);
                this.a.writeInt(errorCode.httpCode);
                this.a.flush();
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IOException("closed");
        }
    }

    public synchronized void a(boolean z, int i, Buffer buffer, int i2) throws IOException {
        if (!this.e) {
            a(i, i2, (byte) 0, z ? (byte) 1 : (byte) 0);
            if (i2 > 0) {
                this.a.write(buffer, i2);
            }
        } else {
            throw new IOException("closed");
        }
    }

    public synchronized void a(boolean z, int i, int i2) throws IOException {
        if (!this.e) {
            a(0, 8, (byte) 6, z ? (byte) 1 : (byte) 0);
            this.a.writeInt(i);
            this.a.writeInt(i2);
            this.a.flush();
        } else {
            throw new IOException("closed");
        }
    }

    public synchronized void a(int i, ErrorCode errorCode, byte[] bArr) throws IOException {
        if (!this.e) {
            if (errorCode.httpCode != -1) {
                a(0, bArr.length + 8, (byte) 7, (byte) 0);
                this.a.writeInt(i);
                this.a.writeInt(errorCode.httpCode);
                if (bArr.length > 0) {
                    this.a.write(bArr);
                }
                this.a.flush();
            } else {
                Http2.a("errorCode.httpCode == -1", new Object[0]);
                throw null;
            }
        } else {
            throw new IOException("closed");
        }
    }

    public synchronized void a(int i, long j) throws IOException {
        if (this.e) {
            throw new IOException("closed");
        }
        if (j != 0 && j <= 2147483647L) {
            a(i, 4, (byte) 8, (byte) 0);
            this.a.writeInt((int) j);
            this.a.flush();
        } else {
            Http2.a("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(j));
            throw null;
        }
    }

    public void a(int i, int i2, byte b, byte b2) throws IOException {
        if (g.isLoggable(Level.FINE)) {
            g.fine(Http2.a(false, i, i2, b, b2));
        }
        int i3 = this.d;
        if (i2 > i3) {
            Http2.a("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(i3), Integer.valueOf(i2));
            throw null;
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            Http2.a("reserved bit set: %s", Integer.valueOf(i));
            throw null;
        }
        BufferedSink bufferedSink = this.a;
        bufferedSink.writeByte((i2 >>> 16) & 255);
        bufferedSink.writeByte((i2 >>> 8) & 255);
        bufferedSink.writeByte(i2 & 255);
        this.a.writeByte(b & 255);
        this.a.writeByte(b2 & 255);
        this.a.writeInt(i & Integer.MAX_VALUE);
    }

    public synchronized void a(boolean z, int i, List<Header> list) throws IOException {
        if (!this.e) {
            this.f.a(list);
            long size = this.c.size();
            int iMin = (int) Math.min(this.d, size);
            long j = iMin;
            byte b = size == j ? (byte) 4 : (byte) 0;
            if (z) {
                b = (byte) (b | 1);
            }
            a(i, iMin, (byte) 1, b);
            this.a.write(this.c, j);
            if (size > j) {
                b(i, size - j);
            }
        } else {
            throw new IOException("closed");
        }
    }
}
