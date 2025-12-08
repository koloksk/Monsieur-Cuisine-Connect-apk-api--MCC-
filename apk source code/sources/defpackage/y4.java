package defpackage;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaBrowserServiceCompat.e;
import android.util.Log;

/* loaded from: classes.dex */
public class y4 implements Runnable {
    public final /* synthetic */ MediaBrowserServiceCompat.k a;
    public final /* synthetic */ String b;
    public final /* synthetic */ Bundle c;
    public final /* synthetic */ int d;
    public final /* synthetic */ MediaBrowserServiceCompat.j e;

    public y4(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, Bundle bundle, int i) {
        this.e = jVar;
        this.a = kVar;
        this.b = str;
        this.c = bundle;
        this.d = i;
    }

    @Override // java.lang.Runnable
    public void run() throws RemoteException {
        IBinder iBinderA = ((MediaBrowserServiceCompat.l) this.a).a();
        MediaBrowserServiceCompat.this.b.remove(iBinderA);
        MediaBrowserServiceCompat.e eVar = MediaBrowserServiceCompat.this.new e();
        String str = this.b;
        eVar.a = str;
        Bundle bundle = this.c;
        eVar.b = bundle;
        eVar.c = this.a;
        MediaBrowserServiceCompat.BrowserRoot browserRootOnGetRoot = MediaBrowserServiceCompat.this.onGetRoot(str, this.d, bundle);
        eVar.d = browserRootOnGetRoot;
        if (browserRootOnGetRoot == null) {
            StringBuilder sbA = g9.a("No root for client ");
            sbA.append(this.b);
            sbA.append(" from service ");
            sbA.append(y4.class.getName());
            Log.i("MBServiceCompat", sbA.toString());
            try {
                ((MediaBrowserServiceCompat.l) this.a).a(2, null);
                return;
            } catch (RemoteException unused) {
                StringBuilder sbA2 = g9.a("Calling onConnectFailed() failed. Ignoring. pkg=");
                sbA2.append(this.b);
                Log.w("MBServiceCompat", sbA2.toString());
                return;
            }
        }
        try {
            MediaBrowserServiceCompat.this.b.put(iBinderA, eVar);
            iBinderA.linkToDeath(eVar, 0);
            if (MediaBrowserServiceCompat.this.e != null) {
                ((MediaBrowserServiceCompat.l) this.a).a(eVar.d.getRootId(), MediaBrowserServiceCompat.this.e, eVar.d.getExtras());
            }
        } catch (RemoteException unused2) {
            StringBuilder sbA3 = g9.a("Calling onConnect() failed. Dropping client. pkg=");
            sbA3.append(this.b);
            Log.w("MBServiceCompat", sbA3.toString());
            MediaBrowserServiceCompat.this.b.remove(iBinderA);
        }
    }
}
