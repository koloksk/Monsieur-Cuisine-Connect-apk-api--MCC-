package defpackage;

import android.support.v7.app.AlertController;
import android.view.View;
import android.widget.AdapterView;

/* loaded from: classes.dex */
public class n6 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ AlertController.RecycleListView a;
    public final /* synthetic */ AlertController b;
    public final /* synthetic */ AlertController.AlertParams c;

    public n6(AlertController.AlertParams alertParams, AlertController.RecycleListView recycleListView, AlertController alertController) {
        this.c = alertParams;
        this.a = recycleListView;
        this.b = alertController;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
        boolean[] zArr = this.c.mCheckedItems;
        if (zArr != null) {
            zArr[i] = this.a.isItemChecked(i);
        }
        this.c.mOnCheckboxClickListener.onClick(this.b.b, i, this.a.isItemChecked(i));
    }
}
