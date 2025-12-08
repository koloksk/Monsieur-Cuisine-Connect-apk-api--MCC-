package defpackage;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import defpackage.v4;
import java.util.List;

/* loaded from: classes.dex */
public class w4<T extends v4> extends s4<T> {
    public w4(T t) {
        super(t);
    }

    @Override // android.media.browse.MediaBrowser.SubscriptionCallback
    public void onChildrenLoaded(@NonNull String str, List<MediaBrowser.MediaItem> list, @NonNull Bundle bundle) {
        ((v4) this.a).a(str, list, bundle);
    }

    @Override // android.media.browse.MediaBrowser.SubscriptionCallback
    public void onError(@NonNull String str, @NonNull Bundle bundle) {
        ((v4) this.a).a(str, bundle);
    }
}
