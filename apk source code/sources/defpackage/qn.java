package defpackage;

import defpackage.ln;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Http2;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.http2.Settings;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

/* loaded from: classes.dex */
public final class qn implements Closeable {
    public static final Logger e = Logger.getLogger(Http2.class.getName());
    public final BufferedSource a;
    public final a b;
    public final boolean c;
    public final ln.a d;

    public static final class a implements Source {
        public final BufferedSource a;
        public int b;
        public byte c;
        public int d;
        public int e;
        public short f;

        public a(BufferedSource bufferedSource) {
            this.a = bufferedSource;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            int i;
            int i2;
            do {
                int i3 = this.e;
                if (i3 != 0) {
                    long j2 = this.a.read(buffer, Math.min(j, i3));
                    if (j2 == -1) {
                        return -1L;
                    }
                    this.e = (int) (this.e - j2);
                    return j2;
                }
                this.a.skip(this.f);
                this.f = (short) 0;
                if ((this.c & 4) != 0) {
                    return -1L;
                }
                i = this.d;
                int iA = qn.a(this.a);
                this.e = iA;
                this.b = iA;
                byte b = (byte) (this.a.readByte() & 255);
                this.c = (byte) (this.a.readByte() & 255);
                if (qn.e.isLoggable(Level.FINE)) {
                    qn.e.fine(Http2.a(true, this.d, this.b, b, this.c));
                }
                i2 = this.a.readInt() & Integer.MAX_VALUE;
                this.d = i2;
                if (b != 9) {
                    Http2.b("%s != TYPE_CONTINUATION", Byte.valueOf(b));
                    throw null;
                }
            } while (i2 == i);
            Http2.b("TYPE_CONTINUATION streamId changed", new Object[0]);
            throw null;
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.a.timeout();
        }
    }

    public interface b {
    }

    public qn(BufferedSource bufferedSource, boolean z) {
        this.a = bufferedSource;
        this.c = z;
        a aVar = new a(bufferedSource);
        this.b = aVar;
        this.d = new ln.a(4096, aVar);
    }

    public void a(b bVar) throws IOException {
        if (this.c) {
            if (a(true, bVar)) {
                return;
            }
            Http2.b("Required SETTINGS preface not received", new Object[0]);
            throw null;
        }
        ByteString byteString = this.a.readByteString(Http2.a.size());
        if (e.isLoggable(Level.FINE)) {
            e.fine(Util.format("<< CONNECTION %s", byteString.hex()));
        }
        if (Http2.a.equals(byteString)) {
            return;
        }
        Http2.b("Expected a connection header but was %s", byteString.utf8());
        throw null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean a(boolean z, b bVar) throws IOException {
        try {
            this.a.require(9L);
            int iA = a(this.a);
            if (iA < 0 || iA > 16384) {
                Http2.b("FRAME_SIZE_ERROR: %s", Integer.valueOf(iA));
                throw null;
            }
            byte b2 = (byte) (this.a.readByte() & 255);
            if (z && b2 != 4) {
                Http2.b("Expected a SETTINGS frame but was %s", Byte.valueOf(b2));
                throw null;
            }
            byte b3 = (byte) (this.a.readByte() & 255);
            int i = this.a.readInt() & Integer.MAX_VALUE;
            if (e.isLoggable(Level.FINE)) {
                e.fine(Http2.a(true, i, iA, b2, b3));
            }
            switch (b2) {
                case 0:
                    if (i == 0) {
                        Http2.b("PROTOCOL_ERROR: TYPE_DATA streamId == 0", new Object[0]);
                        throw null;
                    }
                    boolean z2 = (b3 & 1) != 0;
                    if (((b3 & 32) != 0) == false) {
                        short s = (b3 & 8) != 0 ? (short) (this.a.readByte() & 255) : (short) 0;
                        int iA2 = a(iA, b3, s);
                        BufferedSource bufferedSource = this.a;
                        Http2Connection.g gVar = (Http2Connection.g) bVar;
                        if (Http2Connection.this.b(i)) {
                            Http2Connection http2Connection = Http2Connection.this;
                            if (http2Connection != null) {
                                Buffer buffer = new Buffer();
                                long j = iA2;
                                bufferedSource.require(j);
                                bufferedSource.read(buffer, j);
                                if (buffer.size() == j) {
                                    http2Connection.a(new nn(http2Connection, "OkHttp %s Push Data[%s]", new Object[]{http2Connection.d, Integer.valueOf(i)}, i, buffer, iA2, z2));
                                } else {
                                    throw new IOException(buffer.size() + " != " + iA2);
                                }
                            } else {
                                throw null;
                            }
                        } else {
                            Http2Stream http2StreamA = Http2Connection.this.a(i);
                            if (http2StreamA == null) {
                                Http2Connection.this.a(i, ErrorCode.PROTOCOL_ERROR);
                                long j2 = iA2;
                                Http2Connection.this.a(j2);
                                bufferedSource.skip(j2);
                            } else {
                                http2StreamA.g.a(bufferedSource, iA2);
                                if (z2) {
                                    http2StreamA.a(Util.EMPTY_HEADERS, true);
                                }
                            }
                        }
                        this.a.skip(s);
                        return true;
                    }
                    Http2.b("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
                    throw null;
                case 1:
                    if (i != 0) {
                        boolean z3 = (b3 & 1) != 0;
                        short s2 = (b3 & 8) != 0 ? (short) (this.a.readByte() & 255) : (short) 0;
                        if ((b3 & 32) != 0) {
                            a(bVar, i);
                            iA -= 5;
                        }
                        ((Http2Connection.g) bVar).a(z3, i, -1, a(a(iA, b3, s2), s2, b3, i));
                        return true;
                    }
                    Http2.b("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
                    throw null;
                case 2:
                    if (iA != 5) {
                        Http2.b("TYPE_PRIORITY length: %d != 5", Integer.valueOf(iA));
                        throw null;
                    }
                    if (i != 0) {
                        a(bVar, i);
                        return true;
                    }
                    Http2.b("TYPE_PRIORITY streamId == 0", new Object[0]);
                    throw null;
                case 3:
                    if (iA != 4) {
                        Http2.b("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(iA));
                        throw null;
                    }
                    if (i != 0) {
                        int i2 = this.a.readInt();
                        ErrorCode errorCodeFromHttp2 = ErrorCode.fromHttp2(i2);
                        if (errorCodeFromHttp2 == null) {
                            Http2.b("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(i2));
                            throw null;
                        }
                        Http2Connection.g gVar2 = (Http2Connection.g) bVar;
                        if (Http2Connection.this.b(i)) {
                            Http2Connection http2Connection2 = Http2Connection.this;
                            http2Connection2.a(new on(http2Connection2, "OkHttp %s Push Reset[%s]", new Object[]{http2Connection2.d, Integer.valueOf(i)}, i, errorCodeFromHttp2));
                        } else {
                            Http2Stream http2StreamC = Http2Connection.this.c(i);
                            if (http2StreamC != null) {
                                http2StreamC.a(errorCodeFromHttp2);
                            }
                        }
                        return true;
                    }
                    Http2.b("TYPE_RST_STREAM streamId == 0", new Object[0]);
                    throw null;
                case 4:
                    if (i != 0) {
                        Http2.b("TYPE_SETTINGS streamId != 0", new Object[0]);
                        throw null;
                    }
                    if ((b3 & 1) == 0) {
                        if (iA % 6 != 0) {
                            Http2.b("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(iA));
                            throw null;
                        }
                        Settings settings = new Settings();
                        for (int i3 = 0; i3 < iA; i3 += 6) {
                            int i4 = this.a.readShort() & 65535;
                            int i5 = this.a.readInt();
                            if (i4 == 2) {
                                if (i5 != 0 && i5 != 1) {
                                    Http2.b("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                                    throw null;
                                }
                            } else if (i4 == 3) {
                                i4 = 4;
                            } else if (i4 == 4) {
                                i4 = 7;
                                if (i5 < 0) {
                                    Http2.b("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                                    throw null;
                                }
                            } else if (i4 == 5 && (i5 < 16384 || i5 > 16777215)) {
                                Http2.b("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(i5));
                                throw null;
                            }
                            settings.a(i4, i5);
                        }
                        Http2Connection.g gVar3 = (Http2Connection.g) bVar;
                        if (gVar3 != null) {
                            try {
                                Http2Connection.this.h.execute(new pn(gVar3, "OkHttp %s ACK Settings", new Object[]{Http2Connection.this.d}, false, settings));
                            } catch (RejectedExecutionException unused) {
                            }
                        } else {
                            throw null;
                        }
                    } else if (iA == 0) {
                        if (((Http2Connection.g) bVar) == null) {
                            throw null;
                        }
                    } else {
                        Http2.b("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                        throw null;
                    }
                    return true;
                case 5:
                    if (i != 0) {
                        short s3 = (b3 & 8) != 0 ? (short) (this.a.readByte() & 255) : (short) 0;
                        Http2Connection.this.a(this.a.readInt() & Integer.MAX_VALUE, a(a(iA - 4, b3, s3), s3, b3, i));
                        return true;
                    }
                    Http2.b("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
                    throw null;
                case 6:
                    if (iA != 8) {
                        Http2.b("TYPE_PING length != 8: %s", Integer.valueOf(iA));
                        throw null;
                    }
                    if (i == 0) {
                        ((Http2Connection.g) bVar).a((b3 & 1) != 0, this.a.readInt(), this.a.readInt());
                        return true;
                    }
                    Http2.b("TYPE_PING streamId != 0", new Object[0]);
                    throw null;
                case 7:
                    if (iA < 8) {
                        Http2.b("TYPE_GOAWAY length < 8: %s", Integer.valueOf(iA));
                        throw null;
                    }
                    if (i == 0) {
                        int i6 = this.a.readInt();
                        int i7 = this.a.readInt();
                        int i8 = iA - 8;
                        ErrorCode errorCodeFromHttp22 = ErrorCode.fromHttp2(i7);
                        if (errorCodeFromHttp22 == null) {
                            Http2.b("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(i7));
                            throw null;
                        }
                        ByteString byteString = ByteString.EMPTY;
                        if (i8 > 0) {
                            byteString = this.a.readByteString(i8);
                        }
                        ((Http2Connection.g) bVar).a(i6, errorCodeFromHttp22, byteString);
                        return true;
                    }
                    Http2.b("TYPE_GOAWAY streamId != 0", new Object[0]);
                    throw null;
                case 8:
                    if (iA != 4) {
                        Http2.b("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(iA));
                        throw null;
                    }
                    long j3 = this.a.readInt() & 2147483647L;
                    if (j3 != 0) {
                        ((Http2Connection.g) bVar).a(i, j3);
                        return true;
                    }
                    Http2.b("windowSizeIncrement was 0", Long.valueOf(j3));
                    throw null;
                default:
                    this.a.skip(iA);
                    return true;
            }
        } catch (EOFException unused2) {
            return false;
        }
    }

    public final List<Header> a(int i, short s, byte b2, int i2) throws IOException {
        a aVar = this.b;
        aVar.e = i;
        aVar.b = i;
        aVar.f = s;
        aVar.c = b2;
        aVar.d = i2;
        ln.a aVar2 = this.d;
        while (!aVar2.b.exhausted()) {
            int i3 = aVar2.b.readByte() & 255;
            if (i3 == 128) {
                throw new IOException("index == 0");
            }
            if ((i3 & 128) == 128) {
                int iA = aVar2.a(i3, 127) - 1;
                if (iA >= 0 && iA <= ln.a.length - 1) {
                    aVar2.a.add(ln.a[iA]);
                } else {
                    int iA2 = aVar2.a(iA - ln.a.length);
                    if (iA2 >= 0) {
                        Header[] headerArr = aVar2.e;
                        if (iA2 < headerArr.length) {
                            aVar2.a.add(headerArr[iA2]);
                        }
                    }
                    StringBuilder sbA = g9.a("Header index too large ");
                    sbA.append(iA + 1);
                    throw new IOException(sbA.toString());
                }
            } else if (i3 == 64) {
                ByteString byteStringB = aVar2.b();
                ln.a(byteStringB);
                aVar2.a(-1, new Header(byteStringB, aVar2.b()));
            } else if ((i3 & 64) == 64) {
                aVar2.a(-1, new Header(aVar2.c(aVar2.a(i3, 63) - 1), aVar2.b()));
            } else if ((i3 & 32) == 32) {
                int iA3 = aVar2.a(i3, 31);
                aVar2.d = iA3;
                if (iA3 >= 0 && iA3 <= aVar2.c) {
                    int i4 = aVar2.h;
                    if (iA3 < i4) {
                        if (iA3 == 0) {
                            aVar2.a();
                        } else {
                            aVar2.b(i4 - iA3);
                        }
                    }
                } else {
                    StringBuilder sbA2 = g9.a("Invalid dynamic table size update ");
                    sbA2.append(aVar2.d);
                    throw new IOException(sbA2.toString());
                }
            } else if (i3 != 16 && i3 != 0) {
                aVar2.a.add(new Header(aVar2.c(aVar2.a(i3, 15) - 1), aVar2.b()));
            } else {
                ByteString byteStringB2 = aVar2.b();
                ln.a(byteStringB2);
                aVar2.a.add(new Header(byteStringB2, aVar2.b()));
            }
        }
        ln.a aVar3 = this.d;
        if (aVar3 != null) {
            ArrayList arrayList = new ArrayList(aVar3.a);
            aVar3.a.clear();
            return arrayList;
        }
        throw null;
    }

    public final void a(b bVar, int i) throws IOException {
        int i2 = this.a.readInt() & Integer.MIN_VALUE;
        this.a.readByte();
        if (((Http2Connection.g) bVar) == null) {
            throw null;
        }
    }

    public static int a(BufferedSource bufferedSource) throws IOException {
        return (bufferedSource.readByte() & 255) | ((bufferedSource.readByte() & 255) << 16) | ((bufferedSource.readByte() & 255) << 8);
    }

    public static int a(int i, byte b2, short s) throws IOException {
        if ((b2 & 8) != 0) {
            i--;
        }
        if (s <= i) {
            return (short) (i - s);
        }
        Http2.b("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s), Integer.valueOf(i));
        throw null;
    }
}
