package activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import view.DataPrivacyAcknowledgeLayout;
import view.QuestionDialogView;
import view.SurveyDialogView;

/* loaded from: classes.dex */
public class MainActivity_ViewBinding implements Unbinder {
    public MainActivity a;
    public View b;
    public View c;
    public View d;
    public View e;

    public class a extends DebouncingOnClickListener {
        public final /* synthetic */ MainActivity c;

        public a(MainActivity_ViewBinding mainActivity_ViewBinding, MainActivity mainActivity) {
            this.c = mainActivity;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.bookmarkClick();
        }
    }

    public class b extends DebouncingOnClickListener {
        public final /* synthetic */ MainActivity c;

        public b(MainActivity_ViewBinding mainActivity_ViewBinding, MainActivity mainActivity) {
            this.c = mainActivity;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.onClickLeftTopBarIcon();
        }
    }

    public class c extends DebouncingOnClickListener {
        public final /* synthetic */ MainActivity c;

        public c(MainActivity_ViewBinding mainActivity_ViewBinding, MainActivity mainActivity) {
            this.c = mainActivity;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.onClickMiddleTopBarIcon();
        }
    }

    public class d extends DebouncingOnClickListener {
        public final /* synthetic */ MainActivity c;

        public d(MainActivity_ViewBinding mainActivity_ViewBinding, MainActivity mainActivity) {
            this.c = mainActivity;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            this.c.onClickRightTopBarIcon();
        }
    }

    @UiThread
    public MainActivity_ViewBinding(MainActivity mainActivity) {
        this(mainActivity, mainActivity.getWindow().getDecorView());
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        MainActivity mainActivity = this.a;
        if (mainActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        mainActivity.bookmarkDialog = null;
        mainActivity.bookmarkImageView = null;
        mainActivity.containerGuidedCooking = null;
        mainActivity.containerMain = null;
        mainActivity.containerRecipes = null;
        mainActivity.content = null;
        mainActivity.dialogRecipesReceived = null;
        mainActivity.dialogUserTimeout = null;
        mainActivity.privacyAcknowledge = null;
        mainActivity.termsOfUseAcknowledgeDialog = null;
        mainActivity.surveyDialog = null;
        mainActivity.root = null;
        mainActivity.topLeftButton = null;
        mainActivity.topLeftLabel = null;
        mainActivity.topMiddleButton = null;
        mainActivity.topMiddleLabel = null;
        mainActivity.topRightButton = null;
        mainActivity.topRightLabel = null;
        this.b.setOnClickListener(null);
        this.b = null;
        this.c.setOnClickListener(null);
        this.c = null;
        this.d.setOnClickListener(null);
        this.d = null;
        this.e.setOnClickListener(null);
        this.e = null;
    }

    @UiThread
    public MainActivity_ViewBinding(MainActivity mainActivity, View view2) {
        this.a = mainActivity;
        mainActivity.bookmarkDialog = (QuestionDialogView) Utils.findRequiredViewAsType(view2, R.id.bookmark_question_dialog, "field 'bookmarkDialog'", QuestionDialogView.class);
        View viewFindRequiredView = Utils.findRequiredView(view2, R.id.bookmark_iv, "field 'bookmarkImageView' and method 'bookmarkClick'");
        mainActivity.bookmarkImageView = (ImageView) Utils.castView(viewFindRequiredView, R.id.bookmark_iv, "field 'bookmarkImageView'", ImageView.class);
        this.b = viewFindRequiredView;
        viewFindRequiredView.setOnClickListener(new a(this, mainActivity));
        mainActivity.containerGuidedCooking = (FrameLayout) Utils.findRequiredViewAsType(view2, R.id.container_guided_cooking, "field 'containerGuidedCooking'", FrameLayout.class);
        mainActivity.containerMain = (FrameLayout) Utils.findRequiredViewAsType(view2, R.id.container_main, "field 'containerMain'", FrameLayout.class);
        mainActivity.containerRecipes = (FrameLayout) Utils.findRequiredViewAsType(view2, R.id.container_recipes, "field 'containerRecipes'", FrameLayout.class);
        mainActivity.content = (ViewGroup) Utils.findRequiredViewAsType(view2, R.id.main_activity_content, "field 'content'", ViewGroup.class);
        mainActivity.dialogRecipesReceived = (QuestionDialogView) Utils.findRequiredViewAsType(view2, R.id.dialog_recipes_received, "field 'dialogRecipesReceived'", QuestionDialogView.class);
        mainActivity.dialogUserTimeout = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.dialog_user_timeout, "field 'dialogUserTimeout'", RelativeLayout.class);
        mainActivity.privacyAcknowledge = (DataPrivacyAcknowledgeLayout) Utils.findRequiredViewAsType(view2, R.id.popup_acknowledge_data_privacy, "field 'privacyAcknowledge'", DataPrivacyAcknowledgeLayout.class);
        mainActivity.termsOfUseAcknowledgeDialog = (QuestionDialogView) Utils.findRequiredViewAsType(view2, R.id.popup_acknowledge_terms_of_use, "field 'termsOfUseAcknowledgeDialog'", QuestionDialogView.class);
        mainActivity.surveyDialog = (SurveyDialogView) Utils.findRequiredViewAsType(view2, R.id.popup_survey, "field 'surveyDialog'", SurveyDialogView.class);
        mainActivity.root = (FrameLayout) Utils.findRequiredViewAsType(view2, R.id.main_activity_root, "field 'root'", FrameLayout.class);
        View viewFindRequiredView2 = Utils.findRequiredView(view2, R.id.top_bar_left_button, "field 'topLeftButton' and method 'onClickLeftTopBarIcon'");
        mainActivity.topLeftButton = (ImageButton) Utils.castView(viewFindRequiredView2, R.id.top_bar_left_button, "field 'topLeftButton'", ImageButton.class);
        this.c = viewFindRequiredView2;
        viewFindRequiredView2.setOnClickListener(new b(this, mainActivity));
        mainActivity.topLeftLabel = (TextView) Utils.findRequiredViewAsType(view2, R.id.top_bar_left_label, "field 'topLeftLabel'", TextView.class);
        View viewFindRequiredView3 = Utils.findRequiredView(view2, R.id.top_bar_middle_button, "field 'topMiddleButton' and method 'onClickMiddleTopBarIcon'");
        mainActivity.topMiddleButton = (ImageButton) Utils.castView(viewFindRequiredView3, R.id.top_bar_middle_button, "field 'topMiddleButton'", ImageButton.class);
        this.d = viewFindRequiredView3;
        viewFindRequiredView3.setOnClickListener(new c(this, mainActivity));
        mainActivity.topMiddleLabel = (TextView) Utils.findRequiredViewAsType(view2, R.id.top_bar_middle_label, "field 'topMiddleLabel'", TextView.class);
        View viewFindRequiredView4 = Utils.findRequiredView(view2, R.id.top_bar_right_button, "field 'topRightButton' and method 'onClickRightTopBarIcon'");
        mainActivity.topRightButton = (ImageButton) Utils.castView(viewFindRequiredView4, R.id.top_bar_right_button, "field 'topRightButton'", ImageButton.class);
        this.e = viewFindRequiredView4;
        viewFindRequiredView4.setOnClickListener(new d(this, mainActivity));
        mainActivity.topRightLabel = (TextView) Utils.findRequiredViewAsType(view2, R.id.top_bar_right_label, "field 'topRightLabel'", TextView.class);
    }
}
