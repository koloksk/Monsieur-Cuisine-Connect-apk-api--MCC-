package org.apache.commons.lang3.time;

import android.support.media.ExifInterface;
import defpackage.g9;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class DurationFormatUtils {
    public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.SSS'S'";
    public static final Object a = "y";
    public static final Object b = "M";
    public static final Object c = "d";
    public static final Object d = "H";
    public static final Object e = "m";
    public static final Object f = "s";
    public static final Object g = ExifInterface.LATITUDE_SOUTH;

    public static class a {
        public final Object a;
        public int b = 1;

        public a(Object obj) {
            this.a = obj;
        }

        public static boolean a(a[] aVarArr, Object obj) {
            for (a aVar : aVarArr) {
                if (aVar.a == obj) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (this.a.getClass() != aVar.a.getClass() || this.b != aVar.b) {
                return false;
            }
            Object obj2 = this.a;
            return obj2 instanceof StringBuilder ? obj2.toString().equals(aVar.a.toString()) : obj2 instanceof Number ? obj2.equals(aVar.a) : obj2 == aVar.a;
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            return StringUtils.repeat(this.a.toString(), this.b);
        }
    }

    public static String a(a[] aVarArr, long j, long j2, long j3, long j4, long j5, long j6, long j7, boolean z) {
        int i;
        int i2;
        a[] aVarArr2 = aVarArr;
        StringBuilder sb = new StringBuilder();
        int length = aVarArr2.length;
        int i3 = 0;
        boolean z2 = false;
        while (i3 < length) {
            a aVar = aVarArr2[i3];
            Object obj = aVar.a;
            int i4 = aVar.b;
            if (obj instanceof StringBuilder) {
                sb.append(obj.toString());
                i2 = length;
                i = i3;
            } else {
                if (obj.equals(a)) {
                    sb.append(a(j, z, i4));
                    i2 = length;
                    i = i3;
                } else {
                    if (obj.equals(b)) {
                        i = i3;
                        sb.append(a(j2, z, i4));
                    } else {
                        i = i3;
                        if (obj.equals(c)) {
                            sb.append(a(j3, z, i4));
                        } else if (obj.equals(d)) {
                            sb.append(a(j4, z, i4));
                            i2 = length;
                        } else if (obj.equals(e)) {
                            sb.append(a(j5, z, i4));
                            i2 = length;
                        } else {
                            if (obj.equals(f)) {
                                i2 = length;
                                sb.append(a(j6, z, i4));
                                z2 = true;
                            } else {
                                i2 = length;
                                if (obj.equals(g)) {
                                    if (z2) {
                                        sb.append(a(j7, true, z ? Math.max(3, i4) : 3));
                                    } else {
                                        sb.append(a(j7, z, i4));
                                    }
                                    z2 = false;
                                }
                            }
                            i3 = i + 1;
                            length = i2;
                            aVarArr2 = aVarArr;
                        }
                    }
                    i2 = length;
                }
                z2 = false;
            }
            i3 = i + 1;
            length = i2;
            aVarArr2 = aVarArr;
        }
        return sb.toString();
    }

    public static String formatDuration(long j, String str) {
        return formatDuration(j, str, true);
    }

    public static String formatDurationHMS(long j) {
        return formatDuration(j, "HH:mm:ss.SSS");
    }

    public static String formatDurationISO(long j) {
        return formatDuration(j, ISO_EXTENDED_FORMAT_PATTERN, false);
    }

    public static String formatDurationWords(long j, boolean z, boolean z2) {
        String duration = formatDuration(j, "d' days 'H' hours 'm' minutes 's' seconds'");
        if (z) {
            duration = g9.b(StringUtils.SPACE, duration);
            String strReplaceOnce = StringUtils.replaceOnce(duration, " 0 days", "");
            if (strReplaceOnce.length() != duration.length()) {
                String strReplaceOnce2 = StringUtils.replaceOnce(strReplaceOnce, " 0 hours", "");
                if (strReplaceOnce2.length() != strReplaceOnce.length()) {
                    duration = StringUtils.replaceOnce(strReplaceOnce2, " 0 minutes", "");
                    if (duration.length() != duration.length()) {
                        duration = StringUtils.replaceOnce(duration, " 0 seconds", "");
                    }
                } else {
                    duration = strReplaceOnce;
                }
            }
            if (!duration.isEmpty()) {
                duration = duration.substring(1);
            }
        }
        if (z2) {
            String strReplaceOnce3 = StringUtils.replaceOnce(duration, " 0 seconds", "");
            if (strReplaceOnce3.length() != duration.length()) {
                duration = StringUtils.replaceOnce(strReplaceOnce3, " 0 minutes", "");
                if (duration.length() != strReplaceOnce3.length()) {
                    String strReplaceOnce4 = StringUtils.replaceOnce(duration, " 0 hours", "");
                    if (strReplaceOnce4.length() != duration.length()) {
                        duration = StringUtils.replaceOnce(strReplaceOnce4, " 0 days", "");
                    }
                } else {
                    duration = strReplaceOnce3;
                }
            }
        }
        return StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.SPACE + duration, " 1 seconds", " 1 second"), " 1 minutes", " 1 minute"), " 1 hours", " 1 hour"), " 1 days", " 1 day").trim();
    }

    public static String formatPeriod(long j, long j2, String str) {
        return formatPeriod(j, j2, str, true, TimeZone.getDefault());
    }

    public static String formatPeriodISO(long j, long j2) {
        return formatPeriod(j, j2, ISO_EXTENDED_FORMAT_PATTERN, false, TimeZone.getDefault());
    }

    public static String formatDuration(long j, String str, boolean z) {
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        Validate.inclusiveBetween(0L, Long.MAX_VALUE, j, "durationMillis must not be negative");
        a[] aVarArrA = a(str);
        if (a.a(aVarArrA, c)) {
            long j8 = j / DateUtils.MILLIS_PER_DAY;
            j2 = j - (DateUtils.MILLIS_PER_DAY * j8);
            j3 = j8;
        } else {
            j2 = j;
            j3 = 0;
        }
        if (a.a(aVarArrA, d)) {
            long j9 = j2 / DateUtils.MILLIS_PER_HOUR;
            j2 -= DateUtils.MILLIS_PER_HOUR * j9;
            j4 = j9;
        } else {
            j4 = 0;
        }
        if (a.a(aVarArrA, e)) {
            long j10 = j2 / 60000;
            j2 -= 60000 * j10;
            j5 = j10;
        } else {
            j5 = 0;
        }
        if (a.a(aVarArrA, f)) {
            long j11 = j2 / 1000;
            j7 = j2 - (1000 * j11);
            j6 = j11;
        } else {
            j6 = 0;
            j7 = j2;
        }
        return a(aVarArrA, 0L, 0L, j3, j4, j5, j6, j7, z);
    }

    public static String formatPeriod(long j, long j2, String str, boolean z, TimeZone timeZone) {
        int i = 0;
        Validate.isTrue(j <= j2, "startMillis must not be greater than endMillis", new Object[0]);
        a[] aVarArrA = a(str);
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(new Date(j));
        Calendar calendar2 = Calendar.getInstance(timeZone);
        calendar2.setTime(new Date(j2));
        int i2 = calendar2.get(14) - calendar.get(14);
        int i3 = calendar2.get(13) - calendar.get(13);
        int i4 = calendar2.get(12) - calendar.get(12);
        int i5 = calendar2.get(11) - calendar.get(11);
        int actualMaximum = calendar2.get(5) - calendar.get(5);
        int i6 = calendar2.get(2) - calendar.get(2);
        int i7 = calendar2.get(1) - calendar.get(1);
        while (i2 < 0) {
            i2 += 1000;
            i3--;
        }
        while (i3 < 0) {
            i3 += 60;
            i4--;
        }
        while (i4 < 0) {
            i4 += 60;
            i5--;
        }
        while (i5 < 0) {
            i5 += 24;
            actualMaximum--;
        }
        if (a.a(aVarArrA, b)) {
            while (actualMaximum < 0) {
                actualMaximum += calendar.getActualMaximum(5);
                i6--;
                calendar.add(2, 1);
            }
            while (i6 < 0) {
                i6 += 12;
                i7--;
            }
            if (!a.a(aVarArrA, a) && i7 != 0) {
                while (i7 != 0) {
                    i6 += i7 * 12;
                    i7 = 0;
                }
            }
        } else {
            if (!a.a(aVarArrA, a)) {
                int i8 = calendar2.get(1);
                if (i6 < 0) {
                    i8--;
                }
                while (calendar.get(1) != i8) {
                    int actualMaximum2 = (calendar.getActualMaximum(6) - calendar.get(6)) + actualMaximum;
                    if ((calendar instanceof GregorianCalendar) && calendar.get(2) == 1 && calendar.get(5) == 29) {
                        actualMaximum2++;
                    }
                    calendar.add(1, 1);
                    actualMaximum = calendar.get(6) + actualMaximum2;
                }
                i7 = 0;
            }
            while (calendar.get(2) != calendar2.get(2)) {
                actualMaximum += calendar.getActualMaximum(5);
                calendar.add(2, 1);
            }
            i6 = 0;
            while (actualMaximum < 0) {
                actualMaximum += calendar.getActualMaximum(5);
                i6--;
                calendar.add(2, 1);
            }
        }
        if (!a.a(aVarArrA, c)) {
            i5 += actualMaximum * 24;
            actualMaximum = 0;
        }
        if (!a.a(aVarArrA, d)) {
            i4 += i5 * 60;
            i5 = 0;
        }
        if (!a.a(aVarArrA, e)) {
            i3 += i4 * 60;
            i4 = 0;
        }
        if (a.a(aVarArrA, f)) {
            i = i3;
        } else {
            i2 += i3 * 1000;
        }
        return a(aVarArrA, i7, i6, actualMaximum, i5, i4, i, i2, z);
    }

    public static String a(long j, boolean z, int i) {
        String string = Long.toString(j);
        return z ? StringUtils.leftPad(string, i, '0') : string;
    }

    public static a[] a(String str) {
        Object obj;
        ArrayList arrayList = new ArrayList(str.length());
        boolean z = false;
        StringBuilder sb = null;
        a aVar = null;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (!z || cCharAt == '\'') {
                if (cCharAt != '\'') {
                    if (cCharAt == 'H') {
                        obj = d;
                    } else if (cCharAt == 'M') {
                        obj = b;
                    } else if (cCharAt == 'S') {
                        obj = g;
                    } else if (cCharAt == 'd') {
                        obj = c;
                    } else if (cCharAt == 'm') {
                        obj = e;
                    } else if (cCharAt == 's') {
                        obj = f;
                    } else if (cCharAt != 'y') {
                        if (sb == null) {
                            sb = new StringBuilder();
                            arrayList.add(new a(sb));
                        }
                        sb.append(cCharAt);
                        obj = null;
                    } else {
                        obj = a;
                    }
                } else if (z) {
                    z = false;
                    sb = null;
                    obj = null;
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    arrayList.add(new a(sb2));
                    obj = null;
                    sb = sb2;
                    z = true;
                }
                if (obj != null) {
                    if (aVar != null && aVar.a.equals(obj)) {
                        aVar.b++;
                    } else {
                        a aVar2 = new a(obj);
                        arrayList.add(aVar2);
                        aVar = aVar2;
                    }
                    sb = null;
                }
            } else {
                sb.append(cCharAt);
            }
        }
        if (!z) {
            return (a[]) arrayList.toArray(new a[arrayList.size()]);
        }
        throw new IllegalArgumentException(g9.b("Unmatched quote in format: ", str));
    }
}
