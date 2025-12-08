package defpackage;

import android.content.Context;
import android.support.v7.app.AlertController;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/* loaded from: classes.dex */
public class k6 extends ArrayAdapter<CharSequence> {
    public final /* synthetic */ AlertController.RecycleListView a;
    public final /* synthetic */ AlertController.AlertParams b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public k6(AlertController.AlertParams alertParams, Context context, int i, int i2, CharSequence[] charSequenceArr, AlertController.RecycleListView recycleListView) {
        super(context, i, i2, charSequenceArr);
        this.b = alertParams;
        this.a = recycleListView;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view2, ViewGroup viewGroup) {
        View view3 = super.getView(i, view2, viewGroup);
        boolean[] zArr = this.b.mCheckedItems;
        if (zArr != null && zArr[i]) {
            this.a.setItemChecked(i, true);
        }
        return view3;
    }
}
