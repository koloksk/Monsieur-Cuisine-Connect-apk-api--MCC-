package okhttp3.internal.http2;

import java.io.IOException;
import okhttp3.internal.Util;
import okio.ByteString;

/* loaded from: classes.dex */
public final class Http2 {
    public static final ByteString a = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    public static final String[] b = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
    public static final String[] c = new String[64];
    public static final String[] d = new String[256];

    static {
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = d;
            if (i2 >= strArr.length) {
                break;
            }
            strArr[i2] = Util.format("%8s", Integer.toBinaryString(i2)).replace(' ', '0');
            i2++;
        }
        String[] strArr2 = c;
        strArr2[0] = "";
        strArr2[1] = "END_STREAM";
        int[] iArr = {1};
        strArr2[8] = "PADDED";
        for (int i3 = 0; i3 < 1; i3++) {
            int i4 = iArr[i3];
            c[i4 | 8] = c[i4] + "|PADDED";
        }
        String[] strArr3 = c;
        strArr3[4] = "END_HEADERS";
        strArr3[32] = "PRIORITY";
        strArr3[36] = "END_HEADERS|PRIORITY";
        int[] iArr2 = {4, 32, 36};
        for (int i5 = 0; i5 < 3; i5++) {
            int i6 = iArr2[i5];
            for (int i7 = 0; i7 < 1; i7++) {
                int i8 = iArr[i7];
                int i9 = i8 | i6;
                c[i9] = c[i8] + '|' + c[i6];
                c[i9 | 8] = c[i8] + '|' + c[i6] + "|PADDED";
            }
        }
        while (true) {
            String[] strArr4 = c;
            if (i >= strArr4.length) {
                return;
            }
            if (strArr4[i] == null) {
                strArr4[i] = d[i];
            }
            i++;
        }
    }

    public static IllegalArgumentException a(String str, Object... objArr) {
        throw new IllegalArgumentException(Util.format(str, objArr));
    }

    public static IOException b(String str, Object... objArr) throws IOException {
        throw new IOException(Util.format(str, objArr));
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(boolean r9, int r10, int r11, byte r12, byte r13) {
        /*
            java.lang.String[] r0 = okhttp3.internal.http2.Http2.b
            int r1 = r0.length
            r2 = 0
            r3 = 1
            if (r12 >= r1) goto La
            r0 = r0[r12]
            goto L18
        La:
            java.lang.Object[] r0 = new java.lang.Object[r3]
            java.lang.Byte r1 = java.lang.Byte.valueOf(r12)
            r0[r2] = r1
            java.lang.String r1 = "0x%02x"
            java.lang.String r0 = okhttp3.internal.Util.format(r1, r0)
        L18:
            r1 = 5
            r4 = 3
            r5 = 2
            r6 = 4
            if (r13 != 0) goto L21
            java.lang.String r12 = ""
            goto L6b
        L21:
            if (r12 == r5) goto L67
            if (r12 == r4) goto L67
            if (r12 == r6) goto L5d
            r7 = 6
            if (r12 == r7) goto L5d
            r7 = 7
            if (r12 == r7) goto L67
            r7 = 8
            if (r12 == r7) goto L67
            java.lang.String[] r7 = okhttp3.internal.http2.Http2.c
            int r8 = r7.length
            if (r13 >= r8) goto L39
            r7 = r7[r13]
            goto L3d
        L39:
            java.lang.String[] r7 = okhttp3.internal.http2.Http2.d
            r7 = r7[r13]
        L3d:
            if (r12 != r1) goto L4c
            r8 = r13 & 4
            if (r8 == 0) goto L4c
            java.lang.String r12 = "HEADERS"
            java.lang.String r13 = "PUSH_PROMISE"
            java.lang.String r12 = r7.replace(r12, r13)
            goto L6b
        L4c:
            if (r12 != 0) goto L5b
            r12 = r13 & 32
            if (r12 == 0) goto L5b
            java.lang.String r12 = "PRIORITY"
            java.lang.String r13 = "COMPRESSED"
            java.lang.String r12 = r7.replace(r12, r13)
            goto L6b
        L5b:
            r12 = r7
            goto L6b
        L5d:
            if (r13 != r3) goto L62
            java.lang.String r12 = "ACK"
            goto L6b
        L62:
            java.lang.String[] r12 = okhttp3.internal.http2.Http2.d
            r12 = r12[r13]
            goto L6b
        L67:
            java.lang.String[] r12 = okhttp3.internal.http2.Http2.d
            r12 = r12[r13]
        L6b:
            java.lang.Object[] r13 = new java.lang.Object[r1]
            if (r9 == 0) goto L72
            java.lang.String r9 = "<<"
            goto L74
        L72:
            java.lang.String r9 = ">>"
        L74:
            r13[r2] = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r10)
            r13[r3] = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r11)
            r13[r5] = r9
            r13[r4] = r0
            r13[r6] = r12
            java.lang.String r9 = "%s 0x%08x %5d %-13s %s"
            java.lang.String r9 = okhttp3.internal.Util.format(r9, r13)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2.a(boolean, int, int, byte, byte):java.lang.String");
    }
}
