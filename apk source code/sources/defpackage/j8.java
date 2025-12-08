package defpackage;

import android.support.v7.widget.ListPopupWindow;
import android.view.View;

/* loaded from: classes.dex */
public class j8 implements Runnable {
    public final /* synthetic */ ListPopupWindow a;

    public j8(ListPopupWindow listPopupWindow) {
        this.a = listPopupWindow;
    }

    @Override // java.lang.Runnable
    public void run() {
        View anchorView = this.a.getAnchorView();
        if (anchorView == null || anchorView.getWindowToken() == null) {
            return;
        }
        this.a.show();
    }
}
