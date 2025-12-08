package android.support.v7.widget;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/* loaded from: classes.dex */
public class RecyclerViewAccessibilityDelegate extends AccessibilityDelegateCompat {
    public final RecyclerView d;
    public final AccessibilityDelegateCompat e = new ItemDelegate(this);

    public static class ItemDelegate extends AccessibilityDelegateCompat {
        public final RecyclerViewAccessibilityDelegate d;

        public ItemDelegate(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
            this.d = recyclerViewAccessibilityDelegate;
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
            if (this.d.a() || this.d.d.getLayoutManager() == null) {
                return;
            }
            this.d.d.getLayoutManager().a(view2, accessibilityNodeInfoCompat);
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view2, i, bundle)) {
                return true;
            }
            if (this.d.a() || this.d.d.getLayoutManager() == null) {
                return false;
            }
            RecyclerView.LayoutManager layoutManager = this.d.d.getLayoutManager();
            RecyclerView recyclerView = layoutManager.b;
            return layoutManager.performAccessibilityActionForItem(recyclerView.b, recyclerView.h0, view2, i, bundle);
        }
    }

    public RecyclerViewAccessibilityDelegate(RecyclerView recyclerView) {
        this.d = recyclerView;
    }

    public boolean a() {
        return this.d.hasPendingAdapterUpdates();
    }

    public AccessibilityDelegateCompat getItemDelegate() {
        return this.e;
    }

    @Override // android.support.v4.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view2, accessibilityEvent);
        accessibilityEvent.setClassName(RecyclerView.class.getName());
        if (!(view2 instanceof RecyclerView) || a()) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) view2;
        if (recyclerView.getLayoutManager() != null) {
            recyclerView.getLayoutManager().onInitializeAccessibilityEvent(accessibilityEvent);
        }
    }

    @Override // android.support.v4.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.setClassName(RecyclerView.class.getName());
        if (a() || this.d.getLayoutManager() == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = this.d.getLayoutManager();
        RecyclerView recyclerView = layoutManager.b;
        layoutManager.onInitializeAccessibilityNodeInfo(recyclerView.b, recyclerView.h0, accessibilityNodeInfoCompat);
    }

    @Override // android.support.v4.view.AccessibilityDelegateCompat
    public boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
        if (super.performAccessibilityAction(view2, i, bundle)) {
            return true;
        }
        if (a() || this.d.getLayoutManager() == null) {
            return false;
        }
        RecyclerView.LayoutManager layoutManager = this.d.getLayoutManager();
        RecyclerView recyclerView = layoutManager.b;
        return layoutManager.performAccessibilityAction(recyclerView.b, recyclerView.h0, i, bundle);
    }
}
