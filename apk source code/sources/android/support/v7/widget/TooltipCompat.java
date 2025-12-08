package android.support.v7.widget;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import defpackage.x8;

/* loaded from: classes.dex */
public class TooltipCompat {
    public static void setTooltipText(@NonNull View view2, @Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            view2.setTooltipText(charSequence);
            return;
        }
        x8 x8Var = x8.i;
        if (x8Var != null && x8Var.a == view2) {
            x8.a((x8) null);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            new x8(view2, charSequence);
            return;
        }
        x8 x8Var2 = x8.j;
        if (x8Var2 != null && x8Var2.a == view2) {
            x8Var2.a();
        }
        view2.setOnLongClickListener(null);
        view2.setLongClickable(false);
        view2.setOnHoverListener(null);
    }
}
