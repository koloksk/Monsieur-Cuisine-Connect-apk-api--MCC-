package model;

import android.view.View;
import helper.LayoutHelper;
import java.util.List;

/* loaded from: classes.dex */
public class HelpContentTutorial extends HelpContentItem {
    public List<Integer> d;

    public HelpContentTutorial(String str, Integer num, List<Integer> list) {
        super(str, num);
        this.d = list;
        this.onClickListener = new View.OnClickListener() { // from class: sm
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(view2);
            }
        };
    }

    public /* synthetic */ void a(View view2) {
        LayoutHelper.getInstance().openTutorialFragment(this.d);
    }

    public List<Integer> getTutorialImages() {
        return this.d;
    }
}
