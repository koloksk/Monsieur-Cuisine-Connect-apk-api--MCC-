package defpackage;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class fb {
    public final List<eb> a;
    public final int b;
    public final boolean c;

    public fb(List<eb> list, int i, boolean z) {
        this.a = new ArrayList(list);
        this.b = i;
        this.c = z;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof fb)) {
            return false;
        }
        fb fbVar = (fb) obj;
        return this.a.equals(fbVar.a) && this.c == fbVar.c;
    }

    public int hashCode() {
        return this.a.hashCode() ^ Boolean.valueOf(this.c).hashCode();
    }

    public String toString() {
        return "{ " + this.a + " }";
    }
}
