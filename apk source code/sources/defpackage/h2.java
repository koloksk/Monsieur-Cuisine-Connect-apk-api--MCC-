package defpackage;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;

@RequiresApi(14)
/* loaded from: classes.dex */
public class h2 {
    public static final Interpolator o = q1.c;
    public static final int[] p = {R.attr.state_pressed, R.attr.state_enabled};
    public static final int[] q = {R.attr.state_focused, R.attr.state_enabled};
    public static final int[] r = {R.attr.state_enabled};
    public static final int[] s = new int[0];
    public final m2 b;
    public j2 c;
    public float d;
    public Drawable e;
    public Drawable f;
    public y1 g;
    public Drawable h;
    public float i;
    public float j;
    public final VisibilityAwareImageButton k;
    public final k2 l;
    public ViewTreeObserver.OnPreDrawListener n;
    public int a = 0;
    public final Rect m = new Rect();

    public class a extends e {
        public a(h2 h2Var) {
            super(null);
        }

        @Override // h2.e
        public float a() {
            return 0.0f;
        }
    }

    public class b extends e {
        public b() {
            super(null);
        }

        @Override // h2.e
        public float a() {
            h2 h2Var = h2.this;
            return h2Var.i + h2Var.j;
        }
    }

    public interface c {
    }

    public class d extends e {
        public d() {
            super(null);
        }

        @Override // h2.e
        public float a() {
            return h2.this.i;
        }
    }

    public abstract class e extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        public boolean a;
        public float b;
        public float c;

        public /* synthetic */ e(e2 e2Var) {
        }

        public abstract float a();

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            j2 j2Var = h2.this.c;
            j2Var.a(this.c, j2Var.b);
            this.a = false;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (!this.a) {
                this.b = h2.this.c.c;
                this.c = a();
                this.a = true;
            }
            j2 j2Var = h2.this.c;
            float f = this.b;
            j2Var.a((valueAnimator.getAnimatedFraction() * (this.c - f)) + f, j2Var.b);
        }
    }

    public h2(VisibilityAwareImageButton visibilityAwareImageButton, k2 k2Var) {
        this.k = visibilityAwareImageButton;
        this.l = k2Var;
        m2 m2Var = new m2();
        this.b = m2Var;
        m2Var.a(p, a(new b()));
        this.b.a(q, a(new b()));
        this.b.a(r, a(new d()));
        this.b.a(s, a(new a(this)));
        this.d = this.k.getRotation();
    }

    public static ColorStateList b(int i) {
        return new ColorStateList(new int[][]{q, p, new int[0]}, new int[]{i, i, 0});
    }

    public y1 a(int i, ColorStateList colorStateList) {
        Context context = this.k.getContext();
        y1 y1VarD = d();
        int color = ContextCompat.getColor(context, android.support.design.R.color.design_fab_stroke_top_outer_color);
        int color2 = ContextCompat.getColor(context, android.support.design.R.color.design_fab_stroke_top_inner_color);
        int color3 = ContextCompat.getColor(context, android.support.design.R.color.design_fab_stroke_end_inner_color);
        int color4 = ContextCompat.getColor(context, android.support.design.R.color.design_fab_stroke_end_outer_color);
        y1VarD.e = color;
        y1VarD.f = color2;
        y1VarD.g = color3;
        y1VarD.h = color4;
        float f = i;
        if (y1VarD.d != f) {
            y1VarD.d = f;
            y1VarD.a.setStrokeWidth(f * 1.3333f);
            y1VarD.k = true;
            y1VarD.invalidateSelf();
        }
        y1VarD.a(colorStateList);
        return y1VarD;
    }

    public void a(float f, float f2) {
        throw null;
    }

    public void a(int i) {
        throw null;
    }

    public void a(ColorStateList colorStateList, PorterDuff.Mode mode, int i, int i2) {
        throw null;
    }

    public void a(Rect rect) {
        throw null;
    }

    public void a(int[] iArr) {
        throw null;
    }

    public float b() {
        throw null;
    }

    public void b(Rect rect) {
        throw null;
    }

    public void c() {
        throw null;
    }

    public y1 d() {
        throw null;
    }

    public GradientDrawable e() {
        throw null;
    }

    public void f() {
        throw null;
    }

    public boolean g() {
        throw null;
    }

    public final boolean h() {
        return ViewCompat.isLaidOut(this.k) && !this.k.isInEditMode();
    }

    public final void i() {
        Rect rect = this.m;
        a(rect);
        b(rect);
        k2 k2Var = this.l;
        int i = rect.left;
        int i2 = rect.top;
        int i3 = rect.right;
        int i4 = rect.bottom;
        FloatingActionButton.a aVar = (FloatingActionButton.a) k2Var;
        FloatingActionButton.this.k.set(i, i2, i3, i4);
        FloatingActionButton floatingActionButton = FloatingActionButton.this;
        int i5 = floatingActionButton.h;
        floatingActionButton.setPadding(i + i5, i2 + i5, i3 + i5, i4 + i5);
    }

    public GradientDrawable a() {
        GradientDrawable gradientDrawableE = e();
        gradientDrawableE.setShape(1);
        gradientDrawableE.setColor(-1);
        return gradientDrawableE;
    }

    public final ValueAnimator a(@NonNull e eVar) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(o);
        valueAnimator.setDuration(100L);
        valueAnimator.addListener(eVar);
        valueAnimator.addUpdateListener(eVar);
        valueAnimator.setFloatValues(0.0f, 1.0f);
        return valueAnimator;
    }
}
