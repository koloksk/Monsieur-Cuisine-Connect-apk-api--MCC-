package defpackage;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/* loaded from: classes.dex */
public class b9 extends c9 {
    @Override // android.support.v7.widget.helper.ItemTouchUIUtil
    public void clearView(View view2) {
        Object tag = view2.getTag(R.id.item_touch_helper_previous_elevation);
        if (tag != null && (tag instanceof Float)) {
            ViewCompat.setElevation(view2, ((Float) tag).floatValue());
        }
        view2.setTag(R.id.item_touch_helper_previous_elevation, null);
        view2.setTranslationX(0.0f);
        view2.setTranslationY(0.0f);
    }

    @Override // android.support.v7.widget.helper.ItemTouchUIUtil
    public void onDraw(Canvas canvas, RecyclerView recyclerView, View view2, float f, float f2, int i, boolean z) {
        if (z && view2.getTag(R.id.item_touch_helper_previous_elevation) == null) {
            Float fValueOf = Float.valueOf(ViewCompat.getElevation(view2));
            int childCount = recyclerView.getChildCount();
            float f3 = 0.0f;
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = recyclerView.getChildAt(i2);
                if (childAt != view2) {
                    float elevation = ViewCompat.getElevation(childAt);
                    if (elevation > f3) {
                        f3 = elevation;
                    }
                }
            }
            ViewCompat.setElevation(view2, f3 + 1.0f);
            view2.setTag(R.id.item_touch_helper_previous_elevation, fValueOf);
        }
        view2.setTranslationX(f);
        view2.setTranslationY(f2);
    }
}
