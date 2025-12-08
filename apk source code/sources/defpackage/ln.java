package defpackage;

import defpackage.sn;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.internal.http2.Header;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

/* loaded from: classes.dex */
public final class ln {
    public static final Header[] a = {new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
    public static final Map<ByteString, Integer> b;

    static {
        int i = 0;
        LinkedHashMap linkedHashMap = new LinkedHashMap(a.length);
        while (true) {
            Header[] headerArr = a;
            if (i >= headerArr.length) {
                b = Collections.unmodifiableMap(linkedHashMap);
                return;
            } else {
                if (!linkedHashMap.containsKey(headerArr[i].name)) {
                    linkedHashMap.put(a[i].name, Integer.valueOf(i));
                }
                i++;
            }
        }
    }

    public static ByteString a(ByteString byteString) throws IOException {
        int size = byteString.size();
        for (int i = 0; i < size; i++) {
            byte b2 = byteString.getByte(i);
            if (b2 >= 65 && b2 <= 90) {
                StringBuilder sbA = g9.a("PROTOCOL_ERROR response malformed: mixed case name: ");
                sbA.append(byteString.utf8());
                throw new IOException(sbA.toString());
            }
        }
        return byteString;
    }

    public static final class a {
        public final BufferedSource b;
        public final int c;
        public int d;
        public final List<Header> a = new ArrayList();
        public Header[] e = new Header[8];
        public int f = 7;
        public int g = 0;
        public int h = 0;

        public a(int i, Source source) {
            this.c = i;
            this.d = i;
            this.b = Okio.buffer(source);
        }

        public final void a() {
            Arrays.fill(this.e, (Object) null);
            this.f = this.e.length - 1;
            this.g = 0;
            this.h = 0;
        }

        public final int b(int i) {
            int i2 = 0;
            if (i > 0) {
                int length = this.e.length;
                while (true) {
                    length--;
                    if (length < this.f || i <= 0) {
                        break;
                    }
                    Header[] headerArr = this.e;
                    i -= headerArr[length].a;
                    this.h -= headerArr[length].a;
                    this.g--;
                    i2++;
                }
                Header[] headerArr2 = this.e;
                int i3 = this.f;
                System.arraycopy(headerArr2, i3 + 1, headerArr2, i3 + 1 + i2, this.g);
                this.f += i2;
            }
            return i2;
        }

        public final ByteString c(int i) throws IOException {
            if (i >= 0 && i <= ln.a.length - 1) {
                return ln.a[i].name;
            }
            int iA = a(i - ln.a.length);
            if (iA >= 0) {
                Header[] headerArr = this.e;
                if (iA < headerArr.length) {
                    return headerArr[iA].name;
                }
            }
            StringBuilder sbA = g9.a("Header index too large ");
            sbA.append(i + 1);
            throw new IOException(sbA.toString());
        }

        public final int a(int i) {
            return this.f + 1 + i;
        }

        public final void a(int i, Header header) {
            this.a.add(header);
            int i2 = header.a;
            if (i != -1) {
                i2 -= this.e[(this.f + 1) + i].a;
            }
            int i3 = this.d;
            if (i2 > i3) {
                a();
                return;
            }
            int iB = b((this.h + i2) - i3);
            if (i == -1) {
                int i4 = this.g + 1;
                Header[] headerArr = this.e;
                if (i4 > headerArr.length) {
                    Header[] headerArr2 = new Header[headerArr.length * 2];
                    System.arraycopy(headerArr, 0, headerArr2, headerArr.length, headerArr.length);
                    this.f = this.e.length - 1;
                    this.e = headerArr2;
                }
                int i5 = this.f;
                this.f = i5 - 1;
                this.e[i5] = header;
                this.g++;
            } else {
                this.e[this.f + 1 + i + iB + i] = header;
            }
            this.h += i2;
        }

        public ByteString b() throws IOException {
            int i = this.b.readByte() & 255;
            boolean z = (i & 128) == 128;
            int iA = a(i, 127);
            if (z) {
                sn snVar = sn.d;
                byte[] byteArray = this.b.readByteArray(iA);
                if (snVar != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int i2 = 0;
                    sn.a aVar = snVar.a;
                    int i3 = 0;
                    for (byte b : byteArray) {
                        i3 = (i3 << 8) | (b & 255);
                        i2 += 8;
                        while (i2 >= 8) {
                            int i4 = i2 - 8;
                            aVar = aVar.a[(i3 >>> i4) & 255];
                            if (aVar.a == null) {
                                byteArrayOutputStream.write(aVar.b);
                                i2 -= aVar.c;
                                aVar = snVar.a;
                            } else {
                                i2 = i4;
                            }
                        }
                    }
                    while (i2 > 0) {
                        sn.a aVar2 = aVar.a[(i3 << (8 - i2)) & 255];
                        if (aVar2.a != null || aVar2.c > i2) {
                            break;
                        }
                        byteArrayOutputStream.write(aVar2.b);
                        i2 -= aVar2.c;
                        aVar = snVar.a;
                    }
                    return ByteString.of(byteArrayOutputStream.toByteArray());
                }
                throw null;
            }
            return this.b.readByteString(iA);
        }

        public int a(int i, int i2) throws IOException {
            int i3 = i & i2;
            if (i3 < i2) {
                return i3;
            }
            int i4 = 0;
            while (true) {
                int i5 = this.b.readByte() & 255;
                if ((i5 & 128) == 0) {
                    return i2 + (i5 << i4);
                }
                i2 += (i5 & 127) << i4;
                i4 += 7;
            }
        }
    }

    public static final class b {
        public final Buffer a;
        public boolean d;
        public int c = Integer.MAX_VALUE;
        public Header[] f = new Header[8];
        public int g = 7;
        public int h = 0;
        public int i = 0;
        public int e = 4096;
        public final boolean b = true;

        public b(Buffer buffer) {
            this.a = buffer;
        }

        public final void a() {
            Arrays.fill(this.f, (Object) null);
            this.g = this.f.length - 1;
            this.h = 0;
            this.i = 0;
        }

        public final int a(int i) {
            int i2 = 0;
            if (i > 0) {
                int length = this.f.length;
                while (true) {
                    length--;
                    if (length < this.g || i <= 0) {
                        break;
                    }
                    Header[] headerArr = this.f;
                    i -= headerArr[length].a;
                    this.i -= headerArr[length].a;
                    this.h--;
                    i2++;
                }
                Header[] headerArr2 = this.f;
                int i3 = this.g;
                System.arraycopy(headerArr2, i3 + 1, headerArr2, i3 + 1 + i2, this.h);
                Header[] headerArr3 = this.f;
                int i4 = this.g;
                Arrays.fill(headerArr3, i4 + 1, i4 + 1 + i2, (Object) null);
                this.g += i2;
            }
            return i2;
        }

        public final void a(Header header) {
            int i = header.a;
            int i2 = this.e;
            if (i > i2) {
                a();
                return;
            }
            a((this.i + i) - i2);
            int i3 = this.h + 1;
            Header[] headerArr = this.f;
            if (i3 > headerArr.length) {
                Header[] headerArr2 = new Header[headerArr.length * 2];
                System.arraycopy(headerArr, 0, headerArr2, headerArr.length, headerArr.length);
                this.g = this.f.length - 1;
                this.f = headerArr2;
            }
            int i4 = this.g;
            this.g = i4 - 1;
            this.f[i4] = header;
            this.h++;
            this.i += i;
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x006b  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0072  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00a9  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00b1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a(java.util.List<okhttp3.internal.http2.Header> r14) throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 237
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: ln.b.a(java.util.List):void");
        }

        public void a(int i, int i2, int i3) {
            if (i < i2) {
                this.a.writeByte(i | i3);
                return;
            }
            this.a.writeByte(i3 | i2);
            int i4 = i - i2;
            while (i4 >= 128) {
                this.a.writeByte(128 | (i4 & 127));
                i4 >>>= 7;
            }
            this.a.writeByte(i4);
        }

        public void a(ByteString byteString) throws IOException {
            if (this.b) {
                if (sn.d != null) {
                    long j = 0;
                    long j2 = 0;
                    for (int i = 0; i < byteString.size(); i++) {
                        j2 += sn.c[byteString.getByte(i) & 255];
                    }
                    if (((int) ((j2 + 7) >> 3)) < byteString.size()) {
                        Buffer buffer = new Buffer();
                        if (sn.d != null) {
                            int i2 = 0;
                            for (int i3 = 0; i3 < byteString.size(); i3++) {
                                int i4 = byteString.getByte(i3) & 255;
                                int i5 = sn.b[i4];
                                byte b = sn.c[i4];
                                j = (j << b) | i5;
                                i2 += b;
                                while (i2 >= 8) {
                                    i2 -= 8;
                                    buffer.writeByte((int) (j >> i2));
                                }
                            }
                            if (i2 > 0) {
                                buffer.writeByte((int) ((255 >>> i2) | (j << (8 - i2))));
                            }
                            ByteString byteString2 = buffer.readByteString();
                            a(byteString2.size(), 127, 128);
                            this.a.write(byteString2);
                            return;
                        }
                        throw null;
                    }
                } else {
                    throw null;
                }
            }
            a(byteString.size(), 127, 0);
            this.a.write(byteString);
        }
    }
}
