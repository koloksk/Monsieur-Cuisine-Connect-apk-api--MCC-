package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class NavigationMenuPresenter implements MenuPresenter {
    public NavigationMenuView a;
    public LinearLayout b;
    public MenuPresenter.Callback c;
    public MenuBuilder d;
    public int e;
    public c f;
    public LayoutInflater g;
    public int h;
    public boolean i;
    public ColorStateList j;
    public ColorStateList k;
    public Drawable l;
    public int m;
    public int n;
    public final View.OnClickListener o = new a();

    public class a implements View.OnClickListener {
        public a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            NavigationMenuPresenter.this.setUpdateSuspended(true);
            MenuItemImpl itemData = ((NavigationMenuItemView) view2).getItemData();
            NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
            boolean zPerformItemAction = navigationMenuPresenter.d.performItemAction(itemData, navigationMenuPresenter, 0);
            if (itemData != null && itemData.isCheckable() && zPerformItemAction) {
                NavigationMenuPresenter.this.f.a(itemData);
            }
            NavigationMenuPresenter.this.setUpdateSuspended(false);
            NavigationMenuPresenter.this.updateMenuView(false);
        }
    }

    public static class b extends k {
        public b(View view2) {
            super(view2);
        }
    }

    public static class d implements e {
    }

    public interface e {
    }

    public static class f implements e {
        public final int a;
        public final int b;

        public f(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }

    public static class g implements e {
        public final MenuItemImpl a;
        public boolean b;

        public g(MenuItemImpl menuItemImpl) {
            this.a = menuItemImpl;
        }
    }

    public static class h extends k {
        public h(LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
            super(layoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false));
            this.itemView.setOnClickListener(onClickListener);
        }
    }

    public static class i extends k {
        public i(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_separator, viewGroup, false));
        }
    }

    public static class j extends k {
        public j(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_subheader, viewGroup, false));
        }
    }

    public static abstract class k extends RecyclerView.ViewHolder {
        public k(View view2) {
            super(view2);
        }
    }

    public void addHeaderView(@NonNull View view2) {
        this.b.addView(view2);
        NavigationMenuView navigationMenuView = this.a;
        navigationMenuView.setPadding(0, 0, 0, navigationMenuView.getPaddingBottom());
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public void dispatchApplyWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
        if (this.m != systemWindowInsetTop) {
            this.m = systemWindowInsetTop;
            if (this.b.getChildCount() == 0) {
                NavigationMenuView navigationMenuView = this.a;
                navigationMenuView.setPadding(0, this.m, 0, navigationMenuView.getPaddingBottom());
            }
        }
        ViewCompat.dispatchApplyWindowInsets(this.b, windowInsetsCompat);
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    public int getHeaderCount() {
        return this.b.getChildCount();
    }

    public View getHeaderView(int i2) {
        return this.b.getChildAt(i2);
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public int getId() {
        return this.e;
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.l;
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.j;
    }

    @Nullable
    public ColorStateList getItemTintList() {
        return this.k;
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.a == null) {
            this.a = (NavigationMenuView) this.g.inflate(R.layout.design_navigation_menu, viewGroup, false);
            if (this.f == null) {
                this.f = new c();
            }
            this.b = (LinearLayout) this.g.inflate(R.layout.design_navigation_item_header, (ViewGroup) this.a, false);
            this.a.setAdapter(this.f);
        }
        return this.a;
    }

    public View inflateHeaderView(@LayoutRes int i2) {
        View viewInflate = this.g.inflate(i2, (ViewGroup) this.b, false);
        addHeaderView(viewInflate);
        return viewInflate;
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.g = LayoutInflater.from(context);
        this.d = menuBuilder;
        this.n = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuPresenter.Callback callback = this.c;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, z);
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItemImpl menuItemImpl;
        View actionView;
        ParcelableSparseArray parcelableSparseArray;
        MenuItemImpl menuItemImpl2;
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(ListMenuPresenter.VIEWS_TAG);
            if (sparseParcelableArray != null) {
                this.a.restoreHierarchyState(sparseParcelableArray);
            }
            Bundle bundle2 = bundle.getBundle("android:menu:adapter");
            if (bundle2 != null) {
                c cVar = this.f;
                if (cVar == null) {
                    throw null;
                }
                int i2 = bundle2.getInt("android:menu:checked", 0);
                if (i2 != 0) {
                    cVar.e = true;
                    int size = cVar.c.size();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size) {
                            break;
                        }
                        e eVar = cVar.c.get(i3);
                        if ((eVar instanceof g) && (menuItemImpl2 = ((g) eVar).a) != null && menuItemImpl2.getItemId() == i2) {
                            cVar.a(menuItemImpl2);
                            break;
                        }
                        i3++;
                    }
                    cVar.e = false;
                    cVar.a();
                }
                SparseArray sparseParcelableArray2 = bundle2.getSparseParcelableArray("android:menu:action_views");
                if (sparseParcelableArray2 != null) {
                    int size2 = cVar.c.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        e eVar2 = cVar.c.get(i4);
                        if ((eVar2 instanceof g) && (menuItemImpl = ((g) eVar2).a) != null && (actionView = menuItemImpl.getActionView()) != null && (parcelableSparseArray = (ParcelableSparseArray) sparseParcelableArray2.get(menuItemImpl.getItemId())) != null) {
                            actionView.restoreHierarchyState(parcelableSparseArray);
                        }
                    }
                }
            }
            SparseArray sparseParcelableArray3 = bundle.getSparseParcelableArray("android:menu:header");
            if (sparseParcelableArray3 != null) {
                this.b.restoreHierarchyState(sparseParcelableArray3);
            }
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        if (this.a != null) {
            SparseArray<Parcelable> sparseArray = new SparseArray<>();
            this.a.saveHierarchyState(sparseArray);
            bundle.putSparseParcelableArray(ListMenuPresenter.VIEWS_TAG, sparseArray);
        }
        c cVar = this.f;
        if (cVar != null) {
            if (cVar == null) {
                throw null;
            }
            Bundle bundle2 = new Bundle();
            MenuItemImpl menuItemImpl = cVar.d;
            if (menuItemImpl != null) {
                bundle2.putInt("android:menu:checked", menuItemImpl.getItemId());
            }
            SparseArray<? extends Parcelable> sparseArray2 = new SparseArray<>();
            int size = cVar.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                e eVar = cVar.c.get(i2);
                if (eVar instanceof g) {
                    MenuItemImpl menuItemImpl2 = ((g) eVar).a;
                    View actionView = menuItemImpl2 != null ? menuItemImpl2.getActionView() : null;
                    if (actionView != null) {
                        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                        actionView.saveHierarchyState(parcelableSparseArray);
                        sparseArray2.put(menuItemImpl2.getItemId(), parcelableSparseArray);
                    }
                }
            }
            bundle2.putSparseParcelableArray("android:menu:action_views", sparseArray2);
            bundle.putBundle("android:menu:adapter", bundle2);
        }
        if (this.b != null) {
            SparseArray<? extends Parcelable> sparseArray3 = new SparseArray<>();
            this.b.saveHierarchyState(sparseArray3);
            bundle.putSparseParcelableArray("android:menu:header", sparseArray3);
        }
        return bundle;
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    public void removeHeaderView(@NonNull View view2) {
        this.b.removeView(view2);
        if (this.b.getChildCount() == 0) {
            NavigationMenuView navigationMenuView = this.a;
            navigationMenuView.setPadding(0, this.m, 0, navigationMenuView.getPaddingBottom());
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.c = callback;
    }

    public void setCheckedItem(MenuItemImpl menuItemImpl) {
        this.f.a(menuItemImpl);
    }

    public void setId(int i2) {
        this.e = i2;
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        this.l = drawable;
        updateMenuView(false);
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.k = colorStateList;
        updateMenuView(false);
    }

    public void setItemTextAppearance(@StyleRes int i2) {
        this.h = i2;
        this.i = true;
        updateMenuView(false);
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.j = colorStateList;
        updateMenuView(false);
    }

    public void setUpdateSuspended(boolean z) {
        c cVar = this.f;
        if (cVar != null) {
            cVar.e = z;
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        c cVar = this.f;
        if (cVar != null) {
            cVar.a();
            cVar.notifyDataSetChanged();
        }
    }

    public class c extends RecyclerView.Adapter<k> {
        public final ArrayList<e> c = new ArrayList<>();
        public MenuItemImpl d;
        public boolean e;

        public c() {
            a();
        }

        public final void a() {
            if (this.e) {
                return;
            }
            this.e = true;
            this.c.clear();
            this.c.add(new d());
            int i = -1;
            int size = NavigationMenuPresenter.this.d.getVisibleItems().size();
            boolean z = false;
            int i2 = 0;
            boolean z2 = false;
            int size2 = 0;
            while (i2 < size) {
                MenuItemImpl menuItemImpl = NavigationMenuPresenter.this.d.getVisibleItems().get(i2);
                if (menuItemImpl.isChecked()) {
                    a(menuItemImpl);
                }
                if (menuItemImpl.isCheckable()) {
                    menuItemImpl.setExclusiveCheckable(z);
                }
                if (menuItemImpl.hasSubMenu()) {
                    SubMenu subMenu = menuItemImpl.getSubMenu();
                    if (subMenu.hasVisibleItems()) {
                        if (i2 != 0) {
                            this.c.add(new f(NavigationMenuPresenter.this.n, z ? 1 : 0));
                        }
                        this.c.add(new g(menuItemImpl));
                        int size3 = subMenu.size();
                        int i3 = z ? 1 : 0;
                        int i4 = i3;
                        while (i3 < size3) {
                            MenuItemImpl menuItemImpl2 = (MenuItemImpl) subMenu.getItem(i3);
                            if (menuItemImpl2.isVisible()) {
                                if (i4 == 0 && menuItemImpl2.getIcon() != null) {
                                    i4 = 1;
                                }
                                if (menuItemImpl2.isCheckable()) {
                                    menuItemImpl2.setExclusiveCheckable(z);
                                }
                                if (menuItemImpl.isChecked()) {
                                    a(menuItemImpl);
                                }
                                this.c.add(new g(menuItemImpl2));
                            }
                            i3++;
                            z = false;
                        }
                        if (i4 != 0) {
                            int size4 = this.c.size();
                            for (int size5 = this.c.size(); size5 < size4; size5++) {
                                ((g) this.c.get(size5)).b = true;
                            }
                        }
                    }
                } else {
                    int groupId = menuItemImpl.getGroupId();
                    if (groupId != i) {
                        size2 = this.c.size();
                        z2 = menuItemImpl.getIcon() != null;
                        if (i2 != 0) {
                            size2++;
                            ArrayList<e> arrayList = this.c;
                            int i5 = NavigationMenuPresenter.this.n;
                            arrayList.add(new f(i5, i5));
                        }
                    } else if (!z2 && menuItemImpl.getIcon() != null) {
                        int size6 = this.c.size();
                        for (int i6 = size2; i6 < size6; i6++) {
                            ((g) this.c.get(i6)).b = true;
                        }
                        z2 = true;
                    }
                    g gVar = new g(menuItemImpl);
                    gVar.b = z2;
                    this.c.add(gVar);
                    i = groupId;
                }
                i2++;
                z = false;
            }
            this.e = z ? 1 : 0;
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.c.size();
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            e eVar = this.c.get(i);
            if (eVar instanceof f) {
                return 2;
            }
            if (eVar instanceof d) {
                return 3;
            }
            if (eVar instanceof g) {
                return ((g) eVar).a.hasSubMenu() ? 1 : 0;
            }
            throw new RuntimeException("Unknown item type.");
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) throws Resources.NotFoundException {
            k kVar = (k) viewHolder;
            int itemViewType = getItemViewType(i);
            if (itemViewType != 0) {
                if (itemViewType == 1) {
                    ((TextView) kVar.itemView).setText(((g) this.c.get(i)).a.getTitle());
                    return;
                } else {
                    if (itemViewType != 2) {
                        return;
                    }
                    f fVar = (f) this.c.get(i);
                    kVar.itemView.setPadding(0, fVar.a, 0, fVar.b);
                    return;
                }
            }
            NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) kVar.itemView;
            navigationMenuItemView.setIconTintList(NavigationMenuPresenter.this.k);
            NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
            if (navigationMenuPresenter.i) {
                navigationMenuItemView.setTextAppearance(navigationMenuPresenter.h);
            }
            ColorStateList colorStateList = NavigationMenuPresenter.this.j;
            if (colorStateList != null) {
                navigationMenuItemView.setTextColor(colorStateList);
            }
            Drawable drawable = NavigationMenuPresenter.this.l;
            ViewCompat.setBackground(navigationMenuItemView, drawable != null ? drawable.getConstantState().newDrawable() : null);
            g gVar = (g) this.c.get(i);
            navigationMenuItemView.setNeedsEmptyIcon(gVar.b);
            navigationMenuItemView.initialize(gVar.a, 0);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder hVar;
            if (i == 0) {
                NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
                hVar = new h(navigationMenuPresenter.g, viewGroup, navigationMenuPresenter.o);
            } else if (i == 1) {
                hVar = new j(NavigationMenuPresenter.this.g, viewGroup);
            } else {
                if (i != 2) {
                    if (i != 3) {
                        return null;
                    }
                    return new b(NavigationMenuPresenter.this.b);
                }
                hVar = new i(NavigationMenuPresenter.this.g, viewGroup);
            }
            return hVar;
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
            k kVar = (k) viewHolder;
            if (kVar instanceof h) {
                ((NavigationMenuItemView) kVar.itemView).recycle();
            }
        }

        public void a(MenuItemImpl menuItemImpl) {
            if (this.d == menuItemImpl || !menuItemImpl.isCheckable()) {
                return;
            }
            MenuItemImpl menuItemImpl2 = this.d;
            if (menuItemImpl2 != null) {
                menuItemImpl2.setChecked(false);
            }
            this.d = menuItemImpl;
            menuItemImpl.setChecked(true);
        }
    }
}
