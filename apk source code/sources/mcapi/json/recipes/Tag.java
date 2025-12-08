package mcapi.json.recipes;

/* loaded from: classes.dex */
public class Tag {
    public String category;
    public String name;

    public db.model.Tag toTag(db.model.Recipe recipe) {
        db.model.Tag tag = new db.model.Tag();
        tag.setCategory(this.category);
        tag.setName(this.name);
        tag.setRecipeId(recipe.getId());
        return tag;
    }
}
