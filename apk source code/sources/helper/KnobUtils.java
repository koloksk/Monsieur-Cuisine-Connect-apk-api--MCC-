package helper;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import application.App;
import de.silpion.mc2.R;
import org.apache.commons.lang3.time.DurationFormatUtils;
import view.knob.BaseKnob;
import view.knob.Knob;
import view.knob.KnobTime;

/* loaded from: classes.dex */
public final class KnobUtils {
    public static MinutesSeconds getMinSec(long j) {
        if (j < 0) {
            return null;
        }
        String[] strArrSplit = DurationFormatUtils.formatDuration(j, "mm:ss").split(":");
        return new MinutesSeconds(strArrSplit[0], strArrSplit[1]);
    }

    public static String getMinSecString(long j) {
        if (j >= 0) {
            return DurationFormatUtils.formatDuration(j, "mm:ss");
        }
        Log.w("KnobUtils", "getMinSecString: millis < 0");
        return "00:00";
    }

    public static void resetWarningProgressColor(Knob knob) {
        if (knob != null) {
            knob.setProgressColor(ContextCompat.getColor(App.getInstance(), R.color.accent_dark));
        }
    }

    public static void setActiveIndicator(BaseKnob baseKnob) {
        if (baseKnob != null) {
            baseKnob.setArcColor(ContextCompat.getColor(App.getInstance(), R.color.divider));
            baseKnob.setProgressColor(ContextCompat.getColor(App.getInstance(), R.color.accent_dark));
        }
    }

    public static void setInactiveIndicator(Knob knob) {
        if (knob != null) {
            knob.setArcColor(ContextCompat.getColor(App.getInstance(), R.color.preset_pause_deactivated));
            knob.setProgressColor(ContextCompat.getColor(App.getInstance(), android.R.color.transparent));
        }
    }

    public static void setWarningProgressColor(Knob knob) {
        if (knob != null) {
            knob.setProgressColor(ContextCompat.getColor(App.getInstance(), R.color.accent));
        }
    }

    public static void updateTurbo(KnobTime knobTime, int i) {
        if (knobTime != null) {
            knobTime.setTime(i * 1000);
            knobTime.setProgress(i);
        }
    }
}
