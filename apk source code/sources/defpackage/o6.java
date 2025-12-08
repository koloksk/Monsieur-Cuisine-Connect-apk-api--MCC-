package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.appcompat.R;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import java.lang.Thread;

@RequiresApi(14)
/* loaded from: classes.dex */
public abstract class o6 extends AppCompatDelegate {
    public static boolean q;
    public static final int[] r;
    public final Context c;
    public final Window d;
    public final Window.Callback e;
    public final Window.Callback f;
    public final AppCompatCallback g;
    public ActionBar h;
    public MenuInflater i;
    public boolean j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public CharSequence o;
    public boolean p;

    public static class a implements Thread.UncaughtExceptionHandler {
        public final /* synthetic */ Thread.UncaughtExceptionHandler a;

        public a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.a = uncaughtExceptionHandler;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            String message;
            boolean z = false;
            if ((th instanceof Resources.NotFoundException) && (message = th.getMessage()) != null && (message.contains("drawable") || message.contains("Drawable"))) {
                z = true;
            }
            if (!z) {
                this.a.uncaughtException(thread, th);
                return;
            }
            Resources.NotFoundException notFoundException = new Resources.NotFoundException(th.getMessage() + ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
            notFoundException.initCause(th.getCause());
            notFoundException.setStackTrace(th.getStackTrace());
            this.a.uncaughtException(thread, notFoundException);
        }
    }

    public class b implements ActionBarDrawerToggle.Delegate {
        public b() {
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            return o6.this.a();
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(o6.this.a(), (AttributeSet) null, new int[]{R.attr.homeAsUpIndicator});
            Drawable drawable = tintTypedArrayObtainStyledAttributes.getDrawable(0);
            tintTypedArrayObtainStyledAttributes.recycle();
            return drawable;
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            o6 o6Var = o6.this;
            o6Var.c();
            ActionBar actionBar = o6Var.h;
            return (actionBar == null || (actionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i) {
            o6 o6Var = o6.this;
            o6Var.c();
            ActionBar actionBar = o6Var.h;
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(i);
            }
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, int i) {
            o6 o6Var = o6.this;
            o6Var.c();
            ActionBar actionBar = o6Var.h;
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(drawable);
                actionBar.setHomeActionContentDescription(i);
            }
        }
    }

    public class c extends WindowCallbackWrapper {
        public c(Window.Callback callback) {
            super(callback);
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return o6.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean dispatchKeyShortcutEvent(android.view.KeyEvent r6) {
            /*
                r5 = this;
                boolean r0 = super.dispatchKeyShortcutEvent(r6)
                r1 = 0
                r2 = 1
                if (r0 != 0) goto L4f
                o6 r0 = defpackage.o6.this
                int r3 = r6.getKeyCode()
                android.support.v7.app.AppCompatDelegateImplV9 r0 = (android.support.v7.app.AppCompatDelegateImplV9) r0
                r0.c()
                android.support.v7.app.ActionBar r4 = r0.h
                if (r4 == 0) goto L1f
                boolean r3 = r4.onKeyShortcut(r3, r6)
                if (r3 == 0) goto L1f
            L1d:
                r6 = r2
                goto L4d
            L1f:
                android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState r3 = r0.I
                if (r3 == 0) goto L34
                int r4 = r6.getKeyCode()
                boolean r3 = r0.a(r3, r4, r6, r2)
                if (r3 == 0) goto L34
                android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState r6 = r0.I
                if (r6 == 0) goto L1d
                r6.n = r2
                goto L1d
            L34:
                android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState r3 = r0.I
                if (r3 != 0) goto L4c
                android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState r3 = r0.b(r1)
                r0.b(r3, r6)
                int r4 = r6.getKeyCode()
                boolean r6 = r0.a(r3, r4, r6, r2)
                r3.m = r1
                if (r6 == 0) goto L4c
                goto L1d
            L4c:
                r6 = r1
            L4d:
                if (r6 == 0) goto L50
            L4f:
                r1 = r2
            L50:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: o6.c.dispatchKeyShortcutEvent(android.view.KeyEvent):boolean");
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onContentChanged() {
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onCreatePanelMenu(int i, Menu menu) {
            if (i != 0 || (menu instanceof MenuBuilder)) {
                return super.onCreatePanelMenu(i, menu);
            }
            return false;
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            AppCompatDelegateImplV9 appCompatDelegateImplV9 = (AppCompatDelegateImplV9) o6.this;
            if (appCompatDelegateImplV9 == null) {
                throw null;
            }
            if (i == 108) {
                appCompatDelegateImplV9.c();
                ActionBar actionBar = appCompatDelegateImplV9.h;
                if (actionBar != null) {
                    actionBar.dispatchMenuVisibilityChanged(true);
                }
            }
            return true;
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onPanelClosed(int i, Menu menu) {
            super.onPanelClosed(i, menu);
            AppCompatDelegateImplV9 appCompatDelegateImplV9 = (AppCompatDelegateImplV9) o6.this;
            if (appCompatDelegateImplV9 == null) {
                throw null;
            }
            if (i == 108) {
                appCompatDelegateImplV9.c();
                ActionBar actionBar = appCompatDelegateImplV9.h;
                if (actionBar != null) {
                    actionBar.dispatchMenuVisibilityChanged(false);
                    return;
                }
                return;
            }
            if (i == 0) {
                AppCompatDelegateImplV9.PanelFeatureState panelFeatureStateB = appCompatDelegateImplV9.b(i);
                if (panelFeatureStateB.o) {
                    appCompatDelegateImplV9.a(panelFeatureStateB, false);
                }
            }
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onPreparePanel(int i, View view2, Menu menu) {
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (i == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true);
            }
            boolean zOnPreparePanel = super.onPreparePanel(i, view2, menu);
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(false);
            }
            return zOnPreparePanel;
        }
    }

    static {
        if (0 != 0 && !q) {
            Thread.setDefaultUncaughtExceptionHandler(new a(Thread.getDefaultUncaughtExceptionHandler()));
            q = true;
        }
        r = new int[]{android.R.attr.windowBackground};
    }

    public o6(Context context, Window window, AppCompatCallback appCompatCallback) {
        this.c = context;
        this.d = window;
        this.g = appCompatCallback;
        Window.Callback callback = window.getCallback();
        this.e = callback;
        if (callback instanceof c) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        Window.Callback callbackA = a(callback);
        this.f = callbackA;
        this.d.setCallback(callbackA);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, (AttributeSet) null, r);
        Drawable drawableIfKnown = tintTypedArrayObtainStyledAttributes.getDrawableIfKnown(0);
        if (drawableIfKnown != null) {
            this.d.setBackgroundDrawable(drawableIfKnown);
        }
        tintTypedArrayObtainStyledAttributes.recycle();
    }

    public final Context a() {
        c();
        ActionBar actionBar = this.h;
        Context themedContext = actionBar != null ? actionBar.getThemedContext() : null;
        return themedContext == null ? this.c : themedContext;
    }

    public abstract Window.Callback a(Window.Callback callback);

    public abstract void a(CharSequence charSequence);

    public abstract boolean a(KeyEvent keyEvent);

    public final Window.Callback b() {
        return this.d.getCallback();
    }

    public abstract void c();

    @Override // android.support.v7.app.AppCompatDelegate
    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return new b();
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public MenuInflater getMenuInflater() {
        if (this.i == null) {
            c();
            ActionBar actionBar = this.h;
            this.i = new SupportMenuInflater(actionBar != null ? actionBar.getThemedContext() : this.c);
        }
        return this.i;
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public ActionBar getSupportActionBar() {
        c();
        return this.h;
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void onDestroy() {
        this.p = true;
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void onSaveInstanceState(Bundle bundle) {
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void onStart() {
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public final void setTitle(CharSequence charSequence) {
        this.o = charSequence;
        a(charSequence);
    }
}
