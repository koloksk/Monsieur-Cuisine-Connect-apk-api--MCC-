package adapter.guidedcooking.viewholder;

import adapter.guidedcooking.GuidedCookingStepsAdapter;
import adapter.guidedcooking.view.StepViewInfo;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import db.model.Recipe;
import de.silpion.mc2.R;
import helper.RecipeAssetsHelper;
import mcapi.McUsageApi;
import org.json.JSONException;

/* loaded from: classes.dex */
public class EndViewHolder extends StepViewHolder {

    @BindView(R.id.item_end_recipe_image)
    public ImageView backgroundImageView;

    @BindView(R.id.item_end_favorite_ib)
    public ImageButton favoriteImageButton;

    @BindView(R.id.item_end_name_tv)
    public TextView nameTextView;

    @BindView(R.id.item_step_top_container_rl)
    public RelativeLayout numberTopContainerRelativeLayout;
    public final GuidedCookingStepsAdapter t;
    public String u;
    public Long v;
    public boolean w;

    public EndViewHolder(GuidedCookingStepsAdapter guidedCookingStepsAdapter, View view2) {
        super(view2);
        this.u = RecipeAssetsHelper.getImageFolderPath(App.getInstance());
        this.t = guidedCookingStepsAdapter;
        ButterKnife.bind(this, view2);
        this.numberTopContainerRelativeLayout.setVisibility(4);
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void activate() throws JSONException {
        if (this.v == null || this.w) {
            return;
        }
        this.w = true;
        McUsageApi.getInstance().postRecipeDoneEvent(this.v.longValue());
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void bindStep(StepViewInfo stepViewInfo, Recipe recipe, int i, boolean z) {
        this.nameTextView.setText(recipe.getName());
        String str = this.u;
        Picasso.get().load(RecipeAssetsHelper.getLargeRecipeImageFile(str, recipe.getLargeImagePath(str))).fit().centerCrop().into(this.backgroundImageView);
        this.favoriteImageButton.setSelected(recipe.getIsFavorite());
        this.w = false;
        this.v = recipe.getId();
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void deactivate() {
    }
}
