package defpackage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import okio.Buffer;
import okio.ByteString;

/* loaded from: classes.dex */
public final class ho extends ByteString {
    public final transient byte[][] e;
    public final transient int[] f;

    public ho(Buffer buffer, int i) {
        super(null);
        io.a(buffer.b, 0L, i);
        fo foVar = buffer.a;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            int i5 = foVar.c;
            int i6 = foVar.b;
            if (i5 == i6) {
                throw new AssertionError("s.limit == s.pos");
            }
            i3 += i5 - i6;
            i4++;
            foVar = foVar.f;
        }
        this.e = new byte[i4][];
        this.f = new int[i4 * 2];
        fo foVar2 = buffer.a;
        int i7 = 0;
        while (i2 < i) {
            this.e[i7] = foVar2.a;
            int i8 = (foVar2.c - foVar2.b) + i2;
            i2 = i8 > i ? i : i8;
            int[] iArr = this.f;
            iArr[i7] = i2;
            iArr[this.e.length + i7] = foVar2.b;
            foVar2.d = true;
            i7++;
            foVar2 = foVar2.f;
        }
    }

    private Object writeReplace() {
        return b();
    }

    public final int a(int i) {
        int iBinarySearch = Arrays.binarySearch(this.f, 0, this.e.length, i + 1);
        return iBinarySearch >= 0 ? iBinarySearch : ~iBinarySearch;
    }

    @Override // okio.ByteString
    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    public final ByteString b() {
        return new ByteString(toByteArray());
    }

    @Override // okio.ByteString
    public String base64() {
        return b().base64();
    }

    @Override // okio.ByteString
    public String base64Url() {
        return b().base64Url();
    }

    @Override // okio.ByteString
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == size() && rangeEquals(0, byteString, 0, size())) {
                return true;
            }
        }
        return false;
    }

    @Override // okio.ByteString
    public byte getByte(int i) {
        io.a(this.f[this.e.length - 1], i, 1L);
        int iA = a(i);
        int i2 = iA == 0 ? 0 : this.f[iA - 1];
        int[] iArr = this.f;
        byte[][] bArr = this.e;
        return bArr[iA][(i - i2) + iArr[bArr.length + iA]];
    }

    @Override // okio.ByteString
    public int hashCode() {
        int i = this.b;
        if (i != 0) {
            return i;
        }
        int length = this.e.length;
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        while (i2 < length) {
            byte[] bArr = this.e[i2];
            int[] iArr = this.f;
            int i5 = iArr[length + i2];
            int i6 = iArr[i2];
            int i7 = (i6 - i4) + i5;
            while (i5 < i7) {
                i3 = (i3 * 31) + bArr[i5];
                i5++;
            }
            i2++;
            i4 = i6;
        }
        this.b = i3;
        return i3;
    }

    @Override // okio.ByteString
    public String hex() {
        return b().hex();
    }

    @Override // okio.ByteString
    public ByteString hmacSha1(ByteString byteString) {
        return b().hmacSha1(byteString);
    }

    @Override // okio.ByteString
    public ByteString hmacSha256(ByteString byteString) {
        return b().hmacSha256(byteString);
    }

    @Override // okio.ByteString
    public int indexOf(byte[] bArr, int i) {
        return b().indexOf(bArr, i);
    }

    @Override // okio.ByteString
    public int lastIndexOf(byte[] bArr, int i) {
        return b().lastIndexOf(bArr, i);
    }

    @Override // okio.ByteString
    public ByteString md5() {
        return b().md5();
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int i, ByteString byteString, int i2, int i3) {
        if (i < 0 || i > size() - i3) {
            return false;
        }
        int iA = a(i);
        while (i3 > 0) {
            int i4 = iA == 0 ? 0 : this.f[iA - 1];
            int iMin = Math.min(i3, ((this.f[iA] - i4) + i4) - i);
            int[] iArr = this.f;
            byte[][] bArr = this.e;
            if (!byteString.rangeEquals(i2, bArr[iA], (i - i4) + iArr[bArr.length + iA], iMin)) {
                return false;
            }
            i += iMin;
            i2 += iMin;
            i3 -= iMin;
            iA++;
        }
        return true;
    }

    @Override // okio.ByteString
    public ByteString sha1() {
        return b().sha1();
    }

    @Override // okio.ByteString
    public ByteString sha256() {
        return b().sha256();
    }

    @Override // okio.ByteString
    public int size() {
        return this.f[this.e.length - 1];
    }

    @Override // okio.ByteString
    public String string(Charset charset) {
        return b().string(charset);
    }

    @Override // okio.ByteString
    public ByteString substring(int i) {
        return b().substring(i);
    }

    @Override // okio.ByteString
    public ByteString toAsciiLowercase() {
        return b().toAsciiLowercase();
    }

    @Override // okio.ByteString
    public ByteString toAsciiUppercase() {
        return b().toAsciiUppercase();
    }

    @Override // okio.ByteString
    public byte[] toByteArray() {
        int[] iArr = this.f;
        byte[][] bArr = this.e;
        byte[] bArr2 = new byte[iArr[bArr.length - 1]];
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int[] iArr2 = this.f;
            int i3 = iArr2[length + i];
            int i4 = iArr2[i];
            System.arraycopy(this.e[i], i3, bArr2, i2, i4 - i2);
            i++;
            i2 = i4;
        }
        return bArr2;
    }

    @Override // okio.ByteString
    public String toString() {
        return b().toString();
    }

    @Override // okio.ByteString
    public String utf8() {
        return b().utf8();
    }

    @Override // okio.ByteString
    public void write(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        int length = this.e.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int[] iArr = this.f;
            int i3 = iArr[length + i];
            int i4 = iArr[i];
            outputStream.write(this.e[i], i3, i4 - i2);
            i++;
            i2 = i4;
        }
    }

    @Override // okio.ByteString
    public void a(Buffer buffer) {
        int length = this.e.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int[] iArr = this.f;
            int i3 = iArr[length + i];
            int i4 = iArr[i];
            fo foVar = new fo(this.e[i], i3, (i3 + i4) - i2, true, false);
            fo foVar2 = buffer.a;
            if (foVar2 == null) {
                foVar.g = foVar;
                foVar.f = foVar;
                buffer.a = foVar;
            } else {
                foVar2.g.a(foVar);
            }
            i++;
            i2 = i4;
        }
        buffer.b += i2;
    }

    @Override // okio.ByteString
    public ByteString substring(int i, int i2) {
        return b().substring(i, i2);
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        if (i < 0 || i > size() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int iA = a(i);
        while (i3 > 0) {
            int i4 = iA == 0 ? 0 : this.f[iA - 1];
            int iMin = Math.min(i3, ((this.f[iA] - i4) + i4) - i);
            int[] iArr = this.f;
            byte[][] bArr2 = this.e;
            if (!io.a(bArr2[iA], (i - i4) + iArr[bArr2.length + iA], bArr, i2, iMin)) {
                return false;
            }
            i += iMin;
            i2 += iMin;
            i3 -= iMin;
            iA++;
        }
        return true;
    }

    @Override // okio.ByteString
    public byte[] a() {
        return toByteArray();
    }
}
