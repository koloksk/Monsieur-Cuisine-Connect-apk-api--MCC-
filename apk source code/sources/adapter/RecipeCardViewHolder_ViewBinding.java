package adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class RecipeCardViewHolder_ViewBinding implements Unbinder {
    public RecipeCardViewHolder a;

    @UiThread
    public RecipeCardViewHolder_ViewBinding(RecipeCardViewHolder recipeCardViewHolder, View view2) {
        this.a = recipeCardViewHolder;
        recipeCardViewHolder.levelLabelTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.recipe_card_level_label, "field 'levelLabelTextView'", TextView.class);
        recipeCardViewHolder.minutesTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.recipe_card_minutes, "field 'minutesTextView'", TextView.class);
        recipeCardViewHolder.nameTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.recipe_card_name, "field 'nameTextView'", TextView.class);
        recipeCardViewHolder.newRecipeMark = (ImageView) Utils.findRequiredViewAsType(view2, R.id.recipe_card_new_mark, "field 'newRecipeMark'", ImageView.class);
        recipeCardViewHolder.recipeImage = (ImageView) Utils.findRequiredViewAsType(view2, R.id.recipe_card_image, "field 'recipeImage'", ImageView.class);
        recipeCardViewHolder.servingsTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.recipe_card_servings, "field 'servingsTextView'", TextView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        RecipeCardViewHolder recipeCardViewHolder = this.a;
        if (recipeCardViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        recipeCardViewHolder.levelLabelTextView = null;
        recipeCardViewHolder.minutesTextView = null;
        recipeCardViewHolder.nameTextView = null;
        recipeCardViewHolder.newRecipeMark = null;
        recipeCardViewHolder.recipeImage = null;
        recipeCardViewHolder.servingsTextView = null;
    }
}
