package com.google.zxing.client.result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes.dex */
public final class CalendarParsedResult extends ParsedResult {
    public static final Pattern m = Pattern.compile("P(?:(\\d+)W)?(?:(\\d+)D)?(?:T(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?)?");
    public static final long[] n = {604800000, DateUtils.MILLIS_PER_DAY, DateUtils.MILLIS_PER_HOUR, 60000, 1000};
    public static final Pattern o = Pattern.compile("[0-9]{8}(T[0-9]{6}Z?)?");
    public final String b;
    public final Date c;
    public final boolean d;
    public final Date e;
    public final boolean f;
    public final String g;
    public final String h;
    public final String[] i;
    public final String j;
    public final double k;
    public final double l;

    public CalendarParsedResult(String str, String str2, String str3, String str4, String str5, String str6, String[] strArr, String str7, double d, double d2) {
        super(ParsedResultType.CALENDAR);
        this.b = str;
        try {
            this.c = a(str2);
            boolean z = false;
            if (str3 == null) {
                long j = -1;
                if (str4 != null) {
                    Matcher matcher = m.matcher(str4);
                    if (matcher.matches()) {
                        int i = 0;
                        j = 0;
                        while (i < n.length) {
                            int i2 = i + 1;
                            if (matcher.group(i2) != null) {
                                j += n[i] * Integer.parseInt(r9);
                            }
                            i = i2;
                        }
                    }
                }
                this.e = j < 0 ? null : new Date(this.c.getTime() + j);
            } else {
                try {
                    this.e = a(str3);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e.toString());
                }
            }
            this.d = str2.length() == 8;
            if (str3 != null && str3.length() == 8) {
                z = true;
            }
            this.f = z;
            this.g = str5;
            this.h = str6;
            this.i = strArr;
            this.j = str7;
            this.k = d;
            this.l = d2;
        } catch (ParseException e2) {
            throw new IllegalArgumentException(e2.toString());
        }
    }

    public static Date a(String str) throws ParseException {
        if (!o.matcher(str).matches()) {
            throw new ParseException(str, 0);
        }
        if (str.length() == 8) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZones.GMT_ID));
            return simpleDateFormat.parse(str);
        }
        if (str.length() != 16 || str.charAt(15) != 'Z') {
            return new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH).parse(str);
        }
        Date date = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH).parse(str.substring(0, 15));
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        long time = date.getTime() + gregorianCalendar.get(15);
        gregorianCalendar.setTime(new Date(time));
        return new Date(time + gregorianCalendar.get(16));
    }

    public String[] getAttendees() {
        return this.i;
    }

    public String getDescription() {
        return this.j;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(100);
        ParsedResult.maybeAppend(this.b, sb);
        ParsedResult.maybeAppend(a(this.d, this.c), sb);
        ParsedResult.maybeAppend(a(this.f, this.e), sb);
        ParsedResult.maybeAppend(this.g, sb);
        ParsedResult.maybeAppend(this.h, sb);
        ParsedResult.maybeAppend(this.i, sb);
        ParsedResult.maybeAppend(this.j, sb);
        return sb.toString();
    }

    public Date getEnd() {
        return this.e;
    }

    public double getLatitude() {
        return this.k;
    }

    public String getLocation() {
        return this.g;
    }

    public double getLongitude() {
        return this.l;
    }

    public String getOrganizer() {
        return this.h;
    }

    public Date getStart() {
        return this.c;
    }

    public String getSummary() {
        return this.b;
    }

    public boolean isEndAllDay() {
        return this.f;
    }

    public boolean isStartAllDay() {
        return this.d;
    }

    public static String a(boolean z, Date date) {
        DateFormat dateTimeInstance;
        if (date == null) {
            return null;
        }
        if (z) {
            dateTimeInstance = DateFormat.getDateInstance(2);
        } else {
            dateTimeInstance = DateFormat.getDateTimeInstance(2, 2);
        }
        return dateTimeInstance.format(date);
    }
}
