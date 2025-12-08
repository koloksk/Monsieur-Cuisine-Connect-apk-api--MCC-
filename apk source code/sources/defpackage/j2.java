package defpackage;

import android.support.v7.graphics.drawable.DrawableWrapper;

/* loaded from: classes.dex */
public class j2 extends DrawableWrapper {
    public static final double e = Math.cos(Math.toRadians(45.0d));
    public float b;
    public float c;
    public float d;

    public static float a(float f, float f2, boolean z) {
        if (!z) {
            return f;
        }
        return (float) (((1.0d - e) * f2) + f);
    }

    public static float b(float f, float f2, boolean z) {
        if (!z) {
            return f * 1.5f;
        }
        return (float) (((1.0d - e) * f2) + (f * 1.5f));
    }

    public void a(float f, float f2) {
        throw null;
    }
}
