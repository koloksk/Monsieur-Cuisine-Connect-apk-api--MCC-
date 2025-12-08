package defpackage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import fragment.RecipeOverviewFragment;

/* loaded from: classes.dex */
public class wj extends RecyclerView.OnScrollListener {
    public final /* synthetic */ RecipeOverviewFragment a;

    public wj(RecipeOverviewFragment recipeOverviewFragment) {
        this.a = recipeOverviewFragment;
    }

    @Override // android.support.v7.widget.RecyclerView.OnScrollListener
    public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        super.onScrollStateChanged(recyclerView, i);
        if (i == 0) {
            RecipeOverviewFragment recipeOverviewFragment = this.a;
            if (recipeOverviewFragment.k) {
                return;
            }
            recipeOverviewFragment.C = null;
            int iFindFirstVisibleItemPosition = recipeOverviewFragment.D.findFirstVisibleItemPosition();
            int iFindLastVisibleItemPosition = this.a.D.findLastVisibleItemPosition() - iFindFirstVisibleItemPosition;
            int i2 = iFindLastVisibleItemPosition / 2;
            int selectedPosition = this.a.A.getSelectedPosition();
            int itemCount = ((this.a.A.getItemCount() - 1) - iFindLastVisibleItemPosition) + i2;
            String str = RecipeOverviewFragment.G;
            StringBuilder sb = new StringBuilder();
            sb.append("IDLE (Search) >> p ");
            sb.append(iFindFirstVisibleItemPosition);
            sb.append(" >> o ");
            sb.append(i2);
            sb.append(" >> p+o ");
            int i3 = iFindFirstVisibleItemPosition + i2;
            sb.append(i3);
            sb.append(" >> c ");
            sb.append(selectedPosition);
            sb.append(" >> l ");
            sb.append(itemCount);
            Log.i(str, sb.toString());
            if (i3 > i2 || (selectedPosition > i2 && i3 == i2)) {
                if (i3 < itemCount || (selectedPosition < itemCount && i3 == itemCount)) {
                    Log.i(RecipeOverviewFragment.G, "    >> to " + i3);
                    this.a.A.selectPosition(i3, false);
                }
            }
        }
    }
}
