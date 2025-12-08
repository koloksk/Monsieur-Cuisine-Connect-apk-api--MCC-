package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import defpackage.g9;

/* loaded from: classes.dex */
public final class NavUtils {
    public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
    public static final String TAG = "NavUtils";

    @Nullable
    public static Intent getParentActivityIntent(@NonNull Activity activity2) {
        Intent parentActivityIntent = activity2.getParentActivityIntent();
        if (parentActivityIntent != null) {
            return parentActivityIntent;
        }
        String parentActivityName = getParentActivityName(activity2);
        if (parentActivityName == null) {
            return null;
        }
        ComponentName componentName = new ComponentName(activity2, parentActivityName);
        try {
            return getParentActivityName(activity2, componentName) == null ? Intent.makeMainActivity(componentName) : new Intent().setComponent(componentName);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "getParentActivityIntent: bad parentActivityName '" + parentActivityName + "' in manifest");
            return null;
        }
    }

    @Nullable
    public static String getParentActivityName(@NonNull Activity activity2) {
        try {
            return getParentActivityName(activity2, activity2.getComponentName());
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void navigateUpFromSameTask(@NonNull Activity activity2) {
        Intent parentActivityIntent = getParentActivityIntent(activity2);
        if (parentActivityIntent != null) {
            navigateUpTo(activity2, parentActivityIntent);
            return;
        }
        StringBuilder sbA = g9.a("Activity ");
        sbA.append(activity2.getClass().getSimpleName());
        sbA.append(" does not have a parent activity name specified.");
        sbA.append(" (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> ");
        sbA.append(" element in your manifest?)");
        throw new IllegalArgumentException(sbA.toString());
    }

    public static void navigateUpTo(@NonNull Activity activity2, @NonNull Intent intent) {
        activity2.navigateUpTo(intent);
    }

    public static boolean shouldUpRecreateTask(@NonNull Activity activity2, @NonNull Intent intent) {
        return activity2.shouldUpRecreateTask(intent);
    }

    @Nullable
    public static String getParentActivityName(@NonNull Context context, @NonNull ComponentName componentName) throws PackageManager.NameNotFoundException {
        String string;
        ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(componentName, 128);
        String str = activityInfo.parentActivityName;
        if (str != null) {
            return str;
        }
        Bundle bundle = activityInfo.metaData;
        if (bundle == null || (string = bundle.getString(PARENT_ACTIVITY)) == null) {
            return null;
        }
        if (string.charAt(0) != '.') {
            return string;
        }
        return context.getPackageName() + string;
    }

    @Nullable
    public static Intent getParentActivityIntent(@NonNull Context context, @NonNull Class<?> cls) throws PackageManager.NameNotFoundException {
        String parentActivityName = getParentActivityName(context, new ComponentName(context, cls));
        if (parentActivityName == null) {
            return null;
        }
        ComponentName componentName = new ComponentName(context, parentActivityName);
        if (getParentActivityName(context, componentName) == null) {
            return Intent.makeMainActivity(componentName);
        }
        return new Intent().setComponent(componentName);
    }

    @Nullable
    public static Intent getParentActivityIntent(@NonNull Context context, @NonNull ComponentName componentName) throws PackageManager.NameNotFoundException {
        String parentActivityName = getParentActivityName(context, componentName);
        if (parentActivityName == null) {
            return null;
        }
        ComponentName componentName2 = new ComponentName(componentName.getPackageName(), parentActivityName);
        if (getParentActivityName(context, componentName2) == null) {
            return Intent.makeMainActivity(componentName2);
        }
        return new Intent().setComponent(componentName2);
    }
}
