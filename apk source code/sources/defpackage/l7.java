package defpackage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.internal.view.SupportSubMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

@RequiresApi(14)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class l7 extends j7 implements SubMenu {
    public l7(Context context, SupportSubMenu supportSubMenu) {
        super(context, supportSubMenu);
    }

    @Override // android.view.SubMenu
    public void clearHeader() {
        ((SupportSubMenu) this.a).clearHeader();
    }

    @Override // android.view.SubMenu
    public MenuItem getItem() {
        return a(((SupportSubMenu) this.a).getItem());
    }

    @Override // defpackage.f7
    public Object getWrappedObject() {
        return (SupportSubMenu) this.a;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(int i) {
        ((SupportSubMenu) this.a).setHeaderIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(int i) {
        ((SupportSubMenu) this.a).setHeaderTitle(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderView(View view2) {
        ((SupportSubMenu) this.a).setHeaderView(view2);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(int i) {
        ((SupportSubMenu) this.a).setIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(Drawable drawable) {
        ((SupportSubMenu) this.a).setHeaderIcon(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(CharSequence charSequence) {
        ((SupportSubMenu) this.a).setHeaderTitle(charSequence);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(Drawable drawable) {
        ((SupportSubMenu) this.a).setIcon(drawable);
        return this;
    }
}
