package mcapi.json.recipes;

/* loaded from: classes.dex */
public class Step {
    public Led led;
    public MachineValues machineValues;
    public Measurement measurement;
    public String mode;
    public int step;
    public String text;

    public db.model.Step toStep(db.model.Recipe recipe) {
        db.model.Step step = new db.model.Step();
        step.setMode(this.mode);
        step.setStep(this.step);
        step.setText(this.text);
        step.setRecipeId(recipe.getId());
        return step;
    }
}
