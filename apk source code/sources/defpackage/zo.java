package defpackage;

import mcapi.ResponseListener;
import services.RecipeImportService;

/* compiled from: lambda */
/* loaded from: classes.dex */
public final /* synthetic */ class zo implements ResponseListener {
    private final /* synthetic */ RecipeImportService a;

    @Override // mcapi.ResponseListener
    public final void receivedResponse(int i, Object obj, Exception exc) {
        this.a.a(i, (Boolean) obj, exc);
    }
}
