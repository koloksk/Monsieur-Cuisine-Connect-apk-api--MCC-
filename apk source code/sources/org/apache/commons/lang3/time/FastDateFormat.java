package org.apache.commons.lang3.time;

import defpackage.g9;
import defpackage.mo;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class FastDateFormat extends Format implements DateParser, DatePrinter {
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    public static final mo<FastDateFormat> c = new a();
    public static final long serialVersionUID = 2;
    public final FastDatePrinter a;
    public final FastDateParser b;

    public static class a extends mo<FastDateFormat> {
    }

    public FastDateFormat(String str, TimeZone timeZone, Locale locale) {
        this(str, timeZone, locale, null);
    }

    public static FastDateFormat getDateInstance(int i) {
        return (FastDateFormat) c.a(Integer.valueOf(i), (Integer) null, (TimeZone) null, (Locale) null);
    }

    public static FastDateFormat getDateTimeInstance(int i, int i2) {
        return (FastDateFormat) c.a(Integer.valueOf(i), Integer.valueOf(i2), (TimeZone) null, (Locale) null);
    }

    public static FastDateFormat getInstance() {
        mo<FastDateFormat> moVar = c;
        if (moVar != null) {
            return (FastDateFormat) moVar.a(3, 3, TimeZone.getDefault(), Locale.getDefault());
        }
        throw null;
    }

    public static FastDateFormat getTimeInstance(int i) {
        return (FastDateFormat) c.a((Integer) null, Integer.valueOf(i), (TimeZone) null, (Locale) null);
    }

    @Deprecated
    public StringBuffer applyRules(Calendar calendar, StringBuffer stringBuffer) {
        return this.a.applyRules(calendar, stringBuffer);
    }

    public boolean equals(Object obj) {
        if (obj instanceof FastDateFormat) {
            return this.a.equals(((FastDateFormat) obj).a);
        }
        return false;
    }

    @Override // java.text.Format, org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        String str;
        FastDatePrinter fastDatePrinter = this.a;
        if (fastDatePrinter == null) {
            throw null;
        }
        if (obj instanceof Date) {
            str = fastDatePrinter.format((Date) obj);
        } else if (obj instanceof Calendar) {
            str = fastDatePrinter.format((Calendar) obj);
        } else {
            if (!(obj instanceof Long)) {
                StringBuilder sbA = g9.a("Unknown class: ");
                sbA.append(obj == null ? "<null>" : obj.getClass().getName());
                throw new IllegalArgumentException(sbA.toString());
            }
            str = fastDatePrinter.format(((Long) obj).longValue());
        }
        stringBuffer.append(str);
        return stringBuffer;
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public Locale getLocale() {
        return this.a.getLocale();
    }

    public int getMaxLengthEstimate() {
        return this.a.getMaxLengthEstimate();
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public String getPattern() {
        return this.a.getPattern();
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public TimeZone getTimeZone() {
        return this.a.getTimeZone();
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str) throws ParseException {
        return this.b.parse(str);
    }

    @Override // java.text.Format, org.apache.commons.lang3.time.DateParser
    public Object parseObject(String str, ParsePosition parsePosition) {
        return this.b.parseObject(str, parsePosition);
    }

    public String toString() {
        StringBuilder sbA = g9.a("FastDateFormat[");
        sbA.append(this.a.getPattern());
        sbA.append(",");
        sbA.append(this.a.getLocale());
        sbA.append(",");
        sbA.append(this.a.getTimeZone().getID());
        sbA.append("]");
        return sbA.toString();
    }

    public FastDateFormat(String str, TimeZone timeZone, Locale locale, Date date) {
        this.a = new FastDatePrinter(str, timeZone, locale);
        this.b = new FastDateParser(str, timeZone, locale, date);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str, ParsePosition parsePosition) {
        return this.b.parse(str, parsePosition);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public boolean parse(String str, ParsePosition parsePosition, Calendar calendar) {
        return this.b.parse(str, parsePosition, calendar);
    }

    public static FastDateFormat getDateInstance(int i, Locale locale) {
        return (FastDateFormat) c.a(Integer.valueOf(i), (Integer) null, (TimeZone) null, locale);
    }

    public static FastDateFormat getDateTimeInstance(int i, int i2, Locale locale) {
        return (FastDateFormat) c.a(Integer.valueOf(i), Integer.valueOf(i2), (TimeZone) null, locale);
    }

    public static FastDateFormat getTimeInstance(int i, Locale locale) {
        return (FastDateFormat) c.a((Integer) null, Integer.valueOf(i), (TimeZone) null, locale);
    }

    public static FastDateFormat getInstance(String str) {
        return (FastDateFormat) c.a(str, null, null);
    }

    public static FastDateFormat getInstance(String str, TimeZone timeZone) {
        return (FastDateFormat) c.a(str, timeZone, null);
    }

    public static FastDateFormat getDateInstance(int i, TimeZone timeZone) {
        return (FastDateFormat) c.a(Integer.valueOf(i), (Integer) null, timeZone, (Locale) null);
    }

    public static FastDateFormat getDateTimeInstance(int i, int i2, TimeZone timeZone) {
        return getDateTimeInstance(i, i2, timeZone, null);
    }

    public static FastDateFormat getInstance(String str, Locale locale) {
        return (FastDateFormat) c.a(str, null, locale);
    }

    public static FastDateFormat getTimeInstance(int i, TimeZone timeZone) {
        return (FastDateFormat) c.a((Integer) null, Integer.valueOf(i), timeZone, (Locale) null);
    }

    public static FastDateFormat getDateTimeInstance(int i, int i2, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) c.a(Integer.valueOf(i), Integer.valueOf(i2), timeZone, locale);
    }

    public static FastDateFormat getInstance(String str, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) c.a(str, timeZone, locale);
    }

    public static FastDateFormat getDateInstance(int i, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) c.a(Integer.valueOf(i), (Integer) null, timeZone, locale);
    }

    public static FastDateFormat getTimeInstance(int i, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) c.a((Integer) null, Integer.valueOf(i), timeZone, locale);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(long j) {
        return this.a.format(j);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Date date) {
        return this.a.format(date);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Calendar calendar) {
        return this.a.format(calendar);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    @Deprecated
    public StringBuffer format(long j, StringBuffer stringBuffer) {
        return this.a.format(j, stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    @Deprecated
    public StringBuffer format(Date date, StringBuffer stringBuffer) {
        return this.a.format(date, stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    @Deprecated
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer) {
        return this.a.format(calendar, stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(long j, B b) {
        return (B) this.a.format(j, (long) b);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Date date, B b) {
        return (B) this.a.format(date, (Date) b);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Calendar calendar, B b) {
        return (B) this.a.format(calendar, (Calendar) b);
    }
}
