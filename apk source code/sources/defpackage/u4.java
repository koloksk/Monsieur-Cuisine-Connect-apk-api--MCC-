package defpackage;

import android.media.browse.MediaBrowser;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import defpackage.t4;

/* loaded from: classes.dex */
public class u4<T extends t4> extends MediaBrowser.ItemCallback {
    public final T a;

    public u4(T t) {
        this.a = t;
    }

    @Override // android.media.browse.MediaBrowser.ItemCallback
    public void onError(@NonNull String str) {
        MediaBrowserCompat.ItemCallback.this.onError(str);
    }

    @Override // android.media.browse.MediaBrowser.ItemCallback
    public void onItemLoaded(MediaBrowser.MediaItem mediaItem) {
        if (mediaItem == null) {
            ((MediaBrowserCompat.ItemCallback.a) this.a).a(null);
            return;
        }
        Parcel parcelObtain = Parcel.obtain();
        mediaItem.writeToParcel(parcelObtain, 0);
        ((MediaBrowserCompat.ItemCallback.a) this.a).a(parcelObtain);
    }
}
