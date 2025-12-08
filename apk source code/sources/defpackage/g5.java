package defpackage;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

/* loaded from: classes.dex */
public class g5 implements Runnable {
    public final /* synthetic */ MediaBrowserServiceCompat.k a;
    public final /* synthetic */ String b;
    public final /* synthetic */ Bundle c;
    public final /* synthetic */ ResultReceiver d;
    public final /* synthetic */ MediaBrowserServiceCompat.j e;

    public g5(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, Bundle bundle, ResultReceiver resultReceiver) {
        this.e = jVar;
        this.a = kVar;
        this.b = str;
        this.c = bundle;
        this.d = resultReceiver;
    }

    @Override // java.lang.Runnable
    public void run() {
        MediaBrowserServiceCompat.e eVar = MediaBrowserServiceCompat.this.b.get(((MediaBrowserServiceCompat.l) this.a).a());
        if (eVar != null) {
            MediaBrowserServiceCompat.this.a(this.b, this.c, eVar, this.d);
            return;
        }
        StringBuilder sbA = g9.a("sendCustomAction for callback that isn't registered action=");
        sbA.append(this.b);
        sbA.append(", extras=");
        sbA.append(this.c);
        Log.w("MBServiceCompat", sbA.toString());
    }
}
