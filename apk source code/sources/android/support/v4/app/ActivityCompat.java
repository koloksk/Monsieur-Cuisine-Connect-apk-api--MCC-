package android.support.v4.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v13.view.DragAndDropPermissionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.view.DragEvent;
import android.view.View;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ActivityCompat extends ContextCompat {
    public static PermissionCompatDelegate sDelegate;

    /* renamed from: android.support.v4.app.ActivityCompat$1, reason: invalid class name */
    public static class AnonymousClass1 implements Runnable {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ String[] val$permissions;
        public final /* synthetic */ int val$requestCode;

        public AnonymousClass1(String[] strArr, Activity activity2, int i) {
            this.val$permissions = strArr;
            this.val$activity = activity2;
            this.val$requestCode = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            int[] iArr = new int[this.val$permissions.length];
            PackageManager packageManager = this.val$activity.getPackageManager();
            String packageName = this.val$activity.getPackageName();
            int length = this.val$permissions.length;
            for (int i = 0; i < length; i++) {
                iArr[i] = packageManager.checkPermission(this.val$permissions[i], packageName);
            }
            ((OnRequestPermissionsResultCallback) this.val$activity).onRequestPermissionsResult(this.val$requestCode, this.val$permissions, iArr);
        }
    }

    public interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr);
    }

    public interface PermissionCompatDelegate {
        boolean onActivityResult(@NonNull Activity activity2, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) int i, int i2, @Nullable Intent intent);

        boolean requestPermissions(@NonNull Activity activity2, @NonNull String[] strArr, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) int i);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface RequestPermissionsRequestCodeValidator {
        void validateRequestPermissionsRequestCode(int i);
    }

    @RequiresApi(21)
    public static class SharedElementCallback21Impl extends android.app.SharedElementCallback {
        public SharedElementCallback mCallback;

        public SharedElementCallback21Impl(SharedElementCallback sharedElementCallback) {
            this.mCallback = sharedElementCallback;
        }

        @Override // android.app.SharedElementCallback
        public Parcelable onCaptureSharedElementSnapshot(View view2, Matrix matrix, RectF rectF) {
            return this.mCallback.onCaptureSharedElementSnapshot(view2, matrix, rectF);
        }

        @Override // android.app.SharedElementCallback
        public View onCreateSnapshotView(Context context, Parcelable parcelable) {
            return this.mCallback.onCreateSnapshotView(context, parcelable);
        }

        @Override // android.app.SharedElementCallback
        public void onMapSharedElements(List<String> list, Map<String, View> map) {
            this.mCallback.onMapSharedElements(list, map);
        }

        @Override // android.app.SharedElementCallback
        public void onRejectSharedElements(List<View> list) {
            this.mCallback.onRejectSharedElements(list);
        }

        @Override // android.app.SharedElementCallback
        public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
            this.mCallback.onSharedElementEnd(list, list2, list3);
        }

        @Override // android.app.SharedElementCallback
        public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
            this.mCallback.onSharedElementStart(list, list2, list3);
        }
    }

    @RequiresApi(23)
    public static class SharedElementCallback23Impl extends SharedElementCallback21Impl {
        public SharedElementCallback23Impl(SharedElementCallback sharedElementCallback) {
            super(sharedElementCallback);
        }

        @Override // android.app.SharedElementCallback
        public void onSharedElementsArrived(List<String> list, List<View> list2, final SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
            this.mCallback.onSharedElementsArrived(list, list2, new SharedElementCallback.OnSharedElementsReadyListener() { // from class: android.support.v4.app.ActivityCompat.SharedElementCallback23Impl.1
                @Override // android.support.v4.app.SharedElementCallback.OnSharedElementsReadyListener
                public void onSharedElementsReady() {
                    onSharedElementsReadyListener.onSharedElementsReady();
                }
            });
        }
    }

    public static void finishAffinity(@NonNull Activity activity2) {
        activity2.finishAffinity();
    }

    public static void finishAfterTransition(@NonNull Activity activity2) {
        activity2.finishAfterTransition();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static PermissionCompatDelegate getPermissionCompatDelegate() {
        return sDelegate;
    }

    @Nullable
    public static Uri getReferrer(@NonNull Activity activity2) {
        return activity2.getReferrer();
    }

    @Deprecated
    public static boolean invalidateOptionsMenu(Activity activity2) {
        activity2.invalidateOptionsMenu();
        return true;
    }

    public static void postponeEnterTransition(@NonNull Activity activity2) {
        activity2.postponeEnterTransition();
    }

    @Nullable
    public static DragAndDropPermissionsCompat requestDragAndDropPermissions(Activity activity2, DragEvent dragEvent) {
        return DragAndDropPermissionsCompat.request(activity2, dragEvent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void requestPermissions(@NonNull Activity activity2, @NonNull String[] strArr, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) int i) {
        PermissionCompatDelegate permissionCompatDelegate = sDelegate;
        if (permissionCompatDelegate == null || !permissionCompatDelegate.requestPermissions(activity2, strArr, i)) {
            if (activity2 instanceof RequestPermissionsRequestCodeValidator) {
                ((RequestPermissionsRequestCodeValidator) activity2).validateRequestPermissionsRequestCode(i);
            }
            activity2.requestPermissions(strArr, i);
        }
    }

    @NonNull
    public static <T extends View> T requireViewById(@NonNull Activity activity2, @IdRes int i) {
        T t = (T) activity2.findViewById(i);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Activity");
    }

    public static void setEnterSharedElementCallback(@NonNull Activity activity2, @Nullable SharedElementCallback sharedElementCallback) {
        activity2.setEnterSharedElementCallback(sharedElementCallback != null ? new SharedElementCallback23Impl(sharedElementCallback) : null);
    }

    public static void setExitSharedElementCallback(@NonNull Activity activity2, @Nullable SharedElementCallback sharedElementCallback) {
        activity2.setExitSharedElementCallback(sharedElementCallback != null ? new SharedElementCallback23Impl(sharedElementCallback) : null);
    }

    public static void setPermissionCompatDelegate(@Nullable PermissionCompatDelegate permissionCompatDelegate) {
        sDelegate = permissionCompatDelegate;
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity2, @NonNull String str) {
        return activity2.shouldShowRequestPermissionRationale(str);
    }

    public static void startActivityForResult(@NonNull Activity activity2, @NonNull Intent intent, int i, @Nullable Bundle bundle) {
        activity2.startActivityForResult(intent, i, bundle);
    }

    public static void startIntentSenderForResult(@NonNull Activity activity2, @NonNull IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, @Nullable Bundle bundle) throws IntentSender.SendIntentException {
        activity2.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }

    public static void startPostponedEnterTransition(@NonNull Activity activity2) {
        activity2.startPostponedEnterTransition();
    }
}
