package defpackage;

import android.support.v7.widget.Toolbar;
import android.view.View;

/* loaded from: classes.dex */
public class w8 implements View.OnClickListener {
    public final /* synthetic */ Toolbar a;

    public w8(Toolbar toolbar) {
        this.a = toolbar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        this.a.collapseActionView();
    }
}
