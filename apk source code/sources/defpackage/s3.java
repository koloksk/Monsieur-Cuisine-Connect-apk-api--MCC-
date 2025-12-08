package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

@RequiresApi(18)
/* loaded from: classes.dex */
public class s3 implements t3 {
    public final ViewGroupOverlay a;

    public s3(@NonNull ViewGroup viewGroup) {
        this.a = viewGroup.getOverlay();
    }

    @Override // defpackage.t3
    public void a(@NonNull View view2) {
        this.a.remove(view2);
    }
}
