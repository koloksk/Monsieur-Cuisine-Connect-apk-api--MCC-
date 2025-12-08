package machineAdapter.ipc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface ButtonState {
    public static final int DOWN = 0;
    public static final int RELEASE = 1;
}
