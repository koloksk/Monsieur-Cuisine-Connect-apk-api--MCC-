package machineAdapter.ipc;

import android.support.annotation.IntRange;
import android.support.v4.media.MediaDescriptionCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED, to = 10)
@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface SpeedLevel {
}
