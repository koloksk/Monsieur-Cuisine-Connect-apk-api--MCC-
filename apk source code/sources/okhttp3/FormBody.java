package okhttp3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;

/* loaded from: classes.dex */
public final class FormBody extends RequestBody {
    public static final MediaType c = MediaType.get("application/x-www-form-urlencoded");
    public final List<String> a;
    public final List<String> b;

    public static final class Builder {
        public final List<String> a;
        public final List<String> b;

        @Nullable
        public final Charset c;

        public Builder() {
            this(null);
        }

        public Builder add(String str, String str2) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            if (str2 == null) {
                throw new NullPointerException("value == null");
            }
            this.a.add(HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true, this.c));
            this.b.add(HttpUrl.a(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true, this.c));
            return this;
        }

        public Builder addEncoded(String str, String str2) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            if (str2 == null) {
                throw new NullPointerException("value == null");
            }
            this.a.add(HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true, this.c));
            this.b.add(HttpUrl.a(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true, this.c));
            return this;
        }

        public FormBody build() {
            return new FormBody(this.a, this.b);
        }

        public Builder(@Nullable Charset charset) {
            this.a = new ArrayList();
            this.b = new ArrayList();
            this.c = charset;
        }
    }

    public FormBody(List<String> list, List<String> list2) {
        this.a = Util.immutableList(list);
        this.b = Util.immutableList(list2);
    }

    public final long a(@Nullable BufferedSink bufferedSink, boolean z) {
        Buffer buffer = z ? new Buffer() : bufferedSink.buffer();
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                buffer.writeByte(38);
            }
            buffer.writeUtf8(this.a.get(i));
            buffer.writeByte(61);
            buffer.writeUtf8(this.b.get(i));
        }
        if (!z) {
            return 0L;
        }
        long size2 = buffer.size();
        buffer.clear();
        return size2;
    }

    @Override // okhttp3.RequestBody
    public long contentLength() {
        return a(null, true);
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        return c;
    }

    public String encodedName(int i) {
        return this.a.get(i);
    }

    public String encodedValue(int i) {
        return this.b.get(i);
    }

    public String name(int i) {
        return HttpUrl.a(encodedName(i), true);
    }

    public int size() {
        return this.a.size();
    }

    public String value(int i) {
        return HttpUrl.a(encodedValue(i), true);
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        a(bufferedSink, false);
    }
}
