package defpackage;

import android.R;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.res.Resources;
import android.support.annotation.RequiresApi;
import android.view.View;

@RequiresApi(21)
/* loaded from: classes.dex */
public class r2 {
    public static final int[] a = {R.attr.stateListAnimator};

    public static void a(View view2, float f) throws Resources.NotFoundException {
        int integer = view2.getResources().getInteger(android.support.design.R.integer.app_bar_elevation_anim_duration);
        StateListAnimator stateListAnimator = new StateListAnimator();
        long j = integer;
        stateListAnimator.addState(new int[]{R.attr.enabled, android.support.design.R.attr.state_collapsible, -android.support.design.R.attr.state_collapsed}, ObjectAnimator.ofFloat(view2, "elevation", 0.0f).setDuration(j));
        stateListAnimator.addState(new int[]{R.attr.enabled}, ObjectAnimator.ofFloat(view2, "elevation", f).setDuration(j));
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(view2, "elevation", 0.0f).setDuration(0L));
        view2.setStateListAnimator(stateListAnimator);
    }
}
