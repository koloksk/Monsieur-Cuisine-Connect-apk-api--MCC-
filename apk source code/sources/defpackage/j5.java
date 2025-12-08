package defpackage;

import android.media.browse.MediaBrowser;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class j5<T> {
    public MediaBrowserService.Result a;

    public j5(MediaBrowserService.Result result) {
        this.a = result;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(T t) {
        ArrayList arrayList = null;
        if (!(t instanceof List)) {
            if (!(t instanceof Parcel)) {
                this.a.sendResult(null);
                return;
            }
            Parcel parcel = (Parcel) t;
            parcel.setDataPosition(0);
            this.a.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
            parcel.recycle();
            return;
        }
        MediaBrowserService.Result result = this.a;
        List<Parcel> list = (List) t;
        if (list != null) {
            arrayList = new ArrayList();
            for (Parcel parcel2 : list) {
                parcel2.setDataPosition(0);
                arrayList.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel2));
                parcel2.recycle();
            }
        }
        result.sendResult(arrayList);
    }
}
