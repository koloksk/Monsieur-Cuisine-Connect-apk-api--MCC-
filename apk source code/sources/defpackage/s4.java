package defpackage;

import android.media.browse.MediaBrowser;
import android.support.annotation.NonNull;
import defpackage.r4;
import java.util.List;

/* loaded from: classes.dex */
public class s4<T extends r4> extends MediaBrowser.SubscriptionCallback {
    public final T a;

    public s4(T t) {
        this.a = t;
    }

    @Override // android.media.browse.MediaBrowser.SubscriptionCallback
    public void onChildrenLoaded(@NonNull String str, List<MediaBrowser.MediaItem> list) {
        this.a.a(str, list);
    }

    @Override // android.media.browse.MediaBrowser.SubscriptionCallback
    public void onError(@NonNull String str) {
        this.a.a(str);
    }
}
