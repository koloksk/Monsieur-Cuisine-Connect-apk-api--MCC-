package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import com.journeyapps.barcodescanner.ViewfinderView;

@RequiresApi(9)
/* loaded from: classes.dex */
public abstract class RoundedBitmapDrawable extends Drawable {
    public final Bitmap a;
    public int b;
    public final BitmapShader e;
    public float g;
    public boolean k;
    public int l;
    public int m;
    public int c = 119;
    public final Paint d = new Paint(3);
    public final Matrix f = new Matrix();
    public final Rect h = new Rect();
    public final RectF i = new RectF();
    public boolean j = true;

    public RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        this.b = ViewfinderView.CURRENT_POINT_OPACITY;
        if (resources != null) {
            this.b = resources.getDisplayMetrics().densityDpi;
        }
        this.a = bitmap;
        if (bitmap == null) {
            this.m = -1;
            this.l = -1;
            this.e = null;
        } else {
            a();
            Bitmap bitmap2 = this.a;
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            this.e = new BitmapShader(bitmap2, tileMode, tileMode);
        }
    }

    public final void a() {
        this.l = this.a.getScaledWidth(this.b);
        this.m = this.a.getScaledHeight(this.b);
    }

    public void b() {
        if (this.j) {
            if (this.k) {
                int iMin = Math.min(this.l, this.m);
                a(this.c, iMin, iMin, getBounds(), this.h);
                int iMin2 = Math.min(this.h.width(), this.h.height());
                this.h.inset(Math.max(0, (this.h.width() - iMin2) / 2), Math.max(0, (this.h.height() - iMin2) / 2));
                this.g = iMin2 * 0.5f;
            } else {
                a(this.c, this.l, this.m, getBounds(), this.h);
            }
            this.i.set(this.h);
            if (this.e != null) {
                Matrix matrix = this.f;
                RectF rectF = this.i;
                matrix.setTranslate(rectF.left, rectF.top);
                this.f.preScale(this.i.width() / this.a.getWidth(), this.i.height() / this.a.getHeight());
                this.e.setLocalMatrix(this.f);
                this.d.setShader(this.e);
            }
            this.j = false;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = this.a;
        if (bitmap == null) {
            return;
        }
        b();
        if (this.d.getShader() == null) {
            canvas.drawBitmap(bitmap, (Rect) null, this.h, this.d);
            return;
        }
        RectF rectF = this.i;
        float f = this.g;
        canvas.drawRoundRect(rectF, f, f, this.d);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.d.getAlpha();
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.a;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.d.getColorFilter();
    }

    public float getCornerRadius() {
        return this.g;
    }

    public int getGravity() {
        return this.c;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.m;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.l;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Bitmap bitmap;
        if (this.c == 119 && !this.k && (bitmap = this.a) != null && !bitmap.hasAlpha() && this.d.getAlpha() >= 255) {
            if (!(this.g > 0.05f)) {
                return -1;
            }
        }
        return -3;
    }

    @NonNull
    public final Paint getPaint() {
        return this.d;
    }

    public boolean hasAntiAlias() {
        return this.d.isAntiAlias();
    }

    public boolean hasMipMap() {
        throw new UnsupportedOperationException();
    }

    public boolean isCircular() {
        return this.k;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.k) {
            this.g = Math.min(this.m, this.l) / 2;
        }
        this.j = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.d.getAlpha()) {
            this.d.setAlpha(i);
            invalidateSelf();
        }
    }

    public void setAntiAlias(boolean z) {
        this.d.setAntiAlias(z);
        invalidateSelf();
    }

    public void setCircular(boolean z) {
        this.k = z;
        this.j = true;
        if (!z) {
            setCornerRadius(0.0f);
            return;
        }
        this.g = Math.min(this.m, this.l) / 2;
        this.d.setShader(this.e);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.d.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setCornerRadius(float f) {
        if (this.g == f) {
            return;
        }
        this.k = false;
        if (f > 0.05f) {
            this.d.setShader(this.e);
        } else {
            this.d.setShader(null);
        }
        this.g = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.d.setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.d.setFilterBitmap(z);
        invalidateSelf();
    }

    public void setGravity(int i) {
        if (this.c != i) {
            this.c = i;
            this.j = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean z) {
        throw new UnsupportedOperationException();
    }

    public void setTargetDensity(@NonNull Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(@NonNull DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    public void a(int i, int i2, int i3, Rect rect, Rect rect2) {
        throw new UnsupportedOperationException();
    }

    public void setTargetDensity(int i) {
        if (this.b != i) {
            if (i == 0) {
                i = ViewfinderView.CURRENT_POINT_OPACITY;
            }
            this.b = i;
            if (this.a != null) {
                a();
            }
            invalidateSelf();
        }
    }
}
