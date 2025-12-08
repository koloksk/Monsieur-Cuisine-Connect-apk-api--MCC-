package view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.silpion.mc2.R;
import helper.ActionListener;
import helper.ResourceHelper;

/* loaded from: classes.dex */
public class DataPrivacyAcknowledgeLayout extends RelativeLayout {
    public ActionListener<DataPrivacyAcknowledgeLayout> a;

    @BindView(R.id.data_privacy_acknowledge_header)
    public TextView acknowledgeHeader;

    @BindView(R.id.data_privacy_acknowledge_text)
    public TextView acknowledgeText;
    public Unbinder b;

    public DataPrivacyAcknowledgeLayout(Context context) {
        this(context, null);
    }

    public void markPolicyTermAsChanged(boolean z) {
        if (z) {
            this.acknowledgeHeader.setText(R.string.data_privacy_acknowledge_header_new_version);
        } else {
            this.acknowledgeHeader.setText(R.string.data_privacy_acknowledge_header);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.acknowledgeText.setText(Html.fromHtml(ResourceHelper.getLocalPrivacyTermHTML()));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Unbinder unbinder = this.b;
        if (unbinder != null) {
            unbinder.unbind();
            this.b = null;
        }
    }

    public void setAcknowledgeListener(ActionListener<DataPrivacyAcknowledgeLayout> actionListener) {
        this.a = actionListener;
    }

    public void setHTMLContent(String str) {
        this.acknowledgeText.setText(Html.fromHtml(str));
    }

    public void setTitle(String str) {
        this.acknowledgeHeader.setText(str);
    }

    public DataPrivacyAcknowledgeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DataPrivacyAcknowledgeLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DataPrivacyAcknowledgeLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        RelativeLayout.inflate(context, R.layout.data_privacy_acknowledge_layout, this);
        this.b = ButterKnife.bind(this, this);
    }
}
