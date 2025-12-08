package defpackage;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import defpackage.p4;

/* loaded from: classes.dex */
public class q4<T extends p4> extends MediaBrowser.ConnectionCallback {
    public final T a;

    public q4(T t) {
        this.a = t;
    }

    @Override // android.media.browse.MediaBrowser.ConnectionCallback
    public void onConnected() {
        MediaBrowserCompat.ConnectionCallback.b bVar = (MediaBrowserCompat.ConnectionCallback.b) this.a;
        MediaBrowserCompat.ConnectionCallback.a aVar = MediaBrowserCompat.ConnectionCallback.this.b;
        if (aVar != null) {
            MediaBrowserCompat.c cVar = (MediaBrowserCompat.c) aVar;
            Bundle extras = ((MediaBrowser) cVar.b).getExtras();
            if (extras != null) {
                cVar.f = extras.getInt("extra_service_version", 0);
                IBinder binder = BundleCompat.getBinder(extras, "extra_messenger");
                if (binder != null) {
                    cVar.g = new MediaBrowserCompat.g(binder, cVar.c);
                    Messenger messenger = new Messenger(cVar.d);
                    cVar.h = messenger;
                    cVar.d.a(messenger);
                    try {
                        MediaBrowserCompat.g gVar = cVar.g;
                        Messenger messenger2 = cVar.h;
                        if (gVar == null) {
                            throw null;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putBundle("data_root_hints", gVar.b);
                        gVar.a(6, bundle, messenger2);
                    } catch (RemoteException unused) {
                        Log.i("MediaBrowserCompat", "Remote error registering client messenger.");
                    }
                }
                IMediaSession iMediaSessionAsInterface = IMediaSession.Stub.asInterface(BundleCompat.getBinder(extras, "extra_session_binder"));
                if (iMediaSessionAsInterface != null) {
                    cVar.i = MediaSessionCompat.Token.fromToken(((MediaBrowser) cVar.b).getSessionToken(), iMediaSessionAsInterface);
                }
            }
        }
        MediaBrowserCompat.ConnectionCallback.this.onConnected();
    }

    @Override // android.media.browse.MediaBrowser.ConnectionCallback
    public void onConnectionFailed() {
        MediaBrowserCompat.ConnectionCallback.b bVar = (MediaBrowserCompat.ConnectionCallback.b) this.a;
        MediaBrowserCompat.ConnectionCallback.a aVar = MediaBrowserCompat.ConnectionCallback.this.b;
        if (aVar != null) {
        }
        MediaBrowserCompat.ConnectionCallback.this.onConnectionFailed();
    }

    @Override // android.media.browse.MediaBrowser.ConnectionCallback
    public void onConnectionSuspended() {
        MediaBrowserCompat.ConnectionCallback.b bVar = (MediaBrowserCompat.ConnectionCallback.b) this.a;
        MediaBrowserCompat.ConnectionCallback.a aVar = MediaBrowserCompat.ConnectionCallback.this.b;
        if (aVar != null) {
            MediaBrowserCompat.c cVar = (MediaBrowserCompat.c) aVar;
            cVar.g = null;
            cVar.h = null;
            cVar.i = null;
            cVar.d.a(null);
        }
        MediaBrowserCompat.ConnectionCallback.this.onConnectionSuspended();
    }
}
