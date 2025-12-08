package okhttp3.internal.ws;

import defpackage.g9;
import okio.Buffer;
import okio.ByteString;

/* loaded from: classes.dex */
public final class WebSocketProtocol {
    public WebSocketProtocol() {
        throw new AssertionError("No instances.");
    }

    public static void a(Buffer.UnsafeCursor unsafeCursor, byte[] bArr) {
        int length = bArr.length;
        int i = 0;
        do {
            byte[] bArr2 = unsafeCursor.data;
            int i2 = unsafeCursor.start;
            int i3 = unsafeCursor.end;
            while (i2 < i3) {
                int i4 = i % length;
                bArr2[i2] = (byte) (bArr2[i2] ^ bArr[i4]);
                i2++;
                i = i4 + 1;
            }
        } while (unsafeCursor.next() != -1);
    }

    public static String acceptHeader(String str) {
        return ByteString.encodeUtf8(str + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").sha1().base64();
    }

    public static String a(int i) {
        if (i < 1000 || i >= 5000) {
            return g9.b("Code must be in range [1000,5000): ", i);
        }
        if ((i < 1004 || i > 1006) && (i < 1012 || i > 2999)) {
            return null;
        }
        return "Code " + i + " is reserved and may not be used.";
    }
}
