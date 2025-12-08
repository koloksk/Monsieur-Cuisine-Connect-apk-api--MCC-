package helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import application.App;
import defpackage.g9;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class WifiHelper {
    public static WifiHelper f;
    public final ConnectivityManager a;
    public final WifiManager b;
    public BroadcastReceiver c;
    public BroadcastReceiver d;
    public BroadcastReceiver e;

    public static class ConnectionStateReceiver extends BroadcastReceiver {
        public WifiConnectionStateListener a;

        public ConnectionStateReceiver(WifiConnectionStateListener wifiConnectionStateListener) {
            this.a = wifiConnectionStateListener;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.supplicant.STATE_CHANGE")) {
                Log.d("WifiReceiver", ">>>>SUPPLICANT_STATE_CHANGED_ACTION<<<<<<");
                switch (a.a[((SupplicantState) intent.getParcelableExtra("newState")).ordinal()]) {
                    case 1:
                        Log.i("SupplicantState", "ASSOCIATED");
                        break;
                    case 2:
                        Log.i("SupplicantState", "ASSOCIATING");
                        this.a.establishingConnection();
                        Log.d("WifiReceiver", ">>>>connecting<<<<<<");
                        break;
                    case 3:
                        Log.i("SupplicantState", "Authenticating...");
                        break;
                    case 4:
                        Log.i("SupplicantState", "Connected");
                        this.a.connected();
                        Log.d("WifiReceiver", ">>>>connected<<<<<<");
                        break;
                    case 5:
                        Log.i("SupplicantState", "Disconnected");
                        break;
                    case 6:
                        Log.i("SupplicantState", "DORMANT");
                        break;
                    case 7:
                        Log.i("SupplicantState", "FOUR_WAY_HANDSHAKE");
                        break;
                    case 8:
                        Log.i("SupplicantState", "GROUP_HANDSHAKE");
                        break;
                    case 9:
                        Log.i("SupplicantState", "INACTIVE");
                        break;
                    case 10:
                        Log.i("SupplicantState", "INTERFACE_DISABLED");
                        break;
                    case 11:
                        Log.i("SupplicantState", "INVALID");
                        break;
                    case 12:
                        Log.i("SupplicantState", "SCANNING");
                        break;
                    case 13:
                        Log.i("SupplicantState", "UNINITIALIZED");
                        break;
                    default:
                        Log.i("SupplicantState", "Unknown");
                        break;
                }
                if (intent.getIntExtra("supplicantError", -1) == 1) {
                    Log.i("ERROR_AUTHENTICATING", "ERROR_AUTHENTICATING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    this.a.failed();
                    Log.d("WifiReceiver", ">>>>failed<<<<<<");
                }
            }
        }
    }

    public interface NetworkStateListener {
        void networkStateChanged(String str, String str2);
    }

    public interface ScanResultsListener {
        void scanResultsAvailable();
    }

    public interface WifiConnectionStateListener {
        void connected();

        void establishingConnection();

        void failed();
    }

    public class WifiInfoView {
        public final String SSID;
        public final String capabilities;
        public boolean isConfigured;
        public final boolean isSecured;

        public WifiInfoView(WifiHelper wifiHelper, ScanResult scanResult) {
            this.SSID = scanResult.SSID;
            String str = scanResult.capabilities;
            this.capabilities = str;
            this.isSecured = str.toUpperCase().contains("WEP") || this.capabilities.toUpperCase().contains("WPA") || this.capabilities.toUpperCase().contains("WPA2");
        }
    }

    public interface WifiStateListener {
        void stateReached(int i);
    }

    public static /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[SupplicantState.values().length];
            a = iArr;
            try {
                iArr[SupplicantState.ASSOCIATED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[SupplicantState.ASSOCIATING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[SupplicantState.AUTHENTICATING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[SupplicantState.COMPLETED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[SupplicantState.DISCONNECTED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[SupplicantState.DORMANT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[SupplicantState.FOUR_WAY_HANDSHAKE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[SupplicantState.GROUP_HANDSHAKE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[SupplicantState.INACTIVE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[SupplicantState.INTERFACE_DISABLED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[SupplicantState.INVALID.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[SupplicantState.SCANNING.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                a[SupplicantState.UNINITIALIZED.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public class b extends BroadcastReceiver {
        public final NetworkStateListener a;

        public b(@NonNull NetworkStateListener networkStateListener) {
            this.a = networkStateListener;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            boolean booleanExtra = intent.getBooleanExtra("noConnectivity", false);
            NetworkInfo activeNetworkInfo = WifiHelper.this.a.getActiveNetworkInfo();
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("otherNetwork");
            String stringExtra = intent.getStringExtra("reason");
            boolean booleanExtra2 = intent.getBooleanExtra("isFailover", false);
            StringBuilder sbA = g9.a("Wi-Fi -- Network state: ");
            sbA.append(booleanExtra ? "no connection " : "");
            sbA.append("\n\tnetwork=");
            sbA.append(activeNetworkInfo);
            sbA.append("\n\tother network=");
            sbA.append(networkInfo);
            sbA.append("\n\treason=");
            sbA.append(stringExtra);
            sbA.append(booleanExtra2 ? " failover" : "");
            Log.v("WifiHelper", sbA.toString());
            if (WifiHelper.this == null) {
                throw null;
            }
            String extraInfo = activeNetworkInfo != null ? activeNetworkInfo.getExtraInfo() : null;
            if (WifiHelper.this == null) {
                throw null;
            }
            String extraInfo2 = networkInfo != null ? networkInfo.getExtraInfo() : null;
            this.a.networkStateChanged(extraInfo != null ? WifiHelper.this.b(extraInfo) : null, extraInfo2 != null ? WifiHelper.this.b(extraInfo2) : null);
        }
    }

    public class c extends BroadcastReceiver {
        public final ScanResultsListener a;

        public c(WifiHelper wifiHelper, ScanResultsListener scanResultsListener) {
            this.a = scanResultsListener;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.v("WifiHelper", "Wi-Fi: scan results available.");
            context.unregisterReceiver(this);
            this.a.scanResultsAvailable();
        }
    }

    public class d extends BroadcastReceiver {
        public final WifiStateListener a;
        public final int b;

        public d(WifiStateListener wifiStateListener, int i) {
            this.a = wifiStateListener;
            this.b = i;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("wifi_state", 4);
            int intExtra2 = intent.getIntExtra("previous_wifi_state", 4);
            StringBuilder sbA = g9.a("Wi-Fi changed from ");
            sbA.append(WifiHelper.a(WifiHelper.this, intExtra2));
            sbA.append(" to ");
            sbA.append(WifiHelper.a(WifiHelper.this, intExtra));
            Log.v("WifiHelper", sbA.toString());
            if (intExtra == this.b) {
                context.unregisterReceiver(this);
                this.a.stateReached(intExtra);
            }
        }
    }

    public WifiHelper(NetworkStateListener networkStateListener, WifiConnectionStateListener wifiConnectionStateListener) {
        this.c = new b(networkStateListener);
        this.d = new ConnectionStateReceiver(wifiConnectionStateListener);
        Context applicationContext = App.getInstance().getApplicationContext();
        applicationContext.registerReceiver(this.c, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        applicationContext.registerReceiver(this.d, new IntentFilter("android.net.wifi.supplicant.STATE_CHANGE"));
        this.b = (WifiManager) applicationContext.getSystemService("wifi");
        this.a = (ConnectivityManager) applicationContext.getSystemService("connectivity");
    }

    public static /* synthetic */ String a(WifiHelper wifiHelper, int i) {
        if (wifiHelper != null) {
            return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "INVALID VALUE" : "UNKNOWN" : "ENABLED" : "ENABLING" : "DISABLED" : "DISABLING";
        }
        throw null;
    }

    public static WifiHelper getInstance(NetworkStateListener networkStateListener, WifiConnectionStateListener wifiConnectionStateListener) {
        if (f == null) {
            f = new WifiHelper(networkStateListener, wifiConnectionStateListener);
        }
        f.setNetworkStateListener(networkStateListener);
        f.setWifiConnectionStateListener(wifiConnectionStateListener);
        return f;
    }

    public final String b(String str) {
        return (str.startsWith("\"") && str.endsWith("\"")) ? str.substring(1, str.length() - 1) : str;
    }

    public boolean configureWifi(@NonNull WifiInfoView wifiInfoView, String str) {
        if (wifiInfoView.capabilities == null || wifiInfoView.SSID == null) {
            return false;
        }
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + wifiInfoView.SSID + "\"";
        if (wifiInfoView.capabilities.contains("[WEP]")) {
            if (str.matches("[0-9A-Fa-f]{64}")) {
                wifiConfiguration.preSharedKey = str;
            } else {
                wifiConfiguration.preSharedKey = g9.a("\"", str, "\"");
            }
            wifiConfiguration.wepTxKeyIndex = 0;
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.allowedAuthAlgorithms.set(0);
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedKeyManagement.set(0);
        } else if (wifiInfoView.capabilities.contains("-PSK-")) {
            wifiConfiguration.preSharedKey = g9.a("\"", str, "\"");
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.allowedAuthAlgorithms.set(0);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedProtocols.set(1);
            wifiConfiguration.allowedProtocols.set(0);
        } else {
            if (wifiInfoView.capabilities.contains("EAP")) {
                return false;
            }
            wifiConfiguration.allowedKeyManagement.set(0);
        }
        StringBuilder sbA = g9.a("Wi-Fi: adding/updating configuration for [");
        sbA.append(wifiInfoView.SSID);
        sbA.append("].");
        Log.i("WifiHelper", sbA.toString());
        return this.b.addNetwork(wifiConfiguration) != -1 && this.b.saveConfiguration();
    }

    public boolean connectConfiguredWifi(@NonNull String str) {
        if (isWifiConnected(str)) {
            return true;
        }
        WifiConfiguration wifiConfigurationA = a(str);
        if (wifiConfigurationA == null) {
            return false;
        }
        StringBuilder sbA = g9.a("Wi-Fi: enabling configuration & connecting to [");
        sbA.append(wifiConfigurationA.SSID);
        sbA.append(", ");
        sbA.append(wifiConfigurationA.networkId);
        sbA.append("].");
        Log.i("WifiHelper", sbA.toString());
        this.b.disconnect();
        this.b.enableNetwork(wifiConfigurationA.networkId, true);
        this.b.saveConfiguration();
        return this.b.reconnect();
    }

    public void deleteAllConfigurations() {
        List<WifiConfiguration> configuredNetworks = this.b.getConfiguredNetworks();
        Log.i("WifiHelper", "Wi-Fi: removing all wifi configurations");
        if (configuredNetworks == null) {
            return;
        }
        Iterator<WifiConfiguration> it = configuredNetworks.iterator();
        while (it.hasNext()) {
            this.b.removeNetwork(it.next().networkId);
            this.b.saveConfiguration();
        }
    }

    public boolean deleteWifiConfiguration(WifiInfoView wifiInfoView) {
        if (wifiInfoView == null) {
            return true;
        }
        StringBuilder sbA = g9.a("Wi-Fi: removing wifi configuration for [");
        sbA.append(wifiInfoView.SSID);
        sbA.append("].");
        Log.i("WifiHelper", sbA.toString());
        WifiConfiguration wifiConfigurationA = a(wifiInfoView.SSID);
        return wifiConfigurationA != null && this.b.removeNetwork(wifiConfigurationA.networkId) && this.b.saveConfiguration();
    }

    public void doFactoryReset(final Runnable runnable) {
        if (isWifiEnabled()) {
            deleteAllConfigurations();
            this.b.setWifiEnabled(false);
            runnable.run();
        } else {
            final Context applicationContext = App.getInstance().getApplicationContext();
            b bVar = new b(new NetworkStateListener() { // from class: rk
                @Override // helper.WifiHelper.NetworkStateListener
                public final void networkStateChanged(String str, String str2) {
                    this.a.a(applicationContext, runnable, str, str2);
                }
            });
            this.e = bVar;
            applicationContext.registerReceiver(bVar, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.b.setWifiEnabled(true);
        }
    }

    public String getConnectedSSID() {
        if (a()) {
            return b(this.b.getConnectionInfo().getSSID());
        }
        return null;
    }

    public String getIPAddress() {
        if (!a()) {
            return "";
        }
        int ipAddress = this.b.getConnectionInfo().getIpAddress();
        return String.format(Locale.getDefault(), "%d.%d.%d.%d", Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255));
    }

    public String getMACAddress() {
        return this.b.getConnectionInfo().getMacAddress();
    }

    public List<WifiInfoView> getScanResults() {
        Log.v("WifiHelper", "Wi-Fi: preparing scan results for display.");
        ArrayList arrayList = new ArrayList();
        Iterator<ScanResult> it = this.b.getScanResults().iterator();
        while (it.hasNext()) {
            arrayList.add(new WifiInfoView(this, it.next()));
        }
        HashSet hashSet = new HashSet();
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            WifiInfoView wifiInfoView = (WifiInfoView) arrayList.get(size);
            if (hashSet.contains(wifiInfoView.SSID)) {
                arrayList.remove(size);
            } else {
                hashSet.add(wifiInfoView.SSID);
            }
        }
        int size2 = arrayList.size();
        while (true) {
            size2--;
            if (size2 < 0) {
                break;
            }
            if (TextUtils.isEmpty(((WifiInfoView) arrayList.get(size2)).SSID)) {
                arrayList.remove(size2);
            }
        }
        int size3 = arrayList.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            WifiInfoView wifiInfoView2 = (WifiInfoView) arrayList.get(size3);
            String str = wifiInfoView2.capabilities;
            if (str != null && !str.contains("-PSK-") && !wifiInfoView2.capabilities.contains("[WEP]") && (wifiInfoView2.capabilities.contains("-EAP-") || wifiInfoView2.capabilities.contains("[IBSS]"))) {
                arrayList.remove(size3);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (true) {
            boolean z = false;
            if (!it2.hasNext()) {
                break;
            }
            WifiInfoView wifiInfoView3 = (WifiInfoView) it2.next();
            if (a(wifiInfoView3.SSID) != null) {
                z = true;
            }
            wifiInfoView3.isConfigured = z;
        }
        int i = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            WifiInfoView wifiInfoView4 = (WifiInfoView) arrayList.get(i2);
            if (wifiInfoView4.isConfigured) {
                if (i == 0 && isWifiConnected(wifiInfoView4.SSID)) {
                    arrayList.remove(wifiInfoView4);
                    arrayList.add(0, wifiInfoView4);
                    i = 1;
                } else {
                    arrayList.remove(wifiInfoView4);
                    arrayList.add(i, wifiInfoView4);
                }
            }
        }
        StringBuilder sbA = g9.a("Wi-Fi: ");
        sbA.append(arrayList.size());
        sbA.append(" scan results prepared for display.");
        Log.i("WifiHelper", sbA.toString());
        return arrayList;
    }

    public boolean hasEnabledConfiguration() {
        Iterator<WifiConfiguration> it = this.b.getConfiguredNetworks().iterator();
        while (it.hasNext()) {
            int i = it.next().status;
            if (i == 2 || i == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isWifiConnected(@NonNull String str) {
        String connectedSSID = getConnectedSSID();
        Log.v("WifiHelper", "Wi-Fi: connected ssid is [" + connectedSSID + "] comparing to [" + str + "]?");
        return TextUtils.equals(connectedSSID, str);
    }

    public boolean isWifiEnabled() {
        return this.b.isWifiEnabled();
    }

    public void reconnect() {
        this.b.reconnect();
    }

    public void setNetworkStateListener(NetworkStateListener networkStateListener) {
        Context applicationContext = App.getInstance().getApplicationContext();
        applicationContext.unregisterReceiver(this.c);
        b bVar = new b(networkStateListener);
        this.c = bVar;
        applicationContext.registerReceiver(bVar, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void setWifiConnectionStateListener(WifiConnectionStateListener wifiConnectionStateListener) {
        Context applicationContext = App.getInstance().getApplicationContext();
        applicationContext.unregisterReceiver(this.d);
        ConnectionStateReceiver connectionStateReceiver = new ConnectionStateReceiver(wifiConnectionStateListener);
        this.d = connectionStateReceiver;
        applicationContext.registerReceiver(connectionStateReceiver, new IntentFilter("android.net.wifi.supplicant.STATE_CHANGE"));
    }

    public boolean setWifiEnabled(Context context, boolean z, WifiStateListener wifiStateListener) {
        StringBuilder sbA = g9.a("Wi-Fi: ");
        sbA.append(z ? "enabling" : "disabling");
        sbA.append(" wifi.");
        Log.v("WifiHelper", sbA.toString());
        int i = z ? 3 : 1;
        if (!z) {
            return this.b.setWifiEnabled(false);
        }
        if (this.b.getWifiState() == i) {
            wifiStateListener.stateReached(i);
            return true;
        }
        context.registerReceiver(new d(wifiStateListener, i), new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
        return this.b.setWifiEnabled(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean setWifiOn(boolean r6) {
        /*
            r5 = this;
            r0 = 0
            r1 = 1
            if (r6 != 0) goto L51
            java.lang.String r6 = r5.getConnectedSSID()
            android.net.wifi.WifiConfiguration r2 = r5.a(r6)
            if (r2 != 0) goto Lf
            goto L44
        Lf:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Wi-Fi: connected to ["
            r3.append(r4)
            r3.append(r6)
            java.lang.String r6 = "] disconnecting."
            r3.append(r6)
            java.lang.String r6 = r3.toString()
            java.lang.String r3 = "WifiHelper"
            android.util.Log.i(r3, r6)
            android.net.wifi.WifiManager r6 = r5.b
            boolean r6 = r6.disconnect()
            if (r6 == 0) goto L46
            android.net.wifi.WifiManager r6 = r5.b
            int r2 = r2.networkId
            boolean r6 = r6.disableNetwork(r2)
            if (r6 == 0) goto L46
            android.net.wifi.WifiManager r6 = r5.b
            boolean r6 = r6.saveConfiguration()
            if (r6 == 0) goto L46
        L44:
            r6 = r1
            goto L47
        L46:
            r6 = r0
        L47:
            if (r6 == 0) goto L50
            boolean r6 = r5.a(r0)
            if (r6 == 0) goto L50
            r0 = r1
        L50:
            return r0
        L51:
            boolean r6 = r5.a(r1)
            if (r6 == 0) goto L60
            android.net.wifi.WifiManager r6 = r5.b
            boolean r6 = r6.reassociate()
            if (r6 == 0) goto L60
            r0 = r1
        L60:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: helper.WifiHelper.setWifiOn(boolean):boolean");
    }

    public boolean startScan(Context context, ScanResultsListener scanResultsListener) {
        if (!this.b.isWifiEnabled()) {
            Log.w("WifiHelper", "Wi-Fi: can't scan -- wifi not enabled.");
            return false;
        }
        if (context == null) {
            Log.w("WifiHelper", "Wi-Fi: can't scan -- context must not be (null).");
            return false;
        }
        if (scanResultsListener == null) {
            Log.w("WifiHelper", "Wi-Fi: won't scan -- no listener supplied.");
            return false;
        }
        Log.v("WifiHelper", "Wi-Fi: starting scan.");
        context.registerReceiver(new c(this, scanResultsListener), new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        return this.b.startScan();
    }

    public /* synthetic */ void a(Context context, Runnable runnable, String str, String str2) {
        if (this.b.isWifiEnabled()) {
            deleteAllConfigurations();
            this.b.setWifiEnabled(false);
            context.unregisterReceiver(this.e);
            runnable.run();
        }
    }

    public static WifiHelper getInstance() {
        return f;
    }

    public final boolean a(boolean z) {
        List<WifiConfiguration> configuredNetworks = this.b.getConfiguredNetworks();
        if (configuredNetworks == null) {
            return true;
        }
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            String strB = b(wifiConfiguration.SSID);
            if (z) {
                Log.v("WifiHelper", "Wi-Fi: enabling network [" + strB + "] (id=" + wifiConfiguration.networkId + ")");
                this.b.enableNetwork(wifiConfiguration.networkId, false);
            } else {
                Log.v("WifiHelper", "Wi-Fi: disabling network [" + strB + "] (id=" + wifiConfiguration.networkId + ")");
                this.b.disableNetwork(wifiConfiguration.networkId);
            }
        }
        return this.b.saveConfiguration();
    }

    public final WifiConfiguration a(@NonNull String str) {
        List<WifiConfiguration> configuredNetworks = this.b.getConfiguredNetworks();
        if (configuredNetworks == null) {
            return null;
        }
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (TextUtils.equals(str, b(wifiConfiguration.SSID))) {
                Log.v("WifiHelper", "Wi-Fi: found configuration for [" + str + "]?");
                return wifiConfiguration;
            }
        }
        return null;
    }

    public final boolean a() {
        NetworkInfo activeNetworkInfo;
        return isWifiEnabled() && (activeNetworkInfo = this.a.getActiveNetworkInfo()) != null && activeNetworkInfo.getType() == 1 && activeNetworkInfo.isConnected();
    }
}
