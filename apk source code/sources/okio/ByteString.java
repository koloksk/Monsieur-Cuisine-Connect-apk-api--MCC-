package okio;

import defpackage.g9;
import defpackage.io;
import defpackage.zn;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class ByteString implements Serializable, Comparable<ByteString> {
    public static final long serialVersionUID = 1;
    public final byte[] a;
    public transient int b;
    public transient String c;
    public static final char[] d = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final ByteString EMPTY = of(new byte[0]);

    public ByteString(byte[] bArr) {
        this.a = bArr;
    }

    @Nullable
    public static ByteString decodeBase64(String str) {
        int i;
        int i2;
        char cCharAt;
        if (str == null) {
            throw new IllegalArgumentException("base64 == null");
        }
        int length = str.length();
        while (length > 0 && ((cCharAt = str.charAt(length - 1)) == '=' || cCharAt == '\n' || cCharAt == '\r' || cCharAt == ' ' || cCharAt == '\t')) {
            length = i2;
        }
        int i3 = (int) ((length * 6) / 8);
        byte[] bArr = new byte[i3];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            if (i4 < length) {
                char cCharAt2 = str.charAt(i4);
                if (cCharAt2 >= 'A' && cCharAt2 <= 'Z') {
                    i = cCharAt2 - 'A';
                } else if (cCharAt2 >= 'a' && cCharAt2 <= 'z') {
                    i = cCharAt2 - 'G';
                } else if (cCharAt2 >= '0' && cCharAt2 <= '9') {
                    i = cCharAt2 + 4;
                } else if (cCharAt2 != '+' && cCharAt2 != '-') {
                    if (cCharAt2 != '/' && cCharAt2 != '_') {
                        if (cCharAt2 != '\n' && cCharAt2 != '\r' && cCharAt2 != ' ' && cCharAt2 != '\t') {
                            break;
                        }
                        i4++;
                    } else {
                        i = 63;
                    }
                } else {
                    i = 62;
                }
                i6 = (i6 << 6) | ((byte) i);
                i5++;
                if (i5 % 4 == 0) {
                    int i8 = i7 + 1;
                    bArr[i7] = (byte) (i6 >> 16);
                    int i9 = i8 + 1;
                    bArr[i8] = (byte) (i6 >> 8);
                    bArr[i9] = (byte) i6;
                    i7 = i9 + 1;
                }
                i4++;
            } else {
                int i10 = i5 % 4;
                if (i10 == 1) {
                    break;
                }
                if (i10 == 2) {
                    bArr[i7] = (byte) ((i6 << 12) >> 16);
                    i7++;
                } else if (i10 == 3) {
                    int i11 = i6 << 6;
                    int i12 = i7 + 1;
                    bArr[i7] = (byte) (i11 >> 16);
                    i7 = i12 + 1;
                    bArr[i12] = (byte) (i11 >> 8);
                }
                if (i7 != i3) {
                    byte[] bArr2 = new byte[i7];
                    System.arraycopy(bArr, 0, bArr2, 0, i7);
                    bArr = bArr2;
                }
            }
        }
        bArr = null;
        if (bArr != null) {
            return new ByteString(bArr);
        }
        return null;
    }

    public static ByteString decodeHex(String str) {
        if (str == null) {
            throw new IllegalArgumentException("hex == null");
        }
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException(g9.b("Unexpected hex string: ", str));
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (a(str.charAt(i2 + 1)) + (a(str.charAt(i2)) << 4));
        }
        return of(bArr);
    }

    public static ByteString encodeString(String str, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        if (charset != null) {
            return new ByteString(str.getBytes(charset));
        }
        throw new IllegalArgumentException("charset == null");
    }

    public static ByteString encodeUtf8(String str) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        ByteString byteString = new ByteString(str.getBytes(io.a));
        byteString.c = str;
        return byteString;
    }

    public static ByteString of(byte... bArr) {
        if (bArr != null) {
            return new ByteString((byte[]) bArr.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    public static ByteString read(InputStream inputStream, int i) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException(g9.b("byteCount < 0: ", i));
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = inputStream.read(bArr, i2, i - i2);
            if (i3 == -1) {
                throw new EOFException();
            }
            i2 += i3;
        }
        return new ByteString(bArr);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IllegalAccessException, NoSuchFieldException, IOException, IllegalArgumentException {
        ByteString byteString = read(objectInputStream, objectInputStream.readInt());
        try {
            Field declaredField = ByteString.class.getDeclaredField("a");
            declaredField.setAccessible(true);
            declaredField.set(this, byteString.a);
        } catch (IllegalAccessException unused) {
            throw new AssertionError();
        } catch (NoSuchFieldException unused2) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.a.length);
        objectOutputStream.write(this.a);
    }

    public final ByteString a(String str) {
        try {
            return of(MessageDigest.getInstance(str).digest(this.a));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(this.a).asReadOnlyBuffer();
    }

    public String base64() {
        return zn.a(this.a, zn.a);
    }

    public String base64Url() {
        return zn.a(this.a, zn.b);
    }

    public final boolean endsWith(ByteString byteString) {
        return rangeEquals(size() - byteString.size(), byteString, 0, byteString.size());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            int size = byteString.size();
            byte[] bArr = this.a;
            if (size == bArr.length && byteString.rangeEquals(0, bArr, 0, bArr.length)) {
                return true;
            }
        }
        return false;
    }

    public byte getByte(int i) {
        return this.a[i];
    }

    public int hashCode() {
        int i = this.b;
        if (i != 0) {
            return i;
        }
        int iHashCode = Arrays.hashCode(this.a);
        this.b = iHashCode;
        return iHashCode;
    }

    public String hex() {
        byte[] bArr = this.a;
        char[] cArr = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            char[] cArr2 = d;
            cArr[i] = cArr2[(b >> 4) & 15];
            i = i2 + 1;
            cArr[i2] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public ByteString hmacSha1(ByteString byteString) {
        return a("HmacSHA1", byteString);
    }

    public ByteString hmacSha256(ByteString byteString) {
        return a("HmacSHA256", byteString);
    }

    public ByteString hmacSha512(ByteString byteString) {
        return a("HmacSHA512", byteString);
    }

    public final int indexOf(ByteString byteString) {
        return indexOf(byteString.a(), 0);
    }

    public final int lastIndexOf(ByteString byteString) {
        return lastIndexOf(byteString.a(), size());
    }

    public ByteString md5() {
        return a("MD5");
    }

    public boolean rangeEquals(int i, ByteString byteString, int i2, int i3) {
        return byteString.rangeEquals(i2, this.a, i, i3);
    }

    public ByteString sha1() {
        return a("SHA-1");
    }

    public ByteString sha256() {
        return a("SHA-256");
    }

    public ByteString sha512() {
        return a("SHA-512");
    }

    public int size() {
        return this.a.length;
    }

    public final boolean startsWith(ByteString byteString) {
        return rangeEquals(0, byteString, 0, byteString.size());
    }

    public String string(Charset charset) {
        if (charset != null) {
            return new String(this.a, charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    public ByteString substring(int i) {
        return substring(i, this.a.length);
    }

    public ByteString toAsciiLowercase() {
        int i = 0;
        while (true) {
            byte[] bArr = this.a;
            if (i >= bArr.length) {
                return this;
            }
            byte b = bArr[i];
            if (b >= 65 && b <= 90) {
                byte[] bArr2 = (byte[]) bArr.clone();
                bArr2[i] = (byte) (b + 32);
                for (int i2 = i + 1; i2 < bArr2.length; i2++) {
                    byte b2 = bArr2[i2];
                    if (b2 >= 65 && b2 <= 90) {
                        bArr2[i2] = (byte) (b2 + 32);
                    }
                }
                return new ByteString(bArr2);
            }
            i++;
        }
    }

    public ByteString toAsciiUppercase() {
        int i = 0;
        while (true) {
            byte[] bArr = this.a;
            if (i >= bArr.length) {
                return this;
            }
            byte b = bArr[i];
            if (b >= 97 && b <= 122) {
                byte[] bArr2 = (byte[]) bArr.clone();
                bArr2[i] = (byte) (b - 32);
                for (int i2 = i + 1; i2 < bArr2.length; i2++) {
                    byte b2 = bArr2[i2];
                    if (b2 >= 97 && b2 <= 122) {
                        bArr2[i2] = (byte) (b2 - 32);
                    }
                }
                return new ByteString(bArr2);
            }
            i++;
        }
    }

    public byte[] toByteArray() {
        return (byte[]) this.a.clone();
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0032, code lost:
    
        r3 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            r9 = this;
            byte[] r0 = r9.a
            int r0 = r0.length
            if (r0 != 0) goto L8
            java.lang.String r0 = "[size=0]"
            return r0
        L8:
            java.lang.String r0 = r9.utf8()
            int r1 = r0.length()
            r2 = 0
            r3 = r2
            r4 = r3
        L13:
            r5 = -1
            r6 = 64
            if (r3 >= r1) goto L3c
            if (r4 != r6) goto L1b
            goto L40
        L1b:
            int r7 = r0.codePointAt(r3)
            boolean r8 = java.lang.Character.isISOControl(r7)
            if (r8 == 0) goto L2d
            r8 = 10
            if (r7 == r8) goto L2d
            r8 = 13
            if (r7 != r8) goto L32
        L2d:
            r8 = 65533(0xfffd, float:9.1831E-41)
            if (r7 != r8) goto L34
        L32:
            r3 = r5
            goto L40
        L34:
            int r4 = r4 + 1
            int r5 = java.lang.Character.charCount(r7)
            int r3 = r3 + r5
            goto L13
        L3c:
            int r3 = r0.length()
        L40:
            java.lang.String r1 = "…]"
            java.lang.String r4 = "[size="
            java.lang.String r7 = "]"
            if (r3 != r5) goto L84
            byte[] r0 = r9.a
            int r0 = r0.length
            if (r0 > r6) goto L62
            java.lang.String r0 = "[hex="
            java.lang.StringBuilder r0 = defpackage.g9.a(r0)
            java.lang.String r1 = r9.hex()
            r0.append(r1)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            goto L83
        L62:
            java.lang.StringBuilder r0 = defpackage.g9.a(r4)
            byte[] r3 = r9.a
            int r3 = r3.length
            r0.append(r3)
            java.lang.String r3 = " hex="
            r0.append(r3)
            okio.ByteString r2 = r9.substring(r2, r6)
            java.lang.String r2 = r2.hex()
            r0.append(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
        L83:
            return r0
        L84:
            java.lang.String r2 = r0.substring(r2, r3)
            java.lang.String r5 = "\\"
            java.lang.String r6 = "\\\\"
            java.lang.String r2 = r2.replace(r5, r6)
            java.lang.String r5 = "\n"
            java.lang.String r6 = "\\n"
            java.lang.String r2 = r2.replace(r5, r6)
            java.lang.String r5 = "\r"
            java.lang.String r6 = "\\r"
            java.lang.String r2 = r2.replace(r5, r6)
            int r0 = r0.length()
            if (r3 >= r0) goto Lc0
            java.lang.StringBuilder r0 = defpackage.g9.a(r4)
            byte[] r3 = r9.a
            int r3 = r3.length
            r0.append(r3)
            java.lang.String r3 = " text="
            r0.append(r3)
            r0.append(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            goto Lc6
        Lc0:
            java.lang.String r0 = "[text="
            java.lang.String r0 = defpackage.g9.a(r0, r2, r7)
        Lc6:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.ByteString.toString():java.lang.String");
    }

    public String utf8() {
        String str = this.c;
        if (str != null) {
            return str;
        }
        String str2 = new String(this.a, io.a);
        this.c = str2;
        return str2;
    }

    public void write(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        outputStream.write(this.a);
    }

    @Override // java.lang.Comparable
    public int compareTo(ByteString byteString) {
        int size = size();
        int size2 = byteString.size();
        int iMin = Math.min(size, size2);
        for (int i = 0; i < iMin; i++) {
            int i2 = getByte(i) & 255;
            int i3 = byteString.getByte(i) & 255;
            if (i2 != i3) {
                return i2 < i3 ? -1 : 1;
            }
        }
        if (size == size2) {
            return 0;
        }
        return size < size2 ? -1 : 1;
    }

    public final boolean endsWith(byte[] bArr) {
        return rangeEquals(size() - bArr.length, bArr, 0, bArr.length);
    }

    public final int indexOf(ByteString byteString, int i) {
        return indexOf(byteString.a(), i);
    }

    public final int lastIndexOf(ByteString byteString, int i) {
        return lastIndexOf(byteString.a(), i);
    }

    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        if (i >= 0) {
            byte[] bArr2 = this.a;
            if (i <= bArr2.length - i3 && i2 >= 0 && i2 <= bArr.length - i3 && io.a(bArr2, i, bArr, i2, i3)) {
                return true;
            }
        }
        return false;
    }

    public final boolean startsWith(byte[] bArr) {
        return rangeEquals(0, bArr, 0, bArr.length);
    }

    public ByteString substring(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("beginIndex < 0");
        }
        byte[] bArr = this.a;
        if (i2 > bArr.length) {
            StringBuilder sbA = g9.a("endIndex > length(");
            sbA.append(this.a.length);
            sbA.append(")");
            throw new IllegalArgumentException(sbA.toString());
        }
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("endIndex < beginIndex");
        }
        if (i == 0 && i2 == bArr.length) {
            return this;
        }
        byte[] bArr2 = new byte[i3];
        System.arraycopy(this.a, i, bArr2, 0, i3);
        return new ByteString(bArr2);
    }

    public static ByteString of(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            io.a(bArr.length, i, i2);
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            return new ByteString(bArr2);
        }
        throw new IllegalArgumentException("data == null");
    }

    public final ByteString a(String str, ByteString byteString) throws NoSuchAlgorithmException, InvalidKeyException {
        try {
            Mac mac = Mac.getInstance(str);
            mac.init(new SecretKeySpec(byteString.toByteArray(), str));
            return of(mac.doFinal(this.a));
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new AssertionError(e2);
        }
    }

    public final int indexOf(byte[] bArr) {
        return indexOf(bArr, 0);
    }

    public final int lastIndexOf(byte[] bArr) {
        return lastIndexOf(bArr, size());
    }

    public int indexOf(byte[] bArr, int i) {
        int length = this.a.length - bArr.length;
        for (int iMax = Math.max(i, 0); iMax <= length; iMax++) {
            if (io.a(this.a, iMax, bArr, 0, bArr.length)) {
                return iMax;
            }
        }
        return -1;
    }

    public int lastIndexOf(byte[] bArr, int i) {
        for (int iMin = Math.min(i, this.a.length - bArr.length); iMin >= 0; iMin--) {
            if (io.a(this.a, iMin, bArr, 0, bArr.length)) {
                return iMin;
            }
        }
        return -1;
    }

    public static int a(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        char c2 = 'a';
        if (c < 'a' || c > 'f') {
            c2 = 'A';
            if (c < 'A' || c > 'F') {
                throw new IllegalArgumentException("Unexpected hex digit: " + c);
            }
        }
        return (c - c2) + 10;
    }

    public static ByteString of(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            byte[] bArr = new byte[byteBuffer.remaining()];
            byteBuffer.get(bArr);
            return new ByteString(bArr);
        }
        throw new IllegalArgumentException("data == null");
    }

    public byte[] a() {
        return this.a;
    }

    public void a(Buffer buffer) {
        byte[] bArr = this.a;
        buffer.write(bArr, 0, bArr.length);
    }
}
