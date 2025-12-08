package android.support.design.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.math.MathUtils;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.ViewGroupUtils;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import cooking.Limits;
import defpackage.a2;
import defpackage.b2;
import defpackage.p2;
import defpackage.q1;
import defpackage.q2;

/* loaded from: classes.dex */
public class CollapsingToolbarLayout extends FrameLayout {
    public boolean a;
    public int b;
    public Toolbar c;
    public View d;
    public View e;
    public int f;
    public int g;
    public int h;
    public int i;
    public final Rect j;
    public final a2 k;
    public boolean l;
    public boolean m;
    public Drawable n;
    public Drawable o;
    public int p;
    public boolean q;
    public ValueAnimator r;
    public long s;
    public int t;
    public AppBarLayout.OnOffsetChangedListener u;
    public int v;
    public WindowInsetsCompat w;

    public class a implements OnApplyWindowInsetsListener {
        public a() {
        }

        @Override // android.support.v4.view.OnApplyWindowInsetsListener
        public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
            CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
            if (collapsingToolbarLayout == null) {
                throw null;
            }
            WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows(collapsingToolbarLayout) ? windowInsetsCompat : null;
            if (!ObjectsCompat.equals(collapsingToolbarLayout.w, windowInsetsCompat2)) {
                collapsingToolbarLayout.w = windowInsetsCompat2;
                collapsingToolbarLayout.requestLayout();
            }
            return windowInsetsCompat.consumeSystemWindowInsets();
        }
    }

    public class b implements AppBarLayout.OnOffsetChangedListener {
        public b() {
        }

        @Override // android.support.design.widget.AppBarLayout.OnOffsetChangedListener
        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
            collapsingToolbarLayout.v = i;
            WindowInsetsCompat windowInsetsCompat = collapsingToolbarLayout.w;
            int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
            int childCount = CollapsingToolbarLayout.this.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = CollapsingToolbarLayout.this.getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                q2 q2VarC = CollapsingToolbarLayout.c(childAt);
                int i3 = layoutParams.a;
                if (i3 == 1) {
                    q2VarC.a(MathUtils.clamp(-i, 0, CollapsingToolbarLayout.this.a(childAt)));
                } else if (i3 == 2) {
                    q2VarC.a(Math.round((-i) * layoutParams.b));
                }
            }
            CollapsingToolbarLayout.this.c();
            CollapsingToolbarLayout collapsingToolbarLayout2 = CollapsingToolbarLayout.this;
            if (collapsingToolbarLayout2.o != null && systemWindowInsetTop > 0) {
                ViewCompat.postInvalidateOnAnimation(collapsingToolbarLayout2);
            }
            CollapsingToolbarLayout.this.k.c(Math.abs(i) / ((CollapsingToolbarLayout.this.getHeight() - ViewCompat.getMinimumHeight(CollapsingToolbarLayout.this)) - systemWindowInsetTop));
        }
    }

    public CollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public static q2 c(View view2) {
        q2 q2Var = (q2) view2.getTag(R.id.view_offset_helper);
        if (q2Var != null) {
            return q2Var;
        }
        q2 q2Var2 = new q2(view2);
        view2.setTag(R.id.view_offset_helper, q2Var2);
        return q2Var2;
    }

    public final void a() {
        if (this.a) {
            Toolbar toolbar = null;
            this.c = null;
            this.d = null;
            int i = this.b;
            if (i != -1) {
                Toolbar toolbar2 = (Toolbar) findViewById(i);
                this.c = toolbar2;
                if (toolbar2 != null) {
                    ViewParent parent = toolbar2.getParent();
                    View view2 = toolbar2;
                    while (parent != this && parent != null) {
                        if (parent instanceof View) {
                            view2 = (View) parent;
                        }
                        parent = parent.getParent();
                        view2 = view2;
                    }
                    this.d = view2;
                }
            }
            if (this.c == null) {
                int childCount = getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    View childAt = getChildAt(i2);
                    if (childAt instanceof Toolbar) {
                        toolbar = (Toolbar) childAt;
                        break;
                    }
                    i2++;
                }
                this.c = toolbar;
            }
            b();
            this.a = false;
        }
    }

    public final void b() {
        View view2;
        if (!this.l && (view2 = this.e) != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.e);
            }
        }
        if (!this.l || this.c == null) {
            return;
        }
        if (this.e == null) {
            this.e = new View(getContext());
        }
        if (this.e.getParent() == null) {
            this.c.addView(this.e, -1, -1);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        Drawable drawable;
        super.draw(canvas);
        a();
        if (this.c == null && (drawable = this.n) != null && this.p > 0) {
            drawable.mutate().setAlpha(this.p);
            this.n.draw(canvas);
        }
        if (this.l && this.m) {
            this.k.a(canvas);
        }
        if (this.o == null || this.p <= 0) {
            return;
        }
        WindowInsetsCompat windowInsetsCompat = this.w;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        if (systemWindowInsetTop > 0) {
            this.o.setBounds(0, -this.v, getWidth(), systemWindowInsetTop - this.v);
            this.o.mutate().setAlpha(this.p);
            this.o.draw(canvas);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0018, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x001d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean drawChild(android.graphics.Canvas r5, android.view.View r6, long r7) {
        /*
            r4 = this;
            android.graphics.drawable.Drawable r0 = r4.n
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L2f
            int r0 = r4.p
            if (r0 <= 0) goto L2f
            android.view.View r0 = r4.d
            if (r0 == 0) goto L14
            if (r0 != r4) goto L11
            goto L14
        L11:
            if (r6 != r0) goto L1a
            goto L18
        L14:
            android.support.v7.widget.Toolbar r0 = r4.c
            if (r6 != r0) goto L1a
        L18:
            r0 = r1
            goto L1b
        L1a:
            r0 = r2
        L1b:
            if (r0 == 0) goto L2f
            android.graphics.drawable.Drawable r0 = r4.n
            android.graphics.drawable.Drawable r0 = r0.mutate()
            int r3 = r4.p
            r0.setAlpha(r3)
            android.graphics.drawable.Drawable r0 = r4.n
            r0.draw(r5)
            r0 = r1
            goto L30
        L2f:
            r0 = r2
        L30:
            boolean r5 = super.drawChild(r5, r6, r7)
            if (r5 != 0) goto L3a
            if (r0 == 0) goto L39
            goto L3a
        L39:
            r1 = r2
        L3a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CollapsingToolbarLayout.drawChild(android.graphics.Canvas, android.view.View, long):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.o;
        boolean zA = false;
        if (drawable != null && drawable.isStateful()) {
            zA = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.n;
        if (drawable2 != null && drawable2.isStateful()) {
            zA |= drawable2.setState(drawableState);
        }
        a2 a2Var = this.k;
        if (a2Var != null) {
            zA |= a2Var.a(drawableState);
        }
        if (zA) {
            invalidate();
        }
    }

    public int getCollapsedTitleGravity() {
        return this.k.h;
    }

    @NonNull
    public Typeface getCollapsedTitleTypeface() {
        Typeface typeface = this.k.s;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    @Nullable
    public Drawable getContentScrim() {
        return this.n;
    }

    public int getExpandedTitleGravity() {
        return this.k.g;
    }

    public int getExpandedTitleMarginBottom() {
        return this.i;
    }

    public int getExpandedTitleMarginEnd() {
        return this.h;
    }

    public int getExpandedTitleMarginStart() {
        return this.f;
    }

    public int getExpandedTitleMarginTop() {
        return this.g;
    }

    @NonNull
    public Typeface getExpandedTitleTypeface() {
        Typeface typeface = this.k.t;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    public int getScrimAlpha() {
        return this.p;
    }

    public long getScrimAnimationDuration() {
        return this.s;
    }

    public int getScrimVisibleHeightTrigger() {
        int i = this.t;
        if (i >= 0) {
            return i;
        }
        WindowInsetsCompat windowInsetsCompat = this.w;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        return minimumHeight > 0 ? Math.min((minimumHeight * 2) + systemWindowInsetTop, getHeight()) : getHeight() / 3;
    }

    @Nullable
    public Drawable getStatusBarScrim() {
        return this.o;
    }

    @Nullable
    public CharSequence getTitle() {
        if (this.l) {
            return this.k.v;
        }
        return null;
    }

    public boolean isTitleEnabled() {
        return this.l;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object parent = getParent();
        if (parent instanceof AppBarLayout) {
            ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((View) parent));
            if (this.u == null) {
                this.u = new b();
            }
            ((AppBarLayout) parent).addOnOffsetChangedListener(this.u);
            ViewCompat.requestApplyInsets(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        ViewParent parent = getParent();
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = this.u;
        if (onOffsetChangedListener != null && (parent instanceof AppBarLayout)) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(onOffsetChangedListener);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View view2;
        super.onLayout(z, i, i2, i3, i4);
        WindowInsetsCompat windowInsetsCompat = this.w;
        if (windowInsetsCompat != null) {
            int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                if (!ViewCompat.getFitsSystemWindows(childAt) && childAt.getTop() < systemWindowInsetTop) {
                    ViewCompat.offsetTopAndBottom(childAt, systemWindowInsetTop);
                }
            }
        }
        if (this.l && (view2 = this.e) != null) {
            boolean z2 = ViewCompat.isAttachedToWindow(view2) && this.e.getVisibility() == 0;
            this.m = z2;
            if (z2) {
                boolean z3 = ViewCompat.getLayoutDirection(this) == 1;
                View view3 = this.d;
                if (view3 == null) {
                    view3 = this.c;
                }
                int iA = a(view3);
                ViewGroupUtils.getDescendantRect(this, this.e, this.j);
                a2 a2Var = this.k;
                int titleMarginEnd = this.j.left + (z3 ? this.c.getTitleMarginEnd() : this.c.getTitleMarginStart());
                int titleMarginTop = this.c.getTitleMarginTop() + this.j.top + iA;
                int titleMarginStart = this.j.right + (z3 ? this.c.getTitleMarginStart() : this.c.getTitleMarginEnd());
                int titleMarginBottom = (this.j.bottom + iA) - this.c.getTitleMarginBottom();
                if (!a2.a(a2Var.e, titleMarginEnd, titleMarginTop, titleMarginStart, titleMarginBottom)) {
                    a2Var.e.set(titleMarginEnd, titleMarginTop, titleMarginStart, titleMarginBottom);
                    a2Var.G = true;
                    a2Var.b();
                }
                a2 a2Var2 = this.k;
                int i6 = z3 ? this.h : this.f;
                int i7 = this.j.top + this.g;
                int i8 = (i3 - i) - (z3 ? this.f : this.h);
                int i9 = (i4 - i2) - this.i;
                if (!a2.a(a2Var2.d, i6, i7, i8, i9)) {
                    a2Var2.d.set(i6, i7, i8, i9);
                    a2Var2.G = true;
                    a2Var2.b();
                }
                this.k.c();
            }
        }
        int childCount2 = getChildCount();
        for (int i10 = 0; i10 < childCount2; i10++) {
            q2 q2VarC = c(getChildAt(i10));
            q2VarC.b = q2VarC.a.getTop();
            q2VarC.c = q2VarC.a.getLeft();
            q2VarC.a();
        }
        if (this.c != null) {
            if (this.l && TextUtils.isEmpty(this.k.v)) {
                this.k.a(this.c.getTitle());
            }
            View view4 = this.d;
            if (view4 == null || view4 == this) {
                setMinimumHeight(b(this.c));
            } else {
                setMinimumHeight(b(view4));
            }
        }
        c();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        a();
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        WindowInsetsCompat windowInsetsCompat = this.w;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        if (mode != 0 || systemWindowInsetTop <= 0) {
            return;
        }
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + systemWindowInsetTop, 1073741824));
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Drawable drawable = this.n;
        if (drawable != null) {
            drawable.setBounds(0, 0, i, i2);
        }
    }

    public void setCollapsedTitleGravity(int i) {
        a2 a2Var = this.k;
        if (a2Var.h != i) {
            a2Var.h = i;
            a2Var.c();
        }
    }

    public void setCollapsedTitleTextAppearance(@StyleRes int i) {
        this.k.b(i);
    }

    public void setCollapsedTitleTextColor(@ColorInt int i) {
        setCollapsedTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setCollapsedTitleTypeface(@Nullable Typeface typeface) {
        a2 a2Var = this.k;
        if (a2Var.a(a2Var.s, typeface)) {
            a2Var.s = typeface;
            a2Var.c();
        }
    }

    public void setContentScrim(@Nullable Drawable drawable) {
        Drawable drawable2 = this.n;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
            this.n = drawableMutate;
            if (drawableMutate != null) {
                drawableMutate.setBounds(0, 0, getWidth(), getHeight());
                this.n.setCallback(this);
                this.n.setAlpha(this.p);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setContentScrimColor(@ColorInt int i) {
        setContentScrim(new ColorDrawable(i));
    }

    public void setContentScrimResource(@DrawableRes int i) {
        setContentScrim(ContextCompat.getDrawable(getContext(), i));
    }

    public void setExpandedTitleColor(@ColorInt int i) {
        setExpandedTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setExpandedTitleGravity(int i) {
        a2 a2Var = this.k;
        if (a2Var.g != i) {
            a2Var.g = i;
            a2Var.c();
        }
    }

    public void setExpandedTitleMargin(int i, int i2, int i3, int i4) {
        this.f = i;
        this.g = i2;
        this.h = i3;
        this.i = i4;
        requestLayout();
    }

    public void setExpandedTitleMarginBottom(int i) {
        this.i = i;
        requestLayout();
    }

    public void setExpandedTitleMarginEnd(int i) {
        this.h = i;
        requestLayout();
    }

    public void setExpandedTitleMarginStart(int i) {
        this.f = i;
        requestLayout();
    }

    public void setExpandedTitleMarginTop(int i) {
        this.g = i;
        requestLayout();
    }

    public void setExpandedTitleTextAppearance(@StyleRes int i) {
        this.k.d(i);
    }

    public void setExpandedTitleTextColor(@NonNull ColorStateList colorStateList) {
        a2 a2Var = this.k;
        if (a2Var.k != colorStateList) {
            a2Var.k = colorStateList;
            a2Var.c();
        }
    }

    public void setExpandedTitleTypeface(@Nullable Typeface typeface) {
        a2 a2Var = this.k;
        if (a2Var.a(a2Var.t, typeface)) {
            a2Var.t = typeface;
            a2Var.c();
        }
    }

    public void setScrimAlpha(int i) {
        Toolbar toolbar;
        if (i != this.p) {
            if (this.n != null && (toolbar = this.c) != null) {
                ViewCompat.postInvalidateOnAnimation(toolbar);
            }
            this.p = i;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setScrimAnimationDuration(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) long j) {
        this.s = j;
    }

    public void setScrimVisibleHeightTrigger(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) int i) {
        if (this.t != i) {
            this.t = i;
            c();
        }
    }

    public void setScrimsShown(boolean z) {
        setScrimsShown(z, ViewCompat.isLaidOut(this) && !isInEditMode());
    }

    public void setStatusBarScrim(@Nullable Drawable drawable) {
        Drawable drawable2 = this.o;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
            this.o = drawableMutate;
            if (drawableMutate != null) {
                if (drawableMutate.isStateful()) {
                    this.o.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.o, ViewCompat.getLayoutDirection(this));
                this.o.setVisible(getVisibility() == 0, false);
                this.o.setCallback(this);
                this.o.setAlpha(this.p);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setStatusBarScrimColor(@ColorInt int i) {
        setStatusBarScrim(new ColorDrawable(i));
    }

    public void setStatusBarScrimResource(@DrawableRes int i) {
        setStatusBarScrim(ContextCompat.getDrawable(getContext(), i));
    }

    public void setTitle(@Nullable CharSequence charSequence) {
        this.k.a(charSequence);
    }

    public void setTitleEnabled(boolean z) {
        if (z != this.l) {
            this.l = z;
            b();
            requestLayout();
        }
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.o;
        if (drawable != null && drawable.isVisible() != z) {
            this.o.setVisible(z, false);
        }
        Drawable drawable2 = this.n;
        if (drawable2 == null || drawable2.isVisible() == z) {
            return;
        }
        this.n.setVisible(z, false);
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.n || drawable == this.o;
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setCollapsedTitleTextColor(@NonNull ColorStateList colorStateList) {
        a2 a2Var = this.k;
        if (a2Var.l != colorStateList) {
            a2Var.l = colorStateList;
            a2Var.c();
        }
    }

    public void setScrimsShown(boolean z, boolean z2) {
        if (this.q != z) {
            if (z2) {
                int i = z ? 255 : 0;
                a();
                ValueAnimator valueAnimator = this.r;
                if (valueAnimator == null) {
                    ValueAnimator valueAnimator2 = new ValueAnimator();
                    this.r = valueAnimator2;
                    valueAnimator2.setDuration(this.s);
                    this.r.setInterpolator(i > this.p ? q1.c : q1.d);
                    this.r.addUpdateListener(new b2(this));
                } else if (valueAnimator.isRunning()) {
                    this.r.cancel();
                }
                this.r.setIntValues(this.p, i);
                this.r.start();
            } else {
                setScrimAlpha(z ? 255 : 0);
            }
            this.q = z;
        }
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = true;
        this.j = new Rect();
        this.t = -1;
        p2.a(context);
        a2 a2Var = new a2(this);
        this.k = a2Var;
        a2Var.J = q1.e;
        a2Var.c();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout, i, R.style.Widget_Design_CollapsingToolbar);
        this.k.e(typedArrayObtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691));
        this.k.c(typedArrayObtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
        this.i = dimensionPixelSize;
        this.h = dimensionPixelSize;
        this.g = dimensionPixelSize;
        this.f = dimensionPixelSize;
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
            this.f = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.h = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.g = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.i = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
        }
        this.l = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CollapsingToolbarLayout_titleEnabled, true);
        setTitle(typedArrayObtainStyledAttributes.getText(R.styleable.CollapsingToolbarLayout_title));
        this.k.d(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
        this.k.b(android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
            this.k.d(typedArrayObtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
            this.k.b(typedArrayObtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
        }
        this.t = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
        this.s = typedArrayObtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_scrimAnimationDuration, Limits.TURBO_MAX_SECONDS);
        setContentScrim(typedArrayObtainStyledAttributes.getDrawable(R.styleable.CollapsingToolbarLayout_contentScrim));
        setStatusBarScrim(typedArrayObtainStyledAttributes.getDrawable(R.styleable.CollapsingToolbarLayout_statusBarScrim));
        this.b = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_toolbarId, -1);
        typedArrayObtainStyledAttributes.recycle();
        setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener(this, new a());
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final void c() {
        if (this.n == null && this.o == null) {
            return;
        }
        setScrimsShown(getHeight() + this.v < getScrimVisibleHeightTrigger());
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        public static final int COLLAPSE_MODE_OFF = 0;
        public static final int COLLAPSE_MODE_PARALLAX = 2;
        public static final int COLLAPSE_MODE_PIN = 1;
        public int a;
        public float b;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = 0;
            this.b = 0.5f;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout_Layout);
            this.a = typedArrayObtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
            setParallaxMultiplier(typedArrayObtainStyledAttributes.getFloat(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5f));
            typedArrayObtainStyledAttributes.recycle();
        }

        public int getCollapseMode() {
            return this.a;
        }

        public float getParallaxMultiplier() {
            return this.b;
        }

        public void setCollapseMode(int i) {
            this.a = i;
        }

        public void setParallaxMultiplier(float f) {
            this.b = f;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.a = 0;
            this.b = 0.5f;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2, i3);
            this.a = 0;
            this.b = 0.5f;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
            this.b = 0.5f;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.a = 0;
            this.b = 0.5f;
        }

        @RequiresApi(19)
        public LayoutParams(FrameLayout.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
            this.b = 0.5f;
        }
    }

    public static int b(@NonNull View view2) {
        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            return view2.getHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
        }
        return view2.getHeight();
    }

    public final int a(View view2) {
        return ((getHeight() - c(view2).b) - view2.getHeight()) - ((FrameLayout.LayoutParams) ((LayoutParams) view2.getLayoutParams())).bottomMargin;
    }
}
