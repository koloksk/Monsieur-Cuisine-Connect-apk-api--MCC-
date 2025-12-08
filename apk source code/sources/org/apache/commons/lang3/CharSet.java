package org.apache.commons.lang3;

import defpackage.jo;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class CharSet implements Serializable {
    public static final Map<String, CharSet> COMMON;
    public static final long serialVersionUID = 5947847346149275958L;
    public final Set<jo> a = Collections.synchronizedSet(new HashSet());
    public static final CharSet EMPTY = new CharSet(null);
    public static final CharSet ASCII_ALPHA = new CharSet("a-zA-Z");
    public static final CharSet ASCII_ALPHA_LOWER = new CharSet("a-z");
    public static final CharSet ASCII_ALPHA_UPPER = new CharSet("A-Z");
    public static final CharSet ASCII_NUMERIC = new CharSet("0-9");

    static {
        Map<String, CharSet> mapSynchronizedMap = Collections.synchronizedMap(new HashMap());
        COMMON = mapSynchronizedMap;
        mapSynchronizedMap.put(null, EMPTY);
        COMMON.put("", EMPTY);
        COMMON.put("a-zA-Z", ASCII_ALPHA);
        COMMON.put("A-Za-z", ASCII_ALPHA);
        COMMON.put("a-z", ASCII_ALPHA_LOWER);
        COMMON.put("A-Z", ASCII_ALPHA_UPPER);
        COMMON.put("0-9", ASCII_NUMERIC);
    }

    public CharSet(String... strArr) {
        for (String str : strArr) {
            add(str);
        }
    }

    public static CharSet getInstance(String... strArr) {
        CharSet charSet;
        if (strArr == null) {
            return null;
        }
        return (strArr.length != 1 || (charSet = COMMON.get(strArr[0])) == null) ? new CharSet(strArr) : charSet;
    }

    public void add(String str) {
        if (str == null) {
            return;
        }
        int length = str.length();
        int i = 0;
        while (i < length) {
            int i2 = length - i;
            if (i2 >= 4 && str.charAt(i) == '^' && str.charAt(i + 2) == '-') {
                this.a.add(new jo(str.charAt(i + 1), str.charAt(i + 3), true));
                i += 4;
            } else if (i2 >= 3 && str.charAt(i + 1) == '-') {
                this.a.add(new jo(str.charAt(i), str.charAt(i + 2), false));
                i += 3;
            } else if (i2 < 2 || str.charAt(i) != '^') {
                Set<jo> set = this.a;
                char cCharAt = str.charAt(i);
                set.add(new jo(cCharAt, cCharAt, false));
                i++;
            } else {
                Set<jo> set2 = this.a;
                char cCharAt2 = str.charAt(i + 1);
                set2.add(new jo(cCharAt2, cCharAt2, true));
                i += 2;
            }
        }
    }

    public boolean contains(char c) {
        boolean z;
        Iterator<jo> it = this.a.iterator();
        do {
            z = false;
            if (!it.hasNext()) {
                return false;
            }
            jo next = it.next();
            if ((c >= next.a && c <= next.b) != next.c) {
                z = true;
            }
        } while (!z);
        return true;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CharSet) {
            return this.a.equals(((CharSet) obj).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() + 89;
    }

    public String toString() {
        return this.a.toString();
    }
}
