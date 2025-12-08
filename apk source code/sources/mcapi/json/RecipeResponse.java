package mcapi.json;

import defpackage.g9;
import mcapi.json.recipes.Recipe;

/* loaded from: classes.dex */
public class RecipeResponse {
    public Recipe data;

    public String toString() {
        StringBuilder sbA = g9.a("API Recipe ");
        sbA.append(this.data);
        return sbA.toString();
    }
}
