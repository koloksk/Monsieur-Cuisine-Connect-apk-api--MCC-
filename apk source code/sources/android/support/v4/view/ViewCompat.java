package android.support.v4.view;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.util.Log;
import android.view.Display;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeProvider;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;

    @Deprecated
    public static final int LAYER_TYPE_HARDWARE = 2;

    @Deprecated
    public static final int LAYER_TYPE_NONE = 0;

    @Deprecated
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;

    @Deprecated
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;

    @Deprecated
    public static final int MEASURED_SIZE_MASK = 16777215;

    @Deprecated
    public static final int MEASURED_STATE_MASK = -16777216;

    @Deprecated
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;

    @Deprecated
    public static final int OVER_SCROLL_ALWAYS = 0;

    @Deprecated
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;

    @Deprecated
    public static final int OVER_SCROLL_NEVER = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    public static final int TYPE_NON_TOUCH = 1;
    public static final int TYPE_TOUCH = 0;
    public static final j a;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface FocusDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface FocusRealDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface FocusRelativeDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface NestedScrollType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ScrollAxis {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ScrollIndicators {
    }

    @RequiresApi(15)
    public static class a extends j {
        @Override // android.support.v4.view.ViewCompat.j
        public boolean C(View view2) {
            return view2.hasOnClickListeners();
        }
    }

    @RequiresApi(16)
    public static class b extends a {
        @Override // android.support.v4.view.ViewCompat.j
        public boolean D(View view2) {
            return view2.hasOverlappingRendering();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean E(View view2) {
            return view2.hasTransientState();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void P(View view2) {
            view2.postInvalidateOnAnimation();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, int i, int i2, int i3, int i4) {
            view2.postInvalidateOnAnimation(i, i2, i3, i4);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void b(View view2, boolean z) {
            view2.setHasTransientState(z);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public AccessibilityNodeProviderCompat e(View view2) {
            AccessibilityNodeProvider accessibilityNodeProvider = view2.getAccessibilityNodeProvider();
            if (accessibilityNodeProvider != null) {
                return new AccessibilityNodeProviderCompat(accessibilityNodeProvider);
            }
            return null;
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean k(View view2) {
            return view2.getFitsSystemWindows();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int l(View view2) {
            return view2.getImportantForAccessibility();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int p(View view2) {
            return view2.getMinimumHeight();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int q(View view2) {
            return view2.getMinimumWidth();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public ViewParent u(View view2) {
            return view2.getParentForAccessibility();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, Runnable runnable) {
            view2.postOnAnimation(runnable);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, Runnable runnable, long j) {
            view2.postOnAnimationDelayed(runnable, j);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean a(View view2, int i, Bundle bundle) {
            return view2.performAccessibilityAction(i, bundle);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, Drawable drawable) {
            view2.setBackground(drawable);
        }
    }

    @RequiresApi(17)
    public static class c extends b {
        @Override // android.support.v4.view.ViewCompat.j
        public boolean O(View view2) {
            return view2.isPaddingRelative();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, Paint paint) {
            view2.setLayerPaint(paint);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void b(View view2, int i, int i2, int i3, int i4) {
            view2.setPaddingRelative(i, i2, i3, i4);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void f(View view2, int i) {
            view2.setLabelFor(i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void g(View view2, int i) {
            view2.setLayoutDirection(i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public Display i(View view2) {
            return view2.getDisplay();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int n(View view2) {
            return view2.getLabelFor();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int o(View view2) {
            return view2.getLayoutDirection();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int s(View view2) {
            return view2.getPaddingEnd();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int t(View view2) {
            return view2.getPaddingStart();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int y(View view2) {
            return view2.getWindowSystemUiVisibility();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int b() {
            return View.generateViewId();
        }
    }

    @RequiresApi(18)
    public static class d extends c {
        @Override // android.support.v4.view.ViewCompat.j
        public boolean J(View view2) {
            return view2.isInLayout();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, Rect rect) {
            view2.setClipBounds(rect);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public Rect h(View view2) {
            return view2.getClipBounds();
        }
    }

    @RequiresApi(19)
    public static class e extends d {
        @Override // android.support.v4.view.ViewCompat.j
        public boolean F(View view2) {
            return view2.isAttachedToWindow();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean L(View view2) {
            return view2.isLaidOut();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean M(View view2) {
            return view2.isLayoutDirectionResolved();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void c(View view2, int i) {
            view2.setAccessibilityLiveRegion(i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int d(View view2) {
            return view2.getAccessibilityLiveRegion();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void d(View view2, int i) {
            view2.setImportantForAccessibility(i);
        }
    }

    @RequiresApi(21)
    public static class f extends e {

        public class a implements View.OnApplyWindowInsetsListener {
            public final /* synthetic */ OnApplyWindowInsetsListener a;

            public a(f fVar, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
                this.a = onApplyWindowInsetsListener;
            }

            @Override // android.view.View.OnApplyWindowInsetsListener
            public WindowInsets onApplyWindowInsets(View view2, WindowInsets windowInsets) {
                return (WindowInsets) WindowInsetsCompat.a(this.a.onApplyWindowInsets(view2, WindowInsetsCompat.a(windowInsets)));
            }
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean B(View view2) {
            return view2.hasNestedScrollingParent();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean H(View view2) {
            return view2.isImportantForAccessibility();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean N(View view2) {
            return view2.isNestedScrollingEnabled();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void Q(View view2) {
            view2.requestApplyInsets();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void S(View view2) {
            view2.stopNestedScroll();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, String str) {
            view2.setTransitionName(str);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void b(View view2, float f) {
            view2.setTranslationZ(f);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void c(View view2, float f) {
            view2.setZ(f);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void d(View view2, boolean z) {
            view2.setNestedScrollingEnabled(z);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public ColorStateList f(View view2) {
            return view2.getBackgroundTintList();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public PorterDuff.Mode g(View view2) {
            return view2.getBackgroundTintMode();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public float j(View view2) {
            return view2.getElevation();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public String w(View view2) {
            return view2.getTransitionName();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public float x(View view2) {
            return view2.getTranslationZ();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public float z(View view2) {
            return view2.getZ();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, float f) {
            view2.setElevation(f);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public WindowInsetsCompat b(View view2, WindowInsetsCompat windowInsetsCompat) {
            WindowInsets windowInsets = (WindowInsets) WindowInsetsCompat.a(windowInsetsCompat);
            WindowInsets windowInsetsOnApplyWindowInsets = view2.onApplyWindowInsets(windowInsets);
            if (windowInsetsOnApplyWindowInsets != windowInsets) {
                windowInsets = new WindowInsets(windowInsetsOnApplyWindowInsets);
            }
            return WindowInsetsCompat.a(windowInsets);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean j(View view2, int i) {
            return view2.startNestedScroll(i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            if (onApplyWindowInsetsListener == null) {
                view2.setOnApplyWindowInsetsListener(null);
            } else {
                view2.setOnApplyWindowInsetsListener(new a(this, onApplyWindowInsetsListener));
            }
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean a(View view2, int i, int i2, int i3, int i4, int[] iArr) {
            return view2.dispatchNestedScroll(i, i2, i3, i4, iArr);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean a(View view2, int i, int i2, int[] iArr, int[] iArr2) {
            return view2.dispatchNestedPreScroll(i, i2, iArr, iArr2);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean a(View view2, float f, float f2, boolean z) {
            return view2.dispatchNestedFling(f, f2, z);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean a(View view2, float f, float f2) {
            return view2.dispatchNestedPreFling(f, f2);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, ColorStateList colorStateList) {
            view2.setBackgroundTintList(colorStateList);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, PorterDuff.Mode mode) {
            view2.setBackgroundTintMode(mode);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public WindowInsetsCompat a(View view2, WindowInsetsCompat windowInsetsCompat) {
            WindowInsets windowInsets = (WindowInsets) WindowInsetsCompat.a(windowInsetsCompat);
            WindowInsets windowInsetsDispatchApplyWindowInsets = view2.dispatchApplyWindowInsets(windowInsets);
            if (windowInsetsDispatchApplyWindowInsets != windowInsets) {
                windowInsets = new WindowInsets(windowInsetsDispatchApplyWindowInsets);
            }
            return WindowInsetsCompat.a(windowInsets);
        }
    }

    @RequiresApi(23)
    public static class g extends f {
        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, int i, int i2) {
            view2.setScrollIndicators(i, i2);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void b(View view2, int i) {
            view2.offsetTopAndBottom(i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void i(View view2, int i) {
            view2.setScrollIndicators(i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int v(View view2) {
            return view2.getScrollIndicators();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, int i) {
            view2.offsetLeftAndRight(i);
        }
    }

    @RequiresApi(26)
    public static class i extends h {
        @Override // android.support.v4.view.ViewCompat.j
        public boolean A(@NonNull View view2) {
            return view2.hasExplicitFocusable();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean G(@NonNull View view2) {
            return view2.isFocusedByDefault();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean I(@NonNull View view2) {
            return view2.isImportantForAutofill();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean K(@NonNull View view2) {
            return view2.isKeyboardNavigationCluster();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean R(@NonNull View view2) {
            return view2.restoreDefaultFocus();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(@NonNull View view2, @Nullable String... strArr) {
            view2.setAutofillHints(strArr);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void c(@NonNull View view2, boolean z) {
            view2.setKeyboardNavigationCluster(z);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void e(@NonNull View view2, int i) {
            view2.setImportantForAutofill(i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void h(@NonNull View view2, int i) {
            view2.setNextClusterForwardId(i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int m(@NonNull View view2) {
            return view2.getImportantForAutofill();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public int r(@NonNull View view2) {
            return view2.getNextClusterForwardId();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, CharSequence charSequence) {
            view2.setTooltipText(charSequence);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(@NonNull View view2, boolean z) {
            view2.setFocusedByDefault(z);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public View a(@NonNull View view2, View view3, int i) {
            return view2.keyboardNavigationClusterSearch(view3, i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(@NonNull View view2, @NonNull Collection<View> collection, int i) {
            view2.addKeyboardNavigationClusters(collection, i);
        }
    }

    public static class j {
        public static Method f;
        public static Field g;
        public Method a;
        public Method b;
        public boolean c;
        public WeakHashMap<View, ViewPropertyAnimatorCompat> d = null;
        public static final AtomicInteger e = new AtomicInteger(1);
        public static boolean h = false;

        public boolean A(@NonNull View view2) {
            return view2.hasFocusable();
        }

        public boolean B(View view2) {
            throw null;
        }

        public boolean C(View view2) {
            throw null;
        }

        public boolean D(View view2) {
            throw null;
        }

        public boolean E(View view2) {
            throw null;
        }

        public boolean F(View view2) {
            throw null;
        }

        public boolean G(@NonNull View view2) {
            return false;
        }

        public boolean H(View view2) {
            throw null;
        }

        public boolean I(@NonNull View view2) {
            return true;
        }

        public boolean J(View view2) {
            throw null;
        }

        public boolean K(@NonNull View view2) {
            return false;
        }

        public boolean L(View view2) {
            throw null;
        }

        public boolean M(View view2) {
            throw null;
        }

        public boolean N(View view2) {
            throw null;
        }

        public boolean O(View view2) {
            throw null;
        }

        public void P(View view2) {
            throw null;
        }

        public void Q(View view2) {
            throw null;
        }

        public boolean R(@NonNull View view2) {
            return view2.requestFocus();
        }

        public void S(View view2) {
            throw null;
        }

        public WindowInsetsCompat a(View view2, WindowInsetsCompat windowInsetsCompat) {
            throw null;
        }

        public View a(@NonNull View view2, View view3, int i) {
            return null;
        }

        public void a(View view2) {
        }

        public void a(View view2, float f2) {
            throw null;
        }

        public void a(View view2, int i) {
            throw null;
        }

        public void a(View view2, int i, int i2) {
            throw null;
        }

        public void a(View view2, int i, int i2, int i3, int i4) {
            throw null;
        }

        public void a(View view2, ColorStateList colorStateList) {
            throw null;
        }

        public void a(View view2, Paint paint) {
            throw null;
        }

        public void a(View view2, PorterDuff.Mode mode) {
            throw null;
        }

        public void a(View view2, Rect rect) {
            throw null;
        }

        public void a(View view2, Drawable drawable) {
            throw null;
        }

        public void a(View view2, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            throw null;
        }

        public void a(View view2, PointerIconCompat pointerIconCompat) {
        }

        public void a(View view2, View.DragShadowBuilder dragShadowBuilder) {
        }

        public void a(View view2, CharSequence charSequence) {
        }

        public void a(View view2, Runnable runnable) {
            throw null;
        }

        public void a(View view2, Runnable runnable, long j) {
            throw null;
        }

        public void a(View view2, String str) {
            throw null;
        }

        public void a(@NonNull View view2, @NonNull Collection<View> collection, int i) {
        }

        public void a(@NonNull View view2, boolean z) {
        }

        public void a(@NonNull View view2, @Nullable String... strArr) {
        }

        public boolean a(View view2, float f2, float f3) {
            throw null;
        }

        public boolean a(View view2, float f2, float f3, boolean z) {
            throw null;
        }

        public boolean a(View view2, int i, int i2, int i3, int i4, int[] iArr) {
            throw null;
        }

        public boolean a(View view2, int i, int i2, int[] iArr, int[] iArr2) {
            throw null;
        }

        public boolean a(View view2, int i, Bundle bundle) {
            throw null;
        }

        public boolean a(View view2, ClipData clipData, View.DragShadowBuilder dragShadowBuilder, Object obj, int i) {
            return view2.startDrag(clipData, dragShadowBuilder, obj, i);
        }

        public int b() {
            throw null;
        }

        public WindowInsetsCompat b(View view2, WindowInsetsCompat windowInsetsCompat) {
            throw null;
        }

        public void b(View view2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (!this.c) {
                a();
            }
            Method method = this.b;
            if (method == null) {
                view2.onFinishTemporaryDetach();
                return;
            }
            try {
                method.invoke(view2, new Object[0]);
            } catch (Exception e2) {
                Log.d("ViewCompat", "Error calling dispatchFinishTemporaryDetach", e2);
            }
        }

        public void b(View view2, float f2) {
            throw null;
        }

        public void b(View view2, int i) {
            throw null;
        }

        public void b(View view2, int i, int i2, int i3, int i4) {
            throw null;
        }

        public void b(View view2, boolean z) {
            throw null;
        }

        public void c(View view2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (!this.c) {
                a();
            }
            Method method = this.a;
            if (method == null) {
                view2.onStartTemporaryDetach();
                return;
            }
            try {
                method.invoke(view2, new Object[0]);
            } catch (Exception e2) {
                Log.d("ViewCompat", "Error calling dispatchStartTemporaryDetach", e2);
            }
        }

        public void c(View view2, float f2) {
            throw null;
        }

        public void c(View view2, int i) {
            throw null;
        }

        public void c(@NonNull View view2, boolean z) {
        }

        public int d(View view2) {
            throw null;
        }

        public void d(View view2, int i) {
            throw null;
        }

        public void d(View view2, boolean z) {
            throw null;
        }

        public AccessibilityNodeProviderCompat e(View view2) {
            throw null;
        }

        public void e(@NonNull View view2, int i) {
        }

        public ColorStateList f(View view2) {
            throw null;
        }

        public void f(View view2, int i) {
            throw null;
        }

        public PorterDuff.Mode g(View view2) {
            throw null;
        }

        public void g(View view2, int i) {
            throw null;
        }

        public Rect h(View view2) {
            throw null;
        }

        public void h(@NonNull View view2, int i) {
        }

        public Display i(View view2) {
            throw null;
        }

        public void i(View view2, int i) {
            throw null;
        }

        public float j(View view2) {
            throw null;
        }

        public boolean j(View view2, int i) {
            throw null;
        }

        public boolean k(View view2) {
            throw null;
        }

        public int l(View view2) {
            throw null;
        }

        @TargetApi(26)
        public int m(@NonNull View view2) {
            return 0;
        }

        public int n(View view2) {
            throw null;
        }

        public int o(View view2) {
            throw null;
        }

        public int p(View view2) {
            throw null;
        }

        public int q(View view2) {
            throw null;
        }

        public int r(@NonNull View view2) {
            return -1;
        }

        public int s(View view2) {
            throw null;
        }

        public int t(View view2) {
            throw null;
        }

        public ViewParent u(View view2) {
            throw null;
        }

        public int v(View view2) {
            throw null;
        }

        public String w(View view2) {
            throw null;
        }

        public float x(View view2) {
            throw null;
        }

        public int y(View view2) {
            throw null;
        }

        public float z(View view2) {
            throw null;
        }

        public final void a() {
            try {
                this.a = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
                this.b = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
            } catch (NoSuchMethodException e2) {
                Log.e("ViewCompat", "Couldn't find method", e2);
            }
            this.c = true;
        }
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            a = new i();
        } else if (i2 >= 24) {
            a = new h();
        } else {
            a = new g();
        }
    }

    public static void addKeyboardNavigationClusters(@NonNull View view2, @NonNull Collection<View> collection, int i2) {
        a.a(view2, collection, i2);
    }

    public static ViewPropertyAnimatorCompat animate(View view2) {
        j jVar = a;
        if (jVar.d == null) {
            jVar.d = new WeakHashMap<>();
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = jVar.d.get(view2);
        if (viewPropertyAnimatorCompat != null) {
            return viewPropertyAnimatorCompat;
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = new ViewPropertyAnimatorCompat(view2);
        jVar.d.put(view2, viewPropertyAnimatorCompat2);
        return viewPropertyAnimatorCompat2;
    }

    @Deprecated
    public static boolean canScrollHorizontally(View view2, int i2) {
        return view2.canScrollHorizontally(i2);
    }

    @Deprecated
    public static boolean canScrollVertically(View view2, int i2) {
        return view2.canScrollVertically(i2);
    }

    public static void cancelDragAndDrop(View view2) {
        a.a(view2);
    }

    @Deprecated
    public static int combineMeasuredStates(int i2, int i3) {
        return View.combineMeasuredStates(i2, i3);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
        return a.a(view2, windowInsetsCompat);
    }

    public static void dispatchFinishTemporaryDetach(View view2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a.b(view2);
    }

    public static boolean dispatchNestedFling(@NonNull View view2, float f2, float f3, boolean z) {
        return a.a(view2, f2, f3, z);
    }

    public static boolean dispatchNestedPreFling(@NonNull View view2, float f2, float f3) {
        return a.a(view2, f2, f3);
    }

    public static boolean dispatchNestedPreScroll(@NonNull View view2, int i2, int i3, @Nullable int[] iArr, @Nullable int[] iArr2) {
        return a.a(view2, i2, i3, iArr, iArr2);
    }

    public static boolean dispatchNestedScroll(@NonNull View view2, int i2, int i3, int i4, int i5, @Nullable int[] iArr) {
        return a.a(view2, i2, i3, i4, i5, iArr);
    }

    public static void dispatchStartTemporaryDetach(View view2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a.c(view2);
    }

    public static int generateViewId() {
        return a.b();
    }

    public static int getAccessibilityLiveRegion(View view2) {
        return a.d(view2);
    }

    public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view2) {
        return a.e(view2);
    }

    @Deprecated
    public static float getAlpha(View view2) {
        return view2.getAlpha();
    }

    public static ColorStateList getBackgroundTintList(View view2) {
        return a.f(view2);
    }

    public static PorterDuff.Mode getBackgroundTintMode(View view2) {
        return a.g(view2);
    }

    public static Rect getClipBounds(View view2) {
        return a.h(view2);
    }

    public static Display getDisplay(@NonNull View view2) {
        return a.i(view2);
    }

    public static float getElevation(View view2) {
        return a.j(view2);
    }

    public static boolean getFitsSystemWindows(View view2) {
        return a.k(view2);
    }

    public static int getImportantForAccessibility(View view2) {
        return a.l(view2);
    }

    public static int getImportantForAutofill(@NonNull View view2) {
        return a.m(view2);
    }

    public static int getLabelFor(View view2) {
        return a.n(view2);
    }

    @Deprecated
    public static int getLayerType(View view2) {
        return view2.getLayerType();
    }

    public static int getLayoutDirection(View view2) {
        return a.o(view2);
    }

    @Nullable
    @Deprecated
    public static Matrix getMatrix(View view2) {
        return view2.getMatrix();
    }

    @Deprecated
    public static int getMeasuredHeightAndState(View view2) {
        return view2.getMeasuredHeightAndState();
    }

    @Deprecated
    public static int getMeasuredState(View view2) {
        return view2.getMeasuredState();
    }

    @Deprecated
    public static int getMeasuredWidthAndState(View view2) {
        return view2.getMeasuredWidthAndState();
    }

    public static int getMinimumHeight(View view2) {
        return a.p(view2);
    }

    public static int getMinimumWidth(View view2) {
        return a.q(view2);
    }

    public static int getNextClusterForwardId(@NonNull View view2) {
        return a.r(view2);
    }

    @Deprecated
    public static int getOverScrollMode(View view2) {
        return view2.getOverScrollMode();
    }

    public static int getPaddingEnd(View view2) {
        return a.s(view2);
    }

    public static int getPaddingStart(View view2) {
        return a.t(view2);
    }

    public static ViewParent getParentForAccessibility(View view2) {
        return a.u(view2);
    }

    @Deprecated
    public static float getPivotX(View view2) {
        return view2.getPivotX();
    }

    @Deprecated
    public static float getPivotY(View view2) {
        return view2.getPivotY();
    }

    @Deprecated
    public static float getRotation(View view2) {
        return view2.getRotation();
    }

    @Deprecated
    public static float getRotationX(View view2) {
        return view2.getRotationX();
    }

    @Deprecated
    public static float getRotationY(View view2) {
        return view2.getRotationY();
    }

    @Deprecated
    public static float getScaleX(View view2) {
        return view2.getScaleX();
    }

    @Deprecated
    public static float getScaleY(View view2) {
        return view2.getScaleY();
    }

    public static int getScrollIndicators(@NonNull View view2) {
        return a.v(view2);
    }

    public static String getTransitionName(View view2) {
        return a.w(view2);
    }

    @Deprecated
    public static float getTranslationX(View view2) {
        return view2.getTranslationX();
    }

    @Deprecated
    public static float getTranslationY(View view2) {
        return view2.getTranslationY();
    }

    public static float getTranslationZ(View view2) {
        return a.x(view2);
    }

    public static int getWindowSystemUiVisibility(View view2) {
        return a.y(view2);
    }

    @Deprecated
    public static float getX(View view2) {
        return view2.getX();
    }

    @Deprecated
    public static float getY(View view2) {
        return view2.getY();
    }

    public static float getZ(View view2) {
        return a.z(view2);
    }

    public static boolean hasAccessibilityDelegate(View view2) {
        if (a == null) {
            throw null;
        }
        if (j.h) {
            return false;
        }
        if (j.g == null) {
            try {
                Field declaredField = View.class.getDeclaredField("mAccessibilityDelegate");
                j.g = declaredField;
                declaredField.setAccessible(true);
            } catch (Throwable unused) {
                j.h = true;
                return false;
            }
        }
        try {
            return j.g.get(view2) != null;
        } catch (Throwable unused2) {
            j.h = true;
            return false;
        }
    }

    public static boolean hasExplicitFocusable(@NonNull View view2) {
        return a.A(view2);
    }

    public static boolean hasNestedScrollingParent(@NonNull View view2) {
        return a.B(view2);
    }

    public static boolean hasOnClickListeners(View view2) {
        return a.C(view2);
    }

    public static boolean hasOverlappingRendering(View view2) {
        return a.D(view2);
    }

    public static boolean hasTransientState(View view2) {
        return a.E(view2);
    }

    public static boolean isAttachedToWindow(View view2) {
        return a.F(view2);
    }

    public static boolean isFocusedByDefault(@NonNull View view2) {
        return a.G(view2);
    }

    public static boolean isImportantForAccessibility(View view2) {
        return a.H(view2);
    }

    public static boolean isImportantForAutofill(@NonNull View view2) {
        return a.I(view2);
    }

    public static boolean isInLayout(View view2) {
        return a.J(view2);
    }

    public static boolean isKeyboardNavigationCluster(@NonNull View view2) {
        return a.K(view2);
    }

    public static boolean isLaidOut(View view2) {
        return a.L(view2);
    }

    public static boolean isLayoutDirectionResolved(View view2) {
        return a.M(view2);
    }

    public static boolean isNestedScrollingEnabled(@NonNull View view2) {
        return a.N(view2);
    }

    @Deprecated
    public static boolean isOpaque(View view2) {
        return view2.isOpaque();
    }

    public static boolean isPaddingRelative(View view2) {
        return a.O(view2);
    }

    @Deprecated
    public static void jumpDrawablesToCurrentState(View view2) {
        view2.jumpDrawablesToCurrentState();
    }

    public static View keyboardNavigationClusterSearch(@NonNull View view2, View view3, int i2) {
        return a.a(view2, view3, i2);
    }

    public static void offsetLeftAndRight(View view2, int i2) {
        a.a(view2, i2);
    }

    public static void offsetTopAndBottom(View view2, int i2) {
        a.b(view2, i2);
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
        return a.b(view2, windowInsetsCompat);
    }

    @Deprecated
    public static void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        view2.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    public static void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        if (a == null) {
            throw null;
        }
        view2.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat.unwrap());
    }

    @Deprecated
    public static void onPopulateAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
        view2.onPopulateAccessibilityEvent(accessibilityEvent);
    }

    public static boolean performAccessibilityAction(View view2, int i2, Bundle bundle) {
        return a.a(view2, i2, bundle);
    }

    public static void postInvalidateOnAnimation(View view2) {
        a.P(view2);
    }

    public static void postOnAnimation(View view2, Runnable runnable) {
        a.a(view2, runnable);
    }

    public static void postOnAnimationDelayed(View view2, Runnable runnable, long j2) {
        a.a(view2, runnable, j2);
    }

    public static void requestApplyInsets(View view2) {
        a.Q(view2);
    }

    @NonNull
    public static <T extends View> T requireViewById(@NonNull View view2, @IdRes int i2) {
        T t = (T) view2.findViewById(i2);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this View");
    }

    @Deprecated
    public static int resolveSizeAndState(int i2, int i3, int i4) {
        return View.resolveSizeAndState(i2, i3, i4);
    }

    public static boolean restoreDefaultFocus(@NonNull View view2) {
        return a.R(view2);
    }

    public static void setAccessibilityDelegate(View view2, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        if (a == null) {
            throw null;
        }
        view2.setAccessibilityDelegate(accessibilityDelegateCompat != null ? accessibilityDelegateCompat.a : null);
    }

    public static void setAccessibilityLiveRegion(View view2, int i2) {
        a.c(view2, i2);
    }

    @Deprecated
    public static void setActivated(View view2, boolean z) {
        view2.setActivated(z);
    }

    @Deprecated
    public static void setAlpha(View view2, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        view2.setAlpha(f2);
    }

    public static void setAutofillHints(@NonNull View view2, @Nullable String... strArr) {
        a.a(view2, strArr);
    }

    public static void setBackground(View view2, Drawable drawable) {
        a.a(view2, drawable);
    }

    public static void setBackgroundTintList(View view2, ColorStateList colorStateList) {
        a.a(view2, colorStateList);
    }

    public static void setBackgroundTintMode(View view2, PorterDuff.Mode mode) {
        a.a(view2, mode);
    }

    @Deprecated
    public static void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a == null) {
            throw null;
        }
        if (j.f == null) {
            try {
                j.f = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", Boolean.TYPE);
            } catch (NoSuchMethodException e2) {
                Log.e("ViewCompat", "Unable to find childrenDrawingOrderEnabled", e2);
            }
            j.f.setAccessible(true);
        }
        try {
            j.f.invoke(viewGroup, Boolean.valueOf(z));
        } catch (IllegalAccessException e3) {
            Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", e3);
        } catch (IllegalArgumentException e4) {
            Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", e4);
        } catch (InvocationTargetException e5) {
            Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", e5);
        }
    }

    public static void setClipBounds(View view2, Rect rect) {
        a.a(view2, rect);
    }

    public static void setElevation(View view2, float f2) {
        a.a(view2, f2);
    }

    @Deprecated
    public static void setFitsSystemWindows(View view2, boolean z) {
        view2.setFitsSystemWindows(z);
    }

    public static void setFocusedByDefault(@NonNull View view2, boolean z) {
        a.a(view2, z);
    }

    public static void setHasTransientState(View view2, boolean z) {
        a.b(view2, z);
    }

    public static void setImportantForAccessibility(View view2, int i2) {
        a.d(view2, i2);
    }

    public static void setImportantForAutofill(@NonNull View view2, int i2) {
        a.e(view2, i2);
    }

    public static void setKeyboardNavigationCluster(@NonNull View view2, boolean z) {
        a.c(view2, z);
    }

    public static void setLabelFor(View view2, @IdRes int i2) {
        a.f(view2, i2);
    }

    public static void setLayerPaint(View view2, Paint paint) {
        a.a(view2, paint);
    }

    @Deprecated
    public static void setLayerType(View view2, int i2, Paint paint) {
        view2.setLayerType(i2, paint);
    }

    public static void setLayoutDirection(View view2, int i2) {
        a.g(view2, i2);
    }

    public static void setNestedScrollingEnabled(@NonNull View view2, boolean z) {
        a.d(view2, z);
    }

    public static void setNextClusterForwardId(@NonNull View view2, int i2) {
        a.h(view2, i2);
    }

    public static void setOnApplyWindowInsetsListener(View view2, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        a.a(view2, onApplyWindowInsetsListener);
    }

    @Deprecated
    public static void setOverScrollMode(View view2, int i2) {
        view2.setOverScrollMode(i2);
    }

    public static void setPaddingRelative(View view2, int i2, int i3, int i4, int i5) {
        a.b(view2, i2, i3, i4, i5);
    }

    @Deprecated
    public static void setPivotX(View view2, float f2) {
        view2.setPivotX(f2);
    }

    @Deprecated
    public static void setPivotY(View view2, float f2) {
        view2.setPivotY(f2);
    }

    public static void setPointerIcon(@NonNull View view2, PointerIconCompat pointerIconCompat) {
        a.a(view2, pointerIconCompat);
    }

    @Deprecated
    public static void setRotation(View view2, float f2) {
        view2.setRotation(f2);
    }

    @Deprecated
    public static void setRotationX(View view2, float f2) {
        view2.setRotationX(f2);
    }

    @Deprecated
    public static void setRotationY(View view2, float f2) {
        view2.setRotationY(f2);
    }

    @Deprecated
    public static void setSaveFromParentEnabled(View view2, boolean z) {
        view2.setSaveFromParentEnabled(z);
    }

    @Deprecated
    public static void setScaleX(View view2, float f2) {
        view2.setScaleX(f2);
    }

    @Deprecated
    public static void setScaleY(View view2, float f2) {
        view2.setScaleY(f2);
    }

    public static void setScrollIndicators(@NonNull View view2, int i2) {
        a.i(view2, i2);
    }

    public static void setTooltipText(@NonNull View view2, @Nullable CharSequence charSequence) {
        a.a(view2, charSequence);
    }

    public static void setTransitionName(View view2, String str) {
        a.a(view2, str);
    }

    @Deprecated
    public static void setTranslationX(View view2, float f2) {
        view2.setTranslationX(f2);
    }

    @Deprecated
    public static void setTranslationY(View view2, float f2) {
        view2.setTranslationY(f2);
    }

    public static void setTranslationZ(View view2, float f2) {
        a.b(view2, f2);
    }

    @Deprecated
    public static void setX(View view2, float f2) {
        view2.setX(f2);
    }

    @Deprecated
    public static void setY(View view2, float f2) {
        view2.setY(f2);
    }

    public static void setZ(View view2, float f2) {
        a.c(view2, f2);
    }

    public static boolean startDragAndDrop(View view2, ClipData clipData, View.DragShadowBuilder dragShadowBuilder, Object obj, int i2) {
        return a.a(view2, clipData, dragShadowBuilder, obj, i2);
    }

    public static boolean startNestedScroll(@NonNull View view2, int i2) {
        return a.j(view2, i2);
    }

    public static void stopNestedScroll(@NonNull View view2) {
        a.S(view2);
    }

    public static void updateDragShadow(View view2, View.DragShadowBuilder dragShadowBuilder) {
        a.a(view2, dragShadowBuilder);
    }

    @RequiresApi(24)
    public static class h extends g {
        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, PointerIconCompat pointerIconCompat) {
            view2.setPointerIcon((PointerIcon) (pointerIconCompat != null ? pointerIconCompat.getPointerIcon() : null));
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void b(View view2) {
            view2.dispatchFinishTemporaryDetach();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void c(View view2) {
            view2.dispatchStartTemporaryDetach();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public boolean a(View view2, ClipData clipData, View.DragShadowBuilder dragShadowBuilder, Object obj, int i) {
            return view2.startDragAndDrop(clipData, dragShadowBuilder, obj, i);
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2) {
            view2.cancelDragAndDrop();
        }

        @Override // android.support.v4.view.ViewCompat.j
        public void a(View view2, View.DragShadowBuilder dragShadowBuilder) {
            view2.updateDragShadow(dragShadowBuilder);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean dispatchNestedPreScroll(@NonNull View view2, int i2, int i3, @Nullable int[] iArr, @Nullable int[] iArr2, int i4) {
        if (view2 instanceof NestedScrollingChild2) {
            return ((NestedScrollingChild2) view2).dispatchNestedPreScroll(i2, i3, iArr, iArr2, i4);
        }
        if (i4 == 0) {
            return a.a(view2, i2, i3, iArr, iArr2);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean dispatchNestedScroll(@NonNull View view2, int i2, int i3, int i4, int i5, @Nullable int[] iArr, int i6) {
        if (view2 instanceof NestedScrollingChild2) {
            return ((NestedScrollingChild2) view2).dispatchNestedScroll(i2, i3, i4, i5, iArr, i6);
        }
        if (i6 == 0) {
            return a.a(view2, i2, i3, i4, i5, iArr);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean hasNestedScrollingParent(@NonNull View view2, int i2) {
        if (view2 instanceof NestedScrollingChild2) {
            ((NestedScrollingChild2) view2).hasNestedScrollingParent(i2);
            return false;
        }
        if (i2 == 0) {
            return a.B(view2);
        }
        return false;
    }

    public static void postInvalidateOnAnimation(View view2, int i2, int i3, int i4, int i5) {
        a.a(view2, i2, i3, i4, i5);
    }

    public static void setScrollIndicators(@NonNull View view2, int i2, int i3) {
        a.a(view2, i2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean startNestedScroll(@NonNull View view2, int i2, int i3) {
        if (view2 instanceof NestedScrollingChild2) {
            return ((NestedScrollingChild2) view2).startNestedScroll(i2, i3);
        }
        if (i3 == 0) {
            return a.j(view2, i2);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void stopNestedScroll(@NonNull View view2, int i2) {
        if (view2 instanceof NestedScrollingChild2) {
            ((NestedScrollingChild2) view2).stopNestedScroll(i2);
        } else if (i2 == 0) {
            a.S(view2);
        }
    }
}
