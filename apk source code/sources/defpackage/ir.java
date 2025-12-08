package defpackage;

import helper.ActionListener;
import view.QuestionDialogView;

/* loaded from: classes.dex */
public class ir extends QuestionDialogView.LinkHandler {
    public final /* synthetic */ QuestionDialogView a;

    public ir(QuestionDialogView questionDialogView) {
        this.a = questionDialogView;
    }

    @Override // view.QuestionDialogView.LinkHandler
    public void onLinkClicked(String str) {
        ActionListener<String> actionListener = this.a.a;
        if (actionListener != null) {
            actionListener.onAction(str);
        }
    }
}
