package defpackage;

import fragment.RecipeOverviewFragment;

/* compiled from: lambda */
/* loaded from: classes.dex */
public final /* synthetic */ class cg implements Runnable {
    private final /* synthetic */ RecipeOverviewFragment a;
    private final /* synthetic */ String b;

    public /* synthetic */ cg(RecipeOverviewFragment recipeOverviewFragment, String str) {
        this.a = recipeOverviewFragment;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a(this.b);
    }
}
