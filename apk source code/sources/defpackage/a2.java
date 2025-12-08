package defpackage;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.v4.math.MathUtils;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;

/* loaded from: classes.dex */
public final class a2 {
    public static final Paint S = null;
    public Paint A;
    public float B;
    public float C;
    public float D;
    public float E;
    public int[] F;
    public boolean G;
    public Interpolator I;
    public Interpolator J;
    public float K;
    public float L;
    public float M;
    public int N;
    public float O;
    public float P;
    public float Q;
    public int R;
    public final View a;
    public boolean b;
    public float c;
    public ColorStateList k;
    public ColorStateList l;
    public float m;
    public float n;
    public float o;
    public float p;
    public float q;
    public float r;
    public Typeface s;
    public Typeface t;
    public Typeface u;
    public CharSequence v;
    public CharSequence w;
    public boolean x;
    public boolean y;
    public Bitmap z;
    public int g = 16;
    public int h = 16;
    public float i = 15.0f;
    public float j = 15.0f;
    public final TextPaint H = new TextPaint(129);
    public final Rect e = new Rect();
    public final Rect d = new Rect();
    public final RectF f = new RectF();

    static {
        Paint paint = null;
        if (0 != 0) {
            paint.setAntiAlias(true);
            S.setColor(-65281);
        }
    }

    public a2(View view2) {
        this.a = view2;
    }

    public void a(ColorStateList colorStateList) {
        if (this.l != colorStateList) {
            this.l = colorStateList;
            c();
        }
    }

    public void b() {
        this.b = this.e.width() > 0 && this.e.height() > 0 && this.d.width() > 0 && this.d.height() > 0;
    }

    public void c(int i) {
        if (this.h != i) {
            this.h = i;
            c();
        }
    }

    public void d(int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), i, R.styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.k = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor);
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.i = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int) this.i);
        }
        this.R = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
        this.P = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.Q = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.O = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        tintTypedArrayObtainStyledAttributes.recycle();
        this.t = a(i);
        c();
    }

    public void e(int i) {
        if (this.g != i) {
            this.g = i;
            c();
        }
    }

    public void b(int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), i, R.styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.l = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor);
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.j = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int) this.j);
        }
        this.N = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
        this.L = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.M = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.K = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        tintTypedArrayObtainStyledAttributes.recycle();
        this.s = a(i);
        c();
    }

    public final Typeface a(int i) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = this.a.getContext().obtainStyledAttributes(i, new int[]{android.R.attr.fontFamily});
        try {
            String string = typedArrayObtainStyledAttributes.getString(0);
            if (string != null) {
                return Typeface.create(string, 0);
            }
            typedArrayObtainStyledAttributes.recycle();
            return null;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public void c(float f) {
        float fClamp = MathUtils.clamp(f, 0.0f, 1.0f);
        if (fClamp != this.c) {
            this.c = fClamp;
            a(fClamp);
        }
    }

    public final boolean a(int[] iArr) {
        ColorStateList colorStateList;
        this.F = iArr;
        ColorStateList colorStateList2 = this.l;
        if (!((colorStateList2 != null && colorStateList2.isStateful()) || ((colorStateList = this.k) != null && colorStateList.isStateful()))) {
            return false;
        }
        c();
        return true;
    }

    public void c() {
        if (this.a.getHeight() <= 0 || this.a.getWidth() <= 0) {
            return;
        }
        float f = this.E;
        b(this.j);
        CharSequence charSequence = this.w;
        float fMeasureText = charSequence != null ? this.H.measureText(charSequence, 0, charSequence.length()) : 0.0f;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(this.h, this.x ? 1 : 0);
        int i = absoluteGravity & 112;
        if (i == 48) {
            this.n = this.e.top - this.H.ascent();
        } else if (i != 80) {
            this.n = this.e.centerY() + (((this.H.descent() - this.H.ascent()) / 2.0f) - this.H.descent());
        } else {
            this.n = this.e.bottom;
        }
        int i2 = absoluteGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i2 == 1) {
            this.p = this.e.centerX() - (fMeasureText / 2.0f);
        } else if (i2 != 5) {
            this.p = this.e.left;
        } else {
            this.p = this.e.right - fMeasureText;
        }
        b(this.i);
        CharSequence charSequence2 = this.w;
        float fMeasureText2 = charSequence2 != null ? this.H.measureText(charSequence2, 0, charSequence2.length()) : 0.0f;
        int absoluteGravity2 = GravityCompat.getAbsoluteGravity(this.g, this.x ? 1 : 0);
        int i3 = absoluteGravity2 & 112;
        if (i3 == 48) {
            this.m = this.d.top - this.H.ascent();
        } else if (i3 != 80) {
            this.m = this.d.centerY() + (((this.H.descent() - this.H.ascent()) / 2.0f) - this.H.descent());
        } else {
            this.m = this.d.bottom;
        }
        int i4 = absoluteGravity2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i4 == 1) {
            this.o = this.d.centerX() - (fMeasureText2 / 2.0f);
        } else if (i4 != 5) {
            this.o = this.d.left;
        } else {
            this.o = this.d.right - fMeasureText2;
        }
        Bitmap bitmap = this.z;
        if (bitmap != null) {
            bitmap.recycle();
            this.z = null;
        }
        d(f);
        a(this.c);
    }

    @ColorInt
    public final int a() {
        int[] iArr = this.F;
        if (iArr != null) {
            return this.l.getColorForState(iArr, 0);
        }
        return this.l.getDefaultColor();
    }

    public final void d(float f) {
        b(f);
        this.y = false;
        if (0 != 0 && this.z == null && !this.d.isEmpty() && !TextUtils.isEmpty(this.w)) {
            a(0.0f);
            this.B = this.H.ascent();
            this.C = this.H.descent();
            TextPaint textPaint = this.H;
            CharSequence charSequence = this.w;
            int iRound = Math.round(textPaint.measureText(charSequence, 0, charSequence.length()));
            int iRound2 = Math.round(this.C - this.B);
            if (iRound > 0 && iRound2 > 0) {
                this.z = Bitmap.createBitmap(iRound, iRound2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(this.z);
                CharSequence charSequence2 = this.w;
                canvas.drawText(charSequence2, 0, charSequence2.length(), 0.0f, iRound2 - this.H.descent(), this.H);
                if (this.A == null) {
                    this.A = new Paint(3);
                }
            }
        }
        ViewCompat.postInvalidateOnAnimation(this.a);
    }

    public final void a(float f) {
        int defaultColor;
        this.f.left = a(this.d.left, this.e.left, f, this.I);
        this.f.top = a(this.m, this.n, f, this.I);
        this.f.right = a(this.d.right, this.e.right, f, this.I);
        this.f.bottom = a(this.d.bottom, this.e.bottom, f, this.I);
        this.q = a(this.o, this.p, f, this.I);
        this.r = a(this.m, this.n, f, this.I);
        d(a(this.i, this.j, f, this.J));
        ColorStateList colorStateList = this.l;
        ColorStateList colorStateList2 = this.k;
        if (colorStateList != colorStateList2) {
            TextPaint textPaint = this.H;
            int[] iArr = this.F;
            if (iArr != null) {
                defaultColor = colorStateList2.getColorForState(iArr, 0);
            } else {
                defaultColor = colorStateList2.getDefaultColor();
            }
            textPaint.setColor(a(defaultColor, a(), f));
        } else {
            this.H.setColor(a());
        }
        this.H.setShadowLayer(a(this.O, this.K, f, null), a(this.P, this.L, f, null), a(this.Q, this.M, f, null), a(this.R, this.N, f));
        ViewCompat.postInvalidateOnAnimation(this.a);
    }

    public final void b(float f) {
        boolean z;
        float f2;
        if (this.v == null) {
            return;
        }
        float fWidth = this.e.width();
        float fWidth2 = this.d.width();
        if (Math.abs(f - this.j) < 0.001f) {
            f2 = this.j;
            this.D = 1.0f;
            if (a(this.u, this.s)) {
                this.u = this.s;
                z = true;
            } else {
                z = false;
            }
        } else {
            float f3 = this.i;
            if (a(this.u, this.t)) {
                this.u = this.t;
                z = true;
            } else {
                z = false;
            }
            if (Math.abs(f - this.i) < 0.001f) {
                this.D = 1.0f;
            } else {
                this.D = f / this.i;
            }
            float f4 = this.j / this.i;
            fWidth = fWidth2 * f4 > fWidth ? Math.min(fWidth / f4, fWidth2) : fWidth2;
            f2 = f3;
        }
        if (fWidth > 0.0f) {
            z = this.E != f2 || this.G || z;
            this.E = f2;
            this.G = false;
        }
        if (this.w == null || z) {
            this.H.setTextSize(this.E);
            this.H.setTypeface(this.u);
            this.H.setLinearText(this.D != 1.0f);
            CharSequence charSequenceEllipsize = TextUtils.ellipsize(this.v, this.H, fWidth, TextUtils.TruncateAt.END);
            if (TextUtils.equals(charSequenceEllipsize, this.w)) {
                return;
            }
            this.w = charSequenceEllipsize;
            this.x = (ViewCompat.getLayoutDirection(this.a) == 1 ? TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL : TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR).isRtl(charSequenceEllipsize, 0, charSequenceEllipsize.length());
        }
    }

    public void a(Canvas canvas) {
        float fAscent;
        int iSave = canvas.save();
        if (this.w != null && this.b) {
            float f = this.q;
            float f2 = this.r;
            boolean z = this.y && this.z != null;
            if (z) {
                fAscent = this.B * this.D;
            } else {
                fAscent = this.H.ascent() * this.D;
                this.H.descent();
            }
            if (z) {
                f2 += fAscent;
            }
            float f3 = f2;
            float f4 = this.D;
            if (f4 != 1.0f) {
                canvas.scale(f4, f4, f, f3);
            }
            if (z) {
                canvas.drawBitmap(this.z, f, f3, this.A);
            } else {
                CharSequence charSequence = this.w;
                canvas.drawText(charSequence, 0, charSequence.length(), f, f3, this.H);
            }
        }
        canvas.restoreToCount(iSave);
    }

    public final boolean a(Typeface typeface, Typeface typeface2) {
        return !(typeface == null || typeface.equals(typeface2)) || (typeface == null && typeface2 != null);
    }

    public void a(CharSequence charSequence) {
        if (charSequence == null || !charSequence.equals(this.v)) {
            this.v = charSequence;
            this.w = null;
            Bitmap bitmap = this.z;
            if (bitmap != null) {
                bitmap.recycle();
                this.z = null;
            }
            c();
        }
    }

    public static int a(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.argb((int) ((Color.alpha(i2) * f) + (Color.alpha(i) * f2)), (int) ((Color.red(i2) * f) + (Color.red(i) * f2)), (int) ((Color.green(i2) * f) + (Color.green(i) * f2)), (int) ((Color.blue(i2) * f) + (Color.blue(i) * f2)));
    }

    public static float a(float f, float f2, float f3, Interpolator interpolator) {
        if (interpolator != null) {
            f3 = interpolator.getInterpolation(f3);
        }
        return q1.a(f, f2, f3);
    }

    public static boolean a(Rect rect, int i, int i2, int i3, int i4) {
        return rect.left == i && rect.top == i2 && rect.right == i3 && rect.bottom == i4;
    }
}
