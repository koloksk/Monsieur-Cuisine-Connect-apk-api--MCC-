package mcapi.json;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import application.App;
import helper.EncryptionChipServiceAdapter;
import helper.SharedPreferencesHelper;
import helper.SystemProperties;
import helper.UsageLogger;
import java.util.HashMap;
import java.util.Map;
import machineAdapter.ICommandInterface;

/* loaded from: classes.dex */
public class MachineInfoData {
    public static final transient String a = EncryptionChipServiceAdapter.getInstance().getSESerial();
    public final Map<String, Object> lastdata;
    public final String prserial;
    public final String seserial = a;
    public final Map<String, Object> userinfo = new HashMap();
    public final String version;

    public MachineInfoData() throws PackageManager.NameNotFoundException {
        String str = "n/a";
        try {
            ICommandInterface commandInterface = App.getInstance().getMachineAdapter().getCommandInterface();
            String strValueOf = commandInterface != null ? String.valueOf(commandInterface.getFirmwareVersion()) : "?";
            PackageInfo packageInfo = App.getInstance().getPackageManager().getPackageInfo(App.getInstance().getPackageName(), 0);
            if (packageInfo != null) {
                String str2 = packageInfo.versionName;
                String strValueOf2 = String.valueOf(packageInfo.versionCode);
                str = "" + SystemProperties.get("ro.custom.build.version", "?") + "/" + strValueOf + "/" + str2 + "-" + strValueOf2;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        this.version = str;
        String[] publicKeyBase64 = EncryptionChipServiceAdapter.getInstance().getPublicKeyBase64();
        if (publicKeyBase64 != null && publicKeyBase64.length == 2) {
            JsonWebKey jsonWebKey = new JsonWebKey();
            jsonWebKey.x = publicKeyBase64[0];
            jsonWebKey.y = publicKeyBase64[1];
            this.userinfo.put("jwk", jsonWebKey);
            this.userinfo.put("jogdial", Boolean.valueOf(SharedPreferencesHelper.getInstance().isJogDialPushedAtBootTime()));
        }
        this.userinfo.put("fails", Integer.valueOf(SharedPreferencesHelper.getInstance().getFailedRequestCount()));
        this.prserial = Build.SERIAL;
        this.lastdata = UsageLogger.getInstance().forServer();
    }
}
