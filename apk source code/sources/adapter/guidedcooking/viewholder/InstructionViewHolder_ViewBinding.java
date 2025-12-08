package adapter.guidedcooking.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class InstructionViewHolder_ViewBinding implements Unbinder {
    public InstructionViewHolder a;

    @UiThread
    public InstructionViewHolder_ViewBinding(InstructionViewHolder instructionViewHolder, View view2) {
        this.a = instructionViewHolder;
        instructionViewHolder.numberContainerRelativeLayout = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.step_number_rl, "field 'numberContainerRelativeLayout'", RelativeLayout.class);
        instructionViewHolder.numberTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.item_step_number_tv, "field 'numberTextView'", TextView.class);
        instructionViewHolder.textView = (TextView) Utils.findRequiredViewAsType(view2, R.id.item_step_instruction_tv, "field 'textView'", TextView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        InstructionViewHolder instructionViewHolder = this.a;
        if (instructionViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        instructionViewHolder.numberContainerRelativeLayout = null;
        instructionViewHolder.numberTextView = null;
        instructionViewHolder.textView = null;
    }
}
