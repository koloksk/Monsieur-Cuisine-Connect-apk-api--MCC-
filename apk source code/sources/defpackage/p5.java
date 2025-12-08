package defpackage;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompatApi24$Callback;

/* loaded from: classes.dex */
public class p5<T extends MediaSessionCompatApi24$Callback> extends o5<T> {
    public p5(T t) {
        super(t);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onPrepare() {
        ((MediaSessionCompatApi24$Callback) this.a).onPrepare();
    }

    @Override // android.media.session.MediaSession.Callback
    public void onPrepareFromMediaId(String str, Bundle bundle) {
        ((MediaSessionCompatApi24$Callback) this.a).onPrepareFromMediaId(str, bundle);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onPrepareFromSearch(String str, Bundle bundle) {
        ((MediaSessionCompatApi24$Callback) this.a).onPrepareFromSearch(str, bundle);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onPrepareFromUri(Uri uri, Bundle bundle) {
        ((MediaSessionCompatApi24$Callback) this.a).onPrepareFromUri(uri, bundle);
    }
}
