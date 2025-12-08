package android.support.v7.view.menu;

import android.support.annotation.RestrictTo;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class MenuAdapter extends BaseAdapter {
    public static final int f = R.layout.abc_popup_menu_item_layout;
    public MenuBuilder a;
    public int b = -1;
    public boolean c;
    public final boolean d;
    public final LayoutInflater e;

    public MenuAdapter(MenuBuilder menuBuilder, LayoutInflater layoutInflater, boolean z) {
        this.d = z;
        this.e = layoutInflater;
        this.a = menuBuilder;
        a();
    }

    public void a() {
        MenuItemImpl expandedItem = this.a.getExpandedItem();
        if (expandedItem != null) {
            ArrayList<MenuItemImpl> nonActionItems = this.a.getNonActionItems();
            int size = nonActionItems.size();
            for (int i = 0; i < size; i++) {
                if (nonActionItems.get(i) == expandedItem) {
                    this.b = i;
                    return;
                }
            }
        }
        this.b = -1;
    }

    public MenuBuilder getAdapterMenu() {
        return this.a;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.b < 0 ? (this.d ? this.a.getNonActionItems() : this.a.getVisibleItems()).size() : r0.size() - 1;
    }

    public boolean getForceShowIcon() {
        return this.c;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view2, ViewGroup viewGroup) {
        if (view2 == null) {
            view2 = this.e.inflate(f, viewGroup, false);
        }
        MenuView.ItemView itemView = (MenuView.ItemView) view2;
        if (this.c) {
            ((ListMenuItemView) view2).setForceShowIcon(true);
        }
        itemView.initialize(getItem(i), 0);
        return view2;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }

    public void setForceShowIcon(boolean z) {
        this.c = z;
    }

    @Override // android.widget.Adapter
    public MenuItemImpl getItem(int i) {
        ArrayList<MenuItemImpl> nonActionItems = this.d ? this.a.getNonActionItems() : this.a.getVisibleItems();
        int i2 = this.b;
        if (i2 >= 0 && i >= i2) {
            i++;
        }
        return nonActionItems.get(i);
    }
}
