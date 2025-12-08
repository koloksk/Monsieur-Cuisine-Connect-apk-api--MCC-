package android.support.v4.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class NestedScrollingParentHelper {
    public int a;

    public NestedScrollingParentHelper(@NonNull ViewGroup viewGroup) {
    }

    public int getNestedScrollAxes() {
        return this.a;
    }

    public void onNestedScrollAccepted(@NonNull View view2, @NonNull View view3, int i) {
        onNestedScrollAccepted(view2, view3, i, 0);
    }

    public void onStopNestedScroll(@NonNull View view2) {
        onStopNestedScroll(view2, 0);
    }

    public void onNestedScrollAccepted(@NonNull View view2, @NonNull View view3, int i, int i2) {
        this.a = i;
    }

    public void onStopNestedScroll(@NonNull View view2, int i) {
        this.a = 0;
    }
}
