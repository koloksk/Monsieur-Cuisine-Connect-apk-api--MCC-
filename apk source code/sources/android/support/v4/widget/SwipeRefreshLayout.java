package android.support.v4.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.ListView;
import defpackage.d6;
import defpackage.i6;
import defpackage.j6;

/* loaded from: classes.dex */
public class SwipeRefreshLayout extends ViewGroup implements NestedScrollingParent, NestedScrollingChild {
    public static final int DEFAULT = 1;
    public static final int LARGE = 0;
    public Animation A;
    public Animation B;
    public Animation C;
    public Animation D;
    public boolean E;
    public int F;
    public boolean G;
    public OnChildScrollUpCallback H;
    public Animation.AnimationListener I;
    public final Animation J;
    public final Animation K;
    public View a;
    public OnRefreshListener b;
    public boolean c;
    public int d;
    public float e;
    public float f;
    public final NestedScrollingParentHelper g;
    public final NestedScrollingChildHelper h;
    public final int[] i;
    public final int[] j;
    public boolean k;
    public int l;
    public int m;
    public int mFrom;
    public int mOriginalOffsetTop;
    public float n;
    public float o;
    public boolean p;
    public int q;
    public boolean r;
    public boolean s;
    public final DecelerateInterpolator t;
    public d6 u;
    public int v;
    public float w;
    public int x;
    public CircularProgressDrawable y;
    public Animation z;
    public static final String L = SwipeRefreshLayout.class.getSimpleName();
    public static final int[] M = {R.attr.enabled};

    public interface OnChildScrollUpCallback {
        boolean canChildScrollUp(@NonNull SwipeRefreshLayout swipeRefreshLayout, @Nullable View view2);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public class a implements Animation.AnimationListener {
        public a() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            OnRefreshListener onRefreshListener;
            SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
            if (!swipeRefreshLayout.c) {
                swipeRefreshLayout.b();
                return;
            }
            swipeRefreshLayout.y.setAlpha(255);
            SwipeRefreshLayout.this.y.start();
            SwipeRefreshLayout swipeRefreshLayout2 = SwipeRefreshLayout.this;
            if (swipeRefreshLayout2.E && (onRefreshListener = swipeRefreshLayout2.b) != null) {
                onRefreshListener.onRefresh();
            }
            SwipeRefreshLayout swipeRefreshLayout3 = SwipeRefreshLayout.this;
            swipeRefreshLayout3.m = swipeRefreshLayout3.u.getTop();
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }
    }

    public class b extends Animation {
        public b() {
        }

        @Override // android.view.animation.Animation
        public void applyTransformation(float f, Transformation transformation) {
            SwipeRefreshLayout.this.setAnimationProgress(1.0f - f);
        }
    }

    public class c extends Animation {
        public final /* synthetic */ int a;
        public final /* synthetic */ int b;

        public c(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        @Override // android.view.animation.Animation
        public void applyTransformation(float f, Transformation transformation) {
            SwipeRefreshLayout.this.y.setAlpha((int) (((this.b - r0) * f) + this.a));
        }
    }

    public class d implements Animation.AnimationListener {
        public d() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
            if (swipeRefreshLayout.r) {
                return;
            }
            swipeRefreshLayout.a((Animation.AnimationListener) null);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }
    }

    public class e extends Animation {
        public e() {
        }

        @Override // android.view.animation.Animation
        public void applyTransformation(float f, Transformation transformation) {
            SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
            int iAbs = !swipeRefreshLayout.G ? swipeRefreshLayout.x - Math.abs(swipeRefreshLayout.mOriginalOffsetTop) : swipeRefreshLayout.x;
            SwipeRefreshLayout swipeRefreshLayout2 = SwipeRefreshLayout.this;
            SwipeRefreshLayout.this.setTargetOffsetTopAndBottom((swipeRefreshLayout2.mFrom + ((int) ((iAbs - r1) * f))) - swipeRefreshLayout2.u.getTop());
            SwipeRefreshLayout.this.y.setArrowScale(1.0f - f);
        }
    }

    public class f extends Animation {
        public f() {
        }

        @Override // android.view.animation.Animation
        public void applyTransformation(float f, Transformation transformation) {
            SwipeRefreshLayout.this.c(f);
        }
    }

    public SwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    private void setColorViewAlpha(int i) {
        this.u.getBackground().setAlpha(i);
        this.y.setAlpha(i);
    }

    public final void a(boolean z, boolean z2) {
        if (this.c != z) {
            this.E = z2;
            a();
            this.c = z;
            if (!z) {
                a(this.I);
                return;
            }
            int i = this.m;
            Animation.AnimationListener animationListener = this.I;
            this.mFrom = i;
            this.J.reset();
            this.J.setDuration(200L);
            this.J.setInterpolator(this.t);
            if (animationListener != null) {
                this.u.a = animationListener;
            }
            this.u.clearAnimation();
            this.u.startAnimation(this.J);
        }
    }

    public void b() {
        this.u.clearAnimation();
        this.y.stop();
        this.u.setVisibility(8);
        setColorViewAlpha(255);
        if (this.r) {
            setAnimationProgress(0.0f);
        } else {
            setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.m);
        }
        this.m = this.u.getTop();
    }

    public void c(float f2) {
        setTargetOffsetTopAndBottom((this.mFrom + ((int) ((this.mOriginalOffsetTop - r0) * f2))) - this.u.getTop());
    }

    public boolean canChildScrollUp() {
        OnChildScrollUpCallback onChildScrollUpCallback = this.H;
        if (onChildScrollUpCallback != null) {
            return onChildScrollUpCallback.canChildScrollUp(this, this.a);
        }
        View view2 = this.a;
        return view2 instanceof ListView ? ListViewCompat.canScrollList((ListView) view2, -1) : view2.canScrollVertically(-1);
    }

    public final void d(float f2) {
        float f3 = this.o;
        float f4 = f2 - f3;
        int i = this.d;
        if (f4 <= i || this.p) {
            return;
        }
        this.n = f3 + i;
        this.p = true;
        this.y.setAlpha(76);
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean dispatchNestedFling(float f2, float f3, boolean z) {
        return this.h.dispatchNestedFling(f2, f3, z);
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.h.dispatchNestedPreFling(f2, f3);
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.h.dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.h.dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    @Override // android.view.ViewGroup
    public int getChildDrawingOrder(int i, int i2) {
        int i3 = this.v;
        return i3 < 0 ? i2 : i2 == i + (-1) ? i3 : i2 >= i3 ? i2 + 1 : i2;
    }

    @Override // android.view.ViewGroup, android.support.v4.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.g.getNestedScrollAxes();
    }

    public int getProgressCircleDiameter() {
        return this.F;
    }

    public int getProgressViewEndOffset() {
        return this.x;
    }

    public int getProgressViewStartOffset() {
        return this.mOriginalOffsetTop;
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean hasNestedScrollingParent() {
        return this.h.hasNestedScrollingParent();
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return this.h.isNestedScrollingEnabled();
    }

    public boolean isRefreshing() {
        return this.c;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b();
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0058  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            r4.a()
            int r0 = r5.getActionMasked()
            boolean r1 = r4.s
            r2 = 0
            if (r1 == 0) goto L10
            if (r0 != 0) goto L10
            r4.s = r2
        L10:
            boolean r1 = r4.isEnabled()
            if (r1 == 0) goto L81
            boolean r1 = r4.s
            if (r1 != 0) goto L81
            boolean r1 = r4.canChildScrollUp()
            if (r1 != 0) goto L81
            boolean r1 = r4.c
            if (r1 != 0) goto L81
            boolean r1 = r4.k
            if (r1 == 0) goto L29
            goto L81
        L29:
            if (r0 == 0) goto L5d
            r1 = 1
            r3 = -1
            if (r0 == r1) goto L58
            r1 = 2
            if (r0 == r1) goto L3d
            r1 = 3
            if (r0 == r1) goto L58
            r1 = 6
            if (r0 == r1) goto L39
            goto L7e
        L39:
            r4.a(r5)
            goto L7e
        L3d:
            int r0 = r4.q
            if (r0 != r3) goto L49
            java.lang.String r5 = android.support.v4.widget.SwipeRefreshLayout.L
            java.lang.String r0 = "Got ACTION_MOVE event but don't have an active pointer id."
            android.util.Log.e(r5, r0)
            return r2
        L49:
            int r0 = r5.findPointerIndex(r0)
            if (r0 >= 0) goto L50
            return r2
        L50:
            float r5 = r5.getY(r0)
            r4.d(r5)
            goto L7e
        L58:
            r4.p = r2
            r4.q = r3
            goto L7e
        L5d:
            int r0 = r4.mOriginalOffsetTop
            d6 r1 = r4.u
            int r1 = r1.getTop()
            int r0 = r0 - r1
            r4.setTargetOffsetTopAndBottom(r0)
            int r0 = r5.getPointerId(r2)
            r4.q = r0
            r4.p = r2
            int r0 = r5.findPointerIndex(r0)
            if (r0 >= 0) goto L78
            return r2
        L78:
            float r5 = r5.getY(r0)
            r4.o = r5
        L7e:
            boolean r5 = r4.p
            return r5
        L81:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SwipeRefreshLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (this.a == null) {
            a();
        }
        View view2 = this.a;
        if (view2 == null) {
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        view2.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
        int measuredWidth2 = this.u.getMeasuredWidth();
        int measuredHeight2 = this.u.getMeasuredHeight();
        int i5 = measuredWidth / 2;
        int i6 = measuredWidth2 / 2;
        int i7 = this.m;
        this.u.layout(i5 - i6, i7, i5 + i6, measuredHeight2 + i7);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.a == null) {
            a();
        }
        View view2 = this.a;
        if (view2 == null) {
            return;
        }
        view2.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
        this.u.measure(View.MeasureSpec.makeMeasureSpec(this.F, 1073741824), View.MeasureSpec.makeMeasureSpec(this.F, 1073741824));
        this.v = -1;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            if (getChildAt(i3) == this.u) {
                this.v = i3;
                return;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public boolean onNestedFling(View view2, float f2, float f3, boolean z) {
        return dispatchNestedFling(f2, f3, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public boolean onNestedPreFling(View view2, float f2, float f3) {
        return dispatchNestedPreFling(f2, f3);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public void onNestedPreScroll(View view2, int i, int i2, int[] iArr) {
        if (i2 > 0) {
            float f2 = this.f;
            if (f2 > 0.0f) {
                float f3 = i2;
                if (f3 > f2) {
                    iArr[1] = i2 - ((int) f2);
                    this.f = 0.0f;
                } else {
                    this.f = f2 - f3;
                    iArr[1] = i2;
                }
                b(this.f);
            }
        }
        if (this.G && i2 > 0 && this.f == 0.0f && Math.abs(i2 - iArr[1]) > 0) {
            this.u.setVisibility(8);
        }
        int[] iArr2 = this.i;
        if (dispatchNestedPreScroll(i - iArr[0], i2 - iArr[1], iArr2, null)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public void onNestedScroll(View view2, int i, int i2, int i3, int i4) {
        dispatchNestedScroll(i, i2, i3, i4, this.j);
        if (i4 + this.j[1] >= 0 || canChildScrollUp()) {
            return;
        }
        float fAbs = this.f + Math.abs(r11);
        this.f = fAbs;
        b(fAbs);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public void onNestedScrollAccepted(View view2, View view3, int i) {
        this.g.onNestedScrollAccepted(view2, view3, i);
        startNestedScroll(i & 2);
        this.f = 0.0f;
        this.k = true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public boolean onStartNestedScroll(View view2, View view3, int i) {
        return (!isEnabled() || this.s || this.c || (i & 2) == 0) ? false : true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    public void onStopNestedScroll(View view2) {
        this.g.onStopNestedScroll(view2);
        this.k = false;
        float f2 = this.f;
        if (f2 > 0.0f) {
            a(f2);
            this.f = 0.0f;
        }
        stopNestedScroll();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.s && actionMasked == 0) {
            this.s = false;
        }
        if (!isEnabled() || this.s || canChildScrollUp() || this.c || this.k) {
            return false;
        }
        if (actionMasked == 0) {
            this.q = motionEvent.getPointerId(0);
            this.p = false;
        } else {
            if (actionMasked == 1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.q);
                if (iFindPointerIndex < 0) {
                    Log.e(L, "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }
                if (this.p) {
                    float y = (motionEvent.getY(iFindPointerIndex) - this.n) * 0.5f;
                    this.p = false;
                    a(y);
                }
                this.q = -1;
                return false;
            }
            if (actionMasked == 2) {
                int iFindPointerIndex2 = motionEvent.findPointerIndex(this.q);
                if (iFindPointerIndex2 < 0) {
                    Log.e(L, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                float y2 = motionEvent.getY(iFindPointerIndex2);
                d(y2);
                if (this.p) {
                    float f2 = (y2 - this.n) * 0.5f;
                    if (f2 <= 0.0f) {
                        return false;
                    }
                    b(f2);
                }
            } else {
                if (actionMasked == 3) {
                    return false;
                }
                if (actionMasked == 5) {
                    int actionIndex = motionEvent.getActionIndex();
                    if (actionIndex < 0) {
                        Log.e(L, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                        return false;
                    }
                    this.q = motionEvent.getPointerId(actionIndex);
                } else if (actionMasked == 6) {
                    a(motionEvent);
                }
            }
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        View view2 = this.a;
        if (view2 == null || ViewCompat.isNestedScrollingEnabled(view2)) {
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    public void setAnimationProgress(float f2) {
        this.u.setScaleX(f2);
        this.u.setScaleY(f2);
    }

    @Deprecated
    public void setColorScheme(@ColorRes int... iArr) {
        setColorSchemeResources(iArr);
    }

    public void setColorSchemeColors(@ColorInt int... iArr) {
        a();
        this.y.setColorSchemeColors(iArr);
    }

    public void setColorSchemeResources(@ColorRes int... iArr) {
        Context context = getContext();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = ContextCompat.getColor(context, iArr[i]);
        }
        setColorSchemeColors(iArr2);
    }

    public void setDistanceToTriggerSync(int i) {
        this.e = i;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (z) {
            return;
        }
        b();
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z) {
        this.h.setNestedScrollingEnabled(z);
    }

    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback onChildScrollUpCallback) {
        this.H = onChildScrollUpCallback;
    }

    public void setOnRefreshListener(@Nullable OnRefreshListener onRefreshListener) {
        this.b = onRefreshListener;
    }

    @Deprecated
    public void setProgressBackgroundColor(int i) {
        setProgressBackgroundColorSchemeResource(i);
    }

    public void setProgressBackgroundColorSchemeColor(@ColorInt int i) {
        this.u.setBackgroundColor(i);
    }

    public void setProgressBackgroundColorSchemeResource(@ColorRes int i) {
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), i));
    }

    public void setProgressViewEndTarget(boolean z, int i) {
        this.x = i;
        this.r = z;
        this.u.invalidate();
    }

    public void setProgressViewOffset(boolean z, int i, int i2) {
        this.r = z;
        this.mOriginalOffsetTop = i;
        this.x = i2;
        this.G = true;
        b();
        this.c = false;
    }

    public void setRefreshing(boolean z) {
        if (!z || this.c == z) {
            a(z, false);
            return;
        }
        this.c = z;
        setTargetOffsetTopAndBottom((!this.G ? this.x + this.mOriginalOffsetTop : this.x) - this.m);
        this.E = false;
        Animation.AnimationListener animationListener = this.I;
        this.u.setVisibility(0);
        this.y.setAlpha(255);
        i6 i6Var = new i6(this);
        this.z = i6Var;
        i6Var.setDuration(this.l);
        if (animationListener != null) {
            this.u.a = animationListener;
        }
        this.u.clearAnimation();
        this.u.startAnimation(this.z);
    }

    public void setSize(int i) {
        if (i == 0 || i == 1) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (i == 0) {
                this.F = (int) (displayMetrics.density * 56.0f);
            } else {
                this.F = (int) (displayMetrics.density * 40.0f);
            }
            this.u.setImageDrawable(null);
            this.y.setStyle(i);
            this.u.setImageDrawable(this.y);
        }
    }

    public void setTargetOffsetTopAndBottom(int i) {
        this.u.bringToFront();
        ViewCompat.offsetTopAndBottom(this.u, i);
        this.m = this.u.getTop();
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean startNestedScroll(int i) {
        return this.h.startNestedScroll(i);
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public void stopNestedScroll() {
        this.h.stopNestedScroll();
    }

    public SwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = false;
        this.e = -1.0f;
        this.i = new int[2];
        this.j = new int[2];
        this.q = -1;
        this.v = -1;
        this.I = new a();
        this.J = new e();
        this.K = new f();
        this.d = ViewConfiguration.get(context).getScaledTouchSlop();
        this.l = getResources().getInteger(R.integer.config_mediumAnimTime);
        setWillNotDraw(false);
        this.t = new DecelerateInterpolator(2.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.F = (int) (displayMetrics.density * 40.0f);
        this.u = new d6(getContext(), -328966);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
        this.y = circularProgressDrawable;
        circularProgressDrawable.setStyle(1);
        this.u.setImageDrawable(this.y);
        this.u.setVisibility(8);
        addView(this.u);
        setChildrenDrawingOrderEnabled(true);
        int i = (int) (displayMetrics.density * 64.0f);
        this.x = i;
        this.e = i;
        this.g = new NestedScrollingParentHelper(this);
        this.h = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        int i2 = -this.F;
        this.m = i2;
        this.mOriginalOffsetTop = i2;
        c(1.0f);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, M);
        setEnabled(typedArrayObtainStyledAttributes.getBoolean(0, true));
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void b(float f2) {
        this.y.setArrowEnabled(true);
        float fMin = Math.min(1.0f, Math.abs(f2 / this.e));
        float fMax = (((float) Math.max(fMin - 0.4d, 0.0d)) * 5.0f) / 3.0f;
        float fAbs = Math.abs(f2) - this.e;
        float f3 = this.G ? this.x - this.mOriginalOffsetTop : this.x;
        double dMax = Math.max(0.0f, Math.min(fAbs, f3 * 2.0f) / f3) / 4.0f;
        float fPow = ((float) (dMax - Math.pow(dMax, 2.0d))) * 2.0f;
        int i = this.mOriginalOffsetTop + ((int) ((f3 * fMin) + (f3 * fPow * 2.0f)));
        if (this.u.getVisibility() != 0) {
            this.u.setVisibility(0);
        }
        if (!this.r) {
            this.u.setScaleX(1.0f);
            this.u.setScaleY(1.0f);
        }
        if (this.r) {
            setAnimationProgress(Math.min(1.0f, f2 / this.e));
        }
        if (f2 < this.e) {
            if (this.y.getAlpha() > 76 && !a(this.B)) {
                this.B = a(this.y.getAlpha(), 76);
            }
        } else if (this.y.getAlpha() < 255 && !a(this.C)) {
            this.C = a(this.y.getAlpha(), 255);
        }
        this.y.setStartEndTrim(0.0f, Math.min(0.8f, fMax * 0.8f));
        this.y.setArrowScale(Math.min(1.0f, fMax));
        this.y.setProgressRotation(((fPow * 2.0f) + ((fMax * 0.4f) - 0.25f)) * 0.5f);
        setTargetOffsetTopAndBottom(i - this.m);
    }

    public void a(Animation.AnimationListener animationListener) {
        b bVar = new b();
        this.A = bVar;
        bVar.setDuration(150L);
        d6 d6Var = this.u;
        d6Var.a = animationListener;
        d6Var.clearAnimation();
        this.u.startAnimation(this.A);
    }

    public final Animation a(int i, int i2) {
        c cVar = new c(i, i2);
        cVar.setDuration(300L);
        d6 d6Var = this.u;
        d6Var.a = null;
        d6Var.clearAnimation();
        this.u.startAnimation(cVar);
        return cVar;
    }

    public final void a() {
        if (this.a == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (!childAt.equals(this.u)) {
                    this.a = childAt;
                    return;
                }
            }
        }
    }

    public final boolean a(Animation animation) {
        return (animation == null || !animation.hasStarted() || animation.hasEnded()) ? false : true;
    }

    public final void a(float f2) {
        if (f2 > this.e) {
            a(true, true);
            return;
        }
        this.c = false;
        this.y.setStartEndTrim(0.0f, 0.0f);
        d dVar = this.r ? null : new d();
        int i = this.m;
        if (this.r) {
            this.mFrom = i;
            this.w = this.u.getScaleX();
            j6 j6Var = new j6(this);
            this.D = j6Var;
            j6Var.setDuration(150L);
            if (dVar != null) {
                this.u.a = dVar;
            }
            this.u.clearAnimation();
            this.u.startAnimation(this.D);
        } else {
            this.mFrom = i;
            this.K.reset();
            this.K.setDuration(200L);
            this.K.setInterpolator(this.t);
            if (dVar != null) {
                this.u.a = dVar;
            }
            this.u.clearAnimation();
            this.u.startAnimation(this.K);
        }
        this.y.setArrowEnabled(false);
    }

    public final void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.q) {
            this.q = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
        }
    }
}
