package machineAdapter.ipc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface ScaleCalibrationState {
    public static final int FAILURE = 6;
    public static final int FIRST_LOAD_1_KG = 2;
    public static final int INITIALIZING = 0;
    public static final int NOT_AVAILABLE = 7;
    public static final int OVERLOAD = 5;
    public static final int SECOND_LOAD_3_KG = 3;
    public static final int SUCCESS = 4;
    public static final int ZERO_POINT = 1;
}
