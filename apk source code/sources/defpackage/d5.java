package defpackage;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaBrowserServiceCompat.e;
import android.util.Log;

/* loaded from: classes.dex */
public class d5 implements Runnable {
    public final /* synthetic */ MediaBrowserServiceCompat.k a;
    public final /* synthetic */ Bundle b;
    public final /* synthetic */ MediaBrowserServiceCompat.j c;

    public d5(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, Bundle bundle) {
        this.c = jVar;
        this.a = kVar;
        this.b = bundle;
    }

    @Override // java.lang.Runnable
    public void run() throws RemoteException {
        IBinder iBinderA = ((MediaBrowserServiceCompat.l) this.a).a();
        MediaBrowserServiceCompat.this.b.remove(iBinderA);
        MediaBrowserServiceCompat.e eVar = MediaBrowserServiceCompat.this.new e();
        eVar.c = this.a;
        eVar.b = this.b;
        MediaBrowserServiceCompat.this.b.put(iBinderA, eVar);
        try {
            iBinderA.linkToDeath(eVar, 0);
        } catch (RemoteException unused) {
            Log.w("MBServiceCompat", "IBinder is already dead.");
        }
    }
}
