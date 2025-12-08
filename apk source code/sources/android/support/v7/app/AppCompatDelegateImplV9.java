package android.support.v7.app;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import defpackage.g9;
import defpackage.o6;
import defpackage.t6;
import defpackage.u6;
import defpackage.y6;
import java.lang.reflect.InvocationTargetException;

@RequiresApi(14)
/* loaded from: classes.dex */
public class AppCompatDelegateImplV9 extends o6 implements MenuBuilder.Callback, LayoutInflater.Factory2 {
    public boolean A;
    public ViewGroup B;
    public TextView C;
    public View D;
    public boolean E;
    public boolean F;
    public boolean G;
    public PanelFeatureState[] H;
    public PanelFeatureState I;
    public boolean J;
    public boolean K;
    public int L;
    public final Runnable M;
    public boolean N;
    public Rect O;
    public Rect P;
    public AppCompatViewInflater Q;
    public DecorContentParent s;
    public b t;
    public e u;
    public ActionMode v;
    public ActionBarContextView w;
    public PopupWindow x;
    public Runnable y;
    public ViewPropertyAnimatorCompat z;

    public static final class PanelFeatureState {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public ViewGroup g;
        public View h;
        public View i;
        public MenuBuilder j;
        public ListMenuPresenter k;
        public Context l;
        public boolean m;
        public boolean n;
        public boolean o;
        public boolean p = false;
        public boolean q;
        public boolean qwertyMode;
        public Bundle r;

        public static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new a();
            public int a;
            public boolean b;
            public Bundle c;

            public static class a implements Parcelable.ClassLoaderCreator<SavedState> {
                @Override // android.os.Parcelable.ClassLoaderCreator
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.a(parcel, classLoader);
                }

                @Override // android.os.Parcelable.Creator
                public Object[] newArray(int i) {
                    return new SavedState[i];
                }

                @Override // android.os.Parcelable.Creator
                public Object createFromParcel(Parcel parcel) {
                    return SavedState.a(parcel, null);
                }
            }

            public static SavedState a(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.a = parcel.readInt();
                boolean z = parcel.readInt() == 1;
                savedState.b = z;
                if (z) {
                    savedState.c = parcel.readBundle(classLoader);
                }
                return savedState;
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.a);
                parcel.writeInt(this.b ? 1 : 0);
                if (this.b) {
                    parcel.writeBundle(this.c);
                }
            }
        }

        public PanelFeatureState(int i) {
            this.a = i;
        }

        public void a(MenuBuilder menuBuilder) {
            ListMenuPresenter listMenuPresenter;
            MenuBuilder menuBuilder2 = this.j;
            if (menuBuilder == menuBuilder2) {
                return;
            }
            if (menuBuilder2 != null) {
                menuBuilder2.removeMenuPresenter(this.k);
            }
            this.j = menuBuilder;
            if (menuBuilder == null || (listMenuPresenter = this.k) == null) {
                return;
            }
            menuBuilder.addMenuPresenter(listMenuPresenter);
        }

        public void clearMenuPresenters() {
            MenuBuilder menuBuilder = this.j;
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.k);
            }
            this.k = null;
        }

        public boolean hasPanelItems() {
            if (this.h == null) {
                return false;
            }
            return this.i != null || this.k.getAdapter().getCount() > 0;
        }
    }

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
            if ((appCompatDelegateImplV9.L & 1) != 0) {
                appCompatDelegateImplV9.a(0);
            }
            AppCompatDelegateImplV9 appCompatDelegateImplV92 = AppCompatDelegateImplV9.this;
            if ((appCompatDelegateImplV92.L & 4096) != 0) {
                appCompatDelegateImplV92.a(108);
            }
            AppCompatDelegateImplV9 appCompatDelegateImplV93 = AppCompatDelegateImplV9.this;
            appCompatDelegateImplV93.K = false;
            appCompatDelegateImplV93.L = 0;
        }
    }

    public final class b implements MenuPresenter.Callback {
        public b() {
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            AppCompatDelegateImplV9.this.a(menuBuilder);
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callbackB = AppCompatDelegateImplV9.this.b();
            if (callbackB == null) {
                return true;
            }
            callbackB.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    public class c implements ActionMode.Callback {
        public ActionMode.Callback a;

        public class a extends ViewPropertyAnimatorListenerAdapter {
            public a() {
            }

            @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
            public void onAnimationEnd(View view2) {
                AppCompatDelegateImplV9.this.w.setVisibility(8);
                AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
                PopupWindow popupWindow = appCompatDelegateImplV9.x;
                if (popupWindow != null) {
                    popupWindow.dismiss();
                } else if (appCompatDelegateImplV9.w.getParent() instanceof View) {
                    ViewCompat.requestApplyInsets((View) AppCompatDelegateImplV9.this.w.getParent());
                }
                AppCompatDelegateImplV9.this.w.removeAllViews();
                AppCompatDelegateImplV9.this.z.setListener(null);
                AppCompatDelegateImplV9.this.z = null;
            }
        }

        public c(ActionMode.Callback callback) {
            this.a = callback;
        }

        @Override // android.support.v7.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.a.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.support.v7.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.a.onCreateActionMode(actionMode, menu);
        }

        @Override // android.support.v7.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.a.onDestroyActionMode(actionMode);
            AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
            if (appCompatDelegateImplV9.x != null) {
                appCompatDelegateImplV9.d.getDecorView().removeCallbacks(AppCompatDelegateImplV9.this.y);
            }
            AppCompatDelegateImplV9 appCompatDelegateImplV92 = AppCompatDelegateImplV9.this;
            if (appCompatDelegateImplV92.w != null) {
                appCompatDelegateImplV92.d();
                AppCompatDelegateImplV9 appCompatDelegateImplV93 = AppCompatDelegateImplV9.this;
                appCompatDelegateImplV93.z = ViewCompat.animate(appCompatDelegateImplV93.w).alpha(0.0f);
                AppCompatDelegateImplV9.this.z.setListener(new a());
            }
            AppCompatDelegateImplV9 appCompatDelegateImplV94 = AppCompatDelegateImplV9.this;
            AppCompatCallback appCompatCallback = appCompatDelegateImplV94.g;
            if (appCompatCallback != null) {
                appCompatCallback.onSupportActionModeFinished(appCompatDelegateImplV94.v);
            }
            AppCompatDelegateImplV9.this.v = null;
        }

        @Override // android.support.v7.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.a.onPrepareActionMode(actionMode, menu);
        }
    }

    public class d extends ContentFrameLayout {
        public d(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImplV9.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5) {
                    AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
                    appCompatDelegateImplV9.a(appCompatDelegateImplV9.b(0), true);
                    return true;
                }
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public void setBackgroundResource(int i) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), i));
        }
    }

    public final class e implements MenuPresenter.Callback {
        public e() {
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            boolean z2 = rootMenu != menuBuilder;
            AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
            if (z2) {
                menuBuilder = rootMenu;
            }
            PanelFeatureState panelFeatureStateA = appCompatDelegateImplV9.a((Menu) menuBuilder);
            if (panelFeatureStateA != null) {
                if (!z2) {
                    AppCompatDelegateImplV9.this.a(panelFeatureStateA, z);
                } else {
                    AppCompatDelegateImplV9.this.a(panelFeatureStateA.a, panelFeatureStateA, rootMenu);
                    AppCompatDelegateImplV9.this.a(panelFeatureStateA, true);
                }
            }
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callbackB;
            if (menuBuilder != null) {
                return true;
            }
            AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
            if (!appCompatDelegateImplV9.j || (callbackB = appCompatDelegateImplV9.b()) == null || AppCompatDelegateImplV9.this.p) {
                return true;
            }
            callbackB.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    public AppCompatDelegateImplV9(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
        this.z = null;
        this.M = new a();
    }

    public View a(View view2, String str, Context context, AttributeSet attributeSet) {
        throw null;
    }

    @Override // defpackage.o6
    public void a(CharSequence charSequence) {
        DecorContentParent decorContentParent = this.s;
        if (decorContentParent != null) {
            decorContentParent.setWindowTitle(charSequence);
            return;
        }
        ActionBar actionBar = this.h;
        if (actionBar != null) {
            actionBar.setWindowTitle(charSequence);
            return;
        }
        TextView textView = this.C;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void addContentView(View view2, ViewGroup.LayoutParams layoutParams) {
        e();
        ((ViewGroup) this.B.findViewById(R.id.content)).addView(view2, layoutParams);
        this.e.onContentChanged();
    }

    public final boolean b(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        DecorContentParent decorContentParent;
        Resources.Theme themeNewTheme;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
        if (this.p) {
            return false;
        }
        if (panelFeatureState.m) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.I;
        if (panelFeatureState2 != null && panelFeatureState2 != panelFeatureState) {
            a(panelFeatureState2, false);
        }
        Window.Callback callbackB = b();
        if (callbackB != null) {
            panelFeatureState.i = callbackB.onCreatePanelView(panelFeatureState.a);
        }
        int i = panelFeatureState.a;
        boolean z = i == 0 || i == 108;
        if (z && (decorContentParent3 = this.s) != null) {
            decorContentParent3.setMenuPrepared();
        }
        if (panelFeatureState.i == null && (!z || !(this.h instanceof y6))) {
            if (panelFeatureState.j == null || panelFeatureState.q) {
                if (panelFeatureState.j == null) {
                    Context context = this.c;
                    int i2 = panelFeatureState.a;
                    if ((i2 == 0 || i2 == 108) && this.s != null) {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme = context.getTheme();
                        theme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarTheme, typedValue, true);
                        if (typedValue.resourceId != 0) {
                            themeNewTheme = context.getResources().newTheme();
                            themeNewTheme.setTo(theme);
                            themeNewTheme.applyStyle(typedValue.resourceId, true);
                            themeNewTheme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarWidgetTheme, typedValue, true);
                        } else {
                            theme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarWidgetTheme, typedValue, true);
                            themeNewTheme = null;
                        }
                        if (typedValue.resourceId != 0) {
                            if (themeNewTheme == null) {
                                themeNewTheme = context.getResources().newTheme();
                                themeNewTheme.setTo(theme);
                            }
                            themeNewTheme.applyStyle(typedValue.resourceId, true);
                        }
                        if (themeNewTheme != null) {
                            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
                            contextThemeWrapper.getTheme().setTo(themeNewTheme);
                            context = contextThemeWrapper;
                        }
                    }
                    MenuBuilder menuBuilder = new MenuBuilder(context);
                    menuBuilder.setCallback(this);
                    panelFeatureState.a(menuBuilder);
                    if (panelFeatureState.j == null) {
                        return false;
                    }
                }
                if (z && this.s != null) {
                    if (this.t == null) {
                        this.t = new b();
                    }
                    this.s.setMenu(panelFeatureState.j, this.t);
                }
                panelFeatureState.j.stopDispatchingItemsChanged();
                if (!callbackB.onCreatePanelMenu(panelFeatureState.a, panelFeatureState.j)) {
                    panelFeatureState.a(null);
                    if (z && (decorContentParent = this.s) != null) {
                        decorContentParent.setMenu(null, this.t);
                    }
                    return false;
                }
                panelFeatureState.q = false;
            }
            panelFeatureState.j.stopDispatchingItemsChanged();
            Bundle bundle = panelFeatureState.r;
            if (bundle != null) {
                panelFeatureState.j.restoreActionViewStates(bundle);
                panelFeatureState.r = null;
            }
            if (!callbackB.onPreparePanel(0, panelFeatureState.i, panelFeatureState.j)) {
                if (z && (decorContentParent2 = this.s) != null) {
                    decorContentParent2.setMenu(null, this.t);
                }
                panelFeatureState.j.startDispatchingItemsChanged();
                return false;
            }
            boolean z2 = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.qwertyMode = z2;
            panelFeatureState.j.setQwertyMode(z2);
            panelFeatureState.j.startDispatchingItemsChanged();
        }
        panelFeatureState.m = true;
        panelFeatureState.n = false;
        this.I = panelFeatureState;
        return true;
    }

    @Override // defpackage.o6
    public void c() {
        e();
        if (this.j && this.h == null) {
            Window.Callback callback = this.e;
            if (callback instanceof Activity) {
                this.h = new WindowDecorActionBar((Activity) this.e, this.k);
            } else if (callback instanceof Dialog) {
                this.h = new WindowDecorActionBar((Dialog) this.e);
            }
            ActionBar actionBar = this.h;
            if (actionBar != null) {
                actionBar.setDefaultDisplayHomeAsUpEnabled(this.N);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0135  */
    @Override // android.support.v7.app.AppCompatDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View createView(android.view.View r8, java.lang.String r9, @android.support.annotation.NonNull android.content.Context r10, @android.support.annotation.NonNull android.util.AttributeSet r11) {
        /*
            Method dump skipped, instructions count: 644
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImplV9.createView(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet):android.view.View");
    }

    public void d() {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.z;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
        }
    }

    public final void e() {
        ViewGroup viewGroup;
        if (this.A) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = this.c.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.AppCompatTheme);
        if (!typedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionBar)) {
            typedArrayObtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowNoTitle, false)) {
            requestWindowFeature(1);
        } else if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionBar, false)) {
            requestWindowFeature(108);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            requestWindowFeature(109);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            requestWindowFeature(10);
        }
        this.m = typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_android_windowIsFloating, false);
        typedArrayObtainStyledAttributes.recycle();
        this.d.getDecorView();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.c);
        if (this.n) {
            viewGroup = this.l ? (ViewGroup) layoutInflaterFrom.inflate(android.support.v7.appcompat.R.layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) layoutInflaterFrom.inflate(android.support.v7.appcompat.R.layout.abc_screen_simple, (ViewGroup) null);
            ViewCompat.setOnApplyWindowInsetsListener(viewGroup, new t6(this));
        } else if (this.m) {
            viewGroup = (ViewGroup) layoutInflaterFrom.inflate(android.support.v7.appcompat.R.layout.abc_dialog_title_material, (ViewGroup) null);
            this.k = false;
            this.j = false;
        } else if (this.j) {
            TypedValue typedValue = new TypedValue();
            this.c.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarTheme, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new ContextThemeWrapper(this.c, typedValue.resourceId) : this.c).inflate(android.support.v7.appcompat.R.layout.abc_screen_toolbar, (ViewGroup) null);
            DecorContentParent decorContentParent = (DecorContentParent) viewGroup.findViewById(android.support.v7.appcompat.R.id.decor_content_parent);
            this.s = decorContentParent;
            decorContentParent.setWindowCallback(b());
            if (this.k) {
                this.s.initFeature(109);
            }
            if (this.E) {
                this.s.initFeature(2);
            }
            if (this.F) {
                this.s.initFeature(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            StringBuilder sbA = g9.a("AppCompat does not support the current theme features: { windowActionBar: ");
            sbA.append(this.j);
            sbA.append(", windowActionBarOverlay: ");
            sbA.append(this.k);
            sbA.append(", android:windowIsFloating: ");
            sbA.append(this.m);
            sbA.append(", windowActionModeOverlay: ");
            sbA.append(this.l);
            sbA.append(", windowNoTitle: ");
            sbA.append(this.n);
            sbA.append(" }");
            throw new IllegalArgumentException(sbA.toString());
        }
        if (this.s == null) {
            this.C = (TextView) viewGroup.findViewById(android.support.v7.appcompat.R.id.title);
        }
        ViewUtils.makeOptionalFitsSystemWindows(viewGroup);
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(android.support.v7.appcompat.R.id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup) this.d.findViewById(R.id.content);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(R.id.content);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        this.d.setContentView(viewGroup);
        contentFrameLayout.setAttachListener(new u6(this));
        this.B = viewGroup;
        Window.Callback callback = this.e;
        CharSequence title = callback instanceof Activity ? ((Activity) callback).getTitle() : this.o;
        if (!TextUtils.isEmpty(title)) {
            a(title);
        }
        ContentFrameLayout contentFrameLayout2 = (ContentFrameLayout) this.B.findViewById(R.id.content);
        View decorView = this.d.getDecorView();
        contentFrameLayout2.setDecorPadding(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray typedArrayObtainStyledAttributes2 = this.c.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.AppCompatTheme);
        typedArrayObtainStyledAttributes2.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout2.getMinWidthMajor());
        typedArrayObtainStyledAttributes2.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout2.getMinWidthMinor());
        if (typedArrayObtainStyledAttributes2.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            typedArrayObtainStyledAttributes2.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout2.getFixedWidthMajor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            typedArrayObtainStyledAttributes2.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout2.getFixedWidthMinor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            typedArrayObtainStyledAttributes2.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout2.getFixedHeightMajor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            typedArrayObtainStyledAttributes2.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout2.getFixedHeightMinor());
        }
        typedArrayObtainStyledAttributes2.recycle();
        contentFrameLayout2.requestLayout();
        this.A = true;
        PanelFeatureState panelFeatureStateB = b(0);
        if (this.p) {
            return;
        }
        if (panelFeatureStateB == null || panelFeatureStateB.j == null) {
            c(108);
        }
    }

    public final boolean f() {
        ViewGroup viewGroup;
        return this.A && (viewGroup = this.B) != null && ViewCompat.isLaidOut(viewGroup);
    }

    @Override // android.support.v7.app.AppCompatDelegate
    @Nullable
    public <T extends View> T findViewById(@IdRes int i) {
        e();
        return (T) this.d.findViewById(i);
    }

    public final void g() {
        if (this.A) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public boolean hasWindowFeature(int i) {
        int iD = d(i);
        if (iD == 1) {
            return this.n;
        }
        if (iD == 2) {
            return this.E;
        }
        if (iD == 5) {
            return this.F;
        }
        if (iD == 10) {
            return this.l;
        }
        if (iD == 108) {
            return this.j;
        }
        if (iD != 109) {
            return false;
        }
        return this.k;
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void installViewFactory() {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.c);
        if (layoutInflaterFrom.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflaterFrom, this);
        } else {
            if (layoutInflaterFrom.getFactory2() instanceof AppCompatDelegateImplV9) {
                return;
            }
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void invalidateOptionsMenu() {
        c();
        ActionBar actionBar = this.h;
        if (actionBar == null || !actionBar.invalidateOptionsMenu()) {
            c(0);
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void onConfigurationChanged(Configuration configuration) {
        if (this.j && this.A) {
            c();
            ActionBar actionBar = this.h;
            if (actionBar != null) {
                actionBar.onConfigurationChanged(configuration);
            }
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.c);
        applyDayNight();
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void onCreate(Bundle bundle) {
        Window.Callback callback = this.e;
        if (!(callback instanceof Activity) || NavUtils.getParentActivityName((Activity) callback) == null) {
            return;
        }
        ActionBar actionBar = this.h;
        if (actionBar == null) {
            this.N = true;
        } else {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:74:0x013c  */
    @Override // android.view.LayoutInflater.Factory2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onCreateView(android.view.View r8, java.lang.String r9, android.content.Context r10, android.util.AttributeSet r11) {
        /*
            Method dump skipped, instructions count: 650
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImplV9.onCreateView(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet):android.view.View");
    }

    @Override // defpackage.o6, android.support.v7.app.AppCompatDelegate
    public void onDestroy() {
        if (this.K) {
            this.d.getDecorView().removeCallbacks(this.M);
        }
        this.p = true;
        ActionBar actionBar = this.h;
        if (actionBar != null) {
            actionBar.a();
        }
    }

    @Override // android.support.v7.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        PanelFeatureState panelFeatureStateA;
        Window.Callback callbackB = b();
        if (callbackB == null || this.p || (panelFeatureStateA = a((Menu) menuBuilder.getRootMenu())) == null) {
            return false;
        }
        return callbackB.onMenuItemSelected(panelFeatureStateA.a, menuItem);
    }

    @Override // android.support.v7.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        DecorContentParent decorContentParent = this.s;
        if (decorContentParent == null || !decorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(this.c).hasPermanentMenuKey() && !this.s.isOverflowMenuShowPending())) {
            PanelFeatureState panelFeatureStateB = b(0);
            panelFeatureStateB.p = true;
            a(panelFeatureStateB, false);
            a(panelFeatureStateB, (KeyEvent) null);
            return;
        }
        Window.Callback callbackB = b();
        if (this.s.isOverflowMenuShowing()) {
            this.s.hideOverflowMenu();
            if (this.p) {
                return;
            }
            callbackB.onPanelClosed(108, b(0).j);
            return;
        }
        if (callbackB == null || this.p) {
            return;
        }
        if (this.K && (1 & this.L) != 0) {
            this.d.getDecorView().removeCallbacks(this.M);
            this.M.run();
        }
        PanelFeatureState panelFeatureStateB2 = b(0);
        MenuBuilder menuBuilder2 = panelFeatureStateB2.j;
        if (menuBuilder2 == null || panelFeatureStateB2.q || !callbackB.onPreparePanel(0, panelFeatureStateB2.i, menuBuilder2)) {
            return;
        }
        callbackB.onMenuOpened(108, panelFeatureStateB2.j);
        this.s.showOverflowMenu();
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void onPostCreate(Bundle bundle) {
        e();
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void onPostResume() {
        c();
        ActionBar actionBar = this.h;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void onStop() {
        c();
        ActionBar actionBar = this.h;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(false);
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public boolean requestWindowFeature(int i) {
        int iD = d(i);
        if (this.n && iD == 108) {
            return false;
        }
        if (this.j && iD == 1) {
            this.j = false;
        }
        if (iD == 1) {
            g();
            this.n = true;
            return true;
        }
        if (iD == 2) {
            g();
            this.E = true;
            return true;
        }
        if (iD == 5) {
            g();
            this.F = true;
            return true;
        }
        if (iD == 10) {
            g();
            this.l = true;
            return true;
        }
        if (iD == 108) {
            g();
            this.j = true;
            return true;
        }
        if (iD != 109) {
            return this.d.requestFeature(iD);
        }
        g();
        this.k = true;
        return true;
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void setContentView(View view2) {
        e();
        ViewGroup viewGroup = (ViewGroup) this.B.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view2);
        this.e.onContentChanged();
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void setSupportActionBar(Toolbar toolbar) {
        if (this.e instanceof Activity) {
            c();
            ActionBar actionBar = this.h;
            if (actionBar instanceof WindowDecorActionBar) {
                throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
            }
            this.i = null;
            if (actionBar != null) {
                actionBar.a();
            }
            if (toolbar != null) {
                y6 y6Var = new y6(toolbar, ((Activity) this.e).getTitle(), this.f);
                this.h = y6Var;
                this.d.setCallback(y6Var.c);
            } else {
                this.h = null;
                this.d.setCallback(this.f);
            }
            invalidateOptionsMenu();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0047  */
    @Override // android.support.v7.app.AppCompatDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.support.v7.view.ActionMode startSupportActionMode(@android.support.annotation.NonNull android.support.v7.view.ActionMode.Callback r8) {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImplV9.startSupportActionMode(android.support.v7.view.ActionMode$Callback):android.support.v7.view.ActionMode");
    }

    public final int d(int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        }
        if (i != 9) {
            return i;
        }
        Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
        return 109;
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void setContentView(int i) {
        e();
        ViewGroup viewGroup = (ViewGroup) this.B.findViewById(R.id.content);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.c).inflate(i, viewGroup);
        this.e.onContentChanged();
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:92:? A[RETURN, SYNTHETIC] */
    @Override // defpackage.o6
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(android.view.KeyEvent r7) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImplV9.a(android.view.KeyEvent):boolean");
    }

    public final void c(int i) {
        this.L = (1 << i) | this.L;
        if (this.K) {
            return;
        }
        ViewCompat.postOnAnimation(this.d.getDecorView(), this.M);
        this.K = true;
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void setContentView(View view2, ViewGroup.LayoutParams layoutParams) {
        e();
        ViewGroup viewGroup = (ViewGroup) this.B.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view2, layoutParams);
        this.e.onContentChanged();
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:95:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(android.support.v7.app.AppCompatDelegateImplV9.PanelFeatureState r14, android.view.KeyEvent r15) {
        /*
            Method dump skipped, instructions count: 411
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImplV9.a(android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState, android.view.KeyEvent):void");
    }

    public PanelFeatureState b(int i) {
        PanelFeatureState[] panelFeatureStateArr = this.H;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[i + 1];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.H = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState(i);
        panelFeatureStateArr[i] = panelFeatureState2;
        return panelFeatureState2;
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    public int e(int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z;
        boolean z2;
        ActionBarContextView actionBarContextView = this.w;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.w.getLayoutParams();
            if (this.w.isShown()) {
                if (this.O == null) {
                    this.O = new Rect();
                    this.P = new Rect();
                }
                Rect rect = this.O;
                Rect rect2 = this.P;
                rect.set(0, i, 0, 0);
                ViewUtils.computeFitSystemWindows(this.B, rect, rect2);
                if (marginLayoutParams.topMargin != (rect2.top == 0 ? i : 0)) {
                    marginLayoutParams.topMargin = i;
                    View view2 = this.D;
                    if (view2 == null) {
                        View view3 = new View(this.c);
                        this.D = view3;
                        view3.setBackgroundColor(this.c.getResources().getColor(android.support.v7.appcompat.R.color.abc_input_method_navigation_guard));
                        this.B.addView(this.D, -1, new ViewGroup.LayoutParams(-1, i));
                    } else {
                        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                        if (layoutParams.height != i) {
                            layoutParams.height = i;
                            this.D.setLayoutParams(layoutParams);
                        }
                    }
                    z2 = true;
                } else {
                    z2 = false;
                }
                z = this.D != null;
                if (!this.l && z) {
                    i = 0;
                }
                boolean z3 = z;
                z = z2;
                z = z3;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z = false;
            } else {
                z = false;
                z = false;
            }
            if (z) {
                this.w.setLayoutParams(marginLayoutParams);
            }
        }
        View view4 = this.D;
        if (view4 != null) {
            view4.setVisibility(z ? 0 : 8);
        }
        return i;
    }

    public void a(MenuBuilder menuBuilder) {
        if (this.G) {
            return;
        }
        this.G = true;
        this.s.dismissPopups();
        Window.Callback callbackB = b();
        if (callbackB != null && !this.p) {
            callbackB.onPanelClosed(108, menuBuilder);
        }
        this.G = false;
    }

    public void a(PanelFeatureState panelFeatureState, boolean z) {
        ViewGroup viewGroup;
        DecorContentParent decorContentParent;
        if (z && panelFeatureState.a == 0 && (decorContentParent = this.s) != null && decorContentParent.isOverflowMenuShowing()) {
            a(panelFeatureState.j);
            return;
        }
        WindowManager windowManager = (WindowManager) this.c.getSystemService("window");
        if (windowManager != null && panelFeatureState.o && (viewGroup = panelFeatureState.g) != null) {
            windowManager.removeView(viewGroup);
            if (z) {
                a(panelFeatureState.a, panelFeatureState, null);
            }
        }
        panelFeatureState.m = false;
        panelFeatureState.n = false;
        panelFeatureState.o = false;
        panelFeatureState.h = null;
        panelFeatureState.p = true;
        if (this.I == panelFeatureState) {
            this.I = null;
        }
    }

    public void a(int i, PanelFeatureState panelFeatureState, Menu menu) {
        if (menu == null) {
            if (panelFeatureState == null && i >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.H;
                if (i < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i];
                }
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.j;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.o) && !this.p) {
            this.e.onPanelClosed(i, menu);
        }
    }

    public PanelFeatureState a(Menu menu) {
        PanelFeatureState[] panelFeatureStateArr = this.H;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null && panelFeatureState.j == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    public final boolean a(PanelFeatureState panelFeatureState, int i, KeyEvent keyEvent, int i2) {
        MenuBuilder menuBuilder;
        boolean zPerformShortcut = false;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.m || b(panelFeatureState, keyEvent)) && (menuBuilder = panelFeatureState.j) != null) {
            zPerformShortcut = menuBuilder.performShortcut(i, keyEvent, i2);
        }
        if (zPerformShortcut && (i2 & 1) == 0 && this.s == null) {
            a(panelFeatureState, true);
        }
        return zPerformShortcut;
    }

    public void a(int i) {
        PanelFeatureState panelFeatureStateB;
        PanelFeatureState panelFeatureStateB2 = b(i);
        if (panelFeatureStateB2.j != null) {
            Bundle bundle = new Bundle();
            panelFeatureStateB2.j.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelFeatureStateB2.r = bundle;
            }
            panelFeatureStateB2.j.stopDispatchingItemsChanged();
            panelFeatureStateB2.j.clear();
        }
        panelFeatureStateB2.q = true;
        panelFeatureStateB2.p = true;
        if ((i != 108 && i != 0) || this.s == null || (panelFeatureStateB = b(0)) == null) {
            return;
        }
        panelFeatureStateB.m = false;
        b(panelFeatureStateB, null);
    }
}
