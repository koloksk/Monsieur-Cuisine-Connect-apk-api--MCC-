package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.internal.view.SupportMenuItem;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/* loaded from: classes.dex */
public final class MenuItemCompat {

    @Deprecated
    public static final int SHOW_AS_ACTION_ALWAYS = 2;

    @Deprecated
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;

    @Deprecated
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;

    @Deprecated
    public static final int SHOW_AS_ACTION_NEVER = 0;

    @Deprecated
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
    public static final d a;

    @Deprecated
    public interface OnActionExpandListener {
        boolean onMenuItemActionCollapse(MenuItem menuItem);

        boolean onMenuItemActionExpand(MenuItem menuItem);
    }

    public static class a implements MenuItem.OnActionExpandListener {
        public final /* synthetic */ OnActionExpandListener a;

        public a(OnActionExpandListener onActionExpandListener) {
            this.a = onActionExpandListener;
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return this.a.onMenuItemActionCollapse(menuItem);
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return this.a.onMenuItemActionExpand(menuItem);
        }
    }

    @RequiresApi(26)
    public static class b extends c {
        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, CharSequence charSequence) {
            menuItem.setContentDescription(charSequence);
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public void b(MenuItem menuItem, CharSequence charSequence) {
            menuItem.setTooltipText(charSequence);
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public int c(MenuItem menuItem) {
            return menuItem.getAlphabeticModifiers();
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public PorterDuff.Mode d(MenuItem menuItem) {
            return menuItem.getIconTintMode();
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public CharSequence e(MenuItem menuItem) {
            return menuItem.getContentDescription();
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public CharSequence f(MenuItem menuItem) {
            return menuItem.getTooltipText();
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, char c, char c2, int i, int i2) {
            menuItem.setShortcut(c, c2, i, i2);
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public void b(MenuItem menuItem, char c, int i) {
            menuItem.setNumericShortcut(c, i);
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, char c, int i) {
            menuItem.setAlphabeticShortcut(c, i);
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public int b(MenuItem menuItem) {
            return menuItem.getNumericModifiers();
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, ColorStateList colorStateList) {
            menuItem.setIconTintList(colorStateList);
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public ColorStateList a(MenuItem menuItem) {
            return menuItem.getIconTintList();
        }

        @Override // android.support.v4.view.MenuItemCompat.c, android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, PorterDuff.Mode mode) {
            menuItem.setIconTintMode(mode);
        }
    }

    public static class c implements d {
        @Override // android.support.v4.view.MenuItemCompat.d
        public ColorStateList a(MenuItem menuItem) {
            return null;
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, char c, char c2, int i, int i2) {
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, char c, int i) {
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, ColorStateList colorStateList) {
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, PorterDuff.Mode mode) {
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public void a(MenuItem menuItem, CharSequence charSequence) {
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public int b(MenuItem menuItem) {
            return 0;
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public void b(MenuItem menuItem, char c, int i) {
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public void b(MenuItem menuItem, CharSequence charSequence) {
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public int c(MenuItem menuItem) {
            return 0;
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public PorterDuff.Mode d(MenuItem menuItem) {
            return null;
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public CharSequence e(MenuItem menuItem) {
            return null;
        }

        @Override // android.support.v4.view.MenuItemCompat.d
        public CharSequence f(MenuItem menuItem) {
            return null;
        }
    }

    public interface d {
        ColorStateList a(MenuItem menuItem);

        void a(MenuItem menuItem, char c, char c2, int i, int i2);

        void a(MenuItem menuItem, char c, int i);

        void a(MenuItem menuItem, ColorStateList colorStateList);

        void a(MenuItem menuItem, PorterDuff.Mode mode);

        void a(MenuItem menuItem, CharSequence charSequence);

        int b(MenuItem menuItem);

        void b(MenuItem menuItem, char c, int i);

        void b(MenuItem menuItem, CharSequence charSequence);

        int c(MenuItem menuItem);

        PorterDuff.Mode d(MenuItem menuItem);

        CharSequence e(MenuItem menuItem);

        CharSequence f(MenuItem menuItem);
    }

    static {
        if (Build.VERSION.SDK_INT >= 26) {
            a = new b();
        } else {
            a = new c();
        }
    }

    @Deprecated
    public static boolean collapseActionView(MenuItem menuItem) {
        return menuItem.collapseActionView();
    }

    @Deprecated
    public static boolean expandActionView(MenuItem menuItem) {
        return menuItem.expandActionView();
    }

    public static ActionProvider getActionProvider(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getSupportActionProvider();
        }
        Log.w("MenuItemCompat", "getActionProvider: item does not implement SupportMenuItem; returning null");
        return null;
    }

    @Deprecated
    public static View getActionView(MenuItem menuItem) {
        return menuItem.getActionView();
    }

    public static int getAlphabeticModifiers(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).getAlphabeticModifiers() : a.c(menuItem);
    }

    public static CharSequence getContentDescription(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).getContentDescription() : a.e(menuItem);
    }

    public static ColorStateList getIconTintList(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).getIconTintList() : a.a(menuItem);
    }

    public static PorterDuff.Mode getIconTintMode(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).getIconTintMode() : a.d(menuItem);
    }

    public static int getNumericModifiers(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).getNumericModifiers() : a.b(menuItem);
    }

    public static CharSequence getTooltipText(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).getTooltipText() : a.f(menuItem);
    }

    @Deprecated
    public static boolean isActionViewExpanded(MenuItem menuItem) {
        return menuItem.isActionViewExpanded();
    }

    public static MenuItem setActionProvider(MenuItem menuItem, ActionProvider actionProvider) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).setSupportActionProvider(actionProvider);
        }
        Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
        return menuItem;
    }

    @Deprecated
    public static MenuItem setActionView(MenuItem menuItem, View view2) {
        return menuItem.setActionView(view2);
    }

    public static void setAlphabeticShortcut(MenuItem menuItem, char c2, int i) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem) menuItem).setAlphabeticShortcut(c2, i);
        } else {
            a.a(menuItem, c2, i);
        }
    }

    public static void setContentDescription(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem) menuItem).setContentDescription(charSequence);
        } else {
            a.a(menuItem, charSequence);
        }
    }

    public static void setIconTintList(MenuItem menuItem, ColorStateList colorStateList) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem) menuItem).setIconTintList(colorStateList);
        } else {
            a.a(menuItem, colorStateList);
        }
    }

    public static void setIconTintMode(MenuItem menuItem, PorterDuff.Mode mode) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem) menuItem).setIconTintMode(mode);
        } else {
            a.a(menuItem, mode);
        }
    }

    public static void setNumericShortcut(MenuItem menuItem, char c2, int i) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem) menuItem).setNumericShortcut(c2, i);
        } else {
            a.b(menuItem, c2, i);
        }
    }

    @Deprecated
    public static MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
        return menuItem.setOnActionExpandListener(new a(onActionExpandListener));
    }

    public static void setShortcut(MenuItem menuItem, char c2, char c3, int i, int i2) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem) menuItem).setShortcut(c2, c3, i, i2);
        } else {
            a.a(menuItem, c2, c3, i, i2);
        }
    }

    @Deprecated
    public static void setShowAsAction(MenuItem menuItem, int i) {
        menuItem.setShowAsAction(i);
    }

    public static void setTooltipText(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem) menuItem).setTooltipText(charSequence);
        } else {
            a.b(menuItem, charSequence);
        }
    }

    @Deprecated
    public static MenuItem setActionView(MenuItem menuItem, int i) {
        return menuItem.setActionView(i);
    }
}
