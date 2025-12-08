package android.support.v4.content.res;

import android.content.res.Resources;
import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public final class ConfigurationHelper {
    public static int getDensityDpi(@NonNull Resources resources) {
        return resources.getConfiguration().densityDpi;
    }
}
