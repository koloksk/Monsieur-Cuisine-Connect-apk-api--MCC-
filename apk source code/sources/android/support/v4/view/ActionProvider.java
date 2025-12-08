package android.support.v4.view;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import defpackage.g9;

/* loaded from: classes.dex */
public abstract class ActionProvider {
    public final Context a;
    public SubUiVisibilityListener b;
    public VisibilityListener c;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface SubUiVisibilityListener {
        void onSubUiVisibilityChanged(boolean z);
    }

    public interface VisibilityListener {
        void onActionProviderVisibilityChanged(boolean z);
    }

    public ActionProvider(Context context) {
        this.a = context;
    }

    public Context getContext() {
        return this.a;
    }

    public boolean hasSubMenu() {
        return false;
    }

    public boolean isVisible() {
        return true;
    }

    public abstract View onCreateActionView();

    public View onCreateActionView(MenuItem menuItem) {
        return onCreateActionView();
    }

    public boolean onPerformDefaultAction() {
        return false;
    }

    public void onPrepareSubMenu(SubMenu subMenu) {
    }

    public boolean overridesItemVisibility() {
        return false;
    }

    public void refreshVisibility() {
        if (this.c == null || !overridesItemVisibility()) {
            return;
        }
        this.c.onActionProviderVisibilityChanged(isVisible());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void reset() {
        this.c = null;
        this.b = null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setSubUiVisibilityListener(SubUiVisibilityListener subUiVisibilityListener) {
        this.b = subUiVisibilityListener;
    }

    public void setVisibilityListener(VisibilityListener visibilityListener) {
        if (this.c != null && visibilityListener != null) {
            StringBuilder sbA = g9.a("setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this ");
            sbA.append(getClass().getSimpleName());
            sbA.append(" instance while it is still in use somewhere else?");
            Log.w("ActionProvider(support)", sbA.toString());
        }
        this.c = visibilityListener;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void subUiVisibilityChanged(boolean z) {
        SubUiVisibilityListener subUiVisibilityListener = this.b;
        if (subUiVisibilityListener != null) {
            subUiVisibilityListener.onSubUiVisibilityChanged(z);
        }
    }
}
