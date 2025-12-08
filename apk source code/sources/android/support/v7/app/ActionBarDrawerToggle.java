package android.support.v7.app;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/* loaded from: classes.dex */
public class ActionBarDrawerToggle implements DrawerLayout.DrawerListener {
    public final Delegate a;
    public final DrawerLayout b;
    public DrawerArrowDrawable c;
    public boolean d;
    public Drawable e;
    public boolean f;
    public boolean g;
    public final int h;
    public final int i;
    public View.OnClickListener j;
    public boolean k;

    public interface Delegate {
        Context getActionBarThemedContext();

        Drawable getThemeUpIndicator();

        boolean isNavigationVisible();

        void setActionBarDescription(@StringRes int i);

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i);
    }

    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate();
    }

    public class a implements View.OnClickListener {
        public a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            ActionBarDrawerToggle actionBarDrawerToggle = ActionBarDrawerToggle.this;
            if (actionBarDrawerToggle.f) {
                actionBarDrawerToggle.b();
                return;
            }
            View.OnClickListener onClickListener = actionBarDrawerToggle.j;
            if (onClickListener != null) {
                onClickListener.onClick(view2);
            }
        }
    }

    @RequiresApi(18)
    public static class b implements Delegate {
        public final Activity a;

        public b(Activity activity2) {
            this.a = activity2;
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            android.app.ActionBar actionBar = this.a.getActionBar();
            return actionBar != null ? actionBar.getThemedContext() : this.a;
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            android.app.ActionBar actionBar = this.a.getActionBar();
            TypedArray typedArrayObtainStyledAttributes = (actionBar != null ? actionBar.getThemedContext() : this.a).obtainStyledAttributes(null, new int[]{R.attr.homeAsUpIndicator}, R.attr.actionBarStyle, 0);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
            typedArrayObtainStyledAttributes.recycle();
            return drawable;
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            android.app.ActionBar actionBar = this.a.getActionBar();
            return (actionBar == null || (actionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i) {
            android.app.ActionBar actionBar = this.a.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(i);
            }
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, int i) {
            android.app.ActionBar actionBar = this.a.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(drawable);
                actionBar.setHomeActionContentDescription(i);
            }
        }
    }

    public static class c implements Delegate {
        public final Toolbar a;
        public final Drawable b;
        public final CharSequence c;

        public c(Toolbar toolbar) {
            this.a = toolbar;
            this.b = toolbar.getNavigationIcon();
            this.c = toolbar.getNavigationContentDescription();
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            return this.a.getContext();
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            return this.b;
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            return true;
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(@StringRes int i) {
            if (i == 0) {
                this.a.setNavigationContentDescription(this.c);
            } else {
                this.a.setNavigationContentDescription(i);
            }
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, @StringRes int i) {
            this.a.setNavigationIcon(drawable);
            if (i == 0) {
                this.a.setNavigationContentDescription(this.c);
            } else {
                this.a.setNavigationContentDescription(i);
            }
        }
    }

    public ActionBarDrawerToggle(Activity activity2, DrawerLayout drawerLayout, @StringRes int i, @StringRes int i2) {
        this(activity2, null, drawerLayout, null, i, i2);
    }

    public void a(Drawable drawable, int i) {
        if (!this.k && !this.a.isNavigationVisible()) {
            Log.w(android.support.v4.app.ActionBarDrawerToggle.TAG, "DrawerToggle may not show up because NavigationIcon is not visible. You may need to call actionbar.setDisplayHomeAsUpEnabled(true);");
            this.k = true;
        }
        this.a.setActionBarUpIndicator(drawable, i);
    }

    public void b() {
        int drawerLockMode = this.b.getDrawerLockMode(GravityCompat.START);
        if (this.b.isDrawerVisible(GravityCompat.START) && drawerLockMode != 2) {
            this.b.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != 1) {
            this.b.openDrawer(GravityCompat.START);
        }
    }

    @NonNull
    public DrawerArrowDrawable getDrawerArrowDrawable() {
        return this.c;
    }

    public View.OnClickListener getToolbarNavigationClickListener() {
        return this.j;
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.f;
    }

    public boolean isDrawerSlideAnimationEnabled() {
        return this.d;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.g) {
            this.e = a();
        }
        syncState();
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    public void onDrawerClosed(View view2) {
        a(0.0f);
        if (this.f) {
            this.a.setActionBarDescription(this.h);
        }
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    public void onDrawerOpened(View view2) {
        a(1.0f);
        if (this.f) {
            this.a.setActionBarDescription(this.i);
        }
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    public void onDrawerSlide(View view2, float f) {
        if (this.d) {
            a(Math.min(1.0f, Math.max(0.0f, f)));
        } else {
            a(0.0f);
        }
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    public void onDrawerStateChanged(int i) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332 || !this.f) {
            return false;
        }
        b();
        return true;
    }

    public void setDrawerArrowDrawable(@NonNull DrawerArrowDrawable drawerArrowDrawable) {
        this.c = drawerArrowDrawable;
        syncState();
    }

    public void setDrawerIndicatorEnabled(boolean z) {
        if (z != this.f) {
            if (z) {
                a(this.c, this.b.isDrawerOpen(GravityCompat.START) ? this.i : this.h);
            } else {
                a(this.e, 0);
            }
            this.f = z;
        }
    }

    public void setDrawerSlideAnimationEnabled(boolean z) {
        this.d = z;
        if (z) {
            return;
        }
        a(0.0f);
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        if (drawable == null) {
            this.e = a();
            this.g = false;
        } else {
            this.e = drawable;
            this.g = true;
        }
        if (this.f) {
            return;
        }
        a(this.e, 0);
    }

    public void setToolbarNavigationClickListener(View.OnClickListener onClickListener) {
        this.j = onClickListener;
    }

    public void syncState() {
        if (this.b.isDrawerOpen(GravityCompat.START)) {
            a(1.0f);
        } else {
            a(0.0f);
        }
        if (this.f) {
            a(this.c, this.b.isDrawerOpen(GravityCompat.START) ? this.i : this.h);
        }
    }

    public ActionBarDrawerToggle(Activity activity2, DrawerLayout drawerLayout, Toolbar toolbar, @StringRes int i, @StringRes int i2) {
        this(activity2, toolbar, drawerLayout, null, i, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ActionBarDrawerToggle(Activity activity2, Toolbar toolbar, DrawerLayout drawerLayout, DrawerArrowDrawable drawerArrowDrawable, @StringRes int i, @StringRes int i2) {
        this.d = true;
        this.f = true;
        this.k = false;
        if (toolbar != null) {
            this.a = new c(toolbar);
            toolbar.setNavigationOnClickListener(new a());
        } else if (activity2 instanceof DelegateProvider) {
            this.a = ((DelegateProvider) activity2).getDrawerToggleDelegate();
        } else {
            this.a = new b(activity2);
        }
        this.b = drawerLayout;
        this.h = i;
        this.i = i2;
        if (drawerArrowDrawable == null) {
            this.c = new DrawerArrowDrawable(this.a.getActionBarThemedContext());
        } else {
            this.c = drawerArrowDrawable;
        }
        this.e = a();
    }

    public Drawable a() {
        return this.a.getThemeUpIndicator();
    }

    public final void a(float f) {
        if (f == 1.0f) {
            this.c.setVerticalMirror(true);
        } else if (f == 0.0f) {
            this.c.setVerticalMirror(false);
        }
        this.c.setProgress(f);
    }

    public void setHomeAsUpIndicator(int i) {
        setHomeAsUpIndicator(i != 0 ? this.b.getResources().getDrawable(i) : null);
    }
}
