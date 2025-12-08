package android.support.v4.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.util.Pair;
import android.view.View;

/* loaded from: classes.dex */
public class ActivityOptionsCompat {
    public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";

    @RequiresApi(16)
    public static class ActivityOptionsCompatApi16Impl extends ActivityOptionsCompat {
        public final ActivityOptions mActivityOptions;

        public ActivityOptionsCompatApi16Impl(ActivityOptions activityOptions) {
            this.mActivityOptions = activityOptions;
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public Bundle toBundle() {
            return this.mActivityOptions.toBundle();
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public void update(ActivityOptionsCompat activityOptionsCompat) {
            if (activityOptionsCompat instanceof ActivityOptionsCompatApi16Impl) {
                this.mActivityOptions.update(((ActivityOptionsCompatApi16Impl) activityOptionsCompat).mActivityOptions);
            }
        }
    }

    @RequiresApi(23)
    public static class ActivityOptionsCompatApi23Impl extends ActivityOptionsCompatApi16Impl {
        public ActivityOptionsCompatApi23Impl(ActivityOptions activityOptions) {
            super(activityOptions);
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public void requestUsageTimeReport(PendingIntent pendingIntent) {
            this.mActivityOptions.requestUsageTimeReport(pendingIntent);
        }
    }

    @RequiresApi(24)
    public static class ActivityOptionsCompatApi24Impl extends ActivityOptionsCompatApi23Impl {
        public ActivityOptionsCompatApi24Impl(ActivityOptions activityOptions) {
            super(activityOptions);
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public Rect getLaunchBounds() {
            return this.mActivityOptions.getLaunchBounds();
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public ActivityOptionsCompat setLaunchBounds(@Nullable Rect rect) {
            return new ActivityOptionsCompatApi24Impl(this.mActivityOptions.setLaunchBounds(rect));
        }
    }

    @RequiresApi(16)
    public static ActivityOptionsCompat createImpl(ActivityOptions activityOptions) {
        return Build.VERSION.SDK_INT >= 24 ? new ActivityOptionsCompatApi24Impl(activityOptions) : new ActivityOptionsCompatApi23Impl(activityOptions);
    }

    @NonNull
    public static ActivityOptionsCompat makeBasic() {
        return createImpl(ActivityOptions.makeBasic());
    }

    @NonNull
    public static ActivityOptionsCompat makeClipRevealAnimation(@NonNull View view2, int i, int i2, int i3, int i4) {
        return createImpl(ActivityOptions.makeClipRevealAnimation(view2, i, i2, i3, i4));
    }

    @NonNull
    public static ActivityOptionsCompat makeCustomAnimation(@NonNull Context context, int i, int i2) {
        return createImpl(ActivityOptions.makeCustomAnimation(context, i, i2));
    }

    @NonNull
    public static ActivityOptionsCompat makeScaleUpAnimation(@NonNull View view2, int i, int i2, int i3, int i4) {
        return createImpl(ActivityOptions.makeScaleUpAnimation(view2, i, i2, i3, i4));
    }

    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(@NonNull Activity activity2, @NonNull View view2, @NonNull String str) {
        return createImpl(ActivityOptions.makeSceneTransitionAnimation(activity2, view2, str));
    }

    @NonNull
    public static ActivityOptionsCompat makeTaskLaunchBehind() {
        return createImpl(ActivityOptions.makeTaskLaunchBehind());
    }

    @NonNull
    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(@NonNull View view2, @NonNull Bitmap bitmap, int i, int i2) {
        return createImpl(ActivityOptions.makeThumbnailScaleUpAnimation(view2, bitmap, i, i2));
    }

    @Nullable
    public Rect getLaunchBounds() {
        return null;
    }

    public void requestUsageTimeReport(@NonNull PendingIntent pendingIntent) {
    }

    @NonNull
    public ActivityOptionsCompat setLaunchBounds(@Nullable Rect rect) {
        return this;
    }

    @Nullable
    public Bundle toBundle() {
        return null;
    }

    public void update(@NonNull ActivityOptionsCompat activityOptionsCompat) {
    }

    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(@NonNull Activity activity2, Pair<View, String>... pairArr) {
        android.util.Pair[] pairArr2 = null;
        if (pairArr != null) {
            pairArr2 = new android.util.Pair[pairArr.length];
            for (int i = 0; i < pairArr.length; i++) {
                pairArr2[i] = android.util.Pair.create(pairArr[i].first, pairArr[i].second);
            }
        }
        return createImpl(ActivityOptions.makeSceneTransitionAnimation(activity2, pairArr2));
    }
}
