package defpackage;

import helper.ActionListener;
import view.QuestionDialogView;

/* loaded from: classes.dex */
public class hr extends QuestionDialogView.LinkHandler {
    public final /* synthetic */ QuestionDialogView a;

    public hr(QuestionDialogView questionDialogView) {
        this.a = questionDialogView;
    }

    @Override // view.QuestionDialogView.LinkHandler
    public void onLinkClicked(String str) {
        QuestionDialogView questionDialogView = this.a;
        ActionListener<QuestionDialogView> actionListener = questionDialogView.f;
        if (actionListener != null) {
            actionListener.onAction(questionDialogView);
        }
    }
}
