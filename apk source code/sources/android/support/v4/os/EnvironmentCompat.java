package android.support.v4.os;

import android.os.Environment;
import java.io.File;

/* loaded from: classes.dex */
public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";

    public static String getStorageState(File file) {
        return Environment.getStorageState(file);
    }
}
