package defpackage;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.util.Pair;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class x4 implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ Bundle b;
    public final /* synthetic */ MediaBrowserServiceCompat.g c;

    public x4(MediaBrowserServiceCompat.g gVar, String str, Bundle bundle) {
        this.c = gVar;
        this.a = str;
        this.b = bundle;
    }

    @Override // java.lang.Runnable
    public void run() {
        Iterator<IBinder> it = MediaBrowserServiceCompat.this.b.keySet().iterator();
        while (it.hasNext()) {
            MediaBrowserServiceCompat.e eVar = MediaBrowserServiceCompat.this.b.get(it.next());
            List<Pair<IBinder, Bundle>> list = eVar.e.get(this.a);
            if (list != null) {
                for (Pair<IBinder, Bundle> pair : list) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(this.b, pair.second)) {
                        MediaBrowserServiceCompat.this.a(this.a, eVar, pair.second);
                    }
                }
            }
        }
    }
}
