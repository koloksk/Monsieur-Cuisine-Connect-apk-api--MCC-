package org.apache.commons.lang3.text;

import defpackage.g9;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

@Deprecated
/* loaded from: classes.dex */
public class StrTokenizer implements ListIterator<String>, Cloneable {
    public static final StrTokenizer j;
    public static final StrTokenizer k;
    public char[] a;
    public String[] b;
    public int c;
    public StrMatcher d;
    public StrMatcher e;
    public StrMatcher f;
    public StrMatcher g;
    public boolean h;
    public boolean i;

    static {
        StrTokenizer strTokenizer = new StrTokenizer();
        j = strTokenizer;
        strTokenizer.setDelimiterMatcher(StrMatcher.commaMatcher());
        j.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        j.setIgnoredMatcher(StrMatcher.noneMatcher());
        j.setTrimmerMatcher(StrMatcher.trimMatcher());
        j.setEmptyTokenAsNull(false);
        j.setIgnoreEmptyTokens(false);
        StrTokenizer strTokenizer2 = new StrTokenizer();
        k = strTokenizer2;
        strTokenizer2.setDelimiterMatcher(StrMatcher.tabMatcher());
        k.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        k.setIgnoredMatcher(StrMatcher.noneMatcher());
        k.setTrimmerMatcher(StrMatcher.trimMatcher());
        k.setEmptyTokenAsNull(false);
        k.setIgnoreEmptyTokens(false);
    }

    public StrTokenizer() {
        this.d = StrMatcher.splitMatcher();
        this.e = StrMatcher.noneMatcher();
        this.f = StrMatcher.noneMatcher();
        this.g = StrMatcher.noneMatcher();
        this.h = false;
        this.i = true;
        this.a = null;
    }

    public static StrTokenizer b() {
        return (StrTokenizer) j.clone();
    }

    public static StrTokenizer c() {
        return (StrTokenizer) k.clone();
    }

    public static StrTokenizer getCSVInstance() {
        return b();
    }

    public static StrTokenizer getTSVInstance() {
        return c();
    }

    public final void a() {
        if (this.b == null) {
            char[] cArr = this.a;
            if (cArr == null) {
                List<String> list = tokenize(null, 0, 0);
                this.b = (String[]) list.toArray(new String[list.size()]);
            } else {
                List<String> list2 = tokenize(cArr, 0, cArr.length);
                this.b = (String[]) list2.toArray(new String[list2.size()]);
            }
        }
    }

    public Object clone() {
        try {
            StrTokenizer strTokenizer = (StrTokenizer) super.clone();
            char[] cArr = strTokenizer.a;
            if (cArr != null) {
                strTokenizer.a = (char[]) cArr.clone();
            }
            strTokenizer.reset();
            return strTokenizer;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public String getContent() {
        if (this.a == null) {
            return null;
        }
        return new String(this.a);
    }

    public StrMatcher getDelimiterMatcher() {
        return this.d;
    }

    public StrMatcher getIgnoredMatcher() {
        return this.f;
    }

    public StrMatcher getQuoteMatcher() {
        return this.e;
    }

    public String[] getTokenArray() {
        a();
        return (String[]) this.b.clone();
    }

    public List<String> getTokenList() {
        a();
        ArrayList arrayList = new ArrayList(this.b.length);
        arrayList.addAll(Arrays.asList(this.b));
        return arrayList;
    }

    public StrMatcher getTrimmerMatcher() {
        return this.g;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public boolean hasNext() {
        a();
        return this.c < this.b.length;
    }

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        a();
        return this.c > 0;
    }

    public boolean isEmptyTokenAsNull() {
        return this.h;
    }

    public boolean isIgnoreEmptyTokens() {
        return this.i;
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return this.c;
    }

    public String nextToken() {
        if (!hasNext()) {
            return null;
        }
        String[] strArr = this.b;
        int i = this.c;
        this.c = i + 1;
        return strArr[i];
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return this.c - 1;
    }

    public String previousToken() {
        if (!hasPrevious()) {
            return null;
        }
        String[] strArr = this.b;
        int i = this.c - 1;
        this.c = i;
        return strArr[i];
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    public StrTokenizer reset() {
        this.c = 0;
        this.b = null;
        return this;
    }

    public StrTokenizer setDelimiterChar(char c) {
        return setDelimiterMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setDelimiterMatcher(StrMatcher strMatcher) {
        if (strMatcher == null) {
            this.d = StrMatcher.noneMatcher();
        } else {
            this.d = strMatcher;
        }
        return this;
    }

    public StrTokenizer setDelimiterString(String str) {
        return setDelimiterMatcher(StrMatcher.stringMatcher(str));
    }

    public StrTokenizer setEmptyTokenAsNull(boolean z) {
        this.h = z;
        return this;
    }

    public StrTokenizer setIgnoreEmptyTokens(boolean z) {
        this.i = z;
        return this;
    }

    public StrTokenizer setIgnoredChar(char c) {
        return setIgnoredMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setIgnoredMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.f = strMatcher;
        }
        return this;
    }

    public StrTokenizer setQuoteChar(char c) {
        return setQuoteMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setQuoteMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.e = strMatcher;
        }
        return this;
    }

    public StrTokenizer setTrimmerMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.g = strMatcher;
        }
        return this;
    }

    public int size() {
        a();
        return this.b.length;
    }

    public String toString() {
        if (this.b == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        StringBuilder sbA = g9.a("StrTokenizer");
        sbA.append(getTokenList());
        return sbA.toString();
    }

    public List<String> tokenize(char[] cArr, int i, int i2) {
        if (cArr == null || i2 == 0) {
            return Collections.emptyList();
        }
        StrBuilder strBuilder = new StrBuilder();
        ArrayList arrayList = new ArrayList();
        while (i >= 0 && i < i2) {
            while (i < i2) {
                int iMax = Math.max(getIgnoredMatcher().isMatch(cArr, i, i, i2), getTrimmerMatcher().isMatch(cArr, i, i, i2));
                if (iMax == 0 || getDelimiterMatcher().isMatch(cArr, i, i, i2) > 0 || getQuoteMatcher().isMatch(cArr, i, i, i2) > 0) {
                    break;
                }
                i += iMax;
            }
            if (i >= i2) {
                a(arrayList, "");
                i = -1;
            } else {
                int iIsMatch = getDelimiterMatcher().isMatch(cArr, i, i, i2);
                if (iIsMatch > 0) {
                    a(arrayList, "");
                    i += iIsMatch;
                } else {
                    int iIsMatch2 = getQuoteMatcher().isMatch(cArr, i, i, i2);
                    i = iIsMatch2 > 0 ? a(cArr, i + iIsMatch2, i2, strBuilder, arrayList, i, iIsMatch2) : a(cArr, i, i2, strBuilder, arrayList, 0, 0);
                }
            }
            if (i >= i2) {
                a(arrayList, "");
            }
        }
        return arrayList;
    }

    public static StrTokenizer getCSVInstance(String str) {
        StrTokenizer strTokenizerB = b();
        strTokenizerB.reset(str);
        return strTokenizerB;
    }

    public static StrTokenizer getTSVInstance(String str) {
        StrTokenizer strTokenizerC = c();
        strTokenizerC.reset(str);
        return strTokenizerC;
    }

    @Override // java.util.ListIterator
    public void add(String str) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        String[] strArr = this.b;
        int i = this.c;
        this.c = i + 1;
        return strArr[i];
    }

    @Override // java.util.ListIterator
    public String previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        String[] strArr = this.b;
        int i = this.c - 1;
        this.c = i;
        return strArr[i];
    }

    @Override // java.util.ListIterator
    public void set(String str) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    public StrTokenizer reset(String str) {
        reset();
        if (str != null) {
            this.a = str.toCharArray();
        } else {
            this.a = null;
        }
        return this;
    }

    public static StrTokenizer getCSVInstance(char[] cArr) {
        StrTokenizer strTokenizerB = b();
        strTokenizerB.reset(cArr);
        return strTokenizerB;
    }

    public static StrTokenizer getTSVInstance(char[] cArr) {
        StrTokenizer strTokenizerC = c();
        strTokenizerC.reset(cArr);
        return strTokenizerC;
    }

    public StrTokenizer reset(char[] cArr) {
        reset();
        this.a = ArrayUtils.clone(cArr);
        return this;
    }

    public final void a(List<String> list, String str) {
        if (StringUtils.isEmpty(str)) {
            if (isIgnoreEmptyTokens()) {
                return;
            }
            if (isEmptyTokenAsNull()) {
                str = null;
            }
        }
        list.add(str);
    }

    public StrTokenizer(String str) {
        this.d = StrMatcher.splitMatcher();
        this.e = StrMatcher.noneMatcher();
        this.f = StrMatcher.noneMatcher();
        this.g = StrMatcher.noneMatcher();
        this.h = false;
        this.i = true;
        if (str != null) {
            this.a = str.toCharArray();
        } else {
            this.a = null;
        }
    }

    public final int a(char[] cArr, int i, int i2, StrBuilder strBuilder, List<String> list, int i3, int i4) {
        strBuilder.clear();
        boolean z = i4 > 0;
        int i5 = i;
        int size = 0;
        while (i5 < i2) {
            if (z) {
                int i6 = size;
                int i7 = i5;
                if (a(cArr, i5, i2, i3, i4)) {
                    int i8 = i7 + i4;
                    if (a(cArr, i8, i2, i3, i4)) {
                        strBuilder.append(cArr, i7, i4);
                        i5 = (i4 * 2) + i7;
                        size = strBuilder.size();
                    } else {
                        size = i6;
                        i5 = i8;
                        z = false;
                    }
                } else {
                    i5 = i7 + 1;
                    strBuilder.append(cArr[i7]);
                    size = strBuilder.size();
                }
            } else {
                int i9 = size;
                int i10 = i5;
                int iIsMatch = getDelimiterMatcher().isMatch(cArr, i10, i, i2);
                if (iIsMatch > 0) {
                    a(list, strBuilder.substring(0, i9));
                    return i10 + iIsMatch;
                }
                if (i4 <= 0 || !a(cArr, i10, i2, i3, i4)) {
                    int iIsMatch2 = getIgnoredMatcher().isMatch(cArr, i10, i, i2);
                    if (iIsMatch2 <= 0) {
                        iIsMatch2 = getTrimmerMatcher().isMatch(cArr, i10, i, i2);
                        if (iIsMatch2 > 0) {
                            strBuilder.append(cArr, i10, iIsMatch2);
                        } else {
                            i5 = i10 + 1;
                            strBuilder.append(cArr[i10]);
                            size = strBuilder.size();
                        }
                    }
                    i5 = i10 + iIsMatch2;
                    size = i9;
                } else {
                    i5 = i10 + i4;
                    size = i9;
                    z = true;
                }
            }
        }
        a(list, strBuilder.substring(0, size));
        return -1;
    }

    public StrTokenizer(String str, char c) {
        this(str);
        setDelimiterChar(c);
    }

    public StrTokenizer(String str, String str2) {
        this(str);
        setDelimiterString(str2);
    }

    public StrTokenizer(String str, StrMatcher strMatcher) {
        this(str);
        setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(String str, char c, char c2) {
        this(str, c);
        setQuoteChar(c2);
    }

    public StrTokenizer(String str, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(str, strMatcher);
        setQuoteMatcher(strMatcher2);
    }

    public final boolean a(char[] cArr, int i, int i2, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i + i5;
            if (i6 >= i2 || cArr[i6] != cArr[i3 + i5]) {
                return false;
            }
        }
        return true;
    }

    public StrTokenizer(char[] cArr) {
        this.d = StrMatcher.splitMatcher();
        this.e = StrMatcher.noneMatcher();
        this.f = StrMatcher.noneMatcher();
        this.g = StrMatcher.noneMatcher();
        this.h = false;
        this.i = true;
        this.a = ArrayUtils.clone(cArr);
    }

    public StrTokenizer(char[] cArr, char c) {
        this(cArr);
        setDelimiterChar(c);
    }

    public StrTokenizer(char[] cArr, String str) {
        this(cArr);
        setDelimiterString(str);
    }

    public StrTokenizer(char[] cArr, StrMatcher strMatcher) {
        this(cArr);
        setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(char[] cArr, char c, char c2) {
        this(cArr, c);
        setQuoteChar(c2);
    }

    public StrTokenizer(char[] cArr, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(cArr, strMatcher);
        setQuoteMatcher(strMatcher2);
    }
}
