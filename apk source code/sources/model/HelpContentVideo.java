package model;

import android.view.View;
import helper.LayoutHelper;

/* loaded from: classes.dex */
public class HelpContentVideo extends HelpContentItem {
    public int d;

    public HelpContentVideo(String str, int i, final int i2) {
        super(str, Integer.valueOf(i));
        this.d = i2;
        this.onClickListener = new View.OnClickListener() { // from class: tm
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LayoutHelper.getInstance().openVideoPlayerFragment(i2);
            }
        };
    }

    public int getVideoResID() {
        return this.d;
    }
}
