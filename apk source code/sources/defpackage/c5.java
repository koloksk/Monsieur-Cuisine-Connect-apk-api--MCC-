package defpackage;

import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

/* loaded from: classes.dex */
public class c5 implements Runnable {
    public final /* synthetic */ MediaBrowserServiceCompat.k a;
    public final /* synthetic */ String b;
    public final /* synthetic */ ResultReceiver c;
    public final /* synthetic */ MediaBrowserServiceCompat.j d;

    public c5(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, ResultReceiver resultReceiver) {
        this.d = jVar;
        this.a = kVar;
        this.b = str;
        this.c = resultReceiver;
    }

    @Override // java.lang.Runnable
    public void run() {
        MediaBrowserServiceCompat.e eVar = MediaBrowserServiceCompat.this.b.get(((MediaBrowserServiceCompat.l) this.a).a());
        if (eVar != null) {
            MediaBrowserServiceCompat.this.a(this.b, eVar, this.c);
            return;
        }
        StringBuilder sbA = g9.a("getMediaItem for callback that isn't registered id=");
        sbA.append(this.b);
        Log.w("MBServiceCompat", sbA.toString());
    }
}
