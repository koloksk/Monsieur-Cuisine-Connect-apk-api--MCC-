package adapter.guidedcooking.viewholder;

import adapter.guidedcooking.view.StepViewInfo;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import db.model.Recipe;
import de.silpion.mc2.R;
import java.util.Locale;

/* loaded from: classes.dex */
public class WaitViewHolder extends StepViewHolder {
    public TextView t;
    public TextView u;
    public RelativeLayout v;
    public TextView w;
    public TextView x;

    public WaitViewHolder(View view2) {
        super(view2);
        this.x = (TextView) view2.findViewById(R.id.item_step_text);
        this.v = (RelativeLayout) view2.findViewById(R.id.step_number_rl);
        this.w = (TextView) view2.findViewById(R.id.item_step_number_tv);
        this.t = (TextView) view2.findViewById(R.id.recipe_name_horizontal);
        this.u = (TextView) view2.findViewById(R.id.recipe_name_vertical);
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void activate() {
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void bindStep(StepViewInfo stepViewInfo, Recipe recipe, int i, boolean z) {
        if (z) {
            this.v.setVisibility(0);
            this.w.setText(c(i));
            this.u.setVisibility(0);
            this.t.setVisibility(8);
            this.u.setText(recipe.getName());
        } else {
            this.v.setVisibility(8);
            this.u.setVisibility(8);
            this.t.setVisibility(0);
            this.t.setText(recipe.getName());
        }
        this.x.setText(String.format(Locale.getDefault(), "%s, position: %d", stepViewInfo.getMode(), Integer.valueOf(i)));
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void deactivate() {
    }
}
