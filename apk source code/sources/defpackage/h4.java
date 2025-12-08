package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowId;

@RequiresApi(18)
/* loaded from: classes.dex */
public class h4 implements i4 {
    public final WindowId a;

    public h4(@NonNull View view2) {
        this.a = view2.getWindowId();
    }

    public boolean equals(Object obj) {
        return (obj instanceof h4) && ((h4) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
