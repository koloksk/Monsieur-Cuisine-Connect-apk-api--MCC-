package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewOverlay;

@RequiresApi(18)
/* loaded from: classes.dex */
public class y3 implements z3 {
    public final ViewOverlay a;

    public y3(@NonNull View view2) {
        this.a = view2.getOverlay();
    }
}
