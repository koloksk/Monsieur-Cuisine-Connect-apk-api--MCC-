package machineAdapter.ipc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface LidState {
    public static final int CLOSED = 1;
    public static final int NA = 2;
    public static final int OPEN = 0;
}
