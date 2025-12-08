package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.MenuPopupWindow;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/* loaded from: classes.dex */
public final class k7 extends i7 implements PopupWindow.OnDismissListener, AdapterView.OnItemClickListener, MenuPresenter, View.OnKeyListener {
    public final Context b;
    public final MenuBuilder c;
    public final MenuAdapter d;
    public final boolean e;
    public final int f;
    public final int g;
    public final int h;
    public final MenuPopupWindow i;
    public PopupWindow.OnDismissListener l;
    public View m;
    public View n;
    public MenuPresenter.Callback o;
    public ViewTreeObserver p;
    public boolean q;
    public boolean r;
    public int s;
    public boolean u;
    public final ViewTreeObserver.OnGlobalLayoutListener j = new a();
    public final View.OnAttachStateChangeListener k = new b();
    public int t = 0;

    public class a implements ViewTreeObserver.OnGlobalLayoutListener {
        public a() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (!k7.this.isShowing() || k7.this.i.isModal()) {
                return;
            }
            View view2 = k7.this.n;
            if (view2 == null || !view2.isShown()) {
                k7.this.dismiss();
            } else {
                k7.this.i.show();
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
            ViewTreeObserver viewTreeObserver = k7.this.p;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    k7.this.p = view2.getViewTreeObserver();
                }
                k7 k7Var = k7.this;
                k7Var.p.removeGlobalOnLayoutListener(k7Var.j);
            }
            view2.removeOnAttachStateChangeListener(this);
        }
    }

    public k7(Context context, MenuBuilder menuBuilder, View view2, int i, int i2, boolean z) {
        this.b = context;
        this.c = menuBuilder;
        this.e = z;
        this.d = new MenuAdapter(menuBuilder, LayoutInflater.from(context), this.e);
        this.g = i;
        this.h = i2;
        Resources resources = context.getResources();
        this.f = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.m = view2;
        this.i = new MenuPopupWindow(this.b, null, this.g, this.h);
        menuBuilder.addMenuPresenter(this, context);
    }

    @Override // defpackage.i7
    public void a(MenuBuilder menuBuilder) {
    }

    @Override // defpackage.i7
    public void a(boolean z) {
        this.d.setForceShowIcon(z);
    }

    @Override // defpackage.i7
    public void b(int i) {
        this.i.setHorizontalOffset(i);
    }

    @Override // defpackage.i7
    public void c(int i) {
        this.i.setVerticalOffset(i);
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public void dismiss() {
        if (isShowing()) {
            this.i.dismiss();
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public ListView getListView() {
        return this.i.getListView();
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public boolean isShowing() {
        return !this.q && this.i.isShowing();
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        if (menuBuilder != this.c) {
            return;
        }
        dismiss();
        MenuPresenter.Callback callback = this.o;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, z);
        }
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        this.q = true;
        this.c.close();
        ViewTreeObserver viewTreeObserver = this.p;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.p = this.n.getViewTreeObserver();
            }
            this.p.removeGlobalOnLayoutListener(this.j);
            this.p = null;
        }
        this.n.removeOnAttachStateChangeListener(this.k);
        PopupWindow.OnDismissListener onDismissListener = this.l;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
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
        if (subMenuBuilder.hasVisibleItems()) {
            MenuPopupHelper menuPopupHelper = new MenuPopupHelper(this.b, subMenuBuilder, this.n, this.e, this.g, this.h);
            menuPopupHelper.setPresenterCallback(this.o);
            menuPopupHelper.setForceShowIcon(i7.b(subMenuBuilder));
            menuPopupHelper.setGravity(this.t);
            menuPopupHelper.setOnDismissListener(this.l);
            this.l = null;
            this.c.close(false);
            if (menuPopupHelper.tryShow(this.i.getHorizontalOffset(), this.i.getVerticalOffset())) {
                MenuPresenter.Callback callback = this.o;
                if (callback == null) {
                    return true;
                }
                callback.onOpenSubMenu(subMenuBuilder);
                return true;
            }
        }
        return false;
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.o = callback;
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public void show() {
        View view2;
        boolean z = true;
        if (!isShowing()) {
            if (this.q || (view2 = this.m) == null) {
                z = false;
            } else {
                this.n = view2;
                this.i.setOnDismissListener(this);
                this.i.setOnItemClickListener(this);
                this.i.setModal(true);
                View view3 = this.n;
                boolean z2 = this.p == null;
                ViewTreeObserver viewTreeObserver = view3.getViewTreeObserver();
                this.p = viewTreeObserver;
                if (z2) {
                    viewTreeObserver.addOnGlobalLayoutListener(this.j);
                }
                view3.addOnAttachStateChangeListener(this.k);
                this.i.setAnchorView(view3);
                this.i.setDropDownGravity(this.t);
                if (!this.r) {
                    this.s = i7.a(this.d, null, this.b, this.f);
                    this.r = true;
                }
                this.i.setContentWidth(this.s);
                this.i.setInputMethodMode(2);
                this.i.setEpicenterBounds(this.a);
                this.i.show();
                ListView listView = this.i.getListView();
                listView.setOnKeyListener(this);
                if (this.u && this.c.getHeaderTitle() != null) {
                    FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.b).inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) listView, false);
                    TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
                    if (textView != null) {
                        textView.setText(this.c.getHeaderTitle());
                    }
                    frameLayout.setEnabled(false);
                    listView.addHeaderView(frameLayout, null, false);
                }
                this.i.setAdapter(this.d);
                this.i.show();
            }
        }
        if (!z) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        this.r = false;
        MenuAdapter menuAdapter = this.d;
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }

    @Override // defpackage.i7
    public void a(int i) {
        this.t = i;
    }

    @Override // defpackage.i7
    public void b(boolean z) {
        this.u = z;
    }

    @Override // defpackage.i7
    public void a(View view2) {
        this.m = view2;
    }

    @Override // defpackage.i7
    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.l = onDismissListener;
    }
}
