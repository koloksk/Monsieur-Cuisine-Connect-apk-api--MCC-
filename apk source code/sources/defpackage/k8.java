package defpackage;

import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;

/* loaded from: classes.dex */
public class k8 implements AdapterView.OnItemSelectedListener {
    public final /* synthetic */ ListPopupWindow a;

    public k8(ListPopupWindow listPopupWindow) {
        this.a = listPopupWindow;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view2, int i, long j) {
        f8 f8Var;
        if (i == -1 || (f8Var = this.a.c) == null) {
            return;
        }
        f8Var.setListSelectionHidden(false);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
