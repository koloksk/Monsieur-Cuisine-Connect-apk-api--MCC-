package sound;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface SoundLength {
    public static final int LONG = 2500;
    public static final int MIDDLE = 800;
    public static final int MIDDLE_THRESHOLD = 1000;
    public static final int SHORT = 500;
    public static final int SMALL_THRESHOLD = 300;
}
