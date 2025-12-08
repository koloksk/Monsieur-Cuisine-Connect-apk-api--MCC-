package defpackage;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* loaded from: classes.dex */
public final class eo implements BufferedSource {
    public final Buffer a = new Buffer();
    public final Source b;
    public boolean c;

    public eo(Source source) {
        if (source == null) {
            throw new NullPointerException("source == null");
        }
        this.b = source;
    }

    @Override // okio.BufferedSource
    public Buffer buffer() {
        return this.a;
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.c) {
            return;
        }
        this.c = true;
        this.b.close();
        this.a.clear();
    }

    @Override // okio.BufferedSource
    public boolean exhausted() throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        return this.a.exhausted() && this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1;
    }

    @Override // okio.BufferedSource
    public Buffer getBuffer() {
        return this.a;
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b) throws IOException {
        return indexOf(b, 0L, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString byteString) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long jMax = 0;
        while (true) {
            long jIndexOfElement = this.a.indexOfElement(byteString, jMax);
            if (jIndexOfElement != -1) {
                return jIndexOfElement;
            }
            Buffer buffer = this.a;
            long j = buffer.b;
            if (this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1L;
            }
            jMax = Math.max(jMax, j);
        }
    }

    @Override // okio.BufferedSource
    public InputStream inputStream() {
        return new a();
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.c;
    }

    @Override // okio.BufferedSource
    public BufferedSource peek() {
        return Okio.buffer(new bo(this));
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long j, ByteString byteString) throws IOException {
        int size = byteString.size();
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        if (j < 0 || size < 0 || byteString.size() - 0 < size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            long j2 = i + j;
            if (!request(1 + j2) || this.a.getByte(j2) != byteString.getByte(0 + i)) {
                return false;
            }
        }
        return true;
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) throws IOException {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j < 0) {
            throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
        }
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        Buffer buffer2 = this.a;
        if (buffer2.b == 0 && this.b.read(buffer2, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1L;
        }
        return this.a.read(buffer, Math.min(j, this.a.b));
    }

    @Override // okio.BufferedSource
    public long readAll(Sink sink) throws IOException {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        long j = 0;
        while (this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1) {
            long jCompleteSegmentByteCount = this.a.completeSegmentByteCount();
            if (jCompleteSegmentByteCount > 0) {
                j += jCompleteSegmentByteCount;
                sink.write(this.a, jCompleteSegmentByteCount);
            }
        }
        if (this.a.size() <= 0) {
            return j;
        }
        long size = j + this.a.size();
        Buffer buffer = this.a;
        sink.write(buffer, buffer.size());
        return size;
    }

    @Override // okio.BufferedSource
    public byte readByte() throws IOException {
        require(1L);
        return this.a.readByte();
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray(long j) throws IOException {
        if (request(j)) {
            return this.a.readByteArray(j);
        }
        throw new EOFException();
    }

    @Override // okio.BufferedSource
    public ByteString readByteString(long j) throws IOException {
        if (request(j)) {
            return this.a.readByteString(j);
        }
        throw new EOFException();
    }

    @Override // okio.BufferedSource
    public long readDecimalLong() throws IOException {
        byte b;
        require(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!request(i2)) {
                break;
            }
            b = this.a.getByte(i);
            if ((b < 48 || b > 57) && !(i == 0 && b == 45)) {
                break;
            }
            i = i2;
        }
        if (i == 0) {
            throw new NumberFormatException(String.format("Expected leading [0-9] or '-' character but was %#x", Byte.valueOf(b)));
        }
        return this.a.readDecimalLong();
    }

    @Override // okio.BufferedSource
    public void readFully(Buffer buffer, long j) throws IOException {
        try {
            if (!request(j)) {
                throw new EOFException();
            }
            this.a.readFully(buffer, j);
        } catch (EOFException e) {
            buffer.writeAll(this.a);
            throw e;
        }
    }

    @Override // okio.BufferedSource
    public long readHexadecimalUnsignedLong() throws IOException {
        byte b;
        require(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!request(i2)) {
                break;
            }
            b = this.a.getByte(i);
            if ((b < 48 || b > 57) && ((b < 97 || b > 102) && (b < 65 || b > 70))) {
                break;
            }
            i = i2;
        }
        if (i == 0) {
            throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", Byte.valueOf(b)));
        }
        return this.a.readHexadecimalUnsignedLong();
    }

    @Override // okio.BufferedSource
    public int readInt() throws IOException {
        require(4L);
        return this.a.readInt();
    }

    @Override // okio.BufferedSource
    public int readIntLe() throws IOException {
        require(4L);
        return this.a.readIntLe();
    }

    @Override // okio.BufferedSource
    public long readLong() throws IOException {
        require(8L);
        return this.a.readLong();
    }

    @Override // okio.BufferedSource
    public long readLongLe() throws IOException {
        require(8L);
        return this.a.readLongLe();
    }

    @Override // okio.BufferedSource
    public short readShort() throws IOException {
        require(2L);
        return this.a.readShort();
    }

    @Override // okio.BufferedSource
    public short readShortLe() throws IOException {
        require(2L);
        return this.a.readShortLe();
    }

    @Override // okio.BufferedSource
    public String readString(long j, Charset charset) throws IOException {
        if (!request(j)) {
            throw new EOFException();
        }
        if (charset != null) {
            return this.a.readString(j, charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    @Override // okio.BufferedSource
    public String readUtf8(long j) throws IOException {
        if (request(j)) {
            return this.a.readUtf8(j);
        }
        throw new EOFException();
    }

    @Override // okio.BufferedSource
    public int readUtf8CodePoint() throws IOException {
        require(1L);
        byte b = this.a.getByte(0L);
        if ((b & 224) == 192) {
            require(2L);
        } else if ((b & 240) == 224) {
            require(3L);
        } else if ((b & 248) == 240) {
            require(4L);
        }
        return this.a.readUtf8CodePoint();
    }

    @Override // okio.BufferedSource
    @Nullable
    public String readUtf8Line() throws IOException {
        long jIndexOf = indexOf((byte) 10);
        if (jIndexOf != -1) {
            return this.a.a(jIndexOf);
        }
        long j = this.a.b;
        if (j == 0) {
            return null;
        }
        if (request(j)) {
            return this.a.readUtf8(j);
        }
        throw new EOFException();
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict() throws IOException {
        return readUtf8LineStrict(Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public boolean request(long j) throws IOException {
        Buffer buffer;
        if (j < 0) {
            throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
        }
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        do {
            buffer = this.a;
            if (buffer.b >= j) {
                return true;
            }
        } while (this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1);
        return false;
    }

    @Override // okio.BufferedSource
    public void require(long j) throws IOException {
        if (!request(j)) {
            throw new EOFException();
        }
    }

    @Override // okio.BufferedSource
    public int select(Options options) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        do {
            int iA = this.a.a(options, true);
            if (iA == -1) {
                return -1;
            }
            if (iA != -2) {
                this.a.skip(options.a[iA].size());
                return iA;
            }
        } while (this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1);
        return -1;
    }

    @Override // okio.BufferedSource
    public void skip(long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (j > 0) {
            Buffer buffer = this.a;
            if (buffer.b == 0 && this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                throw new EOFException();
            }
            long jMin = Math.min(j, this.a.size());
            this.a.skip(jMin);
            j -= jMin;
        }
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.b.timeout();
    }

    public String toString() {
        StringBuilder sbA = g9.a("buffer(");
        sbA.append(this.b);
        sbA.append(")");
        return sbA.toString();
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b, long j) throws IOException {
        return indexOf(b, j, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict(long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException(g9.a("limit < 0: ", j));
        }
        long j2 = j == Long.MAX_VALUE ? Long.MAX_VALUE : j + 1;
        long jIndexOf = indexOf((byte) 10, 0L, j2);
        if (jIndexOf != -1) {
            return this.a.a(jIndexOf);
        }
        if (j2 < Long.MAX_VALUE && request(j2) && this.a.getByte(j2 - 1) == 13 && request(1 + j2) && this.a.getByte(j2) == 10) {
            return this.a.a(j2);
        }
        Buffer buffer = new Buffer();
        Buffer buffer2 = this.a;
        buffer2.copyTo(buffer, 0L, Math.min(32L, buffer2.size()));
        StringBuilder sbA = g9.a("\\n not found: limit=");
        sbA.append(Math.min(this.a.size(), j));
        sbA.append(" content=");
        sbA.append(buffer.readByteString().hex());
        sbA.append((char) 8230);
        throw new EOFException(sbA.toString());
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b, long j, long j2) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        if (j < 0 || j2 < j) {
            throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", Long.valueOf(j), Long.valueOf(j2)));
        }
        while (j < j2) {
            long jIndexOf = this.a.indexOf(b, j, j2);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            Buffer buffer = this.a;
            long j3 = buffer.b;
            if (j3 >= j2 || this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                break;
            }
            j = Math.max(j, j3);
        }
        return -1L;
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray() throws IOException {
        this.a.writeAll(this.b);
        return this.a.readByteArray();
    }

    @Override // okio.BufferedSource
    public ByteString readByteString() throws IOException {
        this.a.writeAll(this.b);
        return this.a.readByteString();
    }

    @Override // okio.BufferedSource
    public String readUtf8() throws IOException {
        this.a.writeAll(this.b);
        return this.a.readUtf8();
    }

    public class a extends InputStream {
        public a() {
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            eo eoVar = eo.this;
            if (eoVar.c) {
                throw new IOException("closed");
            }
            return (int) Math.min(eoVar.a.b, 2147483647L);
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            eo.this.close();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            eo eoVar = eo.this;
            if (eoVar.c) {
                throw new IOException("closed");
            }
            Buffer buffer = eoVar.a;
            if (buffer.b == 0 && eoVar.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1;
            }
            return eo.this.a.readByte() & 255;
        }

        public String toString() {
            return eo.this + ".inputStream()";
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            if (!eo.this.c) {
                io.a(bArr.length, i, i2);
                eo eoVar = eo.this;
                Buffer buffer = eoVar.a;
                if (buffer.b == 0 && eoVar.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return -1;
                }
                return eo.this.a.read(bArr, i, i2);
            }
            throw new IOException("closed");
        }
    }

    @Override // okio.BufferedSource
    public String readString(Charset charset) throws IOException {
        if (charset != null) {
            this.a.writeAll(this.b);
            return this.a.readString(charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    @Override // okio.BufferedSource
    public void readFully(byte[] bArr) throws IOException {
        try {
            require(bArr.length);
            this.a.readFully(bArr);
        } catch (EOFException e) {
            int i = 0;
            while (true) {
                Buffer buffer = this.a;
                long j = buffer.b;
                if (j > 0) {
                    int i2 = buffer.read(bArr, i, (int) j);
                    if (i2 == -1) {
                        throw new AssertionError();
                    }
                    i += i2;
                } else {
                    throw e;
                }
            }
        }
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString byteString, long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long jIndexOfElement = this.a.indexOfElement(byteString, j);
            if (jIndexOfElement != -1) {
                return jIndexOfElement;
            }
            Buffer buffer = this.a;
            long j2 = buffer.b;
            if (this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1L;
            }
            j = Math.max(j, j2);
        }
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long j, ByteString byteString, int i, int i2) throws IOException {
        if (!this.c) {
            if (j < 0 || i < 0 || i2 < 0 || byteString.size() - i < i2) {
                return false;
            }
            for (int i3 = 0; i3 < i2; i3++) {
                long j2 = i3 + j;
                if (!request(1 + j2) || this.a.getByte(j2) != byteString.getByte(i + i3)) {
                    return false;
                }
            }
            return true;
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSource
    public int read(byte[] bArr) throws IOException {
        long length = bArr.length;
        io.a(bArr.length, 0, length);
        Buffer buffer = this.a;
        if (buffer.b == 0 && this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        }
        return this.a.read(bArr, 0, (int) Math.min(length, this.a.b));
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString byteString) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long jMax = 0;
        while (true) {
            long jIndexOf = this.a.indexOf(byteString, jMax);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            Buffer buffer = this.a;
            long j = buffer.b;
            if (this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1L;
            }
            jMax = Math.max(jMax, (j - byteString.size()) + 1);
        }
    }

    @Override // okio.BufferedSource
    public int read(byte[] bArr, int i, int i2) throws IOException {
        long j = i2;
        io.a(bArr.length, i, j);
        Buffer buffer = this.a;
        if (buffer.b == 0 && this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        }
        return this.a.read(bArr, i, (int) Math.min(j, this.a.b));
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString byteString, long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long jIndexOf = this.a.indexOf(byteString, j);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            Buffer buffer = this.a;
            long j2 = buffer.b;
            if (this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1L;
            }
            j = Math.max(j, (j2 - byteString.size()) + 1);
        }
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) throws IOException {
        Buffer buffer = this.a;
        if (buffer.b == 0 && this.b.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        }
        return this.a.read(byteBuffer);
    }
}
