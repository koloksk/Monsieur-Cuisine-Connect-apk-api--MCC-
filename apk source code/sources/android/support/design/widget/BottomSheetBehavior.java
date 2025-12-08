package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import defpackage.g9;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    public float a;
    public int b;
    public boolean c;
    public int d;
    public int e;
    public int f;
    public boolean g;
    public boolean h;
    public int i;
    public ViewDragHelper j;
    public boolean k;
    public int l;
    public boolean m;
    public int n;
    public WeakReference<V> o;
    public WeakReference<View> p;
    public BottomSheetCallback q;
    public VelocityTracker r;
    public int s;
    public int t;
    public boolean u;
    public final ViewDragHelper.Callback v;

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view2, float f);

        public abstract void onStateChanged(@NonNull View view2, int i);
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public final int b;

        public static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        }

        public SavedState(Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.b);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.b = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.b = i;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface State {
    }

    public class a implements Runnable {
        public final /* synthetic */ View a;
        public final /* synthetic */ int b;

        public a(View view2, int i) {
            this.a = view2;
            this.b = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            BottomSheetBehavior.this.a(this.a, this.b);
        }
    }

    public class b extends ViewDragHelper.Callback {
        public b() {
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view2, int i, int i2) {
            return view2.getLeft();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view2, int i, int i2) {
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            return MathUtils.clamp(i, bottomSheetBehavior.e, bottomSheetBehavior.g ? bottomSheetBehavior.n : bottomSheetBehavior.f);
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int getViewVerticalDragRange(View view2) {
            int i;
            int i2;
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            if (bottomSheetBehavior.g) {
                i = bottomSheetBehavior.n;
                i2 = bottomSheetBehavior.e;
            } else {
                i = bottomSheetBehavior.f;
                i2 = bottomSheetBehavior.e;
            }
            return i - i2;
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            if (i == 1) {
                BottomSheetBehavior.this.b(1);
            }
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view2, int i, int i2, int i3, int i4) {
            BottomSheetBehavior.this.a(i2);
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0058  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0069  */
        @Override // android.support.v4.widget.ViewDragHelper.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onViewReleased(android.view.View r5, float r6, float r7) {
            /*
                r4 = this;
                r6 = 0
                int r0 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
                r1 = 4
                r2 = 3
                if (r0 >= 0) goto Ld
                android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                int r6 = r6.e
            Lb:
                r1 = r2
                goto L4a
            Ld:
                android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                boolean r3 = r0.g
                if (r3 == 0) goto L1f
                boolean r0 = r0.a(r5, r7)
                if (r0 == 0) goto L1f
                android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                int r6 = r6.n
                r1 = 5
                goto L4a
            L1f:
                int r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
                if (r6 != 0) goto L46
                int r6 = r5.getTop()
                android.support.design.widget.BottomSheetBehavior r7 = android.support.design.widget.BottomSheetBehavior.this
                int r7 = r7.e
                int r7 = r6 - r7
                int r7 = java.lang.Math.abs(r7)
                android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                int r0 = r0.f
                int r6 = r6 - r0
                int r6 = java.lang.Math.abs(r6)
                if (r7 >= r6) goto L41
                android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                int r6 = r6.e
                goto Lb
            L41:
                android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                int r6 = r6.f
                goto L4a
            L46:
                android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                int r6 = r6.f
            L4a:
                android.support.design.widget.BottomSheetBehavior r7 = android.support.design.widget.BottomSheetBehavior.this
                android.support.v4.widget.ViewDragHelper r7 = r7.j
                int r0 = r5.getLeft()
                boolean r6 = r7.settleCapturedViewAt(r0, r6)
                if (r6 == 0) goto L69
                android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                r7 = 2
                r6.b(r7)
                android.support.design.widget.BottomSheetBehavior$c r6 = new android.support.design.widget.BottomSheetBehavior$c
                android.support.design.widget.BottomSheetBehavior r7 = android.support.design.widget.BottomSheetBehavior.this
                r6.<init>(r5, r1)
                android.support.v4.view.ViewCompat.postOnAnimation(r5, r6)
                goto L6e
            L69:
                android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                r5.b(r1)
            L6e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.b.onViewReleased(android.view.View, float, float):void");
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view2, int i) {
            View view3;
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            int i2 = bottomSheetBehavior.i;
            if (i2 == 1 || bottomSheetBehavior.u) {
                return false;
            }
            if (i2 == 3 && bottomSheetBehavior.s == i && (view3 = bottomSheetBehavior.p.get()) != null && view3.canScrollVertically(-1)) {
                return false;
            }
            WeakReference<V> weakReference = BottomSheetBehavior.this.o;
            return weakReference != null && weakReference.get() == view2;
        }
    }

    public class c implements Runnable {
        public final View a;
        public final int b;

        public c(View view2, int i) {
            this.a = view2;
            this.b = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewDragHelper viewDragHelper = BottomSheetBehavior.this.j;
            if (viewDragHelper == null || !viewDragHelper.continueSettling(true)) {
                BottomSheetBehavior.this.b(this.b);
            } else {
                ViewCompat.postOnAnimation(this.a, this);
            }
        }
    }

    public BottomSheetBehavior() {
        this.i = 4;
        this.v = new b();
    }

    public static <V extends View> BottomSheetBehavior<V> from(V v) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    }

    public boolean a(View view2, float f) {
        if (this.h) {
            return true;
        }
        if (view2.getTop() < this.f) {
            return false;
        }
        return Math.abs(((f * 0.1f) + ((float) view2.getTop())) - ((float) this.f)) / ((float) this.b) > 0.5f;
    }

    public void b(int i) {
        BottomSheetCallback bottomSheetCallback;
        if (this.i == i) {
            return;
        }
        this.i = i;
        if (this.o.get() == null || (bottomSheetCallback = this.q) == null) {
            return;
        }
        BottomSheetDialog.d dVar = (BottomSheetDialog.d) bottomSheetCallback;
        if (dVar == null) {
            throw null;
        }
        if (i == 5) {
            BottomSheetDialog.this.cancel();
        }
    }

    public final int getPeekHeight() {
        if (this.c) {
            return -1;
        }
        return this.b;
    }

    public boolean getSkipCollapsed() {
        return this.h;
    }

    public final int getState() {
        return this.i;
    }

    public boolean isHideable() {
        return this.g;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (!v.isShown()) {
            this.k = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.s = -1;
            VelocityTracker velocityTracker = this.r;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.r = null;
            }
        }
        if (this.r == null) {
            this.r = VelocityTracker.obtain();
        }
        this.r.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x = (int) motionEvent.getX();
            this.t = (int) motionEvent.getY();
            WeakReference<View> weakReference = this.p;
            View view2 = weakReference != null ? weakReference.get() : null;
            if (view2 != null && coordinatorLayout.isPointInChildBounds(view2, x, this.t)) {
                this.s = motionEvent.getPointerId(motionEvent.getActionIndex());
                this.u = true;
            }
            this.k = this.s == -1 && !coordinatorLayout.isPointInChildBounds(v, x, this.t);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.u = false;
            this.s = -1;
            if (this.k) {
                this.k = false;
                return false;
            }
        }
        if (!this.k && this.j.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        View view3 = this.p.get();
        return (actionMasked != 2 || view3 == null || this.k || this.i == 1 || coordinatorLayout.isPointInChildBounds(view3, (int) motionEvent.getX(), (int) motionEvent.getY()) || Math.abs(((float) this.t) - motionEvent.getY()) <= ((float) this.j.getTouchSlop())) ? false : true;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        int iMax;
        if (ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v)) {
            ViewCompat.setFitsSystemWindows(v, true);
        }
        int top = v.getTop();
        coordinatorLayout.onLayoutChild(v, i);
        this.n = coordinatorLayout.getHeight();
        if (this.c) {
            if (this.d == 0) {
                this.d = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            }
            iMax = Math.max(this.d, this.n - ((coordinatorLayout.getWidth() * 9) / 16));
        } else {
            iMax = this.b;
        }
        int iMax2 = Math.max(0, this.n - v.getHeight());
        this.e = iMax2;
        this.f = Math.max(this.n - iMax, iMax2);
        int i2 = this.i;
        if (i2 == 3) {
            ViewCompat.offsetTopAndBottom(v, this.e);
        } else if (this.g && i2 == 5) {
            ViewCompat.offsetTopAndBottom(v, this.n);
        } else {
            int i3 = this.i;
            if (i3 == 4) {
                ViewCompat.offsetTopAndBottom(v, this.f);
            } else if (i3 == 1 || i3 == 2) {
                ViewCompat.offsetTopAndBottom(v, top - v.getTop());
            }
        }
        if (this.j == null) {
            this.j = ViewDragHelper.create(coordinatorLayout, this.v);
        }
        this.o = new WeakReference<>(v);
        this.p = new WeakReference<>(a(v));
        return true;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v, View view2, float f, float f2) {
        return view2 == this.p.get() && (this.i != 3 || super.onNestedPreFling(coordinatorLayout, v, view2, f, f2));
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view2, int i, int i2, int[] iArr) {
        if (view2 != this.p.get()) {
            return;
        }
        int top = v.getTop();
        int i3 = top - i2;
        if (i2 > 0) {
            int i4 = this.e;
            if (i3 < i4) {
                iArr[1] = top - i4;
                ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                b(3);
            } else {
                iArr[1] = i2;
                ViewCompat.offsetTopAndBottom(v, -i2);
                b(1);
            }
        } else if (i2 < 0 && !view2.canScrollVertically(-1)) {
            int i5 = this.f;
            if (i3 <= i5 || this.g) {
                iArr[1] = i2;
                ViewCompat.offsetTopAndBottom(v, -i2);
                b(1);
            } else {
                iArr[1] = top - i5;
                ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                b(4);
            }
        }
        a(v.getTop());
        this.l = i2;
        this.m = true;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v, savedState.getSuperState());
        int i = savedState.b;
        if (i == 1 || i == 2) {
            this.i = 4;
        } else {
            this.i = i;
        }
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v), this.i);
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view2, View view3, int i) {
        this.l = 0;
        this.m = false;
        return (i & 2) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0043  */
    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onStopNestedScroll(android.support.design.widget.CoordinatorLayout r4, V r5, android.view.View r6) {
        /*
            r3 = this;
            int r4 = r5.getTop()
            int r0 = r3.e
            r1 = 3
            if (r4 != r0) goto Ld
            r3.b(r1)
            return
        Ld:
            java.lang.ref.WeakReference<android.view.View> r4 = r3.p
            if (r4 == 0) goto L84
            java.lang.Object r4 = r4.get()
            if (r6 != r4) goto L84
            boolean r4 = r3.m
            if (r4 != 0) goto L1c
            goto L84
        L1c:
            int r4 = r3.l
            r6 = 4
            if (r4 <= 0) goto L24
            int r4 = r3.e
            goto L65
        L24:
            boolean r4 = r3.g
            if (r4 == 0) goto L43
            android.view.VelocityTracker r4 = r3.r
            float r0 = r3.a
            r2 = 1000(0x3e8, float:1.401E-42)
            r4.computeCurrentVelocity(r2, r0)
            android.view.VelocityTracker r4 = r3.r
            int r0 = r3.s
            float r4 = r4.getYVelocity(r0)
            boolean r4 = r3.a(r5, r4)
            if (r4 == 0) goto L43
            int r4 = r3.n
            r1 = 5
            goto L65
        L43:
            int r4 = r3.l
            if (r4 != 0) goto L62
            int r4 = r5.getTop()
            int r0 = r3.e
            int r0 = r4 - r0
            int r0 = java.lang.Math.abs(r0)
            int r2 = r3.f
            int r4 = r4 - r2
            int r4 = java.lang.Math.abs(r4)
            if (r0 >= r4) goto L5f
            int r4 = r3.e
            goto L65
        L5f:
            int r4 = r3.f
            goto L64
        L62:
            int r4 = r3.f
        L64:
            r1 = r6
        L65:
            android.support.v4.widget.ViewDragHelper r6 = r3.j
            int r0 = r5.getLeft()
            boolean r4 = r6.smoothSlideViewTo(r5, r0, r4)
            if (r4 == 0) goto L7e
            r4 = 2
            r3.b(r4)
            android.support.design.widget.BottomSheetBehavior$c r4 = new android.support.design.widget.BottomSheetBehavior$c
            r4.<init>(r5, r1)
            android.support.v4.view.ViewCompat.postOnAnimation(r5, r4)
            goto L81
        L7e:
            r3.b(r1)
        L81:
            r4 = 0
            r3.m = r4
        L84:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.onStopNestedScroll(android.support.design.widget.CoordinatorLayout, android.view.View, android.view.View):void");
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (!v.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.i == 1 && actionMasked == 0) {
            return true;
        }
        ViewDragHelper viewDragHelper = this.j;
        if (viewDragHelper != null) {
            viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            this.s = -1;
            VelocityTracker velocityTracker = this.r;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.r = null;
            }
        }
        if (this.r == null) {
            this.r = VelocityTracker.obtain();
        }
        this.r.addMovement(motionEvent);
        if (actionMasked == 2 && !this.k && Math.abs(this.t - motionEvent.getY()) > this.j.getTouchSlop()) {
            this.j.captureChildView(v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.k;
    }

    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.q = bottomSheetCallback;
    }

    public void setHideable(boolean z) {
        this.g = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setPeekHeight(int r4) {
        /*
            r3 = this;
            r0 = 1
            r1 = 0
            r2 = -1
            if (r4 != r2) goto Lc
            boolean r4 = r3.c
            if (r4 != 0) goto L15
            r3.c = r0
            goto L24
        Lc:
            boolean r2 = r3.c
            if (r2 != 0) goto L17
            int r2 = r3.b
            if (r2 == r4) goto L15
            goto L17
        L15:
            r0 = r1
            goto L24
        L17:
            r3.c = r1
            int r1 = java.lang.Math.max(r1, r4)
            r3.b = r1
            int r1 = r3.n
            int r1 = r1 - r4
            r3.f = r1
        L24:
            if (r0 == 0) goto L3a
            int r4 = r3.i
            r0 = 4
            if (r4 != r0) goto L3a
            java.lang.ref.WeakReference<V extends android.view.View> r4 = r3.o
            if (r4 == 0) goto L3a
            java.lang.Object r4 = r4.get()
            android.view.View r4 = (android.view.View) r4
            if (r4 == 0) goto L3a
            r4.requestLayout()
        L3a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.setPeekHeight(int):void");
    }

    public void setSkipCollapsed(boolean z) {
        this.h = z;
    }

    public final void setState(int i) {
        if (i == this.i) {
            return;
        }
        WeakReference<V> weakReference = this.o;
        if (weakReference == null) {
            if (i == 4 || i == 3 || (this.g && i == 5)) {
                this.i = i;
                return;
            }
            return;
        }
        V v = weakReference.get();
        if (v == null) {
            return;
        }
        ViewParent parent = v.getParent();
        if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(v)) {
            v.post(new a(v, i));
        } else {
            a((View) v, i);
        }
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        int i;
        super(context, attributeSet);
        this.i = 4;
        this.v = new b();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Layout);
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (typedValuePeekValue != null && (i = typedValuePeekValue.data) == -1) {
            setPeekHeight(i);
        } else {
            setPeekHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        }
        setHideable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setSkipCollapsed(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        typedArrayObtainStyledAttributes.recycle();
        this.a = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    @VisibleForTesting
    public View a(View view2) {
        if (ViewCompat.isNestedScrollingEnabled(view2)) {
            return view2;
        }
        if (!(view2 instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view2;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View viewA = a(viewGroup.getChildAt(i));
            if (viewA != null) {
                return viewA;
            }
        }
        return null;
    }

    public void a(View view2, int i) {
        int i2;
        if (i == 4) {
            i2 = this.f;
        } else if (i == 3) {
            i2 = this.e;
        } else if (this.g && i == 5) {
            i2 = this.n;
        } else {
            throw new IllegalArgumentException(g9.b("Illegal state argument: ", i));
        }
        if (this.j.smoothSlideViewTo(view2, view2.getLeft(), i2)) {
            b(2);
            ViewCompat.postOnAnimation(view2, new c(view2, i));
        } else {
            b(i);
        }
    }

    public void a(int i) {
        BottomSheetCallback bottomSheetCallback;
        if (this.o.get() == null || (bottomSheetCallback = this.q) == null) {
            return;
        }
        if (i > this.f) {
        }
    }
}
