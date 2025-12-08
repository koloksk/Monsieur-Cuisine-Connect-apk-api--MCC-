package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuPresenter;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import defpackage.i7;
import defpackage.k7;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class MenuPopupHelper {
    public final Context a;
    public final MenuBuilder b;
    public final boolean c;
    public final int d;
    public final int e;
    public View f;
    public int g;
    public boolean h;
    public MenuPresenter.Callback i;
    public i7 j;
    public PopupWindow.OnDismissListener k;
    public final PopupWindow.OnDismissListener l;

    public class a implements PopupWindow.OnDismissListener {
        public a() {
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            MenuPopupHelper.this.onDismiss();
        }
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, R.attr.popupMenuStyle, 0);
    }

    public final void a(int i, int i2, boolean z, boolean z2) {
        i7 popup = getPopup();
        popup.b(z2);
        if (z) {
            if ((GravityCompat.getAbsoluteGravity(this.g, ViewCompat.getLayoutDirection(this.f)) & 7) == 5) {
                i += this.f.getWidth();
            }
            popup.b(i);
            popup.c(i2);
            int i3 = (int) ((this.a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            popup.a = new Rect(i - i3, i2 - i3, i + i3, i2 + i3);
        }
        popup.show();
    }

    public void dismiss() {
        if (isShowing()) {
            this.j.dismiss();
        }
    }

    public int getGravity() {
        return this.g;
    }

    public ListView getListView() {
        return getPopup().getListView();
    }

    @NonNull
    public i7 getPopup() {
        if (this.j == null) {
            Display defaultDisplay = ((WindowManager) this.a.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            i7 cascadingMenuPopup = Math.min(point.x, point.y) >= this.a.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width) ? new CascadingMenuPopup(this.a, this.f, this.d, this.e, this.c) : new k7(this.a, this.b, this.f, this.d, this.e, this.c);
            cascadingMenuPopup.a(this.b);
            cascadingMenuPopup.a(this.l);
            cascadingMenuPopup.a(this.f);
            cascadingMenuPopup.setCallback(this.i);
            cascadingMenuPopup.a(this.h);
            cascadingMenuPopup.a(this.g);
            this.j = cascadingMenuPopup;
        }
        return this.j;
    }

    public boolean isShowing() {
        i7 i7Var = this.j;
        return i7Var != null && i7Var.isShowing();
    }

    public void onDismiss() {
        this.j = null;
        PopupWindow.OnDismissListener onDismissListener = this.k;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public void setAnchorView(@NonNull View view2) {
        this.f = view2;
    }

    public void setForceShowIcon(boolean z) {
        this.h = z;
        i7 i7Var = this.j;
        if (i7Var != null) {
            i7Var.a(z);
        }
    }

    public void setGravity(int i) {
        this.g = i;
    }

    public void setOnDismissListener(@Nullable PopupWindow.OnDismissListener onDismissListener) {
        this.k = onDismissListener;
    }

    public void setPresenterCallback(@Nullable MenuPresenter.Callback callback) {
        this.i = callback;
        i7 i7Var = this.j;
        if (i7Var != null) {
            i7Var.setCallback(callback);
        }
    }

    public void show() {
        if (!tryShow()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public boolean tryShow() {
        if (isShowing()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        a(0, 0, false, false);
        return true;
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view2) {
        this(context, menuBuilder, view2, false, R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view2, boolean z, @AttrRes int i) {
        this(context, menuBuilder, view2, z, i, 0);
    }

    public void show(int i, int i2) {
        if (!tryShow(i, i2)) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view2, boolean z, @AttrRes int i, @StyleRes int i2) {
        this.g = GravityCompat.START;
        this.l = new a();
        this.a = context;
        this.b = menuBuilder;
        this.f = view2;
        this.c = z;
        this.d = i;
        this.e = i2;
    }

    public boolean tryShow(int i, int i2) {
        if (isShowing()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        a(i, i2, true, true);
        return true;
    }
}
