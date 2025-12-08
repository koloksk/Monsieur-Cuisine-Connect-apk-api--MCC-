package android.support.design.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import defpackage.p2;

/* loaded from: classes.dex */
public class NavigationView extends ScrimInsetsFrameLayout {
    public static final int[] i = {R.attr.state_checked};
    public static final int[] j = {-16842910};
    public final NavigationMenu d;
    public final NavigationMenuPresenter e;
    public OnNavigationItemSelectedListener f;
    public int g;
    public MenuInflater h;

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    public class a implements MenuBuilder.Callback {
        public a() {
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            OnNavigationItemSelectedListener onNavigationItemSelectedListener = NavigationView.this.f;
            return onNavigationItemSelectedListener != null && onNavigationItemSelectedListener.onNavigationItemSelected(menuItem);
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
        }
    }

    public NavigationView(Context context) {
        this(context, null);
    }

    private MenuInflater getMenuInflater() {
        if (this.h == null) {
            this.h = new SupportMenuInflater(getContext());
        }
        return this.h;
    }

    public final ColorStateList a(int i2) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i2, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i3 = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        return new ColorStateList(new int[][]{j, i, FrameLayout.EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(j, defaultColor), i3, defaultColor});
    }

    public void addHeaderView(@NonNull View view2) {
        this.e.addHeaderView(view2);
    }

    public int getHeaderCount() {
        return this.e.getHeaderCount();
    }

    public View getHeaderView(int i2) {
        return this.e.getHeaderView(i2);
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.e.getItemBackground();
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.e.getItemTintList();
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.e.getItemTextColor();
    }

    public Menu getMenu() {
        return this.d;
    }

    public View inflateHeaderView(@LayoutRes int i2) {
        return this.e.inflateHeaderView(i2);
    }

    public void inflateMenu(int i2) {
        this.e.setUpdateSuspended(true);
        getMenuInflater().inflate(i2, this.d);
        this.e.setUpdateSuspended(false);
        this.e.updateMenuView(false);
    }

    @Override // android.support.design.internal.ScrimInsetsFrameLayout
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onInsetsChanged(WindowInsetsCompat windowInsetsCompat) {
        this.e.dispatchApplyWindowInsets(windowInsetsCompat);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            i2 = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i2), this.g), 1073741824);
        } else if (mode == 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(this.g, 1073741824);
        }
        super.onMeasure(i2, i3);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.d.restorePresenterStates(savedState.menuState);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        savedState.menuState = bundle;
        this.d.savePresenterStates(bundle);
        return savedState;
    }

    public void removeHeaderView(@NonNull View view2) {
        this.e.removeHeaderView(view2);
    }

    public void setCheckedItem(@IdRes int i2) {
        MenuItem menuItemFindItem = this.d.findItem(i2);
        if (menuItemFindItem != null) {
            this.e.setCheckedItem((MenuItemImpl) menuItemFindItem);
        }
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        this.e.setItemBackground(drawable);
    }

    public void setItemBackgroundResource(@DrawableRes int i2) {
        setItemBackground(ContextCompat.getDrawable(getContext(), i2));
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.e.setItemIconTintList(colorStateList);
    }

    public void setItemTextAppearance(@StyleRes int i2) {
        this.e.setItemTextAppearance(i2);
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.e.setItemTextColor(colorStateList);
    }

    public void setNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        this.f = onNavigationItemSelectedListener;
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public Bundle menuState;

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

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.menuState = parcel.readBundle(classLoader);
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeBundle(this.menuState);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public NavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NavigationView(Context context, AttributeSet attributeSet, int i2) {
        ColorStateList colorStateListA;
        int resourceId;
        boolean z;
        super(context, attributeSet, i2);
        this.e = new NavigationMenuPresenter();
        p2.a(context);
        this.d = new NavigationMenu(context);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, android.support.design.R.styleable.NavigationView, i2, android.support.design.R.style.Widget_Design_NavigationView);
        ViewCompat.setBackground(this, tintTypedArrayObtainStyledAttributes.getDrawable(android.support.design.R.styleable.NavigationView_android_background));
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.NavigationView_elevation)) {
            ViewCompat.setElevation(this, tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.design.R.styleable.NavigationView_elevation, 0));
        }
        ViewCompat.setFitsSystemWindows(this, tintTypedArrayObtainStyledAttributes.getBoolean(android.support.design.R.styleable.NavigationView_android_fitsSystemWindows, false));
        this.g = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.design.R.styleable.NavigationView_android_maxWidth, 0);
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.NavigationView_itemIconTint)) {
            colorStateListA = tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.design.R.styleable.NavigationView_itemIconTint);
        } else {
            colorStateListA = a(R.attr.textColorSecondary);
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.NavigationView_itemTextAppearance)) {
            resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(android.support.design.R.styleable.NavigationView_itemTextAppearance, 0);
            z = true;
        } else {
            resourceId = 0;
            z = false;
        }
        ColorStateList colorStateList = tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.NavigationView_itemTextColor) ? tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.design.R.styleable.NavigationView_itemTextColor) : null;
        if (!z && colorStateList == null) {
            colorStateList = a(R.attr.textColorPrimary);
        }
        Drawable drawable = tintTypedArrayObtainStyledAttributes.getDrawable(android.support.design.R.styleable.NavigationView_itemBackground);
        this.d.setCallback(new a());
        this.e.setId(1);
        this.e.initForMenu(context, this.d);
        this.e.setItemIconTintList(colorStateListA);
        if (z) {
            this.e.setItemTextAppearance(resourceId);
        }
        this.e.setItemTextColor(colorStateList);
        this.e.setItemBackground(drawable);
        this.d.addMenuPresenter(this.e);
        addView((View) this.e.getMenuView(this));
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.NavigationView_menu)) {
            inflateMenu(tintTypedArrayObtainStyledAttributes.getResourceId(android.support.design.R.styleable.NavigationView_menu, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.NavigationView_headerLayout)) {
            inflateHeaderView(tintTypedArrayObtainStyledAttributes.getResourceId(android.support.design.R.styleable.NavigationView_headerLayout, 0));
        }
        tintTypedArrayObtainStyledAttributes.recycle();
    }
}
