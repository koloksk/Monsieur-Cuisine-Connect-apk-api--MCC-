package okhttp3;

import defpackage.g9;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import okio.ByteString;

/* loaded from: classes.dex */
public final class Credentials {
    public static String basic(String str, String str2) {
        return basic(str, str2, StandardCharsets.ISO_8859_1);
    }

    public static String basic(String str, String str2, Charset charset) {
        return g9.b("Basic ", ByteString.encodeString(str + ":" + str2, charset).base64());
    }
}
