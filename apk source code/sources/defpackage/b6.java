package defpackage;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

/* loaded from: classes.dex */
public class b6 extends View.AccessibilityDelegate {
    public final /* synthetic */ AccessibilityDelegateCompat a;

    public b6(AccessibilityDelegateCompat.a aVar, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        this.a = accessibilityDelegateCompat;
    }

    @Override // android.view.View.AccessibilityDelegate
    public boolean dispatchPopulateAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        return this.a.dispatchPopulateAccessibilityEvent(view2, accessibilityEvent);
    }

    @Override // android.view.View.AccessibilityDelegate
    public AccessibilityNodeProvider getAccessibilityNodeProvider(View view2) {
        AccessibilityNodeProviderCompat accessibilityNodeProvider = this.a.getAccessibilityNodeProvider(view2);
        if (accessibilityNodeProvider != null) {
            return (AccessibilityNodeProvider) accessibilityNodeProvider.getProvider();
        }
        return null;
    }

    @Override // android.view.View.AccessibilityDelegate
    public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        this.a.onInitializeAccessibilityEvent(view2, accessibilityEvent);
    }

    @Override // android.view.View.AccessibilityDelegate
    public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
        this.a.onInitializeAccessibilityNodeInfo(view2, AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo));
    }

    @Override // android.view.View.AccessibilityDelegate
    public void onPopulateAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        this.a.onPopulateAccessibilityEvent(view2, accessibilityEvent);
    }

    @Override // android.view.View.AccessibilityDelegate
    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view2, AccessibilityEvent accessibilityEvent) {
        return this.a.onRequestSendAccessibilityEvent(viewGroup, view2, accessibilityEvent);
    }

    @Override // android.view.View.AccessibilityDelegate
    public boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
        return this.a.performAccessibilityAction(view2, i, bundle);
    }

    @Override // android.view.View.AccessibilityDelegate
    public void sendAccessibilityEvent(View view2, int i) {
        this.a.sendAccessibilityEvent(view2, i);
    }

    @Override // android.view.View.AccessibilityDelegate
    public void sendAccessibilityEventUnchecked(View view2, AccessibilityEvent accessibilityEvent) {
        this.a.sendAccessibilityEventUnchecked(view2, accessibilityEvent);
    }
}
