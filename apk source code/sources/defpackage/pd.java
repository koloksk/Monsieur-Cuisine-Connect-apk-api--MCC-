package defpackage;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.Target;

/* loaded from: classes.dex */
public final class pd extends vc<Target> {
    public pd(Picasso picasso, Target target, Request request, int i, int i2, Drawable drawable, String str, Object obj, int i3) {
        super(picasso, target, request, i, i2, i3, drawable, str, obj, false);
    }

    @Override // defpackage.vc
    public void a(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", this));
        }
        Target targetB = b();
        if (targetB != null) {
            targetB.onBitmapLoaded(bitmap, loadedFrom);
            if (bitmap.isRecycled()) {
                throw new IllegalStateException("Target callback must not recycle bitmap!");
            }
        }
    }

    @Override // defpackage.vc
    public void a(Exception exc) {
        Target targetB = b();
        if (targetB != null) {
            if (this.g != 0) {
                targetB.onBitmapFailed(exc, this.a.e.getResources().getDrawable(this.g));
            } else {
                targetB.onBitmapFailed(exc, this.h);
            }
        }
    }
}
