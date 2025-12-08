package machineAdapter.ipc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface ErrorState {
    public static final int LID_OPEN = 9;
    public static final int LID_OPEN_DURING_COOKING = 10;
    public static final int MAX_RUNTIME_EXCEEDED = 13;
    public static final int MOTOR = 4;
    public static final int MOTOR_OVERHEAT = 12;
    public static final int NO = 0;
    public static final int NOT_LOGGED_IN = 98;
    public static final int NO_BOWL_DETECTED = 8;
    public static final int NO_FAVORITES = 11;
    public static final int NO_INTERNET = 99;
    public static final int NO_WATER = 5;
    public static final int POWER_INPUT_OVERVOLTAGE = 2;
    public static final int POWER_INPUT_UNDERVOLTAGE = 3;
    public static final int SCALE_OVERLOAD = 7;
    public static final int SERIAL_PORT_COMMUNICATION = 1;
    public static final int UNDEFINED = 6;
}
