package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/* loaded from: classes.dex */
public final class BitmapCompat {
    public static final c a = new b();

    @RequiresApi(18)
    public static class a extends c {
    }

    @RequiresApi(19)
    public static class b extends a {
    }

    public static class c {
    }

    public static int getAllocationByteCount(@NonNull Bitmap bitmap) {
        return bitmap.getAllocationByteCount();
    }

    public static boolean hasMipMap(@NonNull Bitmap bitmap) {
        return bitmap.hasMipMap();
    }

    public static void setHasMipMap(@NonNull Bitmap bitmap, boolean z) {
        bitmap.setHasMipMap(z);
    }
}
