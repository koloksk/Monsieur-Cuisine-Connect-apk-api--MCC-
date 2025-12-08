package defpackage;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.util.Log;

/* loaded from: classes.dex */
public class a5 implements Runnable {
    public final /* synthetic */ MediaBrowserServiceCompat.k a;
    public final /* synthetic */ String b;
    public final /* synthetic */ IBinder c;
    public final /* synthetic */ Bundle d;
    public final /* synthetic */ MediaBrowserServiceCompat.j e;

    public a5(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, IBinder iBinder, Bundle bundle) {
        this.e = jVar;
        this.a = kVar;
        this.b = str;
        this.c = iBinder;
        this.d = bundle;
    }

    @Override // java.lang.Runnable
    public void run() {
        MediaBrowserServiceCompat.e eVar = MediaBrowserServiceCompat.this.b.get(((MediaBrowserServiceCompat.l) this.a).a());
        if (eVar != null) {
            MediaBrowserServiceCompat.this.a(this.b, eVar, this.c, this.d);
            return;
        }
        StringBuilder sbA = g9.a("addSubscription for callback that isn't registered id=");
        sbA.append(this.b);
        Log.w("MBServiceCompat", sbA.toString());
    }
}
