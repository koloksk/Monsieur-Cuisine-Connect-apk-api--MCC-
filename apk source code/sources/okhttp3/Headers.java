package okhttp3;

import defpackage.g9;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* loaded from: classes.dex */
public final class Headers {
    public final String[] a;

    public Headers(Builder builder) {
        List<String> list = builder.a;
        this.a = (String[]) list.toArray(new String[list.size()]);
    }

    public static void a(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        if (str.isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt <= ' ' || cCharAt >= 127) {
                throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(cCharAt), Integer.valueOf(i), str));
            }
        }
    }

    public static Headers of(String... strArr) {
        if (strArr == null) {
            throw new NullPointerException("namesAndValues == null");
        }
        if (strArr.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values");
        }
        String[] strArr2 = (String[]) strArr.clone();
        for (int i = 0; i < strArr2.length; i++) {
            if (strArr2[i] == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            strArr2[i] = strArr2[i].trim();
        }
        for (int i2 = 0; i2 < strArr2.length; i2 += 2) {
            String str = strArr2[i2];
            String str2 = strArr2[i2 + 1];
            a(str);
            a(str2, str);
        }
        return new Headers(strArr2);
    }

    public long byteCount() {
        String[] strArr = this.a;
        long length = strArr.length * 2;
        for (int i = 0; i < strArr.length; i++) {
            length += this.a[i].length();
        }
        return length;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Headers) && Arrays.equals(((Headers) obj).a, this.a);
    }

    @Nullable
    public String get(String str) {
        String[] strArr = this.a;
        int length = strArr.length;
        do {
            length -= 2;
            if (length < 0) {
                return null;
            }
        } while (!str.equalsIgnoreCase(strArr[length]));
        return strArr[length + 1];
    }

    @Nullable
    public Date getDate(String str) {
        String str2 = get(str);
        if (str2 != null) {
            return HttpDate.parse(str2);
        }
        return null;
    }

    @Nullable
    @IgnoreJRERequirement
    public Instant getInstant(String str) {
        Date date = getDate(str);
        if (date != null) {
            return date.toInstant();
        }
        return null;
    }

    public int hashCode() {
        return Arrays.hashCode(this.a);
    }

    public String name(int i) {
        return this.a[i * 2];
    }

    public Set<String> names() {
        TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        int size = size();
        for (int i = 0; i < size; i++) {
            treeSet.add(name(i));
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        Collections.addAll(builder.a, this.a);
        return builder;
    }

    public int size() {
        return this.a.length / 2;
    }

    public Map<String, List<String>> toMultimap() {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        int size = size();
        for (int i = 0; i < size; i++) {
            String lowerCase = name(i).toLowerCase(Locale.US);
            List arrayList = (List) treeMap.get(lowerCase);
            if (arrayList == null) {
                arrayList = new ArrayList(2);
                treeMap.put(lowerCase, arrayList);
            }
            arrayList.add(value(i));
        }
        return treeMap;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = size();
        for (int i = 0; i < size; i++) {
            sb.append(name(i));
            sb.append(": ");
            sb.append(value(i));
            sb.append(StringUtils.LF);
        }
        return sb.toString();
    }

    public String value(int i) {
        return this.a[(i * 2) + 1];
    }

    public List<String> values(String str) {
        int size = size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            if (str.equalsIgnoreCase(name(i))) {
                if (arrayList == null) {
                    arrayList = new ArrayList(2);
                }
                arrayList.add(value(i));
            }
        }
        return arrayList != null ? Collections.unmodifiableList(arrayList) : Collections.emptyList();
    }

    public static final class Builder {
        public final List<String> a = new ArrayList(20);

        public Builder a(String str) {
            int iIndexOf = str.indexOf(":", 1);
            if (iIndexOf != -1) {
                a(str.substring(0, iIndexOf), str.substring(iIndexOf + 1));
                return this;
            }
            if (!str.startsWith(":")) {
                this.a.add("");
                this.a.add(str.trim());
                return this;
            }
            String strSubstring = str.substring(1);
            this.a.add("");
            this.a.add(strSubstring.trim());
            return this;
        }

        public Builder add(String str) {
            int iIndexOf = str.indexOf(":");
            if (iIndexOf != -1) {
                return add(str.substring(0, iIndexOf).trim(), str.substring(iIndexOf + 1));
            }
            throw new IllegalArgumentException(g9.b("Unexpected header: ", str));
        }

        public Builder addAll(Headers headers) {
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                a(headers.name(i), headers.value(i));
            }
            return this;
        }

        public Builder addUnsafeNonAscii(String str, String str2) {
            Headers.a(str);
            this.a.add(str);
            this.a.add(str2.trim());
            return this;
        }

        public Headers build() {
            return new Headers(this);
        }

        @Nullable
        public String get(String str) {
            for (int size = this.a.size() - 2; size >= 0; size -= 2) {
                if (str.equalsIgnoreCase(this.a.get(size))) {
                    return this.a.get(size + 1);
                }
            }
            return null;
        }

        public Builder removeAll(String str) {
            int i = 0;
            while (i < this.a.size()) {
                if (str.equalsIgnoreCase(this.a.get(i))) {
                    this.a.remove(i);
                    this.a.remove(i);
                    i -= 2;
                }
                i += 2;
            }
            return this;
        }

        public Builder set(String str, Date date) {
            if (date == null) {
                throw new NullPointerException(g9.a("value for name ", str, " == null"));
            }
            set(str, HttpDate.format(date));
            return this;
        }

        @IgnoreJRERequirement
        public Builder set(String str, Instant instant) {
            if (instant != null) {
                return set(str, new Date(instant.toEpochMilli()));
            }
            throw new NullPointerException(g9.a("value for name ", str, " == null"));
        }

        public Builder add(String str, String str2) {
            Headers.a(str);
            Headers.a(str2, str);
            this.a.add(str);
            this.a.add(str2.trim());
            return this;
        }

        public Builder set(String str, String str2) {
            Headers.a(str);
            Headers.a(str2, str);
            removeAll(str);
            this.a.add(str);
            this.a.add(str2.trim());
            return this;
        }

        public Builder add(String str, Date date) {
            if (date != null) {
                add(str, HttpDate.format(date));
                return this;
            }
            throw new NullPointerException(g9.a("value for name ", str, " == null"));
        }

        public Builder a(String str, String str2) {
            this.a.add(str);
            this.a.add(str2.trim());
            return this;
        }

        @IgnoreJRERequirement
        public Builder add(String str, Instant instant) {
            if (instant != null) {
                return add(str, new Date(instant.toEpochMilli()));
            }
            throw new NullPointerException(g9.a("value for name ", str, " == null"));
        }
    }

    public Headers(String[] strArr) {
        this.a = strArr;
    }

    public static void a(String str, String str2) {
        if (str != null) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char cCharAt = str.charAt(i);
                if ((cCharAt <= 31 && cCharAt != '\t') || cCharAt >= 127) {
                    throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in %s value: %s", Integer.valueOf(cCharAt), Integer.valueOf(i), str2, str));
                }
            }
            return;
        }
        throw new NullPointerException(g9.a("value for name ", str2, " == null"));
    }

    public static Headers of(Map<String, String> map) {
        if (map != null) {
            String[] strArr = new String[map.size() * 2];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    String strTrim = entry.getKey().trim();
                    String strTrim2 = entry.getValue().trim();
                    a(strTrim);
                    a(strTrim2, strTrim);
                    strArr[i] = strTrim;
                    strArr[i + 1] = strTrim2;
                    i += 2;
                } else {
                    throw new IllegalArgumentException("Headers cannot be null");
                }
            }
            return new Headers(strArr);
        }
        throw new NullPointerException("headers == null");
    }
}
