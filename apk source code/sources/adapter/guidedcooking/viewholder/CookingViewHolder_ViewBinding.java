package adapter.guidedcooking.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import view.QuestionDialogView;

/* loaded from: classes.dex */
public class CookingViewHolder_ViewBinding implements Unbinder {
    public CookingViewHolder a;

    @UiThread
    public CookingViewHolder_ViewBinding(CookingViewHolder cookingViewHolder, View view2) {
        this.a = cookingViewHolder;
        cookingViewHolder.controlRelativeLayout = (RelativeLayout) Utils.findOptionalViewAsType(view2, R.id.cooking_controls_container, "field 'controlRelativeLayout'", RelativeLayout.class);
        cookingViewHolder.cookingLabelTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.cooking_label_tv, "field 'cookingLabelTextView'", TextView.class);
        cookingViewHolder.knobLeftContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.knob_left, "field 'knobLeftContainer'", RelativeLayout.class);
        cookingViewHolder.knobMiddleContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.knob_middle, "field 'knobMiddleContainer'", RelativeLayout.class);
        cookingViewHolder.knobRightContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.knob_right, "field 'knobRightContainer'", RelativeLayout.class);
        cookingViewHolder.numberContainerRelativeLayout = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.step_number_rl, "field 'numberContainerRelativeLayout'", RelativeLayout.class);
        cookingViewHolder.speedWarningQuestion = (QuestionDialogView) Utils.findRequiredViewAsType(view2, R.id.speed_warning_question, "field 'speedWarningQuestion'", QuestionDialogView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        CookingViewHolder cookingViewHolder = this.a;
        if (cookingViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        cookingViewHolder.controlRelativeLayout = null;
        cookingViewHolder.cookingLabelTextView = null;
        cookingViewHolder.knobLeftContainer = null;
        cookingViewHolder.knobMiddleContainer = null;
        cookingViewHolder.knobRightContainer = null;
        cookingViewHolder.numberContainerRelativeLayout = null;
        cookingViewHolder.speedWarningQuestion = null;
    }
}
