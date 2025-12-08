package android.support.v4.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewParentCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import cooking.Limits;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    public static final Rect n = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final FocusStrategy$BoundsAdapter<AccessibilityNodeInfoCompat> o = new a();
    public static final FocusStrategy$CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> p = new b();
    public final AccessibilityManager h;
    public final View i;
    public c j;
    public final Rect d = new Rect();
    public final Rect e = new Rect();
    public final Rect f = new Rect();
    public final int[] g = new int[2];
    public int k = Integer.MIN_VALUE;
    public int l = Integer.MIN_VALUE;
    public int m = Integer.MIN_VALUE;

    public static class a implements FocusStrategy$BoundsAdapter<AccessibilityNodeInfoCompat> {
        @Override // android.support.v4.widget.FocusStrategy$BoundsAdapter
        public void obtainBounds(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, Rect rect) {
            accessibilityNodeInfoCompat.getBoundsInParent(rect);
        }
    }

    public static class b implements FocusStrategy$CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> {
        @Override // android.support.v4.widget.FocusStrategy$CollectionAdapter
        public AccessibilityNodeInfoCompat get(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat, int i) {
            return sparseArrayCompat.valueAt(i);
        }

        @Override // android.support.v4.widget.FocusStrategy$CollectionAdapter
        public int size(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat) {
            return sparseArrayCompat.size();
        }
    }

    public class c extends AccessibilityNodeProviderCompat {
        public c() {
        }

        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.c(i));
        }

        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
        public AccessibilityNodeInfoCompat findFocus(int i) {
            int i2 = i == 2 ? ExploreByTouchHelper.this.k : ExploreByTouchHelper.this.l;
            if (i2 == Integer.MIN_VALUE) {
                return null;
            }
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.c(i2));
        }

        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
        public boolean performAction(int i, int i2, Bundle bundle) {
            return ExploreByTouchHelper.this.a(i, i2, bundle);
        }
    }

    public ExploreByTouchHelper(@NonNull View view2) {
        if (view2 == null) {
            throw new IllegalArgumentException("View may not be null");
        }
        this.i = view2;
        this.h = (AccessibilityManager) view2.getContext().getSystemService("accessibility");
        view2.setFocusable(true);
        if (ViewCompat.getImportantForAccessibility(view2) == 0) {
            ViewCompat.setImportantForAccessibility(view2, 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x012f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x017a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean a(int r21, @android.support.annotation.Nullable android.graphics.Rect r22) {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.ExploreByTouchHelper.a(int, android.graphics.Rect):boolean");
    }

    @NonNull
    public final AccessibilityNodeInfoCompat b(int i) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain();
        accessibilityNodeInfoCompatObtain.setEnabled(true);
        accessibilityNodeInfoCompatObtain.setFocusable(true);
        accessibilityNodeInfoCompatObtain.setClassName("android.view.View");
        accessibilityNodeInfoCompatObtain.setBoundsInParent(n);
        accessibilityNodeInfoCompatObtain.setBoundsInScreen(n);
        accessibilityNodeInfoCompatObtain.setParent(this.i);
        onPopulateNodeForVirtualView(i, accessibilityNodeInfoCompatObtain);
        if (accessibilityNodeInfoCompatObtain.getText() == null && accessibilityNodeInfoCompatObtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoCompatObtain.getBoundsInParent(this.e);
        if (this.e.equals(n)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = accessibilityNodeInfoCompatObtain.getActions();
        if ((actions & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((actions & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoCompatObtain.setPackageName(this.i.getContext().getPackageName());
        accessibilityNodeInfoCompatObtain.setSource(this.i, i);
        boolean z = false;
        if (this.k == i) {
            accessibilityNodeInfoCompatObtain.setAccessibilityFocused(true);
            accessibilityNodeInfoCompatObtain.addAction(128);
        } else {
            accessibilityNodeInfoCompatObtain.setAccessibilityFocused(false);
            accessibilityNodeInfoCompatObtain.addAction(64);
        }
        boolean z2 = this.l == i;
        if (z2) {
            accessibilityNodeInfoCompatObtain.addAction(2);
        } else if (accessibilityNodeInfoCompatObtain.isFocusable()) {
            accessibilityNodeInfoCompatObtain.addAction(1);
        }
        accessibilityNodeInfoCompatObtain.setFocused(z2);
        this.i.getLocationOnScreen(this.g);
        accessibilityNodeInfoCompatObtain.getBoundsInScreen(this.d);
        if (this.d.equals(n)) {
            accessibilityNodeInfoCompatObtain.getBoundsInParent(this.d);
            if (accessibilityNodeInfoCompatObtain.mParentVirtualDescendantId != -1) {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain2 = AccessibilityNodeInfoCompat.obtain();
                for (int i2 = accessibilityNodeInfoCompatObtain.mParentVirtualDescendantId; i2 != -1; i2 = accessibilityNodeInfoCompatObtain2.mParentVirtualDescendantId) {
                    accessibilityNodeInfoCompatObtain2.setParent(this.i, -1);
                    accessibilityNodeInfoCompatObtain2.setBoundsInParent(n);
                    onPopulateNodeForVirtualView(i2, accessibilityNodeInfoCompatObtain2);
                    accessibilityNodeInfoCompatObtain2.getBoundsInParent(this.e);
                    Rect rect = this.d;
                    Rect rect2 = this.e;
                    rect.offset(rect2.left, rect2.top);
                }
                accessibilityNodeInfoCompatObtain2.recycle();
            }
            this.d.offset(this.g[0] - this.i.getScrollX(), this.g[1] - this.i.getScrollY());
        }
        if (this.i.getLocalVisibleRect(this.f)) {
            this.f.offset(this.g[0] - this.i.getScrollX(), this.g[1] - this.i.getScrollY());
            if (this.d.intersect(this.f)) {
                accessibilityNodeInfoCompatObtain.setBoundsInScreen(this.d);
                Rect rect3 = this.d;
                if (rect3 != null && !rect3.isEmpty() && this.i.getWindowVisibility() == 0) {
                    Object parent = this.i.getParent();
                    while (true) {
                        if (parent instanceof View) {
                            View view2 = (View) parent;
                            if (view2.getAlpha() <= 0.0f || view2.getVisibility() != 0) {
                                break;
                            }
                            parent = view2.getParent();
                        } else if (parent != null) {
                            z = true;
                        }
                    }
                }
                if (z) {
                    accessibilityNodeInfoCompatObtain.setVisibleToUser(true);
                }
            }
        }
        return accessibilityNodeInfoCompatObtain;
    }

    @NonNull
    public AccessibilityNodeInfoCompat c(int i) {
        if (i != -1) {
            return b(i);
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(this.i);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.i, accessibilityNodeInfoCompatObtain);
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        if (accessibilityNodeInfoCompatObtain.getChildCount() > 0 && arrayList.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            accessibilityNodeInfoCompatObtain.addChild(this.i, ((Integer) arrayList.get(i2)).intValue());
        }
        return accessibilityNodeInfoCompatObtain;
    }

    public final boolean clearKeyboardFocusForVirtualView(int i) {
        if (this.l != i) {
            return false;
        }
        this.l = Integer.MIN_VALUE;
        onVirtualViewKeyboardFocusChanged(i, false);
        sendEventForVirtualView(i, 8);
        return true;
    }

    public final boolean dispatchHoverEvent(@NonNull MotionEvent motionEvent) {
        if (!this.h.isEnabled() || !this.h.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 7 || action == 9) {
            int virtualViewAt = getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
            int i = this.m;
            if (i != virtualViewAt) {
                this.m = virtualViewAt;
                sendEventForVirtualView(virtualViewAt, 128);
                sendEventForVirtualView(i, 256);
            }
            return virtualViewAt != Integer.MIN_VALUE;
        }
        if (action != 10 || this.k == Integer.MIN_VALUE) {
            return false;
        }
        int i2 = this.m;
        if (i2 != Integer.MIN_VALUE) {
            this.m = Integer.MIN_VALUE;
            sendEventForVirtualView(Integer.MIN_VALUE, 128);
            sendEventForVirtualView(i2, 256);
        }
        return true;
    }

    public final boolean dispatchKeyEvent(@NonNull KeyEvent keyEvent) {
        int i = 0;
        if (keyEvent.getAction() == 1) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 61) {
            if (keyEvent.hasNoModifiers()) {
                return a(2, (Rect) null);
            }
            if (keyEvent.hasModifiers(1)) {
                return a(1, (Rect) null);
            }
            return false;
        }
        int i2 = 66;
        if (keyCode != 66) {
            switch (keyCode) {
                case 19:
                case 20:
                case 21:
                case 22:
                    if (!keyEvent.hasNoModifiers()) {
                        return false;
                    }
                    if (keyCode == 19) {
                        i2 = 33;
                    } else if (keyCode == 21) {
                        i2 = 17;
                    } else if (keyCode != 22) {
                        i2 = Limits.MAX_TEMPERATURE;
                    }
                    int repeatCount = keyEvent.getRepeatCount() + 1;
                    boolean z = false;
                    while (i < repeatCount && a(i2, (Rect) null)) {
                        i++;
                        z = true;
                    }
                    return z;
                case 23:
                    break;
                default:
                    return false;
            }
        }
        if (!keyEvent.hasNoModifiers() || keyEvent.getRepeatCount() != 0) {
            return false;
        }
        int i3 = this.l;
        if (i3 != Integer.MIN_VALUE) {
            onPerformActionForVirtualView(i3, 16, null);
        }
        return true;
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.k;
    }

    @Override // android.support.v4.view.AccessibilityDelegateCompat
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view2) {
        if (this.j == null) {
            this.j = new c();
        }
        return this.j;
    }

    @Deprecated
    public int getFocusedVirtualView() {
        return getAccessibilityFocusedVirtualViewId();
    }

    public final int getKeyboardFocusedVirtualViewId() {
        return this.l;
    }

    public abstract int getVirtualViewAt(float f, float f2);

    public abstract void getVisibleVirtualViews(List<Integer> list);

    public final void invalidateRoot() {
        invalidateVirtualView(-1, 1);
    }

    public final void invalidateVirtualView(int i) {
        invalidateVirtualView(i, 0);
    }

    public final void onFocusChanged(boolean z, int i, @Nullable Rect rect) {
        int i2 = this.l;
        if (i2 != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i2);
        }
        if (z) {
            a(i, rect);
        }
    }

    @Override // android.support.v4.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view2, accessibilityEvent);
        onPopulateEventForHost(accessibilityEvent);
    }

    @Override // android.support.v4.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
        onPopulateNodeForHost(accessibilityNodeInfoCompat);
    }

    public abstract boolean onPerformActionForVirtualView(int i, int i2, @Nullable Bundle bundle);

    public void onPopulateEventForHost(@NonNull AccessibilityEvent accessibilityEvent) {
    }

    public void onPopulateEventForVirtualView(int i, @NonNull AccessibilityEvent accessibilityEvent) {
    }

    public void onPopulateNodeForHost(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    public abstract void onPopulateNodeForVirtualView(int i, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

    public void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
    }

    public final boolean requestKeyboardFocusForVirtualView(int i) {
        int i2;
        if ((!this.i.isFocused() && !this.i.requestFocus()) || (i2 = this.l) == i) {
            return false;
        }
        if (i2 != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i2);
        }
        this.l = i;
        onVirtualViewKeyboardFocusChanged(i, true);
        sendEventForVirtualView(i, 8);
        return true;
    }

    public final boolean sendEventForVirtualView(int i, int i2) {
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.h.isEnabled() || (parent = this.i.getParent()) == null) {
            return false;
        }
        return ViewParentCompat.requestSendAccessibilityEvent(parent, this.i, a(i, i2));
    }

    public final void invalidateVirtualView(int i, int i2) {
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.h.isEnabled() || (parent = this.i.getParent()) == null) {
            return;
        }
        AccessibilityEvent accessibilityEventA = a(i, 2048);
        AccessibilityEventCompat.setContentChangeTypes(accessibilityEventA, i2);
        ViewParentCompat.requestSendAccessibilityEvent(parent, this.i, accessibilityEventA);
    }

    public final AccessibilityEvent a(int i, int i2) {
        if (i != -1) {
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i2);
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompatC = c(i);
            accessibilityEventObtain.getText().add(accessibilityNodeInfoCompatC.getText());
            accessibilityEventObtain.setContentDescription(accessibilityNodeInfoCompatC.getContentDescription());
            accessibilityEventObtain.setScrollable(accessibilityNodeInfoCompatC.isScrollable());
            accessibilityEventObtain.setPassword(accessibilityNodeInfoCompatC.isPassword());
            accessibilityEventObtain.setEnabled(accessibilityNodeInfoCompatC.isEnabled());
            accessibilityEventObtain.setChecked(accessibilityNodeInfoCompatC.isChecked());
            onPopulateEventForVirtualView(i, accessibilityEventObtain);
            if (accessibilityEventObtain.getText().isEmpty() && accessibilityEventObtain.getContentDescription() == null) {
                throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
            }
            accessibilityEventObtain.setClassName(accessibilityNodeInfoCompatC.getClassName());
            AccessibilityRecordCompat.setSource(accessibilityEventObtain, this.i, i);
            accessibilityEventObtain.setPackageName(this.i.getContext().getPackageName());
            return accessibilityEventObtain;
        }
        AccessibilityEvent accessibilityEventObtain2 = AccessibilityEvent.obtain(i2);
        this.i.onInitializeAccessibilityEvent(accessibilityEventObtain2);
        return accessibilityEventObtain2;
    }

    public boolean a(int i, int i2, Bundle bundle) {
        int i3;
        if (i == -1) {
            return ViewCompat.performAccessibilityAction(this.i, i2, bundle);
        }
        boolean z = true;
        if (i2 == 1) {
            return requestKeyboardFocusForVirtualView(i);
        }
        if (i2 == 2) {
            return clearKeyboardFocusForVirtualView(i);
        }
        if (i2 != 64) {
            if (i2 != 128) {
                return onPerformActionForVirtualView(i, i2, bundle);
            }
            return a(i);
        }
        if (this.h.isEnabled() && this.h.isTouchExplorationEnabled() && (i3 = this.k) != i) {
            if (i3 != Integer.MIN_VALUE) {
                a(i3);
            }
            this.k = i;
            this.i.invalidate();
            sendEventForVirtualView(i, 32768);
        } else {
            z = false;
        }
        return z;
    }

    public final boolean a(int i) {
        if (this.k != i) {
            return false;
        }
        this.k = Integer.MIN_VALUE;
        this.i.invalidate();
        sendEventForVirtualView(i, 65536);
        return true;
    }
}
