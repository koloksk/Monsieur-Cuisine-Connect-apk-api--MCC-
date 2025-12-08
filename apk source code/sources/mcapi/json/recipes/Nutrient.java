package mcapi.json.recipes;

/* loaded from: classes.dex */
public class Nutrient {
    public String amount;
    public String type;
    public String unit;

    public db.model.Nutrient toNutrient(db.model.Recipe recipe) {
        db.model.Nutrient nutrient = new db.model.Nutrient();
        nutrient.setAmount(this.amount);
        nutrient.setType(this.type);
        nutrient.setUnit(this.unit);
        nutrient.setRecipeId(recipe.getId());
        return nutrient;
    }
}
