package android.support.v4.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.util.Preconditions;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import defpackage.e6;
import defpackage.f6;
import defpackage.g9;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class CircularProgressDrawable extends Drawable implements Animatable {
    public static final int DEFAULT = 1;
    public static final int LARGE = 0;
    public static final Interpolator g = new LinearInterpolator();
    public static final Interpolator h = new FastOutSlowInInterpolator();
    public static final int[] i = {ViewCompat.MEASURED_STATE_MASK};
    public final a a;
    public float b;
    public Resources c;
    public Animator d;
    public float e;
    public boolean f;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ProgressDrawableSize {
    }

    public CircularProgressDrawable(@NonNull Context context) {
        this.c = ((Context) Preconditions.checkNotNull(context)).getResources();
        a aVar = new a();
        this.a = aVar;
        aVar.i = i;
        aVar.a(0);
        setStrokeWidth(2.5f);
        a aVar2 = this.a;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new e6(this, aVar2));
        valueAnimatorOfFloat.setRepeatCount(-1);
        valueAnimatorOfFloat.setRepeatMode(1);
        valueAnimatorOfFloat.setInterpolator(g);
        valueAnimatorOfFloat.addListener(new f6(this, aVar2));
        this.d = valueAnimatorOfFloat;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.b, bounds.exactCenterX(), bounds.exactCenterY());
        a aVar = this.a;
        RectF rectF = aVar.a;
        float f = aVar.q;
        float fMin = (aVar.h / 2.0f) + f;
        if (f <= 0.0f) {
            fMin = (Math.min(bounds.width(), bounds.height()) / 2.0f) - Math.max((aVar.r * aVar.p) / 2.0f, aVar.h / 2.0f);
        }
        rectF.set(bounds.centerX() - fMin, bounds.centerY() - fMin, bounds.centerX() + fMin, bounds.centerY() + fMin);
        float f2 = aVar.e;
        float f3 = aVar.g;
        float f4 = (f2 + f3) * 360.0f;
        float f5 = ((aVar.f + f3) * 360.0f) - f4;
        aVar.b.setColor(aVar.u);
        aVar.b.setAlpha(aVar.t);
        float f6 = aVar.h / 2.0f;
        rectF.inset(f6, f6);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, aVar.d);
        float f7 = -f6;
        rectF.inset(f7, f7);
        canvas.drawArc(rectF, f4, f5, false, aVar.b);
        if (aVar.n) {
            Path path = aVar.o;
            if (path == null) {
                Path path2 = new Path();
                aVar.o = path2;
                path2.setFillType(Path.FillType.EVEN_ODD);
            } else {
                path.reset();
            }
            float fMin2 = Math.min(rectF.width(), rectF.height()) / 2.0f;
            float f8 = (aVar.r * aVar.p) / 2.0f;
            aVar.o.moveTo(0.0f, 0.0f);
            aVar.o.lineTo(aVar.r * aVar.p, 0.0f);
            Path path3 = aVar.o;
            float f9 = aVar.r;
            float f10 = aVar.p;
            path3.lineTo((f9 * f10) / 2.0f, aVar.s * f10);
            aVar.o.offset((rectF.centerX() + fMin2) - f8, (aVar.h / 2.0f) + rectF.centerY());
            aVar.o.close();
            aVar.c.setColor(aVar.u);
            aVar.c.setAlpha(aVar.t);
            canvas.save();
            canvas.rotate(f4 + f5, rectF.centerX(), rectF.centerY());
            canvas.drawPath(aVar.o, aVar.c);
            canvas.restore();
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.a.t;
    }

    public boolean getArrowEnabled() {
        return this.a.n;
    }

    public float getArrowHeight() {
        return this.a.s;
    }

    public float getArrowScale() {
        return this.a.p;
    }

    public float getArrowWidth() {
        return this.a.r;
    }

    public int getBackgroundColor() {
        return this.a.d.getColor();
    }

    public float getCenterRadius() {
        return this.a.q;
    }

    @NonNull
    public int[] getColorSchemeColors() {
        return this.a.i;
    }

    public float getEndTrim() {
        return this.a.f;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public float getProgressRotation() {
        return this.a.g;
    }

    public float getStartTrim() {
        return this.a.e;
    }

    @NonNull
    public Paint.Cap getStrokeCap() {
        return this.a.b.getStrokeCap();
    }

    public float getStrokeWidth() {
        return this.a.h;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.d.isRunning();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.a.t = i2;
        invalidateSelf();
    }

    public void setArrowDimensions(float f, float f2) {
        a aVar = this.a;
        aVar.r = (int) f;
        aVar.s = (int) f2;
        invalidateSelf();
    }

    public void setArrowEnabled(boolean z) {
        a aVar = this.a;
        if (aVar.n != z) {
            aVar.n = z;
        }
        invalidateSelf();
    }

    public void setArrowScale(float f) {
        a aVar = this.a;
        if (f != aVar.p) {
            aVar.p = f;
        }
        invalidateSelf();
    }

    public void setBackgroundColor(int i2) {
        this.a.d.setColor(i2);
        invalidateSelf();
    }

    public void setCenterRadius(float f) {
        this.a.q = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.a.b.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setColorSchemeColors(@NonNull int... iArr) {
        a aVar = this.a;
        aVar.i = iArr;
        aVar.a(0);
        this.a.a(0);
        invalidateSelf();
    }

    public void setProgressRotation(float f) {
        this.a.g = f;
        invalidateSelf();
    }

    public void setStartEndTrim(float f, float f2) {
        a aVar = this.a;
        aVar.e = f;
        aVar.f = f2;
        invalidateSelf();
    }

    public void setStrokeCap(@NonNull Paint.Cap cap) {
        this.a.b.setStrokeCap(cap);
        invalidateSelf();
    }

    public void setStrokeWidth(float f) {
        a aVar = this.a;
        aVar.h = f;
        aVar.b.setStrokeWidth(f);
        invalidateSelf();
    }

    public void setStyle(int i2) {
        if (i2 == 0) {
            a(11.0f, 3.0f, 12.0f, 6.0f);
        } else {
            a(7.5f, 2.5f, 10.0f, 5.0f);
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.d.cancel();
        a aVar = this.a;
        float f = aVar.e;
        aVar.k = f;
        float f2 = aVar.f;
        aVar.l = f2;
        aVar.m = aVar.g;
        if (f2 != f) {
            this.f = true;
            this.d.setDuration(666L);
            this.d.start();
            return;
        }
        aVar.a(0);
        a aVar2 = this.a;
        aVar2.k = 0.0f;
        aVar2.l = 0.0f;
        aVar2.m = 0.0f;
        aVar2.e = 0.0f;
        aVar2.f = 0.0f;
        aVar2.g = 0.0f;
        this.d.setDuration(1332L);
        this.d.start();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.d.cancel();
        this.b = 0.0f;
        this.a.a(false);
        this.a.a(0);
        a aVar = this.a;
        aVar.k = 0.0f;
        aVar.l = 0.0f;
        aVar.m = 0.0f;
        aVar.e = 0.0f;
        aVar.f = 0.0f;
        aVar.g = 0.0f;
        invalidateSelf();
    }

    public static class a {
        public int[] i;
        public int j;
        public float k;
        public float l;
        public float m;
        public boolean n;
        public Path o;
        public float q;
        public int r;
        public int s;
        public int u;
        public final RectF a = new RectF();
        public final Paint b = new Paint();
        public final Paint c = new Paint();
        public final Paint d = new Paint();
        public float e = 0.0f;
        public float f = 0.0f;
        public float g = 0.0f;
        public float h = 5.0f;
        public float p = 1.0f;
        public int t = 255;

        public a() {
            this.b.setStrokeCap(Paint.Cap.SQUARE);
            this.b.setAntiAlias(true);
            this.b.setStyle(Paint.Style.STROKE);
            this.c.setStyle(Paint.Style.FILL);
            this.c.setAntiAlias(true);
            this.d.setColor(0);
        }

        public void a(int i) {
            this.j = i;
            this.u = this.i[i];
        }

        public void a(boolean z) {
            if (this.n != z) {
                this.n = z;
            }
        }
    }

    public final void a(float f, float f2, float f3, float f4) {
        a aVar = this.a;
        float f5 = this.c.getDisplayMetrics().density;
        float f6 = f2 * f5;
        aVar.h = f6;
        aVar.b.setStrokeWidth(f6);
        aVar.q = f * f5;
        aVar.a(0);
        aVar.r = (int) (f3 * f5);
        aVar.s = (int) (f4 * f5);
    }

    public static /* synthetic */ void a(CircularProgressDrawable circularProgressDrawable, float f, a aVar, boolean z) {
        float interpolation;
        float interpolation2;
        if (circularProgressDrawable.f) {
            circularProgressDrawable.a(f, aVar);
            float fFloor = (float) (Math.floor(aVar.m / 0.8f) + 1.0d);
            float f2 = aVar.k;
            float f3 = aVar.l;
            aVar.e = (((f3 - 0.01f) - f2) * f) + f2;
            aVar.f = f3;
            float f4 = aVar.m;
            aVar.g = g9.a(fFloor, f4, f, f4);
            return;
        }
        if (f != 1.0f || z) {
            float f5 = aVar.m;
            if (f < 0.5f) {
                interpolation = aVar.k;
                interpolation2 = (h.getInterpolation(f / 0.5f) * 0.79f) + 0.01f + interpolation;
            } else {
                float f6 = aVar.k + 0.79f;
                interpolation = f6 - (((1.0f - h.getInterpolation((f - 0.5f) / 0.5f)) * 0.79f) + 0.01f);
                interpolation2 = f6;
            }
            float f7 = (0.20999998f * f) + f5;
            float f8 = (f + circularProgressDrawable.e) * 216.0f;
            aVar.e = interpolation;
            aVar.f = interpolation2;
            aVar.g = f7;
            circularProgressDrawable.b = f8;
        }
    }

    public final void a(float f, a aVar) {
        if (f > 0.75f) {
            float f2 = (f - 0.75f) / 0.25f;
            int[] iArr = aVar.i;
            int i2 = aVar.j;
            int i3 = iArr[i2];
            int i4 = iArr[(i2 + 1) % iArr.length];
            aVar.u = ((((i3 >> 24) & 255) + ((int) ((((i4 >> 24) & 255) - r1) * f2))) << 24) | ((((i3 >> 16) & 255) + ((int) ((((i4 >> 16) & 255) - r3) * f2))) << 16) | ((((i3 >> 8) & 255) + ((int) ((((i4 >> 8) & 255) - r4) * f2))) << 8) | ((i3 & 255) + ((int) (f2 * ((i4 & 255) - r2))));
            return;
        }
        aVar.u = aVar.i[aVar.j];
    }
}
