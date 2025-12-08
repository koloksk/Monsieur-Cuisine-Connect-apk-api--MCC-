package okhttp3.internal.http;

import javax.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/* loaded from: classes.dex */
public final class RealResponseBody extends ResponseBody {

    @Nullable
    public final String a;
    public final long b;
    public final BufferedSource c;

    public RealResponseBody(@Nullable String str, long j, BufferedSource bufferedSource) {
        this.a = str;
        this.b = j;
        this.c = bufferedSource;
    }

    @Override // okhttp3.ResponseBody
    public long contentLength() {
        return this.b;
    }

    @Override // okhttp3.ResponseBody
    public MediaType contentType() {
        String str = this.a;
        if (str != null) {
            return MediaType.parse(str);
        }
        return null;
    }

    @Override // okhttp3.ResponseBody
    public BufferedSource source() {
        return this.c;
    }
}
