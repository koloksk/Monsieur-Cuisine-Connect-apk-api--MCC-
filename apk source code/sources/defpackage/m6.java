package defpackage;

import android.support.v7.app.AlertController;
import android.view.View;
import android.widget.AdapterView;

/* loaded from: classes.dex */
public class m6 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ AlertController a;
    public final /* synthetic */ AlertController.AlertParams b;

    public m6(AlertController.AlertParams alertParams, AlertController alertController) {
        this.b = alertParams;
        this.a = alertController;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
        this.b.mOnClickListener.onClick(this.a.b, i);
        if (this.b.mIsSingleChoice) {
            return;
        }
        this.a.b.dismiss();
    }
}
