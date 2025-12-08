package android.support.v7.app;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.app.ActionBar;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import defpackage.x6;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WindowDecorActionBar extends ActionBar implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    public static final Interpolator F = new AccelerateInterpolator();
    public static final Interpolator G = new DecelerateInterpolator();
    public boolean A;
    public boolean B;
    public Context a;
    public Context b;
    public Activity c;
    public ActionBarOverlayLayout d;
    public ActionBarContainer e;
    public DecorToolbar f;
    public ActionBarContextView g;
    public View h;
    public ScrollingTabContainerView i;
    public TabImpl k;
    public boolean m;
    public ActionModeImpl n;
    public ActionMode o;
    public ActionMode.Callback p;
    public boolean q;
    public boolean s;
    public boolean v;
    public boolean w;
    public boolean x;
    public ViewPropertyAnimatorCompatSet z;
    public ArrayList<TabImpl> j = new ArrayList<>();
    public int l = -1;
    public ArrayList<ActionBar.OnMenuVisibilityListener> r = new ArrayList<>();
    public int t = 0;
    public boolean u = true;
    public boolean y = true;
    public final ViewPropertyAnimatorListener C = new a();
    public final ViewPropertyAnimatorListener D = new b();
    public final ViewPropertyAnimatorUpdateListener E = new c();

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        public final Context c;
        public final MenuBuilder d;
        public ActionMode.Callback e;
        public WeakReference<View> f;

        public ActionModeImpl(Context context, ActionMode.Callback callback) {
            this.c = context;
            this.e = callback;
            MenuBuilder defaultShowAsAction = new MenuBuilder(context).setDefaultShowAsAction(1);
            this.d = defaultShowAsAction;
            defaultShowAsAction.setCallback(this);
        }

        public boolean dispatchOnCreate() {
            this.d.stopDispatchingItemsChanged();
            try {
                return this.e.onCreateActionMode(this, this.d);
            } finally {
                this.d.startDispatchingItemsChanged();
            }
        }

        @Override // android.support.v7.view.ActionMode
        public void finish() {
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            if (windowDecorActionBar.n != this) {
                return;
            }
            if ((windowDecorActionBar.v || windowDecorActionBar.w) ? false : true) {
                this.e.onDestroyActionMode(this);
            } else {
                WindowDecorActionBar windowDecorActionBar2 = WindowDecorActionBar.this;
                windowDecorActionBar2.o = this;
                windowDecorActionBar2.p = this.e;
            }
            this.e = null;
            WindowDecorActionBar.this.animateToMode(false);
            WindowDecorActionBar.this.g.closeMode();
            WindowDecorActionBar.this.f.getViewGroup().sendAccessibilityEvent(32);
            WindowDecorActionBar windowDecorActionBar3 = WindowDecorActionBar.this;
            windowDecorActionBar3.d.setHideOnContentScrollEnabled(windowDecorActionBar3.B);
            WindowDecorActionBar.this.n = null;
        }

        @Override // android.support.v7.view.ActionMode
        public View getCustomView() {
            WeakReference<View> weakReference = this.f;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }

        @Override // android.support.v7.view.ActionMode
        public Menu getMenu() {
            return this.d;
        }

        @Override // android.support.v7.view.ActionMode
        public MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.c);
        }

        @Override // android.support.v7.view.ActionMode
        public CharSequence getSubtitle() {
            return WindowDecorActionBar.this.g.getSubtitle();
        }

        @Override // android.support.v7.view.ActionMode
        public CharSequence getTitle() {
            return WindowDecorActionBar.this.g.getTitle();
        }

        @Override // android.support.v7.view.ActionMode
        public void invalidate() {
            if (WindowDecorActionBar.this.n != this) {
                return;
            }
            this.d.stopDispatchingItemsChanged();
            try {
                this.e.onPrepareActionMode(this, this.d);
            } finally {
                this.d.startDispatchingItemsChanged();
            }
        }

        @Override // android.support.v7.view.ActionMode
        public boolean isTitleOptional() {
            return WindowDecorActionBar.this.g.isTitleOptional();
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            ActionMode.Callback callback = this.e;
            if (callback != null) {
                return callback.onActionItemClicked(this, menuItem);
            }
            return false;
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (this.e == null) {
                return;
            }
            invalidate();
            WindowDecorActionBar.this.g.showOverflowMenu();
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            if (this.e == null) {
                return false;
            }
            if (!subMenuBuilder.hasVisibleItems()) {
                return true;
            }
            new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), subMenuBuilder).show();
            return true;
        }

        @Override // android.support.v7.view.ActionMode
        public void setCustomView(View view2) {
            WindowDecorActionBar.this.g.setCustomView(view2);
            this.f = new WeakReference<>(view2);
        }

        @Override // android.support.v7.view.ActionMode
        public void setSubtitle(CharSequence charSequence) {
            WindowDecorActionBar.this.g.setSubtitle(charSequence);
        }

        @Override // android.support.v7.view.ActionMode
        public void setTitle(CharSequence charSequence) {
            WindowDecorActionBar.this.g.setTitle(charSequence);
        }

        @Override // android.support.v7.view.ActionMode
        public void setTitleOptionalHint(boolean z) {
            super.setTitleOptionalHint(z);
            WindowDecorActionBar.this.g.setTitleOptional(z);
        }

        @Override // android.support.v7.view.ActionMode
        public void setSubtitle(int i) {
            setSubtitle(WindowDecorActionBar.this.a.getResources().getString(i));
        }

        @Override // android.support.v7.view.ActionMode
        public void setTitle(int i) {
            setTitle(WindowDecorActionBar.this.a.getResources().getString(i));
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public class TabImpl extends ActionBar.Tab {
        public ActionBar.TabListener a;
        public Object b;
        public Drawable c;
        public CharSequence d;
        public CharSequence e;
        public int f = -1;
        public View g;

        public TabImpl() {
        }

        public ActionBar.TabListener getCallback() {
            return this.a;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public CharSequence getContentDescription() {
            return this.e;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public View getCustomView() {
            return this.g;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public Drawable getIcon() {
            return this.c;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public int getPosition() {
            return this.f;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public Object getTag() {
            return this.b;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public CharSequence getText() {
            return this.d;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public void select() {
            WindowDecorActionBar.this.selectTab(this);
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setContentDescription(int i) {
            return setContentDescription(WindowDecorActionBar.this.a.getResources().getText(i));
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setCustomView(View view2) {
            this.g = view2;
            int i = this.f;
            if (i >= 0) {
                WindowDecorActionBar.this.i.updateTab(i);
            }
            return this;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setIcon(Drawable drawable) {
            this.c = drawable;
            int i = this.f;
            if (i >= 0) {
                WindowDecorActionBar.this.i.updateTab(i);
            }
            return this;
        }

        public void setPosition(int i) {
            this.f = i;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setTabListener(ActionBar.TabListener tabListener) {
            this.a = tabListener;
            return this;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setTag(Object obj) {
            this.b = obj;
            return this;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setText(CharSequence charSequence) {
            this.d = charSequence;
            int i = this.f;
            if (i >= 0) {
                WindowDecorActionBar.this.i.updateTab(i);
            }
            return this;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setContentDescription(CharSequence charSequence) {
            this.e = charSequence;
            int i = this.f;
            if (i >= 0) {
                WindowDecorActionBar.this.i.updateTab(i);
            }
            return this;
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setCustomView(int i) {
            return setCustomView(LayoutInflater.from(WindowDecorActionBar.this.getThemedContext()).inflate(i, (ViewGroup) null));
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setIcon(int i) {
            return setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.a, i));
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setText(int i) {
            return setText(WindowDecorActionBar.this.a.getResources().getText(i));
        }
    }

    public class a extends ViewPropertyAnimatorListenerAdapter {
        public a() {
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view2) {
            View view3;
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            if (windowDecorActionBar.u && (view3 = windowDecorActionBar.h) != null) {
                view3.setTranslationY(0.0f);
                WindowDecorActionBar.this.e.setTranslationY(0.0f);
            }
            WindowDecorActionBar.this.e.setVisibility(8);
            WindowDecorActionBar.this.e.setTransitioning(false);
            WindowDecorActionBar windowDecorActionBar2 = WindowDecorActionBar.this;
            windowDecorActionBar2.z = null;
            ActionMode.Callback callback = windowDecorActionBar2.p;
            if (callback != null) {
                callback.onDestroyActionMode(windowDecorActionBar2.o);
                windowDecorActionBar2.o = null;
                windowDecorActionBar2.p = null;
            }
            ActionBarOverlayLayout actionBarOverlayLayout = WindowDecorActionBar.this.d;
            if (actionBarOverlayLayout != null) {
                ViewCompat.requestApplyInsets(actionBarOverlayLayout);
            }
        }
    }

    public class b extends ViewPropertyAnimatorListenerAdapter {
        public b() {
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view2) {
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            windowDecorActionBar.z = null;
            windowDecorActionBar.e.requestLayout();
        }
    }

    public class c implements ViewPropertyAnimatorUpdateListener {
        public c() {
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorUpdateListener
        public void onAnimationUpdate(View view2) {
            ((View) WindowDecorActionBar.this.e.getParent()).invalidate();
        }
    }

    public WindowDecorActionBar(Activity activity2, boolean z) {
        this.c = activity2;
        View decorView = activity2.getWindow().getDecorView();
        a(decorView);
        if (z) {
            return;
        }
        this.h = decorView.findViewById(R.id.content);
    }

    public final void a(View view2) {
        DecorToolbar wrapper;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) view2.findViewById(android.support.v7.appcompat.R.id.decor_content_parent);
        this.d = actionBarOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setActionBarVisibilityCallback(this);
        }
        Object objFindViewById = view2.findViewById(android.support.v7.appcompat.R.id.action_bar);
        if (objFindViewById instanceof DecorToolbar) {
            wrapper = (DecorToolbar) objFindViewById;
        } else {
            if (!(objFindViewById instanceof Toolbar)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Can't make a decor toolbar out of ");
                sb.append(objFindViewById);
                throw new IllegalStateException(sb.toString() != null ? objFindViewById.getClass().getSimpleName() : "null");
            }
            wrapper = ((Toolbar) objFindViewById).getWrapper();
        }
        this.f = wrapper;
        this.g = (ActionBarContextView) view2.findViewById(android.support.v7.appcompat.R.id.action_context_bar);
        ActionBarContainer actionBarContainer = (ActionBarContainer) view2.findViewById(android.support.v7.appcompat.R.id.action_bar_container);
        this.e = actionBarContainer;
        DecorToolbar decorToolbar = this.f;
        if (decorToolbar == null || this.g == null || actionBarContainer == null) {
            throw new IllegalStateException(WindowDecorActionBar.class.getSimpleName() + " can only be used with a compatible window decor layout");
        }
        this.a = decorToolbar.getContext();
        boolean z = (this.f.getDisplayOptions() & 4) != 0;
        if (z) {
            this.m = true;
        }
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.a);
        setHomeButtonEnabled(actionBarPolicy.enableHomeButtonByDefault() || z);
        a(actionBarPolicy.hasEmbeddedTabs());
        TypedArray typedArrayObtainStyledAttributes = this.a.obtainStyledAttributes(null, android.support.v7.appcompat.R.styleable.ActionBar, android.support.v7.appcompat.R.attr.actionBarStyle, 0);
        if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.ActionBar_hideOnContentScroll, false)) {
            setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            setElevation(dimensionPixelSize);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.support.v7.app.ActionBar
    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.r.add(onMenuVisibilityListener);
    }

    @Override // android.support.v7.app.ActionBar
    public void addTab(ActionBar.Tab tab) {
        addTab(tab, this.j.isEmpty());
    }

    public void animateToMode(boolean z) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
        if (z) {
            if (!this.x) {
                this.x = true;
                ActionBarOverlayLayout actionBarOverlayLayout = this.d;
                if (actionBarOverlayLayout != null) {
                    actionBarOverlayLayout.setShowingForActionMode(true);
                }
                b(false);
            }
        } else if (this.x) {
            this.x = false;
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.d;
            if (actionBarOverlayLayout2 != null) {
                actionBarOverlayLayout2.setShowingForActionMode(false);
            }
            b(false);
        }
        if (!ViewCompat.isLaidOut(this.e)) {
            if (z) {
                this.f.setVisibility(4);
                this.g.setVisibility(0);
                return;
            } else {
                this.f.setVisibility(0);
                this.g.setVisibility(8);
                return;
            }
        }
        if (z) {
            viewPropertyAnimatorCompat2 = this.f.setupAnimatorToVisibility(4, 100L);
            viewPropertyAnimatorCompat = this.g.setupAnimatorToVisibility(0, 200L);
        } else {
            viewPropertyAnimatorCompat = this.f.setupAnimatorToVisibility(0, 200L);
            viewPropertyAnimatorCompat2 = this.g.setupAnimatorToVisibility(8, 100L);
        }
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
        viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat2, viewPropertyAnimatorCompat);
        viewPropertyAnimatorCompatSet.start();
    }

    public final void b() {
        if (this.i != null) {
            return;
        }
        ScrollingTabContainerView scrollingTabContainerView = new ScrollingTabContainerView(this.a);
        if (this.s) {
            scrollingTabContainerView.setVisibility(0);
            this.f.setEmbeddedTabView(scrollingTabContainerView);
        } else {
            if (getNavigationMode() == 2) {
                scrollingTabContainerView.setVisibility(0);
                ActionBarOverlayLayout actionBarOverlayLayout = this.d;
                if (actionBarOverlayLayout != null) {
                    ViewCompat.requestApplyInsets(actionBarOverlayLayout);
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
            this.e.setTabContainer(scrollingTabContainerView);
        }
        this.i = scrollingTabContainerView;
    }

    @Override // android.support.v7.app.ActionBar
    public boolean collapseActionView() {
        DecorToolbar decorToolbar = this.f;
        if (decorToolbar == null || !decorToolbar.hasExpandedActionView()) {
            return false;
        }
        this.f.collapseActionView();
        return true;
    }

    @Override // android.support.v7.app.ActionBar
    public void dispatchMenuVisibilityChanged(boolean z) {
        if (z == this.q) {
            return;
        }
        this.q = z;
        int size = this.r.size();
        for (int i = 0; i < size; i++) {
            this.r.get(i).onMenuVisibilityChanged(z);
        }
    }

    public void doHide(boolean z) {
        View view2;
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.z;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
        }
        if (this.t != 0 || (!this.A && !z)) {
            this.C.onAnimationEnd(null);
            return;
        }
        this.e.setAlpha(1.0f);
        this.e.setTransitioning(true);
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet2 = new ViewPropertyAnimatorCompatSet();
        float f = -this.e.getHeight();
        if (z) {
            this.e.getLocationInWindow(new int[]{0, 0});
            f -= r5[1];
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompatTranslationY = ViewCompat.animate(this.e).translationY(f);
        viewPropertyAnimatorCompatTranslationY.setUpdateListener(this.E);
        viewPropertyAnimatorCompatSet2.play(viewPropertyAnimatorCompatTranslationY);
        if (this.u && (view2 = this.h) != null) {
            viewPropertyAnimatorCompatSet2.play(ViewCompat.animate(view2).translationY(f));
        }
        viewPropertyAnimatorCompatSet2.setInterpolator(F);
        viewPropertyAnimatorCompatSet2.setDuration(250L);
        viewPropertyAnimatorCompatSet2.setListener(this.C);
        this.z = viewPropertyAnimatorCompatSet2;
        viewPropertyAnimatorCompatSet2.start();
    }

    public void doShow(boolean z) {
        View view2;
        View view3;
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.z;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
        }
        this.e.setVisibility(0);
        if (this.t == 0 && (this.A || z)) {
            this.e.setTranslationY(0.0f);
            float f = -this.e.getHeight();
            if (z) {
                this.e.getLocationInWindow(new int[]{0, 0});
                f -= r5[1];
            }
            this.e.setTranslationY(f);
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet2 = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompatTranslationY = ViewCompat.animate(this.e).translationY(0.0f);
            viewPropertyAnimatorCompatTranslationY.setUpdateListener(this.E);
            viewPropertyAnimatorCompatSet2.play(viewPropertyAnimatorCompatTranslationY);
            if (this.u && (view3 = this.h) != null) {
                view3.setTranslationY(f);
                viewPropertyAnimatorCompatSet2.play(ViewCompat.animate(this.h).translationY(0.0f));
            }
            viewPropertyAnimatorCompatSet2.setInterpolator(G);
            viewPropertyAnimatorCompatSet2.setDuration(250L);
            viewPropertyAnimatorCompatSet2.setListener(this.D);
            this.z = viewPropertyAnimatorCompatSet2;
            viewPropertyAnimatorCompatSet2.start();
        } else {
            this.e.setAlpha(1.0f);
            this.e.setTranslationY(0.0f);
            if (this.u && (view2 = this.h) != null) {
                view2.setTranslationY(0.0f);
            }
            this.D.onAnimationEnd(null);
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.d;
        if (actionBarOverlayLayout != null) {
            ViewCompat.requestApplyInsets(actionBarOverlayLayout);
        }
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void enableContentAnimations(boolean z) {
        this.u = z;
    }

    @Override // android.support.v7.app.ActionBar
    public View getCustomView() {
        return this.f.getCustomView();
    }

    @Override // android.support.v7.app.ActionBar
    public int getDisplayOptions() {
        return this.f.getDisplayOptions();
    }

    @Override // android.support.v7.app.ActionBar
    public float getElevation() {
        return ViewCompat.getElevation(this.e);
    }

    @Override // android.support.v7.app.ActionBar
    public int getHeight() {
        return this.e.getHeight();
    }

    @Override // android.support.v7.app.ActionBar
    public int getHideOffset() {
        return this.d.getActionBarHideOffset();
    }

    @Override // android.support.v7.app.ActionBar
    public int getNavigationItemCount() {
        int navigationMode = this.f.getNavigationMode();
        if (navigationMode == 1) {
            return this.f.getDropdownItemCount();
        }
        if (navigationMode != 2) {
            return 0;
        }
        return this.j.size();
    }

    @Override // android.support.v7.app.ActionBar
    public int getNavigationMode() {
        return this.f.getNavigationMode();
    }

    @Override // android.support.v7.app.ActionBar
    public int getSelectedNavigationIndex() {
        TabImpl tabImpl;
        int navigationMode = this.f.getNavigationMode();
        if (navigationMode == 1) {
            return this.f.getDropdownSelectedPosition();
        }
        if (navigationMode == 2 && (tabImpl = this.k) != null) {
            return tabImpl.getPosition();
        }
        return -1;
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab getSelectedTab() {
        return this.k;
    }

    @Override // android.support.v7.app.ActionBar
    public CharSequence getSubtitle() {
        return this.f.getSubtitle();
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab getTabAt(int i) {
        return this.j.get(i);
    }

    @Override // android.support.v7.app.ActionBar
    public int getTabCount() {
        return this.j.size();
    }

    @Override // android.support.v7.app.ActionBar
    public Context getThemedContext() {
        if (this.b == null) {
            TypedValue typedValue = new TypedValue();
            this.a.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarWidgetTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.b = new ContextThemeWrapper(this.a, i);
            } else {
                this.b = this.a;
            }
        }
        return this.b;
    }

    @Override // android.support.v7.app.ActionBar
    public CharSequence getTitle() {
        return this.f.getTitle();
    }

    public boolean hasIcon() {
        return this.f.hasIcon();
    }

    public boolean hasLogo() {
        return this.f.hasLogo();
    }

    @Override // android.support.v7.app.ActionBar
    public void hide() {
        if (this.v) {
            return;
        }
        this.v = true;
        b(false);
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void hideForSystem() {
        if (this.w) {
            return;
        }
        this.w = true;
        b(true);
    }

    @Override // android.support.v7.app.ActionBar
    public boolean isHideOnContentScrollEnabled() {
        return this.d.isHideOnContentScrollEnabled();
    }

    @Override // android.support.v7.app.ActionBar
    public boolean isShowing() {
        int height = getHeight();
        return this.y && (height == 0 || getHideOffset() < height);
    }

    @Override // android.support.v7.app.ActionBar
    public boolean isTitleTruncated() {
        DecorToolbar decorToolbar = this.f;
        return decorToolbar != null && decorToolbar.isTitleTruncated();
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab newTab() {
        return new TabImpl();
    }

    @Override // android.support.v7.app.ActionBar
    public void onConfigurationChanged(Configuration configuration) {
        a(ActionBarPolicy.get(this.a).hasEmbeddedTabs());
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onContentScrollStarted() {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.z;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
            this.z = null;
        }
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onContentScrollStopped() {
    }

    @Override // android.support.v7.app.ActionBar
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Menu menu;
        ActionModeImpl actionModeImpl = this.n;
        if (actionModeImpl == null || (menu = actionModeImpl.getMenu()) == null) {
            return false;
        }
        menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return menu.performShortcut(i, keyEvent, 0);
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onWindowVisibilityChanged(int i) {
        this.t = i;
    }

    @Override // android.support.v7.app.ActionBar
    public void removeAllTabs() {
        if (this.k != null) {
            selectTab(null);
        }
        this.j.clear();
        ScrollingTabContainerView scrollingTabContainerView = this.i;
        if (scrollingTabContainerView != null) {
            scrollingTabContainerView.removeAllTabs();
        }
        this.l = -1;
    }

    @Override // android.support.v7.app.ActionBar
    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.r.remove(onMenuVisibilityListener);
    }

    @Override // android.support.v7.app.ActionBar
    public void removeTab(ActionBar.Tab tab) {
        removeTabAt(tab.getPosition());
    }

    @Override // android.support.v7.app.ActionBar
    public void removeTabAt(int i) {
        if (this.i == null) {
            return;
        }
        TabImpl tabImpl = this.k;
        int position = tabImpl != null ? tabImpl.getPosition() : this.l;
        this.i.removeTabAt(i);
        TabImpl tabImplRemove = this.j.remove(i);
        if (tabImplRemove != null) {
            tabImplRemove.setPosition(-1);
        }
        int size = this.j.size();
        for (int i2 = i; i2 < size; i2++) {
            this.j.get(i2).setPosition(i2);
        }
        if (position == i) {
            selectTab(this.j.isEmpty() ? null : this.j.get(Math.max(0, i - 1)));
        }
    }

    public boolean requestFocus() {
        ViewGroup viewGroup = this.f.getViewGroup();
        if (viewGroup == null || viewGroup.hasFocus()) {
            return false;
        }
        viewGroup.requestFocus();
        return true;
    }

    @Override // android.support.v7.app.ActionBar
    public void selectTab(ActionBar.Tab tab) {
        if (getNavigationMode() != 2) {
            this.l = tab != null ? tab.getPosition() : -1;
            return;
        }
        FragmentTransaction fragmentTransactionDisallowAddToBackStack = (!(this.c instanceof FragmentActivity) || this.f.getViewGroup().isInEditMode()) ? null : ((FragmentActivity) this.c).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
        TabImpl tabImpl = this.k;
        if (tabImpl != tab) {
            this.i.setTabSelected(tab != null ? tab.getPosition() : -1);
            TabImpl tabImpl2 = this.k;
            if (tabImpl2 != null) {
                tabImpl2.getCallback().onTabUnselected(this.k, fragmentTransactionDisallowAddToBackStack);
            }
            TabImpl tabImpl3 = (TabImpl) tab;
            this.k = tabImpl3;
            if (tabImpl3 != null) {
                tabImpl3.getCallback().onTabSelected(this.k, fragmentTransactionDisallowAddToBackStack);
            }
        } else if (tabImpl != null) {
            tabImpl.getCallback().onTabReselected(this.k, fragmentTransactionDisallowAddToBackStack);
            this.i.animateToTab(tab.getPosition());
        }
        if (fragmentTransactionDisallowAddToBackStack == null || fragmentTransactionDisallowAddToBackStack.isEmpty()) {
            return;
        }
        fragmentTransactionDisallowAddToBackStack.commit();
    }

    @Override // android.support.v7.app.ActionBar
    public void setBackgroundDrawable(Drawable drawable) {
        this.e.setPrimaryBackground(drawable);
    }

    @Override // android.support.v7.app.ActionBar
    public void setCustomView(int i) {
        setCustomView(LayoutInflater.from(getThemedContext()).inflate(i, this.f.getViewGroup(), false));
    }

    @Override // android.support.v7.app.ActionBar
    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
        if (this.m) {
            return;
        }
        setDisplayHomeAsUpEnabled(z);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(z ? 4 : 0, 4);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayOptions(int i) {
        if ((i & 4) != 0) {
            this.m = true;
        }
        this.f.setDisplayOptions(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayShowCustomEnabled(boolean z) {
        setDisplayOptions(z ? 16 : 0, 16);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayShowHomeEnabled(boolean z) {
        setDisplayOptions(z ? 2 : 0, 2);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayShowTitleEnabled(boolean z) {
        setDisplayOptions(z ? 8 : 0, 8);
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayUseLogoEnabled(boolean z) {
        setDisplayOptions(z ? 1 : 0, 1);
    }

    @Override // android.support.v7.app.ActionBar
    public void setElevation(float f) {
        ViewCompat.setElevation(this.e, f);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHideOffset(int i) {
        if (i != 0 && !this.d.isInOverlayMode()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
        }
        this.d.setActionBarHideOffset(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHideOnContentScrollEnabled(boolean z) {
        if (z && !this.d.isInOverlayMode()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
        }
        this.B = z;
        this.d.setHideOnContentScrollEnabled(z);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.f.setNavigationContentDescription(charSequence);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeAsUpIndicator(Drawable drawable) {
        this.f.setNavigationIcon(drawable);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeButtonEnabled(boolean z) {
        this.f.setHomeButtonEnabled(z);
    }

    @Override // android.support.v7.app.ActionBar
    public void setIcon(int i) {
        this.f.setIcon(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.f.setDropdownParams(spinnerAdapter, new x6(onNavigationListener));
    }

    @Override // android.support.v7.app.ActionBar
    public void setLogo(int i) {
        this.f.setLogo(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setNavigationMode(int i) {
        ActionBarOverlayLayout actionBarOverlayLayout;
        int navigationMode = this.f.getNavigationMode();
        if (navigationMode == 2) {
            this.l = getSelectedNavigationIndex();
            selectTab(null);
            this.i.setVisibility(8);
        }
        if (navigationMode != i && !this.s && (actionBarOverlayLayout = this.d) != null) {
            ViewCompat.requestApplyInsets(actionBarOverlayLayout);
        }
        this.f.setNavigationMode(i);
        boolean z = false;
        if (i == 2) {
            b();
            this.i.setVisibility(0);
            int i2 = this.l;
            if (i2 != -1) {
                setSelectedNavigationItem(i2);
                this.l = -1;
            }
        }
        this.f.setCollapsible(i == 2 && !this.s);
        ActionBarOverlayLayout actionBarOverlayLayout2 = this.d;
        if (i == 2 && !this.s) {
            z = true;
        }
        actionBarOverlayLayout2.setHasNonEmbeddedTabs(z);
    }

    @Override // android.support.v7.app.ActionBar
    public void setSelectedNavigationItem(int i) {
        int navigationMode = this.f.getNavigationMode();
        if (navigationMode == 1) {
            this.f.setDropdownSelectedPosition(i);
        } else {
            if (navigationMode != 2) {
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
            }
            selectTab(this.j.get(i));
        }
    }

    @Override // android.support.v7.app.ActionBar
    public void setShowHideAnimationEnabled(boolean z) {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet;
        this.A = z;
        if (z || (viewPropertyAnimatorCompatSet = this.z) == null) {
            return;
        }
        viewPropertyAnimatorCompatSet.cancel();
    }

    @Override // android.support.v7.app.ActionBar
    public void setSplitBackgroundDrawable(Drawable drawable) {
    }

    @Override // android.support.v7.app.ActionBar
    public void setStackedBackgroundDrawable(Drawable drawable) {
        this.e.setStackedBackground(drawable);
    }

    @Override // android.support.v7.app.ActionBar
    public void setSubtitle(int i) {
        setSubtitle(this.a.getString(i));
    }

    @Override // android.support.v7.app.ActionBar
    public void setTitle(int i) {
        setTitle(this.a.getString(i));
    }

    @Override // android.support.v7.app.ActionBar
    public void setWindowTitle(CharSequence charSequence) {
        this.f.setWindowTitle(charSequence);
    }

    @Override // android.support.v7.app.ActionBar
    public void show() {
        if (this.v) {
            this.v = false;
            b(false);
        }
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void showForSystem() {
        if (this.w) {
            this.w = false;
            b(true);
        }
    }

    @Override // android.support.v7.app.ActionBar
    public ActionMode startActionMode(ActionMode.Callback callback) {
        ActionModeImpl actionModeImpl = this.n;
        if (actionModeImpl != null) {
            actionModeImpl.finish();
        }
        this.d.setHideOnContentScrollEnabled(false);
        this.g.killMode();
        ActionModeImpl actionModeImpl2 = new ActionModeImpl(this.g.getContext(), callback);
        if (!actionModeImpl2.dispatchOnCreate()) {
            return null;
        }
        this.n = actionModeImpl2;
        actionModeImpl2.invalidate();
        this.g.initForMode(actionModeImpl2);
        animateToMode(true);
        this.g.sendAccessibilityEvent(32);
        return actionModeImpl2;
    }

    @Override // android.support.v7.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i) {
        addTab(tab, i, this.j.isEmpty());
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeActionContentDescription(int i) {
        this.f.setNavigationContentDescription(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setHomeAsUpIndicator(int i) {
        this.f.setNavigationIcon(i);
    }

    @Override // android.support.v7.app.ActionBar
    public void setIcon(Drawable drawable) {
        this.f.setIcon(drawable);
    }

    @Override // android.support.v7.app.ActionBar
    public void setLogo(Drawable drawable) {
        this.f.setLogo(drawable);
    }

    @Override // android.support.v7.app.ActionBar
    public void setSubtitle(CharSequence charSequence) {
        this.f.setSubtitle(charSequence);
    }

    @Override // android.support.v7.app.ActionBar
    public void setTitle(CharSequence charSequence) {
        this.f.setTitle(charSequence);
    }

    @Override // android.support.v7.app.ActionBar
    public void addTab(ActionBar.Tab tab, boolean z) {
        b();
        this.i.addTab(tab, z);
        a(tab, this.j.size());
        if (z) {
            selectTab(tab);
        }
    }

    @Override // android.support.v7.app.ActionBar
    public void setDisplayOptions(int i, int i2) {
        int displayOptions = this.f.getDisplayOptions();
        if ((i2 & 4) != 0) {
            this.m = true;
        }
        this.f.setDisplayOptions((i & i2) | ((~i2) & displayOptions));
    }

    @Override // android.support.v7.app.ActionBar
    public void setCustomView(View view2) {
        this.f.setCustomView(view2);
    }

    @Override // android.support.v7.app.ActionBar
    public void setCustomView(View view2, ActionBar.LayoutParams layoutParams) {
        view2.setLayoutParams(layoutParams);
        this.f.setCustomView(view2);
    }

    @Override // android.support.v7.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i, boolean z) {
        b();
        this.i.addTab(tab, i, z);
        a(tab, i);
        if (z) {
            selectTab(tab);
        }
    }

    public final void b(boolean z) {
        if (this.x || !(this.v || this.w)) {
            if (this.y) {
                return;
            }
            this.y = true;
            doShow(z);
            return;
        }
        if (this.y) {
            this.y = false;
            doHide(z);
        }
    }

    public WindowDecorActionBar(Dialog dialog) {
        a(dialog.getWindow().getDecorView());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WindowDecorActionBar(View view2) {
        a(view2);
    }

    public final void a(boolean z) {
        this.s = z;
        if (!z) {
            this.f.setEmbeddedTabView(null);
            this.e.setTabContainer(this.i);
        } else {
            this.e.setTabContainer(null);
            this.f.setEmbeddedTabView(this.i);
        }
        boolean z2 = getNavigationMode() == 2;
        ScrollingTabContainerView scrollingTabContainerView = this.i;
        if (scrollingTabContainerView != null) {
            if (z2) {
                scrollingTabContainerView.setVisibility(0);
                ActionBarOverlayLayout actionBarOverlayLayout = this.d;
                if (actionBarOverlayLayout != null) {
                    ViewCompat.requestApplyInsets(actionBarOverlayLayout);
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
        }
        this.f.setCollapsible(!this.s && z2);
        this.d.setHasNonEmbeddedTabs(!this.s && z2);
    }

    public final void a(ActionBar.Tab tab, int i) {
        TabImpl tabImpl = (TabImpl) tab;
        if (tabImpl.getCallback() != null) {
            tabImpl.setPosition(i);
            this.j.add(i, tabImpl);
            int size = this.j.size();
            while (true) {
                i++;
                if (i >= size) {
                    return;
                } else {
                    this.j.get(i).setPosition(i);
                }
            }
        } else {
            throw new IllegalStateException("Action Bar Tab must have a Callback");
        }
    }
}
