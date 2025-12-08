package fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import view.RecipeInfoRelativeLayout;

/* loaded from: classes.dex */
public class RecipeOverviewFragment_ViewBinding implements Unbinder {
    public RecipeOverviewFragment a;

    @UiThread
    public RecipeOverviewFragment_ViewBinding(RecipeOverviewFragment recipeOverviewFragment, View view2) {
        this.a = recipeOverviewFragment;
        recipeOverviewFragment.alphabetImageButton = (ImageButton) Utils.findRequiredViewAsType(view2, R.id.recipes_order_by_name, "field 'alphabetImageButton'", ImageButton.class);
        recipeOverviewFragment.alphabetRecyclerView = (RecyclerView) Utils.findRequiredViewAsType(view2, R.id.recipe_bottom_scroller, "field 'alphabetRecyclerView'", RecyclerView.class);
        recipeOverviewFragment.categoriesImageButton = (ImageButton) Utils.findRequiredViewAsType(view2, R.id.recipes_order_by_category, "field 'categoriesImageButton'", ImageButton.class);
        recipeOverviewFragment.favoriteImageButton = (ImageButton) Utils.findRequiredViewAsType(view2, R.id.recipes_show_favorites, "field 'favoriteImageButton'", ImageButton.class);
        recipeOverviewFragment.recipeInfo = (RecipeInfoRelativeLayout) Utils.findRequiredViewAsType(view2, R.id.recipe_info_layout, "field 'recipeInfo'", RecipeInfoRelativeLayout.class);
        recipeOverviewFragment.recipeVerticalCard = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.recipe_list_card, "field 'recipeVerticalCard'", ViewGroup.class);
        recipeOverviewFragment.recipesCardView = (RecyclerView) Utils.findRequiredViewAsType(view2, R.id.recipe_cards, "field 'recipesCardView'", RecyclerView.class);
        recipeOverviewFragment.recipesListContainer = (LinearLayout) Utils.findRequiredViewAsType(view2, R.id.recipe_list_container, "field 'recipesListContainer'", LinearLayout.class);
        recipeOverviewFragment.recipesListView = (RecyclerView) Utils.findRequiredViewAsType(view2, R.id.recipe_list, "field 'recipesListView'", RecyclerView.class);
        recipeOverviewFragment.searchImageButton = (ImageButton) Utils.findRequiredViewAsType(view2, R.id.recipes_search, "field 'searchImageButton'", ImageButton.class);
        recipeOverviewFragment.switchOrientationImageButton = (ImageButton) Utils.findRequiredViewAsType(view2, R.id.recipes_orientation_toggle, "field 'switchOrientationImageButton'", ImageButton.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        RecipeOverviewFragment recipeOverviewFragment = this.a;
        if (recipeOverviewFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        recipeOverviewFragment.alphabetImageButton = null;
        recipeOverviewFragment.alphabetRecyclerView = null;
        recipeOverviewFragment.categoriesImageButton = null;
        recipeOverviewFragment.favoriteImageButton = null;
        recipeOverviewFragment.recipeInfo = null;
        recipeOverviewFragment.recipeVerticalCard = null;
        recipeOverviewFragment.recipesCardView = null;
        recipeOverviewFragment.recipesListContainer = null;
        recipeOverviewFragment.recipesListView = null;
        recipeOverviewFragment.searchImageButton = null;
        recipeOverviewFragment.switchOrientationImageButton = null;
    }
}
