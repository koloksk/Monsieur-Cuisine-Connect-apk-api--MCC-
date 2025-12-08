package defpackage;

import android.view.View;
import helper.LayoutHelper;

/* compiled from: lambda */
/* loaded from: classes.dex */
public final /* synthetic */ class tq implements View.OnClickListener {
    public static final /* synthetic */ tq a = new tq();

    private /* synthetic */ tq() {
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view2) {
        LayoutHelper.getInstance().closeVideoPlayerFragment();
    }
}
