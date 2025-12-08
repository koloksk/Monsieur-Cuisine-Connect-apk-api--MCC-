package defpackage;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserServiceCompatApi21$ServiceCompatProxy;
import java.util.List;

/* loaded from: classes.dex */
public class i5 extends MediaBrowserService {
    public final MediaBrowserServiceCompatApi21$ServiceCompatProxy a;

    public i5(Context context, MediaBrowserServiceCompatApi21$ServiceCompatProxy mediaBrowserServiceCompatApi21$ServiceCompatProxy) {
        attachBaseContext(context);
        this.a = mediaBrowserServiceCompatApi21$ServiceCompatProxy;
    }

    @Override // android.service.media.MediaBrowserService
    public MediaBrowserService.BrowserRoot onGetRoot(String str, int i, Bundle bundle) {
        h5 h5VarOnGetRoot = this.a.onGetRoot(str, i, bundle == null ? null : new Bundle(bundle));
        if (h5VarOnGetRoot == null) {
            return null;
        }
        return new MediaBrowserService.BrowserRoot(h5VarOnGetRoot.a, h5VarOnGetRoot.b);
    }

    @Override // android.service.media.MediaBrowserService
    public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
        this.a.onLoadChildren(str, new j5<>(result));
    }
}
