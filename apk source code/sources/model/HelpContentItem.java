package model;

import android.view.View;

/* loaded from: classes.dex */
public abstract class HelpContentItem {
    public String a;
    public int b;
    public boolean c = false;
    public View.OnClickListener onClickListener;

    public HelpContentItem(String str, Integer num) {
        this.a = str;
        this.b = num.intValue();
    }

    public Integer getBackground() {
        return Integer.valueOf(this.b);
    }

    public View.OnClickListener getOnClickListener() {
        return this.onClickListener;
    }

    public String getTitle() {
        return this.a;
    }

    public boolean isSelected() {
        return this.c;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setSelected(boolean z) {
        this.c = z;
    }
}
