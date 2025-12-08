package view.knob;

import view.knob.Knob;
import view.knob.KnobScale;
import view.knob.KnobTemperature;
import view.knob.KnobTime;

/* loaded from: classes.dex */
public interface KnobListener extends Knob.StateListener, Knob.KnobValueListener, KnobTime.KnobTimeListener, KnobTemperature.KnobTemperatureListener, KnobScale.KnobScaleTareListener, Knob.SkipPreheatStepListener {
}
