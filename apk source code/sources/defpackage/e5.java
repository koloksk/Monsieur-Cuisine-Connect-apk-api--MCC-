package defpackage;

import android.os.IBinder;
import android.support.v4.media.MediaBrowserServiceCompat;

/* loaded from: classes.dex */
public class e5 implements Runnable {
    public final /* synthetic */ MediaBrowserServiceCompat.k a;
    public final /* synthetic */ MediaBrowserServiceCompat.j b;

    public e5(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar) {
        this.b = jVar;
        this.a = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        IBinder iBinderA = ((MediaBrowserServiceCompat.l) this.a).a();
        MediaBrowserServiceCompat.e eVarRemove = MediaBrowserServiceCompat.this.b.remove(iBinderA);
        if (eVarRemove != null) {
            iBinderA.unlinkToDeath(eVarRemove, 0);
        }
    }
}
