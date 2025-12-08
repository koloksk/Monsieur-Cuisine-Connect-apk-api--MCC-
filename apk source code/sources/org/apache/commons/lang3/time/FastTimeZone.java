package org.apache.commons.lang3.time;

import defpackage.no;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class FastTimeZone {
    public static final Pattern a = Pattern.compile("^(?:(?i)GMT)?([+-])?(\\d\\d?)?(:?(\\d\\d?))?$");
    public static final TimeZone b = new no(false, 0, 0);

    public static TimeZone getGmtTimeZone() {
        return b;
    }

    public static TimeZone getTimeZone(String str) {
        TimeZone gmtTimeZone = getGmtTimeZone(str);
        return gmtTimeZone != null ? gmtTimeZone : TimeZone.getTimeZone(str);
    }

    public static TimeZone getGmtTimeZone(String str) {
        if ("Z".equals(str) || "UTC".equals(str)) {
            return b;
        }
        Matcher matcher = a.matcher(str);
        if (!matcher.matches()) {
            return null;
        }
        String strGroup = matcher.group(2);
        boolean z = false;
        int i = strGroup != null ? Integer.parseInt(strGroup) : 0;
        String strGroup2 = matcher.group(4);
        int i2 = strGroup2 != null ? Integer.parseInt(strGroup2) : 0;
        if (i == 0 && i2 == 0) {
            return b;
        }
        String strGroup3 = matcher.group(1);
        if (strGroup3 != null && strGroup3.charAt(0) == '-') {
            z = true;
        }
        return new no(z, i, i2);
    }
}
