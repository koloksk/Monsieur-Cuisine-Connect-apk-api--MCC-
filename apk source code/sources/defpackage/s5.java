package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import application.Configuration;
import java.util.HashSet;
import java.util.Locale;

@RequiresApi(14)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class s5 {
    public static final Locale[] c = new Locale[0];
    public static final Locale d;
    public static final Locale e;
    public static final Locale f;
    public final Locale[] a;

    @NonNull
    public final String b;

    static {
        new s5(new Locale[0]);
        d = new Locale(Configuration.Language.EN, "XA");
        e = new Locale("ar", "XB");
        f = q5.a("en-Latn");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public s5(@NonNull Locale... localeArr) {
        if (localeArr.length == 0) {
            this.a = c;
            this.b = "";
            return;
        }
        Locale[] localeArr2 = new Locale[localeArr.length];
        HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < localeArr.length; i++) {
            Locale locale = localeArr[i];
            if (locale == null) {
                throw new NullPointerException("list[" + i + "] is null");
            }
            if (hashSet.contains(locale)) {
                throw new IllegalArgumentException("list[" + i + "] is a repetition");
            }
            Locale locale2 = (Locale) locale.clone();
            localeArr2[i] = locale2;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(locale2.getLanguage());
            String country = locale2.getCountry();
            if (country != null && !country.isEmpty()) {
                sb2.append("-");
                sb2.append(locale2.getCountry());
            }
            sb.append(sb2.toString());
            if (i < localeArr.length - 1) {
                sb.append(',');
            }
            hashSet.add(locale2);
        }
        this.a = localeArr2;
        this.b = sb.toString();
    }

    public static boolean b(Locale locale) {
        return d.equals(locale) || e.equals(locale);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0056  */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.util.Locale] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int a(java.util.Locale r8) {
        /*
            r7 = this;
            r0 = 0
            r1 = r0
        L2:
            java.util.Locale[] r2 = r7.a
            int r3 = r2.length
            if (r1 >= r3) goto L6d
            r2 = r2[r1]
            boolean r3 = r8.equals(r2)
            r4 = 1
            if (r3 == 0) goto L11
            goto L67
        L11:
            java.lang.String r3 = r8.getLanguage()
            java.lang.String r5 = r2.getLanguage()
            boolean r3 = r3.equals(r5)
            if (r3 != 0) goto L20
            goto L56
        L20:
            boolean r3 = b(r8)
            if (r3 != 0) goto L56
            boolean r3 = b(r2)
            if (r3 == 0) goto L2d
            goto L56
        L2d:
            java.lang.String r3 = r8.getScript()
            boolean r5 = r3.isEmpty()
            java.lang.String r6 = ""
            if (r5 != 0) goto L3a
            goto L3b
        L3a:
            r3 = r6
        L3b:
            boolean r5 = r3.isEmpty()
            if (r5 == 0) goto L58
            java.lang.String r3 = r8.getCountry()
            boolean r5 = r3.isEmpty()
            if (r5 != 0) goto L67
            java.lang.String r2 = r2.getCountry()
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L56
            goto L67
        L56:
            r4 = r0
            goto L67
        L58:
            java.lang.String r2 = r2.getScript()
            boolean r4 = r2.isEmpty()
            if (r4 != 0) goto L63
            r6 = r2
        L63:
            boolean r4 = r3.equals(r6)
        L67:
            if (r4 <= 0) goto L6a
            return r1
        L6a:
            int r1 = r1 + 1
            goto L2
        L6d:
            r8 = 2147483647(0x7fffffff, float:NaN)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.s5.a(java.util.Locale):int");
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof s5)) {
            return false;
        }
        Locale[] localeArr = ((s5) obj).a;
        if (this.a.length != localeArr.length) {
            return false;
        }
        int i = 0;
        while (true) {
            Locale[] localeArr2 = this.a;
            if (i >= localeArr2.length) {
                return true;
            }
            if (!localeArr2[i].equals(localeArr[i])) {
                return false;
            }
            i++;
        }
    }

    public int hashCode() {
        int iHashCode = 1;
        int i = 0;
        while (true) {
            Locale[] localeArr = this.a;
            if (i >= localeArr.length) {
                return iHashCode;
            }
            iHashCode = (iHashCode * 31) + localeArr[i].hashCode();
            i++;
        }
    }

    public String toString() {
        StringBuilder sbA = g9.a("[");
        int i = 0;
        while (true) {
            Locale[] localeArr = this.a;
            if (i >= localeArr.length) {
                sbA.append("]");
                return sbA.toString();
            }
            sbA.append(localeArr[i]);
            if (i < this.a.length - 1) {
                sbA.append(',');
            }
            i++;
        }
    }
}
