package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    public final AccessibilityNodeInfo a;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int mParentVirtualDescendantId = -1;

    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        public final Object a;

        public CollectionInfoCompat(Object obj) {
            this.a = obj;
        }

        public static CollectionInfoCompat obtain(int i, int i2, boolean z, int i3) {
            return new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z, i3));
        }

        public int getColumnCount() {
            return ((AccessibilityNodeInfo.CollectionInfo) this.a).getColumnCount();
        }

        public int getRowCount() {
            return ((AccessibilityNodeInfo.CollectionInfo) this.a).getRowCount();
        }

        public int getSelectionMode() {
            return ((AccessibilityNodeInfo.CollectionInfo) this.a).getSelectionMode();
        }

        public boolean isHierarchical() {
            return ((AccessibilityNodeInfo.CollectionInfo) this.a).isHierarchical();
        }

        public static CollectionInfoCompat obtain(int i, int i2, boolean z) {
            return new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z));
        }
    }

    public static class CollectionItemInfoCompat {
        public final Object a;

        public CollectionItemInfoCompat(Object obj) {
            this.a = obj;
        }

        public static CollectionItemInfoCompat obtain(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z, z2));
        }

        public int getColumnIndex() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).getColumnIndex();
        }

        public int getColumnSpan() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).getColumnSpan();
        }

        public int getRowIndex() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).getRowIndex();
        }

        public int getRowSpan() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).getRowSpan();
        }

        public boolean isHeading() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).isHeading();
        }

        public boolean isSelected() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).isSelected();
        }

        public static CollectionItemInfoCompat obtain(int i, int i2, int i3, int i4, boolean z) {
            return new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z));
        }
    }

    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        public final Object a;

        public RangeInfoCompat(Object obj) {
            this.a = obj;
        }

        public static RangeInfoCompat obtain(int i, float f, float f2, float f3) {
            return new RangeInfoCompat(AccessibilityNodeInfo.RangeInfo.obtain(i, f, f2, f3));
        }

        public float getCurrent() {
            return ((AccessibilityNodeInfo.RangeInfo) this.a).getCurrent();
        }

        public float getMax() {
            return ((AccessibilityNodeInfo.RangeInfo) this.a).getMax();
        }

        public float getMin() {
            return ((AccessibilityNodeInfo.RangeInfo) this.a).getMin();
        }

        public int getType() {
            return ((AccessibilityNodeInfo.RangeInfo) this.a).getType();
        }
    }

    @Deprecated
    public AccessibilityNodeInfoCompat(Object obj) {
        this.a = (AccessibilityNodeInfo) obj;
    }

    public static AccessibilityNodeInfoCompat a(Object obj) {
        if (obj != null) {
            return new AccessibilityNodeInfoCompat(obj);
        }
        return null;
    }

    public static AccessibilityNodeInfoCompat obtain(View view2) {
        return wrap(AccessibilityNodeInfo.obtain(view2));
    }

    public static AccessibilityNodeInfoCompat wrap(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        return new AccessibilityNodeInfoCompat(accessibilityNodeInfo);
    }

    public void addAction(int i) {
        this.a.addAction(i);
    }

    public void addChild(View view2) {
        this.a.addChild(view2);
    }

    public boolean canOpenPopup() {
        return this.a.canOpenPopup();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AccessibilityNodeInfoCompat.class != obj.getClass()) {
            return false;
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) obj;
        AccessibilityNodeInfo accessibilityNodeInfo = this.a;
        if (accessibilityNodeInfo == null) {
            if (accessibilityNodeInfoCompat.a != null) {
                return false;
            }
        } else if (!accessibilityNodeInfo.equals(accessibilityNodeInfoCompat.a)) {
            return false;
        }
        return true;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String str) {
        ArrayList arrayList = new ArrayList();
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = this.a.findAccessibilityNodeInfosByText(str);
        int size = listFindAccessibilityNodeInfosByText.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(wrap(listFindAccessibilityNodeInfosByText.get(i)));
        }
        return arrayList;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String str) {
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = this.a.findAccessibilityNodeInfosByViewId(str);
        ArrayList arrayList = new ArrayList();
        Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByViewId.iterator();
        while (it.hasNext()) {
            arrayList.add(wrap(it.next()));
        }
        return arrayList;
    }

    public AccessibilityNodeInfoCompat findFocus(int i) {
        return a(this.a.findFocus(i));
    }

    public AccessibilityNodeInfoCompat focusSearch(int i) {
        return a(this.a.focusSearch(i));
    }

    public List<AccessibilityActionCompat> getActionList() {
        List<AccessibilityNodeInfo.AccessibilityAction> actionList = this.a.getActionList();
        if (actionList == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = actionList.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(new AccessibilityActionCompat(actionList.get(i)));
        }
        return arrayList;
    }

    public int getActions() {
        return this.a.getActions();
    }

    public void getBoundsInParent(Rect rect) {
        this.a.getBoundsInParent(rect);
    }

    public void getBoundsInScreen(Rect rect) {
        this.a.getBoundsInScreen(rect);
    }

    public AccessibilityNodeInfoCompat getChild(int i) {
        return a(this.a.getChild(i));
    }

    public int getChildCount() {
        return this.a.getChildCount();
    }

    public CharSequence getClassName() {
        return this.a.getClassName();
    }

    public CollectionInfoCompat getCollectionInfo() {
        AccessibilityNodeInfo.CollectionInfo collectionInfo = this.a.getCollectionInfo();
        if (collectionInfo != null) {
            return new CollectionInfoCompat(collectionInfo);
        }
        return null;
    }

    public CollectionItemInfoCompat getCollectionItemInfo() {
        AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo = this.a.getCollectionItemInfo();
        if (collectionItemInfo != null) {
            return new CollectionItemInfoCompat(collectionItemInfo);
        }
        return null;
    }

    public CharSequence getContentDescription() {
        return this.a.getContentDescription();
    }

    public int getDrawingOrder() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.a.getDrawingOrder();
        }
        return 0;
    }

    public CharSequence getError() {
        return this.a.getError();
    }

    public Bundle getExtras() {
        return this.a.getExtras();
    }

    @Deprecated
    public Object getInfo() {
        return this.a;
    }

    public int getInputType() {
        return this.a.getInputType();
    }

    public AccessibilityNodeInfoCompat getLabelFor() {
        return a(this.a.getLabelFor());
    }

    public AccessibilityNodeInfoCompat getLabeledBy() {
        return a(this.a.getLabeledBy());
    }

    public int getLiveRegion() {
        return this.a.getLiveRegion();
    }

    public int getMaxTextLength() {
        return this.a.getMaxTextLength();
    }

    public int getMovementGranularities() {
        return this.a.getMovementGranularities();
    }

    public CharSequence getPackageName() {
        return this.a.getPackageName();
    }

    public AccessibilityNodeInfoCompat getParent() {
        return a(this.a.getParent());
    }

    public RangeInfoCompat getRangeInfo() {
        AccessibilityNodeInfo.RangeInfo rangeInfo = this.a.getRangeInfo();
        if (rangeInfo != null) {
            return new RangeInfoCompat(rangeInfo);
        }
        return null;
    }

    @Nullable
    public CharSequence getRoleDescription() {
        return this.a.getExtras().getCharSequence("AccessibilityNodeInfo.roleDescription");
    }

    public CharSequence getText() {
        return this.a.getText();
    }

    public int getTextSelectionEnd() {
        return this.a.getTextSelectionEnd();
    }

    public int getTextSelectionStart() {
        return this.a.getTextSelectionStart();
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() {
        return a(this.a.getTraversalAfter());
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() {
        return a(this.a.getTraversalBefore());
    }

    public String getViewIdResourceName() {
        return this.a.getViewIdResourceName();
    }

    public AccessibilityWindowInfoCompat getWindow() {
        return AccessibilityWindowInfoCompat.a(this.a.getWindow());
    }

    public int getWindowId() {
        return this.a.getWindowId();
    }

    public int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.a;
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        return accessibilityNodeInfo.hashCode();
    }

    public boolean isAccessibilityFocused() {
        return this.a.isAccessibilityFocused();
    }

    public boolean isCheckable() {
        return this.a.isCheckable();
    }

    public boolean isChecked() {
        return this.a.isChecked();
    }

    public boolean isClickable() {
        return this.a.isClickable();
    }

    public boolean isContentInvalid() {
        return this.a.isContentInvalid();
    }

    public boolean isContextClickable() {
        return this.a.isContextClickable();
    }

    public boolean isDismissable() {
        return this.a.isDismissable();
    }

    public boolean isEditable() {
        return this.a.isEditable();
    }

    public boolean isEnabled() {
        return this.a.isEnabled();
    }

    public boolean isFocusable() {
        return this.a.isFocusable();
    }

    public boolean isFocused() {
        return this.a.isFocused();
    }

    public boolean isImportantForAccessibility() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.a.isImportantForAccessibility();
        }
        return true;
    }

    public boolean isLongClickable() {
        return this.a.isLongClickable();
    }

    public boolean isMultiLine() {
        return this.a.isMultiLine();
    }

    public boolean isPassword() {
        return this.a.isPassword();
    }

    public boolean isScrollable() {
        return this.a.isScrollable();
    }

    public boolean isSelected() {
        return this.a.isSelected();
    }

    public boolean isVisibleToUser() {
        return this.a.isVisibleToUser();
    }

    public boolean performAction(int i) {
        return this.a.performAction(i);
    }

    public void recycle() {
        this.a.recycle();
    }

    public boolean refresh() {
        return this.a.refresh();
    }

    public boolean removeAction(AccessibilityActionCompat accessibilityActionCompat) {
        return this.a.removeAction((AccessibilityNodeInfo.AccessibilityAction) accessibilityActionCompat.a);
    }

    public boolean removeChild(View view2) {
        return this.a.removeChild(view2);
    }

    public void setAccessibilityFocused(boolean z) {
        this.a.setAccessibilityFocused(z);
    }

    public void setBoundsInParent(Rect rect) {
        this.a.setBoundsInParent(rect);
    }

    public void setBoundsInScreen(Rect rect) {
        this.a.setBoundsInScreen(rect);
    }

    public void setCanOpenPopup(boolean z) {
        this.a.setCanOpenPopup(z);
    }

    public void setCheckable(boolean z) {
        this.a.setCheckable(z);
    }

    public void setChecked(boolean z) {
        this.a.setChecked(z);
    }

    public void setClassName(CharSequence charSequence) {
        this.a.setClassName(charSequence);
    }

    public void setClickable(boolean z) {
        this.a.setClickable(z);
    }

    public void setCollectionInfo(Object obj) {
        this.a.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo) ((CollectionInfoCompat) obj).a);
    }

    public void setCollectionItemInfo(Object obj) {
        this.a.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo) ((CollectionItemInfoCompat) obj).a);
    }

    public void setContentDescription(CharSequence charSequence) {
        this.a.setContentDescription(charSequence);
    }

    public void setContentInvalid(boolean z) {
        this.a.setContentInvalid(z);
    }

    public void setContextClickable(boolean z) {
        this.a.setContextClickable(z);
    }

    public void setDismissable(boolean z) {
        this.a.setDismissable(z);
    }

    public void setDrawingOrder(int i) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.a.setDrawingOrder(i);
        }
    }

    public void setEditable(boolean z) {
        this.a.setEditable(z);
    }

    public void setEnabled(boolean z) {
        this.a.setEnabled(z);
    }

    public void setError(CharSequence charSequence) {
        this.a.setError(charSequence);
    }

    public void setFocusable(boolean z) {
        this.a.setFocusable(z);
    }

    public void setFocused(boolean z) {
        this.a.setFocused(z);
    }

    public void setImportantForAccessibility(boolean z) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.a.setImportantForAccessibility(z);
        }
    }

    public void setInputType(int i) {
        this.a.setInputType(i);
    }

    public void setLabelFor(View view2) {
        this.a.setLabelFor(view2);
    }

    public void setLabeledBy(View view2) {
        this.a.setLabeledBy(view2);
    }

    public void setLiveRegion(int i) {
        this.a.setLiveRegion(i);
    }

    public void setLongClickable(boolean z) {
        this.a.setLongClickable(z);
    }

    public void setMaxTextLength(int i) {
        this.a.setMaxTextLength(i);
    }

    public void setMovementGranularities(int i) {
        this.a.setMovementGranularities(i);
    }

    public void setMultiLine(boolean z) {
        this.a.setMultiLine(z);
    }

    public void setPackageName(CharSequence charSequence) {
        this.a.setPackageName(charSequence);
    }

    public void setParent(View view2) {
        this.a.setParent(view2);
    }

    public void setPassword(boolean z) {
        this.a.setPassword(z);
    }

    public void setRangeInfo(RangeInfoCompat rangeInfoCompat) {
        this.a.setRangeInfo((AccessibilityNodeInfo.RangeInfo) rangeInfoCompat.a);
    }

    public void setRoleDescription(@Nullable CharSequence charSequence) {
        this.a.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", charSequence);
    }

    public void setScrollable(boolean z) {
        this.a.setScrollable(z);
    }

    public void setSelected(boolean z) {
        this.a.setSelected(z);
    }

    public void setSource(View view2) {
        this.a.setSource(view2);
    }

    public void setText(CharSequence charSequence) {
        this.a.setText(charSequence);
    }

    public void setTextSelection(int i, int i2) {
        this.a.setTextSelection(i, i2);
    }

    public void setTraversalAfter(View view2) {
        this.a.setTraversalAfter(view2);
    }

    public void setTraversalBefore(View view2) {
        this.a.setTraversalBefore(view2);
    }

    public void setViewIdResourceName(String str) {
        this.a.setViewIdResourceName(str);
    }

    public void setVisibleToUser(boolean z) {
        this.a.setVisibleToUser(z);
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        Rect rect = new Rect();
        getBoundsInParent(rect);
        sb.append("; boundsInParent: " + rect);
        getBoundsInScreen(rect);
        sb.append("; boundsInScreen: " + rect);
        sb.append("; packageName: ");
        sb.append(getPackageName());
        sb.append("; className: ");
        sb.append(getClassName());
        sb.append("; text: ");
        sb.append(getText());
        sb.append("; contentDescription: ");
        sb.append(getContentDescription());
        sb.append("; viewId: ");
        sb.append(getViewIdResourceName());
        sb.append("; checkable: ");
        sb.append(isCheckable());
        sb.append("; checked: ");
        sb.append(isChecked());
        sb.append("; focusable: ");
        sb.append(isFocusable());
        sb.append("; focused: ");
        sb.append(isFocused());
        sb.append("; selected: ");
        sb.append(isSelected());
        sb.append("; clickable: ");
        sb.append(isClickable());
        sb.append("; longClickable: ");
        sb.append(isLongClickable());
        sb.append("; enabled: ");
        sb.append(isEnabled());
        sb.append("; password: ");
        sb.append(isPassword());
        sb.append("; scrollable: " + isScrollable());
        sb.append("; [");
        int actions = getActions();
        while (actions != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(actions);
            actions &= ~iNumberOfTrailingZeros;
            if (iNumberOfTrailingZeros == 1) {
                str = "ACTION_FOCUS";
            } else if (iNumberOfTrailingZeros != 2) {
                switch (iNumberOfTrailingZeros) {
                    case 4:
                        str = "ACTION_SELECT";
                        break;
                    case 8:
                        str = "ACTION_CLEAR_SELECTION";
                        break;
                    case 16:
                        str = "ACTION_CLICK";
                        break;
                    case 32:
                        str = "ACTION_LONG_CLICK";
                        break;
                    case 64:
                        str = "ACTION_ACCESSIBILITY_FOCUS";
                        break;
                    case 128:
                        str = "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
                        break;
                    case 256:
                        str = "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
                        break;
                    case 512:
                        str = "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
                        break;
                    case 1024:
                        str = "ACTION_NEXT_HTML_ELEMENT";
                        break;
                    case 2048:
                        str = "ACTION_PREVIOUS_HTML_ELEMENT";
                        break;
                    case 4096:
                        str = "ACTION_SCROLL_FORWARD";
                        break;
                    case 8192:
                        str = "ACTION_SCROLL_BACKWARD";
                        break;
                    case 16384:
                        str = "ACTION_COPY";
                        break;
                    case 32768:
                        str = "ACTION_PASTE";
                        break;
                    case 65536:
                        str = "ACTION_CUT";
                        break;
                    case 131072:
                        str = "ACTION_SET_SELECTION";
                        break;
                    default:
                        str = "ACTION_UNKNOWN";
                        break;
                }
            } else {
                str = "ACTION_CLEAR_FOCUS";
            }
            sb.append(str);
            if (actions != 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public AccessibilityNodeInfo unwrap() {
        return this.a;
    }

    public static AccessibilityNodeInfoCompat obtain(View view2, int i) {
        return a(AccessibilityNodeInfo.obtain(view2, i));
    }

    public void addAction(AccessibilityActionCompat accessibilityActionCompat) {
        this.a.addAction((AccessibilityNodeInfo.AccessibilityAction) accessibilityActionCompat.a);
    }

    public void addChild(View view2, int i) {
        this.a.addChild(view2, i);
    }

    public boolean performAction(int i, Bundle bundle) {
        return this.a.performAction(i, bundle);
    }

    public boolean removeChild(View view2, int i) {
        return this.a.removeChild(view2, i);
    }

    public void setLabelFor(View view2, int i) {
        this.a.setLabelFor(view2, i);
    }

    public void setLabeledBy(View view2, int i) {
        this.a.setLabeledBy(view2, i);
    }

    public void setParent(View view2, int i) {
        this.mParentVirtualDescendantId = i;
        this.a.setParent(view2, i);
    }

    public void setSource(View view2, int i) {
        this.a.setSource(view2, i);
    }

    public void setTraversalAfter(View view2, int i) {
        this.a.setTraversalAfter(view2, i);
    }

    public void setTraversalBefore(View view2, int i) {
        this.a.setTraversalBefore(view2, i);
    }

    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS;
        public final Object a;
        public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
        public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
        public static final AccessibilityActionCompat ACTION_CLICK = new AccessibilityActionCompat(16, null);
        public static final AccessibilityActionCompat ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null);
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
        public static final AccessibilityActionCompat ACTION_COPY = new AccessibilityActionCompat(16384, null);
        public static final AccessibilityActionCompat ACTION_PASTE = new AccessibilityActionCompat(32768, null);
        public static final AccessibilityActionCompat ACTION_CUT = new AccessibilityActionCompat(65536, null);
        public static final AccessibilityActionCompat ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null);
        public static final AccessibilityActionCompat ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
        public static final AccessibilityActionCompat ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
        public static final AccessibilityActionCompat ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
        public static final AccessibilityActionCompat ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null);
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN);
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION);
        public static final AccessibilityActionCompat ACTION_SCROLL_UP = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT);
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT);
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK);

        static {
            ACTION_SET_PROGRESS = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 24 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS : null);
        }

        public AccessibilityActionCompat(int i, CharSequence charSequence) {
            this.a = new AccessibilityNodeInfo.AccessibilityAction(i, charSequence);
        }

        public int getId() {
            return ((AccessibilityNodeInfo.AccessibilityAction) this.a).getId();
        }

        public CharSequence getLabel() {
            return ((AccessibilityNodeInfo.AccessibilityAction) this.a).getLabel();
        }

        public AccessibilityActionCompat(Object obj) {
            this.a = obj;
        }
    }

    public AccessibilityNodeInfoCompat(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.a = accessibilityNodeInfo;
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return wrap(AccessibilityNodeInfo.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        return wrap(AccessibilityNodeInfo.obtain(accessibilityNodeInfoCompat.a));
    }
}
