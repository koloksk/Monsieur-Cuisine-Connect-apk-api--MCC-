package mcapi.json.usages;

/* loaded from: classes.dex */
public class RecipeStepCookingEventPayload extends RecipeCookingEventPayload {
    public int step;

    public RecipeStepCookingEventPayload(long j, int i) {
        super(j);
        this.step = i;
    }
}
