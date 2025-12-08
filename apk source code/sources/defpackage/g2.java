package defpackage;

import android.view.ViewTreeObserver;

/* loaded from: classes.dex */
public class g2 implements ViewTreeObserver.OnPreDrawListener {
    public final /* synthetic */ h2 a;

    public g2(h2 h2Var) {
        this.a = h2Var;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        h2 h2Var = this.a;
        float rotation = h2Var.k.getRotation();
        if (h2Var.d == rotation) {
            return true;
        }
        h2Var.d = rotation;
        j2 j2Var = h2Var.c;
        if (j2Var != null) {
            float f = -rotation;
            if (j2Var.d != f) {
                j2Var.d = f;
                j2Var.invalidateSelf();
            }
        }
        y1 y1Var = h2Var.g;
        if (y1Var == null) {
            return true;
        }
        float f2 = -h2Var.d;
        if (f2 == y1Var.l) {
            return true;
        }
        y1Var.l = f2;
        y1Var.invalidateSelf();
        return true;
    }
}
