package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public final class PopupWindowCompat {
    public static final d a = new c();

    @RequiresApi(19)
    public static class a extends d {
        @Override // android.support.v4.widget.PopupWindowCompat.d
        public void a(PopupWindow popupWindow, View view2, int i, int i2, int i3) {
            popupWindow.showAsDropDown(view2, i, i2, i3);
        }
    }

    @RequiresApi(21)
    public static class b extends a {
        public static Field a;

        static {
            try {
                Field declaredField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                a = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", e);
            }
        }
    }

    @RequiresApi(23)
    public static class c extends b {
        @Override // android.support.v4.widget.PopupWindowCompat.d
        public void a(PopupWindow popupWindow, boolean z) {
            popupWindow.setOverlapAnchor(z);
        }

        @Override // android.support.v4.widget.PopupWindowCompat.d
        public int b(PopupWindow popupWindow) {
            return popupWindow.getWindowLayoutType();
        }

        @Override // android.support.v4.widget.PopupWindowCompat.d
        public boolean a(PopupWindow popupWindow) {
            return popupWindow.getOverlapAnchor();
        }

        @Override // android.support.v4.widget.PopupWindowCompat.d
        public void a(PopupWindow popupWindow, int i) {
            popupWindow.setWindowLayoutType(i);
        }
    }

    public static class d {
        public void a(PopupWindow popupWindow, int i) {
            throw null;
        }

        public void a(PopupWindow popupWindow, View view2, int i, int i2, int i3) {
            throw null;
        }

        public void a(PopupWindow popupWindow, boolean z) {
            throw null;
        }

        public boolean a(PopupWindow popupWindow) {
            throw null;
        }

        public int b(PopupWindow popupWindow) {
            throw null;
        }
    }

    public static boolean getOverlapAnchor(@NonNull PopupWindow popupWindow) {
        return a.a(popupWindow);
    }

    public static int getWindowLayoutType(@NonNull PopupWindow popupWindow) {
        return a.b(popupWindow);
    }

    public static void setOverlapAnchor(@NonNull PopupWindow popupWindow, boolean z) {
        a.a(popupWindow, z);
    }

    public static void setWindowLayoutType(@NonNull PopupWindow popupWindow, int i) {
        a.a(popupWindow, i);
    }

    public static void showAsDropDown(@NonNull PopupWindow popupWindow, @NonNull View view2, int i, int i2, int i3) {
        a.a(popupWindow, view2, i, i2, i3);
    }
}
