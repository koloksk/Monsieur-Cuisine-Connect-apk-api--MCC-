package view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import helper.ActionListener;

/* loaded from: classes.dex */
public class WifiSetupLayout_ViewBinding implements Unbinder {
    public WifiSetupLayout a;
    public View b;
    public View c;

    public class a extends DebouncingOnClickListener {
        public final /* synthetic */ WifiSetupLayout c;

        public a(WifiSetupLayout_ViewBinding wifiSetupLayout_ViewBinding, WifiSetupLayout wifiSetupLayout) {
            this.c = wifiSetupLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            ActionListener<WifiSetupLayout> actionListener;
            WifiSetupLayout wifiSetupLayout = this.c;
            if (wifiSetupLayout.nextButton.isEnabled() && (actionListener = wifiSetupLayout.c) != null) {
                actionListener.onAction(wifiSetupLayout);
            }
        }
    }

    public class b extends DebouncingOnClickListener {
        public final /* synthetic */ WifiSetupLayout c;

        public b(WifiSetupLayout_ViewBinding wifiSetupLayout_ViewBinding, WifiSetupLayout wifiSetupLayout) {
            this.c = wifiSetupLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            WifiSetupLayout wifiSetupLayout = this.c;
            ActionListener<WifiSetupLayout> actionListener = wifiSetupLayout.a;
            if (actionListener != null) {
                actionListener.onAction(wifiSetupLayout);
            }
        }
    }

    @UiThread
    public WifiSetupLayout_ViewBinding(WifiSetupLayout wifiSetupLayout) {
        this(wifiSetupLayout, wifiSetupLayout);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        WifiSetupLayout wifiSetupLayout = this.a;
        if (wifiSetupLayout == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        wifiSetupLayout.nextButton = null;
        wifiSetupLayout.nextButtonImage = null;
        wifiSetupLayout.scanResultsList = null;
        wifiSetupLayout.scanningProgress = null;
        wifiSetupLayout.wifiText = null;
        wifiSetupLayout.wrongPassword = null;
        this.b.setOnClickListener(null);
        this.b = null;
        this.c.setOnClickListener(null);
        this.c = null;
    }

    @UiThread
    public WifiSetupLayout_ViewBinding(WifiSetupLayout wifiSetupLayout, View view2) {
        this.a = wifiSetupLayout;
        View viewFindRequiredView = Utils.findRequiredView(view2, R.id.wifi_setup_next, "field 'nextButton' and method 'nextClicked'");
        wifiSetupLayout.nextButton = viewFindRequiredView;
        this.b = viewFindRequiredView;
        viewFindRequiredView.setOnClickListener(new a(this, wifiSetupLayout));
        wifiSetupLayout.nextButtonImage = (ImageView) Utils.findRequiredViewAsType(view2, R.id.wifi_setup_next_image, "field 'nextButtonImage'", ImageView.class);
        wifiSetupLayout.scanResultsList = (ListView) Utils.findRequiredViewAsType(view2, R.id.wifi_scan_results, "field 'scanResultsList'", ListView.class);
        wifiSetupLayout.scanningProgress = (ProgressBar) Utils.findRequiredViewAsType(view2, R.id.wifi_scanning_progress, "field 'scanningProgress'", ProgressBar.class);
        wifiSetupLayout.wifiText = (TextView) Utils.findRequiredViewAsType(view2, R.id.wifi_row_text, "field 'wifiText'", TextView.class);
        wifiSetupLayout.wrongPassword = (TextView) Utils.findRequiredViewAsType(view2, R.id.wifi_wrong_password_error, "field 'wrongPassword'", TextView.class);
        View viewFindRequiredView2 = Utils.findRequiredView(view2, R.id.wifi_setup_cancel, "method 'cancelClicked'");
        this.c = viewFindRequiredView2;
        viewFindRequiredView2.setOnClickListener(new b(this, wifiSetupLayout));
    }
}
