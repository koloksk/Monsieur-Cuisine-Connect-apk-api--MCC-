package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.MenuPopupWindow;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import defpackage.i7;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class CascadingMenuPopup extends i7 implements MenuPresenter, View.OnKeyListener, PopupWindow.OnDismissListener {
    public boolean A;
    public final Context b;
    public final int c;
    public final int d;
    public final int e;
    public final boolean f;
    public final Handler g;
    public View o;
    public View p;
    public int q;
    public boolean r;
    public boolean s;
    public int t;
    public int u;
    public boolean w;
    public MenuPresenter.Callback x;
    public ViewTreeObserver y;
    public PopupWindow.OnDismissListener z;
    public final List<MenuBuilder> h = new ArrayList();
    public final List<d> i = new ArrayList();
    public final ViewTreeObserver.OnGlobalLayoutListener j = new a();
    public final View.OnAttachStateChangeListener k = new b();
    public final MenuItemHoverListener l = new c();
    public int m = 0;
    public int n = 0;
    public boolean v = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HorizPosition {
    }

    public class a implements ViewTreeObserver.OnGlobalLayoutListener {
        public a() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (!CascadingMenuPopup.this.isShowing() || CascadingMenuPopup.this.i.size() <= 0 || CascadingMenuPopup.this.i.get(0).a.isModal()) {
                return;
            }
            View view2 = CascadingMenuPopup.this.p;
            if (view2 == null || !view2.isShown()) {
                CascadingMenuPopup.this.dismiss();
                return;
            }
            Iterator<d> it = CascadingMenuPopup.this.i.iterator();
            while (it.hasNext()) {
                it.next().a.show();
            }
        }
    }

    public class b implements View.OnAttachStateChangeListener {
        public b() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view2) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view2) {
            ViewTreeObserver viewTreeObserver = CascadingMenuPopup.this.y;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    CascadingMenuPopup.this.y = view2.getViewTreeObserver();
                }
                CascadingMenuPopup cascadingMenuPopup = CascadingMenuPopup.this;
                cascadingMenuPopup.y.removeGlobalOnLayoutListener(cascadingMenuPopup.j);
            }
            view2.removeOnAttachStateChangeListener(this);
        }
    }

    public class c implements MenuItemHoverListener {

        public class a implements Runnable {
            public final /* synthetic */ d a;
            public final /* synthetic */ MenuItem b;
            public final /* synthetic */ MenuBuilder c;

            public a(d dVar, MenuItem menuItem, MenuBuilder menuBuilder) {
                this.a = dVar;
                this.b = menuItem;
                this.c = menuBuilder;
            }

            @Override // java.lang.Runnable
            public void run() {
                d dVar = this.a;
                if (dVar != null) {
                    CascadingMenuPopup.this.A = true;
                    dVar.b.close(false);
                    CascadingMenuPopup.this.A = false;
                }
                if (this.b.isEnabled() && this.b.hasSubMenu()) {
                    this.c.performItemAction(this.b, 4);
                }
            }
        }

        public c() {
        }

        @Override // android.support.v7.widget.MenuItemHoverListener
        public void onItemHoverEnter(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            CascadingMenuPopup.this.g.removeCallbacksAndMessages(null);
            int size = CascadingMenuPopup.this.i.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    i = -1;
                    break;
                } else if (menuBuilder == CascadingMenuPopup.this.i.get(i).b) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == -1) {
                return;
            }
            int i2 = i + 1;
            CascadingMenuPopup.this.g.postAtTime(new a(i2 < CascadingMenuPopup.this.i.size() ? CascadingMenuPopup.this.i.get(i2) : null, menuItem, menuBuilder), menuBuilder, SystemClock.uptimeMillis() + 200);
        }

        @Override // android.support.v7.widget.MenuItemHoverListener
        public void onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            CascadingMenuPopup.this.g.removeCallbacksAndMessages(menuBuilder);
        }
    }

    public static class d {
        public final MenuPopupWindow a;
        public final MenuBuilder b;
        public final int c;

        public d(@NonNull MenuPopupWindow menuPopupWindow, @NonNull MenuBuilder menuBuilder, int i) {
            this.a = menuPopupWindow;
            this.b = menuBuilder;
            this.c = i;
        }

        public ListView a() {
            return this.a.getListView();
        }
    }

    public CascadingMenuPopup(@NonNull Context context, @NonNull View view2, @AttrRes int i, @StyleRes int i2, boolean z) {
        this.b = context;
        this.o = view2;
        this.d = i;
        this.e = i2;
        this.f = z;
        this.q = ViewCompat.getLayoutDirection(view2) != 1 ? 1 : 0;
        Resources resources = context.getResources();
        this.c = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.g = new Handler();
    }

    @Override // defpackage.i7
    public void a(boolean z) {
        this.v = z;
    }

    @Override // defpackage.i7
    public boolean a() {
        return false;
    }

    @Override // defpackage.i7
    public void b(int i) {
        this.r = true;
        this.t = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x012d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void c(@android.support.annotation.NonNull android.support.v7.view.menu.MenuBuilder r17) {
        /*
            Method dump skipped, instructions count: 497
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.view.menu.CascadingMenuPopup.c(android.support.v7.view.menu.MenuBuilder):void");
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public void dismiss() {
        int size = this.i.size();
        if (size > 0) {
            d[] dVarArr = (d[]) this.i.toArray(new d[size]);
            for (int i = size - 1; i >= 0; i--) {
                d dVar = dVarArr[i];
                if (dVar.a.isShowing()) {
                    dVar.a.dismiss();
                }
            }
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public ListView getListView() {
        if (this.i.isEmpty()) {
            return null;
        }
        return this.i.get(r0.size() - 1).a();
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.i.size() > 0 && this.i.get(0).a.isShowing();
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        int size = this.i.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (menuBuilder == this.i.get(i).b) {
                break;
            } else {
                i++;
            }
        }
        if (i < 0) {
            return;
        }
        int i2 = i + 1;
        if (i2 < this.i.size()) {
            this.i.get(i2).b.close(false);
        }
        d dVarRemove = this.i.remove(i);
        dVarRemove.b.removeMenuPresenter(this);
        if (this.A) {
            dVarRemove.a.setExitTransition(null);
            dVarRemove.a.setAnimationStyle(0);
        }
        dVarRemove.a.dismiss();
        int size2 = this.i.size();
        if (size2 > 0) {
            this.q = this.i.get(size2 - 1).c;
        } else {
            this.q = ViewCompat.getLayoutDirection(this.o) == 1 ? 0 : 1;
        }
        if (size2 != 0) {
            if (z) {
                this.i.get(0).b.close(false);
                return;
            }
            return;
        }
        dismiss();
        MenuPresenter.Callback callback = this.x;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, true);
        }
        ViewTreeObserver viewTreeObserver = this.y;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.y.removeGlobalOnLayoutListener(this.j);
            }
            this.y = null;
        }
        this.p.removeOnAttachStateChangeListener(this.k);
        this.z.onDismiss();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        d dVar;
        int size = this.i.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                dVar = null;
                break;
            }
            dVar = this.i.get(i);
            if (!dVar.a.isShowing()) {
                break;
            } else {
                i++;
            }
        }
        if (dVar != null) {
            dVar.b.close(false);
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view2, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        return null;
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        for (d dVar : this.i) {
            if (subMenuBuilder == dVar.b) {
                dVar.a().requestFocus();
                return true;
            }
        }
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        subMenuBuilder.addMenuPresenter(this, this.b);
        if (isShowing()) {
            c(subMenuBuilder);
        } else {
            this.h.add(subMenuBuilder);
        }
        MenuPresenter.Callback callback = this.x;
        if (callback != null) {
            callback.onOpenSubMenu(subMenuBuilder);
        }
        return true;
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.x = callback;
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public void show() {
        if (isShowing()) {
            return;
        }
        Iterator<MenuBuilder> it = this.h.iterator();
        while (it.hasNext()) {
            c(it.next());
        }
        this.h.clear();
        View view2 = this.o;
        this.p = view2;
        if (view2 != null) {
            boolean z = this.y == null;
            ViewTreeObserver viewTreeObserver = this.p.getViewTreeObserver();
            this.y = viewTreeObserver;
            if (z) {
                viewTreeObserver.addOnGlobalLayoutListener(this.j);
            }
            this.p.addOnAttachStateChangeListener(this.k);
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        Iterator<d> it = this.i.iterator();
        while (it.hasNext()) {
            ListAdapter adapter2 = it.next().a().getAdapter();
            if (adapter2 instanceof HeaderViewListAdapter) {
                adapter2 = ((HeaderViewListAdapter) adapter2).getWrappedAdapter();
            }
            ((MenuAdapter) adapter2).notifyDataSetChanged();
        }
    }

    @Override // defpackage.i7
    public void a(MenuBuilder menuBuilder) {
        menuBuilder.addMenuPresenter(this, this.b);
        if (isShowing()) {
            c(menuBuilder);
        } else {
            this.h.add(menuBuilder);
        }
    }

    @Override // defpackage.i7
    public void b(boolean z) {
        this.w = z;
    }

    @Override // defpackage.i7
    public void a(int i) {
        if (this.m != i) {
            this.m = i;
            this.n = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this.o));
        }
    }

    @Override // defpackage.i7
    public void a(@NonNull View view2) {
        if (this.o != view2) {
            this.o = view2;
            this.n = GravityCompat.getAbsoluteGravity(this.m, ViewCompat.getLayoutDirection(view2));
        }
    }

    @Override // defpackage.i7
    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.z = onDismissListener;
    }

    @Override // defpackage.i7
    public void c(int i) {
        this.s = true;
        this.u = i;
    }
}
