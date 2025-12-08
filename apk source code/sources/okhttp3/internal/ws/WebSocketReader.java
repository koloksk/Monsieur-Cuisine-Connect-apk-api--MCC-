package okhttp3.internal.ws;

import defpackage.g9;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

/* loaded from: classes.dex */
public final class WebSocketReader {
    public final boolean a;
    public final BufferedSource b;
    public final FrameCallback c;
    public boolean d;
    public int e;
    public long f;
    public boolean g;
    public boolean h;
    public final Buffer i = new Buffer();
    public final Buffer j = new Buffer();
    public final byte[] k;
    public final Buffer.UnsafeCursor l;

    public interface FrameCallback {
        void onReadClose(int i, String str);

        void onReadMessage(String str) throws IOException;

        void onReadMessage(ByteString byteString) throws IOException;

        void onReadPing(ByteString byteString);

        void onReadPong(ByteString byteString);
    }

    public WebSocketReader(boolean z, BufferedSource bufferedSource, FrameCallback frameCallback) {
        if (bufferedSource == null) {
            throw new NullPointerException("source == null");
        }
        if (frameCallback == null) {
            throw new NullPointerException("frameCallback == null");
        }
        this.a = z;
        this.b = bufferedSource;
        this.c = frameCallback;
        this.k = z ? null : new byte[4];
        this.l = z ? null : new Buffer.UnsafeCursor();
    }

    public final void a() throws IOException {
        String utf8;
        long j = this.f;
        if (j > 0) {
            this.b.readFully(this.i, j);
            if (!this.a) {
                this.i.readAndWriteUnsafe(this.l);
                this.l.seek(0L);
                WebSocketProtocol.a(this.l, this.k);
                this.l.close();
            }
        }
        switch (this.e) {
            case 8:
                short s = 1005;
                long size = this.i.size();
                if (size == 1) {
                    throw new ProtocolException("Malformed close payload length of 1.");
                }
                if (size != 0) {
                    s = this.i.readShort();
                    utf8 = this.i.readUtf8();
                    String strA = WebSocketProtocol.a(s);
                    if (strA != null) {
                        throw new ProtocolException(strA);
                    }
                } else {
                    utf8 = "";
                }
                this.c.onReadClose(s, utf8);
                this.d = true;
                return;
            case 9:
                this.c.onReadPing(this.i.readByteString());
                return;
            case 10:
                this.c.onReadPong(this.i.readByteString());
                return;
            default:
                StringBuilder sbA = g9.a("Unknown control opcode: ");
                sbA.append(Integer.toHexString(this.e));
                throw new ProtocolException(sbA.toString());
        }
    }

    /* JADX WARN: Finally extract failed */
    public final void b() throws IOException {
        if (this.d) {
            throw new IOException("closed");
        }
        long jTimeoutNanos = this.b.timeout().timeoutNanos();
        this.b.timeout().clearTimeout();
        try {
            int i = this.b.readByte() & 255;
            this.b.timeout().timeout(jTimeoutNanos, TimeUnit.NANOSECONDS);
            this.e = i & 15;
            this.g = (i & 128) != 0;
            boolean z = (i & 8) != 0;
            this.h = z;
            if (z && !this.g) {
                throw new ProtocolException("Control frames must be final.");
            }
            boolean z2 = (i & 64) != 0;
            boolean z3 = (i & 32) != 0;
            boolean z4 = (i & 16) != 0;
            if (z2 || z3 || z4) {
                throw new ProtocolException("Reserved flags are unsupported.");
            }
            int i2 = this.b.readByte() & 255;
            boolean z5 = (i2 & 128) != 0;
            if (z5 == this.a) {
                throw new ProtocolException(this.a ? "Server-sent frames must not be masked." : "Client-sent frames must be masked.");
            }
            long j = i2 & 127;
            this.f = j;
            if (j == 126) {
                this.f = this.b.readShort() & 65535;
            } else if (j == 127) {
                long j2 = this.b.readLong();
                this.f = j2;
                if (j2 < 0) {
                    StringBuilder sbA = g9.a("Frame length 0x");
                    sbA.append(Long.toHexString(this.f));
                    sbA.append(" > 0x7FFFFFFFFFFFFFFF");
                    throw new ProtocolException(sbA.toString());
                }
            }
            if (this.h && this.f > 125) {
                throw new ProtocolException("Control frame must be less than 125B.");
            }
            if (z5) {
                this.b.readFully(this.k);
            }
        } catch (Throwable th) {
            this.b.timeout().timeout(jTimeoutNanos, TimeUnit.NANOSECONDS);
            throw th;
        }
    }
}
