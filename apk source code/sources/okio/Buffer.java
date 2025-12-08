package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import defpackage.bo;
import defpackage.fo;
import defpackage.g9;
import defpackage.go;
import defpackage.ho;
import defpackage.io;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public final class Buffer implements BufferedSource, BufferedSink, Cloneable, ByteChannel {
    public static final byte[] c = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    @Nullable
    public fo a;
    public long b;

    public static final class UnsafeCursor implements Closeable {
        public fo a;
        public Buffer buffer;
        public byte[] data;
        public boolean readWrite;
        public long offset = -1;
        public int start = -1;
        public int end = -1;

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            this.buffer = null;
            this.a = null;
            this.offset = -1L;
            this.data = null;
            this.start = -1;
            this.end = -1;
        }

        public final long expandBuffer(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException(g9.b("minByteCount <= 0: ", i));
            }
            if (i > 8192) {
                throw new IllegalArgumentException(g9.b("minByteCount > Segment.SIZE: ", i));
            }
            Buffer buffer = this.buffer;
            if (buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            if (!this.readWrite) {
                throw new IllegalStateException("expandBuffer() only permitted for read/write buffers");
            }
            long j = buffer.b;
            fo foVarA = buffer.a(i);
            int i2 = 8192 - foVarA.c;
            foVarA.c = 8192;
            long j2 = i2;
            this.buffer.b = j + j2;
            this.a = foVarA;
            this.offset = j;
            this.data = foVarA.a;
            this.start = 8192 - i2;
            this.end = 8192;
            return j2;
        }

        public final int next() {
            long j = this.offset;
            if (j != this.buffer.b) {
                return j == -1 ? seek(0L) : seek(j + (this.end - this.start));
            }
            throw new IllegalStateException();
        }

        public final long resizeBuffer(long j) {
            Buffer buffer = this.buffer;
            if (buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            if (!this.readWrite) {
                throw new IllegalStateException("resizeBuffer() only permitted for read/write buffers");
            }
            long j2 = buffer.b;
            if (j <= j2) {
                if (j < 0) {
                    throw new IllegalArgumentException(g9.a("newSize < 0: ", j));
                }
                long j3 = j2 - j;
                while (true) {
                    if (j3 <= 0) {
                        break;
                    }
                    Buffer buffer2 = this.buffer;
                    fo foVar = buffer2.a.g;
                    int i = foVar.c;
                    long j4 = i - foVar.b;
                    if (j4 > j3) {
                        foVar.c = (int) (i - j3);
                        break;
                    }
                    buffer2.a = foVar.a();
                    go.a(foVar);
                    j3 -= j4;
                }
                this.a = null;
                this.offset = j;
                this.data = null;
                this.start = -1;
                this.end = -1;
            } else if (j > j2) {
                long j5 = j - j2;
                boolean z = true;
                while (j5 > 0) {
                    fo foVarA = this.buffer.a(1);
                    int iMin = (int) Math.min(j5, 8192 - foVarA.c);
                    int i2 = foVarA.c + iMin;
                    foVarA.c = i2;
                    j5 -= iMin;
                    if (z) {
                        this.a = foVarA;
                        this.offset = j2;
                        this.data = foVarA.a;
                        this.start = i2 - iMin;
                        this.end = i2;
                        z = false;
                    }
                }
            }
            this.buffer.b = j;
            return j2;
        }

        public final int seek(long j) {
            if (j >= -1) {
                Buffer buffer = this.buffer;
                long j2 = buffer.b;
                if (j <= j2) {
                    if (j == -1 || j == j2) {
                        this.a = null;
                        this.offset = j;
                        this.data = null;
                        this.start = -1;
                        this.end = -1;
                        return -1;
                    }
                    long j3 = 0;
                    fo foVar = buffer.a;
                    fo foVar2 = this.a;
                    if (foVar2 != null) {
                        long j4 = this.offset - (this.start - foVar2.b);
                        if (j4 > j) {
                            j2 = j4;
                            foVar2 = foVar;
                            foVar = foVar2;
                        } else {
                            j3 = j4;
                        }
                    } else {
                        foVar2 = foVar;
                    }
                    if (j2 - j > j - j3) {
                        while (true) {
                            int i = foVar2.c;
                            int i2 = foVar2.b;
                            if (j < (i - i2) + j3) {
                                break;
                            }
                            j3 += i - i2;
                            foVar2 = foVar2.f;
                        }
                    } else {
                        while (j2 > j) {
                            foVar = foVar.g;
                            j2 -= foVar.c - foVar.b;
                        }
                        foVar2 = foVar;
                        j3 = j2;
                    }
                    if (this.readWrite && foVar2.d) {
                        fo foVar3 = new fo((byte[]) foVar2.a.clone(), foVar2.b, foVar2.c, false, true);
                        Buffer buffer2 = this.buffer;
                        if (buffer2.a == foVar2) {
                            buffer2.a = foVar3;
                        }
                        foVar2.a(foVar3);
                        foVar3.g.a();
                        foVar2 = foVar3;
                    }
                    this.a = foVar2;
                    this.offset = j;
                    this.data = foVar2.a;
                    int i3 = foVar2.b + ((int) (j - j3));
                    this.start = i3;
                    int i4 = foVar2.c;
                    this.end = i4;
                    return i4 - i3;
                }
            }
            throw new ArrayIndexOutOfBoundsException(String.format("offset=%s > size=%s", Long.valueOf(j), Long.valueOf(this.buffer.b)));
        }
    }

    public class a extends OutputStream {
        public a() {
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() {
        }

        public String toString() {
            return Buffer.this + ".outputStream()";
        }

        @Override // java.io.OutputStream
        public void write(int i) {
            Buffer.this.writeByte((int) ((byte) i));
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) {
            Buffer.this.write(bArr, i, i2);
        }
    }

    public class b extends InputStream {
        public b() {
        }

        @Override // java.io.InputStream
        public int available() {
            return (int) Math.min(Buffer.this.b, 2147483647L);
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.InputStream
        public int read() {
            Buffer buffer = Buffer.this;
            if (buffer.b > 0) {
                return buffer.readByte() & 255;
            }
            return -1;
        }

        public String toString() {
            return Buffer.this + ".inputStream()";
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) {
            return Buffer.this.read(bArr, i, i2);
        }
    }

    public final void a(InputStream inputStream, long j, boolean z) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        while (true) {
            if (j <= 0 && !z) {
                return;
            }
            fo foVarA = a(1);
            int i = inputStream.read(foVarA.a, foVarA.c, (int) Math.min(j, 8192 - foVarA.c));
            if (i == -1) {
                if (!z) {
                    throw new EOFException();
                }
                return;
            } else {
                foVarA.c += i;
                long j2 = i;
                this.b += j2;
                j -= j2;
            }
        }
    }

    @Override // okio.BufferedSource
    public Buffer buffer() {
        return this;
    }

    public final void clear() {
        try {
            skip(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public final long completeSegmentByteCount() {
        long j = this.b;
        if (j == 0) {
            return 0L;
        }
        fo foVar = this.a.g;
        return (foVar.c >= 8192 || !foVar.e) ? j : j - (r3 - foVar.b);
    }

    public final Buffer copyTo(OutputStream outputStream) throws IOException {
        return copyTo(outputStream, 0L, this.b);
    }

    @Override // okio.BufferedSink
    public BufferedSink emit() {
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer emitCompleteSegments() {
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Buffer)) {
            return false;
        }
        Buffer buffer = (Buffer) obj;
        long j = this.b;
        if (j != buffer.b) {
            return false;
        }
        long j2 = 0;
        if (j == 0) {
            return true;
        }
        fo foVar = this.a;
        fo foVar2 = buffer.a;
        int i = foVar.b;
        int i2 = foVar2.b;
        while (j2 < this.b) {
            long jMin = Math.min(foVar.c - i, foVar2.c - i2);
            int i3 = 0;
            while (i3 < jMin) {
                int i4 = i + 1;
                int i5 = i2 + 1;
                if (foVar.a[i] != foVar2.a[i2]) {
                    return false;
                }
                i3++;
                i = i4;
                i2 = i5;
            }
            if (i == foVar.c) {
                foVar = foVar.f;
                i = foVar.b;
            }
            if (i2 == foVar2.c) {
                foVar2 = foVar2.f;
                i2 = foVar2.b;
            }
            j2 += jMin;
        }
        return true;
    }

    @Override // okio.BufferedSource
    public boolean exhausted() {
        return this.b == 0;
    }

    @Override // okio.BufferedSink, okio.Sink, java.io.Flushable
    public void flush() {
    }

    @Override // okio.BufferedSource
    public Buffer getBuffer() {
        return this;
    }

    public final byte getByte(long j) {
        int i;
        io.a(this.b, j, 1L);
        long j2 = this.b;
        if (j2 - j <= j) {
            long j3 = j - j2;
            fo foVar = this.a;
            do {
                foVar = foVar.g;
                int i2 = foVar.c;
                i = foVar.b;
                j3 += i2 - i;
            } while (j3 < 0);
            return foVar.a[i + ((int) j3)];
        }
        fo foVar2 = this.a;
        while (true) {
            int i3 = foVar2.c;
            int i4 = foVar2.b;
            long j4 = i3 - i4;
            if (j < j4) {
                return foVar2.a[i4 + ((int) j)];
            }
            j -= j4;
            foVar2 = foVar2.f;
        }
    }

    public int hashCode() {
        fo foVar = this.a;
        if (foVar == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = foVar.c;
            for (int i3 = foVar.b; i3 < i2; i3++) {
                i = (i * 31) + foVar.a[i3];
            }
            foVar = foVar.f;
        } while (foVar != this.a);
        return i;
    }

    public final ByteString hmacSha1(ByteString byteString) {
        return a("HmacSHA1", byteString);
    }

    public final ByteString hmacSha256(ByteString byteString) {
        return a("HmacSHA256", byteString);
    }

    public final ByteString hmacSha512(ByteString byteString) {
        return a("HmacSHA512", byteString);
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b2) {
        return indexOf(b2, 0L, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString byteString) {
        return indexOfElement(byteString, 0L);
    }

    @Override // okio.BufferedSource
    public InputStream inputStream() {
        return new b();
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return true;
    }

    public final ByteString md5() {
        return a("MD5");
    }

    @Override // okio.BufferedSink
    public OutputStream outputStream() {
        return new a();
    }

    @Override // okio.BufferedSource
    public BufferedSource peek() {
        return Okio.buffer(new bo(this));
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long j, ByteString byteString) {
        return rangeEquals(j, byteString, 0, byteString.size());
    }

    @Override // okio.BufferedSource
    public int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    @Override // okio.BufferedSource
    public long readAll(Sink sink) throws IOException {
        long j = this.b;
        if (j > 0) {
            sink.write(this, j);
        }
        return j;
    }

    public final UnsafeCursor readAndWriteUnsafe() {
        return readAndWriteUnsafe(new UnsafeCursor());
    }

    @Override // okio.BufferedSource
    public byte readByte() {
        long j = this.b;
        if (j == 0) {
            throw new IllegalStateException("size == 0");
        }
        fo foVar = this.a;
        int i = foVar.b;
        int i2 = foVar.c;
        int i3 = i + 1;
        byte b2 = foVar.a[i];
        this.b = j - 1;
        if (i3 == i2) {
            this.a = foVar.a();
            go.a(foVar);
        } else {
            foVar.b = i3;
        }
        return b2;
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray() {
        try {
            return readByteArray(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // okio.BufferedSource
    public ByteString readByteString() {
        return new ByteString(readByteArray());
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a0 A[EDGE_INSN: B:48:0x00a0->B:38:0x00a0 BREAK  A[LOOP:0: B:5:0x000d->B:50:?], SYNTHETIC] */
    @Override // okio.BufferedSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long readDecimalLong() {
        /*
            r15 = this;
            long r0 = r15.b
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto Lab
            r0 = -7
            r4 = 0
            r5 = r4
            r6 = r5
        Ld:
            fo r7 = r15.a
            byte[] r8 = r7.a
            int r9 = r7.b
            int r10 = r7.c
        L15:
            if (r9 >= r10) goto L8c
            r11 = r8[r9]
            r12 = 48
            if (r11 < r12) goto L62
            r13 = 57
            if (r11 > r13) goto L62
            int r12 = r12 - r11
            r13 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r13 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
            if (r13 < 0) goto L39
            if (r13 != 0) goto L33
            long r13 = (long) r12
            int r13 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
            if (r13 >= 0) goto L33
            goto L39
        L33:
            r13 = 10
            long r2 = r2 * r13
            long r11 = (long) r12
            long r2 = r2 + r11
            goto L6c
        L39:
            okio.Buffer r0 = new okio.Buffer
            r0.<init>()
            okio.Buffer r0 = r0.writeDecimalLong(r2)
            okio.Buffer r0 = r0.writeByte(r11)
            if (r5 != 0) goto L4b
            r0.readByte()
        L4b:
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.String r2 = "Number too large: "
            java.lang.StringBuilder r2 = defpackage.g9.a(r2)
            java.lang.String r0 = r0.readUtf8()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L62:
            r12 = 45
            if (r11 != r12) goto L71
            if (r4 != 0) goto L71
            r11 = 1
            long r0 = r0 - r11
            r5 = 1
        L6c:
            int r9 = r9 + 1
            int r4 = r4 + 1
            goto L15
        L71:
            if (r4 == 0) goto L75
            r6 = 1
            goto L8c
        L75:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = "Expected leading [0-9] or '-' character but was 0x"
            java.lang.StringBuilder r1 = defpackage.g9.a(r1)
            java.lang.String r2 = java.lang.Integer.toHexString(r11)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L8c:
            if (r9 != r10) goto L98
            fo r8 = r7.a()
            r15.a = r8
            defpackage.go.a(r7)
            goto L9a
        L98:
            r7.b = r9
        L9a:
            if (r6 != 0) goto La0
            fo r7 = r15.a
            if (r7 != 0) goto Ld
        La0:
            long r0 = r15.b
            long r6 = (long) r4
            long r0 = r0 - r6
            r15.b = r0
            if (r5 == 0) goto La9
            goto Laa
        La9:
            long r2 = -r2
        Laa:
            return r2
        Lab:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "size == 0"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readDecimalLong():long");
    }

    public final Buffer readFrom(InputStream inputStream) throws IOException {
        a(inputStream, Long.MAX_VALUE, true);
        return this;
    }

    @Override // okio.BufferedSource
    public void readFully(Buffer buffer, long j) throws EOFException {
        long j2 = this.b;
        if (j2 >= j) {
            buffer.write(this, j);
        } else {
            buffer.write(this, j2);
            throw new EOFException();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009c A[EDGE_INSN: B:44:0x009c->B:38:0x009c BREAK  A[LOOP:0: B:5:0x000b->B:46:?], SYNTHETIC] */
    @Override // okio.BufferedSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long readHexadecimalUnsignedLong() {
        /*
            r14 = this;
            long r0 = r14.b
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto La3
            r0 = 0
            r1 = r0
            r4 = r2
        Lb:
            fo r6 = r14.a
            byte[] r7 = r6.a
            int r8 = r6.b
            int r9 = r6.c
        L13:
            if (r8 >= r9) goto L88
            r10 = r7[r8]
            r11 = 48
            if (r10 < r11) goto L22
            r11 = 57
            if (r10 > r11) goto L22
            int r11 = r10 + (-48)
            goto L39
        L22:
            r11 = 97
            if (r10 < r11) goto L2d
            r11 = 102(0x66, float:1.43E-43)
            if (r10 > r11) goto L2d
            int r11 = r10 + (-97)
            goto L37
        L2d:
            r11 = 65
            if (r10 < r11) goto L6d
            r11 = 70
            if (r10 > r11) goto L6d
            int r11 = r10 + (-65)
        L37:
            int r11 = r11 + 10
        L39:
            r12 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r12 = r12 & r4
            int r12 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r12 != 0) goto L49
            r10 = 4
            long r4 = r4 << r10
            long r10 = (long) r11
            long r4 = r4 | r10
            int r8 = r8 + 1
            int r0 = r0 + 1
            goto L13
        L49:
            okio.Buffer r0 = new okio.Buffer
            r0.<init>()
            okio.Buffer r0 = r0.writeHexadecimalUnsignedLong(r4)
            okio.Buffer r0 = r0.writeByte(r10)
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.String r2 = "Number too large: "
            java.lang.StringBuilder r2 = defpackage.g9.a(r2)
            java.lang.String r0 = r0.readUtf8()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L6d:
            if (r0 == 0) goto L71
            r1 = 1
            goto L88
        L71:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = "Expected leading [0-9a-fA-F] character but was 0x"
            java.lang.StringBuilder r1 = defpackage.g9.a(r1)
            java.lang.String r2 = java.lang.Integer.toHexString(r10)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L88:
            if (r8 != r9) goto L94
            fo r7 = r6.a()
            r14.a = r7
            defpackage.go.a(r6)
            goto L96
        L94:
            r6.b = r8
        L96:
            if (r1 != 0) goto L9c
            fo r6 = r14.a
            if (r6 != 0) goto Lb
        L9c:
            long r1 = r14.b
            long r6 = (long) r0
            long r1 = r1 - r6
            r14.b = r1
            return r4
        La3:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "size == 0"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readHexadecimalUnsignedLong():long");
    }

    @Override // okio.BufferedSource
    public int readInt() {
        long j = this.b;
        if (j < 4) {
            StringBuilder sbA = g9.a("size < 4: ");
            sbA.append(this.b);
            throw new IllegalStateException(sbA.toString());
        }
        fo foVar = this.a;
        int i = foVar.b;
        int i2 = foVar.c;
        if (i2 - i < 4) {
            return ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8) | (readByte() & 255);
        }
        byte[] bArr = foVar.a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i] & 255) << 24) | ((bArr[i3] & 255) << 16);
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & 255) << 8);
        int i8 = i6 + 1;
        int i9 = i7 | (bArr[i6] & 255);
        this.b = j - 4;
        if (i8 == i2) {
            this.a = foVar.a();
            go.a(foVar);
        } else {
            foVar.b = i8;
        }
        return i9;
    }

    @Override // okio.BufferedSource
    public int readIntLe() {
        return io.a(readInt());
    }

    @Override // okio.BufferedSource
    public long readLong() {
        long j = this.b;
        if (j < 8) {
            StringBuilder sbA = g9.a("size < 8: ");
            sbA.append(this.b);
            throw new IllegalStateException(sbA.toString());
        }
        fo foVar = this.a;
        int i = foVar.b;
        int i2 = foVar.c;
        if (i2 - i < 8) {
            return ((readInt() & 4294967295L) << 32) | (4294967295L & readInt());
        }
        byte[] bArr = foVar.a;
        long j2 = (bArr[i] & 255) << 56;
        int i3 = i + 1 + 1 + 1;
        long j3 = ((bArr[r8] & 255) << 48) | j2 | ((bArr[r3] & 255) << 40);
        long j4 = j3 | ((bArr[i3] & 255) << 32) | ((bArr[r3] & 255) << 24);
        long j5 = j4 | ((bArr[r6] & 255) << 16);
        long j6 = j5 | ((bArr[r3] & 255) << 8);
        int i4 = i3 + 1 + 1 + 1 + 1 + 1;
        long j7 = (bArr[r6] & 255) | j6;
        this.b = j - 8;
        if (i4 == i2) {
            this.a = foVar.a();
            go.a(foVar);
        } else {
            foVar.b = i4;
        }
        return j7;
    }

    @Override // okio.BufferedSource
    public long readLongLe() {
        return io.a(readLong());
    }

    @Override // okio.BufferedSource
    public short readShort() {
        long j = this.b;
        if (j < 2) {
            StringBuilder sbA = g9.a("size < 2: ");
            sbA.append(this.b);
            throw new IllegalStateException(sbA.toString());
        }
        fo foVar = this.a;
        int i = foVar.b;
        int i2 = foVar.c;
        if (i2 - i < 2) {
            return (short) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        byte[] bArr = foVar.a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i] & 255) << 8) | (bArr[i3] & 255);
        this.b = j - 2;
        if (i4 == i2) {
            this.a = foVar.a();
            go.a(foVar);
        } else {
            foVar.b = i4;
        }
        return (short) i5;
    }

    @Override // okio.BufferedSource
    public short readShortLe() {
        return io.a(readShort());
    }

    @Override // okio.BufferedSource
    public String readString(Charset charset) {
        try {
            return readString(this.b, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final UnsafeCursor readUnsafe() {
        return readUnsafe(new UnsafeCursor());
    }

    @Override // okio.BufferedSource
    public String readUtf8() {
        try {
            return readString(this.b, io.a);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // okio.BufferedSource
    public int readUtf8CodePoint() throws EOFException {
        int i;
        int i2;
        int i3;
        if (this.b == 0) {
            throw new EOFException();
        }
        byte b2 = getByte(0L);
        if ((b2 & 128) == 0) {
            i = b2 & 127;
            i3 = 0;
            i2 = 1;
        } else if ((b2 & 224) == 192) {
            i = b2 & 31;
            i2 = 2;
            i3 = 128;
        } else if ((b2 & 240) == 224) {
            i = b2 & 15;
            i2 = 3;
            i3 = 2048;
        } else {
            if ((b2 & 248) != 240) {
                skip(1L);
                return 65533;
            }
            i = b2 & 7;
            i2 = 4;
            i3 = 65536;
        }
        long j = i2;
        if (this.b < j) {
            StringBuilder sbA = g9.a("size < ", i2, ": ");
            sbA.append(this.b);
            sbA.append(" (to read code point prefixed 0x");
            sbA.append(Integer.toHexString(b2));
            sbA.append(")");
            throw new EOFException(sbA.toString());
        }
        for (int i4 = 1; i4 < i2; i4++) {
            long j2 = i4;
            byte b3 = getByte(j2);
            if ((b3 & 192) != 128) {
                skip(j2);
                return 65533;
            }
            i = (i << 6) | (b3 & 63);
        }
        skip(j);
        if (i > 1114111) {
            return 65533;
        }
        if ((i < 55296 || i > 57343) && i >= i3) {
            return i;
        }
        return 65533;
    }

    @Override // okio.BufferedSource
    @Nullable
    public String readUtf8Line() throws EOFException {
        long jIndexOf = indexOf((byte) 10);
        if (jIndexOf != -1) {
            return a(jIndexOf);
        }
        long j = this.b;
        if (j != 0) {
            return readUtf8(j);
        }
        return null;
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict() throws EOFException {
        return readUtf8LineStrict(Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public boolean request(long j) {
        return this.b >= j;
    }

    @Override // okio.BufferedSource
    public void require(long j) throws EOFException {
        if (this.b < j) {
            throw new EOFException();
        }
    }

    @Override // okio.BufferedSource
    public int select(Options options) {
        int iA = a(options, false);
        if (iA == -1) {
            return -1;
        }
        try {
            skip(options.a[iA].size());
            return iA;
        } catch (EOFException unused) {
            throw new AssertionError();
        }
    }

    public final ByteString sha1() {
        return a("SHA-1");
    }

    public final ByteString sha256() {
        return a("SHA-256");
    }

    public final ByteString sha512() {
        return a("SHA-512");
    }

    public final long size() {
        return this.b;
    }

    @Override // okio.BufferedSource
    public void skip(long j) throws EOFException {
        while (j > 0) {
            if (this.a == null) {
                throw new EOFException();
            }
            int iMin = (int) Math.min(j, r0.c - r0.b);
            long j2 = iMin;
            this.b -= j2;
            j -= j2;
            fo foVar = this.a;
            int i = foVar.b + iMin;
            foVar.b = i;
            if (i == foVar.c) {
                this.a = foVar.a();
                go.a(foVar);
            }
        }
    }

    public final ByteString snapshot() {
        long j = this.b;
        if (j <= 2147483647L) {
            return snapshot((int) j);
        }
        StringBuilder sbA = g9.a("size > Integer.MAX_VALUE: ");
        sbA.append(this.b);
        throw new IllegalArgumentException(sbA.toString());
    }

    @Override // okio.Source
    public Timeout timeout() {
        return Timeout.NONE;
    }

    public String toString() {
        return snapshot().toString();
    }

    @Override // okio.BufferedSink
    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long j2 = source.read(this, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
            if (j2 == -1) {
                return j;
            }
            j += j2;
        }
    }

    public final Buffer writeTo(OutputStream outputStream) throws IOException {
        return writeTo(outputStream, this.b);
    }

    public Buffer clone() {
        Buffer buffer = new Buffer();
        if (this.b == 0) {
            return buffer;
        }
        fo foVarB = this.a.b();
        buffer.a = foVarB;
        foVarB.g = foVarB;
        foVarB.f = foVarB;
        fo foVar = this.a;
        while (true) {
            foVar = foVar.f;
            if (foVar == this.a) {
                buffer.b = this.b;
                return buffer;
            }
            buffer.a.g.a(foVar.b());
        }
    }

    public final Buffer copyTo(OutputStream outputStream, long j, long j2) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        io.a(this.b, j, j2);
        if (j2 == 0) {
            return this;
        }
        fo foVar = this.a;
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
            int iMin = (int) Math.min(foVar.c - r9, j2);
            outputStream.write(foVar.a, (int) (foVar.b + j), iMin);
            j2 -= iMin;
            foVar = foVar.f;
            j = 0;
        }
        return this;
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b2, long j) {
        return indexOf(b2, j, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString byteString, long j) {
        int i;
        int i2;
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        fo foVar = this.a;
        if (foVar == null) {
            return -1L;
        }
        long j3 = this.b;
        if (j3 - j < j) {
            while (j3 > j) {
                foVar = foVar.g;
                j3 -= foVar.c - foVar.b;
            }
        } else {
            while (true) {
                long j4 = (foVar.c - foVar.b) + j2;
                if (j4 >= j) {
                    break;
                }
                foVar = foVar.f;
                j2 = j4;
            }
            j3 = j2;
        }
        if (byteString.size() == 2) {
            byte b2 = byteString.getByte(0);
            byte b3 = byteString.getByte(1);
            while (j3 < this.b) {
                byte[] bArr = foVar.a;
                i = (int) ((foVar.b + j) - j3);
                int i3 = foVar.c;
                while (i < i3) {
                    byte b4 = bArr[i];
                    if (b4 == b2 || b4 == b3) {
                        i2 = foVar.b;
                        return (i - i2) + j3;
                    }
                    i++;
                }
                j3 += foVar.c - foVar.b;
                foVar = foVar.f;
                j = j3;
            }
            return -1L;
        }
        byte[] bArrA = byteString.a();
        while (j3 < this.b) {
            byte[] bArr2 = foVar.a;
            i = (int) ((foVar.b + j) - j3);
            int i4 = foVar.c;
            while (i < i4) {
                byte b5 = bArr2[i];
                for (byte b6 : bArrA) {
                    if (b5 == b6) {
                        i2 = foVar.b;
                        return (i - i2) + j3;
                    }
                }
                i++;
            }
            j3 += foVar.c - foVar.b;
            foVar = foVar.f;
            j = j3;
        }
        return -1L;
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long j, ByteString byteString, int i, int i2) {
        if (j < 0 || i < 0 || i2 < 0 || this.b - j < i2 || byteString.size() - i < i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (getByte(i3 + j) != byteString.getByte(i + i3)) {
                return false;
            }
        }
        return true;
    }

    @Override // okio.BufferedSource
    public int read(byte[] bArr, int i, int i2) {
        io.a(bArr.length, i, i2);
        fo foVar = this.a;
        if (foVar == null) {
            return -1;
        }
        int iMin = Math.min(i2, foVar.c - foVar.b);
        System.arraycopy(foVar.a, foVar.b, bArr, i, iMin);
        int i3 = foVar.b + iMin;
        foVar.b = i3;
        this.b -= iMin;
        if (i3 == foVar.c) {
            this.a = foVar.a();
            go.a(foVar);
        }
        return iMin;
    }

    public final UnsafeCursor readAndWriteUnsafe(UnsafeCursor unsafeCursor) {
        if (unsafeCursor.buffer != null) {
            throw new IllegalStateException("already attached to a buffer");
        }
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = true;
        return unsafeCursor;
    }

    @Override // okio.BufferedSource
    public ByteString readByteString(long j) throws EOFException {
        return new ByteString(readByteArray(j));
    }

    public final Buffer readFrom(InputStream inputStream, long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
        }
        a(inputStream, j, false);
        return this;
    }

    public final UnsafeCursor readUnsafe(UnsafeCursor unsafeCursor) {
        if (unsafeCursor.buffer != null) {
            throw new IllegalStateException("already attached to a buffer");
        }
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = false;
        return unsafeCursor;
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict(long j) throws EOFException {
        if (j < 0) {
            throw new IllegalArgumentException(g9.a("limit < 0: ", j));
        }
        long j2 = j != Long.MAX_VALUE ? j + 1 : Long.MAX_VALUE;
        long jIndexOf = indexOf((byte) 10, 0L, j2);
        if (jIndexOf != -1) {
            return a(jIndexOf);
        }
        if (j2 < size() && getByte(j2 - 1) == 13 && getByte(j2) == 10) {
            return a(j2);
        }
        Buffer buffer = new Buffer();
        copyTo(buffer, 0L, Math.min(32L, size()));
        StringBuilder sbA = g9.a("\\n not found: limit=");
        sbA.append(Math.min(size(), j));
        sbA.append(" content=");
        sbA.append(buffer.readByteString().hex());
        sbA.append((char) 8230);
        throw new EOFException(sbA.toString());
    }

    @Override // okio.BufferedSink
    public Buffer writeByte(int i) {
        fo foVarA = a(1);
        byte[] bArr = foVarA.a;
        int i2 = foVarA.c;
        foVarA.c = i2 + 1;
        bArr[i2] = (byte) i;
        this.b++;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeDecimalLong(long j) {
        if (j == 0) {
            return writeByte(48);
        }
        boolean z = false;
        int i = 1;
        if (j < 0) {
            j = -j;
            if (j < 0) {
                return writeUtf8("-9223372036854775808");
            }
            z = true;
        }
        if (j >= 100000000) {
            i = j < 1000000000000L ? j < 10000000000L ? j < 1000000000 ? 9 : 10 : j < 100000000000L ? 11 : 12 : j < 1000000000000000L ? j < 10000000000000L ? 13 : j < 100000000000000L ? 14 : 15 : j < 100000000000000000L ? j < 10000000000000000L ? 16 : 17 : j < 1000000000000000000L ? 18 : 19;
        } else if (j >= 10000) {
            i = j < 1000000 ? j < 100000 ? 5 : 6 : j < 10000000 ? 7 : 8;
        } else if (j >= 100) {
            i = j < 1000 ? 3 : 4;
        } else if (j >= 10) {
            i = 2;
        }
        if (z) {
            i++;
        }
        fo foVarA = a(i);
        byte[] bArr = foVarA.a;
        int i2 = foVarA.c + i;
        while (j != 0) {
            i2--;
            bArr[i2] = c[(int) (j % 10)];
            j /= 10;
        }
        if (z) {
            bArr[i2 - 1] = 45;
        }
        foVarA.c += i;
        this.b += i;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeHexadecimalUnsignedLong(long j) {
        if (j == 0) {
            return writeByte(48);
        }
        int iNumberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        fo foVarA = a(iNumberOfTrailingZeros);
        byte[] bArr = foVarA.a;
        int i = foVarA.c;
        for (int i2 = (i + iNumberOfTrailingZeros) - 1; i2 >= i; i2--) {
            bArr[i2] = c[(int) (15 & j)];
            j >>>= 4;
        }
        foVarA.c += iNumberOfTrailingZeros;
        this.b += iNumberOfTrailingZeros;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeInt(int i) {
        fo foVarA = a(4);
        byte[] bArr = foVarA.a;
        int i2 = foVarA.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        bArr[i5] = (byte) (i & 255);
        foVarA.c = i5 + 1;
        this.b += 4;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeIntLe(int i) {
        return writeInt(io.a(i));
    }

    @Override // okio.BufferedSink
    public Buffer writeLong(long j) {
        fo foVarA = a(8);
        byte[] bArr = foVarA.a;
        int i = foVarA.c;
        int i2 = i + 1;
        bArr[i] = (byte) ((j >>> 56) & 255);
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((j >>> 48) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((j >>> 40) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((j >>> 32) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((j >>> 24) & 255);
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((j >>> 16) & 255);
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((j >>> 8) & 255);
        bArr[i8] = (byte) (j & 255);
        foVarA.c = i8 + 1;
        this.b += 8;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeLongLe(long j) {
        return writeLong(io.a(j));
    }

    @Override // okio.BufferedSink
    public Buffer writeShort(int i) {
        fo foVarA = a(2);
        byte[] bArr = foVarA.a;
        int i2 = foVarA.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        bArr[i3] = (byte) (i & 255);
        foVarA.c = i3 + 1;
        this.b += 2;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeShortLe(int i) {
        return writeShort((int) io.a((short) i));
    }

    public final Buffer writeTo(OutputStream outputStream, long j) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        io.a(this.b, 0L, j);
        fo foVar = this.a;
        while (j > 0) {
            int iMin = (int) Math.min(j, foVar.c - foVar.b);
            outputStream.write(foVar.a, foVar.b, iMin);
            int i = foVar.b + iMin;
            foVar.b = i;
            long j2 = iMin;
            this.b -= j2;
            j -= j2;
            if (i == foVar.c) {
                fo foVarA = foVar.a();
                this.a = foVarA;
                go.a(foVar);
                foVar = foVarA;
            }
        }
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeUtf8CodePoint(int i) {
        if (i < 128) {
            writeByte(i);
        } else if (i < 2048) {
            writeByte((i >> 6) | 192);
            writeByte((i & 63) | 128);
        } else if (i < 65536) {
            if (i < 55296 || i > 57343) {
                writeByte((i >> 12) | 224);
                writeByte(((i >> 6) & 63) | 128);
                writeByte((i & 63) | 128);
            } else {
                writeByte(63);
            }
        } else {
            if (i > 1114111) {
                StringBuilder sbA = g9.a("Unexpected code point: ");
                sbA.append(Integer.toHexString(i));
                throw new IllegalArgumentException(sbA.toString());
            }
            writeByte((i >> 18) | 240);
            writeByte(((i >> 12) & 63) | 128);
            writeByte(((i >> 6) & 63) | 128);
            writeByte((i & 63) | 128);
        }
        return this;
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b2, long j, long j2) {
        fo foVar;
        long j3 = 0;
        if (j >= 0 && j2 >= j) {
            long j4 = this.b;
            if (j2 <= j4) {
                j4 = j2;
            }
            if (j == j4 || (foVar = this.a) == null) {
                return -1L;
            }
            long j5 = this.b;
            if (j5 - j < j) {
                while (j5 > j) {
                    foVar = foVar.g;
                    j5 -= foVar.c - foVar.b;
                }
            } else {
                while (true) {
                    long j6 = (foVar.c - foVar.b) + j3;
                    if (j6 >= j) {
                        break;
                    }
                    foVar = foVar.f;
                    j3 = j6;
                }
                j5 = j3;
            }
            long j7 = j;
            while (j5 < j4) {
                byte[] bArr = foVar.a;
                int iMin = (int) Math.min(foVar.c, (foVar.b + j4) - j5);
                for (int i = (int) ((foVar.b + j7) - j5); i < iMin; i++) {
                    if (bArr[i] == b2) {
                        return (i - foVar.b) + j5;
                    }
                }
                j5 += foVar.c - foVar.b;
                foVar = foVar.f;
                j7 = j5;
            }
            return -1L;
        }
        throw new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", Long.valueOf(this.b), Long.valueOf(j), Long.valueOf(j2)));
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray(long j) throws EOFException {
        io.a(this.b, 0L, j);
        if (j <= 2147483647L) {
            byte[] bArr = new byte[(int) j];
            readFully(bArr);
            return bArr;
        }
        throw new IllegalArgumentException(g9.a("byteCount > Integer.MAX_VALUE: ", j));
    }

    @Override // okio.BufferedSource
    public String readString(long j, Charset charset) throws EOFException {
        io.a(this.b, 0L, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (j > 2147483647L) {
            throw new IllegalArgumentException(g9.a("byteCount > Integer.MAX_VALUE: ", j));
        }
        if (j == 0) {
            return "";
        }
        fo foVar = this.a;
        if (foVar.b + j > foVar.c) {
            return new String(readByteArray(j), charset);
        }
        String str = new String(foVar.a, foVar.b, (int) j, charset);
        int i = (int) (foVar.b + j);
        foVar.b = i;
        this.b -= j;
        if (i == foVar.c) {
            this.a = foVar.a();
            go.a(foVar);
        }
        return str;
    }

    @Override // okio.BufferedSource
    public String readUtf8(long j) throws EOFException {
        return readString(j, io.a);
    }

    @Override // okio.BufferedSink
    public Buffer writeString(String str, Charset charset) {
        return writeString(str, 0, str.length(), charset);
    }

    @Override // okio.BufferedSink
    public Buffer writeUtf8(String str) {
        return writeUtf8(str, 0, str.length());
    }

    public final ByteString snapshot(int i) {
        if (i == 0) {
            return ByteString.EMPTY;
        }
        return new ho(this, i);
    }

    @Override // okio.BufferedSink
    public Buffer write(ByteString byteString) {
        if (byteString != null) {
            byteString.a(this);
            return this;
        }
        throw new IllegalArgumentException("byteString == null");
    }

    @Override // okio.BufferedSink
    public Buffer writeString(String str, int i, int i2, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i < 0) {
            throw new IllegalAccessError(g9.b("beginIndex < 0: ", i));
        }
        if (i2 >= i) {
            if (i2 > str.length()) {
                StringBuilder sbA = g9.a("endIndex > string.length: ", i2, " > ");
                sbA.append(str.length());
                throw new IllegalArgumentException(sbA.toString());
            }
            if (charset != null) {
                if (charset.equals(io.a)) {
                    return writeUtf8(str, i, i2);
                }
                byte[] bytes = str.substring(i, i2).getBytes(charset);
                return write(bytes, 0, bytes.length);
            }
            throw new IllegalArgumentException("charset == null");
        }
        throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
    }

    @Override // okio.BufferedSink
    public Buffer writeUtf8(String str, int i, int i2) {
        char cCharAt;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException(g9.b("beginIndex < 0: ", i));
        }
        if (i2 >= i) {
            if (i2 > str.length()) {
                StringBuilder sbA = g9.a("endIndex > string.length: ", i2, " > ");
                sbA.append(str.length());
                throw new IllegalArgumentException(sbA.toString());
            }
            while (i < i2) {
                char cCharAt2 = str.charAt(i);
                if (cCharAt2 < 128) {
                    fo foVarA = a(1);
                    byte[] bArr = foVarA.a;
                    int i3 = foVarA.c - i;
                    int iMin = Math.min(i2, 8192 - i3);
                    int i4 = i + 1;
                    bArr[i + i3] = (byte) cCharAt2;
                    while (true) {
                        i = i4;
                        if (i >= iMin || (cCharAt = str.charAt(i)) >= 128) {
                            break;
                        }
                        i4 = i + 1;
                        bArr[i + i3] = (byte) cCharAt;
                    }
                    int i5 = foVarA.c;
                    int i6 = (i3 + i) - i5;
                    foVarA.c = i5 + i6;
                    this.b += i6;
                } else {
                    if (cCharAt2 < 2048) {
                        writeByte((cCharAt2 >> 6) | 192);
                        writeByte((cCharAt2 & '?') | 128);
                    } else if (cCharAt2 >= 55296 && cCharAt2 <= 57343) {
                        int i7 = i + 1;
                        char cCharAt3 = i7 < i2 ? str.charAt(i7) : (char) 0;
                        if (cCharAt2 <= 56319 && cCharAt3 >= 56320 && cCharAt3 <= 57343) {
                            int i8 = (((cCharAt2 & 10239) << 10) | (9215 & cCharAt3)) + 65536;
                            writeByte((i8 >> 18) | 240);
                            writeByte(((i8 >> 12) & 63) | 128);
                            writeByte(((i8 >> 6) & 63) | 128);
                            writeByte((i8 & 63) | 128);
                            i += 2;
                        } else {
                            writeByte(63);
                            i = i7;
                        }
                    } else {
                        writeByte((cCharAt2 >> '\f') | 224);
                        writeByte(((cCharAt2 >> 6) & 63) | 128);
                        writeByte((cCharAt2 & '?') | 128);
                    }
                    i++;
                }
            }
            return this;
        }
        throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
    }

    @Override // okio.BufferedSource
    public void readFully(byte[] bArr) throws EOFException {
        int i = 0;
        while (i < bArr.length) {
            int i2 = read(bArr, i, bArr.length - i);
            if (i2 == -1) {
                throw new EOFException();
            }
            i += i2;
        }
    }

    @Override // okio.BufferedSink
    public Buffer write(byte[] bArr) {
        if (bArr != null) {
            return write(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0055, code lost:
    
        if (r19 == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0058, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int a(okio.Options r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 161
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.a(okio.Options, boolean):int");
    }

    @Override // okio.BufferedSink
    public Buffer write(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            long j = i2;
            io.a(bArr.length, i, j);
            int i3 = i2 + i;
            while (i < i3) {
                fo foVarA = a(1);
                int iMin = Math.min(i3 - i, 8192 - foVarA.c);
                System.arraycopy(bArr, i, foVarA.a, foVarA.c, iMin);
                i += iMin;
                foVarA.c += iMin;
            }
            this.b += j;
            return this;
        }
        throw new IllegalArgumentException("source == null");
    }

    public final Buffer copyTo(Buffer buffer, long j, long j2) {
        if (buffer != null) {
            io.a(this.b, j, j2);
            if (j2 == 0) {
                return this;
            }
            buffer.b += j2;
            fo foVar = this.a;
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
                fo foVarB = foVar.b();
                int i3 = (int) (foVarB.b + j);
                foVarB.b = i3;
                foVarB.c = Math.min(i3 + ((int) j2), foVarB.c);
                fo foVar2 = buffer.a;
                if (foVar2 == null) {
                    foVarB.g = foVarB;
                    foVarB.f = foVarB;
                    buffer.a = foVarB;
                } else {
                    foVar2.g.a(foVarB);
                }
                j2 -= foVarB.c - foVarB.b;
                foVar = foVar.f;
                j = 0;
            }
            return this;
        }
        throw new IllegalArgumentException("out == null");
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) throws IOException {
        fo foVar = this.a;
        if (foVar == null) {
            return -1;
        }
        int iMin = Math.min(byteBuffer.remaining(), foVar.c - foVar.b);
        byteBuffer.put(foVar.a, foVar.b, iMin);
        int i = foVar.b + iMin;
        foVar.b = i;
        this.b -= iMin;
        if (i == foVar.c) {
            this.a = foVar.a();
            go.a(foVar);
        }
        return iMin;
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer != null) {
            int iRemaining = byteBuffer.remaining();
            int i = iRemaining;
            while (i > 0) {
                fo foVarA = a(1);
                int iMin = Math.min(i, 8192 - foVarA.c);
                byteBuffer.get(foVarA.a, foVarA.c, iMin);
                i -= iMin;
                foVarA.c += iMin;
            }
            this.b += iRemaining;
            return iRemaining;
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString byteString) throws IOException {
        return indexOf(byteString, 0L);
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j >= 0) {
            long j2 = this.b;
            if (j2 == 0) {
                return -1L;
            }
            if (j > j2) {
                j = j2;
            }
            buffer.write(this, j);
            return j;
        }
        throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString byteString, long j) throws IOException {
        int i;
        boolean z;
        fo foVar;
        if (byteString.size() == 0) {
            throw new IllegalArgumentException("bytes is empty");
        }
        long j2 = 0;
        if (j >= 0) {
            fo foVar2 = this.a;
            long j3 = -1;
            if (foVar2 == null) {
                return -1L;
            }
            long j4 = this.b;
            if (j4 - j < j) {
                while (j4 > j) {
                    foVar2 = foVar2.g;
                    j4 -= foVar2.c - foVar2.b;
                }
            } else {
                while (true) {
                    long j5 = (foVar2.c - foVar2.b) + j2;
                    if (j5 >= j) {
                        break;
                    }
                    foVar2 = foVar2.f;
                    j2 = j5;
                }
                j4 = j2;
            }
            byte b2 = byteString.getByte(0);
            int size = byteString.size();
            long j6 = (this.b - size) + 1;
            long j7 = j4;
            long j8 = j;
            while (j7 < j6) {
                byte[] bArr = foVar2.a;
                byte b3 = b2;
                int iMin = (int) Math.min(foVar2.c, (foVar2.b + j6) - j7);
                int i2 = (int) ((foVar2.b + j8) - j7);
                while (i2 < iMin) {
                    byte b4 = b3;
                    if (bArr[i2] == b4) {
                        int i3 = i2 + 1;
                        int i4 = foVar2.c;
                        byte[] bArr2 = foVar2.a;
                        fo foVar3 = foVar2;
                        int i5 = 1;
                        while (true) {
                            if (i5 >= size) {
                                i = iMin;
                                z = true;
                                break;
                            }
                            if (i3 == i4) {
                                fo foVar4 = foVar3.f;
                                foVar = foVar4;
                                bArr2 = foVar4.a;
                                i3 = foVar4.b;
                                i4 = foVar4.c;
                            } else {
                                foVar = foVar3;
                            }
                            i = iMin;
                            if (bArr2[i3] != byteString.getByte(i5)) {
                                z = false;
                                break;
                            }
                            i3++;
                            i5++;
                            foVar3 = foVar;
                            iMin = i;
                        }
                        if (z) {
                            return (i2 - foVar2.b) + j7;
                        }
                    } else {
                        i = iMin;
                    }
                    i2++;
                    iMin = i;
                    b3 = b4;
                }
                j7 += foVar2.c - foVar2.b;
                foVar2 = foVar2.f;
                b2 = b3;
                j8 = j7;
                j3 = -1;
            }
            return j3;
        }
        throw new IllegalArgumentException("fromIndex < 0");
    }

    @Override // okio.BufferedSink
    public BufferedSink write(Source source, long j) throws IOException {
        while (j > 0) {
            long j2 = source.read(this, j);
            if (j2 == -1) {
                throw new EOFException();
            }
            j -= j2;
        }
        return this;
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j) {
        fo foVarA;
        if (buffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (buffer != this) {
            io.a(buffer.b, 0L, j);
            while (j > 0) {
                fo foVar = buffer.a;
                if (j < foVar.c - foVar.b) {
                    fo foVar2 = this.a;
                    fo foVar3 = foVar2 != null ? foVar2.g : null;
                    if (foVar3 != null && foVar3.e) {
                        if ((foVar3.c + j) - (foVar3.d ? 0 : foVar3.b) <= PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
                            buffer.a.a(foVar3, (int) j);
                            buffer.b -= j;
                            this.b += j;
                            return;
                        }
                    }
                    fo foVar4 = buffer.a;
                    int i = (int) j;
                    if (foVar4 != null) {
                        if (i > 0 && i <= foVar4.c - foVar4.b) {
                            if (i >= 1024) {
                                foVarA = foVar4.b();
                            } else {
                                foVarA = go.a();
                                System.arraycopy(foVar4.a, foVar4.b, foVarA.a, 0, i);
                            }
                            foVarA.c = foVarA.b + i;
                            foVar4.b += i;
                            foVar4.g.a(foVarA);
                            buffer.a = foVarA;
                        } else {
                            throw new IllegalArgumentException();
                        }
                    } else {
                        throw null;
                    }
                }
                fo foVar5 = buffer.a;
                long j2 = foVar5.c - foVar5.b;
                buffer.a = foVar5.a();
                fo foVar6 = this.a;
                if (foVar6 == null) {
                    this.a = foVar5;
                    foVar5.g = foVar5;
                    foVar5.f = foVar5;
                } else {
                    foVar6.g.a(foVar5);
                    fo foVar7 = foVar5.g;
                    if (foVar7 != foVar5) {
                        if (foVar7.e) {
                            int i2 = foVar5.c - foVar5.b;
                            if (i2 <= (8192 - foVar7.c) + (foVar7.d ? 0 : foVar7.b)) {
                                foVar5.a(foVar5.g, i2);
                                foVar5.a();
                                go.a(foVar5);
                            }
                        }
                    } else {
                        throw new IllegalStateException();
                    }
                }
                buffer.b -= j2;
                this.b += j2;
                j -= j2;
            }
            return;
        }
        throw new IllegalArgumentException("source == this");
    }

    public String a(long j) throws EOFException {
        if (j > 0) {
            long j2 = j - 1;
            if (getByte(j2) == 13) {
                String utf8 = readUtf8(j2);
                skip(2L);
                return utf8;
            }
        }
        String utf82 = readUtf8(j);
        skip(1L);
        return utf82;
    }

    public fo a(int i) {
        if (i >= 1 && i <= 8192) {
            fo foVar = this.a;
            if (foVar == null) {
                fo foVarA = go.a();
                this.a = foVarA;
                foVarA.g = foVarA;
                foVarA.f = foVarA;
                return foVarA;
            }
            fo foVar2 = foVar.g;
            if (foVar2.c + i <= 8192 && foVar2.e) {
                return foVar2;
            }
            fo foVarA2 = go.a();
            foVar2.a(foVarA2);
            return foVarA2;
        }
        throw new IllegalArgumentException();
    }

    public final ByteString a(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(str);
            if (this.a != null) {
                messageDigest.update(this.a.a, this.a.b, this.a.c - this.a.b);
                fo foVar = this.a;
                while (true) {
                    foVar = foVar.f;
                    if (foVar == this.a) {
                        break;
                    }
                    messageDigest.update(foVar.a, foVar.b, foVar.c - foVar.b);
                }
            }
            return ByteString.of(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public final ByteString a(String str, ByteString byteString) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Mac mac = Mac.getInstance(str);
            mac.init(new SecretKeySpec(byteString.toByteArray(), str));
            if (this.a != null) {
                mac.update(this.a.a, this.a.b, this.a.c - this.a.b);
                fo foVar = this.a;
                while (true) {
                    foVar = foVar.f;
                    if (foVar == this.a) {
                        break;
                    }
                    mac.update(foVar.a, foVar.b, foVar.c - foVar.b);
                }
            }
            return ByteString.of(mac.doFinal());
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }
}
