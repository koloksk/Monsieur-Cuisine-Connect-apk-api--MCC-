package machineAdapter.ipc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface ActuatorState {
    public static final int OFF = 0;
    public static final int ON = 1;
}
