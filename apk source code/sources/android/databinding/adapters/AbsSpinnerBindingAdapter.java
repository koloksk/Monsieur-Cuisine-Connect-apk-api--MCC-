package android.databinding.adapters;

import android.R;
import android.databinding.BindingAdapter;
import android.support.annotation.RestrictTo;
import android.widget.AbsSpinner;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import defpackage.p1;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class AbsSpinnerBindingAdapter {
    @BindingAdapter({"android:entries"})
    public static <T extends CharSequence> void setEntries(AbsSpinner absSpinner, T[] tArr) {
        if (tArr == null) {
            absSpinner.setAdapter((SpinnerAdapter) null);
            return;
        }
        SpinnerAdapter adapter2 = absSpinner.getAdapter();
        boolean z = false;
        if (adapter2 == null || adapter2.getCount() != tArr.length) {
            z = true;
            break;
        }
        for (int i = 0; i < tArr.length; i++) {
            if (!tArr[i].equals(adapter2.getItem(i))) {
                z = true;
                break;
            }
        }
        if (z) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(absSpinner.getContext(), R.layout.simple_spinner_item, tArr);
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            absSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        }
    }

    @BindingAdapter({"android:entries"})
    public static <T> void setEntries(AbsSpinner absSpinner, List<T> list) {
        if (list != null) {
            SpinnerAdapter adapter2 = absSpinner.getAdapter();
            if (adapter2 instanceof p1) {
                ((p1) adapter2).a(list);
                return;
            } else {
                absSpinner.setAdapter((SpinnerAdapter) new p1(absSpinner.getContext(), list, R.layout.simple_spinner_item, R.layout.simple_spinner_dropdown_item, 0));
                return;
            }
        }
        absSpinner.setAdapter((SpinnerAdapter) null);
    }
}
