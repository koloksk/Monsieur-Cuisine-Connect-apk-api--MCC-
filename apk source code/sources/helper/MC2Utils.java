package helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public class MC2Utils {
    public static String getUTF8String(String str) {
        try {
            return URLDecoder.decode(str, CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            Log.e("MC2Utils", "getUTF8String, error", e);
            return str;
        }
    }

    public static boolean isNumeric(String str) throws NumberFormatException {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static void logDeviceDimensions(@NonNull Activity activity2) {
    }
}
