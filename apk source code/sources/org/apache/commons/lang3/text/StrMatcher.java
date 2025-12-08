package org.apache.commons.lang3.text;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

@Deprecated
/* loaded from: classes.dex */
public abstract class StrMatcher {
    public static final StrMatcher a = new a(',');
    public static final StrMatcher b = new a('\t');
    public static final StrMatcher c = new a(' ');
    public static final StrMatcher d = new b(" \t\n\r\f".toCharArray());
    public static final StrMatcher e = new e();
    public static final StrMatcher f = new a('\'');
    public static final StrMatcher g = new a('\"');
    public static final StrMatcher h = new b("'\"".toCharArray());
    public static final StrMatcher i = new c();

    public static final class a extends StrMatcher {
        public final char j;

        public a(char c) {
            this.j = c;
        }

        @Override // org.apache.commons.lang3.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            return this.j == cArr[i] ? 1 : 0;
        }
    }

    public static final class b extends StrMatcher {
        public final char[] j;

        public b(char[] cArr) {
            char[] cArr2 = (char[]) cArr.clone();
            this.j = cArr2;
            Arrays.sort(cArr2);
        }

        @Override // org.apache.commons.lang3.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            return Arrays.binarySearch(this.j, cArr[i]) >= 0 ? 1 : 0;
        }
    }

    public static final class c extends StrMatcher {
        @Override // org.apache.commons.lang3.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            return 0;
        }
    }

    public static final class d extends StrMatcher {
        public final char[] j;

        public d(String str) {
            this.j = str.toCharArray();
        }

        @Override // org.apache.commons.lang3.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            int length = this.j.length;
            if (i + length > i3) {
                return 0;
            }
            int i4 = 0;
            while (true) {
                char[] cArr2 = this.j;
                if (i4 >= cArr2.length) {
                    return length;
                }
                if (cArr2[i4] != cArr[i]) {
                    return 0;
                }
                i4++;
                i++;
            }
        }

        public String toString() {
            return super.toString() + ' ' + Arrays.toString(this.j);
        }
    }

    public static final class e extends StrMatcher {
        @Override // org.apache.commons.lang3.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            return cArr[i] <= ' ' ? 1 : 0;
        }
    }

    public static StrMatcher charMatcher(char c2) {
        return new a(c2);
    }

    public static StrMatcher charSetMatcher(char... cArr) {
        return (cArr == null || cArr.length == 0) ? i : cArr.length == 1 ? new a(cArr[0]) : new b(cArr);
    }

    public static StrMatcher commaMatcher() {
        return a;
    }

    public static StrMatcher doubleQuoteMatcher() {
        return g;
    }

    public static StrMatcher noneMatcher() {
        return i;
    }

    public static StrMatcher quoteMatcher() {
        return h;
    }

    public static StrMatcher singleQuoteMatcher() {
        return f;
    }

    public static StrMatcher spaceMatcher() {
        return c;
    }

    public static StrMatcher splitMatcher() {
        return d;
    }

    public static StrMatcher stringMatcher(String str) {
        return StringUtils.isEmpty(str) ? i : new d(str);
    }

    public static StrMatcher tabMatcher() {
        return b;
    }

    public static StrMatcher trimMatcher() {
        return e;
    }

    public int isMatch(char[] cArr, int i2) {
        return isMatch(cArr, i2, 0, cArr.length);
    }

    public abstract int isMatch(char[] cArr, int i2, int i3, int i4);

    public static StrMatcher charSetMatcher(String str) {
        if (StringUtils.isEmpty(str)) {
            return i;
        }
        if (str.length() == 1) {
            return new a(str.charAt(0));
        }
        return new b(str.toCharArray());
    }
}
