package view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class CenterScrollRecyclerView extends RecyclerView {

    public static class a extends LinearSmoothScroller {
        public a(Context context) {
            super(context);
        }

        @Override // android.support.v7.widget.LinearSmoothScroller
        public int calculateDtToFit(int i, int i2, int i3, int i4, int i5) {
            return (((i4 - i3) / 2) + i3) - (((i2 - i) / 2) + i);
        }
    }

    public CenterScrollRecyclerView(Context context) {
        super(context);
    }

    @Override // android.support.v7.widget.RecyclerView
    public void smoothScrollToPosition(int i) {
        a aVar = new a(getContext());
        aVar.setTargetPosition(i);
        getLayoutManager().startSmoothScroll(aVar);
    }

    public CenterScrollRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CenterScrollRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
