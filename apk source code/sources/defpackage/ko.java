package defpackage;

/* loaded from: classes.dex */
public final class ko {
    public final Object a;
    public final int b;

    public ko(Object obj) {
        this.b = System.identityHashCode(obj);
        this.a = obj;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ko)) {
            return false;
        }
        ko koVar = (ko) obj;
        return this.b == koVar.b && this.a == koVar.a;
    }

    public int hashCode() {
        return this.b;
    }
}
