package defpackage;

import helper.ActionListener;
import view.SurveyDialogView;

/* loaded from: classes.dex */
public class kr extends SurveyDialogView.LinkHandler {
    public final /* synthetic */ SurveyDialogView a;

    public kr(SurveyDialogView surveyDialogView) {
        this.a = surveyDialogView;
    }

    @Override // view.SurveyDialogView.LinkHandler
    public void onLinkClicked(String str) {
        ActionListener<String> actionListener = this.a.a;
        if (actionListener != null) {
            actionListener.onAction(str);
        }
    }
}
