package mcapi.json;

import defpackage.g9;

/* loaded from: classes.dex */
public class ConstraintResponse {
    public String constraint;

    public String toString() {
        StringBuilder sbA = g9.a("CONSTRAINT ");
        sbA.append(this.constraint);
        return sbA.toString();
    }
}
