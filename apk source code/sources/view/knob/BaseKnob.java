package view.knob;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public abstract class BaseKnob extends View {
    public int A;
    public int B;

    @ColorInt
    public int C;
    public int D;
    public boolean E;
    public boolean F;
    public final RectF a;
    public boolean b;
    public RelativeLayout c;
    public String d;
    public double e;
    public boolean f;
    public int g;
    public int h;
    public int i;
    public boolean j;
    public Paint k;
    public int l;
    public int m;
    public boolean n;
    public int o;
    public Paint p;
    public float q;
    public int r;
    public boolean s;

    @Nullable
    public Drawable t;
    public int u;
    public int v;
    public float w;
    public boolean x;
    public int y;
    public int z;

    public BaseKnob(@NonNull Context context) {
        super(context);
        this.a = new RectF();
        this.e = 0.0d;
        this.f = true;
        this.g = 0;
        this.h = 0;
        this.i = 360;
        this.j = true;
        this.l = 0;
        this.m = 2;
        this.n = true;
        this.o = 0;
        this.q = 0.0f;
        this.r = 4;
        this.s = false;
        this.x = true;
        this.A = 1000;
        this.B = 1000;
        this.D = 0;
        this.E = true;
        this.F = true;
        a(context, null, 0);
    }

    private void setTouchInSide(boolean z) {
        int i;
        Drawable drawable = this.t;
        int intrinsicWidth = 0;
        if (drawable != null) {
            int intrinsicHeight = drawable.getIntrinsicHeight() / 2;
            intrinsicWidth = this.t.getIntrinsicWidth() / 2;
            i = intrinsicHeight;
        } else {
            i = 0;
        }
        this.x = z;
        if (z) {
            this.w = this.l / 4.0f;
        } else {
            this.w = this.l - Math.min(intrinsicWidth, i);
        }
    }

    public double a(float f, float f2) {
        float f3 = f - this.y;
        float f4 = f2 - this.z;
        if (!this.n) {
            f3 = -f3;
        }
        double degrees = Math.toDegrees((Math.atan2(f4, f3) + 1.5707963267948966d) - Math.toRadians(this.g));
        if (degrees < 0.0d) {
            degrees += 360.0d;
        }
        return degrees - this.h;
    }

    public void cancelTouchEvent() {
        this.b = true;
    }

    @Override // android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.t;
        if (drawable != null && drawable.isStateful()) {
            this.t.setState(getDrawableState());
        }
        invalidate();
    }

    public boolean getEnabled() {
        return this.f;
    }

    public String getKnobTag() {
        return this.d;
    }

    public int getMaxProgress() {
        return this.A;
    }

    public int getMaxProgressLimit() {
        return this.B;
    }

    public int getProgress() {
        return this.o;
    }

    @ColorInt
    public int getProgressColor() {
        return this.C;
    }

    @Override // android.view.View
    public void onDraw(@NonNull Canvas canvas) {
        Drawable drawable;
        if (!this.n) {
            canvas.scale(-1.0f, 1.0f, this.a.centerX(), this.a.centerY());
        }
        float f = (this.h - 90) + this.g;
        canvas.drawArc(this.a, f, !this.f ? this.i : (this.B / this.A) * this.i, false, this.k);
        canvas.drawArc(this.a, f, this.q, false, this.p);
        if (this.E) {
            canvas.translate(this.y - this.u, this.z - this.v);
        } else {
            canvas.translate(0.0f, 0.0f);
        }
        if (!this.F || (drawable = this.t) == null) {
            return;
        }
        drawable.draw(canvas);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int defaultSize = View.getDefaultSize(getSuggestedMinimumHeight(), i2);
        int defaultSize2 = View.getDefaultSize(getSuggestedMinimumWidth(), i);
        int iMin = Math.min(defaultSize2, defaultSize);
        this.y = (int) (defaultSize2 * 0.5f);
        this.z = (int) (defaultSize * 0.5f);
        int paddingLeft = iMin - getPaddingLeft();
        int i3 = paddingLeft / 2;
        this.l = i3;
        this.D = i3 - 32;
        float f = (defaultSize / 2) - i3;
        float f2 = (defaultSize2 / 2) - i3;
        float f3 = paddingLeft;
        this.a.set(f2, f, f2 + f3, f3 + f);
        double d = ((int) this.q) + this.h + this.g + 90;
        this.u = (int) (Math.cos(Math.toRadians(d)) * this.D);
        this.v = (int) (Math.sin(Math.toRadians(d)) * this.D);
        setTouchInSide(this.x);
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (!this.f) {
            return false;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        int action = motionEvent.getAction();
        if (action == 0) {
            this.b = false;
            a(motionEvent);
        } else if (action == 1) {
            this.b = false;
            getParent().requestDisallowInterceptTouchEvent(false);
        } else if (action != 2) {
            if (action == 3) {
                this.b = false;
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        } else if (!this.b) {
            a(motionEvent);
        }
        return true;
    }

    public void setArcColor(int i) {
        this.k.setColor(i);
        invalidate();
    }

    public void setBackgroundVisibility(boolean z) {
        RelativeLayout relativeLayout = this.c;
        if (relativeLayout != null) {
            ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.knob_bg_iv);
            if (z) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(4);
            }
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        this.f = z;
        this.F = z;
        this.E = z;
        setBackgroundVisibility(z);
    }

    public void setKnobTag(String str) {
        this.d = str;
    }

    public void setMaxProgress(int i) {
        if (this.B > i) {
            this.B = i;
        }
        this.A = i;
    }

    public void setMaxProgressLimit(int i) {
        int i2 = this.B;
        if (i > this.A) {
            this.A = i;
        }
        this.B = i;
        updateProgress(this.o, false);
        if (this.B != i2) {
            invalidate();
        }
    }

    public void setMoveEnabled(boolean z) {
        this.j = z;
    }

    public void setParent(RelativeLayout relativeLayout) {
        this.c = relativeLayout;
    }

    public void setProgressColor(int i) {
        this.p.setColor(i);
        invalidate();
    }

    public void setThumbEnabled(boolean z) {
        this.E = z;
    }

    public void setThumbVisible(boolean z) {
        this.F = z;
    }

    public void setValueFromUser() {
    }

    public void updateProgress(int i, boolean z) {
        int iMax;
        if (i == -1 || (iMax = Math.max(0, Math.min(i, this.B))) == this.o) {
            return;
        }
        this.o = iMax;
        float f = (iMax / this.A) * this.i;
        this.q = f;
        if (this.E) {
            double d = (int) (this.h + f + this.g + 90.0f);
            this.u = (int) (Math.cos(Math.toRadians(d)) * this.D);
            this.v = (int) (Math.sin(Math.toRadians(d)) * this.D);
        }
        if (z) {
            setValueFromUser();
        }
        invalidate();
    }

    public void a(@NonNull MotionEvent motionEvent) {
        if (77.0f > this.w) {
            return;
        }
        double dA = a(motionEvent.getX(), motionEvent.getY());
        this.e = dA;
        updateProgress(Math.max(0, Math.min((int) Math.round(a() * dA), this.A)), true);
    }

    public final void a(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        float f = context.getResources().getDisplayMetrics().density;
        int color = ContextCompat.getColor(context, R.color.progress_gray);
        this.C = ContextCompat.getColor(context, R.color.default_blue_light);
        this.t = ContextCompat.getDrawable(context, R.drawable.seek_arc_control_selector);
        this.r = (int) (this.r * f);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BaseKnob, i, 0);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(13);
            if (drawable != null) {
                this.t = drawable;
            }
            Drawable drawable2 = this.t;
            if (drawable2 != null) {
                int intrinsicHeight = drawable2.getIntrinsicHeight() / 2;
                int intrinsicWidth = this.t.getIntrinsicWidth() / 2;
                this.t.setBounds(-intrinsicWidth, -intrinsicHeight, intrinsicWidth, intrinsicHeight);
            }
            this.o = typedArrayObtainStyledAttributes.getInteger(5, this.o);
            this.r = (int) typedArrayObtainStyledAttributes.getDimension(7, this.r);
            this.m = (int) typedArrayObtainStyledAttributes.getDimension(1, this.m);
            this.h = typedArrayObtainStyledAttributes.getInt(10, this.h);
            this.i = typedArrayObtainStyledAttributes.getInt(11, this.i);
            this.g = typedArrayObtainStyledAttributes.getInt(8, this.g);
            this.s = typedArrayObtainStyledAttributes.getBoolean(9, this.s);
            this.x = typedArrayObtainStyledAttributes.getBoolean(15, this.x);
            this.n = typedArrayObtainStyledAttributes.getBoolean(2, this.n);
            this.f = typedArrayObtainStyledAttributes.getBoolean(3, this.f);
            color = typedArrayObtainStyledAttributes.getColor(0, color);
            this.C = typedArrayObtainStyledAttributes.getColor(6, this.C);
            typedArrayObtainStyledAttributes.recycle();
        }
        this.o = Math.max(0, Math.min(this.o, this.A));
        int i2 = this.i;
        if (i2 > 360) {
            i2 = 360;
        }
        this.i = i2;
        if (i2 < 0) {
            i2 = 0;
        }
        this.i = i2;
        this.q = (this.o / this.A) * i2;
        int i3 = this.h;
        if (i3 > 360) {
            i3 = 0;
        }
        this.h = i3;
        this.h = i3 >= 0 ? i3 : 0;
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{4.0f, 7.0f}, 0.0f);
        Paint paint = new Paint();
        this.k = paint;
        paint.setColor(color);
        this.k.setAntiAlias(true);
        this.k.setStyle(Paint.Style.STROKE);
        this.k.setStrokeWidth(this.m);
        this.k.setPathEffect(dashPathEffect);
        Paint paint2 = new Paint();
        this.p = paint2;
        paint2.setColor(this.C);
        this.p.setAntiAlias(true);
        this.p.setStyle(Paint.Style.STROKE);
        this.p.setStrokeWidth(this.r);
        this.p.setPathEffect(dashPathEffect);
        if (this.s) {
            this.k.setStrokeCap(Paint.Cap.ROUND);
            this.p.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    public BaseKnob(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new RectF();
        this.e = 0.0d;
        this.f = true;
        this.g = 0;
        this.h = 0;
        this.i = 360;
        this.j = true;
        this.l = 0;
        this.m = 2;
        this.n = true;
        this.o = 0;
        this.q = 0.0f;
        this.r = 4;
        this.s = false;
        this.x = true;
        this.A = 1000;
        this.B = 1000;
        this.D = 0;
        this.E = true;
        this.F = true;
        a(context, attributeSet, R.attr.seekArcStyle);
    }

    public BaseKnob(@NonNull Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new RectF();
        this.e = 0.0d;
        this.f = true;
        this.g = 0;
        this.h = 0;
        this.i = 360;
        this.j = true;
        this.l = 0;
        this.m = 2;
        this.n = true;
        this.o = 0;
        this.q = 0.0f;
        this.r = 4;
        this.s = false;
        this.x = true;
        this.A = 1000;
        this.B = 1000;
        this.D = 0;
        this.E = true;
        this.F = true;
        a(context, attributeSet, i);
    }

    public float a() {
        return this.A / this.i;
    }
}
