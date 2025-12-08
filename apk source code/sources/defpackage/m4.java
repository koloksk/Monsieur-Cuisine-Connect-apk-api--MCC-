package defpackage;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.Gravity;

@RequiresApi(21)
/* loaded from: classes.dex */
public class m4 extends RoundedBitmapDrawable {
    public m4(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
    }

    @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
    public void a(int i, int i2, int i3, Rect rect, Rect rect2) {
        Gravity.apply(i, i2, i3, rect, rect2, 0);
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        b();
        outline.setRoundRect(this.h, getCornerRadius());
    }

    @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
    public boolean hasMipMap() {
        Bitmap bitmap = this.a;
        return bitmap != null && bitmap.hasMipMap();
    }

    @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
    public void setMipMap(boolean z) {
        Bitmap bitmap = this.a;
        if (bitmap != null) {
            bitmap.setHasMipMap(z);
            invalidateSelf();
        }
    }
}
