package android.support.transition;

import android.graphics.Matrix;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@RequiresApi(14)
/* loaded from: classes.dex */
public interface GhostViewImpl {

    public interface Creator {
        GhostViewImpl addGhost(View view2, ViewGroup viewGroup, Matrix matrix);

        void removeGhost(View view2);
    }

    void a(ViewGroup viewGroup, View view2);

    void setVisibility(int i);
}
