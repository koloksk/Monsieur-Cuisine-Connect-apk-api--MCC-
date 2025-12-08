package machineAdapter.ipc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface ScaleCalibration {
    public static final int ENTER = 0;
    public static final int EXIT = 2;
    public static final int START = 1;
}
