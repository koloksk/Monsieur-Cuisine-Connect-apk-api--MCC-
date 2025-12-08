package adapter;

import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import db.model.Recipe;
import de.silpion.mc2.R;
import helper.ActionListener;
import java.lang.ref.WeakReference;
import java.util.List;
import sound.SoundLength;
import view.RecyclerItemClickListener;

/* loaded from: classes.dex */
public class RecipeListAdapter extends RecipeAdapter<RecipeListViewHolder> {
    public final Handler h;
    public final RecipeCardViewHolder i;
    public WeakReference<RecyclerView> j;
    public final RecyclerItemClickListener k;

    public class RecipeListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_list_item)
        public ViewGroup background;

        @BindView(R.id.recipe_list_item_name)
        public TextView nameTextView;

        public RecipeListViewHolder(RecipeListAdapter recipeListAdapter, View view2) {
            super(view2);
            ButterKnife.bind(this, view2);
        }
    }

    public class RecipeListViewHolder_ViewBinding implements Unbinder {
        public RecipeListViewHolder a;

        @UiThread
        public RecipeListViewHolder_ViewBinding(RecipeListViewHolder recipeListViewHolder, View view2) {
            this.a = recipeListViewHolder;
            recipeListViewHolder.background = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.recipe_list_item, "field 'background'", ViewGroup.class);
            recipeListViewHolder.nameTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.recipe_list_item_name, "field 'nameTextView'", TextView.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            RecipeListViewHolder recipeListViewHolder = this.a;
            if (recipeListViewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.a = null;
            recipeListViewHolder.background = null;
            recipeListViewHolder.nameTextView = null;
        }
    }

    public class a implements RecyclerItemClickListener.OnItemClickListener {
        public a() {
        }

        @Override // view.RecyclerItemClickListener.OnItemClickListener
        public void onItemClick(View view2, int i) {
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            RecipeListAdapter.this.selectPosition(i);
        }

        @Override // view.RecyclerItemClickListener.OnItemClickListener
        public void onItemLongPress(View view2, int i) {
            ActionListener<Recipe> actionListener;
            RecipeListAdapter recipeListAdapter = RecipeListAdapter.this;
            Recipe recipeA = recipeListAdapter.a(i);
            if (recipeA == null || (actionListener = recipeListAdapter.e) == null) {
                return;
            }
            actionListener.onAction(recipeA);
        }
    }

    public RecipeListAdapter(List<Recipe> list, RecipeCardViewHolder recipeCardViewHolder) {
        super(list);
        this.h = new Handler();
        this.k = new RecyclerItemClickListener(new a());
        this.i = recipeCardViewHolder;
    }

    public /* synthetic */ void a() {
        WeakReference<RecyclerView> weakReference = this.j;
        final RecyclerView recyclerView = weakReference != null ? weakReference.get() : null;
        if (recyclerView != null) {
            recyclerView.post(new Runnable() { // from class: t0
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.a(recyclerView);
                }
            });
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.j = new WeakReference<>(recyclerView);
        recyclerView.addOnItemTouchListener(this.k);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.j = null;
        recyclerView.removeOnItemTouchListener(this.k);
    }

    @Override // adapter.RecipeAdapter
    public void selectPosition(int i) {
        selectPosition(i, true);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecipeListViewHolder recipeListViewHolder, int i) {
        Recipe recipe = this.c.get(i);
        recipeListViewHolder.nameTextView.setText(recipe.getName());
        if (recipe.isSelected()) {
            recipeListViewHolder.background.setSelected(true);
        } else {
            recipeListViewHolder.background.setSelected(false);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecipeListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RecipeListViewHolder(this, (RelativeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe_list, viewGroup, false));
    }

    public synchronized void selectPosition(int i, boolean z) {
        if (i >= this.c.size()) {
            return;
        }
        int i2 = 0;
        for (Recipe recipe : this.c) {
            if (i2 != i && recipe.isSelected()) {
                recipe.setSelected(false);
                notifyItemChanged(i2);
            }
            i2++;
        }
        final Recipe recipe2 = i < 0 ? null : this.c.get(i);
        this.d = i;
        if (recipe2 != null && !recipe2.isSelected()) {
            recipe2.setSelected(true);
            notifyItemChanged(i);
        }
        this.h.removeCallbacksAndMessages(null);
        if (this.d > 0 && z) {
            this.h.postDelayed(new Runnable() { // from class: s0
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.a();
                }
            }, 50L);
        }
        this.h.postDelayed(new Runnable() { // from class: u0
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(recipe2);
            }
        }, 150L);
    }

    public /* synthetic */ void a(RecyclerView recyclerView) {
        recyclerView.smoothScrollToPosition(this.d);
    }

    public /* synthetic */ void a(Recipe recipe) {
        this.i.a(recipe, recipe != null && recipe.hasTag(RecipeAdapter.f));
    }
}
