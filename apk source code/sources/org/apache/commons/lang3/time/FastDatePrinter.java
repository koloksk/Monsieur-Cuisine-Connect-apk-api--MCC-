package org.apache.commons.lang3.time;

import defpackage.g9;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.FieldPosition;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

/* loaded from: classes.dex */
public class FastDatePrinter implements DatePrinter, Serializable {
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    public static final ConcurrentMap<i, String> f = new ConcurrentHashMap(7);
    public static final long serialVersionUID = 1;
    public final String a;
    public final TimeZone b;
    public final Locale c;
    public transient f[] d;
    public transient int e;

    public static class a implements f {
        public final char a;

        public a(char c) {
            this.a = c;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 1;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.a);
        }
    }

    public static class b implements d {
        public final d a;

        public b(d dVar) {
            this.a = dVar;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(7);
            this.a.a(appendable, i != 1 ? i - 1 : 7);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public void a(Appendable appendable, int i) throws IOException {
            this.a.a(appendable, i);
        }
    }

    public interface d extends f {
        void a(Appendable appendable, int i) throws IOException;
    }

    public static class e implements d {
        public final int a;
        public final int b;

        public e(int i, int i2) {
            if (i2 < 3) {
                throw new IllegalArgumentException();
            }
            this.a = i;
            this.b = i2;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.b;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            FastDatePrinter.a(appendable, calendar.get(this.a), this.b);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            FastDatePrinter.a(appendable, i, this.b);
        }
    }

    public interface f {
        int a();

        void a(Appendable appendable, Calendar calendar) throws IOException;
    }

    public static class g implements f {
        public final String a;

        public g(String str) {
            this.a = str;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.length();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.a);
        }
    }

    public static class i {
        public final TimeZone a;
        public final int b;
        public final Locale c;

        public i(TimeZone timeZone, boolean z, int i, Locale locale) {
            this.a = timeZone;
            if (z) {
                this.b = Integer.MIN_VALUE | i;
            } else {
                this.b = i;
            }
            this.c = locale;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof i)) {
                return false;
            }
            i iVar = (i) obj;
            return this.a.equals(iVar.a) && this.b == iVar.b && this.c.equals(iVar.c);
        }

        public int hashCode() {
            return this.a.hashCode() + ((this.c.hashCode() + (this.b * 31)) * 31);
        }
    }

    public static class j implements f {
        public final Locale a;
        public final int b;
        public final String c;
        public final String d;

        public j(TimeZone timeZone, Locale locale, int i) {
            this.a = locale;
            this.b = i;
            this.c = FastDatePrinter.a(timeZone, false, i, locale);
            this.d = FastDatePrinter.a(timeZone, true, i, locale);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return Math.max(this.c.length(), this.d.length());
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            TimeZone timeZone = calendar.getTimeZone();
            if (calendar.get(16) == 0) {
                appendable.append(FastDatePrinter.a(timeZone, false, this.b, this.a));
            } else {
                appendable.append(FastDatePrinter.a(timeZone, true, this.b, this.a));
            }
        }
    }

    public static class k implements f {
        public static final k b = new k(true);
        public static final k c = new k(false);
        public final boolean a;

        public k(boolean z) {
            this.a = z;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 5;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(16) + calendar.get(15);
            if (i < 0) {
                appendable.append('-');
                i = -i;
            } else {
                appendable.append('+');
            }
            int i2 = i / 3600000;
            FastDatePrinter.a(appendable, i2);
            if (this.a) {
                appendable.append(':');
            }
            FastDatePrinter.a(appendable, (i / 60000) - (i2 * 60));
        }
    }

    public static class l implements d {
        public final d a;

        public l(d dVar) {
            this.a = dVar;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int leastMaximum = calendar.get(10);
            if (leastMaximum == 0) {
                leastMaximum = calendar.getLeastMaximum(10) + 1;
            }
            this.a.a(appendable, leastMaximum);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public void a(Appendable appendable, int i) throws IOException {
            this.a.a(appendable, i);
        }
    }

    public static class m implements d {
        public final d a;

        public m(d dVar) {
            this.a = dVar;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int maximum = calendar.get(11);
            if (maximum == 0) {
                maximum = calendar.getMaximum(11) + 1;
            }
            this.a.a(appendable, maximum);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public void a(Appendable appendable, int i) throws IOException {
            this.a.a(appendable, i);
        }
    }

    public static class o implements d {
        public final int a;

        public o(int i) {
            this.a = i;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 2;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            a(appendable, calendar.get(this.a));
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            if (i < 100) {
                FastDatePrinter.a(appendable, i);
            } else {
                FastDatePrinter.a(appendable, i, 2);
            }
        }
    }

    public static class q implements d {
        public static final q a = new q();

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 2;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            a(appendable, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            if (i < 10) {
                appendable.append((char) (i + 48));
            } else {
                FastDatePrinter.a(appendable, i);
            }
        }
    }

    public static class r implements d {
        public final int a;

        public r(int i) {
            this.a = i;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 4;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            a(appendable, calendar.get(this.a));
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            if (i < 10) {
                appendable.append((char) (i + 48));
            } else if (i < 100) {
                FastDatePrinter.a(appendable, i);
            } else {
                FastDatePrinter.a(appendable, i, 1);
            }
        }
    }

    public static class s implements d {
        public final d a;

        public s(d dVar) {
            this.a = dVar;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            this.a.a(appendable, calendar.getWeekYear());
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public void a(Appendable appendable, int i) throws IOException {
            this.a.a(appendable, i);
        }
    }

    public FastDatePrinter(String str, TimeZone timeZone, Locale locale) {
        this.a = str;
        this.b = timeZone;
        this.c = locale;
        a();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        a();
    }

    public final void a() {
        List<f> pattern = parsePattern();
        f[] fVarArr = (f[]) pattern.toArray(new f[pattern.size()]);
        this.d = fVarArr;
        int length = fVarArr.length;
        int iA = 0;
        while (true) {
            length--;
            if (length < 0) {
                this.e = iA;
                return;
            }
            iA += this.d[length].a();
        }
    }

    @Deprecated
    public StringBuffer applyRules(Calendar calendar, StringBuffer stringBuffer) {
        a(calendar, (Calendar) stringBuffer);
        return stringBuffer;
    }

    public final Calendar b() {
        return Calendar.getInstance(this.b, this.c);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDatePrinter)) {
            return false;
        }
        FastDatePrinter fastDatePrinter = (FastDatePrinter) obj;
        return this.a.equals(fastDatePrinter.a) && this.b.equals(fastDatePrinter.b) && this.c.equals(fastDatePrinter.c);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    @Deprecated
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (obj instanceof Date) {
            return format((Date) obj, stringBuffer);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj, stringBuffer);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue(), stringBuffer);
        }
        StringBuilder sbA = g9.a("Unknown class: ");
        sbA.append(obj == null ? "<null>" : obj.getClass().getName());
        throw new IllegalArgumentException(sbA.toString());
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public Locale getLocale() {
        return this.c;
    }

    public int getMaxLengthEstimate() {
        return this.e;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String getPattern() {
        return this.a;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public TimeZone getTimeZone() {
        return this.b;
    }

    public int hashCode() {
        return (((this.c.hashCode() * 13) + this.b.hashCode()) * 13) + this.a.hashCode();
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x015a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<org.apache.commons.lang3.time.FastDatePrinter.f> parsePattern() {
        /*
            Method dump skipped, instructions count: 462
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.FastDatePrinter.parsePattern():java.util.List");
    }

    public String parseToken(String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        int i2 = iArr[0];
        int length = str.length();
        char cCharAt = str.charAt(i2);
        if ((cCharAt < 'A' || cCharAt > 'Z') && (cCharAt < 'a' || cCharAt > 'z')) {
            sb.append('\'');
            boolean z = false;
            while (i2 < length) {
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 != '\'') {
                    if (!z && ((cCharAt2 >= 'A' && cCharAt2 <= 'Z') || (cCharAt2 >= 'a' && cCharAt2 <= 'z'))) {
                        i2--;
                        break;
                    }
                    sb.append(cCharAt2);
                } else {
                    int i3 = i2 + 1;
                    if (i3 >= length || str.charAt(i3) != '\'') {
                        z = !z;
                    } else {
                        sb.append(cCharAt2);
                        i2 = i3;
                    }
                }
                i2++;
            }
        } else {
            sb.append(cCharAt);
            while (true) {
                int i4 = i2 + 1;
                if (i4 >= length || str.charAt(i4) != cCharAt) {
                    break;
                }
                sb.append(cCharAt);
                i2 = i4;
            }
        }
        iArr[0] = i2;
        return sb.toString();
    }

    public d selectNumberRule(int i2, int i3) {
        return i3 != 1 ? i3 != 2 ? new e(i2, i3) : new o(i2) : new r(i2);
    }

    public String toString() {
        StringBuilder sbA = g9.a("FastDatePrinter[");
        sbA.append(this.a);
        sbA.append(",");
        sbA.append(this.c);
        sbA.append(",");
        sbA.append(this.b.getID());
        sbA.append("]");
        return sbA.toString();
    }

    public static class h implements f {
        public final int a;
        public final String[] b;

        public h(int i, String[] strArr) {
            this.a = i;
            this.b = strArr;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            int length = this.b.length;
            int i = 0;
            while (true) {
                length--;
                if (length < 0) {
                    return i;
                }
                int length2 = this.b[length].length();
                if (length2 > i) {
                    i = length2;
                }
            }
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.b[calendar.get(this.a)]);
        }
    }

    public static class n implements d {
        public static final n a = new n();

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 2;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            FastDatePrinter.a(appendable, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            FastDatePrinter.a(appendable, i);
        }
    }

    public static class p implements d {
        public static final p a = new p();

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 2;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            FastDatePrinter.a(appendable, calendar.get(1) % 100);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            FastDatePrinter.a(appendable, i);
        }
    }

    public static class c implements f {
        public static final c b = new c(3);
        public static final c c = new c(5);
        public static final c d = new c(6);
        public final int a;

        public c(int i) {
            this.a = i;
        }

        public static c a(int i) {
            if (i == 1) {
                return b;
            }
            if (i == 2) {
                return c;
            }
            if (i == 3) {
                return d;
            }
            throw new IllegalArgumentException("invalid number of X");
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(16) + calendar.get(15);
            if (i == 0) {
                appendable.append("Z");
                return;
            }
            if (i < 0) {
                appendable.append('-');
                i = -i;
            } else {
                appendable.append('+');
            }
            int i2 = i / 3600000;
            FastDatePrinter.a(appendable, i2);
            int i3 = this.a;
            if (i3 < 5) {
                return;
            }
            if (i3 == 6) {
                appendable.append(':');
            }
            FastDatePrinter.a(appendable, (i / 60000) - (i2 * 60));
        }
    }

    public final <B extends Appendable> B a(Calendar calendar, B b2) {
        try {
            for (f fVar : this.d) {
                fVar.a(b2, calendar);
            }
        } catch (IOException e2) {
            ExceptionUtils.rethrow(e2);
        }
        return b2;
    }

    public static /* synthetic */ void a(Appendable appendable, int i2) throws IOException {
        appendable.append((char) ((i2 / 10) + 48));
        appendable.append((char) ((i2 % 10) + 48));
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(long j2) {
        Calendar calendarB = b();
        calendarB.setTimeInMillis(j2);
        StringBuilder sb = new StringBuilder(this.e);
        a(calendarB, (Calendar) sb);
        return sb.toString();
    }

    public static /* synthetic */ void a(Appendable appendable, int i2, int i3) throws IOException {
        if (i2 < 10000) {
            int i4 = i2 < 1000 ? i2 < 100 ? i2 < 10 ? 1 : 2 : 3 : 4;
            for (int i5 = i3 - i4; i5 > 0; i5--) {
                appendable.append('0');
            }
            if (i4 != 1) {
                if (i4 != 2) {
                    if (i4 != 3) {
                        if (i4 != 4) {
                            return;
                        }
                        appendable.append((char) ((i2 / 1000) + 48));
                        i2 %= 1000;
                    }
                    if (i2 >= 100) {
                        appendable.append((char) ((i2 / 100) + 48));
                        i2 %= 100;
                    } else {
                        appendable.append('0');
                    }
                }
                if (i2 >= 10) {
                    appendable.append((char) ((i2 / 10) + 48));
                    i2 %= 10;
                } else {
                    appendable.append('0');
                }
            }
            appendable.append((char) (i2 + 48));
            return;
        }
        char[] cArr = new char[10];
        int i6 = 0;
        while (i2 != 0) {
            cArr[i6] = (char) ((i2 % 10) + 48);
            i2 /= 10;
            i6++;
        }
        while (i6 < i3) {
            appendable.append('0');
            i3--;
        }
        while (true) {
            i6--;
            if (i6 < 0) {
                return;
            } else {
                appendable.append(cArr[i6]);
            }
        }
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Date date) {
        Calendar calendarB = b();
        calendarB.setTime(date);
        StringBuilder sb = new StringBuilder(this.e);
        a(calendarB, (Calendar) sb);
        return sb.toString();
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Calendar calendar) {
        return ((StringBuilder) format(calendar, (Calendar) new StringBuilder(this.e))).toString();
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(long j2, StringBuffer stringBuffer) {
        Calendar calendarB = b();
        calendarB.setTimeInMillis(j2);
        a(calendarB, (Calendar) stringBuffer);
        return stringBuffer;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(Date date, StringBuffer stringBuffer) {
        Calendar calendarB = b();
        calendarB.setTime(date);
        a(calendarB, (Calendar) stringBuffer);
        return stringBuffer;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer) {
        return format(calendar.getTime(), stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(long j2, B b2) {
        Calendar calendarB = b();
        calendarB.setTimeInMillis(j2);
        a(calendarB, (Calendar) b2);
        return b2;
    }

    public static String a(TimeZone timeZone, boolean z, int i2, Locale locale) {
        i iVar = new i(timeZone, z, i2, locale);
        String str = f.get(iVar);
        if (str != null) {
            return str;
        }
        String displayName = timeZone.getDisplayName(z, i2, locale);
        String strPutIfAbsent = f.putIfAbsent(iVar, displayName);
        return strPutIfAbsent != null ? strPutIfAbsent : displayName;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Date date, B b2) {
        Calendar calendarB = b();
        calendarB.setTime(date);
        a(calendarB, (Calendar) b2);
        return b2;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Calendar calendar, B b2) {
        if (!calendar.getTimeZone().equals(this.b)) {
            calendar = (Calendar) calendar.clone();
            calendar.setTimeZone(this.b);
        }
        a(calendar, (Calendar) b2);
        return b2;
    }
}
