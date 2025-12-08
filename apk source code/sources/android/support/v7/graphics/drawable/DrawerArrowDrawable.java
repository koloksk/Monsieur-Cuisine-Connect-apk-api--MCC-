package android.support.v7.graphics.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.appcompat.R;
import defpackage.g9;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class DrawerArrowDrawable extends Drawable {
    public static final int ARROW_DIRECTION_END = 3;
    public static final int ARROW_DIRECTION_LEFT = 0;
    public static final int ARROW_DIRECTION_RIGHT = 1;
    public static final int ARROW_DIRECTION_START = 2;
    public static final float m = (float) Math.toRadians(45.0d);
    public float b;
    public float c;
    public float d;
    public float e;
    public boolean f;
    public final int h;
    public float j;
    public float k;
    public final Paint a = new Paint();
    public final Path g = new Path();
    public boolean i = false;
    public int l = 2;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ArrowDirection {
    }

    public DrawerArrowDrawable(Context context) {
        this.a.setStyle(Paint.Style.STROKE);
        this.a.setStrokeJoin(Paint.Join.MITER);
        this.a.setStrokeCap(Paint.Cap.BUTT);
        this.a.setAntiAlias(true);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle);
        setColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DrawerArrowToggle_color, 0));
        setBarThickness(typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_thickness, 0.0f));
        setSpinEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true));
        setGapSize(Math.round(typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0f)));
        this.h = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, 0);
        this.c = Math.round(typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_barLength, 0.0f));
        this.b = Math.round(typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_arrowHeadLength, 0.0f));
        this.d = typedArrayObtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_arrowShaftLength, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
    }

    public static float a(float f, float f2, float f3) {
        return g9.a(f2, f, f3, f);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int i = this.l;
        boolean z = false;
        if (i != 0 && (i == 1 || (i == 3 ? DrawableCompat.getLayoutDirection(this) == 0 : DrawableCompat.getLayoutDirection(this) == 1))) {
            z = true;
        }
        float f = this.b;
        float fA = a(this.c, (float) Math.sqrt(f * f * 2.0f), this.j);
        float fA2 = a(this.c, this.d, this.j);
        float fRound = Math.round(a(0.0f, this.k, this.j));
        float fA3 = a(0.0f, m, this.j);
        float fA4 = a(z ? 0.0f : -180.0f, z ? 180.0f : 0.0f, this.j);
        double d = fA;
        double d2 = fA3;
        boolean z2 = z;
        float fRound2 = Math.round(Math.cos(d2) * d);
        float fRound3 = Math.round(Math.sin(d2) * d);
        this.g.rewind();
        float fA5 = a(this.a.getStrokeWidth() + this.e, -this.k, this.j);
        float f2 = (-fA2) / 2.0f;
        this.g.moveTo(f2 + fRound, 0.0f);
        this.g.rLineTo(fA2 - (fRound * 2.0f), 0.0f);
        this.g.moveTo(f2, fA5);
        this.g.rLineTo(fRound2, fRound3);
        this.g.moveTo(f2, -fA5);
        this.g.rLineTo(fRound2, -fRound3);
        this.g.close();
        canvas.save();
        float strokeWidth = this.a.getStrokeWidth();
        float fHeight = bounds.height() - (3.0f * strokeWidth);
        canvas.translate(bounds.centerX(), (strokeWidth * 1.5f) + this.e + ((((int) (fHeight - (2.0f * r5))) / 4) * 2));
        if (this.f) {
            canvas.rotate(fA4 * (this.i ^ z2 ? -1 : 1));
        } else if (z2) {
            canvas.rotate(180.0f);
        }
        canvas.drawPath(this.g, this.a);
        canvas.restore();
    }

    public float getArrowHeadLength() {
        return this.b;
    }

    public float getArrowShaftLength() {
        return this.d;
    }

    public float getBarLength() {
        return this.c;
    }

    public float getBarThickness() {
        return this.a.getStrokeWidth();
    }

    @ColorInt
    public int getColor() {
        return this.a.getColor();
    }

    public int getDirection() {
        return this.l;
    }

    public float getGapSize() {
        return this.e;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.h;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.h;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public final Paint getPaint() {
        return this.a;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.j;
    }

    public boolean isSpinEnabled() {
        return this.f;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.a.getAlpha()) {
            this.a.setAlpha(i);
            invalidateSelf();
        }
    }

    public void setArrowHeadLength(float f) {
        if (this.b != f) {
            this.b = f;
            invalidateSelf();
        }
    }

    public void setArrowShaftLength(float f) {
        if (this.d != f) {
            this.d = f;
            invalidateSelf();
        }
    }

    public void setBarLength(float f) {
        if (this.c != f) {
            this.c = f;
            invalidateSelf();
        }
    }

    public void setBarThickness(float f) {
        if (this.a.getStrokeWidth() != f) {
            this.a.setStrokeWidth(f);
            this.k = (float) (Math.cos(m) * (f / 2.0f));
            invalidateSelf();
        }
    }

    public void setColor(@ColorInt int i) {
        if (i != this.a.getColor()) {
            this.a.setColor(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setDirection(int i) {
        if (i != this.l) {
            this.l = i;
            invalidateSelf();
        }
    }

    public void setGapSize(float f) {
        if (f != this.e) {
            this.e = f;
            invalidateSelf();
        }
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (this.j != f) {
            this.j = f;
            invalidateSelf();
        }
    }

    public void setSpinEnabled(boolean z) {
        if (this.f != z) {
            this.f = z;
            invalidateSelf();
        }
    }

    public void setVerticalMirror(boolean z) {
        if (this.i != z) {
            this.i = z;
            invalidateSelf();
        }
    }
}
