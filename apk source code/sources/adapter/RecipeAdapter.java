package adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import application.App;
import db.model.Recipe;
import de.silpion.mc2.R;
import helper.ActionListener;
import java.util.List;

/* loaded from: classes.dex */
public abstract class RecipeAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    public static final String f = App.getInstance().getResources().getString(R.string.new_recipes_category_name);
    public static final String g = RecipeAdapter.class.getSimpleName();
    public List<Recipe> c;
    public int d = -1;
    public ActionListener<Recipe> e;

    public RecipeAdapter(List<Recipe> list) {
        this.c = list;
    }

    public Recipe a(int i) {
        List<Recipe> list = this.c;
        if (list == null || i < 0 || i >= list.size()) {
            return null;
        }
        return this.c.get(i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.c.size();
    }

    public int getSelectedPosition() {
        return this.d;
    }

    public String getSelectedPositionBubbleText() {
        String name;
        int i = this.d;
        if (i < 0 || i >= this.c.size() || (name = this.c.get(i).getName()) == null || name.length() < 1) {
            return null;
        }
        return name.substring(0, 1);
    }

    public Recipe getSelectedRecipe() {
        int i = this.d;
        if (i < 0 || i >= this.c.size()) {
            return null;
        }
        return this.c.get(this.d);
    }

    public abstract void selectPosition(int i);

    public synchronized void setItems(List<Recipe> list) {
        Log.i(g, "setItems");
        int i = this.d;
        int iMax = -1;
        this.d = -1;
        this.c.clear();
        if (list != null) {
            this.c.addAll(list);
        }
        notifyDataSetChanged();
        if (this.c.size() != 0) {
            iMax = Math.max(0, Math.min(this.c.size() - 1, i));
        }
        selectPosition(iMax);
    }

    public void setRecipeOpenListener(ActionListener<Recipe> actionListener) {
        this.e = actionListener;
    }
}
