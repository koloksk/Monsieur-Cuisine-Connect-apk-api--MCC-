package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.view.accessibility.AccessibilityWindowInfo;

/* loaded from: classes.dex */
public class AccessibilityWindowInfoCompat {
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    public Object a;

    public AccessibilityWindowInfoCompat(Object obj) {
        this.a = obj;
    }

    public static AccessibilityWindowInfoCompat a(Object obj) {
        if (obj != null) {
            return new AccessibilityWindowInfoCompat(obj);
        }
        return null;
    }

    public static AccessibilityWindowInfoCompat obtain() {
        return a(AccessibilityWindowInfo.obtain());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AccessibilityWindowInfoCompat.class != obj.getClass()) {
            return false;
        }
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat) obj;
        Object obj2 = this.a;
        if (obj2 == null) {
            if (accessibilityWindowInfoCompat.a != null) {
                return false;
            }
        } else if (!obj2.equals(accessibilityWindowInfoCompat.a)) {
            return false;
        }
        return true;
    }

    public AccessibilityNodeInfoCompat getAnchor() {
        if (Build.VERSION.SDK_INT >= 24) {
            return AccessibilityNodeInfoCompat.a(((AccessibilityWindowInfo) this.a).getAnchor());
        }
        return null;
    }

    public void getBoundsInScreen(Rect rect) {
        ((AccessibilityWindowInfo) this.a).getBoundsInScreen(rect);
    }

    public AccessibilityWindowInfoCompat getChild(int i) {
        return a(((AccessibilityWindowInfo) this.a).getChild(i));
    }

    public int getChildCount() {
        return ((AccessibilityWindowInfo) this.a).getChildCount();
    }

    public int getId() {
        return ((AccessibilityWindowInfo) this.a).getId();
    }

    public int getLayer() {
        return ((AccessibilityWindowInfo) this.a).getLayer();
    }

    public AccessibilityWindowInfoCompat getParent() {
        return a(((AccessibilityWindowInfo) this.a).getParent());
    }

    public AccessibilityNodeInfoCompat getRoot() {
        return AccessibilityNodeInfoCompat.a(((AccessibilityWindowInfo) this.a).getRoot());
    }

    public CharSequence getTitle() {
        if (Build.VERSION.SDK_INT >= 24) {
            return ((AccessibilityWindowInfo) this.a).getTitle();
        }
        return null;
    }

    public int getType() {
        return ((AccessibilityWindowInfo) this.a).getType();
    }

    public int hashCode() {
        Object obj = this.a;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public boolean isAccessibilityFocused() {
        return ((AccessibilityWindowInfo) this.a).isAccessibilityFocused();
    }

    public boolean isActive() {
        return ((AccessibilityWindowInfo) this.a).isActive();
    }

    public boolean isFocused() {
        return ((AccessibilityWindowInfo) this.a).isFocused();
    }

    public void recycle() {
        ((AccessibilityWindowInfo) this.a).recycle();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Rect rect = new Rect();
        getBoundsInScreen(rect);
        sb.append("AccessibilityWindowInfo[");
        sb.append("id=");
        sb.append(getId());
        sb.append(", type=");
        int type = getType();
        sb.append(type != 1 ? type != 2 ? type != 3 ? type != 4 ? "<UNKNOWN>" : "TYPE_ACCESSIBILITY_OVERLAY" : "TYPE_SYSTEM" : "TYPE_INPUT_METHOD" : "TYPE_APPLICATION");
        sb.append(", layer=");
        sb.append(getLayer());
        sb.append(", bounds=");
        sb.append(rect);
        sb.append(", focused=");
        sb.append(isFocused());
        sb.append(", active=");
        sb.append(isActive());
        sb.append(", hasParent=");
        sb.append(getParent() != null);
        sb.append(", hasChildren=");
        sb.append(getChildCount() > 0);
        sb.append(']');
        return sb.toString();
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        if (accessibilityWindowInfoCompat == null) {
            return null;
        }
        return a(AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) accessibilityWindowInfoCompat.a));
    }
}
