package okhttp3;

import defpackage.g9;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;

/* loaded from: classes.dex */
public final class Cookie {
    public static final Pattern j = Pattern.compile("(\\d{2,4})[^\\d]*");
    public static final Pattern k = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
    public static final Pattern l = Pattern.compile("(\\d{1,2})[^\\d]*");
    public static final Pattern m = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
    public final String a;
    public final String b;
    public final long c;
    public final String d;
    public final String e;
    public final boolean f;
    public final boolean g;
    public final boolean h;
    public final boolean i;

    public static final class Builder {

        @Nullable
        public String a;

        @Nullable
        public String b;

        @Nullable
        public String d;
        public boolean f;
        public boolean g;
        public boolean h;
        public boolean i;
        public long c = HttpDate.MAX_DATE;
        public String e = "/";

        public final Builder a(String str, boolean z) {
            if (str == null) {
                throw new NullPointerException("domain == null");
            }
            String strCanonicalizeHost = Util.canonicalizeHost(str);
            if (strCanonicalizeHost == null) {
                throw new IllegalArgumentException(g9.b("unexpected domain: ", str));
            }
            this.d = strCanonicalizeHost;
            this.i = z;
            return this;
        }

        public Cookie build() {
            return new Cookie(this);
        }

        public Builder domain(String str) {
            a(str, false);
            return this;
        }

        public Builder expiresAt(long j) {
            if (j <= 0) {
                j = Long.MIN_VALUE;
            }
            if (j > HttpDate.MAX_DATE) {
                j = 253402300799999L;
            }
            this.c = j;
            this.h = true;
            return this;
        }

        public Builder hostOnlyDomain(String str) {
            a(str, true);
            return this;
        }

        public Builder httpOnly() {
            this.g = true;
            return this;
        }

        public Builder name(String str) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            if (!str.trim().equals(str)) {
                throw new IllegalArgumentException("name is not trimmed");
            }
            this.a = str;
            return this;
        }

        public Builder path(String str) {
            if (!str.startsWith("/")) {
                throw new IllegalArgumentException("path must start with '/'");
            }
            this.e = str;
            return this;
        }

        public Builder secure() {
            this.f = true;
            return this;
        }

        public Builder value(String str) {
            if (str == null) {
                throw new NullPointerException("value == null");
            }
            if (!str.trim().equals(str)) {
                throw new IllegalArgumentException("value is not trimmed");
            }
            this.b = str;
            return this;
        }
    }

    public Cookie(String str, String str2, long j2, String str3, String str4, boolean z, boolean z2, boolean z3, boolean z4) {
        this.a = str;
        this.b = str2;
        this.c = j2;
        this.d = str3;
        this.e = str4;
        this.f = z;
        this.g = z2;
        this.i = z3;
        this.h = z4;
    }

    public static boolean a(String str, String str2) {
        if (str.equals(str2)) {
            return true;
        }
        return str.endsWith(str2) && str.charAt((str.length() - str2.length()) - 1) == '.' && !Util.verifyAsIpAddress(str);
    }

    @Nullable
    public static Cookie parse(HttpUrl httpUrl, String str) throws NumberFormatException {
        long j2;
        String str2;
        String strSubstring;
        long j3;
        long jCurrentTimeMillis = System.currentTimeMillis();
        int length = str.length();
        char c = ';';
        int iDelimiterOffset = Util.delimiterOffset(str, 0, length, ';');
        int iDelimiterOffset2 = Util.delimiterOffset(str, 0, iDelimiterOffset, '=');
        if (iDelimiterOffset2 != iDelimiterOffset) {
            String strTrimSubstring = Util.trimSubstring(str, 0, iDelimiterOffset2);
            if (!strTrimSubstring.isEmpty() && Util.indexOfControlOrNonAscii(strTrimSubstring) == -1) {
                String strTrimSubstring2 = Util.trimSubstring(str, iDelimiterOffset2 + 1, iDelimiterOffset);
                if (Util.indexOfControlOrNonAscii(strTrimSubstring2) == -1) {
                    int i = iDelimiterOffset + 1;
                    boolean z = false;
                    boolean z2 = false;
                    boolean z3 = false;
                    boolean z4 = true;
                    long j4 = -1;
                    String str3 = null;
                    long jA = HttpDate.MAX_DATE;
                    String str4 = null;
                    while (true) {
                        if (i < length) {
                            int iDelimiterOffset3 = Util.delimiterOffset(str, i, length, c);
                            int iDelimiterOffset4 = Util.delimiterOffset(str, i, iDelimiterOffset3, '=');
                            String strTrimSubstring3 = Util.trimSubstring(str, i, iDelimiterOffset4);
                            String strTrimSubstring4 = iDelimiterOffset4 < iDelimiterOffset3 ? Util.trimSubstring(str, iDelimiterOffset4 + 1, iDelimiterOffset3) : "";
                            if (strTrimSubstring3.equalsIgnoreCase("expires")) {
                                try {
                                    jA = a(strTrimSubstring4, 0, strTrimSubstring4.length());
                                    z3 = true;
                                } catch (NumberFormatException | IllegalArgumentException unused) {
                                }
                            } else if (strTrimSubstring3.equalsIgnoreCase("max-age")) {
                                try {
                                    j3 = Long.parseLong(strTrimSubstring4);
                                } catch (NumberFormatException e) {
                                    if (!strTrimSubstring4.matches("-?\\d+")) {
                                        throw e;
                                    }
                                    if (!strTrimSubstring4.startsWith("-")) {
                                        j4 = Long.MAX_VALUE;
                                    }
                                }
                                j4 = j3 <= 0 ? Long.MIN_VALUE : j3;
                                z3 = true;
                            } else if (strTrimSubstring3.equalsIgnoreCase("domain")) {
                                if (strTrimSubstring4.endsWith(".")) {
                                    throw new IllegalArgumentException();
                                }
                                if (strTrimSubstring4.startsWith(".")) {
                                    strTrimSubstring4 = strTrimSubstring4.substring(1);
                                }
                                String strCanonicalizeHost = Util.canonicalizeHost(strTrimSubstring4);
                                if (strCanonicalizeHost == null) {
                                    throw new IllegalArgumentException();
                                }
                                str3 = strCanonicalizeHost;
                                z4 = false;
                            } else if (strTrimSubstring3.equalsIgnoreCase("path")) {
                                str4 = strTrimSubstring4;
                            } else if (strTrimSubstring3.equalsIgnoreCase("secure")) {
                                z = true;
                            } else if (strTrimSubstring3.equalsIgnoreCase("httponly")) {
                                z2 = true;
                            }
                            i = iDelimiterOffset3 + 1;
                            c = ';';
                        } else {
                            if (j4 == Long.MIN_VALUE) {
                                j2 = Long.MIN_VALUE;
                            } else if (j4 != -1) {
                                long j5 = jCurrentTimeMillis + (j4 <= 9223372036854775L ? j4 * 1000 : Long.MAX_VALUE);
                                j2 = (j5 < jCurrentTimeMillis || j5 > HttpDate.MAX_DATE) ? 253402300799999L : j5;
                            } else {
                                j2 = jA;
                            }
                            String strHost = httpUrl.host();
                            if (str3 == null) {
                                str2 = strHost;
                            } else if (a(strHost, str3)) {
                                str2 = str3;
                            }
                            if (strHost.length() == str2.length() || PublicSuffixDatabase.get().getEffectiveTldPlusOne(str2) != null) {
                                String str5 = str4;
                                if (str5 == null || !str5.startsWith("/")) {
                                    String strEncodedPath = httpUrl.encodedPath();
                                    int iLastIndexOf = strEncodedPath.lastIndexOf(47);
                                    strSubstring = iLastIndexOf != 0 ? strEncodedPath.substring(0, iLastIndexOf) : "/";
                                } else {
                                    strSubstring = str5;
                                }
                                return new Cookie(strTrimSubstring, strTrimSubstring2, j2, str2, strSubstring, z, z2, z4, z3);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static List<Cookie> parseAll(HttpUrl httpUrl, Headers headers) throws NumberFormatException {
        List<String> listValues = headers.values("Set-Cookie");
        int size = listValues.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            Cookie cookie = parse(httpUrl, listValues.get(i));
            if (cookie != null) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(cookie);
            }
        }
        return arrayList != null ? Collections.unmodifiableList(arrayList) : Collections.emptyList();
    }

    public String domain() {
        return this.d;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Cookie)) {
            return false;
        }
        Cookie cookie = (Cookie) obj;
        return cookie.a.equals(this.a) && cookie.b.equals(this.b) && cookie.d.equals(this.d) && cookie.e.equals(this.e) && cookie.c == this.c && cookie.f == this.f && cookie.g == this.g && cookie.h == this.h && cookie.i == this.i;
    }

    public long expiresAt() {
        return this.c;
    }

    public int hashCode() {
        int iHashCode = (this.e.hashCode() + ((this.d.hashCode() + ((this.b.hashCode() + ((this.a.hashCode() + 527) * 31)) * 31)) * 31)) * 31;
        long j2 = this.c;
        return ((((((((iHashCode + ((int) (j2 ^ (j2 >>> 32)))) * 31) + (!this.f ? 1 : 0)) * 31) + (!this.g ? 1 : 0)) * 31) + (!this.h ? 1 : 0)) * 31) + (!this.i ? 1 : 0);
    }

    public boolean hostOnly() {
        return this.i;
    }

    public boolean httpOnly() {
        return this.g;
    }

    public boolean matches(HttpUrl httpUrl) {
        if (!(this.i ? httpUrl.host().equals(this.d) : a(httpUrl.host(), this.d))) {
            return false;
        }
        String str = this.e;
        String strEncodedPath = httpUrl.encodedPath();
        if (strEncodedPath.equals(str) || (strEncodedPath.startsWith(str) && (str.endsWith("/") || strEncodedPath.charAt(str.length()) == '/'))) {
            return !this.f || httpUrl.isHttps();
        }
        return false;
    }

    public String name() {
        return this.a;
    }

    public String path() {
        return this.e;
    }

    public boolean persistent() {
        return this.h;
    }

    public boolean secure() {
        return this.f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append('=');
        sb.append(this.b);
        if (this.h) {
            if (this.c == Long.MIN_VALUE) {
                sb.append("; max-age=0");
            } else {
                sb.append("; expires=");
                sb.append(HttpDate.format(new Date(this.c)));
            }
        }
        if (!this.i) {
            sb.append("; domain=");
            sb.append(this.d);
        }
        sb.append("; path=");
        sb.append(this.e);
        if (this.f) {
            sb.append("; secure");
        }
        if (this.g) {
            sb.append("; httponly");
        }
        return sb.toString();
    }

    public String value() {
        return this.b;
    }

    public static long a(String str, int i, int i2) throws NumberFormatException {
        int iA = a(str, i, i2, false);
        Matcher matcher = m.matcher(str);
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        int iIndexOf = -1;
        int i6 = -1;
        int i7 = -1;
        while (iA < i2) {
            int iA2 = a(str, iA + 1, i2, true);
            matcher.region(iA, iA2);
            if (i4 == -1 && matcher.usePattern(m).matches()) {
                i4 = Integer.parseInt(matcher.group(1));
                i6 = Integer.parseInt(matcher.group(2));
                i7 = Integer.parseInt(matcher.group(3));
            } else if (i5 == -1 && matcher.usePattern(l).matches()) {
                i5 = Integer.parseInt(matcher.group(1));
            } else if (iIndexOf == -1 && matcher.usePattern(k).matches()) {
                iIndexOf = k.pattern().indexOf(matcher.group(1).toLowerCase(Locale.US)) / 4;
            } else if (i3 == -1 && matcher.usePattern(j).matches()) {
                i3 = Integer.parseInt(matcher.group(1));
            }
            iA = a(str, iA2 + 1, i2, false);
        }
        if (i3 >= 70 && i3 <= 99) {
            i3 += 1900;
        }
        if (i3 >= 0 && i3 <= 69) {
            i3 += 2000;
        }
        if (i3 < 1601) {
            throw new IllegalArgumentException();
        }
        if (iIndexOf == -1) {
            throw new IllegalArgumentException();
        }
        if (i5 < 1 || i5 > 31) {
            throw new IllegalArgumentException();
        }
        if (i4 < 0 || i4 > 23) {
            throw new IllegalArgumentException();
        }
        if (i6 < 0 || i6 > 59) {
            throw new IllegalArgumentException();
        }
        if (i7 >= 0 && i7 <= 59) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar(Util.UTC);
            gregorianCalendar.setLenient(false);
            gregorianCalendar.set(1, i3);
            gregorianCalendar.set(2, iIndexOf - 1);
            gregorianCalendar.set(5, i5);
            gregorianCalendar.set(11, i4);
            gregorianCalendar.set(12, i6);
            gregorianCalendar.set(13, i7);
            gregorianCalendar.set(14, 0);
            return gregorianCalendar.getTimeInMillis();
        }
        throw new IllegalArgumentException();
    }

    public Cookie(Builder builder) {
        String str = builder.a;
        if (str != null) {
            String str2 = builder.b;
            if (str2 != null) {
                String str3 = builder.d;
                if (str3 != null) {
                    this.a = str;
                    this.b = str2;
                    this.c = builder.c;
                    this.d = str3;
                    this.e = builder.e;
                    this.f = builder.f;
                    this.g = builder.g;
                    this.h = builder.h;
                    this.i = builder.i;
                    return;
                }
                throw new NullPointerException("builder.domain == null");
            }
            throw new NullPointerException("builder.value == null");
        }
        throw new NullPointerException("builder.name == null");
    }

    public static int a(String str, int i, int i2, boolean z) {
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (((cCharAt < ' ' && cCharAt != '\t') || cCharAt >= 127 || (cCharAt >= '0' && cCharAt <= '9') || ((cCharAt >= 'a' && cCharAt <= 'z') || ((cCharAt >= 'A' && cCharAt <= 'Z') || cCharAt == ':'))) == (!z)) {
                return i;
            }
            i++;
        }
        return i2;
    }
}
