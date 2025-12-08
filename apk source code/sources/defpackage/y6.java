package defpackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class y6 extends ActionBar {
    public DecorToolbar a;
    public boolean b;
    public Window.Callback c;
    public boolean d;
    public boolean e;
    public ArrayList<ActionBar.OnMenuVisibilityListener> f = new ArrayList<>();
    public final Runnable g = new a();
    public final Toolbar.OnMenuItemClickListener h = new b();

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            y6 y6Var = y6.this;
            Menu menuB = y6Var.b();
            MenuBuilder menuBuilder = menuB instanceof MenuBuilder ? (MenuBuilder) menuB : null;
            if (menuBuilder != null) {
                menuBuilder.stopDispatchingItemsChanged();
            }
            try {
                menuB.clear();
                if (!y6Var.c.onCreatePanelMenu(0, menuB) || !y6Var.c.onPreparePanel(0, null, menuB)) {
                    menuB.clear();
                }
            } finally {
                if (menuBuilder != null) {
                    menuBuilder.startDispatchingItemsChanged();
                }
            }
        }
    }

    public class b implements Toolbar.OnMenuItemClickListener {
        public b() {
        }

        @Override // android.support.v7.widget.Toolbar.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            return y6.this.c.onMenuItemSelected(0, menuItem);
        }
    }

    public final class c implements MenuPresenter.Callback {
        public boolean a;

        public c() {
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (this.a) {
                return;
            }
            this.a = true;
            y6.this.a.dismissPopupMenus();
            Window.Callback callback = y6.this.c;
            if (callback != null) {
                callback.onPanelClosed(108, menuBuilder);
            }
            this.a = false;
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback = y6.this.c;
            if (callback == null) {
                return false;
            }
            callback.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    public final class d implements MenuBuilder.Callback {
        public d() {
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return false;
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            y6 y6Var = y6.this;
            if (y6Var.c != null) {
                if (y6Var.a.isOverflowMenuShowing()) {
                    y6.this.c.onPanelClosed(108, menuBuilder);
                } else if (y6.this.c.onPreparePanel(0, null, menuBuilder)) {
                    y6.this.c.onMenuOpened(108, menuBuilder);
                }
            }
        }
    }

    public class e extends WindowCallbackWrapper {
        public e(Window.Callback callback) {
            super(callback);
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public View onCreatePanelView(int i) {
            return i == 0 ? new View(y6.this.a.getContext()) : super.onCreatePanelView(i);
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onPreparePanel(int i, View view2, Menu menu) {
            boolean zOnPreparePanel = super.onPreparePanel(i, view2, menu);
            if (zOnPreparePanel) {
                y6 y6Var = y6.this;
                if (!y6Var.b) {
                    y6Var.a.setMenuPrepared();
                    y6.this.b = true;
                }
            }
            return zOnPreparePanel;
        }
    }

    public y6(Toolbar toolbar, CharSequence charSequence, Window.Callback callback) {
        this.a = new ToolbarWidgetWrapper(toolbar, false);
        e eVar = new e(callback);
        this.c = eVar;
        this.a.setWindowCallback(eVar);
        toolbar.setOnMenuItemClickListener(this.h);
        this.a.setWindowTitle(charSequence);
    }

    @Override // android.support.v7.app.ActionBar
    public void a() {
        this.a.getViewGroup().removeCallbacks(this.g);
    }

    @Override // android.support.v7.app.ActionBar
    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.f.add(onMenuVisibilityListener);
    }

    @Override // android.support.v7.app.ActionBar
    public void addTab(ActionBar.Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public final Menu b() {
        if (!this.d) {
            this.a.setMenuCallbacks(new c(), new d());
            this.d = true;
        }
        return this.a.getMenu();
    }

    @Override // android.support.v7.app.ActionBar
    public boolean closeOptionsMenu() {
        return this.a.hideOverflowMenu();
    }

    @Override // android.support.v7.app.ActionBar
    public boolean collapseActionView() {
        if (!this.a.hasExpandedActionView()) {
            return false;
        }
        this.a.collapseActionView();
        return true;
    }

    @Override // android.support.v7.app.ActionBar
    public void dispatchMenuVisibilityChanged(boolean z) {
        if (z == this.e) {
            return;
        }
        this.e = z;
        int size = this.f.size();
        for (int i = 0; i < size; i++) {
            this.f.get(i).onMenuVisibilityChanged(z);
        }
    }

    @Override // android.support.v7.app.ActionBar
    public View getCustomView() {
        return this.a.getCustomView();
    }

    @Override // android.support.v7.app.ActionBar
    public int getDisplayOptions() {
        return this.a.getDisplayOptions();
    }

    @Override // android.support.v7.app.ActionBar
    public float getElevation() {
        return ViewCompat.getElevation(this.a.getViewGroup());
    }

    @Override // android.support.v7.app.ActionBar
    public int getHeight() {
        return this.a.getHeight();
    }

    @Override // android.support.v7.app.ActionBar
    public int getNavigationItemCount() {
        return 0;
    }

    @Override // android.support.v7.app.ActionBar
    public int getNavigationMode() {
        return 0;
    }

    @Override // android.support.v7.app.ActionBar
    public int getSelectedNavigationIndex() {
        return -1;
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab getSelectedTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public CharSequence getSubtitle() {
        return this.a.getSubtitle();
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab getTabAt(int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public int getTabCount() {
        return 0;
    }

    @Override // android.support.v7.app.ActionBar
    public Context getThemedContext() {
        return this.a.getContext();
    }

    @Override // android.support.v7.app.ActionBar
    public CharSequence getTitle() {
        return this.a.getTitle();
    }

    @Override // android.support.v7.app.ActionBar
    public void hide() {
        this.a.setVisibility(8);
    }

    @Override // android.support.v7.app.ActionBar
    public boolean invalidateOptionsMenu() {
        this.a.getViewGroup().removeCallbacks(this.g);
        ViewCompat.postOnAnimation(this.a.getViewGroup(), this.g);
        return true;
    }

    @Override // android.support.v7.app.ActionBar
    public boolean isShowing() {
        return this.a.getVisibility() == 0;
    }

    @Override // android.support.v7.app.ActionBar
    public boolean isTitleTruncated() {
        return super.isTitleTruncated();
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab newTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.support.v7.app.ActionBar
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Menu menuB = b();
        if (menuB == null) {
            return false;
        }
        menuB.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return menuB.performShortcut(i, keyEvent, 0);
    }

    @Override // android.support.v7.app.ActionBar
    public boolean onMenuKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            this.a.showOverflowMenu();
        }
        return true;
    }

    @Override // android.support.v7.app.ActionBar
    public boolean openOptionsMenu() {
        return this.a.showOverflowMenu();
    }

    @Override // android.support.v7.app.ActionBar
    public void removeAllTabs() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.f.remove(onMenuVisibilityListener);
    }

    @Override // android.support.v7.app.ActionBar
    public void removeTab(ActionBar.Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public void removeTabAt(int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public void selectTab(ActionBar.Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        this.a.setBackgroundDrawable(drawable);
    }

    @Override // android.support.v7.app.ActionBar
    public void setCustomView(View view2) {
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-2, -2);
        if (view2 != null) {
            view2.setLayoutParams(layoutParams);
        }
        this.a.setCustomView(view2);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(z ? 4 : 0, 4);
    }

    @Override // android.support.v7.app.ActionBar
    @SuppressLint({"WrongConstant"})
    public void setDisplayOptions(int i) {
        setDisplayOptions(i, -1);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayShowCustomEnabled(boolean z) {
        setDisplayOptions(z ? 16 : 0, 16);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayShowHomeEnabled(boolean z) {
        setDisplayOptions(z ? 2 : 0, 2);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayShowTitleEnabled(boolean z) {
        setDisplayOptions(z ? 8 : 0, 8);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayUseLogoEnabled(boolean z) {
        setDisplayOptions(z ? 1 : 0, 1);
    }

    @Override // android.support.v7.app.ActionBar
    public void setElevation(float f) {
        ViewCompat.setElevation(this.a.getViewGroup(), f);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.a.setNavigationContentDescription(charSequence);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeAsUpIndicator(Drawable drawable) {
        this.a.setNavigationIcon(drawable);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeButtonEnabled(boolean z) {
    }

    @Override // android.support.v7.app.ActionBar
    public void setIcon(int i) {
        this.a.setIcon(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.a.setDropdownParams(spinnerAdapter, new x6(onNavigationListener));
    }

    @Override // android.support.v7.app.ActionBar
    public void setLogo(int i) {
        this.a.setLogo(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setNavigationMode(int i) {
        if (i == 2) {
            throw new IllegalArgumentException("Tabs not supported in this configuration");
        }
        this.a.setNavigationMode(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setSelectedNavigationItem(int i) {
        if (this.a.getNavigationMode() != 1) {
            throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
        this.a.setDropdownSelectedPosition(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setShowHideAnimationEnabled(boolean z) {
    }

    @Override // android.support.v7.app.ActionBar
    public void setSplitBackgroundDrawable(Drawable drawable) {
    }

    @Override // android.support.v7.app.ActionBar
    public void setStackedBackgroundDrawable(Drawable drawable) {
    }

    @Override // android.support.v7.app.ActionBar
    public void setSubtitle(CharSequence charSequence) {
        this.a.setSubtitle(charSequence);
    }

    @Override // android.support.v7.app.ActionBar
    public void setTitle(CharSequence charSequence) {
        this.a.setTitle(charSequence);
    }

    @Override // android.support.v7.app.ActionBar
    public void setWindowTitle(CharSequence charSequence) {
        this.a.setWindowTitle(charSequence);
    }

    @Override // android.support.v7.app.ActionBar
    public void show() {
        this.a.setVisibility(0);
    }

    @Override // android.support.v7.app.ActionBar
    public void addTab(ActionBar.Tab tab, boolean z) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayOptions(int i, int i2) {
        this.a.setDisplayOptions((i & i2) | ((~i2) & this.a.getDisplayOptions()));
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeActionContentDescription(int i) {
        this.a.setNavigationContentDescription(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeAsUpIndicator(int i) {
        this.a.setNavigationIcon(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setIcon(Drawable drawable) {
        this.a.setIcon(drawable);
    }

    @Override // android.support.v7.app.ActionBar
    public void setLogo(Drawable drawable) {
        this.a.setLogo(drawable);
    }

    @Override // android.support.v7.app.ActionBar
    public void setSubtitle(int i) {
        DecorToolbar decorToolbar = this.a;
        decorToolbar.setSubtitle(i != 0 ? decorToolbar.getContext().getText(i) : null);
    }

    @Override // android.support.v7.app.ActionBar
    public void setTitle(int i) {
        DecorToolbar decorToolbar = this.a;
        decorToolbar.setTitle(i != 0 ? decorToolbar.getContext().getText(i) : null);
    }

    @Override // android.support.v7.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i, boolean z) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.support.v7.app.ActionBar
    public void setCustomView(View view2, ActionBar.LayoutParams layoutParams) {
        if (view2 != null) {
            view2.setLayoutParams(layoutParams);
        }
        this.a.setCustomView(view2);
    }

    @Override // android.support.v7.app.ActionBar
    public void setCustomView(int i) {
        View viewInflate = LayoutInflater.from(this.a.getContext()).inflate(i, this.a.getViewGroup(), false);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-2, -2);
        if (viewInflate != null) {
            viewInflate.setLayoutParams(layoutParams);
        }
        this.a.setCustomView(viewInflate);
    }
}
