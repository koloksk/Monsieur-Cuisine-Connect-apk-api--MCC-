package cooking;

import android.util.Log;
import model.Presets;

/* loaded from: classes.dex */
public class Limits {
    public static final int SPEED_DEFAULT = 1;
    public static final int SPEED_KNEADING_MAX_REVERSE = 4;
    public static final int SPEED_MAX = 10;
    public static final int SPEED_MAX_HEATER_ON = 3;
    public static final int SPEED_MAX_REVERSE = 3;
    public static final long SPEED_STIRRER_WARNING = 5;
    public static final int TEMPERATURE_LEVEL_MAX;
    public static final long TIME_MAX_MILLIS = 5940000;
    public static final long TURBO_FIRST_TICK_MILLIS = 100;
    public static final int TURBO_MAX_SECONDS = 600;
    public static final int TURBO_OVERHEATED_TEMPERATURE = 60;
    public static final int TURBO_OVERHEATED_TEMPERATURE_LEVEL = 5;
    public static final long TURBO_WARNING_SECONDS = 8;
    public static final long WEIGHT_MAX_KNEADING = 600;
    public static final int MAX_TEMPERATURE = 130;
    public static final int[] a = {0, 37, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, Presets.KNEADING_MAX_TIME, 125, MAX_TEMPERATURE};
    public static final String b = Limits.class.getSimpleName();
    public static int WARNING_SPEED_LEVEL_AT_HIGH_TEMPERATURE = 4;
    public static int WARNING_TEMPERATURE_AT_HIGH_SPEED = 60;

    static {
        TEMPERATURE_LEVEL_MAX = r0.length - 1;
    }

    public static long getMaxTimeForSpeedLevel(int i) {
        if (i > 6) {
            return 900000L;
        }
        return TIME_MAX_MILLIS;
    }

    public static int getStepFromTemperature(int i) {
        int i2 = TEMPERATURE_LEVEL_MAX;
        int i3 = 0;
        while (true) {
            if (i3 > TEMPERATURE_LEVEL_MAX) {
                break;
            }
            if (i <= a[i3]) {
                i2 = i3;
                break;
            }
            i3++;
        }
        Log.i(b, "getStepFromTemperature: temperature >> " + i + " step >> " + i2);
        return i2;
    }

    public static int getTemperatureFromStep(int i) {
        int i2 = (i < 0 || i > TEMPERATURE_LEVEL_MAX) ? 0 : a[i];
        Log.i(b, "getTemperatureFromStep: step >> " + i + " temperature >> " + i2);
        return i2;
    }
}
