package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewGroupUtils;
import android.support.v7.widget.AppCompatImageHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import defpackage.c2;
import defpackage.d2;
import defpackage.e2;
import defpackage.f2;
import defpackage.g2;
import defpackage.h2;
import defpackage.i2;
import defpackage.k2;
import defpackage.p2;
import defpackage.q1;
import defpackage.y1;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@CoordinatorLayout.DefaultBehavior(Behavior.class)
/* loaded from: classes.dex */
public class FloatingActionButton extends VisibilityAwareImageButton {
    public static final int NO_CUSTOM_SIZE = 0;
    public static final int SIZE_AUTO = -1;
    public static final int SIZE_MINI = 1;
    public static final int SIZE_NORMAL = 0;
    public ColorStateList b;
    public PorterDuff.Mode c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public boolean j;
    public final Rect k;
    public final Rect l;
    public AppCompatImageHelper m;
    public h2 n;

    public static class Behavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
        public Rect a;
        public boolean b;

        public Behavior() {
            this.b = true;
        }

        public static boolean a(@NonNull View view2) {
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                return ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior() instanceof BottomSheetBehavior;
            }
            return false;
        }

        public final boolean b(View view2, FloatingActionButton floatingActionButton) {
            if (!a(view2, floatingActionButton)) {
                return false;
            }
            if (view2.getTop() < (floatingActionButton.getHeight() / 2) + ((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams())).topMargin) {
                floatingActionButton.a((OnVisibilityChangedListener) null, false);
                return true;
            }
            floatingActionButton.b(null, false);
            return true;
        }

        public boolean isAutoHideEnabled() {
            return this.b;
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges == 0) {
                layoutParams.dodgeInsetEdges = 80;
            }
        }

        public void setAutoHideEnabled(boolean z) {
            this.b = z;
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton floatingActionButton, @NonNull Rect rect) {
            Rect rect2 = floatingActionButton.k;
            rect.set(floatingActionButton.getLeft() + rect2.left, floatingActionButton.getTop() + rect2.top, floatingActionButton.getRight() - rect2.right, floatingActionButton.getBottom() - rect2.bottom);
            return true;
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view2) {
            if (view2 instanceof AppBarLayout) {
                a(coordinatorLayout, (AppBarLayout) view2, floatingActionButton);
                return false;
            }
            if (!a(view2)) {
                return false;
            }
            b(view2, floatingActionButton);
            return false;
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int i) {
            List<View> dependencies = coordinatorLayout.getDependencies(floatingActionButton);
            int size = dependencies.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                View view2 = dependencies.get(i3);
                if (!(view2 instanceof AppBarLayout)) {
                    if (a(view2) && b(view2, floatingActionButton)) {
                        break;
                    }
                } else {
                    if (a(coordinatorLayout, (AppBarLayout) view2, floatingActionButton)) {
                        break;
                    }
                }
            }
            coordinatorLayout.onLayoutChild(floatingActionButton, i);
            Rect rect = floatingActionButton.k;
            if (rect == null || rect.centerX() <= 0 || rect.centerY() <= 0) {
                return true;
            }
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
            int i4 = floatingActionButton.getRight() >= coordinatorLayout.getWidth() - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin ? rect.right : floatingActionButton.getLeft() <= ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin ? -rect.left : 0;
            if (floatingActionButton.getBottom() >= coordinatorLayout.getHeight() - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) {
                i2 = rect.bottom;
            } else if (floatingActionButton.getTop() <= ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) {
                i2 = -rect.top;
            }
            if (i2 != 0) {
                ViewCompat.offsetTopAndBottom(floatingActionButton, i2);
            }
            if (i4 == 0) {
                return true;
            }
            ViewCompat.offsetLeftAndRight(floatingActionButton, i4);
            return true;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton_Behavior_Layout);
            this.b = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
            typedArrayObtainStyledAttributes.recycle();
        }

        public final boolean a(View view2, FloatingActionButton floatingActionButton) {
            return this.b && ((CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams()).getAnchorId() == view2.getId() && floatingActionButton.getUserSetVisibility() == 0;
        }

        public final boolean a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (!a(appBarLayout, floatingActionButton)) {
                return false;
            }
            if (this.a == null) {
                this.a = new Rect();
            }
            Rect rect = this.a;
            ViewGroupUtils.getDescendantRect(coordinatorLayout, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.a((OnVisibilityChangedListener) null, false);
                return true;
            }
            floatingActionButton.b(null, false);
            return true;
        }
    }

    public static abstract class OnVisibilityChangedListener {
        public void onHidden(FloatingActionButton floatingActionButton) {
        }

        public void onShown(FloatingActionButton floatingActionButton) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Size {
    }

    public class a implements k2 {
        public a() {
        }
    }

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    private h2 getImpl() {
        if (this.n == null) {
            this.n = new i2(this, new a());
        }
        return this.n;
    }

    public void b(OnVisibilityChangedListener onVisibilityChangedListener, boolean z) {
        h2 impl = getImpl();
        d2 d2Var = onVisibilityChangedListener == null ? null : new d2(this, onVisibilityChangedListener);
        boolean z2 = true;
        if (impl.k.getVisibility() == 0 ? impl.a == 1 : impl.a != 2) {
            z2 = false;
        }
        if (z2) {
            return;
        }
        impl.k.animate().cancel();
        if (impl.h()) {
            impl.a = 2;
            if (impl.k.getVisibility() != 0) {
                impl.k.setAlpha(0.0f);
                impl.k.setScaleY(0.0f);
                impl.k.setScaleX(0.0f);
            }
            impl.k.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).setInterpolator(q1.d).setListener(new f2(impl, z, d2Var));
            return;
        }
        impl.k.a(0, z);
        impl.k.setAlpha(1.0f);
        impl.k.setScaleY(1.0f);
        impl.k.setScaleX(1.0f);
        if (d2Var != null) {
            d2Var.a.onShown(d2Var.b);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().a(getDrawableState());
    }

    @Override // android.view.View
    @Nullable
    public ColorStateList getBackgroundTintList() {
        return this.b;
    }

    @Override // android.view.View
    @Nullable
    public PorterDuff.Mode getBackgroundTintMode() {
        return this.c;
    }

    public float getCompatElevation() {
        return getImpl().b();
    }

    @NonNull
    public Drawable getContentBackground() {
        return getImpl().h;
    }

    public boolean getContentRect(@NonNull Rect rect) {
        if (!ViewCompat.isLaidOut(this)) {
            return false;
        }
        rect.set(0, 0, getWidth(), getHeight());
        int i = rect.left;
        Rect rect2 = this.k;
        rect.left = i + rect2.left;
        rect.top += rect2.top;
        rect.right -= rect2.right;
        rect.bottom -= rect2.bottom;
        return true;
    }

    public int getCustomSize() {
        return this.g;
    }

    @ColorInt
    public int getRippleColor() {
        return this.e;
    }

    public int getSize() {
        return this.f;
    }

    public int getSizeDimension() {
        return a(this.f);
    }

    public boolean getUseCompatPadding() {
        return this.j;
    }

    public void hide() {
        hide(null);
    }

    @Override // android.widget.ImageView, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().c();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        h2 impl = getImpl();
        if (impl.g()) {
            if (impl.n == null) {
                impl.n = new g2(impl);
            }
            impl.k.getViewTreeObserver().addOnPreDrawListener(impl.n);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        h2 impl = getImpl();
        if (impl.n != null) {
            impl.k.getViewTreeObserver().removeOnPreDrawListener(impl.n);
            impl.n = null;
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i, int i2) {
        int sizeDimension = getSizeDimension();
        this.h = (sizeDimension - this.i) / 2;
        getImpl().i();
        int iMin = Math.min(a(sizeDimension, i), a(sizeDimension, i2));
        Rect rect = this.k;
        setMeasuredDimension(rect.left + iMin + rect.right, iMin + rect.top + rect.bottom);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && getContentRect(this.l) && !this.l.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    @Override // android.view.View
    public void setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (this.b != colorStateList) {
            this.b = colorStateList;
            h2 impl = getImpl();
            Drawable drawable = impl.e;
            if (drawable != null) {
                DrawableCompat.setTintList(drawable, colorStateList);
            }
            y1 y1Var = impl.g;
            if (y1Var != null) {
                y1Var.a(colorStateList);
            }
        }
    }

    @Override // android.view.View
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.c != mode) {
            this.c = mode;
            Drawable drawable = getImpl().e;
            if (drawable != null) {
                DrawableCompat.setTintMode(drawable, mode);
            }
        }
    }

    public void setCompatElevation(float f) {
        h2 impl = getImpl();
        if (impl.i != f) {
            impl.i = f;
            impl.a(f, impl.j);
        }
    }

    public void setCustomSize(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Custom size should be non-negative.");
        }
        this.g = i;
    }

    @Override // android.widget.ImageView
    public void setImageResource(@DrawableRes int i) {
        this.m.setImageResource(i);
    }

    public void setRippleColor(@ColorInt int i) {
        if (this.e != i) {
            this.e = i;
            getImpl().a(i);
        }
    }

    public void setSize(int i) {
        if (i != this.f) {
            this.f = i;
            requestLayout();
        }
    }

    public void setUseCompatPadding(boolean z) {
        if (this.j != z) {
            this.j = z;
            getImpl().f();
        }
    }

    @Override // android.support.design.widget.VisibilityAwareImageButton, android.widget.ImageView, android.view.View
    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    public void show() {
        show(null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void a(@Nullable OnVisibilityChangedListener onVisibilityChangedListener, boolean z) {
        h2 impl = getImpl();
        d2 d2Var = onVisibilityChangedListener == null ? null : new d2(this, onVisibilityChangedListener);
        boolean z2 = false;
        if (impl.k.getVisibility() != 0 ? impl.a != 2 : impl.a == 1) {
            z2 = true;
        }
        if (z2) {
            return;
        }
        impl.k.animate().cancel();
        if (impl.h()) {
            impl.a = 1;
            impl.k.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(200L).setInterpolator(q1.c).setListener(new e2(impl, z, d2Var));
        } else {
            impl.k.a(z ? 8 : 4, z);
            if (d2Var != null) {
                d2Var.a.onHidden(d2Var.b);
            }
        }
    }

    public void hide(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        a(onVisibilityChangedListener, true);
    }

    public void show(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        b(onVisibilityChangedListener, true);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.k = new Rect();
        this.l = new Rect();
        p2.a(context);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, i, R.style.Widget_Design_FloatingActionButton);
        this.b = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.FloatingActionButton_backgroundTint);
        this.c = c2.a(typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionButton_backgroundTintMode, -1), (PorterDuff.Mode) null);
        this.e = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionButton_rippleColor, 0);
        this.f = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionButton_fabSize, -1);
        this.g = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionButton_fabCustomSize, 0);
        this.d = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionButton_borderWidth, 0);
        float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.FloatingActionButton_elevation, 0.0f);
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(R.styleable.FloatingActionButton_pressedTranslationZ, 0.0f);
        this.j = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FloatingActionButton_useCompatPadding, false);
        typedArrayObtainStyledAttributes.recycle();
        AppCompatImageHelper appCompatImageHelper = new AppCompatImageHelper(this);
        this.m = appCompatImageHelper;
        appCompatImageHelper.loadFromAttributes(attributeSet, i);
        this.i = (int) getResources().getDimension(R.dimen.design_fab_image_size);
        getImpl().a(this.b, this.c, this.e, this.d);
        h2 impl = getImpl();
        if (impl.i != dimension) {
            impl.i = dimension;
            impl.a(dimension, impl.j);
        }
        h2 impl2 = getImpl();
        if (impl2.j != dimension2) {
            impl2.j = dimension2;
            impl2.a(impl2.i, dimension2);
        }
    }

    public final int a(int i) {
        Resources resources = getResources();
        int i2 = this.g;
        if (i2 != 0) {
            return i2;
        }
        if (i != -1) {
            if (i != 1) {
                return resources.getDimensionPixelSize(R.dimen.design_fab_size_normal);
            }
            return resources.getDimensionPixelSize(R.dimen.design_fab_size_mini);
        }
        if (Math.max(resources.getConfiguration().screenWidthDp, resources.getConfiguration().screenHeightDp) < 470) {
            return a(1);
        }
        return a(0);
    }

    public static int a(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode != Integer.MIN_VALUE) {
            return mode != 1073741824 ? i : size;
        }
        return Math.min(i, size);
    }
}
