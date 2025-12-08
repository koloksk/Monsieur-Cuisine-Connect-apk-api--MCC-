package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import db.model.Recipe;
import de.silpion.mc2.R;
import helper.ActionListener;
import java.util.List;
import view.RecyclerItemClickListener;

/* loaded from: classes.dex */
public class RecipeCardAdapter extends RecipeAdapter<RecipeCardViewHolder> {
    public final RecyclerItemClickListener h;

    public class a implements RecyclerItemClickListener.OnItemClickListener {
        public a() {
        }

        @Override // view.RecyclerItemClickListener.OnItemClickListener
        public void onItemClick(View view2, int i) {
            ActionListener<Recipe> actionListener;
            RecipeCardAdapter recipeCardAdapter = RecipeCardAdapter.this;
            Recipe recipeA = recipeCardAdapter.a(i);
            if (recipeA == null || (actionListener = recipeCardAdapter.e) == null) {
                return;
            }
            actionListener.onAction(recipeA);
        }

        @Override // view.RecyclerItemClickListener.OnItemClickListener
        public void onItemLongPress(View view2, int i) {
        }
    }

    public RecipeCardAdapter(List<Recipe> list) {
        super(list);
        this.h = new RecyclerItemClickListener(new a());
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(this.h);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeOnItemTouchListener(this.h);
    }

    @Override // adapter.RecipeAdapter
    public synchronized void selectPosition(int i) {
        if (i >= 0) {
            if (i < this.c.size()) {
                this.d = i;
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecipeCardViewHolder recipeCardViewHolder, int i) {
        Recipe recipe = this.c.get(i);
        recipeCardViewHolder.a(recipe, recipe != null && recipe.hasTag(RecipeAdapter.f));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecipeCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RecipeCardViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe_card, viewGroup, false));
    }
}
