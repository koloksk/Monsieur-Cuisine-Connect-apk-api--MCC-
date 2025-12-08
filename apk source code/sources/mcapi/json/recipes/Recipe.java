package mcapi.json.recipes;

import android.util.Log;
import defpackage.g9;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class Recipe {
    public String complexity;
    public int duration;
    public int durationTotal;
    public GuidedCooking guidedCooking;
    public Long id;
    public String imageBase;
    public String imageName;
    public List<IngredientBase> ingredientsBases;
    public List<String> instructions;
    public String language;
    public int level;
    public String machineType;
    public float machineVersion;
    public String name;
    public List<Nutrient> nutrients;
    public List<String> preparations;
    public String recipeType = "";
    public Boolean remove;
    public int schemeVersion;
    public List<Tag> tags;
    public String unit;
    public Date updated;
    public Date valid_from;
    public Date valid_to;
    public int version;
    public int yield;
    public String yieldUnit;

    public String toString() {
        return String.format(Locale.getDefault(), "[%d] \"%s\"", this.id, this.name);
    }

    public db.model.Recipe updateRecipe(db.model.Recipe recipe) {
        if (recipe == null) {
            recipe = new db.model.Recipe();
            recipe.setId(this.id);
        } else if (!this.id.equals(recipe.getId())) {
            StringBuilder sbA = g9.a("Will not apply changes to wrong recipe (my id:");
            sbA.append(this.id);
            sbA.append(", other id: ");
            sbA.append(recipe.getId());
            sbA.append(").");
            Log.w("Recipe", sbA.toString());
            return null;
        }
        recipe.setVersion(this.version);
        recipe.setMachineType(this.machineType);
        recipe.setMachineVersion(this.machineVersion);
        recipe.setValidFrom(this.valid_from);
        recipe.setValidTo(this.valid_to);
        recipe.setUpdated(this.updated);
        recipe.setName(this.name);
        recipe.setSchemeVersion(this.schemeVersion);
        recipe.setLanguage(this.language);
        recipe.setPreparations(this.preparations);
        recipe.setInstructions(this.instructions);
        recipe.setImageBase(this.imageBase);
        recipe.setImageName(this.imageName);
        recipe.setComplexity(this.complexity);
        recipe.setLevel(this.level);
        recipe.setDuration(this.duration);
        recipe.setDurationTotal(this.durationTotal);
        recipe.setUnit(this.unit);
        recipe.setYield(this.yield);
        recipe.setYieldUnit(this.yieldUnit);
        recipe.setRecipeType(this.recipeType);
        return recipe;
    }
}
