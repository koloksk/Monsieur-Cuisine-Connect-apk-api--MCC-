package android.databinding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.annotation.RestrictTo;
import android.widget.SeekBar;

@InverseBindingMethods({@InverseBindingMethod(attribute = "android:progress", type = SeekBar.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class SeekBarBindingAdapter {

    public interface OnProgressChanged {
        void onProgressChanged(SeekBar seekBar, int i, boolean z);
    }

    public interface OnStartTrackingTouch {
        void onStartTrackingTouch(SeekBar seekBar);
    }

    public interface OnStopTrackingTouch {
        void onStopTrackingTouch(SeekBar seekBar);
    }

    public static class a implements SeekBar.OnSeekBarChangeListener {
        public final /* synthetic */ OnProgressChanged a;
        public final /* synthetic */ InverseBindingListener b;
        public final /* synthetic */ OnStartTrackingTouch c;
        public final /* synthetic */ OnStopTrackingTouch d;

        public a(OnProgressChanged onProgressChanged, InverseBindingListener inverseBindingListener, OnStartTrackingTouch onStartTrackingTouch, OnStopTrackingTouch onStopTrackingTouch) {
            this.a = onProgressChanged;
            this.b = inverseBindingListener;
            this.c = onStartTrackingTouch;
            this.d = onStopTrackingTouch;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            OnProgressChanged onProgressChanged = this.a;
            if (onProgressChanged != null) {
                onProgressChanged.onProgressChanged(seekBar, i, z);
            }
            InverseBindingListener inverseBindingListener = this.b;
            if (inverseBindingListener != null) {
                inverseBindingListener.onChange();
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            OnStartTrackingTouch onStartTrackingTouch = this.c;
            if (onStartTrackingTouch != null) {
                onStartTrackingTouch.onStartTrackingTouch(seekBar);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            OnStopTrackingTouch onStopTrackingTouch = this.d;
            if (onStopTrackingTouch != null) {
                onStopTrackingTouch.onStopTrackingTouch(seekBar);
            }
        }
    }

    @BindingAdapter(requireAll = false, value = {"android:onStartTrackingTouch", "android:onStopTrackingTouch", "android:onProgressChanged", "android:progressAttrChanged"})
    public static void setOnSeekBarChangeListener(SeekBar seekBar, OnStartTrackingTouch onStartTrackingTouch, OnStopTrackingTouch onStopTrackingTouch, OnProgressChanged onProgressChanged, InverseBindingListener inverseBindingListener) {
        if (onStartTrackingTouch == null && onStopTrackingTouch == null && onProgressChanged == null && inverseBindingListener == null) {
            seekBar.setOnSeekBarChangeListener(null);
        } else {
            seekBar.setOnSeekBarChangeListener(new a(onProgressChanged, inverseBindingListener, onStartTrackingTouch, onStopTrackingTouch));
        }
    }

    @BindingAdapter({"android:progress"})
    public static void setProgress(SeekBar seekBar, int i) {
        if (i != seekBar.getProgress()) {
            seekBar.setProgress(i);
        }
    }
}
