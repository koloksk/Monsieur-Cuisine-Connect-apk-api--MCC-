package defpackage;

import android.text.Editable;
import android.text.TextWatcher;
import fragment.RecipeOverviewFragment;

/* loaded from: classes.dex */
public class xj implements TextWatcher {
    public final /* synthetic */ RecipeOverviewFragment a;

    public xj(RecipeOverviewFragment recipeOverviewFragment) {
        this.a = recipeOverviewFragment;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (i3 > 100) {
            return;
        }
        RecipeOverviewFragment recipeOverviewFragment = this.a;
        String string = charSequence.toString();
        recipeOverviewFragment.f.removeCallbacksAndMessages(null);
        recipeOverviewFragment.f.postDelayed(new cg(recipeOverviewFragment, string), 200L);
    }
}
