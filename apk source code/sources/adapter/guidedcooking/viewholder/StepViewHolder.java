package adapter.guidedcooking.viewholder;

import adapter.guidedcooking.view.StepViewInfo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import db.model.Recipe;
import helper.ActionListener;
import java.util.Locale;

/* loaded from: classes.dex */
public abstract class StepViewHolder extends RecyclerView.ViewHolder {
    public ActionListener<StepViewHolder> s;

    public StepViewHolder(View view2) {
        super(view2);
    }

    public abstract void activate();

    public abstract void bindStep(StepViewInfo stepViewInfo, Recipe recipe, int i, boolean z);

    public String c(int i) {
        return String.format(Locale.getDefault(), "%02d", Integer.valueOf(i + 1));
    }

    public abstract void deactivate();

    public void n() {
        ActionListener<StepViewHolder> actionListener = this.s;
        if (actionListener != null) {
            actionListener.onAction(this);
        }
    }

    public void setStepDoneListener(ActionListener<StepViewHolder> actionListener) {
        this.s = actionListener;
    }
}
