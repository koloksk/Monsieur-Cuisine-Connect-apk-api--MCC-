package okhttp3;

import defpackage.g9;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okio.Buffer;

/* loaded from: classes.dex */
public final class HttpUrl {
    public static final char[] j = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public final String a;
    public final String b;
    public final String c;
    public final String d;
    public final int e;
    public final List<String> f;

    @Nullable
    public final List<String> g;

    @Nullable
    public final String h;
    public final String i;

    public HttpUrl(Builder builder) {
        this.a = builder.a;
        this.b = a(builder.b, false);
        this.c = a(builder.c, false);
        this.d = builder.d;
        int i = builder.e;
        this.e = i == -1 ? defaultPort(builder.a) : i;
        this.f = a(builder.f, false);
        List<String> list = builder.g;
        this.g = list != null ? a(list, true) : null;
        String str = builder.h;
        this.h = str != null ? a(str, 0, str.length(), false) : null;
        this.i = builder.toString();
    }

    public static void a(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i = 0; i < size; i += 2) {
            String str = list.get(i);
            String str2 = list.get(i + 1);
            if (i > 0) {
                sb.append('&');
            }
            sb.append(str);
            if (str2 != null) {
                sb.append('=');
                sb.append(str2);
            }
        }
    }

    public static int defaultPort(String str) {
        if (str.equals("http")) {
            return 80;
        }
        return str.equals("https") ? 443 : -1;
    }

    public static HttpUrl get(String str) throws NumberFormatException {
        Builder builder = new Builder();
        builder.a((HttpUrl) null, str);
        return builder.build();
    }

    @Nullable
    public static HttpUrl parse(String str) {
        try {
            return get(str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    @Nullable
    public String encodedFragment() {
        if (this.h == null) {
            return null;
        }
        return this.i.substring(this.i.indexOf(35) + 1);
    }

    public String encodedPassword() {
        if (this.c.isEmpty()) {
            return "";
        }
        return this.i.substring(this.i.indexOf(58, this.a.length() + 3) + 1, this.i.indexOf(64));
    }

    public String encodedPath() {
        int iIndexOf = this.i.indexOf(47, this.a.length() + 3);
        String str = this.i;
        return this.i.substring(iIndexOf, Util.delimiterOffset(str, iIndexOf, str.length(), "?#"));
    }

    public List<String> encodedPathSegments() {
        int iIndexOf = this.i.indexOf(47, this.a.length() + 3);
        String str = this.i;
        int iDelimiterOffset = Util.delimiterOffset(str, iIndexOf, str.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (iIndexOf < iDelimiterOffset) {
            int i = iIndexOf + 1;
            int iDelimiterOffset2 = Util.delimiterOffset(this.i, i, iDelimiterOffset, '/');
            arrayList.add(this.i.substring(i, iDelimiterOffset2));
            iIndexOf = iDelimiterOffset2;
        }
        return arrayList;
    }

    @Nullable
    public String encodedQuery() {
        if (this.g == null) {
            return null;
        }
        int iIndexOf = this.i.indexOf(63) + 1;
        String str = this.i;
        return this.i.substring(iIndexOf, Util.delimiterOffset(str, iIndexOf, str.length(), '#'));
    }

    public String encodedUsername() {
        if (this.b.isEmpty()) {
            return "";
        }
        int length = this.a.length() + 3;
        String str = this.i;
        return this.i.substring(length, Util.delimiterOffset(str, length, str.length(), ":@"));
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof HttpUrl) && ((HttpUrl) obj).i.equals(this.i);
    }

    @Nullable
    public String fragment() {
        return this.h;
    }

    public int hashCode() {
        return this.i.hashCode();
    }

    public String host() {
        return this.d;
    }

    public boolean isHttps() {
        return this.a.equals("https");
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.a = this.a;
        builder.b = encodedUsername();
        builder.c = encodedPassword();
        builder.d = this.d;
        builder.e = this.e != defaultPort(this.a) ? this.e : -1;
        builder.f.clear();
        builder.f.addAll(encodedPathSegments());
        builder.encodedQuery(encodedQuery());
        builder.h = encodedFragment();
        return builder;
    }

    public String password() {
        return this.c;
    }

    public List<String> pathSegments() {
        return this.f;
    }

    public int pathSize() {
        return this.f.size();
    }

    public int port() {
        return this.e;
    }

    @Nullable
    public String query() {
        if (this.g == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        a(sb, this.g);
        return sb.toString();
    }

    @Nullable
    public String queryParameter(String str) {
        List<String> list = this.g;
        if (list == null) {
            return null;
        }
        int size = list.size();
        for (int i = 0; i < size; i += 2) {
            if (str.equals(this.g.get(i))) {
                return this.g.get(i + 1);
            }
        }
        return null;
    }

    public String queryParameterName(int i) {
        List<String> list = this.g;
        if (list != null) {
            return list.get(i * 2);
        }
        throw new IndexOutOfBoundsException();
    }

    public Set<String> queryParameterNames() {
        if (this.g == null) {
            return Collections.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int size = this.g.size();
        for (int i = 0; i < size; i += 2) {
            linkedHashSet.add(this.g.get(i));
        }
        return Collections.unmodifiableSet(linkedHashSet);
    }

    public String queryParameterValue(int i) {
        List<String> list = this.g;
        if (list != null) {
            return list.get((i * 2) + 1);
        }
        throw new IndexOutOfBoundsException();
    }

    public List<String> queryParameterValues(String str) {
        if (this.g == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = this.g.size();
        for (int i = 0; i < size; i += 2) {
            if (str.equals(this.g.get(i))) {
                arrayList.add(this.g.get(i + 1));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public int querySize() {
        List<String> list = this.g;
        if (list != null) {
            return list.size() / 2;
        }
        return 0;
    }

    public String redact() {
        return newBuilder("/...").username("").password("").build().toString();
    }

    @Nullable
    public HttpUrl resolve(String str) {
        Builder builderNewBuilder = newBuilder(str);
        if (builderNewBuilder != null) {
            return builderNewBuilder.build();
        }
        return null;
    }

    public String scheme() {
        return this.a;
    }

    public String toString() {
        return this.i;
    }

    @Nullable
    public String topPrivateDomain() {
        if (Util.verifyAsIpAddress(this.d)) {
            return null;
        }
        return PublicSuffixDatabase.get().getEffectiveTldPlusOne(this.d);
    }

    public URI uri() {
        Builder builderNewBuilder = newBuilder();
        int size = builderNewBuilder.f.size();
        for (int i = 0; i < size; i++) {
            builderNewBuilder.f.set(i, a(builderNewBuilder.f.get(i), "[]", true, true, false, true));
        }
        List<String> list = builderNewBuilder.g;
        if (list != null) {
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                String str = builderNewBuilder.g.get(i2);
                if (str != null) {
                    builderNewBuilder.g.set(i2, a(str, "\\^`{|}", true, true, true, true));
                }
            }
        }
        String str2 = builderNewBuilder.h;
        if (str2 != null) {
            builderNewBuilder.h = a(str2, " \"#<>\\^`{|}", true, true, false, false);
        }
        String string = builderNewBuilder.toString();
        try {
            return new URI(string);
        } catch (URISyntaxException e) {
            try {
                return URI.create(string.replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", ""));
            } catch (Exception unused) {
                throw new RuntimeException(e);
            }
        }
    }

    public URL url() {
        try {
            return new URL(this.i);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String username() {
        return this.b;
    }

    @Nullable
    public static HttpUrl get(URL url) {
        return parse(url.toString());
    }

    @Nullable
    public static HttpUrl get(URI uri) {
        return parse(uri.toString());
    }

    public static final class Builder {

        @Nullable
        public String a;

        @Nullable
        public String d;
        public final List<String> f;

        @Nullable
        public List<String> g;

        @Nullable
        public String h;
        public String b = "";
        public String c = "";
        public int e = -1;

        public Builder() {
            ArrayList arrayList = new ArrayList();
            this.f = arrayList;
            arrayList.add("");
        }

        public final Builder a(String str, boolean z) {
            int i = 0;
            do {
                int iDelimiterOffset = Util.delimiterOffset(str, i, str.length(), "/\\");
                a(str, i, iDelimiterOffset, iDelimiterOffset < str.length(), z);
                i = iDelimiterOffset + 1;
            } while (i <= str.length());
            return this;
        }

        public Builder addEncodedPathSegment(String str) {
            if (str == null) {
                throw new NullPointerException("encodedPathSegment == null");
            }
            a(str, 0, str.length(), false, true);
            return this;
        }

        public Builder addEncodedPathSegments(String str) {
            if (str == null) {
                throw new NullPointerException("encodedPathSegments == null");
            }
            a(str, true);
            return this;
        }

        public Builder addEncodedQueryParameter(String str, @Nullable String str2) {
            if (str == null) {
                throw new NullPointerException("encodedName == null");
            }
            if (this.g == null) {
                this.g = new ArrayList();
            }
            this.g.add(HttpUrl.a(str, " \"'<>#&=", true, false, true, true));
            this.g.add(str2 != null ? HttpUrl.a(str2, " \"'<>#&=", true, false, true, true) : null);
            return this;
        }

        public Builder addPathSegment(String str) {
            if (str == null) {
                throw new NullPointerException("pathSegment == null");
            }
            a(str, 0, str.length(), false, false);
            return this;
        }

        public Builder addPathSegments(String str) {
            if (str == null) {
                throw new NullPointerException("pathSegments == null");
            }
            a(str, false);
            return this;
        }

        public Builder addQueryParameter(String str, @Nullable String str2) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            if (this.g == null) {
                this.g = new ArrayList();
            }
            this.g.add(HttpUrl.a(str, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
            this.g.add(str2 != null ? HttpUrl.a(str2, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true) : null);
            return this;
        }

        public final boolean b(String str) {
            return str.equals("..") || str.equalsIgnoreCase("%2e.") || str.equalsIgnoreCase(".%2e") || str.equalsIgnoreCase("%2e%2e");
        }

        public HttpUrl build() {
            if (this.a == null) {
                throw new IllegalStateException("scheme == null");
            }
            if (this.d != null) {
                return new HttpUrl(this);
            }
            throw new IllegalStateException("host == null");
        }

        public final void c(String str) {
            for (int size = this.g.size() - 2; size >= 0; size -= 2) {
                if (str.equals(this.g.get(size))) {
                    this.g.remove(size + 1);
                    this.g.remove(size);
                    if (this.g.isEmpty()) {
                        this.g = null;
                        return;
                    }
                }
            }
        }

        public Builder encodedFragment(@Nullable String str) {
            this.h = str != null ? HttpUrl.a(str, "", true, false, false, false) : null;
            return this;
        }

        public Builder encodedPassword(String str) {
            if (str == null) {
                throw new NullPointerException("encodedPassword == null");
            }
            this.c = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
            return this;
        }

        public Builder encodedPath(String str) {
            if (str == null) {
                throw new NullPointerException("encodedPath == null");
            }
            if (!str.startsWith("/")) {
                throw new IllegalArgumentException(g9.b("unexpected encodedPath: ", str));
            }
            a(str, 0, str.length());
            return this;
        }

        public Builder encodedQuery(@Nullable String str) {
            this.g = str != null ? HttpUrl.a(HttpUrl.a(str, " \"'<>#", true, false, true, true)) : null;
            return this;
        }

        public Builder encodedUsername(String str) {
            if (str == null) {
                throw new NullPointerException("encodedUsername == null");
            }
            this.b = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
            return this;
        }

        public Builder fragment(@Nullable String str) {
            this.h = str != null ? HttpUrl.a(str, "", false, false, false, false) : null;
            return this;
        }

        public Builder host(String str) {
            if (str == null) {
                throw new NullPointerException("host == null");
            }
            String strB = b(str, 0, str.length());
            if (strB == null) {
                throw new IllegalArgumentException(g9.b("unexpected host: ", str));
            }
            this.d = strB;
            return this;
        }

        public Builder password(String str) {
            if (str == null) {
                throw new NullPointerException("password == null");
            }
            this.c = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
            return this;
        }

        public Builder port(int i) {
            if (i <= 0 || i > 65535) {
                throw new IllegalArgumentException(g9.b("unexpected port: ", i));
            }
            this.e = i;
            return this;
        }

        public Builder query(@Nullable String str) {
            this.g = str != null ? HttpUrl.a(HttpUrl.a(str, " \"'<>#", false, false, true, true)) : null;
            return this;
        }

        public Builder removeAllEncodedQueryParameters(String str) {
            if (str == null) {
                throw new NullPointerException("encodedName == null");
            }
            if (this.g == null) {
                return this;
            }
            c(HttpUrl.a(str, " \"'<>#&=", true, false, true, true));
            return this;
        }

        public Builder removeAllQueryParameters(String str) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            if (this.g == null) {
                return this;
            }
            c(HttpUrl.a(str, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
            return this;
        }

        public Builder removePathSegment(int i) {
            this.f.remove(i);
            if (this.f.isEmpty()) {
                this.f.add("");
            }
            return this;
        }

        public Builder scheme(String str) {
            if (str == null) {
                throw new NullPointerException("scheme == null");
            }
            if (str.equalsIgnoreCase("http")) {
                this.a = "http";
            } else {
                if (!str.equalsIgnoreCase("https")) {
                    throw new IllegalArgumentException(g9.b("unexpected scheme: ", str));
                }
                this.a = "https";
            }
            return this;
        }

        public Builder setEncodedPathSegment(int i, String str) {
            if (str == null) {
                throw new NullPointerException("encodedPathSegment == null");
            }
            String strA = HttpUrl.a(str, 0, str.length(), " \"<>^`{}|/\\?#", true, false, false, true, null);
            this.f.set(i, strA);
            if (a(strA) || b(strA)) {
                throw new IllegalArgumentException(g9.b("unexpected path segment: ", str));
            }
            return this;
        }

        public Builder setEncodedQueryParameter(String str, @Nullable String str2) {
            removeAllEncodedQueryParameters(str);
            addEncodedQueryParameter(str, str2);
            return this;
        }

        public Builder setPathSegment(int i, String str) {
            if (str == null) {
                throw new NullPointerException("pathSegment == null");
            }
            String strA = HttpUrl.a(str, 0, str.length(), " \"<>^`{}|/\\?#", false, false, false, true, null);
            if (a(strA) || b(strA)) {
                throw new IllegalArgumentException(g9.b("unexpected path segment: ", str));
            }
            this.f.set(i, strA);
            return this;
        }

        public Builder setQueryParameter(String str, @Nullable String str2) {
            removeAllQueryParameters(str);
            addQueryParameter(str, str2);
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            String str = this.a;
            if (str != null) {
                sb.append(str);
                sb.append("://");
            } else {
                sb.append("//");
            }
            if (!this.b.isEmpty() || !this.c.isEmpty()) {
                sb.append(this.b);
                if (!this.c.isEmpty()) {
                    sb.append(':');
                    sb.append(this.c);
                }
                sb.append('@');
            }
            String str2 = this.d;
            if (str2 != null) {
                if (str2.indexOf(58) != -1) {
                    sb.append('[');
                    sb.append(this.d);
                    sb.append(']');
                } else {
                    sb.append(this.d);
                }
            }
            if (this.e != -1 || this.a != null) {
                int iDefaultPort = this.e;
                if (iDefaultPort == -1) {
                    iDefaultPort = HttpUrl.defaultPort(this.a);
                }
                String str3 = this.a;
                if (str3 == null || iDefaultPort != HttpUrl.defaultPort(str3)) {
                    sb.append(':');
                    sb.append(iDefaultPort);
                }
            }
            List<String> list = this.f;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                sb.append('/');
                sb.append(list.get(i));
            }
            if (this.g != null) {
                sb.append('?');
                HttpUrl.a(sb, this.g);
            }
            if (this.h != null) {
                sb.append('#');
                sb.append(this.h);
            }
            return sb.toString();
        }

        public Builder username(String str) {
            if (str == null) {
                throw new NullPointerException("username == null");
            }
            this.b = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
            return this;
        }

        @Nullable
        public static String b(String str, int i, int i2) {
            return Util.canonicalizeHost(HttpUrl.a(str, i, i2, false));
        }

        /* JADX WARN: Removed duplicated region for block: B:33:0x0062  */
        /* JADX WARN: Unreachable blocks removed: 1, instructions: 2 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public okhttp3.HttpUrl.Builder a(@javax.annotation.Nullable okhttp3.HttpUrl r21, java.lang.String r22) throws java.lang.NumberFormatException {
            /*
                Method dump skipped, instructions count: 698
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.HttpUrl.Builder.a(okhttp3.HttpUrl, java.lang.String):okhttp3.HttpUrl$Builder");
        }

        public final void a(String str, int i, int i2) {
            if (i == i2) {
                return;
            }
            char cCharAt = str.charAt(i);
            if (cCharAt != '/' && cCharAt != '\\') {
                List<String> list = this.f;
                list.set(list.size() - 1, "");
            } else {
                this.f.clear();
                this.f.add("");
                i++;
            }
            while (true) {
                int i3 = i;
                if (i3 >= i2) {
                    return;
                }
                i = Util.delimiterOffset(str, i3, i2, "/\\");
                boolean z = i < i2;
                a(str, i3, i, z, true);
                if (z) {
                    i++;
                }
            }
        }

        public final void a(String str, int i, int i2, boolean z, boolean z2) {
            String strA = HttpUrl.a(str, i, i2, " \"<>^`{}|/\\?#", z2, false, false, true, null);
            if (a(strA)) {
                return;
            }
            if (b(strA)) {
                if (this.f.remove(r10.size() - 1).isEmpty() && !this.f.isEmpty()) {
                    this.f.set(r10.size() - 1, "");
                    return;
                } else {
                    this.f.add("");
                    return;
                }
            }
            if (this.f.get(r11.size() - 1).isEmpty()) {
                this.f.set(r11.size() - 1, strA);
            } else {
                this.f.add(strA);
            }
            if (z) {
                this.f.add("");
            }
        }

        public final boolean a(String str) {
            return str.equals(".") || str.equalsIgnoreCase("%2e");
        }
    }

    public static List<String> a(String str) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i <= str.length()) {
            int iIndexOf = str.indexOf(38, i);
            if (iIndexOf == -1) {
                iIndexOf = str.length();
            }
            int iIndexOf2 = str.indexOf(61, i);
            if (iIndexOf2 != -1 && iIndexOf2 <= iIndexOf) {
                arrayList.add(str.substring(i, iIndexOf2));
                arrayList.add(str.substring(iIndexOf2 + 1, iIndexOf));
            } else {
                arrayList.add(str.substring(i, iIndexOf));
                arrayList.add(null);
            }
            i = iIndexOf + 1;
        }
        return arrayList;
    }

    @Nullable
    public Builder newBuilder(String str) {
        try {
            Builder builder = new Builder();
            builder.a(this, str);
            return builder;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public static String a(String str, boolean z) {
        return a(str, 0, str.length(), z);
    }

    public final List<String> a(List<String> list, boolean z) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            String str = list.get(i);
            arrayList.add(str != null ? a(str, 0, str.length(), z) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r8, int r9, int r10, boolean r11) {
        /*
            r0 = r9
        L1:
            if (r0 >= r10) goto L60
            char r1 = r8.charAt(r0)
            r2 = 43
            r3 = 37
            if (r1 == r3) goto L15
            if (r1 != r2) goto L12
            if (r11 == 0) goto L12
            goto L15
        L12:
            int r0 = r0 + 1
            goto L1
        L15:
            okio.Buffer r1 = new okio.Buffer
            r1.<init>()
            r1.writeUtf8(r8, r9, r0)
        L1d:
            if (r0 >= r10) goto L5b
            int r9 = r8.codePointAt(r0)
            if (r9 != r3) goto L48
            int r4 = r0 + 2
            if (r4 >= r10) goto L48
            int r5 = r0 + 1
            char r5 = r8.charAt(r5)
            int r5 = okhttp3.internal.Util.decodeHexDigit(r5)
            char r6 = r8.charAt(r4)
            int r6 = okhttp3.internal.Util.decodeHexDigit(r6)
            r7 = -1
            if (r5 == r7) goto L52
            if (r6 == r7) goto L52
            int r0 = r5 << 4
            int r0 = r0 + r6
            r1.writeByte(r0)
            r0 = r4
            goto L55
        L48:
            if (r9 != r2) goto L52
            if (r11 == 0) goto L52
            r4 = 32
            r1.writeByte(r4)
            goto L55
        L52:
            r1.writeUtf8CodePoint(r9)
        L55:
            int r9 = java.lang.Character.charCount(r9)
            int r0 = r0 + r9
            goto L1d
        L5b:
            java.lang.String r8 = r1.readUtf8()
            return r8
        L60:
            java.lang.String r8 = r8.substring(r9, r10)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.HttpUrl.a(java.lang.String, int, int, boolean):java.lang.String");
    }

    public static boolean a(String str, int i, int i2) {
        int i3 = i + 2;
        return i3 < i2 && str.charAt(i) == '%' && Util.decodeHexDigit(str.charAt(i + 1)) != -1 && Util.decodeHexDigit(str.charAt(i3)) != -1;
    }

    public static String a(String str, int i, int i2, String str2, boolean z, boolean z2, boolean z3, boolean z4, @Nullable Charset charset) {
        int iCharCount = i;
        while (iCharCount < i2) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt >= 32 && iCodePointAt != 127 && ((iCodePointAt < 128 || !z4) && str2.indexOf(iCodePointAt) == -1 && ((iCodePointAt != 37 || (z && (!z2 || a(str, iCharCount, i2)))) && (iCodePointAt != 43 || !z3)))) {
                iCharCount += Character.charCount(iCodePointAt);
            } else {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, i, iCharCount);
                Buffer buffer2 = null;
                while (iCharCount < i2) {
                    int iCodePointAt2 = str.codePointAt(iCharCount);
                    if (!z || (iCodePointAt2 != 9 && iCodePointAt2 != 10 && iCodePointAt2 != 12 && iCodePointAt2 != 13)) {
                        if (iCodePointAt2 == 43 && z3) {
                            buffer.writeUtf8(z ? "+" : "%2B");
                        } else if (iCodePointAt2 >= 32 && iCodePointAt2 != 127 && ((iCodePointAt2 < 128 || !z4) && str2.indexOf(iCodePointAt2) == -1 && (iCodePointAt2 != 37 || (z && (!z2 || a(str, iCharCount, i2)))))) {
                            buffer.writeUtf8CodePoint(iCodePointAt2);
                        } else {
                            if (buffer2 == null) {
                                buffer2 = new Buffer();
                            }
                            if (charset != null && !charset.equals(StandardCharsets.UTF_8)) {
                                buffer2.writeString(str, iCharCount, Character.charCount(iCodePointAt2) + iCharCount, charset);
                            } else {
                                buffer2.writeUtf8CodePoint(iCodePointAt2);
                            }
                            while (!buffer2.exhausted()) {
                                int i3 = buffer2.readByte() & 255;
                                buffer.writeByte(37);
                                buffer.writeByte((int) j[(i3 >> 4) & 15]);
                                buffer.writeByte((int) j[i3 & 15]);
                            }
                        }
                    }
                    iCharCount += Character.charCount(iCodePointAt2);
                }
                return buffer.readUtf8();
            }
        }
        return str.substring(i, i2);
    }

    public static String a(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, @Nullable Charset charset) {
        return a(str, 0, str.length(), str2, z, z2, z3, z4, charset);
    }

    public static String a(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        return a(str, 0, str.length(), str2, z, z2, z3, z4, null);
    }
}
