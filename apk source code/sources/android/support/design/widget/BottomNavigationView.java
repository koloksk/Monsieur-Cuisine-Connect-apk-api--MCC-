package android.support.design.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import defpackage.p2;

/* loaded from: classes.dex */
public class BottomNavigationView extends FrameLayout {
    public static final int[] g = {R.attr.state_checked};
    public static final int[] h = {-16842910};
    public final MenuBuilder a;
    public final BottomNavigationMenuView b;
    public final BottomNavigationPresenter c;
    public MenuInflater d;
    public OnNavigationItemSelectedListener e;
    public OnNavigationItemReselectedListener f;

    public interface OnNavigationItemReselectedListener {
        void onNavigationItemReselected(@NonNull MenuItem menuItem);
    }

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public Bundle b;

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

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeBundle(this.b);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.b = parcel.readBundle(classLoader);
        }
    }

    public class a implements MenuBuilder.Callback {
        public a() {
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            if (BottomNavigationView.this.f == null || menuItem.getItemId() != BottomNavigationView.this.getSelectedItemId()) {
                OnNavigationItemSelectedListener onNavigationItemSelectedListener = BottomNavigationView.this.e;
                return (onNavigationItemSelectedListener == null || onNavigationItemSelectedListener.onNavigationItemSelected(menuItem)) ? false : true;
            }
            BottomNavigationView.this.f.onNavigationItemReselected(menuItem);
            return true;
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
        }
    }

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    private MenuInflater getMenuInflater() {
        if (this.d == null) {
            this.d = new SupportMenuInflater(getContext());
        }
        return this.d;
    }

    public final ColorStateList a(int i) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i2 = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        return new ColorStateList(new int[][]{h, g, FrameLayout.EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(h, defaultColor), i2, defaultColor});
    }

    @DrawableRes
    public int getItemBackgroundResource() {
        return this.b.getItemBackgroundRes();
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.b.getIconTintList();
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.b.getItemTextColor();
    }

    public int getMaxItemCount() {
        return 5;
    }

    @NonNull
    public Menu getMenu() {
        return this.a;
    }

    @IdRes
    public int getSelectedItemId() {
        return this.b.getSelectedItemId();
    }

    public void inflateMenu(int i) {
        this.c.setUpdateSuspended(true);
        getMenuInflater().inflate(i, this.a);
        this.c.setUpdateSuspended(false);
        this.c.updateMenuView(true);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.a.restorePresenterStates(savedState.b);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        savedState.b = bundle;
        this.a.savePresenterStates(bundle);
        return savedState;
    }

    public void setItemBackgroundResource(@DrawableRes int i) {
        this.b.setItemBackgroundRes(i);
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.b.setIconTintList(colorStateList);
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.b.setItemTextColor(colorStateList);
    }

    public void setOnNavigationItemReselectedListener(@Nullable OnNavigationItemReselectedListener onNavigationItemReselectedListener) {
        this.f = onNavigationItemReselectedListener;
    }

    public void setOnNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        this.e = onNavigationItemSelectedListener;
    }

    public void setSelectedItemId(@IdRes int i) {
        MenuItem menuItemFindItem = this.a.findItem(i);
        if (menuItemFindItem == null || this.a.performItemAction(menuItemFindItem, this.c, 0)) {
            return;
        }
        menuItemFindItem.setChecked(true);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = new BottomNavigationPresenter();
        p2.a(context);
        this.a = new BottomNavigationMenu(context);
        this.b = new BottomNavigationMenuView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.b.setLayoutParams(layoutParams);
        this.c.setBottomNavigationMenuView(this.b);
        this.c.setId(1);
        this.b.setPresenter(this.c);
        this.a.addMenuPresenter(this.c);
        this.c.initForMenu(getContext(), this.a);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, android.support.design.R.styleable.BottomNavigationView, i, android.support.design.R.style.Widget_Design_BottomNavigationView);
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.BottomNavigationView_itemIconTint)) {
            this.b.setIconTintList(tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.design.R.styleable.BottomNavigationView_itemIconTint));
        } else {
            this.b.setIconTintList(a(R.attr.textColorSecondary));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.BottomNavigationView_itemTextColor)) {
            this.b.setItemTextColor(tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.design.R.styleable.BottomNavigationView_itemTextColor));
        } else {
            this.b.setItemTextColor(a(R.attr.textColorSecondary));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.BottomNavigationView_elevation)) {
            ViewCompat.setElevation(this, tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.design.R.styleable.BottomNavigationView_elevation, 0));
        }
        this.b.setItemBackgroundRes(tintTypedArrayObtainStyledAttributes.getResourceId(android.support.design.R.styleable.BottomNavigationView_itemBackground, 0));
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.BottomNavigationView_menu)) {
            inflateMenu(tintTypedArrayObtainStyledAttributes.getResourceId(android.support.design.R.styleable.BottomNavigationView_menu, 0));
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        addView(this.b, layoutParams);
        this.a.setCallback(new a());
    }
}
