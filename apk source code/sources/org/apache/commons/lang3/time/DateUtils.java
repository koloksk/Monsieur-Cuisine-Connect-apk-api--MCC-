package org.apache.commons.lang3.time;

import defpackage.g9;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class DateUtils {
    public static final long MILLIS_PER_DAY = 86400000;
    public static final long MILLIS_PER_HOUR = 3600000;
    public static final long MILLIS_PER_MINUTE = 60000;
    public static final long MILLIS_PER_SECOND = 1000;
    public static final int RANGE_MONTH_MONDAY = 6;
    public static final int RANGE_MONTH_SUNDAY = 5;
    public static final int RANGE_WEEK_CENTER = 4;
    public static final int RANGE_WEEK_MONDAY = 2;
    public static final int RANGE_WEEK_RELATIVE = 3;
    public static final int RANGE_WEEK_SUNDAY = 1;
    public static final int SEMI_MONTH = 1001;
    public static final int[][] a = {new int[]{14}, new int[]{13}, new int[]{12}, new int[]{11, 10}, new int[]{5, 5, 9}, new int[]{2, 1001}, new int[]{1}, new int[]{0}};

    public static class a implements Iterator<Calendar> {
        public final Calendar a;
        public final Calendar b;

        public a(Calendar calendar, Calendar calendar2) {
            this.a = calendar2;
            this.b = calendar;
            calendar.add(5, -1);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b.before(this.a);
        }

        @Override // java.util.Iterator
        public Calendar next() {
            if (this.b.equals(this.a)) {
                throw new NoSuchElementException();
            }
            this.b.add(5, 1);
            return (Calendar) this.b.clone();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public enum b {
        TRUNCATE,
        ROUND,
        CEILING
    }

    public static Date a(String str, Locale locale, String[] strArr, boolean z) throws ParseException {
        if (str == null || strArr == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
        TimeZone timeZone = TimeZone.getDefault();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        ParsePosition parsePosition = new ParsePosition(0);
        Calendar calendar = Calendar.getInstance(timeZone, locale);
        calendar.setLenient(z);
        for (String str2 : strArr) {
            FastDateParser fastDateParser = new FastDateParser(str2, timeZone, locale);
            calendar.clear();
            if (fastDateParser.parse(str, parsePosition, calendar) && parsePosition.getIndex() == str.length()) {
                return calendar.getTime();
            }
            parsePosition.setIndex(0);
        }
        throw new ParseException(g9.b("Unable to parse the date: ", str), -1);
    }

    public static Date addDays(Date date, int i) {
        return a(date, 5, i);
    }

    public static Date addHours(Date date, int i) {
        return a(date, 11, i);
    }

    public static Date addMilliseconds(Date date, int i) {
        return a(date, 14, i);
    }

    public static Date addMinutes(Date date, int i) {
        return a(date, 12, i);
    }

    public static Date addMonths(Date date, int i) {
        return a(date, 2, i);
    }

    public static Date addSeconds(Date date, int i) {
        return a(date, 13, i);
    }

    public static Date addWeeks(Date date, int i) {
        return a(date, 3, i);
    }

    public static Date addYears(Date date, int i) {
        return a(date, 1, i);
    }

    public static Date b(Date date, int i, int i2) {
        a(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.setTime(date);
        calendar.set(i, i2);
        return calendar.getTime();
    }

    public static Date ceiling(Date date, int i) {
        a(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        a(calendar, i, b.CEILING);
        return calendar.getTime();
    }

    public static long getFragmentInDays(Date date, int i) {
        return a(date, i, TimeUnit.DAYS);
    }

    public static long getFragmentInHours(Date date, int i) {
        return a(date, i, TimeUnit.HOURS);
    }

    public static long getFragmentInMilliseconds(Date date, int i) {
        return a(date, i, TimeUnit.MILLISECONDS);
    }

    public static long getFragmentInMinutes(Date date, int i) {
        return a(date, i, TimeUnit.MINUTES);
    }

    public static long getFragmentInSeconds(Date date, int i) {
        return a(date, i, TimeUnit.SECONDS);
    }

    public static boolean isSameDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return isSameDay(calendar, calendar2);
    }

    public static boolean isSameInstant(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date.getTime() == date2.getTime();
    }

    public static boolean isSameLocalTime(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(14) == calendar2.get(14) && calendar.get(13) == calendar2.get(13) && calendar.get(12) == calendar2.get(12) && calendar.get(11) == calendar2.get(11) && calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1) && calendar.get(0) == calendar2.get(0) && calendar.getClass() == calendar2.getClass();
    }

    public static Iterator<Calendar> iterator(Date date, int i) {
        a(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return iterator(calendar, i);
    }

    public static Date parseDate(String str, String... strArr) throws ParseException {
        return parseDate(str, null, strArr);
    }

    public static Date parseDateStrictly(String str, String... strArr) throws ParseException {
        return parseDateStrictly(str, null, strArr);
    }

    public static Date round(Date date, int i) {
        a(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        a(calendar, i, b.ROUND);
        return calendar.getTime();
    }

    public static Date setDays(Date date, int i) {
        return b(date, 5, i);
    }

    public static Date setHours(Date date, int i) {
        return b(date, 11, i);
    }

    public static Date setMilliseconds(Date date, int i) {
        return b(date, 14, i);
    }

    public static Date setMinutes(Date date, int i) {
        return b(date, 12, i);
    }

    public static Date setMonths(Date date, int i) {
        return b(date, 2, i);
    }

    public static Date setSeconds(Date date, int i) {
        return b(date, 13, i);
    }

    public static Date setYears(Date date, int i) {
        return b(date, 1, i);
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date truncate(Date date, int i) {
        a(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        a(calendar, i, b.TRUNCATE);
        return calendar.getTime();
    }

    public static int truncatedCompareTo(Calendar calendar, Calendar calendar2, int i) {
        return truncate(calendar, i).compareTo(truncate(calendar2, i));
    }

    public static boolean truncatedEquals(Calendar calendar, Calendar calendar2, int i) {
        return truncatedCompareTo(calendar, calendar2, i) == 0;
    }

    public static long getFragmentInDays(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.DAYS);
    }

    public static long getFragmentInHours(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.HOURS);
    }

    public static long getFragmentInMilliseconds(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.MILLISECONDS);
    }

    public static long getFragmentInMinutes(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.MINUTES);
    }

    public static long getFragmentInSeconds(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.SECONDS);
    }

    public static Date parseDate(String str, Locale locale, String... strArr) throws ParseException {
        return a(str, locale, strArr, true);
    }

    public static Date parseDateStrictly(String str, Locale locale, String... strArr) throws ParseException {
        return a(str, locale, strArr, false);
    }

    public static boolean truncatedEquals(Date date, Date date2, int i) {
        return truncatedCompareTo(date, date2, i) == 0;
    }

    public static boolean isSameInstant(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.getTime().getTime() == calendar2.getTime().getTime();
    }

    public static Calendar toCalendar(Date date, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(date);
        return calendar;
    }

    public static int truncatedCompareTo(Date date, Date date2, int i) {
        return truncate(date, i).compareTo(truncate(date2, i));
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007d A[LOOP:0: B:30:0x0077->B:32:0x007d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0087 A[LOOP:1: B:33:0x0081->B:35:0x0087, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Iterator<java.util.Calendar> iterator(java.util.Calendar r8, int r9) {
        /*
            if (r8 == 0) goto L91
            r0 = -1
            r1 = 2
            r2 = 5
            r3 = 1
            r4 = 7
            switch(r9) {
                case 1: goto L41;
                case 2: goto L41;
                case 3: goto L41;
                case 4: goto L41;
                case 5: goto L26;
                case 6: goto L26;
                default: goto La;
            }
        La:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "The range style "
            r0.append(r1)
            r0.append(r9)
            java.lang.String r9 = " is not valid."
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            r8.<init>(r9)
            throw r8
        L26:
            java.util.Calendar r8 = truncate(r8, r1)
            java.lang.Object r5 = r8.clone()
            java.util.Calendar r5 = (java.util.Calendar) r5
            r5.add(r1, r3)
            r5.add(r2, r0)
            r6 = 6
            if (r9 != r6) goto L3c
            r6 = r5
            r5 = r8
            goto L66
        L3c:
            r1 = r3
            r6 = r5
            r5 = r8
        L3f:
            r8 = r4
            goto L67
        L41:
            java.util.Calendar r5 = truncate(r8, r2)
            java.util.Calendar r6 = truncate(r8, r2)
            if (r9 == r1) goto L66
            r1 = 3
            if (r9 == r1) goto L5f
            r7 = 4
            if (r9 == r7) goto L53
            r1 = r3
            goto L3f
        L53:
            int r9 = r8.get(r4)
            int r9 = r9 - r1
            int r8 = r8.get(r4)
            int r8 = r8 + r1
            r1 = r9
            goto L67
        L5f:
            int r1 = r8.get(r4)
            int r8 = r1 + (-1)
            goto L67
        L66:
            r8 = r3
        L67:
            if (r1 >= r3) goto L6b
            int r1 = r1 + 7
        L6b:
            if (r1 <= r4) goto L6f
            int r1 = r1 + (-7)
        L6f:
            if (r8 >= r3) goto L73
            int r8 = r8 + 7
        L73:
            if (r8 <= r4) goto L77
            int r8 = r8 + (-7)
        L77:
            int r9 = r5.get(r4)
            if (r9 == r1) goto L81
            r5.add(r2, r0)
            goto L77
        L81:
            int r9 = r6.get(r4)
            if (r9 == r8) goto L8b
            r6.add(r2, r3)
            goto L81
        L8b:
            org.apache.commons.lang3.time.DateUtils$a r8 = new org.apache.commons.lang3.time.DateUtils$a
            r8.<init>(r5, r6)
            return r8
        L91:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "The date must not be null"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.DateUtils.iterator(java.util.Calendar, int):java.util.Iterator");
    }

    public static Calendar ceiling(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            a(calendar2, i, b.CEILING);
            return calendar2;
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar round(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            a(calendar2, i, b.ROUND);
            return calendar2;
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar truncate(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            a(calendar2, i, b.TRUNCATE);
            return calendar2;
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static boolean isSameDay(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(0) == calendar2.get(0) && calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6);
    }

    public static Date ceiling(Object obj, int i) {
        if (obj != null) {
            if (obj instanceof Date) {
                return ceiling((Date) obj, i);
            }
            if (obj instanceof Calendar) {
                return ceiling((Calendar) obj, i).getTime();
            }
            StringBuilder sbA = g9.a("Could not find ceiling of for type: ");
            sbA.append(obj.getClass());
            throw new ClassCastException(sbA.toString());
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date round(Object obj, int i) {
        if (obj != null) {
            if (obj instanceof Date) {
                return round((Date) obj, i);
            }
            if (obj instanceof Calendar) {
                return round((Calendar) obj, i).getTime();
            }
            throw new ClassCastException("Could not round " + obj);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date truncate(Object obj, int i) {
        if (obj != null) {
            if (obj instanceof Date) {
                return truncate((Date) obj, i);
            }
            if (obj instanceof Calendar) {
                return truncate((Calendar) obj, i).getTime();
            }
            throw new ClassCastException("Could not truncate " + obj);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date a(Date date, int i, int i2) {
        a(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(i, i2);
        return calendar.getTime();
    }

    public static void a(Calendar calendar, int i, b bVar) {
        int i2;
        char c;
        boolean z;
        int i3;
        boolean z2;
        char c2;
        if (calendar.get(1) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        }
        if (i == 14) {
            return;
        }
        Date time = calendar.getTime();
        long time2 = time.getTime();
        int i4 = calendar.get(14);
        if (b.TRUNCATE == bVar || i4 < 500) {
            time2 -= i4;
        }
        boolean z3 = i == 13;
        int i5 = calendar.get(13);
        if (!z3 && (b.TRUNCATE == bVar || i5 < 30)) {
            time2 -= i5 * 1000;
        }
        if (i == 12) {
            z3 = true;
        }
        int i6 = calendar.get(12);
        if (!z3 && (b.TRUNCATE == bVar || i6 < 30)) {
            time2 -= i6 * 60000;
        }
        if (time.getTime() != time2) {
            time.setTime(time2);
            calendar.setTime(time);
        }
        int[][] iArr = a;
        int length = iArr.length;
        int i7 = 0;
        boolean z4 = false;
        while (i7 < length) {
            int[] iArr2 = iArr[i7];
            for (int i8 : iArr2) {
                if (i8 == i) {
                    if (bVar == b.CEILING || (bVar == b.ROUND && z4)) {
                        if (i == 1001) {
                            if (calendar.get(5) == 1) {
                                calendar.add(5, 15);
                                return;
                            } else {
                                calendar.add(5, -15);
                                calendar.add(2, 1);
                                return;
                            }
                        }
                        if (i == 9) {
                            if (calendar.get(11) == 0) {
                                calendar.add(11, 12);
                                return;
                            } else {
                                calendar.add(11, -12);
                                calendar.add(5, 1);
                                return;
                            }
                        }
                        calendar.add(iArr2[0], 1);
                        return;
                    }
                    return;
                }
            }
            if (i != 9) {
                if (i == 1001 && iArr2[0] == 5) {
                    i2 = calendar.get(5) - 1;
                    if (i2 >= 15) {
                        i2 -= 15;
                    }
                    z = i2 > 7;
                    c = '\f';
                    i3 = i2;
                    z2 = true;
                }
                c = '\f';
                z = z4;
                z2 = false;
                i3 = 0;
            } else {
                if (iArr2[0] == 11) {
                    i2 = calendar.get(11);
                    c = '\f';
                    if (i2 >= 12) {
                        i2 -= 12;
                    }
                    z = i2 >= 6;
                    i3 = i2;
                    z2 = true;
                }
                c = '\f';
                z = z4;
                z2 = false;
                i3 = 0;
            }
            if (z2) {
                c2 = 0;
            } else {
                c2 = 0;
                int actualMinimum = calendar.getActualMinimum(iArr2[0]);
                int actualMaximum = calendar.getActualMaximum(iArr2[0]);
                int i9 = calendar.get(iArr2[0]) - actualMinimum;
                z = i9 > (actualMaximum - actualMinimum) / 2;
                i3 = i9;
            }
            if (i3 != 0) {
                calendar.set(iArr2[c2], calendar.get(iArr2[c2]) - i3);
            }
            i7++;
            z4 = z;
        }
        throw new IllegalArgumentException("The field " + i + " is not supported");
    }

    public static Iterator<?> iterator(Object obj, int i) {
        if (obj != null) {
            if (obj instanceof Date) {
                return iterator((Date) obj, i);
            }
            if (obj instanceof Calendar) {
                return iterator((Calendar) obj, i);
            }
            throw new ClassCastException("Could not iterate based on " + obj);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static long a(Date date, int i, TimeUnit timeUnit) {
        a(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return a(calendar, i, timeUnit);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long a(java.util.Calendar r9, int r10, java.util.concurrent.TimeUnit r11) {
        /*
            if (r9 == 0) goto L8e
            r0 = 0
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.DAYS
            r3 = 1
            if (r11 != r2) goto Lb
            r2 = 0
            goto Lc
        Lb:
            r2 = r3
        Lc:
            r4 = 6
            r5 = 5
            r6 = 2
            if (r10 == r3) goto L21
            if (r10 == r6) goto L14
            goto L2e
        L14:
            int r7 = r9.get(r5)
            int r7 = r7 - r2
            long r7 = (long) r7
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.DAYS
            long r7 = r11.convert(r7, r2)
            goto L2d
        L21:
            int r7 = r9.get(r4)
            int r7 = r7 - r2
            long r7 = (long) r7
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.DAYS
            long r7 = r11.convert(r7, r2)
        L2d:
            long r0 = r0 + r7
        L2e:
            if (r10 == r3) goto L55
            if (r10 == r6) goto L55
            if (r10 == r5) goto L55
            if (r10 == r4) goto L55
            switch(r10) {
                case 11: goto L63;
                case 12: goto L71;
                case 13: goto L7f;
                case 14: goto L8d;
                default: goto L39;
            }
        L39:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r0 = "The fragment "
            r11.append(r0)
            r11.append(r10)
            java.lang.String r10 = " is not supported"
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            r9.<init>(r10)
            throw r9
        L55:
            r10 = 11
            int r10 = r9.get(r10)
            long r2 = (long) r10
            java.util.concurrent.TimeUnit r10 = java.util.concurrent.TimeUnit.HOURS
            long r2 = r11.convert(r2, r10)
            long r0 = r0 + r2
        L63:
            r10 = 12
            int r10 = r9.get(r10)
            long r2 = (long) r10
            java.util.concurrent.TimeUnit r10 = java.util.concurrent.TimeUnit.MINUTES
            long r2 = r11.convert(r2, r10)
            long r0 = r0 + r2
        L71:
            r10 = 13
            int r10 = r9.get(r10)
            long r2 = (long) r10
            java.util.concurrent.TimeUnit r10 = java.util.concurrent.TimeUnit.SECONDS
            long r2 = r11.convert(r2, r10)
            long r0 = r0 + r2
        L7f:
            r10 = 14
            int r9 = r9.get(r10)
            long r9 = (long) r9
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MILLISECONDS
            long r9 = r11.convert(r9, r2)
            long r0 = r0 + r9
        L8d:
            return r0
        L8e:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "The date must not be null"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.DateUtils.a(java.util.Calendar, int, java.util.concurrent.TimeUnit):long");
    }

    public static void a(Date date) {
        Validate.isTrue(date != null, "The date must not be null", new Object[0]);
    }
}
