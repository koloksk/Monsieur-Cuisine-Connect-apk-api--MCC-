package org.apache.commons.lang3.time;

import defpackage.g9;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes.dex */
public class FastDateParser implements DateParser, Serializable {
    public static final long serialVersionUID = 3;
    public final String a;
    public final TimeZone b;
    public final Locale c;
    public final int d;
    public final int e;
    public transient List<m> f;
    public static final Locale g = new Locale("ja", "JP", "JP");
    public static final Comparator<String> h = new a();
    public static final ConcurrentMap<Locale, l>[] i = new ConcurrentMap[17];
    public static final l j = new b(1);
    public static final l k = new c(2);
    public static final l l = new j(1);
    public static final l m = new j(3);
    public static final l n = new j(4);
    public static final l o = new j(6);
    public static final l p = new j(5);
    public static final l q = new d(7);
    public static final l r = new j(8);
    public static final l s = new j(11);
    public static final l t = new e(11);
    public static final l u = new f(10);
    public static final l v = new j(10);
    public static final l w = new j(12);
    public static final l x = new j(13);
    public static final l y = new j(14);

    public static class a implements Comparator<String> {
        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            return str2.compareTo(str);
        }
    }

    public static class b extends j {
        public b(int i) {
            super(i);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.j
        public int a(FastDateParser fastDateParser, int i) {
            if (i >= 100) {
                return i;
            }
            int i2 = fastDateParser.d + i;
            if (i < fastDateParser.e) {
                i2 += 100;
            }
            return i2;
        }
    }

    public static class c extends j {
        public c(int i) {
            super(i);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.j
        public int a(FastDateParser fastDateParser, int i) {
            return i - 1;
        }
    }

    public static class d extends j {
        public d(int i) {
            super(i);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.j
        public int a(FastDateParser fastDateParser, int i) {
            if (i == 7) {
                return 1;
            }
            return 1 + i;
        }
    }

    public static class e extends j {
        public e(int i) {
            super(i);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.j
        public int a(FastDateParser fastDateParser, int i) {
            if (i == 24) {
                return 0;
            }
            return i;
        }
    }

    public static class f extends j {
        public f(int i) {
            super(i);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.j
        public int a(FastDateParser fastDateParser, int i) {
            if (i == 12) {
                return 0;
            }
            return i;
        }
    }

    public static class g extends k {
        public final int b;
        public final Locale c;
        public final Map<String, Integer> d;

        public g(int i, Calendar calendar, Locale locale) {
            super(null);
            this.b = i;
            this.c = locale;
            StringBuilder sbA = g9.a("((?iu)");
            this.d = FastDateParser.a(calendar, locale, i, sbA);
            sbA.setLength(sbA.length() - 1);
            sbA.append(")");
            this.a = Pattern.compile(sbA.toString());
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.k
        public void a(FastDateParser fastDateParser, Calendar calendar, String str) {
            String lowerCase = str.toLowerCase(this.c);
            Integer num = this.d.get(lowerCase);
            if (num == null) {
                num = this.d.get(lowerCase + ClassUtils.PACKAGE_SEPARATOR_CHAR);
            }
            calendar.set(this.b, num.intValue());
        }
    }

    public static class h extends l {
        public final String a;

        public h(String str) {
            super(null);
            this.a = str;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.l
        public boolean a() {
            return false;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.l
        public boolean a(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i) {
            for (int i2 = 0; i2 < this.a.length(); i2++) {
                int index = parsePosition.getIndex() + i2;
                if (index == str.length()) {
                    parsePosition.setErrorIndex(index);
                    return false;
                }
                if (this.a.charAt(i2) != str.charAt(index)) {
                    parsePosition.setErrorIndex(index);
                    return false;
                }
            }
            parsePosition.setIndex(parsePosition.getIndex() + this.a.length());
            return true;
        }
    }

    public static class i extends k {
        public static final l b = new i("(Z|(?:[+-]\\d{2}))");
        public static final l c = new i("(Z|(?:[+-]\\d{2}\\d{2}))");
        public static final l d = new i("(Z|(?:[+-]\\d{2}(?::)\\d{2}))");

        public i(String str) {
            super(null);
            this.a = Pattern.compile(str);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.k
        public void a(FastDateParser fastDateParser, Calendar calendar, String str) {
            calendar.setTimeZone(FastTimeZone.getGmtTimeZone(str));
        }

        public static l a(int i) {
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
    }

    public static class j extends l {
        public final int a;

        public j(int i) {
            super(null);
            this.a = i;
        }

        public int a(FastDateParser fastDateParser, int i) {
            return i;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.l
        public boolean a() {
            return true;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.l
        public boolean a(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i) throws NumberFormatException {
            int index = parsePosition.getIndex();
            int length = str.length();
            if (i == 0) {
                while (index < length && Character.isWhitespace(str.charAt(index))) {
                    index++;
                }
                parsePosition.setIndex(index);
            } else {
                int i2 = i + index;
                if (length > i2) {
                    length = i2;
                }
            }
            while (index < length && Character.isDigit(str.charAt(index))) {
                index++;
            }
            if (parsePosition.getIndex() == index) {
                parsePosition.setErrorIndex(index);
                return false;
            }
            int i3 = Integer.parseInt(str.substring(parsePosition.getIndex(), index));
            parsePosition.setIndex(index);
            calendar.set(this.a, a(fastDateParser, i3));
            return true;
        }
    }

    public static abstract class k extends l {
        public Pattern a;

        public /* synthetic */ k(a aVar) {
            super(null);
        }

        public abstract void a(FastDateParser fastDateParser, Calendar calendar, String str);

        @Override // org.apache.commons.lang3.time.FastDateParser.l
        public boolean a() {
            return false;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.l
        public boolean a(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i) {
            Matcher matcher = this.a.matcher(str.substring(parsePosition.getIndex()));
            if (!matcher.lookingAt()) {
                parsePosition.setErrorIndex(parsePosition.getIndex());
                return false;
            }
            parsePosition.setIndex(matcher.end(1) + parsePosition.getIndex());
            a(fastDateParser, calendar, matcher.group(1));
            return true;
        }
    }

    public static abstract class l {
        public l() {
        }

        public boolean a() {
            return false;
        }

        public abstract boolean a(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i);

        public /* synthetic */ l(a aVar) {
        }
    }

    public static class m {
        public final l a;
        public final int b;

        public m(l lVar, int i) {
            this.a = lVar;
            this.b = i;
        }
    }

    public static class n extends k {
        public final Locale b;
        public final Map<String, a> c;

        public static class a {
            public TimeZone a;
            public int b;

            public a(TimeZone timeZone, boolean z) {
                this.a = timeZone;
                this.b = z ? timeZone.getDSTSavings() : 0;
            }
        }

        public n(Locale locale) {
            super(null);
            this.c = new HashMap();
            this.b = locale;
            StringBuilder sbA = g9.a("((?iu)[+-]\\d{4}|GMT[+-]\\d{1,2}:\\d{2}");
            TreeSet treeSet = new TreeSet(FastDateParser.h);
            for (String[] strArr : DateFormatSymbols.getInstance(locale).getZoneStrings()) {
                String str = strArr[0];
                if (!str.equalsIgnoreCase(TimeZones.GMT_ID)) {
                    TimeZone timeZone = TimeZone.getTimeZone(str);
                    a aVar = new a(timeZone, false);
                    a aVar2 = aVar;
                    for (int i = 1; i < strArr.length; i++) {
                        if (i == 3) {
                            aVar2 = new a(timeZone, true);
                        } else if (i == 5) {
                            aVar2 = aVar;
                        }
                        if (strArr[i] != null) {
                            String lowerCase = strArr[i].toLowerCase(locale);
                            if (treeSet.add(lowerCase)) {
                                this.c.put(lowerCase, aVar2);
                            }
                        }
                    }
                }
            }
            Iterator it = treeSet.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                sbA.append('|');
                FastDateParser.a(sbA, str2);
            }
            sbA.append(")");
            this.a = Pattern.compile(sbA.toString());
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.k
        public void a(FastDateParser fastDateParser, Calendar calendar, String str) {
            TimeZone gmtTimeZone = FastTimeZone.getGmtTimeZone(str);
            if (gmtTimeZone != null) {
                calendar.setTimeZone(gmtTimeZone);
                return;
            }
            String lowerCase = str.toLowerCase(this.b);
            a aVar = this.c.get(lowerCase);
            if (aVar == null) {
                aVar = this.c.get(lowerCase + ClassUtils.PACKAGE_SEPARATOR_CHAR);
            }
            calendar.set(16, aVar.b);
            calendar.set(15, aVar.a.getRawOffset());
        }
    }

    public FastDateParser(String str, TimeZone timeZone, Locale locale) {
        this(str, timeZone, locale, null);
    }

    public static /* synthetic */ boolean a(char c2) {
        return (c2 >= 'A' && c2 <= 'Z') || (c2 >= 'a' && c2 <= 'z');
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        a(Calendar.getInstance(this.b, this.c));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0123 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(java.util.Calendar r8) {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.FastDateParser.a(java.util.Calendar):void");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDateParser)) {
            return false;
        }
        FastDateParser fastDateParser = (FastDateParser) obj;
        return this.a.equals(fastDateParser.a) && this.b.equals(fastDateParser.b) && this.c.equals(fastDateParser.c);
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public Locale getLocale() {
        return this.c;
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public String getPattern() {
        return this.a;
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public TimeZone getTimeZone() {
        return this.b;
    }

    public int hashCode() {
        return (((this.c.hashCode() * 13) + this.b.hashCode()) * 13) + this.a.hashCode();
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Date date = parse(str, parsePosition);
        if (date != null) {
            return date;
        }
        if (!this.c.equals(g)) {
            throw new ParseException(g9.b("Unparseable date: ", str), parsePosition.getErrorIndex());
        }
        StringBuilder sbA = g9.a("(The ");
        sbA.append(this.c);
        sbA.append(" locale does not support dates before 1868 AD)\nUnparseable date: \"");
        sbA.append(str);
        throw new ParseException(sbA.toString(), parsePosition.getErrorIndex());
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Object parseObject(String str) throws ParseException {
        return parse(str);
    }

    public String toString() {
        StringBuilder sbA = g9.a("FastDateParser[");
        sbA.append(this.a);
        sbA.append(",");
        sbA.append(this.c);
        sbA.append(",");
        sbA.append(this.b.getID());
        sbA.append("]");
        return sbA.toString();
    }

    public FastDateParser(String str, TimeZone timeZone, Locale locale, Date date) {
        int i2;
        this.a = str;
        this.b = timeZone;
        this.c = locale;
        Calendar calendar = Calendar.getInstance(timeZone, locale);
        if (date != null) {
            calendar.setTime(date);
            i2 = calendar.get(1);
        } else if (locale.equals(g)) {
            i2 = 0;
        } else {
            calendar.setTime(new Date());
            i2 = calendar.get(1) - 80;
        }
        int i3 = (i2 / 100) * 100;
        this.d = i3;
        this.e = i2 - i3;
        a(calendar);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Object parseObject(String str, ParsePosition parsePosition) {
        return parse(str, parsePosition);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str, ParsePosition parsePosition) {
        Calendar calendar = Calendar.getInstance(this.b, this.c);
        calendar.clear();
        if (parse(str, parsePosition, calendar)) {
            return calendar.getTime();
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0037  */
    @Override // org.apache.commons.lang3.time.DateParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean parse(java.lang.String r11, java.text.ParsePosition r12, java.util.Calendar r13) {
        /*
            r10 = this;
            java.util.List<org.apache.commons.lang3.time.FastDateParser$m> r0 = r10.f
            java.util.ListIterator r0 = r0.listIterator()
        L6:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L45
            java.lang.Object r1 = r0.next()
            org.apache.commons.lang3.time.FastDateParser$m r1 = (org.apache.commons.lang3.time.FastDateParser.m) r1
            org.apache.commons.lang3.time.FastDateParser$l r2 = r1.a
            boolean r2 = r2.a()
            r3 = 0
            if (r2 == 0) goto L37
            boolean r2 = r0.hasNext()
            if (r2 != 0) goto L22
            goto L37
        L22:
            java.lang.Object r2 = r0.next()
            org.apache.commons.lang3.time.FastDateParser$m r2 = (org.apache.commons.lang3.time.FastDateParser.m) r2
            org.apache.commons.lang3.time.FastDateParser$l r2 = r2.a
            r0.previous()
            boolean r2 = r2.a()
            if (r2 == 0) goto L37
            int r2 = r1.b
            r9 = r2
            goto L38
        L37:
            r9 = r3
        L38:
            org.apache.commons.lang3.time.FastDateParser$l r4 = r1.a
            r5 = r10
            r6 = r13
            r7 = r11
            r8 = r12
            boolean r1 = r4.a(r5, r6, r7, r8, r9)
            if (r1 != 0) goto L6
            return r3
        L45:
            r11 = 1
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.FastDateParser.parse(java.lang.String, java.text.ParsePosition, java.util.Calendar):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.StringBuilder a(java.lang.StringBuilder r6, java.lang.String r7) {
        /*
            r0 = 0
        L1:
            int r1 = r7.length()
            r2 = 63
            r3 = 46
            if (r0 >= r1) goto L38
            char r1 = r7.charAt(r0)
            r4 = 36
            r5 = 92
            if (r1 == r4) goto L2f
            if (r1 == r3) goto L2f
            if (r1 == r2) goto L2f
            r2 = 94
            if (r1 == r2) goto L2f
            r2 = 91
            if (r1 == r2) goto L2f
            if (r1 == r5) goto L2f
            r2 = 123(0x7b, float:1.72E-43)
            if (r1 == r2) goto L2f
            r2 = 124(0x7c, float:1.74E-43)
            if (r1 == r2) goto L2f
            switch(r1) {
                case 40: goto L2f;
                case 41: goto L2f;
                case 42: goto L2f;
                case 43: goto L2f;
                default: goto L2e;
            }
        L2e:
            goto L32
        L2f:
            r6.append(r5)
        L32:
            r6.append(r1)
            int r0 = r0 + 1
            goto L1
        L38:
            int r7 = r6.length()
            int r7 = r7 + (-1)
            char r7 = r6.charAt(r7)
            if (r7 != r3) goto L47
            r6.append(r2)
        L47:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.FastDateParser.a(java.lang.StringBuilder, java.lang.String):java.lang.StringBuilder");
    }

    public static /* synthetic */ Map a(Calendar calendar, Locale locale, int i2, StringBuilder sb) {
        HashMap map = new HashMap();
        Map<String, Integer> displayNames = calendar.getDisplayNames(i2, 0, locale);
        TreeSet treeSet = new TreeSet(h);
        for (Map.Entry<String, Integer> entry : displayNames.entrySet()) {
            String lowerCase = entry.getKey().toLowerCase(locale);
            if (treeSet.add(lowerCase)) {
                map.put(lowerCase, entry.getValue());
            }
        }
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            a(sb, (String) it.next());
            sb.append('|');
        }
        return map;
    }

    public static ConcurrentMap<Locale, l> a(int i2) {
        ConcurrentMap<Locale, l> concurrentMap;
        synchronized (i) {
            if (i[i2] == null) {
                i[i2] = new ConcurrentHashMap(3);
            }
            concurrentMap = i[i2];
        }
        return concurrentMap;
    }

    public final l a(int i2, Calendar calendar) {
        ConcurrentMap<Locale, l> concurrentMapA = a(i2);
        l nVar = concurrentMapA.get(this.c);
        if (nVar == null) {
            nVar = i2 == 15 ? new n(this.c) : new g(i2, calendar, this.c);
            l lVarPutIfAbsent = concurrentMapA.putIfAbsent(this.c, nVar);
            if (lVarPutIfAbsent != null) {
                return lVarPutIfAbsent;
            }
        }
        return nVar;
    }
}
