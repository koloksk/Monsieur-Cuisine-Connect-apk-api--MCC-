package android.support.v4.widget;

import android.R;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import defpackage.g9;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class DrawerLayout extends ViewGroup {
    public static final int[] I = {R.attr.colorPrimaryDark};
    public static final int[] J = {R.attr.layout_gravity};
    public static final boolean K = true;
    public static final int LOCK_MODE_LOCKED_CLOSED = 1;
    public static final int LOCK_MODE_LOCKED_OPEN = 2;
    public static final int LOCK_MODE_UNDEFINED = 3;
    public static final int LOCK_MODE_UNLOCKED = 0;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    public CharSequence A;
    public Object B;
    public boolean C;
    public Drawable D;
    public Drawable E;
    public Drawable F;
    public Drawable G;
    public final ArrayList<View> H;
    public final c a;
    public float b;
    public int c;
    public int d;
    public float e;
    public Paint f;
    public final ViewDragHelper g;
    public final ViewDragHelper h;
    public final d i;
    public final d j;
    public int k;
    public boolean l;
    public boolean m;
    public int n;
    public int o;
    public int p;
    public int q;
    public boolean r;

    @Nullable
    public DrawerListener s;
    public List<DrawerListener> t;
    public float u;
    public float v;
    public Drawable w;
    public Drawable x;
    public Drawable y;
    public CharSequence z;

    public interface DrawerListener {
        void onDrawerClosed(@NonNull View view2);

        void onDrawerOpened(@NonNull View view2);

        void onDrawerSlide(@NonNull View view2, float f);

        void onDrawerStateChanged(int i);
    }

    public static abstract class SimpleDrawerListener implements DrawerListener {
        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        public void onDrawerClosed(View view2) {
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        public void onDrawerOpened(View view2) {
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        public void onDrawerSlide(View view2, float f) {
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        public void onDrawerStateChanged(int i) {
        }
    }

    public class a implements View.OnApplyWindowInsetsListener {
        public a(DrawerLayout drawerLayout) {
        }

        @Override // android.view.View.OnApplyWindowInsetsListener
        @TargetApi(21)
        public WindowInsets onApplyWindowInsets(View view2, WindowInsets windowInsets) {
            ((DrawerLayout) view2).setChildInsets(windowInsets, windowInsets.getSystemWindowInsetTop() > 0);
            return windowInsets.consumeSystemWindowInsets();
        }
    }

    public class b extends AccessibilityDelegateCompat {
        public final Rect d = new Rect();

        public b() {
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public boolean dispatchPopulateAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() != 32) {
                return super.dispatchPopulateAccessibilityEvent(view2, accessibilityEvent);
            }
            List<CharSequence> text = accessibilityEvent.getText();
            View viewB = DrawerLayout.this.b();
            if (viewB == null) {
                return true;
            }
            CharSequence drawerTitle = DrawerLayout.this.getDrawerTitle(DrawerLayout.this.a(viewB));
            if (drawerTitle == null) {
                return true;
            }
            text.add(drawerTitle);
            return true;
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view2, accessibilityEvent);
            accessibilityEvent.setClassName(DrawerLayout.class.getName());
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (DrawerLayout.K) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
            } else {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompatObtain);
                accessibilityNodeInfoCompat.setSource(view2);
                Object parentForAccessibility = ViewCompat.getParentForAccessibility(view2);
                if (parentForAccessibility instanceof View) {
                    accessibilityNodeInfoCompat.setParent((View) parentForAccessibility);
                }
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
                accessibilityNodeInfoCompatObtain.recycle();
                ViewGroup viewGroup = (ViewGroup) view2;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (DrawerLayout.d(childAt)) {
                        accessibilityNodeInfoCompat.addChild(childAt);
                    }
                }
            }
            accessibilityNodeInfoCompat.setClassName(DrawerLayout.class.getName());
            accessibilityNodeInfoCompat.setFocusable(false);
            accessibilityNodeInfoCompat.setFocused(false);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view2, AccessibilityEvent accessibilityEvent) {
            if (DrawerLayout.K || DrawerLayout.d(view2)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view2, accessibilityEvent);
            }
            return false;
        }
    }

    public static final class c extends AccessibilityDelegateCompat {
        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
            if (DrawerLayout.d(view2)) {
                return;
            }
            accessibilityNodeInfoCompat.setParent(null);
        }
    }

    public class d extends ViewDragHelper.Callback {
        public final int a;
        public ViewDragHelper b;
        public final Runnable c = new a();

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                View viewA;
                int width;
                d dVar = d.this;
                int edgeSize = dVar.b.getEdgeSize();
                boolean z = dVar.a == 3;
                if (z) {
                    viewA = DrawerLayout.this.a(3);
                    width = (viewA != null ? -viewA.getWidth() : 0) + edgeSize;
                } else {
                    viewA = DrawerLayout.this.a(5);
                    width = DrawerLayout.this.getWidth() - edgeSize;
                }
                if (viewA != null) {
                    if (((!z || viewA.getLeft() >= width) && (z || viewA.getLeft() <= width)) || DrawerLayout.this.getDrawerLockMode(viewA) != 0) {
                        return;
                    }
                    LayoutParams layoutParams = (LayoutParams) viewA.getLayoutParams();
                    dVar.b.smoothSlideViewTo(viewA, width, viewA.getTop());
                    layoutParams.b = true;
                    DrawerLayout.this.invalidate();
                    dVar.a();
                    DrawerLayout drawerLayout = DrawerLayout.this;
                    if (drawerLayout.r) {
                        return;
                    }
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                    int childCount = drawerLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        drawerLayout.getChildAt(i).dispatchTouchEvent(motionEventObtain);
                    }
                    motionEventObtain.recycle();
                    drawerLayout.r = true;
                }
            }
        }

        public d(int i) {
            this.a = i;
        }

        public final void a() {
            View viewA = DrawerLayout.this.a(this.a == 3 ? 5 : 3);
            if (viewA != null) {
                DrawerLayout.this.closeDrawer(viewA);
            }
        }

        public void b() {
            DrawerLayout.this.removeCallbacks(this.c);
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view2, int i, int i2) {
            if (DrawerLayout.this.a(view2, 3)) {
                return Math.max(-view2.getWidth(), Math.min(i, 0));
            }
            int width = DrawerLayout.this.getWidth();
            return Math.max(width - view2.getWidth(), Math.min(i, width));
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view2, int i, int i2) {
            return view2.getTop();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view2) {
            if (DrawerLayout.this.c(view2)) {
                return view2.getWidth();
            }
            return 0;
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onEdgeDragStarted(int i, int i2) {
            View viewA = (i & 1) == 1 ? DrawerLayout.this.a(3) : DrawerLayout.this.a(5);
            if (viewA == null || DrawerLayout.this.getDrawerLockMode(viewA) != 0) {
                return;
            }
            this.b.captureChildView(viewA, i2);
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public boolean onEdgeLock(int i) {
            return false;
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onEdgeTouched(int i, int i2) {
            DrawerLayout.this.postDelayed(this.c, 160L);
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewCaptured(View view2, int i) {
            ((LayoutParams) view2.getLayoutParams()).b = false;
            a();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            DrawerLayout.this.a(i, this.b.getCapturedView());
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view2, int i, int i2, int i3, int i4) {
            float width = (DrawerLayout.this.a(view2, 3) ? i + r3 : DrawerLayout.this.getWidth() - i) / view2.getWidth();
            DrawerLayout.this.b(view2, width);
            view2.setVisibility(width == 0.0f ? 4 : 0);
            DrawerLayout.this.invalidate();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewReleased(View view2, float f, float f2) {
            int i;
            if (DrawerLayout.this == null) {
                throw null;
            }
            float f3 = ((LayoutParams) view2.getLayoutParams()).a;
            int width = view2.getWidth();
            if (DrawerLayout.this.a(view2, 3)) {
                i = (f > 0.0f || (f == 0.0f && f3 > 0.5f)) ? 0 : -width;
            } else {
                int width2 = DrawerLayout.this.getWidth();
                if (f < 0.0f || (f == 0.0f && f3 > 0.5f)) {
                    width2 -= width;
                }
                i = width2;
            }
            this.b.settleCapturedViewAt(i, view2.getTop());
            DrawerLayout.this.invalidate();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view2, int i) {
            return DrawerLayout.this.c(view2) && DrawerLayout.this.a(view2, this.a) && DrawerLayout.this.getDrawerLockMode(view2) == 0;
        }
    }

    public DrawerLayout(@NonNull Context context) {
        this(context, null);
    }

    public static boolean d(View view2) {
        return (ViewCompat.getImportantForAccessibility(view2) == 4 || ViewCompat.getImportantForAccessibility(view2) == 2) ? false : true;
    }

    public void a(int i, View view2) {
        View rootView;
        int viewDragState = this.g.getViewDragState();
        int viewDragState2 = this.h.getViewDragState();
        int i2 = 2;
        if (viewDragState == 1 || viewDragState2 == 1) {
            i2 = 1;
        } else if (viewDragState != 2 && viewDragState2 != 2) {
            i2 = 0;
        }
        if (view2 != null && i == 0) {
            float f = ((LayoutParams) view2.getLayoutParams()).a;
            if (f == 0.0f) {
                LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
                if ((layoutParams.c & 1) == 1) {
                    layoutParams.c = 0;
                    List<DrawerListener> list = this.t;
                    if (list != null) {
                        for (int size = list.size() - 1; size >= 0; size--) {
                            this.t.get(size).onDrawerClosed(view2);
                        }
                    }
                    a(view2, false);
                    if (hasWindowFocus() && (rootView = getRootView()) != null) {
                        rootView.sendAccessibilityEvent(32);
                    }
                }
            } else if (f == 1.0f) {
                LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
                if ((layoutParams2.c & 1) == 0) {
                    layoutParams2.c = 1;
                    List<DrawerListener> list2 = this.t;
                    if (list2 != null) {
                        for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                            this.t.get(size2).onDrawerOpened(view2);
                        }
                    }
                    a(view2, true);
                    if (hasWindowFocus()) {
                        sendAccessibilityEvent(32);
                    }
                }
            }
        }
        if (i2 != this.k) {
            this.k = i2;
            List<DrawerListener> list3 = this.t;
            if (list3 != null) {
                for (int size3 = list3.size() - 1; size3 >= 0; size3--) {
                    this.t.get(size3).onDrawerStateChanged(i2);
                }
            }
        }
    }

    public void addDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener == null) {
            return;
        }
        if (this.t == null) {
            this.t = new ArrayList();
        }
        this.t.add(drawerListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        if (getDescendantFocusability() == 393216) {
            return;
        }
        int childCount = getChildCount();
        boolean z = false;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (!c(childAt)) {
                this.H.add(childAt);
            } else if (isDrawerOpen(childAt)) {
                childAt.addFocusables(arrayList, i, i2);
                z = true;
            }
        }
        if (!z) {
            int size = this.H.size();
            for (int i4 = 0; i4 < size; i4++) {
                View view2 = this.H.get(i4);
                if (view2.getVisibility() == 0) {
                    view2.addFocusables(arrayList, i, i2);
                }
            }
        }
        this.H.clear();
    }

    @Override // android.view.ViewGroup
    public void addView(View view2, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view2, i, layoutParams);
        if (a() != null || c(view2)) {
            ViewCompat.setImportantForAccessibility(view2, 4);
        } else {
            ViewCompat.setImportantForAccessibility(view2, 1);
        }
    }

    public void b(View view2, float f) {
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        if (f == layoutParams.a) {
            return;
        }
        layoutParams.a = f;
        List<DrawerListener> list = this.t;
        if (list == null) {
            return;
        }
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                this.t.get(size).onDrawerSlide(view2, f);
            }
        }
    }

    public boolean c(View view2) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(((LayoutParams) view2.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(view2));
        return ((absoluteGravity & 3) == 0 && (absoluteGravity & 5) == 0) ? false : true;
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public void closeDrawer(@NonNull View view2) {
        closeDrawer(view2, true);
    }

    public void closeDrawers() {
        a(false);
    }

    @Override // android.view.View
    public void computeScroll() {
        int childCount = getChildCount();
        float fMax = 0.0f;
        for (int i = 0; i < childCount; i++) {
            fMax = Math.max(fMax, ((LayoutParams) getChildAt(i).getLayoutParams()).a);
        }
        this.e = fMax;
        boolean zContinueSettling = this.g.continueSettling(true);
        boolean zContinueSettling2 = this.h.continueSettling(true);
        if (zContinueSettling || zContinueSettling2) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view2, long j) {
        int height = getHeight();
        boolean zB = b(view2);
        int width = getWidth();
        int iSave = canvas.save();
        int i = 0;
        if (zB) {
            int childCount = getChildCount();
            int i2 = 0;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt != view2 && childAt.getVisibility() == 0) {
                    Drawable background = childAt.getBackground();
                    if ((background != null && background.getOpacity() == -1) && c(childAt) && childAt.getHeight() >= height) {
                        if (a(childAt, 3)) {
                            int right = childAt.getRight();
                            if (right > i2) {
                                i2 = right;
                            }
                        } else {
                            int left = childAt.getLeft();
                            if (left < width) {
                                width = left;
                            }
                        }
                    }
                }
            }
            canvas.clipRect(i2, 0, width, getHeight());
            i = i2;
        }
        boolean zDrawChild = super.drawChild(canvas, view2, j);
        canvas.restoreToCount(iSave);
        float f = this.e;
        if (f > 0.0f && zB) {
            this.f.setColor((this.d & ViewCompat.MEASURED_SIZE_MASK) | (((int) ((((-16777216) & r2) >>> 24) * f)) << 24));
            canvas.drawRect(i, 0.0f, width, getHeight(), this.f);
        } else if (this.x != null && a(view2, 3)) {
            int intrinsicWidth = this.x.getIntrinsicWidth();
            int right2 = view2.getRight();
            float fMax = Math.max(0.0f, Math.min(right2 / this.g.getEdgeSize(), 1.0f));
            this.x.setBounds(right2, view2.getTop(), intrinsicWidth + right2, view2.getBottom());
            this.x.setAlpha((int) (fMax * 255.0f));
            this.x.draw(canvas);
        } else if (this.y != null && a(view2, 5)) {
            int intrinsicWidth2 = this.y.getIntrinsicWidth();
            int left2 = view2.getLeft();
            float fMax2 = Math.max(0.0f, Math.min((getWidth() - left2) / this.h.getEdgeSize(), 1.0f));
            this.y.setBounds(left2 - intrinsicWidth2, view2.getTop(), left2, view2.getBottom());
            this.y.setAlpha((int) (fMax2 * 255.0f));
            this.y.draw(canvas);
        }
        return zDrawChild;
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public float getDrawerElevation() {
        return this.b;
    }

    public int getDrawerLockMode(int i) {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (i == 3) {
            int i2 = this.n;
            if (i2 != 3) {
                return i2;
            }
            int i3 = layoutDirection == 0 ? this.p : this.q;
            if (i3 != 3) {
                return i3;
            }
            return 0;
        }
        if (i == 5) {
            int i4 = this.o;
            if (i4 != 3) {
                return i4;
            }
            int i5 = layoutDirection == 0 ? this.q : this.p;
            if (i5 != 3) {
                return i5;
            }
            return 0;
        }
        if (i == 8388611) {
            int i6 = this.p;
            if (i6 != 3) {
                return i6;
            }
            int i7 = layoutDirection == 0 ? this.n : this.o;
            if (i7 != 3) {
                return i7;
            }
            return 0;
        }
        if (i != 8388613) {
            return 0;
        }
        int i8 = this.q;
        if (i8 != 3) {
            return i8;
        }
        int i9 = layoutDirection == 0 ? this.o : this.n;
        if (i9 != 3) {
            return i9;
        }
        return 0;
    }

    @Nullable
    public CharSequence getDrawerTitle(int i) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity == 3) {
            return this.z;
        }
        if (absoluteGravity == 5) {
            return this.A;
        }
        return null;
    }

    @Nullable
    public Drawable getStatusBarBackgroundDrawable() {
        return this.w;
    }

    public boolean isDrawerOpen(@NonNull View view2) {
        if (c(view2)) {
            return (((LayoutParams) view2.getLayoutParams()).c & 1) == 1;
        }
        throw new IllegalArgumentException("View " + view2 + " is not a drawer");
    }

    public boolean isDrawerVisible(@NonNull View view2) {
        if (c(view2)) {
            return ((LayoutParams) view2.getLayoutParams()).a > 0.0f;
        }
        throw new IllegalArgumentException("View " + view2 + " is not a drawer");
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.m = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.m = true;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.C || this.w == null) {
            return;
        }
        Object obj = this.B;
        int systemWindowInsetTop = obj != null ? ((WindowInsets) obj).getSystemWindowInsetTop() : 0;
        if (systemWindowInsetTop > 0) {
            this.w.setBounds(0, 0, getWidth(), systemWindowInsetTop);
            this.w.draw(canvas);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0031  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            int r0 = r7.getActionMasked()
            android.support.v4.widget.ViewDragHelper r1 = r6.g
            boolean r1 = r1.shouldInterceptTouchEvent(r7)
            android.support.v4.widget.ViewDragHelper r2 = r6.h
            boolean r2 = r2.shouldInterceptTouchEvent(r7)
            r1 = r1 | r2
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L38
            if (r0 == r3) goto L31
            r7 = 2
            r4 = 3
            if (r0 == r7) goto L1e
            if (r0 == r4) goto L31
            goto L36
        L1e:
            android.support.v4.widget.ViewDragHelper r7 = r6.g
            boolean r7 = r7.checkTouchSlop(r4)
            if (r7 == 0) goto L36
            android.support.v4.widget.DrawerLayout$d r7 = r6.i
            r7.b()
            android.support.v4.widget.DrawerLayout$d r7 = r6.j
            r7.b()
            goto L36
        L31:
            r6.a(r3)
            r6.r = r2
        L36:
            r7 = r2
            goto L60
        L38:
            float r0 = r7.getX()
            float r7 = r7.getY()
            r6.u = r0
            r6.v = r7
            float r4 = r6.e
            r5 = 0
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L5d
            android.support.v4.widget.ViewDragHelper r4 = r6.g
            int r0 = (int) r0
            int r7 = (int) r7
            android.view.View r7 = r4.findTopChildUnder(r0, r7)
            if (r7 == 0) goto L5d
            boolean r7 = r6.b(r7)
            if (r7 == 0) goto L5d
            r7 = r3
            goto L5e
        L5d:
            r7 = r2
        L5e:
            r6.r = r2
        L60:
            if (r1 != 0) goto L85
            if (r7 != 0) goto L85
            int r7 = r6.getChildCount()
            r0 = r2
        L69:
            if (r0 >= r7) goto L7e
            android.view.View r1 = r6.getChildAt(r0)
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            android.support.v4.widget.DrawerLayout$LayoutParams r1 = (android.support.v4.widget.DrawerLayout.LayoutParams) r1
            boolean r1 = r1.b
            if (r1 == 0) goto L7b
            r7 = r3
            goto L7f
        L7b:
            int r0 = r0 + 1
            goto L69
        L7e:
            r7 = r2
        L7f:
            if (r7 != 0) goto L85
            boolean r7 = r6.r
            if (r7 == 0) goto L86
        L85:
            r2 = r3
        L86:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.DrawerLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (b() != null) {
                keyEvent.startTracking();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyUp(i, keyEvent);
        }
        View viewB = b();
        if (viewB != null && getDrawerLockMode(viewB) == 0) {
            closeDrawers();
        }
        return viewB != null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        int i5;
        boolean z2 = true;
        this.l = true;
        int i6 = i3 - i;
        int childCount = getChildCount();
        int i7 = 0;
        while (i7 < childCount) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (b(childAt)) {
                    int i8 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    childAt.layout(i8, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, childAt.getMeasuredWidth() + i8, childAt.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin);
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (a(childAt, 3)) {
                        float f2 = measuredWidth;
                        i5 = (-measuredWidth) + ((int) (layoutParams.a * f2));
                        f = (measuredWidth + i5) / f2;
                    } else {
                        float f3 = measuredWidth;
                        f = (i6 - r11) / f3;
                        i5 = i6 - ((int) (layoutParams.a * f3));
                    }
                    boolean z3 = f != layoutParams.a ? z2 : false;
                    int i9 = layoutParams.gravity & 112;
                    if (i9 == 16) {
                        int i10 = i4 - i2;
                        int i11 = (i10 - measuredHeight) / 2;
                        int i12 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                        if (i11 < i12) {
                            i11 = i12;
                        } else {
                            int i13 = i11 + measuredHeight;
                            int i14 = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                            if (i13 > i10 - i14) {
                                i11 = (i10 - i14) - measuredHeight;
                            }
                        }
                        childAt.layout(i5, i11, measuredWidth + i5, measuredHeight + i11);
                    } else if (i9 != 80) {
                        int i15 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                        childAt.layout(i5, i15, measuredWidth + i5, measuredHeight + i15);
                    } else {
                        int i16 = i4 - i2;
                        childAt.layout(i5, (i16 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) - childAt.getMeasuredHeight(), measuredWidth + i5, i16 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                    }
                    if (z3) {
                        b(childAt, f);
                    }
                    int i17 = layoutParams.a > 0.0f ? 0 : 4;
                    if (childAt.getVisibility() != i17) {
                        childAt.setVisibility(i17);
                    }
                }
            }
            i7++;
            z2 = true;
        }
        this.l = false;
        this.m = false;
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824 || mode2 != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
            if (mode != Integer.MIN_VALUE && mode == 0) {
                size = 300;
            }
            if (mode2 != Integer.MIN_VALUE && mode2 == 0) {
                size2 = 300;
            }
        }
        setMeasuredDimension(size, size2);
        int i3 = 0;
        boolean z = this.B != null && ViewCompat.getFitsSystemWindows(this);
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int childCount = getChildCount();
        int i4 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i3 < childCount) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (z) {
                    int absoluteGravity = GravityCompat.getAbsoluteGravity(layoutParams.gravity, layoutDirection);
                    if (ViewCompat.getFitsSystemWindows(childAt)) {
                        WindowInsets windowInsetsReplaceSystemWindowInsets = (WindowInsets) this.B;
                        if (absoluteGravity == 3) {
                            windowInsetsReplaceSystemWindowInsets = windowInsetsReplaceSystemWindowInsets.replaceSystemWindowInsets(windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetLeft(), windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetTop(), i4, windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetBottom());
                        } else if (absoluteGravity == 5) {
                            windowInsetsReplaceSystemWindowInsets = windowInsetsReplaceSystemWindowInsets.replaceSystemWindowInsets(i4, windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetTop(), windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetRight(), windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetBottom());
                        }
                        childAt.dispatchApplyWindowInsets(windowInsetsReplaceSystemWindowInsets);
                    } else {
                        WindowInsets windowInsetsReplaceSystemWindowInsets2 = (WindowInsets) this.B;
                        if (absoluteGravity == 3) {
                            windowInsetsReplaceSystemWindowInsets2 = windowInsetsReplaceSystemWindowInsets2.replaceSystemWindowInsets(windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetLeft(), windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetTop(), i4, windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetBottom());
                        } else if (absoluteGravity == 5) {
                            windowInsetsReplaceSystemWindowInsets2 = windowInsetsReplaceSystemWindowInsets2.replaceSystemWindowInsets(i4, windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetTop(), windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetRight(), windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetBottom());
                        }
                        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetLeft();
                        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetTop();
                        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetRight();
                        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetBottom();
                    }
                }
                if (b(childAt)) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec((size - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec((size2 - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, 1073741824));
                } else {
                    if (!c(childAt)) {
                        throw new IllegalStateException("Child " + childAt + " at index " + i3 + " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
                    }
                    float elevation = ViewCompat.getElevation(childAt);
                    float f = this.b;
                    if (elevation != f) {
                        ViewCompat.setElevation(childAt, f);
                    }
                    int iA = a(childAt) & 7;
                    if (iA == 3) {
                        i4 = 1;
                    }
                    if ((i4 != 0 && z2) || (i4 == 0 && z3)) {
                        StringBuilder sbA = g9.a("Child drawer has absolute gravity ");
                        sbA.append(b(iA));
                        sbA.append(" but this ");
                        sbA.append("DrawerLayout");
                        sbA.append(" already has a ");
                        sbA.append("drawer view along that edge");
                        throw new IllegalStateException(sbA.toString());
                    }
                    if (i4 != 0) {
                        z2 = true;
                    } else {
                        z3 = true;
                    }
                    childAt.measure(ViewGroup.getChildMeasureSpec(i, this.c + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(i2, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, ((ViewGroup.MarginLayoutParams) layoutParams).height));
                }
            }
            i3++;
            i4 = 0;
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        View viewA;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        int i = savedState.b;
        if (i != 0 && (viewA = a(i)) != null) {
            openDrawer(viewA);
        }
        int i2 = savedState.c;
        if (i2 != 3) {
            setDrawerLockMode(i2, 3);
        }
        int i3 = savedState.d;
        if (i3 != 3) {
            setDrawerLockMode(i3, 5);
        }
        int i4 = savedState.e;
        if (i4 != 3) {
            setDrawerLockMode(i4, GravityCompat.START);
        }
        int i5 = savedState.f;
        if (i5 != 3) {
            setDrawerLockMode(i5, 8388613);
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            boolean z = layoutParams.c == 1;
            boolean z2 = layoutParams.c == 2;
            if (z || z2) {
                savedState.b = layoutParams.gravity;
                break;
            }
        }
        savedState.c = this.n;
        savedState.d = this.o;
        savedState.e = this.p;
        savedState.f = this.q;
        return savedState;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005a  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            android.support.v4.widget.ViewDragHelper r0 = r6.g
            r0.processTouchEvent(r7)
            android.support.v4.widget.ViewDragHelper r0 = r6.h
            r0.processTouchEvent(r7)
            int r0 = r7.getAction()
            r0 = r0 & 255(0xff, float:3.57E-43)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L5f
            if (r0 == r2) goto L20
            r7 = 3
            if (r0 == r7) goto L1a
            goto L6d
        L1a:
            r6.a(r2)
            r6.r = r1
            goto L6d
        L20:
            float r0 = r7.getX()
            float r7 = r7.getY()
            android.support.v4.widget.ViewDragHelper r3 = r6.g
            int r4 = (int) r0
            int r5 = (int) r7
            android.view.View r3 = r3.findTopChildUnder(r4, r5)
            if (r3 == 0) goto L5a
            boolean r3 = r6.b(r3)
            if (r3 == 0) goto L5a
            float r3 = r6.u
            float r0 = r0 - r3
            float r3 = r6.v
            float r7 = r7 - r3
            android.support.v4.widget.ViewDragHelper r3 = r6.g
            int r3 = r3.getTouchSlop()
            float r0 = r0 * r0
            float r7 = r7 * r7
            float r7 = r7 + r0
            int r3 = r3 * r3
            float r0 = (float) r3
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r7 >= 0) goto L5a
            android.view.View r7 = r6.a()
            if (r7 == 0) goto L5a
            int r7 = r6.getDrawerLockMode(r7)
            r0 = 2
            if (r7 != r0) goto L5b
        L5a:
            r1 = r2
        L5b:
            r6.a(r1)
            goto L6d
        L5f:
            float r0 = r7.getX()
            float r7 = r7.getY()
            r6.u = r0
            r6.v = r7
            r6.r = r1
        L6d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.DrawerLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void openDrawer(@NonNull View view2) {
        openDrawer(view2, true);
    }

    public void removeDrawerListener(@NonNull DrawerListener drawerListener) {
        List<DrawerListener> list;
        if (drawerListener == null || (list = this.t) == null) {
            return;
        }
        list.remove(drawerListener);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z) {
            a(true);
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.l) {
            return;
        }
        super.requestLayout();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setChildInsets(Object obj, boolean z) {
        this.B = obj;
        this.C = z;
        setWillNotDraw(!z && getBackground() == null);
        requestLayout();
    }

    public void setDrawerElevation(float f) {
        this.b = f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (c(childAt)) {
                ViewCompat.setElevation(childAt, this.b);
            }
        }
    }

    @Deprecated
    public void setDrawerListener(DrawerListener drawerListener) {
        DrawerListener drawerListener2 = this.s;
        if (drawerListener2 != null) {
            removeDrawerListener(drawerListener2);
        }
        if (drawerListener != null) {
            addDrawerListener(drawerListener);
        }
        this.s = drawerListener;
    }

    public void setDrawerLockMode(int i) {
        setDrawerLockMode(i, 3);
        setDrawerLockMode(i, 5);
    }

    public void setDrawerShadow(@DrawableRes int i, int i2) {
        setDrawerShadow(ContextCompat.getDrawable(getContext(), i), i2);
    }

    public void setDrawerShadow(Drawable drawable, int i) {
    }

    public void setDrawerTitle(int i, @Nullable CharSequence charSequence) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity == 3) {
            this.z = charSequence;
        } else if (absoluteGravity == 5) {
            this.A = charSequence;
        }
    }

    public void setScrimColor(@ColorInt int i) {
        this.d = i;
        invalidate();
    }

    public void setStatusBarBackground(@Nullable Drawable drawable) {
        this.w = drawable;
        invalidate();
    }

    public void setStatusBarBackgroundColor(@ColorInt int i) {
        this.w = new ColorDrawable(i);
        invalidate();
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void closeDrawer(@NonNull View view2, boolean z) {
        if (!c(view2)) {
            throw new IllegalArgumentException("View " + view2 + " is not a sliding drawer");
        }
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        if (this.m) {
            layoutParams.a = 0.0f;
            layoutParams.c = 0;
        } else if (z) {
            layoutParams.c |= 4;
            if (a(view2, 3)) {
                this.g.smoothSlideViewTo(view2, -view2.getWidth(), view2.getTop());
            } else {
                this.h.smoothSlideViewTo(view2, getWidth(), view2.getTop());
            }
        } else {
            a(view2, 0.0f);
            int i = layoutParams.gravity;
            a(0, view2);
            view2.setVisibility(4);
        }
        invalidate();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public void openDrawer(@NonNull View view2, boolean z) {
        if (!c(view2)) {
            throw new IllegalArgumentException("View " + view2 + " is not a sliding drawer");
        }
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        if (this.m) {
            layoutParams.a = 1.0f;
            layoutParams.c = 1;
            a(view2, true);
        } else if (z) {
            layoutParams.c |= 2;
            if (a(view2, 3)) {
                this.g.smoothSlideViewTo(view2, 0, view2.getTop());
            } else {
                this.h.smoothSlideViewTo(view2, getWidth() - view2.getWidth(), view2.getTop());
            }
        } else {
            a(view2, 1.0f);
            int i = layoutParams.gravity;
            a(0, view2);
            view2.setVisibility(0);
        }
        invalidate();
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new c();
        this.d = -1728053248;
        this.f = new Paint();
        this.m = true;
        this.n = 3;
        this.o = 3;
        this.p = 3;
        this.q = 3;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = null;
        setDescendantFocusability(262144);
        float f = getResources().getDisplayMetrics().density;
        this.c = (int) ((64.0f * f) + 0.5f);
        float f2 = 400.0f * f;
        this.i = new d(3);
        this.j = new d(5);
        ViewDragHelper viewDragHelperCreate = ViewDragHelper.create(this, 1.0f, this.i);
        this.g = viewDragHelperCreate;
        viewDragHelperCreate.setEdgeTrackingEnabled(1);
        this.g.setMinVelocity(f2);
        this.i.b = this.g;
        ViewDragHelper viewDragHelperCreate2 = ViewDragHelper.create(this, 1.0f, this.j);
        this.h = viewDragHelperCreate2;
        viewDragHelperCreate2.setEdgeTrackingEnabled(2);
        this.h.setMinVelocity(f2);
        this.j.b = this.h;
        setFocusableInTouchMode(true);
        ViewCompat.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new b());
        setMotionEventSplittingEnabled(false);
        if (ViewCompat.getFitsSystemWindows(this)) {
            setOnApplyWindowInsetsListener(new a(this));
            setSystemUiVisibility(1280);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(I);
            try {
                this.w = typedArrayObtainStyledAttributes.getDrawable(0);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        }
        this.b = f * 10.0f;
        this.H = new ArrayList<>();
    }

    public void setDrawerLockMode(int i, int i2) {
        View viewA;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this));
        if (i2 == 3) {
            this.n = i;
        } else if (i2 == 5) {
            this.o = i;
        } else if (i2 == 8388611) {
            this.p = i;
        } else if (i2 == 8388613) {
            this.q = i;
        }
        if (i != 0) {
            (absoluteGravity == 3 ? this.g : this.h).cancel();
        }
        if (i != 1) {
            if (i == 2 && (viewA = a(absoluteGravity)) != null) {
                openDrawer(viewA);
                return;
            }
            return;
        }
        View viewA2 = a(absoluteGravity);
        if (viewA2 != null) {
            closeDrawer(viewA2);
        }
    }

    public void setStatusBarBackground(int i) {
        this.w = i != 0 ? ContextCompat.getDrawable(getContext(), i) : null;
        invalidate();
    }

    public boolean isDrawerVisible(int i) {
        View viewA = a(i);
        if (viewA != null) {
            return isDrawerVisible(viewA);
        }
        return false;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public float a;
        public boolean b;
        public int c;
        public int gravity;

        public LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = 0;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.J);
            this.gravity = typedArrayObtainStyledAttributes.getInt(0, 0);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = 0;
        }

        public LayoutParams(int i, int i2, int i3) {
            this(i, i2);
            this.gravity = i3;
        }

        public LayoutParams(@NonNull LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = 0;
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = 0;
        }

        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = 0;
        }
    }

    public boolean isDrawerOpen(int i) {
        View viewA = a(i);
        if (viewA != null) {
            return isDrawerOpen(viewA);
        }
        return false;
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;

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
                return new SavedState(parcel, null);
            }
        }

        public SavedState(@NonNull Parcel parcel, @Nullable ClassLoader classLoader) {
            super(parcel, classLoader);
            this.b = 0;
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            this.e = parcel.readInt();
            this.f = parcel.readInt();
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeInt(this.e);
            parcel.writeInt(this.f);
        }

        public SavedState(@NonNull Parcelable parcelable) {
            super(parcelable);
            this.b = 0;
        }
    }

    public static String b(int i) {
        return (i & 3) == 3 ? "LEFT" : (i & 5) == 5 ? "RIGHT" : Integer.toHexString(i);
    }

    public boolean b(View view2) {
        return ((LayoutParams) view2.getLayoutParams()).gravity == 0;
    }

    public View b() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (c(childAt) && isDrawerVisible(childAt)) {
                return childAt;
            }
        }
        return null;
    }

    public int getDrawerLockMode(@NonNull View view2) {
        if (c(view2)) {
            return getDrawerLockMode(((LayoutParams) view2.getLayoutParams()).gravity);
        }
        throw new IllegalArgumentException("View " + view2 + " is not a drawer");
    }

    public void setDrawerLockMode(int i, @NonNull View view2) {
        if (c(view2)) {
            setDrawerLockMode(i, ((LayoutParams) view2.getLayoutParams()).gravity);
            return;
        }
        throw new IllegalArgumentException("View " + view2 + " is not a drawer with appropriate layout_gravity");
    }

    public void closeDrawer(int i) {
        closeDrawer(i, true);
    }

    public void closeDrawer(int i, boolean z) {
        View viewA = a(i);
        if (viewA != null) {
            closeDrawer(viewA, z);
        } else {
            StringBuilder sbA = g9.a("No drawer view found with gravity ");
            sbA.append(b(i));
            throw new IllegalArgumentException(sbA.toString());
        }
    }

    public void openDrawer(int i) {
        openDrawer(i, true);
    }

    public void openDrawer(int i, boolean z) {
        View viewA = a(i);
        if (viewA != null) {
            openDrawer(viewA, z);
        } else {
            StringBuilder sbA = g9.a("No drawer view found with gravity ");
            sbA.append(b(i));
            throw new IllegalArgumentException(sbA.toString());
        }
    }

    public final void a(View view2, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((!z && !c(childAt)) || (z && childAt == view2)) {
                ViewCompat.setImportantForAccessibility(childAt, 1);
            } else {
                ViewCompat.setImportantForAccessibility(childAt, 4);
            }
        }
    }

    public void a(View view2, float f) {
        float f2 = ((LayoutParams) view2.getLayoutParams()).a;
        float width = view2.getWidth();
        int i = ((int) (width * f)) - ((int) (f2 * width));
        if (!a(view2, 3)) {
            i = -i;
        }
        view2.offsetLeftAndRight(i);
        b(view2, f);
    }

    public int a(View view2) {
        return GravityCompat.getAbsoluteGravity(((LayoutParams) view2.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this));
    }

    public boolean a(View view2, int i) {
        return (a(view2) & i) == i;
    }

    public View a() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((((LayoutParams) childAt.getLayoutParams()).c & 1) == 1) {
                return childAt;
            }
        }
        return null;
    }

    public View a(int i) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this)) & 7;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((a(childAt) & 7) == absoluteGravity) {
                return childAt;
            }
        }
        return null;
    }

    public void a(boolean z) {
        boolean zSmoothSlideViewTo;
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (c(childAt) && (!z || layoutParams.b)) {
                int width = childAt.getWidth();
                if (a(childAt, 3)) {
                    zSmoothSlideViewTo = this.g.smoothSlideViewTo(childAt, -width, childAt.getTop());
                } else {
                    zSmoothSlideViewTo = this.h.smoothSlideViewTo(childAt, getWidth(), childAt.getTop());
                }
                z2 |= zSmoothSlideViewTo;
                layoutParams.b = false;
            }
        }
        this.i.b();
        this.j.b();
        if (z2) {
            invalidate();
        }
    }
}
