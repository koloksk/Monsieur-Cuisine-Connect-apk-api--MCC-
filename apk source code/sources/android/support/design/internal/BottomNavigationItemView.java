package android.support.design.internal;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.TooltipCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class BottomNavigationItemView extends FrameLayout implements MenuView.ItemView {
    public static final int INVALID_ITEM_POSITION = -1;
    public static final int[] l = {R.attr.state_checked};
    public final int a;
    public final int b;
    public final float c;
    public final float d;
    public boolean e;
    public ImageView f;
    public final TextView g;
    public final TextView h;
    public int i;
    public MenuItemImpl j;
    public ColorStateList k;

    public BottomNavigationItemView(@NonNull Context context) {
        this(context, null);
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.j;
    }

    public int getItemPosition() {
        return this.i;
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl menuItemImpl, int i) {
        this.j = menuItemImpl;
        setCheckable(menuItemImpl.isCheckable());
        setChecked(menuItemImpl.isChecked());
        setEnabled(menuItemImpl.isEnabled());
        setIcon(menuItemImpl.getIcon());
        setTitle(menuItemImpl.getTitle());
        setId(menuItemImpl.getItemId());
        setContentDescription(menuItemImpl.getContentDescription());
        TooltipCompat.setTooltipText(this, menuItemImpl.getTooltipText());
    }

    @Override // android.view.ViewGroup, android.view.View
    public int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        MenuItemImpl menuItemImpl = this.j;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.j.isChecked()) {
            FrameLayout.mergeDrawableStates(iArrOnCreateDrawableState, l);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    public void setCheckable(boolean z) {
        refreshDrawableState();
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    public void setChecked(boolean z) {
        this.h.setPivotX(r0.getWidth() / 2);
        this.h.setPivotY(r0.getBaseline());
        this.g.setPivotX(r0.getWidth() / 2);
        this.g.setPivotY(r0.getBaseline());
        if (this.e) {
            if (z) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.f.getLayoutParams();
                layoutParams.gravity = 49;
                layoutParams.topMargin = this.a;
                this.f.setLayoutParams(layoutParams);
                this.h.setVisibility(0);
                this.h.setScaleX(1.0f);
                this.h.setScaleY(1.0f);
            } else {
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.f.getLayoutParams();
                layoutParams2.gravity = 17;
                layoutParams2.topMargin = this.a;
                this.f.setLayoutParams(layoutParams2);
                this.h.setVisibility(4);
                this.h.setScaleX(0.5f);
                this.h.setScaleY(0.5f);
            }
            this.g.setVisibility(4);
        } else if (z) {
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.f.getLayoutParams();
            layoutParams3.gravity = 49;
            layoutParams3.topMargin = this.a + this.b;
            this.f.setLayoutParams(layoutParams3);
            this.h.setVisibility(0);
            this.g.setVisibility(4);
            this.h.setScaleX(1.0f);
            this.h.setScaleY(1.0f);
            this.g.setScaleX(this.c);
            this.g.setScaleY(this.c);
        } else {
            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) this.f.getLayoutParams();
            layoutParams4.gravity = 49;
            layoutParams4.topMargin = this.a;
            this.f.setLayoutParams(layoutParams4);
            this.h.setVisibility(4);
            this.g.setVisibility(0);
            this.h.setScaleX(this.d);
            this.h.setScaleY(this.d);
            this.g.setScaleX(1.0f);
            this.g.setScaleY(1.0f);
        }
        refreshDrawableState();
    }

    @Override // android.view.View, android.support.v7.view.menu.MenuView.ItemView
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.g.setEnabled(z);
        this.h.setEnabled(z);
        this.f.setEnabled(z);
        if (z) {
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND));
        } else {
            ViewCompat.setPointerIcon(this, null);
        }
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                drawable = constantState.newDrawable();
            }
            drawable = DrawableCompat.wrap(drawable).mutate();
            DrawableCompat.setTintList(drawable, this.k);
        }
        this.f.setImageDrawable(drawable);
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.k = colorStateList;
        MenuItemImpl menuItemImpl = this.j;
        if (menuItemImpl != null) {
            setIcon(menuItemImpl.getIcon());
        }
    }

    public void setItemBackground(int i) {
        ViewCompat.setBackground(this, i == 0 ? null : ContextCompat.getDrawable(getContext(), i));
    }

    public void setItemPosition(int i) {
        this.i = i;
    }

    public void setShiftingMode(boolean z) {
        this.e = z;
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    public void setShortcut(boolean z, char c) {
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.g.setTextColor(colorStateList);
        this.h.setTextColor(colorStateList);
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    public void setTitle(CharSequence charSequence) {
        this.g.setText(charSequence);
        this.h.setText(charSequence);
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }

    public BottomNavigationItemView(@NonNull Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationItemView(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        this.i = -1;
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(android.support.design.R.dimen.design_bottom_navigation_text_size);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(android.support.design.R.dimen.design_bottom_navigation_active_text_size);
        this.a = resources.getDimensionPixelSize(android.support.design.R.dimen.design_bottom_navigation_margin);
        this.b = dimensionPixelSize - dimensionPixelSize2;
        float f = dimensionPixelSize2;
        float f2 = dimensionPixelSize;
        this.c = (f * 1.0f) / f2;
        this.d = (f2 * 1.0f) / f;
        LayoutInflater.from(context).inflate(android.support.design.R.layout.design_bottom_navigation_item, (ViewGroup) this, true);
        setBackgroundResource(android.support.design.R.drawable.design_bottom_navigation_item_background);
        this.f = (ImageView) findViewById(android.support.design.R.id.icon);
        this.g = (TextView) findViewById(android.support.design.R.id.smallLabel);
        this.h = (TextView) findViewById(android.support.design.R.id.largeLabel);
    }
}
