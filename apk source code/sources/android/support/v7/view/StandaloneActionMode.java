package android.support.v7.view;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.v7.view.ActionMode;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionBarContextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class StandaloneActionMode extends ActionMode implements MenuBuilder.Callback {
    public Context c;
    public ActionBarContextView d;
    public ActionMode.Callback e;
    public WeakReference<View> f;
    public boolean g;
    public boolean h;
    public MenuBuilder i;

    public StandaloneActionMode(Context context, ActionBarContextView actionBarContextView, ActionMode.Callback callback, boolean z) {
        this.c = context;
        this.d = actionBarContextView;
        this.e = callback;
        MenuBuilder defaultShowAsAction = new MenuBuilder(actionBarContextView.getContext()).setDefaultShowAsAction(1);
        this.i = defaultShowAsAction;
        defaultShowAsAction.setCallback(this);
        this.h = z;
    }

    @Override // android.support.v7.view.ActionMode
    public void finish() {
        if (this.g) {
            return;
        }
        this.g = true;
        this.d.sendAccessibilityEvent(32);
        this.e.onDestroyActionMode(this);
    }

    @Override // android.support.v7.view.ActionMode
    public View getCustomView() {
        WeakReference<View> weakReference = this.f;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // android.support.v7.view.ActionMode
    public Menu getMenu() {
        return this.i;
    }

    @Override // android.support.v7.view.ActionMode
    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.d.getContext());
    }

    @Override // android.support.v7.view.ActionMode
    public CharSequence getSubtitle() {
        return this.d.getSubtitle();
    }

    @Override // android.support.v7.view.ActionMode
    public CharSequence getTitle() {
        return this.d.getTitle();
    }

    @Override // android.support.v7.view.ActionMode
    public void invalidate() {
        this.e.onPrepareActionMode(this, this.i);
    }

    @Override // android.support.v7.view.ActionMode
    public boolean isTitleOptional() {
        return this.d.isTitleOptional();
    }

    @Override // android.support.v7.view.ActionMode
    public boolean isUiFocusable() {
        return this.h;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
    }

    public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
    }

    @Override // android.support.v7.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.e.onActionItemClicked(this, menuItem);
    }

    @Override // android.support.v7.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        invalidate();
        this.d.showOverflowMenu();
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return true;
        }
        new MenuPopupHelper(this.d.getContext(), subMenuBuilder).show();
        return true;
    }

    @Override // android.support.v7.view.ActionMode
    public void setCustomView(View view2) {
        this.d.setCustomView(view2);
        this.f = view2 != null ? new WeakReference<>(view2) : null;
    }

    @Override // android.support.v7.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
        this.d.setSubtitle(charSequence);
    }

    @Override // android.support.v7.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        this.d.setTitle(charSequence);
    }

    @Override // android.support.v7.view.ActionMode
    public void setTitleOptionalHint(boolean z) {
        super.setTitleOptionalHint(z);
        this.d.setTitleOptional(z);
    }

    @Override // android.support.v7.view.ActionMode
    public void setSubtitle(int i) {
        setSubtitle(this.c.getString(i));
    }

    @Override // android.support.v7.view.ActionMode
    public void setTitle(int i) {
        setTitle(this.c.getString(i));
    }
}
