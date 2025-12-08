package defpackage;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.appcompat.R;

/* loaded from: classes.dex */
public class p2 {
    public static final int[] a = {R.attr.colorPrimary};

    public static void a(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(a);
        boolean z = !typedArrayObtainStyledAttributes.hasValue(0);
        typedArrayObtainStyledAttributes.recycle();
        if (z) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
        }
    }
}
