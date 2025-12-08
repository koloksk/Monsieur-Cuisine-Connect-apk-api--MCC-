package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.design.R;
import android.support.v4.util.Pools;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.TooltipCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import defpackage.p2;
import defpackage.q1;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import sound.SoundLength;

@ViewPager.DecorView
/* loaded from: classes.dex */
public class TabLayout extends HorizontalScrollView {
    public static final Pools.Pool<Tab> E = new Pools.SynchronizedPool(16);
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    public TabLayoutOnPageChangeListener A;
    public b B;
    public boolean C;
    public final Pools.Pool<e> D;
    public final ArrayList<Tab> a;
    public Tab b;
    public final d c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public ColorStateList i;
    public float j;
    public float k;
    public final int l;
    public int m;
    public final int n;
    public final int o;
    public final int p;
    public int q;
    public int r;
    public int s;
    public OnTabSelectedListener t;
    public final ArrayList<OnTabSelectedListener> u;
    public OnTabSelectedListener v;
    public ValueAnimator w;
    public ViewPager x;
    public PagerAdapter y;
    public DataSetObserver z;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Mode {
    }

    public interface OnTabSelectedListener {
        void onTabReselected(Tab tab);

        void onTabSelected(Tab tab);

        void onTabUnselected(Tab tab);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface TabGravity {
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public final WeakReference<TabLayout> a;
        public int b;
        public int c;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.a = new WeakReference<>(tabLayout);
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            this.b = this.c;
            this.c = i;
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
            TabLayout tabLayout = this.a.get();
            if (tabLayout != null) {
                tabLayout.a(i, f, this.c != 2 || this.b == 1, (this.c == 2 && this.b == 0) ? false : true);
            }
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            TabLayout tabLayout = this.a.get();
            if (tabLayout == null || tabLayout.getSelectedTabPosition() == i || i >= tabLayout.getTabCount()) {
                return;
            }
            int i2 = this.c;
            tabLayout.a(tabLayout.getTabAt(i), i2 == 0 || (i2 == 2 && this.b == 0));
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        public final ViewPager a;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.a = viewPager;
        }

        @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
        public void onTabReselected(Tab tab) {
        }

        @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
        public void onTabSelected(Tab tab) throws Resources.NotFoundException {
            this.a.setCurrentItem(tab.getPosition());
        }

        @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
        public void onTabUnselected(Tab tab) {
        }
    }

    public class a implements ValueAnimator.AnimatorUpdateListener {
        public a() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            TabLayout.this.scrollTo(((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
        }
    }

    public class b implements ViewPager.OnAdapterChangeListener {
        public boolean a;

        public b() {
        }

        @Override // android.support.v4.view.ViewPager.OnAdapterChangeListener
        public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2) {
            TabLayout tabLayout = TabLayout.this;
            if (tabLayout.x == viewPager) {
                tabLayout.a(pagerAdapter2, this.a);
            }
        }
    }

    public class c extends DataSetObserver {
        public c() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            TabLayout.this.c();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            TabLayout.this.c();
        }
    }

    public TabLayout(Context context) {
        this(context, null);
    }

    private int getDefaultHeight() {
        int size = this.a.size();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i < size) {
                Tab tab = this.a.get(i);
                if (tab != null && tab.getIcon() != null && !TextUtils.isEmpty(tab.getText())) {
                    z = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        return z ? 72 : 48;
    }

    private float getScrollPosition() {
        return r0.c + this.c.d;
    }

    private int getTabMinWidth() {
        int i = this.n;
        if (i != -1) {
            return i;
        }
        if (this.s == 0) {
            return this.p;
        }
        return 0;
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.c.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    private void setSelectedTabView(int i) {
        int childCount = this.c.getChildCount();
        if (i < childCount) {
            int i2 = 0;
            while (i2 < childCount) {
                this.c.getChildAt(i2).setSelected(i2 == i);
                i2++;
            }
        }
    }

    public void a(int i, float f, boolean z, boolean z2) {
        int iRound = Math.round(i + f);
        if (iRound < 0 || iRound >= this.c.getChildCount()) {
            return;
        }
        if (z2) {
            d dVar = this.c;
            ValueAnimator valueAnimator = dVar.h;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                dVar.h.cancel();
            }
            dVar.c = i;
            dVar.d = f;
            dVar.a();
        }
        ValueAnimator valueAnimator2 = this.w;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            this.w.cancel();
        }
        scrollTo(a(i, f), 0);
        if (z) {
            setSelectedTabView(iRound);
        }
    }

    public void addOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        if (this.u.contains(onTabSelectedListener)) {
            return;
        }
        this.u.add(onTabSelectedListener);
    }

    public void addTab(@NonNull Tab tab) {
        addTab(tab, this.a.isEmpty());
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view2) {
        a(view2);
    }

    public int b(int i) {
        return Math.round(getResources().getDisplayMetrics().density * i);
    }

    public void c() {
        int currentItem;
        removeAllTabs();
        PagerAdapter pagerAdapter = this.y;
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
            for (int i = 0; i < count; i++) {
                addTab(newTab().setText(this.y.getPageTitle(i)), false);
            }
            ViewPager viewPager = this.x;
            if (viewPager == null || count <= 0 || (currentItem = viewPager.getCurrentItem()) == getSelectedTabPosition() || currentItem >= getTabCount()) {
                return;
            }
            a(getTabAt(currentItem), true);
        }
    }

    public void clearOnTabSelectedListeners() {
        this.u.clear();
    }

    public int getSelectedTabPosition() {
        Tab tab = this.b;
        if (tab != null) {
            return tab.getPosition();
        }
        return -1;
    }

    @Nullable
    public Tab getTabAt(int i) {
        if (i < 0 || i >= getTabCount()) {
            return null;
        }
        return this.a.get(i);
    }

    public int getTabCount() {
        return this.a.size();
    }

    public int getTabGravity() {
        return this.r;
    }

    public int getTabMaxWidth() {
        return this.m;
    }

    public int getTabMode() {
        return this.s;
    }

    @Nullable
    public ColorStateList getTabTextColors() {
        return this.i;
    }

    @NonNull
    public Tab newTab() {
        Tab tabAcquire = E.acquire();
        if (tabAcquire == null) {
            tabAcquire = new Tab();
        }
        tabAcquire.g = this;
        Pools.Pool<e> pool = this.D;
        e eVarAcquire = pool != null ? pool.acquire() : null;
        if (eVarAcquire == null) {
            eVarAcquire = new e(getContext());
        }
        if (tabAcquire != eVarAcquire.a) {
            eVarAcquire.a = tabAcquire;
            eVarAcquire.a();
        }
        eVarAcquire.setFocusable(true);
        eVarAcquire.setMinimumWidth(getTabMinWidth());
        tabAcquire.h = eVarAcquire;
        return tabAcquire;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.x == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                a((ViewPager) parent, true, true);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.C) {
            setupWithViewPager(null);
            this.C = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0075  */
    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMeasure(int r6, int r7) {
        /*
            r5 = this;
            int r0 = r5.getDefaultHeight()
            int r0 = r5.b(r0)
            int r1 = r5.getPaddingTop()
            int r1 = r1 + r0
            int r0 = r5.getPaddingBottom()
            int r0 = r0 + r1
            int r1 = android.view.View.MeasureSpec.getMode(r7)
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = 1073741824(0x40000000, float:2.0)
            if (r1 == r2) goto L24
            if (r1 == 0) goto L1f
            goto L30
        L1f:
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r3)
            goto L30
        L24:
            int r7 = android.view.View.MeasureSpec.getSize(r7)
            int r7 = java.lang.Math.min(r0, r7)
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r3)
        L30:
            int r0 = android.view.View.MeasureSpec.getSize(r6)
            int r1 = android.view.View.MeasureSpec.getMode(r6)
            if (r1 == 0) goto L49
            int r1 = r5.o
            if (r1 <= 0) goto L3f
            goto L47
        L3f:
            r1 = 56
            int r1 = r5.b(r1)
            int r1 = r0 - r1
        L47:
            r5.m = r1
        L49:
            super.onMeasure(r6, r7)
            int r6 = r5.getChildCount()
            r0 = 1
            if (r6 != r0) goto L97
            r6 = 0
            android.view.View r1 = r5.getChildAt(r6)
            int r2 = r5.s
            if (r2 == 0) goto L6a
            if (r2 == r0) goto L5f
            goto L77
        L5f:
            int r2 = r1.getMeasuredWidth()
            int r4 = r5.getMeasuredWidth()
            if (r2 == r4) goto L75
            goto L76
        L6a:
            int r2 = r1.getMeasuredWidth()
            int r4 = r5.getMeasuredWidth()
            if (r2 >= r4) goto L75
            goto L76
        L75:
            r0 = r6
        L76:
            r6 = r0
        L77:
            if (r6 == 0) goto L97
            int r6 = r5.getPaddingTop()
            int r0 = r5.getPaddingBottom()
            int r0 = r0 + r6
            android.view.ViewGroup$LayoutParams r6 = r1.getLayoutParams()
            int r6 = r6.height
            int r6 = android.widget.HorizontalScrollView.getChildMeasureSpec(r7, r0, r6)
            int r7 = r5.getMeasuredWidth()
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r3)
            r1.measure(r7, r6)
        L97:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.TabLayout.onMeasure(int, int):void");
    }

    public void removeAllTabs() {
        for (int childCount = this.c.getChildCount() - 1; childCount >= 0; childCount--) {
            c(childCount);
        }
        Iterator<Tab> it = this.a.iterator();
        while (it.hasNext()) {
            Tab next = it.next();
            it.remove();
            next.a();
            E.release(next);
        }
        this.b = null;
    }

    public void removeOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        this.u.remove(onTabSelectedListener);
    }

    public void removeTab(Tab tab) {
        if (tab.g != this) {
            throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
        }
        removeTabAt(tab.getPosition());
    }

    public void removeTabAt(int i) {
        Tab tab = this.b;
        int position = tab != null ? tab.getPosition() : 0;
        c(i);
        Tab tabRemove = this.a.remove(i);
        if (tabRemove != null) {
            tabRemove.a();
            E.release(tabRemove);
        }
        int size = this.a.size();
        for (int i2 = i; i2 < size; i2++) {
            this.a.get(i2).e = i2;
        }
        if (position == i) {
            a(this.a.isEmpty() ? null : this.a.get(Math.max(0, i - 1)), true);
        }
    }

    @Deprecated
    public void setOnTabSelectedListener(@Nullable OnTabSelectedListener onTabSelectedListener) {
        OnTabSelectedListener onTabSelectedListener2 = this.t;
        if (onTabSelectedListener2 != null) {
            removeOnTabSelectedListener(onTabSelectedListener2);
        }
        this.t = onTabSelectedListener;
        if (onTabSelectedListener != null) {
            addOnTabSelectedListener(onTabSelectedListener);
        }
    }

    public void setScrollAnimatorListener(Animator.AnimatorListener animatorListener) {
        b();
        this.w.addListener(animatorListener);
    }

    public void setScrollPosition(int i, float f, boolean z) {
        a(i, f, z, true);
    }

    public void setSelectedTabIndicatorColor(@ColorInt int i) {
        d dVar = this.c;
        if (dVar.b.getColor() != i) {
            dVar.b.setColor(i);
            ViewCompat.postInvalidateOnAnimation(dVar);
        }
    }

    public void setSelectedTabIndicatorHeight(int i) {
        d dVar = this.c;
        if (dVar.a != i) {
            dVar.a = i;
            ViewCompat.postInvalidateOnAnimation(dVar);
        }
    }

    public void setTabGravity(int i) {
        if (this.r != i) {
            this.r = i;
            a();
        }
    }

    public void setTabMode(int i) {
        if (i != this.s) {
            this.s = i;
            a();
        }
    }

    public void setTabTextColors(@Nullable ColorStateList colorStateList) {
        if (this.i != colorStateList) {
            this.i = colorStateList;
            int size = this.a.size();
            for (int i = 0; i < size; i++) {
                this.a.get(i).b();
            }
        }
    }

    @Deprecated
    public void setTabsFromPagerAdapter(@Nullable PagerAdapter pagerAdapter) {
        a(pagerAdapter, false);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        setupWithViewPager(viewPager, true);
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }

    public static final class Tab {
        public static final int INVALID_POSITION = -1;
        public Object a;
        public Drawable b;
        public CharSequence c;
        public CharSequence d;
        public int e = -1;
        public View f;
        public TabLayout g;
        public e h;

        public void a() {
            this.g = null;
            this.h = null;
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = -1;
            this.f = null;
        }

        public void b() {
            e eVar = this.h;
            if (eVar != null) {
                eVar.a();
            }
        }

        @Nullable
        public CharSequence getContentDescription() {
            return this.d;
        }

        @Nullable
        public View getCustomView() {
            return this.f;
        }

        @Nullable
        public Drawable getIcon() {
            return this.b;
        }

        public int getPosition() {
            return this.e;
        }

        @Nullable
        public Object getTag() {
            return this.a;
        }

        @Nullable
        public CharSequence getText() {
            return this.c;
        }

        public boolean isSelected() {
            TabLayout tabLayout = this.g;
            if (tabLayout != null) {
                return tabLayout.getSelectedTabPosition() == this.e;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public void select() {
            TabLayout tabLayout = this.g;
            if (tabLayout == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            tabLayout.a(this, true);
        }

        @NonNull
        public Tab setContentDescription(@StringRes int i) {
            TabLayout tabLayout = this.g;
            if (tabLayout != null) {
                return setContentDescription(tabLayout.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setCustomView(@Nullable View view2) {
            this.f = view2;
            b();
            return this;
        }

        @NonNull
        public Tab setIcon(@Nullable Drawable drawable) {
            this.b = drawable;
            b();
            return this;
        }

        @NonNull
        public Tab setTag(@Nullable Object obj) {
            this.a = obj;
            return this;
        }

        @NonNull
        public Tab setText(@Nullable CharSequence charSequence) {
            this.c = charSequence;
            b();
            return this;
        }

        @NonNull
        public Tab setCustomView(@LayoutRes int i) {
            return setCustomView(LayoutInflater.from(this.h.getContext()).inflate(i, (ViewGroup) this.h, false));
        }

        @NonNull
        public Tab setIcon(@DrawableRes int i) {
            TabLayout tabLayout = this.g;
            if (tabLayout != null) {
                return setIcon(AppCompatResources.getDrawable(tabLayout.getContext(), i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setText(@StringRes int i) {
            TabLayout tabLayout = this.g;
            if (tabLayout != null) {
                return setText(tabLayout.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setContentDescription(@Nullable CharSequence charSequence) {
            this.d = charSequence;
            b();
            return this;
        }
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void addTab(@NonNull Tab tab, int i) {
        addTab(tab, i, this.a.isEmpty());
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view2, int i) {
        a(view2);
    }

    public final void b() {
        if (this.w == null) {
            ValueAnimator valueAnimator = new ValueAnimator();
            this.w = valueAnimator;
            valueAnimator.setInterpolator(q1.b);
            this.w.setDuration(300L);
            this.w.addUpdateListener(new a());
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean z) {
        a(viewPager, z, false);
    }

    public TabLayout(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        this.a = new ArrayList<>();
        this.m = Integer.MAX_VALUE;
        this.u = new ArrayList<>();
        this.D = new Pools.SimplePool(12);
        p2.a(context);
        setHorizontalScrollBarEnabled(false);
        d dVar = new d(context);
        this.c = dVar;
        super.addView(dVar, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TabLayout, i, R.style.Widget_Design_TabLayout);
        d dVar2 = this.c;
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, 0);
        if (dVar2.a != dimensionPixelSize) {
            dVar2.a = dimensionPixelSize;
            ViewCompat.postInvalidateOnAnimation(dVar2);
        }
        d dVar3 = this.c;
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.TabLayout_tabIndicatorColor, 0);
        if (dVar3.b.getColor() != color) {
            dVar3.b.setColor(color);
            ViewCompat.postInvalidateOnAnimation(dVar3);
        }
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
        this.g = dimensionPixelSize2;
        this.f = dimensionPixelSize2;
        this.e = dimensionPixelSize2;
        this.d = dimensionPixelSize2;
        this.d = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, dimensionPixelSize2);
        this.e = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.e);
        this.f = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.f);
        this.g = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.g);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
        this.h = resourceId;
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, android.support.v7.appcompat.R.styleable.TextAppearance);
        try {
            this.j = typedArrayObtainStyledAttributes2.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.TextAppearance_android_textSize, 0);
            this.i = typedArrayObtainStyledAttributes2.getColorStateList(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor);
            typedArrayObtainStyledAttributes2.recycle();
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.TabLayout_tabTextColor)) {
                this.i = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.TabLayout_tabTextColor);
            }
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
                this.i = a(this.i.getDefaultColor(), typedArrayObtainStyledAttributes.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0));
            }
            this.n = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
            this.o = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
            this.l = typedArrayObtainStyledAttributes.getResourceId(R.styleable.TabLayout_tabBackground, 0);
            this.q = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
            this.s = typedArrayObtainStyledAttributes.getInt(R.styleable.TabLayout_tabMode, 1);
            this.r = typedArrayObtainStyledAttributes.getInt(R.styleable.TabLayout_tabGravity, 0);
            typedArrayObtainStyledAttributes.recycle();
            Resources resources = getResources();
            this.k = resources.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
            this.p = resources.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
            a();
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes2.recycle();
            throw th;
        }
    }

    public void addTab(@NonNull Tab tab, boolean z) {
        addTab(tab, this.a.size(), z);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup, android.view.ViewManager
    public void addView(View view2, ViewGroup.LayoutParams layoutParams) {
        a(view2);
    }

    public void addTab(@NonNull Tab tab, int i, boolean z) {
        if (tab.g == this) {
            tab.e = i;
            this.a.add(i, tab);
            int size = this.a.size();
            while (true) {
                i++;
                if (i >= size) {
                    break;
                } else {
                    this.a.get(i).e = i;
                }
            }
            e eVar = tab.h;
            d dVar = this.c;
            int position = tab.getPosition();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
            a(layoutParams);
            dVar.addView(eVar, position, layoutParams);
            if (z) {
                tab.select();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view2, int i, ViewGroup.LayoutParams layoutParams) {
        a(view2);
    }

    public void setTabTextColors(int i, int i2) {
        setTabTextColors(a(i, i2));
    }

    public final void c(int i) {
        e eVar = (e) this.c.getChildAt(i);
        this.c.removeViewAt(i);
        if (eVar != null) {
            if (eVar.a != null) {
                eVar.a = null;
                eVar.a();
            }
            eVar.setSelected(false);
            this.D.release(eVar);
        }
        requestLayout();
    }

    public class d extends LinearLayout {
        public int a;
        public final Paint b;
        public int c;
        public float d;
        public int e;
        public int f;
        public int g;
        public ValueAnimator h;

        public class a implements ValueAnimator.AnimatorUpdateListener {
            public final /* synthetic */ int a;
            public final /* synthetic */ int b;
            public final /* synthetic */ int c;
            public final /* synthetic */ int d;

            public a(int i, int i2, int i3, int i4) {
                this.a = i;
                this.b = i2;
                this.c = i3;
                this.d = i4;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                d dVar = d.this;
                int iA = q1.a(this.a, this.b, animatedFraction);
                int iRound = Math.round(animatedFraction * (this.d - r2)) + this.c;
                if (iA == dVar.f && iRound == dVar.g) {
                    return;
                }
                dVar.f = iA;
                dVar.g = iRound;
                ViewCompat.postInvalidateOnAnimation(dVar);
            }
        }

        public class b extends AnimatorListenerAdapter {
            public final /* synthetic */ int a;

            public b(int i) {
                this.a = i;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                d dVar = d.this;
                dVar.c = this.a;
                dVar.d = 0.0f;
            }
        }

        public d(Context context) {
            super(context);
            this.c = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            setWillNotDraw(false);
            this.b = new Paint();
        }

        public final void a() {
            int right;
            View childAt = getChildAt(this.c);
            int i = -1;
            if (childAt == null || childAt.getWidth() <= 0) {
                right = -1;
            } else {
                int left = childAt.getLeft();
                right = childAt.getRight();
                if (this.d > 0.0f && this.c < getChildCount() - 1) {
                    View childAt2 = getChildAt(this.c + 1);
                    float left2 = this.d * childAt2.getLeft();
                    float f = this.d;
                    left = (int) (((1.0f - f) * left) + left2);
                    right = (int) (((1.0f - this.d) * right) + (f * childAt2.getRight()));
                }
                i = left;
            }
            if (i == this.f && right == this.g) {
                return;
            }
            this.f = i;
            this.g = right;
            ViewCompat.postInvalidateOnAnimation(this);
        }

        @Override // android.view.View
        public void draw(Canvas canvas) {
            super.draw(canvas);
            int i = this.f;
            if (i < 0 || this.g <= i) {
                return;
            }
            canvas.drawRect(i, getHeight() - this.a, this.g, getHeight(), this.b);
        }

        @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            ValueAnimator valueAnimator = this.h;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                a();
                return;
            }
            this.h.cancel();
            a(this.c, Math.round((1.0f - this.h.getAnimatedFraction()) * this.h.getDuration()));
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (View.MeasureSpec.getMode(i) != 1073741824) {
                return;
            }
            TabLayout tabLayout = TabLayout.this;
            boolean z = true;
            if (tabLayout.s == 1 && tabLayout.r == 1) {
                int childCount = getChildCount();
                int iMax = 0;
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt = getChildAt(i3);
                    if (childAt.getVisibility() == 0) {
                        iMax = Math.max(iMax, childAt.getMeasuredWidth());
                    }
                }
                if (iMax <= 0) {
                    return;
                }
                if (iMax * childCount <= getMeasuredWidth() - (TabLayout.this.b(16) * 2)) {
                    boolean z2 = false;
                    for (int i4 = 0; i4 < childCount; i4++) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getChildAt(i4).getLayoutParams();
                        if (layoutParams.width != iMax || layoutParams.weight != 0.0f) {
                            layoutParams.width = iMax;
                            layoutParams.weight = 0.0f;
                            z2 = true;
                        }
                    }
                    z = z2;
                } else {
                    TabLayout tabLayout2 = TabLayout.this;
                    tabLayout2.r = 0;
                    tabLayout2.a(false);
                }
                if (z) {
                    super.onMeasure(i, i2);
                }
            }
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onRtlPropertiesChanged(int i) {
            super.onRtlPropertiesChanged(i);
        }

        public void a(int i, int i2) {
            int i3;
            int i4;
            ValueAnimator valueAnimator = this.h;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.h.cancel();
            }
            boolean z = ViewCompat.getLayoutDirection(this) == 1;
            View childAt = getChildAt(i);
            if (childAt == null) {
                a();
                return;
            }
            int left = childAt.getLeft();
            int right = childAt.getRight();
            if (Math.abs(i - this.c) <= 1) {
                i3 = this.f;
                i4 = this.g;
            } else {
                int iB = TabLayout.this.b(24);
                i3 = (i >= this.c ? !z : z) ? left - iB : iB + right;
                i4 = i3;
            }
            if (i3 == left && i4 == right) {
                return;
            }
            ValueAnimator valueAnimator2 = new ValueAnimator();
            this.h = valueAnimator2;
            valueAnimator2.setInterpolator(q1.b);
            valueAnimator2.setDuration(i2);
            valueAnimator2.setFloatValues(0.0f, 1.0f);
            valueAnimator2.addUpdateListener(new a(i3, left, i4, right));
            valueAnimator2.addListener(new b(i));
            valueAnimator2.start();
        }
    }

    public final void a(@Nullable ViewPager viewPager, boolean z, boolean z2) {
        ViewPager viewPager2 = this.x;
        if (viewPager2 != null) {
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = this.A;
            if (tabLayoutOnPageChangeListener != null) {
                viewPager2.removeOnPageChangeListener(tabLayoutOnPageChangeListener);
            }
            b bVar = this.B;
            if (bVar != null) {
                this.x.removeOnAdapterChangeListener(bVar);
            }
        }
        OnTabSelectedListener onTabSelectedListener = this.v;
        if (onTabSelectedListener != null) {
            removeOnTabSelectedListener(onTabSelectedListener);
            this.v = null;
        }
        if (viewPager != null) {
            this.x = viewPager;
            if (this.A == null) {
                this.A = new TabLayoutOnPageChangeListener(this);
            }
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener2 = this.A;
            tabLayoutOnPageChangeListener2.c = 0;
            tabLayoutOnPageChangeListener2.b = 0;
            viewPager.addOnPageChangeListener(tabLayoutOnPageChangeListener2);
            ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener = new ViewPagerOnTabSelectedListener(viewPager);
            this.v = viewPagerOnTabSelectedListener;
            addOnTabSelectedListener(viewPagerOnTabSelectedListener);
            PagerAdapter adapter2 = viewPager.getAdapter();
            if (adapter2 != null) {
                a(adapter2, z);
            }
            if (this.B == null) {
                this.B = new b();
            }
            b bVar2 = this.B;
            bVar2.a = z;
            viewPager.addOnAdapterChangeListener(bVar2);
            setScrollPosition(viewPager.getCurrentItem(), 0.0f, true);
        } else {
            this.x = null;
            a((PagerAdapter) null, false);
        }
        this.C = z2;
    }

    public class e extends LinearLayout {
        public Tab a;
        public TextView b;
        public ImageView c;
        public View d;
        public TextView e;
        public ImageView f;
        public int g;

        public e(Context context) {
            super(context);
            this.g = 2;
            int i = TabLayout.this.l;
            if (i != 0) {
                ViewCompat.setBackground(this, AppCompatResources.getDrawable(context, i));
            }
            ViewCompat.setPaddingRelative(this, TabLayout.this.d, TabLayout.this.e, TabLayout.this.f, TabLayout.this.g);
            setGravity(17);
            setOrientation(1);
            setClickable(true);
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND));
        }

        public final void a() {
            Tab tab = this.a;
            View customView = tab != null ? tab.getCustomView() : null;
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(customView);
                    }
                    addView(customView);
                }
                this.d = customView;
                TextView textView = this.b;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                ImageView imageView = this.c;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.c.setImageDrawable(null);
                }
                TextView textView2 = (TextView) customView.findViewById(android.R.id.text1);
                this.e = textView2;
                if (textView2 != null) {
                    this.g = TextViewCompat.getMaxLines(textView2);
                }
                this.f = (ImageView) customView.findViewById(android.R.id.icon);
            } else {
                View view2 = this.d;
                if (view2 != null) {
                    removeView(view2);
                    this.d = null;
                }
                this.e = null;
                this.f = null;
            }
            boolean z = false;
            if (this.d == null) {
                if (this.c == null) {
                    ImageView imageView2 = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_icon, (ViewGroup) this, false);
                    addView(imageView2, 0);
                    this.c = imageView2;
                }
                if (this.b == null) {
                    TextView textView3 = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_text, (ViewGroup) this, false);
                    addView(textView3);
                    this.b = textView3;
                    this.g = TextViewCompat.getMaxLines(textView3);
                }
                TextViewCompat.setTextAppearance(this.b, TabLayout.this.h);
                ColorStateList colorStateList = TabLayout.this.i;
                if (colorStateList != null) {
                    this.b.setTextColor(colorStateList);
                }
                a(this.b, this.c);
            } else if (this.e != null || this.f != null) {
                a(this.e, this.f);
            }
            if (tab != null && tab.isSelected()) {
                z = true;
            }
            setSelected(z);
        }

        @Override // android.view.View
        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(ActionBar.Tab.class.getName());
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(ActionBar.Tab.class.getName());
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x0098  */
        @Override // android.widget.LinearLayout, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onMeasure(int r8, int r9) {
            /*
                r7 = this;
                int r0 = android.view.View.MeasureSpec.getSize(r8)
                int r1 = android.view.View.MeasureSpec.getMode(r8)
                android.support.design.widget.TabLayout r2 = android.support.design.widget.TabLayout.this
                int r2 = r2.getTabMaxWidth()
                if (r2 <= 0) goto L1e
                if (r1 == 0) goto L14
                if (r0 <= r2) goto L1e
            L14:
                android.support.design.widget.TabLayout r8 = android.support.design.widget.TabLayout.this
                int r8 = r8.m
                r0 = -2147483648(0xffffffff80000000, float:-0.0)
                int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r0)
            L1e:
                super.onMeasure(r8, r9)
                android.widget.TextView r0 = r7.b
                if (r0 == 0) goto La8
                r7.getResources()
                android.support.design.widget.TabLayout r0 = android.support.design.widget.TabLayout.this
                float r0 = r0.j
                int r1 = r7.g
                android.widget.ImageView r2 = r7.c
                r3 = 1
                if (r2 == 0) goto L3b
                int r2 = r2.getVisibility()
                if (r2 != 0) goto L3b
                r1 = r3
                goto L49
            L3b:
                android.widget.TextView r2 = r7.b
                if (r2 == 0) goto L49
                int r2 = r2.getLineCount()
                if (r2 <= r3) goto L49
                android.support.design.widget.TabLayout r0 = android.support.design.widget.TabLayout.this
                float r0 = r0.k
            L49:
                android.widget.TextView r2 = r7.b
                float r2 = r2.getTextSize()
                android.widget.TextView r4 = r7.b
                int r4 = r4.getLineCount()
                android.widget.TextView r5 = r7.b
                int r5 = android.support.v4.widget.TextViewCompat.getMaxLines(r5)
                int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r2 != 0) goto L63
                if (r5 < 0) goto La8
                if (r1 == r5) goto La8
            L63:
                android.support.design.widget.TabLayout r5 = android.support.design.widget.TabLayout.this
                int r5 = r5.s
                r6 = 0
                if (r5 != r3) goto L99
                if (r2 <= 0) goto L99
                if (r4 != r3) goto L99
                android.widget.TextView r2 = r7.b
                android.text.Layout r2 = r2.getLayout()
                if (r2 == 0) goto L98
                float r4 = r2.getLineWidth(r6)
                android.text.TextPaint r2 = r2.getPaint()
                float r2 = r2.getTextSize()
                float r2 = r0 / r2
                float r2 = r2 * r4
                int r4 = r7.getMeasuredWidth()
                int r5 = r7.getPaddingLeft()
                int r4 = r4 - r5
                int r5 = r7.getPaddingRight()
                int r4 = r4 - r5
                float r4 = (float) r4
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 <= 0) goto L99
            L98:
                r3 = r6
            L99:
                if (r3 == 0) goto La8
                android.widget.TextView r2 = r7.b
                r2.setTextSize(r6, r0)
                android.widget.TextView r0 = r7.b
                r0.setMaxLines(r1)
                super.onMeasure(r8, r9)
            La8:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.TabLayout.e.onMeasure(int, int):void");
        }

        @Override // android.view.View
        public boolean performClick() {
            boolean zPerformClick = super.performClick();
            if (this.a == null) {
                return zPerformClick;
            }
            if (!zPerformClick) {
                playSoundEffect(0);
            }
            this.a.select();
            return true;
        }

        @Override // android.view.View
        public void setSelected(boolean z) {
            if (isSelected() != z) {
            }
            super.setSelected(z);
            TextView textView = this.b;
            if (textView != null) {
                textView.setSelected(z);
            }
            ImageView imageView = this.c;
            if (imageView != null) {
                imageView.setSelected(z);
            }
            View view2 = this.d;
            if (view2 != null) {
                view2.setSelected(z);
            }
        }

        public final void a(@Nullable TextView textView, @Nullable ImageView imageView) {
            Tab tab = this.a;
            Drawable icon = tab != null ? tab.getIcon() : null;
            Tab tab2 = this.a;
            CharSequence text = tab2 != null ? tab2.getText() : null;
            Tab tab3 = this.a;
            CharSequence contentDescription = tab3 != null ? tab3.getContentDescription() : null;
            int iB = 0;
            if (imageView != null) {
                if (icon != null) {
                    imageView.setImageDrawable(icon);
                    imageView.setVisibility(0);
                    setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable(null);
                }
                imageView.setContentDescription(contentDescription);
            }
            boolean z = !TextUtils.isEmpty(text);
            if (textView != null) {
                if (z) {
                    textView.setText(text);
                    textView.setVisibility(0);
                    setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText((CharSequence) null);
                }
                textView.setContentDescription(contentDescription);
            }
            if (imageView != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                if (z && imageView.getVisibility() == 0) {
                    iB = TabLayout.this.b(8);
                }
                if (iB != marginLayoutParams.bottomMargin) {
                    marginLayoutParams.bottomMargin = iB;
                    imageView.requestLayout();
                }
            }
            TooltipCompat.setTooltipText(this, z ? null : contentDescription);
        }
    }

    public void a(@Nullable PagerAdapter pagerAdapter, boolean z) {
        DataSetObserver dataSetObserver;
        PagerAdapter pagerAdapter2 = this.y;
        if (pagerAdapter2 != null && (dataSetObserver = this.z) != null) {
            pagerAdapter2.unregisterDataSetObserver(dataSetObserver);
        }
        this.y = pagerAdapter;
        if (z && pagerAdapter != null) {
            if (this.z == null) {
                this.z = new c();
            }
            pagerAdapter.registerDataSetObserver(this.z);
        }
        c();
    }

    public final void a(View view2) {
        if (view2 instanceof TabItem) {
            TabItem tabItem = (TabItem) view2;
            Tab tabNewTab = newTab();
            CharSequence charSequence = tabItem.a;
            if (charSequence != null) {
                tabNewTab.setText(charSequence);
            }
            Drawable drawable = tabItem.b;
            if (drawable != null) {
                tabNewTab.setIcon(drawable);
            }
            int i = tabItem.c;
            if (i != 0) {
                tabNewTab.setCustomView(i);
            }
            if (!TextUtils.isEmpty(tabItem.getContentDescription())) {
                tabNewTab.setContentDescription(tabItem.getContentDescription());
            }
            addTab(tabNewTab);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    public final void a(LinearLayout.LayoutParams layoutParams) {
        if (this.s == 1 && this.r == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
        } else {
            layoutParams.width = -2;
            layoutParams.weight = 0.0f;
        }
    }

    public final void a(int i) {
        boolean z;
        if (i == -1) {
            return;
        }
        if (getWindowToken() != null && ViewCompat.isLaidOut(this)) {
            d dVar = this.c;
            int childCount = dVar.getChildCount();
            int i2 = 0;
            while (true) {
                if (i2 >= childCount) {
                    z = false;
                    break;
                } else {
                    if (dVar.getChildAt(i2).getWidth() <= 0) {
                        z = true;
                        break;
                    }
                    i2++;
                }
            }
            if (!z) {
                int scrollX = getScrollX();
                int iA = a(i, 0.0f);
                if (scrollX != iA) {
                    b();
                    this.w.setIntValues(scrollX, iA);
                    this.w.start();
                }
                this.c.a(i, SoundLength.SMALL_THRESHOLD);
                return;
            }
        }
        setScrollPosition(i, 0.0f, true);
    }

    public void a(Tab tab, boolean z) {
        Tab tab2 = this.b;
        if (tab2 == tab) {
            if (tab2 != null) {
                for (int size = this.u.size() - 1; size >= 0; size--) {
                    this.u.get(size).onTabReselected(tab);
                }
                a(tab.getPosition());
                return;
            }
            return;
        }
        int position = tab != null ? tab.getPosition() : -1;
        if (z) {
            if ((tab2 == null || tab2.getPosition() == -1) && position != -1) {
                setScrollPosition(position, 0.0f, true);
            } else {
                a(position);
            }
            if (position != -1) {
                setSelectedTabView(position);
            }
        }
        if (tab2 != null) {
            for (int size2 = this.u.size() - 1; size2 >= 0; size2--) {
                this.u.get(size2).onTabUnselected(tab2);
            }
        }
        this.b = tab;
        if (tab != null) {
            for (int size3 = this.u.size() - 1; size3 >= 0; size3--) {
                this.u.get(size3).onTabSelected(tab);
            }
        }
    }

    public final int a(int i, float f) {
        if (this.s != 0) {
            return 0;
        }
        View childAt = this.c.getChildAt(i);
        int i2 = i + 1;
        View childAt2 = i2 < this.c.getChildCount() ? this.c.getChildAt(i2) : null;
        int width = childAt != null ? childAt.getWidth() : 0;
        int width2 = childAt2 != null ? childAt2.getWidth() : 0;
        int left = ((width / 2) + childAt.getLeft()) - (getWidth() / 2);
        int i3 = (int) ((width + width2) * 0.5f * f);
        return ViewCompat.getLayoutDirection(this) == 0 ? left + i3 : left - i3;
    }

    public final void a() {
        ViewCompat.setPaddingRelative(this.c, this.s == 0 ? Math.max(0, this.q - this.d) : 0, 0, 0, 0);
        int i = this.s;
        if (i == 0) {
            this.c.setGravity(GravityCompat.START);
        } else if (i == 1) {
            this.c.setGravity(1);
        }
        a(true);
    }

    public void a(boolean z) {
        for (int i = 0; i < this.c.getChildCount(); i++) {
            View childAt = this.c.getChildAt(i);
            childAt.setMinimumWidth(getTabMinWidth());
            a((LinearLayout.LayoutParams) childAt.getLayoutParams());
            if (z) {
                childAt.requestLayout();
            }
        }
    }

    public static ColorStateList a(int i, int i2) {
        return new ColorStateList(new int[][]{HorizontalScrollView.SELECTED_STATE_SET, HorizontalScrollView.EMPTY_STATE_SET}, new int[]{i2, i});
    }
}
