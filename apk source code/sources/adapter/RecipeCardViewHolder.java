package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import application.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import db.model.Recipe;
import de.silpion.mc2.R;
import helper.RecipeAssetsHelper;

/* loaded from: classes.dex */
public class RecipeCardViewHolder extends RecyclerView.ViewHolder {
    public static final String s = RecipeAssetsHelper.getImageFolderPath(App.getInstance());

    @BindView(R.id.recipe_card_level_label)
    public TextView levelLabelTextView;

    @BindView(R.id.recipe_card_minutes)
    public TextView minutesTextView;

    @BindView(R.id.recipe_card_name)
    public TextView nameTextView;

    @BindView(R.id.recipe_card_new_mark)
    public ImageView newRecipeMark;

    @BindView(R.id.recipe_card_image)
    public ImageView recipeImage;

    @BindView(R.id.recipe_card_servings)
    public TextView servingsTextView;

    public RecipeCardViewHolder(View view2) {
        super(view2);
        ButterKnife.bind(this, view2);
    }

    public void a(Recipe recipe, boolean z) {
        App.getInstance();
        if (recipe == null) {
            this.itemView.setVisibility(4);
            return;
        }
        this.itemView.setVisibility(0);
        this.nameTextView.setText(recipe.getName());
        this.minutesTextView.setText(String.valueOf(recipe.getDurationTotal()));
        this.servingsTextView.setText(String.valueOf(recipe.getYield()));
        this.levelLabelTextView.setText(recipe.getComplexity());
        this.newRecipeMark.setVisibility(z ? 0 : 8);
        String str = s;
        Picasso.get().load(RecipeAssetsHelper.getSmallRecipeImageFile(str, recipe.getSmallImagePath(str))).resize(0, 373).into(this.recipeImage);
    }
}
