package adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.silpion.mc2.R;
import helper.WifiHelper;
import java.util.List;
import view.QuicksandTextView;

/* loaded from: classes.dex */
public class ScanResultAdapter extends ArrayAdapter<WifiHelper.WifiInfoView> {
    public final List<WifiHelper.WifiInfoView> a;
    public WifiHelper b;

    public class a {
        public final QuicksandTextView a;
        public final LinearLayout b;
        public final ImageView c;
        public final QuicksandTextView d;

        public a(View view2) {
            this.d = (QuicksandTextView) view2.findViewById(R.id.ssid);
            this.a = (QuicksandTextView) view2.findViewById(R.id.capabilities);
            this.b = (LinearLayout) view2.findViewById(R.id.item_scan_result_container_ll);
            this.c = (ImageView) view2.findViewById(R.id.wifi_is_secured);
        }
    }

    public ScanResultAdapter(@NonNull Context context, @LayoutRes int i, @NonNull List<WifiHelper.WifiInfoView> list) {
        super(context, i, list);
        this.a = list;
    }

    public void connectionChanged(String str) {
        WifiHelper.WifiInfoView item;
        if (str == null) {
            notifyDataSetChanged();
            return;
        }
        int i = 0;
        while (true) {
            if (i >= getCount()) {
                item = null;
                break;
            }
            item = getItem(i);
            if (item != null && TextUtils.equals(item.SSID, str)) {
                break;
            } else {
                i++;
            }
        }
        if (item != null) {
            remove(item);
            insert(item, 0);
        }
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    @NonNull
    public View getView(int i, @Nullable View view2, @NonNull ViewGroup viewGroup) {
        a aVar;
        if (view2 == null) {
            view2 = LayoutInflater.from(getContext()).inflate(R.layout.item_scan_result, viewGroup, false);
            aVar = new a(view2);
            view2.setTag(aVar);
        } else {
            aVar = (a) view2.getTag();
        }
        WifiHelper.WifiInfoView item = getItem(i);
        if (ScanResultAdapter.this.a != null) {
            if (i == r1.size() - 1) {
                aVar.b.setBackgroundResource(R.drawable.shape_settings_rectangle_rounded_bottom);
            } else {
                aVar.b.setBackgroundResource(R.drawable.shape_settings_rectangle_not_rounded);
            }
            if (!item.isSecured) {
                aVar.c.setImageDrawable(ScanResultAdapter.this.getContext().getDrawable(R.drawable.asset_008_unsecured_wifi));
            }
        }
        int i2 = R.color.text_primary;
        if (item != null) {
            aVar.d.setText(item.SSID);
            if (!item.isConfigured) {
                QuicksandTextView quicksandTextView = aVar.a;
                String str = item.capabilities;
                quicksandTextView.setText(str != null ? str : "");
            } else if (ScanResultAdapter.this.b.isWifiConnected(item.SSID)) {
                i2 = R.color.accent_primary;
                aVar.a.setText(R.string.info_wifi_connected);
            } else {
                aVar.a.setText(R.string.info_wifi_configured);
            }
        } else {
            aVar.d.setText("");
            aVar.a.setText("");
        }
        aVar.d.setTextColor(ResourcesCompat.getColor(ScanResultAdapter.this.getContext().getResources(), i2, null));
        return view2;
    }

    public void setWifiHelper(WifiHelper wifiHelper) {
        this.b = wifiHelper;
    }
}
