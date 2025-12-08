package android.databinding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.annotation.RestrictTo;
import android.widget.RatingBar;

@InverseBindingMethods({@InverseBindingMethod(attribute = "android:rating", type = RatingBar.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class RatingBarBindingAdapter {

    public static class a implements RatingBar.OnRatingBarChangeListener {
        public final /* synthetic */ RatingBar.OnRatingBarChangeListener a;
        public final /* synthetic */ InverseBindingListener b;

        public a(RatingBar.OnRatingBarChangeListener onRatingBarChangeListener, InverseBindingListener inverseBindingListener) {
            this.a = onRatingBarChangeListener;
            this.b = inverseBindingListener;
        }

        @Override // android.widget.RatingBar.OnRatingBarChangeListener
        public void onRatingChanged(RatingBar ratingBar, float f, boolean z) {
            RatingBar.OnRatingBarChangeListener onRatingBarChangeListener = this.a;
            if (onRatingBarChangeListener != null) {
                onRatingBarChangeListener.onRatingChanged(ratingBar, f, z);
            }
            this.b.onChange();
        }
    }

    @BindingAdapter(requireAll = false, value = {"android:onRatingChanged", "android:ratingAttrChanged"})
    public static void setListeners(RatingBar ratingBar, RatingBar.OnRatingBarChangeListener onRatingBarChangeListener, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            ratingBar.setOnRatingBarChangeListener(onRatingBarChangeListener);
        } else {
            ratingBar.setOnRatingBarChangeListener(new a(onRatingBarChangeListener, inverseBindingListener));
        }
    }

    @BindingAdapter({"android:rating"})
    public static void setRating(RatingBar ratingBar, float f) {
        if (ratingBar.getRating() != f) {
            ratingBar.setRating(f);
        }
    }
}
