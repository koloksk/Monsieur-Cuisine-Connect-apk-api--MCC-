package view;

import adapter.ScanResultAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.silpion.mc2.R;
import helper.ActionListener;
import helper.WifiHelper;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class WifiSetupLayout extends RelativeLayout {
    public static final String g = WifiSetupLayout.class.getSimpleName();
    public ActionListener<WifiSetupLayout> a;
    public WifiHelper.WifiInfoView b;
    public ActionListener<WifiSetupLayout> c;
    public ScanResultAdapter d;
    public Unbinder e;
    public WifiHelper f;

    @BindView(R.id.wifi_setup_next)
    public View nextButton;

    @BindView(R.id.wifi_setup_next_image)
    public ImageView nextButtonImage;

    @BindView(R.id.wifi_scan_results)
    public ListView scanResultsList;

    @BindView(R.id.wifi_scanning_progress)
    public ProgressBar scanningProgress;

    @BindView(R.id.wifi_row_text)
    public TextView wifiText;

    @BindView(R.id.wifi_wrong_password_error)
    public TextView wrongPassword;

    public class a implements WifiHelper.WifiConnectionStateListener {
        public a() {
        }

        @Override // helper.WifiHelper.WifiConnectionStateListener
        public void connected() {
            WifiSetupLayout.this.scanningProgress.setVisibility(8);
            WifiSetupLayout.this.wrongPassword.setVisibility(8);
            WifiSetupLayout wifiSetupLayout = WifiSetupLayout.this;
            wifiSetupLayout.b = null;
            wifiSetupLayout.nextButtonImage.clearColorFilter();
            wifiSetupLayout.nextButton.setEnabled(true);
        }

        @Override // helper.WifiHelper.WifiConnectionStateListener
        public void establishingConnection() {
            WifiSetupLayout.this.scanningProgress.setVisibility(0);
            WifiSetupLayout.this.wrongPassword.setVisibility(8);
        }

        @Override // helper.WifiHelper.WifiConnectionStateListener
        public void failed() {
            WifiSetupLayout.this.scanningProgress.setVisibility(8);
            WifiSetupLayout wifiSetupLayout = WifiSetupLayout.this;
            if (wifiSetupLayout.b != null) {
                wifiSetupLayout.wrongPassword.setVisibility(0);
                WifiSetupLayout wifiSetupLayout2 = WifiSetupLayout.this;
                WifiHelper.WifiInfoView wifiInfoView = wifiSetupLayout2.b;
                wifiInfoView.isConfigured = false;
                wifiSetupLayout2.f.deleteWifiConfiguration(wifiInfoView);
            }
            WifiSetupLayout.this.a();
        }
    }

    public WifiSetupLayout(Context context) {
        this(context, null);
    }

    public /* synthetic */ void a(int i) {
        this.d.clear();
        this.d.addAll(this.f.getScanResults());
        this.d.notifyDataSetChanged();
        c();
        this.scanningProgress.setVisibility(8);
    }

    public /* synthetic */ boolean b(AdapterView adapterView, View view2, int i, long j) {
        final WifiHelper.WifiInfoView item = this.d.getItem(i);
        if (item == null || !item.isConfigured) {
            return false;
        }
        PopupMenu popupMenu = new PopupMenu(getContext(), view2);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: ar
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return this.a.a(item, menuItem);
            }
        });
        popupMenu.getMenuInflater().inflate(R.menu.configured_wifi_menu, popupMenu.getMenu());
        popupMenu.show();
        return true;
    }

    public final void c() {
        if (this.f.startScan(getContext(), new WifiHelper.ScanResultsListener() { // from class: br
            @Override // helper.WifiHelper.ScanResultsListener
            public final void scanResultsAvailable() {
                this.a.b();
            }
        })) {
            this.scanningProgress.setVisibility(0);
        }
    }

    public final void d() {
        String connectedSSID = this.f.getConnectedSSID();
        if (TextUtils.isEmpty(connectedSSID)) {
            this.wifiText.setText(getContext().getResources().getString(R.string.choose_network));
        } else {
            this.wifiText.setText(connectedSSID);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.e = ButterKnife.bind(this, this);
        a();
        a aVar = new a();
        this.d = new ScanResultAdapter(getContext(), R.layout.item_scan_result, new ArrayList());
        WifiHelper wifiHelper = WifiHelper.getInstance(new WifiHelper.NetworkStateListener() { // from class: zq
            @Override // helper.WifiHelper.NetworkStateListener
            public final void networkStateChanged(String str, String str2) {
                this.a.a(str, str2);
            }
        }, aVar);
        this.f = wifiHelper;
        this.d.setWifiHelper(wifiHelper);
        this.d.addAll(this.f.getScanResults());
        this.scanResultsList.setAdapter((ListAdapter) this.d);
        this.scanResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: wq
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view2, int i, long j) {
                this.a.a(adapterView, view2, i, j);
            }
        });
        this.scanResultsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: xq
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public final boolean onItemLongClick(AdapterView adapterView, View view2, int i, long j) {
                return this.a.b(adapterView, view2, i, j);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Unbinder unbinder = this.e;
        if (unbinder != null) {
            unbinder.unbind();
            this.e = null;
        }
    }

    public void setCancelListener(ActionListener<WifiSetupLayout> actionListener) {
        this.a = actionListener;
    }

    public void setNextListener(ActionListener<WifiSetupLayout> actionListener) {
        this.c = actionListener;
    }

    public void start() {
        d();
        if (!this.f.isWifiEnabled()) {
            this.f.setWifiEnabled(getContext(), true, new WifiHelper.WifiStateListener() { // from class: yq
                @Override // helper.WifiHelper.WifiStateListener
                public final void stateReached(int i) {
                    this.a.a(i);
                }
            });
        } else if (this.f.setWifiOn(true)) {
            this.d.clear();
            this.d.addAll(this.f.getScanResults());
            this.d.notifyDataSetChanged();
            c();
        }
    }

    public void stop() {
    }

    public WifiSetupLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiSetupLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiSetupLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        RelativeLayout.inflate(context, R.layout.wifi_setup_layout, this);
    }

    public /* synthetic */ void a(String str, String str2) {
        this.d.connectionChanged(str);
        d();
    }

    public /* synthetic */ void a(AdapterView adapterView, View view2, int i, long j) {
        final WifiHelper.WifiInfoView item = this.d.getItem(i);
        if (item == null) {
            return;
        }
        if (this.f.isWifiConnected(item.SSID)) {
            this.scanningProgress.setVisibility(0);
            this.b = null;
            this.f.reconnect();
            return;
        }
        if (item.isConfigured) {
            this.scanningProgress.setVisibility(0);
            this.f.connectConfiguredWifi(item.SSID);
            this.b = null;
            d();
            this.d.notifyDataSetChanged();
            return;
        }
        this.scanningProgress.setVisibility(0);
        Log.w(g, "showPasswordDialog");
        if (!item.isSecured) {
            this.f.configureWifi(item, "");
            this.f.connectConfiguredWifi(item.SSID);
            item.isConfigured = true;
            this.b = item;
            return;
        }
        this.b = null;
        Context context = getContext();
        Resources resources = context.getResources();
        View viewInflate = RelativeLayout.inflate(context, R.layout.dialog_firstlaunch_wifi_password, null);
        final TextInputEditText textInputEditText = (TextInputEditText) viewInflate.findViewById(R.id.password_input);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(viewInflate);
        builder.setTitle(resources.getString(R.string.enter_password_for, item.SSID));
        builder.setCancelable(true);
        builder.setPositiveButton(resources.getString(R.string.connect), new DialogInterface.OnClickListener() { // from class: cr
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.a.a(item, textInputEditText, dialogInterface, i2);
            }
        });
        builder.show();
    }

    public /* synthetic */ void b() {
        this.d.clear();
        this.d.addAll(this.f.getScanResults());
        this.d.notifyDataSetChanged();
        this.scanningProgress.setVisibility(8);
    }

    public /* synthetic */ boolean a(WifiHelper.WifiInfoView wifiInfoView, MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.delete_wifi_configuration) {
            return true;
        }
        boolean zIsWifiConnected = this.f.isWifiConnected(wifiInfoView.SSID);
        if (!this.f.deleteWifiConfiguration(wifiInfoView)) {
            return true;
        }
        if (zIsWifiConnected) {
            a();
        }
        wifiInfoView.isConfigured = false;
        this.d.notifyDataSetChanged();
        return true;
    }

    public final void a() {
        this.nextButton.setEnabled(false);
        this.nextButtonImage.setColorFilter(Color.argb(100, 150, 150, 150), PorterDuff.Mode.MULTIPLY);
    }

    public /* synthetic */ void a(WifiHelper.WifiInfoView wifiInfoView, TextInputEditText textInputEditText, DialogInterface dialogInterface, int i) {
        if (this.f.configureWifi(wifiInfoView, textInputEditText.getText().toString())) {
            this.f.connectConfiguredWifi(wifiInfoView.SSID);
            wifiInfoView.isConfigured = true;
            this.b = wifiInfoView;
            d();
            c();
        }
        dialogInterface.dismiss();
    }
}
