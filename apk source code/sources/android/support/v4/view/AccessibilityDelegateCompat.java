package android.support.v4.view;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeProvider;
import defpackage.b6;

/* loaded from: classes.dex */
public class AccessibilityDelegateCompat {
    public static final b b = new a();
    public static final View.AccessibilityDelegate c = new View.AccessibilityDelegate();
    public final View.AccessibilityDelegate a = new b6((a) b, this);

    @RequiresApi(16)
    public static class a extends b {
    }

    public static class b {
    }

    public boolean dispatchPopulateAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        return c.dispatchPopulateAccessibilityEvent(view2, accessibilityEvent);
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view2) {
        AccessibilityNodeProvider accessibilityNodeProvider = c.getAccessibilityNodeProvider(view2);
        if (accessibilityNodeProvider != null) {
            return new AccessibilityNodeProviderCompat(accessibilityNodeProvider);
        }
        return null;
    }

    public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        c.onInitializeAccessibilityEvent(view2, accessibilityEvent);
    }

    public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        c.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.unwrap());
    }

    public void onPopulateAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        c.onPopulateAccessibilityEvent(view2, accessibilityEvent);
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view2, AccessibilityEvent accessibilityEvent) {
        return c.onRequestSendAccessibilityEvent(viewGroup, view2, accessibilityEvent);
    }

    public boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
        return c.performAccessibilityAction(view2, i, bundle);
    }

    public void sendAccessibilityEvent(View view2, int i) {
        c.sendAccessibilityEvent(view2, i);
    }

    public void sendAccessibilityEventUnchecked(View view2, AccessibilityEvent accessibilityEvent) {
        c.sendAccessibilityEventUnchecked(view2, accessibilityEvent);
    }
}
