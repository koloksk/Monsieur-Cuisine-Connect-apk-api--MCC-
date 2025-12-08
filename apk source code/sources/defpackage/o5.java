package defpackage;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompatApi23$Callback;

/* loaded from: classes.dex */
public class o5<T extends MediaSessionCompatApi23$Callback> extends n5<T> {
    public o5(T t) {
        super(t);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onPlayFromUri(Uri uri, Bundle bundle) {
        ((MediaSessionCompatApi23$Callback) this.a).onPlayFromUri(uri, bundle);
    }
}
