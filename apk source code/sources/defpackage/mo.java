package defpackage;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.FastDateFormat;

/* loaded from: classes.dex */
public abstract class mo<F extends Format> {
    public static final ConcurrentMap<a, String> b = new ConcurrentHashMap(7);
    public final ConcurrentMap<a, F> a = new ConcurrentHashMap(7);

    public static class a {
        public final Object[] a;
        public int b;

        public a(Object... objArr) {
            this.a = objArr;
        }

        public boolean equals(Object obj) {
            return Arrays.equals(this.a, ((a) obj).a);
        }

        public int hashCode() {
            if (this.b == 0) {
                int iHashCode = 0;
                for (Object obj : this.a) {
                    if (obj != null) {
                        iHashCode = obj.hashCode() + (iHashCode * 7);
                    }
                }
                this.b = iHashCode;
            }
            return this.b;
        }
    }

    public F a(String str, TimeZone timeZone, Locale locale) {
        Validate.notNull(str, "pattern must not be null", new Object[0]);
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        a aVar = new a(str, timeZone, locale);
        F f = this.a.get(aVar);
        if (f != null) {
            return f;
        }
        FastDateFormat fastDateFormat = new FastDateFormat(str, timeZone, locale);
        F fPutIfAbsent = this.a.putIfAbsent(aVar, fastDateFormat);
        return fPutIfAbsent != null ? fPutIfAbsent : fastDateFormat;
    }

    public final F a(Integer num, Integer num2, TimeZone timeZone, Locale locale) {
        DateFormat dateTimeInstance;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        a aVar = new a(num, num2, locale);
        String pattern = b.get(aVar);
        if (pattern == null) {
            try {
                if (num == null) {
                    dateTimeInstance = DateFormat.getTimeInstance(num2.intValue(), locale);
                } else if (num2 == null) {
                    dateTimeInstance = DateFormat.getDateInstance(num.intValue(), locale);
                } else {
                    dateTimeInstance = DateFormat.getDateTimeInstance(num.intValue(), num2.intValue(), locale);
                }
                pattern = ((SimpleDateFormat) dateTimeInstance).toPattern();
                String strPutIfAbsent = b.putIfAbsent(aVar, pattern);
                if (strPutIfAbsent != null) {
                    pattern = strPutIfAbsent;
                }
            } catch (ClassCastException unused) {
                throw new IllegalArgumentException("No date time pattern for locale: " + locale);
            }
        }
        return (F) a(pattern, timeZone, locale);
    }

    public F a(int i, int i2, TimeZone timeZone, Locale locale) {
        return (F) a(Integer.valueOf(i), Integer.valueOf(i2), timeZone, locale);
    }
}
