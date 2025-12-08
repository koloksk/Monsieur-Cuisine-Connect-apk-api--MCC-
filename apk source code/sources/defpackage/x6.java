package defpackage;

import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;

/* loaded from: classes.dex */
public class x6 implements AdapterView.OnItemSelectedListener {
    public final ActionBar.OnNavigationListener a;

    public x6(ActionBar.OnNavigationListener onNavigationListener) {
        this.a = onNavigationListener;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view2, int i, long j) {
        ActionBar.OnNavigationListener onNavigationListener = this.a;
        if (onNavigationListener != null) {
            onNavigationListener.onNavigationItemSelected(i, j);
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
