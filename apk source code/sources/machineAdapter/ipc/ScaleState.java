package machineAdapter.ipc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface ScaleState {
    public static final int CALIBRATION = 2;
    public static final int NORMAL = 0;
    public static final int OVERLOAD = 1;
}
