package android.support.design.internal;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.util.Pools;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class BottomNavigationMenuView extends ViewGroup implements MenuView {
    public final TransitionSet a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    public final View.OnClickListener f;
    public final Pools.Pool<BottomNavigationItemView> g;
    public boolean h;
    public BottomNavigationItemView[] i;
    public int j;
    public int k;
    public ColorStateList l;
    public ColorStateList m;
    public int n;
    public int[] o;
    public BottomNavigationPresenter p;
    public MenuBuilder q;

    public class a implements View.OnClickListener {
        public a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            MenuItemImpl itemData = ((BottomNavigationItemView) view2).getItemData();
            BottomNavigationMenuView bottomNavigationMenuView = BottomNavigationMenuView.this;
            if (bottomNavigationMenuView.q.performItemAction(itemData, bottomNavigationMenuView.p, 0)) {
                return;
            }
            itemData.setChecked(true);
        }
    }

    public BottomNavigationMenuView(Context context) {
        this(context, null);
    }

    private BottomNavigationItemView getNewItem() {
        BottomNavigationItemView bottomNavigationItemViewAcquire = this.g.acquire();
        return bottomNavigationItemViewAcquire == null ? new BottomNavigationItemView(getContext()) : bottomNavigationItemViewAcquire;
    }

    public void buildMenuView() {
        removeAllViews();
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.i;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                this.g.release(bottomNavigationItemView);
            }
        }
        if (this.q.size() == 0) {
            this.j = 0;
            this.k = 0;
            this.i = null;
            return;
        }
        this.i = new BottomNavigationItemView[this.q.size()];
        this.h = this.q.size() > 3;
        for (int i = 0; i < this.q.size(); i++) {
            this.p.setUpdateSuspended(true);
            this.q.getItem(i).setCheckable(true);
            this.p.setUpdateSuspended(false);
            BottomNavigationItemView newItem = getNewItem();
            this.i[i] = newItem;
            newItem.setIconTintList(this.l);
            newItem.setTextColor(this.m);
            newItem.setItemBackground(this.n);
            newItem.setShiftingMode(this.h);
            newItem.initialize((MenuItemImpl) this.q.getItem(i), 0);
            newItem.setItemPosition(i);
            newItem.setOnClickListener(this.f);
            addView(newItem);
        }
        int iMin = Math.min(this.q.size() - 1, this.k);
        this.k = iMin;
        this.q.getItem(iMin).setChecked(true);
    }

    @Nullable
    public ColorStateList getIconTintList() {
        return this.l;
    }

    public int getItemBackgroundRes() {
        return this.n;
    }

    public ColorStateList getItemTextColor() {
        return this.m;
    }

    public int getSelectedItemId() {
        return this.j;
    }

    @Override // android.support.v7.view.menu.MenuView
    public int getWindowAnimations() {
        return 0;
    }

    @Override // android.support.v7.view.menu.MenuView
    public void initialize(MenuBuilder menuBuilder) {
        this.q = menuBuilder;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int measuredWidth = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection(this) == 1) {
                    int i8 = i5 - measuredWidth;
                    childAt.layout(i8 - childAt.getMeasuredWidth(), 0, i8, i6);
                } else {
                    childAt.layout(measuredWidth, 0, childAt.getMeasuredWidth() + measuredWidth, i6);
                }
                measuredWidth += childAt.getMeasuredWidth();
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int childCount = getChildCount();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.e, 1073741824);
        if (this.h) {
            int i3 = childCount - 1;
            int iMin = Math.min(size - (this.c * i3), this.d);
            int i4 = size - iMin;
            int iMin2 = Math.min(i4 / i3, this.b);
            int i5 = i4 - (i3 * iMin2);
            int i6 = 0;
            while (i6 < childCount) {
                this.o[i6] = i6 == this.k ? iMin : iMin2;
                if (i5 > 0) {
                    int[] iArr = this.o;
                    iArr[i6] = iArr[i6] + 1;
                    i5--;
                }
                i6++;
            }
        } else {
            int iMin3 = Math.min(size / (childCount == 0 ? 1 : childCount), this.d);
            int i7 = size - (iMin3 * childCount);
            for (int i8 = 0; i8 < childCount; i8++) {
                int[] iArr2 = this.o;
                iArr2[i8] = iMin3;
                if (i7 > 0) {
                    iArr2[i8] = iArr2[i8] + 1;
                    i7--;
                }
            }
        }
        int measuredWidth = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(this.o[i9], 1073741824), iMakeMeasureSpec);
                childAt.getLayoutParams().width = childAt.getMeasuredWidth();
                measuredWidth += childAt.getMeasuredWidth();
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(measuredWidth, View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), 0), View.resolveSizeAndState(this.e, iMakeMeasureSpec, 0));
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.l = colorStateList;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.i;
        if (bottomNavigationItemViewArr == null) {
            return;
        }
        for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
            bottomNavigationItemView.setIconTintList(colorStateList);
        }
    }

    public void setItemBackgroundRes(int i) {
        this.n = i;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.i;
        if (bottomNavigationItemViewArr == null) {
            return;
        }
        for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
            bottomNavigationItemView.setItemBackground(i);
        }
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.m = colorStateList;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.i;
        if (bottomNavigationItemViewArr == null) {
            return;
        }
        for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
            bottomNavigationItemView.setTextColor(colorStateList);
        }
    }

    public void setPresenter(BottomNavigationPresenter bottomNavigationPresenter) {
        this.p = bottomNavigationPresenter;
    }

    public void updateMenuView() {
        int size = this.q.size();
        if (size != this.i.length) {
            buildMenuView();
            return;
        }
        int i = this.j;
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = this.q.getItem(i2);
            if (item.isChecked()) {
                this.j = item.getItemId();
                this.k = i2;
            }
        }
        if (i != this.j) {
            TransitionManager.beginDelayedTransition(this, this.a);
        }
        for (int i3 = 0; i3 < size; i3++) {
            this.p.setUpdateSuspended(true);
            this.i[i3].initialize((MenuItemImpl) this.q.getItem(i3), 0);
            this.p.setUpdateSuspended(false);
        }
    }

    public BottomNavigationMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.g = new Pools.SynchronizedPool(5);
        this.h = true;
        this.j = 0;
        this.k = 0;
        Resources resources = getResources();
        this.b = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
        this.c = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
        this.d = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
        this.e = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_height);
        AutoTransition autoTransition = new AutoTransition();
        this.a = autoTransition;
        autoTransition.setOrdering(0);
        this.a.setDuration(115L);
        this.a.setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator());
        this.a.addTransition(new TextScale());
        this.f = new a();
        this.o = new int[5];
    }
}
