package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.transition.R;
import android.view.View;

@RequiresApi(14)
/* loaded from: classes.dex */
public class b4 implements g4 {
    public float a(@NonNull View view2) {
        Float f = (Float) view2.getTag(R.id.save_non_transition_alpha);
        return f != null ? view2.getAlpha() / f.floatValue() : view2.getAlpha();
    }
}
