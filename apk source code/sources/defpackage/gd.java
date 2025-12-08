package defpackage;

import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

/* loaded from: classes.dex */
public class gd extends vc<ImageView> {
    public Callback m;

    public gd(Picasso picasso, ImageView imageView, Request request, int i, int i2, int i3, Drawable drawable, String str, Object obj, Callback callback, boolean z) {
        super(picasso, imageView, request, i, i2, i3, drawable, str, obj, z);
        this.m = callback;
    }

    @Override // defpackage.vc
    public void a(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", this));
        }
        ImageView imageView = (ImageView) this.c.get();
        if (imageView == null) {
            return;
        }
        Picasso picasso = this.a;
        kd.a(imageView, picasso.e, bitmap, loadedFrom, this.d, picasso.m);
        Callback callback = this.m;
        if (callback != null) {
            callback.onSuccess();
        }
    }

    @Override // defpackage.vc
    public void a(Exception exc) {
        ImageView imageView = (ImageView) this.c.get();
        if (imageView == null) {
            return;
        }
        Object drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).stop();
        }
        int i = this.g;
        if (i != 0) {
            imageView.setImageResource(i);
        } else {
            Drawable drawable2 = this.h;
            if (drawable2 != null) {
                imageView.setImageDrawable(drawable2);
            }
        }
        Callback callback = this.m;
        if (callback != null) {
            callback.onError(exc);
        }
    }

    @Override // defpackage.vc
    public void a() {
        this.l = true;
        if (this.m != null) {
            this.m = null;
        }
    }
}
