package view.knob;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import cooking.CookingUtils;
import cooking.Limits;
import de.silpion.mc2.R;
import defpackage.g9;

/* loaded from: classes.dex */
public class KnobTemperature extends Knob {
    public static final String g0 = KnobTemperature.class.getSimpleName();
    public static final String h0;
    public ImageView e0;
    public KnobTemperatureListener f0;

    public interface KnobTemperatureListener {
        void onTemperatureValueSet(int i);
    }

    static {
        StringBuilder sbA = g9.a("%s() should not be called on ");
        sbA.append(g0);
        h0 = sbA.toString();
    }

    public KnobTemperature(Context context) {
        this(context, null, 0);
    }

    private void setDegreeVisibility(boolean z) {
        ImageView imageView = this.e0;
        if (imageView != null) {
            if (z) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    public int addTemperatureSteps(int i) {
        return Math.max(0, Math.min((i * getProgressFromValue(1)) + getProgress(), getMaxProgressLimit()));
    }

    public /* synthetic */ void e() {
        super.setModeImageToSelected();
        super.valueSetVisibility(true);
    }

    public void init() {
        setDegreeVisibility(true);
        setMoveEnabled(true);
        setThumbVisible(true);
        setDrawActiveIndicator(true);
    }

    @Override // view.knob.Knob
    public void initViews() {
        super.initViews();
        this.e0 = (ImageView) this.c.findViewById(R.id.knob_degree_iv);
        postDelayed(new Runnable() { // from class: mr
            @Override // java.lang.Runnable
            public final void run() {
                this.a.e();
            }
        }, 5000L);
    }

    public void setKnobTemperatureListener(@Nullable KnobTemperatureListener knobTemperatureListener) {
        if (knobTemperatureListener != null) {
            this.f0 = knobTemperatureListener;
        }
    }

    public void setMeasuredTemperature(int i) {
        setValueSetTextSizeNormal();
        super.valueSetText(String.valueOf(i));
    }

    @Override // view.knob.Knob
    public void setModeImageToIdle() {
        throw new IllegalAccessError(String.format(h0, "setModeImageToIdle"));
    }

    @Override // view.knob.Knob
    public void setModeImageToSelected() {
        throw new IllegalAccessError(String.format(h0, "setModeImageToSelected"));
    }

    @Override // view.knob.Knob
    public void setProgress(int i) {
        throw new IllegalAccessError(String.format("%s() should not be called directly. Use either setTemperatureLevel() or setMeasuredTemperature() instead.", "setProgress"));
    }

    public void setTemperatureLevel(int i) {
        super.setText(String.valueOf(CookingUtils.getTemperature(i)));
        if (getValueFromProgress() != i) {
            updateProgress(getProgressFromValue(i), true);
        }
    }

    public void setTemperatureLevelLimit(int i) {
        if (i < 0 || i > Limits.TEMPERATURE_LEVEL_MAX) {
            return;
        }
        setMaxValueLimit(i);
    }

    @Override // view.knob.Knob
    public void setText(String str) {
        throw new IllegalAccessError(String.format("%s() should not be called directly. Use either setTemperatureLevel() or setMeasuredTemperature() instead.", "setText"));
    }

    public void setTextForMeasuredTemperature(String str) {
        setValueSetTextSizeSmall();
        super.valueSetText(str);
    }

    @Override // view.knob.Knob, view.knob.BaseKnob
    public void setValueFromUser() {
        Log.v(g0, "setValueFromUser");
        super.setValueFromUser();
        int temperatureFromStep = Limits.getTemperatureFromStep(getValueFromProgress());
        KnobTemperatureListener knobTemperatureListener = this.f0;
        if (knobTemperatureListener != null) {
            knobTemperatureListener.onTemperatureValueSet(temperatureFromStep);
        }
    }

    @Override // view.knob.Knob
    public void valueSetText(String str) {
        throw new IllegalAccessError(String.format("%s() should not be called directly. Use either setTemperatureLevel() or setMeasuredTemperature() instead.", "valueSetText"));
    }

    @Override // view.knob.Knob
    public void valueSetVisibility(boolean z) {
        throw new IllegalAccessError(String.format(h0, "valueSetVisibility"));
    }

    public KnobTemperature(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KnobTemperature(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setMaxValue(Limits.TEMPERATURE_LEVEL_MAX);
        setMaxValueLimit(Limits.TEMPERATURE_LEVEL_MAX);
    }
}
