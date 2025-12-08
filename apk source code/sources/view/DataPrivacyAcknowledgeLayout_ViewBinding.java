package view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import helper.ActionListener;

/* loaded from: classes.dex */
public class DataPrivacyAcknowledgeLayout_ViewBinding implements Unbinder {
    public DataPrivacyAcknowledgeLayout a;
    public View b;
    public View c;

    public class a extends DebouncingOnClickListener {
        public final /* synthetic */ DataPrivacyAcknowledgeLayout c;

        public a(DataPrivacyAcknowledgeLayout_ViewBinding dataPrivacyAcknowledgeLayout_ViewBinding, DataPrivacyAcknowledgeLayout dataPrivacyAcknowledgeLayout) {
            this.c = dataPrivacyAcknowledgeLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            DataPrivacyAcknowledgeLayout dataPrivacyAcknowledgeLayout = this.c;
            ActionListener<DataPrivacyAcknowledgeLayout> actionListener = dataPrivacyAcknowledgeLayout.a;
            if (actionListener != null) {
                actionListener.onAction(dataPrivacyAcknowledgeLayout);
            }
        }
    }

    public class b extends DebouncingOnClickListener {
        public final /* synthetic */ DataPrivacyAcknowledgeLayout c;

        public b(DataPrivacyAcknowledgeLayout_ViewBinding dataPrivacyAcknowledgeLayout_ViewBinding, DataPrivacyAcknowledgeLayout dataPrivacyAcknowledgeLayout) {
            this.c = dataPrivacyAcknowledgeLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            DataPrivacyAcknowledgeLayout dataPrivacyAcknowledgeLayout = this.c;
            ActionListener<DataPrivacyAcknowledgeLayout> actionListener = dataPrivacyAcknowledgeLayout.a;
            if (actionListener != null) {
                actionListener.onAction(dataPrivacyAcknowledgeLayout);
            }
        }
    }

    @UiThread
    public DataPrivacyAcknowledgeLayout_ViewBinding(DataPrivacyAcknowledgeLayout dataPrivacyAcknowledgeLayout) {
        this(dataPrivacyAcknowledgeLayout, dataPrivacyAcknowledgeLayout);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DataPrivacyAcknowledgeLayout dataPrivacyAcknowledgeLayout = this.a;
        if (dataPrivacyAcknowledgeLayout == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        dataPrivacyAcknowledgeLayout.acknowledgeHeader = null;
        dataPrivacyAcknowledgeLayout.acknowledgeText = null;
        this.b.setOnClickListener(null);
        this.b = null;
        this.c.setOnClickListener(null);
        this.c = null;
    }

    @UiThread
    public DataPrivacyAcknowledgeLayout_ViewBinding(DataPrivacyAcknowledgeLayout dataPrivacyAcknowledgeLayout, View view2) {
        this.a = dataPrivacyAcknowledgeLayout;
        dataPrivacyAcknowledgeLayout.acknowledgeHeader = (TextView) Utils.findRequiredViewAsType(view2, R.id.data_privacy_acknowledge_header, "field 'acknowledgeHeader'", TextView.class);
        dataPrivacyAcknowledgeLayout.acknowledgeText = (TextView) Utils.findRequiredViewAsType(view2, R.id.data_privacy_acknowledge_text, "field 'acknowledgeText'", TextView.class);
        View viewFindRequiredView = Utils.findRequiredView(view2, R.id.data_privacy_acknowledge_label, "method 'onAcknowledgeClick'");
        this.b = viewFindRequiredView;
        viewFindRequiredView.setOnClickListener(new a(this, dataPrivacyAcknowledgeLayout));
        View viewFindRequiredView2 = Utils.findRequiredView(view2, R.id.data_privacy_acknowledge_button, "method 'onAcknowledgeClick'");
        this.c = viewFindRequiredView2;
        viewFindRequiredView2.setOnClickListener(new b(this, dataPrivacyAcknowledgeLayout));
    }
}
