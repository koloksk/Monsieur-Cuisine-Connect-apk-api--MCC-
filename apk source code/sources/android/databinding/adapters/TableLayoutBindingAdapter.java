package android.databinding.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.RestrictTo;
import android.util.SparseBooleanArray;
import android.widget.TableLayout;
import java.util.regex.Pattern;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class TableLayoutBindingAdapter {
    public static Pattern a = Pattern.compile("\\s*,\\s*");

    public static SparseBooleanArray a(CharSequence charSequence) throws NumberFormatException {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        if (charSequence == null) {
            return sparseBooleanArray;
        }
        for (String str : a.split(charSequence)) {
            try {
                int i = Integer.parseInt(str);
                if (i >= 0) {
                    sparseBooleanArray.put(i, true);
                }
            } catch (NumberFormatException unused) {
            }
        }
        return sparseBooleanArray;
    }

    @BindingAdapter({"android:collapseColumns"})
    public static void setCollapseColumns(TableLayout tableLayout, CharSequence charSequence) throws NumberFormatException {
        SparseBooleanArray sparseBooleanArrayA = a(charSequence);
        for (int i = 0; i < 20; i++) {
            boolean z = sparseBooleanArrayA.get(i, false);
            if (z != tableLayout.isColumnCollapsed(i)) {
                tableLayout.setColumnCollapsed(i, z);
            }
        }
    }

    @BindingAdapter({"android:shrinkColumns"})
    public static void setShrinkColumns(TableLayout tableLayout, CharSequence charSequence) throws NumberFormatException {
        if (charSequence != null && charSequence.length() > 0 && charSequence.charAt(0) == '*') {
            tableLayout.setShrinkAllColumns(true);
            return;
        }
        tableLayout.setShrinkAllColumns(false);
        SparseBooleanArray sparseBooleanArrayA = a(charSequence);
        int size = sparseBooleanArrayA.size();
        for (int i = 0; i < size; i++) {
            int iKeyAt = sparseBooleanArrayA.keyAt(i);
            boolean zValueAt = sparseBooleanArrayA.valueAt(i);
            if (zValueAt) {
                tableLayout.setColumnShrinkable(iKeyAt, zValueAt);
            }
        }
    }

    @BindingAdapter({"android:stretchColumns"})
    public static void setStretchColumns(TableLayout tableLayout, CharSequence charSequence) throws NumberFormatException {
        if (charSequence != null && charSequence.length() > 0 && charSequence.charAt(0) == '*') {
            tableLayout.setStretchAllColumns(true);
            return;
        }
        tableLayout.setStretchAllColumns(false);
        SparseBooleanArray sparseBooleanArrayA = a(charSequence);
        int size = sparseBooleanArrayA.size();
        for (int i = 0; i < size; i++) {
            int iKeyAt = sparseBooleanArrayA.keyAt(i);
            boolean zValueAt = sparseBooleanArrayA.valueAt(i);
            if (zValueAt) {
                tableLayout.setColumnStretchable(iKeyAt, zValueAt);
            }
        }
    }
}
