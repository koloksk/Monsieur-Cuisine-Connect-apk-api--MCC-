package adapter.guidedcooking.viewholder;

import adapter.guidedcooking.view.StepViewInfo;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import db.model.Recipe;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class PreparationViewHolder extends StepViewHolder {

    @BindView(R.id.step_number_rl)
    public RelativeLayout numberContainerRelativeLayout;

    @BindView(R.id.item_step_number_tv)
    public TextView numberTextView;
    public TextView t;

    @BindView(R.id.item_step_instruction_tv)
    public TextView textView;
    public TextView u;

    public PreparationViewHolder(View view2) {
        super(view2);
        ButterKnife.bind(this, view2);
        this.t = (TextView) view2.findViewById(R.id.recipe_name_horizontal);
        this.u = (TextView) view2.findViewById(R.id.recipe_name_vertical);
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void activate() {
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void bindStep(StepViewInfo stepViewInfo, Recipe recipe, int i, boolean z) {
        if (z) {
            this.numberContainerRelativeLayout.setVisibility(0);
            this.numberTextView.setText(c(i));
            this.u.setVisibility(0);
            this.t.setVisibility(8);
            this.u.setText(recipe.getName());
        } else {
            this.numberContainerRelativeLayout.setVisibility(8);
            this.u.setVisibility(8);
            this.t.setVisibility(0);
            this.t.setText(recipe.getName());
        }
        this.textView.setText(stepViewInfo.getText());
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void deactivate() {
    }
}
