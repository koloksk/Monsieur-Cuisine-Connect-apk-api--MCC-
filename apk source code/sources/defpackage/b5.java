package defpackage;

import android.os.IBinder;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.util.Log;

/* loaded from: classes.dex */
public class b5 implements Runnable {
    public final /* synthetic */ MediaBrowserServiceCompat.k a;
    public final /* synthetic */ String b;
    public final /* synthetic */ IBinder c;
    public final /* synthetic */ MediaBrowserServiceCompat.j d;

    public b5(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, IBinder iBinder) {
        this.d = jVar;
        this.a = kVar;
        this.b = str;
        this.c = iBinder;
    }

    @Override // java.lang.Runnable
    public void run() {
        MediaBrowserServiceCompat.e eVar = MediaBrowserServiceCompat.this.b.get(((MediaBrowserServiceCompat.l) this.a).a());
        if (eVar == null) {
            StringBuilder sbA = g9.a("removeSubscription for callback that isn't registered id=");
            sbA.append(this.b);
            Log.w("MBServiceCompat", sbA.toString());
        } else {
            if (MediaBrowserServiceCompat.this.a(this.b, eVar, this.c)) {
                return;
            }
            StringBuilder sbA2 = g9.a("removeSubscription called for ");
            sbA2.append(this.b);
            sbA2.append(" which is not subscribed");
            Log.w("MBServiceCompat", sbA2.toString());
        }
    }
}
