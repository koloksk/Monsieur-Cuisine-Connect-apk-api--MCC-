package mcapi.json.recipes;

import db.model.IngredientsBase;

/* loaded from: classes.dex */
public class Ingredient {
    public String amount;
    public String name;
    public String unit;

    public db.model.Ingredient toIngredient(IngredientsBase ingredientsBase) {
        db.model.Ingredient ingredient = new db.model.Ingredient();
        ingredient.setAmount(this.amount);
        ingredient.setName(this.name);
        ingredient.setUnit(this.unit);
        ingredient.setIngredientsBaseId(ingredientsBase.getId());
        return ingredient;
    }
}
