package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import db.model.Ingredient;
import db.model.IngredientsBase;
import de.silpion.mc2.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class IngredientsAdapter extends RecyclerView.Adapter<c> {
    public LayoutInflater c;
    public List<Ingredient> d = new ArrayList();

    public class a extends c {
        public TextView s;
        public TextView t;

        public a(IngredientsAdapter ingredientsAdapter, View view2) {
            super(ingredientsAdapter, view2);
            this.s = (TextView) view2.findViewById(R.id.item_ingredient_header_tv);
            this.t = (TextView) view2.findViewById(R.id.item_ingredient_tv);
        }

        @Override // adapter.IngredientsAdapter.c
        public void a(Ingredient ingredient, int i) {
            this.t.setText(ingredient.getName());
            this.s.setVisibility(i == 0 ? 0 : 8);
        }
    }

    public class b extends c {
        public TextView s;
        public TextView t;
        public TextView u;

        public b(IngredientsAdapter ingredientsAdapter, View view2) {
            super(ingredientsAdapter, view2);
            this.t = (TextView) view2.findViewById(R.id.item_ingredient_name_tv);
            this.s = (TextView) view2.findViewById(R.id.item_ingredient_amount_tv);
            this.u = (TextView) view2.findViewById(R.id.item_ingredient_unit_tv);
        }

        @Override // adapter.IngredientsAdapter.c
        public void a(Ingredient ingredient, int i) {
            this.t.setText(ingredient.getName());
            this.u.setText(String.valueOf(ingredient.getUnit()));
            this.s.setText(String.valueOf(ingredient.getAmount()));
        }
    }

    public abstract class c extends RecyclerView.ViewHolder {
        public c(IngredientsAdapter ingredientsAdapter, View view2) {
            super(view2);
        }

        public abstract void a(Ingredient ingredient, int i);
    }

    public IngredientsAdapter(Context context, List<IngredientsBase> list) {
        this.c = LayoutInflater.from(context);
        for (IngredientsBase ingredientsBase : list) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientsBase.getName());
            ingredient.setIsHeader(true);
            this.d.add(ingredient);
            List<Ingredient> ingredients = ingredientsBase.getIngredients();
            if (ingredients != null) {
                this.d.addAll(ingredients);
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.d.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.d.get(i).getIsHeader() ? 0 : 1;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(c cVar, int i) {
        cVar.a(this.d.get(i), i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public c onCreateViewHolder(ViewGroup viewGroup, int i) {
        return i != 0 ? i != 1 ? new a(this, this.c.inflate(R.layout.item_ingredient_header, viewGroup, false)) : new b(this, this.c.inflate(R.layout.item_ingredient, viewGroup, false)) : new a(this, this.c.inflate(R.layout.item_ingredient_header, viewGroup, false));
    }
}
