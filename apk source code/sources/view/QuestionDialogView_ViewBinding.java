package view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class QuestionDialogView_ViewBinding implements Unbinder {
    public QuestionDialogView a;

    @UiThread
    public QuestionDialogView_ViewBinding(QuestionDialogView questionDialogView) {
        this(questionDialogView, questionDialogView);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        QuestionDialogView questionDialogView = this.a;
        if (questionDialogView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        questionDialogView.body = null;
        questionDialogView.buttonOne = null;
        questionDialogView.buttonOneImage = null;
        questionDialogView.buttonOneText = null;
        questionDialogView.buttonThree = null;
        questionDialogView.buttonThreeImage = null;
        questionDialogView.buttonThreeText = null;
        questionDialogView.buttonTwo = null;
        questionDialogView.buttonTwoImage = null;
        questionDialogView.buttonTwoText = null;
        questionDialogView.checkbox = null;
        questionDialogView.checkboxText = null;
        questionDialogView.title = null;
    }

    @UiThread
    public QuestionDialogView_ViewBinding(QuestionDialogView questionDialogView, View view2) {
        this.a = questionDialogView;
        questionDialogView.body = (TextView) Utils.findRequiredViewAsType(view2, R.id.dialog_body, "field 'body'", TextView.class);
        questionDialogView.buttonOne = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.button_one, "field 'buttonOne'", ViewGroup.class);
        questionDialogView.buttonOneImage = (ImageView) Utils.findRequiredViewAsType(view2, R.id.button_one_image, "field 'buttonOneImage'", ImageView.class);
        questionDialogView.buttonOneText = (TextView) Utils.findRequiredViewAsType(view2, R.id.button_one_text, "field 'buttonOneText'", TextView.class);
        questionDialogView.buttonThree = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.button_three, "field 'buttonThree'", ViewGroup.class);
        questionDialogView.buttonThreeImage = (ImageView) Utils.findRequiredViewAsType(view2, R.id.button_three_image, "field 'buttonThreeImage'", ImageView.class);
        questionDialogView.buttonThreeText = (TextView) Utils.findRequiredViewAsType(view2, R.id.button_three_text, "field 'buttonThreeText'", TextView.class);
        questionDialogView.buttonTwo = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.button_two, "field 'buttonTwo'", ViewGroup.class);
        questionDialogView.buttonTwoImage = (ImageView) Utils.findRequiredViewAsType(view2, R.id.button_two_image, "field 'buttonTwoImage'", ImageView.class);
        questionDialogView.buttonTwoText = (TextView) Utils.findRequiredViewAsType(view2, R.id.button_two_text, "field 'buttonTwoText'", TextView.class);
        questionDialogView.checkbox = (Switch) Utils.findRequiredViewAsType(view2, R.id.dialog_checkbox, "field 'checkbox'", Switch.class);
        questionDialogView.checkboxText = (TextView) Utils.findRequiredViewAsType(view2, R.id.dialog_checkbox_text, "field 'checkboxText'", TextView.class);
        questionDialogView.title = (TextView) Utils.findRequiredViewAsType(view2, R.id.dialog_title, "field 'title'", TextView.class);
    }
}
