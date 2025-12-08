package com.google.gson.internal;

import defpackage.g9;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class PreJava9DateFormatProvider {
    public static DateFormat getUSDateFormat(int i) {
        String str;
        if (i == 0) {
            str = "EEEE, MMMM d, y";
        } else if (i == 1) {
            str = "MMMM d, y";
        } else if (i == 2) {
            str = "MMM d, y";
        } else {
            if (i != 3) {
                throw new IllegalArgumentException(g9.b("Unknown DateFormat style: ", i));
            }
            str = "M/d/yy";
        }
        return new SimpleDateFormat(str, Locale.US);
    }

    public static DateFormat getUSDateTimeFormat(int i, int i2) {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        if (i == 0) {
            str = "EEEE, MMMM d, yyyy";
        } else if (i == 1) {
            str = "MMMM d, yyyy";
        } else if (i == 2) {
            str = "MMM d, yyyy";
        } else {
            if (i != 3) {
                throw new IllegalArgumentException(g9.b("Unknown DateFormat style: ", i));
            }
            str = "M/d/yy";
        }
        sb.append(str);
        sb.append(StringUtils.SPACE);
        if (i2 == 0 || i2 == 1) {
            str2 = "h:mm:ss a z";
        } else if (i2 == 2) {
            str2 = "h:mm:ss a";
        } else {
            if (i2 != 3) {
                throw new IllegalArgumentException(g9.b("Unknown DateFormat style: ", i2));
            }
            str2 = "h:mm a";
        }
        sb.append(str2);
        return new SimpleDateFormat(sb.toString(), Locale.US);
    }
}
