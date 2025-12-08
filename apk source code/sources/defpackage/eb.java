package defpackage;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

/* loaded from: classes.dex */
public final class eb {
    public final DataCharacter a;
    public final DataCharacter b;
    public final FinderPattern c;

    public eb(DataCharacter dataCharacter, DataCharacter dataCharacter2, FinderPattern finderPattern, boolean z) {
        this.a = dataCharacter;
        this.b = dataCharacter2;
        this.c = finderPattern;
    }

    public static boolean a(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof eb)) {
            return false;
        }
        eb ebVar = (eb) obj;
        return a(this.a, ebVar.a) && a(this.b, ebVar.b) && a(this.c, ebVar.c);
    }

    public int hashCode() {
        return (a(this.a) ^ a(this.b)) ^ a(this.c);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        sb.append(this.a);
        sb.append(" , ");
        sb.append(this.b);
        sb.append(" : ");
        FinderPattern finderPattern = this.c;
        sb.append(finderPattern == null ? "null" : Integer.valueOf(finderPattern.getValue()));
        sb.append(" ]");
        return sb.toString();
    }

    public static int a(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
