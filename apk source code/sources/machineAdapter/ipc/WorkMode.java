package machineAdapter.ipc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface WorkMode {
    public static final int KNEADING = 5;
    public static final int MOTOR_OFF_HEATER_OFF = 0;
    public static final int MOTOR_ON_HEATER_ON = 1;
    public static final int ROASTING = 4;
    public static final int SLEEP = 6;
    public static final int STEAMING = 3;
    public static final int TURBO = 2;
}
