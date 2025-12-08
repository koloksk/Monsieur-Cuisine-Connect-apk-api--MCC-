package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListPopupWindow;

/* loaded from: classes.dex */
public final class ListPopupWindowCompat {
    @Deprecated
    public static View.OnTouchListener createDragToOpenListener(Object obj, View view2) {
        return createDragToOpenListener((ListPopupWindow) obj, view2);
    }

    @Nullable
    public static View.OnTouchListener createDragToOpenListener(@NonNull ListPopupWindow listPopupWindow, @NonNull View view2) {
        return listPopupWindow.createDragToOpenListener(view2);
    }
}
