package android.support.v4.app;

import android.app.ActivityManager;
import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public final class ActivityManagerCompat {
    public static boolean isLowRamDevice(@NonNull ActivityManager activityManager) {
        return activityManager.isLowRamDevice();
    }
}
