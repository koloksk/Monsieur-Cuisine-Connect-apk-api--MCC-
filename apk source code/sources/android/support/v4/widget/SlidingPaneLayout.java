package android.support.v4.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SlidingPaneLayout extends ViewGroup {
    public static final d u = new f();
    public int a;
    public int b;
    public Drawable c;
    public Drawable d;
    public final int e;
    public boolean f;
    public View g;
    public float h;
    public float i;
    public int j;
    public boolean k;
    public int l;
    public float m;
    public float n;
    public PanelSlideListener o;
    public final ViewDragHelper p;
    public boolean q;
    public boolean r;
    public final Rect s;
    public final ArrayList<b> t;

    public interface PanelSlideListener {
        void onPanelClosed(@NonNull View view2);

        void onPanelOpened(@NonNull View view2);

        void onPanelSlide(@NonNull View view2, float f);
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public boolean b;

        public static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.b ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.b = parcel.readInt() != 0;
        }
    }

    public static class SimplePanelSlideListener implements PanelSlideListener {
        @Override // android.support.v4.widget.SlidingPaneLayout.PanelSlideListener
        public void onPanelClosed(View view2) {
        }

        @Override // android.support.v4.widget.SlidingPaneLayout.PanelSlideListener
        public void onPanelOpened(View view2) {
        }

        @Override // android.support.v4.widget.SlidingPaneLayout.PanelSlideListener
        public void onPanelSlide(View view2, float f) {
        }
    }

    public class a extends AccessibilityDelegateCompat {
        public final Rect d = new Rect();

        public a() {
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view2, accessibilityEvent);
            accessibilityEvent.setClassName(SlidingPaneLayout.class.getName());
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompatObtain);
            Rect rect = this.d;
            accessibilityNodeInfoCompatObtain.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompatObtain.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompatObtain.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompatObtain.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompatObtain.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompatObtain.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompatObtain.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompatObtain.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompatObtain.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompatObtain.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompatObtain.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompatObtain.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompatObtain.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompatObtain.getActions());
            accessibilityNodeInfoCompat.setMovementGranularities(accessibilityNodeInfoCompatObtain.getMovementGranularities());
            accessibilityNodeInfoCompatObtain.recycle();
            accessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
            accessibilityNodeInfoCompat.setSource(view2);
            Object parentForAccessibility = ViewCompat.getParentForAccessibility(view2);
            if (parentForAccessibility instanceof View) {
                accessibilityNodeInfoCompat.setParent((View) parentForAccessibility);
            }
            int childCount = SlidingPaneLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = SlidingPaneLayout.this.getChildAt(i);
                if (!SlidingPaneLayout.this.a(childAt) && childAt.getVisibility() == 0) {
                    ViewCompat.setImportantForAccessibility(childAt, 1);
                    accessibilityNodeInfoCompat.addChild(childAt);
                }
            }
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view2, AccessibilityEvent accessibilityEvent) {
            if (SlidingPaneLayout.this.a(view2)) {
                return false;
            }
            return super.onRequestSendAccessibilityEvent(viewGroup, view2, accessibilityEvent);
        }
    }

    public class b implements Runnable {
        public final View a;

        public b(View view2) {
            this.a = view2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.a.getParent() == SlidingPaneLayout.this) {
                this.a.setLayerType(0, null);
                SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
                View view2 = this.a;
                if (slidingPaneLayout == null) {
                    throw null;
                }
                ViewCompat.setLayerPaint(view2, ((LayoutParams) view2.getLayoutParams()).c);
            }
            SlidingPaneLayout.this.t.remove(this);
        }
    }

    public class c extends ViewDragHelper.Callback {
        public c() {
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view2, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) SlidingPaneLayout.this.g.getLayoutParams();
            if (!SlidingPaneLayout.this.a()) {
                int paddingLeft = SlidingPaneLayout.this.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                return Math.min(Math.max(i, paddingLeft), SlidingPaneLayout.this.j + paddingLeft);
            }
            int width = SlidingPaneLayout.this.getWidth() - (SlidingPaneLayout.this.g.getWidth() + (SlidingPaneLayout.this.getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin));
            return Math.max(Math.min(i, width), width - SlidingPaneLayout.this.j);
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view2, int i, int i2) {
            return view2.getTop();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view2) {
            return SlidingPaneLayout.this.j;
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onEdgeDragStarted(int i, int i2) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            slidingPaneLayout.p.captureChildView(slidingPaneLayout.g, i2);
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewCaptured(View view2, int i) {
            SlidingPaneLayout.this.b();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            if (SlidingPaneLayout.this.p.getViewDragState() == 0) {
                SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
                if (slidingPaneLayout.h != 0.0f) {
                    View view2 = slidingPaneLayout.g;
                    PanelSlideListener panelSlideListener = slidingPaneLayout.o;
                    if (panelSlideListener != null) {
                        panelSlideListener.onPanelOpened(view2);
                    }
                    slidingPaneLayout.sendAccessibilityEvent(32);
                    SlidingPaneLayout.this.q = true;
                    return;
                }
                slidingPaneLayout.b(slidingPaneLayout.g);
                SlidingPaneLayout slidingPaneLayout2 = SlidingPaneLayout.this;
                View view3 = slidingPaneLayout2.g;
                PanelSlideListener panelSlideListener2 = slidingPaneLayout2.o;
                if (panelSlideListener2 != null) {
                    panelSlideListener2.onPanelClosed(view3);
                }
                slidingPaneLayout2.sendAccessibilityEvent(32);
                SlidingPaneLayout.this.q = false;
            }
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view2, int i, int i2, int i3, int i4) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            if (slidingPaneLayout.g == null) {
                slidingPaneLayout.h = 0.0f;
            } else {
                boolean zA = slidingPaneLayout.a();
                LayoutParams layoutParams = (LayoutParams) slidingPaneLayout.g.getLayoutParams();
                int width = slidingPaneLayout.g.getWidth();
                if (zA) {
                    i = (slidingPaneLayout.getWidth() - i) - width;
                }
                float paddingRight = (i - ((zA ? slidingPaneLayout.getPaddingRight() : slidingPaneLayout.getPaddingLeft()) + (zA ? ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin))) / slidingPaneLayout.j;
                slidingPaneLayout.h = paddingRight;
                if (slidingPaneLayout.l != 0) {
                    slidingPaneLayout.a(paddingRight);
                }
                if (layoutParams.b) {
                    slidingPaneLayout.a(slidingPaneLayout.g, slidingPaneLayout.h, slidingPaneLayout.a);
                }
                View view3 = slidingPaneLayout.g;
                PanelSlideListener panelSlideListener = slidingPaneLayout.o;
                if (panelSlideListener != null) {
                    panelSlideListener.onPanelSlide(view3, slidingPaneLayout.h);
                }
            }
            SlidingPaneLayout.this.invalidate();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewReleased(View view2, float f, float f2) {
            int paddingLeft;
            LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
            if (SlidingPaneLayout.this.a()) {
                int paddingRight = SlidingPaneLayout.this.getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                if (f < 0.0f || (f == 0.0f && SlidingPaneLayout.this.h > 0.5f)) {
                    paddingRight += SlidingPaneLayout.this.j;
                }
                paddingLeft = (SlidingPaneLayout.this.getWidth() - paddingRight) - SlidingPaneLayout.this.g.getWidth();
            } else {
                paddingLeft = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + SlidingPaneLayout.this.getPaddingLeft();
                if (f > 0.0f || (f == 0.0f && SlidingPaneLayout.this.h > 0.5f)) {
                    paddingLeft += SlidingPaneLayout.this.j;
                }
            }
            SlidingPaneLayout.this.p.settleCapturedViewAt(paddingLeft, view2.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view2, int i) {
            if (SlidingPaneLayout.this.k) {
                return false;
            }
            return ((LayoutParams) view2.getLayoutParams()).a;
        }
    }

    public interface d {
    }

    public static class e implements d {
    }

    @RequiresApi(17)
    public static class f extends e {
    }

    public SlidingPaneLayout(@NonNull Context context) {
        this(context, null);
    }

    public final boolean a(int i) {
        if (!this.r && !b(0.0f)) {
            return false;
        }
        this.q = false;
        return true;
    }

    public void b(View view2) {
        int left;
        int right;
        int top;
        int bottom;
        View childAt;
        boolean z;
        View view3 = view2;
        boolean zA = a();
        int width = zA ? getWidth() - getPaddingRight() : getPaddingLeft();
        int paddingLeft = zA ? getPaddingLeft() : getWidth() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        if (view3 == null || !view2.isOpaque()) {
            left = 0;
            right = 0;
            top = 0;
            bottom = 0;
        } else {
            left = view2.getLeft();
            right = view2.getRight();
            top = view2.getTop();
            bottom = view2.getBottom();
        }
        int childCount = getChildCount();
        int i = 0;
        while (i < childCount && (childAt = getChildAt(i)) != view3) {
            if (childAt.getVisibility() == 8) {
                z = zA;
            } else {
                z = zA;
                childAt.setVisibility((Math.max(zA ? paddingLeft : width, childAt.getLeft()) < left || Math.max(paddingTop, childAt.getTop()) < top || Math.min(zA ? width : paddingLeft, childAt.getRight()) > right || Math.min(height, childAt.getBottom()) > bottom) ? 0 : 4);
            }
            i++;
            view3 = view2;
            zA = z;
        }
    }

    public boolean canScroll(View view2, boolean z, int i, int i2, int i3) {
        int i4;
        if (view2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view2;
            int scrollX = view2.getScrollX();
            int scrollY = view2.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(childAt, true, i, i5 - childAt.getLeft(), i4 - childAt.getTop())) {
                    return true;
                }
            }
        }
        if (z) {
            if (view2.canScrollHorizontally(a() ? i : -i)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public boolean canSlide() {
        return this.f;
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public boolean closePane() {
        return a(0);
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.p.continueSettling(true)) {
            if (this.f) {
                ViewCompat.postInvalidateOnAnimation(this);
            } else {
                this.p.abort();
            }
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        int i;
        int right;
        super.draw(canvas);
        Drawable drawable = a() ? this.d : this.c;
        View childAt = getChildCount() > 1 ? getChildAt(1) : null;
        if (childAt == null || drawable == null) {
            return;
        }
        int top = childAt.getTop();
        int bottom = childAt.getBottom();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (a()) {
            right = childAt.getRight();
            i = intrinsicWidth + right;
        } else {
            int left = childAt.getLeft();
            int i2 = left - intrinsicWidth;
            i = left;
            right = i2;
        }
        drawable.setBounds(right, top, i, bottom);
        drawable.draw(canvas);
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view2, long j) {
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        int iSave = canvas.save();
        if (this.f && !layoutParams.a && this.g != null) {
            canvas.getClipBounds(this.s);
            if (a()) {
                Rect rect = this.s;
                rect.left = Math.max(rect.left, this.g.getRight());
            } else {
                Rect rect2 = this.s;
                rect2.right = Math.min(rect2.right, this.g.getLeft());
            }
            canvas.clipRect(this.s);
        }
        boolean zDrawChild = super.drawChild(canvas, view2, j);
        canvas.restoreToCount(iSave);
        return zDrawChild;
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    @ColorInt
    public int getCoveredFadeColor() {
        return this.b;
    }

    public int getParallaxDistance() {
        return this.l;
    }

    @ColorInt
    public int getSliderFadeColor() {
        return this.a;
    }

    public boolean isOpen() {
        return !this.f || this.h == 1.0f;
    }

    public boolean isSlideable() {
        return this.f;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.r = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.r = true;
        int size = this.t.size();
        for (int i = 0; i < size; i++) {
            this.t.get(i).run();
        }
        this.t.clear();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View childAt;
        int actionMasked = motionEvent.getActionMasked();
        if (!this.f && actionMasked == 0 && getChildCount() > 1 && (childAt = getChildAt(1)) != null) {
            this.q = !this.p.isViewUnder(childAt, (int) motionEvent.getX(), (int) motionEvent.getY());
        }
        if (!this.f || (this.k && actionMasked != 0)) {
            this.p.cancel();
            return super.onInterceptTouchEvent(motionEvent);
        }
        if (actionMasked == 3 || actionMasked == 1) {
            this.p.cancel();
            return false;
        }
        if (actionMasked == 0) {
            this.k = false;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.m = x;
            this.n = y;
            if (this.p.isViewUnder(this.g, (int) x, (int) y) && a(this.g)) {
                z = true;
            }
            return this.p.shouldInterceptTouchEvent(motionEvent) || z;
        }
        if (actionMasked == 2) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            float fAbs = Math.abs(x2 - this.m);
            float fAbs2 = Math.abs(y2 - this.n);
            if (fAbs > this.p.getTouchSlop() && fAbs2 > fAbs) {
                this.p.cancel();
                this.k = true;
                return false;
            }
        }
        z = false;
        if (this.p.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean zA = a();
        if (zA) {
            this.p.setEdgeTrackingEnabled(2);
        } else {
            this.p.setEdgeTrackingEnabled(1);
        }
        int i10 = i3 - i;
        int paddingRight = zA ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = zA ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.r) {
            this.h = (this.f && this.q) ? 1.0f : 0.0f;
        }
        int i11 = paddingRight;
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt = getChildAt(i12);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                if (layoutParams.a) {
                    int i13 = i10 - paddingLeft;
                    int iMin = (Math.min(paddingRight, i13 - this.e) - i11) - (((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                    this.j = iMin;
                    int i14 = zA ? ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    layoutParams.b = (measuredWidth / 2) + ((i11 + i14) + iMin) > i13;
                    int i15 = (int) (iMin * this.h);
                    i5 = i14 + i15 + i11;
                    this.h = i15 / this.j;
                    i6 = 0;
                } else if (!this.f || (i7 = this.l) == 0) {
                    i5 = paddingRight;
                    i6 = 0;
                } else {
                    i6 = (int) ((1.0f - this.h) * i7);
                    i5 = paddingRight;
                }
                if (zA) {
                    i9 = (i10 - i5) + i6;
                    i8 = i9 - measuredWidth;
                } else {
                    i8 = i5 - i6;
                    i9 = i8 + measuredWidth;
                }
                childAt.layout(i8, paddingTop, i9, childAt.getMeasuredHeight() + paddingTop);
                i11 = i5;
                paddingRight = childAt.getWidth() + paddingRight;
            }
        }
        if (this.r) {
            if (this.f) {
                if (this.l != 0) {
                    a(this.h);
                }
                if (((LayoutParams) this.g.getLayoutParams()).b) {
                    a(this.g, this.h, this.a);
                }
            } else {
                for (int i16 = 0; i16 < childCount; i16++) {
                    a(getChildAt(i16), 0.0f, this.a);
                }
            }
            b(this.g);
        }
        this.r = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00ad A[PHI: r13
  0x00ad: PHI (r13v2 float) = (r13v1 float), (r13v8 float) binds: [B:36:0x00a4, B:38:0x00a9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x013a  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMeasure(int r21, int r22) {
        /*
            Method dump skipped, instructions count: 565
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.onMeasure(int, int):void");
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.b) {
            openPane();
        } else {
            closePane();
        }
        this.q = savedState.b;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.b = isSlideable() ? isOpen() : this.q;
        return savedState;
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            this.r = true;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.f) {
            return super.onTouchEvent(motionEvent);
        }
        this.p.processTouchEvent(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.m = x;
            this.n = y;
        } else if (actionMasked == 1 && a(this.g)) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            float f2 = x2 - this.m;
            float f3 = y2 - this.n;
            int touchSlop = this.p.getTouchSlop();
            if ((f3 * f3) + (f2 * f2) < touchSlop * touchSlop && this.p.isViewUnder(this.g, (int) x2, (int) y2)) {
                a(0);
            }
        }
        return true;
    }

    public boolean openPane() {
        if (!this.r && !b(1.0f)) {
            return false;
        }
        this.q = true;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view2, View view3) {
        super.requestChildFocus(view2, view3);
        if (isInTouchMode() || this.f) {
            return;
        }
        this.q = view2 == this.g;
    }

    public void setCoveredFadeColor(@ColorInt int i) {
        this.b = i;
    }

    public void setPanelSlideListener(@Nullable PanelSlideListener panelSlideListener) {
        this.o = panelSlideListener;
    }

    public void setParallaxDistance(int i) {
        this.l = i;
        requestLayout();
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable) {
        setShadowDrawableLeft(drawable);
    }

    public void setShadowDrawableLeft(@Nullable Drawable drawable) {
        this.c = drawable;
    }

    public void setShadowDrawableRight(@Nullable Drawable drawable) {
        this.d = drawable;
    }

    @Deprecated
    public void setShadowResource(@DrawableRes int i) {
        setShadowDrawable(getResources().getDrawable(i));
    }

    public void setShadowResourceLeft(int i) {
        setShadowDrawableLeft(ContextCompat.getDrawable(getContext(), i));
    }

    public void setShadowResourceRight(int i) {
        setShadowDrawableRight(ContextCompat.getDrawable(getContext(), i));
    }

    public void setSliderFadeColor(@ColorInt int i) {
        this.a = i;
    }

    @Deprecated
    public void smoothSlideClosed() {
        closePane();
    }

    @Deprecated
    public void smoothSlideOpen() {
        openPane();
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public static final int[] d = {R.attr.layout_weight};
        public boolean a;
        public boolean b;
        public Paint c;
        public float weight;

        public LayoutParams() {
            super(-1, -1);
            this.weight = 0.0f;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.weight = 0.0f;
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.weight = 0.0f;
        }

        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.weight = 0.0f;
        }

        public LayoutParams(@NonNull LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.weight = 0.0f;
            this.weight = layoutParams.weight;
        }

        public LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            this.weight = 0.0f;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, d);
            this.weight = typedArrayObtainStyledAttributes.getFloat(0, 0.0f);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = -858993460;
        this.r = true;
        this.s = new Rect();
        this.t = new ArrayList<>();
        float f2 = context.getResources().getDisplayMetrics().density;
        this.e = (int) ((32.0f * f2) + 0.5f);
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new a());
        ViewCompat.setImportantForAccessibility(this, 1);
        ViewDragHelper viewDragHelperCreate = ViewDragHelper.create(this, 0.5f, new c());
        this.p = viewDragHelperCreate;
        viewDragHelperCreate.setMinVelocity(f2 * 400.0f);
    }

    public final void a(View view2, float f2, int i) {
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        if (f2 > 0.0f && i != 0) {
            int i2 = (((int) ((((-16777216) & i) >>> 24) * f2)) << 24) | (i & ViewCompat.MEASURED_SIZE_MASK);
            if (layoutParams.c == null) {
                layoutParams.c = new Paint();
            }
            layoutParams.c.setColorFilter(new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_OVER));
            if (view2.getLayerType() != 2) {
                view2.setLayerType(2, layoutParams.c);
            }
            ViewCompat.setLayerPaint(view2, ((LayoutParams) view2.getLayoutParams()).c);
            return;
        }
        if (view2.getLayerType() != 0) {
            Paint paint = layoutParams.c;
            if (paint != null) {
                paint.setColorFilter(null);
            }
            b bVar = new b(view2);
            this.t.add(bVar);
            ViewCompat.postOnAnimation(this, bVar);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(float r10) {
        /*
            r9 = this;
            boolean r0 = r9.a()
            android.view.View r1 = r9.g
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            android.support.v4.widget.SlidingPaneLayout$LayoutParams r1 = (android.support.v4.widget.SlidingPaneLayout.LayoutParams) r1
            boolean r2 = r1.b
            r3 = 0
            if (r2 == 0) goto L1c
            if (r0 == 0) goto L16
            int r1 = r1.rightMargin
            goto L18
        L16:
            int r1 = r1.leftMargin
        L18:
            if (r1 > 0) goto L1c
            r1 = 1
            goto L1d
        L1c:
            r1 = r3
        L1d:
            int r2 = r9.getChildCount()
        L21:
            if (r3 >= r2) goto L57
            android.view.View r4 = r9.getChildAt(r3)
            android.view.View r5 = r9.g
            if (r4 != r5) goto L2c
            goto L54
        L2c:
            float r5 = r9.i
            r6 = 1065353216(0x3f800000, float:1.0)
            float r5 = r6 - r5
            int r7 = r9.l
            float r8 = (float) r7
            float r5 = r5 * r8
            int r5 = (int) r5
            r9.i = r10
            float r8 = r6 - r10
            float r7 = (float) r7
            float r8 = r8 * r7
            int r7 = (int) r8
            int r5 = r5 - r7
            if (r0 == 0) goto L42
            int r5 = -r5
        L42:
            r4.offsetLeftAndRight(r5)
            if (r1 == 0) goto L54
            float r5 = r9.i
            if (r0 == 0) goto L4d
            float r5 = r5 - r6
            goto L4f
        L4d:
            float r5 = r6 - r5
        L4f:
            int r6 = r9.b
            r9.a(r4, r5, r6)
        L54:
            int r3 = r3 + 1
            goto L21
        L57:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.a(float):void");
    }

    public void b() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    public boolean b(float f2) {
        int paddingLeft;
        if (!this.f) {
            return false;
        }
        boolean zA = a();
        LayoutParams layoutParams = (LayoutParams) this.g.getLayoutParams();
        if (zA) {
            int paddingRight = getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            paddingLeft = (int) (getWidth() - (((f2 * this.j) + paddingRight) + this.g.getWidth()));
        } else {
            paddingLeft = (int) ((f2 * this.j) + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin);
        }
        ViewDragHelper viewDragHelper = this.p;
        View view2 = this.g;
        if (!viewDragHelper.smoothSlideViewTo(view2, paddingLeft, view2.getTop())) {
            return false;
        }
        b();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public boolean a(View view2) {
        if (view2 == null) {
            return false;
        }
        return this.f && ((LayoutParams) view2.getLayoutParams()).b && this.h > 0.0f;
    }

    public boolean a() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }
}
