package machineAdapter.ipc;

import android.support.v4.os.EnvironmentCompat;
import db.model.StepMode;

/* loaded from: classes.dex */
public class WorkModeNames {
    public static String fromMode(int i) {
        switch (i) {
            case 0:
                return "idle";
            case 1:
                return "motor_and_heater";
            case 2:
                return StepMode.TURBO;
            case 3:
                return StepMode.STEAMING;
            case 4:
                return StepMode.ROASTING;
            case 5:
                return StepMode.KNEADING;
            case 6:
                return "sleep";
            default:
                return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }
}
