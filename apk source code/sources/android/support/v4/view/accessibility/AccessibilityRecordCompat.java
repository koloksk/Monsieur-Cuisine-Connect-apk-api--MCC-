package android.support.v4.view.accessibility;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;
import java.util.List;

/* loaded from: classes.dex */
public class AccessibilityRecordCompat {
    public final AccessibilityRecord a;

    @Deprecated
    public AccessibilityRecordCompat(Object obj) {
        this.a = (AccessibilityRecord) obj;
    }

    @Deprecated
    public static AccessibilityRecordCompat obtain(AccessibilityRecordCompat accessibilityRecordCompat) {
        return new AccessibilityRecordCompat(AccessibilityRecord.obtain(accessibilityRecordCompat.a));
    }

    @Deprecated
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AccessibilityRecordCompat.class != obj.getClass()) {
            return false;
        }
        AccessibilityRecordCompat accessibilityRecordCompat = (AccessibilityRecordCompat) obj;
        AccessibilityRecord accessibilityRecord = this.a;
        if (accessibilityRecord == null) {
            if (accessibilityRecordCompat.a != null) {
                return false;
            }
        } else if (!accessibilityRecord.equals(accessibilityRecordCompat.a)) {
            return false;
        }
        return true;
    }

    @Deprecated
    public int getAddedCount() {
        return this.a.getAddedCount();
    }

    @Deprecated
    public CharSequence getBeforeText() {
        return this.a.getBeforeText();
    }

    @Deprecated
    public CharSequence getClassName() {
        return this.a.getClassName();
    }

    @Deprecated
    public CharSequence getContentDescription() {
        return this.a.getContentDescription();
    }

    @Deprecated
    public int getCurrentItemIndex() {
        return this.a.getCurrentItemIndex();
    }

    @Deprecated
    public int getFromIndex() {
        return this.a.getFromIndex();
    }

    @Deprecated
    public Object getImpl() {
        return this.a;
    }

    @Deprecated
    public int getItemCount() {
        return this.a.getItemCount();
    }

    @Deprecated
    public int getMaxScrollX() {
        return getMaxScrollX(this.a);
    }

    @Deprecated
    public int getMaxScrollY() {
        return getMaxScrollY(this.a);
    }

    @Deprecated
    public Parcelable getParcelableData() {
        return this.a.getParcelableData();
    }

    @Deprecated
    public int getRemovedCount() {
        return this.a.getRemovedCount();
    }

    @Deprecated
    public int getScrollX() {
        return this.a.getScrollX();
    }

    @Deprecated
    public int getScrollY() {
        return this.a.getScrollY();
    }

    @Deprecated
    public AccessibilityNodeInfoCompat getSource() {
        return AccessibilityNodeInfoCompat.a(this.a.getSource());
    }

    @Deprecated
    public List<CharSequence> getText() {
        return this.a.getText();
    }

    @Deprecated
    public int getToIndex() {
        return this.a.getToIndex();
    }

    @Deprecated
    public int getWindowId() {
        return this.a.getWindowId();
    }

    @Deprecated
    public int hashCode() {
        AccessibilityRecord accessibilityRecord = this.a;
        if (accessibilityRecord == null) {
            return 0;
        }
        return accessibilityRecord.hashCode();
    }

    @Deprecated
    public boolean isChecked() {
        return this.a.isChecked();
    }

    @Deprecated
    public boolean isEnabled() {
        return this.a.isEnabled();
    }

    @Deprecated
    public boolean isFullScreen() {
        return this.a.isFullScreen();
    }

    @Deprecated
    public boolean isPassword() {
        return this.a.isPassword();
    }

    @Deprecated
    public boolean isScrollable() {
        return this.a.isScrollable();
    }

    @Deprecated
    public void recycle() {
        this.a.recycle();
    }

    @Deprecated
    public void setAddedCount(int i) {
        this.a.setAddedCount(i);
    }

    @Deprecated
    public void setBeforeText(CharSequence charSequence) {
        this.a.setBeforeText(charSequence);
    }

    @Deprecated
    public void setChecked(boolean z) {
        this.a.setChecked(z);
    }

    @Deprecated
    public void setClassName(CharSequence charSequence) {
        this.a.setClassName(charSequence);
    }

    @Deprecated
    public void setContentDescription(CharSequence charSequence) {
        this.a.setContentDescription(charSequence);
    }

    @Deprecated
    public void setCurrentItemIndex(int i) {
        this.a.setCurrentItemIndex(i);
    }

    @Deprecated
    public void setEnabled(boolean z) {
        this.a.setEnabled(z);
    }

    @Deprecated
    public void setFromIndex(int i) {
        this.a.setFromIndex(i);
    }

    @Deprecated
    public void setFullScreen(boolean z) {
        this.a.setFullScreen(z);
    }

    @Deprecated
    public void setItemCount(int i) {
        this.a.setItemCount(i);
    }

    @Deprecated
    public void setMaxScrollX(int i) {
        setMaxScrollX(this.a, i);
    }

    @Deprecated
    public void setMaxScrollY(int i) {
        setMaxScrollY(this.a, i);
    }

    @Deprecated
    public void setParcelableData(Parcelable parcelable) {
        this.a.setParcelableData(parcelable);
    }

    @Deprecated
    public void setPassword(boolean z) {
        this.a.setPassword(z);
    }

    @Deprecated
    public void setRemovedCount(int i) {
        this.a.setRemovedCount(i);
    }

    @Deprecated
    public void setScrollX(int i) {
        this.a.setScrollX(i);
    }

    @Deprecated
    public void setScrollY(int i) {
        this.a.setScrollY(i);
    }

    @Deprecated
    public void setScrollable(boolean z) {
        this.a.setScrollable(z);
    }

    @Deprecated
    public void setSource(View view2) {
        this.a.setSource(view2);
    }

    @Deprecated
    public void setToIndex(int i) {
        this.a.setToIndex(i);
    }

    public static int getMaxScrollX(AccessibilityRecord accessibilityRecord) {
        return accessibilityRecord.getMaxScrollX();
    }

    public static int getMaxScrollY(AccessibilityRecord accessibilityRecord) {
        return accessibilityRecord.getMaxScrollY();
    }

    @Deprecated
    public static AccessibilityRecordCompat obtain() {
        return new AccessibilityRecordCompat(AccessibilityRecord.obtain());
    }

    public static void setMaxScrollX(AccessibilityRecord accessibilityRecord, int i) {
        accessibilityRecord.setMaxScrollX(i);
    }

    public static void setMaxScrollY(AccessibilityRecord accessibilityRecord, int i) {
        accessibilityRecord.setMaxScrollY(i);
    }

    @Deprecated
    public void setSource(View view2, int i) {
        setSource(this.a, view2, i);
    }

    public static void setSource(@NonNull AccessibilityRecord accessibilityRecord, View view2, int i) {
        accessibilityRecord.setSource(view2, i);
    }
}
