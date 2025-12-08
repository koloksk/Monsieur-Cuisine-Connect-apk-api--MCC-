package android.support.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.RestrictTo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class AnimationUtilsCompat {
    public static Interpolator loadInterpolator(Context context, int i) throws Resources.NotFoundException {
        return AnimationUtils.loadInterpolator(context, i);
    }
}
