package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

/* loaded from: classes.dex */
public final class MultipartBody extends RequestBody {
    public final ByteString a;
    public final MediaType b;
    public final MediaType c;
    public final List<Part> d;
    public long e = -1;
    public static final MediaType MIXED = MediaType.get("multipart/mixed");
    public static final MediaType ALTERNATIVE = MediaType.get("multipart/alternative");
    public static final MediaType DIGEST = MediaType.get("multipart/digest");
    public static final MediaType PARALLEL = MediaType.get("multipart/parallel");
    public static final MediaType FORM = MediaType.get("multipart/form-data");
    public static final byte[] f = {58, 32};
    public static final byte[] g = {13, 10};
    public static final byte[] h = {45, 45};

    public static final class Builder {
        public final ByteString a;
        public MediaType b;
        public final List<Part> c;

        public Builder() {
            this(UUID.randomUUID().toString());
        }

        public Builder addFormDataPart(String str, String str2) {
            return addPart(Part.createFormData(str, str2));
        }

        public Builder addPart(RequestBody requestBody) {
            return addPart(Part.create(requestBody));
        }

        public MultipartBody build() {
            if (this.c.isEmpty()) {
                throw new IllegalStateException("Multipart body must have at least one part.");
            }
            return new MultipartBody(this.a, this.b, this.c);
        }

        public Builder setType(MediaType mediaType) {
            if (mediaType == null) {
                throw new NullPointerException("type == null");
            }
            if (mediaType.type().equals("multipart")) {
                this.b = mediaType;
                return this;
            }
            throw new IllegalArgumentException("multipart != " + mediaType);
        }

        public Builder(String str) {
            this.b = MultipartBody.MIXED;
            this.c = new ArrayList();
            this.a = ByteString.encodeUtf8(str);
        }

        public Builder addFormDataPart(String str, @Nullable String str2, RequestBody requestBody) {
            return addPart(Part.createFormData(str, str2, requestBody));
        }

        public Builder addPart(@Nullable Headers headers, RequestBody requestBody) {
            return addPart(Part.create(headers, requestBody));
        }

        public Builder addPart(Part part) {
            if (part != null) {
                this.c.add(part);
                return this;
            }
            throw new NullPointerException("part == null");
        }
    }

    public static final class Part {

        @Nullable
        public final Headers a;
        public final RequestBody b;

        public Part(@Nullable Headers headers, RequestBody requestBody) {
            this.a = headers;
            this.b = requestBody;
        }

        public static Part create(RequestBody requestBody) {
            return create(null, requestBody);
        }

        public static Part createFormData(String str, String str2) {
            return createFormData(str, null, RequestBody.create((MediaType) null, str2));
        }

        public RequestBody body() {
            return this.b;
        }

        @Nullable
        public Headers headers() {
            return this.a;
        }

        public static Part create(@Nullable Headers headers, RequestBody requestBody) {
            if (requestBody == null) {
                throw new NullPointerException("body == null");
            }
            if (headers != null && headers.get("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            }
            if (headers == null || headers.get("Content-Length") == null) {
                return new Part(headers, requestBody);
            }
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }

        public static Part createFormData(String str, @Nullable String str2, RequestBody requestBody) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            StringBuilder sb = new StringBuilder("form-data; name=");
            MultipartBody.a(sb, str);
            if (str2 != null) {
                sb.append("; filename=");
                MultipartBody.a(sb, str2);
            }
            return create(new Headers.Builder().addUnsafeNonAscii("Content-Disposition", sb.toString()).build(), requestBody);
        }
    }

    public MultipartBody(ByteString byteString, MediaType mediaType, List<Part> list) {
        this.a = byteString;
        this.b = mediaType;
        this.c = MediaType.get(mediaType + "; boundary=" + byteString.utf8());
        this.d = Util.immutableList(list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final long a(@Nullable BufferedSink bufferedSink, boolean z) throws IOException {
        Buffer buffer;
        if (z) {
            bufferedSink = new Buffer();
            buffer = bufferedSink;
        } else {
            buffer = 0;
        }
        int size = this.d.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            Part part = this.d.get(i);
            Headers headers = part.a;
            RequestBody requestBody = part.b;
            bufferedSink.write(h);
            bufferedSink.write(this.a);
            bufferedSink.write(g);
            if (headers != null) {
                int size2 = headers.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    bufferedSink.writeUtf8(headers.name(i2)).write(f).writeUtf8(headers.value(i2)).write(g);
                }
            }
            MediaType mediaTypeContentType = requestBody.contentType();
            if (mediaTypeContentType != null) {
                bufferedSink.writeUtf8("Content-Type: ").writeUtf8(mediaTypeContentType.toString()).write(g);
            }
            long jContentLength = requestBody.contentLength();
            if (jContentLength != -1) {
                bufferedSink.writeUtf8("Content-Length: ").writeDecimalLong(jContentLength).write(g);
            } else if (z) {
                buffer.clear();
                return -1L;
            }
            bufferedSink.write(g);
            if (z) {
                j += jContentLength;
            } else {
                requestBody.writeTo(bufferedSink);
            }
            bufferedSink.write(g);
        }
        bufferedSink.write(h);
        bufferedSink.write(this.a);
        bufferedSink.write(h);
        bufferedSink.write(g);
        if (!z) {
            return j;
        }
        long size3 = j + buffer.size();
        buffer.clear();
        return size3;
    }

    public String boundary() {
        return this.a.utf8();
    }

    @Override // okhttp3.RequestBody
    public long contentLength() throws IOException {
        long j = this.e;
        if (j != -1) {
            return j;
        }
        long jA = a((BufferedSink) null, true);
        this.e = jA;
        return jA;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        return this.c;
    }

    public Part part(int i) {
        return this.d.get(i);
    }

    public List<Part> parts() {
        return this.d;
    }

    public int size() {
        return this.d.size();
    }

    public MediaType type() {
        return this.b;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        a(bufferedSink, false);
    }

    public static void a(StringBuilder sb, String str) {
        sb.append('\"');
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n') {
                sb.append("%0A");
            } else if (cCharAt == '\r') {
                sb.append("%0D");
            } else if (cCharAt != '\"') {
                sb.append(cCharAt);
            } else {
                sb.append("%22");
            }
        }
        sb.append('\"');
    }
}
