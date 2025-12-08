package defpackage;

import android.graphics.Bitmap;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

/* loaded from: classes.dex */
public class dd extends vc<Object> {
    public final Object m;
    public Callback n;

    public dd(Picasso picasso, Request request, int i, int i2, Object obj, String str, Callback callback) {
        super(picasso, null, request, i, i2, 0, null, str, obj, false);
        this.m = new Object();
        this.n = callback;
    }

    @Override // defpackage.vc
    public void a(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        Callback callback = this.n;
        if (callback != null) {
            callback.onSuccess();
        }
    }

    @Override // defpackage.vc
    public Object b() {
        return this.m;
    }

    @Override // defpackage.vc
    public void a(Exception exc) {
        Callback callback = this.n;
        if (callback != null) {
            callback.onError(exc);
        }
    }

    @Override // defpackage.vc
    public void a() {
        this.l = true;
        this.n = null;
    }
}
