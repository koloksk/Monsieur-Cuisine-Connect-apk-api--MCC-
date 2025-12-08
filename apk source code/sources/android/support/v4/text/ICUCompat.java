package android.support.v4.text;

import android.support.annotation.Nullable;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/* loaded from: classes.dex */
public final class ICUCompat {
    public static Method a;

    static {
        try {
            a = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", Locale.class);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Nullable
    public static String maximizeAndGetScript(Locale locale) {
        try {
            return ((Locale) a.invoke(null, locale)).getScript();
        } catch (IllegalAccessException e) {
            Log.w("ICUCompat", e);
            return locale.getScript();
        } catch (InvocationTargetException e2) {
            Log.w("ICUCompat", e2);
            return locale.getScript();
        }
    }
}
