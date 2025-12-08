package android.support.v4.view;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import cooking.Limits;
import defpackage.g9;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
public class ViewPager extends ViewGroup {
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    public static final int[] g0 = {R.attr.layout_gravity};
    public static final Comparator<e> h0 = new a();
    public static final Interpolator i0 = new b();
    public static final h j0 = new h();
    public int A;
    public int B;
    public float C;
    public float D;
    public float E;
    public float F;
    public int G;
    public VelocityTracker H;
    public int I;
    public int J;
    public int K;
    public int L;
    public boolean M;
    public long N;
    public EdgeEffect O;
    public EdgeEffect P;
    public boolean Q;
    public boolean R;
    public int S;
    public List<OnPageChangeListener> T;
    public OnPageChangeListener U;
    public OnPageChangeListener V;
    public List<OnAdapterChangeListener> W;
    public int a;
    public PageTransformer a0;
    public final ArrayList<e> b;
    public int b0;
    public final e c;
    public int c0;
    public final Rect d;
    public ArrayList<View> d0;
    public PagerAdapter e;
    public final Runnable e0;
    public int f;
    public int f0;
    public int g;
    public Parcelable h;
    public ClassLoader i;
    public Scroller j;
    public boolean k;
    public g l;
    public int m;
    public Drawable n;
    public int o;
    public int p;
    public float q;
    public float r;
    public int s;
    public boolean t;
    public boolean u;
    public boolean v;
    public int w;
    public boolean x;
    public boolean y;
    public int z;

    @Target({ElementType.TYPE})
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DecorView {
    }

    public interface OnAdapterChangeListener {
        void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2);
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    public interface PageTransformer {
        void transformPage(@NonNull View view2, float f);
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public int b;
        public Parcelable c;
        public ClassLoader d;

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

        public SavedState(@NonNull Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder sbA = g9.a("FragmentPager.SavedState{");
            sbA.append(Integer.toHexString(System.identityHashCode(this)));
            sbA.append(" position=");
            sbA.append(this.b);
            sbA.append("}");
            return sbA.toString();
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.b);
            parcel.writeParcelable(this.c, i);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? SavedState.class.getClassLoader() : classLoader;
            this.b = parcel.readInt();
            this.c = parcel.readParcelable(classLoader);
            this.d = classLoader;
        }
    }

    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
        }
    }

    public static class a implements Comparator<e> {
        @Override // java.util.Comparator
        public int compare(e eVar, e eVar2) {
            return eVar.b - eVar2.b;
        }
    }

    public static class b implements Interpolator {
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    }

    public class c implements Runnable {
        public c() {
        }

        @Override // java.lang.Runnable
        public void run() throws Resources.NotFoundException {
            ViewPager.this.setScrollState(0);
            ViewPager viewPager = ViewPager.this;
            viewPager.d(viewPager.f);
        }
    }

    public class d implements OnApplyWindowInsetsListener {
        public final Rect a = new Rect();

        public d() {
        }

        @Override // android.support.v4.view.OnApplyWindowInsetsListener
        public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
            WindowInsetsCompat windowInsetsCompatOnApplyWindowInsets = ViewCompat.onApplyWindowInsets(view2, windowInsetsCompat);
            if (windowInsetsCompatOnApplyWindowInsets.isConsumed()) {
                return windowInsetsCompatOnApplyWindowInsets;
            }
            Rect rect = this.a;
            rect.left = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetLeft();
            rect.top = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetTop();
            rect.right = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetRight();
            rect.bottom = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetBottom();
            int childCount = ViewPager.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                WindowInsetsCompat windowInsetsCompatDispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), windowInsetsCompatOnApplyWindowInsets);
                rect.left = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetLeft(), rect.left);
                rect.top = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetTop(), rect.top);
                rect.right = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetRight(), rect.right);
                rect.bottom = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetBottom(), rect.bottom);
            }
            return windowInsetsCompatOnApplyWindowInsets.replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public static class e {
        public Object a;
        public int b;
        public boolean c;
        public float d;
        public float e;
    }

    public class f extends AccessibilityDelegateCompat {
        public f() {
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
            PagerAdapter pagerAdapter;
            super.onInitializeAccessibilityEvent(view2, accessibilityEvent);
            accessibilityEvent.setClassName(ViewPager.class.getName());
            PagerAdapter pagerAdapter2 = ViewPager.this.e;
            accessibilityEvent.setScrollable(pagerAdapter2 != null && pagerAdapter2.getCount() > 1);
            if (accessibilityEvent.getEventType() != 4096 || (pagerAdapter = ViewPager.this.e) == null) {
                return;
            }
            accessibilityEvent.setItemCount(pagerAdapter.getCount());
            accessibilityEvent.setFromIndex(ViewPager.this.f);
            accessibilityEvent.setToIndex(ViewPager.this.f);
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
            PagerAdapter pagerAdapter = ViewPager.this.e;
            accessibilityNodeInfoCompat.setScrollable(pagerAdapter != null && pagerAdapter.getCount() > 1);
            if (ViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public boolean performAccessibilityAction(View view2, int i, Bundle bundle) throws Resources.NotFoundException {
            if (super.performAccessibilityAction(view2, i, bundle)) {
                return true;
            }
            if (i == 4096) {
                if (!ViewPager.this.canScrollHorizontally(1)) {
                    return false;
                }
                ViewPager viewPager = ViewPager.this;
                viewPager.setCurrentItem(viewPager.f + 1);
                return true;
            }
            if (i != 8192 || !ViewPager.this.canScrollHorizontally(-1)) {
                return false;
            }
            ViewPager viewPager2 = ViewPager.this;
            viewPager2.setCurrentItem(viewPager2.f - 1);
            return true;
        }
    }

    public class g extends DataSetObserver {
        public g() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() throws Resources.NotFoundException {
            ViewPager.this.a();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() throws Resources.NotFoundException {
            ViewPager.this.a();
        }
    }

    public static class h implements Comparator<View> {
        @Override // java.util.Comparator
        public int compare(View view2, View view3) {
            LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) view3.getLayoutParams();
            boolean z = layoutParams.isDecor;
            return z != layoutParams2.isDecor ? z ? 1 : -1 : layoutParams.c - layoutParams2.c;
        }
    }

    public ViewPager(@NonNull Context context) {
        super(context);
        this.b = new ArrayList<>();
        this.c = new e();
        this.d = new Rect();
        this.g = -1;
        this.h = null;
        this.i = null;
        this.q = -3.4028235E38f;
        this.r = Float.MAX_VALUE;
        this.w = 1;
        this.G = -1;
        this.Q = true;
        this.e0 = new c();
        this.f0 = 0;
        c();
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.u != z) {
            this.u = z;
        }
    }

    public void a(int i, boolean z, boolean z2, int i2) throws Resources.NotFoundException {
        PagerAdapter pagerAdapter = this.e;
        if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (!z2 && this.f == i && this.b.size() != 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (i < 0) {
            i = 0;
        } else if (i >= this.e.getCount()) {
            i = this.e.getCount() - 1;
        }
        int i3 = this.w;
        int i4 = this.f;
        if (i > i4 + i3 || i < i4 - i3) {
            for (int i5 = 0; i5 < this.b.size(); i5++) {
                this.b.get(i5).c = true;
            }
        }
        boolean z3 = this.f != i;
        if (!this.Q) {
            d(i);
            a(i, z, i2, z3);
        } else {
            this.f = i;
            if (z3) {
                a(i);
            }
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        e eVarA;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0 && (eVarA = a(childAt)) != null && eVarA.b == this.f) {
                    childAt.addFocusables(arrayList, i, i2);
                }
            }
        }
        if ((descendantFocusability != 262144 || size == arrayList.size()) && isFocusable()) {
            if ((i2 & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) {
                return;
            }
            arrayList.add(this);
        }
    }

    public void addOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        if (this.W == null) {
            this.W = new ArrayList();
        }
        this.W.add(onAdapterChangeListener);
    }

    public void addOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        if (this.T == null) {
            this.T = new ArrayList();
        }
        this.T.add(onPageChangeListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(ArrayList<View> arrayList) {
        e eVarA;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (eVarA = a(childAt)) != null && eVarA.b == this.f) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view2, int i, ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        boolean z = layoutParams2.isDecor | (view2.getClass().getAnnotation(DecorView.class) != null);
        layoutParams2.isDecor = z;
        if (!this.t) {
            super.addView(view2, i, layoutParams);
        } else {
            if (z) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams2.b = true;
            addViewInLayout(view2, i, layoutParams);
        }
    }

    public boolean arrowScroll(int i) throws Resources.NotFoundException {
        boolean zRequestFocus;
        boolean z;
        View viewFindFocus = findFocus();
        boolean zD = false;
        if (viewFindFocus == this) {
            viewFindFocus = null;
        } else if (viewFindFocus != null) {
            ViewParent parent = viewFindFocus.getParent();
            while (true) {
                if (!(parent instanceof ViewGroup)) {
                    z = false;
                    break;
                }
                if (parent == this) {
                    z = true;
                    break;
                }
                parent = parent.getParent();
            }
            if (!z) {
                StringBuilder sb = new StringBuilder();
                sb.append(viewFindFocus.getClass().getSimpleName());
                for (ViewParent parent2 = viewFindFocus.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                    sb.append(" => ");
                    sb.append(parent2.getClass().getSimpleName());
                }
                StringBuilder sbA = g9.a("arrowScroll tried to find focus based on non-child current focused view ");
                sbA.append(sb.toString());
                Log.e("ViewPager", sbA.toString());
                viewFindFocus = null;
            }
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i);
        if (viewFindNextFocus != null && viewFindNextFocus != viewFindFocus) {
            if (i == 17) {
                zRequestFocus = (viewFindFocus == null || a(this.d, viewFindNextFocus).left < a(this.d, viewFindFocus).left) ? viewFindNextFocus.requestFocus() : d();
            } else if (i == 66) {
                zRequestFocus = (viewFindFocus == null || a(this.d, viewFindNextFocus).left > a(this.d, viewFindFocus).left) ? viewFindNextFocus.requestFocus() : e();
            }
            zD = zRequestFocus;
        } else if (i == 17 || i == 1) {
            zD = d();
        } else if (i == 66 || i == 2) {
            zD = e();
        }
        if (zD) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
        }
        return zD;
    }

    public e b(int i) {
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            e eVar = this.b.get(i2);
            if (eVar.b == i) {
                return eVar;
            }
        }
        return null;
    }

    public boolean beginFakeDrag() {
        if (this.x) {
            return false;
        }
        this.M = true;
        setScrollState(1);
        this.C = 0.0f;
        this.E = 0.0f;
        VelocityTracker velocityTracker = this.H;
        if (velocityTracker == null) {
            this.H = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, 0.0f, 0.0f, 0);
        this.H.addMovement(motionEventObtain);
        motionEventObtain.recycle();
        this.N = jUptimeMillis;
        return true;
    }

    public void c() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.j = new Scroller(context, i0);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.B = viewConfiguration.getScaledPagingTouchSlop();
        this.I = (int) (400.0f * f2);
        this.J = viewConfiguration.getScaledMaximumFlingVelocity();
        this.O = new EdgeEffect(context);
        this.P = new EdgeEffect(context);
        this.K = (int) (25.0f * f2);
        this.L = (int) (2.0f * f2);
        this.z = (int) (f2 * 16.0f);
        ViewCompat.setAccessibilityDelegate(this, new f());
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, new d());
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
        return z && view2.canScrollHorizontally(-i);
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        if (this.e == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        return i < 0 ? scrollX > ((int) (((float) clientWidth) * this.q)) : i > 0 && scrollX < ((int) (((float) clientWidth) * this.r));
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public void clearOnPageChangeListeners() {
        List<OnPageChangeListener> list = this.T;
        if (list != null) {
            list.clear();
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        this.k = true;
        if (this.j.isFinished() || !this.j.computeScrollOffset()) {
            a(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.j.getCurrX();
        int currY = this.j.getCurrY();
        if (scrollX != currX || scrollY != currY) {
            scrollTo(currX, currY);
            if (!c(currX)) {
                this.j.abortAnimation();
                scrollTo(0, currY);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0061, code lost:
    
        r4 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f2 A[PHI: r2 r6 r12
  0x00f2: PHI (r2v21 int) = (r2v20 int), (r2v8 int), (r2v23 int) binds: [B:61:0x00e7, B:58:0x00d1, B:52:0x00bb] A[DONT_GENERATE, DONT_INLINE]
  0x00f2: PHI (r6v6 int) = (r6v1 int), (r6v5 int), (r6v8 int) binds: [B:61:0x00e7, B:58:0x00d1, B:52:0x00bb] A[DONT_GENERATE, DONT_INLINE]
  0x00f2: PHI (r12v5 float) = (r12v3 float), (r12v4 float), (r12v2 float) binds: [B:61:0x00e7, B:58:0x00d1, B:52:0x00bb] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0187 A[PHI: r1 r9
  0x0187: PHI (r1v39 float) = (r1v37 float), (r1v38 float), (r1v36 float) binds: [B:95:0x017c, B:92:0x0162, B:86:0x0146] A[DONT_GENERATE, DONT_INLINE]
  0x0187: PHI (r9v21 int) = (r9v19 int), (r9v20 int), (r9v18 int) binds: [B:95:0x017c, B:92:0x0162, B:86:0x0146] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void d(int r15) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 925
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.d(int):void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        e eVarA;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (eVarA = a(childAt)) != null && eVarA.b == this.f && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        PagerAdapter pagerAdapter;
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean zDraw = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (pagerAdapter = this.e) != null && pagerAdapter.getCount() > 1)) {
            if (!this.O.isFinished()) {
                int iSave = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate(getPaddingTop() + (-height), this.q * width);
                this.O.setSize(height, width);
                zDraw = false | this.O.draw(canvas);
                canvas.restoreToCount(iSave);
            }
            if (!this.P.isFinished()) {
                int iSave2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate(-getPaddingTop(), (-(this.r + 1.0f)) * width2);
                this.P.setSize(height2, width2);
                zDraw |= this.P.draw(canvas);
                canvas.restoreToCount(iSave2);
            }
        } else {
            this.O.finish();
            this.P.finish();
        }
        if (zDraw) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.n;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    public boolean e() throws Resources.NotFoundException {
        PagerAdapter pagerAdapter = this.e;
        if (pagerAdapter == null || this.f >= pagerAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.f + 1, true);
        return true;
    }

    public void endFakeDrag() throws Resources.NotFoundException {
        if (!this.M) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.e != null) {
            VelocityTracker velocityTracker = this.H;
            velocityTracker.computeCurrentVelocity(1000, this.J);
            int xVelocity = (int) velocityTracker.getXVelocity(this.G);
            this.v = true;
            int clientWidth = getClientWidth();
            int scrollX = getScrollX();
            e eVarB = b();
            a(a(eVarB.b, ((scrollX / clientWidth) - eVarB.e) / eVarB.d, xVelocity, (int) (this.C - this.E)), true, true, xVelocity);
        }
        this.x = false;
        this.y = false;
        VelocityTracker velocityTracker2 = this.H;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
            this.H = null;
        }
        this.M = false;
    }

    public boolean executeKeyEvent(@NonNull KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 21) {
                return keyEvent.hasModifiers(2) ? d() : arrowScroll(17);
            }
            if (keyCode == 22) {
                return keyEvent.hasModifiers(2) ? e() : arrowScroll(66);
            }
            if (keyCode == 61) {
                if (keyEvent.hasNoModifiers()) {
                    return arrowScroll(2);
                }
                if (keyEvent.hasModifiers(1)) {
                    return arrowScroll(1);
                }
            }
        }
        return false;
    }

    public final boolean f() {
        this.G = -1;
        this.x = false;
        this.y = false;
        VelocityTracker velocityTracker = this.H;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.H = null;
        }
        this.O.onRelease();
        this.P.onRelease();
        return this.O.isFinished() || this.P.isFinished();
    }

    public void fakeDragBy(float f2) {
        if (!this.M) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.e == null) {
            return;
        }
        this.C += f2;
        float scrollX = getScrollX() - f2;
        float clientWidth = getClientWidth();
        float f3 = this.q * clientWidth;
        float f4 = this.r * clientWidth;
        e eVar = this.b.get(0);
        e eVar2 = this.b.get(r4.size() - 1);
        if (eVar.b != 0) {
            f3 = eVar.e * clientWidth;
        }
        if (eVar2.b != this.e.getCount() - 1) {
            f4 = eVar2.e * clientWidth;
        }
        if (scrollX < f3) {
            scrollX = f3;
        } else if (scrollX > f4) {
            scrollX = f4;
        }
        int i = (int) scrollX;
        this.C = (scrollX - i) + this.C;
        scrollTo(i, getScrollY());
        c(i);
        MotionEvent motionEventObtain = MotionEvent.obtain(this.N, SystemClock.uptimeMillis(), 2, this.C, 0.0f, 0);
        this.H.addMovement(motionEventObtain);
        motionEventObtain.recycle();
    }

    public final void g() {
        if (this.c0 != 0) {
            ArrayList<View> arrayList = this.d0;
            if (arrayList == null) {
                this.d0 = new ArrayList<>();
            } else {
                arrayList.clear();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.d0.add(getChildAt(i));
            }
            Collections.sort(this.d0, j0);
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    @Nullable
    public PagerAdapter getAdapter() {
        return this.e;
    }

    @Override // android.view.ViewGroup
    public int getChildDrawingOrder(int i, int i2) {
        if (this.c0 == 2) {
            i2 = (i - 1) - i2;
        }
        return ((LayoutParams) this.d0.get(i2).getLayoutParams()).d;
    }

    public int getCurrentItem() {
        return this.f;
    }

    public int getOffscreenPageLimit() {
        return this.w;
    }

    public int getPageMargin() {
        return this.m;
    }

    public boolean isFakeDragging() {
        return this.M;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.Q = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeCallbacks(this.e0);
        Scroller scroller = this.j;
        if (scroller != null && !scroller.isFinished()) {
            this.j.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float f2;
        float f3;
        super.onDraw(canvas);
        if (this.m <= 0 || this.n == null || this.b.size() <= 0 || this.e == null) {
            return;
        }
        int scrollX = getScrollX();
        float width = getWidth();
        float f4 = this.m / width;
        int i = 0;
        e eVar = this.b.get(0);
        float f5 = eVar.e;
        int size = this.b.size();
        int i2 = eVar.b;
        int i3 = this.b.get(size - 1).b;
        while (i2 < i3) {
            while (i2 > eVar.b && i < size) {
                i++;
                eVar = this.b.get(i);
            }
            if (i2 == eVar.b) {
                float f6 = eVar.e;
                float f7 = eVar.d;
                f2 = (f6 + f7) * width;
                f5 = f6 + f7 + f4;
            } else {
                float pageWidth = this.e.getPageWidth(i2);
                f2 = (f5 + pageWidth) * width;
                f5 = pageWidth + f4 + f5;
            }
            if (this.m + f2 > scrollX) {
                f3 = f4;
                this.n.setBounds(Math.round(f2), this.o, Math.round(this.m + f2), this.p);
                this.n.draw(canvas);
            } else {
                f3 = f4;
            }
            if (f2 > scrollX + r2) {
                return;
            }
            i2++;
            f4 = f3;
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            f();
            return false;
        }
        if (action != 0) {
            if (this.x) {
                return true;
            }
            if (this.y) {
                return false;
            }
        }
        if (action == 0) {
            float x = motionEvent.getX();
            this.E = x;
            this.C = x;
            float y = motionEvent.getY();
            this.F = y;
            this.D = y;
            this.G = motionEvent.getPointerId(0);
            this.y = false;
            this.k = true;
            this.j.computeScrollOffset();
            if (this.f0 != 2 || Math.abs(this.j.getFinalX() - this.j.getCurrX()) <= this.L) {
                a(false);
                this.x = false;
            } else {
                this.j.abortAnimation();
                this.v = false;
                d(this.f);
                this.x = true;
                b(true);
                setScrollState(1);
            }
        } else if (action == 2) {
            int i = this.G;
            if (i != -1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(i);
                float x2 = motionEvent.getX(iFindPointerIndex);
                float f2 = x2 - this.C;
                float fAbs = Math.abs(f2);
                float y2 = motionEvent.getY(iFindPointerIndex);
                float fAbs2 = Math.abs(y2 - this.F);
                if (f2 != 0.0f) {
                    float f3 = this.C;
                    if (!((f3 < ((float) this.A) && f2 > 0.0f) || (f3 > ((float) (getWidth() - this.A)) && f2 < 0.0f)) && canScroll(this, false, (int) f2, (int) x2, (int) y2)) {
                        this.C = x2;
                        this.D = y2;
                        this.y = true;
                        return false;
                    }
                }
                if (fAbs > this.B && fAbs * 0.5f > fAbs2) {
                    this.x = true;
                    b(true);
                    setScrollState(1);
                    float f4 = this.E;
                    float f5 = this.B;
                    this.C = f2 > 0.0f ? f4 + f5 : f4 - f5;
                    this.D = y2;
                    setScrollingCacheEnabled(true);
                } else if (fAbs2 > this.B) {
                    this.y = true;
                }
                if (this.x && a(x2)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
            }
        } else if (action == 6) {
            a(motionEvent);
        }
        if (this.H == null) {
            this.H = VelocityTracker.obtain();
        }
        this.H.addMovement(motionEvent);
        return this.x;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008e  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onLayout(boolean r19, int r20, int r21, int r22, int r23) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) throws Resources.NotFoundException {
        LayoutParams layoutParams;
        LayoutParams layoutParams2;
        int i3;
        setMeasuredDimension(ViewGroup.getDefaultSize(0, i), ViewGroup.getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        this.A = Math.min(measuredWidth / 10, this.z);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int i4 = 0;
        while (true) {
            boolean z = true;
            int i5 = 1073741824;
            if (i4 >= childCount) {
                break;
            }
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8 && (layoutParams2 = (LayoutParams) childAt.getLayoutParams()) != null && layoutParams2.isDecor) {
                int i6 = layoutParams2.gravity;
                int i7 = i6 & 7;
                int i8 = i6 & 112;
                boolean z2 = i8 == 48 || i8 == 80;
                if (i7 != 3 && i7 != 5) {
                    z = false;
                }
                int i9 = Integer.MIN_VALUE;
                if (z2) {
                    i3 = Integer.MIN_VALUE;
                    i9 = 1073741824;
                } else {
                    i3 = z ? 1073741824 : Integer.MIN_VALUE;
                }
                int i10 = ((ViewGroup.LayoutParams) layoutParams2).width;
                if (i10 != -2) {
                    if (i10 == -1) {
                        i10 = paddingLeft;
                    }
                    i9 = 1073741824;
                } else {
                    i10 = paddingLeft;
                }
                int i11 = ((ViewGroup.LayoutParams) layoutParams2).height;
                if (i11 == -2) {
                    i11 = measuredHeight;
                    i5 = i3;
                } else if (i11 == -1) {
                    i11 = measuredHeight;
                }
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i10, i9), View.MeasureSpec.makeMeasureSpec(i11, i5));
                if (z2) {
                    measuredHeight -= childAt.getMeasuredHeight();
                } else if (z) {
                    paddingLeft -= childAt.getMeasuredWidth();
                }
            }
            i4++;
        }
        View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        this.s = View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.t = true;
        d(this.f);
        this.t = false;
        int childCount2 = getChildCount();
        for (int i12 = 0; i12 < childCount2; i12++) {
            View childAt2 = getChildAt(i12);
            if (childAt2.getVisibility() != 8 && ((layoutParams = (LayoutParams) childAt2.getLayoutParams()) == null || !layoutParams.isDecor)) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * layoutParams.a), 1073741824), this.s);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0064  */
    @android.support.annotation.CallSuper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPageScrolled(int r13, float r14, int r15) {
        /*
            r12 = this;
            int r0 = r12.S
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L6b
            int r0 = r12.getScrollX()
            int r3 = r12.getPaddingLeft()
            int r4 = r12.getPaddingRight()
            int r5 = r12.getWidth()
            int r6 = r12.getChildCount()
            r7 = r1
        L1b:
            if (r7 >= r6) goto L6b
            android.view.View r8 = r12.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            android.support.v4.view.ViewPager$LayoutParams r9 = (android.support.v4.view.ViewPager.LayoutParams) r9
            boolean r10 = r9.isDecor
            if (r10 != 0) goto L2c
            goto L68
        L2c:
            int r9 = r9.gravity
            r9 = r9 & 7
            if (r9 == r2) goto L4d
            r10 = 3
            if (r9 == r10) goto L47
            r10 = 5
            if (r9 == r10) goto L3a
            r9 = r3
            goto L5c
        L3a:
            int r9 = r5 - r4
            int r10 = r8.getMeasuredWidth()
            int r9 = r9 - r10
            int r10 = r8.getMeasuredWidth()
            int r4 = r4 + r10
            goto L59
        L47:
            int r9 = r8.getWidth()
            int r9 = r9 + r3
            goto L5c
        L4d:
            int r9 = r8.getMeasuredWidth()
            int r9 = r5 - r9
            int r9 = r9 / 2
            int r9 = java.lang.Math.max(r9, r3)
        L59:
            r11 = r9
            r9 = r3
            r3 = r11
        L5c:
            int r3 = r3 + r0
            int r10 = r8.getLeft()
            int r3 = r3 - r10
            if (r3 == 0) goto L67
            r8.offsetLeftAndRight(r3)
        L67:
            r3 = r9
        L68:
            int r7 = r7 + 1
            goto L1b
        L6b:
            android.support.v4.view.ViewPager$OnPageChangeListener r0 = r12.U
            if (r0 == 0) goto L72
            r0.onPageScrolled(r13, r14, r15)
        L72:
            java.util.List<android.support.v4.view.ViewPager$OnPageChangeListener> r0 = r12.T
            if (r0 == 0) goto L8d
            int r0 = r0.size()
            r3 = r1
        L7b:
            if (r3 >= r0) goto L8d
            java.util.List<android.support.v4.view.ViewPager$OnPageChangeListener> r4 = r12.T
            java.lang.Object r4 = r4.get(r3)
            android.support.v4.view.ViewPager$OnPageChangeListener r4 = (android.support.v4.view.ViewPager.OnPageChangeListener) r4
            if (r4 == 0) goto L8a
            r4.onPageScrolled(r13, r14, r15)
        L8a:
            int r3 = r3 + 1
            goto L7b
        L8d:
            android.support.v4.view.ViewPager$OnPageChangeListener r0 = r12.V
            if (r0 == 0) goto L94
            r0.onPageScrolled(r13, r14, r15)
        L94:
            android.support.v4.view.ViewPager$PageTransformer r13 = r12.a0
            if (r13 == 0) goto Lc5
            int r13 = r12.getScrollX()
            int r14 = r12.getChildCount()
        La0:
            if (r1 >= r14) goto Lc5
            android.view.View r15 = r12.getChildAt(r1)
            android.view.ViewGroup$LayoutParams r0 = r15.getLayoutParams()
            android.support.v4.view.ViewPager$LayoutParams r0 = (android.support.v4.view.ViewPager.LayoutParams) r0
            boolean r0 = r0.isDecor
            if (r0 == 0) goto Lb1
            goto Lc2
        Lb1:
            int r0 = r15.getLeft()
            int r0 = r0 - r13
            float r0 = (float) r0
            int r3 = r12.getClientWidth()
            float r3 = (float) r3
            float r0 = r0 / r3
            android.support.v4.view.ViewPager$PageTransformer r3 = r12.a0
            r3.transformPage(r15, r0)
        Lc2:
            int r1 = r1 + 1
            goto La0
        Lc5:
            r12.R = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onPageScrolled(int, float, int):void");
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3;
        e eVarA;
        int childCount = getChildCount();
        int i4 = -1;
        if ((i & 2) != 0) {
            i4 = childCount;
            i2 = 0;
            i3 = 1;
        } else {
            i2 = childCount - 1;
            i3 = -1;
        }
        while (i2 != i4) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (eVarA = a(childAt)) != null && eVarA.b == this.f && childAt.requestFocus(i, rect)) {
                return true;
            }
            i2 += i3;
        }
        return false;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) throws Resources.NotFoundException {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        PagerAdapter pagerAdapter = this.e;
        if (pagerAdapter != null) {
            pagerAdapter.restoreState(savedState.c, savedState.d);
            a(savedState.b, false, true, 0);
        } else {
            this.g = savedState.b;
            this.h = savedState.c;
            this.i = savedState.d;
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.b = this.f;
        PagerAdapter pagerAdapter = this.e;
        if (pagerAdapter != null) {
            savedState.c = pagerAdapter.saveState();
        }
        return savedState;
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            int i5 = this.m;
            a(i, i3, i5, i5);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00dc  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void removeOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        List<OnAdapterChangeListener> list = this.W;
        if (list != null) {
            list.remove(onAdapterChangeListener);
        }
    }

    public void removeOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        List<OnPageChangeListener> list = this.T;
        if (list != null) {
            list.remove(onPageChangeListener);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view2) {
        if (this.t) {
            removeViewInLayout(view2);
        } else {
            super.removeView(view2);
        }
    }

    public void setAdapter(@Nullable PagerAdapter pagerAdapter) throws Resources.NotFoundException {
        PagerAdapter pagerAdapter2 = this.e;
        if (pagerAdapter2 != null) {
            pagerAdapter2.setViewPagerObserver(null);
            this.e.startUpdate((ViewGroup) this);
            for (int i = 0; i < this.b.size(); i++) {
                e eVar = this.b.get(i);
                this.e.destroyItem((ViewGroup) this, eVar.b, eVar.a);
            }
            this.e.finishUpdate((ViewGroup) this);
            this.b.clear();
            int i2 = 0;
            while (i2 < getChildCount()) {
                if (!((LayoutParams) getChildAt(i2).getLayoutParams()).isDecor) {
                    removeViewAt(i2);
                    i2--;
                }
                i2++;
            }
            this.f = 0;
            scrollTo(0, 0);
        }
        PagerAdapter pagerAdapter3 = this.e;
        this.e = pagerAdapter;
        this.a = 0;
        if (pagerAdapter != null) {
            if (this.l == null) {
                this.l = new g();
            }
            this.e.setViewPagerObserver(this.l);
            this.v = false;
            boolean z = this.Q;
            this.Q = true;
            this.a = this.e.getCount();
            if (this.g >= 0) {
                this.e.restoreState(this.h, this.i);
                a(this.g, false, true, 0);
                this.g = -1;
                this.h = null;
                this.i = null;
            } else if (z) {
                requestLayout();
            } else {
                d(this.f);
            }
        }
        List<OnAdapterChangeListener> list = this.W;
        if (list == null || list.isEmpty()) {
            return;
        }
        int size = this.W.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.W.get(i3).onAdapterChanged(this, pagerAdapter3, pagerAdapter);
        }
    }

    public void setCurrentItem(int i) throws Resources.NotFoundException {
        this.v = false;
        a(i, !this.Q, false, 0);
    }

    public void setOffscreenPageLimit(int i) throws Resources.NotFoundException {
        if (i < 1) {
            Log.w("ViewPager", "Requested offscreen page limit " + i + " too small; defaulting to 1");
            i = 1;
        }
        if (i != this.w) {
            this.w = i;
            d(this.f);
        }
    }

    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.U = onPageChangeListener;
    }

    public void setPageMargin(int i) {
        int i2 = this.m;
        this.m = i;
        int width = getWidth();
        a(width, width, i, i2);
        requestLayout();
    }

    public void setPageMarginDrawable(@Nullable Drawable drawable) {
        this.n = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setPageTransformer(boolean z, @Nullable PageTransformer pageTransformer) throws Resources.NotFoundException {
        setPageTransformer(z, pageTransformer, 2);
    }

    public void setScrollState(int i) {
        if (this.f0 == i) {
            return;
        }
        this.f0 = i;
        if (this.a0 != null) {
            boolean z = i != 0;
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).setLayerType(z ? this.b0 : 0, null);
            }
        }
        OnPageChangeListener onPageChangeListener = this.U;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(i);
        }
        List<OnPageChangeListener> list = this.T;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                OnPageChangeListener onPageChangeListener2 = this.T.get(i3);
                if (onPageChangeListener2 != null) {
                    onPageChangeListener2.onPageScrollStateChanged(i);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.V;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageScrollStateChanged(i);
        }
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.n;
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public float a;
        public boolean b;
        public int c;
        public int d;
        public int gravity;
        public boolean isDecor;

        public LayoutParams() {
            super(-1, -1);
            this.a = 0.0f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = 0.0f;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.g0);
            this.gravity = typedArrayObtainStyledAttributes.getInteger(0, 48);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public void setPageTransformer(boolean z, @Nullable PageTransformer pageTransformer, int i) throws Resources.NotFoundException {
        boolean z2 = pageTransformer != null;
        boolean z3 = z2 != (this.a0 != null);
        this.a0 = pageTransformer;
        setChildrenDrawingOrderEnabled(z2);
        if (z2) {
            this.c0 = z ? 2 : 1;
            this.b0 = i;
        } else {
            this.c0 = 0;
        }
        if (z3) {
            d(this.f);
        }
    }

    public final void b(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    public void setCurrentItem(int i, boolean z) throws Resources.NotFoundException {
        this.v = false;
        a(i, z, false, 0);
    }

    public void setPageMarginDrawable(@DrawableRes int i) {
        setPageMarginDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public final e b() {
        int i;
        int clientWidth = getClientWidth();
        float f2 = 0.0f;
        float scrollX = clientWidth > 0 ? getScrollX() / clientWidth : 0.0f;
        float f3 = clientWidth > 0 ? this.m / clientWidth : 0.0f;
        e eVar = null;
        int i2 = 0;
        int i3 = -1;
        boolean z = true;
        float f4 = 0.0f;
        while (i2 < this.b.size()) {
            e eVar2 = this.b.get(i2);
            if (!z && eVar2.b != (i = i3 + 1)) {
                eVar2 = this.c;
                eVar2.e = f2 + f4 + f3;
                eVar2.b = i;
                eVar2.d = this.e.getPageWidth(i);
                i2--;
            }
            f2 = eVar2.e;
            float f5 = eVar2.d + f2 + f3;
            if (!z && scrollX < f2) {
                return eVar;
            }
            if (scrollX < f5 || i2 == this.b.size() - 1) {
                return eVar2;
            }
            i3 = eVar2.b;
            f4 = eVar2.d;
            i2++;
            z = false;
            eVar = eVar2;
        }
        return eVar;
    }

    public ViewPager(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = new ArrayList<>();
        this.c = new e();
        this.d = new Rect();
        this.g = -1;
        this.h = null;
        this.i = null;
        this.q = -3.4028235E38f;
        this.r = Float.MAX_VALUE;
        this.w = 1;
        this.G = -1;
        this.Q = true;
        this.e0 = new c();
        this.f0 = 0;
        c();
    }

    public final void a(int i, boolean z, int i2, boolean z2) throws Resources.NotFoundException {
        int scrollX;
        int iAbs;
        e eVarB = b(i);
        int iMax = eVarB != null ? (int) (Math.max(this.q, Math.min(eVarB.e, this.r)) * getClientWidth()) : 0;
        if (z) {
            if (getChildCount() == 0) {
                setScrollingCacheEnabled(false);
            } else {
                Scroller scroller = this.j;
                if ((scroller == null || scroller.isFinished()) ? false : true) {
                    scrollX = this.k ? this.j.getCurrX() : this.j.getStartX();
                    this.j.abortAnimation();
                    setScrollingCacheEnabled(false);
                } else {
                    scrollX = getScrollX();
                }
                int i3 = scrollX;
                int scrollY = getScrollY();
                int i4 = iMax - i3;
                int i5 = 0 - scrollY;
                if (i4 == 0 && i5 == 0) {
                    a(false);
                    d(this.f);
                    setScrollState(0);
                } else {
                    setScrollingCacheEnabled(true);
                    setScrollState(2);
                    int clientWidth = getClientWidth();
                    int i6 = clientWidth / 2;
                    float f2 = clientWidth;
                    float f3 = i6;
                    float fSin = (((float) Math.sin((Math.min(1.0f, (Math.abs(i4) * 1.0f) / f2) - 0.5f) * 0.47123894f)) * f3) + f3;
                    int iAbs2 = Math.abs(i2);
                    if (iAbs2 > 0) {
                        iAbs = Math.round(Math.abs(fSin / iAbs2) * 1000.0f) * 4;
                    } else {
                        iAbs = (int) (((Math.abs(i4) / ((this.e.getPageWidth(this.f) * f2) + this.m)) + 1.0f) * 100.0f);
                    }
                    int iMin = Math.min(iAbs, Limits.TURBO_MAX_SECONDS);
                    this.k = false;
                    this.j.startScroll(i3, scrollY, i4, i5, iMin);
                    ViewCompat.postInvalidateOnAnimation(this);
                }
            }
            if (z2) {
                a(i);
                return;
            }
            return;
        }
        if (z2) {
            a(i);
        }
        a(false);
        scrollTo(iMax, 0);
        c(iMax);
    }

    public final boolean c(int i) {
        if (this.b.size() == 0) {
            if (this.Q) {
                return false;
            }
            this.R = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.R) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        e eVarB = b();
        int clientWidth = getClientWidth();
        int i2 = this.m;
        int i3 = clientWidth + i2;
        float f2 = clientWidth;
        int i4 = eVarB.b;
        float f3 = ((i / f2) - eVarB.e) / (eVarB.d + (i2 / f2));
        this.R = false;
        onPageScrolled(i4, f3, (int) (i3 * f3));
        if (this.R) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    public e a(int i, int i2) {
        e eVar = new e();
        eVar.b = i;
        eVar.a = this.e.instantiateItem((ViewGroup) this, i);
        eVar.d = this.e.getPageWidth(i);
        if (i2 >= 0 && i2 < this.b.size()) {
            this.b.add(i2, eVar);
        } else {
            this.b.add(eVar);
        }
        return eVar;
    }

    public void a() throws Resources.NotFoundException {
        int count = this.e.getCount();
        this.a = count;
        boolean z = this.b.size() < (this.w * 2) + 1 && this.b.size() < count;
        int iMax = this.f;
        int i = 0;
        boolean z2 = false;
        while (i < this.b.size()) {
            e eVar = this.b.get(i);
            int itemPosition = this.e.getItemPosition(eVar.a);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.b.remove(i);
                    i--;
                    if (!z2) {
                        this.e.startUpdate((ViewGroup) this);
                        z2 = true;
                    }
                    this.e.destroyItem((ViewGroup) this, eVar.b, eVar.a);
                    int i2 = this.f;
                    if (i2 == eVar.b) {
                        iMax = Math.max(0, Math.min(i2, count - 1));
                    }
                } else {
                    int i3 = eVar.b;
                    if (i3 != itemPosition) {
                        if (i3 == this.f) {
                            iMax = itemPosition;
                        }
                        eVar.b = itemPosition;
                    }
                }
                z = true;
            }
            i++;
        }
        if (z2) {
            this.e.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.b, h0);
        if (z) {
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i4).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.a = 0.0f;
                }
            }
            a(iMax, false, true, 0);
            requestLayout();
        }
    }

    public e a(View view2) {
        for (int i = 0; i < this.b.size(); i++) {
            e eVar = this.b.get(i);
            if (this.e.isViewFromObject(view2, eVar.a)) {
                return eVar;
            }
        }
        return null;
    }

    public final void a(int i, int i2, int i3, int i4) {
        if (i2 > 0 && !this.b.isEmpty()) {
            if (!this.j.isFinished()) {
                this.j.setFinalX(getCurrentItem() * getClientWidth());
                return;
            } else {
                scrollTo((int) ((getScrollX() / (((i2 - getPaddingLeft()) - getPaddingRight()) + i4)) * (((i - getPaddingLeft()) - getPaddingRight()) + i3)), getScrollY());
                return;
            }
        }
        e eVarB = b(this.f);
        int iMin = (int) ((eVarB != null ? Math.min(eVarB.e, this.r) : 0.0f) * ((i - getPaddingLeft()) - getPaddingRight()));
        if (iMin != getScrollX()) {
            a(false);
            scrollTo(iMin, getScrollY());
        }
    }

    public final void a(int i) {
        OnPageChangeListener onPageChangeListener = this.U;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(i);
        }
        List<OnPageChangeListener> list = this.T;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnPageChangeListener onPageChangeListener2 = this.T.get(i2);
                if (onPageChangeListener2 != null) {
                    onPageChangeListener2.onPageSelected(i);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.V;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageSelected(i);
        }
    }

    public final void a(boolean z) {
        boolean z2 = this.f0 == 2;
        if (z2) {
            setScrollingCacheEnabled(false);
            if (!this.j.isFinished()) {
                this.j.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.j.getCurrX();
                int currY = this.j.getCurrY();
                if (scrollX != currX || scrollY != currY) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        c(currX);
                    }
                }
            }
        }
        this.v = false;
        for (int i = 0; i < this.b.size(); i++) {
            e eVar = this.b.get(i);
            if (eVar.c) {
                eVar.c = false;
                z2 = true;
            }
        }
        if (z2) {
            if (z) {
                ViewCompat.postOnAnimation(this, this.e0);
            } else {
                this.e0.run();
            }
        }
    }

    public boolean d() throws Resources.NotFoundException {
        int i = this.f;
        if (i <= 0) {
            return false;
        }
        setCurrentItem(i - 1, true);
        return true;
    }

    public final boolean a(float f2) {
        boolean z;
        boolean z2;
        float f3 = this.C - f2;
        this.C = f2;
        float scrollX = getScrollX() + f3;
        float clientWidth = getClientWidth();
        float f4 = this.q * clientWidth;
        float f5 = this.r * clientWidth;
        boolean z3 = false;
        e eVar = this.b.get(0);
        ArrayList<e> arrayList = this.b;
        e eVar2 = arrayList.get(arrayList.size() - 1);
        if (eVar.b != 0) {
            f4 = eVar.e * clientWidth;
            z = false;
        } else {
            z = true;
        }
        if (eVar2.b != this.e.getCount() - 1) {
            f5 = eVar2.e * clientWidth;
            z2 = false;
        } else {
            z2 = true;
        }
        if (scrollX < f4) {
            if (z) {
                this.O.onPull(Math.abs(f4 - scrollX) / clientWidth);
                z3 = true;
            }
            scrollX = f4;
        } else if (scrollX > f5) {
            if (z2) {
                this.P.onPull(Math.abs(scrollX - f5) / clientWidth);
                z3 = true;
            }
            scrollX = f5;
        }
        int i = (int) scrollX;
        this.C = (scrollX - i) + this.C;
        scrollTo(i, getScrollY());
        c(i);
        return z3;
    }

    public final int a(int i, float f2, int i2, int i3) {
        if (Math.abs(i3) <= this.K || Math.abs(i2) <= this.I) {
            i += (int) (f2 + (i >= this.f ? 0.4f : 0.6f));
        } else if (i2 <= 0) {
            i++;
        }
        if (this.b.size() <= 0) {
            return i;
        }
        return Math.max(this.b.get(0).b, Math.min(i, this.b.get(r4.size() - 1).b));
    }

    public final void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.G) {
            int i = actionIndex == 0 ? 1 : 0;
            this.C = motionEvent.getX(i);
            this.G = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.H;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    public final Rect a(Rect rect, View view2) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view2 == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view2.getLeft();
        rect.right = view2.getRight();
        rect.top = view2.getTop();
        rect.bottom = view2.getBottom();
        ViewParent parent = view2.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent;
            rect.left = viewGroup.getLeft() + rect.left;
            rect.right = viewGroup.getRight() + rect.right;
            rect.top = viewGroup.getTop() + rect.top;
            rect.bottom = viewGroup.getBottom() + rect.bottom;
            parent = viewGroup.getParent();
        }
        return rect;
    }
}
