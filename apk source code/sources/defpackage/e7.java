package defpackage;

import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.support.v4.util.ArrayMap;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class e7<T> extends f7<T> {
    public final Context b;
    public Map<SupportMenuItem, MenuItem> c;
    public Map<SupportSubMenu, SubMenu> d;

    public e7(Context context, T t) {
        super(t);
        this.b = context;
    }

    public final MenuItem a(MenuItem menuItem) {
        if (!(menuItem instanceof SupportMenuItem)) {
            return menuItem;
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
        if (this.c == null) {
            this.c = new ArrayMap();
        }
        MenuItem menuItem2 = this.c.get(menuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        MenuItem menuItemWrapSupportMenuItem = MenuWrapperFactory.wrapSupportMenuItem(this.b, supportMenuItem);
        this.c.put(supportMenuItem, menuItemWrapSupportMenuItem);
        return menuItemWrapSupportMenuItem;
    }

    public final SubMenu a(SubMenu subMenu) {
        if (!(subMenu instanceof SupportSubMenu)) {
            return subMenu;
        }
        SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
        if (this.d == null) {
            this.d = new ArrayMap();
        }
        SubMenu subMenu2 = this.d.get(supportSubMenu);
        if (subMenu2 != null) {
            return subMenu2;
        }
        SubMenu subMenuWrapSupportSubMenu = MenuWrapperFactory.wrapSupportSubMenu(this.b, supportSubMenu);
        this.d.put(supportSubMenu, subMenuWrapSupportSubMenu);
        return subMenuWrapSupportSubMenu;
    }
}
