package android.support.transition;

import android.view.View;

/* loaded from: classes.dex */
public abstract class VisibilityPropagation extends TransitionPropagation {
    public static final String[] a = {"android:visibilityPropagation:visibility", "android:visibilityPropagation:center"};

    public static int a(TransitionValues transitionValues, int i) {
        int[] iArr;
        if (transitionValues == null || (iArr = (int[]) transitionValues.values.get("android:visibilityPropagation:center")) == null) {
            return -1;
        }
        return iArr[i];
    }

    @Override // android.support.transition.TransitionPropagation
    public void captureValues(TransitionValues transitionValues) {
        View view2 = transitionValues.f0view;
        Integer numValueOf = (Integer) transitionValues.values.get("android:visibility:visibility");
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(view2.getVisibility());
        }
        transitionValues.values.put("android:visibilityPropagation:visibility", numValueOf);
        int[] iArr = {Math.round(view2.getTranslationX()) + i, 0};
        view2.getLocationOnScreen(iArr);
        int i = iArr[0];
        iArr[0] = (view2.getWidth() / 2) + iArr[0];
        iArr[1] = Math.round(view2.getTranslationY()) + iArr[1];
        iArr[1] = (view2.getHeight() / 2) + iArr[1];
        transitionValues.values.put("android:visibilityPropagation:center", iArr);
    }

    @Override // android.support.transition.TransitionPropagation
    public String[] getPropagationProperties() {
        return a;
    }

    public int getViewVisibility(TransitionValues transitionValues) {
        Integer num;
        if (transitionValues == null || (num = (Integer) transitionValues.values.get("android:visibilityPropagation:visibility")) == null) {
            return 8;
        }
        return num.intValue();
    }

    public int getViewX(TransitionValues transitionValues) {
        return a(transitionValues, 0);
    }

    public int getViewY(TransitionValues transitionValues) {
        return a(transitionValues, 1);
    }
}
