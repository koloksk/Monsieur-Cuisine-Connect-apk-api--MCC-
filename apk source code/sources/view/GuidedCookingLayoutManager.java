package view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/* loaded from: classes.dex */
public class GuidedCookingLayoutManager extends LinearLayoutManager {
    public boolean H;

    public GuidedCookingLayoutManager(Context context) {
        super(context);
        this.H = true;
    }

    @Override // android.support.v7.widget.LinearLayoutManager, android.support.v7.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.H && super.canScrollHorizontally();
    }

    @Override // android.support.v7.widget.LinearLayoutManager, android.support.v7.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.H && super.canScrollVertically();
    }

    @Override // android.support.v7.widget.LinearLayoutManager
    public void setOrientation(int i) {
        this.H = i == 1;
        super.setOrientation(i);
    }
}
