package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.appcompat.R;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.ShowableListMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;

/* loaded from: classes.dex */
public class PopupMenu {
    public final Context a;
    public final MenuBuilder b;
    public final View c;
    public final MenuPopupHelper d;
    public OnMenuItemClickListener e;
    public OnDismissListener f;
    public View.OnTouchListener g;

    public interface OnDismissListener {
        void onDismiss(PopupMenu popupMenu);
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public class a implements MenuBuilder.Callback {
        public a() {
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            OnMenuItemClickListener onMenuItemClickListener = PopupMenu.this.e;
            if (onMenuItemClickListener != null) {
                return onMenuItemClickListener.onMenuItemClick(menuItem);
            }
            return false;
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
        }
    }

    public class b implements PopupWindow.OnDismissListener {
        public b() {
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            PopupMenu popupMenu = PopupMenu.this;
            OnDismissListener onDismissListener = popupMenu.f;
            if (onDismissListener != null) {
                onDismissListener.onDismiss(popupMenu);
            }
        }
    }

    public class c extends ForwardingListener {
        public c(View view2) {
            super(view2);
        }

        @Override // android.support.v7.widget.ForwardingListener
        public ShowableListMenu getPopup() {
            return PopupMenu.this.d.getPopup();
        }

        @Override // android.support.v7.widget.ForwardingListener
        public boolean onForwardingStarted() {
            PopupMenu.this.show();
            return true;
        }

        @Override // android.support.v7.widget.ForwardingListener
        public boolean onForwardingStopped() {
            PopupMenu.this.dismiss();
            return true;
        }
    }

    public PopupMenu(@NonNull Context context, @NonNull View view2) {
        this(context, view2, 0);
    }

    public void dismiss() {
        this.d.dismiss();
    }

    @NonNull
    public View.OnTouchListener getDragToOpenListener() {
        if (this.g == null) {
            this.g = new c(this.c);
        }
        return this.g;
    }

    public int getGravity() {
        return this.d.getGravity();
    }

    @NonNull
    public Menu getMenu() {
        return this.b;
    }

    @NonNull
    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.a);
    }

    public void inflate(@MenuRes int i) {
        getMenuInflater().inflate(i, this.b);
    }

    public void setGravity(int i) {
        this.d.setGravity(i);
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        this.f = onDismissListener;
    }

    public void setOnMenuItemClickListener(@Nullable OnMenuItemClickListener onMenuItemClickListener) {
        this.e = onMenuItemClickListener;
    }

    public void show() {
        this.d.show();
    }

    public PopupMenu(@NonNull Context context, @NonNull View view2, int i) {
        this(context, view2, i, R.attr.popupMenuStyle, 0);
    }

    public PopupMenu(@NonNull Context context, @NonNull View view2, int i, @AttrRes int i2, @StyleRes int i3) {
        this.a = context;
        this.c = view2;
        MenuBuilder menuBuilder = new MenuBuilder(context);
        this.b = menuBuilder;
        menuBuilder.setCallback(new a());
        MenuPopupHelper menuPopupHelper = new MenuPopupHelper(context, this.b, view2, false, i2, i3);
        this.d = menuPopupHelper;
        menuPopupHelper.setGravity(i);
        this.d.setOnDismissListener(new b());
    }
}
