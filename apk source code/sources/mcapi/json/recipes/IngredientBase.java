package mcapi.json.recipes;

import db.model.IngredientsBase;
import java.util.List;

/* loaded from: classes.dex */
public class IngredientBase {
    public List<Ingredient> ingredients;
    public String name;

    public IngredientsBase toBase(db.model.Recipe recipe) {
        IngredientsBase ingredientsBase = new IngredientsBase();
        ingredientsBase.setName(this.name);
        ingredientsBase.setRecipeId(recipe.getId());
        return ingredientsBase;
    }
}
