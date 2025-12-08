package defpackage;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserServiceCompatApi23$ServiceCompatProxy;

/* loaded from: classes.dex */
public class k5 extends i5 {
    public k5(Context context, MediaBrowserServiceCompatApi23$ServiceCompatProxy mediaBrowserServiceCompatApi23$ServiceCompatProxy) {
        super(context, mediaBrowserServiceCompatApi23$ServiceCompatProxy);
    }

    @Override // android.service.media.MediaBrowserService
    public void onLoadItem(String str, MediaBrowserService.Result<MediaBrowser.MediaItem> result) {
        ((MediaBrowserServiceCompatApi23$ServiceCompatProxy) this.a).onLoadItem(str, new j5<>(result));
    }
}
