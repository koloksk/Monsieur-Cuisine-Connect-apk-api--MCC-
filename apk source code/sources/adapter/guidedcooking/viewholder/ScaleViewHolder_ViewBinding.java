package adapter.guidedcooking.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import view.knob.KnobScale;

/* loaded from: classes.dex */
public class ScaleViewHolder_ViewBinding implements Unbinder {
    public ScaleViewHolder a;

    @UiThread
    public ScaleViewHolder_ViewBinding(ScaleViewHolder scaleViewHolder, View view2) {
        this.a = scaleViewHolder;
        scaleViewHolder.nameTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.item_step_name_tv, "field 'nameTextView'", TextView.class);
        scaleViewHolder.numberContainerRelativeLayout = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.step_number_rl, "field 'numberContainerRelativeLayout'", RelativeLayout.class);
        scaleViewHolder.numberTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.item_step_number_tv, "field 'numberTextView'", TextView.class);
        scaleViewHolder.scaleKnob = (KnobScale) Utils.findRequiredViewAsType(view2, R.id.knob, "field 'scaleKnob'", KnobScale.class);
        scaleViewHolder.scaleKnobContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.knob_scale_container, "field 'scaleKnobContainer'", RelativeLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ScaleViewHolder scaleViewHolder = this.a;
        if (scaleViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        scaleViewHolder.nameTextView = null;
        scaleViewHolder.numberContainerRelativeLayout = null;
        scaleViewHolder.numberTextView = null;
        scaleViewHolder.scaleKnob = null;
        scaleViewHolder.scaleKnobContainer = null;
    }
}
