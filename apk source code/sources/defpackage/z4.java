package defpackage;

import android.support.v4.media.MediaBrowserServiceCompat;

/* loaded from: classes.dex */
public class z4 implements Runnable {
    public final /* synthetic */ MediaBrowserServiceCompat.k a;
    public final /* synthetic */ MediaBrowserServiceCompat.j b;

    public z4(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar) {
        this.b = jVar;
        this.a = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        MediaBrowserServiceCompat.e eVarRemove = MediaBrowserServiceCompat.this.b.remove(((MediaBrowserServiceCompat.l) this.a).a());
        if (eVarRemove != null) {
            ((MediaBrowserServiceCompat.l) eVarRemove.c).a().unlinkToDeath(eVarRemove, 0);
        }
    }
}
