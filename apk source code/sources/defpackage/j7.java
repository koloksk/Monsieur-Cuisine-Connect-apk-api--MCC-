package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;

@RequiresApi(14)
/* loaded from: classes.dex */
public class j7 extends e7<SupportMenu> implements Menu {
    public j7(Context context, SupportMenu supportMenu) {
        super(context, supportMenu);
    }

    @Override // android.view.Menu
    public MenuItem add(CharSequence charSequence) {
        return a(((SupportMenu) this.a).add(charSequence));
    }

    @Override // android.view.Menu
    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2 = menuItemArr != null ? new MenuItem[menuItemArr.length] : null;
        int iAddIntentOptions = ((SupportMenu) this.a).addIntentOptions(i, i2, i3, componentName, intentArr, intent, i4, menuItemArr2);
        if (menuItemArr2 != null) {
            int length = menuItemArr2.length;
            for (int i5 = 0; i5 < length; i5++) {
                menuItemArr[i5] = a(menuItemArr2[i5]);
            }
        }
        return iAddIntentOptions;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return a(((SupportMenu) this.a).addSubMenu(charSequence));
    }

    @Override // android.view.Menu
    public void clear() {
        Map<SupportMenuItem, MenuItem> map = this.c;
        if (map != null) {
            map.clear();
        }
        Map<SupportSubMenu, SubMenu> map2 = this.d;
        if (map2 != null) {
            map2.clear();
        }
        ((SupportMenu) this.a).clear();
    }

    @Override // android.view.Menu
    public void close() {
        ((SupportMenu) this.a).close();
    }

    @Override // android.view.Menu
    public MenuItem findItem(int i) {
        return a(((SupportMenu) this.a).findItem(i));
    }

    @Override // android.view.Menu
    public MenuItem getItem(int i) {
        return a(((SupportMenu) this.a).getItem(i));
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        return ((SupportMenu) this.a).hasVisibleItems();
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return ((SupportMenu) this.a).isShortcutKey(i, keyEvent);
    }

    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        return ((SupportMenu) this.a).performIdentifierAction(i, i2);
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        return ((SupportMenu) this.a).performShortcut(i, keyEvent, i2);
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        Map<SupportMenuItem, MenuItem> map = this.c;
        if (map != null) {
            Iterator<SupportMenuItem> it = map.keySet().iterator();
            while (it.hasNext()) {
                if (i == it.next().getGroupId()) {
                    it.remove();
                }
            }
        }
        ((SupportMenu) this.a).removeGroup(i);
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        Map<SupportMenuItem, MenuItem> map = this.c;
        if (map != null) {
            Iterator<SupportMenuItem> it = map.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (i == it.next().getItemId()) {
                    it.remove();
                    break;
                }
            }
        }
        ((SupportMenu) this.a).removeItem(i);
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        ((SupportMenu) this.a).setGroupCheckable(i, z, z2);
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        ((SupportMenu) this.a).setGroupEnabled(i, z);
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        ((SupportMenu) this.a).setGroupVisible(i, z);
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        ((SupportMenu) this.a).setQwertyMode(z);
    }

    @Override // android.view.Menu
    public int size() {
        return ((SupportMenu) this.a).size();
    }

    @Override // android.view.Menu
    public MenuItem add(int i) {
        return a(((SupportMenu) this.a).add(i));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i) {
        return a(((SupportMenu) this.a).addSubMenu(i));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return a(((SupportMenu) this.a).add(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        return a(((SupportMenu) this.a).addSubMenu(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, int i4) {
        return a(((SupportMenu) this.a).add(i, i2, i3, i4));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return a(((SupportMenu) this.a).addSubMenu(i, i2, i3, i4));
    }
}
