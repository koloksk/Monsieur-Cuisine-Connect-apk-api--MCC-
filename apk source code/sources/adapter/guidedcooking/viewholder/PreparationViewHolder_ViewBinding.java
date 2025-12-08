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
public class PreparationViewHolder_ViewBinding implements Unbinder {
    public PreparationViewHolder a;

    @UiThread
    public PreparationViewHolder_ViewBinding(PreparationViewHolder preparationViewHolder, View view2) {
        this.a = preparationViewHolder;
        preparationViewHolder.numberContainerRelativeLayout = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.step_number_rl, "field 'numberContainerRelativeLayout'", RelativeLayout.class);
        preparationViewHolder.numberTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.item_step_number_tv, "field 'numberTextView'", TextView.class);
        preparationViewHolder.textView = (TextView) Utils.findRequiredViewAsType(view2, R.id.item_step_instruction_tv, "field 'textView'", TextView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        PreparationViewHolder preparationViewHolder = this.a;
        if (preparationViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        preparationViewHolder.numberContainerRelativeLayout = null;
        preparationViewHolder.numberTextView = null;
        preparationViewHolder.textView = null;
    }
}
