package android.support.design.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public abstract class HeaderScrollingViewBehavior extends ViewOffsetBehavior<View> {
    public final Rect d;
    public final Rect e;
    public int f;
    public int g;

    public HeaderScrollingViewBehavior() {
        this.d = new Rect();
        this.e = new Rect();
        this.f = 0;
    }

    public final int a(View view2) {
        int i;
        if (this.g == 0) {
            return 0;
        }
        float f = 0.0f;
        if (view2 instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) view2;
            int totalScrollRange = appBarLayout.getTotalScrollRange();
            int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
            int iA = behavior instanceof AppBarLayout.Behavior ? ((AppBarLayout.Behavior) behavior).a() : 0;
            if ((downNestedPreScrollRange == 0 || totalScrollRange + iA > downNestedPreScrollRange) && (i = totalScrollRange - downNestedPreScrollRange) != 0) {
                f = 1.0f + (iA / i);
            }
        }
        int i2 = this.g;
        return MathUtils.clamp((int) (f * i2), 0, i2);
    }

    public final int getOverlayTop() {
        return this.g;
    }

    @Override // android.support.design.widget.ViewOffsetBehavior
    public void layoutChild(CoordinatorLayout coordinatorLayout, View view2, int i) {
        AppBarLayout appBarLayoutA = ((AppBarLayout.ScrollingViewBehavior) this).a(coordinatorLayout.getDependencies(view2));
        if (appBarLayoutA == null) {
            super.layoutChild(coordinatorLayout, view2, i);
            this.f = 0;
            return;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view2.getLayoutParams();
        Rect rect = this.d;
        rect.set(coordinatorLayout.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, appBarLayoutA.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((appBarLayoutA.getBottom() + coordinatorLayout.getHeight()) - coordinatorLayout.getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        WindowInsetsCompat lastWindowInsets = coordinatorLayout.getLastWindowInsets();
        if (lastWindowInsets != null && ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(view2)) {
            rect.left = lastWindowInsets.getSystemWindowInsetLeft() + rect.left;
            rect.right -= lastWindowInsets.getSystemWindowInsetRight();
        }
        Rect rect2 = this.e;
        int i2 = layoutParams.gravity;
        if (i2 == 0) {
            i2 = 8388659;
        }
        GravityCompat.apply(i2, view2.getMeasuredWidth(), view2.getMeasuredHeight(), rect, rect2, i);
        int iA = a(appBarLayoutA);
        view2.layout(rect2.left, rect2.top - iA, rect2.right, rect2.bottom - iA);
        this.f = rect2.top - appBarLayoutA.getBottom();
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view2, int i, int i2, int i3, int i4) {
        int i5 = view2.getLayoutParams().height;
        if (i5 != -1 && i5 != -2) {
            return false;
        }
        AppBarLayout appBarLayoutA = ((AppBarLayout.ScrollingViewBehavior) this).a(coordinatorLayout.getDependencies(view2));
        if (appBarLayoutA == null) {
            return false;
        }
        if (ViewCompat.getFitsSystemWindows(appBarLayoutA) && !ViewCompat.getFitsSystemWindows(view2)) {
            ViewCompat.setFitsSystemWindows(view2, true);
            if (ViewCompat.getFitsSystemWindows(view2)) {
                view2.requestLayout();
                return true;
            }
        }
        int size = View.MeasureSpec.getSize(i3);
        if (size == 0) {
            size = coordinatorLayout.getHeight();
        }
        coordinatorLayout.onMeasureChild(view2, i, i2, View.MeasureSpec.makeMeasureSpec((size - appBarLayoutA.getMeasuredHeight()) + appBarLayoutA.getTotalScrollRange(), i5 == -1 ? 1073741824 : Integer.MIN_VALUE), i4);
        return true;
    }

    public final void setOverlayTop(int i) {
        this.g = i;
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.d = new Rect();
        this.e = new Rect();
        this.f = 0;
    }
}
