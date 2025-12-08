package view.knob;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import de.silpion.mc2.R;
import view.QuicksandTextView;
import view.knob.Knob;

/* loaded from: classes.dex */
public class KnobSpeed extends Knob {
    public final Drawable e0;
    public final Drawable f0;

    public KnobSpeed(Context context) {
        this(context, null, 0);
    }

    public void hideCustomInfo() {
        View view2 = (View) getParent();
        view2.findViewById(R.id.knob_mode_iv).setVisibility(0);
        ((QuicksandTextView) view2.findViewById(R.id.top_layout).findViewById(R.id.knob_custom_info_tv)).setVisibility(8);
    }

    public void init() {
        setDrawActiveIndicator(true);
        setMaxValue(10);
    }

    public void initTurbo() {
        setModeImageToSelected();
        setBackgroundVisibility(true);
        setProgress(getMaxProgress());
        valueSetVisibility(false);
        setThumbEnabled(false);
        setMoveEnabled(false);
        setText("Turbo");
    }

    public void setPreheatMode(Knob.SkipPreheatStepListener skipPreheatStepListener) {
        View view2 = (View) getParent();
        view2.findViewById(R.id.top_layout).setVisibility(8);
        view2.findViewById(R.id.knob_mode_iv).setVisibility(8);
        view2.findViewById(R.id.top_layout_preheat).setVisibility(0);
        view2.findViewById(R.id.preheat_skip_btn).setVisibility(0);
        view2.findViewById(R.id.knob_top_container_rl).setTranslationY(-20.0f);
        setSkipCookingStepListener(skipPreheatStepListener);
    }

    public void setReverse(boolean z) {
        if (this.L.isEnabled() != z) {
            return;
        }
        this.L.setEnabled(!z);
        updateModeIndicatorImage();
    }

    public void showCustomInfo(String str) {
        View view2 = (View) getParent();
        view2.findViewById(R.id.knob_mode_iv).setVisibility(8);
        QuicksandTextView quicksandTextView = (QuicksandTextView) view2.findViewById(R.id.top_layout).findViewById(R.id.knob_custom_info_tv);
        quicksandTextView.setText(str);
        quicksandTextView.setVisibility(0);
    }

    public void unsetPreheatMode() {
        View view2 = (View) getParent();
        view2.findViewById(R.id.top_layout).setVisibility(0);
        view2.findViewById(R.id.knob_mode_iv).setVisibility(0);
        view2.findViewById(R.id.top_layout_preheat).setVisibility(8);
        view2.findViewById(R.id.preheat_skip_btn).setVisibility(8);
        view2.findViewById(R.id.knob_top_container_rl).setTranslationY(0.0f);
        setSkipCookingStepListener(null);
    }

    @Override // view.knob.Knob
    public void updateModeIndicatorImage() {
        if (this.L.isEnabled()) {
            super.updateModeIndicatorImage();
        } else if (this.L.isActivated()) {
            this.L.setImageDrawable(this.f0);
        } else {
            this.L.setImageDrawable(this.e0);
        }
    }

    public KnobSpeed(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KnobSpeed(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.KnobSpeed, i, 0);
            this.e0 = typedArrayObtainStyledAttributes.getDrawable(0);
            this.f0 = typedArrayObtainStyledAttributes.getDrawable(1);
            typedArrayObtainStyledAttributes.recycle();
            return;
        }
        this.e0 = null;
        this.f0 = null;
    }
}
