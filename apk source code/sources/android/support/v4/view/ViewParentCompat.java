package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

/* loaded from: classes.dex */
public final class ViewParentCompat {
    public static final c a = new b();

    @RequiresApi(19)
    public static class a extends c {
    }

    @RequiresApi(21)
    public static class b extends a {
    }

    public static class c {
    }

    public static void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view2, View view3, int i) {
        viewParent.notifySubtreeAccessibilityStateChanged(view2, view3, i);
    }

    public static boolean onNestedFling(ViewParent viewParent, View view2, float f, float f2, boolean z) {
        try {
            return viewParent.onNestedFling(view2, f, f2, z);
        } catch (AbstractMethodError e) {
            Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedFling", e);
            return false;
        }
    }

    public static boolean onNestedPreFling(ViewParent viewParent, View view2, float f, float f2) {
        try {
            return viewParent.onNestedPreFling(view2, f, f2);
        } catch (AbstractMethodError e) {
            Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedPreFling", e);
            return false;
        }
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view2, int i, int i2, int[] iArr) {
        onNestedPreScroll(viewParent, view2, i, i2, iArr, 0);
    }

    public static void onNestedScroll(ViewParent viewParent, View view2, int i, int i2, int i3, int i4) {
        onNestedScroll(viewParent, view2, i, i2, i3, i4, 0);
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view2, View view3, int i) {
        onNestedScrollAccepted(viewParent, view2, view3, i, 0);
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view2, View view3, int i) {
        return onStartNestedScroll(viewParent, view2, view3, i, 0);
    }

    public static void onStopNestedScroll(ViewParent viewParent, View view2) {
        onStopNestedScroll(viewParent, view2, 0);
    }

    @Deprecated
    public static boolean requestSendAccessibilityEvent(ViewParent viewParent, View view2, AccessibilityEvent accessibilityEvent) {
        return viewParent.requestSendAccessibilityEvent(view2, accessibilityEvent);
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view2, int i, int i2, int[] iArr, int i3) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onNestedPreScroll(view2, i, i2, iArr, i3);
            return;
        }
        if (i3 == 0) {
            try {
                viewParent.onNestedPreScroll(view2, i, i2, iArr);
            } catch (AbstractMethodError e) {
                Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedPreScroll", e);
            }
        }
    }

    public static void onNestedScroll(ViewParent viewParent, View view2, int i, int i2, int i3, int i4, int i5) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onNestedScroll(view2, i, i2, i3, i4, i5);
            return;
        }
        if (i5 == 0) {
            try {
                viewParent.onNestedScroll(view2, i, i2, i3, i4);
            } catch (AbstractMethodError e) {
                Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedScroll", e);
            }
        }
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view2, View view3, int i, int i2) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onNestedScrollAccepted(view2, view3, i, i2);
            return;
        }
        if (i2 == 0) {
            try {
                viewParent.onNestedScrollAccepted(view2, view3, i);
            } catch (AbstractMethodError e) {
                Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedScrollAccepted", e);
            }
        }
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view2, View view3, int i, int i2) {
        if (viewParent instanceof NestedScrollingParent2) {
            return ((NestedScrollingParent2) viewParent).onStartNestedScroll(view2, view3, i, i2);
        }
        if (i2 != 0) {
            return false;
        }
        try {
            return viewParent.onStartNestedScroll(view2, view3, i);
        } catch (AbstractMethodError e) {
            Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onStartNestedScroll", e);
            return false;
        }
    }

    public static void onStopNestedScroll(ViewParent viewParent, View view2, int i) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onStopNestedScroll(view2, i);
            return;
        }
        if (i == 0) {
            try {
                viewParent.onStopNestedScroll(view2);
            } catch (AbstractMethodError e) {
                Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onStopNestedScroll", e);
            }
        }
    }
}
