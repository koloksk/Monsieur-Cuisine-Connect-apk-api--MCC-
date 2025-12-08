package application;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class Configuration {
    public static final String DEFAULT_LANGUAGE = "de";
    public static final int JOG_DIAL_BOOT_PRESS_TIME = 5000;
    public static final long JOG_DIAL_BOOT_TIMEOUT = 60000;
    public static final int LED_DIALOG_DURATION = 3000;
    public static final int LED_FINISH_COOKING_TIMER_DURATION_IN_MILLIS = 3000;
    public static final int SHARED_PREFERENCES_VERSION = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Language {
        public static final String DE = "de";
        public static final String EN = "en";
        public static final String ES = "es";
        public static final String FR = "fr";
        public static final String IT = "it";
        public static final String PL = "pl";
    }
}
