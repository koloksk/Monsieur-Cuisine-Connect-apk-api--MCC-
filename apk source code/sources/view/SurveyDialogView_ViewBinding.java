package view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class SurveyDialogView_ViewBinding implements Unbinder {
    public SurveyDialogView a;

    @UiThread
    public SurveyDialogView_ViewBinding(SurveyDialogView surveyDialogView) {
        this(surveyDialogView, surveyDialogView);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        SurveyDialogView surveyDialogView = this.a;
        if (surveyDialogView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        surveyDialogView.campaignText = null;
        surveyDialogView.dialogTitle = null;
        surveyDialogView.buttonOne = null;
        surveyDialogView.buttonOneImage = null;
        surveyDialogView.buttonOneText = null;
        surveyDialogView.buttonThree = null;
        surveyDialogView.buttonThreeImage = null;
        surveyDialogView.buttonThreeText = null;
        surveyDialogView.buttonTwo = null;
        surveyDialogView.buttonTwoImage = null;
        surveyDialogView.buttonTwoText = null;
        surveyDialogView.imageView = null;
    }

    @UiThread
    public SurveyDialogView_ViewBinding(SurveyDialogView surveyDialogView, View view2) {
        this.a = surveyDialogView;
        surveyDialogView.campaignText = (QuicksandTextView) Utils.findRequiredViewAsType(view2, R.id.campaign_text, "field 'campaignText'", QuicksandTextView.class);
        surveyDialogView.dialogTitle = (QuicksandTextView) Utils.findRequiredViewAsType(view2, R.id.dialog_title, "field 'dialogTitle'", QuicksandTextView.class);
        surveyDialogView.buttonOne = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.button_one, "field 'buttonOne'", ViewGroup.class);
        surveyDialogView.buttonOneImage = (ImageView) Utils.findRequiredViewAsType(view2, R.id.button_one_image, "field 'buttonOneImage'", ImageView.class);
        surveyDialogView.buttonOneText = (TextView) Utils.findRequiredViewAsType(view2, R.id.button_one_text, "field 'buttonOneText'", TextView.class);
        surveyDialogView.buttonThree = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.button_three, "field 'buttonThree'", ViewGroup.class);
        surveyDialogView.buttonThreeImage = (ImageView) Utils.findRequiredViewAsType(view2, R.id.button_three_image, "field 'buttonThreeImage'", ImageView.class);
        surveyDialogView.buttonThreeText = (TextView) Utils.findRequiredViewAsType(view2, R.id.button_three_text, "field 'buttonThreeText'", TextView.class);
        surveyDialogView.buttonTwo = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.button_two, "field 'buttonTwo'", ViewGroup.class);
        surveyDialogView.buttonTwoImage = (ImageView) Utils.findRequiredViewAsType(view2, R.id.button_two_image, "field 'buttonTwoImage'", ImageView.class);
        surveyDialogView.buttonTwoText = (TextView) Utils.findRequiredViewAsType(view2, R.id.button_two_text, "field 'buttonTwoText'", TextView.class);
        surveyDialogView.imageView = (ImageView) Utils.findRequiredViewAsType(view2, R.id.qr_image_view, "field 'imageView'", ImageView.class);
    }
}
