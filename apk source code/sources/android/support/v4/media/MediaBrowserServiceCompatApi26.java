package android.support.v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.support.annotation.RequiresApi;
import android.util.Log;
import defpackage.k5;
import java.lang.reflect.Field;
import java.util.List;

@RequiresApi(26)
/* loaded from: classes.dex */
public class MediaBrowserServiceCompatApi26 {
    public static Field a;

    public interface ServiceCompatProxy extends MediaBrowserServiceCompatApi23$ServiceCompatProxy {
        void onLoadChildren(String str, b bVar, Bundle bundle);
    }

    public static class a extends k5 {
        public a(Context context, ServiceCompatProxy serviceCompatProxy) {
            super(context, serviceCompatProxy);
        }

        @Override // android.service.media.MediaBrowserService
        public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle bundle) {
            ((ServiceCompatProxy) this.a).onLoadChildren(str, new b(result), bundle);
        }
    }

    public static class b {
        public MediaBrowserService.Result a;

        public b(MediaBrowserService.Result result) {
            this.a = result;
        }
    }

    static {
        try {
            Field declaredField = MediaBrowserService.Result.class.getDeclaredField("mFlags");
            a = declaredField;
            declaredField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.w("MBSCompatApi26", e);
        }
    }

    public static Object a(Context context, ServiceCompatProxy serviceCompatProxy) {
        return new a(context, serviceCompatProxy);
    }

    public static void a(Object obj, String str, Bundle bundle) {
        ((MediaBrowserService) obj).notifyChildrenChanged(str, bundle);
    }

    public static Bundle a(Object obj) {
        return ((MediaBrowserService) obj).getBrowserRootHints();
    }
}
