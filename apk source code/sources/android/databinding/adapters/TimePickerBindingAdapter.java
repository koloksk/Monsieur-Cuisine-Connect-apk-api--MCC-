package android.databinding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.annotation.RestrictTo;
import android.widget.TimePicker;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class TimePickerBindingAdapter {

    public static class a implements TimePicker.OnTimeChangedListener {
        public final /* synthetic */ TimePicker.OnTimeChangedListener a;
        public final /* synthetic */ InverseBindingListener b;
        public final /* synthetic */ InverseBindingListener c;

        public a(TimePicker.OnTimeChangedListener onTimeChangedListener, InverseBindingListener inverseBindingListener, InverseBindingListener inverseBindingListener2) {
            this.a = onTimeChangedListener;
            this.b = inverseBindingListener;
            this.c = inverseBindingListener2;
        }

        @Override // android.widget.TimePicker.OnTimeChangedListener
        public void onTimeChanged(TimePicker timePicker, int i, int i2) {
            TimePicker.OnTimeChangedListener onTimeChangedListener = this.a;
            if (onTimeChangedListener != null) {
                onTimeChangedListener.onTimeChanged(timePicker, i, i2);
            }
            InverseBindingListener inverseBindingListener = this.b;
            if (inverseBindingListener != null) {
                inverseBindingListener.onChange();
            }
            InverseBindingListener inverseBindingListener2 = this.c;
            if (inverseBindingListener2 != null) {
                inverseBindingListener2.onChange();
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:hour")
    public static int getHour(TimePicker timePicker) {
        return timePicker.getHour();
    }

    @InverseBindingAdapter(attribute = "android:minute")
    public static int getMinute(TimePicker timePicker) {
        return timePicker.getMinute();
    }

    @BindingAdapter({"android:hour"})
    public static void setHour(TimePicker timePicker, int i) {
        if (timePicker.getHour() != i) {
            timePicker.setHour(i);
        }
    }

    @BindingAdapter(requireAll = false, value = {"android:onTimeChanged", "android:hourAttrChanged", "android:minuteAttrChanged"})
    public static void setListeners(TimePicker timePicker, TimePicker.OnTimeChangedListener onTimeChangedListener, InverseBindingListener inverseBindingListener, InverseBindingListener inverseBindingListener2) {
        if (inverseBindingListener == null && inverseBindingListener2 == null) {
            timePicker.setOnTimeChangedListener(onTimeChangedListener);
        } else {
            timePicker.setOnTimeChangedListener(new a(onTimeChangedListener, inverseBindingListener, inverseBindingListener2));
        }
    }

    @BindingAdapter({"android:minute"})
    public static void setMinute(TimePicker timePicker, int i) {
        if (timePicker.getMinute() != i) {
            timePicker.setMinute(i);
        }
    }
}
